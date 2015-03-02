package client.roll;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
	private int rollVal;
	private Timer timer;
	private int countDown;

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
		timer.cancel();
		timer.purge();
		Random rand = new Random();
		rollVal = rand.nextInt((12 - 2) + 1) + 2;
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		if (modelController.canRollNumber(playerIndex)) {
			try {
				serverProxy.rollNumber(rollVal);
			} catch (ServerResponseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Server Error", "Server Error",
						JOptionPane.ERROR_MESSAGE);
			}
			getRollView().closeModal();
			getResultView().showModal();
			getResultView().setRollValue(rollVal);
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
			this.countDown = 10;
			this.timer = new Timer();
			timer.schedule(new AutoRoll(),500,1100);
			
		}

	}
	
	private class AutoRoll extends TimerTask{
		@Override
		public void run() {
			countDown--;
			getRollView().setMessage("Rolling in "+(countDown));
			if(countDown==0)
				rollDice();
		}
	}

}
