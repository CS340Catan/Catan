package server.commands;

import shared.communication.UserActionParams;

/**
 * Builds a dev card for a given player
 * @author Seth White
 *
 */
public class BuyDevCardCommand implements ICommand {
	String type;
	int playerIndex;
	public BuyDevCardCommand(UserActionParams params){
		playerIndex = params.getPlayerIndex();
		 type = params.getType();
	}
	/**
	 * Builds a dev card for a given player
	 * @author Seth White
	 *
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
