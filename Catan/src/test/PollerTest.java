package test;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import shared.utils.IServer;
import client.controllers.Poller;
import client.model.ClientModel;
import client.model.ClientModelController;

public class PollerTest {
	
	private IServer server;
	private ClientModel clientModel;
	private ClientModel mockServerModel;
	private ClientModelController clientModelController;

	@Before
	public void setUp() throws Exception {
		//create and populate model
		clientModel = new ClientModel();
		mockServerModel = new ClientModel();
		clientModelController = new ClientModelController(clientModel);
		server = new MockServer(mockServerModel);
	}
	
	@Test
	public void pollerUpdating() {
		mockServerModel.setVersion(clientModel.getVersion()); //start off with the versions the same
		int originalVersion = clientModelController.getClientModel().getVersion(); 
		//keep track of what the version is to start with
		Poller poller = new Poller(server, clientModelController); //set the poller
		
		poller.updateModel();
		//update the model
		assertEquals(clientModelController.getClientModel().getVersion(), originalVersion); 
		//the model should be the same as before(the poller shouldn't have updated it)
		
		mockServerModel.setVersion(originalVersion + 1); 
		//change the version number of the model on the server manually
		poller.updateModel();
		//update the model
		assertEquals(clientModelController.getClientModel().getVersion(), originalVersion+1); 
		//the client model should have updated to match the model on the server
	}

}
