package client.turntracker;

import client.data.PlayerInfo;
import client.data.UserPlayerInfo;
import client.model.ClientModel;
import client.model.ClientModelController;
import client.model.Player;

public class TrackerInitialState implements ITurnTrackerControllerState {

	@Override
	public void initFromModel(ITurnTrackerView view, TurnTrackerController turnTracker) {
		// TODO Auto-generated method stub
		ClientModelController clientModelController = new ClientModelController();
		view.setLocalPlayerColor(clientModelController.getPlayerColor(UserPlayerInfo.getSingleton().getPlayerIndex()));
		
		//get players, then init them all in the view
		Player[] players = ClientModel.getSingleton().getPlayers();
		for(Player player:players) {
			if(player != null){
				view.initializePlayer(player.getPlayerIndex(), player.getName(), player.getCatanColor());
			}
		}
		
		turnTracker.setState(new TurnTrackerFinishState());
	}

}
