package server.commands;

/**
 * Returns a list of commands applied to the initial clientModel
 * 
 * @author Seth White
 *
 */
public class GetCommandsCommand extends ICommand {

	public GetCommandsCommand(int gameId) {
		this.setType("GetCommands");
	}

	/**
	 * Returns the commands applied to the initial clientModel
	 */
	@Override
	public void execute() {
		// TODO Need to implement GetCommands command

	}

}
