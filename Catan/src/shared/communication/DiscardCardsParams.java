package shared.communication;

import client.model.ResourceList;

/**
 * Class which contains data for 'moves/discardCards'
 * 
 * @author winstonhurst
 *
 */
public class DiscardCardsParams {
	String type = "discardCards";
	/**
	 * Who's discarding,
	 */
	int playerIndex;
	ResourceList discardedCards;

	public DiscardCardsParams(int playerIndex, ResourceList discardedCards) {
		this.playerIndex = playerIndex;
		this.discardedCards = discardedCards;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public ResourceList getDiscardedCards() {
		return discardedCards;
	}

	public void setDiscardedCards(ResourceList discardedCards) {
		this.discardedCards = discardedCards;
	}

	public String getType() {
		return type;
	}
}
