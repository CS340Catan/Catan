package server.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import client.model.ClientModel;
import server.commands.*;
import server.model.*;
import shared.communication.*;
import shared.locations.*;
import shared.data.GameInfo;
import shared.model.Player;
import shared.utils.IServer;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

public class ServerFacade implements IServer {

	private static ServerFacade serverFacade = null;
	private int gameID;
	private static HashMap<Integer, ServerModel> modelMap = new HashMap<Integer, ServerModel>();
	private int currentPlayerID; // !!!!!NOT THE INDEX WITHIN THE GAME!!!!!!!
	private static Logger logger;
	static {
		logger = Logger.getLogger("CatanServer");
	}

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
		ICommand command = new LoginCommand(credentials);
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
		logger.info("server/facade/ServerFacade - entering Register");
		ICommand command = new RegisterCommand(credentials);
		command.execute();
		logger.info("server/facade/ServerFacade - exiting Register");
		return true;
	}

	/**
	 * Retrieves a list of current games and returns them
	 * 
	 * @return a list of GameSummary's containing all current games
	 */
	@Override
	public GameSummary[] getGameList() throws ServerResponseException {
		/*
		 * Don't want to use a command because the execute() function doesn't
		 * return anything, and we need some information back
		 */
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
		ICommand command = new CreateGameCommand(params);
		command.execute();

		for (GameSummary summary : GameList.getSingleton().getGames()) {
			if (summary.getTitle().equals(params.getname())) {
				return summary.toGameInfo();
			}
		}
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
		ICommand command = new JoinGameCommand(params, currentPlayerID);
		command.execute();
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
		new LoadGameCommand(params).execute();
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
		if (this.getServerModel().getVersion() != version || version == -1) {
			return this.getServerModel().toClientModel();
		} else {
			return null;
		}
	}

	/**
	 * Clears the command history of the current game
	 * 
	 * @return a full, reset ClientModel of the current game
	 */
	@Override
	public ClientModel resetGame() throws ServerResponseException {
		// TODO Auto-generated method stub
		return this.getServerModel().toClientModel();
	}

	/**
	 * Retrieves a list of all commands from the current game
	 * 
	 * @return a list of all the executed commands
	 */
	@Override
	public CommandList getCommands() throws ServerResponseException {
		List<String> commandList = new ArrayList<String>();

		for (ICommand command : this.getServerModel().getCommands()) {
			commandList.add(command.toJSONString());
		}

		return new CommandList(commandList);
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
		for (String commandJSONString : commands.getCommands()) {
			ICommand command = ICommand.fromJSONString(commandJSONString);
			command.execute();
		}

		return this.getServerModel().toClientModel();
	}

	/**
	 * Retrieves all the possible AI types that can be played against
	 * 
	 * @return a list of strings, each string specifying an AI type
	 */
	@Override
	public String[] getAITypes() throws ServerResponseException {
		String[] AITypeArray = new String[0];
		return AITypeArray;
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
		AddAIResponse response = new AddAIResponse();
		response.setResponse("Failed");
		return response;
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
	public ChangeLogLevelResponse changeLogLevel(ChangeLogLevelParams params) {
		ChangeLogLevelResponse response = new ChangeLogLevelResponse();
		try {
			new ChangeLogLevelCommand(params).execute();
			response.setResponse("Success");
		} catch (ServerResponseException e) {
			response.setResponse("Invalid request");
			e.printStackTrace();
		}
		return response;
	}

	// Unnecessary, get model does it
	@Override
	public ClientModel updateModel(int versionNumber)
			throws ServerResponseException {
		return this.getServerModel().toClientModel();
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

		ICommand command = new SendChatCommand(chatMessage);
		command.execute();

		return this.getServerModel().toClientModel();
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

		ICommand command = new AcceptTradeCommand(params);
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

		ICommand command = new DiscardCardsCommand(params);
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

		ICommand command = new RollNumberCommand(new RollParams(
				getPlayerIndex(), number));
		command.execute();

		return this.getServerModel().toClientModel();
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

		ICommand command = new BuildRoadCommand(params);
		command.execute();

		return this.getServerModel().toClientModel();
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

		ICommand command = new BuildSettlementCommand(params);
		command.execute();

		return this.getServerModel().toClientModel();
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

		ICommand command = new BuildCityCommand(params);
		command.execute();

		return this.getServerModel().toClientModel();
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

		ICommand command = new OfferTradeCommand(params, getGameID());
		command.execute();

		return this.getServerModel().toClientModel();
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

		ICommand command = new MaritimeTradeCommand(params, getGameID());
		command.execute();

		return this.getServerModel().toClientModel();
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

		ICommand command = new RobPlayerCommand(params);
		command.execute();

		return this.getServerModel().toClientModel();
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

		ICommand command = new FinishTurnCommand(params, getGameID());
		command.execute();

		return this.getServerModel().toClientModel();
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

		ICommand command = new BuyDevCardCommand(params);
		command.execute();

		return this.getServerModel().toClientModel();
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

		ICommand command = new PlaySoldierCommand(params);
		command.execute();

		return this.getServerModel().toClientModel();
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

		ICommand command = new PlayYearOfPlentyCommand(params);
		command.execute();

		return this.getServerModel().toClientModel();
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

		ICommand command = new PlayRoadBuildingCommand(params);
		command.execute();

		return this.getServerModel().toClientModel();
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

		ICommand command = new PlayMonopolyCommand(params);
		command.execute();

		return this.getServerModel().toClientModel();
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

		ICommand command = new PlayMonumentCommand(params);
		command.execute();

		return this.getServerModel().toClientModel();
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
		logger.info("server/facade/ServerFacade - entering setFirstGame");
		ServerModel serverModel = Serializer
				.deserializeServerModel("{\"deck\":{\"yearOfPlenty\":2,\"monopoly\":2,\"soldier\":14,\"roadBuilding\":2,\"monument\":5},\"map\":{\"hexes\":[{\"location\":{\"x\":0,\"y\":-2}},{\"resource\":\"brick\",\"location\":{\"x\":1,\"y\":-2},\"number\":4},{\"resource\":\"wood\",\"location\":{\"x\":2,\"y\":-2},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":-1,\"y\":-1},\"number\":8},{\"resource\":\"wood\",\"location\":{\"x\":0,\"y\":-1},\"number\":3},{\"resource\":\"ore\",\"location\":{\"x\":1,\"y\":-1},\"number\":9},{\"resource\":\"sheep\",\"location\":{\"x\":2,\"y\":-1},\"number\":12},{\"resource\":\"ore\",\"location\":{\"x\":-2,\"y\":0},\"number\":5},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":0},\"number\":10},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":0},\"number\":11},{\"resource\":\"brick\",\"location\":{\"x\":1,\"y\":0},\"number\":5},{\"resource\":\"wheat\",\"location\":{\"x\":2,\"y\":0},\"number\":6},{\"resource\":\"wheat\",\"location\":{\"x\":-2,\"y\":1},\"number\":2},{\"resource\":\"sheep\",\"location\":{\"x\":-1,\"y\":1},\"number\":9},{\"resource\":\"wood\",\"location\":{\"x\":0,\"y\":1},\"number\":4},{\"resource\":\"sheep\",\"location\":{\"x\":1,\"y\":1},\"number\":10},{\"resource\":\"wood\",\"location\":{\"x\":-2,\"y\":2},\"number\":6},{\"resource\":\"ore\",\"location\":{\"x\":-1,\"y\":2},\"number\":3},{\"resource\":\"wheat\",\"location\":{\"x\":0,\"y\":2},\"number\":8}],\"roads\":[{\"owner\":1,\"location\":{\"direction\":\"S\",\"x\":-1,\"y\":-1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":-2}},{\"owner\":2,\"location\":{\"direction\":\"S\",\"x\":1,\"y\":-1}},{\"owner\":0,\"location\":{\"direction\":\"S\",\"x\":0,\"y\":1}},{\"owner\":2,\"location\":{\"direction\":\"S\",\"x\":0,\"y\":0}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-2,\"y\":1}},{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"cities\":[],\"settlements\":[{\"owner\":3,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":1}},{\"owner\":3,\"location\":{\"direction\":\"SE\",\"x\":1,\"y\":-2}},{\"owner\":2,\"location\":{\"direction\":\"SW\",\"x\":0,\"y\":0}},{\"owner\":2,\"location\":{\"direction\":\"SW\",\"x\":1,\"y\":-1}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-2,\"y\":1}},{\"owner\":0,\"location\":{\"direction\":\"SE\",\"x\":0,\"y\":1}},{\"owner\":1,\"location\":{\"direction\":\"SW\",\"x\":-1,\"y\":-1}},{\"owner\":0,\"location\":{\"direction\":\"SW\",\"x\":2,\"y\":0}}],\"radius\":3,\"ports\":[{\"ratio\":3,\"direction\":\"SE\",\"location\":{\"x\":-3,\"y\":0}},{\"ratio\":2,\"resource\":\"brick\",\"direction\":\"NE\",\"location\":{\"x\":-2,\"y\":3}},{\"ratio\":2,\"resource\":\"wood\",\"direction\":\"NE\",\"location\":{\"x\":-3,\"y\":2}},{\"ratio\":2,\"resource\":\"wheat\",\"direction\":\"S\",\"location\":{\"x\":-1,\"y\":-2}},{\"ratio\":3,\"direction\":\"N\",\"location\":{\"x\":0,\"y\":3}},{\"ratio\":2,\"resource\":\"sheep\",\"direction\":\"NW\",\"location\":{\"x\":3,\"y\":-1}},{\"ratio\":3,\"direction\":\"SW\",\"location\":{\"x\":3,\"y\":-3}},{\"ratio\":2,\"resource\":\"ore\",\"direction\":\"S\",\"location\":{\"x\":1,\"y\":-3}},{\"ratio\":3,\"direction\":\"NW\",\"location\":{\"x\":2,\"y\":1}}],\"robber\":{\"x\":0,\"y\":-2}},\"players\":[{\"resources\":{\"brick\":0,\"wood\":1,\"sheep\":1,\"wheat\":1,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":0,\"playerIndex\":0,\"name\":\"Sam\",\"color\":\"orange\"},{\"resources\":{\"brick\":1,\"wood\":2,\"sheep\":2,\"wheat\":1,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":1,\"playerIndex\":1,\"name\":\"Brooke\",\"color\":\"blue\"},{\"resources\":{\"brick\":0,\"wood\":1,\"sheep\":1,\"wheat\":0,\"ore\":2},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":10,\"playerIndex\":2,\"name\":\"Pete\",\"color\":\"red\"},{\"resources\":{\"brick\":2,\"wood\":2,\"sheep\":0,\"wheat\":2,\"ore\":0},\"oldDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"newDevCards\":{\"yearOfPlenty\":0,\"monopoly\":0,\"soldier\":0,\"roadBuilding\":0,\"monument\":0},\"roads\":13,\"cities\":4,\"settlements\":3,\"soldiers\":0,\"victoryPoints\":2,\"monuments\":0,\"playedDevCard\":false,\"discarded\":false,\"playerID\":11,\"playerIndex\":3,\"name\":\"Mark\",\"color\":\"green\"}],\"log\":{\"lines\":[{\"source\":\"Sam\",\"message\":\"Sam built a road\"},{\"source\":\"Sam\",\"message\":\"Sam built a settlement\"},{\"source\":\"Sam\",\"message\":\"Sam\u0027s turn just ended\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a road\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a settlement\"},{\"source\":\"Brooke\",\"message\":\"Brooke\u0027s turn just ended\"},{\"source\":\"Pete\",\"message\":\"Pete built a road\"},{\"source\":\"Pete\",\"message\":\"Pete built a settlement\"},{\"source\":\"Pete\",\"message\":\"Pete\u0027s turn just ended\"},{\"source\":\"Mark\",\"message\":\"Mark built a road\"},{\"source\":\"Mark\",\"message\":\"Mark built a settlement\"},{\"source\":\"Mark\",\"message\":\"Mark\u0027s turn just ended\"},{\"source\":\"Mark\",\"message\":\"Mark built a road\"},{\"source\":\"Mark\",\"message\":\"Mark built a settlement\"},{\"source\":\"Mark\",\"message\":\"Mark\u0027s turn just ended\"},{\"source\":\"Pete\",\"message\":\"Pete built a road\"},{\"source\":\"Pete\",\"message\":\"Pete built a settlement\"},{\"source\":\"Pete\",\"message\":\"Pete\u0027s turn just ended\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a road\"},{\"source\":\"Brooke\",\"message\":\"Brooke built a settlement\"},{\"source\":\"Brooke\",\"message\":\"Brooke\u0027s turn just ended\"},{\"source\":\"Sam\",\"message\":\"Sam built a road\"},{\"source\":\"Sam\",\"message\":\"Sam built a settlement\"},{\"source\":\"Sam\",\"message\":\"Sam\u0027s turn just ended\"}]},\"chat\":{\"lines\":[]},\"bank\":{\"brick\":23,\"wood\":21,\"sheep\":20,\"wheat\":22,\"ore\":22},\"turnTracker\":{\"status\":\"Rolling\",\"currentTurn\":0,\"longestRoad\":-1,\"largestArmy\":-1},\"winner\":-1,\"version\":0}");

		this.setFirstGameCommands(serverModel);

		modelMap.put(0, serverModel);

		RegisteredPlayers.getSingleton().addNewPlayer("Sam", "sam");
		RegisteredPlayers.getSingleton().addNewPlayer("Brooke", "brooke");
		RegisteredPlayers.getSingleton().addNewPlayer("Pete", "pete");
		RegisteredPlayers.getSingleton().addNewPlayer("Mark", "mark");

		PlayerSummary sam = new PlayerSummary("orange", "Sam", 1);
		PlayerSummary brooke = new PlayerSummary("blue", "Brooke", 2);
		PlayerSummary pete = new PlayerSummary("red", "Pete", 3);
		PlayerSummary mark = new PlayerSummary("green", "Mark", 4);
		PlayerSummary[] summaries = new PlayerSummary[4];

		summaries[0] = sam;
		summaries[1] = brooke;
		summaries[2] = pete;
		summaries[3] = mark;

		GameSummary gameSummary = new GameSummary("Default Game", 0, summaries);
		GameList.getSingleton().addGame(gameSummary);
		logger.info("server/facade/ServerFacade - exiting setFirstGame");
	}

	private void setFirstGameCommands(ServerModel serverModel) {
		List<ICommand> commandList = serverModel.getCommands();

		/*
		 * Create commands for all roads.
		 */
		EdgeLocation location_1 = new EdgeLocation(new HexLocation(-1, -1),
				EdgeDirection.South);
		location_1.convertToPrimitives();
		commandList.add(new BuildRoadCommand(new BuildRoadParams(1, location_1,
				true)));

		EdgeLocation location_2 = new EdgeLocation(new HexLocation(-1, 1),
				EdgeDirection.SouthWest);
		location_2.convertToPrimitives();
		commandList.add(new BuildRoadCommand(new BuildRoadParams(3, location_2,
				true)));

		EdgeLocation location_3 = new EdgeLocation(new HexLocation(2, -2),
				EdgeDirection.SouthWest);
		location_3.convertToPrimitives();
		commandList.add(new BuildRoadCommand(new BuildRoadParams(3, location_3,
				true)));

		EdgeLocation location_4 = new EdgeLocation(new HexLocation(1, -1),
				EdgeDirection.South);
		location_4.convertToPrimitives();
		commandList.add(new BuildRoadCommand(new BuildRoadParams(2, location_4,
				true)));

		EdgeLocation location_5 = new EdgeLocation(new HexLocation(0, 1),
				EdgeDirection.South);
		location_5.convertToPrimitives();
		commandList.add(new BuildRoadCommand(new BuildRoadParams(0, location_5,
				true)));

		EdgeLocation location_6 = new EdgeLocation(new HexLocation(0, 0),
				EdgeDirection.South);
		location_6.convertToPrimitives();
		commandList.add(new BuildRoadCommand(new BuildRoadParams(2, location_6,
				true)));

		EdgeLocation location_7 = new EdgeLocation(new HexLocation(-2, 1),
				EdgeDirection.SouthWest);
		location_7.convertToPrimitives();
		commandList.add(new BuildRoadCommand(new BuildRoadParams(1, location_7,
				true)));

		EdgeLocation location_8 = new EdgeLocation(new HexLocation(2, 0),
				EdgeDirection.SouthWest);
		location_8.convertToPrimitives();
		commandList.add(new BuildRoadCommand(new BuildRoadParams(0, location_8,
				true)));

		/*
		 * Create commands for all settlements.
		 */
		VertexLocation location_9 = new VertexLocation(new HexLocation(-1, 1),
				VertexDirection.SouthWest);
		location_9.convertToPrimitives();
		commandList.add(new BuildSettlementCommand(new BuildSettlementParams(3,
				location_9, true)));

		VertexLocation location_10 = new VertexLocation(new HexLocation(1, -2),
				VertexDirection.SouthEast);
		location_10.convertToPrimitives();
		commandList.add(new BuildSettlementCommand(new BuildSettlementParams(3,
				location_10, true)));

		VertexLocation location_11 = new VertexLocation(new HexLocation(0, 0),
				VertexDirection.SouthWest);
		location_11.convertToPrimitives();
		commandList.add(new BuildSettlementCommand(new BuildSettlementParams(2,
				location_11, true)));

		VertexLocation location_12 = new VertexLocation(new HexLocation(1, -1),
				VertexDirection.SouthWest);
		location_12.convertToPrimitives();
		commandList.add(new BuildSettlementCommand(new BuildSettlementParams(2,
				location_12, true)));

		VertexLocation location_13 = new VertexLocation(new HexLocation(-2, 1),
				VertexDirection.SouthWest);
		location_13.convertToPrimitives();
		commandList.add(new BuildSettlementCommand(new BuildSettlementParams(1,
				location_13, true)));

		VertexLocation location_14 = new VertexLocation(new HexLocation(0, 1),
				VertexDirection.SouthEast);
		location_14.convertToPrimitives();
		commandList.add(new BuildSettlementCommand(new BuildSettlementParams(0,
				location_14, true)));

		VertexLocation location_15 = new VertexLocation(
				new HexLocation(-1, -1), VertexDirection.SouthWest);
		location_15.convertToPrimitives();
		commandList.add(new BuildSettlementCommand(new BuildSettlementParams(1,
				location_15, true)));

		VertexLocation location_16 = new VertexLocation(new HexLocation(2, 0),
				VertexDirection.SouthWest);
		location_16.convertToPrimitives();
		commandList.add(new BuildSettlementCommand(new BuildSettlementParams(0,
				location_16, true)));
	}

	public HashMap<Integer, ServerModel> getModelMap() {
		return modelMap;
	}

	public int getPlayerIndex() {

		ServerModel model = getServerModel();
		String playerName = RegisteredPlayers.getSingleton().getPlayerName(
				getPlayerID());
		Player[] players = model.getPlayers();

		for (Player p : players) {
			if (p.getName().equals(playerName)) {
				return p.getPlayerIndex();
			}
		}

		return -1;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		ServerFacade.logger = logger;
	}

}
