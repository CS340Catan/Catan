package server.httpHandlers;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class HandlerUtil {
	public static int getGameID(HttpExchange exchange) {
		Headers reqHeaders = exchange.getRequestHeaders();
		List<String> cookies = reqHeaders.get("Cookie");
		int gameID = -1;
		for(String cookie : cookies){
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = (JsonObject) parser.parse(cookie);
			JsonElement gameIDElement = jsonObject.get("gameID");
			gameID = gameIDElement.getAsInt();
		}
		//if there is no game cookie return -1
		//otherwise return the gameID
		return gameID;
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
			String jsonString = gson.toJson(message);
			System.out.println(jsonString);
			for(int i = 0; i < jsonString.length();i++){
				exchange.getResponseBody().write(jsonString.charAt(i));
			}
			exchange.getResponseBody().close();			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
