package server.httpHandlers;

import java.io.IOException;
import java.util.logging.Logger;

import server.facade.FacadeSwitch;
import shared.communication.UserCredentials;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

import com.sun.net.httpserver.HttpExchange;

public class RegisterHandler implements IHttpHandler {
	
	private static Logger logger;
	static {
		logger = Logger.getLogger("CatanServer");
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		logger.info("server/httpHandlers/RegisterHandler - entering handle");
		  
		try {

			String inputStreamString = HandlerUtil.requestBodyToString(exchange);
			UserCredentials userCredentials = (UserCredentials) Serializer.deserialize(inputStreamString, UserCredentials.class);

			FacadeSwitch.getSingleton().Register(userCredentials);
			HandlerUtil.setUserCookie(exchange, userCredentials);
			HandlerUtil.sendResponse(exchange, 200, "Success", String.class);		
		} catch (ServerResponseException e) {
			HandlerUtil.sendResponse(exchange, 400, "Failed to register - " + e.getMessage(), String.class);
			e.printStackTrace();
		}
		catch (Exception e){
			HandlerUtil.sendResponse(exchange, 400, "Failed to register - bad json.", String.class);
			e.printStackTrace();
		}
		logger.info("server/httpHandlers/RegisterHandler - exiting handle");
	}

}
