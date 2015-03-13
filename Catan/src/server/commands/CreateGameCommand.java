package server.commands;

import shared.communication.CreateGameParams;

/**
 * 
 * @author winstonhurst
 *This command creates a new game and adds it to the list of games.
 */
public class CreateGameCommand implements ICommand {
	CreateGameParams params;
	
	/**
	 * 
	 * @param params - contains randomTiles (bool) randomNumbers (bool), and the game's name 
	 */
	public CreateGameCommand(CreateGameParams params){
		this.params = params;
	}
	
	/**
	 * Generates a new game id
	 * Adds new game to the game list.
	 * Adds a new key => pair to the game map (id=>this new game)
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
