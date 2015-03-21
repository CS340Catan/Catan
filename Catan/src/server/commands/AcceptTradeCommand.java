package server.commands;

import client.model.ClientModel;
import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.AcceptTradeParams;
import shared.communication.TradeOfferParams;
import shared.model.ResourceList;
import shared.model.TradeOffer;
import shared.utils.ServerResponseException;

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
	 * @throws ServerResponseException 
	 */
	@Override
	public void execute() throws ServerResponseException {
		// TODO see what resources were offered in model, if willAccept, reallocate, otherwise do nothing
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController controller = new ServerModelController(model);
		
		ResourceList resourceList = model.getTradeOffer().getResourceList();
			
		if(willAccept) {
			
			if(controller.canAcceptTrade(receiverIndex, resourceList)) {
				
				TradeOffer tradeOffer = model.getTradeOffer();
		
				int senderIndex = tradeOffer.getSender();
				//receiverIndex = tradeOffer.getReceiver();	//already got from accept params
				
				/*these shouldn't be copies of the model data, this should be accessing and changing the model itself, right?*/
				
				//get resources that will be traded
				int brick = resourceList.getBrick();
				int ore = resourceList.getOre();
				int sheep = resourceList.getSheep();
				int wheat = resourceList.getWheat();
				int wood = resourceList.getWood();
				
				//adjust the senders resources
				ResourceList senderList = model.getPlayers()[senderIndex].getResources();
				senderList.setBrick(senderList.getBrick() + brick);
				senderList.setOre(senderList.getOre() + ore);
				senderList.setSheep(senderList.getSheep() + sheep);
				senderList.setWheat(senderList.getWheat() + wheat);
				senderList.setWood(senderList.getWood() + wood);
				
				//adjust the receivers resources
				ResourceList receiverList = model.getPlayers()[receiverIndex].getResources();
				receiverList.setBrick(receiverList.getBrick() - brick);
				receiverList.setOre(receiverList.getOre() - ore);
				receiverList.setSheep(receiverList.getSheep() - sheep);
				receiverList.setWheat(receiverList.getWheat() - wheat);
				receiverList.setWood(receiverList.getWood() - wood);
				
				//get rid of offerTrade params in model
				model.setTradeOffer(null);
				
				/*
				 * Add this command to the list of commands currently stored inside
				 * the model.
				 */
				model.getCommands().add(this);
				model.incrementVersion();
			}
			else {
				throw new ServerResponseException("Unable to accept trade offer, insufficient resources");
			}

		}
		


	}

}
