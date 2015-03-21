package server.commands;

import shared.utils.ServerResponseException;

public abstract class ICommand {

	private String type;
	
	public abstract void execute() throws ServerResponseException;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
