package server.httpHandlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.util.Scanner;

import server.facade.ServerFacade;
import shared.communication.UserActionParams;
import shared.communication.UserCredentials;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;
import sun.misc.IOUtils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.sun.net.httpserver.HttpExchange;

public class LoginHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String inputStreamString = HandlerUtil.requestBodyToString(exchange);
		UserCredentials userCredentials = (UserCredentials) Serializer.deserialize(inputStreamString, UserCredentials.class);
		
		try {
			ServerFacade.getSingleton().login(userCredentials);
		} catch (ServerResponseException e) {
			HandlerUtil.sendResponse(exchange, 400, "Failed to login - bad username or password.", String.class);
			e.printStackTrace();
		}
		HandlerUtil.sendResponse(exchange, 200, "Success", String.class);
        System.out.println(inputStreamString);
		
	}

}
