package server.persistance;

import java.util.List;

import server.model.RegisteredPlayers;
import server.model.ServerModel;
import shared.communication.UserCredentials;

public interface IPersistance {
	/**
	 * This class will save all games and players to the hard disk.
	 * 
	 * @param gameList
	 *            List of games to be saved.
	 * @param players
	 *            List of players to be saved.
	 */
	public void saveGames(List<ServerModel> gameList, RegisteredPlayers players);

	/**
	 * Updates a specific game stored within server.
	 * 
	 * @pre Game already exists on hard disk.
	 * @param game
	 *            Game to be saved/updated.
	 */
	public void updateGame(ServerModel game);

	/**
	 * Adds a player to the server.
	 * 
	 * @pre Player does not already exist.
	 * @param newUser
	 *            New user to be added.
	 */
	public void addPlayer(UserCredentials newUser);

	/**
	 * Adds a game to the server.
	 * 
	 * @pre Game does not already exist.
	 * @param game
	 *            New game to be added.
	 */
	public void addGame(ServerModel game);

	/**
	 * Get a game from the hard disk given game id.
	 * 
	 * @param gameID
	 *            Game id to be retrieved.
	 */
	public ServerModel getGame(int gameID);

	/**
	 * Get all the players currently stored within the server.
	 */
	public void getPlayers();

	/**
	 * Get all the games currently stored within the server.
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
