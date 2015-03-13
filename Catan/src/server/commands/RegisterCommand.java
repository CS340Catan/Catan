package server.commands;

import shared.communication.UserCredentials;

/**
 * @author Drewfus
 * This is the command class for the Register function called on the server.
 * It will receive a UserCredentials object in the constructor
 */

public class RegisterCommand implements ICommand {
	
	UserCredentials params;
	
	RegisterCommand(UserCredentials params) {
		this.params = params;
	}
	/**
	 * Checks to see if the requested registration is valid, adds it to the RegisteredPlayers class if it is
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}