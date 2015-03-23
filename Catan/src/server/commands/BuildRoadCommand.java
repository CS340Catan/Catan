package server.commands;

import server.facade.ServerFacade;
import server.model.*;
import shared.communication.*;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.model.*;
import shared.utils.ServerResponseException;

/**
 * This is the command class for the BuildRoad function called on the server. It
 * will receive a BuildRoadParams object and a gameID in the constructor
 * 
 * @author Drewfus
 */

public class BuildRoadCommand extends ICommand {

	int playerIndex;
	EdgeLocationParam roadLocation;
	boolean free;

	public BuildRoadCommand(BuildRoadParams params) {

		this.roadLocation = params.getRoadLocation();
		this.playerIndex = params.getPlayerIndex();
		this.free = params.isFree();
		this.setType("BuildRoad");
	}

	/**
	 * Adds a road to the server model and updates map, player roads, player
	 * resources. Checks if longest road needs to be updated
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {

		System.out.println("BuildRoadCommand called");
		
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController controller = new ServerModelController(model);

		/*
		 * Create a road using the stored location and player index currently
		 * stored within the command class members.
		 */
		EdgeLocation roadEdgeLocation = new EdgeLocation();
		roadEdgeLocation.setDirection(roadLocation.getDirection());
		roadEdgeLocation.setX(roadLocation.getX());
		roadEdgeLocation.setY(roadLocation.getY());
		roadEdgeLocation.convertFromPrimitives();
		Road road = new Road(playerIndex, roadEdgeLocation);

		if (controller.canBuildRoad(playerIndex, road, free, free)) {

			/*
			 * If a road can be built, add the road to the model.
			 */
			model.addRoad(road);

			/*
			 * Decrement from the player's resources the appropriate amount of
			 * resources. In addition, add the appropriate amount of resources
			 * to the bank. Remove one road piece from the player's hand.
			 */
			Player player = model.getPlayers()[playerIndex];
			model.addResourceFromBank(playerIndex, ResourceType.BRICK, -1);
			model.addResourceFromBank(playerIndex, ResourceType.WOOD, -1);
			player.setRoads(player.getRoads() - 1);

			/*
			 * Re-compute the longest road and re-allocate corresponding victory
			 * points.
			 */
			model.reallocateLongestRoad();

			/*
			 * Add this command to the list of commands currently stored inside
			 * the model.
			 */
			model.getCommands().add(this);
			model.incrementVersion();

		} else {
			throw new ServerResponseException("Unable to build road.");
		}
	}
}
