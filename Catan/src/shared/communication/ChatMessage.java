package shared.communication;

public class ChatMessage {
	String type = "sendChat";
	int playerIndex;
	String content;
	
	public ChatMessage(int playerIndex, String content){
		this.playerIndex = playerIndex;
		this.content = content;
	}
}
