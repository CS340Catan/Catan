package shared.communication;

import java.util.List;

/**
 * This is the class returned by the server when the function listAI() is called
 * Domain:
 * 	AIList:list
 * DomainConstraints
 * 	For now it can only contain "LARGEST_ARMY"
 *
 */
public class ListAIResponse {
	
	List<String> AITypes;

	public List<String> getAITypes() {
		return AITypes;
	}

	public void setAITypes(List<String> aITypes) {
		AITypes = aITypes;
	}

}
