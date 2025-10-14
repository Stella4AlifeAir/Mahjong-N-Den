package com.pheephoo.mjgame.engine;

import com.pheephoo.utilx.Common;
import java.util.Vector;
import java.util.Enumeration;

import com.pheephoo.mjgame.Constant;
import com.pheephoo.mjgame.engine.WinEngine;
import com.pheephoo.mjgame.ui.AIPlayer;
import com.pheephoo.mjgame.ui.PublicNetworkCanvas;


/**
 * Game Rules are implemented here
 * 1. Dealing the tile
 * 2. Updating the display for each player
 * 3. Checking win, kong, pong, chow notice
 * 4. Calculating winning point
 * 
 * testing code is marked with prefix t_
 * networking specific code is marked with prefix n_
 * 
 * @author Samuel
 * @version 1.00, 24/04/2005
 */ 
public class EngineFA implements EngineIF {
	
	/*
	 * Reference to other object
	 */
		
    GameListenerIF gameUI[];	//reference to specific player, human player, computer player, AI player 
	MahjongDeck deck;					//reference to deck object
    public Player player[];			//reference to generic player. contains attribute common to all kind of player
	public long startTime;
	public long lastActive; //to retain the table if everyone leave of a few mins
	public int lastBotIndex=0;
	public int prevPlayAction=0;
	public Tile lastTile;
	
    boolean kongVisible;
    
    
    /*
     * development use only
     */
	public int demoMode;//14/10/2004 for demo. 
	
	int debugLevel=0;
	int t_tileGivenCount;		//use in getPreTile();

	int numOfDiscard;//for calculating the first two limit hand.(heavenly and earthly hand)
	
	
	//misc
	boolean winNoticeInsertFlag=false;
    
    /*
     * Common Game Playing
     */
    WinEngine wineng; //Calculating win helper class
	public int playMode;	//0=offline 1=online private 2=online public  3=competition
	boolean isRobbingKong;//for flagging robbing the kong condition
	private int turnBeforeRobbingKong;//for removing the tile from player whose tile is robbed
	
	boolean somebodyKong;
	boolean isConcealedKong;
    int numOfPlayer;			//number of player that has join
    int currentWind;			//current prevailing wind
    int dealerWind;
    int numConfirm;//for used in counting player that respond
    int lastWinningPlayerWind=-1;
    int turn = currentWind;		//current player turn, initially when the game start turn = currentWind
    int mapping[] = {9,9,9,9};  //player to wind direction mapping. eg mapping[0]=2 means player[0] sits on position 2
    public Vector eventQueue=new Vector();			//queue for holding notice for player. (eg. player 1 can PONG, and player 3 can CHOW) 
    public TileCollection tilesOnTable = new TileCollection(TileCollection.MAXTILESONTABLE);

    //11/10/2004 for scoring
    public int minDouble;
    public int maxDouble=5;    
    private int double2Point=1;
    private int unitKongPoint=2;
    private int lastplayernotice=-1;//for used in doSpecialResponse and d to prevent noticing a player twice
    private int doubleCount;

    private int doubleWinCount[]=new int[4];
    
    // the following objects are reused to avoid recreating object
    private Tile newtile;							//new drawed tile to be given to player 
    private Tile lastDiscard;
    private Tile winningTile;

    
    
    /*network related
     */
    public int n_tableNumber;			//for network playing
    public int n_owner;
    Vector freeBot;
        
    //04/11/2004 for used in public network function
    public int n_networkPlayerJoin;
    public int n_started;
    
    public boolean nextGamePressed;
    
    
    
    /*
     * the player that creates the engine is the owner
     * create a deck object
     * 
     * @write mapping
     */
    public EngineFA() {
    	init();
    }

    public EngineFA(int tableNumber, int p_minDouble, int p_maxDouble, int p_owner, int p_playMode) {
    	init();
    	
    	playMode=p_playMode;
    	System.out.println("EngineFA.playMode="+playMode);
    	
    	minDouble=p_minDouble;
    	maxDouble=p_maxDouble;
    	n_tableNumber=tableNumber;
    	n_owner=p_owner;
    	//player[0]=new Player();
        player[0]= new Player(deck, mapping[0]);
    	player[0].cid=0;
        player[1]= new Player(deck, mapping[1]);
    	player[1].cid=0;
        player[2]= new Player(deck, mapping[2]);
    	player[2].cid=0;
        player[3]= new Player(deck, mapping[3]);
    	player[3].cid=0;

    }
    
    public void init() {
    	//simple random simulation;
    	startTime=System.currentTimeMillis();
    	freeBot= new Vector();
    	
    	for (int i=0;i<Constant.BOT_NUM;i++) {
    		freeBot.addElement(new Integer(i));
    	}

    	
        mapping[0]=Common.getRandomInt(0,3);
        wineng= new WinEngine();
        //start testing. no random number

        if (demoMode==1)        
        	mapping[0]=0;
        else if (demoMode==11)
        	mapping[0]=2;
        else if (demoMode==41)
        	mapping[0]=0;
        //end testing
        else if (demoMode==20)
        	mapping[0]=0;
        mapping[0]=0;
        
        mapping[1]=(mapping[0]+1)%4;
        mapping[2]=(mapping[0]+2)%4;
        mapping[3]=(mapping[0]+3)%4;
        deck = new MahjongDeck();
        gameUI= new GameListenerIF[4];
        player = new Player[4];
        //debug(10,"finished EngineFA.init()");
    }
    
    
    private void processJoinInProgress(int index) {
    
    	//debug(10,"EngineFA.processJoinInProgress() start");
    	
    	int z=index;
    	gameUI[z].onJoin(0,mapping[z],4,this, player[z],1);
    	//debug(10,"finish calling own onJoin method");
    	
    	//debug(10,"currentPlayerindex="+z);
    	//debug(10,"***Player tile size***");
    	//debug(10,"***Player 0 = "+ player[0].concealedTiles.size()  + ";nick="+player[0].nick);
    	//debug(10,"***Player 1 = "+ player[1].concealedTiles.size() + ";nick="+player[1].nick);
    	//debug(10,"***Player 2 = "+ player[2].concealedTiles.size() + ";nick="+player[2].nick);
    	//debug(10,"***Player 3 = "+ player[3].concealedTiles.size() + ";nick="+player[3].nick);
    	
    	
    	//this parameter is dummy. coz UIProxy has direct access to the engine object
		gameUI[z].onJoinInProgress( player[z].getWind(),  player[z].concealedTiles,player[z].visibleTiles,
				player[(z+1)%4].getWind(),player[(z+1)%4].pictureNum,player[(z+1)%4].totalPoint, player[(z+1)%4].nick, 13 - player[(z+1)%4].visibleTiles.size(),player[(z+1)%4].visibleTiles,  
				player[(z+2)%4].getWind(),player[(z+2)%4].pictureNum,player[(z+2)%4].totalPoint, player[(z+2)%4].nick,13 - player[(z+2)%4].visibleTiles.size(),player[(z+2)%4].visibleTiles,
				player[(z+3)%4].getWind(),player[(z+3)%4].pictureNum,player[(z+3)%4].totalPoint, player[(z+3)%4].nick,13 - player[(z+3)%4].visibleTiles.size(),player[(z+3)%4].visibleTiles,
				tilesOnTable);
    	
    	
		//gameUI[z].onStart2(player[z].concealedTiles, player[z].visibleTiles);

		
		//debug(10,"EngineFA.processJoinInProgress() end");

		
    }
    
    
    public void startOffline() {
        //notice the other player
    	//debug(10,"engineFA.startOffline()");
    	
    	//for single player. we do not need onotherjoin();
        //gameUI[i].onStart()
        gameUI[0].onJoin( 0, mapping[0], 1,this,player[0],0 );
        //debug(10,"joined player1");
        gameUI[1].onJoin( 0, mapping[1], 2,this,player[1],0 );
        //debug(10,"joined player2");
        gameUI[2].onJoin( 0, mapping[2], 3,this,player[2],0 );
        //debug(10,"joined player3");
        gameUI[3].onJoin( 0, mapping[3], 4,this,player[3],0 );
        //debug(10,"joined player4");

    	for (int i=0;i<4;i++) {
    		for (int j=0;j<4;j++) {
    			if (i!=j) {
    				gameUI[i].onOtherJoin(player[j].getWind(),4,player[j].pictureNum, player[j].nick);
    			}
    		}
    	}
    	
    	//debug(10,"finished run before start");
        start();
        //debug(10,"finished run after start");
        
    }
    
    /*
     * everytime user join, create a player object
     * assign the player with a specific wind
     * assign player to game UI 
     * 
     * @read/write numOfPlayer
     * @write player[]
     * @write gameUI[]
     * 
     * @param pGameUI 		
     * @param pTableNum
     */
    public void join(GameListenerIF pGameUI, int pTableNum) {
        //debug(0,"EngineFA.join()");
    	n_tableNumber=pTableNum;
        
        //create a new player object;
        player[numOfPlayer]= new Player(deck, mapping[numOfPlayer]);
        //debug(1,"EngineFA().join()::::*player " + numOfPlayer + " join on position " + player[numOfPlayer].getWind() );        
        gameUI[numOfPlayer]=pGameUI;
        numOfPlayer++;

     }
    
    
    /*
     * use in network
     */
    	
    /*
     * distribute 13 tiles to each person
     * !!testing modify this method to perform custom tile distribution
     * 
     * @call giveTile()
     */
    private void start() {
    	n_started=1;
    	nextGamePressed=true;
    	//debug(4,"EngineFA.start() 4 player.. START the game!!! DemoMode="+demoMode);
    	//debug(10,"Player Joined:");
    	for (int i=0;i<player.length;i++) {
    		//debug(10,i+ ". "+ player[i].nick  );
    	}
    	deck.reset();
    	
    	////debugcode
    	//demoMode=Constant.TEST_CONCEALKONG2;
    	//end debug code
    	
    	if (demoMode==0) {
	        for (int i=0;i<4;i++) {
	        	//debug(10,"loading player " + i + " tile");
	            player[i].loadInitialTile();
	        }
	        for (int i=0;i<4;i++) {
	        	gameUI[i].onStart(player[i].concealedTiles, player[i].visibleTiles);
	        }
    	}
    	else if (demoMode==1) {
    		deck.demoMode=1;
            gameUI[0].setDemoMode(1);
            gameUI[1].setDemoMode(1);
            gameUI[2].setDemoMode(1);
            gameUI[3].setDemoMode(1);
        	testFinish9();    		
    	}
    	//test kong. mode 1x
    	else if (demoMode==10) {
    		testKong1();//test concealed kong and robbing the kong
    	}
    	else if (demoMode==11) {
    		//debug(10,"start demomode 11");
    		testKong2();//test concealed kong and robbing the kong
    	}
    	else if (demoMode==12) {
    		testKong3();//test kong formed from other discard
    	}
    	else if (demoMode==13) {
    		testKong5();//test kong formed from other discard
    	}
    	else if (demoMode==Constant.TEST_CONCEALKONG1) {
    		testConcealKong1();//test kong formed when we have 4 identical card concealed
    	}
    	else if (demoMode==Constant.TEST_CONCEALKONG2) {
    		testConcealKong2();//test kong formed when we have 1 pong and another card concealed
    	}
    	else if (demoMode==Constant.TEST_CONCEALKONG3) {
    		testConcealKong3();//test kong formed when we have 1 pong and another card concealed, another concealed 4 identical card
    	}
    	else if (demoMode==Constant.TEST_CONCEALKONG4) {
    		testConcealKong4();// (AI) test kong formed when we have 1 pong and another card concealed, another concealed 4 identical card
    	}
    	else if (demoMode==Constant.TEST_ROBKONG1) {
    		testRobKong1();// (AI) test kong formed when we have 1 pong and another card concealed, another concealed 4 identical card
    	}
    	else if (demoMode==Constant.TEST_ROBKONG2) {
    		testRobKong2();// (Player) test kong formed when we have 1 pong and another card concealed, another concealed 4 identical card
    	}
    	
    	
    	else if (demoMode==Constant.TEST_CONCEALKONG3) {
    		testKong5();//test kong formed from other discard
    	}
    	else if (demoMode==Constant.TEST_CLEARHAND1) {//no kong and win
	        for (int i=0;i<4;i++) {
	        	//debug(10,"loading player " + i + " tile");
	            player[i].loadInitialTile();
	        }
	        for (int i=0;i<4;i++) {
	        	gameUI[i].onStart(player[i].concealedTiles, player[i].visibleTiles);
	        }

    	}
    	else if (demoMode==Constant.TEST_CLEARHAND2) {//kong
    		testConcealKong3();
    	}
    	else if (demoMode==Constant.TEST_CLEARHAND3) {//kong+win
    		//debug(3,"demomode=test_clearhand3");
    		testClearHand3();
    	}
    	//test pong
    	else if (demoMode==Constant.TEST_PONG1) {
    		deck.demoMode=Constant.TEST_PONG1;
    		testPong1();
    	}
    	else if (demoMode==22) {
    		deck.demoMode=22;
    		testPong2();
    	}
    	
    	//mode 1xx  network playin
    	else if (demoMode==110) {
    		deck.demoMode=110;
    		//debug(10,"start demomode 110");
        	testNetworkKong1();    		
    	}
    	
    	
    	//this is a network game, give tile directly
    	//27/04/2005
    	if (playMode!=0 ) {
    		//debug(2,"playMode="+playMode);
    		giveTile();
    	}
    }

    /*
     * only used in offline mode
     */
    public void doConfirm() {
    	//debug(2,"doConfirm");    	
    	numConfirm++;
    	if (playMode==0 && numConfirm==4 )
    		giveTile();
    }

    public void nextGame() {
    	//debug(10,"nextGame");    	
    	
    	
    	if (nextGamePressed)
    		return;

    	
    	//if can move to next turn
    	deck.reset();
    	//deck.createTile();
    	turn=0;
    	
    	numOfDiscard=0;
    	tilesOnTable.removeAll();
    	//debug(2,"lastWinningPlayerWind="+lastWinningPlayerWind);
    	
    	if (lastWinningPlayerWind==0 ) {
    		//debug(5,"nextGame::Dealer win... keep the current wind");
    	}
    	else if (lastWinningPlayerWind==-1) {//nobody win.. check for kong 
    		if (somebodyKong) {
        		//debug(5,"nextGame::nobody win but somebody kong... increment the wind");
        		dealerWind=(dealerWind+1);
            	player[0].incrementWind();
            	player[1].incrementWind();
            	player[2].incrementWind();
            	player[3].incrementWind();
    		}
    		else {
        		//debug(5,"nextGame::nobody win and nobody kong... keep the wind");    			
    		}
    	}
    	else {
    		//debug(5,"nextGame::Other win... increment the current wind");
    		dealerWind=(dealerWind+1);
        	player[0].incrementWind();
        	player[1].incrementWind();
        	player[2].incrementWind();
        	player[3].incrementWind();
    	}

    	t_tileGivenCount=0;
    	numConfirm=0;
    	isRobbingKong=false;//for flagging robbing the kong condition
    	somebodyKong=false;
    	
    	lastWinningPlayerWind=-1;


    	//force end of game

    	if (demoMode==Constant.TEST_CONCEALKONG1) {
	    	dealerWind=4;
	    	currentWind=3;
    	}
    	
    	if (dealerWind==4) {
    		dealerWind=0;
        	if (currentWind==3 || playMode==3) {
            	gameUI[0].onFinish();
            	gameUI[1].onFinish();
            	gameUI[2].onFinish();
            	gameUI[3].onFinish();
            	return;
        	}
        	else {
        		currentWind++;        		
        	}
    	}
    	player[0].startNewGame();
    	player[1].startNewGame();
    	player[2].startNewGame();
    	player[3].startNewGame();
    	start();
    }
    
    private void simulateClearHand() {
    	//debug(1,"simulateClearHand");
    		
    	
    	if (demoMode==Constant.TEST_CONCEALKONG3) {
    		newtile=getPreTile(Constant.TEST_CONCEALKONG3);
    	}
    	else if (demoMode==Constant.TEST_CLEARHAND3) {
    		newtile=getPreTile(Constant.TEST_CLEARHAND3);
    		
    	}
    	else {    	
    		newtile = deck.getRandomTile();
    	}

    	player[0].specialAction.removeAllElements();   
    	player[1].specialAction.removeAllElements();
    	player[2].specialAction.removeAllElements();
    	player[3].specialAction.removeAllElements();
    	    	
    	//debug(3,"\n" + t_tileGivenCount+ "*******************************************************************" );
    	//debug(4,"EngineFA.giveTile()::*givetile " + newtile + " to player " + turn+"\tNoOfTile="+ deck.size() );
    	t_printOwnTiles(getPlayNum());
    	
    	player[getPlayNum()].needAction=1;//need to discard a tile
    	
    	
    	
    	//gameUI[getPlayNum()].onTileReceived(newtile, deck.size(),turn);
    	for (int i=0;i<4;i++) {
        	gameUI[i].onTileReceived(newtile, deck.size(),turn);
    	}
    	
    	

    	//debug(1,"\nEngineFA.givetile()::Check if current player "+ turn + " can kong " );    	
        if (checkWhenReceived(turn,newtile)!=0) {
        	//debug(1,"EngineFA.giveTile()::This player can KONG/WIN");
        
        	//if this is the last tile, no need to present the user with a choice
        	//if (deck.size()==0) {
        		  
        	//}
        	//else {
        		player[getPlayNum()].needAction=2;//need to response to own kong, for NETWORK PLAYING
        		gameUI[getPlayNum()].onNotice( player[getPlayNum()].specialAction );
        	//}        	
        }
        else {
        	//debug(1,"clear hand");
    		if (somebodyKong) {
    			nextGamePressed=false;
    			this.doShowScoreResponse();
    			
    		}
    		else {
	    		//debug(4,"CLEAR HAND.... start a new game");
	    		nextGamePressed=false;
	    		gameUI[0].onClearHand();
	    		gameUI[1].onClearHand();
	    		gameUI[2].onClearHand();
	    		gameUI[3].onClearHand();
    		}
        }

    	
    	
    }
    
    
	/*
	 * !!testing modify this method to perform distribute predetermined sequence of tile
	 * 
	 * @call getPreTile() testing
	 * @call checkWhenReceived()
	 */    
    private void giveTile() {
    	//debug(4,"EngineFA.giveTile()");
    	t_tileGivenCount++;////debug code
    	isRobbingKong=false;
    	
    	
    	
    	//start //debug code.. assume if tileGiven=1 is the last tile    
    	if (  (demoMode==Constant.TEST_CLEARHAND1 ||
    			demoMode==Constant.TEST_CLEARHAND2 || demoMode==Constant.TEST_CLEARHAND3) 
    			&& t_tileGivenCount==1) {
    		simulateClearHand();
    		return;
    	}
    	else if (  (demoMode==Constant.TEST_CLEARHAND1 ||
    			demoMode==Constant.TEST_CLEARHAND2 || demoMode==Constant.TEST_CLEARHAND3) 
    			&& t_tileGivenCount==2) {
    		//debug(4,"Try to give tile on dummy discard");
    		
    		if (somebodyKong) {
    			nextGamePressed=false;
    			this.doShowScoreResponse();
    			
    		}
    		else {
	    		//debug(4,"CLEAR HAND.... start a new game");
	    		nextGamePressed=false;
	    		gameUI[0].onClearHand();
	    		gameUI[1].onClearHand();
	    		gameUI[2].onClearHand();
	    		gameUI[3].onClearHand();
    		}
    		return;
    	}
    	//end //debug code
    	
    	
    	//test if tile has run out
    	if (deck.size()==0) {
    		if (somebodyKong) {
    			nextGamePressed=false;
    			this.doShowScoreResponse();
    		}
    		else {
	    		//debug(4,"CLEAR HAND.... start a new game");
	    		nextGamePressed=false;
	    		gameUI[0].onClearHand();
	    		gameUI[1].onClearHand();
	    		gameUI[2].onClearHand();
	    		gameUI[3].onClearHand();
    		}
    		return;
    	}
    	
    	
    	//temporarily disable for testing
    	
    	
    	if (demoMode==Constant.TEST_CONCEALKONG3) {
    		newtile=getPreTile(Constant.TEST_CONCEALKONG3);
    	}
    	else if(demoMode==Constant.TEST_CONCEALKONG2) {
    		newtile=new  Tile(0,3);
    	}
    	else {    	
    		
    		newtile = deck.getRandomTile();
    		
    	}
    	
		//newtile=new  Tile(0,3);
    	
    	player[0].specialAction.removeAllElements();   
    	player[1].specialAction.removeAllElements();
    	player[2].specialAction.removeAllElements();
    	player[3].specialAction.removeAllElements();
    	    	
    	//debug(3,"\n" + t_tileGivenCount+ "*******************************************************************" );
    	//debug(4,"EngineFA.giveTile()::*givetile " + newtile + " to player " + turn+"\tNoOfTile="+ deck.size() );
    	t_printOwnTiles(getPlayNum());
    	
    	player[getPlayNum()].needAction=1;//need to discard a tile
    	
    	
    	//gameUI[getPlayNum()].onTileReceived(newtile, deck.size(),turn);
    	for (int i=0;i<4;i++) {
        	gameUI[i].onTileReceived(newtile, deck.size(),turn);
    	}

    	
    	
    	
    	
    	//debug(1,"\nEngineFA.givetile()::Check if current player "+ turn + " can kong " );    	
        if (checkWhenReceived(turn,newtile)!=0) {
        	//debug(1,"EngineFA.giveTile()::This player can KONG/WIN");
        
        	//if this is the last tile, no need to present the user with a choice
        	//if (deck.size()==0) {
        		  
        	//}
        	//else {
        		player[getPlayNum()].needAction=2;//need to response to own kong, for NETWORK PLAYING
        		gameUI[getPlayNum()].onNotice( player[getPlayNum()].specialAction );
        	//}        	
        }
        else {
        	//debug(1,"EngineFA.giveTile()::calling gameUI on turn");
        	
        	if (deck.size()==0) {
        		if (somebodyKong) {
        			nextGamePressed=false;
        			this.doShowScoreResponse();
        		}
        		else {
    	    		//debug(4,"CLEAR HAND.... start a new game");
    	    		nextGamePressed=false;
    	    		gameUI[0].onClearHand();
    	    		gameUI[1].onClearHand();
    	    		gameUI[2].onClearHand();
    	    		gameUI[3].onClearHand();
        		}
        	}
        	else {
        		gameUI[getPlayNum()].onTurn();
        	}
        }
    }
    
    public void networkJoin(int cid, int pictureNum, String nick, GameListenerIF pGameUI) {
    	if (n_networkPlayerJoin==4) {
    		return;
    	}
    	
    	//System.out.println("EngineFA.networkJoin. cid="+cid);
    	for (int i=0;i<4;i++) {
        	//System.out.println("player[i].cid="+player[i].cid);
    		if (player[i].cid==0) {
    			player[i].cid=cid;
    			player[i].nick=nick;
    			player[i].pictureNum=pictureNum;
    			gameUI[i]=pGameUI;
    			break;
    		}
    	}
    	n_networkPlayerJoin++;
    }
    
    public void networkLeaveInProgress(int cid){ 
    	//debug(4,"EngineFA.networkLeaveInProgress()::start ");
    	//debug(4,"cid="+cid);
    	int i=0;
    	String oldNick="";
    	
    	n_networkPlayerJoin--;
    	if (n_networkPlayerJoin==0) {
        	//debug(4,"this engine is waiting to be cleaned by server");

        	if (playMode!=3) {
            	return;        		
        	}
        	else {
            	for (i=0;i<4;i++) {
            		//debug(10,"current player cid:"+ i+"="+player[i].cid);
            		if (player[i].cid==cid) {
            			//debug(4,"player cid="+ cid + "leave..change to computer");
            			if (playMode==1 || playMode==3) {
        	    			player[i].prevcid=cid;
        	    			//debug(10,"storing prevcid="+ player[i].prevcid );
            			}
            			player[i].cid=999;
                		oldNick=player[i].nick;
                		int botPos=Common.getRandomInt(0,freeBot.size()-1);
            	        int botPicNum=((Integer)freeBot.elementAt(botPos)).intValue();

                		gameUI[i]=new AIPlayer(botPicNum,Constant.BOT_NAME[botPicNum]);
                		player[i].nick=Constant.BOT_NAME[botPicNum];
                		player[i].pictureNum=botPicNum;
                		int wind=player[i].getWind();
//                		prevPlayAction=player[i].needAction;
//                		player[i].needAction=9;//pause
//                		lastBotIndex=i;
						gameUI[i].onJoinInProgressBot(wind,
                				player[i].concealedTiles, player[i].visibleTiles,
        						this, player[i], player[this.getPlayNum( (wind+1)%4 )].visibleTiles  ,
        						player[this.getPlayNum( (wind+2)%4 )].visibleTiles  ,
        						player[this.getPlayNum( (wind+3)%4 )].visibleTiles);
                		
                		freeBot.removeElementAt(botPos);
                	
            			break;
            		}
            	}
        		
        		return;
        	}
    	}
    	
    	for (i=0;i<4;i++) {
    		//debug(10,"current player cid:"+ i+"="+player[i].cid);
    		if (player[i].cid==cid) {
    			//debug(4,"player cid="+ cid + "leave..change to computer");
    			if (playMode==1 || playMode==3) {
	    			player[i].prevcid=cid;
	    			//debug(10,"storing prevcid="+ player[i].prevcid );
    			}
    			player[i].cid=999;
        		oldNick=player[i].nick;
        		int botPos=Common.getRandomInt(0,freeBot.size()-1);
    	        int botPicNum=((Integer)freeBot.elementAt(botPos)).intValue();

        		gameUI[i]=new AIPlayer(botPicNum,Constant.BOT_NAME[botPicNum]);
        		player[i].nick=Constant.BOT_NAME[botPicNum];
        		player[i].pictureNum=botPicNum;
        		int wind=player[i].getWind();
        		gameUI[i].onJoinInProgressBot(wind,
        				player[i].concealedTiles, player[i].visibleTiles,
						this, player[i], player[this.getPlayNum( (wind+1)%4 )].visibleTiles  ,
						player[this.getPlayNum( (wind+2)%4 )].visibleTiles  ,
						player[this.getPlayNum( (wind+3)%4 )].visibleTiles);
        		
        		freeBot.removeElementAt(botPos);
        		
    			break;
    		}
    	}

    	//notice other player
    	for (int k=0;k<4;k++) {
    		if (k!=i) {
    			//debug(10,"EngineFA::onLeaveInProgress::position="+mapping[i]);
    			gameUI[k].onOtherLeaveInProgress(player[i].wind,player[i].pictureNum,oldNick,player[i].nick);
    		}
    	}

    	
    	if (turn==player[i].getWind()) {
    		//debug(10,"EngineFA.onLeaveInProgress:::AI player turn while replacing");
    		//player[i].
    		//gameUI[k].ont
    	}
    		
    		
    	//debug(4,"EngineFA.networkLeaveInProgress()::finished");
    }
    
    public void networkJoinInProgress(int cid, int pictureNum, String nick, GameListenerIF pGameUI) {
    	//debug(4,"EngineFA.networkJoinInProgress::start()");
    	//debug(4,"New Player joined: cid="+cid+" ;pictureNum="+pictureNum+" ;nick="+nick+ " ;n_networkPlayerJoin"+n_networkPlayerJoin);
    	
    	int i=0;

    	
    	if (playMode==1 || playMode==3) {
	    	//for private network playing... searching for previous joined player
	    	for (i=0;i<4;i++) {
	    		if ( gameUI[i] instanceof AIPlayer && player[i].prevcid==cid) {
	    			break;
	    		}
	    	}
	    	if (i==4) {
		    	for (i=0;i<4;i++) {
		    		if ( gameUI[i] instanceof AIPlayer) {
		    			break;
		    		}
		    	}	    		
	    	}
	    	
    	}
    	else {
	    	//for public network playing... juz replace the first aiplayer found
	    	for (i=0;i<4;i++) {
	    		if ( gameUI[i] instanceof AIPlayer) {
	    			break;
	    		}
	    	}
    	}
    	
    	
    	//updating bot name
    	
    	freeBot.addElement(new Integer(player[i].pictureNum));
    	
    	player[i].cid=cid;
    	player[i].nick=nick;
    	player[i].pictureNum=pictureNum;    	

//    	AIPlayer 	tmpAIPlayer=(AIPlayer ) gameUI[lastBotIndex];
    	gameUI[i]=pGameUI;


    	
    	n_networkPlayerJoin++;
    	
    	//notice other player
    	for (int k=0;k<4;k++) {
    		if (k!=i) {
    			//debug(10,"EngineFA::onJoinInProgress::position="+mapping[i]);
    			gameUI[k].onOtherJoinInProgress(player[i].wind,pictureNum,nick);
    		}
    	}
    	
    	//System.out.println("JoinInProgress**");
    	for (int k=0;k<4;k++) {
			//System.out.println("nick="+player[k].nick + " ;wind="+player[k].wind);
    	}
    	
    	
    	processJoinInProgress(i);
    	// we need to implement the method for informing other player that a new player has joined
    	//informPlayer();

    	if (playMode==3 && n_networkPlayerJoin==1) {
    		//debug(10,"waking up");
    		//player[lastBotIndex].needAction=prevPlayAction;	    		
    		doDiscard(lastTile);
    	}

    	
    	
    	//debug(4,"EngineFA.networkJoinInProgress::finish()");
    }

    //network playing function
    public void networkLeave(int cid) {
    	//System.out.println("EngineFA.networkLeave(). cid="+cid);
    	//for (int i=1;i<4;i++) {
        //why start from 1??
    	for (int i=0;i<4;i++) {
        	//System.out.println("EngineFA.networkLeave(). player.i.cid="+player[i].cid);
    		if (player[i].cid==cid) {
    			player[i].cid=0;
    	    	n_networkPlayerJoin--;
    	    	//System.out.println("networkleave::n_networkPlayerJoin"+n_networkPlayerJoin);
    			gameUI[i]=null;
    		}
    	}
    }
    
    
    //network playing function
    public void forceStart() {
    	//debug(10,"EngineFA.start forceStart()");
    	
    	int temp=n_networkPlayerJoin;
    	//debug(10,"n_networkPlayerJoin="+temp);
  
    	while (temp<4) {
    		player[temp].cid=999;
    		//debug(10,"setting bot");
    		int botPos=Common.getRandomInt(0,freeBot.size()-1);
	        int picNum=((Integer)freeBot.elementAt(botPos)).intValue();
    		gameUI[temp]=new AIPlayer(picNum,Constant.BOT_NAME[picNum]);
    		freeBot.removeElementAt(botPos);
    		temp++;
    	}
    	//debug(10,"end forceStart()");
    	startOffline();
    }
 
    
    
    
    
    /*
     * temporarily put the tile exposed by Concealed Pong as a discard tile
     */
    private boolean checkRobbingKong(Tile tile) {
    	//check all player that can win
    	//21/09/2004
    	//debug(2,"checkRobbingKong() tile="+tile);
    	//debug(2,"kongvisible="+kongVisible);
   
    	
    	
    	if (!kongVisible) {
        	isRobbingKong=false;
    		return false;
    	}
    	
    	Tile temptile=new Tile(tile.type,tile.value);
    	//17/11/2004
    	//debug(3,"EngineFA.checkRobbingKong() newtile=" + temptile);

    	clearSpecialActionHelper();
    	
    	for (int i=0;i<4;i++) {
    		if (i!=getPlayNum(turn)) {
    			turnBeforeRobbingKong=turn;
				isRobbingKong=true;//mark it true first then set it false if its not robbing the kong
//				doubleCount=checkWinHelper(i,eventQueue,temptile);//extra one double for robbing the kong
    			doubleWinCount[i]=checkWinHelper(i,eventQueue,temptile);

				//debug(3,"doubleCount " + i + " = " + doubleCount);
    			if (eventQueue.size()!=0) { 	// if there is one or more players that can win, then exit the loop
    				//debug(10,"Can ROB The KONG");
    	        	t_printOwnTiles(i);
    	        	//debug(3,"EngineFA.checkRobbingCount(): doublecount+1");
        			
    	        	specialNoticeOnDiscarded();
    	        	return true;
    			}
    			else {
    				isRobbingKong=false;
    			}
    		}
    	}    	
    	
    	
    	isRobbingKong=false;
    	return false;
    	
    }
    
    
    private void clearSpecialActionHelper() {
        eventQueue.removeAllElements();
    	player[0].specialAction.removeAllElements();   
    	player[1].specialAction.removeAllElements();
    	player[2].specialAction.removeAllElements();
    	player[3].specialAction.removeAllElements();
    }

    /*
     * network version of doDiscard
     */
    public void doDiscard(Tile tile, int hasSpecialAction) {
    	//if no more tile on the deck.. player discard dummy tile.. and 
    	lastActive=System.currentTimeMillis();
    	kongVisible=false;
    	winNoticeInsertFlag=false;
    	numOfDiscard++;
    	if (deck.size()==0 || 
    			(demoMode==Constant.TEST_CLEARHAND1 ||demoMode==Constant.TEST_CLEARHAND2 ||
    					demoMode==Constant.TEST_CLEARHAND3 || demoMode==Constant.TEST_CLEARHAND4) ) {
    		//debug(5,"doDiscard()... lastTile. ignoring the dummy discard");
    		
    		giveTile();
    		return;
    	}    	
    	
    	//debug(30,"\nEngineFA.doDiscard().. player " + turn + " discard " + tile.type + "_" + tile.value);
    	player[turn].needAction=0;
    	lastplayernotice=-1;
    	
    	tilesOnTable.addTileNoSorted(tile);
    	lastDiscard=tile;
    	
    	for(int i=0;i<4;i++) {
    		//debug(30,"EngineFA.doDiscard():::calling gameUI().onTileDiscarded");            
    		if (i!=getPlayNum(turn) ) {
        		//debug(30,"calling ontilediscarded for player "+ getPlayNum(turn));            
    			gameUI[i].onTileDiscarded(tile,turn, deck.size());
    		}
    	}        

    	clearSpecialActionHelper();    	

    	//shortcut
    	if (hasSpecialAction==0) {
        	//debug(1200,"EngineFA.doDiscard shortcut");
            turn=(turn+1)%4;
            giveTile();
            return;
    	}
    	//debug(1200,"EngineFA.doDiscard has specialaction!");

    	for (int i=1;i<4;i++) {
    		//if (i!=getPlayNum()) {
    			int playerIndex=getPlayNum( (turn+i)%4);
//    			doubleCount=checkWinHelper(playerIndex,eventQueue,tile);
    			doubleWinCount[playerIndex]=checkWinHelper(playerIndex,eventQueue,tile);
//    			System.out.println("doubleWinCount["+playerIndex+"playerIndex "+"]="+doubleWinCount[playerIndex]);
//    			if (eventQueue.size()!=0) 	// if there is one or more players that can win, then exit the loop
//    				break;
    	}

    	
    	int previousQueueSize=eventQueue.size();

    	//check all player that can pong/kong
    	//will update the eventQueue vector if there is a player that can pong/kong        
    	for (int i=1;i<4;i++) {
    		int playerIndex=getPlayNum( (turn+i)%4);
    		//if (i!= playerIndex) {
    			//debug(3,"**************player num=" +getPlayNum( (turn+i+1)%4));
    			
    			checkPongHelper(playerIndex,eventQueue,tile);
    			if (eventQueue.size()>previousQueueSize) 	// if there is one player that can pong/kong, exit the loop
    				break;
    		//}
    	}
		checkChowHelper( getPlayNum( (turn+1)%4  ),eventQueue,tile);
		
    	
		//outcome depends on the queue size;
        if (eventQueue.size()==0) {
        	//debug(1,"EngineFA.doDiscard()::calling EngineFA.giveTile");
            turn=(turn+1)%4;
            giveTile();
        }       
        else {
        	specialNoticeOnDiscarded();//some player can pong/kong/win
        }
    }
    
    
    
    
    
    
    /*
     * Notice other player that a tile is discarded
     * Reinitialize player[].specialAction
     * 
     * @read/write turn
     * @write player[].specialAction
     * @call checkWinHelper()
     * @call checkPongHelper()
     * @call checkChowHelper()
     * @call giveTile()
     * @call noticePlayeronDiscarded() this is special notice. eg. pong/kong/chow
     */
    public void doDiscard(Tile tile) {
    	//if no more tile on the deck.. player discard dummy tile.. and 
    	
    	if (playMode==3 && n_networkPlayerJoin==0) {
    		lastTile=tile;
    		return;
    	}
    	
    	winNoticeInsertFlag=false;
    	numOfDiscard++;
    	if (deck.size()==0 || 
    			(demoMode==Constant.TEST_CLEARHAND1 ||demoMode==Constant.TEST_CLEARHAND2 ||
    					demoMode==Constant.TEST_CLEARHAND3 || demoMode==Constant.TEST_CLEARHAND4) ) {
    		//debug(5,"doDiscard()... lastTile. ignoring the dummy discard");
    		giveTile();
    		return;
    	}    	
    	
    	//debug(30,"\nEngineFA.doDiscard().. player " + turn + " discard " + tile.type + "_" + tile.value);
    	player[turn].needAction=0;
    	lastplayernotice=-1;
    	
    	
    	//for forcing throwing certain tile... only used for testing
//    	tile=new Tile(2,3);
    	//end testing
    	//debug(500,"tilesOnTableSize before="+tilesOnTable.size());
    	tilesOnTable.addTileNoSorted(tile);
    	//debug(500,"tilesOnTableSize after="+tilesOnTable.size());
    	
    	lastDiscard=tile;
    	
    	for(int i=0;i<4;i++) {
    		//debug(30,"EngineFA.doDiscard():::calling gameUI().onTileDiscarded");            
    		if (i!=getPlayNum(turn) ) {
        		//debug(30,"calling ontilediscarded for player "+ getPlayNum(turn));            
    			gameUI[i].onTileDiscarded(tile,turn, deck.size());
    		}
    	}        

    	clearSpecialActionHelper();
    	
    	for (int i=1;i<4;i++) {
			int playerIndex=getPlayNum( (turn+i)%4);
//			doubleCount=checkWinHelper(playerIndex,eventQueue,tile);
			doubleWinCount[playerIndex]=checkWinHelper(playerIndex,eventQueue,tile);
			//			if (eventQueue.size()!=0) 	// if there is one or more players that can win, then exit the loop
//				break;
    	}
    	int previousQueueSize=eventQueue.size();

    	//check all player that can pong/kong
    	//will update the eventQueue vector if there is a player that can pong/kong        
    	for (int i=1;i<4;i++) {
    		int playerIndex=getPlayNum( (turn+i)%4);
    		//if (i!= playerIndex) {
    			//debug(3,"**************player num="+playerIndex);
    			
    			checkPongHelper(playerIndex,eventQueue,tile);
    			if (eventQueue.size()>previousQueueSize) 	// if there is one player that can pong/kong, exit the loop
    				break;
    		//}
    	}
		checkChowHelper( getPlayNum( (turn+1)%4  ),eventQueue,tile);
		
    	
		//outcome depends on the queue size;
        if (eventQueue.size()==0) {
        	//debug(1,"EngineFA.doDiscard()::calling EngineFA.giveTile");
            turn=(turn+1)%4;
            giveTile();
        }       
        else {
        	specialNoticeOnDiscarded();//some player can pong/kong/win
        }
    	
    }//end doDiscard()
    
    
    /*
     * modify the global variable eventQueue
     */	
    public void nextState(Tile tile) {
    	//can delegate this checking to client?
    	for (int i=1;i<4;i++) {
    		//if (i!=getPlayNum()) {
    			int playerIndex=getPlayNum( (turn+i)%4);
//    			doubleCount=checkWinHelper(playerIndex,eventQueue,tile);
    			doubleWinCount[playerIndex]=checkWinHelper(playerIndex,eventQueue,tile);
//    			if (eventQueue.size()!=0) 	// if there is one or more players that can win, then exit the loop
//    				break;
    	}
    	int previousQueueSize=eventQueue.size();

    	//check all player that can pong/kong
    	//will update the eventQueue vector if there is a player that can pong/kong        
    	for (int i=1;i<4;i++) {
    		int playerIndex=getPlayNum( (turn+i)%4);
    		//if (i!= playerIndex) {
    			//debug(3,"**************player num=" +getPlayNum( (turn+i+1)%4));
    			
    			checkPongHelper(playerIndex,eventQueue,tile);
    			if (eventQueue.size()>previousQueueSize) 	// if there is one player that can pong/kong, exit the loop
    				break;
    		//}
    	}
		checkChowHelper( getPlayNum( (turn+1)%4  ),eventQueue,tile);
    }
    
    
    /*21/09/2004
     *  algorithm to check when a player win
     */
    private int checkWinHelper(int index, Vector eventQueue, Tile discardedTile) {
    	int doubleCount;
    	//debug(3,"EngineFA.checkWinHelper(): player="+index);

    	player[index].specialAction.removeAllElements();
    	winningTile=new Tile(discardedTile.type,discardedTile.value);
    	player[index].concealedTiles.addTile(winningTile);
    	wineng.loadTiles(player[index].concealedTiles,player[index].visibleTiles, currentWind,player[index].getWind());

    	boolean isSelfPick=false;
    	if (turn==player[index].getWind()) {
    		isSelfPick=true;
    	}
    	doubleCount=wineng.checkWin(isSelfPick);
    
    	if (doubleCount>=0) {
    		if (numOfDiscard<2) {
    			doubleCount=Constant.WIN_TABLELIMIT;
    		}
    	}
    	player[index].concealedTiles.removeTile(winningTile);

    	
    	//debug(3,"doublecount="+doubleCount);
    	
    	if (doubleCount>=0 && isRobbingKong)
    		doubleCount++;
    	
    	if (doubleCount>=minDouble) {
    		//debug(3,"EngineFA.checkWinHelper() WIN!");
    		this.t_printOwnTiles(index);
        	if (isRobbingKong) {
        		player[index].specialAction.addElement(
    					new SpecialAction(GameAction.TYPE_WIN_ONROBBING,0,0) );
        		eventQueue.addElement(new SpecialAction(index,GameAction.TYPE_WIN_ONROBBING) );        		
        	}
        	else {
     			player[index].specialAction.addElement(
    					new SpecialAction(GameAction.TYPE_WIN,0,0) );
     			
     			//10june2005
     			int i=0;//i=insertion of a new SpecialAction position
     			if (eventQueue.size()!=0) {
     				//reorder the WIN first, then kong/chow 
     				SpecialAction tempAction;
     				for (i=0;i<eventQueue.size();i++) {
     					tempAction=(SpecialAction) eventQueue.elementAt(i);
     					if (tempAction.type!=GameAction.TYPE_WIN) {
     						break;
     					}
     				}
     			}
     			eventQueue.insertElementAt(new SpecialAction(index,GameAction.TYPE_WIN),i);
     			if (i!=eventQueue.size() && i!=0){     			
	     			//debug(3,"inserting win SPECIALACTION at "+ i);
	     			winNoticeInsertFlag=true;
     			}
//        		eventQueue.addElement(new SpecialAction(index,GameAction.TYPE_WIN) );
        	}
 			//isWin=true;
    		//debug(3,"WIN!, doublecount="+doubleCount);

    	}    		
    	else {
    		//debug(3,"NOT WIN!, doublecount="+doubleCount);
    		//debug(3,"***MinDouble="+minDouble);
    		//debug(3,"***MaxDouble="+maxDouble);
			
    	}
    	player[index].concealedTiles.resetType();
    	return doubleCount;
    }
    
    
    /* algorithm to check PONG/KONG when a tile is discarded
     * update the variable eventQueue
     * use global variable turn
     * pong when other party throw the tile
     * 
     * @write eventQueue
     * @write player[].specialAction
     */
    private void checkPongHelper(int index,Vector eventQueue, Tile discardedTile) {
    	//debug(1,"EngineFA.checkPongHelper()");
    	
    	int identCounter=1;//identical counter;
    	int tileposition=0;
    	int starttileposition=-1;
       	int previousSize=player[index].specialAction.size();
        
        TileCollection col=player[index].concealedTiles;
        int colsize=col.size();
        for (int i=0;i<colsize;i++) {
        	Tile currenttile = col.getTileAt(i);
        	if ( currenttile.type==discardedTile.type && currenttile.value==discardedTile.value) {
        		identCounter++;
        		if (starttileposition==-1) {
        			starttileposition=tileposition;//use this differently from checkWhenReceived()            		
        		}
        		if (identCounter==3) {
        			//debug(1,"********this is PONG!!!********* player[" +index+"]"  + currenttile.type+"_"+currenttile.value +"::::tile="+starttileposition+"-"+tileposition);
        			//t_printOwnTiles(index);
        			player[index].specialAction.addElement(
        					new SpecialAction(GameAction.TYPE_PONG,starttileposition,tileposition) );
        		}
        		if (identCounter==4) {
        			//debug(1,"********this is KONG!!!*********player[" +index+"]" + currenttile.type+"_"+currenttile.value +"::::tile="+starttileposition+"-"+tileposition);
        			//t_printOwnTiles(index);
        			isConcealedKong=false;
        			player[index].specialAction.addElement(
        					new SpecialAction(GameAction.TYPE_KONG,starttileposition,tileposition) );
        		}
        	}
        	else {
        		identCounter=1;
        		starttileposition=-1;
        	}            		
        	tileposition++;
        }//end for

        if (player[index].specialAction.size()>previousSize) {
        	//it doesn't matter if we use the gameaction TYPE_KONG or TYPE_PONG
        	eventQueue.addElement(new SpecialAction(index,GameAction.TYPE_KONG) );
        }
    }//end checkPongHelper()

    
    /*
	 * algorithm to check KONG when player received tile 
	 * 			include checking both visible and concealed tile
	 * 
	 * no write of eventQueue
	 * @write player[].specialAction
	 *  
	 */
	public int checkWhenReceived(int turn, Tile tile) {
		//debug(1,"EngineFA.checkWhenReceived()");
        
		//05/10/2004
		eventQueue.removeAllElements();

		int i=getPlayNum();//helper method to get the player index

		player[i].specialAction.removeAllElements();
				
		//05/10/2004
//		doubleCount=checkWinHelper(i,eventQueue,tile);
		doubleWinCount[i]=checkWinHelper(i,eventQueue,tile);	
		if (deck.size()!=0) {
			checkKongVisible(i,tile);
		}
		checkKongConcealed(i,tile);
	    
	    if (player[i].specialAction.isEmpty())
	    	return 0;
	    else
	    	return 1;
	}

	
	private void checkKongVisible(int index,Tile tile) {
		//grouptype=2
		kongVisible=false;
		//debug(2,"EngineFA.checkKongVisible");
		
		int tileposition=0;
		int starttileposition=-1;
		Tile currenttile=null;
		Tile temptile=null;
		
		
		TileCollection col=player[index].concealedTiles;
		TileCollection visibleTiles=player[index].visibleTiles;
		int concealTilesSize=col.size();
		int visibleTilesSize=visibleTiles.size();
		
		//debug(0,"EngineFA.checkKongVisible::::visibleTilessize="+visibleTilesSize);
		//debug(0,"EngineFA.checkKongVisible::::concealTilessize="+concealTilesSize);
		
		for(int i=0;i<visibleTilesSize;i++) {
			currenttile = visibleTiles.getTileAt(i);
			if (currenttile.groupType==2) {
				//debug(0,"EngineFA.checkKongVisible:::there is a PONG..tile="+currenttile);
				//check the new tile
				if (currenttile.equals(tile)) {
					player[index].specialAction.addElement(
	    					new SpecialAction(GameAction.TYPE_KONG,i,i+2,true) );					
					//debug(1,"********this is KONG!!!*********" + currenttile.type+"_"+currenttile.value +"::::tile="+starttileposition+"-"+tileposition);
					i=i+2;
					isConcealedKong=false;
					kongVisible=true;
				}
				else {
					for(int j=0;j<concealTilesSize;j++) {
						temptile = col.getTileAt(j);
						//debug(0,"EngineFA.checkKongVisible:::check the concealed tile..Tile="+temptile);
						if (currenttile.equals(temptile)) {
							player[index].specialAction.addElement(
			    					new SpecialAction(GameAction.TYPE_KONG,i,i+2,true) );					
							//debug(1,"********this is KONG!!!*********" + currenttile.type+"_"+currenttile.value +"::::tile="+starttileposition+"-"+tileposition);
							kongVisible=true;
							isConcealedKong=false;
							i=i+2;
							break;
						}
					}
				}
				//check the concealed tile
			}
			else if (currenttile.groupType==3) {
				i=i+2;
			}
		}
		
	}
	
	private void checkKongConcealed(int index, Tile tile) {
		//debug(1,"CheckKongConcealed() started.. tilesize=" + player[index].concealedTiles.size());
		int identCounter=1;//identical counter;
		int tileposition=0;
		int starttileposition=-1;
		int flagNewTileCounted=-1;
	    
		Tile prevtile=new Tile(9,9);
		
	    TileCollection col=player[index].concealedTiles;
	    int colsize=col.size();
	    
	    for (int i=0;i<colsize;i++) {
	    
	    	Tile currenttile = col.getTileAt(i);
	    	if ( currenttile.type==prevtile.type && currenttile.value==prevtile.value) {
	    		identCounter++;
	    		if (starttileposition==-1)
	    			starttileposition=tileposition-1; //becos we use a prev tile           		
	
	    		if (currenttile.type==tile.type && currenttile.value==tile.value) {
	    			if (flagNewTileCounted==-1) {
	    				identCounter++;            		
	    				flagNewTileCounted=1;
	    			}
	    		}
	
	    		if (identCounter==4) {
	    			//debug(1,"********this is CONCEALED KONG!!!*********" + currenttile.type+"_"+currenttile.value +"::::tile="+starttileposition+"-"+tileposition);
	    			//t_printOwnTiles(index);
	    			isConcealedKong=true;
	    			player[index].specialAction.addElement(
	    					new SpecialAction(GameAction.TYPE_KONG,starttileposition,tileposition) );
	    		}
	    	}
	    	else {
	    		identCounter=1;
	    		starttileposition=-1;
	    		flagNewTileCounted=-1;
	    	}            		
	    	prevtile=currenttile;
	    	tileposition++;
	    }//end while
		//debug(1,"CheckKongConcealed() finished");
	}
	
	
    /*
     * algorithm to check CHOW
     * 
     * @write player[i].specialAction.   add to the vector if it exists or create a new vector otherwise
     * @write eventQueue
     */
    private void checkChowHelper(int index,Vector eventQueue, Tile discardedTile) {
    	//debug(1,"EngineFA.checkChowHelper(), checkplayer[" + index+"]");
    	//debug(1,"discardedtile="+discardedTile);
    	
    	if (discardedTile.type>2)
    		return;
    	
    	int tileposition=0;
    	int min2pos=-9;
    	int min1pos=-9;
    	int plus1pos=-9;
    	int plus2pos=-9;
    	boolean canChow=false;

    	TileCollection col=player[index].concealedTiles;
    	int colsize=col.size();

    	for (int i=0;i<colsize;i++) {
    		Tile currenttile = col.getTileAt(i);
        	if (currenttile.type==discardedTile.type && discardedTile.type!=3 && discardedTile.type!=4) {
        		if (currenttile.value>discardedTile.value+2) {
        			break;
        		}
        		else {
        			if (currenttile.value==discardedTile.value-2) {
        				min2pos=tileposition;
        			}
        			else if (currenttile.value==discardedTile.value-1) {
        				min1pos=tileposition;
        			}
        			else if (currenttile.value==discardedTile.value+1) {
        				plus1pos=tileposition;
        			}
        			else if (currenttile.value==discardedTile.value+2) {
        				plus2pos=tileposition;
        			}
        		}
        	}
        	tileposition++;	
        }//end while
    	
    	//found the position, now check chow base on the above values
    	if (min2pos!=-9 && min1pos!=-9) {
			canChow=true;
			
			//debug(1,"********this is CHOW-2!!!********* tile"+ min2pos + " tile " + min1pos);
			//t_printOwnTiles(index);
			player[index].specialAction.addElement(
					new SpecialAction(GameAction.TYPE_CHOW,min2pos,min1pos) );
    	}
    	if (min1pos!=-9 && plus1pos!=-9) {
			canChow=true;
			//debug(1,"********this is CHOW-1!!!********* tile"+ min1pos + " tile " + plus1pos);
			//t_printOwnTiles(index);
			player[index].specialAction.addElement(
					new SpecialAction(GameAction.TYPE_CHOW,min1pos,plus1pos) );
    	}
    	if (plus1pos!=-9 && plus2pos!=-9) {
			canChow=true;
			//debug(1,"********this is CHOW+1!!!********* tile"+ plus1pos + " tile " + plus2pos);
			//t_printOwnTiles(index);
			player[index].specialAction.addElement(
					new SpecialAction(GameAction.TYPE_CHOW,plus1pos,plus2pos) );
    	}

        if (canChow) {
        	eventQueue.addElement(new SpecialAction(index,GameAction.TYPE_CHOW) );
        }
    }
    
    
    public int getNumberOfPlayer() {
    	return numOfPlayer;
    }
    
    public int getCurrentWind() {
    	return currentWind;
    }
    
    public void doConcealedKongResponseN(TileCollection visibleTiles, TileCollection col, boolean response, Tile tile) {
    	//give tile to current player;

    	//debug(3,"EngineFA.doConcealedKongResponseN()");
    	//debug(3,"Temp discard for robbing kong case="+tile);
    	
    	player[0].specialAction.removeAllElements();   
    	player[1].specialAction.removeAllElements();
    	player[2].specialAction.removeAllElements();
    	player[3].specialAction.removeAllElements();

    	int index=getPlayNum();
		int currentColSize=col.size();
    	
    	if (response==true) {
	    	//10/09/2004
	    	for(int i=0;i<4;i++) {
				if (i!=getPlayNum() ) {
					////debug(3,"Concealed Kong");
					//debug(1,"EngineFA:: player[" + i + "] visibleTilesSize:" + player[i].visibleTiles.size());
					gameUI[i].onTileTaken(visibleTiles,
							currentColSize+1 , turn, false, tile);
				}
			}
	    	
			player[index].concealedTiles=col;
			player[index].visibleTiles=visibleTiles;

			//check robbing the kong
	    	//temporary disabled
	    	
	    	if (checkRobbingKong(tile) ) {
	    		//save the previous information first in case user not responding to robbing the kong
	    		
	    		return;
	    	}
	    	

	    	scoreKong(getPlayNum(),isConcealedKong);
	    	//debug(1,"EngineFA.doConcealedKongResponseN() Before give tile. decksize=" + deck.size());
	    	giveTile();
    	}
    }
    
    
    /*
     * if player do the kong and its his turn
	*/
    public void doConcealedKongResponse(boolean response, Tile tile) {
    	//give tile to current player;
    	//debug(3,"EngineFA.doConcealedKongResponse()");
    	//debug(3,"Tile="+tile);
    	
    	player[0].specialAction.removeAllElements();   
    	player[1].specialAction.removeAllElements();
    	player[2].specialAction.removeAllElements();
    	player[3].specialAction.removeAllElements();
    	
    	if (response==true) {
	    	for(int i=0;i<4;i++) {
				if (i!=getPlayNum() ) {
					//debug(1,"EngineFA:: player[" + i + "] visiblTilesSize:" + player[i].visibleTiles.size());
					gameUI[i].onTileTaken(player[getPlayNum()].visibleTiles,
							player[getPlayNum()].concealedTiles.size()+1 , turn, false, tile);
				}
			}

	    	//check robbing the kong
	    	if (checkRobbingKong(tile) ) {
	    		return;
	    	}
	    	scoreKong(getPlayNum(),isConcealedKong);

	    	//debug(1,"EngineFA.doConcealedKongResponse() Before give tile. decksize=" + deck.size());
	    	giveTile();
	    	//05/10/2004
			//gameUI[getPlayNum()].onTurn();
    	}
    }


   	public void doSpecialResponseN(TileCollection visibleTiles, TileCollection col, int type, boolean response, int wind) {
   		//debug(3,"doSpecialResponseN::visibletiles="+ visibleTiles.size() + " ;concealedTiles="+ col.size());
   		int index=getPlayNum(wind);
   		//debug(3,"player index="+ index);
   		
    	//this thing cause major problem before it is fixed
   		if (response==true) {		
	   		////debug(1,"EngineFA.doSpecialResponseN(), player="+wind+";response="+response);
			player[index].needAction=1;
			////debug(3,"local player tile size before synchronization="+ player[index].concealedTiles.size());
			Tile lasttile=tilesOnTable.getTileAt(tilesOnTable.size()-1);
			tilesOnTable.removeLastTile();
			
	    	player[0].specialAction.removeAllElements();   
	    	player[1].specialAction.removeAllElements();
	    	player[2].specialAction.removeAllElements();
	    	player[3].specialAction.removeAllElements();
	    	
			player[index].concealedTiles=col;
			player[index].visibleTiles=visibleTiles;
			int concealedSize=col.size();
			//debug(3,"adding the the last discarded tile locally=" + lastDiscard);	
			//inform the other the redraw the screen
			for(int i=0;i<4;i++) {
				if (i!=getPlayNum(wind) ) {
					//debug(1,"EngineFA:: player[" + i + "] visiblTilesSize:" + player[i].visibleTiles.size());
					gameUI[i].onTileTaken(player[getPlayNum(wind)].visibleTiles,
							concealedSize , wind,true,lasttile);
				}
			}
			
			turn=wind;
			int i=getPlayNum();
			//debug(1,"EngineFA.doSpecialResponse()::calling gameUI.onTurn()");
	    	if (type==GameAction.TYPE_KONG) {
	    		
				scoreKong(i,false);
				
				giveTile();
	    	}
		}
		else {
			//debug(3,"ignoring the special response");
			specialNoticeOnDiscarded();//check other player if they can pong/kong/chow
		}
	}

	/*
	 * for response made by player if it's not the player turn
	 * 
	 * if type of response=KONG ---> give a new tile to the player
	 * else: call the onTurn()
	 */
	public void doSpecialResponse(int type, boolean response, int wind) {

		//debug(1000,"EngineFA.doSpecialResponse(), player="+wind+";response="+response);
		player[getPlayNum(wind)].needAction=1;
		
		
		if (response==true) {
			Tile lasttile=tilesOnTable.getTileAt(tilesOnTable.size()-1);
			tilesOnTable.removeLastTile();
			//inform the other the redraw the screen
			for(int i=0;i<4;i++) {
				if (i!=getPlayNum(wind) ) {
					//debug(1000,"EngineFA:: player[" + i + "] visiblTilesSize:" + player[i].visibleTiles.size());
					gameUI[i].onTileTaken(player[getPlayNum(wind)].visibleTiles,
							player[getPlayNum(wind)].concealedTiles.size() , wind,true,lasttile);
				}
			}
			turn=wind;
			int i=getPlayNum();
			//debug(1000,"EngineFA.doSpecialResponse()::calling gameUI.onTurn()");
	    	if (type==GameAction.TYPE_KONG) {
	    		//26/04/2005
	    		giveTile();
				scoreKong(i,false);
	    	}
		}
		else {
			specialNoticeOnDiscarded();//check other player if they can pong/kong/chow
		}
	}

	public void doShowScoreResponse() {//nobody win
    	//debug(5,"EngineFA.doShowScoreResponse()" );
		//reveal all the other tile and calculate the score, and show the score
    	int playerScore[]= new int[4];

    	for (int i=0;i<4;i++) {
        		player[getPlayNum(i)].payPoint=
        				player[getPlayNum(i)].payKongPoint;
    			player[getPlayNum(i)].totalPoint=player[getPlayNum(i)].totalPoint-
					player[getPlayNum(i)].payPoint;
    	}

    	//debug(10,"Player score on nobody win");
    	//debug(10,"Player 0: Score="+ playerScore[getPlayNum(0)] + " ;total point="+player[getPlayNum(0)].totalPoint);
    	//debug(10,"Player 1: Score="+ playerScore[getPlayNum(1)] + " ;total point="+player[getPlayNum(1)].totalPoint);
    	//debug(10,"Player 2: Score="+ playerScore[getPlayNum(2)] + " ;total point="+player[getPlayNum(2)].totalPoint);
    	//debug(10,"Player 3: Score="+ playerScore[getPlayNum(3)] + " ;total point="+player[getPlayNum(3)].totalPoint);
    	    	
    	for(int i=0;i<4;i++) {
			//if (i!=getPlayNum(wind) ) {
				//debug(3,"EngineFA:: player[" + i + "] visibleTilesSize:" + player[i].visibleTiles.size());
				gameUI[i].onWin(9, player[getPlayNum(0)].concealedTiles, player[getPlayNum(0)].visibleTiles,  playerScore[getPlayNum(0)],-player[getPlayNum(0)].payPoint,player[getPlayNum(0)].totalPoint,
						 player[getPlayNum(1)].concealedTiles, player[getPlayNum(1)].visibleTiles,playerScore[getPlayNum(1)],-player[getPlayNum(1)].payPoint,player[getPlayNum(1)].totalPoint,
						 player[getPlayNum(2)].concealedTiles, player[getPlayNum(2)].visibleTiles, playerScore[getPlayNum(2)],-player[getPlayNum(2)].payPoint,player[getPlayNum(2)].totalPoint,
						 player[getPlayNum(3)].concealedTiles, player[getPlayNum(3)].visibleTiles, playerScore[getPlayNum(3)],-player[getPlayNum(3)].payPoint,player[getPlayNum(3)].totalPoint);
			//}
		}
		
	}
	
    
    public void doWinResponse(int wind) {
    	//debug(5,"EngineFA.doWinResponse():wind="+wind );
    	nextGamePressed=false;
    	
    	lastWinningPlayerWind=wind;
    	int countPay=0;
    	int playerScore[]= new int[4];
    	
    	//converting double to numeric
    	int numDouble=0;
    	doubleCount=doubleWinCount[getPlayNum(wind)];
//    	System.out.println("doubleCount="+doubleCount+ " ;wind="+wind+ " ;playnum="+getPlayNum(wind));
    	if (doubleCount>=Constant.WIN_TABLELIMIT) {
    		doubleCount=this.maxDouble;
    	}
    	
    	if (doubleCount!=0) {
	    	numDouble=1;
    		for (int i=0;i<doubleCount&&i<Constant.WIN_TABLELIMIT;i++) {
	    		numDouble=numDouble*2;
	    	}
    	}
    	else {    	//if there is zero double
    		numDouble=1;
    	}
//    	//debug(5,"doubleCount="+doubleCount+ " ;numDouble="+numDouble);
    	
    	for (int i=0;i<4;i++) {
    		if (i==wind) {
    			playerScore[getPlayNum(i)]=doubleCount;
    		}
    		else {
    			playerScore[getPlayNum(i)]=-doubleCount;
    			
    			if (i==turn || wind==turn ) {
    				//debug(4,"before " + player[getPlayNum(i)].payPoint);
        			player[getPlayNum(i)].payPoint=
        				double2Point*numDouble*2 +player[getPlayNum(i)].payKongPoint;
        			//debug(10,"after" + player[getPlayNum(i)].payPoint);
    			}
        		else {
        			//debug(10,"before " + player[getPlayNum(i)].payPoint);
        			//player[getPlayNum(i)].payPoint=double2Point*numDouble +player[getPlayNum(i)].payKongPoint;
        			player[getPlayNum(i)].payPoint=player[getPlayNum(i)].payKongPoint;
        			//debug(10,"after" + player[getPlayNum(i)].payPoint);
        		}
    	
    			player[getPlayNum(i)].totalPoint=player[getPlayNum(i)].totalPoint-
					player[getPlayNum(i)].payPoint;

    			//debug(10,"player " + i + " paypoint=" + player[getPlayNum(i)].payPoint);
    			countPay=countPay+player[getPlayNum(i)].payPoint; 
    			//debug(10,"countpay="+countPay);
    		}
    	}
		player[getPlayNum(wind)].totalPoint=player[getPlayNum(wind)].totalPoint+countPay;    		
    	player[getPlayNum(wind)].payPoint= -countPay;
		
    	//manually add the winnning tile if its network mode and its a human player
    	if (playMode!=0 &&  !(gameUI[getPlayNum(wind)] instanceof AIPlayer)   ) {
    		System.out.println("playmode="+playMode);
    		player[getPlayNum(wind)].concealedTiles.addTile(winningTile);
    	}

    	//for AIPlayer manually remove the kong set from player add the winning tile to other player if its robbing the kong
    	if (isRobbingKong ) {
    		player[getPlayNum(turnBeforeRobbingKong)].visibleTiles.removeTile(winningTile);
    		if (gameUI[getPlayNum(wind)] instanceof AIPlayer) {
        		player[getPlayNum(wind)].concealedTiles.addTile(winningTile);    		    			
    		}
    	}
    	
    	
    	
    	
//    	//debug(1500,"Player 0: Score="+ playerScore[getPlayNum(0)] + " ;total point="+player[getPlayNum(0)].totalPoint+ " ;double=");
//    	//debug(1500,"Player 1: Score="+ playerScore[getPlayNum(1)] + " ;total point="+player[getPlayNum(1)].totalPoint);
//    	//debug(1500,"Player 2: Score="+ playerScore[getPlayNum(2)] + " ;total point="+player[getPlayNum(2)].totalPoint);
//    	//debug(1500,"Player 3: Score="+ playerScore[getPlayNum(3)] + " ;total point="+player[getPlayNum(3)].totalPoint);

    	
		//reveal all the other tile and calculate the score, and show the score
		for(int i=0;i<4;i++) {
			//if (i!=getPlayNum(wind) ) {
				//debug(3,"EngineFA:: player[" + i + "] visibleTilesSize:" + player[i].visibleTiles.size());
				gameUI[i].onWin(wind, player[getPlayNum(0)].concealedTiles, player[getPlayNum(0)].visibleTiles,  playerScore[getPlayNum(0)],-player[getPlayNum(0)].payPoint,player[getPlayNum(0)].totalPoint,
						 player[getPlayNum(1)].concealedTiles, player[getPlayNum(1)].visibleTiles,playerScore[getPlayNum(1)],-player[getPlayNum(1)].payPoint,player[getPlayNum(1)].totalPoint,
						 player[getPlayNum(2)].concealedTiles, player[getPlayNum(2)].visibleTiles, playerScore[getPlayNum(2)],-player[getPlayNum(2)].payPoint,player[getPlayNum(2)].totalPoint,
						 player[getPlayNum(3)].concealedTiles, player[getPlayNum(3)].visibleTiles, playerScore[getPlayNum(3)],-player[getPlayNum(3)].payPoint,player[getPlayNum(3)].totalPoint);
			//}
		}
		turn=wind;
    }

    private void scoreKong(int playerNum, boolean selfPick) {
    	//countdoubl
    	somebodyKong=true;
    	int multiple=1;
    	if (selfPick)
    		multiple=2;
    	
    	for (int i=0;i<4;i++) {
    		if (i!=playerNum) {
    			player[i].payKongPoint=player[i].payKongPoint+unitKongPoint*multiple;
    	    	//debug(3,"EngineFA.scoreKong() player " + i + " pay " + unitKongPoint*multiple + " points");
    	    	//debug(3,"EngineFA.scoreKong() player " + i + " total pay " + player[i].payKongPoint + " points");
    		}
    	}
    	player[playerNum].payKongPoint= player[playerNum].payKongPoint -3*unitKongPoint*multiple;
    	//debug(3,"EngineFA.scoreKong() player " + playerNum+ " total receive " + player[playerNum].payKongPoint + " points");

    }
	
	    
    private void specialNoticeOnDiscarded() {
    	//debug(400,"EngineFA.specialNoticeOnDiscarded()");
    	Enumeration enum=eventQueue.elements();
    	//debug(400,"EngineFA.specialNoticeOnDiscarded(). event queue size="+ eventQueue.size());
    	
    	if(enum.hasMoreElements()) {
    		SpecialAction action= (SpecialAction) enum.nextElement();
    		//debug(400,"EngineFA.specialNoticeonDiscarded():::***notice player "+ action.player  +" for pong/kong/chow");
        	eventQueue.removeElementAt(0);
        	//debug(100,"Size after removing="+eventQueue.size());
        	//while its the same player, skip the notice
        	while (action.player==lastplayernotice && !eventQueue.isEmpty()) {
        		enum=eventQueue.elements();
        		action= (SpecialAction) enum.nextElement();
        		//debug(400,"remove notice from same player");
        		eventQueue.removeElementAt(0);        		
        	}
        	//debug(100,"after removing redundant="+eventQueue.size());
        	
        	if (lastplayernotice!=action.player) {
	        	lastplayernotice=action.player;
	        	//debug(400,"notice player!! + " + lastplayernotice);
	        	player[action.player].needAction=3;
	        
	        	//10june2005
	        	if (!winNoticeInsertFlag) {
		        	gameUI[action.player].onNotice2(player[action.player].specialAction);//2=Pong&Kong                      			        			        		        		
	        	}
	        	else {
	        		Vector actions=new Vector();
	        		SpecialAction tempAction=(SpecialAction) player[action.player].specialAction.firstElement();
	        		player[action.player].specialAction.removeElementAt(0);
	        		actions.addElement(tempAction);
	        		gameUI[action.player].onNotice2(actions);//2=Pong&Kong                      			        			        		        		
	        	}

        	}
        	else {
        		//debug(400,"EngineFA.specialNoticeonDiscarded()2.giveTile()");
    			//if the player ignore the pong/kong
    			if (isRobbingKong) {
    				//debug(400,"EngineFA.specialNoticeonDiscarded()2. player ignore to win on robbing kong");
    				//17/11/2004    	
    				scoreKong(getPlayNum(),true);
    				giveTile();
    			}
    			else {        		
	        		//if no more player to notice, give tile to current turn.
    				//debug(400,"EngineFA.specialNoticeonDiscarded()2. no more player to notice");
	                turn=(turn+1)%4;
	        		giveTile();
    			}
        	}
            
    	}
    	else {
    		//if no more player to notice, give tile to current turn.
    		//debug(400,"EngineFA.specialNoticeonDiscarded() no more special notice");
			if (isRobbingKong) {
				giveTile();
			}
			else {
	    		//debug(1000,"EngineFA.specialNoticeonDiscarded()xxxxx2.giveTile()");
	            turn=(turn+1)%4;
	            giveTile();
			}
    	}
    	
    }
        
    public void setGameRule(int p_minDouble, int p_maxDouble) {
    	//debug(3,"EngineFA.setGameRule()");
    	minDouble=p_minDouble;
    	maxDouble=p_maxDouble;
    }
    
    
    

    /* helper method
     * get index of player in the array of players that match the current turn
     */
    private int getPlayNum() {
    	for(int i=0;i<4;i++) {
            if(player[i].getWind()==turn) {
            	//listPong.removeElementAt(0);
                return i;
            }
    	}
    	return 0;
    }
    
    private int getPlayNum(int wind) {
    	for(int i=0;i<4;i++) {
            if(player[i].getWind()==wind) {
            	//listPong.removeElementAt(0);
                return i;
            }
    	}
    	return 0;
    }
    
    public void setCanvas(PublicNetworkCanvas p_canvas){}
    public void quitGame() {}
    
    
    public void sendMessageTable(String message, int cid) {
    	//get the current player nick
    	//debug(10,"EngineFA.sendMessage");
    	String nick="";
    	for (int i=0;i<4;i++) {
    		if (player[i].cid==cid) {
    			nick=player[i].nick;
    			break;
    		}
    	}
    	for (int i=0;i<4;i++) {
    		if (player[i].cid!=cid) {
    			//debug(10,"calling ui proxy for player " + i);
    			gameUI[i].onTableMessage(nick+" : "+message);
    		}
    	}
    	//debug(10,"finish EngineFA.sendMessage");
    }
    
    
    
    
/*
 * *********************** TEST CODE ********************************
 */    
    
    
    
    private String tilelistConcealKong3[]={"1_2"};
    private String tilelistConcealKong4[]={"0_8","1_2"};
    private String tilelistClearHand3[]={"0_3"};
    
    /*
     * helper method
     */    
    private Tile getTileFromString(String strTile) {
    	int type= Integer.parseInt( strTile.substring(0,1) );
    	int value = Integer.parseInt (strTile.substring(2,3) );    
    	return new Tile(type,value);
    }
    
    /*
     * use global variable t_tileGivenCount
     * predetermined tile sequence
     * for testing purpose only
     */
    private Tile getPreTile(int playMode) {
    	//get tile
    	//debug(2,"getPreTile()::playMode"+playMode+ " ;tileGivenCount="+t_tileGivenCount);
    	Tile newtile=null;
    	if (playMode==Constant.TEST_CONCEALKONG3) {
        	if (t_tileGivenCount-1<tilelistConcealKong3.length) {
        		newtile=getTileFromString(tilelistConcealKong3[t_tileGivenCount-1]  );
        		//debug(0,"EngineFA.getPretile()::tile="+newtile);
        	}
        	else {
        		newtile = deck.getRandomTile();
        	}
    	}
    	else if (playMode==Constant.TEST_CLEARHAND3) {
        	if (t_tileGivenCount-1<tilelistClearHand3.length) {
        		newtile=getTileFromString(tilelistClearHand3[t_tileGivenCount-1]  );
        		//debug(0,"EngineFA.getPretile()::tile="+newtile);
        	}
        	else {
        		newtile = deck.getRandomTile();
        	}
    	}
    	return newtile;
    	
    }
    
    public void setDemoMode(int p_demoMode) {
    	demoMode=p_demoMode;
    }
    
    
    
    
    /****************************************TEST CODE
     */
    private void testPong1() {
    	String tilelist1[]={
    			"0_1","0_1","3_2","3_2",
				"0_3","0_3","0_4","0_4",
    			"1_5","1_5","0_6","0_6","0_7"};		    	
    
    	player[0].loadBulkTiles(tilelist1);
        
    	gameUI[0].onStart(player[0].concealedTiles, player[0].visibleTiles);
		player[0].printVisible();
    	String tilelist2[]={
    			"0_1","0_1",    "3_2","2_2",
    			"0_3","0_3",    "0_4","0_4","1_5","1_5","0_6","0_6","0_5"};		    	
    	String tilelist3[]={
    			"0_4","0_4",    "1_5","1_5",
    			"0_6","0_6",    "2_1","2_1","2_1","2_3","2_3","2_4","2_5"};		    	
		
		player[1].loadBulkTiles(tilelist2);
        gameUI[1].onStart(player[1].concealedTiles, player[1].visibleTiles);		
        player[2].loadBulkTiles(tilelist3);
        gameUI[2].onStart(player[2].concealedTiles, player[2].visibleTiles);
        player[3].loadInitialTile();
        gameUI[3].onStart(player[3].concealedTiles, player[3].visibleTiles);

    	for(int i=0;i<1;i++) {
			gameUI[i].onTileTaken(player[ (i+1)%4 ].visibleTiles,
					player[(i+1)%4 ].concealedTiles.size()+1 , player[(i+1)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+2)%4 ].visibleTiles,
					player[(i+2)%4 ].concealedTiles.size()+1 , player[(i+2)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+3)%4 ].visibleTiles,
					player[(i+3)%4 ].concealedTiles.size()+1 , player[(i+3)%4 ].getWind(), false,null);
		}
    }//end testKong1

    private void testPong2() {
    	String tilelist1[]={
    			"0_1","0_1","0_1", "3_2","3_2",
				"0_3","0_3","0_4","0_4",
    			"1_5","1_5","0_6","0_6"};		    	
    
    	player[0].loadBulkTiles(tilelist1);
        
    	gameUI[0].onStart(player[0].concealedTiles, player[0].visibleTiles);
		player[0].printVisible();
		
		player[1].loadInitialTile();
        gameUI[1].onStart(player[1].concealedTiles, player[1].visibleTiles);		
		player[2].loadInitialTile();
        gameUI[2].onStart(player[2].concealedTiles, player[2].visibleTiles);
        player[3].loadInitialTile();
        gameUI[3].onStart(player[3].concealedTiles, player[3].visibleTiles);

    	for(int i=0;i<1;i++) {
			gameUI[i].onTileTaken(player[ (i+1)%4 ].visibleTiles,
					player[(i+1)%4 ].concealedTiles.size()+1 , player[(i+1)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+2)%4 ].visibleTiles,
					player[(i+2)%4 ].concealedTiles.size()+1 , player[(i+2)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+3)%4 ].visibleTiles,
					player[(i+3)%4 ].concealedTiles.size()+1 , player[(i+3)%4 ].getWind(), false,null);
		}
    }//end testKong1

    
    // test concealed kong and robbing the kong
    // comp player rob our kong
    private void testNetworkKong1() {
    	String tilelist1[]={
    			"0_1","0_1","0_1","0_1",
				"1_5","1_5","1_5","2_7",
    			"0_3","0_3","4_2","4_2","2_7"};		    	
    	player[0].loadBulkTiles(tilelist1);
		player[0].printVisible();
    	String tilelist2a[]={
    			"3_1","3_1",    "0_2","0_4",
    			"2_3","2_3","2_3",
				"2_5","2_6","2_7",
				"4_1","4_1","4_1"
    			};		    	
        player[1].loadInitialTile();
		player[2].loadBulkTiles(tilelist2a);
        player[3].loadInitialTile();
        gameUI[0].onStart(player[0].concealedTiles, player[0].visibleTiles);
        gameUI[1].onStart(player[1].concealedTiles, player[1].visibleTiles);
        gameUI[2].onStart(player[2].concealedTiles, player[2].visibleTiles);		
        gameUI[3].onStart(player[3].concealedTiles, player[3].visibleTiles);

    }//end testNetworkKong1

    
    
    
    
    // test concealed kong and robbing the kong
    // comp player rob our kong
    private void testKong1() {
    	String tilelist1[]={
    			"0_1","0_1","0_1","0_1",
				"1_5","1_5","1_5","2_7",
    			"0_3","0_3","0_3","0_3","2_8"};		    	
    	player[0].loadBulkTiles(tilelist1);
        gameUI[0].onStart(player[0].concealedTiles, player[0].visibleTiles);
		player[0].printVisible();
    	String tilelist2a[]={
    			"3_1","3_1",    "0_2","0_4",
    			"2_3","2_3","2_3"};		    	
    	String tilelist2b[]={
    			"2_5_0_3","2_6_0_3","2_7_0_3",
    			"4_1_1_2","4_1_1_2","4_1_1_2"};	
		
		player[2].loadBulkTiles(tilelist2a,tilelist2b);
        gameUI[2].onStart(player[2].concealedTiles, player[2].visibleTiles);		
        player[1].loadInitialTile();
        gameUI[1].onStart(player[1].concealedTiles, player[1].visibleTiles);
        player[3].loadInitialTile();
        gameUI[3].onStart(player[3].concealedTiles, player[3].visibleTiles);

    	for(int i=0;i<1;i++) {
			gameUI[i].onTileTaken(player[ (i+1)%4 ].visibleTiles,
					player[(i+1)%4 ].concealedTiles.size()+1 , player[(i+1)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+2)%4 ].visibleTiles,
					player[(i+2)%4 ].concealedTiles.size()+1 , player[(i+2)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+3)%4 ].visibleTiles,
					player[(i+3)%4 ].concealedTiles.size()+1 , player[(i+3)%4 ].getWind(), false,null);
		}
    }//end testKong1

    // test concealed kong and robbing the kong
    // we rob comp player kong
    private void testKong2() {
    	String tilelist1[]={
    			"0_1","0_1","0_1","0_1",
				"1_5","1_5","1_5","2_7",
    			"0_3","0_3","0,3","0_3","2_8"};		    	
    	String tilelist2a[]={
    			"3_1","3_1",    "0_2","0_4",
    			"2_3","2_3","2,3"};		    	
    	String tilelist2b[]={
    			"2_5_0_3","2_6_0_3","2_7_0_3",
    			"4_1_1_2","4_1_1_2","4_1_1_2"};	

    	player[2].loadBulkTiles(tilelist1);
        gameUI[2].onStart(player[2].concealedTiles, player[2].visibleTiles);
		
		player[0].loadBulkTiles(tilelist2a,tilelist2b);
        gameUI[0].onStart(player[0].concealedTiles, player[0].visibleTiles);		
		player[0].printVisible();
        player[1].loadInitialTile();
        gameUI[1].onStart(player[1].concealedTiles, player[1].visibleTiles);
        player[3].loadInitialTile();
        gameUI[3].onStart(player[3].concealedTiles, player[3].visibleTiles);

    	for(int i=0;i<1;i++) {
			gameUI[i].onTileTaken(player[ (i+1)%4 ].visibleTiles,
					player[(i+1)%4 ].concealedTiles.size()+1 , player[(i+1)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+2)%4 ].visibleTiles,
					player[(i+2)%4 ].concealedTiles.size()+1 , player[(i+2)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+3)%4 ].visibleTiles,
					player[(i+3)%4 ].concealedTiles.size()+1 , player[(i+3)%4 ].getWind(), false,null);
		}
    }//end testKong2
 
    //test kong formed by other discard
    private void testKong3() {
    	String tilelist1[]={
    			"0_1","0_1","0_1",
				"1_5","1_5","1_5",
    			"0_3","0_3","0,3",
				"3_1","3_1","3_1",
				"3_2"};		    	
    	String tilelist2[]={
    			"0_1","1_5","0_3","3_1",
				"2_1","2_2","2_3","2_4",
				"2_5","2_6","2_7","2_8",
				"2_9"};
    
		player[0].loadBulkTiles(tilelist1);
		player[0].printVisible();
        player[1].loadBulkTiles(tilelist2);
	
        player[2].loadInitialTile();
    	player[3].loadInitialTile();

        gameUI[0].onStart(player[0].concealedTiles, player[0].visibleTiles);		
        gameUI[1].onStart(player[1].concealedTiles, player[1].visibleTiles);
        gameUI[2].onStart(player[2].concealedTiles, player[2].visibleTiles);
        gameUI[3].onStart(player[3].concealedTiles, player[3].visibleTiles);

        /*
    	for(int i=0;i<1;i++) {
			gameUI[i].onTileTaken(player[ (i+1)%4 ].visibleTiles,
					player[(i+1)%4 ].concealedTiles.size()+1 , player[(i+1)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+2)%4 ].visibleTiles,
					player[(i+2)%4 ].concealedTiles.size()+1 , player[(i+2)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+3)%4 ].visibleTiles,
					player[(i+3)%4 ].concealedTiles.size()+1 , player[(i+3)%4 ].getWind(), false,null);
		}
		*/
    }

    
    //test concealed kong
    private void testConcealKong1() {
    	//debug(2,"EngineFA.testConcealKong1");
    	String tilelist1a[]={
    			"1_1","0_1","0_1",
				"1_5","1_5","1_5","1_5",
    			"0_3","0_3","0_3"
				};		    	
    	String tilelist1b[]={
    			"1_2_0_2","1_2_0_2","1_2_0_2"};
    					
		player[0].loadBulkTiles(tilelist1a,tilelist1b);
        gameUI[0].onStart(player[0].concealedTiles, player[0].visibleTiles);		
		player[0].printVisible();
        player[1].loadInitialTile();
        gameUI[1].onStart(player[1].concealedTiles, player[1].visibleTiles);
        player[2].loadInitialTile();
        gameUI[2].onStart(player[2].concealedTiles, player[2].visibleTiles);
    	player[3].loadInitialTile();
        gameUI[3].onStart(player[3].concealedTiles, player[3].visibleTiles);

    	for(int i=0;i<1;i++) {
			gameUI[i].onTileTaken(player[ (i+1)%4 ].visibleTiles,
					player[(i+1)%4 ].concealedTiles.size()+1 , player[(i+1)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+2)%4 ].visibleTiles,
					player[(i+2)%4 ].concealedTiles.size()+1 , player[(i+2)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+3)%4 ].visibleTiles,
					player[(i+3)%4 ].concealedTiles.size()+1 , player[(i+3)%4 ].getWind(), false,null);
		}
    }

    //test concealed kong
    
    
    
    
    private void testConcealKong2() {
    	//debug(2,"EngineFA.testConcealKong2");

    	/*
    	String tilelist1a[]={
    			"1_1","0_1","0_1",
				"1_5","1_5","1_5","1_2",
    			"0_3","0_3","0_3"
				};		    	
		*/
    	
    	//use this for network testing, coz network mode doesnt allow preloading of visibletiles
    	String tilelist1a[]={
    			"1_1","0_1","0_1",
				"1_5","1_5","1_5","1_2",
    			"0_3","0_3","0_3","1_2","1,2","1,2"
				};		    	
    	
    	
    	String tilelist1b[]={
    			"1_2_0_2","1_2_0_2","1_2_0_2"};
    					
		//player[0].loadBulkTiles(tilelist1a,tilelist1b);
		player[0].loadBulkTiles(tilelist1a);
		player[0].printVisible();
        player[1].loadInitialTile();
        player[2].loadInitialTile();
    	player[3].loadInitialTile();
        gameUI[0].onStart(player[0].concealedTiles, player[0].visibleTiles);		
        gameUI[1].onStart(player[1].concealedTiles, player[1].visibleTiles);
        gameUI[2].onStart(player[2].concealedTiles, player[2].visibleTiles);
        gameUI[3].onStart(player[3].concealedTiles, player[3].visibleTiles);

        /*
    	for(int i=0;i<1;i++) {
			gameUI[i].onTileTaken(player[ (i+1)%4 ].visibleTiles,
					player[(i+1)%4 ].concealedTiles.size()+1 , player[(i+1)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+2)%4 ].visibleTiles,
					player[(i+2)%4 ].concealedTiles.size()+1 , player[(i+2)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+3)%4 ].visibleTiles,
					player[(i+3)%4 ].concealedTiles.size()+1 , player[(i+3)%4 ].getWind(), false,null);
		}
		*/
    }

  

    //player can kong or win on the lasttile
    private void testClearHand3() {
    	//debug(2,"EngineFA.testClearHand3");
    	String tilelist1a[]={
    			"0_1","0_1",
				"2_5","2_5","2_5","2_5",
				"2_6","2_7",
    			"0_3","0_3"
				};		    	
    	String tilelist1b[]={
    			"1_2_0_2","1_2_0_2","1_2_0_2"};
    					
		player[0].loadBulkTiles(tilelist1a,tilelist1b);
        gameUI[0].onStart(player[0].concealedTiles, player[0].visibleTiles);		
		player[0].printVisible();
        player[1].loadInitialTile();
        gameUI[1].onStart(player[1].concealedTiles, player[1].visibleTiles);
        player[2].loadInitialTile();
        gameUI[2].onStart(player[2].concealedTiles, player[2].visibleTiles);
    	player[3].loadInitialTile();
        gameUI[3].onStart(player[3].concealedTiles, player[3].visibleTiles);

    	for(int i=0;i<1;i++) {
			gameUI[i].onTileTaken(player[ (i+1)%4 ].visibleTiles,
					player[(i+1)%4 ].concealedTiles.size()+1 , player[(i+1)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+2)%4 ].visibleTiles,
					player[(i+2)%4 ].concealedTiles.size()+1 , player[(i+2)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+3)%4 ].visibleTiles,
					player[(i+3)%4 ].concealedTiles.size()+1 , player[(i+3)%4 ].getWind(), false,null);
		}
    	
    }
    
    
    private void testConcealKong3() {
    	//debug(2,"EngineFA.testConcealKong3");
    	String tilelist1a[]={
    			"1_1","0_1","0_1",
				"1_5","1_5","1_5","1_5",
    			"0_3","0_3","0_3"
				};		    	
    	String tilelist1b[]={
    			"1_2_0_2","1_2_0_2","1_2_0_2"};
    					
		player[0].loadBulkTiles(tilelist1a,tilelist1b);
        gameUI[0].onStart(player[0].concealedTiles, player[0].visibleTiles);		
		player[0].printVisible();
        player[1].loadInitialTile();
        gameUI[1].onStart(player[1].concealedTiles, player[1].visibleTiles);
        player[2].loadInitialTile();
        gameUI[2].onStart(player[2].concealedTiles, player[2].visibleTiles);
    	player[3].loadInitialTile();
        gameUI[3].onStart(player[3].concealedTiles, player[3].visibleTiles);

    	for(int i=0;i<1;i++) {
			gameUI[i].onTileTaken(player[ (i+1)%4 ].visibleTiles,
					player[(i+1)%4 ].concealedTiles.size()+1 , player[(i+1)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+2)%4 ].visibleTiles,
					player[(i+2)%4 ].concealedTiles.size()+1 , player[(i+2)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+3)%4 ].visibleTiles,
					player[(i+3)%4 ].concealedTiles.size()+1 , player[(i+3)%4 ].getWind(), false,null);
		}
    }
    
    
    //concealed kong perform by bot
    private void testConcealKong4() {
    	//debug(2,"EngineFA.testConcealKong4");
    	String tilelist1a[]={
    			"1_1","0_1","0_1",
				"1_5","1_5","1_5","1_5",
    			"0_3","0_3","0_3"
				};		    	
    	String tilelist1b[]={
    			"1_2_0_2","1_2_0_2","1_2_0_2"};
    					

    	player[1].loadBulkTiles(tilelist1a,tilelist1b);
        gameUI[1].onStart(player[1].concealedTiles, player[1].visibleTiles);

        gameUI[0].onStart(player[0].concealedTiles, player[0].visibleTiles);		
		player[0].loadInitialTile();
        
        player[2].loadInitialTile();
        gameUI[2].onStart(player[2].concealedTiles, player[2].visibleTiles);
    	player[3].loadInitialTile();
        gameUI[3].onStart(player[3].concealedTiles, player[3].visibleTiles);

    	for(int i=0;i<1;i++) {
			gameUI[i].onTileTaken(player[ (i+1)%4 ].visibleTiles,
					player[(i+1)%4 ].concealedTiles.size()+1 , player[(i+1)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+2)%4 ].visibleTiles,
					player[(i+2)%4 ].concealedTiles.size()+1 , player[(i+2)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+3)%4 ].visibleTiles,
					player[(i+3)%4 ].concealedTiles.size()+1 , player[(i+3)%4 ].getWind(), false,null);
		}
    }
    

    //robbing kong perform by bot
    private void testRobKong1() {
    	//debug(2,"EngineFA.testRobKong1");
    	String tilelist1a[]={
    			"1_1","1_2","0_1","0_1",
				"1_5","1_5","1_5",
    			"0_3","0_3","0_3"
				};		    	

    	String tilelist1b[]={
    			"1_2_0_2","1_2_0_2","1_2_0_2"};
    					

    	String tilelist2a[]={"1_3",
				"1_1","2_2","2_3","2_4",
				"2_5","2_5","2_7","2_8",
				"2_9"};

    	String tilelist2b[]={
    			"1_3_0_2","1_3_0_2","1_3_0_2"};
    	
		player[0].loadBulkTiles(tilelist1a,tilelist1b);
    	gameUI[0].onStart(player[0].concealedTiles, player[0].visibleTiles);		

    	player[1].loadBulkTiles(tilelist2a,tilelist2b);
        gameUI[1].onStart(player[1].concealedTiles, player[1].visibleTiles);

        
        player[2].loadInitialTile();
        gameUI[2].onStart(player[2].concealedTiles, player[2].visibleTiles);
    	player[3].loadInitialTile();
        gameUI[3].onStart(player[3].concealedTiles, player[3].visibleTiles);

    	for(int i=0;i<1;i++) {
			gameUI[i].onTileTaken(player[ (i+1)%4 ].visibleTiles,
					player[(i+1)%4 ].concealedTiles.size()+1 , player[(i+1)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+2)%4 ].visibleTiles,
					player[(i+2)%4 ].concealedTiles.size()+1 , player[(i+2)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+3)%4 ].visibleTiles,
					player[(i+3)%4 ].concealedTiles.size()+1 , player[(i+3)%4 ].getWind(), false,null);
		}
    }

    //robbing kong perform by player
    private void testRobKong2() {
    	//debug(2,"EngineFA.testRobKong2");
    	String tilelist1a[]={
    			"1_1","1_2","0_1","0_1",
				"1_5","1_5","1_5",
    			"0_3","0_3","0_3"
				};		    	

    	String tilelist1b[]={
    			"1_2_0_2","1_2_0_2","1_2_0_2"};
    					

    	String tilelist2a[]={"1_3",
				"1_1","2_2","2_3","2_4",
				"2_5","2_5","2_7","2_8",
				"2_9"};

    	String tilelist2b[]={
    			"1_3_0_2","1_3_0_2","1_3_0_2"};
    	
		player[0].loadBulkTiles(tilelist2a,tilelist2b);
    	gameUI[0].onStart(player[0].concealedTiles, player[0].visibleTiles);		

    	player[1].loadBulkTiles(tilelist1a,tilelist1b);
        gameUI[1].onStart(player[1].concealedTiles, player[1].visibleTiles);

        
        player[2].loadInitialTile();
        gameUI[2].onStart(player[2].concealedTiles, player[2].visibleTiles);
    	player[3].loadInitialTile();
        gameUI[3].onStart(player[3].concealedTiles, player[3].visibleTiles);

    	for(int i=0;i<1;i++) {
			gameUI[i].onTileTaken(player[ (i+1)%4 ].visibleTiles,
					player[(i+1)%4 ].concealedTiles.size()+1 , player[(i+1)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+2)%4 ].visibleTiles,
					player[(i+2)%4 ].concealedTiles.size()+1 , player[(i+2)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+3)%4 ].visibleTiles,
					player[(i+3)%4 ].concealedTiles.size()+1 , player[(i+3)%4 ].getWind(), false,null);
		}
    }
    
    
    
    
    //test concealed kong
    private void testKong5() {
    	String tilelist1a[]={
    			"1_1","0_1","0_1",
				"1_5","1_5","1_5","1_5",
    			"0_3","0_3","0,3"
				};		    	
    	String tilelist1b[]={
    			"1_2_0_2","1_2_0_2","1_2_0_2"};
    					
		player[0].loadBulkTiles(tilelist1a,tilelist1b);
        gameUI[0].onStart(player[0].concealedTiles, player[0].visibleTiles);		
		player[0].printVisible();
        player[1].loadInitialTile();
        gameUI[1].onStart(player[1].concealedTiles, player[1].visibleTiles);
        player[2].loadInitialTile();
        gameUI[2].onStart(player[2].concealedTiles, player[2].visibleTiles);
    	player[3].loadInitialTile();
        gameUI[3].onStart(player[3].concealedTiles, player[3].visibleTiles);

    	for(int i=0;i<1;i++) {
			gameUI[i].onTileTaken(player[ (i+1)%4 ].visibleTiles,
					player[(i+1)%4 ].concealedTiles.size()+1 , player[(i+1)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+2)%4 ].visibleTiles,
					player[(i+2)%4 ].concealedTiles.size()+1 , player[(i+2)%4 ].getWind(), false,null);
			gameUI[i].onTileTaken(player[(i+3)%4 ].visibleTiles,
					player[(i+3)%4 ].concealedTiles.size()+1 , player[(i+3)%4 ].getWind(), false,null);
		}
    }
    
    

    

    
    
    
    private void testFinish1() {
    	String tilelist[]={
    			"1_1","1_1",
    			"0_2","0_3","0_3","0_3",
    			"0_3"};		
    	
    	String tileexp[]={
    			"1_5_0_3","1_6_0_3","1_7_0_3",
    			"4_1_1_2","4_1_1_2","4_1_1_2"};	

    	
    	
    	player[0].loadBulkTiles(tilelist,tileexp);
        gameUI[0].onStart(player[0].concealedTiles, player[0].visibleTiles);
		//player[0].printVisible();

        player[1].loadFinish1Player2();
        gameUI[1].onStart(player[1].concealedTiles, player[1].visibleTiles);
    	player[2].loadFinish1Player3();
        gameUI[2].onStart(player[2].concealedTiles, player[2].visibleTiles);
    	player[3].loadFinish1Player4();
        gameUI[3].onStart(player[3].concealedTiles, player[3].visibleTiles);
    }

    
    //two player competing for win... one human one AI
    private void testFinish10() {
    	String tilelist[]={
    			"2_3","2_3",
    			"4_2","4_2"};		
    	
    	String tileexp[]={
    			"1_9_0_2","1_9_0_2","1_9_0_2",
    			"0_4_1_2","0_4_1_2","0_4_1_2","0_4_1_2",
				"1_5_2_2","1_5_2_2","1_5_2_2","1_5_2_2"};	

    	String tilelist2a[]={
				"2_4","2_5","1_3","1_3",
				"3_1","3_1","3_1","2_8",
				"2_8","2_8"};

    	String tilelist2b[]={
    			"0_3_0_2","0_3_0_2","0_3_0_2"};

    	
    	
    	player[0].loadBulkTiles(tilelist,tileexp);
        gameUI[0].onStart(player[0].concealedTiles, player[0].visibleTiles);
		//player[0].printVisible();

        player[1].loadBulkTiles(tilelist2a,tilelist2b);
        gameUI[1].onStart(player[1].concealedTiles, player[1].visibleTiles);
    	player[2].loadFinish1Player3();
        gameUI[2].onStart(player[2].concealedTiles, player[2].visibleTiles);
    	player[3].loadFinish1Player4();
        gameUI[3].onStart(player[3].concealedTiles, player[3].visibleTiles);
    }


    //two player competing for win and chow ... one human win,, ai chow
    private void testFinish9() {
    	String tilelist[]={
    			"2_3","2_3",
    			"4_2","4_2"};		
    	
    	String tileexp[]={
    			"1_9_0_2","1_9_0_2","1_9_0_2",
    			"0_4_1_2","0_4_1_2","0_4_1_2","0_4_1_2",
				"1_5_2_2","1_5_2_2","1_5_2_2","1_5_2_2"};	

    	String tilelist2a[]={
				"2_4","2_5","1_3","1_7",
				"3_1","3_1","3_1","2_8",
				"2_8","2_8"};

    	String tilelist2b[]={
    			"0_3_0_2","0_3_0_2","0_3_0_2"};

    	
    	
    	player[0].loadBulkTiles(tilelist,tileexp);
        gameUI[0].onStart(player[0].concealedTiles, player[0].visibleTiles);
		//player[0].printVisible();

        player[3].loadBulkTiles(tilelist2a,tilelist2b);
        gameUI[3].onStart(player[3].concealedTiles, player[3].visibleTiles);
    	player[2].loadFinish1Player3();
        gameUI[2].onStart(player[2].concealedTiles, player[2].visibleTiles);
    	player[1].loadBulkTiles(tilelist2a,tilelist2b);
        gameUI[1].onStart(player[1].concealedTiles, player[1].visibleTiles);
    }

    
    /*
     * Helper method for //debugging
     */
    private void debug(int level, String txt) {
    	
    	if (level>=debugLevel) {
    		System.out.println(txt);
		}
    }    

    
    private void t_printOwnTiles(int x) {
    	//debug(4,"****SIZE: visibletiles=" + player[x].visibleTiles.size());
        for(int i=0;i<player[x].visibleTiles.size();i++) {
        	//System.out.print(  player[x].visibleTiles.getTileAt(i).type  +"_" + player[x].visibleTiles.getTileAt(i).value + "_"+ player[x].visibleTiles.getTileAt(i).group+"_"+ player[x].visibleTiles.getTileAt(i).groupType+"  ");
        }      	
        //debug(10,"****SIZE: invisibletiles=" + player[x].concealedTiles.size());
        for(int i=0;i<player[x].concealedTiles.size();i++) {
        	//System.out.print("_____");
        	//System.out.print(  player[x].concealedTiles.getTileAt(i).type  +"_" + player[x].concealedTiles.getTileAt(i).value + "_"+ player[x].concealedTiles.getTileAt(i).group +"  " );
        }  
    }

    public String doPing() {
    	return "";
    }
    public void doCleanUp() {
    	player[0]=null;
    	player[1]=null;
    	player[2]=null;
    	player[3]=null;
    	gameUI[0].doCleanUp();
    	gameUI[0]=null;	
    	gameUI[1].doCleanUp();
    	gameUI[1]=null;	
    	gameUI[2].doCleanUp();
    	gameUI[2]=null;	
    	gameUI[3].doCleanUp();
    	gameUI[3]=null;	
    
    }

    public Player[] getPlayers() {
    	return player;
    }
    
}
