package server.httpHandlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class HandlerUtil {
	public static int getGameID(HttpExchange exchange) {
		Headers reqHeaders = exchange.getRequestHeaders();
		List<String> cookies = reqHeaders.get("Cookie");
		//if there is no game cookie return -1
		//otherwise return the gameID
		return -1;
	}
	
	public static String requestBodyToString(HttpExchange exchange){
		Scanner scanner = new Scanner(exchange.getRequestBody(), "UTF-8");
		String jsonString = scanner.useDelimiter("\\A").next();
		scanner.close();
		return jsonString;
	}
	@SuppressWarnings("rawtypes")
	public static void sendResponse(HttpExchange exchange, int httpCode, Object message, Class classType){
		Gson gson = new Gson();
        try {
			exchange.sendResponseHeaders(httpCode,0);        	
    		JsonWriter writer = new JsonWriter(new OutputStreamWriter(exchange.getResponseBody(), "UTF-8"));
    		writer.setIndent("  ");    		
			writer.beginArray();
			gson.toJson(message, classType, writer);
			writer.close();
			exchange.getResponseBody().close();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
