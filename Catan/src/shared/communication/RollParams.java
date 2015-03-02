package shared.communication;

public class RollParams {
	String type = "rollNumber";
	int playerIndex;
	int number;

	public RollParams(int playerIndex, int number) {
		this.playerIndex = playerIndex;
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPlayerIndex() {
		return playerIndex;
	}

	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
