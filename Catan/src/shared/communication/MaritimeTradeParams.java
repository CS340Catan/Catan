package shared.communication;

/**
 * Class which contains data for 'moves/maritimeTrade'
 * 
 * @author winstonhurst
 *
 */
public class MaritimeTradeParams {

	String type = "maritimeTrade";
	/**
	 * Who's doing the trading,
	 */
	int playerIndex;
	/**
	 * (optional): The ratio of the trade your doing as an integer (ie. put 3
	 * for a 3:1 trade),
	 */
	int ratio;
	/**
	 * (optional): What type of resource you're giving.
	 */
	String inputResource;
	/**
	 * (optional): What type of resource you're getting.
	 */
	String outputResource;

	public MaritimeTradeParams(int playerIndex, int ratio,
			String inputResource, String outputResource) {
		this.playerIndex = playerIndex;
		this.ratio = ratio;
		this.inputResource = inputResource;
		this.outputResource = outputResource;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public int getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}

	public String getInputResource() {
		return inputResource;
	}

	public void setInputResource(String inputResource) {
		this.inputResource = inputResource;
	}

	public String getOutputResource() {
		return outputResource;
	}

	public void setOutputResource(String outputResource) {
		this.outputResource = outputResource;
	}

	public String getType() {
		return type;
	}
}
