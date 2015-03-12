package server.facade;

import client.data.GameInfo;
import client.model.ClientModel;
import shared.communication.*;
import shared.utils.IServer;
import shared.utils.ServerResponseException;

public class ServerFacade implements IServer{

	@Override
	public boolean Login(UserCredentials credentials)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean Register(UserCredentials credentials)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GameSummary[] getGameList() throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameInfo createGame(CreateGameParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String joinGame(JoinGameParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String saveGame(SaveParams params) throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loadGame(LoadGameParams params)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClientModel getCurrentGame(int version)
			throws ServerResponseException {
		// TODO Auto-generated method stub
		return null;
	}

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
