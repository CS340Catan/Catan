package server.commands;

import server.facade.ServerFacade;
import shared.communication.UserCredentials;
import shared.model.*;
import shared.utils.ServerResponseException;

/**
 * @author Drewfus
 * This is the command class for the Register function called on the server.
 * It will receive a UserCredentials object in the constructor
 */

public class RegisterCommand implements ICommand {
	
	String username;
	String password;
	
	public RegisterCommand(UserCredentials params) {
		this.username = params.getUsername();
		this.password = params.getPassword();
	}
	/**
	 * Checks to see if the requested registration is valid, adds it to the RegisteredPlayers class if it is
	 * @throws ServerResponseException 
	 */
	@Override
	public void execute() throws ServerResponseException {
		RegisteredPlayers registeredPlayers = RegisteredPlayers.getSingleton();
		if (registeredPlayers.containsKey(username)) {
			throw new ServerResponseException("Invalid Username or password");
		} 
		registeredPlayers.addNewPlayer(username, password);
	}

}

