package server.httpHandlers;

import java.io.IOException;
import java.util.logging.Logger;

import server.facade.ServerFacade;
import shared.utils.ServerResponseException;
import client.model.ClientModel;

import com.sun.net.httpserver.HttpExchange;

public class ModelHandler implements IHttpHandler {
	
	private static Logger logger;
	static {
		logger = Logger.getLogger("CatanServer");
	}
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		//logger.info("server/httpHandlers/ModelHandler - entering ModelHandler");
		int gameID = HandlerUtil.getGameID(exchange);
		ServerFacade.getSingleton().setGameID(gameID);
		String query = exchange.getRequestURI().getQuery();
		int gameVersion = -1;
		if (query!=null){
			String[] queryArray = query.split("=");
			gameVersion = Integer.valueOf(queryArray[1]);
		}
		try {
			ClientModel clientModel = ServerFacade.getSingleton().getCurrentGame(gameVersion);
			HandlerUtil.sendResponse(exchange, 200, clientModel, ClientModel.class);
		} catch(ServerResponseException e) {
			HandlerUtil.sendResponse(exchange, 400, e.getMessage(), String.class);
			e.printStackTrace();
		}
		//logger.info("server/httpHandlers/ModelHandler - exiting ModelHandler");
	}

}
