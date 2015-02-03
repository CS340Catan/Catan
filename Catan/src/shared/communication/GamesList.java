package shared.communication;

import java.util.ArrayList;

/**
 * This class wraps around a list of games on the server, which can then be
 * serialized and sent across the network
 * 
 * @author winstonhurst
 */
public class GamesList {
	/**
	 * A list of games currently on the server
	 */
	ArrayList<GameSummary> games;

	/**
	 * @pre A list of valid GameSummary objects
	 * @post Creates a GamesList object which can be serialized and sent back to
	 *       the client
	 * @param games
	 */
	public GamesList(ArrayList<GameSummary> games) {
		this.games = games;
	}

	public ArrayList<GameSummary> getGames() {
		return games;
	}

	public void setGames(ArrayList<GameSummary> games) {
		this.games = games;
	}

}
