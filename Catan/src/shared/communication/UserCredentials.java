package shared.communication;

/**
 * This class wraps up information for logging in or creating a new user.
 * Includes a user name, password Domain:
 * <ul>
 * <li>username:Username</li>
 * <li>password:Password</li>
 * </ul>
 * Domain Restraints:
 * <ul>
 * <li>username - not null</li>
 * <li>password - not null</li>
 * </ul>
 * 
 * @author winstonhurst
 * 
 */
public class UserCredentials {

	Username name;
	Password password;

	/**
	 * @pre username must be valid
	 * @param username
	 * @param password
	 * 
	 */
	public UserCredentials(Username username, Password password) {
		this.name = username;
		this.password = password;
	}

	public Username getUsername() {
		return name;
	}

	public void setUsername(Username username) {
		this.name = username;
	}

	public Password getPassword() {
		return password;
	}

	public void setPassword(Password password) {
		this.password = password;
	}
}
