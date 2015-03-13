package server.commands;

import shared.communication.RollParams;

/**
 * @author Drewfus
 * This is the command class for the RollNumber function called on the server.
 * It will receive a RollParams object and a gameID in the constructor
 */

public class RollNumberCommand implements ICommand {
	
	RollParams params;
	int gameID;
	
	RollNumberCommand(RollParams params, int gameID) {
		this.params = params;
		this.gameID = gameID;
	}
	/**
	 * Updates each players' resources according to what number was rolled
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}