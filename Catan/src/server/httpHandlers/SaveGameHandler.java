package server.httpHandlers;

import java.io.IOException;

import server.facade.FacadeSwitch;
import shared.communication.SaveParams;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

import com.sun.net.httpserver.HttpExchange;

public class SaveGameHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {

		try {
			String inputStreamString = HandlerUtil
					.requestBodyToString(exchange);
			SaveParams saveParams = (SaveParams) Serializer.deserialize(
					inputStreamString, SaveParams.class);

			FacadeSwitch.getSingleton().setGameID(saveParams.getId());
			FacadeSwitch.getSingleton().saveGame(saveParams);
			HandlerUtil.sendResponse(exchange, 200, "Success", String.class);
		} catch (ServerResponseException e) {
			HandlerUtil.sendResponse(exchange, 400, e.getMessage()
					+ " Be sure you have given a valid file name.",
					String.class);
			e.printStackTrace();
		} catch (Exception e) {
			HandlerUtil.sendResponse(exchange, 400,
					"Could not save - JSON error likely", String.class);
			e.printStackTrace();
		}
	}

}
