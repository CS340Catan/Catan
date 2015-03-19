package server.httpHandlers;

import java.io.IOException;

import server.facade.ServerFacade;
import shared.communication.PlayMonumentParams;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;
import client.model.ClientModel;

import com.sun.net.httpserver.HttpExchange;

public class PlayMonumentCardHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String inputStreamString = HandlerUtil.requestBodyToString(exchange);
		int gameID = HandlerUtil.getGameID(exchange);

		// if gameID is -1, there is no cookie so send back an error message
		if (gameID == -1) {
			HandlerUtil.sendResponse(exchange, 400, "No Game Cookie",
					String.class);
		} else {
			PlayMonumentParams playMonumentParam = (PlayMonumentParams) Serializer
					.deserialize(inputStreamString, PlayMonumentParams.class);
			try {
				ClientModel model = ServerFacade.getSingleton().playMonument(
						playMonumentParam);

				HandlerUtil.sendResponse(exchange, 200, model,
						ClientModel.class);
			} catch (ServerResponseException e) {
				HandlerUtil.sendResponse(exchange, 400,
						"Failed to play monument card.", String.class);
				e.printStackTrace();
			}
		}
	}

}
