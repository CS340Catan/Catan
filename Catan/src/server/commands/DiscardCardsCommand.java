package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.DiscardCardsParams;
import shared.model.ResourceList;

/**
 * 
 * @author winstonhurst
 *This command removes resource cards from the players hand
 */
public class DiscardCardsCommand implements ICommand {
	
	int playerIndex;
	ResourceList discardedCards;
	
	/**
	 * 
	 * @param params - list of cards to discard and the id of the player discarding
	 */
	public DiscardCardsCommand(DiscardCardsParams params){
		
		playerIndex = params.getPlayerIndex();
		discardedCards = params.getDiscardedCards();
	}
	
	/**
	 * Removes the cards from the player's hand.
	 * If all players have are done discarding, change game status to 'Playing'
	 */
	@Override
	public void execute() {
	
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController controller = new ServerModelController(model);
		
		ResourceList resources = model.getPlayers()[playerIndex].getResources();
		
		//get discarded cards
		int brick = discardedCards.getBrick();
		int ore = discardedCards.getOre();
		int sheep = discardedCards.getSheep();
		int wheat = discardedCards.getWheat();
		int wood = discardedCards.getWood();
		
		if(controller.canDiscardCards(playerIndex)) {
			//subtract discarded cards from players resources
			resources.setBrick(resources.getBrick() - brick);
			resources.setOre(resources.getOre() - ore);
			resources.setSheep(resources.getSheep() - sheep);
			resources.setWheat(resources.getWheat() - wheat);
			resources.setWood(resources.getWood() - wood);
		}
		
	}

}

