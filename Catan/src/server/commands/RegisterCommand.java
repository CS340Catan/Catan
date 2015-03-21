package server.commands;

import java.util.logging.Logger;

import server.model.RegisteredPlayers;
import shared.communication.UserCredentials;
import shared.utils.ServerResponseException;

/**
 * This is the command class for the Register function called on the server. It
 * will receive a UserCredentials object in the constructor
 * 
 * @author Drewfus
 */

public class RegisterCommand implements ICommand {

	String username;
	String password;
	private static Logger logger;

	static {
		logger = Logger.getLogger("CatanServer");
	}

	public RegisterCommand(UserCredentials params) {
		this.username = params.getUsername();
		this.password = params.getPassword();
	}

	/**
	 * Checks to see if the requested registration is valid, adds it to the
	 * RegisteredPlayers class if it is
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {
		logger.info("server/commands/RegisterCommand - entering execute");

		/*
		 * Given the inputed values within the command, check if the user
		 * already exists within registered players. If so, throw a server
		 * exception. Else, add the player to the registered player map.
		 */
		RegisteredPlayers registeredPlayers = RegisteredPlayers.getSingleton();
		if (registeredPlayers.containsKey(username)) {
			throw new ServerResponseException("Invalid Username or password");
		} else {
			registeredPlayers.addNewPlayer(username, password);
		}

		logger.info("server/commands/RegisterCommand - exiting execute");
	}

}
