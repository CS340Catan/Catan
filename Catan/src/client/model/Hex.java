package client.model;

/**
 * Represents a map hex, stores its location, what resource it represents, and
 * its number to be rolled
 * 
 * <pre>
 *  <b>Domain:</b>
 * -location:HexLocation
 * -resource:String
 * -number:int
 * </pre>
 * 
 * @author Seth White
 *
 */
public class Hex {
	private HexLocation location;
	private String resource;
	private int numberToken;

	public HexLocation getLocation() {
		return location;
	}

	public void setLocation(HexLocation location) {
		this.location = location;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public int getNumberToken() {
		return numberToken;
	}

	public void setNumberToken(int numberToken) {
		this.numberToken = numberToken;
	}
}
