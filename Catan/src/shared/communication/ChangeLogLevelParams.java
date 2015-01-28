package shared.communication;

/**
 * This is the parameters for the function ChangeLogLevel to be sent to the server
 * Domain:
 * 	logLevel:string
 * Domain:
 * 	must be either: ALL, SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST, OFF
 *
 */
public class ChangeLogLevelParams {
	
	String logLevel;

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}

}
