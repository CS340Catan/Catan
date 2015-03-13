package server.commands;

import shared.communication.BuildCityParams;

/**
 * 
 * @author winstonhurst
 *Command class for placing a city. Places a new city in the model for a player, and removes some  of that player's resources.
 *The validity of the location and player index, as well as other pre-conditions, have been checked earlier.
 */
public class BuildCityCommand implements ICommand {
	BuildCityParams parameters;
	int gameID;
	
	/**
	 * 
	 * @param params - Contains the id of the player (int) and the location of the city (VertxLocation) 
	 * @param gameID - id of game on which the command is to be executed	 
	 */
	public BuildCityCommand(BuildCityParams params, int gameID){
		this.parameters = params;
		this.gameID = gameID;
	}

	/**
	 * Looks up the appropriate game by gameID
	 * Places a city for a player at a certain location within that game's model.
	 * Removes 2 wheat and 3 ore from the player's resource list
	 * Gives a point to the player who has placed the city.
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
