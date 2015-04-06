package server.persistance;

import server.DAO.IGameDAO;
import server.DAO.IUserDAO;
import server.DAO.SQLGameDAO;
import server.DAO.SQLUserDAO;

public class SQLFactory implements IFactory{

	private SQLGameDAO gameDAO;
	private SQLUserDAO userDAO;
	
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

	public SQLGameDAO getGameDAO() {
		return gameDAO;
	}

	public void setGameDAO(SQLGameDAO gameDAO) {
		this.gameDAO = gameDAO;
	}

	public SQLUserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(SQLUserDAO userDAO) {
		this.userDAO = userDAO;
	}

}
