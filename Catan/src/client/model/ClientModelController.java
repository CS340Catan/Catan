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
	public boolean isPlayerTurn(int playerIndex) {
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
		if (isPlayerTurn(playerIndex) && clientModel.getTurnTracker().getStatus() == "rolling") {
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

		if (isPlayerTurn(playerIndex) && (playerHasResources(playerIndex, requiredResourceList) || usingDevCard) && !roadExists(road)
				&& (hasConnectingBuilding(road) || hasConnectingRoad(road)) && playerHasAvailableRoadPiece(playerIndex)
				&& clientModel.getTurnTracker().getStatus().equals("playing")) {
			return true;
		}
		return false;
	}

	private boolean playerHasResources(int playerIndex, ResourceList resourceList) {
		if (clientModel.getPlayers()[playerIndex].getResources().contains(resourceList)) {
			return true;
		}
		return false;
	}

	private boolean roadExists(Road newRoad) {
		for (Road existingRoad : clientModel.getMap().getRoads()) {
			if (!existingRoad.isNotEquivalent(newRoad)) {
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
			if (!existingRoad.isNotEquivalent(road)) {
				return existingRoad.getOwner();
			}
		}
		return -1;
	}

	private boolean hasConnectingBuilding(Road road) {
		HexLocation platformHex = road.getLocation().getHexLoc();

		for (VertexObject settlement : clientModel.getMap().getSettlements()) {
			if (settlement.getOwner() == road.getOwner()) {
				if (buildingExistsForRoad(settlement, road, platformHex)) {
					return true;
				}
			}
		}

		for (VertexObject city : clientModel.getMap().getCities()) {
			if (city.getOwner() == road.getOwner()) {
				if (buildingExistsForRoad(city, road, platformHex)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean buildingExistsForRoad(VertexObject existingBuilding, Road road, HexLocation platformHex) {
		VertexObject testObject = null;
		VertexLocation testLocation = null;
		switch (road.getLocation().getDir()) {
		case NorthWest:
			testLocation = new VertexLocation(platformHex, VertexDirection.NorthWest);
			testObject = new VertexObject(road.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testObject)) {
				return true;
			}
			testLocation = new VertexLocation(platformHex, VertexDirection.West);
			testObject = new VertexObject(road.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testObject)) {
				return true;
			}
			break;
		case North:
			testLocation = new VertexLocation(platformHex, VertexDirection.NorthEast);
			testObject = new VertexObject(road.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testObject)) {
				return true;
			}
			testLocation = new VertexLocation(platformHex, VertexDirection.NorthWest);
			testObject = new VertexObject(road.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testObject)) {
				return true;
			}
			break;
		case NorthEast:
			testLocation = new VertexLocation(platformHex, VertexDirection.NorthEast);
			testObject = new VertexObject(road.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testObject)) {
				return true;
			}
			testLocation = new VertexLocation(platformHex, VertexDirection.East);
			testObject = new VertexObject(road.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testObject)) {
				return true;
			}
			break;
		case SouthEast:
			testLocation = new VertexLocation(platformHex, VertexDirection.SouthEast);
			testObject = new VertexObject(road.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testObject)) {
				return true;
			}
			testLocation = new VertexLocation(platformHex, VertexDirection.East);
			testObject = new VertexObject(road.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testObject)) {
				return true;
			}
			break;
		case South:
			testLocation = new VertexLocation(platformHex, VertexDirection.SouthEast);
			testObject = new VertexObject(road.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testObject)) {
				return true;
			}
			testLocation = new VertexLocation(platformHex, VertexDirection.SouthWest);
			testObject = new VertexObject(road.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testObject)) {
				return true;
			}
			break;
		case SouthWest:
			testLocation = new VertexLocation(platformHex, VertexDirection.West);
			testObject = new VertexObject(road.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testObject)) {
				return true;
			}
			testLocation = new VertexLocation(platformHex, VertexDirection.SouthWest);
			testObject = new VertexObject(road.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testObject)) {
				return true;
			}
			break;
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

			testLocation = new EdgeLocation(roadNeighbor, EdgeDirection.NorthEast);
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

			testLocation = new EdgeLocation(roadNeighbor, EdgeDirection.SouthWest);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadNeighbor, EdgeDirection.SouthEast);
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

			testLocation = new EdgeLocation(roadNeighbor, EdgeDirection.NorthWest);
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

			testLocation = new EdgeLocation(roadNeighbor, EdgeDirection.SouthEast);
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

			testLocation = new EdgeLocation(roadNeighbor, EdgeDirection.NorthWest);
			testRoad = new Road(road.getOwner(), testLocation);
			if (road.getOwner() == roadOwner(testRoad)) {
				return true;
			}

			testLocation = new EdgeLocation(roadNeighbor, EdgeDirection.NorthEast);
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

			testLocation = new EdgeLocation(roadNeighbor, EdgeDirection.SouthWest);
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
		if (isPlayerTurn(city.getOwner()) && playerHasResources(city.getOwner(), resourceList) && preexistingSettlement(city, false)
				&& clientModel.getTurnTracker().getStatus().equals("playing")) {
			return true;
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
	private boolean hasAdjacentVertexObject(VertexObject existingBuilding, VertexObject newBuilding, HexLocation platformHex) {
		VertexObject testBuilding = null;
		HexLocation neighborHex = null;
		VertexLocation testLocation = null;
		switch (newBuilding.getLocation().getDir()) {
		case NorthWest:
			testLocation = new VertexLocation(newBuilding.getLocation().getHexLoc(), VertexDirection.NorthEast);
			testBuilding = new VertexObject(newBuilding.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testBuilding)) {
				return true;
			}
			testLocation = new VertexLocation(newBuilding.getLocation().getHexLoc(), VertexDirection.West);
			testBuilding = new VertexObject(newBuilding.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testBuilding)) {
				return true;
			}
			neighborHex = newBuilding.getLocation().getHexLoc().getNeighborLoc(EdgeDirection.NorthWest);
			testLocation = new VertexLocation(neighborHex, VertexDirection.NorthEast);
			testBuilding = new VertexObject(newBuilding.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testBuilding)) {
				return true;
			}
			break;
		case NorthEast:
			testLocation = new VertexLocation(newBuilding.getLocation().getHexLoc(), VertexDirection.NorthWest);
			testBuilding = new VertexObject(newBuilding.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testBuilding)) {
				return true;
			}
			testLocation = new VertexLocation(newBuilding.getLocation().getHexLoc(), VertexDirection.East);
			testBuilding = new VertexObject(newBuilding.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testBuilding)) {
				return true;
			}
			neighborHex = newBuilding.getLocation().getHexLoc().getNeighborLoc(EdgeDirection.NorthEast);
			testLocation = new VertexLocation(neighborHex, VertexDirection.NorthWest);
			testBuilding = new VertexObject(newBuilding.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testBuilding)) {
				return true;
			}
			break;
		case East:
			testLocation = new VertexLocation(newBuilding.getLocation().getHexLoc(), VertexDirection.NorthEast);
			testBuilding = new VertexObject(newBuilding.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testBuilding)) {
				return true;
			}
			testLocation = new VertexLocation(newBuilding.getLocation().getHexLoc(), VertexDirection.SouthEast);
			testBuilding = new VertexObject(newBuilding.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testBuilding)) {
				return true;
			}
			neighborHex = newBuilding.getLocation().getHexLoc().getNeighborLoc(EdgeDirection.NorthEast);
			testLocation = new VertexLocation(neighborHex, VertexDirection.SouthEast);
			testBuilding = new VertexObject(newBuilding.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testBuilding)) {
				return true;
			}
			break;
		case SouthEast:
			testLocation = new VertexLocation(newBuilding.getLocation().getHexLoc(), VertexDirection.East);
			testBuilding = new VertexObject(newBuilding.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testBuilding)) {
				return true;
			}
			testLocation = new VertexLocation(newBuilding.getLocation().getHexLoc(), VertexDirection.SouthWest);
			testBuilding = new VertexObject(newBuilding.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testBuilding)) {
				return true;
			}
			neighborHex = newBuilding.getLocation().getHexLoc().getNeighborLoc(EdgeDirection.SouthEast);
			testLocation = new VertexLocation(neighborHex, VertexDirection.SouthWest);
			testBuilding = new VertexObject(newBuilding.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testBuilding)) {
				return true;
			}
			break;
		case SouthWest:
			testLocation = new VertexLocation(newBuilding.getLocation().getHexLoc(), VertexDirection.West);
			testBuilding = new VertexObject(newBuilding.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testBuilding)) {
				return true;
			}
			testLocation = new VertexLocation(newBuilding.getLocation().getHexLoc(), VertexDirection.SouthEast);
			testBuilding = new VertexObject(newBuilding.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testBuilding)) {
				return true;
			}
			neighborHex = newBuilding.getLocation().getHexLoc().getNeighborLoc(EdgeDirection.SouthWest);
			testLocation = new VertexLocation(neighborHex, VertexDirection.SouthEast);
			testBuilding = new VertexObject(newBuilding.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testBuilding)) {
				return true;
			}
			break;
		case West:
			testLocation = new VertexLocation(newBuilding.getLocation().getHexLoc(), VertexDirection.NorthWest);
			testBuilding = new VertexObject(newBuilding.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testBuilding)) {
				return true;
			}
			testLocation = new VertexLocation(newBuilding.getLocation().getHexLoc(), VertexDirection.SouthWest);
			testBuilding = new VertexObject(newBuilding.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testBuilding)) {
				return true;
			}
			neighborHex = newBuilding.getLocation().getHexLoc().getNeighborLoc(EdgeDirection.NorthWest);
			testLocation = new VertexLocation(neighborHex, VertexDirection.SouthWest);
			testBuilding = new VertexObject(newBuilding.getOwner(), testLocation);
			if (existingBuilding.isEquivalent(testBuilding)) {
				return true;
			}
			break;
		}

		return false;
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
		for (VertexObject existingSettlement : clientModel.getMap().getSettlements()) {
			if (hasAdjacentVertexObject(existingSettlement, newSettlement, platformHex)) {
				return false;
			}
		}
		for (VertexObject existingCity : clientModel.getMap().getCities()) {
			if (hasAdjacentVertexObject(existingCity, newSettlement, platformHex)) {
				return false;
			}
		}
		return true;
	}

	private boolean roadTouchingNewSettlement(VertexObject newSettlement) {
		VertexDirection newSettlementDirection = newSettlement.getLocation().getDir();

		for (Road existingRoad : clientModel.getMap().getRoads()) {
			if (existingRoad.getOwner() == newSettlement.getOwner()) {
				HexLocation newSettlementLocation = newSettlement.getLocation().getHexLoc();
				HexLocation roadNeighbor = null;
				EdgeLocation testLocation = null;
				Road testRoad = null;

				switch (newSettlementDirection) {
				case NorthWest:
					roadNeighbor = newSettlementLocation.getNeighborLoc(EdgeDirection.NorthWest);
					testLocation = new EdgeLocation(newSettlementLocation, EdgeDirection.NorthWest);
					testRoad = new Road(existingRoad.getOwner(), testLocation);
					if (!existingRoad.isNotEquivalent(testRoad)) {
						return true;
					}

					testLocation = new EdgeLocation(newSettlementLocation, EdgeDirection.North);
					testRoad = new Road(existingRoad.getOwner(), testLocation);
					if (!existingRoad.isNotEquivalent(testRoad)) {
						return true;
					}

					testLocation = new EdgeLocation(roadNeighbor, EdgeDirection.NorthEast);
					testRoad = new Road(existingRoad.getOwner(), testLocation);
					if (!existingRoad.isNotEquivalent(testRoad)) {
						return true;
					}
					break;
				case East:
					roadNeighbor = newSettlementLocation.getNeighborLoc(EdgeDirection.NorthEast);
					testLocation = new EdgeLocation(newSettlementLocation, EdgeDirection.NorthEast);
					testRoad = new Road(existingRoad.getOwner(), testLocation);
					if (!existingRoad.isNotEquivalent(testRoad)) {
						return true;
					}

					testLocation = new EdgeLocation(newSettlementLocation, EdgeDirection.SouthEast);
					testRoad = new Road(existingRoad.getOwner(), testLocation);
					if (!existingRoad.isNotEquivalent(testRoad)) {
						return true;
					}

					testLocation = new EdgeLocation(roadNeighbor, EdgeDirection.South);
					testRoad = new Road(existingRoad.getOwner(), testLocation);
					if (!existingRoad.isNotEquivalent(testRoad)) {
						return true;
					}
					break;
				case NorthEast:
					roadNeighbor = newSettlementLocation.getNeighborLoc(EdgeDirection.NorthEast);
					testLocation = new EdgeLocation(newSettlementLocation, EdgeDirection.NorthEast);
					testRoad = new Road(existingRoad.getOwner(), testLocation);
					if (!existingRoad.isNotEquivalent(testRoad)) {
						return true;
					}

					testLocation = new EdgeLocation(newSettlementLocation, EdgeDirection.North);
					testRoad = new Road(existingRoad.getOwner(), testLocation);
					if (!existingRoad.isNotEquivalent(testRoad)) {
						return true;
					}

					testLocation = new EdgeLocation(roadNeighbor, EdgeDirection.NorthWest);
					testRoad = new Road(existingRoad.getOwner(), testLocation);
					if (!existingRoad.isNotEquivalent(testRoad)) {
						return true;
					}
					break;
				case SouthEast:
					roadNeighbor = newSettlementLocation.getNeighborLoc(EdgeDirection.SouthEast);
					testLocation = new EdgeLocation(newSettlementLocation, EdgeDirection.South);
					testRoad = new Road(existingRoad.getOwner(), testLocation);
					if (!existingRoad.isNotEquivalent(testRoad)) {
						return true;
					}

					testLocation = new EdgeLocation(newSettlementLocation, EdgeDirection.SouthEast);
					testRoad = new Road(existingRoad.getOwner(), testLocation);
					if (!existingRoad.isNotEquivalent(testRoad)) {
						return true;
					}

					testLocation = new EdgeLocation(roadNeighbor, EdgeDirection.SouthWest);
					testRoad = new Road(existingRoad.getOwner(), testLocation);
					if (!existingRoad.isNotEquivalent(testRoad)) {
						return true;
					}
					break;
				case West:
					roadNeighbor = newSettlementLocation.getNeighborLoc(EdgeDirection.NorthWest);
					testLocation = new EdgeLocation(newSettlementLocation, EdgeDirection.NorthWest);
					testRoad = new Road(existingRoad.getOwner(), testLocation);
					if (!existingRoad.isNotEquivalent(testRoad)) {
						return true;
					}

					testLocation = new EdgeLocation(newSettlementLocation, EdgeDirection.SouthWest);
					testRoad = new Road(existingRoad.getOwner(), testLocation);
					if (!existingRoad.isNotEquivalent(testRoad)) {
						return true;
					}

					testLocation = new EdgeLocation(roadNeighbor, EdgeDirection.South);
					testRoad = new Road(existingRoad.getOwner(), testLocation);
					if (!existingRoad.isNotEquivalent(testRoad)) {
						return true;
					}
					break;
				case SouthWest:
					roadNeighbor = newSettlementLocation.getNeighborLoc(EdgeDirection.SouthWest);
					testLocation = new EdgeLocation(newSettlementLocation, EdgeDirection.SouthWest);
					testRoad = new Road(existingRoad.getOwner(), testLocation);
					if (!existingRoad.isNotEquivalent(testRoad)) {
						return true;
					}

					testLocation = new EdgeLocation(newSettlementLocation, EdgeDirection.South);
					testRoad = new Road(existingRoad.getOwner(), testLocation);
					if (!existingRoad.isNotEquivalent(testRoad)) {
						return true;
					}

					testLocation = new EdgeLocation(roadNeighbor, EdgeDirection.SouthEast);
					testRoad = new Road(existingRoad.getOwner(), testLocation);
					if (!existingRoad.isNotEquivalent(testRoad)) {
						return true;
					}
					break;
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

		if (isPlayerTurn(playerIndex) && playerHasResources(playerIndex, resourceList) && !preexistingBuilding(settlement, true)
				&& noAdjacentBuildings(settlement) && roadTouchingNewSettlement(settlement) && clientModel.getTurnTracker().getStatus().equals("playing")) {
			return true;
		}
		return false;
	}

	private boolean preexistingSettlement(VertexObject building, boolean dontCheckOwner) {
		boolean result = false;
		for (VertexObject settlement : clientModel.getMap().getSettlements()) {
			if (settlement.isEquivalent(building)) {
				result = true;
			}
		}
		for (VertexObject city : clientModel.getMap().getCities()) {
			if (city.isEquivalent(building)) {
				result = false;
			}
		}
		return result;

	}

	/**
	 * checks that a settlement exists on the proposed city spot
	 * 
	 * @param building
	 * @param dontCheckOwner
	 * @return
	 */
	private boolean preexistingBuilding(VertexObject building, boolean dontCheckOwner) {
		for (VertexObject settlement : clientModel.getMap().getSettlements()) {
			if (settlement.isEquivalent(building)) {
				return true;
			}
		}
		for (VertexObject city : clientModel.getMap().getCities()) {
			if (city.isEquivalent(building)) {
				return true;
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
		if (clientModel.getPlayers()[playerIndex].getResources().count() > 7 && !clientModel.getPlayers()[playerIndex].alreadyDiscarded()
				&& clientModel.getTurnTracker().getStatus().equals("discarding")) {
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
		if (isPlayerTurn(playerIndex) && clientModel.getPlayers()[playerIndex].getResources().contains(resourceList)
				&& clientModel.getTurnTracker().getStatus().equals("playing")) {
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
		if (clientModel.getPlayers()[playerIndex].getResources().contains(resourceList)) {
			return true;
		}
		return false;
	}

	private boolean buildingOnPort(int playerIndex, VertexObject building) {
		if (building.getOwner() == playerIndex) {
			for (Port port : clientModel.getMap().getPorts()) {
				EdgeLocation edgeLocation = new EdgeLocation(port.getLocation(), port.getDir());
				Road road = new Road(playerIndex, edgeLocation);
				if(hasConnectingBuilding(road)){
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
	public boolean canMaritimeTrade(int playerIndex,  int ratioNumerator) {
		if (isPlayerTurn(playerIndex) 
				&& clientModel.getPlayers()[playerIndex].getResources().ofAKind(ratioNumerator) 
				&& playerOnPort(playerIndex)
				&& clientModel.getTurnTracker().getStatus().equals("playing")) {
			return true;
		}
		return false;
	}

	private boolean playerTouchingRobber(int robbedPlayer, HexLocation robberLocation) {
		VertexObject testObject = null;
		VertexLocation testLocation = null;

		for (VertexObject settlement : clientModel.getMap().getSettlements()) {
			if (settlement.getOwner() == robbedPlayer) {
				testLocation = new VertexLocation(robberLocation, VertexDirection.NorthEast);
				testObject = new VertexObject(robbedPlayer, testLocation);
				if (settlement.isEquivalent(testObject)) {
					return true;
				}
				testLocation = new VertexLocation(robberLocation, VertexDirection.NorthWest);
				testObject = new VertexObject(robbedPlayer, testLocation);
				if (settlement.isEquivalent(testObject)) {
					return true;
				}
				testLocation = new VertexLocation(robberLocation, VertexDirection.West);
				testObject = new VertexObject(robbedPlayer, testLocation);
				if (settlement.isEquivalent(testObject)) {
					return true;
				}
				testLocation = new VertexLocation(robberLocation, VertexDirection.SouthEast);
				testObject = new VertexObject(robbedPlayer, testLocation);
				if (settlement.isEquivalent(testObject)) {
					return true;
				}
				testLocation = new VertexLocation(robberLocation, VertexDirection.SouthWest);
				testObject = new VertexObject(robbedPlayer, testLocation);
				if (settlement.isEquivalent(testObject)) {
					return true;
				}
				testLocation = new VertexLocation(robberLocation, VertexDirection.East);
				testObject = new VertexObject(robbedPlayer, testLocation);
				if (settlement.isEquivalent(testObject)) {
					return true;
				}
			}
		}
		for (VertexObject city : clientModel.getMap().getCities()) {
			if (city.getOwner() == robbedPlayer) {
				testLocation = new VertexLocation(robberLocation, VertexDirection.NorthEast);
				testObject = new VertexObject(robbedPlayer, testLocation);
				if (city.isEquivalent(testObject)) {
					return true;
				}
				testLocation = new VertexLocation(robberLocation, VertexDirection.NorthWest);
				testObject = new VertexObject(robbedPlayer, testLocation);
				if (city.isEquivalent(testObject)) {
					return true;
				}
				testLocation = new VertexLocation(robberLocation, VertexDirection.West);
				testObject = new VertexObject(robbedPlayer, testLocation);
				if (city.isEquivalent(testObject)) {
					return true;
				}
				testLocation = new VertexLocation(robberLocation, VertexDirection.SouthEast);
				testObject = new VertexObject(robbedPlayer, testLocation);
				if (city.isEquivalent(testObject)) {
					return true;
				}
				testLocation = new VertexLocation(robberLocation, VertexDirection.SouthWest);
				testObject = new VertexObject(robbedPlayer, testLocation);
				if (city.isEquivalent(testObject)) {
					return true;
				}
				testLocation = new VertexLocation(robberLocation, VertexDirection.East);
				testObject = new VertexObject(robbedPlayer, testLocation);
				if (city.isEquivalent(testObject)) {
					return true;
				}
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
	public boolean canRobPlayer(HexLocation robberLocation, int robbingPlayer, int robbedPlayer) {
		if (isPlayerTurn(robbingPlayer) && clientModel.getTurnTracker().getStatus().equals("robbing") && playerTouchingRobber(robbedPlayer, robberLocation)
				&& clientModel.getPlayers()[robbedPlayer].getResources().count() > 0) {
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
		if (isPlayerTurn(playerIndex) && playerHasResources(playerIndex, resourceList) && clientModel.getDeck().hasDevCard()
				&& clientModel.getTurnTracker().getStatus().equals("playing")) {
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
	public boolean canPlaySoldierCard(HexLocation hexLocation, int robbingPlayer, int robbedPlayer) {
		if (isPlayerTurn(robbingPlayer) && clientModel.getPlayers()[robbingPlayer].getOldDevCards().getSoldier() > 0
				&& !clientModel.getPlayers()[robbingPlayer].hasPlayedDevCard() && canRobPlayer(hexLocation, robbingPlayer, robbedPlayer)
				&& !clientModel.getMap().getRobber().equals(hexLocation) && clientModel.getTurnTracker().getStatus().equals("playing")) {

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
	public boolean canPlayYearOfPlentyCard(int playerIndex, ResourceList requestedResources) {
		if (isPlayerTurn(playerIndex) && clientModel.getPlayers()[playerIndex].getOldDevCards().getYearOfPlenty() > 0
				&& !clientModel.getPlayers()[playerIndex].hasPlayedDevCard() && clientModel.getBank().contains(requestedResources)
				&& clientModel.getTurnTracker().getStatus().equals("playing")) {
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
	public boolean canPlayRoadBuildingCard(int playerIndex) {
		if (isPlayerTurn(playerIndex) && clientModel.getPlayers()[playerIndex].getOldDevCards().getRoadBuilding() > 0
				&& !clientModel.getPlayers()[playerIndex].hasPlayedDevCard() && clientModel.getTurnTracker().getStatus().equals("playing")
				&& clientModel.getPlayers()[playerIndex].getRoads() >= 2) {
			return true;
		}
		return false;
	}

	/**
	 * Tests if the player can play a monument card.
	 * 
	 * @param playerIndex
	 * @return
	 */
	public boolean canPlayMonumentCard(int playerIndex) {
		if (isPlayerTurn(playerIndex) && clientModel.getPlayers()[playerIndex].getOldDevCards().getMonument() > 0
				&& !clientModel.getPlayers()[playerIndex].hasPlayedDevCard()
				&& (clientModel.getPlayers()[playerIndex].getVictoryPoints() + clientModel.getPlayers()[playerIndex].getOldDevCards().getMonument()) >= 10
				&& clientModel.getTurnTracker().getStatus().equals("playing")) {
			return true;
		}
		return false;
	}

	/**
	 * Tests if the player can play a monopoly card.
	 * 
	 * @param playerIndex
	 * @return
	 */
	public boolean canPlayMonopolyCard(int playerIndex) {
		// TODO make sure pre-conditions are met
		if (isPlayerTurn(playerIndex) && clientModel.getPlayers()[playerIndex].getOldDevCards().getMonopoly() > 0
				&& !clientModel.getPlayers()[playerIndex].hasPlayedDevCard() && clientModel.getTurnTracker().getStatus().equals("playing")) {
			return true;
		}
		return false;
	}

	public boolean canFinishTurn(int playerIndex) {
		if (isPlayerTurn(playerIndex) && clientModel.getTurnTracker().getStatus() == "playing") {
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
