package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.DiscardCardsParams;
import shared.definitions.ResourceType;
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
	 * If all players are done discarding, change game status to 'Robbing'
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
			//subtract discarded cards from players resources, add back to bank
			model.addResourceFromBank(playerIndex, ResourceType.BRICK, -brick);
			model.addResourceFromBank(playerIndex, ResourceType.ORE, -ore);
			model.addResourceFromBank(playerIndex, ResourceType.SHEEP, -sheep);
			model.addResourceFromBank(playerIndex, ResourceType.WHEAT, -wheat);
			model.addResourceFromBank(playerIndex, ResourceType.WOOD, -wood);
		}
		
		// if this is last person to discard, change state to "Robbing", how to know if last person?
		//-----
		
	}

}

