package server.commands;

import server.model.RegisteredPlayers;
import shared.communication.UserCredentials;
import shared.utils.ServerResponseException;

/**
 * Logs a player in
 * 
 * @author Seth White
 *
 */
public class LoginCommand extends ICommand {
	private String username;
	private String password;

	public LoginCommand(UserCredentials credentials) {
		this.username = credentials.getUsername();
		this.password = credentials.getPassword();
		this.setType("Login");

	}

	/**
	 * Returns whether or not a user has valid credentials
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {
		RegisteredPlayers registeredPlayers = RegisteredPlayers.getSingleton();
		/*
		 * If the registered player inputed does not exist within the register
		 * players map, throw an exception stating invalid UserName and password
		 * combination.
		 */
		if (!registeredPlayers.userExists(username, password)) {
			throw new ServerResponseException("Invalid Username or password");
		}

	}

}
