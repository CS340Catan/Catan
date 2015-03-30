package server.facade;

import client.model.ClientModel;

public class FacadeSwitch {
	private static IServerFacade facade;
	
	public static void setMockServer(ClientModel model){
		facade = new MockServerFacade(model);
	}
	
	public static IServerFacade getSingleton(){
		if(facade == null){
				facade = ServerFacade.getSingleton();
		}
		return facade;
	}
}
