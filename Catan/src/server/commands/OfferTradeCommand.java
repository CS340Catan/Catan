package server.commands;

import shared.communication.TradeOfferParams;

/**
 * @author Drewfus
 * This is the command class for the OfferTrade function called on the server.
 * It will receive a TradeOfferParams object and a gameID in the constructor
 */

public class OfferTradeCommand implements ICommand {
	
	TradeOfferParams params;
	int gameID;
	
	OfferTradeCommand(TradeOfferParams params, int gameID) {
		this.params = params;
		this.gameID = gameID;
	}
	/**
	 * Updates the model to contain the trade offer
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}

