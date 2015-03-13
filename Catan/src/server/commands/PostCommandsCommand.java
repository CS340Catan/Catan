package server.commands;

import shared.communication.CommandList;

/**
 * @author Drewfus
 * This is the command class for the PostCommands function called on the server.
 * It will receive a CommandList object and a gameID in the constructor
 */

public class PostCommandsCommand implements ICommand {
	
	CommandList params;
	int gameID;
	
	PostCommandsCommand(CommandList params, int gameID) {
		this.params = params;
		this.gameID = gameID;
	}
	/**
	 * Server will return a client model in the state it would be in after the CommandList is executed
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}

