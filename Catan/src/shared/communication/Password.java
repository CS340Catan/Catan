package shared.communication;
/**
 * 
 * @author winstonhurst
 *	Simple class which is a String with a restricted domain.
 * Domain Restrains
 * <ul>
 * <li>password length greater than 4</li>
 * <li>Passowrd consists of alphanumeric, dashes and underscores only</li>
 * </ul>
 */
public class Password {
	/**
	 * A user's password represented as a srting
	 */
	String password;
	
	/**
	 * Creates a new Password object if the password parameter meets the domain restraints
	 * If the password parameter doesn't meet the constraints, it will throw an InvalidInputException
	 * @param password
	 * @throws InvalidInputException
	 */
	public Password(String password) throws InvalidInputException{
		if(password==null || password.length()<5 || hasInvalidChar(password))
			throw new InvalidInputException("Invalid password\n");
		this.password = password;
	}
	
	public String getPasswordSrting(){
		return password;
	}
	
	private boolean hasInvalidChar(String str){
		return true;
	}
}
