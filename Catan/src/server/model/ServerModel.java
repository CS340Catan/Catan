package server.model;

import client.model.ClientModel;
import shared.model.AbstractModel;
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
}
