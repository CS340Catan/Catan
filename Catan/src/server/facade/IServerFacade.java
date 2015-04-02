package server.facade;

import java.util.HashMap;

import server.model.ServerModel;
import shared.utils.IServer;

public interface IServerFacade extends IServer {

	public int getGameID();

	public void setGameID(int gameID);

	public ServerModel getServerModel();

	public int getPlayerID();

	public void setPlayerID(int playerId);

	public HashMap<Integer, ServerModel> getModelMap();

	public void setFirstGame();

}
