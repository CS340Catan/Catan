package server.commands;

import server.facade.ServerFacade;
import server.model.*;
import shared.communication.*;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.model.*;

/**
 * @author Drewfus
 * This is the command class for the BuildRoad function called on the server.
 * It will receive a BuildRoadParams object and a gameID in the constructor
 */

public class BuildRoadCommand implements ICommand {

	int gameID;
	int playerIndex;
	EdgeLocationParam roadLocation;
	boolean free;

	
	public BuildRoadCommand(BuildRoadParams params, int gameID) {

		this.gameID = gameID;
		this.roadLocation = params.getRoadLocation();
		this.playerIndex = params.getPlayerIndex();
		this.free = params.isFree();
	}
	/**
	 * Adds a road to the server model and updates map, player roads, player resources.
	 * Checks if longest road needs to be updated
	 */
	@Override
	public void execute() {
		
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController controller = new ServerModelController(model);
		
		EdgeLocation roadEdgeLocation = new EdgeLocation();
		roadEdgeLocation.setDirection(roadLocation.getDirection());
		roadEdgeLocation.setX(roadLocation.getX());
		roadEdgeLocation.setY(roadLocation.getY());
		roadEdgeLocation.convertFromPrimitives();
		Road road = new Road(playerIndex, roadEdgeLocation);
		
		if(controller.canBuildRoad(playerIndex, road, free, free)) {	//just used free for setupPhase param, should be the same right?
			
			// add road to model
			model.addRoad(road);
			
			// decrement player resources and roads
			Player player = model.getPlayers()[playerIndex];
			model.addResourceFromBank(playerIndex, ResourceType.BRICK, -1);
			model.addResourceFromBank(playerIndex, ResourceType.WOOD, -1);
			player.setRoads(player.getRoads() - 1);

			
			// re-allocate longest road, and victory points
			model.reallocateLongestRoad();
			
		}
	}

}
