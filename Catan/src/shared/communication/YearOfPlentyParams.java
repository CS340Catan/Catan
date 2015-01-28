package shared.communication;
/**
 * Class which contains data for 'moves/Year_of_Plenty'
 * @author winstonhurst
 *
 */
public class YearOfPlentyParams {
	String type = "Year_of_Plenty";
	/**
	 *  Who's playing this dev card,
	 */
	int playerIndex;
	Resource resource1;
	Resource resrouce2;
	
	public YearOfPlentyParams(int playerIndex,Resource resource1,Resource resrouce2) {
		this.playerIndex = playerIndex;
		this.resource1 = resource1;
		this.resrouce2 = resrouce2;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public Resource getResource1() {
		return resource1;
	}

	public void setResource1(Resource resource1) {
		this.resource1 = resource1;
	}

	public Resource getResrouce2() {
		return resrouce2;
	}

	public void setResrouce2(Resource resrouce2) {
		this.resrouce2 = resrouce2;
	}

	public String getType() {
		return type;
	}
	
	
	

}
