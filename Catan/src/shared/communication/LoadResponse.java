package shared.communication;

public class LoadResponse {
	boolean success;
	
	public LoadResponse(boolean success){
		this.success = success;
	}
	
	public boolean wasSuccessful(){
		return success;
	}
	
	public void setSuccess(boolean success){
		this.success = success;
	}
}
