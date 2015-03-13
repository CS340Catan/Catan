package server.commands;

import shared.communication.MoveSoldierParams;

/**
 * @author Drewfus
 * This is the command class for the PlaySoldier function called on the server.
 * It will receive a MoveSoldierParams object and a gameID in the constructor
 */

public class PlaySoldierCommand implements ICommand {
	
	MoveSoldierParams params;
	int gameID;
	
	PlaySoldierCommand(MoveSoldierParams params, int gameID) {
		this.params = params;
		this.gameID = gameID;
	}
	/**
	 * Moves the robber to the new location, steals a card from the indicated player, updates the robbing player's 
	 * development cards
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
