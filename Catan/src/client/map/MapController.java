package client.map;

import java.util.*;

import shared.communication.BuildCityParams;
import shared.communication.BuildRoadCardParams;
import shared.communication.BuildRoadParams;
import shared.communication.BuildSettlementParams;
import shared.communication.MoveRobberParams;
import shared.communication.MoveSoldierParams;
import shared.communication.UserActionParams;
import shared.definitions.*;
import shared.locations.*;
import shared.model.Hex;
import shared.model.Port;
import shared.model.Road;
import shared.model.VertexObject;
import shared.utils.IServer;
import shared.utils.ServerResponseException;
import client.base.*;
import client.communicator.HTTPCommunicator;
import client.communicator.ServerProxy;
import client.data.*;
import client.map.state.DiscardingState;
import client.map.state.FirstRoundState;
import client.map.state.IMapState;
import client.map.state.InitialState;
import client.map.state.PlayingState;
import client.map.state.RobbingState;
import client.map.state.RollingState;
import client.map.state.SecondRoundState;
import client.model.ClientModel;
import client.model.ClientModelFacade;

/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController, Observer {

	private IRobView robView;
	private IMapState mapState = new InitialState();
	private ClientModelFacade clientModelController;
	private IServer server;
	private boolean playingRoadBuildingCard = false;
	private boolean firstRoadPlaced = false;
	private EdgeLocation firstEdge;
	private HexLocation robberLocation;
	private boolean usingSoldierCard = false;
	private boolean robStarted = false;

	public MapController(IMapView view, IRobView robView) {

		super(view);

		setRobView(robView);

		initFromModel();
		ClientModel.getNotifier().addObserver(this);
		HTTPCommunicator.setServer("localhost", 8081);
		server = ServerProxy.getSingleton();
	}

	public IMapView getView() {

		return (IMapView) super.getView();
	}

	private IRobView getRobView() {
		return robView;
	}

	private void setRobView(IRobView robView) {
		this.robView = robView;
	}

	public void populateWaterHexes() {
		getView().addHex(new HexLocation(0, -3), HexType.WATER);
		getView().addHex(new HexLocation(1, -3), HexType.WATER);
		getView().addHex(new HexLocation(2, -3), HexType.WATER);
		getView().addHex(new HexLocation(3, -3), HexType.WATER);
		getView().addHex(new HexLocation(3, -2), HexType.WATER);
		getView().addHex(new HexLocation(3, -1), HexType.WATER);
		getView().addHex(new HexLocation(3, -0), HexType.WATER);
		getView().addHex(new HexLocation(2, 1), HexType.WATER);
		getView().addHex(new HexLocation(1, 2), HexType.WATER);
		getView().addHex(new HexLocation(0, 3), HexType.WATER);
		getView().addHex(new HexLocation(-1, 3), HexType.WATER);
		getView().addHex(new HexLocation(-2, 3), HexType.WATER);
		getView().addHex(new HexLocation(-3, 3), HexType.WATER);
		getView().addHex(new HexLocation(-3, 2), HexType.WATER);
		getView().addHex(new HexLocation(-3, 1), HexType.WATER);
		getView().addHex(new HexLocation(-3, 0), HexType.WATER);
		getView().addHex(new HexLocation(-2, -1), HexType.WATER);
		getView().addHex(new HexLocation(-1, -2), HexType.WATER);
	}

	public void populateHexes() {
		for (Hex hex : ClientModel.getSingleton().getMap().getHexes()) {
			if (hex != null) {
				HexType hexType = clientModelController.stringToHexType(hex.getResource());
				getView().addHex(hex.getLocation(), hexType);
				if (hex.getNumber() != -1 && hex.getNumber() != 0) {
					getView().addNumber(hex.getLocation(), hex.getNumber());
				} else {
					getView().addHex(hex.getLocation(), HexType.DESERT);
				}
			}
		}
	}

	public void populatePorts() {
		for (Port port : ClientModel.getSingleton().getMap().getPorts()) {
			if (port != null) {
				port.convertFromPrimitives();
				PortType portType = clientModelController.stringToPortType(port.getResource());
				EdgeLocation edgeLocation = new EdgeLocation(port.getLocation(), port.getDir());
				getView().addPort(edgeLocation, portType);
			}
		}
	}

	protected void initFromModel() {
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		Road road = new Road(playerIndex, edgeLoc);
		return mapState.canPlaceRoad(playerIndex, road, playingRoadBuildingCard, clientModelController);
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		VertexObject settlement = new VertexObject(playerIndex, vertLoc);
		// boolean test = mapState.canPlaceSettlement(settlement,
		// playingRoadBuildingCard,
		// clientModelController);
		return mapState.canPlaceSettlement(settlement, playingRoadBuildingCard, clientModelController);
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		VertexObject city = new VertexObject(playerIndex, vertLoc);
		return clientModelController.canBuildCity(city);
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		return clientModelController.canMoveRobber(hexLoc);
	}

	private void sendRoadToServer(EdgeLocation edgeLocation) {
		boolean free = (this.getMapState().getClassName().toUpperCase().equals("FIRSTROUNDSTATE")
				|| this.getMapState().getClassName().toUpperCase().equals("SECONDROUNDSTATE") || playingRoadBuildingCard);
		BuildRoadParams buildRoadParams = new BuildRoadParams(UserPlayerInfo.getSingleton().getPlayerIndex(), edgeLocation, free);
		try {
			server.buildRoad(buildRoadParams);
			if (mapState.getClassName().toUpperCase().equals("FIRSTROUNDSTATE") || mapState.getClassName().toUpperCase().equals("SECONDROUNDSTATE")) {
				int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
				UserActionParams userActionParams = new UserActionParams(playerIndex);
				userActionParams.setType("finishTurn");
				server.finishTurn(userActionParams);
			}
		} catch (ServerResponseException e) {
			e.printStackTrace();
		}

	}

	public void placeRoad(EdgeLocation edgeLoc) {
		if (!playingRoadBuildingCard) {
			if (canPlaceRoad(edgeLoc)) {
				sendRoadToServer(edgeLoc);
				getView().placeRoad(edgeLoc, UserPlayerInfo.getSingleton().getColor());
			}
		} else {// Accounts for placing two roads...
			if (!firstRoadPlaced) {
				firstRoadPlaced = true;
				firstEdge = edgeLoc;
				// sendRoadToServer(firstEdge);
				Road road = new Road(UserPlayerInfo.getSingleton().getPlayerIndex(), firstEdge);
				Road[] oldRoads = ClientModel.getSingleton().getMap().getRoads();
				Road[] newRoads = new Road[oldRoads.length + 1];
				int i = 0;
				for (Road tempRoad : oldRoads) {
					newRoads[i] = tempRoad;
					i += 1;
				}
				newRoads[oldRoads.length] = road;
				ClientModel.getSingleton().getMap().setRoads(newRoads);
				this.update(null, null);
				this.startMove(PieceType.ROAD, true, false);
			} else {
//				sendRoadToServer(firstEdge);
//				sendRoadToServer(edgeLoc);
				try {
					UserPlayerInfo upi = UserPlayerInfo.getSingleton();
					int playerIndex = upi.getPlayerIndex();
					BuildRoadCardParams buildRoadCardParams = new BuildRoadCardParams(playerIndex, firstEdge, edgeLoc);
					server.playRoadBuildingCard(buildRoadCardParams);
				} catch (ServerResponseException e) {
					firstRoadPlaced = false;
					// e.printStackTrace();
				}
				getView().placeRoad(edgeLoc, UserPlayerInfo.getSingleton().getColor());
				firstEdge = null;
				firstRoadPlaced = false;
				playingRoadBuildingCard = false;
			}
		}
	}

	public void placeSettlement(VertexLocation vertLoc) {
		if (canPlaceSettlement(vertLoc)) {
			boolean free = (this.getMapState().getClassName().toUpperCase().equals("FIRSTROUNDSTATE") || this.getMapState().getClassName().toUpperCase()
					.equals("SECONDROUNDSTATE"));
			BuildSettlementParams buildSettlementParams = new BuildSettlementParams(UserPlayerInfo.getSingleton().getPlayerIndex(), vertLoc, free);
			try {
				server.buildSettlement(buildSettlementParams);
			} catch (ServerResponseException e) {
				e.printStackTrace();
			}
			getView().placeSettlement(vertLoc, UserPlayerInfo.getSingleton().getColor());
		}
	}

	public void placeCity(VertexLocation vertLoc) {
		if (canPlaceCity(vertLoc)) {
			BuildCityParams buildCityParams = new BuildCityParams(UserPlayerInfo.getSingleton().getPlayerIndex(), vertLoc);
			try {
				server.buildCity(buildCityParams);
			} catch (ServerResponseException e) {
				e.printStackTrace();
			}
			getView().placeCity(vertLoc, UserPlayerInfo.getSingleton().getColor());

		}
	}

	public void placeRobber(HexLocation hexLoc) {
		if (canPlaceRobber(hexLoc)) {
			ArrayList<RobPlayerInfo> candidateVictims = new ArrayList<RobPlayerInfo>();
			for (int i = 0; i < 4; i++) {
				if (i != UserPlayerInfo.getSingleton().getPlayerIndex() && clientModelController.playerTouchingRobber(i, hexLoc)) {
					RobPlayerInfo robPlayerInfo = new RobPlayerInfo();
					robPlayerInfo.setPlayerIndex(i);
					robPlayerInfo.setColor(clientModelController.getPlayerColor(i));
					robPlayerInfo.setName(ClientModel.getSingleton().getPlayers()[i].getName());
					robPlayerInfo.setNumCards(ClientModel.getSingleton().getPlayers()[i].getResources().totalResourceCount());
					robPlayerInfo.setId(ClientModel.getSingleton().getPlayers()[i].getPlayerid());
					candidateVictims.add(robPlayerInfo);
				}
			}

			RobPlayerInfo[] candidateVictimsArray = new RobPlayerInfo[candidateVictims.size()];
			candidateVictims.toArray(candidateVictimsArray);
			robberLocation = hexLoc;
			getRobView().setPlayers(candidateVictimsArray);
			getView().placeRobber(hexLoc);
			getRobView().showModal();
		}
	}

	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {
		int playerIndex = UserPlayerInfo.getSingleton().getPlayerIndex();
		if (allowDisconnected) {
			this.getView().startDrop(pieceType, clientModelController.getPlayerColor(playerIndex), false);
		} else {
			this.getView().startDrop(pieceType, clientModelController.getPlayerColor(playerIndex), true);
		}
	}

	public void cancelMove() {
		if (playingRoadBuildingCard && firstRoadPlaced) {
			firstRoadPlaced = false;
			MapView mapView = (MapView) this.getView();
			Road[] currentRoads = ClientModel.getSingleton().getMap().getRoads();
			Road[] newRoads = new Road[currentRoads.length - 1];
			for (int i = 0; i < newRoads.length; i++) {
				newRoads[i] = currentRoads[i];
			}
			ClientModel.getSingleton().getMap().setRoads(newRoads);
			mapView.removeMap();
			this.populateHexes();
			this.populatePorts();
			this.populateWaterHexes();
			this.update(null, null);
		}
	}

	public void playSoldierCard() {
		if (clientModelController.isPlayerTurn(UserPlayerInfo.getSingleton().getPlayerIndex())) {
			usingSoldierCard = true;
			this.getView().startDrop(PieceType.ROBBER, clientModelController.getPlayerColor(UserPlayerInfo.getSingleton().getPlayerIndex()), true);
		}
	}

	public void startRob() {
		if (clientModelController.isPlayerTurn(UserPlayerInfo.getSingleton()
				.getPlayerIndex())) {
			robStarted = true;
			usingSoldierCard = false;
			this.getView().startDrop(PieceType.ROBBER, clientModelController.getPlayerColor(UserPlayerInfo.getSingleton().getPlayerIndex()), false);
		}
	}

	public void playRoadBuildingCard() {
		playingRoadBuildingCard = true;
		this.startMove(PieceType.ROAD, true, false);
	}

	public void robPlayer(RobPlayerInfo victim) {
		robStarted = false;
		if (usingSoldierCard) {
			usingSoldierCard = false;
			MoveSoldierParams params = null;
			if (victim == null) {
				params = new MoveSoldierParams(UserPlayerInfo.getSingleton().getPlayerIndex(), -1, robberLocation);
			} else {
				params = new MoveSoldierParams(UserPlayerInfo.getSingleton().getPlayerIndex(), victim.getPlayerIndex(), robberLocation);
			}
			try {
				server.playSoldierCard(params);
			} catch (ServerResponseException e) {
				e.printStackTrace();
			}
		} else {
			MoveRobberParams robPlayerParams = null;
			if (victim == null) {
				robPlayerParams = new MoveRobberParams(UserPlayerInfo.getSingleton().getPlayerIndex(), -1, robberLocation);
			} else {
				robPlayerParams = new MoveRobberParams(UserPlayerInfo.getSingleton().getPlayerIndex(), victim.getPlayerIndex(), robberLocation);
			}
			try {
				server.robPlayer(robPlayerParams);
				robStarted = false;
			} catch (ServerResponseException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		clientModelController = new ClientModelFacade();
		// ClientModel cm = ClientModel.getSingleton();
		// populateHexes();
		// populatePorts();
		mapState.initialize(this);
		updateState();
		mapState.beginRound(this);
		updateRoads();
		updateSettlements();
		updateCities();
		updateRobberPosition();
	}

	private void updateRobberPosition() {
		HexLocation robberLocation = ClientModel.getSingleton().getMap().getRobber();
		this.getView().placeRobber(robberLocation);

	}

	private void updateState() {
		switch (ClientModel.getSingleton().getTurnTracker().getStatus().toUpperCase()) {
		case "FIRSTROUND":
			if (ClientModel.getSingleton().hasFourPlayers() && !mapState.getClassName().equals("FirstRoundState")) {
				mapState = new FirstRoundState();
			}
			break;
		case "SECONDROUND":
			if (!mapState.getClassName().equals("SecondRoundState")) {
				mapState = new SecondRoundState();
			}
			break;
		case "ROLLING":
			mapState = new RollingState();
			break;
		case "ROBBING":
			if (!robStarted) {
				startRob();
			}
			mapState = new RobbingState();
			break;
		case "PLAYING":
			mapState = new PlayingState();
			break;
		case "DISCARDING":
			mapState = new DiscardingState();
			break;
		default:
			break;
		}
	}

	private void updateCities() {
		if (ClientModel.getSingleton().getMap().getCities() != null) {
			for (VertexObject city : ClientModel.getSingleton().getMap().getCities()) {
				int ownerIndex = city.getOwner();
				getView().placeCity(city.getLocation(), clientModelController.getPlayerColor(ownerIndex));
			}
		}
	}

	private void updateSettlements() {
		if (ClientModel.getSingleton().getMap().getSettlements() != null) {
			for (VertexObject settlement : ClientModel.getSingleton().getMap().getSettlements()) {
				int ownerIndex = settlement.getOwner();
				getView().placeSettlement(settlement.getLocation(), clientModelController.getPlayerColor(ownerIndex));
			}
		}
	}

	private void updateRoads() {
		if (ClientModel.getSingleton().getMap().getRoads() != null) {
			for (Road road : ClientModel.getSingleton().getMap().getRoads()) {
				int ownerIndex = road.getOwner();
				getView().placeRoad(road.getLocation(), clientModelController.getPlayerColor(ownerIndex));
			}
		}

	}

	public IMapState getMapState() {
		return mapState;
	}

	public void setMapState(IMapState mapState) {
		this.mapState = mapState;
	}

}
