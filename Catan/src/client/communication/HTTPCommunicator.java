package client.communication;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;


public class HTTPCommunicator {
	
	private static final String HTTP_POST = "POST";
	private static final String HTTP_GET = "GET";
	private static final int GET = 0;
	private static final int POST = 1;
	
	private static String SERVER_HOST = null;
	private static int SERVER_PORT;
	private static String URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	
	/**
	 * Set when the player logs in. Check responses for cookie. Once it's found, set it, and send it as one of hte headers
	 */
	private static String userCookie;
	private static String gameCookie;
	
	
	public static void setServer(String host, int port)
	{
		SERVER_HOST = host;
		SERVER_PORT = port;
		URL_PREFIX = "http://" + SERVER_HOST + ":" + SERVER_PORT;
	}
	/**
	 * @Pre url is valid, object is valid
	 * @Post A valid Java Object returned
	 */
	public String doGet(String urlString, String gsonString){
		return communicate(urlString, gsonString, null,GET);
	}
	
	
	/**
	 * @Pre url is valid, object is valid
	 * @Post A valid Java Object returned
	 */
	public String doPost(String url, String gsonString, String[] postQueries){
		return communicate(url,gsonString, postQueries, POST);
	}
	
	private String communicate(String urlString, String gsonString, String[] postQueries, int requestType){
		if(gsonString != null) {
			try 
			{
				URL url = new URL(URL_PREFIX + urlString);
				
				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				if(userCookie!=null)
					connection.setRequestProperty("catan.user", userCookie);
				if(gameCookie!=null)
					connection.setRequestProperty("catan.game",gameCookie);
				
				if(requestType==POST)
					connection.setRequestMethod(HTTP_POST);
				else if(requestType==GET)
					connection.setRequestMethod(HTTP_GET);
				
				connection.setDoOutput(true);
				connection.connect();
				
				DataOutputStream out = new DataOutputStream(connection.getOutputStream());
				out.writeBytes(gsonString);
				connection.getOutputStream().close();
				
				if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
				{
					//Look for cookies
					String cookie = connection.getHeaderField("Set-Cookie");
					if(cookie!=null)
						parseSetCookie(cookie, connection);
					
					BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	                StringBuilder sb = new StringBuilder();
	                String line;
	                while ((line = br.readLine()) != null) {
	                    sb.append(line+"\n");
	                }
	                br.close();
	                return sb.toString();
				}
				else
				{
					return null;
				}
				
			}
			catch (IOException e)
			{
				return null;
			}
		}
		else {
			
			return null;
		}
	}
	
	private void parseSetCookie(String cookieString, HttpURLConnection connection)
	{
		//strip ;Path=/; and catan.****
		cookieString = cookieString.replace(";Path=/","");
		String cleaned = cookieString.replace("catan.game=","");
		if(!cleaned.equals(cookieString))
			gameCookie = cookieString;
		else{
			cleaned = cookieString.replace("catan.player=","");
			userCookie = cookieString;
		}
		// TODO we may need to still do some decoding, but we need to decide where
	}
}
