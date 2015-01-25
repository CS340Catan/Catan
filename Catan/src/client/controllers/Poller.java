package client.controllers;

/**
 * The purpose of the poller is to perform a function on a timer once a second.
 * This function checks with the ProxyServer to see if the ClientModel needs to be updated with data from the server.
 * The Poller will call a function in ProxyServer which will return a ClientModel. 
 * The Poller will then call update on the ClientModelController.
 * 
 * Domain:
 * 	timer:Timer
 *  server:IServer
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
