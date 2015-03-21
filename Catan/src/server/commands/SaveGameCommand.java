package server.commands;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import server.facade.ServerFacade;
import server.model.ServerModel;
import shared.communication.SaveParams;
import shared.utils.ServerResponseException;

/**
 * This is the command class for the SaveGame function called on the server. It
 * will receive a SaveParams object and a gameID in the constructor
 * 
 * @author Drewfus
 */

public class SaveGameCommand extends ICommand {

	String fileName;
	int gameID;

	public SaveGameCommand(SaveParams params) {
		this.fileName = params.getname();
		this.gameID = params.getId();
	}

	/**
	 * Saves state of the game to a file, which can be used later for debugging
	 * purposes
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {
		ServerModel model = ServerFacade.getSingleton().getServerModel();
		model.setGameID(ServerFacade.getSingleton().getGameID());
		/*
		 * Grab the model object and save the model to disk for storage.
		 */
		try {
			OutputStream file = new FileOutputStream("saves/" + fileName);
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			output.writeObject(model);
			output.close();
		} catch (IOException ex) {
			throw new ServerResponseException("Could not save to file saves/"
					+ fileName);
		}
	}

}
