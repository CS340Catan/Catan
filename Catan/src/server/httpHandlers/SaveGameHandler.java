package server.httpHandlers;

import java.io.IOException;

import server.facade.ServerFacade;
import shared.communication.SaveParams;
import shared.communication.UserCredentials;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

import com.sun.net.httpserver.HttpExchange;

public class SaveGameHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String inputStreamString = HandlerUtil.requestBodyToString(exchange);
		SaveParams saveParams = (SaveParams) Serializer.deserialize(inputStreamString, UserCredentials.class);
		try {
			ServerFacade.getSingleton().saveGame(saveParams);
			HandlerUtil.sendResponse(exchange, 200, "Success", String.class);
		} catch (ServerResponseException e) {
			HandlerUtil.sendResponse(exchange, 400, "Could not save game. Be sure you have given a valid file name.", String.class);
			e.printStackTrace();
		}
	}

}
