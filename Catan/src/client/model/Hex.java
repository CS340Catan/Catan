package client.model;

import shared.locations.HexLocation;

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
	private int number = -1;

	/**
	 * Default constructor, requires all fields
	 * 
	 * @Pre no field may be null
	 * @Post result: a Hex
	 * @param location
	 * @param resource
	 * @param numberToken
	 */
	public Hex(HexLocation location, String resource, int numberToken) {
		super();
		this.location = location;
		this.resource = resource;
		this.number = numberToken;
	}

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

	public int getNumber() {
		return number;
	}

	public void setNumber(int numberToken) {
		this.number = numberToken;
	}
}
