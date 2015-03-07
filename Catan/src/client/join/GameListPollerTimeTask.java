package client.join;

import java.util.TimerTask;

public class GameListPollerTimeTask extends TimerTask{

	private GameListPoller poller;
	
	public GameListPollerTimeTask (GameListPoller poller) {
		this.poller = poller;
	}
	
	@Override
	public void run() {
		poller.updateGameList();
	}

}
