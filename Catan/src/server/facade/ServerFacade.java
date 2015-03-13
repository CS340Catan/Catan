package server.facade;

import client.data.GameInfo;
import shared.communication.*;
import shared.model.ClientModel;
import shared.utils.IServer;
import shared.utils.ServerResponseException;

public class ServerFacade implements IServer{

	/**
	 * Verifies the user, and adds a cookie for them on the server
	 * 
	 * @param credentials - name and password of user logging in
	 * @return boolean specifying whether the login credentials were valid or not
	 */
	@Override
	public boolean Login(UserCredentials credentials)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Creates a new user account, then logs them in and sets their cookie
	 * 
	 * @param credentials - name and password of new user
	 * @return boolean specifying whether the login credentials were valid or not
	 */
	@Override
	public boolean Register(UserCredentials credentials)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Retrieves a list of current games and returns them
	 * 
	 * @return a list of GameSummary's containing all current games
	 */
	@Override
	public GameSummary[] getGameList() throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Creates a new game on the server
	 * 
	 * @param params - an object with all the information about the game to be created
	 * @return a GameInfo object containing information about the newly created game (id, etc.)
	 */
	@Override
	public GameInfo createGame(CreateGameParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Adds a user to a currently existing game, and sets their cookie
	 * 
	 * @param params - contains info about the player who's joining and the game to join
	 * @return a string saying "success" if successful, or containing error information if not successful
	 */
	@Override
	public String joinGame(JoinGameParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Saves the specified game state into the database for later retrieval
	 * 
	 * @param params - info about the game to be saved, etc.
	 * @return a string saying "success" if successful, or containing error information if not successful
	 */
	@Override
	public String saveGame(SaveParams params) throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Loads a previously saved game from the database back onto the server
	 * 
	 * @param params - information about the game to load
	 * @return a string saying "success" if operation successful, or error information if it failed
	 */
	@Override
	public String loadGame(LoadGameParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Retrieves the current state of the game if the input version is different than the currently stored version
	 * 
	 * @param version - the version number stored on the client
	 * @return a full ClientModel
	 */
	@Override
	public ClientModel getCurrentGame(int version)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	@Override
	public ClientModel resetGame() throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandList getCommands() throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel setCommands(CommandList commands)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getAITypes() throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AddAIResponse addAI(AddAIParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ChangeLogLevelResponse changeLogLevel(ChangeLogLevelParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel updateModel(int versionNumber)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel sendChat(String content) throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel acceptTrade(AcceptTradeParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel discardCards(DiscardCardsParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel rollNumber(int number) throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel buildRoad(BuildRoadParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel buildSettlement(BuildSettlementParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel buildCity(BuildCityParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel offerTrade(TradeOfferParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel maritimeTrade(MaritimeTradeParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel robPlayer(MoveRobberParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel finishTurn(UserActionParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel buyDevCard(UserActionParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel playSoldierCard(MoveSoldierParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel playYearOfPlentyCard(YearOfPlentyParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel playRoadBuildingCard(BuildRoadCardParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel playMonopolyCard(PlayMonopolyParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel playMonument(PlayMonumentParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
