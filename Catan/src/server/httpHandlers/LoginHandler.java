package server.httpHandlers;

import java.io.IOException;
import java.net.HttpURLConnection;

import server.facade.ServerFacade;
import shared.communication.UserCredentials;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

import com.sun.net.httpserver.HttpExchange;

public class LoginHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String inputStreamString = HandlerUtil.requestBodyToString(exchange);
		UserCredentials userCredentials = (UserCredentials) Serializer.deserialize(inputStreamString, UserCredentials.class);
		try {
			ServerFacade.getSingleton().login(userCredentials);
			HandlerUtil.setUserCookie(exchange, userCredentials);
			//HandlerUtil.sendResponse(exchange, HttpURLConnection.HTTP_OK, "Success", String.class);
			HandlerUtil.sendResponse(exchange, 200, "Success", String.class);
		} catch (ServerResponseException e) {
			HandlerUtil.sendResponse(exchange, 400, "Failed to login - bad username or password.", String.class);
			e.printStackTrace();
		}
	}

}
