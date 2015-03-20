package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.BuildCityParams;
import shared.communication.VertexLocationParam;
import shared.definitions.ResourceType;
import shared.locations.VertexLocation;
import shared.model.Player;
import shared.model.VertexObject;
import shared.utils.ServerResponseException;

/**
 * 
 * @author winstonhurst
 *Command class for placing a city. Places a new city in the model for a player, and removes some  of that player's resources.
 *The validity of the location and player index, as well as other pre-conditions, have been checked earlier.
 */
public class BuildCityCommand implements ICommand {
	VertexLocationParam location;
	int playerIndex;
	int gameID;
	
	/**
	 * 
	 * @param params - Contains the id of the player (int) and the location of the city (VertxLocation) 
	 * @param gameID - id of game on which the command is to be executed	 
	 */
	public BuildCityCommand(BuildCityParams params, int gameID){
		this.location = params.getVertexLocation(); 
		this.playerIndex = params.getPlayerIndex();
		this.gameID = gameID;
	}

	/**
	 * Looks up the appropriate game by gameID
	 * Places a city for a player at a certain location within that game's model.
	 * Removes 2 wheat and 3 ore from the player's resource list
	 * Gives a point to the player who has placed the city.
	 * @throws ServerResponseException 
	 */
	@Override
	public void execute() throws ServerResponseException {
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController controller = new ServerModelController(model);
		
		VertexLocation settlementVertexLocation = new VertexLocation();
		settlementVertexLocation.setDirection(location.getDirection());
		settlementVertexLocation.setX(location.getX());
		settlementVertexLocation.setY(location.getY());
		settlementVertexLocation.convertFromPrimitives();
		
		VertexObject city = new VertexObject(playerIndex, settlementVertexLocation);
		
		if(controller.canBuildCity(city)) {	
			
			// add settlement to model
			model.addCity(city);
			
			// decrement player resources
			Player player = model.getPlayers()[playerIndex];
			model.addResourceFromBank(playerIndex, ResourceType.ORE, -3);
			model.addResourceFromBank(playerIndex, ResourceType.WHEAT, -2);
			//Take one city form their stock, give them  a settlement back
			player.setCities(player.getCities() - 1);
			player.setSettlements(player.getSettlements() + 1);
			//give a single victory point
			player.setVictoryPoints(player.getVictoryPoints() + 1);
			
		}
		
		else{
			throw new ServerResponseException("This player cannot build a road at this location");
		}
	
	}

}
