package client.controllers;

/**
 * The purpose of the poller is to perform a function on a timer once a second.
 * This function checks with the server to see if the ClientModel needs to be updated with data from the server.
 * Instead of asking the server directly, the Poller will call a function in ClientController which will
 * eventually make it to the server. Information will not come back to the Poller.
 * 
 * Domain:
 * 	timer:Timer
 * Domain Constraint:
 * 	the timer will be scheduled to perform its task once per second
 */

public class Poller {
	
	/**
	 * @pre an actual game has started
	 * @post a java timer is set that calls a function in the ClientController which checks the server to see
	 * if anything needs to be updated in the ClientModel
	 */
	public void setTimer() {
		
	}
	
	/**
	 * @pre the game has ended or been temporarily exited
	 * @post the java timer no longer 
	 */
	public void stopTimer() {
		
	}
	
}
