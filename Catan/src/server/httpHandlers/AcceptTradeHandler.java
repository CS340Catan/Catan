package server.httpHandlers;

import java.io.IOException;

import server.facade.FacadeSwitch;
import shared.communication.AcceptTradeParams;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;
import client.model.ClientModel;

import com.sun.net.httpserver.HttpExchange;

public class AcceptTradeHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		

		int gameID = HandlerUtil.getGameID(exchange);

		// if gameID is -1, there is no cookie so send back an error message
		if (gameID == -1) {
			HandlerUtil.sendResponse(exchange, 400, "No Game Cookie",
					String.class);
		}

		else {

			try {
				
				String inputStreamString = HandlerUtil.requestBodyToString(exchange);
				// otherwise send acceptTrade params to server model
				AcceptTradeParams acceptTradeParams = (AcceptTradeParams) Serializer
						.deserialize(inputStreamString, AcceptTradeParams.class);
				
				FacadeSwitch.getSingleton().setGameID(gameID);
				ClientModel model = FacadeSwitch.getSingleton()
						.acceptTrade(acceptTradeParams);
				HandlerUtil.sendResponse(exchange, 200, model, ClientModel.class);
			} catch (ServerResponseException e) {
				HandlerUtil.sendResponse(exchange, 400,
						"Failed to accept trade." + e.getMessage(), String.class);
				e.printStackTrace();
			}

		}
	}
}
