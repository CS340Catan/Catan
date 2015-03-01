package client.join;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import shared.communication.AddAIParams;
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
					getView().showModal();
					break;
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

	}

	@Override
	public void update(Observable o, Object arg) {
		playerWaitingState.action(this);
	}

	public IPlayerWaitingState getPlayerWaitingState() {
		return playerWaitingState;
	}

	public void setPlayerWaitingState(IPlayerWaitingState playerWaitingState) {
		this.playerWaitingState = playerWaitingState;
	}

}
