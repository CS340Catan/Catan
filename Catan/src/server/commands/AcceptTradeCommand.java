package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.AcceptTradeParams;
import shared.definitions.CatanColor;
import shared.model.MessageLine;
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
public class AcceptTradeCommand extends ICommand {

	int receiverIndex;
	boolean willAccept;

	public AcceptTradeCommand(AcceptTradeParams params) {

		receiverIndex = params.getPlayerIndex();
		willAccept = params.isWillAccept();
		this.setType("AcceptTrade");
	}

	/**
	 * This method will manipulate the serverModel stored within the command. If
	 * the trade is to be accepted, then the appropriate resources should be
	 * added or removed from the players listed.
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController controller = new ServerModelController(model);

		ResourceList tradeResources = model.getTradeOffer().getResourceList();

		if (willAccept) {
			if (controller.canAcceptTrade(receiverIndex, tradeResources.invertList())) {
				/*
				 * Grab the trade offer currently stored within the tradeOffer
				 * object within the server model. Grab the sender index from
				 * this object.
				 */
				TradeOffer tradeOffer = model.getTradeOffer();
				int senderIndex = tradeOffer.getSender();

				/*
				 * Access the resources that are going to be traded between the
				 * two players.
				 */
				int tradeBrick = tradeResources.getBrick();
				int tradeOre = tradeResources.getOre();
				int tradeSheep = tradeResources.getSheep();
				int tradeWheat = tradeResources.getWheat();
				int tradeWood = tradeResources.getWood();

				/*
				 * Adjust the resources of the sender by subtracting the trade
				 * resources to the player's current resource list.
				 */
				ResourceList senderResources = model.getPlayers()[senderIndex]
						.getResources();
				senderResources.setBrick(senderResources.getBrick()
						- tradeBrick);
				senderResources.setOre(senderResources.getOre() - tradeOre);
				senderResources.setSheep(senderResources.getSheep()
						- tradeSheep);
				senderResources.setWheat(senderResources.getWheat()
						- tradeWheat);
				senderResources.setWood(senderResources.getWood() - tradeWood);

				/*
				 * Adjust the resources of the receiver by adding the trade
				 * resources to the player's current resource list.
				 */
				ResourceList receiverResources = model.getPlayers()[receiverIndex]
						.getResources();
				receiverResources.setBrick(receiverResources.getBrick()
						+ tradeBrick);
				receiverResources.setOre(receiverResources.getOre() + tradeOre);
				receiverResources.setSheep(receiverResources.getSheep()
						+ tradeSheep);
				receiverResources.setWheat(receiverResources.getWheat()
						+ tradeWheat);
				receiverResources.setWood(receiverResources.getWood()
						+ tradeWood);

				/*
				 * Eliminate the trade offer from the model by setting the trade
				 * offer to null.
				 */
				model.setTradeOffer(null);
				
				/*
				 * Add this command to the game history
				 */
				String senderName = model.getPlayers()[senderIndex].getName();
				String receiverName = model.getPlayers()[receiverIndex].getName();
				model.getLog().addLine(new MessageLine(senderName + " traded with " + receiverName, senderName));
				/*
				 * Add this command to the list of commands currently stored
				 * inside the model.
				 */
				model.getCommands().add(this);
				model.incrementVersion();
			} else {
				throw new ServerResponseException(
						"Unable to accept trade offer, insufficient resources or invalid trade offer.");
			}
		}
	}
}
