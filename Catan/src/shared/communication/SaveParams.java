package shared.communication;

/**
 * 
 * @author winstonhurst
 * This class wraps up data that needs be sent to the client from the server
 * in response to the '/games/save' request.
 * 
 * Domain:
 * 	id:Integer
 * 	filename:String
 * 	Domain Restraints:
 * 	id - must be a valid id for an existing game
 * filename - cannot be null or empty
 */

public class SaveParams {
	/**
	 * A valid game ID - the game must exist
	 */
	int id;
	/**
	 * The name of the file to which 
	 */
	String fileName;
	
	/**
	 * @pre id must be a valid game id
	 * @pre filename cannot be null or the empty string
	 * @post creates a wrapper class around data that needs be sent to server in order to save game
	 * @param id
	 * @param fileName
	 * @throws InvalidInputException
	 */
	public SaveParams(int id, String fileName) throws InvalidInputException{
		this.id = id;
		if(fileName==null||fileName.equals(""))
			throw new InvalidInputException("The file name to be written cannot be null!");
		this.fileName = fileName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
