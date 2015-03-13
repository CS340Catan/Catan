package server.commands;

import shared.communication.ChangeLogLevelParams;
/**
 * 
 * @author winstonhurst
 *This command changes the level of logging on the server to one of the following:
 *SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST
 */
public class ChangeLogLevelCommand implements ICommand {
	ChangeLogLevelParams params;
	
	/**
	 * 
	 * @param params - contains new log setting level (string)
	 */
	public ChangeLogLevelCommand(ChangeLogLevelParams params){
		this.params = params;
	}
	
	/**
	 * Changes the state of the server log to the indicated.
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}

