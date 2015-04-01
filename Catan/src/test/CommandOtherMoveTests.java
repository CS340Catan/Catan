package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import server.commands.DiscardCardsCommand;
import server.commands.FinishTurnCommand;
import server.commands.ICommand;
import server.commands.RobPlayerCommand;
import server.commands.RollNumberCommand;
import server.facade.FacadeSwitch;
import server.model.GameList;
import server.model.ServerModel;
import shared.communication.DiscardCardsParams;
import shared.communication.GameSummary;
import shared.communication.MoveRobberParams;
import shared.communication.PlayerSummary;
import shared.communication.RollParams;
import shared.communication.UserActionParams;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import shared.model.Hex;
import shared.model.Player;
import shared.model.ResourceList;
import shared.model.VertexObject;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

public class CommandOtherMoveTests {
	private static String clientModelJson = "{\"deck\":{\"yearOfPlenty\":2,\"monopoly\":2,\"soldier\":14,\"roadBuilding\":2,\"monument\":5},\"map\":{\"hexes\":[{\"location\":{\"x\":0,\"y\":-2}},{\"resource\":\"ore\",\"location\":{\"x\":1,\"y\":-2},\"number\":3},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-2},\"number\":3},{\"resource\":\"wheat\",\"location\":{\"x\":-1,\"y\":-1},\"number\":8},{\"resource\":\"brick\",\"location\":{\"x\":0,\"y\":-1},\"number\":8},{\"resource\":\"sheep\",\"location\":{\"x\":1,\"y\":-1},\"number\":9},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-1},\"number\":11},{\"resource\":\"sheep\",\"location\":{\"x\":-2,\"y\":0},\"number\":10},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":0},\"number\":12},{\"resource\":\"sheep\",\"location\":{\"x\":0,\"y\":0},\"number\":10},{\"resource\":\"wheat\",\"location\":{\"x\":1,\"y\":0},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":2,\"y\":0},\"number\":5},{\"resource\":\"wood\",\"location\":{\"x\":-2,\"y\":1},\"number\":6},{\"resource\":\"brick\",\"location\":{\"x\":-1,\"y\":1},\"number\":4},{\"resource\":\"ore\",\"location\":{\"x\":0,\"y\":1},\"number\":5},{\"resource\":\"wood\",\"location\":{\"x\":1,\"y\":1},\"number\":4},{\"resource\":\"ore\",\"location\":{\"x\":-2,\"y\":2},\"number\":9},{\"resource\":\"wheat\",\"location\":{\"x\":-1,\"y\":2},\"number\":6},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":2},\"number\":2}],\"roads\":[{\"owner\":3,\"location\":{\"direction\":\"S\",\"x\":1,\"y\":0}},{\"owner\":3,\"location\":{\"direction\":\"SE\",\"x\":0,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"cities\":[],\"settlements\":[{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":1,\"y\":0}}],\"radius\":3,\"ports\":[{\"ratio\":3,\"direction\":\"S\",\"location\":{\"x\":1,\"y\":-3}},{\"ratio\":3,\"direction\":\"NE\",\"location\":{\"x\":-2,\"y\":3}},{\"ratio\":2,\"resource\":\"sheep\",\"direction\":\"SW\",\"location\":{\"x\":3,\"y\":-3}},{\"ratio\":3,\"direction\":\"NW\",\"location\":{\"x\":2,\"y\":1}},{\"ratio\":3,\"direction\":\"SE\",\"location\":{\"x\":-3,\"y\":0}},{\"ratio\":2,\"resource\":\"wood\",\"direction\":\"S\",\"location\":{\"x\":-1,\"y\":-2}},{\"ratio\":2,\"resource\":\"ore\",\"direction\":\"N\",\"location\":{\"x\":0,\"y\":3}},{\"ratio\":2,\"resource\":\"wheat\",\"direction\":\"NW\",\"location\":{\"x\":3,\"y\":-1}},{\"ratio\":2,\"resource\":\"brick\",\"direction\":\"NE\",\"location\":{\"x\":-3,\"y\":2}}],\"robber\":{\"x\":0,\"y\":-2}},\"players\":[{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":12,\"playerIndex\":0,\"name\":\"string\",\"color\":\"purple\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":13,\"playerIndex\":1,\"name\":\"test1\",\"color\":\"puce\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":14,\"playerIndex\":2,\"name\":\"test2\",\"color\":\"blue\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":12,\"cities\":4,\"settlements\":4,\"soldiers\":0,\"victoryPoints\":1,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":15,\"playerIndex\":3,\"name\":\"test3\",\"color\":\"orange\"}],\"log\":{\"lines\":[{\"source\":\"string\",\"message\":\"string built a settlement\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"}]},\"chat\":{\"lines\":[]},\"bank\":{\"brick\":24,\"wood\":24,\"sheep\":24,\"wheat\":24,\"ore\":24},\"turnTracker\":{\"status\":\"FirstRound\",\"currentTurn\":0,\"longestRoad\":-1,\"largestArmy\":-1},\"winner\":-1,\"version\":4}";	
	ICommand command;
	@Before
	public void setUp() throws Exception {
		ServerModel model = (ServerModel) Serializer.deserialize(clientModelJson, ServerModel.class);
		FacadeSwitch.setMockServer(model);
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void rollNumberTest(){
		System.out.println("Testing Roll Number 1");
		int playerID = 2;
		RollParams params = new RollParams(playerID, 6);
		Player player = FacadeSwitch.getSingleton().getServerModel().getPlayers()[playerID];
		player.setResources(new ResourceList(0,0,0,0,0));
		//should fail and nothing should happen
		command = new RollNumberCommand(params);
		try {
			command.execute();
			fail("Can do should have failed");
		} catch (ServerResponseException e) {
			assertTrue(player.getResources().totalResourceCount() == 0);
		}
		FacadeSwitch.getSingleton().getServerModel().getTurnTracker().setStatus("Rolling");
		FacadeSwitch.getSingleton().getServerModel().getTurnTracker().setCurrentTurn(playerID);
		System.out.println("Testing Roll Number 2");
		try {//tests player not by the number
			command.execute();
			assertTrue(player.getResources().totalResourceCount() == 0);
		} catch (ServerResponseException e) {
			e.printStackTrace();
			fail("This should work");
		}
		
		for(Hex hex : FacadeSwitch.getSingleton().getServerModel().getMap().getHexes()){
			if(hex.getNumber() == 6){//build a new settlement by a hex with a 6
				VertexObject[] settlements = new VertexObject[1];
				settlements[0] = new VertexObject(playerID, new VertexLocation(hex.getLocation(),VertexDirection.East));
				FacadeSwitch.getSingleton().getServerModel().getMap().setSettlements(settlements);
			}
		}
		FacadeSwitch.getSingleton().getServerModel().getTurnTracker().setStatus("Rolling");
		System.out.println("Testing Roll Number 3");
		try {
			command.execute();
			assertTrue(player.getResources().totalResourceCount() > 0);	//should get some resource		
		} catch (ServerResponseException e) {
			e.printStackTrace();
			fail("This should work");
		}
	}
	@Test
	public void robPlayerTest(){
		System.out.println("Testing Rob Player 1");
		int playerID = 2;
		VertexObject[] settlements = new VertexObject[1];
		settlements[0] = new VertexObject(3, new VertexLocation());
		FacadeSwitch.getSingleton().getServerModel().getMap().setSettlements(settlements);
		FacadeSwitch.getSingleton().getServerModel().getPlayers()[3].setResources(new ResourceList(1, 1, 1, 1, 1));
		MoveRobberParams params = new MoveRobberParams(playerID, 3, new HexLocation(0,0));
		Player player = FacadeSwitch.getSingleton().getServerModel().getPlayers()[playerID];
		//should fail and nothing should happen
		command = new RobPlayerCommand(params);
		try {
			command.execute();
			fail("Can do should have failed");
		} catch (ServerResponseException e) {
			assertEquals(player.getResources().totalResourceCount(),0);
		}
		FacadeSwitch.getSingleton().getServerModel().getTurnTracker().setStatus("Robbing");
		FacadeSwitch.getSingleton().getServerModel().getTurnTracker().setCurrentTurn(playerID);
		FacadeSwitch.getSingleton().getServerModel().getPlayers()[3].setResources(new ResourceList(1, 1, 1, 1, 1));
		System.out.println("Testing Rob Player 2");
		try {
			command.execute();
			Player victim = FacadeSwitch.getSingleton().getServerModel().getPlayers()[3];
			assertEquals(player.getResources().totalResourceCount(),1);
			assertEquals(victim.getResources().totalResourceCount(),4);
		} catch (ServerResponseException e) {
			e.printStackTrace();
			fail("This should work");
		}
	}
	@Test
	public void finishTurnTest(){
		System.out.println("Testing Finish Turn 1");
		int playerID = 2;
		UserActionParams params = new UserActionParams(playerID);
		params.setType("FinishTurn");
		FacadeSwitch.getSingleton().setGameID(0);
		PlayerSummary[] playerSummaries = new PlayerSummary[4];
		playerSummaries[0] = new PlayerSummary("puce","a",0);
		playerSummaries[1] = new PlayerSummary("blue","b",1);
		playerSummaries[2] = new PlayerSummary("white","c",2);
		playerSummaries[2] = new PlayerSummary("red","d",3);
		
		GameList.getSingleton().addGame(new GameSummary("test", 0, playerSummaries));
		FacadeSwitch.getSingleton().getServerModel().getTurnTracker().setCurrentTurn(0);
		//should fail and nothing should happen
		command = new FinishTurnCommand(params, 0);
		try {
			command.execute();
			fail("Can do should have failed");
		} catch (ServerResponseException e) {
			assertTrue(FacadeSwitch.getSingleton().getServerModel().getTurnTracker().getCurrentTurn() == 0);
		}
		
		FacadeSwitch.getSingleton().getServerModel().getTurnTracker().setStatus("Playing");
		FacadeSwitch.getSingleton().getServerModel().getTurnTracker().setCurrentTurn(playerID);
		System.out.println("Testing Finish Turn 2");
		try {
			command.execute();
			assertTrue(FacadeSwitch.getSingleton().getServerModel().getTurnTracker().getCurrentTurn() == 3);
		} catch (ServerResponseException e) {
			e.printStackTrace();
			fail("This should work");
		}
	}
	
	@Test
	public void discardCardsTest(){
		System.out.println("Testing Discard Cards 1");
		ServerModel model = FacadeSwitch.getSingleton().getServerModel();
		int playerID = 1;
		ResourceList resourcesToDiscard = new ResourceList(0,0,2,2,3);
		DiscardCardsParams params = new DiscardCardsParams(playerID,resourcesToDiscard);
		Player player = model.getPlayers()[playerID];
		
		//Test when can do should fail. Nothing should change
		player.setResources(new ResourceList(1,1,1,1,1));
		command = new DiscardCardsCommand(params);
		try {
			command.execute();
			fail("Should fail");
		} catch (ServerResponseException e) {
			assertEquals(1,player.getResources().getBrick());
			assertEquals(1,player.getResources().getWood());
			assertEquals(1,player.getResources().getSheep());
			assertEquals(1,player.getResources().getWheat());
			assertEquals(1,player.getResources().getOre());
			e.printStackTrace();
		}
		
		//Test when it should work and game to new status
		model.getTurnTracker().setStatus("Discarding");
		model.getBank().setWood(0);
		model.getBank().setOre(0);
		model.getBank().setSheep(0);
		model.getBank().setBrick(0);
		model.getBank().setWheat(0);
		player.setResources(new ResourceList(1,4,2,3,4));
		System.out.println("Testing Discard Cards 2");
		try {
			command.execute();
			assertEquals(1,player.getResources().getBrick());
			assertEquals(4,player.getResources().getOre());
			assertEquals(0,player.getResources().getSheep());
			assertEquals(1,player.getResources().getWheat());
			assertEquals(1,player.getResources().getWood());
			
			assertTrue(model.getBank().contains(resourcesToDiscard));
			assertTrue(model.getTurnTracker().getStatus().equals("Robbing"));
			
			
		} catch (ServerResponseException e) {
			e.printStackTrace();
			fail("Should be able to dsicard");
		}
		
		//test when should work but others still need to discard
		model.getTurnTracker().setStatus("Discarding");
		player.setDiscarded(false);
		model.getPlayers()[0].setResources(new ResourceList(2,2,2,2,2));
		model.getBank().setWood(0);
		model.getBank().setOre(0);
		model.getBank().setSheep(0);
		model.getBank().setBrick(0);
		model.getBank().setWheat(0);
		player.setResources(new ResourceList(1,4,2,3,4));
		System.out.println("Testing Discard Cards 3");
		try {
			command.execute();
			assertEquals(1,player.getResources().getBrick());
			assertEquals(4,player.getResources().getOre());
			assertEquals(0,player.getResources().getSheep());
			assertEquals(1,player.getResources().getWheat());
			assertEquals(1,player.getResources().getWood());
			
			assertTrue(model.getBank().contains(resourcesToDiscard));
			assertTrue(model.getTurnTracker().getStatus().equals("Discarding"));
			
			
		} catch (ServerResponseException e) {
			e.printStackTrace();
			fail("Should be able to dsicard");
		}
		
		
	}
}
