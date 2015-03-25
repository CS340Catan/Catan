package server.httpHandlers;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Logger;

import server.facade.ServerFacade;
import shared.communication.JoinGameParams;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

import com.sun.net.httpserver.HttpExchange;

public class JoinGameHandler implements IHttpHandler {
	private static Logger logger;
	static {
		logger = Logger.getLogger("CatanServer");
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		logger.info("server/httpHandlers/JoinGameHandler - entering Handle");

		try {
			
			String inputStreamString = HandlerUtil.requestBodyToString(exchange);
			JoinGameParams joinParams = (JoinGameParams) Serializer.deserialize(inputStreamString, JoinGameParams.class);
			
			ServerFacade.getSingleton().setGameID(joinParams.getId());
			int playerId = HandlerUtil.getPlayerID(exchange);
			ServerFacade.getSingleton().setPlayerID(playerId);
			ServerFacade.getSingleton().joinGame(joinParams);
			
			ArrayList<String> values=new ArrayList<String>();
			String gameCookie = URLEncoder.encode("catan.game=" + joinParams.getId(),"utf-8");
			gameCookie += ";Path=/;";
			values.add(gameCookie);
			exchange.getResponseHeaders().put("Set-Cookie",values);
			
			HandlerUtil.sendResponse(exchange, 200, "Success", String.class);
		} catch (ServerResponseException e) {
			HandlerUtil.sendResponse(exchange, 400, e.getMessage(), String.class);
			e.printStackTrace();
		} catch (NullPointerException e){
			HandlerUtil.sendResponse(exchange, 400, "Cannot join game - No cookie", String.class);
			e.printStackTrace();
		} catch (Exception e){
			HandlerUtil.sendResponse(exchange, 400, "Cannot join game - Bad json or cookie", String.class);
			e.printStackTrace();
		}
		logger.info("***server/httpHandlers/JoinGameHandler - exiting Handle");
	}

}
