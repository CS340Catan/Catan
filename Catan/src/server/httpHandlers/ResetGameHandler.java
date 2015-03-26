package server.httpHandlers;

import java.io.IOException;

import server.facade.ServerFacade;
import shared.utils.ServerResponseException;

import com.sun.net.httpserver.HttpExchange;

public class ResetGameHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try {
			int gameID = HandlerUtil.getGameID(exchange);
			ServerFacade.getSingleton().setGameID(gameID);
			ServerFacade.getSingleton().resetGame();
			HandlerUtil.sendResponse(exchange, 200, "Success", String.class);
		} catch (ServerResponseException e) {
			HandlerUtil.sendResponse(exchange, 400, "Failed to Reset " + e.getMessage(), String.class);
			e.printStackTrace();
		}
		catch (Exception e){
			HandlerUtil.sendResponse(exchange, 400, "You did something bad and you should feel bad", String.class);
			e.printStackTrace();
		}
		
	}

}
