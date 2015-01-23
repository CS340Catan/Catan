package shared.communication;

public class Username {

	/**
	 * The username
	 */
	String username;
	
	/**
	 * @pre The username string passed in must not be null
	 * @pre the Length of the username parameters must be greater than 2 but less than 8
	 * @post Creates a new Username object which puts some restraints on possible string values
	 * @param username
	 * @throws InvalidInputException
	 */
	public Username(String username) throws InvalidInputException{
		if(username==null || username.length()<3 || username.length()>7)
			throw new InvalidInputException("Invalid username!\n");
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
	
	@Override
	public boolean equals(Object o){
		if(o==this)
			return true;
		if(o instanceof Username){
			Username temp = (Username) o;
			if(temp.username.equals(this.username))
				return true;
		}
		return false;
	}
}
