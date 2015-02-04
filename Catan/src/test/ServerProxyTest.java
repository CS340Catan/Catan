package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.communication.HTTPCommunicator;
import client.communicator.ServerProxy;
import client.model.ClientModel;
import shared.communication.*;
import shared.utils.Serializer;

public class ServerProxyTest {

	private String clientModelJson = "{\"deck\":{\"yearOfPlenty\":2,\"monopoly\":2,\"soldier\":14,\"roadBuilding\":2,\"monument\":5},\"map\":{\"hexes\":[{\"location\":{\"x\":0,\"y\":-2}},{\"resource\":\"ore\",\"location\":{\"x\":1,\"y\":-2},\"number\":3},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-2},\"number\":3},{\"resource\":\"wheat\",\"location\":{\"x\":-1,\"y\":-1},\"number\":8},{\"resource\":\"brick\",\"location\":{\"x\":0,\"y\":-1},\"number\":8},{\"resource\":\"sheep\",\"location\":{\"x\":1,\"y\":-1},\"number\":9},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-1},\"number\":11},{\"resource\":\"sheep\",\"location\":{\"x\":-2,\"y\":0},\"number\":10},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":0},\"number\":12},{\"resource\":\"sheep\",\"location\":{\"x\":0,\"y\":0},\"number\":10},{\"resource\":\"wheat\",\"location\":{\"x\":1,\"y\":0},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":2,\"y\":0},\"number\":5},{\"resource\":\"wood\",\"location\":{\"x\":-2,\"y\":1},\"number\":6},{\"resource\":\"brick\",\"location\":{\"x\":-1,\"y\":1},\"number\":4},{\"resource\":\"ore\",\"location\":{\"x\":0,\"y\":1},\"number\":5},{\"resource\":\"wood\",\"location\":{\"x\":1,\"y\":1},\"number\":4},{\"resource\":\"ore\",\"location\":{\"x\":-2,\"y\":2},\"number\":9},{\"resource\":\"wheat\",\"location\":{\"x\":-1,\"y\":2},\"number\":6},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":2},\"number\":2}],\"roads\":[{\"owner\":3,\"location\":{\"direction\":\"S\",\"x\":1,\"y\":0}},{\"owner\":3,\"location\":{\"direction\":\"SE\",\"x\":0,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"cities\":[],\"settlements\":[{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":1,\"y\":0}}],\"radius\":3,\"ports\":[{\"ratio\":3,\"direction\":\"S\",\"location\":{\"x\":1,\"y\":-3}},{\"ratio\":3,\"direction\":\"NE\",\"location\":{\"x\":-2,\"y\":3}},{\"ratio\":2,\"resource\":\"sheep\",\"direction\":\"SW\",\"location\":{\"x\":3,\"y\":-3}},{\"ratio\":3,\"direction\":\"NW\",\"location\":{\"x\":2,\"y\":1}},{\"ratio\":3,\"direction\":\"SE\",\"location\":{\"x\":-3,\"y\":0}},{\"ratio\":2,\"resource\":\"wood\",\"direction\":\"S\",\"location\":{\"x\":-1,\"y\":-2}},{\"ratio\":2,\"resource\":\"ore\",\"direction\":\"N\",\"location\":{\"x\":0,\"y\":3}},{\"ratio\":2,\"resource\":\"wheat\",\"direction\":\"NW\",\"location\":{\"x\":3,\"y\":-1}},{\"ratio\":2,\"resource\":\"brick\",\"direction\":\"NE\",\"location\":{\"x\":-3,\"y\":2}}],\"robber\":{\"x\":0,\"y\":-2}},\"players\":[{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":12,\"playerIndex\":0,\"name\":\"string\",\"color\":\"purple\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":13,\"playerIndex\":1,\"name\":\"test1\",\"color\":\"puce\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":14,\"playerIndex\":2,\"name\":\"test2\",\"color\":\"blue\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":12,\"cities\":4,\"settlements\":4,\"soldiers\":0,\"victoryPoints\":1,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":15,\"playerIndex\":3,\"name\":\"test3\",\"color\":\"orange\"}],\"log\":{\"lines\":[{\"source\":\"string\",\"message\":\"string built a settlement\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"}]},\"chat\":{\"lines\":[]},\"bank\":{\"brick\":24,\"wood\":24,\"sheep\":24,\"wheat\":24,\"ore\":24},\"turnTracker\":{\"status\":\"FirstRound\",\"currentTurn\":0,\"longestRoad\":-1,\"largestArmy\":-1},\"winner\":-1,\"version\":4}";
	private ServerProxy serverProxy;
	private ClientModel model;
	
	@Before
	public void setUp() throws Exception {
		//create and populate model and serverProxy
		model = Serializer.deserializeClientModel(clientModelJson);
		HTTPCommunicator.setServer("localhost", 8081);
		serverProxy = new ServerProxy(new HTTPCommunicator());
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void testLogin() {
		
		UserCredentials credentials;
		boolean response;
		
		//correct test
		credentials = new UserCredentials("Sam", "sam");
		response = serverProxy.Login(credentials);
		assertTrue(response);
		
		//wrong test
		credentials = new UserCredentials("Sam", "notsam");
		response = serverProxy.Login(credentials);
		assertFalse(response);

	}
	
	@Test
	public void testRegister() {
		
		UserCredentials credentials;
		boolean response;
		
		//correct test
		credentials = new UserCredentials("Freddy", "freddy");
		response = serverProxy.Register(credentials);
		assertTrue(response);
		
		//null test
		credentials = new UserCredentials("Johnny", null);
		response = serverProxy.Register(credentials);
		assertFalse(response);
		
		//user already exists test
		credentials = new UserCredentials("Sam", "sam");
		response = serverProxy.Register(credentials);
		assertFalse(response);
	}
	
	@Test
	public void testUpdateModel() {
		//same version
		ClientModel newModel = serverProxy.updateModel(0);
		assertEquals(newModel, null);
		
		//new version
		newModel = serverProxy.updateModel(1);
		assertNotEquals(model, null);
		assertNotEquals(model, newModel);
	}

	@Test
	public void testGetGameList() {
		
		GameSummary[] gameList = serverProxy.getGameList();
		assertEquals(gameList[0].getTitle(), "Default Game");
		
	}
	
	@Test
	public void testCreateGame() throws InvalidInputException {
		
		String title = "New Game A";
		CreateGameParams params = new CreateGameParams(false, false, false, title);
		
		GameSummary game = serverProxy.createGame(params);
		assertEquals(game.getTitle(), title);
	}
	
	@Test
	public void testJoinGame() throws InvalidInputException {
		
		//login so game can be joined
		UserCredentials credentials;
		boolean loginResponse;
		credentials = new UserCredentials("Sam", "sam");
		loginResponse = serverProxy.Login(credentials);
		assertTrue(loginResponse);
		
		//create new game with space to join
		String title = "New Game B";
		CreateGameParams gameParams = new CreateGameParams(false, false, false, title);
		GameSummary game = serverProxy.createGame(gameParams);
		assertNotEquals(game, null);
		
		//join game
		JoinGameParams params = new JoinGameParams("yellow", game.getId());
		String response = serverProxy.joinGame(params);
		System.out.println(response);
		assertEquals(response, "Success");
	}
	
	@Test
	public void testSaveGame() throws InvalidInputException {
		
		String filename = "My Saved Game";
		SaveParams params = new SaveParams(0, filename);
		String response = serverProxy.saveGame(params);
		assertEquals(response, "Success");
	}
	
	@Test
	public void testLoadGame() throws InvalidInputException {
		
		//save a game to load
		String filename = "Test Saved Game";
		SaveParams saveParams = new SaveParams(0, filename);
		String saveResponse = serverProxy.saveGame(saveParams);
		assertEquals(saveResponse, "Success");
		
		//load game
		LoadGameParams params = new LoadGameParams(filename);
		String response = serverProxy.loadGame(params);
		assertEquals(response, "Success");
	}
	
	@Test
	public void testResetGame() {
		
		//login so game can be reset
		UserCredentials credentials;
		boolean response;
		credentials = new UserCredentials("Sam", "sam");
		response = serverProxy.Login(credentials);
		assertTrue(response);
		
		//reset game
		ClientModel resetModel = serverProxy.resetGame();
		assertNotEquals(resetModel, null);
		//assertEquals(resetModel.getVersion(), 0);
		
	}
	
	@Test
	public void testGetCommands() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testSetCommands() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testGetAITypes() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testAddAI() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testChangeLogLevel() {
		//fail("Not yet implemented");
	}
	
	/* -----Move API Tests--------*/
	
	@Test
	public void testSendChat() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testAcceptTrade() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testDiscardCards() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testRollNumber() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testBuildRoad() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testBuildSettlement() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testBuildCity() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testOfferTrade() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testMaritimeTrade() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testRobPlayer() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testFinishTurn() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testBuyDevCard() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testPlaySoldierCard() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testPlayYearOfPlentyCard() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testPlayRoadBuildingCard() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testPlayMonopolyCard() {
		//fail("Not yet implemented");
	}

	@Test
	public void testMonumentCard() {
		//fail("Not yet implemented");
	}
}
