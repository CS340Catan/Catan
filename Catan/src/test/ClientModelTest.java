package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import client.model.ClientModel;
import client.model.ClientModelController;

public class ClientModelTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void enumTest() {
		EdgeDirection thing = EdgeDirection.NorthEast;
		EdgeDirection otherThing = EdgeDirection.NorthEast;
		switch(thing){
		case NorthEast:
			System.out.println("it works!");
		default:
			break;
		}
		System.out.println(thing.equals(otherThing));
	}

}
