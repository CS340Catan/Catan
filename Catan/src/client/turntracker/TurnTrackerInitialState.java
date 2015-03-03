package client.turntracker;

import client.model.ClientModel;
import client.model.Player;

public class TurnTrackerInitialState implements ITurnTrackerControllerState {

	@Override
	public void initFromModel(ITurnTrackerView view,
			TurnTrackerController turnTracker) {
		// get players, then init them all in the view
		Player[] players = ClientModel.getSingleton().getPlayers();
		for (Player player : players) {
			if (player != null) {
				view.initializePlayer(player.getPlayerIndex(),
						player.getName(), player.getCatanColor());
			}
		}

		turnTracker.setState(new TurnTrackerFinishState());
	}

}
