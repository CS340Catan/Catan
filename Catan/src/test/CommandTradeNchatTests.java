package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.commands.ICommand;
import server.commands.MaritimeTradeCommand;
import server.commands.OfferTradeCommand;
import server.commands.SendChatCommand;
import server.facade.FacadeSwitch;
import server.model.ServerModel;
import shared.communication.ChatMessage;
import shared.communication.MaritimeTradeParams;
import shared.communication.TradeOfferParams;
import shared.model.ResourceList;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

public class CommandTradeNchatTests {
	ICommand command;
	
	@Before
	public void setUp() throws Exception {
		//Default Game
		ServerModel serverModel = Serializer
				.deserializeServerModel("{\"deck\":{\"yearOfPlenty\":2,\"monopoly\":2,\"soldier\":14,\"roadBuilding\":2,\"monument\":5},\"map\":{\"hexes\":[{\"location\":{\"x\":0,\"y\":-2}},{\"resource\":\"brick\",\"location\":{\"x\":1,\"y\":-2},\"number\":4},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-2},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":-1,\"y\":-1},\"number\":8},{\"resource\":\"wood\",\"location\":{\"x\":0,\"y\":-1},\"number\":3},{\"resource\":\"ore\",\"location\":{\"x\":1,\"y\":-1},\"number\":9},{\"resource\":\"sheep\",\"location\":{\"x\":2,\"y\":-1},\"number\":12},{\"resource\":\"ore\",\"location\":{\"x\":-2,\"y\":0},\"number\":5},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":0},\"number\":10},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":0},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":1,\"y\":0},\"number\":5},{\"resource\":\"wheat\",\"location\":{\"x\":2,\"y\":0},\"number\":6},{\"resource\":\"wheat\",\"location\":{\"x\":-2,\"y\":1},\"number\":2},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":1},\"number\":9},{\"resource\":\"wood\",\"location\":{\"x\":0,\"y\":1},\"number\":4},{\"resource\":\"sheep\",\"location\":{\"x\":1,\"y\":1},\"number\":10},{\"resource\":\"wood\",\"location\":{\"x\":-2,\"y\":2},\"number\":6},{\"resource\":\"ore\",\"location\":{\"x\":-1,\"y\":2},\"number\":3},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":2},\"number\":8}],\"roads\":[{\"owner\":1,\"location\":{\"direction\":\"S\",\"x\":-1,\"y\":-1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":-2}},{\"owner\":2,\"location\":{\"direction\":\"S\",\"x\":1,\"y\":-1}},{\"owner\":0,\"location\":{\"direction\":\"S\",\"x\":0,\"y\":1}},{\"owner\":2,\"location\":{\"direction\":\"S\",\"x\":0,\"y\":0}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-2,\"y\":1}},{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"cities\":[],\"settlements\":[{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SE\",\"x\":1,\"y\":-2}},{\"owner\":2,\"location\":{\"direction\":\"SW\",\"x\":0,\"y\":0}},{\"owner\":2,\"location\":{\"direction\":\"SW\",\"x\":1,\"y\":-1}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-2,\"y\":1}},{\"owner\":0,\"location\":{\"direction\":\"SE\",\"x\":0,\"y\":1}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":-1}},{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"radius\":3,\"ports\":[{\"ratio\":3,\"direction\":\"SE\",\"location\":{\"x\":-3,\"y\":0}},{\"ratio\":2,\"resource\":\"brick\",\"direction\":\"NE\",\"location\":{\"x\":-2,\"y\":3}},{\"ratio\":2,\"resource\":\"wood\",\"direction\":\"NE\",\"location\":{\"x\":-3,\"y\":2}},{\"ratio\":2,\"resource\":\"wheat\",\"direction\":\"S\",\"location\":{\"x\":-1,\"y\":-2}},{\"ratio\":3,\"direction\":\"N\",\"location\":{\"x\":0,\"y\":3}},{\"ratio\":2,\"resource\":\"sheep\",\"direction\":\"NW\",\"location\":{\"x\":3,\"y\":-1}},{\"ratio\":3,\"direction\":\"SW\",\"location\":{\"x\":3,\"y\":-3}},{\"ratio\":2,\"resource\":\"ore\",\"direction\":\"S\",\"location\":{\"x\":1,\"y\":-3}},{\"ratio\":3,\"direction\":\"NW\",\"location\":{\"x\":2,\"y\":1}}],\"robber\":{\"x\":0,\"y\":-2}},\"players\":[{\"resources\":{\"brick\":0,\"wood\":1,\"sheep\":1,\"wheat\":1,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":0,\"playerIndex\":0,\"name\":\"Sam\",\"color\":\"orange\"},{\"resources\":{\"brick\":0,\"wood\":1,\"sheep\":0,\"wheat\":1,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":1,\"playerIndex\":1,\"name\":\"Brooke\",\"color\":\"blue\"},{\"resources\":{\"brick\":0,\"wood\":1,\"sheep\":1,\"wheat\":1,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":10,\"playerIndex\":2,\"name\":\"Pete\",\"color\":\"red\"},{\"resources\":{\"brick\":1,\"wood\":1,\"sheep\":0,\"wheat\":1,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":11,\"playerIndex\":3,\"name\":\"Mark\",\"color\":\"green\"}],\"log\":{\"lines\":[{\"source\":\"Sam\",\"message\":\"Sam built a road\"},{\"source\":\"Sam\",\"message\":\"Sam built a settlement\"},{\"source\":\"Sam\",\"message\":\"Sam\u0027s turn just ended\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a road\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a settlement\"},{\"source\":\"Brooke\",\"message\":\"Brooke\u0027s turn just ended\"},{\"source\":\"Pete\",\"message\":\"Pete built a road\"},{\"source\":\"Pete\",\"message\":\"Pete built a settlement\"},{\"source\":\"Pete\",\"message\":\"Pete\u0027s turn just ended\"},{\"source\":\"Mark\",\"message\":\"Mark built a road\"},{\"source\":\"Mark\",\"message\":\"Mark built a settlement\"},{\"source\":\"Mark\",\"message\":\"Mark\u0027s turn just ended\"},{\"source\":\"Mark\",\"message\":\"Mark built a road\"},{\"source\":\"Mark\",\"message\":\"Mark built a settlement\"},{\"source\":\"Mark\",\"message\":\"Mark\u0027s turn just ended\"},{\"source\":\"Pete\",\"message\":\"Pete built a road\"},{\"source\":\"Pete\",\"message\":\"Pete built a settlement\"},{\"source\":\"Pete\",\"message\":\"Pete\u0027s turn just ended\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a road\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a settlement\"},{\"source\":\"Brooke\",\"message\":\"Brooke\u0027s turn just ended\"},{\"source\":\"Sam\",\"message\":\"Sam built a road\"},{\"source\":\"Sam\",\"message\":\"Sam built a settlement\"},{\"source\":\"Sam\",\"message\":\"Sam\u0027s turn just ended\"}]},\"chat\":{\"lines\":[]},\"bank\":{\"brick\":23,\"wood\":21,\"sheep\":20,\"wheat\":22,\"ore\":22},\"turnTracker\":{\"status\":\"Rolling\",\"currentTurn\":0,\"longestRoad\":-1,\"largestArmy\":-1},\"winner\":-1,\"version\":0}");
		FacadeSwitch.setMockServer(serverModel);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSendChat() {
		System.out.println("Testing send chat pass1");
		ChatMessage params = new ChatMessage(0, "Hi there");
		command = new SendChatCommand(params);
		try {
			command.execute();
			assertTrue(FacadeSwitch.getSingleton().getServerModel().getChat().getLines()[0].getMessage().equals(
					"Hi there"));
			assertTrue(FacadeSwitch.getSingleton().getServerModel().getChat().getLines()[0].getSource().equals(
					"Sam"));
		} catch (ServerResponseException e) {
			e.printStackTrace();
			fail("this should work");
		}

		System.out.println("Testing send chat pass2");
		params = new ChatMessage(1, "Hey man");
		command = new SendChatCommand(params);
		try {
			command.execute();
			assertTrue(FacadeSwitch.getSingleton().getServerModel().getChat().getLines()[1].getMessage().equals(
					"Hey man"));
			assertTrue(FacadeSwitch.getSingleton().getServerModel().getChat().getLines()[1].getSource().equals(
					"Brooke"));
		} catch (ServerResponseException e) {
			e.printStackTrace();
			fail("this should work");
		}

	}
	
	@Test
	public void testOfferTrade() {
		//Sam\","brick\":0,\"wood\":1,\"sheep\":1,\"wheat\":1,\"ore\"
		//"Brooke\",{\"brick\":0,\"wood\":1,\"sheep\":0,\"wheat\":1,\"ore\":0}
		System.out.println("Testing offer trade fail");
		ResourceList offer = new ResourceList(-2,0,0,1,0); //Sam gives Brooke 2 brick for 1 wheat (he only has 1 brick)
		//public ResourceList(int brick, int ore, int sheep, int wheat, int wood)
		TradeOfferParams params = new TradeOfferParams(0, offer, 1);
		//What you get (+) and what you give (-),
		command = new OfferTradeCommand(params);
		FacadeSwitch.getSingleton().getServerModel().getTurnTracker().setStatus("Playing");
		try {
			command.execute();
			fail("this should fail. Not playing");
		} catch (ServerResponseException e) {
			assertTrue(FacadeSwitch.getSingleton().getServerModel().getTradeOffer().getReceiver() == -1);
			assertTrue(FacadeSwitch.getSingleton().getServerModel().getTradeOffer().getSender() == -1);
		}
		
		offer = new ResourceList(0,2,0,0,-2); //Sam gives Brooke 2 wood for 2 ore
		params = new TradeOfferParams(0, offer, 1);
		command = new OfferTradeCommand(params);
		try {
			command.execute();
			assertTrue(FacadeSwitch.getSingleton().getServerModel().getTradeOffer().getReceiver() == 1);
			assertTrue(FacadeSwitch.getSingleton().getServerModel().getTradeOffer().getSender() == 0);
			assertTrue(FacadeSwitch.getSingleton().getServerModel().getTradeOffer().getResourceList().getBrick() == 0);
			assertTrue(FacadeSwitch.getSingleton().getServerModel().getTradeOffer().getResourceList().getWood() == -2);
			assertTrue(FacadeSwitch.getSingleton().getServerModel().getTradeOffer().getResourceList().getOre() == 2);
			assertTrue(FacadeSwitch.getSingleton().getServerModel().getTradeOffer().getResourceList().getWheat() == 0);
			assertTrue(FacadeSwitch.getSingleton().getServerModel().getTradeOffer().getResourceList().getSheep() == 0);
		} catch (ServerResponseException e) {
			fail("this should work");
		}
	}
	
	@Test
	public void testAcceptTrade() {
		
		
	}
	
	@Test
	public void testMaritimeTrade() {
		
		System.out.println("Testing Maritime Trade");
		ResourceList resources = FacadeSwitch.getSingleton().getServerModel().getPlayers()[0].getResources();
		resources.setBrick(4);
		resources.setOre(0);
		int oldBankBricks = FacadeSwitch.getSingleton().getServerModel().getBank().getBrick();
		
		MaritimeTradeParams params = new MaritimeTradeParams(0, 4, "BRICK", "ORE");
		command = new MaritimeTradeCommand(params, 0);
		
		try {
			command.execute();
			ResourceList newResources = FacadeSwitch.getSingleton().getServerModel().getPlayers()[0].getResources();
			assertTrue(newResources.getBrick() == 0);
			assertTrue(newResources.getOre() == 1);
			int newBankBricks = FacadeSwitch.getSingleton().getServerModel().getBank().getBrick();
			assertTrue(newBankBricks == (oldBankBricks + 4));
			
		} catch (ServerResponseException e) {
			e.printStackTrace();
			fail("this should work");
		}

	}
	
}
