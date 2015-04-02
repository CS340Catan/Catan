package server.httpHandlers;

import java.io.IOException;
import java.util.logging.Logger;

import server.facade.FacadeSwitch;
import shared.communication.BuildRoadParams;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;
import client.model.ClientModel;

import com.sun.net.httpserver.HttpExchange;

public class BuildRoadHandler implements IHttpHandler {
	private static Logger logger;
	static {
		logger = Logger.getLogger("CatanServer");
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		logger.info("server/httpHandlers/BuildRoadHandler - entering Handle");

		int gameID = HandlerUtil.getGameID(exchange);
		int playerID = HandlerUtil.getPlayerID(exchange);

		// if gameID is -1, there is no cookie so send back an error message
		if (gameID == -1) {
			HandlerUtil.sendResponse(exchange, 400, "No Game Cookie",
					String.class);
		} else {

			try {

				String inputStreamString = HandlerUtil
						.requestBodyToString(exchange);
				// otherwise send params to server model
				BuildRoadParams params = (BuildRoadParams) Serializer
						.deserialize(inputStreamString, BuildRoadParams.class);

				FacadeSwitch.getSingleton().setGameID(gameID);
				FacadeSwitch.getSingleton().setPlayerID(playerID);
				ClientModel clientModel = FacadeSwitch.getSingleton()
						.buildRoad(params);
				HandlerUtil.sendResponse(exchange, 200, clientModel,
						ClientModel.class);
			} catch (ServerResponseException e) {
				HandlerUtil.sendResponse(exchange, 400, "Failed to build road"
						+ e.getMessage(), String.class);
				e.printStackTrace();
			}

		}
	}

}
