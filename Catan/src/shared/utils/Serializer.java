package shared.utils;

import client.model.ClientModel;
import client.model.Road;
import client.model.VertexObject;

import com.google.gson.Gson;

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
	 * Client Model deserializer. This will take in a Client Model  JSON string and convert
	 * the string into a ClientModel.
	 * 
	 * @Pre jsonString is not null.
	 * @Post A ClientModel.
	 */
	public static ClientModel deserializeClientModel(String jsonString) {
		Gson gson = new Gson();
		ClientModel clientModel = gson.fromJson(jsonString,ClientModel.class);
		for(Road road : clientModel.getMap().getRoads()) {
			road.getLocation().convertFromPrimitives();
		}
		for(VertexObject settlement : clientModel.getMap().getSettlements()){
			settlement.getLocation().convertFromPrimitives();
		}
		for(VertexObject city : clientModel.getMap().getCities()){
			city.getLocation().convertFromPrimitives();
		}
		return clientModel;
	}
	public static String serializeClientModel(ClientModel clientModel){
		Gson gson = new Gson();
		for(Road road : clientModel.getMap().getRoads()){
			road.getLocation().convertToPrimitives();
		}
		for(VertexObject settlement : clientModel.getMap().getSettlements()){
			settlement.getLocation().convertToPrimitives();
		}
		for(VertexObject city : clientModel.getMap().getCities()){
			city.getLocation().convertToPrimitives();
		}
		return gson.toJson(clientModel);
	}
	/**
	 * Generic deserializer for non-move API responses
	 * @Pre jsonString is not null.
	 * @Post A generic object ready to be cast.
	 * @param jsonString
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object deserialize (String jsonString, Class classType) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString,classType);
	}
}
