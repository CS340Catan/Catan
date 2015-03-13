package shared.communication;

/**
 * This class is for the server call addAI(), it transports the requested AI to
 * be added Domain: AIType:string DomainConstraint: currently, the only string
 * allowed is LARGEST_ARMY
 *
 */
public class AddAIParams {

	String AIType;

	public String getAIType() {
		return AIType;
	}

	public void setAIType(String aIType) {
		AIType = aIType;
	}

}
