package server.model;

import java.util.ArrayList;
import java.util.HashMap;

import shared.communication.GameSummary;
import shared.utils.Serializer;

/**
 * Contains all data relevant to the game
 * 
 * <pre>
 * <b>Domain:</b>
 * -bank:ResourceList
 * -chat:MessageList
 * -log:MessageList
 * -map:Map
 * -players:[Player]
 * -tradeOffer:TradeOffer
 * -turnTracker:TurnTracker
 * -version:int
 * -winner:int
 * -modelMap:HashMap
 * -gamesList:ArrayList
 * </pre>
 * 
 * @author Seth White
 *
 */
public class ClientModel {
	private ResourceList bank;
	private MessageList chat;
	private MessageList log;
	private Deck deck;
	private Map map;
	private Player[] players;
	private TradeOffer tradeOffer;
	private TurnTracker turnTracker;
	private int version = -1;
	private int winner;

	private static HashMap<Integer, ClientModel> modelMap = null;
	private static ArrayList<GameSummary> gamesList = null;

	/**
	 * Default constructor
	 */
	public ClientModel() {

	}

	/**
	 * Returns a HashMap singleton  of ClientModels
	 * @return HashMap<ClientModel>
	 */
	public static HashMap<Integer, ClientModel> getModelMap() {
		if (modelMap == null) {
			modelMap = new HashMap<Integer, ClientModel>();
		}
		return modelMap;
	}
	
	/**
	 * Returns an ArrayList Singleton of GameSummarys
	 * @return ArrayList<GameSummary>
	 */
	public static ArrayList<GameSummary> getGameList() {
		if (gamesList == null) {
			gamesList = new ArrayList<GameSummary>();
		}
		return gamesList;
	}

	public ResourceList getBank() {
		return bank;
	}

	public void setBank(ResourceList bank) {
		this.bank = bank;
	}

	public MessageList getChat() {
		return chat;
	}

	public void setChat(MessageList chat) {
		this.chat = chat;
	}

	public MessageList getLog() {
		return log;
	}

	public void setLog(MessageList log) {
		this.log = log;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public TradeOffer getTradeOffer() {
		return tradeOffer;
	}

	public void setTradeOffer(TradeOffer tradeOffer) {
		this.tradeOffer = tradeOffer;
	}

	public TurnTracker getTurnTracker() {
		return turnTracker;
	}

	public void setTurnTracker(TurnTracker turnTracker) {
		this.turnTracker = turnTracker;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public Deck getDeck() {
		return deck;
	}

	public void setDeck(Deck deck) {
		this.deck = deck;
	}

}
