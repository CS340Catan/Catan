package server.httpHandlers;

import java.io.IOException;

import server.facade.ServerFacade;
import shared.communication.BuildCityParams;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;
import client.model.ClientModel;

import com.sun.net.httpserver.HttpExchange;

public class BuildCityHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		System.out.println("endpoint activated");
		String inputString = HandlerUtil.requestBodyToString(exchange);
		int gameID = HandlerUtil.getGameID(exchange);
		int playerID = HandlerUtil.getPlayerID(exchange);
		
		if(gameID==-1){
			HandlerUtil.sendResponse(exchange, 400, "No Game Cookie", String.class);
		}
		else if(playerID == -1){
			HandlerUtil.sendResponse(exchange, 400, "No Player Cookie", String.class);
		}
		else{
			BuildCityParams params = (BuildCityParams) Serializer.deserialize(inputString, BuildCityParams.class);
			try {
				ServerFacade.getSingleton().setGameID(gameID);
				ServerFacade.getSingleton().setPlayerID(playerID);
				ClientModel clientModel = ServerFacade.getSingleton().buildCity(params);
				HandlerUtil.sendResponse(exchange, 200, clientModel, ClientModel.class);
				
			} catch (ServerResponseException e) {
				HandlerUtil.sendResponse(exchange, 400, e.getMessage(), String.class);
				e.printStackTrace();
			}
		}
	}

}
