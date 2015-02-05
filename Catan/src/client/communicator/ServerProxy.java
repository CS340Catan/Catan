package client.communicator;

import shared.communication.*;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.utils.*;
import client.communication.HTTPCommunicator;
import client.model.ClientModel;
import client.model.ResourceList;

/**
 * This class implements the IServer interface, as the way in which the Client
 * is able to communicate with the server via a HTTP connection. This class with
 * package the input parameters for each individual, such that they can sent to
 * the server via a HTTPCommunicator. The response from the HTTPCommunicator
 * will then be unpackaged into the appropriate objects.
 * 
 * @author Keloric
 *
 */
public class ServerProxy implements IServer {
	
	
	public HTTPCommunicator getHttpCommunicator() {
		return httpCommunicator;
	}

	public void setHttpCommunicator(HTTPCommunicator httpCommunicator) {
		this.httpCommunicator = httpCommunicator;
	}

	/** used to send data over the network */
	private HTTPCommunicator httpCommunicator;

	/**
	 * Default constructor.
	 * 
	 * @param httpCommunicator
	 *            Given communicator that is connected to the server.
	 */
	public ServerProxy(HTTPCommunicator httpCommunicator) {
		this.httpCommunicator = httpCommunicator;
	}

	/**
	 * Checks to see if the server's model has been updated, returns the new model
	 * if there is a new one available, otherwise returns a null ClientModel
	 * 
	 * @return
	 * @pre none
	 * @post ClientModel is updated
	 */
	public ClientModel updateModel(int version) {
		String jsonResponseString = httpCommunicator.doGet("/game/model?version=" + version, null);
		ClientModel model = null;		//Returns null if current model is already correct or there was an error
		if(jsonResponseString != null) {
			if(!jsonResponseString.equals("true")) {
				model = Serializer.deserializeClientModel(jsonResponseString);
			}
		}
		return model;
	}

	/**
	 * Prepares credentials to be sent over network, then sends them to server
	 * login
	 * 
	 * @pre username not null
	 * @pre password not null
	 * @post a valid LoginResponse returned
	 */
	public boolean Login(UserCredentials credentials) {
		String jsonString = Serializer.serialize(credentials);
		String response = httpCommunicator.doPost("/user/login", jsonString);
		if(response == null) {
			return false;
		}
		else {
			return(response.equals("Success"));
		}
	}

	/**
	 * Prepares credentials to be sent over network, then sends them to server
	 * registration
	 * 
	 * @pre username not null
	 * @pre password not null
	 * @post a valid LoginResponse returned
	 */
	public boolean Register(UserCredentials credentials) {
		String jsonString = Serializer.serialize(credentials);
		String response = httpCommunicator.doPost("/user/register", jsonString);
		if(response == null) {
			return false;
		}
		else {
			return(response.equals("Success"));
		}
	}

	/**
	 * Retrieves a list of the currently existing games
	 * 
	 * @pre none
	 * @post A valid CurrentGames returned
	 */
	public GameSummary[] getGameList() {
		String response = httpCommunicator.doGet("/games/list", null);
		if(response != null) {
			return (GameSummary[])Serializer.deserialize(response, GameSummary[].class);
		}
		else {
			return null;
		}
	}

	/**
	 * Prepares params to be sent over network, then sends them to server to
	 * create a game
	 * 
	 * @pre name not null
	 * @pre params contains only valid boolean values
	 * @post a valid GameSummary returned
	 */
	public GameSummary createGame(CreateGameParams params) {
		String jsonString = Serializer.serialize(params);
		String response = httpCommunicator.doPost("/games/create", jsonString);
		if(response != null) {
			return (GameSummary)Serializer.deserialize(response, GameSummary.class);
		}
		else {
			return null;
		}
	}

	/**
	 * Prepares params to be sent over network, then sends them to server to
	 * join a game
	 * 
	 * @pre user has previously logged into server
	 * @pre The player is already in the game OR there is room for a new player
	 * @pre game id is valid
	 * @pre color is valid (red, green, blue, yellow, puce, brown, white,
	 *      purple, orange)
	 * @post a valid boolean returned
	 */
	public String joinGame(JoinGameParams params) {
		String jsonString = Serializer.serialize(params);
		String response = httpCommunicator.doPost("/games/join", jsonString);
		return response;
	}

	/**
	 * Prepares params to be sent over network, then sends them to server to
	 * save a game
	 * 
	 * @pre game id is valid
	 * @pre filname is not null or empty
	 * @post a valid boolean returned
	 */
	public String saveGame(SaveParams params) {
		String jsonString = Serializer.serialize(params);
		String response = httpCommunicator.doPost("/games/save", jsonString);
		return response;
	}

	/**
	 * Prepares the filename to be sent over network, then sends it to server to
	 * load a game
	 * 
	 * @pre a saved game with the specified filename exists on the server
	 * @post a valid boolean returned
	 */
	public String loadGame(LoadGameParams params) {
		String jsonString = Serializer.serialize(params);
		String response = httpCommunicator.doPost("/games/load", jsonString);
		return response;
	}

	/**
	 * Prepares the version number to be sent over the network, then retrieves
	 * current game from server if it's different than the current version
	 * 
	 * @pre user has logged on and joined a game, and therefore has cookies
	 * @pre version is a valid int
	 * @post a valid ClientModel returned
	 */
	public ClientModel getCurrentGame(int version) {
		String jsonResponseString = httpCommunicator.doGet("/game/model?version=" + version, null);
		ClientModel model = null;		//Returns null if current model is already correct or there was an error
		if(jsonResponseString != null) {
			if(!jsonResponseString.equals("true")) {
				model = Serializer.deserializeClientModel(jsonResponseString);
			}
		}
		return model;
	}

	/**
	 * Tells the server to reset the game
	 * 
	 * @pre none
	 * @post a valid ClientModel returned
	 */
	public ClientModel resetGame() {
		String response = httpCommunicator.doPost("/game/reset", null);
		if(response != null) {
			return Serializer.deserializeClientModel(response);
		}
		else {
			return null;
		}
	}

	/**
	 * Retrieves all the past commands in the current game from the server
	 * 
	 * @pre none
	 * @post a valid set of commands returned
	 */
	public CommandList getCommands() {
		String response = httpCommunicator.doGet("/game/commands", null);
		if(response != null) {
			return (CommandList)Serializer.deserialize(response, CommandList.class);
		}
		else {
			return null;
		}
	}

	/**
	 * Prepares commands to be sent over network, then sends them to server to
	 * apply to current game
	 * 
	 * @pre user has logged on and joined a game, and therefore has cookies
	 * @post a valid ClientModel returned
	 */
	public ClientModel setCommands(CommandList commands) {
		String jsonString = Serializer.serialize(commands);
		String response = httpCommunicator.doPost("/game/commands", jsonString);
		if(response != null) {
			return Serializer.deserializeClientModel(response);
		}
		else {
			return null;
		}
	}

	/**
	 * Retrieves a list from the server of the different types of AI players
	 * available
	 * 
	 * @pre none
	 * @post a valid list of AI types returned
	 */
	public String[] getAITypes() {
		String response = httpCommunicator.doGet("/game/listAI", null);
		if(response != null) {
			return (String[])Serializer.deserialize(response, String[].class);
		}
		else {
			return null;
		}
	}

	/**
	 * Prepares the AIType to be sent over network, then sends it to server to
	 * create a new AI player
	 * 
	 * @pre user has logged on and joined a game, and therefore has cookies
	 * @pre there is space in the game for another player
	 * @pre the AIType is a valid type returned by the getAITypes method
	 * @post a valid boolean returned
	 */
	@Override
	public AddAIResponse addAI(AddAIParams params) {
		String jsonString = Serializer.serialize(params);
		String response = httpCommunicator.doPost("/game/addAI", jsonString);
		if(response != null) {
			return (AddAIResponse)Serializer.deserialize(response, AddAIResponse.class);
		}
		else {
			return null;
		}
	}

	/**
	 * Prepares the log level to be sent over network, then sends it to server
	 * to change the granularity of the log it keeps
	 * 
	 * @pre level is a valid LogLevel (SEVERE, WARNING, INFO, CONFIG, FINE,
	 *      FINER, FINEST)
	 * @post a valid boolean returned
	 */

	/**
	 * Prepares the log level to be sent over network, then sends it to server
	 * to change the granularity of the log it keeps
	 * 
	 * @pre level is a valid LogLevel (SEVERE, WARNING, INFO, CONFIG, FINE,
	 *      FINER, FINEST)
	 * @post a valid boolean returned
	 */
	@Override
	public ChangeLogLevelResponse changeLogLevel(ChangeLogLevelParams level) {
		String jsonString = Serializer.serialize(level);
		String response = httpCommunicator.doPost("/util/changeLogLevel", jsonString);
		if(response != null) {
			return (ChangeLogLevelResponse)Serializer.deserialize(response, ChangeLogLevelResponse.class);
		}
		else {
			return null;
		}
	}

	
	/* ----------------------Move APIs ------------------------ */
	
	
	/**
	 * @Pre none
	 * @Post chat contains the player's message at the end
	 * @param content
	 * @return
	 */
	@Override
	public ClientModel sendChat(String content) {
		String jsonString = Serializer.serialize(content);
		String response = httpCommunicator.doPost("/moves/sendChat", jsonString);
		if(response != null) {
			return Serializer.deserializeClientModel(response);
		}
		else {
			return null;
		}
	}

	/**
	 * @Pre Current player has been offered a domestic trade
	 * @Pre To accept the offered trade, current player has the required
	 *      resources
	 * @Post If the current player accepted, the current player and the player
	 *       who offered swap the specified resources
	 * @Post If the current player declined no resources are exchanged
	 * @Post The trade offer is removed
	 * @param willAccept
	 */
	@Override
	public ClientModel acceptTrade(AcceptTradeParams params) {
		String jsonString = Serializer.serialize(params);
		String response = httpCommunicator.doPost("/moves/acceptTrade", jsonString);
		if(response != null) {
			return Serializer.deserializeClientModel(response);
		}
		else {
			return null;
		}
	}

	/**
	 * @Pre The status of the client model is 'Discarding'
	 * @Pre The current player has over 7 cards
	 * @Pre The current player has the cards the current player is choosing to
	 *      discard.
	 * @Post Postconditions
	 * @Post The current player gives up the specified resources
	 * @Post If the current player is the last one to discard, the client model
	 *       status changes to 'Robbing'
	 * @param discardedCards
	 * @return
	 */
	@Override
	public ClientModel discardCards(DiscardCardsParams params) {
		String jsonString = Serializer.serialize(params);
		String response = httpCommunicator.doPost("/moves/discardCards", jsonString);
		if(response != null) {
			return Serializer.deserializeClientModel(response);
		}
		else {
			return null;
		}
	}

	/**
	 * @Pre It is the current player's turn
	 * @Pre The client modelâ€™s status is â€˜Rollingâ€™
	 * @Post The client modelâ€™s status is now in â€˜Discardingâ€™ or
	 *       â€˜Robbingâ€™ or â€˜Playingâ€™
	 * @param number
	 * @return
	 */
	@Override
	public ClientModel rollNumber(int number) {
		String jsonString = Serializer.serialize(new Integer(number));
		String response = httpCommunicator.doPost("/moves/rollNumber", jsonString);
		if(response != null) {
			return Serializer.deserializeClientModel(response);
		}
		else {
			return null;
		}
	}

	/**
	 * @Pre The road location is open
	 * @Pre The road location is connected to another road owned by the player
	 * @Pre The road location is not on water
	 * @Pre The current player has the required resources (1 wood, 1 brick and 1
	 *      road)
	 * @Pre Setup round: Must be placed by settlement owned by the player with
	 *      no adjacent road
	 * @Post The current player lost the resources required to build a road (1
	 *       wood, 1 brick and 1 road)
	 * @Post The road is on the map at the specified location
	 * @Post If applicable, "Longest Road" has been awarded to the player with
	 *       the longest road
	 * @param free
	 * @param roadLocation
	 * @return
	 */
	@Override
	public ClientModel buildRoad(BuildRoadParams params) {
		String jsonString = Serializer.serialize(params);
		String response = httpCommunicator.doPost("/moves/buildRoad", jsonString);
		if(response != null) {
			return Serializer.deserializeClientModel(response);
		}
		else {
			return null;
		}
	}

	/**
	 * @Pre The settlement location is open The settlement location is not on
	 *      water
	 * @Pre The settlement location is connected to one of the current player's
	 *      roads except during setup
	 * @Pre The current player has the required resources (1 wood, 1 brick, 1
	 *      wheat, 1 sheep and 1 settlement)
	 * @Pre The settlement cannot be placed adjacent to another settlement
	 * 
	 * @param free
	 * @param vertexLocation
	 * @return
	 */
	@Override
	public ClientModel buildSettlement(BuildSettlementParams params) {
		String jsonString = Serializer.serialize(params);
		String response = httpCommunicator.doPost("/moves/buildSettlement", jsonString);
		if(response != null) {
			return Serializer.deserializeClientModel(response);
		}
		else {
			return null;
		}
	}

	/**
	 * updates model, builds a city in specified location
	 * 
	 * @pre the city location is where you currently have a settlement
	 * @pre you have the required resources (2 wheat, 3 ore; 1 city)
	 * @post you lost the resources required to build a city (2 wheat, 3 ore; 1
	 *       city)
	 * @post the city is on the map at the specified location
	 * @post you got a settlement back
	 */
	@Override
	public ClientModel buildCity(BuildCityParams params) {
		String jsonString = Serializer.serialize(params);
		String response = httpCommunicator.doPost("/moves/buildCity", jsonString);
		if(response != null) {
			return Serializer.deserializeClientModel(response);
		}
		else {
			return null;
		}
	}

	/**
	 * displays to other player a trade offer
	 * 
	 * @pre you have the resources you are offering
	 * @post the trade is offered to the other player (stored in the server
	 *       model)
	 */
	@Override
	public ClientModel offerTrade(TradeOfferParams params) {
		String jsonString = Serializer.serialize(params);
		String response = httpCommunicator.doPost("/moves/offerTrade", jsonString);
		if(response != null) {
			return Serializer.deserializeClientModel(response);
		}
		else {
			return null;
		}
	}

	/**
	 * trades resource cards according to a certain ratio
	 * 
	 * @pre you have the resources you are giving
	 * @pre for ratios less than 4, you have the correct port for the trade
	 * @post trade has been executed (offered resources are in the bank, and the
	 *       requested resource has been received)
	 */
	@Override
	public ClientModel maritimeTrade(MaritimeTradeParams params) {
		String jsonString = Serializer.serialize(params);
		String response = httpCommunicator.doPost("/moves/maritimeTrade", jsonString);
		if(response != null) {
			return Serializer.deserializeClientModel(response);
		}
		else {
			return null;
		}
	}

	/**
	 * moves the robber, steals a development card from a player
	 * 
	 * @pre robber is not being kept in the same location
	 * @pre if a player is being robbed, the player being robbed has resource
	 *      cards
	 * @post robber is in the new location
	 * @post player being robbed (if any) gave you one of his resource cards
	 *       (randomly selected)
	 */
	@Override
	public ClientModel robPlayer(MoveRobberParams params) {
		String jsonString = Serializer.serialize(params);
		String response = httpCommunicator.doPost("/moves/robPlayer", jsonString);
		if(response != null) {
			return Serializer.deserializeClientModel(response);
		}
		else {
			return null;
		}
	}

	/**
	 * end player's turn, start next payer's turn
	 * 
	 * @pre none
	 * @post cards in new dev card hand have been transferred to old dev card
	 * @post it is the next player's turn
	 */
	@Override
	public ClientModel finishTurn() {
		String response = httpCommunicator.doPost("/moves/finishTurn", null);
		if(response != null) {
			return Serializer.deserializeClientModel(response);
		}
		else {
			return null;
		}
	}

	/**
	 * removes resource cards from player, retrieves development card from bank
	 * 
	 * @pre player has the required resources
	 * @pre there is a development card left in the bank
	 * @post player has a new development card
	 * @post if it is monument card, add it to old devcard hand else, add it to
	 *       new devcard hand
	 */
	@Override
	public ClientModel buyDevCard() {
		String response = httpCommunicator.doPost("/moves/buyDevCard", null);
		if(response != null) {
			return Serializer.deserializeClientModel(response);
		}
		else {
			return null;
		}
	}

	/**
	 * @Pre player has the specific card they want to play in their
	 *      "old dev card hand"
	 * @Pre player hasn't played a dev card this turn
	 * @Pre It's the player's turn
	 * @Pre The current model status is "playing"
	 * @Pre The robber isn't being kept in the same place
	 * @Pre The player to rob has cards (-1 if you can't rob anyone)
	 * @Post The robber is in the new location
	 * @Post The player to rob gives one random resource card to the player
	 *       playing the soldier
	 */
	@Override
	public ClientModel playSoldierCard(MoveSoldierParams params) {
		String jsonString = Serializer.serialize(params);
		String response = httpCommunicator.doPost("/moves/Soldier", jsonString);
		if(response != null) {
			return Serializer.deserializeClientModel(response);
		}
		else {
			return null;
		}
	}

	/**
	 * @Pre player has the specific card they want to play in their
	 *      "old dev card hand"
	 * @Pre player hasn't played a dev card this turn
	 * @Pre It's the player's turn
	 * @Pre The current model status is "playing"
	 * @Pre The two resources you specify are in the bank
	 * @Post Player gains the two resources specified
	 */
	@Override
	public ClientModel playYearOfPlentyCard(YearOfPlentyParams params) {
		String jsonString = Serializer.serialize(params);
		String response = httpCommunicator.doPost("/moves/Year_of_Plenty", jsonString);
		if(response != null) {
			return Serializer.deserializeClientModel(response);
		}
		else {
			return null;
		}
	}

	/**
	 * @Pre player has the specific card they want to play in their
	 *      "old dev card hand"
	 * @Pre player hasn't played a dev card this turn
	 * @Pre It's the player's turn
	 * @Pre The current model status is "playing"
	 * @Pre The first road location is connected to one of your roads
	 * @Pre The second road location is connected to one of your roads on the
	 *      previous location
	 * @Pre Neither location is on water
	 * @Pre Player has two roads
	 * @Post Player uses two roads
	 * @Post The map lists the roads correctly
	 */
	@Override
	public ClientModel playRoadBuildingCard(BuildRoadCardParams params) {
		String jsonString = Serializer.serialize(params);
		String response = httpCommunicator.doPost("/moves/Road_Building", jsonString);
		if(response != null) {
			return Serializer.deserializeClientModel(response);
		}
		else {
			return null;
		}
	}

	/**
	 * @Pre player has the specific card they want to play in their
	 *      "old dev card hand"
	 * @Pre player hasn't played a dev card this turn
	 * @Pre It's the player's turn
	 * @Pre The current model status is "playing"
	 * @Post All other players lose the resource card type chosen
	 * @Post The player of the card gets an equal number of that resource type
	 */
	@Override
	public ClientModel playMonopolyCard(PlayMonopolyParams params) {
		String jsonString = Serializer.serialize(params);
		String response = httpCommunicator.doPost("/moves/Monopoly", jsonString);
		if(response != null) {
			return Serializer.deserializeClientModel(response);
		}
		else {
			return null;
		}
	}

	/**
	 * @Pre none
	 * @Post current player gains a victory point
	 *
	 */
	@Override
	public ClientModel playMonument(PlayMonumentParams params) {
		String jsonString = Serializer.serialize(params);
		String response = httpCommunicator.doPost("/moves/Monument", jsonString);
		if(response != null) {
			return Serializer.deserializeClientModel(response);
		}
		else {
			return null;
		}
	}

}
