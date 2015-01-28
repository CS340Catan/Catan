package shared.communication;

/**
 * This class wraps up information for logging in or creating a new user.
 * Includes a user name, password Domain:
 * <ul>
 * <li>username:String</li>
 * <li>password:String</li>
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

	String name;
	String password;

	/**
	 * @pre username must be valid
	 * @param username
	 * @param password
	 * 
	 */
	public UserCredentials(String username, String password) {
		this.name = username;
		this.password = password;
	}

	public String getUsername() {
		return name;
	}

	public void setUsername(String username) {
		this.name = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
