package shared.communication;

public class ChatMessage {
	String type = "sendChat";
	int playerIndex;
	String content;

	public ChatMessage(int playerIndex, String content) {
		this.playerIndex = playerIndex;
		this.content = content;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
