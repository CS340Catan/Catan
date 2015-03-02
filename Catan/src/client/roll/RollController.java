package client.roll;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Timer;

import javax.swing.JOptionPane;

import shared.utils.IServer;
import shared.utils.ServerResponseException;
import client.base.Controller;
import client.communicator.ServerProxy;
import client.data.UserPlayerInfo;
import client.model.ClientModel;
import client.model.ClientModelController;
import client.model.TurnTracker;

/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController,
		Observer {

	private IRollResultView resultView;
	private IServer serverProxy = ServerProxy.getSingleton();
	private ClientModelController modelController;

	/**
	 * RollController constructor
	 * 
	 * @param view
	 *            Roll view
	 * @param resultView
	 *            Roll result view
	 */
	public RollController(IRollView view, IRollResultView resultView) {

		super(view);

		setResultView(resultView);
		ClientModel.getNotifier().addObserver(this);
		modelController = new ClientModelController();
	}

	public IRollResultView getResultView() {
		return resultView;
	}

	public void setResultView(IRollResultView resultView) {
		this.resultView = resultView;
	}

	public IRollView getRollView() {
		return (IRollView) getView();
	}

	@Override
	public void rollDice() {

		Random rand = new Random();
		int rollVal = rand.nextInt((12 - 2) + 1) + 2;
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		if (modelController.canRollNumber(playerIndex)) {
			try {
				getRollView().closeModal();
				getResultView().showModal();
				getResultView().setRollValue(rollVal);
				serverProxy.rollNumber(rollVal);
				
				if(rollVal==7){
					try {
						new Timer().wait(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					getResultView().closeModal();
				}
			} catch (ServerResponseException e) {
				JOptionPane.showMessageDialog(null, "Invalid JSON or Cookie",
						"Server Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "No can do", "No can do",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		TurnTracker tracker = ClientModel.getSingleton().getTurnTracker();
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		if (tracker.getCurrentTurn() == playerIndex
				&& tracker.getStatus().equals("Rolling")
				&& !getRollView().isModalShowing()) {
			getRollView().showModal();
		}

	}

}
