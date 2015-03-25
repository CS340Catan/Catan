package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.DiscardCardsParams;
import shared.definitions.ResourceType;
import shared.model.Player;
import shared.model.ResourceList;
import shared.utils.ServerResponseException;

/**
 * This command removes resource cards from the players hand
 * 
 * @author winstonhurst
 */
public class DiscardCardsCommand extends ICommand {

	int playerIndex;
	ResourceList discardedCards;

	/**
	 * 
	 * @param params
	 *            - list of cards to discard and the id of the player discarding
	 */
	public DiscardCardsCommand(DiscardCardsParams params) {
		playerIndex = params.getPlayerIndex();
		discardedCards = params.getDiscardedCards();
		this.setType("DiscardCards");

	}

	/**
	 * Removes the cards from the player's hand. If all players are done
	 * discarding, change game status to 'Robbing'
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {

		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController controller = new ServerModelController(model);

		if (controller.canDiscardCards(playerIndex)) {
			/*
			 * Get the cards that are being discarded from the discardedCards
			 * resource list.
			 */
			int discardBrick = discardedCards.getBrick();
			int discardOre = discardedCards.getOre();
			int discardSheep = discardedCards.getSheep();
			int discardWheat = discardedCards.getWheat();
			int discardWood = discardedCards.getWood();

			/*
			 * Removed the cards that are being discarded from the user while
			 * returning those cards back to the bank.
			 */
			model.addResourceFromBank(playerIndex, ResourceType.BRICK,
					-discardBrick);
			model.addResourceFromBank(playerIndex, ResourceType.ORE,
					-discardOre);
			model.addResourceFromBank(playerIndex, ResourceType.SHEEP,
					-discardSheep);
			model.addResourceFromBank(playerIndex, ResourceType.WHEAT,
					-discardWheat);
			model.addResourceFromBank(playerIndex, ResourceType.WOOD,
					-discardWood);

			/*
			 * Set player's discard boolean equal to true, such that the player
			 * has discarded.
			 */
			model.getPlayers()[playerIndex].setDiscarded(true);

			/*
			 * If no other players need to discard, then change the status of
			 * the game to playing within the model's turn tracker. Also, reset
			 * all player's discard boolean equal to false for next discard
			 * phase.
			 */
			if (!model.needToDiscard()) {
				for (Player player : model.getPlayers()) {
					player.setDiscarded(false);
				}
				model.getTurnTracker().setStatus("Robbing");
			}

			/*
			 * Add this command to the list of commands currently stored inside
			 * the model.
			 */
			model.getCommands().add(this);
			model.incrementVersion();
		} else {
			throw new ServerResponseException("Unable to discard cards");
		}
	}
}
