package shared.communication;

import shared.definitions.CatanColor;

/**
 * 
 * @author winstonhurst
 * This class wraps about data about a player to be sent across the network
 * It mirrors the json object passed across the network.
 * This is part of the the response generated from the server from calling '/games/lit'
 *	Domain
 *		color:CatanColor
 *		name:Username
 *		id:integer
 */
public class PlayerSummary {
	/**
	 * The player's color
	 */
	CatanColor color;
	/**
	 * The player's username
	 */
	Username name;
	/**
	 * The Player's id
	 */
	int id;
	
	/**
	 * @pre The player must be in a game, with a valid username, color and id
	 * @post Creates a PlayerSummary Object which can be serialized into json and passed across the network
	 * @param color
	 * @param name
	 * @param id
	 */
	public PlayerSummary(CatanColor color, Username name, int id)
	{
		this.color = color;
		this.name = name;
		this.id = id;
	}

	public CatanColor getColor() {
		return color;
	}

	public void setColor(CatanColor color) {
		this.color = color;
	}

	public Username getName() {
		return name;
	}

	public void setName(Username name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
