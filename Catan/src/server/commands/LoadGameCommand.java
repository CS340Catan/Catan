package server.commands;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

import server.facade.ServerFacade;
import server.model.ServerModel;
import shared.communication.LoadGameParams;
import shared.utils.ServerResponseException;

/**
 * Loads a saved game
 * @author Seth White
 *
 */
public class LoadGameCommand implements ICommand {
	private String fileName;
	/**
	 * Sets the gameid to load
	 */
	public LoadGameCommand(LoadGameParams params){
		this.fileName = params.getName();
	}
	/**
	 * Load a saved game and transmit the right model to the client
	 * @throws ServerResponseException 
	 */
	@Override
	public void execute() throws ServerResponseException {
		//load from disk
		String fullName = "path/"+fileName;
		try{
			      InputStream file = new FileInputStream(fullName);
			      InputStream buffer = new BufferedInputStream(file);
			      ObjectInput input = new ObjectInputStream (buffer);
			      ServerModel model = (ServerModel)input.readObject();
			      ServerFacade.getSingleton().getModelMap().put(model.getGameID(),model);
			      input.close();
		}
		catch(ClassNotFoundException ex){
			throw new ServerResponseException("Could not find class ServerModel");
		}
		catch(IOException ex){
			throw new ServerResponseException("Could not access the file "+fullName);
		}
	}

}
