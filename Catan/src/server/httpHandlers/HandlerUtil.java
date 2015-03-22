package server.httpHandlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import server.model.RegisteredPlayers;
import shared.communication.UserCredentials;

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
		for (String cookie : cookies) {
			JsonParser parser = new JsonParser();
			/*JsonObject jsonObject = (JsonObject) parser.parse(cookie);
			JsonElement gameIDElement = jsonObject.get("gameID");
			gameID = gameIDElement.getAsInt();*/
			String[] cookieParts = cookie.split("=");
			gameID = Integer.valueOf(cookieParts[2]);
			return gameID;
		}
		// if there is no game cookie return -1
		// otherwise return the gameID
		return gameID;
	}

	public static int getPlayerID(HttpExchange exchange) {
		Headers reqHeaders = exchange.getRequestHeaders();
		List<String> cookies = reqHeaders.get("Cookie");
		int playerID = -1;
		for (String cookie : cookies) {
			JsonParser parser = new JsonParser();
			cookie = cookie.replace("catan.user=", "");
			String[] splitArray = cookie.split(";",-1);
			JsonObject jsonObject = (JsonObject) parser.parse(splitArray[0]);
			JsonElement gameIDElement = jsonObject.get("playerID");
			playerID = gameIDElement.getAsInt();
		}
		// if there is no game cookie return -1
		// otherwise return the gameID
		return playerID;
	}

	public static String requestBodyToString(HttpExchange exchange) {
		Scanner scanner = new Scanner(exchange.getRequestBody(), "UTF-8");
		String jsonString = scanner.useDelimiter("\\A").next();
		scanner.close();
		return jsonString;
	}

	@SuppressWarnings("rawtypes")
	public static void sendResponse(HttpExchange exchange, int httpCode,
			Object message, Class classType) {
		Gson gson = new Gson();
		try {
			exchange.sendResponseHeaders(httpCode, 0);
			if(message!=null){
				String jsonString = gson.toJson(message);
				//System.out.println(jsonString);
				for (int i = 0; i < jsonString.length(); i++) {
					exchange.getResponseBody().write(jsonString.charAt(i));
				}
			}
			exchange.getResponseBody().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void setUserCookie(HttpExchange exchange,
			UserCredentials userCredentials) {
		Headers respHeaders = exchange.getResponseHeaders();
		List<String> values = new ArrayList<>();
		String username = userCredentials.getUsername();
		String password = userCredentials.getPassword();
		int playerId = RegisteredPlayers.getSingleton().getPlayerId(username);
		/*values.add("catan.user={\"name\":\"" + username + "\",\"password\":\""
				+ password + "\",\"playerID\":" + playerId + "};Path=/;");*/
		values.add("catan.user={\"name\":\"" + username + "\",\"password\":\""
				+ password + "\",\"playerID\":" + playerId + "}");
		respHeaders.put("Set-Cookie", values);
	}
}
