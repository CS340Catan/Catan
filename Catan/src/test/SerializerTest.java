package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import client.model.ClientModel;
import shared.communication.LoginResponse;
import shared.communication.Resource;
import shared.utils.Serializer;

public class SerializerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSerialize() {
		Resource resource = new Resource("ORE");
		String jsonString = Serializer.serialize(resource);
		assertEquals("{\"name\":\"ORE\"}", jsonString);
	}

	@Test
	public void testDeserializeClientModel() {
		String jsonString = "{\"bank\":{\"brick\":\"10\",\"ore\":\"10\",\"sheep\":\"10\",\"wheat\":\"10\",\"wood\":\"10\"},\"chat\":{\"lines\":[{\"message\":\"test\",\"source\":\"test\"}]},\"log\":{\"lines\":[{\"message\":\"test\",\"source\":\"test\"}]},\"map\":{\"hexes\":[{\"location\":{\"x\":\"10\",\"y\":\"10\"},\"resource\":\"test\",\"15\":\"10\"}],\"ports\":[{\"resource\":\"test\",\"location\":{\"x\":\"10\",\"y\":\"10\"},\"direction\":\"test\",\"ratio\":\"10\"}],\"roads\":[{\"owner\":\"2\",\"location\":{\"x\":\"10\",\"y\":\"10\",\"direction\":\"test\"}}],\"settlements\":[{\"owner\":\"2\",\"location\":{\"x\":\"10\",\"y\":\"10\",\"direction\":\"test\"}}],\"cities\":[{\"owner\":\"2\",\"location\":{\"x\":\"10\",\"y\":\"10\",\"direction\":\"test\"}}],\"radius\":\"10\",\"robber\":{\"x\":\"10\",\"y\":\"10\"}},\"players\":[{\"cities\":\"15\",\"color\":\"test\",\"discarded\":\"true\",\"monuments\":\"15\",\"name\":\"test\",\"newDevCards\":{\"monopoly\":\"15\",\"monument\":\"15\",\"roadBuilding\":\"15\",\"soldier\":\"15\",\"yearOfPlenty\":\"15\"},\"oldDevCards\":{\"monopoly\":\"15\",\"monument\":\"15\",\"roadBuilding\":\"15\",\"soldier\":\"15\",\"yearOfPlenty\":\"15\"},\"playerIndex\":\"2\",\"playedDevCard\":\"true\",\"playerID\":\"10\",\"resources\":{\"brick\":\"10\",\"ore\":\"10\",\"sheep\":\"10\",\"wheat\":\"10\",\"wood\":\"10\"},\"roads\":\"15\",\"settlements\":\"10\",\"soldiers\":\"10\",\"victoryPo10s\":\"10\"}],\"tradeOffer\":{\"sender\":\"10\",\"receiver\":\"10\",\"offer\":{\"brick\":\"10\",\"ore\":\"10\",\"sheep\":\"10\",\"wheat\":\"10\",\"wood\":\"10\"}},\"turnTracker\":{\"currentTurn\":\"2\",\"status\":\"test\",\"longestRoad\":\"2\",\"largestArmy\":\"2\"},\"version\":\"2\",\"winner\":\"2\"}";
		ClientModel clientModel = Serializer.deserializeClientModel(jsonString);
//		System.out.println(Serializer.serialize(clientModel));
		assertEquals("test", clientModel.getLog().getLines()[0].getMessage());
	}
	@Test
	public void testGenericDeserializer(){
		LoginResponse loginResponse = new LoginResponse("test", "testpass");
		String json = Serializer.serialize(loginResponse);
		Object loginResponseObj = Serializer.genericDeserialize(json);
		System.out.println(loginResponseObj);
		LoginResponse deserializedLoginResponse = (LoginResponse) loginResponseObj;
		assertEquals("testpass",deserializedLoginResponse.getPassword());
	}
}
