package client.communication;

import shared.communication.*;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.utils.IServer;
import client.model.ClientModel;
import client.model.ResourceList;

public class ServerProxy implements IServer {
	
	/**used to send data over the network*/
	private HTTPCommunicator httpCommunicator;
	
	public ServerProxy(HTTPCommunicator httpCommunicator) {
		this.httpCommunicator = httpCommunicator;
	}
	
	
	/**
	 * Prepares credentials to be sent over network, then sends them to server login
	 * 
	 * @pre		username not null
	 * @pre		password not null
	 * @post	a valid LoginResponse returned
	 */
	public LoginResponse Login(UserCredentials credentials) {
		return null;
	}
	/**
	 * Prepares credentials to be sent over network, then sends them to server registration
	 * 
	 * @pre		username not null
	 * @pre		password not null
	 * @post	a valid LoginResponse returned
	 */
	public LoginResponse Register(UserCredentials credentials) {
		LoginResponse loginResponse = new LoginResponse();
		return loginResponse;
	}
	/**
	 * Retrieves a list of the currently existing games
	 * 
	 * @pre		none
	 * @post	A valid CurrentGames returned
	 */
	public GamesList getGameList() {
		GamesList currentGames = new GamesList(null);
		return currentGames;
	}
	/**
	 * Prepares params to be sent over network, then sends them to server to create a game
	 * 
	 * @pre		name not null
	 * @pre		params contains only valid boolean values
	 * @post	a valid GameSummary returned
	 */
	public GameSummary createGame(CreateGameParams params) {
		GameSummary newGame = new GameSummary(null, 0, null);
		return newGame;
	}
	/**
	 * Prepares params to be sent over network, then sends them to server to join a game
	 * 
	 * @pre		user has previously logged into server
	 * @pre		The player is already in the game OR there is room for a new player
	 * @pre		game id is valid
	 * @pre		color is valid (red, green, blue, yellow, puce, brown, white, purple, orange)
	 * @post	a valid boolean returned
	 */
	public boolean joinGame(JoinGameParams params) {
		boolean success = true;
		return success;
	}
	/**
	 * Prepares params to be sent over network, then sends them to server to save a game
	 * 
	 * @pre		game id is valid
	 * @pre		filname is not null or empty
	 * @post	a valid boolean returned
	 */
	public boolean saveGame(SaveParams params) {
		boolean success = true;
		return success;
	}
	/**
	 * Prepares the filename to be sent over network, then sends it to server to load a game
	 * 
	 * @pre		a saved game with the specified filename exists on the server
	 * @post	a valid boolean returned
	 */
	public boolean loadGame(String fileName) {
		boolean success = true;
		return success;
	}
	/**
	 * Prepares the version number to be sent over the network, then retrieves current game from server if it's different than the current version
	 * 
	 * @pre		user has logged on and joined a game, and therefore has cookies
	 * @pre		version is a valid int
	 * @post	a valid ClientModel returned
	 */
	public ClientModel getCurrentGame(int version) {
		ClientModel model = new ClientModel(null, null, null, null, null, null, null, version, version);
		return model;
	}
	/**
	 * Tells the server to reset the game
	 * 
	 * @pre		none
	 * @post	a valid ClientModel returned
	 */
	public ClientModel resetGame() {
		ClientModel model = new ClientModel(null, null, null, null, null, null, null, 0, 0);
		return model;
	}
	/**
	 * Retrieves all the past commands in the current game from the server
	 * 
	 * @pre		none
	 * @post	a valid set of commands returned
	 */
	public String[] getCommands() {
		String[] commands = null;
		return commands;
	}
	/**
	 * Prepares commands to be sent over network, then sends them to server to apply to current game 
	 * 
	 * @pre		user has logged on and joined a game, and therefore has cookies
	 * @post	a valid ClientModel returned
	 */
	public ClientModel setCommands(String[] commands) {
		ClientModel model = new ClientModel(null, null, null, null, null, null, null, 0, 0);
		return model;
	}
	/**
	 * Retrieves a list from the server of the different types of AI players available available
	 * 
	 * @pre		none
	 * @post	a valid list of AI types returned
	 */
	public String[] getAITypes() {
		String[] AITypes = null;
		return AITypes;
	}
	/**
	 * Prepares the AIType to be sent over network, then sends it to server to create a new AI player
	 * 
	 * @pre		user has logged on and joined a game, and therefore has cookies
	 * @pre		there is space in the game for another player
	 * @pre		the AIType is a valid type returned by the getAITypes method
	 * @post	a valid boolean returned
	 */
	public boolean addAI(String AIType) {
		boolean success = true;
		return success;
	}
	/**
	 * Prepares the log level to be sent over network, then sends it to server to change the granularity of the log it keeps
	 * 
	 * @pre		level is a valid LogLevel (SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST)
	 * @post	a valid boolean returned
	 */


	@Override
	public boolean changeLogLevel(LogLevels level) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public ClientModel sendChat(String content) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ClientModel acceptTrade(boolean willAccept) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ClientModel discardCards(ResourceList discardedCards) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ClientModel rollNumber(int number) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ClientModel buildRoad(boolean free, EdgeLocation roadLocation) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ClientModel buildSettlement(boolean free,
			VertexLocation vertexLocation) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ClientModel buildCity(VertexLocation vertexLocation) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ClientModel offerTrade(ResourceList offer, int receiver) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ClientModel maritimeTrade(int ratio, String inputResource,
			String outputResource) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ClientModel robPlayer(HexLocation location, int victimIndex) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ClientModel finishTurn() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ClientModel buyDevCard() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ClientModel playSoldierCard(HexLocation location, int victimIndex) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ClientModel playYearOfPlentyCard(String resource1, String resource2) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ClientModel playRoadBuildingCard(EdgeLocation spot1,
			EdgeLocation spot2) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ClientModel playMonopolyCard(String resource) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ClientModel playMonument() {
		// TODO Auto-generated method stub
		return null;
	}




	

	//----MOVE APIs--------
//	public sendChat(String)
//	public acceptTrade(boolean)
//	public discardCards(ResourceList)
//	public rollNumber(integer)
//	public buildRoad(boolean, EdgeLocation)
//	public buildSettlement(boolean, VertexLocation)
//	public buildCity(VertexLocation)
//	public offerTrade(ResourceHand, playerIndex)
//	public maritimeTrade(integer, Resource, Resource)
//	public robPlayer(HexLocation, playerIndex)
//	public finishTurn()
//	public buyDevCard()
//	public playSoldierCard(HexLocation, playerIndex)
//	public playYearOfPlentyCard(Resource, Resource)
//	public playRoadBuildingCard(EdgeLocation, EdgeLocation)
//	public playMonopolyCard(Resource)
//	public playMonument()
	
}
