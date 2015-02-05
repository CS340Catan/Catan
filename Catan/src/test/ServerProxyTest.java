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
		
		//correct test, only if the first time this test is run
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
		
		/*
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
		*/
		
		//join game
		JoinGameParams params = new JoinGameParams("blue", 0);
		String response = serverProxy.joinGame(params);
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
	public void testResetGame() throws InvalidInputException {
		
		/*
		//login and join game so it can be reset
		UserCredentials credentials;
		boolean response;
		credentials = new UserCredentials("Sam", "sam");
		response = serverProxy.Login(credentials);
		assertTrue(response);
		
		//create game
		String title = "New Game C";
		CreateGameParams gameParams = new CreateGameParams(false, false, false, title);
		GameSummary game = serverProxy.createGame(gameParams);
		assertNotEquals(game, null);
		
		//join game
		JoinGameParams params = new JoinGameParams("yellow", game.getId());
		String joinResponse = serverProxy.joinGame(params);
		assertEquals(joinResponse, "Success");
		*/
		
		//reset game
		ClientModel resetModel = serverProxy.resetGame();
		assertEquals(resetModel, null);
		
	}
	
	@Test
	public void testGetCommands() {
		
		CommandList commands = serverProxy.getCommands();
		assertEquals(commands, null);
		
	}
	
	@Test
	public void testSetCommands() {
		
		CommandList commands = new CommandList(null);
		ClientModel model = serverProxy.setCommands(commands);
		assertEquals(model, null);
		
	}
	
	@Test
	public void testGetAITypes() {
		
		String[] response = serverProxy.getAITypes();
		assertEquals(response.length, 1);
		
	}
	
	@Test
	public void testAddAI() {
		
		AddAIParams params = new AddAIParams();
		AddAIResponse response = serverProxy.addAI(params);
		assertEquals(response, null);
	}
	
	@Test
	public void testChangeLogLevel() {
		ChangeLogLevelParams level = new ChangeLogLevelParams();
		ChangeLogLevelResponse response = serverProxy.changeLogLevel(level);
		assertEquals(response, null);
	}
	
	/* -----Move API Tests--------*/
	
	@Test
	public void testSendChat() {
		
		String content = "My Message";
		ClientModel model = serverProxy.sendChat(content);
		assertEquals(model, null);
		
	}
	
	@Test
	public void testAcceptTrade() {
		
		AcceptTradeParams params = new AcceptTradeParams(0, false);
		ClientModel model = serverProxy.acceptTrade(params);
		assertEquals(model, null);
		
	}
	
	@Test
	public void testDiscardCards() {
		
		DiscardCardsParams params = new DiscardCardsParams(0, null);
		ClientModel model = serverProxy.discardCards(params);
		assertEquals(model, null);
		
	}
	
	@Test
	public void testRollNumber() {
		
		ClientModel model = serverProxy.rollNumber(4);
		assertEquals(model, null);
		
	}
	
	@Test
	public void testBuildRoad() {
		
		BuildRoadParams params = new BuildRoadParams(0, null, false);
		ClientModel model = serverProxy.buildRoad(params);
		assertEquals(model, null);
		
	}
	
	@Test
	public void testBuildSettlement() {
		
		BuildSettlementParams params = new BuildSettlementParams(0, null, false);
		ClientModel model = serverProxy.buildSettlement(params);
		assertEquals(model, null);
		
	}
	
	@Test
	public void testBuildCity() {
				
		BuildCityParams params = new BuildCityParams(0, null);
		ClientModel model = serverProxy.buildCity(params);
		assertEquals(model, null);
		
	}
	
	@Test
	public void testOfferTrade() {
				
		TradeOfferParams params = new TradeOfferParams(0, null, 0);
		ClientModel model = serverProxy.offerTrade(params);
		assertEquals(model, null);
		
	}
	
	@Test
	public void testMaritimeTrade() {
				
		MaritimeTradeParams params = new MaritimeTradeParams(0, 0, "Wheat", "Sheep");
		ClientModel model = serverProxy.maritimeTrade(params);
		assertEquals(model, null);
		
	}
	
	@Test
	public void testRobPlayer() {
				
		MoveRobberParams params = new MoveRobberParams(0, 0, null);
		ClientModel model = serverProxy.robPlayer(params);
		assertEquals(model, null);
		
	}
	
	@Test
	public void testFinishTurn() {
		
		ClientModel model = serverProxy.finishTurn();
		assertEquals(model, null);
		
	}
	
	@Test
	public void testBuyDevCard() {
				
		ClientModel model = serverProxy.buyDevCard();
		assertEquals(model, null);
		
	}
	
	@Test
	public void testPlaySoldierCard() {
				
		MoveSoldierParams params = new MoveSoldierParams(0, 0, null);
		ClientModel model = serverProxy.playSoldierCard(params);
		assertEquals(model, null);
		
	}
	
	@Test
	public void testPlayYearOfPlentyCard() {
				
		YearOfPlentyParams params = new YearOfPlentyParams(0, null, null);
		ClientModel model = serverProxy.playYearOfPlentyCard(params);
		assertEquals(model, null);
		
	}
	
	@Test
	public void testPlayRoadBuildingCard() {
				
		BuildRoadCardParams params = new BuildRoadCardParams(0, null, null);
		ClientModel model = serverProxy.playRoadBuildingCard(params);
		assertEquals(model, null);
		
	}
	
	@Test
	public void testPlayMonopolyCard() {
				
		PlayMonopolyParams params = new PlayMonopolyParams(clientModelJson, 0);
		ClientModel model = serverProxy.playMonopolyCard(params);
		assertEquals(model, null);
		
	}

	@Test
	public void testMonumentCard() {
				
		PlayMonumentParams params = new PlayMonumentParams(0);
		ClientModel model = serverProxy.playMonument(params);
		assertEquals(model, null);
		
	}
}
