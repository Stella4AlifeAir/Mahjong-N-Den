package com.pheephoo.mjgame.ui;
import com.pheephoo.mjgame.engine.*;
import com.pheephoo.utilx.Common;

import java.util.*;

/*
 * 
 * @author Samuel
 * @version 1.00, 24/04/2005
 */
public class AIPlayer implements GameListenerIF {

	int debugLevel=10000;//only for debuggin

	public int demoMode;
	
	EngineIF engine;
    Player player;
	WinEngine wineng=new WinEngine();
    
	TileCollection discardedTiles = new TileCollection(TileCollection.MAXTILESONTABLE);
	TileCollection otherVisibleTiles1= new TileCollection(TileCollection.MAXVISIBLESIZE);
	TileCollection otherVisibleTiles2= new TileCollection(TileCollection.MAXVISIBLESIZE);
	TileCollection otherVisibleTiles3= new TileCollection(TileCollection.MAXVISIBLESIZE);
	TileCollection discardCandidates= new TileCollection(30);
    

    Vector actions;//keep track of action

    int currentSelection;//dummy parameter as we need to implement GameListenerIF in various methods
    
    int groupCount;// number of special visible tiles form, eg. chow group, etc    
    int pictureNum;
    String nick;
    
    boolean flagAllKongGame;
    
    int numOfMeld;    
    
    public AIPlayer(int p_pictureNum, String p_nick) {
    	wineng= new WinEngine();
    	pictureNum=p_pictureNum;
    	nick = p_nick;
    }
    
    public void onTileReceived(Tile newtile, int numTileOnDeck, int turn) {
    	if (turn!=player.getWind()) {
    		return;
    	}
    	//debug(0,"\nAIPlayer.onTileReceived()");    	
        //debug(100,"\nCOMPUTER PLAYER "  + player.getWind() + "**received " + newtile.type + "_" + newtile.value);
        this.t_printOwnTiles(100);
    	player.newtile=newtile;
    	player.discardedtile=null;    	
    }
    
    
    /*
	 * receive the tile
	 * 
	 * call the private method chooseTileToThrow()
	 */
    public void onTurn() {
        //this.t_printOwnTiles(player.getTiles());
    	if (player.newtile!=null) {
        	player.receiveTile(player.newtile);
        	player.newtile=null;
        }
        Tile tile= chooseTileToThrow();
        player.getTiles().removeTile(tile);
        //debug(3,"\n**AIPlayer() "+ player.getWind() + " onTurn()::: discard " + tile.type + "_" + tile.value);
    	engine.doDiscard(tile);
    }
    
    private void doAllKong() {
    //throw all the non pair and non pong color
    	//update candidate for discard
    	//debug(2,"doAllKong");
    	for (int i=0;i<player.concealedTiles.size();i++) {
    		Tile temp= player.concealedTiles.getTileAt(i);
            ////debug(10,"checking Tile="+temp);
            //t_printDiscardCandidates(10);
    		if (temp.groupType==0 ) {
            	if (temp.type==3 && temp.value!=engine.getCurrentWind()+1 
            			&& temp.value!=player.getWind()+1) {
            		temp.groupType=11;
            		discardCandidates.addTileByGroup(temp);
            	}
            	//chow probability
            	else if (temp.value==9 && temp.groupType!=9){
            		temp.groupType=21;
            		discardCandidates.addTileByGroup(temp);
            	}
            	else if (temp.value==1 && temp.type!=3 && temp.type!=4 && temp.groupType!=9){
            		temp.groupType=22;
            		discardCandidates.addTileByGroup(temp);
            	}
            	else if (temp.type==4){
            		temp.groupType=31;
            		discardCandidates.addTileByGroup(temp);
            	}
            	else if (temp.type==3 && temp.value==engine.getCurrentWind() ){
            		temp.groupType=41;
            		discardCandidates.addTileByGroup(temp);
            	}
            	else if (temp.type==3  ){
            		temp.groupType=42;
            		discardCandidates.addTileByGroup(temp);
            	}
            }
    	}
    }
    

    /*
     * write discardCandidates
     * Easiest game to play
     */
    private void doZeroDouble() {
    	//update candidate for discard
    	TileCollection col=player.concealedTiles;
    	int size=col.size();
    	
        for (int i=0;i<size;i++) {
    		Tile temp= col.getTileAt(i);
            ////debug(10,"checking Tile="+temp);
            //t_printDiscardCandidates(10);
    		if (temp.groupType==0 || temp.groupType==9) {
            	if (temp.type==3 && temp.value!=engine.getCurrentWind()+1 
            			&& temp.value!=player.getWind()+1) {
            		temp.groupType=11;
            		discardCandidates.addTileByGroup(temp);
            	}
            	//chow probability
            	else if (temp.value==9 && temp.groupType!=9){
            		temp.groupType=21;
            		discardCandidates.addTileByGroup(temp);
            	}
            	else if (temp.value==1 && temp.type!=3 && temp.type!=4 && temp.groupType!=9){
            		temp.groupType=22;
            		discardCandidates.addTileByGroup(temp);
            	}
            	else if (temp.type==4){
            		temp.groupType=31;
            		discardCandidates.addTileByGroup(temp);
            	}
            	else if (temp.type==3 && temp.value==engine.getCurrentWind() ){
            		temp.groupType=41;
            		discardCandidates.addTileByGroup(temp);
            	}
            	else if (temp.type==3  ){
            		temp.groupType=42;
            		discardCandidates.addTileByGroup(temp);
            	}

            	//check for chow possibility            	
            	else if (temp.groupType==9 && temp.value==1) {
            		Tile temptile=new Tile(temp.type,temp.value+2 );
            		int count= discardedTiles.getNumOfTile(temptile)
						+otherVisibleTiles1.getNumOfTile(temptile)
						+otherVisibleTiles2.getNumOfTile(temptile)+
						otherVisibleTiles3.getNumOfTile(temptile)+
						player.visibleTiles.getNumOfTile(temptile)+
						col.getNumOfTile(temptile);
            		
            		if (count==4) {
    		        	//debug(10,"not possible for melding a chow for terminal 1");
    		        	temp.groupType=51;
    		        	discardCandidates.addTileByGroup(temp);
            		}
            		else {
    		        	temp.groupType=61;
    		        	discardCandidates.addTileByGroup(temp);
            		}
            		i++;
            	}
            	else if (temp.groupType==9 && temp.value==8) {
            		Tile temptile=new Tile(temp.type,temp.value-1 );
            		int count= discardedTiles.getNumOfTile(temptile)
						+otherVisibleTiles1.getNumOfTile(temptile)
						+otherVisibleTiles2.getNumOfTile(temptile)+
						otherVisibleTiles3.getNumOfTile(temptile)+
						player.visibleTiles.getNumOfTile(temptile)+
						col.getNumOfTile(temptile);
            		
            		if (count==4) {
    		        	//debug(10,"not possible for melding a chow for terminal 9");
    		        	temp.groupType=52;
    		        	discardCandidates.addTileByGroup(temp);
                		i++;
            		}
            		else {
            			i++;
            			if (i>=size) {
            				break;
            			}
                		temp= player.concealedTiles.getTileAt(i);
    		        	temp.groupType=62;
    		        	discardCandidates.addTileByGroup(temp);
            		}
            	}
            	else if (temp.groupType==9 ) {
            		int possibility=2;
            		
            		Tile temptile=new Tile(temp.type,temp.value-1 );
            		int count= discardedTiles.getNumOfTile(temptile)
						+otherVisibleTiles1.getNumOfTile(temptile)
						+otherVisibleTiles2.getNumOfTile(temptile)+
						otherVisibleTiles3.getNumOfTile(temptile)+
						player.visibleTiles.getNumOfTile(temptile)+
						player.concealedTiles.getNumOfTile(temptile);
            		
            		if (count==4) {
    		        	possibility--;
            		}
            		temptile=new Tile(temp.type,temp.value+2 );            		
            		count= discardedTiles.getNumOfTile(temptile)
						+otherVisibleTiles1.getNumOfTile(temptile)
						+otherVisibleTiles2.getNumOfTile(temptile)+
						otherVisibleTiles3.getNumOfTile(temptile)+
						player.visibleTiles.getNumOfTile(temptile)+
						player.concealedTiles.getNumOfTile(temptile);
            		if (count==4) {
    		        	possibility--;
            		}

            		if (possibility==0) {
    		        	temp.groupType=53;
    		        	//debug(10,"not possible for melding a chow");
    		        	discardCandidates.addTileByGroup(temp);
            		}
            		else {
    		        	temp.groupType=63;
    		        	discardCandidates.addTileByGroup(temp);
            		}
            		i++;
            	}
            	//for chow possibility in middle
            	else if (temp.groupType==0 && temp.type!=3 && temp.type!=4){
            		Tile temptile = new Tile(temp.type,temp.value+2);
            		//debug(10,"temp="+temp+" ;temptile="+temptile);
            		Tile temptile3=null;
            		if (col.getNumOfTile(temptile,0)==0) {
    		        	//debug(10,"isolated tile");
    		        	temp.groupType=61;
    		        	discardCandidates.addTileByGroup(temp);    		        	
            		}
            		//check for the middle of the tile.... eg. got 11   13
            		else {
            			Tile temptile2=new Tile(temp.type,temp.value+1);
                		int count= discardedTiles.getNumOfTile(temptile2)
						+otherVisibleTiles1.getNumOfTile(temptile2)
						+otherVisibleTiles2.getNumOfTile(temptile2)+
						otherVisibleTiles3.getNumOfTile(temptile2)+
						player.visibleTiles.getNumOfTile(temptile2)+
						player.concealedTiles.getNumOfTile(temptile2);

                		if (count==4) {
        		        	//debug(10,"not possible for melding a chow for a middle tile");
        		        	temp.groupType=54;
        		        	discardCandidates.addTileByGroup(temp);
                		}
                		else {
        		        	temp.groupType=71;
        		        	discardCandidates.addTileByGroup(temp);
                			for (int j=i+1;j<size;j++) {
                				temptile3=player.concealedTiles.getTileAt(j);
                				if (temptile.equals(temptile3) && temptile3.groupType==0) {
                					temptile3.groupType=8;
                					//debug(10, "its possible to make a meld. mark for non deletion");
                				}
                			}
                		}
            		}//end else if
            	}

            }
    	}
    	
    }
    
    
    /*
     * temporary method to order the player to throw specific tiles
     */
    private Tile chooseTileToThrow() {
    	Tile tile=null;
        discardCandidates.removeAll();
    	wineng.loadTiles(player.concealedTiles,player.visibleTiles, engine.getCurrentWind(),player.getWind());
    	wineng.checkOwnTile(0);

        int collectedDouble=wineng.numberOfDragon+wineng.numberOfWind;
        if (collectedDouble==0) {
        	//look for potential pair
        	if (wineng.isDragonPair || (wineng.isWindPair && wineng.notPingwuPair))
        		collectedDouble=1;
        }
        
        int neededDouble=((EngineFA)engine).minDouble-collectedDouble;

        if (wineng.countPair>=3) {
        	doAllKong();
        }
        else if (neededDouble<=0) {
        	//debug(2,"Need zero double");
        	flagAllKongGame=false;
        	doZeroDouble();
        }
        else if (neededDouble<=1){
        	//debug(2,"Need 1 double");
        	wineng.loadTiles(player.concealedTiles,player.visibleTiles, engine.getCurrentWind(),player.getWind());
        	wineng.checkOwnTile(1);
        	if (wineng.countPair>=3) {
            	flagAllKongGame=true;
            	doAllKong();
        	}
        	else {
        		//do fullcolor
        		doAllKong();
        	}
        	
        	//do all kong
        	//can chow if there if its the same color
        }
        else {//do all kong and fullcolor
        	
        }
    	
    	t_printOwnTiles(100);
        t_printDiscardCandidates(100);
        
        if (discardCandidates.size()>0) {
        	return discardCandidates.getTileAt(0);
        }

        
        //debug(20,"no discardCandidates.");

        //get random number
        int randomNum= Common.getRandomInt(0,player.concealedTiles.size()-1);
        tile= player.concealedTiles.getTileAt(randomNum);
        int quit=0;
        while (tile.groupType!=0 && quit<20) {
        	randomNum= Common.getRandomInt(0,player.concealedTiles.size()-1);
        	tile= player.concealedTiles.getTileAt(randomNum);
        	quit++;
        }

        return tile;
    }

    public void setDemoMode(int p_demoMode) {
    	demoMode=p_demoMode;
    }

    
    public void onTileDiscarded(Tile tile, int turn, int numTileOnDeck) {
    	player.discardedtile=tile;
    	//debug(2,"discardedtiles size="+discardedTiles.size());
    	discardedTiles.addTile(tile);
    }

    public void onJoin(int tableno, int position, int noOfPlayer, EngineIF _engine, Player _player, int started) {
    	engine=_engine;
        player=_player;
        player.nick=nick;
        player.pictureNum=pictureNum;
    }
    
    public void onOtherJoin(int _pos, int _noOfPlayer, int p_picNum, String p_nick) {}

    
    public void onNotice(Vector p_actions){
    	//debug(0,"AIPlayer.onNotice():::*Player " + player.getWind()+ " ");
		this.actions=p_actions;
		SpecialAction action= (SpecialAction) actions.firstElement();		

		if (action.type==GameAction.TYPE_KONG||action.type==GameAction.TYPE_PONG)
			processInputKong();
		else if (action.type==GameAction.TYPE_WIN)
			processInputWin();
    }

    public void onNotice2(Vector p_actions){

        //debug(2,"\nCOMPUTER PLAYER "  + player.getWind() + ":: onnotice2" );
        //debug(2,"tilediscarded="+player.discardedtile );

		this.actions=p_actions;
		SpecialAction action= (SpecialAction) actions.firstElement();		
		//debug(3,"action=" + action.type);
		
		//base on input mode, it will call different handler for processing kong/pong/chow
		if (action.type==GameAction.TYPE_KONG||action.type==GameAction.TYPE_PONG)
			processInputKong2();
		else if (action.type==GameAction.TYPE_CHOW) 
			processInputChow();
		else if (action.type==GameAction.TYPE_WIN || action.type==GameAction.TYPE_WIN_ONROBBING)
			processInputWin();
    }
    
    public void onTileTaken(TileCollection otherCol, int size, int playerTurn, boolean isDiscardedTile, Tile tempDiscard) {
    	//debug(3,"AIPlayer.onTileTaken()::: tile " + tempDiscard + " is removed");
    	discardedTiles.removeTile(tempDiscard);
    	
    	if (playerTurn<player.getWind())
    		playerTurn=playerTurn+4;
    	if (playerTurn==player.getWind()+1) {
    		otherVisibleTiles1.removeAll();
        	for (int i=0;i<otherCol.size();i++) {
    			Tile temptile=  otherCol.getTileAt(i);
    			otherVisibleTiles1.addTile(temptile);    		
        	}
    	}
    	else if (playerTurn==player.getWind()+2) {
    		otherVisibleTiles2.removeAll();
        	for (int i=0;i<otherCol.size();i++) {
    			Tile temptile=  otherCol.getTileAt(i);
    			otherVisibleTiles2.addTile(temptile);    		
        	}
    	}
    	else if (playerTurn==player.getWind()+3) {
    		otherVisibleTiles3.removeAll();
        	for (int i=0;i<otherCol.size();i++) {
    			Tile temptile=  otherCol.getTileAt(i);
    			otherVisibleTiles3.addTile(temptile);    		
        	}
    	}
    	
    }
             
    public void onStart(TileCollection _col, TileCollection _visibleTiles){
    	//debug(1,"AIPlayer.onStart()");
    	discardedTiles=new TileCollection(TileCollection.MAXTILESONTABLE);
    	groupCount=0;
    	//debug(3,"\n\nAI PLAYER " + player.getWind() + "::");
    
    	int size=_visibleTiles.size();
    	int group=-1;
    	Tile tile;
    	for (int i=0;i<size;i++) {
    		tile=  player.visibleTiles.getTileAt(i);
    		if (group!= tile.group  ) {
    			numOfMeld++;
    			group=tile.group;
    		}
    	}
    	//debug(3,"onStart.numOfmeld="+ numOfMeld);
    	groupCount=numOfMeld;
        engine.doConfirm();
    }
    
    
    private void processInputKong() {
    	//debug(0,"AI.processInputKong()");
    	SpecialAction temp=(SpecialAction) actions.elementAt(0);
    	Tile temptile=null;
    	
    	if (!temp.isVisible)
    		temptile=doKongHelper(currentSelection);
    	else
    		temptile=doKongHelper1a(currentSelection,player.newtile);

    	numOfMeld++;
    	engine.doConcealedKongResponse(true, temptile);
    	if (debugLevel>=2)
    		printVisible();
    }
    
    
    private void processInputKong2() {
    	//debug(0,"AI.processInputKong2()");
    	wineng.loadTiles(player.concealedTiles,player.visibleTiles, engine.getCurrentWind(),player.getWind());
    	wineng.checkOwnTile(1);
    	//debug(0,"finish checking numofstep");
    	//debug(100,"countPair="+wineng.countPair);
    	
    	if (discardedTiles.size()<20)  {
    		//if there is more than 1 eye
    		if (wineng.countPair>1) {
    			//debug(100,"More then one pair.. we can pong");
        		if (player.discardedtile.type==4 || 
        				(player.discardedtile.type==3 &&
        						player.discardedtile.value==player.getWind())  ||
		        		(player.discardedtile.type==3 &&
		        				player.discardedtile.value==engine.getCurrentWind() ) ){ 
        			player.newtile=null;
        			doKongHelper2(currentSelection);        	
        		}
        		else {
        			player.newtile=null;
        			doKongHelper2(currentSelection);        		
        		}
    		}
        	else {
        		engine.doSpecialResponse(GameAction.TYPE_PONG,false, player.getWind());
        	}

    	}
    	else if (wineng.countPair>1){
			//debug(100,"More then one pair.. we can pong");
			player.newtile=null;
			doKongHelper2(currentSelection);
    	}
    	else {
    		engine.doSpecialResponse(GameAction.TYPE_PONG,false, player.getWind());
    	}
    }
    
    private void processInputChow() {
    	//debug(0,"AI.processInputChow()");
//    	System.out.println("AI.processInputChow()");
    	wineng.loadTiles(player.concealedTiles,player.visibleTiles, engine.getCurrentWind(),player.getWind());
    	wineng.checkOwnTile(0);

    	//ignore the chow possibilities if there is concealed chow
    	if (flagAllKongGame) {
        	//debug(0,"ignoring the chow.. allkong game");
			engine.doSpecialResponse(GameAction.TYPE_PONG,false, player.getWind());    			    		
			return;
    	}
    	
    	if (discardedTiles.size()<20)  {
        	//debug(0,"ignoring the chow");
			engine.doSpecialResponse(GameAction.TYPE_PONG,false, player.getWind());
    	}
    	else {
    		SpecialAction action = (SpecialAction) actions.elementAt(0);
        	
    		Tile temptile=player.concealedTiles.getTileAt(action.startTilePosition);
    		Tile temptile2=player.concealedTiles.getTileAt(action.endTilePosition);
    		if (temptile.groupType==3 || temptile2 .groupType==3) {
    			//debug(2,"ignore the chow");//coz it will break our current configuration
    			engine.doSpecialResponse(GameAction.TYPE_PONG,false, player.getWind());    			
    		}
    		else if (temptile.groupType==1 && wineng.countPair>1) {
    	    	player.newtile=null;
    			doChowHelper(currentSelection);    			
    		}
    		else if (temptile.groupType==0 && temptile2.groupType==0) {
    	    	player.newtile=null;
    			doChowHelper(currentSelection);
    		}
    		else {
    			//debug(2,"ignore the chow");//coz it will break our current configuration
    			engine.doSpecialResponse(GameAction.TYPE_PONG,false, player.getWind());    			
    		}
    	}
    	//debug(0,"finish process inputchow");

    }

    private void doKongHelper2(int position) {
    	//debug(0,"AI.doKongHelper2()");
		//inputMode=2;
    	int counter=0;    	
    	SpecialAction action = (SpecialAction) actions.elementAt(0);
    	//debug(3,"Player " + player.getWind() + " kong!! " + "tiles"+ action.startTilePosition+"-"+action.endTilePosition);
    	
    	for (int i=action.startTilePosition;i<=action.endTilePosition;i++) {
        	Tile temptile=player.concealedTiles.getTileAt(i);
        	temptile.group=groupCount;
        	temptile.groupType=2;
        	//debug(0,"adding tile: "+ temptile);
        	player.visibleTiles.addTileNoSorted(temptile);
        	counter++;
    	}

    	//remove from original concealedTiles
    	for (int i=action.startTilePosition;i<=action.endTilePosition;i++) {	                	
    		player.concealedTiles.removeTile(player.concealedTiles.getTileAt(action.startTilePosition));
    	}
    	player.discardedtile.group=groupCount;
    	player.discardedtile.groupType=2;
    	player.visibleTiles.addTileNoSorted(player.discardedtile);
        
    	groupCount++;	

    	numOfMeld++;
    	engine.doSpecialResponse(action.type,true, player.getWind());
    	if (action.type==GameAction.TYPE_KONG) {
    		//debug(100,"AI PLAYER KONG!!!!!!!!!!!!!!!!!!");
    	}
    	if (debugLevel>=2)
    		printVisible();
    	onTurn();

    }

    
    private void doChowHelper(int position) {
    	//debug(0,"AI.doChowHelper()");
    	//debug(0,"AI.doChowHelper() CurrentAction size="+ actions.size());
		SpecialAction action = (SpecialAction) actions.elementAt(0);
		//debug(3,"You chow: tiles"+ action.startTilePosition+"-"+action.endTilePosition);
		Tile temptile=player.concealedTiles.getTileAt(action.startTilePosition);
		Tile temptile2=player.concealedTiles.getTileAt(action.endTilePosition);
		//debug(0,"doChowHelper()...getTile="+temptile.hashCode());
		
		temptile.group=groupCount;
		player.discardedtile.group=groupCount;
		player.discardedtile.groupType=3;
    	
    	if (player.discardedtile.value<temptile.value) {
    		player.visibleTiles.addTileNoSorted(player.discardedtile);
    		player.visibleTiles.addTileNoSorted(new Tile(temptile.type,temptile.value,temptile.group,3));
        	temptile2.group=groupCount;
        	player.visibleTiles.addTileNoSorted(new Tile(temptile2.type,temptile2.value,temptile2.group,3));
    	}
    	else {
    		player.visibleTiles.addTileNoSorted(new Tile(temptile.type,temptile.value,temptile.group,3));
        	temptile2.group=groupCount;
        	if (player.discardedtile.value<temptile2.value) {
        		player.visibleTiles.addTileNoSorted(player.discardedtile);
        		player.visibleTiles.addTileNoSorted(new Tile(temptile2.type,temptile2.value,temptile2.group,3));
        	}
        	else {
        		player.visibleTiles.addTileNoSorted(new Tile(temptile2.type,temptile2.value,temptile2.group,3));
        		player.visibleTiles.addTileNoSorted(player.discardedtile);
        	}
    	}
    	player.concealedTiles.removeTile(temptile);	
    	player.concealedTiles.removeTile(temptile2);
    	        
    	groupCount++;	
    	//debug(0,"AI.doChowHelper() finished");

    	numOfMeld++;
		engine.doSpecialResponse(GameAction.TYPE_PONG,true, player.getWind());
		onTurn();

    }

	private Tile doKongHelper(int position) {
		//debug(0,"AIPlayer.doKongHelper()");
    	
    	SpecialAction action = (SpecialAction) actions.elementAt(0);
    	
    	//debug(3,"Player" + player.getWind() + " kong.. "+ "tiles "+ action.startTilePosition+"-"+action.endTilePosition);
    	Tile temptile=null;
    	int counter=0;
    	for (int i=action.startTilePosition;i<=action.endTilePosition;i++) {
        	temptile=player.concealedTiles.getTileAt(i);
        	temptile.group=groupCount;
        	temptile.groupType=2;
        	//debug(0,"adding tile: "+ temptile.type+"_"+temptile.value);
        	player.visibleTiles.addTileNoSorted(temptile);
        	counter++;
    	}

    	//remove from original concealedTiles
    	for (int i=action.startTilePosition;i<=action.endTilePosition;i++) {	                	
    		player.concealedTiles.removeTile(player.concealedTiles.getTileAt(action.startTilePosition));
    	}
    	
    	if (counter!=4) {
    		player.newtile.group=groupCount;
    		player.newtile.groupType=2;
    		player.visibleTiles.addTileNoSorted(player.newtile);
    	}
    	else {
    		//debug(0,"trying to add newtile");
    		player.newtile.group=groupCount;
    		player.newtile.groupType=2;
    		player.concealedTiles.addTile(player.newtile);
    	}
    	//19aug
    	player.newtile=null;

    	groupCount++;	
    	return temptile;
    }

    private Tile doKongHelper1a(int position, Tile newtile) {
    	//debug(0,"GameUI.doKongHelper1a()");
    	SpecialAction action = (SpecialAction) actions.elementAt(0);
    	Tile temp= player.visibleTiles.getTileAt(action.startTilePosition);
    	
    	if (temp.equals(newtile)) {
        	//debug(2,"GameUI.doKongHelper1a():: concealed kong from newtile");    		
        	newtile.group=temp.group;    	
        	newtile.groupType=temp.groupType;
        	player.visibleTiles.insertTile(newtile);    		
        	//debug(5,"You kong!! " + newtile);
    	}
    	else {
        	//debug(2,"GameUI.doKongHelper1a():: concealed kong from visibletiles");    		
			for(int j=0;j<player.concealedTiles.size();j++) {
				Tile temptile =   player.concealedTiles.getTileAt(j);
				if (temp.equals(temptile)) {
					temptile.group=temp.group;
					temptile.groupType=temp.groupType;
					//debug(0,"visibletile.size="+player.visibleTiles.size());
		        	player.visibleTiles.insertTile(temptile);    							
		        	player.concealedTiles.removeTile(temptile);
		        	player.concealedTiles.addTile(newtile);
					//debug(0,"visibletile.size after add="+player.visibleTiles.size());
		        	//debug(5,"You kong!! " + temptile);
					break;
				}
			}
    	}
    	

    	newtile=null;
    	return temp;
    }
    public void onWin(int playerWind, TileCollection col1, TileCollection visibleTiles1, int double1, int point1,int total1,
    		TileCollection col2, TileCollection visibleTiles2, int double2, int point2,int total2,
			TileCollection col3, TileCollection visibleTiles3, int double3, int point3,int total3,
			TileCollection col4, TileCollection visibleTiles4, int double4, int point4,int total4) {
	}
    
    public void onClearHand() {
    }
    
    private void processInputWin() {
    	//debug(3,"AIPlayer.    WIN!!!!!!!!!" + " player " + player.getWind());
    	if (player.discardedtile!=null)
    		player.concealedTiles.addTile(player.discardedtile);
        else if (player.newtile!=null)
        	player.concealedTiles.addTile(player.newtile);
    	
    	engine.doWinResponse(player.getWind());
    }
    
    public void printVisible() {
    	//debug(2,"\nConcealed : ");
    	int size=player.concealedTiles.size();
        for(int i=0;i<size;i++) {
        	debug2(2,  player.concealedTiles.getTileAt(i).type  +"_" + player.concealedTiles.getTileAt(i).value + "  " );
        }  
        //debug(2,"\nVisible : ");
        for(int i=0;i<=player.visibleTiles.size()-1;i++) {
        	debug2(2, player.visibleTiles.getTileAt(i).type  +"_" + player.visibleTiles.getTileAt(i).value+"_"+ player.visibleTiles.getTileAt(i).group  + "  ");
        }  

    }

    private void debug(int level, String txt) {
    	if (player.wind!=1) 
    		return;
    	if (level>=debugLevel)
    		System.out.println(txt);
    }
    
    private void debug2(int level, String txt) {
    	if (level>=debugLevel)
    		System.out.print(txt);
    }        
    
    public void onJoinInProgress(int pos, TileCollection owncol, TileCollection ownvisibleTiles,
    		int pos1, int pictureNum1, int point1, String nick1,int numOfTile1, TileCollection visibleTiles1,
			int pos2, int pictureNum2, int point2, String nick2,int numOfTile2, TileCollection visibleTiles2,
			int pos3, int pictureNum3, int point3, String nick3,int numOfTile3, TileCollection visibleTiles3,
			TileCollection p_tileOnTable) {}
    
    public void onJoinInProgressBot(int pos, TileCollection _owncol, TileCollection _ownvisibleTiles,
    		EngineIF _engine, Player _player,TileCollection visibleTiles1,TileCollection visibleTiles2,TileCollection visibleTiles3 ) {
    	engine=_engine;
    	player=_player;
    	
    	//debug(100,"onJoinInProgressBot()");
    	discardedTiles.removeAll();
    	otherVisibleTiles1.removeAll();
    	otherVisibleTiles2.removeAll();
    	otherVisibleTiles3.removeAll();
    		
		TileCollection tempcol=((EngineFA)_engine).tilesOnTable;
		for (int i=0;i< tempcol.size();i++) {
			Tile temptile=  tempcol.getTileAt(i);
			discardedTiles.addTile(temptile);
		}
    	for (int i=0;i<visibleTiles1.size();i++) {
			Tile temptile=  visibleTiles1.getTileAt(i);
			otherVisibleTiles1.addTile(temptile);    		
    	}
    	for (int i=0;i<visibleTiles2.size();i++) {
			Tile temptile=  visibleTiles2.getTileAt(i);
			otherVisibleTiles2.addTile(temptile);    		
    	}
    	for (int i=0;i<visibleTiles3.size();i++) {
			Tile temptile=  visibleTiles3.getTileAt(i);
			otherVisibleTiles3.addTile(temptile);    		
    	}
    	//debug(2,"onJoinInProgressBot()::::Total tilediscarded="+discardedTiles.size());
    	

        if (player.visibleTiles.size()>0) {
        	Tile tmp=player.visibleTiles.getTileAt(player.visibleTiles.size()-1);
        	groupCount=tmp.group+1;
        }

    	if (player.needAction==9) {
    		System.out.println("game paused()");
    	}
    	else if (player.needAction==1) {
    		//debug(2,"AIPlayer() needs to discard tile");
            Tile tile= chooseTileToThrow();
            player.getTiles().removeTile(tile);
        	engine.doDiscard(tile);
    	}
    	else if (player.needAction==2) {
    		//debug(2,"AIPlayer() needs to respond onNotice");
    		this.onNotice(player.specialAction);
    	}
    	else if (player.needAction==3) {
            System.out.println("AIPlayer() needs to respond onNotice2");
    		this.onNotice2(player.specialAction);    		
    	}
    	//debug(2,"AIPlayer.onJoinInProgressBot() finished");
    }

    public void resume() {
    	if (player.needAction==1) {
    		//debug(2,"AIPlayer() needs to discard tile");
            Tile tile= chooseTileToThrow();
            player.getTiles().removeTile(tile);
        	engine.doDiscard(tile);
    	}
    	else if (player.needAction==2) {
    		//debug(2,"AIPlayer() needs to respond onNotice");
    		this.onNotice(player.specialAction);
    	}
    	else if (player.needAction==3) {
            System.out.println("AIPlayer() needs to respond onNotice2");
    		this.onNotice2(player.specialAction);    		
    	}
    }
    
    private void t_printOwnTiles(int x) {
    	//debug(x,"****SIZE: visibletiles=" + player.visibleTiles.size());
    	
    	
    	if (x>=debugLevel) {
			System.out.println("\nVisible : ");
		    for(int i=0;i<=player.visibleTiles.size()-1;i++) {
		    	System.out.print( player.visibleTiles.getTileAt(i).type +""  + player.visibleTiles.getTileAt(i).value + "_" + 
		    			player.visibleTiles.getTileAt(i).group + "_"+ player.visibleTiles.getTileAt(i).groupType + "  " );
		    }  
		    System.out.println("\nConcealed: ");
		    for(int i=0;i<=player.concealedTiles.size()-1;i++) {
		    	System.out.print( player.concealedTiles.getTileAt(i).type+""  + player.concealedTiles.getTileAt(i).value + "_" + 
		    			player.concealedTiles.getTileAt(i).groupType + "  ");
		    }  
		    System.out.println("");
    	}
    }

    private void t_printDiscardCandidates(int x) {
    	//debug(x,"****SIZE: visibletiles=" + player.visibleTiles.size());
    	if (x>=debugLevel) {
			System.out.println("\nDiscard Candidates: ");
		    for(int i=0;i<=discardCandidates.size()-1;i++) {
		    	System.out.print( discardCandidates.getTileAt(i).type +""  +discardCandidates.getTileAt(i).value + "_" + 
		    			discardCandidates.getTileAt(i).group + "_"+ discardCandidates.getTileAt(i).groupType + "  " );
		    }  
		    System.out.println("");
    	}
    }

    
    
    public void onFinish(){}
    public void onFinish(String dateStr){}
    
    public void onStart2(TileCollection col, TileCollection visibleTiles) {}
    public void onCommunicationError(){}
    public void onOtherJoinInProgress(int position,int picture, String p_nick){}    
    public void onOtherLeaveInProgress(int pos, int picture, String nick1, String nick2){}
    
    public void onTableMessage(String message){}
    

    public void doCleanUp() {
    	engine=null;
    	player=null;
    }

}
