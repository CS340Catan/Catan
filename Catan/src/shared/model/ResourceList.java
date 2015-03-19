package shared.model;

import java.util.Random;

import shared.definitions.ResourceType;

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
	 * @param wood
	 */
	public ResourceList(int brick, int ore, int sheep, int wheat, int wood) {
		this.brick = brick;
		this.ore = ore;
		this.sheep = sheep;
		this.wheat = wheat;
		this.wood = wood;
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
	 * Tests if there exists the number of inputed resources. For example, are
	 * there 3 bricks currently stored within the resource list.
	 * 
	 * @Pre none
	 * @Post boolean Returns true if there are (number) of type resource.
	 * @param number
	 *            Number of resources.
	 * @param resource
	 *            Resource being queried.
	 * @return
	 */
	public boolean ofAKind(ResourceType resource, int number) {
		if (resource.toString() == "brick" && this.brick >= number) {
			return true;
		}
		if (resource.toString() == "wood" && this.wood >= number) {
			return true;
		}
		if (resource.toString() == "ore" && this.ore >= number) {
			return true;
		}
		if (resource.toString() == "sheep" && this.sheep >= number) {
			return true;
		}
		if (resource.toString() == "wheat" && this.wheat >= number) {
			return true;
		}
		return false;
	}

	/**
	 * This function will invert the current values of the resource list, such
	 * that a negative with become a positive and vice verse. For example, if
	 * the resource list contains 1 wood, the list will now contain -1 wood.
	 */
	public ResourceList invertList() {
		ResourceList invertList = new ResourceList(-(this.brick), -(this.ore),
				-(this.sheep), -(this.wheat), -(this.wood));
		return invertList;
	}

	public void addResources(ResourceList newResources) {
		this.brick += newResources.getBrick();
		this.ore += newResources.getOre();
		this.sheep += newResources.getSheep();
		this.wheat += newResources.getWheat();
		this.wood += newResources.getWood();
	}

	public ResourceType getRandomResourceFromList() {
		int totalResourceCount = this.brick + this.ore + this.sheep
				+ this.wheat + this.wood;
		Random rand = new Random();
		int randomNum = rand.nextInt(totalResourceCount) + 1;

		if (randomNum - this.brick <= 0) {
			return ResourceType.BRICK;
		} else if (randomNum - this.brick - this.ore <= 0)
			return ResourceType.ORE;
		else if (randomNum - this.brick - this.ore - this.sheep <= 0)
			return ResourceType.SHEEP;
		else if (randomNum - this.brick - this.ore - this.sheep - this.wheat <= 0)
			return ResourceType.WHEAT;
		else if (randomNum - this.brick - this.ore - this.sheep - this.wheat
				- this.wood <= 0)
			return ResourceType.WOOD;
		return null;
	}
}
