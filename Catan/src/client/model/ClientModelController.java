package client.model;

import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

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
	 * Default constructor.
	 * 
	 * @Pre clientModel may not be null
	 * @Post result: a ClientModel
	 * @param clientModel
	 */
	public ClientModelController(ClientModel clientModel) {
		this.clientModel = clientModel;
	}

	/**
	 * Check if it is the given player's turn. If it is the player's turn,
	 * return true.
	 * 
	 * @pre none
	 * @post Boolean if it is the player's turn or not
	 * @param playerIndex
	 *            Player being queried.
	 * @return
	 */
	public boolean isPlayerTurn(int playerIndex) {
		if (clientModel.getTurnTracker().getCurrentTurn() == playerIndex) {
			return true;
		}
		return false;
	}

	/**
	 * Check if a player has the list of resources passed in. If the inputed
	 * resourceList is a subset of player[playerIndex], then return true.
	 * 
	 * @param playerIndex
	 *            Player being queried.
	 * @param resourceList
	 *            Resources being queried.
	 * @return
	 */
	public boolean playerHasResources(int playerIndex, ResourceList resourceList) {
		if (clientModel.getPlayers()[playerIndex].getResources().contains(
				resourceList)) {
			return true;
		}
		return false;
	}

	/**
	 * Check if a given road exists in the location inside the given road.
	 * 
	 * @param road
	 *            Road being queried.
	 * @return
	 */
	public boolean roadExists(Road road) {
		for (Road existingRoad : clientModel.getMap().getRoads()) {
			if (existingRoad.checkAvailability(road)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check to see if the road is able to be placed at the location inside the
	 * given road
	 * 
	 * @param road
	 * @return
	 */
	public boolean legitRoadPlacement(Road road) {
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
	 * Tests if the player can build a road
	 * 
	 * @Pre it is the current turn of the player attempting to build a road
	 * @Pre player has the required resources to buy the road
	 * @Pre the road is attached to another road or building
	 * @Pre the road is not over another road
	 * @Post result: a boolean reporting success/fail
	 */
	public boolean canBuildRoad(int playerIndex, Road road) {
		ResourceList requiredResourceList = new ResourceList(1, 0, 0, 0, 1);
		if (isPlayerTurn(playerIndex)
				&& playerHasResources(playerIndex, requiredResourceList)
				&& !roadExists(road)
				&& (hasConnectingBuilding(road) || hasConnectingRoad(road))) {
			return true;
		}
		return false;

	}

	/**
	 * Generically checks for cities and settlements
	 * 
	 * @param building
	 * @param roadLocation
	 * @param platformHex
	 * @return
	 */
	private boolean buildingCheckForRoadBuilding(VertexObject building,
			EdgeLocation roadLocation, HexLocation platformHex) {
		VertexLocation settlementLoc = building.getLocation();
		VertexDirection settlementDirection = settlementLoc.getDir();
		if (building.getLocation().equals(platformHex)) {

			switch (roadLocation.getDir()) {
			case NorthWest:
				if (settlementDirection.equals(VertexDirection.NorthWest)
						|| settlementDirection.equals(VertexDirection.West)) {
					return true;
				}
			case North:
				if (settlementDirection.equals(VertexDirection.NorthWest)
						|| settlementDirection
								.equals(VertexDirection.NorthEast)) {
					return true;
				}
			case NorthEast:
				if (settlementDirection.equals(VertexDirection.NorthEast)
						|| settlementDirection.equals(VertexDirection.East)) {
					return true;
				}
			case SouthEast:
				if (settlementDirection.equals(VertexDirection.East)
						|| settlementDirection
								.equals(VertexDirection.SouthEast)) {
					return true;
				}
			case South:
				if (settlementDirection.equals(VertexDirection.SouthEast)
						|| settlementDirection
								.equals(VertexDirection.SouthWest)) {
					return true;
				}
			case SouthWest:
				if (settlementDirection.equals(VertexDirection.SouthWest)
						|| settlementDirection.equals(VertexDirection.West)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasConnectingBuilding(Road road) {
		EdgeLocation roadLocation = road.getLocation();
		HexLocation platformHex = null;
		for (Hex hex : clientModel.getMap().getHexes()) {
			if (hex.getLocation().equals(roadLocation)) {
				platformHex = hex.getLocation();
			}
		}
		for (VertexObject settlement : clientModel.getMap().getSettlements()) {
			if (settlement.getOwner() == road.getOwner()) {
				return buildingCheckForRoadBuilding(settlement, roadLocation,
						platformHex);
			}
		}
		for (VertexObject city : clientModel.getMap().getCities()) {
			if (city.getOwner() == road.getOwner()) {
				return buildingCheckForRoadBuilding(city, roadLocation,
						platformHex);
			}
		}
		return false;
	}

	/**
	 * Check if there is a road that already exists that connects the queried
	 * road. If so, return true.
	 * 
	 * @param road
	 * @return
	 */
	public boolean hasConnectingRoad(Road road) {
		// TODO check for owner
		EdgeLocation roadLocation = road.getLocation();

		/*
		 * Loop through each existing road to see if an existing road connects
		 * to the road that is being queried.
		 */
		for (Road existingRoad : clientModel.getMap().getRoads()) {
			EdgeLocation existingRoadLocation = existingRoad.getLocation();
			HexLocation neighbor = roadLocation.getHexLoc().getNeighborLoc(
					roadLocation.getDir());

			/*
			 * For each different direction of the road location, check
			 * different edge positions
			 */
			switch (roadLocation.getDir()) {
			case NorthWest:
				/*
				 * Check on road's hex for connecting roads
				 */
				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc())) {
					if (existingRoadLocation.getDir().equals(
							EdgeDirection.SouthWest)
							|| existingRoadLocation.getDir().equals(
									EdgeDirection.North)) {
						return true;
					}
				}

				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc().getNeighborLoc(
								EdgeDirection.North))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.South)) {
					return true;
				}

				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc().getNeighborLoc(
								EdgeDirection.SouthWest))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.NorthEast)) {
					return true;
				}

				if (existingRoadLocation.getHexLoc().equals(neighbor)) {
					if (existingRoadLocation.getDir().equals(
							EdgeDirection.NorthEast)
							|| existingRoadLocation.getDir().equals(
									EdgeDirection.South)) {
						return true;
					}
				}

				if (existingRoadLocation.getHexLoc().equals(
						neighbor.getNeighborLoc(EdgeDirection.NorthEast))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.SouthWest)) {
					return true;
				}

				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc().getNeighborLoc(
								EdgeDirection.South))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.North)) {
					return true;
				}

			case North:
				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc())) {
					if (existingRoadLocation.getDir().equals(
							EdgeDirection.NorthWest)
							|| existingRoadLocation.getDir().equals(
									EdgeDirection.NorthEast)) {
						return true;
					}
				}

				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc().getNeighborLoc(
								EdgeDirection.NorthWest))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.SouthEast)) {
					return true;
				}

				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc().getNeighborLoc(
								EdgeDirection.NorthEast))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.SouthWest)) {
					return true;
				}

				if (existingRoadLocation.getHexLoc().equals(neighbor)) {
					if (existingRoadLocation.getDir().equals(
							EdgeDirection.SouthWest)
							|| existingRoadLocation.getDir().equals(
									EdgeDirection.SouthEast)) {
						return true;
					}
				}

				if (existingRoadLocation.getHexLoc().equals(
						neighbor.getNeighborLoc(EdgeDirection.SouthWest))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.NorthEast)) {
					return true;
				}

				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc().getNeighborLoc(
								EdgeDirection.SouthEast))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.SouthWest)) {
					return true;
				}

			case NorthEast:
				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc())) {
					if (existingRoadLocation.getDir().equals(
							EdgeDirection.North)
							|| existingRoadLocation.getDir().equals(
									EdgeDirection.SouthEast)) {
						return true;
					}
				}

				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc().getNeighborLoc(
								EdgeDirection.North))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.South)) {
					return true;
				}

				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc().getNeighborLoc(
								EdgeDirection.SouthEast))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.NorthWest)) {
					return true;
				}

				if (existingRoadLocation.getHexLoc().equals(neighbor)) {
					if (existingRoadLocation.getDir().equals(
							EdgeDirection.South)
							|| existingRoadLocation.getDir().equals(
									EdgeDirection.NorthWest)) {
						return true;
					}
				}

				if (existingRoadLocation.getHexLoc().equals(
						neighbor.getNeighborLoc(EdgeDirection.South))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.North)) {
					return true;
				}

				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc().getNeighborLoc(
								EdgeDirection.NorthWest))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.SouthEast)) {
					return true;
				}

			case SouthWest:
				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc())) {
					if (existingRoadLocation.getDir().equals(
							EdgeDirection.NorthWest)
							|| existingRoadLocation.getDir().equals(
									EdgeDirection.South)) {
						return true;
					}
				}

				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc().getNeighborLoc(
								EdgeDirection.NorthWest))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.SouthEast)) {
					return true;
				}

				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc().getNeighborLoc(
								EdgeDirection.South))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.North)) {
					return true;
				}

				if (existingRoadLocation.getHexLoc().equals(neighbor)) {
					if (existingRoadLocation.getDir().equals(
							EdgeDirection.North)
							|| existingRoadLocation.getDir().equals(
									EdgeDirection.SouthEast)) {
						return true;
					}
				}

				if (existingRoadLocation.getHexLoc().equals(
						neighbor.getNeighborLoc(EdgeDirection.North))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.South)) {
					return true;
				}

				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc().getNeighborLoc(
								EdgeDirection.SouthEast))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.NorthWest)) {
					return true;
				}

			case South:
				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc())) {
					if (existingRoadLocation.getDir().equals(
							EdgeDirection.SouthEast)
							|| existingRoadLocation.getDir().equals(
									EdgeDirection.SouthWest)) {
						return true;
					}
				}

				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc().getNeighborLoc(
								EdgeDirection.SouthEast))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.NorthWest)) {
					return true;
				}

				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc().getNeighborLoc(
								EdgeDirection.SouthWest))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.NorthEast)) {
					return true;
				}

				if (existingRoadLocation.getHexLoc().equals(neighbor)) {
					if (existingRoadLocation.getDir().equals(
							EdgeDirection.NorthWest)
							|| existingRoadLocation.getDir().equals(
									EdgeDirection.NorthEast)) {
						return true;
					}
				}

				if (existingRoadLocation.getHexLoc().equals(
						neighbor.getNeighborLoc(EdgeDirection.NorthWest))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.SouthEast)) {
					return true;
				}

				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc().getNeighborLoc(
								EdgeDirection.NorthEast))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.SouthWest)) {
					return true;
				}

			case SouthEast:
				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc())) {
					if (existingRoadLocation.getDir().equals(
							EdgeDirection.NorthEast)
							|| existingRoadLocation.getDir().equals(
									EdgeDirection.South)) {
						return true;
					}
				}

				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc().getNeighborLoc(
								EdgeDirection.NorthEast))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.SouthWest)) {
					return true;
				}

				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc().getNeighborLoc(
								EdgeDirection.South))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.North)) {
					return true;
				}

				if (existingRoadLocation.getHexLoc().equals(neighbor)) {
					if (existingRoadLocation.getDir().equals(
							EdgeDirection.North)
							|| existingRoadLocation.getDir().equals(
									EdgeDirection.SouthWest)) {
						return true;
					}
				}

				if (existingRoadLocation.getHexLoc().equals(
						neighbor.getNeighborLoc(EdgeDirection.North))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.South)) {
					return true;
				}

				if (existingRoadLocation.getHexLoc().equals(
						roadLocation.getHexLoc().getNeighborLoc(
								EdgeDirection.SouthWest))
						&& existingRoadLocation.getDir().equals(
								EdgeDirection.NorthEast)) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean preexistingBuilding(VertexObject building,
			boolean dontCheckOwner) {
		HexLocation platformHex = null;
		for (Hex hex : clientModel.getMap().getHexes()) {
			if (hex.equals(building.getLocation().getHexLoc())) {
				platformHex = hex.getLocation();
			}
		}
		for (VertexObject settlement : clientModel.getMap().getSettlements()) {
			if (building.getOwner() == settlement.getOwner() || dontCheckOwner) {
				HexLocation settlementLocation = settlement.getLocation()
						.getHexLoc();
				if (settlementLocation.equals(platformHex)) {
					if (building.getLocation().getDir()
							.equals(settlement.getLocation().getDir())) {
						return true;
					}
				}
			}
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
	public boolean canBuildCity(VertexObject city) {
		ResourceList resourceList = new ResourceList(0, 3, 0, 2, 0);
		if (isPlayerTurn(city.getOwner())
				&& playerHasResources(city.getOwner(), resourceList)
				&& preexistingBuilding(city, false)) {
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
	public boolean canBuildSettlement(VertexObject settlement) {
		//TODO:check adjacent buildings/road
		int playerIndex = settlement.getOwner();
		ResourceList resourceList = new ResourceList(1, 0, 1, 1, 1);
		if (isPlayerTurn(playerIndex)
				&& playerHasResources(playerIndex, resourceList)
				&& !preexistingBuilding(settlement, true)) {
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
				&& clientModel.getPlayers()[playerIndex].getOldDevCards()
						.getSoldier() > 0
				&& !clientModel.getPlayers()[playerIndex].hasPlayedDevCard()
				&& !clientModel.getMap().getRobber().equals(hexLocation)) { // ensures
																			// the
																			// robber
																			// isn't
																			// placed
																			// on
																			// the
																			// same
																			// tile,
																			// which
																			// is
																			// illegal,
																			// might
																			// be
																			// better
																			// to
																			// check
																			// elsewhere
																			// though
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
				&& clientModel.getPlayers()[playerIndex].getOldDevCards()
						.getYearOfPlenty() > 0
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
	public boolean canPlayRoadBuildingCard(int playerIndex,
			EdgeLocation edgeLocation) {
		if (isPlayerTurn(playerIndex)
				&& clientModel.getPlayers()[playerIndex].getOldDevCards()
						.getRoadBuilding() > 0
				&& !clientModel.getPlayers()[playerIndex].hasPlayedDevCard()) {
			// TODO:HSW figure out location checking
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