package server.commands;

import server.facade.ServerFacade;
import shared.communication.JoinGameParams;
import shared.model.ClientModel;
import shared.model.Player;

/**
 * @author Seth White
 *Join Game Command adds a player to the associate Game in the Client Model
 */
public class JoinGameCommand implements ICommand {
	private String color;
	private int gameId;
	private int playerID;
	
	
	public JoinGameCommand(JoinGameParams joinGameParams, int playerId){
		this.color = joinGameParams.getColor();
		this.gameId = joinGameParams.getId();
		this.playerID = playerId;
	}
	
	/**
	 * Adds a player to the given game in the game list
	 */
	@Override
	public void execute() {
		//access the map of id's to games.
		ClientModel model = ServerFacade.getSingleton().getClientModel();
		Player [] players = model.getPlayers();
		if(!model.hasFourPlayers()){
			for(int i = 0; i<4; i++){
				if(players[i]==null){
					players[i] = new Player(i,playerID,0,0);
				}
			}
		}
	

	}

}
