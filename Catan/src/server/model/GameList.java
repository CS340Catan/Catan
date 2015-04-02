package server.model;

import java.util.ArrayList;

import shared.communication.GameSummary;

public class GameList {
	private ArrayList<GameSummary> gameList = new ArrayList<GameSummary>();
	private static GameList singleton = null;

	private GameList() {

	}

	public static GameList getSingleton() {
		if (singleton == null) {
			singleton = new GameList();
		}
		return singleton;
	}

	public void addGame(GameSummary summary) {
		gameList.add(summary);
	}

	public void removeGame(GameSummary summary) {
		gameList.remove(summary);
	}

	public ArrayList<GameSummary> getGames() {
		return gameList;
	}

	public GameSummary getGame(String name) {
		for (GameSummary summary : gameList) {
			if (name.equals(summary.getTitle())) {
				return summary;
			}
		}
		return null;
	}

	public GameSummary getGameByID(int id) {
		for (GameSummary summary : gameList) {
			if (id == (summary.getId())) {
				return summary;
			}
		}
		return null;
	}

}
