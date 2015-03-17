package server.httpHandlers;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Scanner;

import shared.communication.UserActionParams;
import shared.utils.Serializer;
import sun.misc.IOUtils;

import com.sun.net.httpserver.HttpExchange;

public class LoginHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String inputStreamString = HandlerUtil.requestBodyToString(exchange);
        System.out.println(inputStreamString);
		
	}

}
