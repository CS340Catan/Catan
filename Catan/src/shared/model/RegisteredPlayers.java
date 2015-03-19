package shared.model;

import java.util.HashMap;

import shared.utils.IDGenerator;

public class RegisteredPlayers {
	private static RegisteredPlayers currentPlayers = null;
	private HashMap<String, String> registeredPlayers = new HashMap<String, String>();
	private HashMap<String,Integer> playerIDs = new HashMap<String,Integer>();
	
	public static RegisteredPlayers getSingleton(){
		if(currentPlayers == null){
			currentPlayers = new RegisteredPlayers();
		}
		return currentPlayers;
	}
	private RegisteredPlayers(){
		
	}
	
	public void addNewPlayer(String username, String password) {
			registeredPlayers.put(username, password);
			playerIDs.put(username,IDGenerator.generatePlayerID());
	}
	
	public boolean userExists(String username, String password){
		if (registeredPlayers.containsKey(username) && password.equals(registeredPlayers.get(username))) {
				return true;
		}
		return false;
	}
	public int getPlayerId(String username){
		return playerIDs.get(username);
	}
	public boolean containsKey(String key){
		return registeredPlayers.containsKey(key);
	}
}
