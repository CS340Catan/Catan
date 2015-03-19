package server.httpHandlers;

import java.io.IOException;

import server.facade.ServerFacade;
import shared.communication.DiscardCardsParams;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;
import client.model.ClientModel;

import com.sun.net.httpserver.HttpExchange;

public class DiscardCardsHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		
		String inputStreamString = HandlerUtil.requestBodyToString(exchange);
		
		int gameID = HandlerUtil.getGameID(exchange);
		
		//if gameID is -1, there is no cookie so send back an error message
		if (gameID == -1) {
			HandlerUtil.sendResponse(exchange, 400, "No Game Cookie", String.class);
		} 
		else {
			//otherwise send params to server model
			DiscardCardsParams params = (DiscardCardsParams) Serializer.deserialize(inputStreamString, DiscardCardsParams.class);	
		
			try {
				ServerFacade.getSingleton().setGameID(gameID);
				ClientModel clientModel = ServerFacade.getSingleton().discardCards(params);
				HandlerUtil.sendResponse(exchange, 200, clientModel, ClientModel.class);
			} catch (ServerResponseException e) {
				HandlerUtil.sendResponse(exchange, 400, "Failed to discard cards", String.class);
				e.printStackTrace();
			}
			
		}
	}

}
