package shared.model;

/**
 * Tracks the current turn as well as longest road/army awards
 * 
 * <pre>
 * <b>Domain:</b>
 *  -currentTurn:index
 * -status:String
 * -longestRoad:int
 * -largestArmy:int
 * </pre>
 * 
 * @author Seth White
 *
 */
public class TurnTracker {
	private int currentTurn;
	private String status; // ['Rolling' or 'Robbing' or 'Playing' or
							// 'Discarding' or 'FirstRound' or 'SecondRound']:
	private int longestRoad;
	private int largestArmy;

	/**
	 * Default constructor, requires all fields
	 * 
	 * @Pre no field may be null
	 * @Post result: a TurnTracker
	 * @param currentTurn
	 * @param status
	 * @param longestRoad
	 * @param largestArmy
	 */
	public TurnTracker(int currentTurn, String status, int longestRoad,
			int largestArmy) {
		super();
		this.currentTurn = currentTurn;
		this.status = status;
		this.longestRoad = longestRoad;
		this.largestArmy = largestArmy;
	}

	public int getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getLongestRoad() {
		return longestRoad;
	}

	public void setLongestRoad(int longestRoad) {
		this.longestRoad = longestRoad;
	}

	public int getLargestArmy() {
		return largestArmy;
	}

	public void setLargestArmy(int largestArmy) {
		this.largestArmy = largestArmy;
	}
}
