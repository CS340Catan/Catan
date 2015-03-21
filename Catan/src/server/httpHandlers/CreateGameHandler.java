package server.httpHandlers;

import java.io.IOException;

import server.facade.ServerFacade;
import server.model.RegisteredPlayers;
import shared.communication.CreateGameParams;
import shared.model.Player;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;
import client.data.GameInfo;

import com.sun.net.httpserver.HttpExchange;

public class CreateGameHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String inputStreamString = HandlerUtil.requestBodyToString(exchange);
		CreateGameParams params = (CreateGameParams) Serializer.deserialize(inputStreamString, CreateGameParams.class);
		try {
			GameInfo gameInfo = ServerFacade.getSingleton().createGame(params);
			int playerId = HandlerUtil.getPlayerID(exchange);
			String playerName = RegisteredPlayers.getSingleton().getPlayerName(playerId);
			Player[] players = new Player[4];
			players[0]= new Player(0, playerId, 0, 0, playerName, "Red", false, 0, null, null, false, null, 0, 0, 0);
			players[1] = null;
			players[2] = null;
			players[3] = null;
			ServerFacade.getSingleton().getModelMap().get(gameInfo.getId()).setPlayers(players);
			HandlerUtil.sendResponse(exchange, 200, gameInfo, GameInfo.class);			
		} catch (ServerResponseException e) {
			HandlerUtil.sendResponse(exchange, 400, "Failed to register - someone already has that username.", String.class);
			e.printStackTrace();
		}
		
	}

}
