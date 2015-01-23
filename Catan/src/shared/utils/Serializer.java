package shared.utils;

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
		return null;
	}

	/**
	 * Generic deserializer. This will take in a general JSON string and convert
	 * the string into a Java Object.
	 * 
	 * @Pre jsonString is not null.
	 * @Post A generic object ready to be cast.
	 */
	public static Object deserialize(String jsonString) {
		return null;
	}
}
