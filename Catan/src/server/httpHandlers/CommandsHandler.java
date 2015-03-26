package server.httpHandlers;

import java.io.IOException;

import server.facade.FacadeSwitch;
import shared.communication.CommandList;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;
import client.model.ClientModel;

import com.sun.net.httpserver.HttpExchange;

public class CommandsHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		int gameID = HandlerUtil.getGameID(exchange);

		/*
		 * If gameID is -1, there is no cookie so send back an error message.
		 * Else, perform appropriate operations using the given gameID.
		 */
		if (gameID == -1) {
			HandlerUtil.sendResponse(exchange, 400, "No Game Cookie", String.class);
		} else {
			try {
				/*
				 * Grab the request method and set the game id for the server
				 * facade. If the request method is a GET, then operate a
				 * getCommands operation on the FacadeSwitch. If the request
				 * method is a POST, then operate a setCommands operation on the
				 * FacadeSwitch.
				 */
				String requestMethod = exchange.getRequestMethod();
				FacadeSwitch.getSingleton().setGameID(gameID);

				if (requestMethod.equals("GET")) {
					CommandList commandList = FacadeSwitch.getSingleton().getCommands();
					HandlerUtil.sendResponse(exchange, 200, commandList, CommandList.class);
				} else if (requestMethod.equals("POST")) {
					String inputStreamString = HandlerUtil.requestBodyToString(exchange);
					CommandList commandListParam = (CommandList) Serializer.deserialize(inputStreamString, CommandList.class);

					ClientModel model = FacadeSwitch.getSingleton().setCommands(commandListParam);
					HandlerUtil.sendResponse(exchange, 200, model, ClientModel.class);
				} else {
					String errorMessage = "Invalid post/get request method.";
					HandlerUtil.sendResponse(exchange, 400, errorMessage, String.class);
				}
			} catch (ServerResponseException e) {
				HandlerUtil.sendResponse(exchange, 400, "Failed to operate Commands Handler.", String.class);
				e.printStackTrace();
			}

		}
	}

}
