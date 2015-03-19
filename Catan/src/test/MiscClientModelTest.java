package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.model.ClientModel;
import client.model.ClientModelFacade;
import shared.locations.HexLocation;
import shared.model.ResourceList;
import shared.utils.Serializer;

/**
 * Test for canAccpetTrade, canOfferTrade, canDiscardCards, and canRobPlayer
 * 
 * @author winstonhurst
 *
 */
public class MiscClientModelTest {
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
	public void testCanAcceptTradeBadResourceList() {
		int currentPlayer = clientModel.getTurnTracker().getCurrentTurn();
		ClientModelFacade clientModelController = new ClientModelFacade();
		ResourceList resourceList = new ResourceList(900, 0, 0, 0, 0);
		boolean result = clientModelController.canAcceptTrade(currentPlayer,
				resourceList);
		assertFalse(result);
	}

	@Test
	public void testCanAcceptTradeGood() {
		int currentPlayer = clientModel.getTurnTracker().getCurrentTurn();
		ClientModelFacade clientModelController = new ClientModelFacade();
		ResourceList resourceList = clientModel.getPlayers()[currentPlayer]
				.getResources();
		boolean result = clientModelController.canAcceptTrade(currentPlayer,
				resourceList);
		assertTrue(result);
	}

	@Test
	public void testCanOfferTradeBadPlayerId() {
		int currentPlayer = clientModel.getTurnTracker().getCurrentTurn();
		int testPlayer = (currentPlayer + 1) % 4;
		ClientModelFacade clientModelController = new ClientModelFacade();
		ResourceList resourceList = clientModel.getPlayers()[currentPlayer]
				.getResources();
		boolean result = clientModelController.canOfferTrade(testPlayer,
				resourceList);
		assertFalse(result);
	}

	@Test
	public void testCanOfferTradeBadResource() {
		int currentPlayer = clientModel.getTurnTracker().getCurrentTurn();
		ClientModelFacade clientModelController = new ClientModelFacade();
		ResourceList resourceList = new ResourceList(900, 0, 0, 0, 0);
		boolean result = clientModelController.canAcceptTrade(currentPlayer,
				resourceList);
		assertFalse(result);
	}

	@Test
	public void testCanOfferTradeBadIdAndResourceList() {
		int currentPlayer = clientModel.getTurnTracker().getCurrentTurn();
		int testPlayer = (currentPlayer + 1) % 4;
		ClientModelFacade clientModelController = new ClientModelFacade();
		ResourceList resourceList = new ResourceList(100, 0, 0, 0, 0);
		boolean result = clientModelController.canOfferTrade(testPlayer,
				resourceList);
		assertFalse(result);
	}

	@Test
	public void testCanOfferTradeBadStatus() {
		int currentPlayer = clientModel.getTurnTracker().getCurrentTurn();
		clientModel.getTurnTracker().setStatus("Rolling");
		ClientModelFacade clientModelController = new ClientModelFacade();
		ResourceList resourceList = clientModel.getPlayers()[currentPlayer]
				.getResources();
		boolean result = clientModelController.canOfferTrade(currentPlayer,
				resourceList);
		assertFalse(result);
	}

	@Test
	public void testCanOfferTradeGood() {
		int currentPlayer = clientModel.getTurnTracker().getCurrentTurn();
		clientModel.getTurnTracker().setStatus("playing");
		ClientModelFacade clientModelController = new ClientModelFacade();
		ResourceList resourceList = clientModel.getPlayers()[currentPlayer]
				.getResources();
		boolean result = clientModelController.canOfferTrade(currentPlayer,
				resourceList);
		assertTrue(result);
	}

	@Test
	public void testCanDiscardCardsBadStatus() {
		clientModel.getTurnTracker().setStatus("rolling");
		int playerIndex = 1;
		clientModel.getPlayers()[playerIndex].getResources().setBrick(8);
		ClientModelFacade clientModelController = new ClientModelFacade();
		boolean result = clientModelController.canDiscardCards(playerIndex);
		assertFalse(result);
	}

	@Test
	public void testCanDiscardCardsTooFewCards() {
		clientModel.getTurnTracker().setStatus("Discarding");
		int playerIndex = 1;
		clientModel.getPlayers()[playerIndex].getResources().setBrick(7);
		ClientModelFacade clientModelController = new ClientModelFacade();
		boolean result = clientModelController.canDiscardCards(playerIndex);
		assertFalse(result);
	}

	@Test
	public void testCanDiscardCardsAlreadyDiscarded() {
		int playerIndex = 1;
		clientModel.getPlayers()[playerIndex].getResources().setBrick(10);
		clientModel.getPlayers()[playerIndex].setDiscarded(true);
		ClientModelFacade clientModelController = new ClientModelFacade();
		boolean result = clientModelController.canDiscardCards(playerIndex);
		assertFalse(result);
	}

	@Test
	public void testCanDsicardCardsGood() {
		int playerIndex = 1;
		clientModel.getTurnTracker().setStatus("Discarding");
		clientModel.getPlayers()[playerIndex].getResources().setBrick(10);
		clientModel.getPlayers()[playerIndex].setDiscarded(false);
		ClientModelFacade clientModelController = new ClientModelFacade();
		boolean result = clientModelController.canDiscardCards(playerIndex);
		assertTrue(result);
	}

	/**
	 * tests if the player can rob a player
	 * 
	 * @Pre it is the current turn of the player attempting to rob
	 * @Pre the player has just rolled a 7 or the player has just played a
	 *      soldier card
	 * @Pre the victim player is adjacent to the hex the robber is on
	 * @Post result: a boolean reporting success/fail
	 * 
	 *       public boolean canRobPlayer(HexLocation robberLocation, int
	 *       robbingPlayer, int robbedPlayer) { if (isPlayerTurn(robbingPlayer)
	 *       && clientModel.getTurnTracker().getStatus().equals("Robbing") &&
	 *       playerTouchingRobber(robbedPlayer, robberLocation)) { return true;
	 *       } return false;
	 * 
	 *       }
	 */
	@Test
	public void canRobPlayerNotRobbersTurn() {
		clientModel.getTurnTracker().setCurrentTurn(3);
		int robberIndex = 1;
		int victimIndex = 0;
		clientModel.getPlayers()[victimIndex].getResources().setSheep(100);
		clientModel.getTurnTracker().setStatus("playing");
		HexLocation newRobberLocal = new HexLocation(0, 1);
		ClientModelFacade clientModelController = new ClientModelFacade();
		boolean result = clientModelController.canRobPlayer(newRobberLocal,
				robberIndex, victimIndex);
		assertFalse(result);

	}

	@Test
	public void canRobPlayerBadStatus() {
		clientModel.getTurnTracker().setCurrentTurn(3);
		int robberIndex = 3;
		int victimIndex = 0;
		clientModel.getPlayers()[victimIndex].getResources().setSheep(100);
		clientModel.getTurnTracker().setStatus("rolling");
		HexLocation newRobberLocal = new HexLocation(0, 1);
		ClientModelFacade clientModelController = new ClientModelFacade();
		boolean result = clientModelController.canRobPlayer(newRobberLocal,
				robberIndex, victimIndex);
		assertFalse(result);
	}

	@Test
	public void canRobPlayerBadLocation() {
		clientModel.getTurnTracker().setCurrentTurn(3);
		int robberIndex = 3;
		int victimIndex = 0;
		clientModel.getPlayers()[victimIndex].getResources().setSheep(100);
		clientModel.getTurnTracker().setStatus("playing");
		HexLocation newRobberLocal = new HexLocation(2, -1);
		ClientModelFacade clientModelController = new ClientModelFacade();
		boolean result = clientModelController.canRobPlayer(newRobberLocal,
				robberIndex, victimIndex);
		assertFalse(result);
	}

	@Test
	public void canRobPlayerSuccess() {
		clientModel.getTurnTracker().setCurrentTurn(3);
		int robberIndex = 3;
		int victimIndex = 0;
		clientModel.getPlayers()[victimIndex].getResources().setSheep(100);
		clientModel.getTurnTracker().setStatus("playing");
		HexLocation newRobberLocal = new HexLocation(1, 0);
		ClientModelFacade clientModelController = new ClientModelFacade();
		boolean result = clientModelController.canRobPlayer(newRobberLocal,
				robberIndex, victimIndex);
		assertTrue(result);
	}

}
