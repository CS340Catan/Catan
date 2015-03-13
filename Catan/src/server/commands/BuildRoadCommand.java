package server.commands;

import shared.communication.BuildRoadParams;

/**
 * @author Drewfus
 * This is the command class for the BuildRoad function called on the server.
 * It will receive a BuildRoadParams object and a gameID in the constructor
 */

public class BuildRoadCommand implements ICommand {
	
	BuildRoadParams params;
	int gameID;
	
	BuildRoadCommand(BuildRoadParams params, int gameID) {
		this.params = params;
		this.gameID = gameID;
	}
	/**
	 * Adds a road to the server model and updates map, player roads, player resources.
	 * Checks if longest road needs to be updated
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
