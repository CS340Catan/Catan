package server.httpHandlers;

import java.io.IOException;

import server.facade.ServerFacade;
import shared.communication.TradeOfferParams;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;
import client.model.ClientModel;

import com.sun.net.httpserver.HttpExchange;

public class OfferTradeHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String inputStreamString = HandlerUtil.requestBodyToString(exchange);
		
		int gameID = HandlerUtil.getGameID(exchange);
		int playerID = HandlerUtil.getPlayerID(exchange);
		
		
		if(playerID == -1){
			HandlerUtil.sendResponse(exchange, 400, "Player Cookie not set. Not logged in!", String.class);
		}
		else if(gameID == -1){
			HandlerUtil.sendResponse(exchange, 400, "Game Cookie is not set", String.class);
		}
		else{
			TradeOfferParams params = (TradeOfferParams) Serializer.deserialize(inputStreamString, TradeOfferParams.class);
			
			try {
				ServerFacade.getSingleton().setGameID(gameID);
				ServerFacade.getSingleton().setPlayerID(playerID);
				ClientModel model = ServerFacade.getSingleton().offerTrade(params);
				HandlerUtil.sendResponse(exchange, 200, model, ClientModel.class);
				
			} catch (ServerResponseException e) {
				HandlerUtil.sendResponse(exchange, 400, e.getMessage(), String.class);
				e.printStackTrace();
			}
		}
		
	}

}
