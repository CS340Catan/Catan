package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.locations.HexLocation;
import shared.utils.Serializer;
import client.model.ClientModel;
import client.model.ClientModelController;
import client.model.DevCardList;
import client.model.ResourceList;

public class CanPlayCardTest {
	private String clientModelJson = "{\"deck\":{\"yearOfPlenty\":2,\"monopoly\":2,\"soldier\":14,\"roadBuilding\":2,\"monument\":5},\"map\":{\"hexes\":[{\"location\":{\"x\":0,\"y\":-2}},{\"resource\":\"ore\",\"location\":{\"x\":1,\"y\":-2},\"number\":3},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-2},\"number\":3},{\"resource\":\"wheat\",\"location\":{\"x\":-1,\"y\":-1},\"number\":8},{\"resource\":\"brick\",\"location\":{\"x\":0,\"y\":-1},\"number\":8},{\"resource\":\"sheep\",\"location\":{\"x\":1,\"y\":-1},\"number\":9},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-1},\"number\":11},{\"resource\":\"sheep\",\"location\":{\"x\":-2,\"y\":0},\"number\":10},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":0},\"number\":12},{\"resource\":\"sheep\",\"location\":{\"x\":0,\"y\":0},\"number\":10},{\"resource\":\"wheat\",\"location\":{\"x\":1,\"y\":0},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":2,\"y\":0},\"number\":5},{\"resource\":\"wood\",\"location\":{\"x\":-2,\"y\":1},\"number\":6},{\"resource\":\"brick\",\"location\":{\"x\":-1,\"y\":1},\"number\":4},{\"resource\":\"ore\",\"location\":{\"x\":0,\"y\":1},\"number\":5},{\"resource\":\"wood\",\"location\":{\"x\":1,\"y\":1},\"number\":4},{\"resource\":\"ore\",\"location\":{\"x\":-2,\"y\":2},\"number\":9},{\"resource\":\"wheat\",\"location\":{\"x\":-1,\"y\":2},\"number\":6},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":2},\"number\":2}],\"roads\":[{\"owner\":0,\"location\":{\"direction\":\"S\",\"x\":1,\"y\":0}},{\"owner\":0,\"location\":{\"direction\":\"SE\",\"x\":0,\"y\":1}},{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"cities\":[],\"settlements\":[{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":1,\"y\":0}},{\"owner\":0,\"location\":{\"direction\":\"NW\",\"x\":1,\"y\":0}}],\"radius\":3,\"ports\":[{\"ratio\":3,\"direction\":\"S\",\"location\":{\"x\":1,\"y\":-3}},{\"ratio\":3,\"direction\":\"NE\",\"location\":{\"x\":-2,\"y\":3}},{\"ratio\":2,\"resource\":\"sheep\",\"direction\":\"SW\",\"location\":{\"x\":3,\"y\":-3}},{\"ratio\":3,\"direction\":\"NW\",\"location\":{\"x\":2,\"y\":1}},{\"ratio\":3,\"direction\":\"SE\",\"location\":{\"x\":-3,\"y\":0}},{\"ratio\":2,\"resource\":\"wood\",\"direction\":\"S\",\"location\":{\"x\":-1,\"y\":-2}},{\"ratio\":2,\"resource\":\"ore\",\"direction\":\"N\",\"location\":{\"x\":0,\"y\":3}},{\"ratio\":2,\"resource\":\"wheat\",\"direction\":\"NW\",\"location\":{\"x\":3,\"y\":-1}},{\"ratio\":2,\"resource\":\"brick\",\"direction\":\"NE\",\"location\":{\"x\":-3,\"y\":2}}],\"robber\":{\"x\":0,\"y\":-2}},\"players\":[{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":12,\"playerIndex\":0,\"name\":\"string\",\"color\":\"purple\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":13,\"playerIndex\":1,\"name\":\"test1\",\"color\":\"puce\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":15,\"cities\":4,\"settlements\":5,\"soldiers\":0,\"victoryPoints\":0,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":14,\"playerIndex\":2,\"name\":\"test2\",\"color\":\"blue\"},{\"resources\":{\"brick\":0,\"wood\":0,\"sheep\":0,\"wheat\":0,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":12,\"cities\":4,\"settlements\":4,\"soldiers\":0,\"victoryPoints\":1,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":15,\"playerIndex\":3,\"name\":\"test3\",\"color\":\"orange\"}],\"log\":{\"lines\":[{\"source\":\"string\",\"message\":\"string built a settlement\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"},{\"source\":\"test3\",\"message\":\"test3 built a road\"}]},\"chat\":{\"lines\":[]},\"bank\":{\"brick\":24,\"wood\":24,\"sheep\":24,\"wheat\":24,\"ore\":24},\"turnTracker\":{\"status\":\"FirstRound\",\"currentTurn\":0,\"longestRoad\":-1,\"largestArmy\":-1},\"winner\":-1,\"version\":4}";
	private ClientModel clientModel = null;

	@Before
	public void setUp() throws Exception {
		clientModel = Serializer.deserializeClientModel(clientModelJson);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void canPlayMonumentFail() { //no monument card
		clientModel.getPlayers()[0].setVictoryPoints(9);
		DevCardList cardList = new DevCardList(0,0,0,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean fail = clientModelController.canPlayMonumentCard(0);
		assertFalse(fail);
	}
	
	@Test
	public void canPlayMonumentFailTwo() { //won't give them 10 points
		clientModel.getPlayers()[0].setVictoryPoints(8);
		DevCardList cardList = new DevCardList(0,1,0,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean fail = clientModelController.canPlayMonumentCard(0);
		assertFalse(fail);
	}
	
	@Test
	public void canPlayMonumentFailThree() {//not their turn
		clientModel.getPlayers()[0].setVictoryPoints(8);
		DevCardList cardList = new DevCardList(0,2,0,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(1);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean fail = clientModelController.canPlayMonumentCard(0);
		assertFalse(fail);
	}
	
	@Test
	public void canPlayMonumentFailFour() {//status isn't "Playing"
		clientModel.getPlayers()[0].setVictoryPoints(8);
		DevCardList cardList = new DevCardList(0,2,0,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("rolling");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean fail = clientModelController.canPlayMonumentCard(0);
		assertFalse(fail);
	}
	
	@Test
	public void canPlayMonumentFailFive() {//have already played dev card
		clientModel.getPlayers()[0].setVictoryPoints(8);
		DevCardList cardList = new DevCardList(0,2,0,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(true);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean fail = clientModelController.canPlayMonumentCard(0);
		assertFalse(fail);
	}
	
	@Test
	public void canPlayMonumentPass() { //if they have one card
		clientModel.getPlayers()[0].setVictoryPoints(9);
		DevCardList cardList = new DevCardList(0,1,0,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean pass = clientModelController.canPlayMonumentCard(0);
		assertTrue(pass);
	}
	
	@Test
	public void canPlayMonumentPassTwo() { //if they have multiple cards
		clientModel.getPlayers()[0].setVictoryPoints(8);
		DevCardList cardList = new DevCardList(0,2,0,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean pass = clientModelController.canPlayMonumentCard(0);
		assertTrue(pass);
	}
	
	@Test
	public void canPlayMonumentPassThree() { //if it will give them >10 points
		clientModel.getPlayers()[0].setVictoryPoints(8);
		DevCardList cardList = new DevCardList(0,3,0,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean pass = clientModelController.canPlayMonumentCard(0);
		assertTrue(pass);
	}
	
	@Test
	public void canPlayMonopolyPass() { //has 1 card
		DevCardList cardList = new DevCardList(1,0,0,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean pass = clientModelController.canPlayMonopolyCard(0);
		assertTrue(pass);
	}
	
	@Test
	public void canPlayMonopolyPassTwo() { //has 2 cards
		DevCardList cardList = new DevCardList(2,0,0,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean pass = clientModelController.canPlayMonopolyCard(0);
		assertTrue(pass);
	}
	
	@Test
	public void canPlayMonopolyFail() { //not turn
		DevCardList cardList = new DevCardList(1,0,0,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(1);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean fail = clientModelController.canPlayMonopolyCard(0);
		assertFalse(fail);
	}
	
	@Test
	public void canPlayMonopolyFailTwo() { //doesn't have card
		DevCardList cardList = new DevCardList(0,0,0,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean fail = clientModelController.canPlayMonopolyCard(0);
		assertFalse(fail);
	}
	
	@Test
	public void canPlayMonopolyFailThree() { //not "Playing"
		DevCardList cardList = new DevCardList(1,0,0,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("rolling");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean fail = clientModelController.canPlayMonopolyCard(0);
		assertFalse(fail);
	}
	
	@Test
	public void canPlayMonopolyFailFour() { //has already played devcard
		DevCardList cardList = new DevCardList(1,0,0,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(true);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean fail = clientModelController.canPlayMonopolyCard(0);
		assertFalse(fail);
	}
	
	@Test
	public void canPlayRoadBuildingCardPass() { //has 1 card, 3 roads
		DevCardList cardList = new DevCardList(0,0,1,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		clientModel.getPlayers()[0].setRoads(3);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean pass = clientModelController.canPlayRoadBuildingCard(0);
		assertTrue(pass);
	}
	
	@Test
	public void canPlayRoadBuildingCardPassTwo() { //has 2 cards, 2 roads
		DevCardList cardList = new DevCardList(0,0,2,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		clientModel.getPlayers()[0].setRoads(2);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean pass = clientModelController.canPlayRoadBuildingCard(0);
		assertTrue(pass);
	}
	
	@Test
	public void canPlayRoadBuildingCardFail() { //0 cards
		DevCardList cardList = new DevCardList(0,0,0,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		clientModel.getPlayers()[0].setRoads(2);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean pass = clientModelController.canPlayRoadBuildingCard(0);
		assertFalse(pass);
	}
	
	@Test
	public void canPlayRoadBuildingCardFailTwo() { //1 road
		DevCardList cardList = new DevCardList(0,0,1,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		clientModel.getPlayers()[0].setRoads(1);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean pass = clientModelController.canPlayRoadBuildingCard(0);
		assertFalse(pass);
	}
	
	@Test
	public void canPlayRoadBuildingCardFailThree() { //not turn
		DevCardList cardList = new DevCardList(0,0,1,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(1);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		clientModel.getPlayers()[0].setRoads(3);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean pass = clientModelController.canPlayRoadBuildingCard(0);
		assertFalse(pass);
	}
	
	@Test
	public void canPlayRoadBuildingCardFailFour() { //not "Playing"
		DevCardList cardList = new DevCardList(0,0,1,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("rolling");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		clientModel.getPlayers()[0].setRoads(3);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean pass = clientModelController.canPlayRoadBuildingCard(0);
		assertFalse(pass);
	}
	
	@Test
	public void canPlayRoadBuildingCardFailFive() { //has played dev card
		DevCardList cardList = new DevCardList(0,0,1,0,0);
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(true);
		clientModel.getPlayers()[0].setRoads(3);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean pass = clientModelController.canPlayRoadBuildingCard(0);
		assertFalse(pass);
	}
	
	@Test
	public void canPlayYearOfPlentyPass() { //has 1 card, bank has 1 of each
		DevCardList cardList = new DevCardList(0,0,0,0,1);
		ResourceList wantedResources = new ResourceList(0,1,1,0,0);
		clientModel.setBank(new ResourceList(0,1,1,0,0));
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean pass = clientModelController.canPlayYearOfPlentyCard(0,wantedResources);
		assertTrue(pass);
	}
	
	@Test
	public void canPlayYearOfPlentyPassTwo() { //has 1 card, bank has 2 of each
		DevCardList cardList = new DevCardList(0,0,0,0,1);
		ResourceList wantedResources = new ResourceList(0,2,0,0,0);
		clientModel.setBank(new ResourceList(0,2,0,0,0));
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean pass = clientModelController.canPlayYearOfPlentyCard(0,wantedResources);
		assertTrue(pass);
	}
	
	@Test
	public void canPlayYearOfPlentyFail() { //doesn't have year of plenty card
		DevCardList cardList = new DevCardList(0,0,0,0,0);
		ResourceList wantedResources = new ResourceList(0,2,0,0,0);
		clientModel.setBank(new ResourceList(0,2,0,0,0));
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean pass = clientModelController.canPlayYearOfPlentyCard(0,wantedResources);
		assertFalse(pass);
	}
	
	@Test
	public void canPlayYearOfPlentyFailTwo() { //bank doesn't have 1 of the cards
		DevCardList cardList = new DevCardList(0,0,0,0,1);
		ResourceList wantedResources = new ResourceList(0,2,0,0,0);
		clientModel.setBank(new ResourceList(0,1,0,0,0));
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean pass = clientModelController.canPlayYearOfPlentyCard(0,wantedResources);
		assertFalse(pass);
	}
	
	@Test
	public void canPlayYearOfPlentyFailThree() { //not players turn
		DevCardList cardList = new DevCardList(0,0,0,0,1);
		ResourceList wantedResources = new ResourceList(0,2,0,0,0);
		clientModel.setBank(new ResourceList(0,2,0,0,0));
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(1);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean pass = clientModelController.canPlayYearOfPlentyCard(0,wantedResources);
		assertFalse(pass);
	}
	
	@Test
	public void canPlayYearOfPlentyFailFour() { //status not playing
		DevCardList cardList = new DevCardList(0,0,0,0,1);
		ResourceList wantedResources = new ResourceList(0,2,0,0,0);
		clientModel.setBank(new ResourceList(0,2,0,0,0));
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("rolling");
		clientModel.getPlayers()[0].setPlayedDevCard(false);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean pass = clientModelController.canPlayYearOfPlentyCard(0,wantedResources);
		assertFalse(pass);
	}
	
	@Test
	public void canPlayYearOfPlentyFailFive() { //already played dev card
		DevCardList cardList = new DevCardList(0,0,0,0,1);
		ResourceList wantedResources = new ResourceList(0,2,0,0,0);
		clientModel.setBank(new ResourceList(0,2,0,0,0));
		clientModel.getPlayers()[0].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(0);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[0].setPlayedDevCard(true);
		ClientModelController clientModelController = new ClientModelController(clientModel);
		boolean pass = clientModelController.canPlayYearOfPlentyCard(0,wantedResources);
		assertFalse(pass);
	}
	
	@Test
	public void canPlaySoldierCardPass() { //has 1 card
		DevCardList cardList = new DevCardList(0,0,0,1,0);
		clientModel.getPlayers()[1].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(1);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[1].setPlayedDevCard(false);
		clientModel.getPlayers()[0].setResources(new ResourceList(1,1,1,1,1));
		ClientModelController clientModelController = new ClientModelController(clientModel);
		HexLocation currentHexLocation = new HexLocation(2,-1);
		clientModel.getMap().setRobber(currentHexLocation);
		HexLocation futureHexLocation = new HexLocation(0,1);
		boolean pass = clientModelController.canPlaySoldierCard(futureHexLocation,1,0);
		assertTrue(pass);
	}
	
	@Test
	public void canPlaySoldierCardPassTwo() { //has 2 card
		DevCardList cardList = new DevCardList(0,0,0,2,0);
		clientModel.getPlayers()[1].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(1);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[1].setPlayedDevCard(false);
		clientModel.getPlayers()[0].setResources(new ResourceList(1,1,1,1,1));
		ClientModelController clientModelController = new ClientModelController(clientModel);
		HexLocation currentHexLocation = new HexLocation(2,-1);
		clientModel.getMap().setRobber(currentHexLocation);
		HexLocation futureHexLocation = new HexLocation(0,1);
		boolean pass = clientModelController.canPlaySoldierCard(futureHexLocation,1,0);
		assertTrue(pass);
	}
	
	@Test
	public void canPlaySoldierCardFail() { //doesn't have card
		DevCardList cardList = new DevCardList(0,0,0,0,0);
		clientModel.getPlayers()[1].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(1);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[1].setPlayedDevCard(false);
		clientModel.getPlayers()[0].setResources(new ResourceList(1,1,1,1,1));
		ClientModelController clientModelController = new ClientModelController(clientModel);
		HexLocation currentHexLocation = new HexLocation(2,-1);
		clientModel.getMap().setRobber(currentHexLocation);
		HexLocation futureHexLocation = new HexLocation(0,1);
		boolean pass = clientModelController.canPlaySoldierCard(futureHexLocation,1,0);
		assertFalse(pass);
	}
	
	@Test
	public void canPlaySoldierCardFailTwo() { //not their turn
		DevCardList cardList = new DevCardList(0,0,0,1,0);
		clientModel.getPlayers()[1].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(2);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[1].setPlayedDevCard(false);
		clientModel.getPlayers()[0].setResources(new ResourceList(1,1,1,1,1));
		ClientModelController clientModelController = new ClientModelController(clientModel);
		HexLocation currentHexLocation = new HexLocation(2,-1);
		clientModel.getMap().setRobber(currentHexLocation);
		HexLocation futureHexLocation = new HexLocation(0,1);
		boolean pass = clientModelController.canPlaySoldierCard(futureHexLocation,1,0);
		assertFalse(pass);
	}
	
	@Test
	public void canPlaySoldierCardFailThree() { //not "Playing"
		DevCardList cardList = new DevCardList(0,0,0,1,0);
		clientModel.getPlayers()[1].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(1);
		clientModel.getTurnTracker().setStatus("rolling");
		clientModel.getPlayers()[1].setPlayedDevCard(false);
		clientModel.getPlayers()[0].setResources(new ResourceList(1,1,1,1,1));
		ClientModelController clientModelController = new ClientModelController(clientModel);
		HexLocation currentHexLocation = new HexLocation(2,-1);
		clientModel.getMap().setRobber(currentHexLocation);
		HexLocation futureHexLocation = new HexLocation(0,1);
		boolean pass = clientModelController.canPlaySoldierCard(futureHexLocation,1,0);
		assertFalse(pass);
	}
	
	@Test
	public void canPlaySoldierCardFailFour() { //already played dev card
		DevCardList cardList = new DevCardList(0,0,0,1,0);
		clientModel.getPlayers()[1].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(1);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[1].setPlayedDevCard(true);
		clientModel.getPlayers()[0].setResources(new ResourceList(1,1,1,1,1));
		ClientModelController clientModelController = new ClientModelController(clientModel);
		HexLocation currentHexLocation = new HexLocation(2,-2);
		clientModel.getMap().setRobber(currentHexLocation);
		HexLocation futureHexLocation = new HexLocation(0,1);
		boolean pass = clientModelController.canPlaySoldierCard(futureHexLocation,1,0);
		assertFalse(pass);
	}
	
	@Test
	public void canPlaySoldierCardFailFive() { //robber not moving
		DevCardList cardList = new DevCardList(0,0,0,1,0);
		clientModel.getPlayers()[1].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(1);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[1].setPlayedDevCard(false);
		clientModel.getPlayers()[0].setResources(new ResourceList(1,1,1,1,1));
		ClientModelController clientModelController = new ClientModelController(clientModel);
		HexLocation currentHexLocation = new HexLocation(2,-1);
		clientModel.getMap().setRobber(currentHexLocation);
		HexLocation futureHexLocation = new HexLocation(2,-1);
		boolean pass = clientModelController.canPlaySoldierCard(futureHexLocation,1,0);
		assertFalse(pass);
	}
	
	@Test
	public void canPlaySoldierCardFailSix() { //robbed player is not touching new robber location
		DevCardList cardList = new DevCardList(0,0,0,1,0);
		clientModel.getPlayers()[1].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(1);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[1].setPlayedDevCard(false);
		clientModel.getPlayers()[0].setResources(new ResourceList(1,1,1,1,1));
		ClientModelController clientModelController = new ClientModelController(clientModel);
		HexLocation currentHexLocation = new HexLocation(2,-1);
		clientModel.getMap().setRobber(currentHexLocation);
		HexLocation futureHexLocation = new HexLocation(2,0);
		boolean pass = clientModelController.canPlaySoldierCard(futureHexLocation,1,0);
		assertFalse(pass);
	}
	
	@Test
	public void canPlaySoldierCardFailSeven() { //robbed player does not have cards
		DevCardList cardList = new DevCardList(0,0,0,1,0);
		clientModel.getPlayers()[1].setOldDevCards(cardList);
		clientModel.getTurnTracker().setCurrentTurn(1);
		clientModel.getTurnTracker().setStatus("playing");
		clientModel.getPlayers()[1].setPlayedDevCard(false);
		clientModel.getPlayers()[0].setResources(new ResourceList(0,0,0,0,0));
		ClientModelController clientModelController = new ClientModelController(clientModel);
		HexLocation currentHexLocation = new HexLocation(2,-1);
		clientModel.getMap().setRobber(currentHexLocation);
		HexLocation futureHexLocation = new HexLocation(2,0);
		boolean pass = clientModelController.canPlaySoldierCard(futureHexLocation,1,0);
		assertFalse(pass);
	}

}
