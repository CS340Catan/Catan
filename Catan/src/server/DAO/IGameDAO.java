package server.DAO;

import java.util.List;

import server.commands.ICommand;
import server.model.ServerModel;

public interface IGameDAO {
	/**
	 * Gets all games stored on the hard disk for the server.
	 * 
	 * @return List of server models that were stored on the hard disk.
	 */
	public List<ServerModel> getGames();

	/**
	 * This function will create a new game within server's persistence files
	 * given the server model input parameter.
	 * 
	 * @pre GameID of newGame does not already exist within the server.
	 * @param newGame
	 *            Game to be created within the server persistence files.
	 * @return True if successfully created a new game, false if failed to
	 *         create valid game.
	 */
	public boolean createGame(ServerModel newGame);

	/**
	 * Get a specific game from the files currently stored on the hard disk.
	 * 
	 * @pre Valid gameID (game exists within stored files).
	 * @param gameID
	 *            Game to be "loaded"
	 * @return Game from the hard disk. Returns null if no game stored matches
	 *         inputed gameID.
	 */
	public ServerModel getGame(int gameID);

	/**
	 * Saves the game given a gameID to the hard disk.
	 * 
	 * @pre Game matching gameID already exists within the hard disk.
	 * @param gameID
	 *            Game ID to be saved
	 * @param game
	 *            Game to be saved
	 * @return True if successful, false if failed.
	 */
	public boolean saveGame(int gameID, ServerModel game);

	/**
	 * Will query games currently stored on hard disk for specific data.
	 * 
	 * @return List of Server Models that match query parameters.
	 */
	public List<ServerModel> queryGame();

	/**
	 * Erases all currently stored games on the hard disk.
	 * 
	 * @return True if successful, false if failed.
	 */
	public boolean clear();
}
