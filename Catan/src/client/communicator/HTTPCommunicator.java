package client.communicator;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.logging.*;

import client.data.UserPlayerInfo;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

public class HTTPCommunicator {

	private static final String HTTP_POST = "POST";
	private static final String HTTP_GET = "GET";
	private static final int GET = 0;
	private static final int POST = 1;

	private static String SERVER_HOST = null;
	private static int SERVER_PORT;
	private static String URL_PREFIX = "http://" + SERVER_HOST + ":"
			+ SERVER_PORT;

	/**
	 * Set when the player logs in. Check responses for cookie. Once it's found,
	 * set it, and send it as one of hte headers
	 */
	private static String userCookie;
	private static String gameCookie;
	@SuppressWarnings("unused")
	private static Logger logger;

	static {
		logger = Logger.getLogger("CatanClient");
	}

	public static void setServer(String host, int port) {

		SERVER_HOST = host;
		SERVER_PORT = port;
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	}

	/**
	 * @throws ServerResponseException
	 * @Pre url is valid, object is valid
	 * @Post A valid Java Object returned
	 */
	public String doGet(String urlString, String gsonString)
			throws ServerResponseException {
		return communicate(urlString, gsonString, GET);
	}

	/**
	 * @throws ServerResponseException
	 * @Pre url is valid, object is valid
	 * @Post A valid Java Object returned
	 */
	public String doPost(String url, String gsonString)
			throws ServerResponseException {
		return communicate(url, gsonString, POST);
	}

	private String communicate(String urlString, String gsonString,
			int requestType) throws ServerResponseException {
		try {
			URL url = new URL(URL_PREFIX + urlString);

			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();

			// set Cookie
			String cookieValue = "";
			if (userCookie != null)
				cookieValue += userCookie;
			if (gameCookie != null) {
				cookieValue += "; ";
				cookieValue += gameCookie;
			}

			if (cookieValue != "")
				connection.setRequestProperty("Cookie", cookieValue);

			if (requestType == POST)
				connection.setRequestMethod(HTTP_POST);
			else if (requestType == GET)
				connection.setRequestMethod(HTTP_GET);
			connection.setDoOutput(true);
			connection.connect();
			if (gsonString != null) {
				DataOutputStream out = new DataOutputStream(
						connection.getOutputStream());
				out.writeBytes(gsonString);
				connection.getOutputStream().close();
			}

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				// Look for cookies
				String cookie = connection.getHeaderField("Set-Cookie");
				if (cookie != null)
					parseSetCookie(cookie, connection);

				BufferedReader br = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

				br.close();
				String body = sb.toString();
				if (body == null)
					body = "";
				return body;
			} else {
				String response = "ERROR: " + connection.getResponseCode()
						+ " - " + connection.getResponseMessage() + "\n";
				throw new ServerResponseException(response);
			}
		} catch (IOException e) {
			return null;
		}

	}

	/**
	 * will only set cookie if they aren't set already
	 * 
	 * @param cookieString
	 * @param connection
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private int parseSetCookie(String cookieString, HttpURLConnection connection) {
		// if cookies have already been set, don't worry about setting them
		// again.

		// if (gameCookie == null || userCookie == null) {
		// strip ;Path=/; and catan.****
		cookieString = cookieString.replace("~Path=/~", "");
		cookieString = cookieString.replace(";Path=/;", "");
		if (userCookie == null) {
			String decodedCookie = URLDecoder.decode(cookieString);
			decodedCookie = decodedCookie.replace("catan.user=", "");
			decodedCookie = decodedCookie.replace("catan.user=", "");
			Cookie cookie = (Cookie) Serializer.deserialize(decodedCookie,
					Cookie.class);
			UserPlayerInfo.getSingleton().setId(cookie.getPlayerId());
			UserPlayerInfo.getSingleton().setName(cookie.getName());

			String cleaned = cookieString.replace("catan.player=", "");
			userCookie = cleaned;
			return 1;
		} else {
			// String cleaned = cookieString.replace("catan.game=", "");
			// gameCookie = cleaned;
			gameCookie = cookieString;
			UserPlayerInfo.getSingleton().setGameId(gameCookie);
			return 2;
		}
	}
}
