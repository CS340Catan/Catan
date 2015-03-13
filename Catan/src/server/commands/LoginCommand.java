package server.commands;

import shared.communication.UserCredentials;

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
	 */
	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

}
