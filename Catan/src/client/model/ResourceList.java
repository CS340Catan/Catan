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
	private int wood;

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
		this.brick = brick;
		this.ore = ore;
		this.sheep = sheep;
		this.wheat = wheat;
		this.wood = lumber;
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

	public int getWood() {
		return wood;
	}

	public void setWood(int lumber) {
		this.wood = lumber;
	}

	/**
	 * Checks if another resourceList equals this one
	 * 
	 * @Pre none
	 * @Post boolean
	 * @param resourceList
	 * @return
	 */
	public boolean equals(ResourceList resourceList) {
		if (resourceList.getBrick() == this.getBrick()
				&& resourceList.getWood() == this.getWood()
				&& resourceList.getOre() == this.getOre()
				&& resourceList.getSheep() == this.getSheep()
				&& resourceList.getWheat() == this.getWheat()) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if the given resource list is a subset of this resource list.
	 * 
	 * @Pre none
	 * @Post boolean
	 * @param resourceList
	 * @return
	 */
	public boolean contains(ResourceList resourceList) {
		if (resourceList.getBrick() <= this.getBrick()
				&& resourceList.getWood() <= this.getWood()
				&& resourceList.getOre() <= this.getOre()
				&& resourceList.getSheep() <= this.getSheep()
				&& resourceList.getWheat() <= this.getWheat()) {
			return true;
		}
		return false;
	}

	/**
	 * Gives a count of how many resources are stored here
	 * 
	 * @return
	 */
	public int count() {
		return brick + wood + ore + sheep + wheat;
	}

	/**
	 * Tests if there exists a number of a kind in the list, 3 of a kind, 4 of a
	 * kind, etc
	 * 
	 * @Pre none
	 * @Post boolean
	 * @param number
	 * @return
	 */
	public boolean ofAKind(int number) {
		if (this.getBrick() >= number || this.getWood() >= number
				|| this.getOre() >= number || this.getSheep() >= number
				|| this.getWheat() >= number) {
			return true;
		}
		return false;
	}
}
