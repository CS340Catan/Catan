package client.model;

/**
 * An object placed on a vertex, city or settlement
 * 
 * <pre>
 * <b>Domain:</b>
 *  -owner:int
 * -location:EdgeLocation
 * </pre>
 * 
 * @author Seth White
 *
 */
public class VertexObject {
	private int owner;
	private EdgeLocation location;

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
