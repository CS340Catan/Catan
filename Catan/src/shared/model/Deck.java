package shared.model;

import java.util.Random;

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

	public void drawFromDeck(Player drawingPlayer) {
		int totalCardCount = this.yearOfPlenty + this.monument + this.soldier
				+ this.roadBuilding + this.monopoly;
		Random rand = new Random();
		int randomCardIndex = rand.nextInt(totalCardCount) + 1;

		/*
		 * If the development card is monument, it is added to the old cards
		 * list. If the development card is NOT monument, it is added to the new
		 * cards list.
		 */
		if (randomCardIndex - this.yearOfPlenty <= 0) {
			DevCardList newList = drawingPlayer.getNewDevCards();
			newList.setYearOfPlenty(newList.getYearOfPlenty() + 1);
			this.yearOfPlenty--;
		} else if (randomCardIndex - this.yearOfPlenty - this.monument <= 0) {
			DevCardList oldList = drawingPlayer.getOldDevCards();
			oldList.setMonument(oldList.getMonument() + 1);
			this.monument--;
		} else if (randomCardIndex - this.yearOfPlenty - this.monument
				- this.soldier <= 0) {
			DevCardList newList = drawingPlayer.getNewDevCards();

			newList.setSoldier(newList.getSoldier() + 1);

			this.soldier--;
		} else if (randomCardIndex - this.yearOfPlenty - this.monument
				- this.soldier - this.roadBuilding <= 0) {
			DevCardList newList = drawingPlayer.getNewDevCards();
			newList.setRoadBuilding(newList.getRoadBuilding() + 1);
			this.roadBuilding--;
		} else if (randomCardIndex - this.yearOfPlenty - this.monument
				- this.soldier - this.roadBuilding - this.monopoly <= 0) {
			DevCardList newList = drawingPlayer.getNewDevCards();
			newList.setMonopoly(newList.getMonopoly() + 1);
			this.monopoly--;
		}
	}

}
