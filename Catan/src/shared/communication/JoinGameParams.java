package shared.communication;

import shared.definitions.CatanColor;

/**
 * 
 * @author winstonhurst
 * This class wraps up the information the server needs to add a user to a game.
 * 
 * Domain:
 * 	color:CatanColor
 * 	id:Integer value
 */
public class JoinGameParams {
	
	/**
	 * The color the player wished to join as
	 */
	CatanColor color;
	/**
	 * The id of the game the player wishes to join
	 */
	int id;
	
	/**
	 * @pre The color cannot be in use by another player
	 * @pre The id must be the id of a valid game
	 * @post Creates a JoinGameParams object with the information needed to add a user to a game
	 * @param color
	 * @param id
	 */
	public JoinGameParams(CatanColor color, int id) {
		this.color = color;
		this.id = id;
	}

	public CatanColor getColor() {
		return color;
	}

	public void setColor(CatanColor color) {
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	
}
