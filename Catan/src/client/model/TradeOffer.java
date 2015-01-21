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
	private ResourceList resourceList;

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
		return resourceList;
	}

	public void setResourceList(ResourceList resourceList) {
		this.resourceList = resourceList;
	}
}
