package shared.communication;

/**
 * Class which contains data for 'moves/Monopoly'
 * 
 * @author winstonhurst
 *
 */
public class PlayMonopolyParams {

	String type = "Monopoly";
	String resource;
	/**
	 * Who's playing this dev card
	 */
	int playerIndex;

	public PlayMonopolyParams(String resource, int playerIndex) {
		this.resource = resource;
		this.playerIndex = playerIndex;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
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
}
