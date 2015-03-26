package server.httpHandlers;

import java.io.IOException;

import server.facade.ServerFacade;
import shared.communication.ChangeLogLevelParams;
import shared.communication.ChangeLogLevelResponse;
import shared.communication.UserCredentials;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

import com.sun.net.httpserver.HttpExchange;

public class LogLevelHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try {
			String inputStreamString = HandlerUtil.requestBodyToString(exchange);
			ChangeLogLevelParams params = (ChangeLogLevelParams) Serializer.deserialize(inputStreamString, UserCredentials.class);
			
			ChangeLogLevelResponse response = ServerFacade.getSingleton().changeLogLevel(params);
			HandlerUtil.sendResponse(exchange, 200, response.getResponse(), String.class);
			
		} catch (ServerResponseException e) {
			HandlerUtil.sendResponse(exchange, 400, "Failed to login - bad username or password. " + e.getMessage(), String.class);
			e.printStackTrace();
		}
		catch (Exception e){
			HandlerUtil.sendResponse(exchange, 400, "Failed to login - bad json.", String.class);
			e.printStackTrace();
		}
		
	}

}
