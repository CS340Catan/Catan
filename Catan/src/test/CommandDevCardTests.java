package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.commands.BuyDevCardCommand;
import server.commands.ICommand;
import server.commands.PlayMonopolyCommand;
import server.commands.PlayMonumentCommand;
import server.commands.PlayRoadBuildingCommand;
import server.commands.PlaySoldierCommand;
import server.commands.PlayYearOfPlentyCommand;
import server.facade.FacadeSwitch;
import server.model.ServerModel;
import shared.communication.BuildRoadCardParams;
import shared.communication.MoveSoldierParams;
import shared.communication.PlayMonopolyParams;
import shared.communication.PlayMonumentParams;
import shared.communication.UserActionParams;
import shared.communication.YearOfPlentyParams;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.DevCardList;
import shared.model.Player;
import shared.model.ResourceList;
import shared.model.Road;
import shared.model.VertexObject;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

public class CommandDevCardTests {
	ICommand command;
	private static String clientModelJson = "{\"deck\":{\"yearOfPlenty\":2,\"monopoly\":2,\"soldier\":14,\"roadBuilding\":2,\"monument\":5},\"map\":{\"hexes\":[{\"location\":{\"x\":0,\"y\":-2}},{\"resource\":\"ore\",\"location\":{\"x\":1,\"y\":-2},\"number\":3},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-2},\"number\":3},{\"resource\":\"wheat\",\"location\":{\"x\":-1,\"y\":-1},\"number\":8},{\"resource\":\"brick\",\"location\":{\"x\":0,\"y\":-1},\"number\":8},{\"resource\":\"sheep\",\"location\":{\"x\":1,\"y\":-1},\"number\":9},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-1},\"number\":11},{\"resource\":\"sheep\",\"location\":{\"x\":-2,\"y\":0},\"number\":10},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":0},\"number\":12},{\"resource\":\"sheep\",\"location\":{\"x\":0,\"y\":0},\"number\":10},{\"resource\":\"wheat\",\"location\":{\"x\":1,\"y\":0},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":2,\"y\":0},\"number\":5},{\"resource\":\"wood\",\"location\":{\"x\":-2,\"y\":1},\"number\":6},{\"resource\":\"brick\",\"location\":{\"x\":-1,\"y\":1},\"number\":4},{\"resource\":\"ore\",\"location\":{\"x\":0,\"y\":1},\"number\":5},{\"resource\":\"wood\",\"location\":{\"x\":1,\"y\":1},\"number\":4},{\"resource\":\"ore\",\"location\":{\"x\":-2,\"y\":2},\"number\":9},{\"resource\":\"wheat\",\"location\":{\"x\":-1,\"y\":2},\"number\":6},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":2},\"number\":2}],\"roads\":[{\"owner\":3,\"location\":{\"direction\":\"S\",\"x\":1,\"y\":0}},{\"owner\":3,\"location\":{\"direction\":\"SE\",\"x\":0,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"cities\":[],\"settlements\":[{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":1,\"y\":0}}],\"radius\":3,\"ports\":[{\"ratio\":3,\"direction\":\"S\",\"location\":{\"x\":1,\"y\":-3}},{\"ratio\":3,\"direction\":\"NE\",\"location\":{\"x\":-2,\"y\":3}},{\"ratio\":2,\"resource\":\"sheep\",\"direction\":\"SW\",\"location\":{\"x\":3,\"y\":-3}},{\"ratio\":3,\"direction\":\"NW\",\"location\":{\"x\":2,\"y\":1}},{\"ratio\":3,\"direction\":\"SE\",\"location\":{\"x\":-3,\"y\":0}},{\"ratio\":2,\"resource\":\"wood\",\"direction\":\"S\",\"location\":{\"x\":-1,\"y\":-2}},{\"ratio\":2,\"resource\":\"ore\",\"direction\":\"N\",\"location\":{\"x\":0,\"y\":3}},{\"ratio\":2,\"resource\":\"wheat\",\"direction\":\"NW\",\"location\":{\"x\":3,\"y\":-1}},{\"ratio\":2,\"resource\":\"brick\",\"direction\":\"NE\",\"location\":{\"x\":-3,\"y\":2}}],\"robber\":{\"x\":0,\"y\":-2}},\"players\":[{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":12,\"playerIndex\":0,\"name\":\"string\",\"color\":\"purple\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":13,\"playerIndex\":1,\"name\":\"test1\",\"color\":\"puce\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":14,\"playerIndex\":2,\"name\":\"test2\",\"color\":\"blue\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":12,\"cities\":4,\"settlements\":4,\"soldiers\":0,\"victoryPoints\":1,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":15,\"playerIndex\":3,\"name\":\"test3\",\"color\":\"orange\"}],\"log\":{\"lines\":[{\"source\":\"string\",\"message\":\"string built a settlement\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"}]},\"chat\":{\"lines\":[]},\"bank\":{\"brick\":24,\"wood\":24,\"sheep\":24,\"wheat\":24,\"ore\":24},\"turnTracker\":{\"status\":\"FirstRound\",\"currentTurn\":0,\"longestRoad\":-1,\"largestArmy\":-1},\"winner\":-1,\"version\":4}";

	@Before
	public void setUp() throws Exception {
		ServerModel model = (ServerModel) Serializer.deserialize(
				clientModelJson, ServerModel.class);
		FacadeSwitch.setMockServer(model);
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test BUY DEV CARD
	 */
	@Test
	public void buyDevCardTest() {
		System.out.println("Testing BuyDevCardCommand");
		int playerID = 0;
		UserActionParams params = new UserActionParams(playerID);
		Player player = FacadeSwitch.getSingleton().getServerModel()
				.getPlayers()[playerID];
		player.setResources(new ResourceList(1, 1, 1, 1, 1));
		// Testing that game state doesn't change when command throws exception
		command = new BuyDevCardCommand(params);
		try {
			command.execute();
			fail("this should fail. Not playing");
		} catch (ServerResponseException e) {
			int totalDev = player.getNewDevCards().getMonopoly()
					+ player.getNewDevCards().getYearOfPlenty()
					+ player.getNewDevCards().getRoadBuilding()
					+ player.getOldDevCards().getMonument()
					+ player.getNewDevCards().getSoldier();
			assertFalse(1 == totalDev);

			assertFalse(0 == player.getResources().getOre());
			assertFalse(0 == player.getResources().getWheat());
			assertFalse(0 == player.getResources().getSheep());
			assertFalse(0 == player.getResources().getWood());
			assertFalse(0 == player.getResources().getBrick());
		}

		FacadeSwitch.getSingleton().getServerModel().getTurnTracker()
				.setStatus("Playing");

		// Test that game data updates correctly when command execution is
		// successful
		try {
			command.execute();
			// should now have 1 Dev card (new or old)
			// should have 0 wheat ore and sheep, 1 of everything else
			int totalDev = player.getNewDevCards().getMonopoly()
					+ player.getNewDevCards().getYearOfPlenty()
					+ player.getNewDevCards().getRoadBuilding()
					+ player.getOldDevCards().getMonument()
					+ player.getNewDevCards().getSoldier();
			assertEquals(1, totalDev);

			assertEquals(0, player.getResources().getOre());
			assertEquals(0, player.getResources().getWheat());
			assertEquals(0, player.getResources().getSheep());
			assertEquals(1, player.getResources().getBrick());
			assertEquals(1, player.getResources().getWood());
		} catch (ServerResponseException e) {
			e.printStackTrace();
			fail("this should work");
		}
	}

	@Test
	public void playYearOfPlentyTest() {
		System.out.println("Testing PlayYearOfPlentyCommand");
		ServerModel model = FacadeSwitch.getSingleton().getServerModel();
		int playerID = 2;
		YearOfPlentyParams params = new YearOfPlentyParams(playerID, "Brick",
				"Wood");
		Player player = model.getPlayers()[playerID];
		// should fail and nothing should happen
		command = new PlayYearOfPlentyCommand(params);
		try {
			command.execute();
			fail("Can do should have failed");
		} catch (ServerResponseException e) {
			assertEquals(player.getResources().getBrick(), 0);
			assertEquals(player.getResources().getWood(), 0);
		}
		player.getOldDevCards().setYearOfPlenty(1);
		model.getTurnTracker().setStatus("Playing");
		model.getTurnTracker().setCurrentTurn(playerID);

		model.getBank().setBrick(1);
		model.getBank().setWood(1);

		// player should receive 1 brick and 1 wood.
		// Player should no longer have year of plenty
		// bank should have lost 1 wood and 1 brick
		try {
			command.execute();
			assertEquals(player.getResources().getBrick(), 1);
			assertEquals(player.getResources().getWood(), 1);
			assertEquals(player.getOldDevCards().getYearOfPlenty(), 0);

			assertEquals(0, model.getBank().getBrick());
			assertEquals(0, model.getBank().getWood());

		} catch (ServerResponseException e) {
			e.printStackTrace();
			fail("This should work");
		}
	}

	@Test
	public void playMonopolyCardTest() {
		System.out.println("Testing PlayMonopolyCardCommand");
		ServerModel model = FacadeSwitch.getSingleton().getServerModel();
		int playerID = 2;
		PlayMonopolyParams params = new PlayMonopolyParams("ore", playerID);
		Player player = model.getPlayers()[playerID];
		// should fail and nothing should happen
		command = new PlayMonopolyCommand(params);
		try {
			command.execute();
			fail("Can do should have failed");
		} catch (ServerResponseException e) {
			assertEquals(player.getResources().getOre(), 0);
		}
		player.getOldDevCards().setMonopoly(1);
		model.getTurnTracker().setStatus("Playing");
		model.getTurnTracker().setCurrentTurn(playerID);
		model.getPlayers()[3].setResources(new ResourceList(1, 77, 1, 1, 1));
		// Testing that model updates corretly
		// Should have grabbed everyone's ore (only player 3 had ore)
		try {
			command.execute();
			assertEquals(player.getResources().getOre(), 77);
			assertEquals(model.getPlayers()[3].getResources().getOre(), 0);
			assertEquals(player.getOldDevCards().getMonopoly(), 0);

		} catch (ServerResponseException e) {
			e.printStackTrace();
			fail("This should work");
		}
	}

	@Test
	public void playSoldierCardTest() {
		System.out.println("Testing PlaySoldierCardCommand");
		ServerModel model = FacadeSwitch.getSingleton().getServerModel();
		int playerID = 2;
		VertexObject[] settlements = new VertexObject[1];
		settlements[0] = new VertexObject(3, new VertexLocation());
		model.getMap().setSettlements(settlements);
		model.getPlayers()[3].setResources(new ResourceList(1, 1, 1, 1, 1));
		MoveSoldierParams params = new MoveSoldierParams(playerID, 3,
				new HexLocation(0, 0));
		Player player = model.getPlayers()[playerID];
		// should fail and nothing should happen
		command = new PlaySoldierCommand(params);
		try {
			command.execute();
			fail("Can do should have failed");
		} catch (ServerResponseException e) {
			assertEquals(player.getResources().totalResourceCount(), 0);
		}
		player.getOldDevCards().setSoldier(1);
		model.getTurnTracker().setStatus("Playing");
		model.getTurnTracker().setCurrentTurn(playerID);
		model.getPlayers()[3].setResources(new ResourceList(1, 1, 1, 1, 1));
		// Test the model updated correctly.
		// Robber gains 1 resource, loses soldier card, gains soldier
		// visctim loses 1 resource card
		try {
			command.execute();
			assertEquals(player.getResources().totalResourceCount(), 1);
			assertEquals(player.getOldDevCards().getSoldier(), 0);
			assertEquals(player.getSoldiers(), 1);

			assertEquals(model.getPlayers()[3].getResources()
					.totalResourceCount(), 4);
		} catch (ServerResponseException e) {
			e.printStackTrace();
			fail("This should work");
		}
	}

	@Test
	public void playMonumentTest() {
		System.out.println("Testing PlayMonumentCommand");
		int playerID = 2;
		FacadeSwitch.getSingleton().getServerModel().getPlayers()[2]
				.setOldDevCards(new DevCardList(0, 1, 0, 0, 0));
		PlayMonumentParams params = new PlayMonumentParams(playerID);
		Player player = FacadeSwitch.getSingleton().getServerModel()
				.getPlayers()[playerID];
		// should fail and nothing should happen
		command = new PlayMonumentCommand(params);
		try {
			command.execute();
			fail("Can do should have failed");
		} catch (ServerResponseException e) {
			assertEquals(player.getVictoryPoints(), 0);
		}
		player.getOldDevCards().setMonument(1);
		FacadeSwitch.getSingleton().getServerModel().getTurnTracker()
				.setStatus("Playing");
		FacadeSwitch.getSingleton().getServerModel().getTurnTracker()
				.setCurrentTurn(playerID);
		player.setVictoryPoints(9);

		// Test that the model updates correctly
		// player gets his or her point
		try {
			command.execute();
			assertTrue(player.getVictoryPoints() >= 1);
		} catch (ServerResponseException e) {
			e.printStackTrace();
			fail("This should work");
		}
	}

	@Test
	public void playRoadBuildingTest() {
		System.out.println("Testing PlayRoadBuildingTestCommand");
		int playerID = 2;
		FacadeSwitch.getSingleton().getServerModel().getPlayers()[2]
				.setOldDevCards(new DevCardList(0, 0, 1, 0, 0));
		Road[] roads = new Road[4];
		roads[0] = new Road(1, new EdgeLocation(new HexLocation(0, 0),
				EdgeDirection.North));
		roads[1] = new Road(1, new EdgeLocation(new HexLocation(0, 0),
				EdgeDirection.NorthWest));
		roads[2] = new Road(3, new EdgeLocation(new HexLocation(0, 0),
				EdgeDirection.SouthWest));
		roads[3] = new Road(3, new EdgeLocation(new HexLocation(0, 0),
				EdgeDirection.South));

		FacadeSwitch.getSingleton().getServerModel().getMap().setRoads(roads);
		BuildRoadCardParams params = new BuildRoadCardParams(
				playerID,
				new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthEast),
				new EdgeLocation(new HexLocation(0, 0), EdgeDirection.SouthEast));
		Player player = FacadeSwitch.getSingleton().getServerModel()
				.getPlayers()[playerID];
		// should fail and nothing should happen
		command = new PlayRoadBuildingCommand(params);
		try {
			command.execute();
			fail("Can do should have failed");
		} catch (ServerResponseException e) {
		}
		player.getOldDevCards().setRoadBuilding(1);
		FacadeSwitch.getSingleton().getServerModel().getTurnTracker()
				.setStatus("Playing");
		FacadeSwitch.getSingleton().getServerModel().getTurnTracker()
				.setCurrentTurn(playerID);
		ServerModel model = FacadeSwitch.getSingleton().getServerModel();

		// Test that model updates correctly
		// player loses road building card
		// player loses two roads it can build
		// two more roads on map
		try {
			command.execute();
			assertEquals(player.getRoads(), 13);
			assertTrue(player.getOldDevCards().getRoadBuilding() == 0);

			assertTrue(model.getMap().getRoads().length == 6);
		} catch (ServerResponseException e) {
			e.printStackTrace();
			fail("This should work");
		}
	}

}
