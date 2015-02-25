package client.communicator;

public class Cookie {
	private int authentication;
	private String name;
	private int playerID;
	private String password;
	
	public int getAuthentication() {
		return authentication;
	}
	public void setAuthentication(int authentication) {
		this.authentication = authentication;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPlayerId() {
		return playerID;
	}
	public void setPlayerId(int playerId) {
		this.playerID = playerId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
