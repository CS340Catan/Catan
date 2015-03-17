package server.facade;

import java.util.HashMap;

public class RegisteredPlayers {
	
	HashMap<String, String> registeredPlayers = new HashMap<String, String>();
	
	public boolean addNewPlayer(String username, String password) {
		if (registeredPlayers.containsKey(username)) {
			return false;
		} else {
			registeredPlayers.put(username, password);
			return true;
		}
	}
	
	public boolean userExists(String username, String password){
		if (registeredPlayers.containsKey(username) && password.equals(registeredPlayers.get(username))) {
				return true;
		}
		return false;
	}

}
