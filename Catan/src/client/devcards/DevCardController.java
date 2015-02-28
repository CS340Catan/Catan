package client.devcards;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import shared.communication.PlayMonopolyParams;
import shared.communication.PlayMonumentParams;
import shared.communication.Resource;
import shared.communication.UserActionParams;
import shared.communication.YearOfPlentyParams;
import shared.definitions.ResourceType;
import shared.utils.IServer;
import shared.utils.ServerResponseException;
import client.base.Controller;
import client.base.IAction;
import client.communicator.HTTPCommunicator;
import client.communicator.ServerProxy;
import client.data.PlayerInfo;
import client.data.UserPlayerInfo;
import client.model.ClientModel;
import client.model.ClientModelController;
import client.model.ResourceList;

/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController,Observer {

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;
	private IServer serverProxy = ServerProxy.getSingleton();
	private ClientModelController modelController;
	
	
	private final String SERVER_ERROR = "Give us a minute to get the server working...";
	private final String NO_CAN_DO = "Sorry buster, no can do right now";

	/**
	 * DevCardController constructor
	 * 
	 * @param view
	 *            "Play dev card" view
	 * @param buyCardView
	 *            "Buy dev card" view
	 * @param soldierAction
	 *            Action to be executed when the user plays a soldier card. It
	 *            calls "mapController.playSoldierCard()".
	 * @param roadAction
	 *            Action to be executed when the user plays a road building
	 *            card. It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view,
			IBuyDevCardView buyCardView, IAction soldierAction,
			IAction roadAction) {

		super(view);

		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;
		ClientModel.getSingleton().addObserver(this);
		modelController = new ClientModelController(ClientModel.getSingleton());
	}

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView) super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}

//==============================================Begin Implementation Below================================
	
	//===========================BUY CARD VIEW CONTROLLS======================
	@Override
	public void startBuyCard() {
		getBuyCardView().showModal();
	}

	@Override
	public void cancelBuyCard() {
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard() {
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		UserActionParams buyDevCardParams = new UserActionParams(playerIndex);
		buyDevCardParams.setType("buyDevCard");
		if(modelController.canBuyDevCard(playerIndex)){
			try {
				ClientModel updatedModel = serverProxy.buyDevCard(buyDevCardParams);
			} catch (ServerResponseException e) {
				JOptionPane.showMessageDialog(null, SERVER_ERROR,
						"Server Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else{
			JOptionPane.showMessageDialog(null, NO_CAN_DO,
					"No can do", JOptionPane.ERROR_MESSAGE);
		}
		getBuyCardView().closeModal();
	}
	//===============================PLAY DEV CARD  CONTROLLS=====================
	@Override
	public void startPlayCard() {

		getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() {

		getPlayCardView().closeModal();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		//closeModal called just before this
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		String resourceString = resource.toString();
		//if(resourceString.equals("")) = handle that bad boy here
		if(modelController.canPlayMonopolyCard(playerIndex)){
			try {
				ClientModel updatedModel = serverProxy.playMonopolyCard(new PlayMonopolyParams(resourceString, playerIndex));
			} catch (ServerResponseException e) {
				JOptionPane.showMessageDialog(null, SERVER_ERROR,
						"Server Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else{
			JOptionPane.showMessageDialog(null, NO_CAN_DO,
					"No can do", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void playMonumentCard() {
		//closeModal called just before this
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		if(modelController.canPlayMonumentCard(playerIndex))
		{
			try {
				ClientModel updatedModel = serverProxy.playMonument(new PlayMonumentParams(playerIndex));
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
	}

	@Override
	public void playRoadBuildCard() {

		roadAction.execute();
	}

	@Override
	public void playSoldierCard() {

		soldierAction.execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1,
			ResourceType resource2) {
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		ResourceList resources = new ResourceList(0,0,0,0,0);
		
		String res1Str = resource1.toString();
		String res2Str = resource2.toString();
		//if(res1Str.equals("")||res2Str.equals(""))
			//possibly handle error
		Resource res1 = new Resource(res1Str);
		Resource res2 = new Resource(res2Str);
		
		addResource(resource1, resources);
		addResource(resource2, resources);
		if(modelController.canPlayYearOfPlentyCard(playerIndex, resources)){
			try {
				ClientModel updatedModel = serverProxy.playYearOfPlentyCard(new YearOfPlentyParams(playerIndex, res1,res2));
			} catch (ServerResponseException e)  {
				JOptionPane.showMessageDialog(null, SERVER_ERROR,
						"Server Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		else
		{
			JOptionPane.showMessageDialog(null, NO_CAN_DO,
					"No can do", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		//make necesarry changes to this view (probably none)
	}
	
//==================================================================================	
//==============================PRIVATE HELPER FUNCTIONS============================
//==================================================================================
	
	private void addResource(ResourceType resource, ResourceList list){
		switch (resource)
		{
			case WOOD: list.setWood(list.getWood()+1);
						return;
			case SHEEP: list.setSheep(list.getSheep()+1);
						return;
			case ORE: list.setOre(list.getOre()+1);
						return;
			case BRICK: list.setBrick(list.getBrick()+1);
						return;
			case WHEAT: list.setWheat(list.getWheat()+1);
						return;
			default: return;
		}
	}

}
