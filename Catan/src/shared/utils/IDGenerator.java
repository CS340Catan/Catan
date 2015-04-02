package shared.utils;

public class IDGenerator {
	private static int lastAssignedPlayerID = 0;
	private static int lastAssignedGameID = 0;

	public static int generatePlayerID() {
		lastAssignedPlayerID += 1;
		return lastAssignedPlayerID;
	}

	public static int generateGameID() {
		lastAssignedGameID += 1;
		return lastAssignedGameID;
	}

}
