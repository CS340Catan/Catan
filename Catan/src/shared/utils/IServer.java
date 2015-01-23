package shared.utils;

import shared.communication.CreateGameParams;
import shared.communication.GameSummary;
import shared.communication.GamesList;
import shared.communication.JoinGameParams;
import shared.communication.LoginResponse;
import shared.communication.SaveParams;
import shared.communication.UserCredentials;
import shared.communication.LogLevels;
import shared.locations.*;
import client.model.ResourceList;
import client.model.ClientModel;

/**
 * Interface to be implemented by servers, i.e. MockServer, ServerProxy, Server.
 * All methods from API to be implemented with differing functionality
 * 
 * @author Seth White
 *
 */
public interface IServer {
	// ----NON-MOVE APIs--------
	/**
	 * @Pre
	 * @param credentials
	 * @return
	 */
	public LoginResponse Login(UserCredentials credentials);

	public LoginResponse Register(UserCredentials credentials);

	public GamesList getGameList();

	public GameSummary createGame(CreateGameParams params);

	public boolean joinGame(JoinGameParams params);

	public boolean saveGame(SaveParams params);

	public boolean loadGame(String fileName);

	public ClientModel getCurrentGame(int version);

	public ClientModel resetGame();

	public String[] getCommands();

	public ClientModel setCommands(String[] commands);

	public String[] getAITypes();

	public boolean addAI(String AIType);

	public boolean changeLogLevel(LogLevels level);

	public void poll();

	// ----MOVE APIs--------
	/**
	 * @Pre none
	 * @Post chat contains your message at the end
	 * @param content
	 * @return
	 */
	public ClientModel sendChat(String content);

	/**
	 * @Pre Current player has been offered a domestic trade
	 * @Pre To accept the offered trade, current player has the required
	 *      resources
	 * @Post If you accepted, you and the player who offered swap the specified
	 *       resources
	 * @Post If you declined no resources are exchanged
	 * @Post The trade offer is removed
	 * @param willAccept
	 */
	public ClientModel acceptTrade(boolean willAccept);

	/**
	 * @Pre The status of the client model is 'Discarding'
	 * @Pre You have over 7 cards
	 * @Pre You have the cards you're choosing to discard.
	 * @Post Postconditions
	 * @Post You gave up the specified resources
	 * @Post If you're the last one to discard, the client model status changes
	 *       to 'Robbing'
	 * @param discardedCards
	 * @return
	 */
	public ClientModel discardCards(ResourceList discardedCards);

	/**
	 * @Pre It is your turn
	 * @Pre The client model’s status is ‘Rolling’
	 * 
	 * @param number
	 * @return
	 */
	public ClientModel rollNumber(int number);

	public ClientModel buildRoad(boolean free, EdgeLocation roadLocation);

	public ClientModel buildSettlement(boolean free,
			VertexLocation vertexLocation);

	public ClientModel buildCity(VertexLocation vertexLocation);

	public ClientModel offerTrade(ResourceList offer, int receiver);

	public ClientModel maritimeTrade(int ratio, String inputResource,
			String outputResource);

	public ClientModel robPlayer(HexLocation location, int victimIndex);

	public ClientModel finishTurn();

	public ClientModel buyDevCard();

	public ClientModel playSoldierCard(HexLocation location, int victimIndex);

	public ClientModel playYearOfPlentyCard(String resource1, String resource2);

	public ClientModel playRoadBuildingCard(EdgeLocation spot1,
			EdgeLocation spot2);

	public ClientModel playMonopolyCard(String resource);

	public ClientModel playMonument();
}
