package client.map;

import java.util.*;

import shared.communication.BuildCityParams;
import shared.communication.BuildRoadParams;
import shared.communication.BuildSettlementParams;
import shared.communication.MoveRobberParams;
import shared.communication.PlayerIndex;
import shared.definitions.*;
import shared.locations.*;
import shared.utils.IServer;
import shared.utils.ServerResponseException;
import client.base.*;
import client.communicator.HTTPCommunicator;
import client.communicator.ServerProxy;
import client.data.*;
import client.map.state.DiscardingState;
import client.map.state.FirstRoundState;
import client.map.state.IMapState;
import client.map.state.PlayingState;
import client.map.state.RobbingState;
import client.map.state.RollingState;
import client.map.state.SecondRoundState;
import client.model.ClientModel;
import client.model.ClientModelController;
import client.model.ResourceList;
import client.model.Road;
import client.model.VertexObject;

/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController, Observer {

	private IRobView robView;
	private IMapState mapState;
	private ClientModelController clientModelController;
	private IServer server;
	private boolean playingCard = false;
	private boolean firstRoadPlaced = false;
	private EdgeLocation firstEdge;
	private EdgeLocation secondEdge;
	private HexLocation robberLocation;

	public MapController(IMapView view, IRobView robView) {

		super(view);

		setRobView(robView);

		initFromModel();
		ClientModel.getSingleton().addObserver(this);
		HTTPCommunicator.setServer("localhost", 8081);
		server = new ServerProxy(new HTTPCommunicator());
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

	protected void initFromModel() {
		// <temp>
		Random rand = new Random();

		for (int x = 0; x <= 3; ++x) {

			int maxY = 3 - x;
			for (int y = -3; y <= maxY; ++y) {
				int r = rand.nextInt(HexType.values().length);
				HexType hexType = HexType.values()[r];
				HexLocation hexLoc = new HexLocation(x, y);
				getView().addHex(hexLoc, hexType);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest), CatanColor.RED);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest), CatanColor.BLUE);
				getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South), CatanColor.ORANGE);
				getView().placeSettlement(new VertexLocation(hexLoc, VertexDirection.NorthWest), CatanColor.GREEN);
				getView().placeCity(new VertexLocation(hexLoc, VertexDirection.NorthEast), CatanColor.PURPLE);
			}

			if (x != 0) {
				int minY = x - 3;
				for (int y = minY; y <= 3; ++y) {
					int r = rand.nextInt(HexType.values().length);
					HexType hexType = HexType.values()[r];
					HexLocation hexLoc = new HexLocation(-x, y);
					getView().addHex(hexLoc, hexType);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.NorthWest), CatanColor.RED);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.SouthWest), CatanColor.BLUE);
					getView().placeRoad(new EdgeLocation(hexLoc, EdgeDirection.South), CatanColor.ORANGE);
					getView().placeSettlement(new VertexLocation(hexLoc, VertexDirection.NorthWest), CatanColor.GREEN);
					getView().placeCity(new VertexLocation(hexLoc, VertexDirection.NorthEast), CatanColor.PURPLE);
				}
			}
		}

		PortType portType = PortType.BRICK;
		getView().addPort(new EdgeLocation(new HexLocation(0, 3), EdgeDirection.North), portType);
		getView().addPort(new EdgeLocation(new HexLocation(0, -3), EdgeDirection.South), portType);
		getView().addPort(new EdgeLocation(new HexLocation(-3, 3), EdgeDirection.NorthEast), portType);
		getView().addPort(new EdgeLocation(new HexLocation(-3, 0), EdgeDirection.SouthEast), portType);
		getView().addPort(new EdgeLocation(new HexLocation(3, -3), EdgeDirection.SouthWest), portType);
		getView().addPort(new EdgeLocation(new HexLocation(3, 0), EdgeDirection.NorthWest), portType);

		getView().placeRobber(new HexLocation(0, 0));

		getView().addNumber(new HexLocation(-2, 0), 2);
		getView().addNumber(new HexLocation(-2, 1), 3);
		getView().addNumber(new HexLocation(-2, 2), 4);
		getView().addNumber(new HexLocation(-1, 0), 5);
		getView().addNumber(new HexLocation(-1, 1), 6);
		getView().addNumber(new HexLocation(1, -1), 8);
		getView().addNumber(new HexLocation(1, 0), 9);
		getView().addNumber(new HexLocation(2, -2), 10);
		getView().addNumber(new HexLocation(2, -1), 11);
		getView().addNumber(new HexLocation(2, 0), 12);

		// </temp>
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		int playerIndex = PlayerInfo.getSingleton().getPlayerIndex();
		Road road = new Road(playerIndex, edgeLoc);
		return mapState.canPlaceRoad(playerIndex, road, playingCard, clientModelController);
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		int playerIndex = PlayerInfo.getSingleton().getPlayerIndex();
		VertexObject settlement = new VertexObject(playerIndex, vertLoc);
		return mapState.canPlaceSettlement(settlement, playingCard, clientModelController);
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		int playerIndex = PlayerInfo.getSingleton().getPlayerIndex();
		VertexObject city = new VertexObject(playerIndex, vertLoc);
		return clientModelController.canBuildCity(city);
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		return clientModelController.canMoveRobber(hexLoc);
	}

	private void sendRoadToServer(EdgeLocation edgeLocation){
		BuildRoadParams buildRoadParams = new BuildRoadParams(PlayerInfo.getSingleton().getPlayerIndex(), edgeLocation, false);
		try {
			server.buildRoad(buildRoadParams);
		} catch (ServerResponseException e) {
			e.printStackTrace();
			System.out.println("Something broke in sendRoadToServer in MapController");
		}
	}
	public void placeRoad(EdgeLocation edgeLoc) {
		if (!playingCard) {
			if (canPlaceRoad(edgeLoc)) {
				sendRoadToServer(edgeLoc);
				getView().placeRoad(edgeLoc, PlayerInfo.getSingleton().getColor());
			}
		}
		else {//Accounts for placing two roads... means that placeRoad should be called twice by whatever is doing the calling.
			if(!firstRoadPlaced){
				firstRoadPlaced = true;
				firstEdge = edgeLoc;
			}
			else{
				sendRoadToServer(firstEdge);
				getView().placeRoad(firstEdge, PlayerInfo.getSingleton().getColor());
				sendRoadToServer(edgeLoc);
				getView().placeRoad(edgeLoc, PlayerInfo.getSingleton().getColor());
				firstEdge = null;
				firstRoadPlaced = false;
				playingCard = false;
			}
		}
	}

	public void placeSettlement(VertexLocation vertLoc) {
		if (canPlaceSettlement(vertLoc)) {
			BuildSettlementParams buildSettlementParams = new BuildSettlementParams(PlayerInfo.getSingleton().getPlayerIndex(), vertLoc, false);
			try {
				server.buildSettlement(buildSettlementParams);
			} catch (ServerResponseException e) {
				e.printStackTrace();
				System.out.println("Something broke in placeSettlement in MapController");
			}
			getView().placeSettlement(vertLoc, PlayerInfo.getSingleton().getColor());
		}
	}

	public void placeCity(VertexLocation vertLoc) {
		if (canPlaceCity(vertLoc)) {
			BuildCityParams buildCityParams = new BuildCityParams(PlayerInfo.getSingleton().getPlayerIndex(), vertLoc);
			try {
				server.buildCity(buildCityParams);
			} catch (ServerResponseException e) {
				e.printStackTrace();
				System.out.println("Something broke in placeSettlement in MapController");
			}
			getView().placeCity(vertLoc, PlayerInfo.getSingleton().getColor());

		}
	}

	public void placeRobber(HexLocation hexLoc) {
		if (canPlaceRobber(hexLoc)) {
			RobPlayerInfo[] candidateVictims = new RobPlayerInfo[3];
			for(int i = 0; i < 4; i++){
				int infoArrayIndex = 0;
				if(i != PlayerInfo.getSingleton().getPlayerIndex() && clientModelController.playerTouchingRobber(i, hexLoc)){
					RobPlayerInfo robPlayerInfo = new RobPlayerInfo();
					robPlayerInfo.setPlayerIndex(i);
					robPlayerInfo.setColor(clientModelController.getPlayerColor(i));
					robPlayerInfo.setName(ClientModel.getSingleton().getPlayers()[i].getName());
					robPlayerInfo.setNumCards(ClientModel.getSingleton().getPlayers()[i].getResources().count());
					robPlayerInfo.setId(ClientModel.getSingleton().getPlayers()[i].getPlayerid());
					candidateVictims[infoArrayIndex] = robPlayerInfo;
					infoArrayIndex += 1;
				}
			}
			robberLocation = hexLoc;
			getRobView().setPlayers(candidateVictims);
			getView().placeRobber(hexLoc);
			getRobView().showModal();
		}
	}

	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {
		int playerIndex = PlayerInfo.getSingleton().getPlayerIndex();
		this.getView().startDrop(pieceType, clientModelController.getPlayerColor(playerIndex), true);
	}

	public void cancelMove() {
//		this.getView()
	}

	public void playSoldierCard() {
		if(clientModelController.isPlayerTurn(PlayerInfo.getSingleton().getPlayerIndex())){
			this.getView().startDrop(PieceType.ROBBER, clientModelController.getPlayerColor(PlayerInfo.getSingleton().getPlayerIndex()), false);
		}
	}

	public void playRoadBuildingCard() {
//		this.getView().;
	}

	public void robPlayer(RobPlayerInfo victim) {
		MoveRobberParams robPlayerParams = new MoveRobberParams(PlayerInfo.getSingleton().getPlayerIndex(), victim.getPlayerIndex(), robberLocation);
		try {
			server.robPlayer(robPlayerParams);
		} catch (ServerResponseException e) {
			System.out.println("Something broke in robPlayer in mapController.java");
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		clientModelController = new ClientModelController();
		updateState();
		updateRoads();
		updateSettlements();
		updateCities();
	}

	private void updateState(){
		switch(ClientModel.getSingleton().getTurnTracker().getStatus().toUpperCase()){
		case "FIRSTROUND":
			mapState = new FirstRoundState();
			break;
		case "SECONDROUND":
			mapState = new SecondRoundState();
			break;
		case "ROLLING":
			mapState = new RollingState();
			break;
		case "ROBBING":
			mapState = new RobbingState();
			break;
		case "PLAYING":
			mapState = new PlayingState();
			break;
		case "DISCARDING":
			mapState = new DiscardingState();
			break;
		default:
			System.out.println("Somthing has gone terribly, horribly wrong in Update() in mapController.java");
			break;
		}
	}

	private void updateCities() {
		for(VertexObject city: ClientModel.getSingleton().getMap().getCities()){
			int ownerIndex = city.getOwner();
			getView().placeCity(city.getLocation(), clientModelController.getPlayerColor(ownerIndex));
		}
	}

	private void updateSettlements() {
		for(VertexObject settlement: ClientModel.getSingleton().getMap().getSettlements()){
			int ownerIndex = settlement.getOwner();
			getView().placeSettlement(settlement.getLocation(), clientModelController.getPlayerColor(ownerIndex));
		}
	}

	private void updateRoads() {
		for(Road road : ClientModel.getSingleton().getMap().getRoads()){
			int ownerIndex = road.getOwner();
			getView().placeRoad(road.getLocation(), clientModelController.getPlayerColor(ownerIndex));
		}
		
	}

	public IMapState getMapState() {
		return mapState;
	}

	public void setMapState(IMapState mapState) {
		this.mapState = mapState;
	}

}
