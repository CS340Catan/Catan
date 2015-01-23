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
/**
 * @author marcos
 *
 */
/**
 * @author marcos
 *
 */
/**
 * @author marcos
 *
 */
/**
 * @author marcos
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
	 * @Pre Player has 
	 * @param willAccept
	 * @return
	 */
	public ClientModel acceptTrade(boolean willAccept);

	public ClientModel discardCards(ResourceList discardedCards);

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
	
	/**
	 * @Pre none
	 * @Post current player gains a victory point
	 * 
	 */
	public ClientModel playMonument();
}
