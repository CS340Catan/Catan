package shared.communication;

/**
 * This class wraps up data that needs be sent to the client from the server in
 * response to the '/games/save' request.
 * 
 * Domain: id:Integer name:String Domain Restraints: id - must be a valid id for
 * an existing game name - cannot be null or empty
 * 
 * @author winstonhurst
 */

public class SaveParams {
	/**
	 * A valid game ID - the game must exist
	 */
	int id;
	/**
	 * The name of the file to which
	 */
	String name;

	/**
	 * @Pre id must be a valid game id
	 * @Pre name cannot be null or the empty string
	 * @post creates a wrapper class around data that needs be sent to server in
	 *       order to save game
	 * @param id
	 * @param name
	 * @throws InvalidInputException
	 */
	public SaveParams(int id, String name) throws InvalidInputException {
		this.id = id;
		if (name == null || name.equals(""))
			throw new InvalidInputException(
					"The file name to be written cannot be null!");
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getname() {
		return name;
	}

	public void setname(String name) {
		this.name = name;
	}

}
