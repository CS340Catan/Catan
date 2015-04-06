package server.persistance;

import java.util.List;

import server.DAO.IGameDAO;
import server.DAO.IUserDAO;
import server.model.RegisteredPlayers;
import server.model.ServerModel;
import shared.communication.UserCredentials;

public class SQLPersistance implements IPersistance {

	private SQLFactory factory;
	String DBConnection;
	
	@Override
	public void saveGames(List<ServerModel> gameList, RegisteredPlayers players) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateGame(ServerModel game) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addPlayer(UserCredentials newUser) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addGame(ServerModel game) {
		// TODO Auto-generated method stub

	}

	@Override
	public ServerModel getGame(int gameID) {
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public void getPlayers() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getGames() {
		// TODO Auto-generated method stub

	}

	public SQLFactory getFactory() {
		return factory;
	}

	public void setFactory(SQLFactory factory) {
		this.factory = factory;
	}

	@Override
	public void clearGames() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearUsers() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IUserDAO getUserDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IGameDAO getGameDAO() {
		// TODO Auto-generated method stub
		return null;
	}

}
