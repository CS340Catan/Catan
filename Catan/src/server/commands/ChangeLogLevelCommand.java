package server.commands;

/**
 * Change the log level of the server
 * @author Seth White
 *
 */
public class ChangeLogLevelCommand implements ICommand {
	String LogLevel;
	public ChangeLogLevelCommand(String level){
		this.LogLevel = level;
	}
	/**
	 * Change the log level of the server
	 * @author Seth White
	 *
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
