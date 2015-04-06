package server.DAO;

import java.util.List;

import shared.communication.UserCredentials;

public interface IUserDAO {
	public List<UserCredentials> getUsers();
	
	public void createUser(UserCredentials newUser);
	
	public void clear();
}
