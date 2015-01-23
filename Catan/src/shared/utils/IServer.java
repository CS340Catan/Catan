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

public interface IServer {
	//----NON-MOVE APIs--------
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

	//----MOVE APIs--------
	public ClientModel sendChat(String content);
	public ClientModel acceptTrade(boolean willAccept);
	public ClientModel discardCards(ResourceList discardedCards);
	public ClientModel rollNumber(int number);
	public ClientModel buildRoad(boolean free, EdgeLocation roadLocation);
	public ClientModel buildSettlement(boolean free, VertexLocation vertexLocation);
	public ClientModel buildCity(VertexLocation vertexLocation);
	public ClientModel offerTrade(ResourceList offer, int receiver);
	public ClientModel maritimeTrade(int ratio, String inputResource, String outputResource);
	public ClientModel robPlayer(HexLocation location, int victimIndex);
	public ClientModel finishTurn();
	public ClientModel buyDevCard();
	public ClientModel playSoldierCard(HexLocation location, int victimIndex);
	public ClientModel playYearOfPlentyCard(String resource1, String resource2);
	public ClientModel playRoadBuildingCard(EdgeLocation spot1, EdgeLocation spot2);
	public ClientModel playMonopolyCard(String resource);
	public ClientModel playMonument();
}
