package server.commands;

import client.model.ClientModel;
import server.facade.ServerFacade;
import server.model.ServerModel;
import shared.communication.AcceptTradeParams;
import shared.communication.TradeOfferParams;
import shared.model.ResourceList;
import shared.model.TradeOffer;

/**
 * This command will manipulate the ServerModel depending upon the boolean
 * contained within this command.
 * 
 * senderIndex - Who initiated the trade (i.e. sent the trade)
 * 
 * receiverIndex - Who's accepting / rejecting this trade
 * 
 * willAccept - Whether you accept the trade or not
 * 
 * @author Keloric
 */
public class AcceptTradeCommand implements ICommand {

	int senderIndex;
	int receiverIndex;
	boolean willAccept;

	public AcceptTradeCommand(AcceptTradeParams params) {
		
		receiverIndex = params.getPlayerIndex();
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
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		
		if(willAccept) {
			TradeOffer tradeOffer = model.getTradeOffer();
	
			senderIndex = tradeOffer.getSender();
			//receiverIndex = tradeOffer.getReceiver();	//already got from accept params
			
			ResourceList resourceList = tradeOffer.getResourceList();
			int brick = resourceList.getBrick();
			int ore = resourceList.getOre();
			int sheep = resourceList.getSheep();
			int wheat = resourceList.getWheat();
			int wood = resourceList.getWood();
			

		}

	}

}
