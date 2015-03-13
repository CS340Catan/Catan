package shared.communication;

/**
 * This class wraps up the information the server needs to add a user to a game.
 * 
 * Domain: color:String id:Integer value
 * 
 * @author winstonhurst
 */
public class JoinGameParams {

	/**
	 * The color the player wished to join as
	 */
	String color;
	/**
	 * The id of the game the player wishes to join
	 */
	int id;

	/**
	 * @pre The color cannot be in use by another player
	 * @pre The id must be the id of a valid game
	 * @post Creates a JoinGameParams object with the information needed to add
	 *       a user to a game
	 * @param color
	 * @param id
	 */
	public JoinGameParams(String color, int id) {
		this.color = color.toLowerCase();
		this.id = id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color.toLowerCase();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
