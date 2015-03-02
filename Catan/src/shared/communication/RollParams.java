package shared.communication;

public class RollParams {
	String type = "rollNumber";
	int playerIndex;
	int number;
	
	public RollParams(int playerIndex, int number){
		this.playerIndex = playerIndex;
		this.number = number;
	}
}
