package test;

import org.junit.*;

import shared.utils.IServer;
import client.model.ClientModel;

public class PollerTest {
	
	private IServer server;
	private ClientModel model;

	@Before
	public void setUp() throws Exception {
		//create and populate model
		model = new ClientModel();
		server = new MockServer(model);
	}
	
	@Test
	public void updatedModel() {
		
	}
	
	@Test
	public void unupdatedModel() {
	
	}

}
