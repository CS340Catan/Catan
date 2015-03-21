package server.commands;

import shared.communication.BuildRoadCardParams;
import shared.communication.EdgeLocationParam;

/**
 * @author Drewfus This is the command class for the PlayRoadBuilding function
 *         called on the server. It will receive a BuildRoadCardParams object
 *         and a gameID in the constructor
 */

public class PlayRoadBuildingCommand implements ICommand {

	int playerIndex;
	EdgeLocationParam location_1;
	EdgeLocationParam location_2;

	public PlayRoadBuildingCommand(BuildRoadCardParams params) {
		this.playerIndex = params.getPlayerIndex();
		this.location_1 = params.getSpot1();
		this.location_2 = params.getSpot2();
	}

	/**
	 * Places the two roads, updates the player's development cards
	 */
	@Override
	public void execute() {
		
		/*
		 * Add this command to the list of commands currently stored inside
		 * the model.
		 */
		//model.getCommands().add(this);
		//model.incrementVersion();

	}

}
