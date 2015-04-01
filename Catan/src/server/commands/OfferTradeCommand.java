package server.commands;

import server.facade.FacadeSwitch;
import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.TradeOfferParams;
import shared.model.ResourceList;
import shared.model.TradeOffer;
import shared.utils.ServerResponseException;

/**
 * This is the command class for the OfferTrade function called on the server.
 * It will receive a TradeOfferParams object and a gameID in the constructor
 * 
 * @author Drewfus
 */

public class OfferTradeCommand extends ICommand {

	int playerIndex;
	ResourceList offer;
	int receiver;

	public OfferTradeCommand(TradeOfferParams params) {
		this.playerIndex = params.getPlayerIndex();
		this.offer = params.getOffer();
		this.receiver = params.getReceiver();
		this.setType("OfferTrade");
	}

	/**
	 * Updates the model to contain the trade offer
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {

		ServerModel model = FacadeSwitch.getSingleton().getServerModel();
		ServerModelController controller = new ServerModelController(model);

		if (controller.canOfferTrade(playerIndex, offer)) {
			/*
			 * If the trade can be offered, store within the model the trade
			 * offer such that the receiver can see the new trade offer and can
			 * respond appropriately.
			 */
			model.setTradeOffer(null);
			model.setTradeOffer(new TradeOffer(playerIndex, receiver, offer));

			/*
			 * Add this command to the list of commands currently stored inside
			 * the model.
			 */
			model.getCommands().add(this);
			model.incrementVersion();

		} else {
			throw new ServerResponseException("Player " + playerIndex
					+ " cannot offer this trade");
		}

	}

}
