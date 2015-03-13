package server.commands;

import shared.communication.JoinGameParams;

/**
 * @author Seth White
 *Join Game Command adds a player to the associate Game in the Client Model
 */
public class JoinGameCommand implements ICommand {
	private String color;
	private int gameId;
	
	
	public JoinGameCommand(JoinGameParams joinGameParams){
		this.color = joinGameParams.getColor();
		this.gameId = joinGameParams.getId();
	}
	
	/**
	 * Adds a player to the given game in the game list
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
