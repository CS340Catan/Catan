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
		int originalVersion = clientModel.getVersion(); //keep track of what the version is to start with
		Poller poller = new Poller(server, clientModelController); //set the poller
		try {
			Thread.sleep(2000); //let the poller update a few times
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		assertEquals(clientModel.getVersion(), originalVersion); //the model should be the same (the poller shouldn't have updated it)
		mockServerModel.setVersion(originalVersion + 1); //change the version number of the model on the server manually
		try {
			Thread.sleep(2000); //let the poller run
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		assertEquals(clientModel.getVersion(), originalVersion+1); //the client model should have updated to match the model on the server
	}

}
