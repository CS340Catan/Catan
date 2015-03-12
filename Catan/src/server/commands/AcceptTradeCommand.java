package server.commands;

import server.model.ServerModel;

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
	ServerModel serverModel;

	/**
	 * This method will manipulate the serverModel stored within the command. If
	 * the trade is to be accepted, then the appropriate resources should be
	 * added or removed from the players listed.
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
