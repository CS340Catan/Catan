package shared.communication;
/**
 * 
 * @author winstonhurst
 * This class wraps up information for logging in or creating a new user.
 * Includes a user name, password and user id.
 * Domain:
 * 	<ul>
 * 	<li>username:Username</li>
 * 	<li>password:Password</li>
 * 	<li>id:integer</li>
 * 	</ul>
 * Domain Restraints:
 * 	<ul>
 * 	<li>username - not null</li>
 * 	<li>password - not null</li>
 *  <li>id - 0 to -1</li>
 * 	</ul>
 * 
 */
public class UserCredential {
	
	Username name;
	Password password;
	int playerID;
	
	/**
	 * @pre when id is set, two UserCredentials can't have the same id
	 * @param username
	 * @param password
	 * 
	 */
	public UserCredential(Username username, Password password){
		this.name = username;
		this.password = password;
	}
	
	/**
	 * @pre when id is set two UserCredentials can't have the same id
	 * @param username
	 * @param password
	 * @param id
	 */
	public UserCredential(Username username, Password password, int id){
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
	
	public int getId(){
		return this.playerID;
	}
	
	public void setId(int id){
		this.playerID=id;
	}
}
