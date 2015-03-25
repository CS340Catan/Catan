package server.httpHandlers;

import java.io.IOException;

import server.facade.ServerFacade;
import server.model.RegisteredPlayers;
import shared.communication.CreateGameParams;
import shared.data.GameInfo;
import shared.model.Player;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

import com.sun.net.httpserver.HttpExchange;

public class CreateGameHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		try {
			String inputStreamString = HandlerUtil.requestBodyToString(exchange);
			CreateGameParams params = (CreateGameParams) Serializer.deserialize(inputStreamString, CreateGameParams.class);
			
			GameInfo gameInfo = ServerFacade.getSingleton().createGame(params);
			HandlerUtil.sendResponse(exchange, 200, gameInfo, GameInfo.class);			
		} catch (ServerResponseException e) {
			HandlerUtil.sendResponse(exchange, 400, "Failed to register - someone already has that username.", String.class);
			e.printStackTrace();
		}
		
	}

}
