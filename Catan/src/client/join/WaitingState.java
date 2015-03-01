package client.join;

import client.model.ClientModel;
import client.model.Player;

public class WaitingState implements IPlayerWaitingState {

	@Override
	public void action(PlayerWaitingController playerWaitingController) {
		System.out.println("In update in Player Waiting Controller");
		boolean fourPlayers = true;
		for(Player player : ClientModel.getSingleton().getPlayers()){
			if(player == null){
				fourPlayers = false;
				break;
			}
		}
		System.out.println(fourPlayers);
		
		if(fourPlayers){
			ClientModel.getSingleton().setVersion(-1);
			playerWaitingController.getView().closeModal();
			playerWaitingController.setPlayerWaitingState(new NotWaitingState());
		}
	}

}
