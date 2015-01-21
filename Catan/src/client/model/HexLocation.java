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
