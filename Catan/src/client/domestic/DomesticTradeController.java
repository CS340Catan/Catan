package client.domestic;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import shared.communication.AcceptTradeParams;
import shared.communication.TradeOfferParams;
import shared.definitions.*;
import shared.utils.ServerResponseException;
import client.base.*;
import client.communicator.ServerProxy;
import client.data.PlayerInfo;
import client.misc.*;
import client.model.ClientModel;
import client.model.ClientModelController;
import client.model.Player;
import client.model.ResourceList;
import client.model.TradeOffer;

/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements
		IDomesticTradeController, Observer {

	private ClientModelController clientModelController;
	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;
	private int brickAmt, oreAmt, sheepAmt, wheatAmt, woodAmt;
	private TradeOfferParams tradeOfferParams;
	private int receiverPlayerIndex;
	private Set<ResourceType> getResources = new HashSet<>();
	private Set<ResourceType> giveResources = new HashSet<>();

	/**
	 * DomesticTradeController constructor
	 * 
	 * @param tradeView
	 *            Domestic trade view (i.e., view that contains the
	 *            "Domestic Trade" button)
	 * @param tradeOverlay
	 *            Domestic trade overlay (i.e., view that lets the user propose
	 *            a domestic trade)
	 * @param waitOverlay
	 *            Wait overlay used to notify the user they are waiting for
	 *            another player to accept a trade
	 * @param acceptOverlay
	 *            Accept trade overlay which lets the user accept or reject a
	 *            proposed trade
	 */
	public DomesticTradeController(IDomesticTradeView tradeView,
			IDomesticTradeOverlay tradeOverlay, IWaitView waitOverlay,
			IAcceptTradeOverlay acceptOverlay) {

		super(tradeView);

		setTradeOverlay(tradeOverlay);
		setWaitOverlay(waitOverlay);
		setAcceptOverlay(acceptOverlay);
		ClientModel.getSingleton().addObserver(this);
		
		
	}

	public IDomesticTradeView getTradeView() {

		return (IDomesticTradeView) super.getView();
	}

	public IDomesticTradeOverlay getTradeOverlay() {
		return tradeOverlay;
	}

	public void setTradeOverlay(IDomesticTradeOverlay tradeOverlay) {
		this.tradeOverlay = tradeOverlay;
	}

	public IWaitView getWaitOverlay() {
		return waitOverlay;
	}

	public void setWaitOverlay(IWaitView waitView) {
		this.waitOverlay = waitView;
	}

	public IAcceptTradeOverlay getAcceptOverlay() {
		return acceptOverlay;
	}

	public void setAcceptOverlay(IAcceptTradeOverlay acceptOverlay) {
		this.acceptOverlay = acceptOverlay;
	}
	
	private void resetAmts() {
		
		brickAmt = 0;
		oreAmt = 0;
		sheepAmt = 0;
		wheatAmt = 0;
		woodAmt = 0;
		
	}
	
	private int resourceAmt(ResourceType resource) {
			
		switch(resource) {
		
		case BRICK:
			return brickAmt;
		
		case ORE:
			return oreAmt;
			
		case SHEEP: 
			return sheepAmt;
			
		case WHEAT:
			return wheatAmt;
			
		case WOOD:
			return woodAmt;
			
		default:
			return 0;
		
		}
	}
	
	private void incrementResourceAmt(ResourceType resource, int increment) {
		
		switch(resource) {
		
		case BRICK:
			brickAmt += increment;
			break;
		
		case ORE:
			oreAmt += increment;
			break;
			
		case SHEEP: 
			sheepAmt += increment;
			break;
			
		case WHEAT:
			wheatAmt += increment;
			break;
			
		case WOOD:
			woodAmt += increment;
			break;
			
		default:
			break;
		
		}
		
	}
	
	private boolean canIncrease(ResourceType resource) {
		
		int playerIndex = PlayerInfo.getSingleton().getPlayerIndex();
		ResourceList resources = ClientModel.getSingleton().getPlayers()[playerIndex].getResources();
		int brick = resources.getBrick();
		int ore = resources.getOre();
		int sheep = resources.getSheep();
		int wheat = resources.getWheat();
		int wood = resources.getWood();
		
		//if the amthave > amtset, then can increase
		switch(resource) {
		
		case BRICK:
			return (brick > brickAmt);
		
		case ORE:
			return (ore > oreAmt);
			
		case SHEEP: 
			return (sheep > sheepAmt);
			
		case WHEAT:
			return (wheat > wheatAmt);
			
		case WOOD:
			return (wood > woodAmt);
			
		default:
			return false;
		
		}
		
	}
	
	private boolean canDecrease(ResourceType resource) {
		return (resourceAmt(resource) > 0);
	}
	
	private ResourceList createResourceList() {
		
		ResourceList list;
		//create resourceList
		int brick = 0;
		int ore = 0;
		int sheep = 0;
		int wheat = 0;
		int wood = 0;
		
		if(giveResources.contains(ResourceType.BRICK)) {
			brick = brickAmt;
		}else if(getResources.contains(ResourceType.BRICK)) {
			brick = -brickAmt;
		}
		if(giveResources.contains(ResourceType.ORE)) {
			ore = oreAmt;
		}else if(getResources.contains(ResourceType.ORE)) {
			ore = -oreAmt;
		}
		if(giveResources.contains(ResourceType.SHEEP)) {
			sheep = sheepAmt;
		}else if(getResources.contains(ResourceType.SHEEP)) {
			sheep = -sheepAmt;
		}
		if(giveResources.contains(ResourceType.WHEAT)) {
			wheat = wheatAmt;
		}else if(getResources.contains(ResourceType.WHEAT)) {
			wheat = -wheatAmt;
		}
		if(giveResources.contains(ResourceType.WOOD)) {
			wood = woodAmt;
		}else if(getResources.contains(ResourceType.WOOD)) {
			wood = -woodAmt;
		}
		
		list = new ResourceList(brick, ore, sheep, wheat, wood);
		
		return list;
	}

	@Override
	public void startTrade() {

		resetAmts();
		
		//get players, and convert them all from Player to PlayerInfo
		Player[] players = ClientModel.getSingleton().getPlayers();
		PlayerInfo[] playerInfos = new PlayerInfo[players.length];
		for(int i = 0; i < players.length; i++) {
			playerInfos[i] = players[i].getPlayerInfo();
		}
		
		//set the tradeable players with their corresponding info
		getTradeOverlay().setPlayers(playerInfos);
		
		getTradeOverlay().showModal();
		
		//enable players
		getTradeOverlay().setPlayerSelectionEnabled(true);
		getTradeOverlay().setCancelEnabled(true);
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().setStateMessage("Choose Resources to Trade");
		
	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {
		
		//check resource > 0, then decrease
		if(canDecrease(resource)) {
			
			incrementResourceAmt(resource, -1);
			getTradeOverlay().setResourceAmount(resource, "" + resourceAmt(resource));
		}
		
		//disable button if counter is at 0
		getTradeOverlay().setResourceAmountChangeEnabled(resource, canIncrease(resource), canDecrease(resource));
	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {
		
		//check resource < amt user has, then increase
		if(canIncrease(resource)) {
			
			incrementResourceAmt(resource, 1);
			getTradeOverlay().setResourceAmount(resource, "" + resourceAmt(resource));
		}
		
		//disable button if counter is too high
		getTradeOverlay().setResourceAmountChangeEnabled(resource, canIncrease(resource), canDecrease(resource));
		
	}

	@Override
	public void sendTradeOffer() {
		
		
		getTradeOverlay().closeModal();
		getWaitOverlay().showModal();
		

		ResourceList resourceList = createResourceList();
		
		//fill tradeOffer
		int playerIndex = PlayerInfo.getSingleton().getPlayerIndex();
		tradeOfferParams = new TradeOfferParams(playerIndex, resourceList, receiverPlayerIndex);
		
		ServerProxy server = ServerProxy.getSingleton();
		try {
			server.offerTrade(tradeOfferParams);
		} catch (ServerResponseException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		receiverPlayerIndex = playerIndex;
	}

	@Override
	public void setResourceToReceive(ResourceType resource) {
		getResources.add(resource);
	}

	@Override
	public void setResourceToSend(ResourceType resource) {
		giveResources.add(resource);
	}

	@Override
	public void unsetResource(ResourceType resource) {
		for(ResourceType r: getResources) {
			if(r.equals(resource)) {
				getResources.remove(r);
			}
		}
		
		for(ResourceType r: giveResources) {
			if(r.equals(resource)) {
				giveResources.remove(r);
			}
		}
	}

	@Override
	public void cancelTrade() {
		getTradeOverlay().closeModal();
		resetAmts();
	}

	@Override
	public void acceptTrade(boolean willAccept) {
		getAcceptOverlay().closeModal();
		AcceptTradeParams acceptTradeParams = new AcceptTradeParams(PlayerInfo.getSingleton().getPlayerIndex(),willAccept);
		try {
			ServerProxy.getSingleton().acceptTrade(acceptTradeParams);
		} catch (ServerResponseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		clientModelController = new ClientModelController();
		int playerIndex = PlayerInfo.getSingleton().getPlayerIndex();
		TradeOffer tradeOffer = ClientModel.getSingleton().getTradeOffer();
		if (tradeOffer != null) {
			if (tradeOffer.getReceiver() == playerIndex) {
				acceptOverlay.showModal();
				ResourceList resourceList = tradeOffer.getResourceList();
				ResourceList canTradeList = new ResourceList(0,0,0,0,0);
				
				if (resourceList.getBrick() > 0) {
					acceptOverlay.addGetResource(ResourceType.BRICK, resourceList.getBrick());
				} else if (resourceList.getBrick() < 0) {
					acceptOverlay.addGiveResource(ResourceType.BRICK, resourceList.getBrick());
					canTradeList.setBrick(resourceList.getBrick());
				}
				
				if (resourceList.getOre() > 0) {
					acceptOverlay.addGetResource(ResourceType.ORE, resourceList.getOre());
				} else if (resourceList.getOre() < 0) {
					acceptOverlay.addGiveResource(ResourceType.ORE, resourceList.getOre());
					canTradeList.setOre(resourceList.getOre());
				}
				
				if (resourceList.getSheep() > 0) {
					acceptOverlay.addGetResource(ResourceType.SHEEP, resourceList.getSheep());
				} else if (resourceList.getSheep() < 0) {
					acceptOverlay.addGiveResource(ResourceType.SHEEP, resourceList.getSheep());
					canTradeList.setSheep(resourceList.getSheep());
				}
				
				if (resourceList.getWheat() > 0) {
					acceptOverlay.addGetResource(ResourceType.WHEAT, resourceList.getWheat());
				} else if (resourceList.getWheat() < 0) {
					acceptOverlay.addGiveResource(ResourceType.WHEAT, resourceList.getWheat());
					canTradeList.setWheat(resourceList.getSheep());
				}
				
				if (resourceList.getWood() > 0) {
					acceptOverlay.addGetResource(ResourceType.WOOD, resourceList.getWood());
				} else if (resourceList.getWood() < 0) {
					acceptOverlay.addGiveResource(ResourceType.WOOD, resourceList.getWood());
					canTradeList.setWheat(resourceList.getSheep());
				}
				
				int offeringIndex = tradeOffer.getSender();
				acceptOverlay.setPlayerName(clientModelController.getClientModel().getPlayers()[offeringIndex].getName());
				acceptOverlay.setAcceptEnabled(clientModelController.canAcceptTrade(playerIndex, canTradeList));
			}
		}
	}
}
