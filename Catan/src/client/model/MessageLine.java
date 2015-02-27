package client.model;

/**
 * One line of a message with the name of the player who sent it
 * 
 * <pre>
 * <b>Domain:</b>
 * -message:String
 * -source:String
 * </pre>
 * 
 * @author Seth White
 *
 */
public class MessageLine {
	private String message;
	private String source;

	/**
	 * Default constructor, requires all fields
	 * 
	 * @Pre no field may be null
	 * @Post result: a MessageLine
	 * @param message
	 * @param source
	 */
	public MessageLine(String message, String source) {
		super();
		this.message = message;
		this.source = source;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
