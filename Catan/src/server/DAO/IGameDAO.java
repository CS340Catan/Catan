package server.DAO;

import java.util.List;

import server.commands.ICommand;
import server.model.ServerModel;

public interface IGameDAO {
	public List<ServerModel> getGames();
	
	public boolean createGame(ServerModel newGame);
	
	public ServerModel getGame(int gameID);
	
	public boolean saveGame(int gameID);
	
	public List<ServerModel> queryGame();
	
	public boolean storeCommand(List<ICommand> commands);
	
	public boolean clear();
}
