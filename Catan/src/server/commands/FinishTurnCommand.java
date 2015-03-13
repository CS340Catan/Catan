package server.commands;

/**
 * Finishes a players turn
 */
public class FinishTurnCommand implements ICommand {
	int playerId;
	public FinishTurnCommand(int playerId){
		this.playerId = playerId;
	}
	/**
	 * Moves the turn tracker forward and finishes the player's turn
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
