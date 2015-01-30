package shared.communication;

import java.util.ArrayList;

public class ListAIResponse {
	ArrayList<String> AItypes;
	
	public ListAIResponse(ArrayList<String> types){
		this.AItypes = types;
	}
	
	public ArrayList<String> getAITypes(){
		return this.AItypes;
	}
	
	public void setAITypes(ArrayList<String> types){
		this.AItypes = types;
	}
}
