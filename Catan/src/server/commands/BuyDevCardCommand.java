
package server.commands;

import shared.communication.UserActionParams;

/**
 * 
 * @author winstonhurst
 *Gives a player a development card. The development card is added to the player hand, and the appropriate resources are removed.
 *All pre-conditions have been met earlier.
 */
public class BuyDevCardCommand implements ICommand {
	UserActionParams params;
	int gameID;
	
	/**
	 * 
	 * @param params - contains the index (int) of the player buying the development card
	 * @param gameID - id of game on which the command is to be executed
	 */
	public BuyDevCardCommand(UserActionParams params, int gameID){
		this.params = params;
		this.gameID = gameID;
	}
	
	/**
	 * Gets the appropriate game by id
	 * adds a development card to the player's hand.
	 * If the development card is monopoly, it is added to the old cards list
	 * If the development card is NOT monopoly, it is added to the new cards list.
	 * Removes 1 ore, 1 wheat, 1 sheep from the player's resources.
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}

