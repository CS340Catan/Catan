package server.httpHandlers;

import java.io.IOException;

import server.facade.FacadeSwitch;
import shared.communication.MaritimeTradeParams;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;
import client.model.ClientModel;

import com.sun.net.httpserver.HttpExchange;

public class MaritimeTradeHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {

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
				MaritimeTradeParams params = (MaritimeTradeParams) Serializer
						.deserialize(inputStreamString,
								MaritimeTradeParams.class);

				FacadeSwitch.getSingleton().setGameID(gameID);
				FacadeSwitch.getSingleton().setPlayerID(playerID);
				ClientModel clientModel = FacadeSwitch.getSingleton()
						.maritimeTrade(params);
				HandlerUtil.sendResponse(exchange, 200, clientModel,
						ClientModel.class);
			} catch (ServerResponseException e) {
				HandlerUtil.sendResponse(exchange, 400,
						"Failed to maritime trade", String.class);
				e.printStackTrace();
			}

		}
	}

}
