package shared.communication;

import java.util.List;


/**
 * Contains a list of commands
 * @author marcos
 *
 * Domain:
 * 	commands:List of Strings
 * Domain Restraints:
 * 	Strings within list must be valid commands
 */
public class CommandList {
	
	
	/**a list of game commands*/
	private List<String> commands;
	
	

	public CommandList(List<String> commands) {
		super();
		this.commands = commands;
	}

	public List<String> getCommands() {
		return commands;
	}

	public void setCommands(List<String> commands) {
		this.commands = commands;
	}
	
	
}
