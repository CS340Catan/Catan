package shared.communication;

public class JoinResponse {
	boolean success;
	
	public JoinResponse(boolean success){
		this.success = success;
	}
	
	public boolean wasSuccessful(){
		return success;
	}
	
	public void setSuccess(boolean success){
		this.success = success;
	}
}
