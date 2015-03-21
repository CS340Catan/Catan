package server.commands;

/**
 * Returns the client model
 * 
 * @author Seth White
 *
 */
public class GetModelCommand implements ICommand {
	private int version;

	/**
	 * returns the model based on a game id
	 */
	public GetModelCommand(int version) {
		this.version = version;
	}

	@Override
	public void execute() {

	}

}
