package shared.utils;

import shared.communication.CreateGameParams;
import shared.communication.GameSummary;
import shared.communication.GamesList;
import shared.communication.JoinGameParams;
import shared.communication.LoginResponse;
import shared.communication.SaveParams;
import shared.communication.UserCredentials;
import client.communication.LogLevel;
import client.model.ClientModel;

public interface IServer {
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
	public boolean changeLogLevel(LogLevel level);

	//----MOVE APIs--------
	public void sendChat(String content);
	public boolean acceptTrade(boolean willAccept);
	public void discardCards(ResourceHand discardedCards);
	public void rollNumber(int number);
	public void buildRoad(boolean free, EdgeLocation roadLocation);
	public void buildSettlement(boolean free, VertexLocation vertexLocation);
	public void buildCity(VertexLocation vertexLocation);
	public void offerTrade(ResourceHand, playerIndex);
	public void maritimeTrade(integer, Resource, Resource);
	public void robPlayer(HexLocation, playerIndex);
	public void finishTurn();
	public void buyDevCard();
	public void playSoldierCard(HexLocation, playerIndex);
	public void playYearOfPlentyCard(Resource, Resource);
	public void playRoadBuildingCard(EdgeLocation, EdgeLocation);
	public void playMonopolyCard(Resource);
	public void playMonument();
}
