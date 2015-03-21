package server.commands;

import shared.communication.CommandList;

/**
 * This is the command class for the PostCommands function called on the server.
 * It will receive a CommandList object and a gameID in the constructor
 * 
 * @author Drewfus
 */

public class PostCommandsCommand extends ICommand {

	CommandList params;

	PostCommandsCommand(CommandList params) {
		this.params = params;
	}

	/**
	 * Server will return a client model in the state it would be in after the
	 * CommandList is executed
	 */
	@Override
	public void execute() {
		// TODO Implement the PostCommands command

	}

}
