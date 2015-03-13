package server.commands;

import shared.communication.PlayMonopolyParams;

/**
 * @author Drewfus
 * This is the command class for the PlayMonopoly function called on the server.
 * It will receive a PlayMonopolyParams object and a gameID in the constructor
 */

public class PlayMonopolyCommand implements ICommand {
	
	PlayMonopolyParams params;
	int gameID;
	
	PlayMonopolyCommand(PlayMonopolyParams params, int gameID) {
		this.params = params;
		this.gameID = gameID;
	}
	/**
	 * Takes the desired resource card from all players, gives it to the player playing
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}

