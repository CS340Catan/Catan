package client.communication;

public class ClientCommunicator {
	
	public ClientCommunicator() {
		httpCommunicator = new HttpCommunicator();
	}
	
	private HttpCommunicator httpCommunicator;
	
	public LoginResponse Login(UserCredentials credentials) {
		LoginResponse loginResponse = new LoginResponse();
		return loginResponse;
	}
	public LoginResponse Register(UserCredentials credentials) {
		LoginResponse loginResponse = new LoginResponse();
		return loginResponse;
	}
	public currentGames getGameList() {
		GameList currentGames = new GameList();
		return currentGames;
	}
	public GameSummary createGame(createGameParams params) {
		GameSummary newGame = new GameSummary();
		return newGame;
	}
	public boolean joinGame(joinGameParams params) {
		boolean success = true;
		return success;
	}
	public boolean saveGame(SaveParams params) {
		boolean success = true;
		return success;
	}
	public boolean loadGame(String fileName) {
		boolean success = true;
		return success;
	}
	public ClientModel getCurrentGame(int version) {
		ClientModel model = new ClientModel();
		return model;
	}
	public ClientModel resetGame() {
		ClientModel model = new ClientModel();
		return model;
	}
	public String[] getCommands() {
		String[] commands;
		return commands;
	}
	public ClientModel setCommands([String]) {
		ClientModel model = new ClientModel();
		return model;
	}
	public String[] getAITypes() {
		String[] AITypes;
		return AITypes;
	}
	public boolean addAI(String AIType) {
		boolean success = true;
		return success;
	}
	public boolean changeLogLevel(LogLevel level) {
		boolean success = true;
		return success;
	}

	//----MOVE APIs--------
	public sendChat(String)
	public acceptTrade(boolean)
	public discardCards(ResourceList)
	public rollNumber(integer)
	public buildRoad(boolean, EdgeLocation)
	public buildSettlement(boolean, VertexLocation)
	public buildCity(VertexLocation)
	public offerTrade(ResourceHand, playerIndex)
	public maritimeTrade(integer, Resource, Resource)
	public robPlayer(HexLocation, playerIndex)
	public finishTurn()
	public buyDevCard()
	public playSoldierCard(HexLocation, playerIndex)
	public playYearOfPlentyCard(Resource, Resource)
	public playRoadBuildingCard(EdgeLocation, EdgeLocation)
	public playMonopolyCard(Resource)
	public playMonument()
	
}
