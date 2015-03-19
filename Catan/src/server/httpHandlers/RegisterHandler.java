package server.httpHandlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import server.facade.ServerFacade;
import shared.communication.UserCredentials;
import shared.model.*;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class RegisterHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String inputStreamString = HandlerUtil.requestBodyToString(exchange);
		UserCredentials userCredentials = (UserCredentials) Serializer.deserialize(inputStreamString, UserCredentials.class);
		  
		try {
			ServerFacade.getSingleton().Register(userCredentials);
			HandlerUtil.setUserCookie(exchange, userCredentials);
			HandlerUtil.sendResponse(exchange, 200, "Success", String.class);			
		} catch (ServerResponseException e) {
			HandlerUtil.sendResponse(exchange, 400, "Failed to register - someone already has that username.", String.class);
			e.printStackTrace();
		}
	}

}
