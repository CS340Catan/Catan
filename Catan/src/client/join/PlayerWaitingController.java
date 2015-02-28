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
import client.model.ClientModel;
import client.model.ClientModelController;

/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements
		IPlayerWaitingController, Observer {

	private IServer server;
	private Poller poller;

	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);
		ClientModel.getSingleton().addObserver(this);
		server = ServerProxy.getSingleton();
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView) super.getView();
	}

	@Override
	public void start() {

		// poller = new Poller(ServerProxy.getSingleton(),
		// new ClientModelController());
		// poller.setTimer();

		getView().showModal();

		// show ai choices
		String[] AIChoices = { "" };
		try {
			AIChoices = server.getAITypes();
		} catch (ServerResponseException e) {
			// TODO Auto-generated catch block
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
			String outputStr = "Could not reach the server.";
			JOptionPane.showMessageDialog(null, outputStr,
					"Server unavailable", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		ClientModel model = (ClientModel) o;
		if (model.getPlayers().length == 4) {
			getView().closeModal();
		}
	}

}
