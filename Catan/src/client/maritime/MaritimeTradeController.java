package client.maritime;

import java.util.*;

import shared.definitions.*;
import client.base.*;
import client.data.UserPlayerInfo;
import client.model.ClientModel;
import client.model.ClientModelController;
import client.model.ResourceList;

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

	private boolean tradeValid(ResourceType resource, int resourceCount) {
		if (resourceCount >= 4
				|| (modelController.playerOnNormalPort(playerIndex) && resourceCount >= 3)
				|| (modelController.playerOnResourcePort(playerIndex, resource) && resourceCount >= 2)) {

			return true;
		} else {
			return false;
		}
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
		ResourceType[] enabledResourcesArray = new ResourceType[giveResourcesList.size()];
		for (int i = 0; i < giveResourcesList.size(); i++) {
			enabledResourcesArray[i] = giveResourcesList.get(i);
		}
		
		System.out.println("GiveResourceList.size = " + giveResourcesList.size());

		return enabledResourcesArray;
	}

	private void incrementResource(ResourceType resource, int amt) {

		playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		ResourceList resources = ClientModel.getSingleton().getPlayers()[playerIndex]
				.getResources();
		int brick = resources.getBrick();
		int ore = resources.getOre();
		int sheep = resources.getSheep();
		int wheat = resources.getWheat();
		int wood = resources.getWood();

		switch (resource) {

		case BRICK:
			resources.setBrick(brick - ratio);
			break;

		case ORE:
			resources.setOre(ore - ratio);
			break;

		case SHEEP:
			resources.setSheep(sheep - ratio);
			break;

		case WHEAT:
			resources.setWheat(wheat - ratio);
			break;

		case WOOD:
			resources.setWood(wood - ratio);
			break;

		}

	}

	@Override
	public void startTrade() {

		getTradeOverlay().showModal();
		getTradeOverlay().reset();
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().setCancelEnabled(true);

		// show tradeable resources
		getTradeOverlay().showGiveOptions(getValidResources());

	}

	@Override
	public void makeTrade() {

		// change resource amounts in model appropriately
		incrementResource(giveResource, -ratio);
		incrementResource(getResource, 1);

		getTradeOverlay().closeModal();
	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void setGetResource(ResourceType resource) {
		getResource = resource;
		getTradeOverlay().selectGetOption(resource, 1);

		getTradeOverlay().setTradeEnabled(true);
	}

	@Override
	public void setGiveResource(ResourceType resource) {
		giveResource = resource;

		if (modelController.playerOnResourcePort(playerIndex, resource)) {
			ratio = 2;
		} else if (modelController.playerOnNormalPort(playerIndex)) {
			ratio = 3;
		} else {
			ratio = 4;
		}
		getTradeOverlay().selectGiveOption(resource, ratio);

		// show get options, with everything enabled
		ResourceType[] enabledResources = new ResourceType[5];
		enabledResources[1] = ResourceType.BRICK;
		enabledResources[2] = ResourceType.ORE;
		enabledResources[3] = ResourceType.SHEEP;
		enabledResources[4] = ResourceType.WHEAT;
		enabledResources[5] = ResourceType.WOOD;
		getTradeOverlay().showGetOptions(enabledResources);
	}

	@Override
	public void unsetGetValue() {
		getResource = null;

		// show get options, with everything enabled
		ResourceType[] enabledResources = new ResourceType[5];
		enabledResources[1] = ResourceType.BRICK;
		enabledResources[2] = ResourceType.ORE;
		enabledResources[3] = ResourceType.SHEEP;
		enabledResources[4] = ResourceType.WHEAT;
		enabledResources[5] = ResourceType.WOOD;
		getTradeOverlay().showGetOptions(enabledResources);
		getTradeOverlay().setTradeEnabled(false);
	}

	@Override
	public void unsetGiveValue() {
		giveResource = null;

		getTradeOverlay().hideGetOptions();
		getTradeOverlay().showGiveOptions(getValidResources());
		getTradeOverlay().setTradeEnabled(false);
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
