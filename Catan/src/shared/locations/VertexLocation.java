package shared.locations;

/**
 * Represents the location of a vertex on a hex map
 */
public class VertexLocation {

	private HexLocation hexLoc;
	private VertexDirection dir;
	private String direction;
	private int x;
	private int y;

	public VertexLocation(HexLocation hexLoc, VertexDirection dir) {
		setHexLoc(hexLoc);
		setDir(dir);
	}
	
	public VertexLocation(){};

	public void convertToPrimitives() {
		x = hexLoc.getX();
		y = hexLoc.getY();
		switch (dir) {
		case NorthWest:
			direction = "NW";
			break;
		case East:
			direction = "E";
			break;
		case NorthEast:
			direction = "NE";
			break;
		case SouthEast:
			direction = "SE";
			break;
		case West:
			direction = "W";
			break;
		case SouthWest:
			direction = "SW";
			break;

		}
	}

	// public void
	public void convertFromPrimitives() {
		this.hexLoc = new HexLocation(x, y);
		switch (direction) {
		case ("E"):
			dir = VertexDirection.East;
			break;
		case ("NE"):
			dir = VertexDirection.NorthEast;
			break;
		case ("SE"):
			dir = VertexDirection.SouthEast;
			break;
		case ("W"):
			dir = VertexDirection.West;
			break;
		case ("SW"):
			dir = VertexDirection.SouthWest;
			break;
		case ("NW"):
			dir = VertexDirection.NorthWest;
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

	public VertexDirection getDir() {
		return dir;
	}

	private void setDir(VertexDirection direction) {
		this.dir = direction;
	}

	@Override
	public String toString() {
		return "VertexLocation [hexLoc=" + hexLoc + ", dir=" + dir + "]";
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
		VertexLocation other = (VertexLocation) obj;
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
	 * Returns a canonical (i.e., unique) value for this vertex location. Since
	 * each vertex has three different locations on a map, this method converts
	 * a vertex location to a single canonical form. This is useful for using
	 * vertex locations as map keys.
	 * 
	 * @return Normalized vertex location
	 */
	public VertexLocation getNormalizedLocation() {

		// Return location that has direction NW or NE

		switch (dir) {
		case NorthWest:
		case NorthEast:
			return this;
		case West:
			return new VertexLocation(
					hexLoc.getNeighborLoc(EdgeDirection.SouthWest),
					VertexDirection.NorthEast);
		case SouthWest:
			return new VertexLocation(
					hexLoc.getNeighborLoc(EdgeDirection.South),
					VertexDirection.NorthWest);
		case SouthEast:
			return new VertexLocation(
					hexLoc.getNeighborLoc(EdgeDirection.South),
					VertexDirection.NorthEast);
		case East:
			return new VertexLocation(
					hexLoc.getNeighborLoc(EdgeDirection.SouthEast),
					VertexDirection.NorthWest);
		default:
			assert false;
			return null;
		}
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
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
}
