package shared.model;

import java.util.HashMap;

public class RegisteredPlayers {
	private static RegisteredPlayers currentPlayers = null;
	
	public static RegisteredPlayers getSingleton(){
		if(currentPlayers == null){
			currentPlayers = new RegisteredPlayers();
		}
		return currentPlayers;
	}
	private RegisteredPlayers(){
		
	}
	HashMap<String, String> registeredPlayers = new HashMap<String, String>();
	
	public void addNewPlayer(String username, String password) {
			registeredPlayers.put(username, password);
	}
	
	public boolean userExists(String username, String password){
		if (registeredPlayers.containsKey(username) && password.equals(registeredPlayers.get(username))) {
				return true;
		}
		return false;
	}
	
	public boolean containsKey(String key){
		return registeredPlayers.containsKey(key);
	}
}
