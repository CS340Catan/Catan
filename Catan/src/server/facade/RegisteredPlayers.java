package server.facade;

import java.util.HashMap;

public class RegisteredPlayers {
	
	HashMap<String, String> registered = new HashMap<String, String>();
	
	boolean isValidRegistration(String username, String password) {
		if (registered.containsKey(username)) {
			return false;
		} else {
			registered.put(username, password);
			return true;
		}
	}

}
