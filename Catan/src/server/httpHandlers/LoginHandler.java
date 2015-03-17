package server.httpHandlers;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Scanner;

import shared.communication.UserActionParams;
import shared.communication.UserCredentials;
import shared.utils.Serializer;
import sun.misc.IOUtils;

import com.sun.net.httpserver.HttpExchange;

public class LoginHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String inputStreamString = HandlerUtil.requestBodyToString(exchange);
		UserCredentials userCredentials = (UserCredentials) Serializer.deserialize(inputStreamString, UserCredentials.class);
        System.out.println(inputStreamString);
		
	}

}
