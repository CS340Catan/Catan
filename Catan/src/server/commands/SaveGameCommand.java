package server.commands;

import shared.communication.SaveParams;

/**
 * @author Drewfus
 * This is the command class for the SaveGame function called on the server.
 * It will receive a SaveParams object and a gameID in the constructor
 */

public class SaveGameCommand implements ICommand {
	
	String fileName;
	int gameID;
	
	public SaveGameCommand(SaveParams params) {
		this.fileName = params.getname();
		this.gameID = params.getId();
	}
	/**
	 * Saves state of the game to a file, which can be used later for debugging purposes
	 */
	@Override
	public void execute() {
		//get game model
		//serialize game model
		//save to file
	}

}

