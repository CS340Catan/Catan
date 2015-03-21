package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.UserActionParams;
import shared.definitions.ResourceType;
import shared.model.Player;
import shared.model.ResourceList;
import shared.utils.ServerResponseException;

/**
 * 
 * @author winstonhurst Gives a player a development card. The development card
 *         is added to the player hand, and the appropriate resources are
 *         removed. All pre-conditions have been met earlier.
 */
public class BuyDevCardCommand implements ICommand {
	int playerIndex;

	/**
	 * 
	 * @param params
	 *            - contains the index (int) of the player buying the
	 *            development card
	 * @param gameID
	 *            - id of game on which the command is to be executed
	 */
	public BuyDevCardCommand(UserActionParams params) {
		this.playerIndex = params.getPlayerIndex();
	}

	/**
	 * Gets the appropriate game by id adds a development card to the player's
	 * hand. If the development card is monument, it is added to the old cards
	 * list If the development card is NOT monument, it is added to the new
	 * cards list. Removes 1 ore, 1 wheat, 1 sheep from the player's resources.
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController modelController = new ServerModelController(model);

		if (modelController.canBuyDevCard(this.playerIndex)) {
			Player player = model.getPlayers()[this.playerIndex];

			/*
			 * If the player can buy a development card, select a card at random
			 * from the deck and add that card to the user's old development
			 * card list if the card is a monument. Else add the card to the new
			 * card list.
			 */
			model.getDeck().drawFromDeck(player);

			/*
			 * Remove from the player's resources 1 ore, 1 wheat, and 1 sheep.
			 * This is done by inverting a (0,1,1,1,0) list, and "adding" this
			 * inverted list to the player's resource list.
			 */
			ResourceList devCardCost = new ResourceList(0, 1, 1, 1, 0);
			player.getResources().addResources(devCardCost.invertList());
			
			/*
			 * Add this command to the list of commands currently stored inside
			 * the model.
			 */
			model.getCommands().add(this);
			model.incrementVersion();

		} else {
			throw new ServerResponseException("Unable to buy dev card.");
		}
	}

}
