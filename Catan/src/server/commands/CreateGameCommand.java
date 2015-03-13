package server.commands;

import shared.communication.CreateGameParams;

/**
 * Create a new Game
 * @author Seth White
 *
 */
public class CreateGameCommand implements ICommand {
	boolean randomHexes;
	boolean randomNumbers;
	boolean randomPorts;
	String title;
	public CreateGameCommand(CreateGameParams params){
		
	}
	/**
	 * Create a new Game
	 *
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
