package client.join;

import java.util.ArrayList;

import client.communicator.ServerProxy;
import client.controllers.Poller;
import client.data.PlayerInfo;
import client.model.ClientModel;
import client.model.Player;

public class WaitingState implements IPlayerWaitingState {
	private PlayerWaitingController playerWaitingController;
	@Override
	public void action(PlayerWaitingController playerWaitingControllerParam) {
		playerWaitingController = playerWaitingControllerParam;
		if(playerWaitingController.isFourPlayers()){
			playerWaitingController.stopPlayerWaitingPolling();
			playerWaitingController.startNormalPolling();
			playerWaitingController.setPlayerWaitingState(new NotWaitingState());
			playerWaitingController.getView().closeModal();			
		}
		else {
			updateModal();
		}
	}
	private void updateModal(){
		ArrayList<PlayerInfo> playerInfoList = new ArrayList<PlayerInfo>();		
		for(Player player : ClientModel.getSingleton().getPlayers()){
			if(player != null){
				playerInfoList.add(player.getPlayerInfo());
			}
		}
		playerWaitingController.getView().setPlayers(playerInfoList.toArray(new PlayerInfo[playerInfoList.size()]));
		playerWaitingController.getView().closeModal();
		playerWaitingController.getView().showModal();
		
	}
}
