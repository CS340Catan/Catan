package server.persistance;

import java.util.List;

import server.model.RegisteredPlayers;
import server.model.ServerModel;
import shared.communication.UserCredentials;

public interface IPersistance {
	/**
	 * @param gameList
	 * @param players
	 */
	public void saveGames(List<ServerModel> gameList, RegisteredPlayers players);

	/**
	 * @param game
	 */
	public void updateGame(ServerModel game);

	/**
	 * @param newUser
	 */
	public void addPlayer(UserCredentials newUser);

	/**
	 * @param game
	 */
	public void addGame(ServerModel game);

	/**
	 * @param gameID
	 */
	public ServerModel getGame(int gameID);

	/**
	 * 
	 */
	public void getPlayers();

	/**
	 * 
	 */
	public void getGames();

	/**
	 * 
	 */
	public void clearGames();

	/**
	 * 
	 */
	public void clearUsers();
}
