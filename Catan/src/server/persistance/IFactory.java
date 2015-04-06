package server.persistance;

import server.DAO.IGameDAO;
import server.DAO.IUserDAO;

public interface IFactory {
	public IGameDAO createGameDAO();
	
	public IUserDAO createUserDAO();
}
