package server.commands;

import shared.communication.SaveParams;

/**
 * @author Drewfus
 * This is the command class for the SaveGame function called on the server.
 * It will receive a SaveParams object and a gameID in the constructor
 */

public class SaveGameCommand implements ICommand {
	
	SaveParams params;
	int gameID;
	
	SaveGameCommand(SaveParams params, int gameID) {
		this.params = params;
		this.gameID = gameID;
	}
	/**
	 * Saves state of the game to a file, which can be used later for debugging purposes
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}

