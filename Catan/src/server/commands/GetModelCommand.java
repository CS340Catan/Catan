package server.commands;
/**
 * Returns the client model 
 * @author Seth White
 *
 */
public class GetModelCommand implements ICommand {
	private int gameId;
	
	/**
	 * returns the model based on a game id
	 */
	GetModelCommand(int gameId){
		this.gameId = gameId;
	}
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
