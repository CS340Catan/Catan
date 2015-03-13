package shared.communication;

/**
 * Class which contains data for 'moves/finishTurn','moves/buyDevCard', or
 * 'moves/Monument'
 * 
 * @author winstonhurst
 *
 */

public class UserActionParams {

	/**
	 * used for finish turn "finishTurn", buy dev card "buyDevCard", play
	 * monument -"Monument"
	 */
	String type;
	int playerIndex;

	public UserActionParams(int index) {
		this.playerIndex = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

}
