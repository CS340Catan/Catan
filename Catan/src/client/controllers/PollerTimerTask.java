package client.controllers;

import java.util.TimerTask;

public class PollerTimerTask extends TimerTask {

	private Poller poller;

	public PollerTimerTask(Poller poller) {
		this.poller = poller;
	}

	@Override
	public void run() {
		poller.updateModel();
	}

}
