package server.commands;

import java.util.Random;

import shared.communication.CreateGameParams;
import shared.communication.GameSummary;
import shared.model.GameList;
import shared.utils.IDGenerator;

/**
 * 
 * @author winstonhurst
 *This command creates a new game and adds it to the list of games.
 */
public class CreateGameCommand implements ICommand {
	CreateGameParams params;
    Random random = new Random();	
	
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
		int gameID = IDGenerator.generateGameID();
		GameSummary gameSummary = new GameSummary(params.getname(), gameID, null);
	}

	

}
