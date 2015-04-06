package server.DAO;

import java.util.List;

import shared.communication.UserCredentials;

public interface IUserDAO {
	/**
	 * Gets a list of users saved on hard disk.
	 * 
	 * @return List of registered users.
	 */
	public List<UserCredentials> getUsers();

	/**
	 * This function will create a new user within server's persistence files
	 * given the user credentials inputed.
	 * 
	 * @param newUser
	 *            User to be created.
	 * @return True if successful, false if failed.
	 */
	public boolean createUser(UserCredentials newUser);

	/**
	 * Erases all currently stored games on the hard disk.
	 * 
	 * @return True if successful, false if failed.
	 */
	public boolean clear();
}
