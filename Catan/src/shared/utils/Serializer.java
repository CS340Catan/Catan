package shared.utils;

import server.model.ServerModel;
import shared.communication.GameSummary;
import shared.model.Port;
import shared.model.Road;
import shared.model.VertexObject;
import client.model.ClientModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Serializes and deserializes to and from JSON.
 * 
 * @author Seth White
 *
 */
public class Serializer {

	/**
	 * Generic serializer. This will take in a general Java object and convert
	 * that object into a JSON string.
	 * 
	 * @Pre Object is not null.
	 * @Post A deserialized object to be cast to the right class.
	 */
	public static String serialize(Object object) {
		Gson gson = new Gson();
		String serializedObject = gson.toJson(object);
		return serializedObject;
	}

	/**
	 * Client Model deserializer. This will take in a Client Model JSON string
	 * and convert the string into a ClientModel.
	 * 
	 * @Pre jsonString is not null.
	 * @Post A ClientModel.
	 */
	public static ClientModel deserializeClientModel(String jsonString) {
		Gson gson = new Gson();
		ClientModel clientModel = gson.fromJson(jsonString, ClientModel.class);
		if(clientModel.getMap().getRoads() != null){
		for (Road road : clientModel.getMap().getRoads()) {
			road.getLocation().convertFromPrimitives();
		}
		for (VertexObject settlement : clientModel.getMap().getSettlements()) {
			settlement.getLocation().convertFromPrimitives();
		}
		for (VertexObject city : clientModel.getMap().getCities()) {
			city.getLocation().convertFromPrimitives();
		}
		for (Port port : clientModel.getMap().getPorts()) {
			port.convertFromPrimitives();
		}
		}
		return clientModel;
	}

	/**
	 * Client Model deserializer. This will take in a Client Model JSON string
	 * and convert the string into a ClientModel.
	 * 
	 * @Pre jsonString is not null.
	 * @Post A ClientModel.
	 */
	public static ServerModel deserializeServerModel(String jsonString) {
		Gson gson = new Gson();
		ServerModel serverModel = gson.fromJson(jsonString, ServerModel.class);
		for (Road road : serverModel.getMap().getRoads()) {
			road.getLocation().convertFromPrimitives();
		}
		for (VertexObject settlement : serverModel.getMap().getSettlements()) {
			settlement.getLocation().convertFromPrimitives();
		}
		for (VertexObject city : serverModel.getMap().getCities()) {
			city.getLocation().convertFromPrimitives();
		}
		for (Port port : serverModel.getMap().getPorts()) {
			port.convertFromPrimitives();
		}
		return serverModel;
	}

	public static GameSummary[] deserializeGameList(String jsonString) {
		Gson gson = new Gson();
		GameSummary[] gameSummary = gson.fromJson(jsonString, GameSummary[].class);
		return gameSummary;
	}

	public static String serializeClientModel(ClientModel clientModel) {
		Gson gson = new Gson();
		for (Road road : clientModel.getMap().getRoads()) {
			road.getLocation().convertToPrimitives();
		}
		for (VertexObject settlement : clientModel.getMap().getSettlements()) {
			settlement.getLocation().convertToPrimitives();
		}
		for (VertexObject city : clientModel.getMap().getCities()) {
			city.getLocation().convertToPrimitives();
		}
		return gson.toJson(clientModel);
	}

	public static String serializeServerModel(ServerModel serverModel) {
		Gson gson = new Gson();
		if (serverModel.getMap().getRoads() != null) {
			for (Road road : serverModel.getMap().getRoads()) {
				road.getLocation().convertToPrimitives();
			}
			for (VertexObject settlement : serverModel.getMap().getSettlements()) {
				settlement.getLocation().convertToPrimitives();
			}
			for (VertexObject city : serverModel.getMap().getCities()) {
				city.getLocation().convertToPrimitives();
			}
		}
		return gson.toJson(serverModel);
	}

	/**
	 * Generic deserializer for non-move API responses
	 * 
	 * @Pre jsonString is not null.
	 * @Post A generic object ready to be cast.
	 * @param jsonString
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object deserialize(String jsonString, Class classType) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString, classType);
	}

	public static String serializeWithExpose(Object o) {
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		return gson.toJson(o);
	}
}
