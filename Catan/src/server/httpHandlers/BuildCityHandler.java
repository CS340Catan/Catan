package server.httpHandlers;

import java.io.IOException;

import server.facade.FacadeSwitch;
import shared.communication.BuildCityParams;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;
import client.model.ClientModel;

import com.sun.net.httpserver.HttpExchange;

public class BuildCityHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		int gameID = HandlerUtil.getGameID(exchange);
		int playerID = HandlerUtil.getPlayerID(exchange);

		if (gameID == -1) {
			HandlerUtil.sendResponse(exchange, 400, "No Game Cookie",
					String.class);
		} else if (playerID == -1) {
			HandlerUtil.sendResponse(exchange, 400, "No Player Cookie",
					String.class);
		} else {

			try {

				String inputString = HandlerUtil.requestBodyToString(exchange);
				BuildCityParams params = (BuildCityParams) Serializer
						.deserialize(inputString, BuildCityParams.class);

				FacadeSwitch.getSingleton().setGameID(gameID);
				FacadeSwitch.getSingleton().setPlayerID(playerID);
				ClientModel clientModel = FacadeSwitch.getSingleton()
						.buildCity(params);
				HandlerUtil.sendResponse(exchange, 200, clientModel,
						ClientModel.class);

			} catch (ServerResponseException e) {
				HandlerUtil.sendResponse(exchange, 400, "Failed to build city"
						+ e.getMessage(), String.class);
				e.printStackTrace();
			}
		}
	}

}
