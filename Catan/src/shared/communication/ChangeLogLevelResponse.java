package shared.communication;

/**
 * This is the response sent back from the server after a
 * ChangeLogLevelResponse() request the only thing sent back is either the
 * string "Success" or "Invalid request"
 */
public class ChangeLogLevelResponse {

	String response;

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
