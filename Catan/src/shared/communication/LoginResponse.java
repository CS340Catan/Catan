package shared.communication;

/**
 * Given a login function call, a login response will be returned to the client.
 * This login in response will contain the username and password previously
 * inputed, in addition to the correctly associated playerID.
 * 
 * @author Keloric
 *
 */
public class LoginResponse {

	Username name;
	Password password;
	int playerID;

	public LoginResponse() {
	}

	/**
	 * @pre when id is set, two UserCredentials can't have the same id
	 * @param username
	 * @param password
	 * 
	 */
	public LoginResponse(Username username, Password password) {
		this.name = username;
		this.password = password;
	}

	/**
	 * @pre when id is set two UserCredentials can't have the same id
	 * @param username
	 * @param password
	 * @param id
	 */
	public LoginResponse(Username username, Password password, int id) {
		this(username, password);
		this.playerID = id;
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

	public int getId() {
		return this.playerID;
	}

	public void setId(int id) {
		this.playerID = id;
	}
}
