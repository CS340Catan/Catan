package server.model;

import client.model.ClientModel;
import shared.definitions.ResourceType;
import shared.model.*;
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
 * </pre>
 * 
 * @author Seth White
 *
 */
public class ServerModel extends AbstractModel{
	int gameID;
	
	public int getGameID(){
		return gameID;
	}
	
	public void setGameID(int ID){
		this.gameID = ID;
	}
	public String toString() {
		return Serializer.serializeServerModel(this);
	}
	
	public ClientModel toClientModel(){
		AbstractModel am = (AbstractModel) this;
		ClientModel cm = (ClientModel) am;
		return cm;
	}
	
	public void addResourceFromBank(int playerIndex, ResourceType type, int amount) {
		
		ResourceList playerResources = this.getPlayers()[playerIndex].getResources();
		ResourceList bankResources = this.getBank();
		
		switch(type) {
		case BRICK:
			playerResources.setBrick(playerResources.getBrick() + amount);
			bankResources.setBrick(bankResources.getBrick() - amount);
			break;
		case ORE:
			playerResources.setOre(playerResources.getOre() + amount);
			bankResources.setOre(bankResources.getOre() - amount);
			break;
		case SHEEP:
			playerResources.setSheep(playerResources.getSheep() + amount);
			bankResources.setSheep(bankResources.getSheep() - amount);
			break;
		case WHEAT:
			playerResources.setWheat(playerResources.getWheat() + amount);
			bankResources.setWheat(bankResources.getWheat() - amount);
			break;
		case WOOD:
			playerResources.setWood(playerResources.getWood() + amount);
			bankResources.setWood(bankResources.getWood() - amount);
			break;
		default:
			break;
		}
		
		
	}
	
	public void addRoad(Road road) {
		
		Road[] roads = this.getMap().getRoads();
		
		// copy old array into slightly bigger new array 
		Road[] newRoads = new Road[roads.length + 1];
		for(int i = 0; i < roads.length; i++) {
			newRoads[i] = roads[i];
		}
		// add new road
		newRoads[newRoads.length] = road;
		
		this.getMap().setRoads(newRoads);
	}
	
	public void reallocateLongestRoad() {
		//see who has most roads, must be >=5 if there's a tie keep the old player, else switch to new player
		
		Player[] players = this.getPlayers();
		int[] playerRoads = new int[4];
		
		// iterate
		for(Player player: players) {
			if(hasLongestRoad(player.getPlayerIndex())) {
				
				// decrement old longest road player victory points
				if(this.getTurnTracker().getLongestRoad() != -1) {
					Player loser = players[this.getTurnTracker().getLongestRoad()];
					loser.setVictoryPoints(loser.getVictoryPoints() - 1);
				}
				
				// change longest road player
				this.getTurnTracker().setLongestRoad(player.getPlayerIndex());
				

				// increment victory points for new longest road player
				player.setVictoryPoints(player.getVictoryPoints() + 1);

				
			}
			// special case: 2 people tie and surpass 5 at the same time
			else if(hasTiedLongestRoad(player.getPlayerIndex())
					&& (this.getTurnTracker().getLongestRoad() == -1)) {		//initialized to -1 right?
						this.getTurnTracker().setLongestRoad(player.getPlayerIndex());
						player.setVictoryPoints(player.getVictoryPoints() + 1);
			}
		}
		
	}
	
	private boolean hasLongestRoad(int playerIndex) {
		
		Player[] players = this.getPlayers();
		
		for(Player player: players) {
			if(player.getPlayerIndex() != playerIndex 
					&& player.getRoads() >= players[playerIndex].getRoads()) {
				return false;
			}
		}
		
		if(players[playerIndex].getRoads() >= 5) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean hasTiedLongestRoad(int playerIndex) {
		
		Player[] players = this.getPlayers();
		
		for(Player player: players) {
			if(player.getPlayerIndex() != playerIndex 
					&& players[playerIndex].getRoads() >= 5
					&& player.getRoads() == players[playerIndex].getRoads()) {
				return true;
			}
		}
		
		return false;
	}
	
	
	public void addSettlement(VertexObject settlement) {
		
		VertexObject[] settlements = this.getMap().getSettlements();
		
		// copy old array into slightly bigger new array 
		VertexObject[] newSettlements = new VertexObject[settlements.length + 1];
		for(int i = 0; i < settlements.length; i++) {
			newSettlements[i] = settlements[i];
		}
		// add new settlement
		newSettlements[newSettlements.length] = settlement;
		
		this.getMap().setSettlements(newSettlements);
	}

	public void addCity(VertexObject city){
		VertexObject[] currentCities = this.getMap().getCities();
		int currentCount = currentCities.length;
		VertexObject[] newCityList = new VertexObject[currentCount + 1];
		for(int i = 0 ; i<currentCount; i++){
			newCityList[i] = currentCities[i];
		}
		newCityList[currentCount] = city;
		this.getMap().setCities(newCityList);
	}
	
	
	public boolean needToDiscard() {
		
		Player[] players = this.getPlayers();
		
		for(Player player: players) {
			if(player.getResources().count() > 7) {
				return true;
			}
		}
		
		return false;
	}
}
