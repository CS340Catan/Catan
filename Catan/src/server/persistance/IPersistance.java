package server.persistance;

import java.util.List;

import server.model.RegisteredPlayers;
import server.model.ServerModel;
import shared.communication.UserCredentials;

public interface IPersistance {
	public void saveGames(List<ServerModel> gameList, RegisteredPlayers players);
	
	public void updateGame(ServerModel game);
	
	public void addPlayer(UserCredentials newUser);
	
	public void addGame(ServerModel game);
	
	public void getGame(int gameID);
	
	public void getPlayers();
	
	public void getGames();
}
