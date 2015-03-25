package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.BuildRoadCardParams;
import shared.communication.EdgeLocationParam;
import shared.locations.EdgeLocation;
import shared.model.MessageLine;
import shared.model.Player;
import shared.model.Road;
import shared.utils.ServerResponseException;

/**
 * This is the command class for the PlayRoadBuilding function called on the
 * server. It will receive a BuildRoadCardParams object and a gameID in the
 * constructor
 * 
 * @author Drewfus
 */

public class PlayRoadBuildingCommand extends ICommand {

	int playerIndex;
	EdgeLocationParam location_1;
	EdgeLocationParam location_2;

	public PlayRoadBuildingCommand(BuildRoadCardParams params) {
		this.playerIndex = params.getPlayerIndex();
		this.location_1 = params.getSpot1();
		this.location_2 = params.getSpot2();
		this.setType("PlayRoadBuilding");
	}

	/**
	 * Places the two roads, updates the player's development cards
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {

		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController controller = new ServerModelController(model);

		if (controller.canPlayRoadBuildingCard(playerIndex)) {

			EdgeLocation roadEdgeLocation1 = new EdgeLocation();
			roadEdgeLocation1.setDirection(location_1.getDirection());
			roadEdgeLocation1.setX(location_1.getX());
			roadEdgeLocation1.setY(location_1.getY());
			roadEdgeLocation1.convertFromPrimitives();
			Road road1 = new Road(playerIndex, roadEdgeLocation1);

			EdgeLocation roadEdgeLocation2 = new EdgeLocation();
			roadEdgeLocation2.setDirection(location_2.getDirection());
			roadEdgeLocation2.setX(location_2.getX());
			roadEdgeLocation2.setY(location_2.getY());
			roadEdgeLocation2.convertFromPrimitives();
			Road road2 = new Road(playerIndex, roadEdgeLocation2);

			// add road to model
			model.addRoad(road1);
			model.addRoad(road2);

			// decrement player roads, and remove card
			Player player = model.getPlayers()[playerIndex];
			player.setRoads(player.getRoads() - 2);
			player.getOldDevCards().setRoadBuilding(
					player.getOldDevCards().getRoadBuilding() - 1);

			/*
			 * Re-allocate longest road, and victory points
			 */
			model.reallocateLongestRoad();

			/*
			 * Set the player's has played development card boolean equal to
			 * true.
			 */
			player.setPlayedDevCard(true);

			/*
			 * Update game history
			 */
			String name = model.getPlayers()[playerIndex].getName();
			model.getLog().addLine(
					new MessageLine(name + " played a road building card.",
							name));

			/*
			 * Add this command to the list of commands currently stored inside
			 * the model.
			 */
			model.getCommands().add(this);
			model.incrementVersion();
		} else {
			throw new ServerResponseException(
					"Unable to play road building card");
		}

	}

}
