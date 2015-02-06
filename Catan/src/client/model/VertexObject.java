package client.model;

import shared.locations.EdgeDirection;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
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

	public boolean isEquivalent(VertexObject vertexObj) {
		/*
		 * Check if the inputed road has the same hex location as this (same x,y
		 * indices) and the same directionality
		 */
		VertexLocation vertLocation = this.getLocation();

		if (vertLocation.getHexLoc().equals(
				(vertexObj.getLocation().getHexLoc()))
				&& vertLocation.getDir() == vertexObj.getLocation().getDir()) {
			return true;
		}

		HexLocation neighbor1 = null;
		HexLocation neighbor2 = null;

		switch (vertLocation.getDir()) {
		case NorthWest:
			neighbor1 = vertLocation.getHexLoc().getNeighborLoc(
					EdgeDirection.NorthWest);
			neighbor2 = vertLocation.getHexLoc().getNeighborLoc(
					EdgeDirection.North);

			if (neighbor1.equals(vertexObj.getLocation().getHexLoc())
					&& vertexObj.getLocation().getDir() == VertexDirection.East) {
				return true;
			}

			if (neighbor2.equals(vertexObj.getLocation().getHexLoc())
					&& vertexObj.getLocation().getDir() == VertexDirection.SouthWest) {
				return true;
			}
			break;
		case NorthEast:
			neighbor1 = vertLocation.getHexLoc().getNeighborLoc(
					EdgeDirection.NorthEast);
			neighbor2 = vertLocation.getHexLoc().getNeighborLoc(
					EdgeDirection.North);

			if (neighbor1.equals(vertexObj.getLocation().getHexLoc())
					&& vertexObj.getLocation().getDir() == VertexDirection.West) {
				return true;
			}

			if (neighbor2.equals(vertexObj.getLocation().getHexLoc())
					&& vertexObj.getLocation().getDir() == VertexDirection.SouthEast) {
				return true;
			}
			break;
		case East:
			neighbor1 = vertLocation.getHexLoc().getNeighborLoc(
					EdgeDirection.NorthEast);
			neighbor2 = vertLocation.getHexLoc().getNeighborLoc(
					EdgeDirection.SouthEast);

			if (neighbor1.equals(vertexObj.getLocation().getHexLoc())
					&& vertexObj.getLocation().getDir() == VertexDirection.SouthWest) {
				return true;
			}

			if (neighbor2.equals(vertexObj.getLocation().getHexLoc())
					&& vertexObj.getLocation().getDir() == VertexDirection.NorthWest) {
				return true;
			}
			break;
		case SouthEast:
			neighbor1 = vertLocation.getHexLoc().getNeighborLoc(
					EdgeDirection.SouthEast);
			neighbor2 = vertLocation.getHexLoc().getNeighborLoc(
					EdgeDirection.South);

			if (neighbor1.equals(vertexObj.getLocation().getHexLoc())
					&& vertexObj.getLocation().getDir() == VertexDirection.West) {
				return true;
			}

			if (neighbor2.equals(vertexObj.getLocation().getHexLoc())
					&& vertexObj.getLocation().getDir() == VertexDirection.NorthEast) {
				return true;
			}
			break;
		case SouthWest:
			neighbor1 = vertLocation.getHexLoc().getNeighborLoc(
					EdgeDirection.SouthWest);
			neighbor2 = vertLocation.getHexLoc().getNeighborLoc(
					EdgeDirection.South);

			if (neighbor1.equals(vertexObj.getLocation().getHexLoc())
					&& vertexObj.getLocation().getDir() == VertexDirection.East) {
				return true;
			}

			if (neighbor2.equals(vertexObj.getLocation().getHexLoc())
					&& vertexObj.getLocation().getDir() == VertexDirection.NorthWest) {
				return true;
			}
			break;
		case West:
			neighbor1 = vertLocation.getHexLoc().getNeighborLoc(
					EdgeDirection.SouthWest);
			neighbor2 = vertLocation.getHexLoc().getNeighborLoc(
					EdgeDirection.NorthWest);

			if (neighbor1.equals(vertexObj.getLocation().getHexLoc())
					&& vertexObj.getLocation().getDir() == VertexDirection.NorthEast) {
				return true;
			}

			if (neighbor2.equals(vertexObj.getLocation().getHexLoc())
					&& vertexObj.getLocation().getDir() == VertexDirection.SouthEast) {
				return true;
			}
			break;
		}

		return false;
	}
}
