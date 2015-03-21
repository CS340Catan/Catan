package server.commands;

/**
 * This is the command class for the ResetGame function called on the server. It
 * will receive a gameID in the constructor
 * 
 * @author Drewfus
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
		/*
		 * TODO This method will rely upon the currently stored commands of each
		 * individual game.
		 * 
		 * This will reset the game back to the original state after
		 * initialization. This will probably be done by erasing the server
		 * model and re-applying the first 16 commands, which represent the
		 * Initialization phases.
		 */
	}

}
