package client.model;

import shared.locations.VertexLocation;

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
	private VertexLocation location;

	/**
	 * Default constructor, requires all fields
	 * 
	 * @Pre no field may be null
	 * @Post result: a VertexObject
	 * @param owner
	 * @param location
	 */
	public VertexObject(int owner, VertexLocation location) {
		this.owner = owner;
		this.location = location;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

	public VertexLocation getLocation() {
		return location;
	}

	public void setLocation(VertexLocation location) {
		this.location = location;
	}

	public boolean checkAvailability(VertexObject vertexObj) {
		/*
		 * Check if the inputed road has the same hex location as this (same x,y
		 * indices) and the same directionality
		 */
		// TODO: Add check availability on all other adjoining vertices
		if (this.getLocation().getHexLoc() == (vertexObj.getLocation()
				.getHexLoc())
				&& this.getLocation().getDir() == vertexObj.getLocation()
						.getDir()) {
			return false;
		}
		return true;
	}
}
