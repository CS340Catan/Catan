package client.model;

public class Deck {

	private int yearOfPlenty;
	private int monument;
	private int soldier;
	private int roadBuilding;
	private int monopoly;

	public Deck(int yearOfPlenty, int monument, int soldier, int roadBuilding,
			int monopoly) {
		this.yearOfPlenty = yearOfPlenty;
		this.monument = monument;
		this.soldier = soldier;
		this.roadBuilding = roadBuilding;
		this.monopoly = monopoly;
	}

	public boolean hasDevCard() {
		if ((yearOfPlenty + monopoly + monument + soldier + roadBuilding) == 0) {
			return false;
		}
		return true;
	}

	public int getYearOfPlenty() {
		return yearOfPlenty;
	}

	public void setYearOfPlenty(int yearOfPlenty) {
		this.yearOfPlenty = yearOfPlenty;
	}

	public int getMonument() {
		return monument;
	}

	public void setMonument(int monument) {
		this.monument = monument;
	}

	public int getSoldier() {
		return soldier;
	}

	public void setSoldier(int soldier) {
		this.soldier = soldier;
	}

	public int getRoadBuilding() {
		return roadBuilding;
	}

	public void setRoadBuilding(int roadBuilding) {
		this.roadBuilding = roadBuilding;
	}

	public int getMonopoly() {
		return monopoly;
	}

	public void setMonopoly(int monopoly) {
		this.monopoly = monopoly;
	}

}
