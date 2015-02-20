package client.map;

import java.util.*;

import shared.communication.BuildCityParams;
import shared.communication.BuildRoadParams;
import shared.communication.BuildSettlementParams;
import shared.communication.PlayerIndex;
import shared.definitions.*;
import shared.locations.*;
import shared.utils.ServerResponseException;
import client.base.*;
import client.communicator.HTTPCommunicator;
import client.communicator.ServerProxy;
import client.data.*;
import client.map.state.IMapState;
import client.model.ClientModel;
import client.model.ClientModelController;
import client.model.Road;
import client.model.VertexObject;

/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController, Observer {

	private IRobView robView;
	private IMapState mapState;
	private ClientModelController clientModelController;
	private ServerProxy serverProxy;
	private boolean playingCard = false;
	private boolean firstRoadPlaced = false;
	private EdgeLocation firstEdge;
	private EdgeLocation secondEdge;

	public MapController(IMapView view, IRobView robView) {

		super(view);

		setRobView(robView);

		initFromModel();
		ClientModel.getSingleton().addObserver(this);
		HTTPCommunicator.setServer("localhost", 8081);
		serverProxy = new ServerProxy(new HTTPCommunicator());
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
		if (mapState.getClassName().equals("firstRoundState") || mapState.getClassName().equals("secondRoundState") || playingCard) {
			return clientModelController.canBuildRoad(playerIndex, road, true);
		}
		return clientModelController.canBuildRoad(playerIndex, road, false);
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		int playerIndex = PlayerInfo.getSingleton().getPlayerIndex();
		VertexObject settlement = new VertexObject(playerIndex, vertLoc);
		if (mapState.getClassName().equals("firstRoundState") || mapState.getClassName().equals("secondRoundState") || playingCard) {
			return clientModelController.canBuildSettlement(settlement, true,true);
		}
		return clientModelController.canBuildSettlement(settlement, false,false);
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
			serverProxy.buildRoad(buildRoadParams);
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
				serverProxy.buildSettlement(buildSettlementParams);
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
				serverProxy.buildCity(buildCityParams);
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

	}

	@Override
	public void update(Observable o, Object arg) {
		clientModelController = new ClientModelController();
		mapState.mapAction(this);
	}

	public IMapState getMapState() {
		return mapState;
	}

	public void setMapState(IMapState mapState) {
		this.mapState = mapState;
	}

}
