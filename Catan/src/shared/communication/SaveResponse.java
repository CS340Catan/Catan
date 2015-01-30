package shared.communication;

public class SaveResponse {
	boolean success;
	
	public SaveResponse(boolean success){
		this.success = success;
	}
	
	public boolean wasSuccessful(){
		return this.success;
	}
	
	public void setSuccess(boolean success){
		this.success = success;
	}
}
