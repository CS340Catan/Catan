package client.model;

import java.util.Observable;

public class Notifier extends Observable {
	public void modelUpdated(){
		this.setChanged();
		this.notifyObservers();
	}
}
