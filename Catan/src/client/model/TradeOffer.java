package client.model;

/**
 * Contains the contents of a trade offer, the sender, receiver, and resources.
 * 
 * <pre>
 * <b>Domain:</b>
 * -sender:int index
 * -receiver:int index
 * -offer:ResourceList
 * </pre>
 * 
 * @author Seth White
 *
 */
public class TradeOffer {
	private int sender;
	private int receiver;
	private ResourceList offer;

	/**
	 * Default constructor, requires all fields
	 * 
	 * @Pre no field may be null
	 * @Post result: a TradeOffer
	 * @param sender
	 * @param receiver
	 * @param resourceList
	 */
	public TradeOffer(int sender, int receiver, ResourceList resourceList) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.offer = resourceList;
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public int getReceiver() {
		return receiver;
	}

	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	public ResourceList getResourceList() {
		return offer;
	}

	public void setResourceList(ResourceList resourceList) {
		this.offer = resourceList;
	}
}
