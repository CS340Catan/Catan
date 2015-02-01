package test;

import java.util.ArrayList;

import shared.communication.AddAIParams;
import shared.communication.AddAIResponse;
import shared.communication.BuildSettlementParams;
import shared.communication.ChangeLogLevelParams;
import shared.communication.ChangeLogLevelResponse;
import shared.communication.CommandList;
import shared.communication.CreateGameParams;
import shared.communication.GameSummary;
import shared.communication.GamesList;
import shared.communication.JoinGameParams;
import shared.communication.JoinResponse;
import shared.communication.ListAIResponse;
import shared.communication.LoadResponse;
import shared.communication.LoginResponse;
import shared.communication.PlayerSummary;
import shared.communication.SaveParams;
import shared.communication.SaveResponse;
import shared.communication.UserCredentials;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import shared.utils.IServer;
import client.model.ClientModel;
import client.model.Map;
import client.model.MessageLine;
import client.model.MessageList;
import client.model.Player;
import client.model.ResourceList;
import client.model.TradeOffer;
import client.model.TurnTracker;

/**
 * This class implements the IServer interface, such that it may be used as a
 * "dummy" server for testing purposes. The responses for the implemented
 * methods are canned responses. For example, Login(...) will return true iff
 * the inputed username is "test" and the inputed password is "pass".
 * 
 * @author Winston
 *
 */

public class MockServer implements IServer {

	ClientModel clientMockModel;

	/**
	 * Default constructor.
	 * 
	 * @param clientMockModel
	 *            Inputed ClientModel that will be the original ClientModel
	 *            which other methods may use in return statements.
	 */
	public MockServer(ClientModel clientMockModel) {
		this.clientMockModel = clientMockModel;
	}

	/**
	 * Prepares credentials to be sent over network, then sends them to server
	 * login. The inputed credentials must matched the expected canned response.
	 * 
	 * @Pre Username not null.
	 * @Pre Password not null.
	 * @Post A valid LoginResponse returned.
	 */
	@Override
	public LoginResponse Login(UserCredentials credentials) {
		LoginResponse response = null;
		if (credentials.getUsername() == "test"
				&& credentials.getPassword() == "pass") {
			response = new LoginResponse(credentials.getUsername(),
					credentials.getPassword());
		}
		return response;
	}

	/**
	 * Prepares credentials to be sent over network, then sends them to server
	 * registration. The inputed credentials should match the expected canned
	 * response.
	 * 
	 * @Pre Username not null.
	 * @Pre Password not null.
	 * @Post A valid LoginResponse returned.
	 */
	@Override
	public LoginResponse Register(UserCredentials credentials) {
		LoginResponse response = null;
		if (credentials.getUsername() != "test") {
			response = new LoginResponse(credentials.getUsername(),
					credentials.getPassword());
		}
		return response;
	}

	/**
	 * Get a list of games from the mock server. A canned list of games should
	 * be returned.
	 * 
	 * @Pre None
	 * @Post Returns a hard coded GamesList object.
	 */
	@Override
	public GamesList getGameList() {
		ArrayList<GameSummary> games = new ArrayList<GameSummary>();
		boolean success = false;
		String[] names = {"bill","ted","sheila","parker"};
		String[] colors = {"red","white","blue","green"};
		String[] gameNames = {"game1","game2"}; 
		for(int i = 0 ; i<2; ++i){
			PlayerSummary players[] = new PlayerSummary[4];
			for(int j = i; i<5; ++i){
				players[j] = new PlayerSummary(colors[j],names[j],j);
			}
			games.add(new GameSummary(gameNames[i],i,players));
		}
		return new GamesList(games,success);
	}

	/**
	 * Creates a game in the mock server. A canned game summary should be
	 * generated given the input parameters.
	 * 
	 * @Pre Valid CreateGameParams
	 * @Post Returns a canned GameSummary object.
	 */
	@Override
	public GameSummary createGame(CreateGameParams params) {
		PlayerSummary[] players = new PlayerSummary[4];
		for(int i = 0; i<5; i++){
			players[i] = new PlayerSummary();
		}
		return new GameSummary("game1",1,players);
	}

	/**
	 * The Client that calls this method to be added to a game. This should
	 * return true given that the client is not already part of a game.
	 * 
	 * @Pre The user has previously logged in to the server.
	 * @Pre The player may join the game because they are already in the game,
	 *      or there is space in the game to add a new player.
	 * @Pre Valid JoinGameParams
	 * @Post Returns true if the player was added.
	 */
	@Override
	public JoinResponse joinGame(JoinGameParams params) {
		if(params.getColor()=="green")//let's say green is already in
			return new JoinResponse("Invalid Request");
		return new JoinResponse("Success");
	}

	/**
	 * Saves the current game state. Should return true if the save parameters
	 * are valid.
	 * 
	 * @Pre The specified game ID is valid.
	 * @Pre The specified file name is valid (not null or empty).
	 * @Post If good input, returns a true and writes to a local file.
	 * @Post If bad input, will return false and doesn't write to file.
	 */
	@Override
	public SaveResponse saveGame(SaveParams params) {
		if(params.getId()==1 || params.getId()==0){
			if(params.getFileName().equals("game0.txt")||params.getFileName().equals("game1.txt")){
				return new SaveResponse("Success");
			}
		}
		return new SaveResponse("Invalid Request");
	}

	/**
	 * Loads a saved game. Should return true if the input string is valid.
	 * 
	 * @Pre A previously saved game file with the specified name exists in the
	 *      server's saves/directory.
	 * @Post If the operation succeeds, the game in the specified file has been
	 *       loaded into the server and its state restored (including its ID).
	 * @Post If bad parameters, throw an error.
	 */
	@Override
	public LoadResponse loadGame(String fileName) {
		if(fileName.equals("game0.txt"))
			return new LoadResponse("Success");
		return new LoadResponse("Invalid Request");
	}

	/**
	 * Gets the current Client Model associated with this game. Should be a
	 * canned ClientModel that is returned to the the client.
	 * 
	 * @Pre Player is logged in and part of the game
	 * @Pre If specified, the version number is included as the version query
	 *      parameter in the request URL, and its value is a valid integer.
	 * @Post If the operation fails, an exception is thrown
	 */
	@Override
	public ClientModel getCurrentGame(int version) {
		if(version<0)
			return null;
		return clientMockModel;
	}

	/**
	 * Sets the game back to starting state, which is a canned response (a
	 * different ClientModel that that given by getCurrentGame method).
	 * 
	 * @Pre Correct parameters are sent.
	 * @Post If the operation succeeds, the game's command history has been
	 *       cleared out.
	 * @Post If the operation succeeds, the game's players have NOT been cleared
	 *       out.
	 * @Post If the operation succeeds, the body contains the game's updated
	 *       client model JSON.
	 */
	@Override
	public ClientModel resetGame() {
		clientMockModel.setLog(new MessageList(new MessageLine[1]));
		return clientMockModel;
	}

	/**
	 * Gets a list of canned commands.
	 * 
	 * @Pre The caller has previously logged in to the server and joined a game.
	 * @Post If the operation succeeds, returns a valid CommandList.
	 */
	@Override
	public CommandList getCommands() {
		ArrayList<String> commandList = new ArrayList<String>();
		commandList.add("games/create");
		commandList.add("games/join");
		commandList.add("games/join");
		commandList.add("games/join");
		commandList.add("games/join");
		commandList.add("moves/buildSettlement");
		commandList.add("move/buildRoad");
		commandList.add("moves/buildRoad");
		commandList.add("moves/buildSettlement");
		
		return new CommandList(commandList);
	}

	/**
	 * Executes commands inputed by parameter, should return a ClientModel that
	 * matches the inputed commands.
	 * 
	 * @Pre Valid CommandList object.
	 * @Post If the operation succeeds, the passed­in command list has been
	 *       applied to the game.
	 * @Post If the operation succeeds, the body contains an updated client
	 *       model.
	 */
	@Override
	public ClientModel setCommands(CommandList commands) {
		int current = (clientMockModel.getTurnTracker().getCurrentTurn() +1)%4;
		clientMockModel.getTurnTracker().setCurrentTurn(current);
		return clientMockModel;
	}

	/**
	 * Returns a list of AI types, should be a canned response.
	 * 
	 * @Pre None
	 * @Post If the operation succeeds, returns a list of AI types.
	 */
	@Override
	public ListAIResponse getAITypes() {
		ArrayList<String> types = new ArrayList<String>();
		types.add("LARGEST ARMY");
		return new ListAIResponse(types);
	}

	/**
	 * Creates and adds an AI to the game and should return true given that the
	 * AI is a valid input parameter.
	 * 
	 * @Pre There is space in the game for another player (i.e., the game is not
	 *      full).
	 * @Pre The specified "AIType" is valid (i.e., one of the values returned by
	 *      the /game/listAI method).
	 * @Post If the operation succeeds, a new AI player of the specified type
	 *       has been added to the current game.
	 */
	@Override
	public AddAIResponse addAI(AddAIParams params) {
		if(params.getAIType()!="LARGEST ARMY")
			return null;
		return new AddAIResponse();
	}

	/**
	 * Updates the log level and returns true given that the input parameter is
	 * not null.
	 * 
	 * @Pre The caller specifies a valid logging level. Valid values include:
	 *      SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST @ boolean
	 *      returned
	 */
	@Override
	public ChangeLogLevelResponse changeLogLevel(ChangeLogLevelParams level) {
		return null;
	}

	/**
	 * sends a chat message with player name added to the end, should return a
	 * ClientModel with the updated chat string.
	 * 
	 * @Pre Player is logged in and part of the game.
	 * @Post The chat contains your message at the end.
	 */
	@Override
	public ClientModel sendChat(String content) {
		MessageLine newMessage = new MessageLine(content, "billy");
		MessageLine[] oldMessages = clientMockModel.getChat().getLines();
		int size = oldMessages.length+1;
		MessageLine[] newMessages = new MessageLine[size];
		System.arraycopy(oldMessages, 0, newMessages, 0, size-1);
		newMessages[size-1] = newMessage;
		MessageList newList = new MessageList(newMessages);
		clientMockModel.setChat(newList);
		
		return clientMockModel;
	}

	/**
	 * Used to accept trades between player, should return a ClientModel that
	 * takes into account the newly accepted trade.
	 * 
	 * @Pre Player is logged in and part of the game.
	 * @Pre You have been offered a domestic trade.
	 * @Pre To accept the offered trade, you have the required resources.
	 * @Post If you accepted, you and the player who offered swap the specified
	 *       resources.
	 * @Post If you declined no resources are exchanged.
	 * @Post The trade offer is removed.
	 */
	@Override
	public ClientModel acceptTrade(boolean willAccept) {
		if(willAccept==false)
			return clientMockModel;
		Player player0 = clientMockModel.getPlayers()[0];
		Player player1 = clientMockModel.getPlayers()[1];
		//Our canned trade will be 1 sheep for 2 grain
		ResourceList p0Res = player0.getResources();
		ResourceList p1Res = player1.getResources();
		int newWheat0 = p0Res.getWheat() +2;
		p0Res.setWheat(newWheat0);
		int newSheep0 = p0Res.getSheep() -1;
		p0Res.setSheep(newSheep0);
		int newWheat1 = p1Res.getWheat() -2;
		p1Res.setWheat(newWheat1);
		int newSheep1 = p1Res.getSheep() +1;
		p1Res.setSheep(newSheep1);
		clientMockModel.setTradeOffer(new TradeOffer(-1,-1,null));//what does an empty Trade offer look like
		return clientMockModel;	
	}

	/**
	 * Discards a player's cards. Should return an correctly updated
	 * ClientModel.
	 * 
	 * @Pre Player is logged in and part of the game.
	 * @Pre The status of the client model is 'Discarding'.
	 * @Pre You have over 7 cards.
	 * @Pre You have the cards you're choosing to discard.
	 * @Post You gave up the specified resources.
	 * @Post If you're the last one to discard, the client model status changes
	 *       to 'Robbing'.
	 */
	@Override
	public ClientModel discardCards(ResourceList discardedCards) {
		return clientMockModel;
	}

	/**
	 * Apply actions given a specific rollNumber of number. Should return an
	 * correctly updated ClientModel.
	 * 
	 * @Pre Player is logged in and part of the game
	 * @Pre It is the player's turn
	 * @Pre The client model's status is "Rolling"
	 * @Post The client model's status is now in "Discarding" or "Robbing" or
	 *       "Playing"
	 */
	@Override
	public ClientModel rollNumber(int number) {
		return clientMockModel;
	}

	/**
	 * Builds a new road. Should return an correctly updated ClientModel.
	 * 
	 * @Pre Player is logged in and part of the game.
	 * @Pre It is the player's turn.
	 * @Pre The road location is open.
	 * @Pre The road location is connected to another road owned by the player.
	 * @Pre The road location is not on water.
	 * @Pre You have the required resources (1 wood, 1 brick & 1 road).
	 * @Pre Setup round: Must be placed by settlement owned by the player with
	 *      no adjacent road.
	 */
	@Override
	public ClientModel buildRoad(boolean free, EdgeLocation roadLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Builds a settlement. Should return an correctly updated ClientModel.
	 * 
	 * @Pre Player is logged in and part of the game.
	 * @Pre It is the player's turn.
	 * @Pre The settlement location is open–  The settlement location is not on
	 *      water.
	 * @Pre The settlement location is connected to one of your roads except
	 *      during setup.
	 * @Pre You have the required resources (1 wood, 1 brick, 1 wheat, 1 sheep &
	 *      1 settlement).
	 * @Pre The settlement cannot be placed adjacent to another settlement
	 * @Post You lost the resources required to build a settlement (1 wood, 1
	 *       brick, 1 wheat, 1 sheep & 1 settlement).
	 * @Post The settlement is on the map at the specified location.
	 */
	@Override
	public ClientModel buildSettlement(BuildSettlementParams param){
		if(param.isFree())
			return clientMockModel;
//		VertexLocation[] cities = clientMockModel.getMap().getSettlements();
		return null;
	}

	/**
	 * Builds a city. Should return an correctly updated ClientModel.
	 * 
	 * @Pre Player is logged in and part of the game.
	 * @Pre It is the player's turn.
	 * @Pre The city location is where you currently have a settlement.
	 * @Pre You have the required resources (2 wheat, 3 ore & 1 city).
	 * @Post You lost the resources required to build a city (2 wheat, 3 ore & 1
	 *       city).
	 * @Post The city is on the map at the specified location.
	 * @Post You got a settlement back.
	 */
	@Override
	public ClientModel buildCity(VertexLocation vertexLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Player uses this function to offer a trade to another player. Should
	 * return an correctly updated ClientModel.
	 * 
	 * @Pre Player is logged in and part of the game.
	 * @Pre It is the player's turn.
	 * @Pre You have the resources you are offering.
	 * @Post The trade is offered to the other player (stored in the server
	 *       model).
	 */
	@Override
	public ClientModel offerTrade(ResourceList offer, int receiver) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Player makes a trade with bank using specified resources. Should return
	 * an correctly updated ClientModel.
	 * 
	 * @Pre Player is logged in and part of the game.
	 * @Pre It is the player's turn.
	 * @Pre You have the resources you are giving.
	 * @Pre For ratios less than 4, you have the correct port for the trade.
	 * @Post The trade has been executed (the offered resources are in the bank,
	 *       and the requested resource has been received).
	 */
	@Override
	public ClientModel maritimeTrade(int ratio, String inputResource, String outputResource) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Player uses this to move robber and steal a card. Should return an
	 * correctly updated ClientModel.
	 * 
	 * @Pre Player is logged in and part of the game.
	 * @Pre It is the player's turn.
	 * @Pre The robber is not being kept in the same location.
	 * @Pre If a player is being robbed (i.e., victimIndex != ­1), the player
	 *      being robbed has resource cards.
	 * @Post The robber is in the new location.
	 * @Post The player being robbed (if any) gave you one of his resource cards
	 *       (randomly selected).
	 */
	@Override
	public ClientModel robPlayer(HexLocation location, int victimIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This effectively ends a player's turn and makes it the next player's
	 * turn. Should return an correctly updated ClientModel.
	 * 
	 * @Pre Player is logged in and part of the game.
	 * @Pre It is the player's turn.
	 * @Post The cards in your new development card hand have been transferred
	 *       to your old development card hand.
	 * @Post It is the next player's turn.
	 */
	@Override
	public ClientModel finishTurn() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Player uses this to buy a development card. Should return an correctly
	 * updated ClientModel.
	 * 
	 * @Pre Player is logged in and part of the game.
	 * @Pre It is the player's turn.
	 * @Pre You have the required resources (1 ore, 1 wheat, 1 sheep).
	 * @Pre There are development cards left in the deck.
	 * @Post You have a new card.
	 * @Post If it is a monument card, it has been added to your old development
	 *       card hand.
	 * @Post If it is a non­monument card, it has been added to your new
	 *       development card hand (unplayable this turn).
	 */
	@Override
	public ClientModel buyDevCard() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Moves the robber to a new p;ace and possibly steals someone's resource
	 * card. Should return an correctly updated ClientModel.
	 * 
	 * @Pre The robber is not being kept in the same location.
	 * @Pre If a player is being robbed (i.e., victimIndex != ­1), the player
	 *      being robbed has resource cards.
	 * @Post The robber is in the new location.
	 * @Post The player being robbed (if any) gave you one of his resource cards
	 *       (randomly selected).
	 * @Post If applicable, "largest army" has been awarded to the player who
	 *       has played the most soldier cards.
	 * @Post You are not allowed to play other development cards during this
	 *       turn (except for monument cards, which may still be played).
	 */
	@Override
	public ClientModel playSoldierCard(HexLocation location, int victimIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Player plays a year of plenty card, gains two of a certain kind of
	 * resource. Should return an correctly updated ClientModel.
	 * 
	 * @Pre Player is logged in and part of the game.
	 * @Pre It is the player's turn.
	 * @Pre The two specified resources are in the bank.
	 * @Post You gained the two specified resources.
	 */
	@Override
	public ClientModel playYearOfPlentyCard(String resource1, String resource2) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * A player will place two roads on the map. Should return an correctly
	 * updated ClientModel.
	 * 
	 * @Pre The first road location (spot1) is connected to one of your roads.
	 * @Pre The second road location (spot2) is connected to one of your roads
	 *      or to the first road location (spot1).
	 * @Pre Neither road location is on water.
	 * @Pre You have at least two unused roads.
	 * @Post You have two fewer unused roads.
	 * @Post Two new roads appear on the map at the specified locations.
	 * @Post If applicable, "longest road" has been awarded to the player with
	 *       the longest road.
	 */
	@Override
	public ClientModel playRoadBuildingCard(EdgeLocation spot1,
			EdgeLocation spot2) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Plays a monopoly card, steals a resources from other players. Should
	 * return an correctly updated ClientModel.
	 * 
	 * @Pre Player is logged in and part of the game.
	 * @Pre It is the player's turn.
	 * @Post All of the other players have given you all of their resource cards
	 *       of the specified type.
	 */
	@Override
	public ClientModel playMonopolyCard(String resource) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Plays a monument card, which gives the player who played a victory point.
	 * Should return an correctly updated ClientModel.
	 * 
	 * @Pre Player is logged in and part of the game.
	 * @Pre It is the player's turn.
	 * @Pre Player has enough monument cards to win the game (i.e., reach 10
	 *      victory points).
	 * @Post Player gains a victory point.
	 */
	@Override
	public ClientModel playMonument() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Polls the server for an updated version of the model. Should return an
	 * correctly updated ClientModel.
	 * 
	 * @Pre None.
	 * @Post Gets an updated version of the model from the server. We will just change the turn tracker
	 */
	@Override
	public ClientModel updateModel() {
		int current = (clientMockModel.getTurnTracker().getCurrentTurn() +1)%4;
		clientMockModel.getTurnTracker().setCurrentTurn(current);
		return clientMockModel;
	}

}
