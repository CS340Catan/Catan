package server.commands;

import shared.communication.BuildSettlementParams;


/**
 * 
 * @author winstonhurst
 *Command class for placing a settlement. Places a settlement and updates the player's resource list.
 * The validity of pre-conditions has been checked earlier.
 */
public class BuildSettlmentCommand implements ICommand {
	BuildSettlementParams parameter;
	int gameID;
	
	/**
	 * 
	 * @param params - Contains the id of the player (int) and the location of the settlement(VertxLocation) 
	 * @param gameID - id of game on which the command is to be executed
	 */
	public BuildSettlmentCommand(BuildSettlementParams params, int gameID){
		this.parameter = params;
		this.gameID = gameID;
	}
	
	/**
	 * Gets the appropriate game by id.
	 * Places a settlement on the location for the player whose index has been passed.
	 * Player looses 1 wood, 1 brick, 1 wheat, 1 sheep from his or her hand.
	 * Gives the player who placed the settlement a point.
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
