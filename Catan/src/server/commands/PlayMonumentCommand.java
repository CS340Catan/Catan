package server.commands;

import shared.communication.PlayMonumentParams;

/**
 * @author Drewfus
 * This is the command class for the PlayMonument function called on the server.
 * It will receive a PlayMonumentParams object and a gameID in the constructor
 */

public class PlayMonumentCommand implements ICommand {
	
	PlayMonumentParams params;
	int gameID;
	
	PlayMonumentCommand(PlayMonumentParams params, int gameID) {
		this.params = params;
		this.gameID = gameID;
	}
	/**
	 * Gives the player the victory points, should set the game to say he won
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
