package server.facade;

import server.model.ServerModel;

public class FacadeSwitch {
	private static IServerFacade facade;
	
	public static void setMockServer(ServerModel model){
		facade = new MockServerFacade(model);
	}
	
	public static IServerFacade getSingleton(){
		if(facade == null){
				facade = ServerFacade.getSingleton();
		}
		return facade;
	}
}
