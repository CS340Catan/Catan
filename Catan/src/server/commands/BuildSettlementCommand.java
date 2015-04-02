package server.commands;

import server.facade.FacadeSwitch;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.BuildSettlementParams;
import shared.communication.VertexLocationParam;
import shared.definitions.ResourceType;
import shared.locations.VertexLocation;
import shared.model.*;
import shared.utils.ServerResponseException;

/**
 * Command class for placing a settlement. Places a settlement and updates the
 * player's resource list. The validity of pre-conditions has been checked
 * earlier.
 * 
 * @author winstonhurst
 */
public class BuildSettlementCommand extends ICommand {

	VertexLocationParam settlementLocation;
	int playerIndex;
	boolean free;

	/**
	 * 
	 * @param params
	 *            - Contains the id of the player (int) and the location of the
	 *            settlement(VertxLocation)
	 * @param gameID
	 *            - id of game on which the command is to be executed
	 */
	public BuildSettlementCommand(BuildSettlementParams params) {

		this.settlementLocation = params.getVertexLocation();
		this.playerIndex = params.getPlayerIndex();
		this.free = params.isFree();
		this.setType("BuildSettlement");

	}

	/**
	 * Gets the appropriate game by id. Places a settlement on the location for
	 * the player whose index has been passed. Player looses 1 wood, 1 brick, 1
	 * wheat, 1 sheep from his or her hand. Gives the player who placed the
	 * settlement a point.
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {

		ServerModel model = FacadeSwitch.getSingleton().getServerModel();
		ServerModelController controller = new ServerModelController(model);

		/*
		 * Create a settlement using the stored location and player index
		 * currently stored within the command class members.
		 */
		VertexLocation settlementVertexLocation = new VertexLocation();
		settlementVertexLocation
				.setDirection(settlementLocation.getDirection());
		settlementVertexLocation.setX(settlementLocation.getX());
		settlementVertexLocation.setY(settlementLocation.getY());
		settlementVertexLocation.convertFromPrimitives();
		VertexObject settlement = new VertexObject(playerIndex,
				settlementVertexLocation);

		if (controller.canBuildSettlement(settlement, free, free)) {
			/*
			 * If a settlement can be built, add the settlement to the model.
			 */
			model.addSettlement(settlement);

			/*
			 * Decrement from the player's resources the appropriate amount of
			 * resources. In addition, add the appropriate amount of resources
			 * to the bank.
			 */

			Player player = model.getPlayers()[playerIndex];
			if (!free) {
				model.addResourceFromBank(playerIndex, ResourceType.BRICK, -1);
				model.addResourceFromBank(playerIndex, ResourceType.WOOD, -1);
				model.addResourceFromBank(playerIndex, ResourceType.WHEAT, -1);
				model.addResourceFromBank(playerIndex, ResourceType.SHEEP, -1);
			}

			/*
			 * Remove one settlement piece from the player's hand and add an
			 * additional victory point for the settlement.
			 */
			player.setSettlements(player.getSettlements() - 1);
			player.setVictoryPoints(player.getVictoryPoints() + 1);

			/*
			 * Update game history
			 */
			String name = model.getPlayers()[playerIndex].getName();
			model.getLog().addLine(
					new MessageLine(name + " built a settlement.", name));
			if (model.getTurnTracker().getStatus().toLowerCase()
					.equals("secondround")) {
				for (ResourceType resourcetype : controller
						.getAdjacentResources(settlement)) {
					model.addResourceFromBank(playerIndex, resourcetype, 1);
				}
			}
			/*
			 * Add this command to the list of commands currently stored inside
			 * the model.
			 */
			model.getCommands().add(this);
			model.incrementVersion();

		} else {
			throw new ServerResponseException("Unable to build settlement.");
		}
	}
}
