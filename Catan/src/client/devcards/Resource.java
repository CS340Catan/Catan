package client.devcards;

/**
 * A resource with a constrained name
 * 
 * <pre>
 * <b> Domain: </b>
 * name:String
 * </pre>
 * 
 * @author Seth White
 *
 */
public class Resource {
	private String name;

	/**
	 * Constrains name
	 * 
	 * @Pre none
	 * @Post result:resource
	 * @param name
	 */
	public Resource(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
