package client.resources;

import java.util.*;

import client.base.*;
import client.data.PlayerInfo;
import client.data.UserPlayerInfo;
import client.model.ClientModel;

/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements
		IResourceBarController, Observer {

	private Map<ResourceBarElement, IAction> elementActions;

	public ResourceBarController(IResourceBarView view) {

		super(view);

		elementActions = new HashMap<ResourceBarElement, IAction>();
		ClientModel.getSingleton().addObserver(this);
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
		int ore = ClientModel.getSingleton().getPlayers()[playerIndex].getResources().getOre();
		int wood = ClientModel.getSingleton().getPlayers()[playerIndex].getResources().getWood();
		int sheep = ClientModel.getSingleton().getPlayers()[playerIndex].getResources().getSheep();
		int brick = ClientModel.getSingleton().getPlayers()[playerIndex].getResources().getBrick();
		int wheat = ClientModel.getSingleton().getPlayers()[playerIndex].getResources().getWheat();
		this.getView().setElementAmount(ResourceBarElement.ORE, ore);
		this.getView().setElementAmount(ResourceBarElement.WOOD, wood);
		this.getView().setElementAmount(ResourceBarElement.SHEEP, sheep);
		this.getView().setElementAmount(ResourceBarElement.BRICK, brick);
		this.getView().setElementAmount(ResourceBarElement.WHEAT, wheat);
		
	}

}
