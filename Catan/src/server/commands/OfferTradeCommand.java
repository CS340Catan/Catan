package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.TradeOfferParams;
import shared.model.ResourceList;
import shared.model.TradeOffer;
import shared.utils.ServerResponseException;

/**
 * @author Drewfus
 * This is the command class for the OfferTrade function called on the server.
 * It will receive a TradeOfferParams object and a gameID in the constructor
 */

public class OfferTradeCommand implements ICommand {
	
	int playerIndex;
	ResourceList offer;
	int receiver;
	int gameID;
	
	public OfferTradeCommand(TradeOfferParams params, int gameID) {
		this.playerIndex = params.getPlayerIndex();
		this.offer = params.getOffer();
		this.receiver = params.getReceiver();
		this.gameID = gameID;
	}
	
	/**
	 * Updates the model to contain the trade offer
	 * @throws ServerResponseException 
	 */
	@Override
	public void execute() throws ServerResponseException {
		ServerModel model  = ServerFacade.getSingleton().getServerModel();
		ServerModelController controller = new ServerModelController(model);
		
		if(controller.canOfferTrade(playerIndex, offer)){
			model.setTradeOffer(new TradeOffer(playerIndex,receiver, offer));
		}
		
		else{
			throw new ServerResponseException("Player " + playerIndex +" cannot offer this trade");
		}
	}

}

