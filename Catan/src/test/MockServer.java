package test;

import client.model.ClientModel;
import client.model.ResourceList;
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

public class MockServer implements IServer{

	@Override
	public LoginResponse Login(UserCredentials credentials) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoginResponse Register(UserCredentials credentials) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GamesList getGameList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameSummary createGame(CreateGameParams params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean joinGame(JoinGameParams params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveGame(SaveParams params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean loadGame(String fileName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ClientModel getCurrentGame(int version) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel resetGame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getCommands() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel setCommands(String[] commands) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getAITypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addAI(String AIType) {
		// TODO Auto-generated method stub
		return false;
	}

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

}
