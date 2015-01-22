package client.model;

/**
 * Represents the game map, stores relevant locations and layout
 * 
 * <pre>
 * <b>Domain:</b>
 * -hexes:[Hex]
 * -ports:[Port]
 * -roads[Road]
 * -settlements:[VertexObject]
 * -cities:[VertexObject]
 * -radius:int
 * -robber:HexLocation
 * </pre>
 * 
 * @author Seth White
 *
 */
public class Map {
	private Hex[] hexes;
	private Port[] ports;
	private Road[] roads;
	private VertexObject[] settlements;
	private VertexObject[] cities;
	private int radius;
	private HexLocation robber;

	/**
	 * Default constructor, requires all fields
	 * 
	 * @Pre no field may be null
	 * @Post result: a Map
	 * @param hexes
	 * @param ports
	 * @param roads
	 * @param settlements
	 * @param cities
	 * @param radius
	 * @param robber
	 */
	public Map(Hex[] hexes, Port[] ports, Road[] roads,
			VertexObject[] settlements, VertexObject[] cities, int radius,
			HexLocation robber) {
		super();
		this.hexes = hexes;
		this.ports = ports;
		this.roads = roads;
		this.settlements = settlements;
		this.cities = cities;
		this.radius = radius;
		this.robber = robber;
	}

	public Hex[] getHexes() {
		return hexes;
	}

	public void setHexes(Hex[] hexes) {
		this.hexes = hexes;
	}

	public Port[] getPorts() {
		return ports;
	}

	public void setPorts(Port[] ports) {
		this.ports = ports;
	}

	public Road[] getRoads() {
		return roads;
	}

	public void setRoads(Road[] roads) {
		this.roads = roads;
	}

	public VertexObject[] getSettlements() {
		return settlements;
	}

	public void setSettlements(VertexObject[] settlements) {
		this.settlements = settlements;
	}

	public VertexObject[] getCities() {
		return cities;
	}

	public void setCities(VertexObject[] cities) {
		this.cities = cities;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public HexLocation getRobber() {
		return robber;
	}

	public void setRobber(HexLocation robber) {
		this.robber = robber;
	}
}
