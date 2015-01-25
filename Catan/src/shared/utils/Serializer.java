package shared.utils;

import client.model.ClientModel;

import com.google.gson.Gson;

/**
 * Serializes and deserializes from and to JSON.
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
	 * Generic deserializer. This will take in a general JSON string and convert
	 * the string into a Java Object.
	 * 
	 * @Pre jsonString is not null.
	 * @Post A generic object ready to be cast.
	 */
	public static ClientModel deserializeClientModel(String jsonString) {
		Gson gson = new Gson();
		return gson.fromJson(jsonString,ClientModel.class);
	}
}
