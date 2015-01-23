package client.model;

/**
 * Stores the location of an Edge -x:int -y:int -direction:String
 * 
 * <pre>
 * <b>Domain:</b>
 * -x:int
 * -y:int
 * -direction:String
 * </pre>
 * 
 * @author Seth White
 *
 */
public class EdgeLocation {
	private int x;
	private int y;
	private String direction;

	/**
	 * Default constructor, requires all fields
	 * 
	 * @Pre no field may be null
	 * @Post result: an EdgeLocation
	 * @param x
	 * @param y
	 * @param direction
	 */
	public EdgeLocation(int x, int y, String direction) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
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

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
}
