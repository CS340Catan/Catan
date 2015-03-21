package server.commands;

import server.facade.ServerFacade;
import server.model.ServerModel;
import server.model.ServerModelController;
import shared.communication.BuildSettlementParams;
import shared.communication.VertexLocationParam;
import shared.definitions.ResourceType;
import shared.locations.VertexLocation;
import shared.model.*;
import shared.utils.ServerResponseException;


/**
 * 
 * @author winstonhurst
 *Command class for placing a settlement. Places a settlement and updates the player's resource list.
 * The validity of pre-conditions has been checked earlier.
 */
public class BuildSettlementCommand implements ICommand {
	
	VertexLocationParam settlementLocation;
	int playerIndex;
	boolean free;
	int gameID;
	
	/**
	 * 
	 * @param params - Contains the id of the player (int) and the location of the settlement(VertxLocation) 
	 * @param gameID - id of game on which the command is to be executed
	 */
	public BuildSettlementCommand(BuildSettlementParams params, int gameID){
		
		this.gameID = gameID;
		this.settlementLocation = params.getVertexLocation(); 
		this.playerIndex = params.getPlayerIndex();
		this.free = params.isFree();

	}
	
	/**
	 * Gets the appropriate game by id.
	 * Places a settlement on the location for the player whose index has been passed.
	 * Player looses 1 wood, 1 brick, 1 wheat, 1 sheep from his or her hand.
	 * Gives the player who placed the settlement a point.
	 * @throws ServerResponseException 
	 */
	@Override
	public void execute() throws ServerResponseException {

		ServerModel model = ServerFacade.getSingleton().getServerModel();
		ServerModelController controller = new ServerModelController(model);
		
		VertexLocation settlementVertexLocation = new VertexLocation();
		settlementVertexLocation.setDirection(settlementLocation.getDirection());
		settlementVertexLocation.setX(settlementLocation.getX());
		settlementVertexLocation.setY(settlementLocation.getY());
		settlementVertexLocation.convertFromPrimitives();
		VertexObject settlement = new VertexObject(playerIndex, settlementVertexLocation);
		
		if(controller.canBuildSettlement(settlement, free, free)) {	//just used free for setupPhase param, should be the same right?
			
			// add settlement to model
			model.addSettlement(settlement);
			
			// decrement player resources and settlements, update victory points
			Player player = model.getPlayers()[playerIndex];
			model.addResourceFromBank(playerIndex, ResourceType.BRICK, -1);
			model.addResourceFromBank(playerIndex, ResourceType.WOOD, -1);
			player.setSettlements(player.getSettlements() - 1);
			player.setVictoryPoints(player.getVictoryPoints() + 1);
			
			/*
			 * Add this command to the list of commands currently stored inside
			 * the model.
			 */
			model.getCommands().add(this);
			model.incrementVersion();
			
		}
		else {
			throw new ServerResponseException("Unable to build settlement");
		}

	}

}

