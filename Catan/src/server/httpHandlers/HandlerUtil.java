package server.httpHandlers;

import java.io.StringWriter;
import java.util.Scanner;
import com.sun.net.httpserver.HttpExchange;

public class HandlerUtil {
	public static String requestBodyToString(HttpExchange exchange){
		Scanner scanner = new Scanner(exchange.getRequestBody(), "UTF-8");
		String jsonString = scanner.useDelimiter("\\A").next();
		scanner.close();
		return jsonString;
	}
}
