package shared.communication;
/**
 * 
 * @author winstonhurst
 * Custom exception class. Thrown when the user gives bad input.
 * Simply inherits from java's Exception
 * Can be thrown in Username Password, GameSummary, and SaveParams constructors 
 * Example, a Username that is more than 7 letters long, this will be thrown
 */
@SuppressWarnings("serial")
public class InvalidInputException extends Exception {
	/**
	 * Pass a message explaining the exact nature of the error
	 * example: "Password contains invalid character"
	 * @param message A message giving details of what caused the exception and what acceptable values area
	 */
	public InvalidInputException(String message){
		super(message);
	}
}
