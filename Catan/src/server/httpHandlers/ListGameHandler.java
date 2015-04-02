package server.httpHandlers;

import java.io.IOException;

import server.facade.FacadeSwitch;
import shared.communication.GameSummary;
import shared.utils.ServerResponseException;

import com.sun.net.httpserver.HttpExchange;

public class ListGameHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try {
			GameSummary[] list = FacadeSwitch.getSingleton().getGameList();
			HandlerUtil.sendResponse(exchange, 200, list, GameSummary[].class);
		} catch (ServerResponseException e) {
			HandlerUtil.sendResponse(exchange, 400, "Failed to get game",
					String.class);
			e.printStackTrace();
		}
	}

}
