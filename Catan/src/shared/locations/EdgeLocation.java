package shared.locations;

import com.google.gson.annotations.Expose;

/**
 * Represents the location of an edge on a hex map
 */
public class EdgeLocation {

	private HexLocation hexLoc;
	private EdgeDirection dir;
	private String direction;
	private int x;
	private int y;

	public EdgeLocation(HexLocation hexLoc, EdgeDirection dir) {
		setHexLoc(hexLoc);
		setDir(dir);
		direction = dir.toString();
	}
	public void convertToPrimitives(){
		x = hexLoc.getX();
		y = hexLoc.getY();
		switch(dir){
		case NorthWest:
			direction = "NW";
			break;
		case North:
			direction = "N";
			break;
		case NorthEast:
			direction = "NE";
			break;
		case SouthEast:
			direction = "SE";
			break;
		case South:
			direction = "S";
			break;
		case SouthWest:
			direction = "SW";
			break;
		}
	}
//	public void 
	public void convertFromPrimitives() {
		this.hexLoc = new HexLocation(x, y);
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

	public HexLocation getHexLoc() {
		return hexLoc;
	}

	private void setHexLoc(HexLocation hexLoc) {
		if (hexLoc == null) {
			throw new IllegalArgumentException("hexLoc cannot be null");
		}
		this.hexLoc = hexLoc;
	}

	public EdgeDirection getDir() {
		return dir;
	}

	private void setDir(EdgeDirection dir) {
		this.dir = dir;
	}

	@Override
	public String toString() {
		return "EdgeLocation [hexLoc=" + hexLoc + ", dir=" + dir + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dir == null) ? 0 : dir.hashCode());
		result = prime * result + ((hexLoc == null) ? 0 : hexLoc.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EdgeLocation other = (EdgeLocation) obj;
		if (dir != other.dir)
			return false;
		if (hexLoc == null) {
			if (other.hexLoc != null)
				return false;
		} else if (!hexLoc.equals(other.hexLoc))
			return false;
		return true;
	}

	/**
	 * Returns a canonical (i.e., unique) value for this edge location. Since
	 * each edge has two different locations on a map, this method converts a
	 * hex location to a single canonical form. This is useful for using hex
	 * locations as map keys.
	 * 
	 * @return Normalized hex location
	 */
	public EdgeLocation getNormalizedLocation() {

		// Return an EdgeLocation that has direction NW, N, or NE

		switch (dir) {
		case NorthWest:
		case North:
		case NorthEast:
			return this;
		case SouthWest:
		case South:
		case SouthEast:
			return new EdgeLocation(hexLoc.getNeighborLoc(dir),
					dir.getOppositeDirection());
		default:
			assert false;
			return null;
		}
	}

	public String getStringDirection() {
		return direction;
	}

	public void setStringDirection(String direction) {
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
