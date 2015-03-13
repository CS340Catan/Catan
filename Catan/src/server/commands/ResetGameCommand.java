package server.commands;

/**
 * @author Drewfus
 * This is the command class for the ResetGame function called on the server.
 * It will receive a gameID in the constructor
 */

public class ResetGameCommand implements ICommand {
	
	int gameID;
	
	ResetGameCommand(int gameID) {
		this.gameID = gameID;
	}
	/**
	 * Reverts the game back to how it was after initial setup
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}