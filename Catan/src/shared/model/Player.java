package shared.model;

import shared.data.PlayerInfo;
import shared.definitions.CatanColor;

/**
 * Represents a player in the game, contains relevant data, cards held, etc.
 * 
 * <pre>
 * <b>Domain:</b>
 *  - playerIndex:int
 * - cities:int
 * - settlements:int
 * - name: string
 * - color:string
 * - discarded: boolean
 * - monuments:int
 * - newDevCards: DevCardList
 * - oldDevCards: DevCardList
 * - playedDevCard:boolean
 * - playerId:int
 * - resources:ResourceList
 * - roads:int
 * - soldiers:int
 * - victoryPoints:int
 * </pre>
 * 
 * @author Seth White
 *
 */
public class Player {
	private int playerIndex;
	private int playerid;
	private int cities;
	private int settlements;
	private String name;
	private String color;
	private boolean discarded;
	private int monuments;
	private DevCardList newDevCards;
	private DevCardList oldDevCards;
	private boolean playedDevCard;
	private ResourceList resources;
	private int roads;
	private int soldiers;
	private int victoryPoints;

	/**
	 * Default constructor, requires all fields
	 * 
	 * @Pre no field may be null
	 * @Post result: a Player
	 * @param playerIndex
	 * @param playerid
	 * @param cities
	 * @param settlements
	 * @param name
	 * @param color
	 * @param discarded
	 * @param monuments
	 * @param newDevCards
	 * @param oldDevCards
	 * @param playedDevCard
	 * @param resources
	 * @param roads
	 * @param soldiers
	 * @param victoryPoints
	 */
	public Player(int playerIndex, int playerid, int cities, int settlements,
			String name, String color, boolean discarded, int monuments,
			DevCardList newDevCards, DevCardList oldDevCards,
			boolean playedDevCard, ResourceList resources, int roads,
			int soldiers, int victoryPoints) {
		super();
		this.playerIndex = playerIndex;
		this.playerid = playerid;
		this.cities = cities;
		this.settlements = settlements;
		this.name = name;
		this.color = color;
		this.discarded = discarded;
		this.monuments = monuments;
		this.newDevCards = newDevCards;
		this.oldDevCards = oldDevCards;
		this.playedDevCard = playedDevCard;
		this.resources = resources;
		this.roads = roads;
		this.soldiers = soldiers;
		this.victoryPoints = victoryPoints;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public int getPlayerid() {
		return playerid;
	}

	public void setPlayerid(int playerid) {
		this.playerid = playerid;
	}

	public int getCities() {
		return cities;
	}

	public void setCities(int cities) {
		this.cities = cities;
	}

	public int getSettlements() {
		return settlements;
	}

	public void setSettlements(int settlements) {
		this.settlements = settlements;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public CatanColor getCatanColor() {
		// convert color to catan enum
		CatanColor catanColor;
		switch (color) {
		case "red":
			catanColor = CatanColor.RED;
			break;
		case "orange":
			catanColor = CatanColor.ORANGE;
			break;
		case "yellow":
			catanColor = CatanColor.YELLOW;
			break;
		case "blue":
			catanColor = CatanColor.BLUE;
			break;
		case "green":
			catanColor = CatanColor.GREEN;
			break;
		case "purple":
			catanColor = CatanColor.PURPLE;
			break;
		case "puce":
			catanColor = CatanColor.PUCE;
			break;
		case "white":
			catanColor = CatanColor.WHITE;
			break;
		case "brown":
			catanColor = CatanColor.BROWN;
			break;
		default:
			catanColor = null;
			break;
		}

		return catanColor;
	}
	
	public String getColorString(){
		return this.color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean alreadyDiscarded() {
		return discarded;
	}

	public void setDiscarded(boolean discarded) {
		this.discarded = discarded;
	}

	public int getMonuments() {
		return monuments;
	}

	public void setMonuments(int monuments) {
		this.monuments = monuments;
	}

	public DevCardList getNewDevCards() {
		return newDevCards;
	}

	public void setNewDevCards(DevCardList newDevCards) {
		this.newDevCards = newDevCards;
	}

	public DevCardList getOldDevCards() {
		return oldDevCards;
	}

	public void setOldDevCards(DevCardList oldDevCards) {
		this.oldDevCards = oldDevCards;
	}

	public boolean hasPlayedDevCard() {
		return playedDevCard;
	}

	public void setPlayedDevCard(boolean playedDevCard) {
		this.playedDevCard = playedDevCard;
	}

	public ResourceList getResources() {
		return resources;
	}

	public void setResources(ResourceList resources) {
		this.resources = resources;
	}

	public int getRoads() {
		return roads;
	}

	public void setRoads(int roads) {
		this.roads = roads;
	}

	public int getSoldiers() {
		return soldiers;
	}

	public void setSoldiers(int soldiers) {
		this.soldiers = soldiers;
	}

	public int getVictoryPoints() {
		return victoryPoints;
	}

	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}

	public PlayerInfo getPlayerInfo() {
		PlayerInfo playerInfo = new PlayerInfo();

		playerInfo.setId(getPlayerid());
		playerInfo.setPlayerIndex(getPlayerIndex());
		playerInfo.setName(getName());

		// convert color to catan enum
		CatanColor catanColor;
		switch (color) {
		case "red":
			catanColor = CatanColor.RED;
			break;
		case "orange":
			catanColor = CatanColor.ORANGE;
			break;
		case "yellow":
			catanColor = CatanColor.YELLOW;
			break;
		case "blue":
			catanColor = CatanColor.BLUE;
			break;
		case "green":
			catanColor = CatanColor.GREEN;
			break;
		case "purple":
			catanColor = CatanColor.PURPLE;
			break;
		case "puce":
			catanColor = CatanColor.PUCE;
			break;
		case "white":
			catanColor = CatanColor.WHITE;
			break;
		case "brown":
			catanColor = CatanColor.BROWN;
			break;
		default:
			catanColor = null;
			break;
		}

		playerInfo.setColor(catanColor);

		return playerInfo;
	}
	
	public int getNumberRoadsBuilt() {
		return (15 - getRoads());
	}
}
