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

	String name;
	String password;
	int playerID;
	boolean success = false;

	public LoginResponse() {
	}

	/**
	 * @pre when id is set, two UserCredentials can't have the same id
	 * @param username
	 * @param password
	 * 
	 */
	public LoginResponse(String username, String password) {
		this.name = username;
		this.password = password;
	}

	/**
	 * @pre when id is set two UserCredentials can't have the same id
	 * @param username
	 * @param password
	 * @param id
	 */
	public LoginResponse(String username, String password, int id) {
		this(username, password);
		this.playerID = id;
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

	public int getId() {
		return this.playerID;
	}

	public void setId(int id) {
		this.playerID = id;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}
}
