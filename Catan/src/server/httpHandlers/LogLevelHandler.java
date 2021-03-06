package server.httpHandlers;

import java.io.IOException;

import server.facade.FacadeSwitch;
import shared.communication.ChangeLogLevelParams;
import shared.communication.ChangeLogLevelResponse;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

import com.sun.net.httpserver.HttpExchange;

public class LogLevelHandler implements IHttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try {
			String inputStreamString = HandlerUtil
					.requestBodyToString(exchange);
			ChangeLogLevelParams params = (ChangeLogLevelParams) Serializer
					.deserialize(inputStreamString, ChangeLogLevelParams.class);

			ChangeLogLevelResponse response = FacadeSwitch.getSingleton()
					.changeLogLevel(params);
			HandlerUtil.sendResponse(exchange, 200, response,
					ChangeLogLevelResponse.class);

		} catch (ServerResponseException e) {
			HandlerUtil.sendResponse(
					exchange,
					400,
					"Failed to login - bad username or password. "
							+ e.getMessage(), String.class);
			e.printStackTrace();
		} catch (Exception e) {
			HandlerUtil.sendResponse(exchange, 400,
					"Failed to login - bad json.", String.class);
			e.printStackTrace();
		}

	}

}
