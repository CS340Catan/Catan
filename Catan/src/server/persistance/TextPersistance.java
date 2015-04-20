package server.persistance;

import java.util.List;

import server.DAO.IGameDAO;
import server.DAO.IUserDAO;
import server.DAO.TextGameDAO;
import server.DAO.TextUserDAO;
import server.model.RegisteredPlayers;
import server.model.ServerModel;
import shared.communication.UserCredentials;

public class TextPersistance implements IPersistance {

	private TextFactory factory;
	/**
	 * Stores the TextGameDAO so that the TextPersistence can access the games
	 * on disk
	 */
	private TextGameDAO gameDAO;
	/**
	 * Stores the TextGameDAO so that the TextPersistence can access the users
	 * on disk
	 */
	private TextUserDAO userDAO;
	/**
	 * Contains the path to the folder where all save files will be kept.
	 */
	private String pathname;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getPlayers() {
		// TODO Auto-generated method stub

	}

	@Override
	public void getGames() {
		// TODO Auto-generated method stub

	}

	public TextFactory getFactory() {
		return factory;
	}

	public void setFactory(TextFactory factory) {
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

	@Override
	public void startTransaction() {
		// TODO Auto-generated method stub

	}

	@Override
	public void endTransaction() {
		// TODO Auto-generated method stub

	}

}
