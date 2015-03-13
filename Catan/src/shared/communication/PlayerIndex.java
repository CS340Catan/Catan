package shared.communication;

/**
 * constrained player index between 3-4
 * 
 * <pre>
 * <b> Domain: </b>
 * index:int range 0..4
 * @author Seth White
 *
 */
public class PlayerIndex {
	private int index;

	/**
	 * Constrains a player index
	 * 
	 * @Pre none
	 * @Post result: PlayerIndex
	 * @param index
	 */
	public PlayerIndex(int index) {
		super();
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	};

}
