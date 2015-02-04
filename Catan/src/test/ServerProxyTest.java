package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.communication.HTTPCommunicator;
import client.communicator.ServerProxy;
import client.model.ClientModel;
import shared.communication.*;

public class ServerProxyTest {

	private ServerProxy serverProxy;
	private ClientModel model;
	
	@Before
	public void setUp() throws Exception {
		//create and populate model and serverProxy
		model = new ClientModel();
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
		response = serverProxy.Login(credentials);
		assertTrue(response);
		
		//null test
		credentials = new UserCredentials("Johnny", null);
		response = serverProxy.Login(credentials);
		assertFalse(response);
		
		//already exists test
		credentials = new UserCredentials("Sam", "sam");
		response = serverProxy.Login(credentials);
		assertFalse(response);
	}
	
	@Test
	public void testUpdateModel() {
		//same version
		ClientModel newModel = serverProxy.updateModel(0);
		assertEquals(model, newModel);
		
		//new version
		newModel = serverProxy.updateModel(1);
		assertNotEquals(model, newModel);
	}

	@Test
	public void testGetGameList() {
		
		GamesList gamesList = serverProxy.getGameList();
		assertEquals(gamesList.getGames().size(), 4); //?? real size?
		
	}
	
	@Test
	public void testCreateGame() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testJoinGame() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testSaveGame() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testLoadGame() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testGetCurrentGame() {
		//fail("Not yet implemented");
	}
	
	@Test
	public void testResetGame() {
		//fail("Not yet implemented");
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
