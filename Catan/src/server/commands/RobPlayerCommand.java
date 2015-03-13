package server.commands;

import shared.communication.MoveRobberParams;

/**
 * @author Drewfus
 * This is the command class for the RobPlayer function called on the server.
 * It will receive a MoveRobberParams object and a gameID in the constructor
 */

public class RobPlayerCommand implements ICommand {
	
	MoveRobberParams params;
	int gameID;
	
	RobPlayerCommand(MoveRobberParams params, int gameID) {
		this.params = params;
		this.gameID = gameID;
	}
	/**
	 * Moves the robber to the new location, steals a card from the indicated player.
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
