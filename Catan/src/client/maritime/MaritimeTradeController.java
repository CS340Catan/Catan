package client.maritime;

import java.util.*;

import shared.definitions.*;
import shared.locations.EdgeLocation;
import client.base.*;
import client.data.PlayerInfo;
import client.model.ClientModel;
import client.model.ClientModelController;
import client.model.Player;
import client.model.Port;
import client.model.ResourceList;
import client.model.Road;

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
		ClientModel.getSingleton().addObserver(this);
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
	
	private boolean tradeValid(ResourceType resource, int amt) {
		if(amt >= 4 || (modelController.playerOnNormalPort(playerIndex) && amt >= 3) 
				|| (modelController.playerOnResourcePort(playerIndex, resource) && amt >= 2)) {
			
			return true;
		}
		else {
			return false;
		}
	}
	
	private ResourceType[] getValidResources() {
		
		//set up overlay, show or hide buttons based on player resources
		modelController = new ClientModelController();
		playerIndex = PlayerInfo.getSingleton().getPlayerIndex();
		ResourceList resources = ClientModel.getSingleton().getPlayers()[playerIndex].getResources();
		int brick = resources.getBrick();
		int ore = resources.getOre();
		int sheep = resources.getSheep();
		int wheat = resources.getWheat();
		int wood = resources.getWood();
		
		List<ResourceType> giveResources = new ArrayList<>();
		
		//if have 4+ of any resource, add it, or 3+ if have a port, or 2+ if have special resource port
		if(tradeValid(ResourceType.BRICK, brick)) {
			giveResources.add(ResourceType.BRICK);
		}
		if(tradeValid(ResourceType.ORE, ore)) {
			giveResources.add(ResourceType.ORE);
		}
		if(tradeValid(ResourceType.SHEEP, sheep)) {
			giveResources.add(ResourceType.SHEEP);
		}
		if(tradeValid(ResourceType.WHEAT, wheat)) {
			giveResources.add(ResourceType.WHEAT);
		}
		if(tradeValid(ResourceType.WOOD, wood)) {
			giveResources.add(ResourceType.WOOD);
		}

		//put resources into an array, the proper format for the following function
		ResourceType[] enabledResources = new ResourceType[giveResources.size()];
		for(int i = 0; i < giveResources.size(); i++) {
			enabledResources[i] = giveResources.get(i);
		}
		
		return enabledResources;
	}
	
	private void incrementResource(ResourceType resource, int amt) {
		
		ResourceList resources = ClientModel.getSingleton().getPlayers()[playerIndex].getResources();
		int brick = resources.getBrick();
		int ore = resources.getOre();
		int sheep = resources.getSheep();
		int wheat = resources.getWheat();
		int wood = resources.getWood();
		
		switch(resource) {
		
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
		//getTradeOverlay().setTradeEnabled(false);
		//getTradeOverlay().setCancelEnabled(true);

		//show tradeable resources
		getTradeOverlay().showGiveOptions(getValidResources());
		
	}

	@Override
	public void makeTrade() {
		
		//change resource amounts in model appropriately
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
		
		if(modelController.playerOnResourcePort(playerIndex, resource)) {
			ratio = 2;
		}
		else if (modelController.playerOnNormalPort(playerIndex)) {
			ratio = 3;
		}
		else {
			ratio = 4;
		}
		getTradeOverlay().selectGiveOption(resource, ratio);
		
		//show get options, with everything enabled
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
		
		//show get options, with everything enabled
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
	
		playerIndex = PlayerInfo.getSingleton().getPlayerIndex();
		modelController = new ClientModelController();
		
		//if it's my turn, enable view, otherwise disable
		if(modelController.isPlayerTurn(playerIndex)) {
			getTradeView().enableMaritimeTrade(true);
		}
		else {
			getTradeView().enableMaritimeTrade(false);
		}
	}

}
