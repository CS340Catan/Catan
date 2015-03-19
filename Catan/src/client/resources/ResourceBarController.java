package client.resources;

import java.util.*;

import shared.model.ResourceList;
import client.base.*;
import client.data.UserPlayerInfo;
import client.model.ClientModel;
import client.model.ClientModelFacade;

/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements
		IResourceBarController, Observer {

	private Map<ResourceBarElement, IAction> elementActions;

	public ResourceBarController(IResourceBarView view) {

		super(view);

		elementActions = new HashMap<ResourceBarElement, IAction>();
		ClientModel.getNotifier().addObserver(this);
	}

	@Override
	public IResourceBarView getView() {
		return (IResourceBarView) super.getView();
	}

	/**
	 * Sets the action to be executed when the specified resource bar element is
	 * clicked by the user
	 * 
	 * @param element
	 *            The resource bar element with which the action is associated
	 * @param action
	 *            The action to be executed
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {

		elementActions.put(element, action);
	}

	@Override
	public void buildRoad() {
		executeElementAction(ResourceBarElement.ROAD);
	}

	@Override
	public void buildSettlement() {
		executeElementAction(ResourceBarElement.SETTLEMENT);
	}

	@Override
	public void buildCity() {
		executeElementAction(ResourceBarElement.CITY);
	}

	@Override
	public void buyCard() {
		executeElementAction(ResourceBarElement.BUY_CARD);
	}

	@Override
	public void playCard() {
		executeElementAction(ResourceBarElement.PLAY_CARD);
	}

	private void executeElementAction(ResourceBarElement element) {

		if (elementActions.containsKey(element)) {

			IAction action = elementActions.get(element);
			action.execute();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();

		/*
		 * Set the amount of resources currently held by the player.
		 */
		ResourceList playerResources = ClientModel.getSingleton().getPlayers()[playerIndex]
				.getResources();
		int ore = playerResources.getOre();
		int wood = playerResources.getWood();
		int sheep = playerResources.getSheep();
		int brick = playerResources.getBrick();
		int wheat = playerResources.getWheat();

		this.getView().setElementAmount(ResourceBarElement.ORE, ore);
		this.getView().setElementAmount(ResourceBarElement.WOOD, wood);
		this.getView().setElementAmount(ResourceBarElement.SHEEP, sheep);
		this.getView().setElementAmount(ResourceBarElement.BRICK, brick);
		this.getView().setElementAmount(ResourceBarElement.WHEAT, wheat);

		/*
		 * Set the amount of objects (roads, settlements, etc.) currently
		 * available to be placed by the user.
		 */
		int roadCnt = ClientModel.getSingleton().getPlayers()[playerIndex]
				.getRoads();
		this.getView().setElementAmount(ResourceBarElement.ROAD, roadCnt);

		int settlementCnt = ClientModel.getSingleton().getPlayers()[playerIndex]
				.getSettlements();
		this.getView().setElementAmount(ResourceBarElement.SETTLEMENT,
				settlementCnt);

		int cityCnt = ClientModel.getSingleton().getPlayers()[playerIndex]
				.getCities();
		this.getView().setElementAmount(ResourceBarElement.CITY, cityCnt);
		int soldierCount = ClientModel.getSingleton().getPlayers()[playerIndex]
				.getSoldiers();
		this.getView().setElementAmount(ResourceBarElement.SOLDIERS,
				soldierCount);

		/*
		 * Enable or disable the ability to buy or play a development card.
		 */
		ClientModelFacade modelController = new ClientModelFacade();
		this.getView().setElementEnabled(ResourceBarElement.BUY_CARD, false);
		if (modelController.canBuyDevCard(playerIndex))
			this.getView().setElementEnabled(ResourceBarElement.BUY_CARD, true);

		this.getView().setElementEnabled(ResourceBarElement.PLAY_CARD, false);
		if (modelController.isPlayerTurn(playerIndex))
			this.getView()
					.setElementEnabled(ResourceBarElement.PLAY_CARD, true);

		/*
		 * Enable or disable the ability to buy a road, settlement, or city
		 * card. This does not confirm whether or not a road, settlment or city
		 * can be placed, but rather whether the user has adequate resources,
		 * current turn, etc. to play the objects.
		 */
		this.getView().setElementEnabled(ResourceBarElement.ROAD, false);
		if (modelController.canBuyRoad())
			this.getView().setElementEnabled(ResourceBarElement.ROAD, true);

		this.getView().setElementEnabled(ResourceBarElement.SETTLEMENT, false);
		if (modelController.canBuySettlement())
			this.getView().setElementEnabled(ResourceBarElement.SETTLEMENT,
					true);

		this.getView().setElementEnabled(ResourceBarElement.CITY, false);
		if (modelController.canBuyCity())
			this.getView().setElementEnabled(ResourceBarElement.CITY, true);
	}

}
