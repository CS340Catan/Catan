package server.httpHandlers;

import java.io.IOException;

import server.facade.FacadeSwitch;
import shared.communication.UserCredentials;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

import com.sun.net.httpserver.HttpExchange;

public class LoginHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		try {
			String inputStreamString = HandlerUtil
					.requestBodyToString(exchange);
			UserCredentials userCredentials = (UserCredentials) Serializer
					.deserialize(inputStreamString, UserCredentials.class);
			FacadeSwitch.getSingleton().login(userCredentials);
			HandlerUtil.setUserCookie(exchange, userCredentials);
			HandlerUtil.sendResponse(exchange, 200, "Success", String.class);
		} catch (ServerResponseException e) {
			HandlerUtil.sendResponse(
					exchange,
					400,
					"Failed to login - bad username or password. "
							+ e.getMessage(), String.class);
			e.printStackTrace();
		} catch (Exception e) {
			HandlerUtil.sendResponse(exchange, 400,
					"Failed to login - bad json.", String.class);
			e.printStackTrace();
		}
	}

}
