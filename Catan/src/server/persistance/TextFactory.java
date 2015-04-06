package server.persistance;

import server.DAO.TextGameDAO;
import server.DAO.TextUserDAO;

public class TextFactory implements IFactory{

	private TextGameDAO gameDAO;
	private TextUserDAO userDAO;
	
	@Override
	public IGameDAO createGameDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IUserDAO createUserDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	public TextGameDAO getGameDAO() {
		return gameDAO;
	}

	public void setGameDAO(TextGameDAO gameDAO) {
		this.gameDAO = gameDAO;
	}

	public TextUserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(TextUserDAO userDAO) {
		this.userDAO = userDAO;
	}

}
