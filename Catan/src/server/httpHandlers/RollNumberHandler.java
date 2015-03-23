package server.httpHandlers;

import java.io.IOException;
import java.util.logging.Logger;

import server.facade.ServerFacade;
import server.model.ServerModel;
import shared.communication.RollParams;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;
import client.model.ClientModel;

import com.sun.net.httpserver.HttpExchange;

public class RollNumberHandler implements IHttpHandler {
	private static Logger logger;
	static {
		logger = Logger.getLogger("CatanServer");
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		logger.info("***server/httpHandlers/RollNumberHandler - entering handle");
		String inputStreamString = HandlerUtil.requestBodyToString(exchange);
		
		int gameID = HandlerUtil.getGameID(exchange);
		int playerID = HandlerUtil.getPlayerID(exchange);
		
		//if gameID is -1, there is no cookie so send back an error message
		if (gameID == -1) {
			HandlerUtil.sendResponse(exchange, 400, "No Game Cookie", String.class);
		} 
		else {
			//otherwise send params to server model
			RollParams params = (RollParams) Serializer.deserialize(inputStreamString, RollParams.class);	
		
			try {
				ServerFacade.getSingleton().setGameID(gameID);
				ServerFacade.getSingleton().setPlayerID(playerID);
				ClientModel clientModel = ServerFacade.getSingleton().rollNumber(params.getNumber());
				HandlerUtil.sendResponse(exchange, 200, clientModel, ClientModel.class);
			} catch (ServerResponseException e) {
				HandlerUtil.sendResponse(exchange, 400, "Failed to discard cards", String.class);
				e.printStackTrace();
			}
			
		}
		logger.info("***server/httpHandlers/RollNumberHandler - exiting handle");
	}

}
