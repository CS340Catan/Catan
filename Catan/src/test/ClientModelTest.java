package test;

import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.utils.Serializer;
import client.model.ClientModel;
import client.model.ClientModelController;
import client.model.Road;
import client.model.VertexObject;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClientModelTest {
	private String clientModelJson = "{\"deck\":{\"yearOfPlenty\":2,\"monopoly\":2,\"soldier\":14,\"roadBuilding\":2,\"monument\":5},\"map\":{\"hexes\":[{\"location\":{\"x\":0,\"y\":-2}},{\"resource\":\"ore\",\"location\":{\"x\":1,\"y\":-2},\"number\":3},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-2},\"number\":3},{\"resource\":\"wheat\",\"location\":{\"x\":-1,\"y\":-1},\"number\":8},{\"resource\":\"brick\",\"location\":{\"x\":0,\"y\":-1},\"number\":8},{\"resource\":\"sheep\",\"location\":{\"x\":1,\"y\":-1},\"number\":9},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-1},\"number\":11},{\"resource\":\"sheep\",\"location\":{\"x\":-2,\"y\":0},\"number\":10},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":0},\"number\":12},{\"resource\":\"sheep\",\"location\":{\"x\":0,\"y\":0},\"number\":10},{\"resource\":\"wheat\",\"location\":{\"x\":1,\"y\":0},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":2,\"y\":0},\"number\":5},{\"resource\":\"wood\",\"location\":{\"x\":-2,\"y\":1},\"number\":6},{\"resource\":\"brick\",\"location\":{\"x\":-1,\"y\":1},\"number\":4},{\"resource\":\"ore\",\"location\":{\"x\":0,\"y\":1},\"number\":5},{\"resource\":\"wood\",\"location\":{\"x\":1,\"y\":1},\"number\":4},{\"resource\":\"ore\",\"location\":{\"x\":-2,\"y\":2},\"number\":9},{\"resource\":\"wheat\",\"location\":{\"x\":-1,\"y\":2},\"number\":6},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":2},\"number\":2}],\"roads\":[{\"owner\":0,\"location\":{\"direction\":\"S\",\"x\":1,\"y\":0}},{\"owner\":0,\"location\":{\"direction\":\"SE\",\"x\":0,\"y\":1}},{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"cities\":[],\"settlements\":[{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":1,\"y\":0}},{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":1,\"y\":-3}},{\"owner\":0,\"location\":{\"direction\":\"NW\",\"x\":1,\"y\":0}}],\"radius\":3,\"ports\":[{\"ratio\":3,\"direction\":\"S\",\"location\":{\"x\":1,\"y\":-3}},{\"ratio\":3,\"direction\":\"NE\",\"location\":{\"x\":-2,\"y\":3}},{\"ratio\":2,\"resource\":\"sheep\",\"direction\":\"SW\",\"location\":{\"x\":3,\"y\":-3}},{\"ratio\":3,\"direction\":\"NW\",\"location\":{\"x\":2,\"y\":1}},{\"ratio\":3,\"direction\":\"SE\",\"location\":{\"x\":-3,\"y\":0}},{\"ratio\":2,\"resource\":\"wood\",\"direction\":\"S\",\"location\":{\"x\":-1,\"y\":-2}},{\"ratio\":2,\"resource\":\"ore\",\"direction\":\"N\",\"location\":{\"x\":0,\"y\":3}},{\"ratio\":2,\"resource\":\"wheat\",\"direction\":\"NW\",\"location\":{\"x\":3,\"y\":-1}},{\"ratio\":2,\"resource\":\"brick\",\"direction\":\"NE\",\"location\":{\"x\":-3,\"y\":2}}],\"robber\":{\"x\":0,\"y\":-2}},\"players\":[{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":12,\"playerIndex\":0,\"name\":\"string\",\"color\":\"purple\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":13,\"playerIndex\":1,\"name\":\"test1\",\"color\":\"puce\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":14,\"playerIndex\":2,\"name\":\"test2\",\"color\":\"blue\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":12,\"cities\":4,\"settlements\":4,\"soldiers\":0,\"victoryPoints\":1,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":15,\"playerIndex\":3,\"name\":\"test3\",\"color\":\"orange\"}],\"log\":{\"lines\":[{\"source\":\"string\",\"message\":\"string built a settlement\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"}]},\"chat\":{\"lines\":[]},\"bank\":{\"brick\":24,\"wood\":24,\"sheep\":24,\"wheat\":24,\"ore\":24},\"turnTracker\":{\"status\":\"FirstRound\",\"currentTurn\":0,\"longestRoad\":-1,\"largestArmy\":-1},\"winner\":-1,\"version\":4}";
	ClientModel clientModel = null;
	@Before
	public void setUp() throws Exception {
		ClientModel.setClientModel(Serializer.deserializeClientModel(clientModelJson));
		clientModel = ClientModel.getSingleton();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCanRoll() {
		clientModel.getTurnTracker().setStatus("rolling");
		ClientModelController clientModelController = new ClientModelController();
		assertTrue(clientModelController.canRollNumber(0));
	}

	@Test
	public void testCanBuildRoadOnTopOfRoadFail() {
		clientModel.getTurnTracker().setStatus("playing");		
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWood(5);
		ClientModelController clientModelController = new ClientModelController();
		HexLocation hexLocation = new HexLocation(1, 0);
		EdgeLocation edgeLocation = new EdgeLocation(hexLocation, EdgeDirection.South);
		Road newRoad = new Road(0, edgeLocation);
		boolean fail = clientModelController.canBuildRoad(0, newRoad, false);
		assertFalse(fail);
	}

	@Test
	public void testCanBuildRoadNotConnectingFail() {
		clientModel.getTurnTracker().setStatus("playing");				
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWood(5);
		ClientModelController clientModelController = new ClientModelController();
		HexLocation hexLocation = new HexLocation(1, 0);
		EdgeLocation edgeLocation = new EdgeLocation(hexLocation, EdgeDirection.NorthEast);
		Road newRoad = new Road(0, edgeLocation);
		boolean fail = clientModelController.canBuildRoad(0, newRoad, false);
		assertFalse(fail);
	}

	@Test
	public void testCanBuildRoadOffRoadDifferentOwnerFail() {
		clientModel.getTurnTracker().setStatus("playing");				
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWood(5);

		ClientModelController clientModelController = new ClientModelController();
		HexLocation hexLocation = new HexLocation(1, 0);
		EdgeLocation edgeLocation = new EdgeLocation(hexLocation, EdgeDirection.SouthEast);

		Road road = new Road(0, edgeLocation);

		assertFalse(clientModelController.canBuildRoad(1, road, false));
	}

	@Test
	public void testCanBuildRoadOffBuildingSuccess() {
		clientModel.getTurnTracker().setStatus("playing");				
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWood(5);

		ClientModelController clientModelController = new ClientModelController();
		
		HexLocation hexLocation = new HexLocation(1, 0);
		EdgeLocation edgeLocation = new EdgeLocation(hexLocation, EdgeDirection.North);
		Road road = new Road(0, edgeLocation);

		HexLocation hexLocation1 = new HexLocation(1, 0);
		EdgeLocation edgeLocation1 = new EdgeLocation(hexLocation1, EdgeDirection.NorthWest);
		Road road1 = new Road(0, edgeLocation1);

		HexLocation hexLocation2 = new HexLocation(1, -1);
		EdgeLocation edgeLocation2 = new EdgeLocation(hexLocation2, EdgeDirection.SouthWest);
		Road road2 = new Road(0, edgeLocation2);

		assertTrue(clientModelController.canBuildRoad(0, road, false));
		assertTrue(clientModelController.canBuildRoad(0, road1, false));
		assertTrue(clientModelController.canBuildRoad(0, road2, false));

	}

	@Test
	public void testCanBuildRoadOffBuildingBadOwnerFail() {
		clientModel.getTurnTracker().setStatus("playing");				
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWood(5);

		ClientModelController clientModelController = new ClientModelController();
		HexLocation hexLocation = new HexLocation(1, 0);
		EdgeLocation edgeLocation = new EdgeLocation(hexLocation, EdgeDirection.North);

		Road road = new Road(1, edgeLocation);

		assertFalse(clientModelController.canBuildRoad(0, road, false));
	}

	@Test
	public void testCanBuildRoadNoResources() {
		clientModel.getTurnTracker().setStatus("playing");				
		clientModel.getPlayers()[0].getResources().setBrick(0);
		clientModel.getPlayers()[0].getResources().setWood(0);

		ClientModelController clientModelController = new ClientModelController();
		HexLocation hexLocation = new HexLocation(1, 0);
		EdgeLocation edgeLocation = new EdgeLocation(hexLocation, EdgeDirection.North);

		Road road = new Road(1, edgeLocation);

		assertFalse(clientModelController.canBuildRoad(0, road, false));
	}

	@Test
	public void testCanBuildRoadOffRoadSuccess() {
		clientModel.getTurnTracker().setStatus("playing");				
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWood(5);

		ClientModelController clientModelController = new ClientModelController();
		HexLocation hexLocation = new HexLocation(1, 0);
		EdgeLocation edgeLocation = new EdgeLocation(hexLocation, EdgeDirection.SouthEast);

		Road road = new Road(0, edgeLocation);

		assertTrue(clientModelController.canBuildRoad(0, road, false));
	}
	
	@Test
	public void testCanBuildRoadOffRoadNotPlayingFail() {
		clientModel.getTurnTracker().setStatus("rolling");				
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWood(5);

		ClientModelController clientModelController = new ClientModelController();
		HexLocation hexLocation = new HexLocation(1, 0);
		EdgeLocation edgeLocation = new EdgeLocation(hexLocation, EdgeDirection.SouthEast);

		Road road = new Road(0, edgeLocation);

		assertFalse(clientModelController.canBuildRoad(0, road, false));
	}

	@Test
	public void testCanBuildCitySuccess() {
		clientModel.getTurnTracker().setStatus("playing");				
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWheat(5);
		clientModel.getPlayers()[0].getResources().setSheep(5);
		clientModel.getPlayers()[0].getResources().setWood(5);
		clientModel.getPlayers()[0].getResources().setOre(5);
		
		HexLocation hexLocation = new HexLocation(1, 1);
		VertexLocation vertexLocation = new VertexLocation(hexLocation, VertexDirection.NorthWest);
		VertexObject city = new VertexObject(0, vertexLocation);
		ClientModelController clientModelController = new ClientModelController();

		assertTrue(clientModelController.canBuildCity(city));
	}
	
	@Test
	public void testCanBuildCityNotPlayingFail() {
		clientModel.getTurnTracker().setStatus("rolling");				
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWheat(5);
		clientModel.getPlayers()[0].getResources().setSheep(5);
		clientModel.getPlayers()[0].getResources().setWood(5);
		clientModel.getPlayers()[0].getResources().setOre(5);
		
		HexLocation hexLocation = new HexLocation(1, 1);
		VertexLocation vertexLocation = new VertexLocation(hexLocation, VertexDirection.NorthWest);
		VertexObject city = new VertexObject(0, vertexLocation);
		ClientModelController clientModelController = new ClientModelController();

		assertFalse(clientModelController.canBuildCity(city));
	}

	@Test
	public void testCanBuildCityOnNothingFail() {
		clientModel.getTurnTracker().setStatus("playing");				
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWheat(5);
		clientModel.getPlayers()[0].getResources().setSheep(5);
		clientModel.getPlayers()[0].getResources().setWood(5);
		clientModel.getPlayers()[0].getResources().setOre(5);
		
		HexLocation hexLocation = new HexLocation(1, 0);
		VertexLocation vertexLocation = new VertexLocation(hexLocation, VertexDirection.East);
		VertexObject settlement = new VertexObject(0, vertexLocation);
		ClientModelController clientModelController = new ClientModelController();

		assertFalse(clientModelController.canBuildSettlement(settlement));
	}
	
	@Test
	public void testCanBuildCityBadOwnerFail() {
		clientModel.getTurnTracker().setStatus("playing");				
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWheat(5);
		clientModel.getPlayers()[0].getResources().setSheep(5);
		clientModel.getPlayers()[0].getResources().setWood(5);
		clientModel.getPlayers()[0].getResources().setOre(5);
		
		HexLocation hexLocation = new HexLocation(1, 1);
		VertexLocation vertexLocation = new VertexLocation(hexLocation, VertexDirection.NorthWest);
		VertexObject settlement = new VertexObject(1, vertexLocation);
		ClientModelController clientModelController = new ClientModelController();

		assertFalse(clientModelController.canBuildSettlement(settlement));
	}
	@Test
	public void testCanBuildCityWrongResourcesFail() {
		clientModel.getTurnTracker().setStatus("playing");				
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWheat(5);
		clientModel.getPlayers()[0].getResources().setSheep(5);
		clientModel.getPlayers()[0].getResources().setWood(5);
		clientModel.getPlayers()[0].getResources().setOre(0);		
		
		HexLocation hexLocation = new HexLocation(1, 1);
		VertexLocation vertexLocation = new VertexLocation(hexLocation, VertexDirection.NorthWest);
		VertexObject settlement = new VertexObject(0, vertexLocation);
		ClientModelController clientModelController = new ClientModelController();

		assertFalse(clientModelController.canBuildSettlement(settlement));
	}

	@Test
	public void testCanBuildSettlementSuccess() {
		clientModel.getTurnTracker().setStatus("playing");				
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWheat(5);
		clientModel.getPlayers()[0].getResources().setSheep(5);
		clientModel.getPlayers()[0].getResources().setWood(5);
		HexLocation hexLocation = new HexLocation(1, 1);
		VertexLocation vertexLocation = new VertexLocation(hexLocation, VertexDirection.East);
		VertexObject settlement = new VertexObject(0, vertexLocation);
		ClientModelController clientModelController = new ClientModelController();

		assertTrue(clientModelController.canBuildSettlement(settlement));
	}
	@Test
	public void testCanBuildSettlementWrongResourcesFail() {
		clientModel.getTurnTracker().setStatus("playing");				
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWheat(5);
		clientModel.getPlayers()[0].getResources().setSheep(5);
		clientModel.getPlayers()[0].getResources().setWood(0);
		HexLocation hexLocation = new HexLocation(1, 1);
		VertexLocation vertexLocation = new VertexLocation(hexLocation, VertexDirection.East);
		VertexObject settlement = new VertexObject(0, vertexLocation);
		ClientModelController clientModelController = new ClientModelController();

		assertFalse(clientModelController.canBuildSettlement(settlement));
	}
	
	@Test
	public void testCanBuildSettlementNotPlayingFail() {
		clientModel.getTurnTracker().setStatus("rolling");				
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWheat(5);
		clientModel.getPlayers()[0].getResources().setSheep(5);
		clientModel.getPlayers()[0].getResources().setWood(5);
		HexLocation hexLocation = new HexLocation(1, 1);
		VertexLocation vertexLocation = new VertexLocation(hexLocation, VertexDirection.East);
		VertexObject settlement = new VertexObject(0, vertexLocation);
		ClientModelController clientModelController = new ClientModelController();

		assertFalse(clientModelController.canBuildSettlement(settlement));
	}

	@Test
	public void testCanBuildSettlementNotTouchingFail() {
		clientModel.getTurnTracker().setStatus("playing");				
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWheat(5);
		clientModel.getPlayers()[0].getResources().setSheep(5);
		clientModel.getPlayers()[0].getResources().setWood(5);
		HexLocation hexLocation = new HexLocation(1, 0);
		VertexLocation vertexLocation = new VertexLocation(hexLocation, VertexDirection.East);
		VertexObject settlement = new VertexObject(0, vertexLocation);
		ClientModelController clientModelController = new ClientModelController();

		assertFalse(clientModelController.canBuildSettlement(settlement));
	}

	@Test
	public void testCanBuildSettlementOnOtherSettlementFail() {
		clientModel.getTurnTracker().setStatus("playing");				
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWheat(5);
		clientModel.getPlayers()[0].getResources().setSheep(5);
		clientModel.getPlayers()[0].getResources().setWood(5);
		HexLocation hexLocation = new HexLocation(1, 1);
		VertexLocation vertexLocation = new VertexLocation(hexLocation, VertexDirection.NorthWest);
		VertexObject settlement = new VertexObject(0, vertexLocation);
		ClientModelController clientModelController = new ClientModelController();

		assertFalse(clientModelController.canBuildSettlement(settlement));
	}

	@Test
	public void testCanBuildSettlementOneSpaceFail() {
		clientModel.getTurnTracker().setStatus("playing");				
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWheat(5);
		clientModel.getPlayers()[0].getResources().setSheep(5);
		clientModel.getPlayers()[0].getResources().setWood(5);
		HexLocation hexLocation = new HexLocation(1, 1);
		VertexLocation vertexLocation = new VertexLocation(hexLocation, VertexDirection.West);
		VertexObject settlement = new VertexObject(0, vertexLocation);
		ClientModelController clientModelController = new ClientModelController();

		assertFalse(clientModelController.canBuildSettlement(settlement));
	}
	@Test 
	public void testCanMaritimeTradeSuccess(){
		clientModel.getTurnTracker().setStatus("playing");				
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWheat(5);
		clientModel.getPlayers()[0].getResources().setSheep(5);
		clientModel.getPlayers()[0].getResources().setWood(5);
		clientModel.getPlayers()[0].getResources().setOre(5);
		
		ClientModelController clientModelController = new ClientModelController();

		assertTrue(clientModelController.canMaritimeTrade(0, 3));
	}
	@Test 
	public void testCanMaritimeTradeBadOwnerFail(){
		clientModel.getTurnTracker().setStatus("playing");				
		clientModel.getPlayers()[0].getResources().setBrick(5);
		clientModel.getPlayers()[0].getResources().setWheat(5);
		clientModel.getPlayers()[0].getResources().setSheep(5);
		clientModel.getPlayers()[0].getResources().setWood(5);
		clientModel.getPlayers()[0].getResources().setOre(5);
		
		ClientModelController clientModelController = new ClientModelController();

		assertFalse(clientModelController.canMaritimeTrade(1, 3));
	}


}
