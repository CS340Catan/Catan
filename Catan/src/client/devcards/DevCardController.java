package client.devcards;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

import shared.communication.PlayMonopolyParams;
import shared.communication.PlayMonumentParams;
import shared.communication.UserActionParams;
import shared.communication.YearOfPlentyParams;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.model.Player;
import shared.model.ResourceList;
import shared.utils.IServer;
import shared.utils.ServerResponseException;
import client.base.Controller;
import client.base.IAction;
import client.communicator.ServerProxy;
import client.data.UserPlayerInfo;
import client.model.ClientModel;
import client.model.ClientModelFacade;

/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements
		IDevCardController, Observer {

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;
	private IServer serverProxy = ServerProxy.getSingleton();
	private ClientModelFacade modelController;
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
		ClientModel.getNotifier().addObserver(this);
		modelController = new ClientModelFacade();
	}

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView) super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}

	// ==============================================Begin Implementation
	// Below================================

	// ===========================BUY CARD VIEW CONTROLLS======================
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
		if (modelController.canBuyDevCard(playerIndex)) {
			try {
				serverProxy.buyDevCard(buyDevCardParams);
			} catch (ServerResponseException e) {
				JOptionPane.showMessageDialog(null, SERVER_ERROR,
						"Server Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, NO_CAN_DO, "No can do - devCardctrl buy card",
					JOptionPane.ERROR_MESSAGE);
		}
		getBuyCardView().closeModal();
	}

	// ===============================PLAY DEV CARD
	// CONTROLLS=====================
	@Override
	public void startPlayCard() {
		//
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		Player player = ClientModel.getSingleton().getPlayers()[playerIndex];

		int monopolyCntNew = player.getNewDevCards().getMonopoly();
		int monopolyCntOld = player.getOldDevCards().getMonopoly();
		setCard(DevCardType.MONOPOLY, monopolyCntNew, monopolyCntOld);

		int yOPCntNew = player.getNewDevCards().getYearOfPlenty();
		int yOPCntOld = player.getOldDevCards().getYearOfPlenty();
		setCard(DevCardType.YEAR_OF_PLENTY, yOPCntNew, yOPCntOld);

		int soldierCntNew = player.getNewDevCards().getSoldier();
		int soldierCntOld = player.getOldDevCards().getSoldier();
		setCard(DevCardType.SOLDIER, soldierCntNew, soldierCntOld);

		int monumentCntNew = player.getNewDevCards().getMonument();
		int monumentCntOld = player.getOldDevCards().getMonument();
		setCard(DevCardType.MONUMENT, monumentCntNew, monumentCntOld);

		int buildRoadCntNew = player.getNewDevCards().getRoadBuilding();
		int buildRoadCntOld = player.getOldDevCards().getRoadBuilding();
		setCard(DevCardType.ROAD_BUILD, buildRoadCntNew, buildRoadCntOld);

		getPlayCardView().showModal();
	}

	private void setCard(DevCardType type, int cardCountNew, int cardCountOld) {
		getPlayCardView().setCardAmount(type, cardCountNew + cardCountOld);
		getPlayCardView().setCardEnabled(type, cardCountOld > 0);
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		if (ClientModel.getSingleton().getPlayers()[playerIndex]
				.hasPlayedDevCard() && type != DevCardType.MONUMENT) {
			getPlayCardView().setCardEnabled(type, false);
		}
	}

	@Override
	public void cancelPlayCard() {

		getPlayCardView().closeModal();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		// closeModal called just before this
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		String resourceString = resource.toString();
		// if(resourceString.equals("")) = handle that bad boy here
		if (modelController.canPlayMonopolyCard(playerIndex)) {
			try {
				serverProxy.playMonopolyCard(new PlayMonopolyParams(
						resourceString, playerIndex));
			} catch (ServerResponseException e) {
				JOptionPane.showMessageDialog(null, SERVER_ERROR,
						"Server Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, NO_CAN_DO, "No can do - devCardctrl monopoly",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void playMonumentCard() {
		// closeModal called just before this
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		if (modelController.canPlayMonumentCard(playerIndex)) {
			try {
				serverProxy.playMonument(new PlayMonumentParams(playerIndex));
			} catch (ServerResponseException e) {
				JOptionPane.showMessageDialog(null, SERVER_ERROR,
						"Server Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"Totes sry. Ye' can't play that yet!", "No can do - devCardctrl monument",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void playRoadBuildCard() {
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		if (modelController.canPlayRoadBuildingCard(playerIndex)) {
			roadAction.execute();
		} else {
			JOptionPane.showMessageDialog(null,
					"Totes sry. Ye' can't play that yet!", "No can do - devCardctrl road",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void playSoldierCard() {
		soldierAction.execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1,
			ResourceType resource2) {
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		ResourceList resources = new ResourceList(0, 0, 0, 0, 0);

		String res1Str = resource1.toString();
		String res2Str = resource2.toString();
		// if(res1Str.equals("")||res2Str.equals(""))
		// possibly handle error
		Resource res1 = new Resource(res1Str);
		Resource res2 = new Resource(res2Str);

		addResource(resource1, resources);
		addResource(resource2, resources);
		if (modelController.canPlayYearOfPlentyCard(playerIndex, resources)) {
			try {
				serverProxy.playYearOfPlentyCard(new YearOfPlentyParams(
						playerIndex, res1.getName(), res2.getName()));
			} catch (ServerResponseException e) {
				JOptionPane.showMessageDialog(null, SERVER_ERROR,
						"Server Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, NO_CAN_DO, "No can do - devCardctrl year",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		// check if we have enough victory points + monuments to win
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		Player player = ClientModel.getSingleton().getPlayers()[playerIndex];
		int victoryPoints = ClientModel.getSingleton().getPlayers()[playerIndex]
				.getVictoryPoints() + player.getOldDevCards().getMonument();
		if (victoryPoints >= 10
				&& modelController.canPlayMonumentCard(playerIndex)) {
			playMonumentCard();
		}

	}

	// ==================================================================================
	// ==============================PRIVATE HELPER
	// FUNCTIONS============================
	// ==================================================================================

	private void addResource(ResourceType resource, ResourceList list) {
		switch (resource) {
		case WOOD:
			list.setWood(list.getWood() + 1);
			return;
		case SHEEP:
			list.setSheep(list.getSheep() + 1);
			return;
		case ORE:
			list.setOre(list.getOre() + 1);
			return;
		case BRICK:
			list.setBrick(list.getBrick() + 1);
			return;
		case WHEAT:
			list.setWheat(list.getWheat() + 1);
			return;
		default:
			return;
		}
	}

}
