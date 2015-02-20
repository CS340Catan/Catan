package client.devcards;

import java.util.Observable;
import java.util.Observer;

import shared.definitions.ResourceType;
import client.base.*;
import client.model.ClientModel;

/**
 * "Dev card" controller implementation
 */
public class DevCardController extends Controller implements IDevCardController,Observer {

	private IBuyDevCardView buyCardView;
	private IAction soldierAction;
	private IAction roadAction;

	/**
	 * DevCardController constructor
	 * 
	 * @param view
	 *            "Play dev card" view
	 * @param buyCardView
	 *            "Buy dev card" view
	 * @param soldierAction
	 *            Action to be executed when the user plays a soldier card. It
	 *            calls "mapController.playSoldierCard()".
	 * @param roadAction
	 *            Action to be executed when the user plays a road building
	 *            card. It calls "mapController.playRoadBuildingCard()".
	 */
	public DevCardController(IPlayDevCardView view,
			IBuyDevCardView buyCardView, IAction soldierAction,
			IAction roadAction) {

		super(view);

		this.buyCardView = buyCardView;
		this.soldierAction = soldierAction;
		this.roadAction = roadAction;
		ClientModel.getSingleton().addObserver(this);
	}

	public IPlayDevCardView getPlayCardView() {
		return (IPlayDevCardView) super.getView();
	}

	public IBuyDevCardView getBuyCardView() {
		return buyCardView;
	}

//==============================================Begin Implementation Below================================
	
	//===========================BUY CARD VIEW CONTROLLS======================
	//perons needs ore, woord and grain. can i acces the model.contorller? 
	//simply call ClientModelController.canBuyDevCard(); if true, generate proper communcaication class
	//call ServerProxy.buyDevCard(//noparams) 
	@Override
	public void startBuyCard() {
		//disable button if cannot do so
		getBuyCardView().showModal();
	}

	@Override
	public void cancelBuyCard() {
		getBuyCardView().closeModal();
	}

	@Override
	public void buyCard() {
		//model needs to know who is playing
		//just get turn? needs access to the model
		//if(ClientModelController.canBuyDevCard(playerindex))
			//ServerProxy.buyDevCard
		//else -> error message
		getBuyCardView().closeModal();
	}

	//===============================PLAY DEV CARD  CONTROLLS=====================
	@Override
	public void startPlayCard() {

		getPlayCardView().showModal();
	}

	@Override
	public void cancelPlayCard() {

		getPlayCardView().closeModal();
	}

	@Override
	public void playMonopolyCard(ResourceType resource) {
		//had enum use toString? or large switchstatement
		//(if(clientModelController.canPlayMonopolyCard(player index))
			//ServerProxy.playMonopolyCard(new MonopolyCardParams(resource.toString(), playerindex))
	}

	@Override
	public void playMonumentCard() {

	}

	@Override
	public void playRoadBuildCard() {

		roadAction.execute();
	}

	@Override
	public void playSoldierCard() {

		soldierAction.execute();
	}

	@Override
	public void playYearOfPlentyCard(ResourceType resource1,
			ResourceType resource2) {
			
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
