package client.maritime;

import java.util.*;

import shared.communication.MaritimeTradeParams;
import shared.definitions.*;
import shared.model.ClientModel;
import shared.model.ClientModelController;
import shared.model.ResourceList;
import shared.utils.ServerResponseException;
import client.base.*;
import client.communicator.ServerProxy;
import client.data.UserPlayerInfo;

/**
 * Implementation for the maritime trade controller
 */
public class MaritimeTradeController extends Controller implements
		IMaritimeTradeController, Observer {

	private IMaritimeTradeOverlay tradeOverlay;
	private ResourceType getResource, giveResource;
	private ClientModelController modelController;
	private int playerIndex, ratio;

	public MaritimeTradeController(IMaritimeTradeView tradeView,
			IMaritimeTradeOverlay tradeOverlay) {

		super(tradeView);

		setTradeOverlay(tradeOverlay);
		ClientModel.getNotifier().addObserver(this);
	}

	@Override
	public void startTrade() {
		getResource = null;
		giveResource = null;
		ratio = 0;

		getTradeOverlay().showModal();
		getTradeOverlay().reset();
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().setCancelEnabled(true);
		getTradeOverlay().showGiveOptions(getValidResources());
		getTradeOverlay().setStateMessage("Choose what to give");
	}

	@Override
	public void makeTrade() {
		try {
			MaritimeTradeParams maritimeTradeParam = new MaritimeTradeParams(
					playerIndex, ratio, giveResource.toString(),
					getResource.toString());
			ServerProxy.getSingleton().maritimeTrade(maritimeTradeParam);
		} catch (ServerResponseException e) {
			e.printStackTrace();
		}

		getTradeOverlay().closeModal();
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		/*
		 * Set the ratio dependent upon whether or the trade is being made with
		 * a resource port, normal port, or w/o a port.
		 */
		if (modelController.canMaritimeTrade(playerIndex, resource, 2)) {
			ratio = 2;
		} else if (modelController.canMaritimeTrade(playerIndex, resource, 3)) {
			ratio = 3;
		} else if (modelController.canMaritimeTrade(playerIndex, resource, 4)) {
			ratio = 4;
		}
		getTradeOverlay().selectGiveOption(resource, ratio);

		/*
		 * Set the giveResource.
		 */
		this.giveResource = resource;

		// show get options, with everything enabled
		ResourceType[] enabledResources = new ResourceType[5];
		enabledResources[0] = ResourceType.BRICK;
		enabledResources[1] = ResourceType.ORE;
		enabledResources[2] = ResourceType.SHEEP;
		enabledResources[3] = ResourceType.WHEAT;
		enabledResources[4] = ResourceType.WOOD;
		getTradeOverlay().showGetOptions(enabledResources);
		getTradeOverlay().setStateMessage("Choose what to get");
	}

	@Override
	public void setGetResource(ResourceType resource) {
		getResource = resource;

		getTradeOverlay().selectGetOption(resource, 1);
		if (this.giveResource.toString().equals(this.getResource.toString()))
			getTradeOverlay().setStateMessage("Trade...(?)");
		else
			getTradeOverlay().setStateMessage("Trade!");
		getTradeOverlay().setTradeEnabled(true);
	}

	@Override
	public void unsetGiveValue() {
		giveResource = null;

		getTradeOverlay().hideGetOptions();
		getTradeOverlay().showGiveOptions(getValidResources());
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().setStateMessage("Choose what to give");
	}

	@Override
	public void unsetGetValue() {
		getResource = null;

		// show get options, with everything enabled
		ResourceType[] enabledResources = new ResourceType[5];
		enabledResources[0] = ResourceType.BRICK;
		enabledResources[1] = ResourceType.ORE;
		enabledResources[2] = ResourceType.SHEEP;
		enabledResources[3] = ResourceType.WHEAT;
		enabledResources[4] = ResourceType.WOOD;
		getTradeOverlay().showGetOptions(enabledResources);
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().setStateMessage("Choose what to get");
	}

	private ResourceType[] getValidResources() {
		/*
		 * Grab the clientModelController, current player index, and current
		 * player resources (stored as integers) list to be compared in later
		 * parts of the code.
		 */
		modelController = new ClientModelController();
		playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();

		ResourceList resources = ClientModel.getSingleton().getPlayers()[playerIndex]
				.getResources();
		int brick = resources.getBrick();
		int ore = resources.getOre();
		int sheep = resources.getSheep();
		int wheat = resources.getWheat();
		int wood = resources.getWood();

		List<ResourceType> giveResourcesList = new ArrayList<>();

		// if have 4+ of any resource, add it, or 3+ if have a port, or 2+ if
		// have special resource port
		if (tradeValid(ResourceType.BRICK, brick)) {
			giveResourcesList.add(ResourceType.BRICK);
		}
		if (tradeValid(ResourceType.ORE, ore)) {
			giveResourcesList.add(ResourceType.ORE);
		}
		if (tradeValid(ResourceType.SHEEP, sheep)) {
			giveResourcesList.add(ResourceType.SHEEP);
		}
		if (tradeValid(ResourceType.WHEAT, wheat)) {
			giveResourcesList.add(ResourceType.WHEAT);
		}
		if (tradeValid(ResourceType.WOOD, wood)) {
			giveResourcesList.add(ResourceType.WOOD);
		}

		// put resources into an array, the proper format for the following
		// function
		ResourceType[] enabledResourcesArray = new ResourceType[giveResourcesList
				.size()];
		for (int i = 0; i < giveResourcesList.size(); i++) {
			enabledResourcesArray[i] = giveResourcesList.get(i);
		}

		return enabledResourcesArray;
	}

	private boolean tradeValid(ResourceType resource, int resourceCount) {
		ClientModelController controller = new ClientModelController();

		if (controller.canMaritimeTrade(playerIndex, resource, 2)
				|| controller.canMaritimeTrade(playerIndex, resource, 3)
				|| controller.canMaritimeTrade(playerIndex, resource, 4)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void update(Observable o, Object arg) {

		playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		modelController = new ClientModelController();

		// if it's my turn, enable view, otherwise disable
		if (modelController.isPlayerTurn(playerIndex)) {
			getTradeView().enableMaritimeTrade(true);
		} else {
			getTradeView().enableMaritimeTrade(false);
		}
	}

	public IMaritimeTradeView getTradeView() {

		return (IMaritimeTradeView) super.getView();
	}

	public IMaritimeTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IMaritimeTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

}
