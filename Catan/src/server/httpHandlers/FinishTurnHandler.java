package server.httpHandlers;

import java.io.IOException;

import server.facade.ServerFacade;
import shared.communication.UserActionParams;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;
import client.model.ClientModel;

import com.sun.net.httpserver.HttpExchange;

public class FinishTurnHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		String inputStreamString = HandlerUtil.requestBodyToString(exchange);
		
		int gameID = HandlerUtil.getGameID(exchange);
		int playerID = HandlerUtil.getPlayerID(exchange);
		
		//if gameID is -1, there is no cookie so send back an error message
		if (gameID == -1) {
			HandlerUtil.sendResponse(exchange, 400, "No Game Cookie", String.class);
		} 
		else {
			//otherwise send params to server model
			UserActionParams params = (UserActionParams) Serializer.deserialize(inputStreamString, UserActionParams.class);	
		
			try {
				ServerFacade.getSingleton().setGameID(gameID);
				ServerFacade.getSingleton().setPlayerID(playerID);
				ClientModel clientModel = ServerFacade.getSingleton().finishTurn(params);
				int i = 0;
				HandlerUtil.sendResponse(exchange, 200, clientModel, ClientModel.class);
			} catch (ServerResponseException e) {
				HandlerUtil.sendResponse(exchange, 400, "Failed to finish turn.", String.class);
				e.printStackTrace();
			}
			
		}
	}

}
