// STUDENT-CORE-BEGIN
// DO NOT EDIT THIS FILE
/**
The namespace for the turn tracker

@module catan.turntracker
@namespace turntracker
**/

var catan = catan || {};
catan.turntracker = catan.turntracker || {};

catan.turntracker.View = (function turntracker_namespace(){

	var Definitions = catan.definitions;
	var DisplayElement = catan.definitions.DisplayElement;
	
	var BUTTON_STYLE = " three-quarter tall";
	var MAX_PLAYERS = 4;

	/**
		 * A view that is used for displaying the current state of the game:
		 * 1) a table that displays each player's basic information and highlights the current turn,
		 * 2) a button to display the game state		
		@class TurnTrackerView 
		@constructor
	**/
	var TurnTrackerView = (function TurnTrackerView_Class(){
		
		function TurnTrackerView(){
			this.setDisplayElems(new Array());
		};
        
		core.defineProperty(TurnTrackerView.prototype,"controller");
		core.defineProperty(TurnTrackerView.prototype,"stateElem");
		core.defineProperty(TurnTrackerView.prototype,"DisplayElems");
			
		/**
		 * attaches the controller to the view and builds the view on the page.
		 @method setController
		 @param {turntracker.Controller} controller
		 @return void
		 */
		TurnTrackerView.prototype.setController = function(controller){
			this.controller = controller;
			buildView.call(this);
		}
		
		/**
		 * sets the color to display for the local player.
		 @method setClientColor
		 @param {String} color the players specified color
		 @return void
		 */
		TurnTrackerView.prototype.setClientColor = function(color){
			var header = $(".myrow.header").get(0);
			header.className = header.className + " " + color.toLowerCase();

            if(this.getStateElem() != undefined)
				this.getStateElem().setStyle(color+BUTTON_STYLE);
            
            this.clientColor = color;
		}
		
		/**
		 * sets the name and color of a player in the player table.
		 * @method initializePlayer
		 * @param {int} playerNumber The player to update
		 * @param {String} playerName the player's name
		 * @param {String} playerColor the player's color
		 * @return void
		 */
		TurnTrackerView.prototype.initializePlayer = function(playerNumber, playerName, playerColor){
			var displayElem = new DisplayElement.TurnTrackerPlayerElement(playerName, playerColor);
			this.getDisplayElems()[playerNumber] = (displayElem);
			
		}

		/**
		 * updates the information for a player in the player table.
		 * @method updatePlayer
		 * @param {Object} updates an object with the updates for a single player
		 * @param {int} updates.playerIndex the player to update
		 * @param {int} updates.score the score
		 * @param {Boolean} updates.highlight whether the player's display box should be highlighted
		 * @param {Boolean} updates.army whether the player has the largest army award
		 * @param {Boolean} updates.road whether the player has the longest road award
		 * @return void
		 */
		TurnTrackerView.prototype.updatePlayer = function(updates){
			var playerDisplay = this.getDisplayElems()[updates.playerIndex];
				playerDisplay.setScore(updates.score);
				playerDisplay.setHighlight(updates.highlight);
				playerDisplay.setLargestArmy(updates.army);
				playerDisplay.setLongestRoad(updates.road);
		}
		
		/**
		 * updates the game state button's message and enabled state
		 * @method updateStateView
		 * @param {Boolean} enable whether to enable the game state button 
		 * @param {String} message the message to display on the game state button
		 * @return void
		 */
		TurnTrackerView.prototype.updateStateView = function(enable, message){
			var stateElem = this.getStateElem();
			
			stateElem.setMessage(message);
			
			if(enable)
				stateElem.enable();
			else
				stateElem.disable();
		}
		
		var buildView = function(){
			
			var trackerArea = document.getElementById(Definitions.PageViewIDs.trackerArea);
			var gameStateArea = document.getElementById(Definitions.PageViewIDs.gameStateArea);
		
			var action = core.makeAnonymousAction(this, this.endTurn);
			var stateElem = new DisplayElement.ButtonArea(action);
				
				gameStateArea.appendChild(stateElem.getView());
				stateElem.disable();
				if(this.clientColor != undefined)
					stateElem.setStyle(this.clientColor+BUTTON_STYLE);
				this.setStateElem(stateElem);
			
			for(index in this.getDisplayElems()){
				var displayElem = this.getDisplayElems()[index];
				if(displayElem != undefined)
					trackerArea.appendChild(displayElem.getView());
			}
		}
		TurnTrackerView.prototype.endTurn = function(){
			this.getController().endTurn();
		}
		
		return TurnTrackerView;		
		
	}());
	
	return TurnTrackerView;
	
}());



