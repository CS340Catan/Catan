package server.DAO;

import java.util.List;

import server.commands.ICommand;
import server.model.ServerModel;

public class TextGameDAO implements IGameDAO{

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
	public boolean saveGame(int gameID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<ServerModel> queryGame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean storeCommand(List<ICommand> commands) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean clear() {
		// TODO Auto-generated method stub
		return false;
	}

}
