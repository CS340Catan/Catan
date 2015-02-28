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
import client.data.UserPlayerInfo;
import client.misc.IWaitView;
import client.model.ClientModel;
import client.model.ClientModelController;
import client.model.ResourceList;

/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController, Observer {

	private IWaitView waitView;
	private IServer serverProxy = ServerProxy.getSingleton();
	private ClientModelController modelController;
	
	private final String SERVER_ERROR = "Give us a minute to get the server working...";
	private final String NO_CAN_DO = "Sorry buster, no can do right now";

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
		ClientModel.getNotifier().addObserver(this);
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
				
				enableDiscard(newAmount,max);
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
		
		enableDiscard(newAmount,max);
			
	}

	@Override
	public void discard() {
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		if(modelController.canDiscardCards(playerIndex))
		{
			//getResroucelist
			ResourceList list = getDiscardView().getListToDiscard();
			try {
				ClientModel updatedModel = serverProxy.discardCards(new DiscardCardsParams(playerIndex,list));
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
		waitView.showModal();
	}

	@Override
	public void update(Observable o, Object arg) {
		ClientModel updatedModel = ClientModel.getSingleton();
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		if(updatedModel.getTurnTracker().getStatus()=="discarding")
		{
			if(!getDiscardView().isModalShowing() && !waitView.isModalShowing()){
				if(modelController.canDiscardCards(playerIndex)){					
					//setup
					
					setResource(ResourceType.BRICK, playerIndex);
					
					setResource(ResourceType.SHEEP, playerIndex);
					
					setResource(ResourceType.WHEAT, playerIndex);
					
					setResource(ResourceType.ORE, playerIndex);
					
					setResource(ResourceType.WOOD, playerIndex);
			
					//set main button
					getDiscardView().setDiscardButtonEnabled(false);
					getDiscardView().showModal();
				}
			
				else{
					waitView.showModal();
				}
			}
		}
		else{
			if(getDiscardView().isModalShowing()){
			getDiscardView().closeModal();
			}
			if(waitView.isModalShowing()){
					waitView.closeModal();
			}	
		}
	}
	
	private void setResource(ResourceType resource, int playerIndex)
	{
		int currentCount = getResourceCount(resource, playerIndex);
		getDiscardView().setResourceMaxAmount(resource,currentCount);
		getDiscardView().setResourceDiscardAmount(resource,0);
		boolean canInc = false;
		if(currentCount>0)
			canInc = true;
		getDiscardView().setResourceAmountChangeEnabled(resource, canInc, false);
	}
	
	private int getResourceCount(ResourceType resource, int playerIndex)
	{
		ClientModel updatedModel = modelController.getClientModel();
		switch (resource){
		case WOOD: return updatedModel.getPlayers()[playerIndex].getResources().getWood();
		case ORE: return updatedModel.getPlayers()[playerIndex].getResources().getOre();
		case BRICK: return updatedModel.getPlayers()[playerIndex].getResources().getBrick();
		case WHEAT: updatedModel.getPlayers()[playerIndex].getResources().getWheat();
		case SHEEP: updatedModel.getPlayers()[playerIndex].getResources().getSheep();
		default: return -1;
		}
	}
	
	private void enableDiscard(int newAmount, int max)
	{
		boolean enable = newAmount==max;
		
		getDiscardView().setDiscardButtonEnabled(enable);
	}

}
