package shared.communication;

import client.data.GameInfo;
import client.data.PlayerInfo;

/**
 * This class is for sending a list of games currently on the server per the
 * '/games/list' request This class is for send information for a newly created
 * game per the '/games/create' method If this is being returned after
 * requesting '/games/create', all four PlayerSummary objects should be empty
 * Domain: title:String id:Integer players:List of PlayerSummaries
 * 
 * Domain Restraints: title - not null and not the empty string id - a valid
 * game id players - an array of four valid PlayerSummary obejcts
 * 
 * @author winstonhurst
 */
public class GameSummary {

	String title;
	int id;
	PlayerSummary players[] = new PlayerSummary[4];

	public GameSummary(String title, int id, PlayerSummary[] players) {
		this.title = title;
		this.id = id;
		this.players = players;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PlayerSummary[] getPlayers() {
		return players;
	}

	public void setPlayers(PlayerSummary[] players) {
		this.players = players;
	}

	public GameInfo toGameInfo() {
		GameInfo gameInfo = new GameInfo();

		gameInfo.setId(this.id);
		gameInfo.setTitle(this.title);

		for (PlayerSummary player : players) {
			if (player.getName() != "") {
				gameInfo.addPlayer(player.toPlayerInfo());
			}
			// System.out.println(player.toPlayerInfo().getName());
		}

		return gameInfo;
	}
}
