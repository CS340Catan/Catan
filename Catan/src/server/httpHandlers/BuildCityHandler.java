package server.httpHandlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

public class BuildCityHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		System.out.println("endpoint activated");
		
	}

}
