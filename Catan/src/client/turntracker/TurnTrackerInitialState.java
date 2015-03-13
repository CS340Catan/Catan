package client.turntracker;

import shared.model.ClientModel;
import shared.model.Player;

public class TurnTrackerInitialState implements ITurnTrackerControllerState {

	int[] initialized = null;

	@Override
	public void initFromModel(ITurnTrackerView view,
			TurnTrackerController turnTracker) {
		if (this.initialized == null) {
			this.initialized = new int[4];
			initialized[0] = 0;
			initialized[1] = 0;
			initialized[2] = 0;
			initialized[3] = 0;
		}

		// get players, then init them all in the view
		Player[] players = ClientModel.getSingleton().getPlayers();
		for (int i = 0; i < players.length; i++) {
			Player player = players[i];

			if (player != null && initialized[i] != 1) {
				view.initializePlayer(player.getPlayerIndex(),
						player.getName(), player.getCatanColor());
				initialized[i] = 1;
			}
		}
	}

}
