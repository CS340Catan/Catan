package shared.communication;

/**
 * Class which contains data for 'moves/Year_of_Plenty'
 * 
 * @author winstonhurst
 *
 */
public class YearOfPlentyParams {
	String type = "Year_of_Plenty";
	/**
	 * Who's playing this dev card,
	 */
	int playerIndex;
	String resource1;
	String resource2;

	public YearOfPlentyParams(int playerIndex, String resource1,
			String resrouce2) {
		this.playerIndex = playerIndex;
		this.resource1 = resource1;
		this.resource2 = resrouce2;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public String getResource1() {
		return resource1;
	}

	public void setResource1(String resource1) {
		this.resource1 = resource1;
	}

	public String getResource2() {
		return resource2;
	}

	public void setResource2(String resrouce2) {
		this.resource2 = resrouce2;
	}

	public String getType() {
		return type;
	}

}
