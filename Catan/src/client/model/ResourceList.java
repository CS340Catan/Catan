package client.model;

/**
 * A list of resources
 * 
 * <pre>
 * <b>Domain:</b>
 * - brick:int
 * - ore:int
 * - sheep:int
 * - wheat:int
 * - lumber:int
 * </pre>
 * 
 * @author Seth White
 *
 */
public class ResourceList {
	private int brick;
	private int ore;
	private int sheep;
	private int wheat;
	private int lumber;

	/**
	 * Default constructor, requires all fields
	 * 
	 * @Pre no field may be null
	 * @Post result: a ResourceList
	 * @param brick
	 * @param ore
	 * @param sheep
	 * @param wheat
	 * @param lumber
	 */
	public ResourceList(int brick, int ore, int sheep, int wheat, int lumber) {
		super();
		this.brick = brick;
		this.ore = ore;
		this.sheep = sheep;
		this.wheat = wheat;
		this.lumber = lumber;
	}

	public int getBrick() {
		return brick;
	}

	public void setBrick(int brick) {
		this.brick = brick;
	}

	public int getOre() {
		return ore;
	}

	public void setOre(int ore) {
		this.ore = ore;
	}

	public int getSheep() {
		return sheep;
	}

	public void setSheep(int sheep) {
		this.sheep = sheep;
	}

	public int getWheat() {
		return wheat;
	}

	public void setWheat(int wheat) {
		this.wheat = wheat;
	}

	public int getLumber() {
		return lumber;
	}

	public void setLumber(int lumber) {
		this.lumber = lumber;
	}
}
