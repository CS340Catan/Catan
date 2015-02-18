package client.data;

import shared.definitions.*;

/**
 * Used to pass player information into views<br>
 * <br>
 * PROPERTIES:<br>
 * <ul>
 * <li>Id: Unique player ID</li>
 * <li>PlayerIndex: Player's order in the game [0-3]</li>
 * <li>Name: Player's name (non-empty string)</li>
 * <li>Color: Player's color (cannot be null)</li>
 * </ul>
 * 
 */
public class PlayerInfo {

	private int id;
	private static int playerIndex;
	private static String name;
	private static CatanColor color;
	private static PlayerInfo playerInfo = null;
	
	public static PlayerInfo getSingleton(){
		if(playerInfo == null){
			playerInfo = new PlayerInfo();
		}
		return playerInfo;
	}
	
	public PlayerInfo() {
		setId(-1);
		setPlayerIndex(-1);
		setName("");
		setColor(CatanColor.WHITE);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		PlayerInfo.playerIndex = playerIndex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		PlayerInfo.name = name;
	}

	public CatanColor getColor() {
		return color;
	}

	public void setColor(CatanColor color) {
		PlayerInfo.color = color;
	}

	@Override
	public int hashCode() {
		return 31 * this.id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final PlayerInfo other = (PlayerInfo) obj;

		return this.id == other.id;
	}
}
