package shared.model;

import shared.locations.EdgeDirection;
import shared.locations.HexLocation;

/**
 * The port hex, contains location and trading ratio
 * 
 * <pre>
 * <b>Domain:</b>
 *  -resource:String
 * -location:HexLocation
 * -direction:String
 * -ratio:int
 * </pre>
 * 
 * @author Seth White
 *
 */
public class Port {
	private String resource;
	private HexLocation location;
	private EdgeDirection dir;
	private String direction;
	private int ratio;

	/**
	 * Default constructor, requires all fields
	 * 
	 * @Pre no field may be null
	 * @Post result: a Port
	 * @param resource
	 * @param location
	 * @param direction
	 * @param ratio
	 */
	public Port(String resource, HexLocation location, String direction,
			int ratio) {
		super();
		this.resource = resource;
		this.location = location;
		this.direction = direction;
		this.ratio = ratio;
	}

	public void convertFromPrimitives() {
		switch (direction) {
		case "N":
			dir = EdgeDirection.North;
			break;
		case "NE":
			dir = EdgeDirection.NorthEast;
			break;
		case "SE":
			dir = EdgeDirection.SouthEast;
			break;
		case "S":
			dir = EdgeDirection.South;
			break;
		case "SW":
			dir = EdgeDirection.SouthWest;
			break;
		case "NW":
			dir = EdgeDirection.NorthWest;
			break;
		}
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public HexLocation getLocation() {
		return location;
	}

	public void setLocation(HexLocation location) {
		this.location = location;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public int getRatio() {
		return ratio;
	}

	public void setRatio(int ratio) {
		this.ratio = ratio;
	}

	public EdgeDirection getDir() {
		return dir;
	}

	public void setDir(EdgeDirection dir) {
		this.dir = dir;
	}
}
