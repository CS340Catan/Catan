package client.model;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

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

	/**
	 * see if the spot is available for a road
	 * 
	 * @param newRoad
	 * @return
	 */
	public boolean checkAvailability(Road newRoad) {
		/*
		 * Check if the inputed road has the same hex location as this (same x,y
		 * indices) and the same directionality
		 */

		HexLocation hexLocation = this.getLocation().getHexLoc();

		if (hexLocation.equals(newRoad.getLocation().getHexLoc())
				&& this.getLocation().getDir() == newRoad.getLocation().getDir()) {
			return false;
		}

		/*
		 * Check if the inputed has the same hex location as this.nieghbor
		 * (neighbor or this's x,y) and the opposite directionality. So you need
		 * to check for the road's corresponding availability and the adjacent
		 * hex location.
		 */
		if (this.getLocation()
				.getHexLoc()
				.equals(newRoad.getLocation().getHexLoc()
						.getNeighborLoc(newRoad.getLocation().getDir()))
				&& this.getLocation().getDir() == newRoad.getLocation().getDir()
						.getOppositeDirection()) {
			return false;
		}
		return true;
	}
}
