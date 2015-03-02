package client.join;

public class NotWaitingState implements IPlayerWaitingState {

	@Override
	public void action(PlayerWaitingController playerWaitingController) {
		playerWaitingController.startNormalPolling();
	}

}
