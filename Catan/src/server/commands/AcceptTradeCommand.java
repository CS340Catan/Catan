package server.commands;

import shared.communication.AcceptTradeParams;
import shared.communication.TradeOfferParams;
import shared.model.ClientModel;
import shared.model.ResourceList;
import shared.model.TradeOffer;

/**
 * This command will manipulate the ServerModel depending upon the boolean
 * contained within this command.
 * 
 * playerIndex_1 - Who initiated the trade (i.e. sent the trade)
 * 
 * playerIndex_2 - Who's accepting / rejecting this trade
 * 
 * willAccept - Whether you accept the trade or not
 * 
 * @author Keloric
 */
public class AcceptTradeCommand implements ICommand {

	int playerIndex_1;
	int playerIndex_2;
	boolean willAccept;
	ClientModel serverModel;

	public AcceptTradeCommand(AcceptTradeParams params) {
		
		serverModel = ClientModel.getSingleton();
		willAccept = params.isWillAccept();
		
	}
	
	/**
	 * This method will manipulate the serverModel stored within the command. If
	 * the trade is to be accepted, then the appropriate resources should be
	 * added or removed from the players listed.
	 */
	@Override
	public void execute() {
		// TODO see what resources were offered in model, if willAccept, reallocate, otherwise do nothing
		if(willAccept) {
			TradeOffer tradeOffer = serverModel.getTradeOffer();
	
			playerIndex_1 = tradeOffer.getSender();
			playerIndex_2 = tradeOffer.getReceiver();
			
			ResourceList resourceList = tradeOffer.getResourceList();
			int brick = resourceList.getBrick();
			int ore = resourceList.getOre();
			int sheep = resourceList.getSheep();
			int wheat = resourceList.getWheat();
			int wood = resourceList.getWood();
			

		}

	}

}
