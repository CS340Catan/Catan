package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.communicator.HTTPCommunicator;
import client.communicator.ServerProxy;
import client.model.ClientModel;
import shared.communication.*;
import shared.data.GameInfo;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.model.ResourceList;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

public class ServerProxyTest {

	private String clientModelJson = "{\"deck\":{\"yearOfPlenty\":2,\"monopoly\":2,\"soldier\":14,\"roadBuilding\":2,\"monument\":5},\"map\":{\"hexes\":[{\"location\":{\"x\":0,\"y\":-2}},{\"resource\":\"ore\",\"location\":{\"x\":1,\"y\":-2},\"number\":3},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-2},\"number\":3},{\"resource\":\"wheat\",\"location\":{\"x\":-1,\"y\":-1},\"number\":8},{\"resource\":\"brick\",\"location\":{\"x\":0,\"y\":-1},\"number\":8},{\"resource\":\"sheep\",\"location\":{\"x\":1,\"y\":-1},\"number\":9},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-1},\"number\":11},{\"resource\":\"sheep\",\"location\":{\"x\":-2,\"y\":0},\"number\":10},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":0},\"number\":12},{\"resource\":\"sheep\",\"location\":{\"x\":0,\"y\":0},\"number\":10},{\"resource\":\"wheat\",\"location\":{\"x\":1,\"y\":0},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":2,\"y\":0},\"number\":5},{\"resource\":\"wood\",\"location\":{\"x\":-2,\"y\":1},\"number\":6},{\"resource\":\"brick\",\"location\":{\"x\":-1,\"y\":1},\"number\":4},{\"resource\":\"ore\",\"location\":{\"x\":0,\"y\":1},\"number\":5},{\"resource\":\"wood\",\"location\":{\"x\":1,\"y\":1},\"number\":4},{\"resource\":\"ore\",\"location\":{\"x\":-2,\"y\":2},\"number\":9},{\"resource\":\"wheat\",\"location\":{\"x\":-1,\"y\":2},\"number\":6},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":2},\"number\":2}],\"roads\":[{\"owner\":3,\"location\":{\"direction\":\"S\",\"x\":1,\"y\":0}},{\"owner\":3,\"location\":{\"direction\":\"SE\",\"x\":0,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"cities\":[],\"settlements\":[{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":1,\"y\":0}}],\"radius\":3,\"ports\":[{\"ratio\":3,\"direction\":\"S\",\"location\":{\"x\":1,\"y\":-3}},{\"ratio\":3,\"direction\":\"NE\",\"location\":{\"x\":-2,\"y\":3}},{\"ratio\":2,\"resource\":\"sheep\",\"direction\":\"SW\",\"location\":{\"x\":3,\"y\":-3}},{\"ratio\":3,\"direction\":\"NW\",\"location\":{\"x\":2,\"y\":1}},{\"ratio\":3,\"direction\":\"SE\",\"location\":{\"x\":-3,\"y\":0}},{\"ratio\":2,\"resource\":\"wood\",\"direction\":\"S\",\"location\":{\"x\":-1,\"y\":-2}},{\"ratio\":2,\"resource\":\"ore\",\"direction\":\"N\",\"location\":{\"x\":0,\"y\":3}},{\"ratio\":2,\"resource\":\"wheat\",\"direction\":\"NW\",\"location\":{\"x\":3,\"y\":-1}},{\"ratio\":2,\"resource\":\"brick\",\"direction\":\"NE\",\"location\":{\"x\":-3,\"y\":2}}],\"robber\":{\"x\":0,\"y\":-2}},\"players\":[{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":12,\"playerIndex\":0,\"name\":\"string\",\"color\":\"purple\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":13,\"playerIndex\":1,\"name\":\"test1\",\"color\":\"puce\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":14,\"playerIndex\":2,\"name\":\"test2\",\"color\":\"blue\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":12,\"cities\":4,\"settlements\":4,\"soldiers\":0,\"victoryPoints\":1,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":15,\"playerIndex\":3,\"name\":\"test3\",\"color\":\"orange\"}],\"log\":{\"lines\":[{\"source\":\"string\",\"message\":\"string built a settlement\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"}]},\"chat\":{\"lines\":[]},\"bank\":{\"brick\":24,\"wood\":24,\"sheep\":24,\"wheat\":24,\"ore\":24},\"turnTracker\":{\"status\":\"FirstRound\",\"currentTurn\":0,\"longestRoad\":-1,\"largestArmy\":-1},\"winner\":-1,\"version\":4}";
	private ServerProxy serverProxy;
	private ClientModel model;

	@Before
	public void setUp() throws Exception {
		// create and populate model and serverProxy
		model = Serializer.deserializeClientModel(clientModelJson);
		HTTPCommunicator.setServer("localhost", 8081);
		serverProxy = ServerProxy.getSingleton();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testLogin() {
		System.out.println("Testing Login 1");
		UserCredentials credentials;
		boolean response;

		// correct test
		credentials = new UserCredentials("Sam", "sam");
		try {
			response = serverProxy.login(credentials);
			assertTrue(response);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

		// wrong test
		credentials = new UserCredentials("Sam", "notsam");
		System.out.println("Testing Login 2");
		try {
			response = serverProxy.login(credentials);
			assertFalse(response);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testRegister() {
		System.out.println("Testing Register 1");
		UserCredentials credentials;
		boolean response;

		// correct test, only if the first time this test is run
		credentials = new UserCredentials("Freddy", "freddy");
		try {
			response = serverProxy.Register(credentials);
			assertTrue(response);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

		// null test
		System.out.println("Testing Register 2");
		credentials = new UserCredentials("Johnny", null);
		try {
			response = serverProxy.Register(credentials);
			assertFalse(response);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

		// user already exists test
		System.out.println("Testing Register 3");
		credentials = new UserCredentials("Sam", "sam");
		try {
			response = serverProxy.Register(credentials);
			assertFalse(response);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	// @Test
	// public void testUpdateModel() {
	// // same version
	// ClientModel newModel;
	// try {
	// newModel = serverProxy.updateModel(0);
	// assertNotEquals(newModel, null);
	// } catch (ServerResponseException e) {
	// assertTrue(e.getMessage().startsWith("ERROR"));
	// }
	//
	// // new version
	// try {
	// newModel = serverProxy.updateModel(1);
	// assertNotEquals(model, null);
	// assertNotEquals(model, newModel);
	// } catch (ServerResponseException e) {
	// assertTrue(e.getMessage().startsWith("ERROR"));
	// }
	//
	// }

	@Test
	public void testGetGameList() {

		/*
		 * GameInfo[] gameList; try { gameList = serverProxy.getGameList();
		 * assertNotNull(gameList); } catch (ServerResponseException e) {
		 * assertTrue(e.getMessage().startsWith("ERROR")); }
		 */

	}

	@Test
	public void testCreateGame() throws InvalidInputException {
		System.out.println("Testing CreateGame 1");
		String title = "New Game A";
		CreateGameParams params = new CreateGameParams(false, false, false,
				title);

		GameInfo game;
		try {
			game = serverProxy.createGame(params);
			assertNotNull(game);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testJoinGame() throws InvalidInputException {
		System.out.println("Testing JoinGame 1");
		try {
			// login so game can be joined
			UserCredentials credentials = new UserCredentials("Sam", "sam");
			boolean loginResponse = serverProxy.login(credentials);
			assertTrue(loginResponse);

			// join game
			JoinGameParams params = new JoinGameParams("blue", 2);

			String response = serverProxy.joinGame(params);
			System.out.println("Test");
			assertEquals(response, "Success");
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}

	}

	@Test
	public void testSaveGame() throws InvalidInputException {
		System.out.println("Testing SaveGame 1");
		String filename = "My Saved Game";
		SaveParams params = new SaveParams(0, filename);
		String response;
		try {
			response = serverProxy.saveGame(params);
			assertEquals(response, "\"Success\"");
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testLoadGame() throws InvalidInputException {
		System.out.println("Testing LoadGame 1");
		// save a game to load
		String filename = "Test Saved Game";
		SaveParams saveParams = new SaveParams(0, filename);
		String saveResponse;
		try {
			saveResponse = serverProxy.saveGame(saveParams);
			assertEquals(saveResponse, "\"Success\"");
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

		System.out.println("Testing LoadGame 2");
		// load game
		LoadGameParams params = new LoadGameParams(filename);
		String response;
		try {
			response = serverProxy.loadGame(params);
			assertEquals(response, "\"Success\"");
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testResetGame() throws InvalidInputException {

		// //login and join game so it can be reset
		// UserCredentials credentials;
		// boolean response; credentials = new UserCredentials("Sam", "sam");
		// response = serverProxy.login(credentials); assertTrue(response);
		//
		// //create game
		// String title = "New Game C";
		// CreateGameParams gameParams = new CreateGameParams(false, false,
		// false, title);
		// GameInfo game = serverProxy.createGame(gameParams);
		// assertNotEquals(game, null);
		//
		// //join game
		// JoinGameParams params = new JoinGameParams("yellow",
		// game.getId()); String joinResponse = serverProxy.joinGame(params);
		// assertEquals(joinResponse, "Success");

		// reset game
		// ClientModel resetModel;
		// try {
		// resetModel = serverProxy.resetGame();
		// assertNotEquals(resetModel, null);
		// } catch (ServerResponseException e) {
		// assertTrue(e.getMessage().startsWith("ERROR"));
		// }

	}

	@Test
	public void testGetCommands() {
		System.out.println("Testing Get Commands 1");
		CommandList commands;
		try {
			commands = serverProxy.getCommands();
			assertNotEquals(commands, null);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testSetCommands() {
		System.out.println("Testing SetCommands 1");
		CommandList commands = new CommandList(null);
		ClientModel model;
		try {
			model = serverProxy.setCommands(commands);
			assertNotEquals(model, null);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testGetAITypes() {
		System.out.println("Testing GetAITypes 1");
		String[] response;
		try {
			response = serverProxy.getAITypes();
			assertNotNull(response);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testAddAI() {
		System.out.println("Testing AddAI 1");
		AddAIParams params = new AddAIParams();
		AddAIResponse response;
		try {
			response = serverProxy.addAI(params);
			assertNotEquals(response, null);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testChangeLogLevel() {
		System.out.println("Testing ChangeLogLevel 1");
		ChangeLogLevelParams level = new ChangeLogLevelParams();
		level.setLogLevel("SEVERE");
		ChangeLogLevelResponse response;
		try {
			response = serverProxy.changeLogLevel(level);
			assertNotEquals(response, null);
			assertEquals(response.getResponse(), "Success");
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));

		}

	}

	/* -----Move API Tests-------- */

	@Test
	public void testSendChat() {
		System.out.println("Testing SendChat 1");
		String content = "My Message";
		ChatMessage chatMessage = new ChatMessage(1, content);
		ClientModel model;
		try {
			model = serverProxy.sendChat(chatMessage);
			assertNotEquals(model, null);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testAcceptTrade() {

		System.out.println("Testing AcceptTrade 1");
		AcceptTradeParams params = new AcceptTradeParams(0, false);
		ClientModel model;
		try {
			model = serverProxy.acceptTrade(params);
			assertNotEquals(model, null);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testDiscardCards() {

		System.out.println("Testing DiscardCards 1");
		DiscardCardsParams params = new DiscardCardsParams(0, null);
		ClientModel model;
		try {
			model = serverProxy.discardCards(params);
			assertNotEquals(model, null);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testRollNumber() {

		System.out.println("Testing RollNumber 1");
		ClientModel model;
		try {
			model = serverProxy.rollNumber(4);
			assertNotEquals(model, null);

		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testBuildRoad() {
		System.out.println("Testing BuildRoad 1");
		EdgeLocation location = new EdgeLocation(new HexLocation(0, 1),
				EdgeDirection.North);
		BuildRoadParams params = new BuildRoadParams(0, location, false);
		ClientModel model;
		try {
			model = serverProxy.buildRoad(params);
			assertNotEquals(model, null);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testBuildSettlement() {
		System.out.println("Testing BuildSettlement 1");
		VertexLocation location = new VertexLocation();
		BuildSettlementParams params = new BuildSettlementParams(0, location,
				false);
		ClientModel model;
		try {
			model = serverProxy.buildSettlement(params);
			assertNotEquals(model, null);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testBuildCity() {
		System.out.println("Testing BuildCity 1");
		VertexLocation location = new VertexLocation();
		BuildCityParams params = new BuildCityParams(0, location);
		ClientModel model;
		try {
			model = serverProxy.buildCity(params);
			assertNotEquals(model, null);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testOfferTrade() {
		System.out.println("Testing OfferTrade 1");
		ResourceList offer = new ResourceList(-1, 1, 1, 1, 1);
		TradeOfferParams params = new TradeOfferParams(0, offer, 1);
		ClientModel model;
		try {
			model = serverProxy.offerTrade(params);
			assertNotEquals(model, null);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testMaritimeTrade() {
		System.out.println("Testing MaritimeTrade 1");
		MaritimeTradeParams params = new MaritimeTradeParams(0, 0, "Wheat",
				"Sheep");
		ClientModel model;
		try {
			model = serverProxy.maritimeTrade(params);
			assertNotEquals(model, null);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testRobPlayer() {
		System.out.println("Testing RobPlayer 1");
		MoveRobberParams params = new MoveRobberParams(0, 0, null);
		ClientModel model;
		try {
			model = serverProxy.robPlayer(params);
			assertNotEquals(model, null);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testFinishTurn() {
		System.out.println("Testing FinishTurn 1");
		ClientModel model;
		UserActionParams params = new UserActionParams(0);
		params.setType("finishTurn");
		try {
			model = serverProxy.finishTurn(params);
			assertNotEquals(model, null);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testBuyDevCard() {
		System.out.println("Testing BuyDevCard 1");
		ClientModel model;
		UserActionParams params = new UserActionParams(0);
		params.setType("buyDevCard");
		try {
			model = serverProxy.buyDevCard(params);
			assertNotEquals(model, null);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testPlaySoldierCard() {
		System.out.println("Testing PlaySoldierCard 1");
		MoveSoldierParams params = new MoveSoldierParams(0, 0, null);
		ClientModel model;
		try {
			model = serverProxy.playSoldierCard(params);
			assertNotEquals(model, null);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testPlayYearOfPlentyCard() {
		System.out.println("Testing YearofPlentyCard 1");
		YearOfPlentyParams params = new YearOfPlentyParams(0, null, null);
		ClientModel model;
		try {
			model = serverProxy.playYearOfPlentyCard(params);
			assertNotEquals(model, null);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testPlayRoadBuildingCard() {
		System.out.println("Testing PlayRoadBuildingCard 1");
		EdgeLocation location = new EdgeLocation(new HexLocation(0, 1),
				EdgeDirection.North);

		BuildRoadCardParams params = new BuildRoadCardParams(0, location,
				location);
		ClientModel model;
		try {
			model = serverProxy.playRoadBuildingCard(params);
			assertNotEquals(model, null);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testPlayMonopolyCard() {
		System.out.println("Testing PlayMonopolyCard 1");
		PlayMonopolyParams params = new PlayMonopolyParams(clientModelJson, 0);
		ClientModel model;
		try {
			model = serverProxy.playMonopolyCard(params);
			assertNotEquals(model, null);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}

	@Test
	public void testMonumentCard() {
		System.out.println("Testing MonumentCard 1");
		PlayMonumentParams params = new PlayMonumentParams(0);
		ClientModel model;
		try {
			model = serverProxy.playMonument(params);
			assertNotEquals(model, null);
		} catch (ServerResponseException e) {
			assertTrue(e.getMessage().startsWith("ERROR"));
		}

	}
}
