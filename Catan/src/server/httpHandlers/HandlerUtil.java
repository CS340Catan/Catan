package server.httpHandlers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import com.sun.net.httpserver.HttpExchange;

public class HandlerUtil {
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
