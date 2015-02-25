package client.domestic;

import java.util.Observable;
import java.util.Observer;

import shared.definitions.*;
import client.base.*;
import client.data.PlayerInfo;
import client.misc.*;
import client.model.ClientModel;
import client.model.Player;

/**
 * Domestic trade controller implementation
 */
public class DomesticTradeController extends Controller implements
		IDomesticTradeController, Observer {

	private IDomesticTradeOverlay tradeOverlay;
	private IWaitView waitOverlay;
	private IAcceptTradeOverlay acceptOverlay;
	private int brickAmt, oreAmt, sheepAmt, wheatAmt, woodAmt;

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
		//check resource > 0

		if(resourceAmt(resource) > 0) {
			
			getTradeOverlay().setResourceAmount(resource, "" + (resourceAmt(resource) - 1));
		}
	
		
		
		//disable button if counter is at 0
	}

	@Override
	public void increaseResourceAmount(ResourceType resource) {
		
	}

	@Override
	public void sendTradeOffer() {

		getTradeOverlay().closeModal();
		// getWaitOverlay().showModal();
	}

	@Override
	public void setPlayerToTradeWith(int playerIndex) {

	}

	@Override
	public void setResourceToReceive(ResourceType resource) {

	}

	@Override
	public void setResourceToSend(ResourceType resource) {

	}

	@Override
	public void unsetResource(ResourceType resource) {

	}

	@Override
	public void cancelTrade() {

		getTradeOverlay().closeModal();
	}

	@Override
	public void acceptTrade(boolean willAccept) {

		getAcceptOverlay().closeModal();
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
