package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.commands.BuildCityCommand;
import server.commands.BuildRoadCommand;
import server.commands.BuildSettlementCommand;
import server.commands.CreateGameCommand;
import server.commands.ICommand;
import server.commands.RegisterCommand;
import server.facade.FacadeSwitch;
import server.facade.IServerFacade;
import server.model.GameList;
import server.model.RegisteredPlayers;
import server.model.ServerModel;
import shared.communication.BuildCityParams;
import shared.communication.BuildRoadParams;
import shared.communication.BuildSettlementParams;
import shared.communication.CreateGameParams;
import shared.communication.GameSummary;
import shared.communication.UserCredentials;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.Player;
import shared.model.ResourceList;
import shared.model.Road;
import shared.model.VertexObject;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

public class CommandBuildingTests {
	ICommand command;
	
	private static String clientModelJson = "{\"deck\":{\"yearOfPlenty\":2,\"monopoly\":2,\"soldier\":14,\"roadBuilding\":2,\"monument\":5},\"map\":{\"hexes\":[{\"location\":{\"x\":0,\"y\":-2}},{\"resource\":\"ore\",\"location\":{\"x\":1,\"y\":-2},\"number\":3},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-2},\"number\":3},{\"resource\":\"wheat\",\"location\":{\"x\":-1,\"y\":-1},\"number\":8},{\"resource\":\"brick\",\"location\":{\"x\":0,\"y\":-1},\"number\":8},{\"resource\":\"sheep\",\"location\":{\"x\":1,\"y\":-1},\"number\":9},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-1},\"number\":11},{\"resource\":\"sheep\",\"location\":{\"x\":-2,\"y\":0},\"number\":10},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":0},\"number\":12},{\"resource\":\"sheep\",\"location\":{\"x\":0,\"y\":0},\"number\":10},{\"resource\":\"wheat\",\"location\":{\"x\":1,\"y\":0},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":2,\"y\":0},\"number\":5},{\"resource\":\"wood\",\"location\":{\"x\":-2,\"y\":1},\"number\":6},{\"resource\":\"brick\",\"location\":{\"x\":-1,\"y\":1},\"number\":4},{\"resource\":\"ore\",\"location\":{\"x\":0,\"y\":1},\"number\":5},{\"resource\":\"wood\",\"location\":{\"x\":1,\"y\":1},\"number\":4},{\"resource\":\"ore\",\"location\":{\"x\":-2,\"y\":2},\"number\":9},{\"resource\":\"wheat\",\"location\":{\"x\":-1,\"y\":2},\"number\":6},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":2},\"number\":2}],\"roads\":[{\"owner\":3,\"location\":{\"direction\":\"S\",\"x\":1,\"y\":0}},{\"owner\":3,\"location\":{\"direction\":\"SE\",\"x\":0,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"cities\":[],\"settlements\":[{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":1,\"y\":0}}],\"radius\":3,\"ports\":[{\"ratio\":3,\"direction\":\"S\",\"location\":{\"x\":1,\"y\":-3}},{\"ratio\":3,\"direction\":\"NE\",\"location\":{\"x\":-2,\"y\":3}},{\"ratio\":2,\"resource\":\"sheep\",\"direction\":\"SW\",\"location\":{\"x\":3,\"y\":-3}},{\"ratio\":3,\"direction\":\"NW\",\"location\":{\"x\":2,\"y\":1}},{\"ratio\":3,\"direction\":\"SE\",\"location\":{\"x\":-3,\"y\":0}},{\"ratio\":2,\"resource\":\"wood\",\"direction\":\"S\",\"location\":{\"x\":-1,\"y\":-2}},{\"ratio\":2,\"resource\":\"ore\",\"direction\":\"N\",\"location\":{\"x\":0,\"y\":3}},{\"ratio\":2,\"resource\":\"wheat\",\"direction\":\"NW\",\"location\":{\"x\":3,\"y\":-1}},{\"ratio\":2,\"resource\":\"brick\",\"direction\":\"NE\",\"location\":{\"x\":-3,\"y\":2}}],\"robber\":{\"x\":0,\"y\":-2}},\"players\":[{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":12,\"playerIndex\":0,\"name\":\"string\",\"color\":\"purple\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":13,\"playerIndex\":1,\"name\":\"test1\",\"color\":\"puce\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":14,\"playerIndex\":2,\"name\":\"test2\",\"color\":\"blue\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":12,\"cities\":4,\"settlements\":4,\"soldiers\":0,\"victoryPoints\":1,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":15,\"playerIndex\":3,\"name\":\"test3\",\"color\":\"orange\"}],\"log\":{\"lines\":[{\"source\":\"string\",\"message\":\"string built a settlement\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"}]},\"chat\":{\"lines\":[]},\"bank\":{\"brick\":24,\"wood\":24,\"sheep\":24,\"wheat\":24,\"ore\":24},\"turnTracker\":{\"status\":\"FirstRound\",\"currentTurn\":0,\"longestRoad\":-1,\"largestArmy\":-1},\"winner\":-1,\"version\":4}";	
	@Before
	public void setUp() throws Exception {
		ServerModel model = (ServerModel) Serializer.deserialize(clientModelJson, ServerModel.class);
		FacadeSwitch.setMockServer(model);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void buildSettlementTest(){
		System.out.println("Testing Build Settlement 1");
		int playerID = 2;
		VertexObject[] blankSettlements = new VertexObject[0];
		FacadeSwitch.getSingleton().getServerModel().getMap().setSettlements(blankSettlements);
		 BuildSettlementParams params = new BuildSettlementParams(playerID, new VertexLocation(new HexLocation(0,0), VertexDirection.NorthEast), false);
		Player player = FacadeSwitch.getSingleton().getServerModel().getPlayers()[playerID];
		Road[] roads = new Road[4];
		roads[0] = new Road(playerID, new EdgeLocation(new HexLocation(0,0),EdgeDirection.North));
		roads[1] = new Road(playerID, new EdgeLocation(new HexLocation(0,0),EdgeDirection.NorthWest));
		roads[2] = new Road(playerID, new EdgeLocation(new HexLocation(0,0),EdgeDirection.SouthWest));
		roads[3] = new Road(playerID, new EdgeLocation(new HexLocation(0,0),EdgeDirection.South));
		
		FacadeSwitch.getSingleton().getServerModel().getMap().setRoads(roads);
		//should fail and nothing should happen
		command = new BuildSettlementCommand(params);
		try {
			command.execute();
			fail("Can do should have failed");
		} catch (ServerResponseException e) {
			assertEquals(5,player.getSettlements());
		}
		FacadeSwitch.getSingleton().getServerModel().getTurnTracker().setStatus("Playing");
		FacadeSwitch.getSingleton().getServerModel().getTurnTracker().setCurrentTurn(playerID);
		System.out.println("Testing Build Settlement 2");
		try {
			command.execute();
			fail("Can do should have failed");
		} catch (ServerResponseException e) {
			assertEquals(5,player.getSettlements());
		}
		FacadeSwitch.getSingleton().getServerModel().getPlayers()[2].setResources(new ResourceList(10,10,10,10,10));
		System.out.println("Testing Build Settlement 3");
		try {
			command.execute();
			assertTrue(player.getSettlements() == 4);
			VertexObject[] settlements = FacadeSwitch.getSingleton().getServerModel().getMap().getSettlements();
			assertTrue(settlements[0].getOwner() == playerID);
			assertTrue(settlements[0].getLocation().getHexLoc().equals(new HexLocation(0,0)));
			assertTrue(settlements[0].getLocation().getDir().equals(VertexDirection.NorthEast));
		} catch (ServerResponseException e) {
			e.printStackTrace();
			fail("This should work");
		}
		
	}
	@Test
	public void buildCityTest(){
		System.out.println("Testing Build City 1");
		int playerID = 2;
		VertexObject[] blankSettlements = new VertexObject[0];
		FacadeSwitch.getSingleton().getServerModel().getMap().setSettlements(blankSettlements);
		VertexObject[] settlements = new VertexObject[1];
		settlements[0] = new VertexObject(playerID,  new VertexLocation(new HexLocation(0,0), VertexDirection.NorthEast));
		FacadeSwitch.getSingleton().getServerModel().getMap().setSettlements(settlements);
		BuildCityParams params = new BuildCityParams(playerID, new VertexLocation(new HexLocation(0,0), VertexDirection.NorthEast));
		Player player = FacadeSwitch.getSingleton().getServerModel().getPlayers()[playerID];
		Road[] roads = new Road[4];
		roads[0] = new Road(playerID, new EdgeLocation(new HexLocation(0,0),EdgeDirection.North));
		roads[1] = new Road(playerID, new EdgeLocation(new HexLocation(0,0),EdgeDirection.NorthWest));
		roads[2] = new Road(playerID, new EdgeLocation(new HexLocation(0,0),EdgeDirection.SouthWest));
		roads[3] = new Road(playerID, new EdgeLocation(new HexLocation(0,0),EdgeDirection.South));
		
		FacadeSwitch.getSingleton().getServerModel().getMap().setRoads(roads);
		//should fail and nothing should happen
		command = new BuildCityCommand(params);
		try {
			command.execute();
			fail("Can do should have failed");
		} catch (ServerResponseException e) {
			assertEquals(4,player.getCities());
		}
		FacadeSwitch.getSingleton().getServerModel().getTurnTracker().setStatus("Playing");
		FacadeSwitch.getSingleton().getServerModel().getTurnTracker().setCurrentTurn(playerID);
		System.out.println("Testing Build Settlement 2");
		try {
			command.execute();
			fail("Can do should have failed");
		} catch (ServerResponseException e) {
			assertEquals(4,player.getCities());
		}
		FacadeSwitch.getSingleton().getServerModel().getPlayers()[2].setResources(new ResourceList(10,10,10,10,10));	
		System.out.println("Testing Build Settlement 3");
		try {
			command.execute();
			assertEquals(3,player.getCities());
			VertexObject[] cities = FacadeSwitch.getSingleton().getServerModel().getMap().getCities();
			assertTrue(cities[0].getOwner() == playerID);
			assertTrue(cities[0].getLocation().getHexLoc().equals(new HexLocation(0,0)));
			assertTrue(cities[0].getLocation().getDir().equals(VertexDirection.NorthEast));
		} catch (ServerResponseException e) {
			e.printStackTrace();
			fail("This should work");
		}
	}
	@Test
	public void buildRoadTest(){
		int playerID = 2;
		System.out.println("Testing Build Road 1");
		VertexObject[] settlements = new VertexObject[1];
		settlements[0] = new VertexObject(playerID,  new VertexLocation(new HexLocation(0,0), VertexDirection.NorthEast));
		FacadeSwitch.getSingleton().getServerModel().getMap().setSettlements(settlements);
		
		BuildRoadParams params = new BuildRoadParams(playerID, new EdgeLocation(new HexLocation(0,0),EdgeDirection.SouthEast), false);
		Player player = FacadeSwitch.getSingleton().getServerModel().getPlayers()[playerID];
		
		Road[] roads = new Road[4];
		roads[0] = new Road(playerID, new EdgeLocation(new HexLocation(0,0),EdgeDirection.North));
		roads[1] = new Road(playerID, new EdgeLocation(new HexLocation(0,0),EdgeDirection.NorthWest));
		roads[2] = new Road(playerID, new EdgeLocation(new HexLocation(0,0),EdgeDirection.SouthWest));
		roads[3] = new Road(playerID, new EdgeLocation(new HexLocation(0,0),EdgeDirection.South));
		
		FacadeSwitch.getSingleton().getServerModel().getMap().setRoads(roads);
		//should fail and nothing should happen
		command = new BuildRoadCommand(params);
		try {
			command.execute();
			fail("Can do should have failed");
		} catch (ServerResponseException e) {
			assertEquals(15,player.getRoads());
		}
		FacadeSwitch.getSingleton().getServerModel().getTurnTracker().setStatus("Playing");
		FacadeSwitch.getSingleton().getServerModel().getTurnTracker().setCurrentTurn(playerID);
		System.out.println("Testing Build Road 2");
		try {
			command.execute();
			fail("Can do should have failed");
		} catch (ServerResponseException e) {
			assertEquals(15,player.getRoads());
		}
		FacadeSwitch.getSingleton().getServerModel().getPlayers()[2].setResources(new ResourceList(10,10,10,10,10));	
		System.out.println("Testing Build Road 3");
		try {
			command.execute();
			assertEquals(14,player.getRoads());
			Road[] newRoads = FacadeSwitch.getSingleton().getServerModel().getMap().getRoads();
			assertTrue(newRoads[4].getOwner() == playerID);
			assertTrue(newRoads[4].getLocation().getHexLoc().equals(new HexLocation(0,0)));
			assertEquals(EdgeDirection.SouthEast,newRoads[4].getLocation().getDir());
		} catch (ServerResponseException e) {
			e.printStackTrace();
			fail("This should work");
		}
	}
	
}
