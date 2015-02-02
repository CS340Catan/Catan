package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.communicator.ServerProxy;
import client.model.ClientModel;
import shared.communication.*;

public class ServerProxyTest {

	private ServerProxy serverProxy;
	private ClientModel model;
	
	@Before
	public void setUp() throws Exception {
		//create and populate model
		model = new ClientModel();
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testUpdateModel() {
		//same version
		ClientModel newModel = serverProxy.updateModel(0);
		assertEquals(model, newModel);
		
		//new version
		newModel = serverProxy.updateModel(1);
		assertNotEquals(model, newModel);
	}
	
	@Test
	public void testLogin() {
		UserCredentials credentials = new UserCredentials("Sam", "sam");
		LoginResponse response = serverProxy.Login(credentials);
		assertEquals(credentials.getUsername(), response.getUsername());
		assertEquals(credentials.getPassword(), response.getPassword());

	}
	
	/*
	@Test
	public void testRegister() {
		fail("Not yet implemented");
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}
	*/
}
