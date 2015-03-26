package server.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import server.facade.ServerFacade;
import server.model.GameList;
import server.model.ServerModel;
import shared.communication.GameSummary;
import shared.communication.SaveParams;
import shared.utils.Serializer;
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
		this.setType("SaveGame");
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
		int id  = model.getGameID();
		GameSummary summary = GameList.getSingleton().getGameByID(id);
		/*
		 * Grab the model object and save the model to disk for storage.
		 */
		try {
			File file = new File("./saves/" + fileName + ".txt");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			String jsonModel = Serializer.serialize(model);
			String jsonSummary = Serializer.serialize(summary);
//			writer.write(jsonModel);
			writer.write(jsonModel + "\n" + jsonSummary);
			writer.close();
			
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new ServerResponseException("Could not save to file saves/"
					+ fileName);
		}
	}

}
