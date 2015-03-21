package server.commands;

import shared.utils.ServerResponseException;

public abstract class ICommand {

	
	
	public abstract void execute() throws ServerResponseException;
}
