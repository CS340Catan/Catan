package server.model;

import client.model.ClientModel;
import shared.definitions.ResourceType;
import shared.model.AbstractModel;
import shared.model.ResourceList;
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
}
