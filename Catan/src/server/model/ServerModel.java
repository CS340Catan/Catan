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
	
	public void addResource(int playerIndex, ResourceType type, int amount) {
		
		ResourceList resources = this.getPlayers()[playerIndex].getResources();
		
		switch(type) {
		case BRICK:
			resources.setBrick(resources.getBrick() + amount);
			break;
		case ORE:
			resources.setOre(resources.getOre() + amount);
			break;
		case SHEEP:
			resources.setSheep(resources.getSheep() + amount);
			break;
		case WHEAT:
			resources.setWheat(resources.getWheat() + amount);
			break;
		case WOOD:
			resources.setWood(resources.getWood() + amount);
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
				this.getTurnTracker().setLongestRoad(player.getPlayerIndex());	//need to increment/decrement victory points?
			}
			// special case: 2 people tie and surpass 5 at the same time
			else if(hasTiedLongestRoad(player.getPlayerIndex())
					&& (this.getTurnTracker().getLongestRoad() == -1)) {		//initialized to -1 right?
						this.getTurnTracker().setLongestRoad(player.getPlayerIndex());
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
}
