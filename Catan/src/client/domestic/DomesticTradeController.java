package client.domestic;

import java.util.*;

import shared.communication.AcceptTradeParams;
import shared.communication.TradeOfferParams;
import shared.data.PlayerInfo;
import shared.definitions.*;
import shared.model.Player;
import shared.model.ResourceList;
import shared.model.TradeOffer;
import shared.utils.ServerResponseException;
import client.base.*;
import client.communicator.ServerProxy;
import client.data.UserPlayerInfo;
import client.misc.*;
import client.model.ClientModel;
import client.model.ClientModelFacade;

/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements
		IDomesticTradeController, Observer {

	private ClientModelFacade clientModelController;
	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;
	private int brickAmt, oreAmt, sheepAmt, wheatAmt, woodAmt;
	private TradeOfferParams tradeOfferParams;
	private int receiverPlayerIndex;
	private Set<ResourceType> getResources = new HashSet<>();
	private Set<ResourceType> giveResources = new HashSet<>();
	private boolean playerSelected = false;

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
		ClientModel.getNotifier().addObserver(this);
		clientModelController = new ClientModelFacade();
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

	private void setTradeButton() {

		boolean valid = (receiverPlayerIndex != -1)
				&& containsResources(giveResources)
				&& containsResources(getResources) && playerSelected;
		if (valid) {
			getTradeOverlay().setTradeEnabled(true);
			getTradeOverlay().setStateMessage("Trade");
		} else {
			getTradeOverlay().setTradeEnabled(false);
			getTradeOverlay().setStateMessage("Choose Resources To Trade");
		}

	}

	private boolean containsResources(Set<ResourceType> resources) {
		for (ResourceType r : resources) {
			if (resourceAmt(r) > 0) {
				return true;
			}
		}

		return false;
	}

	private void resetAmts() {

		brickAmt = 0;
		oreAmt = 0;
		sheepAmt = 0;
		wheatAmt = 0;
		woodAmt = 0;

	}

	private void resetResource(ResourceType resource) {

		switch (resource) {

		case BRICK:
			brickAmt = 0;
			break;

		case ORE:
			oreAmt = 0;
			break;

		case SHEEP:
			sheepAmt = 0;
			break;

		case WHEAT:
			wheatAmt = 0;
			break;

		case WOOD:
			woodAmt = 0;
			break;

		default:
			break;

		}
	}

	private int resourceAmt(ResourceType resource) {

		switch (resource) {

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

		switch (resource) {

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

		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		ResourceList resources = ClientModel.getSingleton().getPlayers()[playerIndex]
				.getResources();
		int brick = resources.getBrick();
		int ore = resources.getOre();
		int sheep = resources.getSheep();
		int wheat = resources.getWheat();
		int wood = resources.getWood();

		// if you're giving
		if (giveResources.contains(resource)) {
			// if the amthave > amtset, then can increase
			switch (resource) {

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
		} else { // if getting
			return true; // can always increase
		}

	}

	private boolean canDecrease(ResourceType resource) {
		return (resourceAmt(resource) > 0);
	}

	private ResourceList createResourceList() {

		ResourceList list;
		// create resourceList
		int brick = 0;
		int ore = 0;
		int sheep = 0;
		int wheat = 0;
		int wood = 0;

		if (giveResources.contains(ResourceType.BRICK)) {
			brick = brickAmt;
		} else if (getResources.contains(ResourceType.BRICK)) {
			brick = -brickAmt;
		}
		if (giveResources.contains(ResourceType.ORE)) {
			ore = oreAmt;
		} else if (getResources.contains(ResourceType.ORE)) {
			ore = -oreAmt;
		}
		if (giveResources.contains(ResourceType.SHEEP)) {
			sheep = sheepAmt;
		} else if (getResources.contains(ResourceType.SHEEP)) {
			sheep = -sheepAmt;
		}
		if (giveResources.contains(ResourceType.WHEAT)) {
			wheat = wheatAmt;
		} else if (getResources.contains(ResourceType.WHEAT)) {
			wheat = -wheatAmt;
		}
		if (giveResources.contains(ResourceType.WOOD)) {
			wood = woodAmt;
		} else if (getResources.contains(ResourceType.WOOD)) {
			wood = -woodAmt;
		}

		list = new ResourceList(brick, ore, sheep, wheat, wood);

		return list;
	}

	@Override
	public void startTrade() {

		getTradeOverlay().reset();
		resetAmts();

		// get players, and convert them all from Player to PlayerInfo
		Player[] players = ClientModel.getSingleton().getPlayers();
		List<PlayerInfo> playerInfos = new ArrayList<>();
		for (int i = 0; i < players.length; i++) {
			// don't add self
			if (players[i].getPlayerIndex() != UserPlayerInfo.getSingleton()
					.getPlayerIndex()) {
				playerInfos.add(players[i].getPlayerInfo());
			}
		}

		// convert to array
		PlayerInfo[] playerInfosArray = new PlayerInfo[playerInfos.size()];
		playerInfosArray = playerInfos.toArray(playerInfosArray);
		// set the tradeable players with their corresponding info
		getTradeOverlay().setPlayers(playerInfosArray);

		getTradeOverlay().showModal();

		// enable players
		getTradeOverlay().setPlayerSelectionEnabled(true);
		getTradeOverlay().setCancelEnabled(true);
		getTradeOverlay().setTradeEnabled(false);
		getTradeOverlay().setStateMessage("Choose Resources To Trade");

	}

	@Override
	public void decreaseResourceAmount(ResourceType resource) {

		// check resource > 0, then decrease
		if (canDecrease(resource)) {

			incrementResourceAmt(resource, -1);
			getTradeOverlay().setResourceAmount(resource,
					"" + resourceAmt(resource));
		}

		// disable button if counter is at 0
		getTradeOverlay().setResourceAmountChangeEnabled(resource,
				canIncrease(resource), canDecrease(resource));
		setTradeButton();
	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {

		// check resource < amt user has, then increase
		if (canIncrease(resource)) {

			incrementResourceAmt(resource, 1);
			getTradeOverlay().setResourceAmount(resource,
					"" + resourceAmt(resource));
		}

		// disable button if counter is too high
		getTradeOverlay().setResourceAmountChangeEnabled(resource,
				canIncrease(resource), canDecrease(resource));
		setTradeButton();
	}

	@Override
	public void sendTradeOffer() {

		getTradeOverlay().closeModal();
		getWaitOverlay().showModal();

		ResourceList resourceList = createResourceList();

		// fill tradeOffer
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		tradeOfferParams = new TradeOfferParams(playerIndex, resourceList,
				receiverPlayerIndex);

		try {
			if (this.clientModelController.canOfferTrade(playerIndex,
					resourceList)) {
				ServerProxy.getSingleton().offerTrade(tradeOfferParams);
			} else {
				MessageView invalidTradeMessageView = new MessageView();
				String outputStr = "You do not have the resources listed!";
				String title = "Invalid Trade Offer";

				invalidTradeMessageView.setTitle(title);
				invalidTradeMessageView.setMessage(outputStr);
				invalidTradeMessageView.setController(this);
				invalidTradeMessageView.showModal();
			}
		} catch (ServerResponseException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {
		if (playerIndex == -1) {
			playerSelected = false;
		} else {
			playerSelected = true;
		}
		receiverPlayerIndex = playerIndex;
		setTradeButton();

		// TESTING
		// sendTradeOffer();

	}

	@Override
	public void setResourceToReceive(ResourceType resource) {

		// reset the resource, and remove it from getresources if there
		resetResource(resource);
		unsetResource(resource);
		getResources.add(resource);

		// can always increase a receiving resource
		getTradeOverlay().setResourceAmount(resource,
				resourceAmt(resource) + "");
		getTradeOverlay().setResourceAmountChangeEnabled(resource, true,
				canDecrease(resource));

	}

	@Override
	public void setResourceToSend(ResourceType resource) {

		// reset the resource, and remove it from getresources if there
		resetResource(resource);
		unsetResource(resource);
		if (canIncrease(resource)) {
			giveResources.add(resource);
		}

		// enable increase/decrease buttons, and add to giveresources
		getTradeOverlay().setResourceAmount(resource,
				resourceAmt(resource) + "");
		getTradeOverlay().setResourceAmountChangeEnabled(resource,
				canIncrease(resource), canDecrease(resource));
	}

	@Override
	public void unsetResource(ResourceType resource) {

		getResources.remove(resource);
		giveResources.remove(resource);
		setTradeButton();

		/*
		 * unnecessary for(ResourceType r: getResources) {
		 * if(r.equals(resource)) { getResources.remove(r); } }
		 * 
		 * for(ResourceType r: giveResources) { if(r.equals(resource)) {
		 * giveResources.remove(r); } }
		 */
	}

	@Override
	public void cancelTrade() {
		getTradeOverlay().closeModal();
		resetAmts();
	}

	@Override
	public void acceptTrade(boolean willAccept) {
		getAcceptOverlay().closeModal();
		AcceptTradeParams acceptTradeParams = new AcceptTradeParams(
				UserPlayerInfo.getSingleton().getPlayerIndex(), willAccept);
		try {
			ServerProxy.getSingleton().acceptTrade(acceptTradeParams);
		} catch (ServerResponseException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		TradeOffer tradeOffer = ClientModel.getSingleton().getTradeOffer();

		// if it's my turn, enable view, otherwise disable
		if (clientModelController.isPlayerTurn(playerIndex)) {
			getTradeView().enableDomesticTrade(true);
			if (getWaitOverlay().isModalShowing()
					&& ClientModel.getSingleton().getTradeOffer() == null) {
				getWaitOverlay().closeModal();
			}
		} else {
			getTradeView().enableDomesticTrade(false);
		}

		/*
		 * If there is a tradeOffer currently stored within the ClientModel,
		 * check to see if the client is either the offer sender or receiver.
		 */
		if (tradeOffer != null) {
			int senderIndex = tradeOffer.getSender();
			int receiverIndex = tradeOffer.getReceiver();
			/*
			 * If the player is the sender and the waitOverlay is not being
			 * shown, display the waitOverlay so that the sender cannot play
			 * during the trade.
			 */
			if (senderIndex == playerIndex
					&& !getWaitOverlay().isModalShowing()) {
				getWaitOverlay().showModal();
			}
			/*
			 * If the player is the receiver, set up the acceptOverlay and
			 * display to the user.
			 */
			else if (receiverIndex == playerIndex) {
				/*
				 * Reset the acceptOverlay so that get and give resources are
				 * empty.
				 */
				acceptOverlay.reset();
				ResourceList resourceList = tradeOffer.getResourceList();

				/*
				 * Determine who is sending the trade offer. Set that player
				 * name within acceptOverlay.
				 */
				acceptOverlay.setPlayerName(clientModelController
						.getClientModel().getPlayers()[senderIndex].getName());

				/*
				 * Determine which resources are being given and which are being
				 * received. For example, if the current value of the brick
				 * integer within resource list is greater than 0, this means
				 * that X brick(s) will be given to the user upon trade
				 * acceptance. If the value of the brick integer is less than 0,
				 * this means that the user will be giving X brick(s) to the
				 * sender upon trade acceptance.
				 */
				if (resourceList.getBrick() > 0) {
					acceptOverlay.addGetResource(ResourceType.BRICK,
							resourceList.getBrick());
				} else if (resourceList.getBrick() < 0) {
					acceptOverlay.addGiveResource(ResourceType.BRICK,
							-(resourceList.getBrick()));
				}

				if (resourceList.getOre() > 0) {
					acceptOverlay.addGetResource(ResourceType.ORE,
							resourceList.getOre());
				} else if (resourceList.getOre() < 0) {
					acceptOverlay.addGiveResource(ResourceType.ORE,
							-(resourceList.getOre()));
				}

				if (resourceList.getSheep() > 0) {
					acceptOverlay.addGetResource(ResourceType.SHEEP,
							resourceList.getSheep());
				} else if (resourceList.getSheep() < 0) {
					acceptOverlay.addGiveResource(ResourceType.SHEEP,
							-(resourceList.getSheep()));
				}

				if (resourceList.getWheat() > 0) {
					acceptOverlay.addGetResource(ResourceType.WHEAT,
							resourceList.getWheat());
				} else if (resourceList.getWheat() < 0) {
					acceptOverlay.addGiveResource(ResourceType.WHEAT,
							-(resourceList.getWheat()));
				}

				if (resourceList.getWood() > 0) {
					acceptOverlay.addGetResource(ResourceType.WOOD,
							resourceList.getWood());
				} else if (resourceList.getWood() < 0) {
					acceptOverlay.addGiveResource(ResourceType.WOOD,
							-(resourceList.getWood()));
				}

				/*
				 * Determine if the current player has adequate resources to
				 * accept the trade being offered. Set the acceptButton enable
				 * status equal to the boolean returned after checking if the
				 * user has adequate resources.
				 */
				boolean acceptTradeBool = clientModelController.canAcceptTrade(
						playerIndex, resourceList.invertList());
				acceptOverlay.setAcceptEnabled(acceptTradeBool);

				acceptOverlay.showModal();
			}
		}
	}
}
