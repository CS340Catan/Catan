package shared.model;

/**
 * Stores the different types of development cards
 * 
 * <pre>
 * <b>Domain:</b>
 * -monopoly:int
 * -monument:int
 * -roadBuilding:int
 * -soldier:int
 * -yearOfPlenty:int
 * </pre>
 * 
 * @author Seth White
 *
 */
public class DevCardList {
	private int monopoly;
	private int monument;
	private int roadBuilding;
	private int soldier;
	private int yearOfPlenty;

	/**
	 * Default constructor, requires all fields
	 *
	 * @Pre no field may be null
	 * @Post result: a DevCardList
	 * @param monopoly
	 * @param monument
	 * @param roadBuilding
	 * @param soldier
	 * @param yearOfPlenty
	 */
	public DevCardList(int monopoly, int monument, int roadBuilding,
			int soldier, int yearOfPlenty) {
		super();
		this.monopoly = monopoly;
		this.monument = monument;
		this.roadBuilding = roadBuilding;
		this.soldier = soldier;
		this.yearOfPlenty = yearOfPlenty;
	}

	public int getMonopoly() {
		return monopoly;
	}

	public void setMonopoly(int monopoly) {
		this.monopoly = monopoly;
	}

	public int getMonument() {
		return monument;
	}

	public void setMonument(int monument) {
		this.monument = monument;
	}

	public int getRoadBuilding() {
		return roadBuilding;
	}

	public void setRoadBuilding(int roadBuilding) {
		this.roadBuilding = roadBuilding;
	}

	public int getSoldier() {
		return soldier;
	}

	public void setSoldier(int soldier) {
		this.soldier = soldier;
	}

	public int getYearOfPlenty() {
		return yearOfPlenty;
	}

	public void setYearOfPlenty(int yearOfPlenty) {
		this.yearOfPlenty = yearOfPlenty;
	}
	
	public void addDevCards(DevCardList newCards){
		this.setSoldier(this.getSoldier() + newCards.getSoldier());
		this.setMonument(this.getMonument() + newCards.getMonument());
		this.setRoadBuilding(this.getRoadBuilding() + newCards.getRoadBuilding());
		this.setMonopoly(this.getMonopoly() + newCards.getMonopoly());
		this.setYearOfPlenty(this.getYearOfPlenty() + newCards.getYearOfPlenty());
	}
	
	public void resetDevCards(){
		this.setMonopoly(0);
		this.setMonument(0);
		this.setRoadBuilding(0);
		this.setSoldier(0);
		this.setYearOfPlenty(0);
	}

}
