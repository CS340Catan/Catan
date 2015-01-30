package client.model;

import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

/**
 * Handles all "CanDo" methods and provides access to the client model
 * 
 * <pre>
 * <b>Domain:</b>
 * -clientModel:ClientModel
 * </pre>
 * 
 * @author Seth White
 *
 */
public class ClientModelController {
	private ClientModel clientModel;

	/**
	 * Default constructor
	 * 
	 * @Pre clientModel may not be null
	 * @Post result: a ClientModel
	 * @param clientModel
	 */
	public ClientModelController(ClientModel clientModel) {
		this.clientModel = clientModel;
	}

	/**
	 * Checks if it is the given player's turn
	 * 
	 * @pre none
	 * @post boolean if it is the player's turn or not
	 * @param playerIndex
	 * @return
	 */
	public boolean isPlayerTurn(int playerIndex) {
		if (clientModel.getTurnTracker().getCurrentTurn() == playerIndex) {
			return true;
		}
		return false;
	}

	public boolean playerHasResources(int playerIndex, ResourceList resourceList) {
		if (clientModel.getPlayers()[playerIndex].getResources().contains(
				resourceList)) {
			return true;
		}
		return false;
	}
	public boolean roadExists(Road road){
		for(Road existingRoad : clientModel.getMap().getRoads()){
			if(existingRoad.checkAvailability(road)){
				return false;
			}
		}
		return true;
	}
	public boolean legitRoadPlacement(Road road){
		EdgeLocation edgeLocation = road.getLocation();
		HexLocation hexLocation = edgeLocation.getHexLoc();		
		System.out.println(edgeLocation.getDir());
		hexLocation.getNeighborLoc(edgeLocation.getDir());
		return false;
	}
	/**
	 * tests if the player can roll
	 *  
	 * @Pre it is the current turn of the player attempting to roll
	 * @Post result: a boolean reporting success/fail
	 */
	public boolean canRollNumber(int playerIndex) {
		if (isPlayerTurn(playerIndex)
				&& clientModel.getTurnTracker().getStatus() == "Rolling") {
			return true;
		}
		return false;
	}

	/**
	 * tests if the player can build a road
	 * 
	 * @Pre it is the current turn of the player attempting to build a road
	 * @Pre player has the required resources to buy the road
	 * @Pre the road is attached to another road or building
	 * @Pre the road is not over another road
	 * @Post result: a boolean reporting success/fail
	 */
	public boolean canBuildRoad(int playerIndex, Road road) {
		// TODO:HSW check location and connecting buildings/roads/pre-existing
		// buildings
		ResourceList resourceList = new ResourceList(1, 0, 0, 0, 1);
		if (isPlayerTurn(playerIndex)
				&& playerHasResources(playerIndex, resourceList)
				&& !roadExists(road)) {
			return true;
		}
		return false;

	}

	/**
	 * tests if the player can build a city
	 * 
	 * @Pre it is the current turn of the player attempting to build a city
	 * @Pre player has the required resources to buy the city
	 * @Pre the city is replacing an existing settlement
	 * @Post result: a boolean reporting success/fail
	 */
	public boolean canBuildCity(int playerIndex) {
		// TODO:HSW check if built on existing settlement
		ResourceList resourceList = new ResourceList(0, 3, 0, 2, 0);
		if (isPlayerTurn(playerIndex)
				&& playerHasResources(playerIndex, resourceList)) {
			return true;
		}
		return false;
	}

	/**
	 * tests if the player can build a settlement
	 * 
	 * @Pre it is the current turn of the player attempting to build the
	 *      settlement
	 * @Pre player has the required resources to buy the settlement
	 * @Pre Settlement is two edges away from all other settlements
	 * @Post result: a boolean reporting success/fail
	 */
	public boolean canBuildSettlement(int playerIndex) {
		// TODO: HSW check for roads/settlements/pre-existing buildings
		ResourceList resourceList = new ResourceList(1, 0, 1, 1, 1);
		if (isPlayerTurn(playerIndex)
				&& playerHasResources(playerIndex, resourceList)) {
			return true;
		}
		return false;
	}

	/**
	 * tests if the player can discard cards
	 * 
	 * @Pre the player has more than 7 cards after a 7 is rolled
	 * @Post result: a boolean reporting success/fail
	 */
	public boolean canDiscardCards(int playerIndex) {
		if (clientModel.getPlayers()[playerIndex].getResources().count() > 7
				&& !clientModel.getPlayers()[playerIndex].alreadyDiscarded()) {
			return true;
		}
		return false;
	}

	/**
	 * tests if the player can offer a player trade
	 * 
	 * @Pre It is the offering player's turn, or the player is counter-offering
	 *      after the current player has offered a trade
	 * @Pre The player has the resources to offer
	 * @Post result: a boolean reporting success/fail
	 */
	public boolean canOfferTrade(int playerIndex, ResourceList resourceList) {
		// TODO:HSW figure out how to figure out if a trade has been offered
		if (clientModel.getPlayers()[playerIndex].getResources().contains(
				resourceList)) {
			return true;
		}
		return false;

	}

	/**
	 * tests if the player can accept a player trade
	 * 
	 * @Pre it is the current turn of the player attempting to accept the trade
	 * @Pre the player has the offered resources
	 * @Pre the player has the asked for resources
	 * @Post result: a boolean reporting success/fail
	 */
	public boolean canAcceptTrade(int playerIndex, ResourceList resourceList) {
		if (isPlayerTurn(playerIndex)
				&& clientModel.getPlayers()[playerIndex].getResources()
						.contains(resourceList)) {
			return true;
		}
		return false;
	}

	/**
	 * tests if the player can maritime trade
	 * 
	 * @Pre it is the current turn of the player attempting to maritime trade
	 * @Pre the player has a settlement near a port
	 * @Pre the player has the required ratio of resources
	 * @Post result: a boolean reporting success/fail
	 */
	public boolean canMaritimeTrade(int playerIndex, ResourceList resourceList,
			int ratioNumerator) {
		// TODO:HSW check locations
		if (isPlayerTurn(playerIndex)
				&& clientModel.getPlayers()[playerIndex].getResources()
						.ofAKind(ratioNumerator)) {
			return true;
		}
		return false;
	}

	/**
	 * tests if the player can rob a player
	 * 
	 * @Pre it is the current turn of the player attempting to rob
	 * @Pre the player has just rolled a 7 or the player has just played a
	 *      soldier card
	 * @Pre the victim player is adjacent to the hex the robber is on
	 * @Post result: a boolean reporting success/fail
	 */
	public boolean canRobPlayer(HexLocation hexLocation, int playerIndex) {
		// TODO:HSW everything here
		return false;

	}

	/**
	 * tests if the player can buy a dev card
	 * 
	 * @Pre it is the current turn of the player attempting to buy the devCard
	 * @Pre player has the required resources to buy the card
	 * @Post result: a boolean reporting success/fail
	 */
	public boolean canBuyDevCard(int playerIndex) {
		ResourceList resourceList = new ResourceList(0, 1, 1, 1, 0);
		if (isPlayerTurn(playerIndex)
				&& playerHasResources(playerIndex, resourceList)) {
			return true;
		}
		return false;

	}

	/**
	 * tests if the player can play a soldier card
	 * 
	 * @Pre it is the current turn of the player attempting to play the card
	 * @Pre Current player has the card
	 * @Pre Current player has not already played a devCard this turn
	 * @Pre this dev card was not purchased this turn
	 * @Post result: a boolean reporting success/fail
	 */
	public boolean canPlaySoldierCard(HexLocation hexLocation, int playerIndex) {
		if (isPlayerTurn(playerIndex)
				&& clientModel.getPlayers()[playerIndex].getOldDevCards().getSoldier() > 0
				&& !clientModel.getPlayers()[playerIndex].hasPlayedDevCard()
				&& !clientModel.getMap().getRobber().equals(hexLocation)) { //ensures the robber isn't placed on the same tile, which is illegal, might be better to check elsewhere though
			return true;
		}
		return false;
	}

	/**
	 * tests if the player can play a year of plenty card
	 * 
	 * @Pre it is the current turn of the player attempting to play the card
	 * @Pre Current player has the card
	 * @Pre Current player has not already played a devCard this turn
	 * @Pre this dev card was not purchased this turn
	 * @Post result: a boolean reporting success/fail
	 */
	public boolean canPlayYearOfPlentyCard(int playerIndex) {
		if (isPlayerTurn(playerIndex)
				&& clientModel.getPlayers()[playerIndex].getOldDevCards().getYearOfPlenty() > 0
				&& !clientModel.getPlayers()[playerIndex].hasPlayedDevCard()) {
			return true;
		}
		return false;

	}

	/**
	 * tests if the player can play a road building card
	 * 
	 * @Pre it is the current turn of the player attempting to play the card
	 * @Pre Current player has the card
	 * @Pre Current player has not already played a devCard this turn
	 * @Pre this dev card was not purchased this turn
	 * @Post result: a boolean reporting success/fail
	 */
	public boolean canPlayRoadBuildingCard(int playerIndex, EdgeLocation edgeLocation) {
		if (isPlayerTurn(playerIndex)
				&& clientModel.getPlayers()[playerIndex].getOldDevCards().getRoadBuilding() > 0
				&& !clientModel.getPlayers()[playerIndex].hasPlayedDevCard()) {
			//TODO:HSW figure out location checking
			return true;
		}
		return false;

	}

	public ClientModel getClientModel() {
		return clientModel;
	}

	public void setClientModel(ClientModel clientModel) {
		this.clientModel = clientModel;
	}
}