package server.commands;

import shared.communication.UserActionParams;

/**
 * 
 * @author winstonhurst
 *This command finishes a player's turn
 */
public class FinishTurnCommand implements ICommand {
	UserActionParams params;
	int gameID;
	
	/**
	 * 
	 * @param params - contains the player's index in the game
	 * @param gameID - id of the game on which to act
	 */
	public FinishTurnCommand(UserActionParams params, int gameID){
		this.params = params;
		this.gameID = gameID;
	}
	
	/**
	 * Changes current player turn to next player's id.
	 * Changes the status of the game associated with the gameID to Rolling.
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
