package client.map;

import java.util.*;

import shared.communication.BuildCityParams;
import shared.communication.BuildRoadParams;
import shared.communication.BuildSettlementParams;
import shared.communication.MoveRobberParams;
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
import client.model.Hex;
import client.model.Port;
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
	private boolean playingRoadBuildingCard = false;
	private boolean firstRoadPlaced = false;
	private EdgeLocation firstEdge;
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
	
	private void populateHexes(){
		for(Hex hex : ClientModel.getSingleton().getMap().getHexes()){
			HexType hexType = clientModelController.stringToHexType(hex.getResource());
			getView().addHex(hex.getLocation(),hexType);
			getView().addNumber(hex.getLocation(), hex.getNumber());
		}
	}
	private void populatePorts(){
		for(Port port : ClientModel.getSingleton().getMap().getPorts()){
			PortType portType = clientModelController.stringToPortType(port.getResource());
			EdgeLocation edgeLocation = new EdgeLocation(port.getLocation(),port.getDir());
			getView().addPort(edgeLocation, portType);
		}
	}
	
	protected void initFromModel() {
		// <temp>

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
		return mapState.canPlaceRoad(playerIndex, road, playingRoadBuildingCard, clientModelController);
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		int playerIndex = PlayerInfo.getSingleton().getPlayerIndex();
		VertexObject settlement = new VertexObject(playerIndex, vertLoc);
		return mapState.canPlaceSettlement(settlement, playingRoadBuildingCard, clientModelController);
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
		BuildRoadParams buildRoadParams = new BuildRoadParams(PlayerInfo.getSingleton().getPlayerIndex(), edgeLocation, playingRoadBuildingCard);
		try {
			server.buildRoad(buildRoadParams);
		} catch (ServerResponseException e) {
			e.printStackTrace();
			System.out.println("Something broke in sendRoadToServer in MapController");
		}
	}
	public void placeRoad(EdgeLocation edgeLoc) {
		if (!playingRoadBuildingCard) {
			if (canPlaceRoad(edgeLoc)) {
				sendRoadToServer(edgeLoc);
				getView().placeRoad(edgeLoc, PlayerInfo.getSingleton().getColor());
			}
		}
		else {//Accounts for placing two roads... means that placeRoad should be called twice by whatever is doing the calling.
			if(!firstRoadPlaced){
				firstRoadPlaced = true;
				firstEdge = edgeLoc;
				sendRoadToServer(firstEdge);
				getView().placeRoad(firstEdge, PlayerInfo.getSingleton().getColor());
			}
			else{
				sendRoadToServer(edgeLoc);
				getView().placeRoad(edgeLoc, PlayerInfo.getSingleton().getColor());
				firstEdge = null;
				firstRoadPlaced = false;
				playingRoadBuildingCard = false;
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
		playingRoadBuildingCard = true;
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
		populateHexes();
		populatePorts();
		
		
		updateState();
		updateRoads();
		updateSettlements();
		updateCities();
		updateRobberPosition();
	}

	private void updateRobberPosition() {
		HexLocation robberLocation =  ClientModel.getSingleton().getMap().getRobber();
		this.getView().placeRobber(robberLocation);
		
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
