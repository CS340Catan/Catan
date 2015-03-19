package server.facade;

import java.util.HashMap;
import java.util.ArrayList;

import client.data.GameInfo;
import client.model.ClientModel;
import server.commands.*;
import server.model.ServerModel;
import shared.communication.*;
import shared.model.GameList;
//import shared.model.RegisteredPlayers;

import shared.model.Player;
import shared.model.RegisteredPlayers;
import shared.utils.IServer;
import shared.utils.ServerResponseException;

public class ServerFacade implements IServer {

	// private RegisteredPlayers registeredPlayers;
	private static ServerFacade serverFacade = null;
	private ICommand command;
	private int gameID;
	private static HashMap<Integer, ServerModel> modelMap = new HashMap<Integer, ServerModel>();
	private int currentPlayerID;// !!!!!NOT THE INDEX WITHIN THE GAME!!!!!!!

	public static ServerFacade getSingleton() {
		if (serverFacade == null) {
			serverFacade = new ServerFacade();
		}
		return serverFacade;
	}

	private ServerFacade() {

	}

	/**
	 * Verifies the user, and adds a cookie for them on the server
	 * 
	 * @param credentials
	 *            - name and password of user logging in
	 * @return boolean specifying whether the login credentials were valid or
	 *         not
	 */
	@Override
	public boolean login(UserCredentials credentials)
			throws ServerResponseException {
		command = new LoginCommand(credentials);
		command.execute();
		return true;
	}

	/**
	 * Creates a new user account, then logs them in and sets their cookie
	 * 
	 * @param credentials
	 *            - name and password of new user
	 * @return boolean specifying whether the login credentials were valid or
	 *         not
	 */
	@Override
	public boolean Register(UserCredentials credentials)
			throws ServerResponseException {
		command = new RegisterCommand(credentials);
		command.execute();
		return true;
	}

	/**
	 * Retrieves a list of current games and returns them
	 * 
	 * @return a list of GameSummary's containing all current games
	 */
	@Override
	public GameSummary[] getGameList() throws ServerResponseException {
		// don't want to us a command because the execute() function doesn't
		// return anything,
		// and we need some information back
		ArrayList<GameSummary> games = GameList.getSingleton().getGames();
		return games.toArray(new GameSummary[games.size()]);
	}

	/**
	 * Creates a new game on the server
	 * 
	 * @param params
	 *            - an object with all the information about the game to be
	 *            created
	 * @return a GameInfo object containing information about the newly created
	 *         game (id, etc.)
	 */
	@Override
	public GameInfo createGame(CreateGameParams params)
			throws ServerResponseException {
		command = new CreateGameCommand(params);
		command.execute();

		return null;
	}

	/**
	 * Adds a user to a currently existing game, and sets their cookie
	 * 
	 * @param params
	 *            - contains info about the player who's joining and the game to
	 *            join
	 * @return a string saying "Success" if Successful, or containing error
	 *         information if not Successful
	 */
	@Override
	public String joinGame(JoinGameParams params)
			throws ServerResponseException {
		new JoinGameCommand(params, currentPlayerID).execute();
		return null;
	}

	/**
	 * Saves the specified game state into the database for later retrieval
	 * 
	 * @param params
	 *            - info about the game to be saved, etc.
	 * @return a string saying "Success" if Successful, or containing error
	 *         information if not Successful
	 */
	@Override
	public String saveGame(SaveParams params) throws ServerResponseException {
		new SaveGameCommand(params).execute();
		return null;
	}

	/**
	 * Loads a previously saved game from the database back onto the server
	 * 
	 * @param params
	 *            - information about the game to load
	 * @return a string saying "Success" if operation Successful, or error
	 *         information if it failed
	 */
	@Override
	public String loadGame(LoadGameParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Retrieves the current state of the game if the input version is different
	 * than the currently stored version
	 * 
	 * @param version
	 *            - the version number stored on the client
	 * @return a full ClientModel if there's a new game, or null if there is no
	 *         new game
	 */
	@Override
	public ClientModel getCurrentGame(int version)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Clears the command history of the current game
	 * 
	 * @return a full, reset ClientModel of the current game
	 */
	@Override
	public ClientModel resetGame() throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Retrieves a list of all commands from the current game
	 * 
	 * @return a list of all the executed commands
	 */
	@Override
	public CommandList getCommands() throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Executes a list of commands on the current game
	 * 
	 * @param commands
	 *            - a list of all the commands to execute
	 * @return a full ClientModel with the commands executed on it
	 */
	@Override
	public ClientModel setCommands(CommandList commands)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Retrieves all the possible AI types that can be played against
	 * 
	 * @return a list of strings, each string specifying an AI type
	 */
	@Override
	public String[] getAITypes() throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Adds a player to the current game of the specified AI type
	 * 
	 * @param params
	 *            - the AI type to add
	 * @return a response with a string saying "Success" if operation
	 *         Successful, or error data if it failed
	 */
	@Override
	public AddAIResponse addAI(AddAIParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Changes the level at which the server should log
	 * 
	 * @param params
	 *            - contains the level to set the log to
	 * @return a response with a string saying "Success" if operation
	 *         successful, or error data if it failed
	 */
	@Override
	public ChangeLogLevelResponse changeLogLevel(ChangeLogLevelParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	// Unnecessary, get model does it
	@Override
	public ClientModel updateModel(int versionNumber)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Adds a message to the current game's message list
	 * 
	 * @param content
	 *            - a string containing the message to be added
	 * @return an updated ClientModel
	 */
	@Override
	public ClientModel sendChat(ChatMessage chatMessage)
			throws ServerResponseException {
		command = new SendChatCommand(chatMessage, 0);
		command.execute();
		return null;
	}

	/**
	 * Has a player accept/reject an offered trade in the model
	 * 
	 * @param params
	 *            - contains info about the trade that was accepted (accepted or
	 *            not, etc.)
	 * @return an updated ClientModel
	 */
	@Override
	public ClientModel acceptTrade(AcceptTradeParams params)
			throws ServerResponseException {

		command = new AcceptTradeCommand(params);
		command.execute();

		return this.getServerModel().toClientModel();
	}

	/**
	 * Discards cards of a player
	 * 
	 * @param params
	 *            - the resources the player is discarding
	 * @return an updated ClientModel
	 */
	@Override
	public ClientModel discardCards(DiscardCardsParams params)
			throws ServerResponseException {
		command = new DiscardCardsCommand(params);
		command.execute();

		return this.getServerModel().toClientModel();
	}

	/**
	 * Rolls a number for the player, updates ClientModel status
	 * 
	 * @param number
	 *            - the number the player rolled
	 * @return an updated ClientModel
	 */
	@Override
	public ClientModel rollNumber(int number) throws ServerResponseException {

		command = new RollNumberCommand(new RollParams(playerIndex, number));
		command.execute();

		return getServerModel();
	}

	/**
	 * Builds a road on the map, also updates resources and longest road status
	 * 
	 * @param params
	 *            - info about where to build the road
	 * @return an updated ClientModel
	 */
	@Override
	public ClientModel buildRoad(BuildRoadParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Builds a settlement on the map, updates resources
	 * 
	 * @param params
	 *            - info about where to build settlement
	 * @return an updated ClientModel
	 */
	@Override
	public ClientModel buildSettlement(BuildSettlementParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Builds a city on the map, updates resources and gives settlement back
	 * 
	 * @param params
	 *            - info about city location
	 * @return an updated ClientModel
	 */
	@Override
	public ClientModel buildCity(BuildCityParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Offers a trade from the player to another player
	 * 
	 * @param params
	 *            - info about the trade (to who, resources)
	 * @return an updated ClientModel
	 */
	@Override
	public ClientModel offerTrade(TradeOfferParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Makes a maritime trade for the player, updates resources of player and
	 * bank
	 * 
	 * @param params
	 *            - info about which resources will be traded
	 * @return an updated ClientModel
	 */
	@Override
	public ClientModel maritimeTrade(MaritimeTradeParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Moves robber to a new location, allocates random resource from robbed
	 * player to robbing player
	 * 
	 * @param params
	 *            - info about location of robber and who to rob
	 * @return an updated ClientModel
	 */
	@Override
	public ClientModel robPlayer(MoveRobberParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Finishes the players turn, transfers playing status to the next player
	 * 
	 * @param params
	 *            - the player id
	 * @return an updated ClientModel
	 */
	@Override
	public ClientModel finishTurn(UserActionParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Buys a development card for the player
	 * 
	 * @param params
	 *            - the player id
	 * @return an updated ClientModel
	 */
	@Override
	public ClientModel buyDevCard(UserActionParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Plays a soldier card for the player, robs and re-allocates largest army
	 * card correctly
	 * 
	 * @param params
	 *            - info about where the robber goes, who to rob
	 * @return an updated ClientModel
	 */
	@Override
	public ClientModel playSoldierCard(MoveSoldierParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Plays year of plenty card for player, gives resources to player
	 * 
	 * @param params
	 *            - the two resources player wants
	 * @return an updated ClientModel
	 */
	@Override
	public ClientModel playYearOfPlentyCard(YearOfPlentyParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Plays road building card for player, adds two roads to map and
	 * re-allocates longest road correctly
	 * 
	 * @param params
	 *            - info about location of two roads
	 * @return an updated ClientModel
	 */
	@Override
	public ClientModel playRoadBuildingCard(BuildRoadCardParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Plays monopoly card for player, re-allocates specified resource from
	 * other players to player
	 * 
	 * @param params
	 *            - the resource type player wants to steal
	 * @return an updated ClientModel
	 */
	@Override
	public ClientModel playMonopolyCard(PlayMonopolyParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Plays the monument card for player, gives player victory point
	 * 
	 * @param params
	 *            - info about player
	 * @return an updated ClientModel
	 */
	@Override
	public ClientModel playMonument(PlayMonumentParams params)
			throws ServerResponseException {
		command = new PlayMonumentCommand(params);
		command.execute();

		return null;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public ServerModel getServerModel() {
		return modelMap.get(gameID);
	}

	public int getPlayerID() {
		return currentPlayerID;
	}

	public void setPlayerID(int playerId) {
		this.currentPlayerID = playerId;
	}

	public void setFirstGame() {
		ClientModel clientModel = new ClientModel();
		
	}
	
	public int getPlayerIndex() {
		
		ServerModel model = getServerModel();
		String playerName = RegisteredPlayers.getSingleton().getPlayerName(getPlayerID());
		Player[] players = model.getPlayers();
		
		for(Player p: players) {
			if(p.getName().equals(playerName)) {
				return p.getPlayerIndex();
			}
		}
		
		return -1;
	}
	
}
