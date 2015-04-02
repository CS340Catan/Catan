package server.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import server.facade.FacadeSwitch;
import server.model.GameList;
import server.model.ServerModel;
import shared.communication.GameSummary;
import shared.communication.LoadGameParams;
import shared.utils.Serializer;
import shared.utils.ServerResponseException;

/**
 * Loads a saved game
 * 
 * @author Seth White
 *
 */
public class LoadGameCommand extends ICommand {
	private String fileName;

	/**
	 * Sets the gameid to load
	 */
	public LoadGameCommand(LoadGameParams params) {
		this.fileName = params.getName();
		this.setType("LoadGame");

	}

	/**
	 * Load a saved game and transmit the right model to the client
	 * 
	 * @throws ServerResponseException
	 */
	@Override
	public void execute() throws ServerResponseException {
		/*
		 * Load from disk the game using the inputed fileName. If the file could
		 * not be found or the ServerModel class does not work with the object,
		 * throw an exception.
		 */
		String fullName = "./saves/" + fileName + ".txt";
		try {
			FileReader reader = new FileReader(fullName);
			BufferedReader br = new BufferedReader(reader);
			String jsonModel = br.readLine();
			String jsonSummary = br.readLine();
			if (jsonModel == null) {
				br.close();
				throw new ServerResponseException("Tried to load empty file!");
			}
			ServerModel model = (ServerModel) Serializer.deserialize(jsonModel,
					ServerModel.class);
			int id = model.getGameID();
			FacadeSwitch.getSingleton().getModelMap().put(id, model);
			GameSummary summary = (GameSummary) Serializer.deserialize(
					jsonSummary, GameSummary.class);
			GameList.getSingleton().addGame(summary);
			br.close();

		} catch (IOException ex) {
			ex.printStackTrace();
			throw new ServerResponseException("Could not access the file "
					+ fileName);
		}
	}

}
