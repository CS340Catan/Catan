package server.httpHandlers;

import java.io.IOException;

import server.facade.ServerFacade;
import shared.communication.LoadGameParams;
import shared.communication.UserCredentials;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

import com.sun.net.httpserver.HttpExchange;

public class LoadGameHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String inputStreamString = HandlerUtil.requestBodyToString(exchange);
		LoadGameParams loadParams = (LoadGameParams) Serializer.deserialize(inputStreamString, UserCredentials.class);
		try {
			ServerFacade.getSingleton().loadGame(loadParams);
		} catch (ServerResponseException e) {
			HandlerUtil.sendResponse(exchange, 400, "Could not save game. Be sure you have given a valid file name.", String.class);
			e.printStackTrace();
		}
	}

}
