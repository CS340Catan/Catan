package client.join;

import client.data.GameInfo;
import shared.communication.GameSummary;
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

public class GameListPoller {

	private IServer server;
	private Timer timer;
	private boolean timerRunning = false;
	private JoinGameController joinGameController;

	public GameListPoller(IServer server, JoinGameController joinGameController) {
		this.server = server;
		this.joinGameController = joinGameController;
	}

	/**
	 * Calls function in ServerProxy which will update the ClientModel if it has
	 * changed on the server
	 * 
	 * @pre none
	 * @post has either left current ClientModel alone, or updated it
	 */
	public void updateGameList() {
		// System.out.println("Entered GameListPoller:updateGameList() method");

		try {
			GameSummary[] newGameSummaryList = server.getGameList();
			GameInfo[] newGameInfoList = new GameInfo[newGameSummaryList.length];

			for (int i = 0; i < newGameSummaryList.length; i++) {
				newGameInfoList[i] = newGameSummaryList[i].toGameInfo();
			}

			this.joinGameController.updateGameList(newGameInfoList);
		} catch (ServerResponseException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @pre an actual game has started
	 * @post a java timer is set that calls updateModel() every second
	 */
	public void setTimer() {
		timerRunning = true;
		TimerTask timerTask = new GameListPollerTimeTask(this);
		timer = new Timer(true);
		timer.scheduleAtFixedRate(timerTask, 0, 2500);
		/*
		 * (timerTask, 0 means starts now, 1000 means 1 second)
		 */
	}

	/**
	 * @pre the game has ended or been temporarily exited
	 * @post the java timer no longer
	 */
	public void stopTimer() {
		if (this.timer != null) {
			this.timer.cancel();
			this.timer.purge();
			this.timerRunning = false;
		}
	}

	public boolean isTimerRunning() {
		return timerRunning;
	}

	public void setTimerRunning(boolean timerRunning) {
		this.timerRunning = timerRunning;
	}
}
