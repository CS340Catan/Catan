package server.httpHandlers;

import java.io.IOException;

import server.facade.ServerFacade;
import shared.communication.AcceptTradeParams;
import shared.communication.ChatMessage;
import shared.model.ClientModel;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

import com.sun.net.httpserver.HttpExchange;

public class AcceptTradeHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		String inputStreamString = HandlerUtil.requestBodyToString(exchange);
		
		int gameID = HandlerUtil.getGameID(exchange);
		
		//if gameID is -1, there is no cookie so send back an error message
		if (gameID == -1) {
			HandlerUtil.sendResponse(exchange, 400, "No Game Cookie", String.class);
		} 
		else {
			//otherwise send acceptTrade params to server model
			AcceptTradeParams acceptTradeParams = (AcceptTradeParams) Serializer.deserialize(inputStreamString, AcceptTradeParams.class);	
		
			try {
				ServerFacade.getSingleton().setGameID(gameID);
				ClientModel clientModel = ServerFacade.getSingleton().acceptTrade(acceptTradeParams);
				HandlerUtil.sendResponse(exchange, 200, clientModel, ClientModel.class);
			} catch (ServerResponseException e) {
				HandlerUtil.sendResponse(exchange, 400, "Failed to accept trade.", String.class);
				e.printStackTrace();
			}
			
		}
	}
}
