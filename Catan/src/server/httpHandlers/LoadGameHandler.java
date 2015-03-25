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

		try {
			String inputStreamString = HandlerUtil.requestBodyToString(exchange);
			LoadGameParams loadParams = (LoadGameParams) Serializer.deserialize(inputStreamString, UserCredentials.class);
			
			ServerFacade.getSingleton().loadGame(loadParams);
			HandlerUtil.sendResponse(exchange, 200, "Success", String.class);
		} catch (ServerResponseException e) {
			HandlerUtil.sendResponse(exchange, 400, e.getMessage(), String.class);
			e.printStackTrace();
		}
	}

}
