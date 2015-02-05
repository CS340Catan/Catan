package client.model;

import shared.locations.*;

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
	private boolean isPlayerTurn(int playerIndex) {
		if (clientModel.getTurnTracker().getCurrentTurn() == playerIndex) {
			return true;
		}
		return false;
	}

	/**
	 * Check to see if the road is able to be placed at the location inside the
	 * given road
	 * 
	 * @param road
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean legitRoadPlacement(Road road) {
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
	 * @Pre player has available road piece
	 * @Post result: a boolean reporting success/fail
	 */
	public boolean canBuildRoad(int playerIndex, Road road, boolean usingDevCard) {
		ResourceList requiredResourceList = new ResourceList(1, 0, 0, 0, 1);
		/*
		 * Check Pre-conditions. I.e. check if it is the current player's turn,
		 * if the player has the required resources, the road is not covering
		 * another road, the road is attached to a road or a building, and if
		 * the player has an available road piece.
		 */

		if (isPlayerTurn(playerIndex)
				&& (playerHasResources(playerIndex, requiredResourceList) || usingDevCard)
				&& !roadExists(road)
				&& (hasConnectingBuilding(road) || hasConnectingRoad(road))
				&& playerHasAvailableRoadPiece(playerIndex)) {
			return true;
		}
		return false;
	}

	private boolean playerHasResources(int playerIndex,
			ResourceList resourceList) {
		if (clientModel.getPlayers()[playerIndex].getResources().contains(
				resourceList)) {
			return true;
		}
		return false;
	}

	private boolean roadExists(Road newRoad) {
		for (Road existingRoad : clientModel.getMap().getRoads()) {
			if (!existingRoad.checkAvailability(newRoad)) {
				// System.out.println("Existing Road: " +
				// existingRoad.toString());
				// System.out.println("New Road: " + newRoad.toString());
				return true;
			}
		}
		return false;
	}

	private int roadOwner(Road road) {
		/*
		 * Find owner of road given inputed road. If none, return -1 index.
		 * Else, return index of owner.
		 */
		for (Road existingRoad : clientModel.getMap().getRoads()) {
			if (!existingRoad.checkAvailability(road)) {
				return existingRoad.getOwner();
			}
		}
		return -1;
	}

	private boolean hasConnectingBuilding(Road road) {
		EdgeLocation roadLocation = road.getLocation();
		HexLocation roadHexLocation = road.getLocation()
				.getNormalizedLocation().getHexLoc();
		HexLocation platformHex = roadHexLocation;

		for (VertexObject settlement : clientModel.getMap().getSettlements()) {
			if (settlement.getOwner() == road.getOwner()) {
				if (buildingExistsForRoad(settlement, roadLocation, platformHex)) {
					return true;
				}
			}
		}

		for (VertexObject city : clientModel.getMap().getCities()) {
			if (city.getOwner() == road.getOwner()) {
				if (buildingExistsForRoad(city, roadLocation, platformHex)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean buildingExistsForRoad(VertexObject building,
			EdgeLocation roadLocation, HexLocation platformHex) {
		VertexLocation settlementLoc = building.getLocation()
				.getNormalizedLocation();
		VertexDirection settlementDirection = settlementLoc.getDir();
		if (building.getLocation().getHexLoc().equals(platformHex)) {
			switch (roadLocation.getDir()) {
			case NorthWest:
				if (settlementDirection.equals(VertexDirection.NorthWest)
						|| settlementDirection.equals(VertexDirection.West)) {
					return true;
				}
				break;
			case North:
				if (settlementDirection.equals(VertexDirection.NorthWest)
						|| settlementDirection
								.equals(VertexDirection.NorthEast)) {
					return true;
				}
				break;
			case NorthEast:
				if (settlementDirection.equals(VertexDirection.NorthEast)
						|| settlementDirection.equals(VertexDirection.East)) {
					return true;
				}
				break;
			case SouthEast:
				if (settlementDirection.equals(VertexDirection.East)
						|| settlementDirection
								.equals(VertexDirection.SouthEast)) {
					return true;
				}
				break;
			case South:
				if (settlementDirection.equals(VertexDirection.SouthEast)
						|| settlementDirection
								.equals(VertexDirection.SouthWest)) {
					return true;
				}
				break;
			case SouthWest:
				if (settlementDirection.equals(VertexDirection.SouthWest)
						|| settlementDirection.equals(VertexDirection.West)) {
					return true;
				}
				break;
			}
		}
		return false;
	}

	private boolean hasConnectingRoad(Road road) {
		HexLocation roadHexLoc = road.getLocation().getHexLoc();
		EdgeDirection roadEdgeDir = road.getLocation().getDir();
		HexLocation roadNeighbor = roadHexLoc.getNeighborLoc(roadEdgeDir);

		EdgeLocation testLocation = null;
		Road testRoad = null;

		switch (roadEdgeDir) {
		case NorthWest:
			testLocation = new EdgeLocation(roadHexLoc, EdgeDirection.North);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadHexLoc, EdgeDirection.SouthWest);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadNeighbor,
					EdgeDirection.NorthEast);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadNeighbor, EdgeDirection.South);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}
			break;
		case North:
			testLocation = new EdgeLocation(roadHexLoc, EdgeDirection.NorthWest);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadHexLoc, EdgeDirection.NorthEast);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadNeighbor,
					EdgeDirection.SouthWest);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadNeighbor,
					EdgeDirection.SouthEast);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}
			break;
		case NorthEast:
			testLocation = new EdgeLocation(roadHexLoc, EdgeDirection.North);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadHexLoc, EdgeDirection.SouthEast);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadNeighbor, EdgeDirection.South);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadNeighbor,
					EdgeDirection.NorthWest);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}
			break;
		case SouthWest:
			testLocation = new EdgeLocation(roadHexLoc, EdgeDirection.South);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadHexLoc, EdgeDirection.NorthWest);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadNeighbor, EdgeDirection.North);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadNeighbor,
					EdgeDirection.SouthEast);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}
			break;
		case South:
			testLocation = new EdgeLocation(roadHexLoc, EdgeDirection.SouthWest);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadHexLoc, EdgeDirection.SouthEast);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadNeighbor,
					EdgeDirection.NorthWest);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadNeighbor,
					EdgeDirection.NorthEast);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}
			break;
		case SouthEast:
			testLocation = new EdgeLocation(roadHexLoc, EdgeDirection.South);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadHexLoc, EdgeDirection.NorthEast);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadNeighbor, EdgeDirection.North);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadNeighbor,
					EdgeDirection.SouthWest);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}
			break;
		}

		return false;
	}

	private boolean playerHasAvailableRoadPiece(int playerIndex) {
		if (clientModel.getPlayers()[playerIndex].getRoads() > 0) {
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
	 * looks in a specific neighbor hex for a building at a given location
	 * 
	 * @param edgeDirection
	 * @param vertexDirection
	 * @param platformHex
	 * @return
	 */
	private boolean neighborHasAdjacentBuilding(EdgeDirection edgeDirection,
			VertexDirection vertexDirection, HexLocation platformHex) {
		for (VertexObject existingSettlement : clientModel.getMap()
				.getSettlements()) {
			if (platformHex.getNeighborLoc(edgeDirection).equals(
					existingSettlement.getLocation().getNormalizedLocation()
							.getHexLoc())
					&& existingSettlement.getLocation().getNormalizedLocation()
							.getDir().equals(vertexDirection)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns false if adjacent buildings exist, name can be changed, wasn't
	 * feeling very creative
	 * 
	 * @param existingBuilding
	 * @param newBuilding
	 * @param platformHex
	 * @return
	 */
	private boolean crawlForBuildings(VertexObject existingBuilding,
			VertexObject newBuilding, HexLocation platformHex) {

		if (existingBuilding.getLocation().getHexLoc().equals(platformHex)) {
			VertexDirection existingBuildingDirection = existingBuilding
					.getLocation().getNormalizedLocation().getDir();

			switch (newBuilding.getLocation().getDir()) {
			case NorthWest:
				if (existingBuildingDirection.equals(VertexDirection.West)
						|| existingBuildingDirection
								.equals(VertexDirection.NorthEast)
						|| neighborHasAdjacentBuilding(EdgeDirection.NorthWest,
								VertexDirection.NorthEast, platformHex)) {
					return false;
				}
				break;
			case East:
				if (existingBuildingDirection.equals(VertexDirection.NorthEast)
						|| existingBuildingDirection
								.equals(VertexDirection.SouthEast)
						|| neighborHasAdjacentBuilding(EdgeDirection.NorthEast,
								VertexDirection.SouthEast, platformHex)) {
					return false;
				}
				break;
			case NorthEast:
				if (existingBuildingDirection.equals(VertexDirection.NorthWest)
						|| existingBuildingDirection
								.equals(VertexDirection.East)
						|| neighborHasAdjacentBuilding(EdgeDirection.NorthEast,
								VertexDirection.NorthWest, platformHex)) {
					return false;
				}
				break;
			case SouthEast:
				if (existingBuildingDirection.equals(VertexDirection.East)
						|| existingBuildingDirection
								.equals(VertexDirection.SouthWest)
						|| neighborHasAdjacentBuilding(EdgeDirection.SouthEast,
								VertexDirection.SouthWest, platformHex)) {
					return false;
				}
				break;
			case West:
				if (existingBuildingDirection.equals(VertexDirection.NorthWest)
						|| existingBuildingDirection
								.equals(VertexDirection.SouthWest)
						|| neighborHasAdjacentBuilding(EdgeDirection.SouthWest,
								VertexDirection.NorthWest, platformHex)) {
					return false;
				}
			case SouthWest:
				if (existingBuildingDirection.equals(VertexDirection.West)
						|| existingBuildingDirection
								.equals(VertexDirection.SouthEast)
						|| neighborHasAdjacentBuilding(EdgeDirection.SouthWest,
								VertexDirection.SouthEast, platformHex)) {
					return false;
				}
				break;
			}
		}

		return true;
	}

	/**
	 * Returns true if there are no adjacent buildings to the proposed new
	 * settlement
	 * 
	 * @param newSettlement
	 * @return
	 */
	private boolean noAdjacentBuildings(VertexObject newSettlement) {
		HexLocation platformHex = newSettlement.getLocation().getHexLoc();
		for (VertexObject existingSettlement : clientModel.getMap()
				.getSettlements()) {
			if (crawlForBuildings(existingSettlement, newSettlement,
					platformHex)) {
				return true;
			}
		}
		for (VertexObject existingCity : clientModel.getMap().getCities()) {
			if (crawlForBuildings(existingCity, newSettlement, platformHex)) {
				return true;
			}
		}
		return false;
	}

	private boolean roadTouchingNewSettlement(VertexObject newSettlement) {
		HexLocation platformHex = null;
		for (Hex hex : clientModel.getMap().getHexes()) {
			if (hex.getLocation().equals(
					newSettlement.getLocation().getHexLoc())) {
				platformHex = hex.getLocation();
			}
			for (Road road : clientModel.getMap().getRoads()) {
				if (road.getLocation().getNormalizedLocation().getHexLoc()
						.equals(platformHex)
						&& road.getOwner() == newSettlement.getOwner()) {
					switch (newSettlement.getLocation().getDir()) {
					case NorthWest:
						if (road.getLocation().getDir()
								.equals(EdgeDirection.North)
								|| road.getLocation().getDir()
										.equals(EdgeDirection.NorthWest)) {
							return true;
						}
						break;
					case East:
						if (road.getLocation().getDir()
								.equals(EdgeDirection.NorthWest)
								|| road.getLocation().getDir()
										.equals(EdgeDirection.SouthWest)) {
							return true;
						}
						break;
					case NorthEast:
						if (road.getLocation().getDir()
								.equals(EdgeDirection.North)
								|| road.getLocation().getDir()
										.equals(EdgeDirection.NorthEast)) {
							return true;
						}
						break;
					case SouthEast:
						if (road.getLocation().getDir()
								.equals(EdgeDirection.SouthEast)
								|| road.getLocation().getDir()
										.equals(EdgeDirection.South)) {
							return true;
						}
						break;
					case West:
						if (road.getLocation().getDir()
								.equals(EdgeDirection.SouthWest)
								|| road.getLocation().getDir()
										.equals(EdgeDirection.NorthWest)) {
							return true;
						}
						break;
					case SouthWest:
						if (road.getLocation().getDir()
								.equals(EdgeDirection.South)
								|| road.getLocation().getDir()
										.equals(EdgeDirection.NorthWest)) {
							return true;
						}
						break;
					}
				}
			}
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
		int playerIndex = settlement.getOwner();
		ResourceList resourceList = new ResourceList(1, 0, 1, 1, 1);

		// boolean thing = playerHasResources(playerIndex, resourceList);
		// boolean thing2 = !preexistingBuilding(settlement, true);
		// boolean thing3 = noAdjacentBuildings(settlement);
		// boolean thing4 = roadTouchingNewSettlement(settlement);

		if (isPlayerTurn(playerIndex)
				&& playerHasResources(playerIndex, resourceList)
				&& !preexistingBuilding(settlement, true)
				&& noAdjacentBuildings(settlement)
				&& roadTouchingNewSettlement(settlement)) {
			return true;
		}
		return false;
	}

	/**
	 * checks that a settlement exists on the proposed city spot
	 * 
	 * @param building
	 * @param dontCheckOwner
	 * @return
	 */
	private boolean preexistingBuilding(VertexObject building,
			boolean dontCheckOwner) {
		HexLocation platformHex = null;
		for (Hex hex : clientModel.getMap().getHexes()) {
			if (hex.equals(building.getLocation().getNormalizedLocation()
					.getHexLoc())) {
				platformHex = hex.getLocation();
			}
		}
		for (VertexObject settlement : clientModel.getMap().getSettlements()) {
			if (building.getOwner() == settlement.getOwner() || dontCheckOwner) {
				HexLocation settlementLocation = settlement.getLocation()
						.getNormalizedLocation().getHexLoc();
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
		if (isPlayerTurn(playerIndex)
				&& clientModel.getPlayers()[playerIndex].getResources()
						.contains(resourceList)) {
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

	private boolean buildingOnPort(int playerIndex, VertexObject building) {
		if (building.getOwner() == playerIndex) {
			for (Port port : clientModel.getMap().getPorts()) {
				if (building.getLocation().getNormalizedLocation().getHexLoc()
						.equals(port.getLocation())) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean playerOnPort(int playerIndex) {
		for (VertexObject settlement : clientModel.getMap().getSettlements()) {
			return buildingOnPort(playerIndex, settlement);
		}
		for (VertexObject city : clientModel.getMap().getCities()) {
			return buildingOnPort(playerIndex, city);
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
		if (isPlayerTurn(playerIndex)
				&& clientModel.getPlayers()[playerIndex].getResources()
						.ofAKind(ratioNumerator) && playerOnPort(playerIndex)) {
			return true;
		}
		return false;
	}

	private boolean playerTouchingRobber(int robbedPlayer,
			HexLocation robberLocation) {
		for (VertexObject settlement : clientModel.getMap().getSettlements()) {
			if (settlement.getOwner() == robbedPlayer
					&& settlement.getLocation().getNormalizedLocation()
							.getHexLoc().equals(robberLocation)) {
				return true;
			}
		}
		for (VertexObject city : clientModel.getMap().getCities()) {
			if (city.getOwner() == robbedPlayer
					&& city.getLocation().getNormalizedLocation().getHexLoc()
							.equals(robberLocation)) {
				return true;
			}
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
	public boolean canRobPlayer(HexLocation robberLocation, int robbingPlayer,
			int robbedPlayer) {
		if (isPlayerTurn(robbingPlayer)
				&& clientModel.getTurnTracker().getStatus().equals("Robbing")
				&& playerTouchingRobber(robbedPlayer, robberLocation)) {
			return true;
		}
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
	public boolean canPlayRoadBuildingCard(Road road) {
		int playerIndex = road.getOwner();
		if (isPlayerTurn(playerIndex)
				&& clientModel.getPlayers()[playerIndex].getOldDevCards()
						.getRoadBuilding() > 0
				&& !clientModel.getPlayers()[playerIndex].hasPlayedDevCard()) {
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