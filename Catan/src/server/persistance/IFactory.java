package server.persistance;

import server.DAO.IGameDAO;
import server.DAO.IUserDAO;

/**
 * Abstract Factory class for creating DAO objects
 * 
 * @author winstonhurst
 *
 */
public interface IFactory {
	/**
	 * Creates a an IGameDAO which can be used to access the information about
	 * games which have been saved to the disk
	 * 
	 * @return
	 */
	public IGameDAO createGameDAO();

	/**
	 * Creates a an IUserDAO which can be used to access the information about
	 * players which have been saved to the disk
	 * 
	 * @return
	 */
	public IUserDAO createUserDAO();
}
