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
	 * login
	 * 
	 * @pre username not null
	 * @pre password not null
	 * @post a valid LoginResponse returned
	 */
	@Override
	public LoginResponse Login(UserCredentials credentials) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Prepares credentials to be sent over network, then sends them to server
	 * registration
	 * 
	 * @pre username not null
	 * @pre password not null
	 * @post a valid LoginResponse returned
	 */
	@Override
	public LoginResponse Register(UserCredentials credentials) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get a list of games from the mock server
	 * 
	 * @pre None
	 * @post Returns a hard coded GamesList obejct
	 */
	@Override
	public GamesList getGameList() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Creates a game in the mock server
	 * 
	 * @pre Valid CreateGameParams
	 * @post Returns a hard coded Game summary object
	 */
	@Override
	public GameSummary createGame(CreateGameParams params) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Player uses this to join a game
	 * 
	 * @pre The user has previously logged in to the server
	 * @pre The player may join the game because they are already in the game,
	 *      Or there is space in the game to add a new player
	 * @pre Valid JoinGameParams
	 * @post Returns true of the player was added
	 */
	@Override
	public boolean joinGame(JoinGameParams params) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Saves the current game state
	 * 
	 * @pre The specified game ID is valid
	 * @pre The specified file name is valid (i.e., not null or empty)
	 * @post If good input, returns a true and writes to a local file
	 * @post If bad input, will return false and doesn't write to file
	 */
	@Override
	public boolean saveGame(SaveParams params) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Loads a saved game
	 * 
	 * @pre A previously saved game file with the specified name exists in the
	 *      serverâ€™s saves/ directory.
	 * @post If the operation succeeds, the game in the specified file has been
	 *       loaded into the server and its state restored (including its ID).
	 * @post If bad params, throws and error.
	 */
	@Override
	public boolean loadGame(String fileName) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Gets the current Client Model associated with this game
	 * 
	 * @pre Player is logged in and part of the game
	 * @pre If specified, the version number is included as the â€œversionâ€�
	 *      query parameter in the request URL, and its value is a valid
	 *      integer.
	 * @post If the operation fails, an exception is thrown
	 */
	@Override
	public ClientModel getCurrentGame(int version) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sets the game back to starting state
	 * 
	 * @pre Correct params are sent
	 * @post If the operation succeeds, the gameâ€™s command history has been
	 *       cleared out
	 * @post If the operation succeeds, the gameâ€™s players have NOT been
	 *       cleared out
	 * @post If the operation succeeds, the body contains the gameâ€™s updated
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
	 * @pre The caller has previously logged in to the server and joined a game
	 * @post If the operation succeeds, returns a valid CommandList
	 */
	@Override
	public CommandList getCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Executes commands
	 * 
	 * @pre Valid CommandList object
	 * @post If the operation succeeds, the passed Â­in command list has been
	 *       applied to the game.
	 * @post If the operation succeeds, the body contains an updated client
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
	 * @pre none
	 * @post If the operation succeeds, returns a list of AI types
	 */
	@Override
	public String[] getAITypes() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Creates and adds an ai to the game
	 * 
	 * @pre There is space in the game for another player (i.e., the game is not
	 *      â€œfullâ€�).
	 * @pre The specified â€œAITypeâ€� is valid (i.e., one of the values
	 *      returned by the /game/listAI method).
	 * @post If the operation succeeds, a new AI player of the specified type
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
	 * @pre The caller specifies a valid logging level. Valid values include:
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
	 * @pre Player is logged in and part of the game
	 * @post The chat contains your message at the end
	 */
	@Override
	public ClientModel sendChat(String content) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Used to accept trades between player
	 * 
	 * @pre Player is logged in and part of the game
	 * @pre You have been offered a domestic trade
	 * @pre To accept the offered trade, you have the required resources
	 * @post If you accepted, you and the player who offered swap the specified
	 *       resources
	 * @post If you declined no resources are exchanged
	 * @post The trade offer is removed
	 */
	@Override
	public ClientModel acceptTrade(boolean willAccept) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Discards a playerâ€™s cards
	 * 
	 * @pre Player is logged in and part of the game
	 * @pre The status of the client model is 'Discarding'
	 * @pre You have over 7 cards
	 * @pre You have the cards you're choosing to discard.
	 * @post You gave up the specified resources
	 * @post If you're the last one to discard, the client model status changes
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
	 * @pre Player is logged in and part of the game
	 * @pre It is the player's turn
	 * @pre The client modelâ€™s status is â€˜Rollingâ€™
	 * @post The client modelâ€™s status is now in â€˜Discardingâ€™ or
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
	 * @pre Player is logged in and part of the game
	 * @pre It is the player's turn
	 * @pre The road location is open
	 * @pre The road location is connected to another road owned by the player
	 * @pre The road location is not on water
	 * @pre You have the required resources (1 wood, 1 brickÍ¾ 1 road)
	 * @pre Setup round: Must be placed by settlement owned by the player with
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
	 * @pre Player is logged in and part of the game
	 * @pre It is the player's turn
	 * @pre The settlement location is openâ–  The settlement location is not on
	 *      water
	 * @pre The settlement location is connected to one of your roads except
	 *      during setup
	 * @pre You have the required resources (1 wood, 1 brick, 1 wheat, 1 sheepÍ¾
	 *      1 settlement)
	 * @pre The settlement cannot be placed adjacent to another settlement
	 * @post You lost the resources required to build a settlement (1 wood, 1
	 *       brick, 1 wheat, 1 sheepÍ¾ 1 settlement)
	 * @post The settlement is on the map at the specified location
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
	 * @pre Player is logged in and part of the game
	 * @pre It is the player's turn
	 * @pre The city location is where you currently have a settlement
	 * @pre You have the required resources (2 wheat, 3 oreÍ¾ 1 city)
	 * @post You lost the resources required to build a city (2 wheat, 3 oreÍ¾ 1
	 *       city)
	 * @post The city is on the map at the specified location
	 * @post You got a settlement back
	 */
	@Override
	public ClientModel buildCity(VertexLocation vertexLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Player uses this function to offer a trade to another player
	 * 
	 * @pre Player is logged in and part of the game
	 * @pre It is the player's turn
	 * @pre You have the resources you are offering
	 * @post The trade is offered to the other player (stored in the server
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
	 * @pre Player is logged in and part of the game
	 * @pre It is the player's turn
	 * @pre You have the resources you are giving
	 * @pre For ratios less than 4, you have the correct port for the trade
	 * @post The trade has been executed (the offered resources are in the bank,
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
	 * @pre Player is logged in and part of the game
	 * @pre It is the player's turn
	 * @pre The robber is not being kept in the same location
	 * @pre If a player is being robbed (i.e., victimIndex != Â­1), the player
	 *      being robbed has resource cards
	 * @post The robber is in the new location
	 * @post The player being robbed (if any) gave you one of his resource cards
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
	 * @pre Player is logged in and part of the game
	 * @pre It is the player's turn
	 * @post The cards in your new dev card hand have been transferred to your
	 *       old dev card hand
	 * @post It is the next playerâ€™s turn
	 */
	@Override
	public ClientModel finishTurn() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Player uses this to buy a development card
	 * 
	 * @pre Player is logged in and part of the game
	 * @pre It is the player's turn
	 * @pre You have the required resources (1 ore, 1 wheat, 1 sheep)
	 * @pre There are dev cards left in the deck
	 * @post You have a new card
	 * @post If it is a monument card, it has been added to your old devcard
	 *       hand
	 * @post If it is a nonÂ­monument card, it has been added to your new
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
	 * @pre The robber is not being kept in the same location
	 * @pre If a player is being robbed (i.e., victimIndex != Â­1), the player
	 *      being robbed has resource cards
	 * @post The robber is in the new location
	 * @post The player being robbed (if any) gave you one of his resource cards
	 *       (randomly selected)
	 * @post If applicable, â€œlargest armyâ€� has been awarded to the player
	 *       who has played the most soldier cards
	 * @post You are not allowed to play other development cards during this
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
	 * @pre Player is logged in and part of the game
	 * @pre It is the player's turn
	 * @pre The two specified resources are in the bank
	 * @post You gained the two specified resources
	 */
	@Override
	public ClientModel playYearOfPlentyCard(String resource1, String resource2) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * A player will place two roads on the map
	 * 
	 * @pre The first road location (spot1) is connected to one of your roads.
	 * @pre The second road location (spot2) is connected to one of your roads
	 *      or to the first road location (spot1)
	 * @pre Neither road location is on water
	 * @pre You have at least two unused roads
	 * @post You have two fewer unused roads
	 * @post Two new roads appear on the map at the specified locations
	 * @post If applicable, â€œlongest roadâ€� has been awarded to the player
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
	 * @pre Player is logged in and part of the game
	 * @pre It is the player's turn
	 * @post All of the other players have given you all of their resource cards
	 *       of the specified type
	 */
	@Override
	public ClientModel playMonopolyCard(String resource) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Plays a monument car, which gives the player who played a victory point
	 * 
	 * @pre Player is logged in and part of the game
	 * @pre It is the player's turn
	 * @pre Player has enough monument cards to win the game (i.e., reach 10
	 *      victory points)
	 * @post Player gains a victory point
	 */
	@Override
	public ClientModel playMonument() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Polls the server for an updated version of the model
	 * 
	 * @pre none. Is called every second to check for updated model version on
	 *      the server
	 * @post gets an updated version of the model from the server
	 */
	@Override
	public ClientModel poll() {
		return null;
		// TODO Auto-generated method stub

	}

}
