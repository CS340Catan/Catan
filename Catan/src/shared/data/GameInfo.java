package shared.data;

import java.util.*;

/**
 * Used to pass game information into views<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique game ID</li>
 * <li>Title: Game title (non-empty string)</li>
 * <li>Players: List of players who have joined the game (can be empty)</li>
 * </ul>
 * 
 */
public class GameInfo {
	private int id;
	private String title;
	private List<PlayerInfo> players;

	public GameInfo() {
		setId(-1);
		setTitle("");
		players = new ArrayList<PlayerInfo>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void addPlayer(PlayerInfo newPlayer) {
		players.add(newPlayer);
	}

	public List<PlayerInfo> getPlayers() {
		return Collections.unmodifiableList(players);
	}

	public boolean equals(GameInfo gI) {
		if (this.id != gI.id)
			return false;
		if (!this.title.equals(gI.getTitle()))
			return false;
		for (int i = 0; i < gI.getPlayers().size(); i++) {
			if (!this.players.get(i).equals(gI.getPlayers().get(i)))
				return false;
		}

		return true;
	}
}
