package server.commands;

import shared.communication.BuildRoadCardParams;

/**
 * @author Drewfus
 * This is the command class for the PlayRoadBuilding function called on the server.
 * It will receive a BuildRoadCardParams object and a gameID in the constructor
 */

public class PlayRoadBuildingCommand implements ICommand {
	
	BuildRoadCardParams params;
	int gameID;
	
	PlayRoadBuildingCommand(BuildRoadCardParams params, int gameID) {
		this.params = params;
		this.gameID = gameID;
	}
	/**
	 * Places the two roads, updates the player's development cards
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
