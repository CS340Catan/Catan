package shared.utils;

@SuppressWarnings("serial")
public class ServerResponseException extends Exception {

	public ServerResponseException (String message){
		super(message);
	}
}
