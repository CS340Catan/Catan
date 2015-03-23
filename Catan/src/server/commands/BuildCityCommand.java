package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.BuildCityParams;
import shared.communication.VertexLocationParam;
import shared.definitions.ResourceType;
import shared.locations.VertexLocation;
import shared.model.MessageLine;
import shared.model.Player;
import shared.model.VertexObject;
import shared.utils.ServerResponseException;

/**
 * Command class for placing a city. Places a new city in the model for a
 * player, and removes some of that player's resources. The validity of the
 * location and player index, as well as other pre-conditions, have been checked
 * earlier.
 * 
 * @author winstonhurst
 */
public class BuildCityCommand extends ICommand {
	VertexLocationParam location;
	int playerIndex;

	/**
	 * 
	 * @param params
	 *            - Contains the id of the player (int) and the location of the
	 *            city (VertxLocation)
	 * @param gameID
	 *            - id of game on which the command is to be executed
	 */
	public BuildCityCommand(BuildCityParams params) {
		this.location = params.getVertexLocation();
		this.playerIndex = params.getPlayerIndex();
		this.setType("BuildCity");
		
	}

	/**
	 * Looks up the appropriate game by gameID Places a city for a player at a
	 * certain location within that game's model. Removes 2 wheat and 3 ore from
	 * the player's resource list Gives a point to the player who has placed the
	 * city.
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController controller = new ServerModelController(model);

		/*
		 * Create a city using the stored location and player index currently
		 * stored within the command class members.
		 */
		VertexLocation settlementVertexLocation = new VertexLocation();
		settlementVertexLocation.setDirection(location.getDirection());
		settlementVertexLocation.setX(location.getX());
		settlementVertexLocation.setY(location.getY());
		settlementVertexLocation.convertFromPrimitives();
		VertexObject city = new VertexObject(playerIndex,
				settlementVertexLocation);

		if (controller.canBuildCity(city)) {
			/*
			 * If a city can be built, add the city to the model.
			 */
			model.addCity(city);	//settlement automatically removed


			/*
			 * Decrement from the player's resources the appropriate amount of
			 * resources. In addition, add the appropriate amount of resources
			 * to the bank.
			 */
			Player player = model.getPlayers()[playerIndex];
			model.addResourceFromBank(playerIndex, ResourceType.ORE, -3);
			model.addResourceFromBank(playerIndex, ResourceType.WHEAT, -2);

			/*
			 * Remove one city piece from the player's hand, while adding one
			 * settlement back to the user because the city replaced the
			 * position of a settlement piece.
			 */
			player.setCities(player.getCities() - 1);
			player.setSettlements(player.getSettlements() + 1);

			/*
			 * Add an additional victory point to the player for playing a city.
			 */
			player.setVictoryPoints(player.getVictoryPoints() + 1);
			
			/*
			 * Update game history
			 */
			String name = model.getPlayers()[playerIndex].getName();
			model.getLog().addLine(new MessageLine(name + " built a city", name));
			
			/*
			 * Add this command to the list of commands currently stored inside
			 * the model.
			 */
			model.getCommands().add(this);
			model.incrementVersion();

		} else {
			throw new ServerResponseException("Unable to build a city.");
		}
	}
}
