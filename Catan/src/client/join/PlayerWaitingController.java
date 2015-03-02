package client.join;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import shared.communication.AddAIParams;
import shared.communication.GameSummary;
import shared.utils.IServer;
import shared.utils.ServerResponseException;
import client.base.*;
import client.communicator.ServerProxy;
import client.controllers.Poller;
import client.data.UserPlayerInfo;
import client.model.ClientModel;
import client.model.ClientModelController;
import client.model.Player;

/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements
		IPlayerWaitingController, Observer {

	private IServer server;
	private Poller poller;
	private IPlayerWaitingState playerWaitingState;

	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);
		ClientModel.getNotifier().addObserver(this);
		setPlayerWaitingState(new WaitingState());
		server = ServerProxy.getSingleton();
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView) super.getView();
	}

	@Override
	public void start() {

		poller = new Poller(ServerProxy.getSingleton(),
				new ClientModelController());
		poller.updateModel();
		poller.setTimer();
		if (ClientModel.getSingleton().getPlayers() != null) {
			for(Player player : ClientModel.getSingleton().getPlayers()){
				if(player == null){
					if (!isFourPlayers()) {
						getView().showModal();
						break;
					} 
				}
			}
		}
		
		String[] AIChoices = { "" };
		try {
			AIChoices = server.getAITypes();
		} catch (ServerResponseException e) {
			e.printStackTrace();
		}

		getView().setAIChoices(AIChoices);

	}
	
	private boolean isFourPlayers() {
		try {
			GameSummary[] gameList = server.getGameList();
			int gameId = UserPlayerInfo.getSingleton().getGameId();
			int numberOfPlayers = 0;
			for (int i = 0; i<4; i++) {
				if(!gameList[gameId].getPlayers()[i].getName().equals("")) {
					numberOfPlayers++;
				}
			}
			if (numberOfPlayers == 4) {
				return true;
			}
		} catch (ServerResponseException e) {
			e.printStackTrace();
		}
		return false;
	}

	
	@Override
	public void addAI() {

		AddAIParams addAIParams = new AddAIParams();
		addAIParams.setAIType(getView().getSelectedAI());
		try {
			server.addAI(addAIParams);
		} catch (ServerResponseException e) {
			String outputStr = "Server Failure";
			JOptionPane.showMessageDialog(null, outputStr,
					"Server Failure", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		if(isFourPlayers()) {
			getView().closeModal();
			setPlayerWaitingState(new NotWaitingState());
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Output update");
		playerWaitingState.action(this);
	}

	public IPlayerWaitingState getPlayerWaitingState() {
		return playerWaitingState;
	}

	public void setPlayerWaitingState(IPlayerWaitingState playerWaitingState) {
		this.playerWaitingState = playerWaitingState;
	}

}
