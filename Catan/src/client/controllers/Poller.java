package client.controllers;

import client.model.ClientModel;
import client.model.ClientModelController;
import shared.utils.IServer;

import java.util.Timer;
import java.util.TimerTask;

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
	
	private IServer server;
	private ClientModelController clientModelController;
	private Timer timer;
	
	public Poller(IServer server) {
		this.server = server;
	}
	
	/** Calls function in ServerProxy which will update the ClientModel if it has changed on the server
	 * @pre none
	 * @post has either left current ClientModel alone, or updated it
	 */
	public void updateModel() {
		ClientModel updatedClientModel = server.updateModel(clientModelController.getClientModel().getVersion());
		if (updatedClientModel != null) {
			clientModelController.setClientModel(updatedClientModel);
		}
	}
	
	/**
	 * @pre an actual game has started
	 * @post a java timer is set that calls updateModel() every second
	 */
	public void setTimer() {
		TimerTask timerTask = new PollerTimerTask(this);
		timer = new Timer(true);
		timer.scheduleAtFixedRate(timerTask, 0, 1000); //(timerTask, 0 means starts now, 1000 means 1/second)
	}
	
	/**
	 * @pre the game has ended or been temporarily exited
	 * @post the java timer no longer 
	 */
	public void stopTimer() {
		if (timer != null) {
			timer.cancel();
		}
	}
	
}
