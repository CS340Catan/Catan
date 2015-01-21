package client.model;

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

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isDiscarded() {
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

	public boolean isPlayedDevCard() {
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
}
