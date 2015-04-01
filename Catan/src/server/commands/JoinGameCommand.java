package server.commands;

import java.util.ArrayList;

import server.facade.FacadeSwitch;
import server.model.GameList;
import server.model.RegisteredPlayers;
import server.model.ServerModel;
import shared.communication.*;
import shared.model.DevCardList;
import shared.model.Player;
import shared.model.ResourceList;
import shared.utils.ServerResponseException;

/**
 * Join Game Command adds a player to the associate Game in the Client Model
 * 
 * @author Seth White
 */
public class JoinGameCommand extends ICommand {
	private String color;
	private int playerID;

	public JoinGameCommand(JoinGameParams joinGameParams, int playerId) {
		this.color = joinGameParams.getColor();
		this.playerID = playerId;
		this.setType("JoinGame");

	}

	/**
	 * Adds a player to the given game in the game list
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {
		ServerModel model = FacadeSwitch.getSingleton().getServerModel();
		if(model==null){
			throw new ServerResponseException("Cannot join game - Invalid game id.");
		}
		String playerName = RegisteredPlayers.getSingleton().getPlayerName(playerID);
		Player[] players = model.getPlayers();
		int gameID = FacadeSwitch.getSingleton().getGameID();

		/*
		 * Iterate through each player currently saved within the model. This
		 * may either grab an empty slot or a slot already occupied by a player.
		 */
		int playerIsInTheGame_Index = -1;

		if (players == null) {
			players = new Player[1];
			players[0] = new Player(0, playerID, 4, 5, playerName, color, false, 0, new DevCardList(0, 0, 0, 0, 0), new DevCardList(0, 0, 0, 0, 0), false,
					new ResourceList(0, 0, 0, 0, 0), 15, 0, 0);
			model.setPlayers(players);
			FacadeSwitch.getSingleton().getModelMap().put(gameID, model);
			for (GameSummary game : GameList.getSingleton().getGames()) {
				if (game.getId() == gameID) {
					PlayerSummary playerSummary = new PlayerSummary(color, playerName, playerID);
					PlayerSummary[] playerSummaries = new PlayerSummary[4]; 
					playerSummaries[0] = playerSummary; 
					game.setPlayers(playerSummaries);
					break;
				}
			}
		} 
		else {
			if(players.length != 4){
				Player firstPlayer = players[0];
				players = new Player[4];
				players[0] = firstPlayer;
			}
			for (int i = 0; i < players.length; i++) {
				/*
				 * If player_i's name matches the inputer player, then set the
				 * temporary index value equal to i and continue the loop.
				 */
				if (players[i] != null && players[i].getName().equals(playerName)) {
					playerIsInTheGame_Index = i;
				}

				/*
				 * If player_i is null (empty slot) and inputed player does not
				 * already exist within the game, then fill the null/empty slot
				 * with the inputed player and return.
				 */
				else if (players[i] == null && playerIsInTheGame_Index == -1) {
					players[i] = new Player(i, playerID, 4, 5, playerName, color, false, 0, new DevCardList(0, 0, 0, 0, 0), new DevCardList(0, 0, 0, 0, 0),
							false, new ResourceList(0, 0, 0, 0, 0), 15, 0, 0);
					model.setPlayers(players);
					for (GameSummary game : GameList.getSingleton().getGames()) {
						if (game.getId() == gameID) {
							game.getPlayers()[i] = new PlayerSummary(color, playerName, playerID);
							break;
						}
					}
					model.incrementVersion(); // TODO Is this increment needed?
					return;
				}

				/*
				 * If player_i is not null (a valid player) and player_i's color
				 * matches 'color', then throw an exception stating that the
				 * inputed color is invalid.
				 */
				else if (players[i] != null && players[i].getColorString().equals(color)) {
					throw new ServerResponseException("A player with that color already exists.");
				}
			}

			/*
			 * If the player did exist within the game, then set the color of
			 * the player equal to the inputed color and return. Else, throw an
			 * exception stating that the game was full.
			 */
			if (playerIsInTheGame_Index != -1) {
				players[playerIsInTheGame_Index].setColor(color);
				for (GameSummary game : GameList.getSingleton().getGames()) {
					if (game.getId() == gameID) {
						game.getPlayers()[playerIsInTheGame_Index].setColor(color);
						break;
					}
				}
				return;
			}
		}
	}
}
