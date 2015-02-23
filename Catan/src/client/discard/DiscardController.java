package client.discard;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import shared.communication.DiscardCardsParams;
import shared.definitions.ResourceType;
import shared.utils.IServer;
import shared.utils.ServerResponseException;
import client.base.Controller;
import client.communicator.HTTPCommunicator;
import client.communicator.ServerProxy;
import client.data.PlayerInfo;
import client.misc.IWaitView;
import client.model.ClientModel;
import client.model.ClientModelController;
import client.model.ResourceList;

/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController, Observer {

	private IWaitView waitView;
	private IServer serverProxy = new ServerProxy(new HTTPCommunicator());
	private ClientModelController modelController;
	
	private final String SERVER_ERROR = "Give us a minute to get the server working...";
	private final String NO_CAN_DO = "Sorry buster, no can do right now";
	//if done discarding, but others still are discarding, then display the wait window

	/**
	 * DiscardController constructor
	 * 
	 * @param view
	 *            View displayed to let the user select cards to discard
	 * @param waitView
	 *            View displayed to notify the user that they are waiting for
	 *            other players to discard
	 */
	public DiscardController(IDiscardView view, IWaitView waitView) {

		super(view);
		
		this.waitView = waitView;
		ClientModel.getSingleton().addObserver(this);
		modelController = new ClientModelController(ClientModel.getSingleton());
	}

	public IDiscardView getDiscardView() {
		return (IDiscardView) super.getView();
	}

	public IWaitView getWaitView() {
		return waitView;
	}

	@Override
	public void increaseAmount(ResourceType resource) {
		//get current, decrease
				int max = getDiscardView().getResourceMaxAmount(resource);
				boolean canIncrease = true;
				boolean canDecrease = true;
				int newAmount = getDiscardView().getResourceDiscardAmount(resource) + 1 ;
				if(newAmount>=0 && newAmount<=max)
					getDiscardView().setResourceDiscardAmount(resource,newAmount);
				if(newAmount<=0)
					canDecrease = false;
				if(newAmount>=max)
					canIncrease = false;
				getDiscardView().setResourceAmountChangeEnabled(resource, canIncrease, canDecrease);
	}

	@Override
	public void decreaseAmount(ResourceType resource) {
		//get current, decrease
		int max = getDiscardView().getResourceMaxAmount(resource);
		boolean canIncrease = true;
		boolean canDecrease = true;
		int newAmount = getDiscardView().getResourceDiscardAmount(resource) -1 ;
		if(newAmount>=0 && newAmount<=max)
			getDiscardView().setResourceDiscardAmount(resource,newAmount);
		if(newAmount<=0)
			canDecrease = false;
		if(newAmount>=max)
			canIncrease = false;
		getDiscardView().setResourceAmountChangeEnabled(resource, canIncrease, canDecrease);
	}

	@Override
	public void discard() {
		int playerIndex = PlayerInfo.getSingleton().getPlayerIndex();
		if(modelController.canDiscardCards(playerIndex))
		{
			//getResroucelist
			ResourceList list = getDiscardView().getListToDiscard();
			try {
				ClientModel updatedModel = serverProxy.discardCards(new DiscardCardsParams(playerIndex,list));
				ClientModel.getSingleton().setClientModel(updatedModel);
				modelController.setClientModel(updatedModel);
			} catch (ServerResponseException e) {
				JOptionPane.showMessageDialog(null, SERVER_ERROR,
						"Server Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, NO_CAN_DO,
					"No can do", JOptionPane.ERROR_MESSAGE);
		}
			
		getDiscardView().closeModal();
	}

	@Override
	public void update(Observable o, Object arg) {
		ClientModel updatedModel = (ClientModel) o;
		modelController.setClientModel(updatedModel);
		int playerIndex = PlayerInfo.getSingleton().getPlayerIndex();
		if(modelController.canDiscardCards(playerIndex)){
			getDiscardView().setDiscardButtonEnabled(true);
			//set maxes
			//show  buttons
		}
		else{
			getDiscardView().setDiscardButtonEnabled(false);
		}
		//change view accordingly, probably already closed, so nothing
	}

}
