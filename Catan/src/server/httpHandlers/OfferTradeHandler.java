package server.httpHandlers;

import java.io.IOException;

import server.facade.FacadeSwitch;
import shared.communication.TradeOfferParams;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;
import client.model.ClientModel;

import com.sun.net.httpserver.HttpExchange;

public class OfferTradeHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		
		int gameID = HandlerUtil.getGameID(exchange);
		int playerID = HandlerUtil.getPlayerID(exchange);
		
		
		if(playerID == -1){
			HandlerUtil.sendResponse(exchange, 400, "Player Cookie not set. Not logged in!", String.class);
		}
		else if(gameID == -1){
			HandlerUtil.sendResponse(exchange, 400, "Game Cookie is not set", String.class);
		}
		else{
			
			try {
				String inputStreamString = HandlerUtil.requestBodyToString(exchange);
				TradeOfferParams params = (TradeOfferParams) Serializer.deserialize(inputStreamString, TradeOfferParams.class);
				
				FacadeSwitch.getSingleton().setGameID(gameID);
				FacadeSwitch.getSingleton().setPlayerID(playerID);
				ClientModel model = FacadeSwitch.getSingleton().offerTrade(params);
				HandlerUtil.sendResponse(exchange, 200, model, ClientModel.class);
				
			} catch (ServerResponseException e) {
				HandlerUtil.sendResponse(exchange, 400, e.getMessage(), String.class);
				e.printStackTrace();
			}
		}
		
	}

}
