package client.controllers;

import client.communicator.ServerProxy;
import client.model.ClientModel;
import shared.model.Player;
import shared.utils.IServer;
import shared.utils.ServerResponseException;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The purpose of the poller is to perform a function on a timer once a second.
 * This function checks with the ProxyServer to see if the ClientModel needs to
 * be updated with data from the server. The Poller will call a function in
 * ProxyServer which will return a ClientModel. The Poller will then call update
 * on the ClientModelController.
 * 
 * Domain: timer:Timer server:IServer Domain Constraint: the timer will be
 * scheduled to perform its task once per second
 */

public class Poller {

	private IServer server;
	private Timer timer;
	private Timer hackyTimer;
	private boolean normalTimerRunning = false;

	public Poller(IServer server) {
		this.server = server;
	}

	/**
	 * Calls function in ServerProxy which will update the ClientModel if it has
	 * changed on the server
	 * 
	 * @pre none
	 * @post has either left current ClientModel alone, or updated it
	 */
	public void updateModel() {
		ClientModel updatedClientModel;
		try {
			updatedClientModel = server.updateModel(ClientModel.getSingleton()
					.getVersion());
			if (updatedClientModel != null) {
				ClientModel.getSingleton().setClientModel(updatedClientModel);
			}
		} catch (ServerResponseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * used for when the move functions are called. they return a new client
	 * model, and that is passed into this function to update
	 */
	public void updateModel(ClientModel newModel) {
		ClientModel.getSingleton().setClientModel(newModel);
	}
	public void unconditionallyUpdateModel(){
		ClientModel updatedClientModel;
		try {
			updatedClientModel = ServerProxy.getSingleton().updateModelNoVersionCheck();
			if (updatedClientModel != null) {
				ClientModel.getSingleton().setClientModel(updatedClientModel);
			}
		} catch (ServerResponseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @pre an actual game has started
	 * @post a java timer is set that calls updateModel() every second
	 */
	public void setTimer() {
		normalTimerRunning = true;
		TimerTask timerTask = new PollerTimerTask(this);
		timer = new Timer(true);
		timer.scheduleAtFixedRate(timerTask, 0, 1000);
		/*
		 * (timerTask, 0 means starts now, 1000 means 1 second)
		 */
	}

	public void setPlayerWaitingTimer() {
		TimerTask timerTask = new TimerTask() {
			private int playerCount = 0;

			@Override
			public void run() {
				try {
					ClientModel updatedClientModel = ServerProxy.getSingleton()
							.updateModelNoVersionCheck();
					if (updatedClientModel != null) {
						int newPlayerCount = 0;
						for (Player player : updatedClientModel.getPlayers()) {
							if (player != null) {
								newPlayerCount += 1;
							}
						}
						if (newPlayerCount > playerCount) {
							playerCount = newPlayerCount;
							ClientModel.getSingleton().setClientModel(
									updatedClientModel);
						}
					}
				} catch (ServerResponseException e) {
					e.printStackTrace();
					System.out
							.println("Something terrible happened in the hacky poller task");
				}
				if (ClientModel.getSingleton().getTurnTracker().getStatus().toUpperCase().equals("SECONDROUND")) {
					setTimer();
					stopPlayerWaitingTimer();
				}
			}
		};
		hackyTimer = new Timer(true);
		hackyTimer.scheduleAtFixedRate(timerTask, 0, 1000); // (timerTask, 0
															// means
		// starts now, 1000
		// means 1/second)
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

	public void stopPlayerWaitingTimer() {
		if (hackyTimer != null) {
			hackyTimer.cancel();
		}
	}

	public boolean isNormalTimerRunning() {
		return normalTimerRunning;
	}

}
