package shared.communication;

/**
 * This class wraps about data about a player to be sent across the network It
 * mirrors the json object passed across the network. This is part of the the
 * response generated from the server from calling '/games/lit'
 * 
 * Domain color:String name:String id:integer
 * 
 * @author winstonhurst
 */
public class PlayerSummary {
	/**
	 * The player's color
	 */
	String color;
	/**
	 * The player's username
	 */
	String name;
	/**
	 * The Player's id
	 */
	int id;

	/**
	 * @pre The player must be in a game, with a valid username, color and id
	 * @post Creates a PlayerSummary Object which can be serialized into json
	 *       and passed across the network
	 * @param color
	 * @param name
	 * @param id
	 */
	public PlayerSummary(String color, String name, int id) {
		this.color = color;
		this.name = name;
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
