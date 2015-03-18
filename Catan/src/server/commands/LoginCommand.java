package server.commands;

import server.facade.ServerFacade;
import shared.communication.UserCredentials;
import shared.model.*;
import shared.utils.ServerResponseException;

/**
 * Logs a player in
 * @author Seth White
 *
 */
public class LoginCommand implements ICommand {
	private String username;
	private String password;
	
	
	public LoginCommand(UserCredentials credentials){
		this.username = credentials.getUsername();
		this.password = credentials.getPassword();
	}
	/**
	 * Returns whether or not a user has valid credentials
	 * @throws ServerResponseException 
	 */
	@Override
	public void execute() throws ServerResponseException {
		ServerFacade serverFacade = ServerFacade.getSingleton();
		RegisteredPlayers registeredPlayers = RegisteredPlayers.getSingleton();
		if(!registeredPlayers.userExists(username, password)){
			throw new ServerResponseException("Invalid Username or password");
		}

	}

}
