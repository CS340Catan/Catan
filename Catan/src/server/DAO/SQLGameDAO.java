package server.DAO;

import java.util.List;

import server.model.ServerModel;

public class SQLGameDAO implements IGameDAO{

	@Override
	public List<ServerModel> getGames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean createGame(ServerModel newGame) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ServerModel getGame(int gameID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean saveGame(int gameID, ServerModel game) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ServerModel> queryGame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean clear() {
		// TODO Auto-generated method stub
		return false;
	}

}
