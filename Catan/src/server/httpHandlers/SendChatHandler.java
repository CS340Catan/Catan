package server.httpHandlers;

import java.io.IOException;
import java.util.List;

import server.facade.ServerFacade;
import shared.communication.ChatMessage;
import shared.communication.UserCredentials;
import shared.model.ClientModel;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class SendChatHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		String inputStreamString = HandlerUtil.requestBodyToString(exchange);
		Headers reqHeaders = exchange.getRequestHeaders();
		List<String> cookies = reqHeaders.get("Cookie");
		//check to see if there is a cookie, if not send back an error message
		
		//then put the cookie in the chatMessage
		ChatMessage chatMessage = (ChatMessage) Serializer.deserialize(inputStreamString, ChatMessage.class);
		
		try {
			ClientModel clientModel = ServerFacade.getSingleton().sendChat(chatMessage);
			HandlerUtil.sendResponse(exchange, 200, clientModel, ClientModel.class);
		} catch (ServerResponseException e) {
			HandlerUtil.sendResponse(exchange, 400, "Failed to send chat.", String.class);
			e.printStackTrace();
		}
		
	}

}
