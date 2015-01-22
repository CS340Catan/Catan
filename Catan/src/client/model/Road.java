package client.model;

/**
 * Contains the location and owner of a Road, changed from EdgeValue in the
 * specs, because that was a poor name.
 * 
 * <pre>
 *  <b>Domain:</b>
 * -owner:int
 * -location:EdgeLocation
 * </pre>
 * 
 * @author Seth White
 *
 */
public class Road {
	private int owner;
	private EdgeLocation location;

	/**
	 * Default constructor, requires all fields
	 * 
	 * @Pre no field may be null
	 * @Post result: a Road
	 * @param owner
	 * @param location
	 */
	public Road(int owner, EdgeLocation location) {
		super();
		this.owner = owner;
		this.location = location;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public EdgeLocation getLocation() {
		return location;
	}

	public void setLocation(EdgeLocation location) {
		this.location = location;
	}
}
