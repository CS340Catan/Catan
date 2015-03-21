package server.commands;

import server.facade.ServerFacade;
import server.model.RegisteredPlayers;
import server.model.ServerModel;
import shared.communication.JoinGameParams;
import shared.model.DevCardList;
import shared.model.Player;
import shared.model.ResourceList;
import shared.utils.ServerResponseException;

/**
 * @author Seth White
 *Join Game Command adds a player to the associate Game in the Client Model
 */
public class JoinGameCommand implements ICommand {
	private String color;
	private int playerID;
	
	
	public JoinGameCommand(JoinGameParams joinGameParams, int playerId){
		this.color = joinGameParams.getColor();
		this.playerID = playerId;
	}
	
	/**
	 * Adds a player to the given game in the game list
	 * @throws ServerResponseException 
	 */
	@Override
	public void execute() throws ServerResponseException {
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		String playerName = RegisteredPlayers.getSingleton().getPlayerName(playerID);
		Player [] players = model.getPlayers();
		if(!model.hasFourPlayers()){
			for(int i = 0; i<4; i++){
				if(players[i]==null){
					players[i] = new Player(i,playerID,0,0,playerName,color,false,0,new DevCardList(0,0,0,0,0),new DevCardList(0,0,0,0,0),
								false,new ResourceList(0,0,0,0,0), 0,0,0);
					break;
				}
				else if(players[i].getName().equals(playerName)){
					//rejoining game, just send back the model
					break;
				}
				else if(players[i].getColorString().equals(color)){
					throw new ServerResponseException("Player with that color already exists");
				}
			}
		}
		else{
			throw new ServerResponseException("The Game is full");
		}
	}

}
