package server.commands;

import shared.communication.MaritimeTradeParams;

/**
 * @author Drewfus
 * This is the command class for the MaritimeTrade function called on the server.
 * It will receive a MaritimeTradeParams object and a gameID in the constructor
 */

public class MaritimeTradeCommand implements ICommand {
	
	MaritimeTradeParams params;
	int gameID;
	
	MaritimeTradeCommand(MaritimeTradeParams params, int gameID) {
		this.params = params;
		this.gameID = gameID;
	}
	/**
	 * Trades with the bank with the desired resources
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}

