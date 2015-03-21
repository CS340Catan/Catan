package server.commands;

import shared.utils.ServerResponseException;

public interface ICommand {

	public void execute() throws ServerResponseException;
}
