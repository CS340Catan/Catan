package client.join;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import shared.communication.AddAIParams;
import shared.utils.IServer;
import shared.utils.ServerResponseException;
import client.base.*;
import client.communicator.HTTPCommunicator;
import client.communicator.ServerProxy;
import client.model.ClientModel;

/**
 * Implementation for the player waiting controller
 */
public class PlayerWaitingController extends Controller implements
		IPlayerWaitingController, Observer {

	private IServer server;

	public PlayerWaitingController(IPlayerWaitingView view) {

		super(view);
		ClientModel.getSingleton().addObserver(this);
		server = new ServerProxy(new HTTPCommunicator());
	}

	@Override
	public IPlayerWaitingView getView() {

		return (IPlayerWaitingView) super.getView();
	}

	@Override
	public void start() {

		getView().showModal();
	}

	@Override
	public void addAI() {
		AddAIParams addAIParams = new AddAIParams();
		addAIParams.setAIType("LARGEST_ARMY");
		try {
			server.addAI(addAIParams);
		} catch (ServerResponseException e) {
			String outputStr = "Could not reach the server.";
			JOptionPane.showMessageDialog(null, outputStr,
					"Server unavailable", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		getView().closeModal();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

}
