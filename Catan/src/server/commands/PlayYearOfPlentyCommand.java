package server.commands;

import shared.communication.YearOfPlentyParams;

/**
 * @author Drewfus
 * This is the command class for the PlayYearOfPlenty function called on the server.
 * It will receive a YearOfPlentyParams object and a gameID in the constructor
 */

public class PlayYearOfPlentyCommand implements ICommand {
	
	YearOfPlentyParams params;
	int gameID;
	
	PlayYearOfPlentyCommand(YearOfPlentyParams params, int gameID) {
		this.params = params;
		this.gameID = gameID;
	}
	/**
	 * Gives the indicated player his resources of choice
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}

