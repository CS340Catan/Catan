// STUDENT-CORE-BEGIN
// DO NOT EDIT THIS FILE
var GameMenu = (function makeMenuView_Class(){
		
	var playerInfo = JSON.parse(decodeURIComponent(Cookies.get("catan.user")));
    
	var JoinStatus = {Join:1,Resume:2,Full:0};
	
	function selectFromArray(array,attributeName, lower){
		var attribs = [];
		for (var count = 0; count < array.length; count++){
			if (array[count][attributeName]) {
				var attrib = array[count][attributeName];
				if (lower) attrib = attrib.toLowerCase();
				attribs.push(attrib);
			}
		}
		return attribs;
	}
	
	function joinable(players){
		var myId = playerInfo.playerID;
		if (players){
			for (var count = 0; count<players.length; count++){
				var pID = players[count].id;
				if (pID == myId)
					return JoinStatus.Resume;
				if (!pID && pID != 0)
					return JoinStatus.Join;
			}
		}
		return JoinStatus.Full;
	}
	
	function attemptJoin(id){
		var submit = document.createElement("div");
		submit.innerHTML='<form id="joingame" method="post" action="/games/join">'+
		'<input type="text" name="id" value="' + id + '">'+
		'<button id="sub" form="joingame" type="submit"></button></form>'
		//document.body.appendChild(submit);
		console.log($(submit).find('#sub').get(0));
		console.log($(submit).find('#sub').click());
		//$.post(	"/games/join","id="+id);
	}	
	
	function getPlayerNames(players){
		return selectFromArray(players,"name");
	}

	function getPlayerColors(players){
		return selectFromArray(
            players.filter(     function(player){ return player.id != playerInfo.playerID    }),
            "color",
            true);
	}
	
	
	var JoinGameView = (function makeJoinGameView(){
		core.defineProperty(JoinGameView.prototype,"controller");
		function JoinGameView(controller,game){
			var ractive = makeJoinRactive(game);
			this.setController(controller);
			var view = this;
		}
		
		JoinGameView.prototype.show = function(){
			$("#joinModal").modal('show');
		}
		
		function makeJoinRactive(game){	
			var colorListString = "Red,Orange,Yellow,Blue,Green,Purple,Puce,White,Brown"
			var players = game.players
			var usedColors = {};
			var pColors = getPlayerColors(players);
			for (var count = 0; count < pColors.length; count++){
				var pColor = pColors[count];
				if (pColor){
					usedColors[pColor] = true;
				}
			}
			var gid = game.id;
			var colors = colorListString.toLowerCase().split(",").map(
				function(color){ return {name:color,taken:(!(!usedColors[color]))};}
			);
			
			var display = document.getElementById("join-game-body");
			var template = ""
				+   '<form id="join-game-form" method="post" action="/games/join"><div id="select-color">'
				+		'<input type="text" name="id" value="{{gid}}" style="display:none">'
				+ 		'{{#colors}}<label class="{{.name}} {{#.taken}}disabled{{/.taken}}">'
				+ 		'<input type="radio" name="color" value="{{.name}}" disabled={{.taken}}><span>{{.name}}</span>'
				+ 		'</label>{{/colors}}' 
				+   '</div></form>';
			var ractive = new Ractive({
				el:display,
				template:template,
				data:{colors:colors,
					  gid:gid}
			})
            
            override_submit(
                    "#join-game-form",
                    function(){console.log("Here");window.location.pathname = "/playerWaiting.html";},
                    function(failure){alert(failure || "Server Error")}
                )
                
			return ractive;
		}
		
		return JoinGameView
	}());
	
	var GameListController = (function makeGameListController(){
		core.defineProperty(GameListController.prototype,'games');
		core.defineProperty(GameListController.prototype,'mainView');
		core.defineProperty(GameListController.prototype,'joinView');
		
		function GameListController(mainView){
			this.mainView = mainView;
			this.mainView.setController(this);
			this.refreshGames();
		}
		
		function annoteJoinable(games){
			return games.map(function(game){
						  var g2 = game;
						  if (joinable(g2.players) ==  JoinStatus.Full){
							  g2.full = true;
						  } else {
							  g2.full = false;
						  }
						  console.log(g2.full);
						  return g2;
						  
					  });
		}
		
		GameListController.prototype.setGames = function(games){
			this.games = games;
			this.mainView.setGames(games);
		}
		
		GameListController.prototype.refreshGames = function(callback){
			var controller = this;
			$.ajax({
				  dataType: "json",
				  url: "/games/list",
				  success: function(games){ 
					  controller.setGames(games)
					  if (callback){
						  callback()					  
					  }
				  }
			});	
		}
        
        GameListController.prototype.addGame = function(game){
			this.games.push(game)
		}
		
		GameListController.prototype.getGameById = function(gid){
			for (var count = 0; count < this.games.length; count++){
				if (this.games[count].id == gid){
					return this.games[count];
				}
			}
			return;
		}
				
		GameListController.prototype.startJoinGameByID = function(gid){
			startJoinGame.call(this,this.getGameById(gid));
		}
		
		
		GameListController.prototype.startJoinGameByIndex = function(gIndex){
			startJoinGame.call(this,this.getGames()[gIndex]);
		}
		
		function startJoinGame(game){
			var players = game.players;
			var joinType = joinable(players);
			//console.log(joinType,JoinStatus.Join);
			if (joinType == JoinStatus.Join || joinType == JoinStatus.Resume ){
				var joinView = new JoinGameView(this,game);
				joinView.show();
			} else {
				//attemptJoin(game.id);
			}				
		}
		
		GameListController.prototype.process = function(jsonData){
			var object = JSON.parse(jsonData),
				content = object.content,
				title = object.title,
				type = object.type,
				callback = function(){};
				
			if (object.type == "REDIRECT"){
				var href = ""+content;
				callback = function(){ document.location.href = href	; };
				content = "Now joining the game.";
			} else {
				callback = function(){ document.location.reload(true) };
				content = content;
			}
			callback();
		}
		
		return GameListController;
		
	}());
	
	var GameListView = (function makeGameListView(){
		core.defineProperty(GameListView.prototype,"controller");
		
		function GameListView(){
			var view = this;
			var menuRactive = makeMenuRactive();
			menuRactive.on('selectGame',function(node){
				// takes in a game id
				view.controller.startJoinGameByIndex(node.node.value);
			});
			// load the data
			
			this.ractive = menuRactive;
		}
		
		GameListView.prototype.setGames = function(games){
			this.ractive.set('games',games);
		}
		
		function makeMenuRactive(){	
			var display = document.getElementById("table1");
			var template = 
				'<table class="table table-striped"><tbody>'+
					'<tr>'+
						'<th>#</th>'+
						'<th>Name</th>'+
						'<th>Current players</th>'+
						'<th>Join</th>'+
					'</tr>'+
					'{{#games:num}}'+
					'<tr>'+
						'<td>{{num}}</td>'+
						'<td>{{.title}}</td>'+
						'<td>{{( displayPlayers (.) )}}</td>'+
						'<td> '+
						'<button href="#" class="btn" value="{{num}}" proxy-click="selectGame" disabled={{.full}}>{{( promptJoin(.) )}}</button></td>'+
					'</tr>'+
					'{{/games}}'+
				'</tbody></table>'
			var data = {
				displayPlayers:function(game){
					var pCount = 0;
					var pNames = "";
					if (game.players){
						var pNames = getPlayerNames(game.players);
						var pNamesString = pNames.join(", ");
					}
					if (pNamesString.length > 0) pNamesString = "  : " + pNamesString;
					return pNames.length+"/4" + pNamesString;
				},
				promptJoin:function(game){
					var joinableType = joinable(game.players);
					if (joinableType == JoinStatus.Join){
						return "Join";
					} else if (joinableType == JoinStatus.Resume){
						return "Re-join";
					} else {
						return "Full";
					}				
				}
			}
			var ractive = new Ractive({
				el:display,
				template:template,
				data:data
			});
			return ractive;
		}
		
		return GameListView;
	}());
	
	
	return {View:GameListView,
			Controller:GameListController};
}());

