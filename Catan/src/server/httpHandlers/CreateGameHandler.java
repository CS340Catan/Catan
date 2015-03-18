package server.httpHandlers;

import java.io.IOException;

import server.facade.ServerFacade;
import shared.communication.CreateGameParams;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

import com.sun.net.httpserver.HttpExchange;

public class CreateGameHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String inputStreamString = HandlerUtil.requestBodyToString(exchange);
		CreateGameParams params = (CreateGameParams) Serializer.deserialize(inputStreamString, CreateGameParams.class);
		
		try {
			ServerFacade.getSingleton().createGame(params);
			HandlerUtil.sendResponse(exchange, 200, "Success", String.class);			
		} catch (ServerResponseException e) {
			HandlerUtil.sendResponse(exchange, 400, "Failed to register - someone already has that username.", String.class);
			e.printStackTrace();
		}
		
	}

}
