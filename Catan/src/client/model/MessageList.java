package client.model;
/**
 * A list of lines that make up a message
 *<pre>
 * <b>Domain:</b>
 * -lines:[MessageLine]
 * </pre>
 * @author Seth White
 *
 */
public class MessageList {
	private MessageLine[] lines;

	public MessageLine[] getLines() {
		return lines;
	}

	public void setLines(MessageLine[] lines) {
		this.lines = lines;
	}	
}
