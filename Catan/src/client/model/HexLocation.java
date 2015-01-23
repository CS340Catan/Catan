package client.model;

/**
 * Stores the location of a hex
 * 
 * <pre>
 * <b>Domain:</b>
 * -x:int
 * -y:int
 * </pre>
 * 
 * @author Seth White
 *
 */
public class HexLocation {
	private int x;
	private int y;

	/**
	 * Default constructor, requires all fields
	 * 
	 * @Pre no field may be null
	 * @Post result: a HexLocation
	 * @param x
	 * @param y
	 */
	public HexLocation(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
