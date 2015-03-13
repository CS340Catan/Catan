package shared.communication;

/**
 * Class which contains data for 'moves/acceptTrade'
 * 
 * @author winstonhurst
 *
 */
public class AcceptTradeParams {

	String type = "acceptTrade";
	/**
	 * Who's accepting / rejecting this trade,
	 */
	int playerIndex;
	/**
	 * Whether you accept the trade or not
	 */
	boolean willAccept;

	public AcceptTradeParams(int playerIndex, boolean willAccept) {
		super();
		this.playerIndex = playerIndex;
		this.willAccept = willAccept;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public boolean isWillAccept() {
		return willAccept;
	}

	public void setWillAccept(boolean willAccept) {
		this.willAccept = willAccept;
	}

	public String getType() {
		return type;
	}
}
