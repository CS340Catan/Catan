package shared.utils;

import shared.communication.*;
import shared.data.GameInfo;
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
	 * // * This method takes in UserCredentials, which contain a username and
	 * password, and determine if the inputed username and password is a valid
	 * combination. A LoginResponse will be returned back.
	 * 
	 * @Pre Username is not null.
	 * @Pre Password is not null.
	 * @Pre If the passed­in (username, password) pair is valid, 1. The server
	 *      returns an HTTP 200 success response with "Success" in the body. 2.
	 *      The HTTP response headers set the catan.user cookie to contain the
	 *      identity of the loggedÂ­in player.
	 * @Post If the passed-in credentials is valid, the server returns a success
	 *       response with a cookie.
	 * @param credentials
	 *            Information containing username and password.
	 * 
	 */
	public boolean login(UserCredentials credentials)
			throws ServerResponseException;

	/**
	 * This method will take UserCredentials and determine if the inputed
	 * username already exists. If not, a new user will be created using inputed
	 * data. A LoginResponse will be returned back.
	 * 
	 * @Pre username is not null.
	 * @Pre password is not null.
	 * @Pre The specified username is not already in use
	 * @Post If there is no existing user with the specified username, 1. A new
	 *       user account has been created with the specified username and
	 *       password. 2. The server returns an HTTP 200 success response with
	 *       "Success" in the body 3. The HTTP response headers set the
	 *       catan.user cookie to contain the identity of the logged­in player.
	 * @Post If there is already an existing user with the specified name, or
	 *       the operation fails for any other reason, 1. The server returns an
	 *       HTTP 400 error response, and the body contains an error message.
	 * @param credentials
	 *            Information containing username and password.
	 * 
	 */
	public boolean Register(UserCredentials credentials)
			throws ServerResponseException;

	/**
	 * This method will get a list containing the current list of games stored
	 * within the server.
	 * 
	 * @Pre none
	 * @Post If the operation succeeds, 1. The server returns an HTTP 200
	 *       success response. 2. The body contains a JSON array containing a
	 *       list of objects that contain information about the server's games.
	 * @Post If the operation fails, 1. The server returns an HTTP 400 error
	 *       response, and the body contains an error message.
	 * 
	 */
	public GameSummary[] getGameList() throws ServerResponseException;

	/**
	 * This method will create a game using the inputed game parameters within
	 * the server. Following this creation, a game summary will be returned to
	 * the client.
	 * 
	 * @Pre name is not null
	 * @Pre randomTiles, randomNumbers, and randomPorts contain valid boolean
	 *      values.
	 * @Post If the operation succeeds, 1. A new game with the specified
	 *       properties has been created 2. The server returns an HTTP 200
	 *       success response. 3. The body contains a JSON object describing the
	 *       newly created game.
	 * @param params
	 * 
	 */
	public GameInfo createGame(CreateGameParams params)
			throws ServerResponseException;

	/**
	 * This method will add a user to a game that requires a player. The client
	 * will be returned a boolean stating whether they have been successfully
	 * added or rejected from the game.
	 * 
	 * @Pre 1. The user has previously logged in to the server (i.e., they have
	 *      a valid catan.user HTTP cookie). 2. The player may join the game
	 *      because 2.a They are already in the game, OR 2.b There is space in
	 *      the game to add a new player 3. The specified game ID is valid 4.
	 *      The specified color is valid (red, green, blue, yellow, puce, brown,
	 *      white, purple, orange).
	 * @Post If the operation succeeds, 1. The server returns an HTTP 200
	 *       success response with â€œSuccessâ€� in the body. 2. The
	 *       player is in the game with the specified color (i.e. calls to
	 *       /games/list method will show the player in the game with the chosen
	 *       color). 3. The server response includes the Set cookie response
	 *       header setting the catan.game HTTP cookie.
	 * @Post If the operation fails,1. The server returns an HTTP 400 error
	 *       response, and the body contains an error message.
	 * 
	 * @param params
	 * 
	 */
	public String joinGame(JoinGameParams params)
			throws ServerResponseException;

	/**
	 * This method will save the game parameters to the server. The client will
	 * be returned a boolean if the game was successfully saved. Prepares params
	 * to be sent over network, then sends them to server to save a game
	 * 
	 * @Pre Game id is valid.
	 * @Pre Filename is not null or empty.
	 * @Post A valid boolean returned.
	 */
	public String saveGame(SaveParams params) throws ServerResponseException;

	/**
	 * This method will take a filename and try and load the game matching the
	 * filename input. A boolean will be returned to the client stating whether
	 * the game was loaded.
	 * 
	 * @Pre A saved game with the specified filename exists on the server.
	 * @Post A valid boolean returned.
	 */
	public String loadGame(LoadGameParams params)
			throws ServerResponseException;

	/**
	 * Prepares the version number to be sent to the server, then retrieves
	 * current game from the server if it's different than the current version.
	 * 
	 * @Pre User has logged on and joined a game, and therefore has cookies.
	 * @Pre Version is a valid integer.
	 * @Post A valid ClientModel returned.
	 */
	public ClientModel getCurrentGame(int version)
			throws ServerResponseException;

	/**
	 * This method resets the game within the server.
	 * 
	 * @Pre none
	 * @Post A valid ClientModel returned.
	 */
	public ClientModel resetGame() throws ServerResponseException;

	/**
	 * Retrieves all the past commands in the current game from the server.
	 * 
	 * @Pre none
	 * @Post A valid set of commands returned.
	 */
	public CommandList getCommands() throws ServerResponseException;

	/**
	 * Prepares commands to be sent to the server, then sends them to server to
	 * apply to current game.
	 * 
	 * @Pre User has logged on and joined a game, and therefore has cookies.
	 * @Post A valid ClientModel returned.
	 */
	public ClientModel setCommands(CommandList commands)
			throws ServerResponseException;

	/**
	 * Retrieves a list from the server of the different types of AI players
	 * available available.
	 * 
	 * @Pre none
	 * @Post A valid list of AI types returned.
	 */
	public String[] getAITypes() throws ServerResponseException;

	/**
	 * Prepares the AIType to the server, then sends it to server to create a
	 * new AI player.
	 * 
	 * @Pre User has logged on and joined a game, and therefore has cookies.
	 * @Pre There is space in the game for another player.
	 * @Pre The AIType is a valid type returned by the getAITypes method.
	 * @Post A valid boolean returned.
	 */
	public AddAIResponse addAI(AddAIParams params)
			throws ServerResponseException;

	/**
	 * Prepares the log level to the server, then sends it to server to change
	 * the granularity of the log it keeps.
	 * 
	 * @Pre Level is a valid LogLevel (SEVERE, WARNING, INFO, CONFIG, FINE,
	 *      FINER, FINEST).
	 * @Post A valid boolean returned.
	 */
	public ChangeLogLevelResponse changeLogLevel(ChangeLogLevelParams params)
			throws ServerResponseException;

	/**
	 * This method will send a signal to the server checking to see if the
	 * current version of the model has been changed.
	 * 
	 * @Pre none
	 * @Post Gets an updated version of the model from the server.
	 */
	public ClientModel updateModel(int versionNumber)
			throws ServerResponseException;

	// ----MOVE APIs--------
	/**
	 * This method sends a chat message to the server. A new ClientModel is
	 * returned to the client.
	 * 
	 * @Pre none
	 * @Post chat contains the player's message at the end
	 * @param content
	 * 
	 */
	public ClientModel sendChat(ChatMessage chatMessage) throws ServerResponseException;

	/**
	 * This method will send a response to the server for a trade offer given to
	 * the client. A new ClientModel will be returned.
	 * 
	 * @Pre Current player has been offered a domestic trade
	 * @Pre To accept the offered trade, current player has the required
	 *      resources
	 * @Post If the current player accepted, the current player and the player
	 *       who offered swap the specified resources
	 * @Post If the current player declined no resources are exchanged
	 * @Post The trade offer is removed
	 * @param willAccept
	 */
	public ClientModel acceptTrade(AcceptTradeParams params)
			throws ServerResponseException;

	/**
	 * This method will discard a set of cards from the client's hand.
	 * 
	 * @Pre The status of the client model is 'Discarding'
	 * @Pre The current player has over 7 cards
	 * @Pre The current player has the cards the current player is choosing to
	 *      discard.
	 * @Post Postconditions
	 * @Post The current player gives up the specified resources
	 * @Post If the current player is the last one to discard, the client model
	 *       status changes to 'Robbing'
	 * @param discardedCards
	 * 
	 */
	public ClientModel discardCards(DiscardCardsParams params)
			throws ServerResponseException;

	/**
	 * This method will roll the dice for the client if current turn. Following
	 * the rolling of the dice, the cient's model will be updated.
	 * 
	 * @Pre It is the current player's turn
	 * @Pre The client model's status is "Rolling"
	 * @Post The client model's status is now in "Discarding" or "Robbing" or
	 *       "Playing"
	 * @param number
	 * 
	 */
	public ClientModel rollNumber(int number) throws ServerResponseException;

	/**
	 * This method will build a road for the client. A new ClientModel will be
	 * returned to the client.
	 * 
	 * @Pre The road location is open
	 * @Pre The road location is connected to another road owned by the player
	 * @Pre The road location is not on water
	 * @Pre The current player has the required resources (1 wood, 1 brick or 1
	 *      road)
	 * @Pre Setup round: Must be placed by settlement owned by the player with
	 *      no adjacent road
	 * @Post The current player lost the resources required to build a road (1
	 *       wood, 1 brick and 1 road)
	 * @Post The road is on the map at the specified location
	 * @Post If applicable, 'longest road' has been awarded to the player with
	 *       the longest road
	 * @param free
	 * @param roadLocation
	 * 
	 */
	public ClientModel buildRoad(BuildRoadParams params)
			throws ServerResponseException;

	/**
	 * This method will build a settlement for the client. A new ClientModel
	 * will be returned to the client.
	 * 
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
	 * 
	 */
	public ClientModel buildSettlement(BuildSettlementParams params)
			throws ServerResponseException;

	/**
	 * This method will build a city for the client. A new ClientModel will be
	 * returned to the client.
	 * 
	 * updates model, builds a city in specified location
	 * 
	 * @Pre the city location is where you currently have a settlement
	 * @Pre you have the required resources (2 wheat, 3 ore; 1 city)
	 * @Post you lost the resources required to build a city (2 wheat, 3 ore; 1
	 *       city)
	 * @Post the city is on the map at the specified location
	 * @Post you got a settlement back
	 */
	public ClientModel buildCity(BuildCityParams params)
			throws ServerResponseException;

	/**
	 * This method will offer a trade with another player. A new ClientModel
	 * will be returned to the client.
	 * 
	 * displays to other player a trade offer
	 * 
	 * @Pre you have the resources you are offering
	 * @Post the trade is offered to the other player (stored in the server
	 *       model)
	 */
	public ClientModel offerTrade(TradeOfferParams params)
			throws ServerResponseException;

	/**
	 * This method will complete trade with a maritime port. A new ClientModel
	 * will be returned to the client.
	 * 
	 * trades resource cards according to a certain ratio
	 * 
	 * @Pre you have the resources you are giving
	 * @Pre for ratios less than 4, you have the correct port for the trade
	 * @Post trade has been executed (offered resources are in the bank, and the
	 *       requested resource has been received)
	 */
	public ClientModel maritimeTrade(MaritimeTradeParams params)
			throws ServerResponseException;

	/**
	 * This method will move the robber and steal from a specific victim. A new
	 * ClientModel will be returned to the client.
	 * 
	 * @Pre robber is not being kept in the same location
	 * @Pre if a player is being robbed, the player being robbed has resource
	 *      cards
	 * @Post robber is in the new location
	 * @Post player being robbed (if any) gave you one of his resource cards
	 *       (randomly selected)
	 */
	public ClientModel robPlayer(MoveRobberParams params)
			throws ServerResponseException;

	/**
	 * This method will end the client's turn and start a new players turn. A
	 * new ClientModel will be returned to the client.
	 * 
	 * @Pre none
	 * @Post cards in new dev card hand have been transferred to old dev card
	 * @Post it is the next player's turn
	 */
	public ClientModel finishTurn(UserActionParams params)
			throws ServerResponseException;

	/**
	 * This method removes resource cards from player's in return for a
	 * development card. A new ClientModel will be returned to the client.
	 * 
	 * @Pre player has the required resources
	 * @Pre there is a development card left in the bank
	 * @Post player has a new development card
	 * @Post if it is monument card, add it to old devcard hand else, add it to
	 *       new devcard hand
	 */
	public ClientModel buyDevCard(UserActionParams params)
			throws ServerResponseException;

	/**
	 * This method will play a soldier card from a player's hand. A new
	 * ClientModel will be returned to the client.
	 * 
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
	public ClientModel playSoldierCard(MoveSoldierParams params)
			throws ServerResponseException;

	/**
	 * This method will play a year of plenty card from a player's hand. A new
	 * ClientModel will be returned to the client.
	 * 
	 * @Pre player has the specific card they want to play in their
	 *      "old dev card hand"
	 * @Pre player hasn't played a dev card this turn
	 * @Pre It's the player's turn
	 * @Pre The current model status is "playing"
	 * @Pre The two resources you specify are in the bank
	 * @Post Player gains the two resources specified
	 */
	public ClientModel playYearOfPlentyCard(YearOfPlentyParams params)
			throws ServerResponseException;

	/**
	 * This method will play a road building card from a player's hand. A new
	 * ClientModel will be returned to the client.
	 * 
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
	public ClientModel playRoadBuildingCard(BuildRoadCardParams params)
			throws ServerResponseException;

	/**
	 * This method plays a monopoly card from the player's hand. A new
	 * ClientModel will be returned to the client.
	 * 
	 * @Pre player has the specific card they want to play in their
	 *      "old dev card hand"
	 * @Pre player hasn't played a dev card this turn
	 * @Pre It's the player's turn
	 * @Pre The current model status is "playing"
	 * @Post All other players lose the resource card type chosen
	 * @Post The player of the card gets an equal number of that resource type
	 */
	public ClientModel playMonopolyCard(PlayMonopolyParams params)
			throws ServerResponseException;

	/**
	 * This method plays a monument card from the player's hand. A new
	 * ClientModel will be returned to the client.
	 * 
	 * @Pre none
	 * @Post current player gains a victory point
	 *
	 */
	public ClientModel playMonument(PlayMonumentParams params)
			throws ServerResponseException;
}
