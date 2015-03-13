package shared.communication;

/**
 * Class which contains data for 'moves/Monopoly'
 * 
 * @author andrew
 *
 */
public class PlayMonumentParams {

	String type = "Monument";
	/**
	 * Who's playing this dev card
	 */
	int playerIndex;

	public PlayMonumentParams(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
