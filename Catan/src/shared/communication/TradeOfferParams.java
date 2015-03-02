package shared.communication;

import client.model.ResourceList;

/**
 * Class which contains data for 'moves/offerParams'
 * 
 * @author winstonhurst
 *
 */
public class TradeOfferParams {

	String type = "offerTrade";
	/**
	 * Who's sending the offer,
	 */
	int playerIndex;
	/**
	 * What you get (+) and what you give (-),
	 */
	ResourceList offer;
	/**
	 * Who you're offering the trade to (0-3)
	 */
	int receiver;

	public TradeOfferParams(int playerIndex, ResourceList offer, int receiver) {
		this.playerIndex = playerIndex;
		this.offer = offer;
		this.receiver = receiver;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public ResourceList getOffer() {
		return offer;
	}

	public void setOffer(ResourceList offer) {
		this.offer = offer;
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	public String getType() {
		return type;
	}
}
