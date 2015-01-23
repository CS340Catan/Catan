package test;

import client.model.ClientModel;
import client.model.ResourceList;
import shared.communication.CommandList;
import shared.communication.CreateGameParams;
import shared.communication.GameSummary;
import shared.communication.GamesList;
import shared.communication.JoinGameParams;
import shared.communication.LogLevels;
import shared.communication.LoginResponse;
import shared.communication.SaveParams;
import shared.communication.UserCredentials;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.utils.IServer;

public class MockServer implements IServer {

	/**
	 * Prepares credentials to be sent over network, then sends them to server
	 * login. The inputed credentials must matched the canned response.
	 * 
	 * @Pre Username not null.
	 * @Pre Password not null.
	 * @Post A valid LoginResponse returned.
	 */
	@Override
	public LoginResponse Login(UserCredentials credentials) {
		LoginResponse response = null;
		if (credentials.getUsername().getUsername() == "test"
				&& credentials.getPassword().getPasswordString() == "pass") {
			response = new LoginResponse(credentials.getUsername(),
					credentials.getPassword());
		}
		return response;
	}

	/**
	 * Prepares credentials to be sent over network, then sends them to server
	 * registration.
	 * 
	 * @Pre Username not null.
	 * @Pre Password not null.
	 * @Post A valid LoginResponse returned.
	 */
	@Override
	public LoginResponse Register(UserCredentials credentials) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get a list of games from the mock server.
	 * 
	 * @Pre None
	 * @Post Returns a hard coded GamesList obejct
	 */
	@Override
	public GamesList getGameList() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Creates a game in the mock server
	 * 
	 * @Pre Valid CreateGameParams
	 * @Post Returns a hard coded Game summary object
	 */
	@Override
	public GameSummary createGame(CreateGameParams params) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Player uses this to join a game
	 * 
	 * @Pre The user has previously logged in to the server
	 * @Pre The player may join the game because they are already in the game,
	 *      Or there is space in the game to add a new player
	 * @Pre Valid JoinGameParams
	 * @Post Returns true of the player was added
	 */
	@Override
	public boolean joinGame(JoinGameParams params) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Saves the current game state
	 * 
	 * @Pre The specified game ID is valid
	 * @Pre The specified file name is valid (i.e., not null or empty)
	 * @Post If good input, returns a true and writes to a local file
	 * @Post If bad input, will return false and doesn't write to file
	 */
	@Override
	public boolean saveGame(SaveParams params) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Loads a saved game
	 * 
	 * @Pre A previously saved game file with the specified name exists in the
	 *      serverâ€™s saves/ directory.
	 * @Post If the operation succeeds, the game in the specified file has been
	 *       loaded into the server and its state restored (including its ID).
	 * @Post If bad params, throws and error.
	 */
	@Override
	public boolean loadGame(String fileName) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Gets the current Client Model associated with this game
	 * 
	 * @Pre Player is logged in and part of the game
	 * @Pre If specified, the version number is included as the â€œversionâ€�
	 *      query parameter in the request URL, and its value is a valid
	 *      integer.
	 * @Post If the operation fails, an exception is thrown
	 */
	@Override
	public ClientModel getCurrentGame(int version) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sets the game back to starting state
	 * 
	 * @Pre Correct params are sent
	 * @Post If the operation succeeds, the gameâ€™s command history has been
	 *       cleared out
	 * @Post If the operation succeeds, the gameâ€™s players have NOT been
	 *       cleared out
	 * @Post If the operation succeeds, the body contains the gameâ€™s updated
	 *       client model JSON
	 */
	@Override
	public ClientModel resetGame() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets a list of used commands
	 * 
	 * @Pre The caller has previously logged in to the server and joined a game
	 * @Post If the operation succeeds, returns a valid CommandList
	 */
	@Override
	public CommandList getCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Executes commands
	 * 
	 * @Pre Valid CommandList object
	 * @Post If the operation succeeds, the passed Â­in command list has been
	 *       applied to the game.
	 * @Post If the operation succeeds, the body contains an updated client
	 *       model
	 */
	@Override
	public ClientModel setCommands(CommandList commands) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * returns a list of AI types
	 * 
	 * @Pre none
	 * @Post If the operation succeeds, returns a list of AI types
	 */
	@Override
	public String[] getAITypes() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Creates and adds an ai to the game
	 * 
	 * @Pre There is space in the game for another player (i.e., the game is not
	 *      â€œfullâ€�).
	 * @Pre The specified â€œAITypeâ€� is valid (i.e., one of the values
	 *      returned by the /game/listAI method).
	 * @Post If the operation succeeds, a new AI player of the specified type
	 *       has been added to the current game.
	 */
	@Override
	public boolean addAI(String AIType) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Updates the log level
	 * 
	 * @Pre The caller specifies a valid logging level. Valid values include:
	 *      SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST @ boolean
	 *      returned
	 */
	@Override
	public boolean changeLogLevel(LogLevels level) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * sends a chat message with player name added to the end
	 * 
	 * @Pre Player is logged in and part of the game
	 * @Post The chat contains your message at the end
	 */
	@Override
	public ClientModel sendChat(String content) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Used to accept trades between player
	 * 
	 * @Pre Player is logged in and part of the game
	 * @Pre You have been offered a domestic trade
	 * @Pre To accept the offered trade, you have the required resources
	 * @Post If you accepted, you and the player who offered swap the specified
	 *       resources
	 * @Post If you declined no resources are exchanged
	 * @Post The trade offer is removed
	 */
	@Override
	public ClientModel acceptTrade(boolean willAccept) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Discards a playerâ€™s cards
	 * 
	 * @Pre Player is logged in and part of the game
	 * @Pre The status of the client model is 'Discarding'
	 * @Pre You have over 7 cards
	 * @Pre You have the cards you're choosing to discard.
	 * @Post You gave up the specified resources
	 * @Post If you're the last one to discard, the client model status changes
	 *       to 'Robbing'
	 */
	@Override
	public ClientModel discardCards(ResourceList discardedCards) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Generates rolled number between 2-12
	 * 
	 * @Pre Player is logged in and part of the game
	 * @Pre It is the player's turn
	 * @Pre The client modelâ€™s status is â€˜Rollingâ€™
	 * @Post The client modelâ€™s status is now in â€˜Discardingâ€™ or
	 *       â€˜Robbingâ€™ or â€˜Playingâ€™
	 */
	@Override
	public ClientModel rollNumber(int number) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Builds a new road
	 * 
	 * @Pre Player is logged in and part of the game
	 * @Pre It is the player's turn
	 * @Pre The road location is open
	 * @Pre The road location is connected to another road owned by the player
	 * @Pre The road location is not on water
	 * @Pre You have the required resources (1 wood, 1 brickÍ¾ 1 road)
	 * @Pre Setup round: Must be placed by settlement owned by the player with
	 *      no adjacent road
	 */
	@Override
	public ClientModel buildRoad(boolean free, EdgeLocation roadLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Builds a settlement
	 * 
	 * @Pre Player is logged in and part of the game
	 * @Pre It is the player's turn
	 * @Pre The settlement location is openâ–  The settlement location is not on
	 *      water
	 * @Pre The settlement location is connected to one of your roads except
	 *      during setup
	 * @Pre You have the required resources (1 wood, 1 brick, 1 wheat, 1 sheepÍ¾
	 *      1 settlement)
	 * @Pre The settlement cannot be placed adjacent to another settlement
	 * @Post You lost the resources required to build a settlement (1 wood, 1
	 *       brick, 1 wheat, 1 sheepÍ¾ 1 settlement)
	 * @Post The settlement is on the map at the specified location
	 */
	@Override
	public ClientModel buildSettlement(boolean free,
			VertexLocation vertexLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Builds a city
	 * 
	 * @Pre Player is logged in and part of the game
	 * @Pre It is the player's turn
	 * @Pre The city location is where you currently have a settlement
	 * @Pre You have the required resources (2 wheat, 3 oreÍ¾ 1 city)
	 * @Post You lost the resources required to build a city (2 wheat, 3 oreÍ¾ 1
	 *       city)
	 * @Post The city is on the map at the specified location
	 * @Post You got a settlement back
	 */
	@Override
	public ClientModel buildCity(VertexLocation vertexLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Player uses this function to offer a trade to another player
	 * 
	 * @Pre Player is logged in and part of the game
	 * @Pre It is the player's turn
	 * @Pre You have the resources you are offering
	 * @Post The trade is offered to the other player (stored in the server
	 *       model)
	 */
	@Override
	public ClientModel offerTrade(ResourceList offer, int receiver) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Player makes a trade with bank using specified resources
	 * 
	 * @Pre Player is logged in and part of the game
	 * @Pre It is the player's turn
	 * @Pre You have the resources you are giving
	 * @Pre For ratios less than 4, you have the correct port for the trade
	 * @Post The trade has been executed (the offered resources are in the bank,
	 *       and the requested resource has been received)
	 */
	@Override
	public ClientModel maritimeTrade(int ratio, String inputResource,
			String outputResource) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Player uses this to move robber and steal a card
	 * 
	 * @Pre Player is logged in and part of the game
	 * @Pre It is the player's turn
	 * @Pre The robber is not being kept in the same location
	 * @Pre If a player is being robbed (i.e., victimIndex != Â­1), the player
	 *      being robbed has resource cards
	 * @Post The robber is in the new location
	 * @Post The player being robbed (if any) gave you one of his resource cards
	 *       (randomly selected)
	 */
	@Override
	public ClientModel robPlayer(HexLocation location, int victimIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This effectively ends a playerâ€™s turn and makes it the next playerâ€™s
	 * turn
	 * 
	 * @Pre Player is logged in and part of the game
	 * @Pre It is the player's turn
	 * @Post The cards in your new dev card hand have been transferred to your
	 *       old dev card hand
	 * @Post It is the next playerâ€™s turn
	 */
	@Override
	public ClientModel finishTurn() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Player uses this to buy a development card
	 * 
	 * @Pre Player is logged in and part of the game
	 * @Pre It is the player's turn
	 * @Pre You have the required resources (1 ore, 1 wheat, 1 sheep)
	 * @Pre There are dev cards left in the deck
	 * @Post You have a new card
	 * @Post If it is a monument card, it has been added to your old devcard
	 *       hand
	 * @Post If it is a nonÂ­monument card, it has been added to your new
	 *       devcard hand (unplayable this turn)
	 */
	@Override
	public ClientModel buyDevCard() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Moves the robber to a new palce and possibly steals someoneâ€™s resource
	 * card
	 * 
	 * @Pre The robber is not being kept in the same location
	 * @Pre If a player is being robbed (i.e., victimIndex != Â­1), the player
	 *      being robbed has resource cards
	 * @Post The robber is in the new location
	 * @Post The player being robbed (if any) gave you one of his resource cards
	 *       (randomly selected)
	 * @Post If applicable, â€œlargest armyâ€� has been awarded to the player
	 *       who has played the most soldier cards
	 * @Post You are not allowed to play other development cards during this
	 *       turn (except for monument cards, which may still be played)
	 */
	@Override
	public ClientModel playSoldierCard(HexLocation location, int victimIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Player plays a year of plenty card, gains two of a certain kind of
	 * resource
	 * 
	 * @Pre Player is logged in and part of the game
	 * @Pre It is the player's turn
	 * @Pre The two specified resources are in the bank
	 * @Post You gained the two specified resources
	 */
	@Override
	public ClientModel playYearOfPlentyCard(String resource1, String resource2) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * A player will place two roads on the map
	 * 
	 * @Pre The first road location (spot1) is connected to one of your roads.
	 * @Pre The second road location (spot2) is connected to one of your roads
	 *      or to the first road location (spot1)
	 * @Pre Neither road location is on water
	 * @Pre You have at least two unused roads
	 * @Post You have two fewer unused roads
	 * @Post Two new roads appear on the map at the specified locations
	 * @Post If applicable, â€œlongest roadâ€� has been awarded to the player
	 *       with the longest road
	 */
	@Override
	public ClientModel playRoadBuildingCard(EdgeLocation spot1,
			EdgeLocation spot2) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Plays a monoploy card, steals a resources from other players
	 * 
	 * @Pre Player is logged in and part of the game
	 * @Pre It is the player's turn
	 * @Post All of the other players have given you all of their resource cards
	 *       of the specified type
	 */
	@Override
	public ClientModel playMonopolyCard(String resource) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Plays a monument card, which gives the player who played a victory point
	 * 
	 * @Pre Player is logged in and part of the game
	 * @Pre It is the player's turn
	 * @Pre Player has enough monument cards to win the game (i.e., reach 10
	 *      victory points)
	 * @Post Player gains a victory point
	 */
	@Override
	public ClientModel playMonument() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Polls the server for an updated version of the model
	 * 
	 * @Pre none. Is called every second to check for updated model version on
	 *      the server
	 * @Post gets an updated version of the model from the server
	 */
	@Override
	public ClientModel poll() {
		return null;
		// TODO Auto-generated method stub

	}

}
