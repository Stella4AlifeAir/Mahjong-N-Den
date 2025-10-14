package com.pheephoo.mjgame.ui;

import com.pheephoo.mjgame.*;
import com.pheephoo.mjgame.engine.*;
import com.pheephoo.mjgame.DeviceConstant;
import com.pheephoo.utilx.Common;

import java.io.IOException;
import java.util.*;
import java.io.InputStream;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.control.VolumeControl;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

/*
 * Main user interface
 * 
 * @author Samuel
 * @version 1.00, 24/04/2005
 */
public class Series60Client extends Canvas implements GameListenerIF {

	int firstPlay=0;//flag for playing animation for the first time in case of offline mode
	
	//for pausing between win screen & tile screen
	private int screen=1;//1=normal screen  2=winningscreen; 0=waiting for showing normal scren
	private boolean nextGamePressed=true;
	
	//debuging
	int speedDebug=0;//accessed by inner class.
	boolean autoDebug=true;//accessed by inner class.
	private int debugLevel=0;//5=show player move  7=show game summary only
	private int demoMode=0;
	
    //reference to main midlet
    private MJGame midlet;	
    private EngineIF engine;				//reference to EngineFA object
    private Player player;					//reference to player object

    public boolean startAllHumanFlag=false;// only used in  onWind and redrawNewGame() ????
    
    
    /*
     *  animation related
     */
    boolean startAnim=false;//accessed by inner class. for dealingImage animationg

    /*
     * Common Game Playing
     */
	private int playMode=0;//playMode; 0=offline 1=online public 2=online private	
    private int lastWinningPlayerWind=-1;
    private boolean isRobbingKong;
    private int groupCount=0;// number of special visible tiles form, eg. chow group, etc    
    private int numberOfTile=0;  //number of tile on the table used in drawArea6
    private int numTileOnDeck=0;
    
    
    //for drawing notice screen
    private int maxNumberofSelection=0; //for used in drawArea7a()
    private int currentSelection=0;
    private SpecialAction firstLevelAction;
    private Vector actions;//keep track of action
    private Vector currentActions;
    private int selectionLevel=0;//0=first level(kong,pong,etc) 1=secondlevel(1,2,3...)

    
    /* allow user to interact with the game
     *  different inputMode value allow different user interaction
     */ 
    private int inputMode=0;//0 = ignore user input 				
    private int pointer=0; //keep track of the location of the pointer
    private int prevpointer=0; //keep track of previous pointer
    private boolean ourTurn=false;
    
    /*
     * misc 
     */
    public boolean isStarted=false; //to be used by MJGame to check if the client has started or not

    boolean isCleanUp=false;//accessed by inner class. if user quit, this variable marks which task should not be performed
	boolean autoTimerEnabled=false;//accessed by inner class. for autoresponse
    private Timer     timer = null;
    private TimerTask task = null;
    int timerCounter=0; //accessed by inner class. for counting autoresponse. used with Timer
    private boolean loadCompleted=false;//to disable the user input until the whole screen is loaded
    private boolean flagClearNotice=false;	//for redrawing notice screen
    long lastInputTime=0;//accessed by inner class. for escaping previous input if user press button multiple time

    
    private boolean waitForEnter=true;//use for pausing dealingImage screen
	public int currentRound=0;//used by GameInfoCanvas class. mark the last round

	// temp memory holder.. for holding user score if user draw
    int playerWind;
	TileCollection col1;
	TileCollection visibleTiles1;
	int double1;
	int point1; 
	int total1;
    TileCollection col2;
	TileCollection visibleTiles2; 
	int double2;
	int point2; 
	int total2;
	TileCollection col3; 
	TileCollection visibleTiles3;
	int double3;
	int point3;
	int total3;
	TileCollection col4;
	TileCollection visibleTiles4;
	int double4;
	int point4;
	int total4;

	
    /*
     * screen related
     * cache image on the memory
     */
	private int swidth=0;
	private int sheight=0;
	Graphics osg;   //accessed by inner class. we draw using this offscreen graphics object
	private Image offScreenBuffer;
	private Image tempBuffer2;  //to hold the whole screen for redrawing after notice dialog
	private Image emptyBuffer2;  //empty screen area, size= one tile (tilewidth, tile height)
	private Image emptyBuffer3; //empty screen area, size= (screen width, tile height)
	private Image discardedBuffer; //to hold the image tile before it is highlighted
	private Image tileTopEdge;
	private Image tileBottomEdge;
	private Image tileSideLeft;
	private Image tileSideRight;
	private Image tileSideTop;
	private Image mainTile;
	private Image mainTileSmall;
	private Image arrow;
	private Image smallEdge;
	private Image dialog;
	private Image firstLevelSymbol;
	private Image secondLevelSymbol;
	private Image windImage;
    
	private Image waitArrowBuffer;
	private int prevWaitArrowPos=-1;
	private int prevWaitArrowPos2=-1;//for remembering position before notice screen
	
    //sound
	private javax.microedition.media.Player tileDiscardedPlayer;
	private javax.microedition.media.Player specialEventPlayer;
	private javax.microedition.media.Player initPlayer;
	private VolumeControl tileDiscardedVolControl;
	private VolumeControl specialEventVolControl;
	private VolumeControl initVolControl;
	private InputStream specialEventStream;
	private InputStream tileDiscardedStream;	

    //setting
	//private Setting setting;
	private int realVolumeLevel=0;//real value for VolumeControl 0-100
	private int volumeLevel;//volume level 
	
	public int pictureNum;//voice of each player, obtained from setting, use by PrivateNetworkCanvas and PublicNetworkCanvas class

	public PlayerInfo playerInfo[]= new PlayerInfo[4];
	int playerInfoPointer=0;
	
	
	//only for debuggin
    public Series60Client(MJGame m, int p_playMode, int _speedDebug) {
    	//debug(2,"constructor. debugmode. " + "p_playmode="+p_playMode +" ;speeddebug="+_speedDebug);
    	speedDebug=_speedDebug;
    	this.setFullScreenMode(true);
    	playMode=p_playMode;
    	midlet = m;
        preloadResources();
    	initResources();
    	startAnim=true;
		osg.setColor(0,0,0);
		osg.fillRect(0, 0,swidth,sheight);
    }
    //debuging use
    public void setDemoMode(int p_demoMode) {
    	demoMode=p_demoMode;
    }

    public Series60Client(MJGame m, int p_playMode) {
    	this.setFullScreenMode(true);
    	playMode=p_playMode;
    	midlet = m;
        preloadResources();
    	initResources();
    	
    	startAnim=true;
		prepareBackground();
    }
    
    private void preloadResources() {
        try {
        	tileTopEdge=Image.createImage("/res/top_edge.png") ;
        	tileBottomEdge=Image.createImage("/res/tile_edge.png") ;
        	tileSideLeft = Image.createImage("/res/side_l.png") ;
        	tileSideRight = Image.createImage("/res/side_r.png") ;
        	tileSideTop = Image.createImage("/res/side_t.png") ;
        	mainTile=Image.createImage("/res/maintile.png");
        	mainTileSmall=Image.createImage("/res/maintile_s.png");
        	arrow= Image.createImage("/res/arrow.png");
        	smallEdge= Image.createImage("/res/small_edge.png");
        	dialog=Image.createImage("/res/dialog.png");
        	firstLevelSymbol=Image.createImage("/res/firstlevel.png");
        	secondLevelSymbol=Image.createImage("/res/secondlevel.png");
        	windImage=Image.createImage("/res/wind.png");
        	
        	offScreenBuffer = Image.createImage(getWidth(), getHeight());

            osg = offScreenBuffer.getGraphics();
        }
        catch (IOException ie){
        	throw new Error();
        }
    }
    
    /**
        * Initialize the resources for the game. 
        */
    private void initResources() {
    	//debug(2,"initResources()");
    	playerInfo[0]= new PlayerInfo();
    	swidth=getWidth();
    	sheight=getHeight();
    	offScreenBuffer = Image.createImage(getWidth(), getHeight());
        osg = offScreenBuffer.getGraphics();        

        reloadSetting();
        drawBackgroundColor();
        emptyBuffer2=Common.extract(offScreenBuffer, 0,0,DeviceConstant.TILE_WIDTH,DeviceConstant.TILE_HEIGHT);
        emptyBuffer3=Common.extract(offScreenBuffer,0,0,swidth,2*  DeviceConstant.TILE_HEIGHT);
    	//debug(2,"initResources() finished");
    }
    
    protected  void  paint(Graphics graphics) {
    	graphics.drawImage(offScreenBuffer, 0, 0, Graphics.LEFT | Graphics.TOP);
    }


    protected void keyPressed(int keyCode) {
    	//debug(2,"keyPressed()::"+System.currentTimeMillis() + "inputMode="+inputMode+ " ;keyCode="+keyCode);
    	    	
    	try {
    		//escaping previous input if the class is still loading or multiple pressed
    		if (!loadCompleted || System.currentTimeMillis()-350<lastInputTime) {
    			return;
    		}
    		switch (keyCode) {  
            	case -6://case KEY_SOFTKEY1:
            	case -7://case KEY_SOFTKEY2:
            		if (loadCompleted) {
            			if (playMode==0)
            				midlet.commandReceived(Constant.OFFLINE_PAUSE);
            			else
            				midlet.commandReceived(Constant.NETWORKCOMMON_PAUSE);
            		}
            		break;
            }  		
    		
    		if (inputMode==0 ) {//for offline mode only.. wait for user to confirm... if confirmed, start the game
    			inputMode=-1;
    			startGame();
    		}
	    	else if (inputMode==1) { //normal. discarding tile with new tile
	    		processInputNormal(keyCode);
	    	}
	    	else if (inputMode==3) { //prompt user to select option when kong and its our turn
	    		processInputKong(keyCode);
	    	}
	    	//prompt user to select option when its not our turn (for kong & pong)
	    	else if (inputMode==4) {
	    		processInputKong2(keyCode);
	    	}
	    	else if (inputMode==5) {
	    		processInputChow(keyCode);
	    	}
	    	else if (inputMode==6) {
	    		processInputWin(keyCode);
	    	}
	    	//inputMode becomes 8 after showing each user tiles
	    	else if (inputMode==8) {
	    		
	    		//use for pausing dealingImage screen
	    		//in the event of clearHand and we wanna wait for user to press enter
	    		//call the onWin() for the second time, this time onWin() will display the scores
	    		if (waitForEnter) {
	    			waitForEnter=false;//use for pausing dealingImage screen
	    		    onWin(playerWind, col1, visibleTiles1, double1, point1,total1,
	    		    		col2, visibleTiles2, double2,  point2, total2,
	    					col3, visibleTiles3, double3, point3,total3,
	    					col4, visibleTiles4, double4, point4,total4) ;
	    			return;
	    		}
	    		autoDebug=true;//for debugging only
	    		if (keyCode==-5) {
	    			inputMode=-1;
	    			waitForEnter=true;
	    			redrawNewGame();
	    		}
	    	}
	    	else {
	    		return;
	    	}
	    	
    	}
    	catch (IOException ex) {
    		throw new Error();
    	}
    	//this repaints is important. otherwise some of the screen won't update
    	screenRepaint();
    }


    /*
     *  @called by processInputKong2()
     *  process Kong for concealed tile on the event of other discard a tile
     */
    private void doKongHelper2(int position) {
    	//debug(2,"doKongHelper2() start");
    	int counter=0;    	
    	SpecialAction action = (SpecialAction) currentActions.elementAt(position-1);
    	//debug(5,"tiles"+ action.startTilePosition+"-"+action.endTilePosition);
    	t_printOwnTiles(3);
    	for (int i=action.startTilePosition;i<=action.endTilePosition;i++) {
        	Tile temptile=player.concealedTiles.getTileAt(i);
        	temptile.group=groupCount;
        	temptile.groupType=2;
        	//debug(1,"adding tile: "+ temptile);
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

    	pointer=1;
        prevpointer=player.concealedTiles.size();
		playSound(pictureNum,action.type);

    	groupCount++;	
        t_printOwnTiles(3);
        drawArea8(0);
    	numberOfTile--;
    	drawEmptyTile();
    	
    	screenRepaint();
    }

    private void screenRepaint() {
    	repaint();
    	serviceRepaints();
    }
    
    private void doFinishHelper() {
    	//debug(2,"doFinishHelper()::" + "player="+player + " ;player.discardedtile="+player.discardedtile);
    	if (player.discardedtile!=null) {
    		//debug(5,"win on discard");
    		player.concealedTiles.addTile(player.discardedtile);
    	}
    	else {
    		//debug(5,"win on own draw");
    		player.concealedTiles.addTile(player.newtile);
    	}
        engine.doWinResponse(player.getWind());
    }
    
    
    private void doChowHelper(int position) {
    	//debug(2,"doChowHelper()");
		//debug(1,"CurrentAction size="+ currentActions.size());
    	SpecialAction action = (SpecialAction) currentActions.elementAt(position-1);
    
    	//debug(5,"You chow!!" +"tiles"+ action.startTilePosition+"-"+action.endTilePosition );
    	t_printOwnTiles(3);
    	
		Tile temptile=player.concealedTiles.getTileAt(action.startTilePosition);
		Tile temptile2=player.concealedTiles.getTileAt(action.endTilePosition);
		//debug(0,"doChowHelper()...getTile="+temptile.hashCode());
		
		temptile.group=groupCount;
		temptile.groupType=3;
    	temptile2.group=groupCount;
		temptile2.groupType=3;
		player.discardedtile.group=groupCount;
		player.discardedtile.groupType=3;
    	
		Tile t_temptile;//we need to create a new tile to avoid pointing to the same ref
		String tiles="";
		
    	if (player.discardedtile.value<temptile.value) {
    		player.visibleTiles.addTileNoSorted(player.discardedtile);
    		t_temptile= new Tile(temptile.group,temptile.type,temptile.value);
    		t_temptile.groupType=3;
    		player.visibleTiles.addTileNoSorted(t_temptile);
    		tiles= tiles+ " ;" + t_temptile;
    		t_temptile= new Tile(temptile2.group,temptile2.type,temptile2.value);
    		t_temptile.groupType=3;
    		player.visibleTiles.addTileNoSorted(t_temptile);
    		tiles= tiles+ " ;" + t_temptile;
    	}
    	else {
    		t_temptile= new Tile(temptile.group,temptile.type,temptile.value);
    		t_temptile.groupType=3;	
    		player.visibleTiles.addTileNoSorted(t_temptile);
    		tiles= tiles+ " ;" + t_temptile;
        	
        	if (player.discardedtile.value<temptile2.value) {
        		player.visibleTiles.addTileNoSorted(player.discardedtile);
        		tiles= tiles+ " ;" + t_temptile;
        		t_temptile= new Tile(temptile2.group,temptile2.type,temptile2.value);
        		t_temptile.groupType=3;
        		player.visibleTiles.addTileNoSorted(t_temptile);
        		tiles= tiles+ " ;" + t_temptile;
        	}
        	else {
        		t_temptile= new Tile(temptile2.group,temptile2.type,temptile2.value);
        		t_temptile.groupType=3;
        		player.visibleTiles.addTileNoSorted(t_temptile);
        		tiles= tiles+ " ;" + t_temptile;
        		player.visibleTiles.addTileNoSorted(player.discardedtile);
        		tiles= tiles+ " ;" + t_temptile;
        	}
    	}
    	//debug(5,tiles);
    	player.concealedTiles.removeTile(temptile);	
    	player.concealedTiles.removeTile(temptile2);
    	        
    	pointer=1;
        prevpointer=player.concealedTiles.size();
    	
        playSound(pictureNum,GameAction.TYPE_CHOW);
        
        t_printOwnTiles(3);
    	groupCount++;	
    	drawArea8(0);

    	numberOfTile--;
    	drawEmptyTile();

    	screenRepaint();
    	//debug(2,"doChowHelper() finish");
    }
    
    /*
     * @call by processInputKong
     * 
     * process Kong for existing pong tiles in revealed tile
     */
    private Tile doKongHelper1a(int position, Tile newtile) {
    	//debug(2,"doKongHelper1a():: existing pong tiles. newtile="+newtile);
    	SpecialAction action = (SpecialAction) currentActions.elementAt(position-1);
    	Tile temp= player.visibleTiles.getTileAt(action.startTilePosition);

        //t_printOwnTiles(3);
    	if (temp.equals(newtile)) {
        	//debug(2,"doKongHelper1a():: concealed kong from newtile+visibletiles");    		
        	newtile.group=temp.group;    	
        	newtile.groupType=temp.groupType;
        	player.visibleTiles.insertTile(newtile);    		
        	//debug(5,"You kong!! " + newtile);
    	}
    	else {
        	//debug(2,"doKongHelper1a():: concealed kong from concealedtile+newtile");    		
			for(int j=0;j<player.concealedTiles.size();j++) {
				Tile temptile = player.concealedTiles.getTileAt(j);
				if (temp.equals(temptile)) {
					temptile.group=temp.group;
					temptile.groupType=temp.groupType;
					//debug(0,"visibletile.size="+player.visibleTiles.size());
		        	player.visibleTiles.insertTile(temptile);    							
		        	player.concealedTiles.removeTile(temptile);
		        	player.concealedTiles.addTile(newtile);
					//debug(5,"visibletile.size after add="+player.visibleTiles.size());
		        	//debug(5,"You kong!! " + temptile);
					break;
				}
			}
    	}
    	newtile=null;
		pointer=1;
        t_printOwnTiles(3);

        playSound(pictureNum,GameAction.TYPE_KONG);
		drawArea8(0);
		screenRepaint();
    	//debug(2,"doKongHelper1a():: existing pong tiles: finish");
    	return temp;
    }
    
    
    /*
     * @call by processInputKong
     * process Kong for concealed tile on the event of user receiving a tile
     * 
     * moving the tile from concealed tile to exposed tile
     * update the screen
     */
	private Tile doKongHelper(int position) {
    	//debug(2,"doKongHelper:: self pick");
    	SpecialAction action = (SpecialAction) currentActions.elementAt(position-1);
    	//debug(5,"You kong!! " + "tiles"+ action.startTilePosition+"-"+action.endTilePosition);
    	t_printOwnTiles(3);

    	String tile="";
    	int counter=0;
    	Tile temptile=null;
    	for (int i=action.startTilePosition;i<=action.endTilePosition;i++) {
        	temptile=player.concealedTiles.getTileAt(i);
        	temptile.group=groupCount;
        	temptile.groupType=2;
        	
        	player.visibleTiles.addTileNoSorted(temptile);
        	tile=tile + " ;"+temptile;
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
        	tile=tile + " ;"+player.newtile;
    	}
    	else {
    		//debug(0,"keypress:inputmode:3,trying to add newtile");
    		player.newtile.group=groupCount;
    		player.newtile.groupType=2;
    		player.concealedTiles.addTile(player.newtile);
        	tile=tile + " ;"+player.newtile;
    	}
    	//19aug
    	player.newtile=null;
		pointer=1;
		
		playSound(pictureNum,GameAction.TYPE_KONG);
		t_printOwnTiles(3);

    	groupCount++;	
    	drawArea8(0);
    	
    	screenRepaint();
    	//debug(3,"GameUI.doKongHelper() tempdiscard=" + temptile);
        t_printOwnTiles(3);

    	//debug(2,"GameUI.doKongHelper:: self pick::finish");
    	return temptile;
    }
    
	private void startWaiting() {
		//debug(3,"startWaiting(). scheduleed Waiting");
		timerCounter=0;
		autoTimerEnabled=true;
	}
	//accessed by innerclass
	void resetWaiting() {
		//debug(3,"resetWaiting()");
		autoTimerEnabled=false;
		timerCounter=0;
	}
	
	private void startAutoTimer() {
		//debug(20,"startAutotimer()");

		if (timer!=null) {
			timer.cancel();
			task.cancel();
			timer=null;
		}
		if (playMode!=0) {
    		if (timer==null) {
	    		autoTimerEnabled=false;
		        task = new ForceActionTask();
		        timer=new Timer();  
		        //debug(20,"schedule to auto play on 10 sc");
		        timer.scheduleAtFixedRate(task,1,1000);//30 seconds
    		}
    	}
    	else if (speedDebug>4) {
    		if (timer==null) {
	    		autoTimerEnabled=false;
		        task = new ForceActionTask();
		        timer=new Timer();  
		        //debug(1,"schedule to auto play on 200 ms");
		        timer.scheduleAtFixedRate(task,50,50);//30 seconds
    		}
    	}
	}
    
    /* notice when it our turn
     *
     * Param:
     * 1. Vector actions: vector of special action that can be performed, eg. kong, chow
     */
    public void onNotice(Vector actions) {
		//debug(2,"onNotice()");
		ourTurn=true;
    	this.actions=actions;
    	selectionLevel=0;
		currentSelection=1;
    	
		//inputMode=3;
		SpecialAction action= (SpecialAction) actions.firstElement();		
		//debug(2,"actionproperties");
		//debug(2,"starttile=" +action.startTilePosition+";endtile="+action.endTilePosition+";type="+action.type+";isVisible="+action.isVisible);
		
		if (action.type==GameAction.TYPE_KONG||action.type==GameAction.TYPE_PONG) {
			inputMode=3;
		}
		else if (action.type==GameAction.TYPE_WIN)
			inputMode=6;

		drawArea7a(0,1,actions);
		startWaiting();

		lastInputTime=System.currentTimeMillis();
    	screenRepaint();
		//debug(2,"onNotice() finished");
    }

    /*
     * notice when it is not our turn
     */
    public void onNotice2(Vector actions) {
		//debug(2,"onNotice2() ");

    	prevWaitArrowPos2=prevWaitArrowPos;
    	drawWaitArrow(0);
    	startWaiting();
    	
    	isRobbingKong=false;
    	ourTurn=false;
    	
    	this.actions=actions;
    	selectionLevel=0;
		currentSelection=1;
		SpecialAction action= (SpecialAction) actions.firstElement();		

		//debug(2,"Action.type="+ action.type);
		//debug(2,"Action.startpos="+ action.startTilePosition);
		//debug(2,"Action.endpos="+ action.endTilePosition);
		//debug(2,"Action.isVisible="+ action.isVisible);
		
		//base on input mode, it will call different handler for processing kong/pong/chow
		if (action.type==GameAction.TYPE_KONG||action.type==GameAction.TYPE_PONG) {
			inputMode=4;
			drawHighlight();//for drawing red line along the discarded tile
		}
		else if (action.type==GameAction.TYPE_CHOW) { 
			inputMode=5;
			drawHighlight();//for drawing red line along the discarded tile
		}
		else if (action.type==GameAction.TYPE_WIN) {
			inputMode=6;
			drawHighlight();//for drawing red line along the discarded tile
		}
		else if (action.type==GameAction.TYPE_WIN_ONROBBING) {
			//debug(3,"notice when it is not our turn:ACTION...winonrobbing");
			isRobbingKong=true;
			inputMode=6;			
		}
		
		drawArea7a(0,1,actions);
    	lastInputTime=System.currentTimeMillis();
    	
    	screenRepaint();
    	//tmp debug
    	//debug(2,"player.discarded="+player.discardedtile);
    	//debug(2,"onNotice2() finished");
    }
    
    /*if the player is the last player, this method will be called twice
    the flag 'isJoinCalled' is a hack to solve the problem
    **/
    public void onJoin(int tableno, int position, int noOfPlayer, EngineIF _engine, Player _player, int started) {
    	//debug(2,"onJoin()");
    	isStarted=true;
    	startAnim=false;
    	
    	engine=_engine;
        player=_player;
    	//debug(2,"onJoin()...player.getWind="+player.getWind());

        player.nick=Setting.nick;
        playerInfo[0].nick=Setting.nick;
        playerInfo[0].position=position;
        playerInfo[0].picture=pictureNum;
        player.pictureNum=pictureNum;
        
    	engine.setGameRule(Setting.minDouble,Setting.maxDouble);
    	//debug(5,"You join position " + position+ "  no of player" + noOfPlayer);
    	//debug(2,"onJoin() finished");
    }

    public  void onOtherJoin(int _pos, int _noOfPlayer, int p_picNum, String p_nick) {    
        //debug(5,"onOtherJoin(). join on position " + _pos + "  no of player" + _noOfPlayer + "; nick="+ p_nick);
        playerInfoPointer++;
        if (playerInfoPointer<playerInfo.length) {
	        playerInfo[playerInfoPointer]=new PlayerInfo();
	        playerInfo[playerInfoPointer].position=_pos;
	        playerInfo[playerInfoPointer].nick=p_nick;
	        playerInfo[playerInfoPointer].picture=p_picNum;
        }
    }

    public void onJoinInProgressBot(int pos, TileCollection _owncol, TileCollection _ownvisibleTiles,
    		EngineIF _engine, Player _player,TileCollection visibleTiles1,TileCollection visibleTiles2,TileCollection visibleTiles3 ) {}
    
    public void onOtherLeaveInProgress(int position, int picture, String nick1, String nick2){
    	//debug(5,"onOtherLeaveInProgress()::position="+position+	";picture="+picture+";nick1="+nick1+";nick2="+nick2);
    	
    	for (int i=0;i<4;i++) {
    		if (playerInfo[i].position==position) {
    			playerInfo[i].picture=picture;
    			playerInfo[i].nick=nick2;
    			break;
    		}
    	}

    	String message=nick1 + " left " + getPositionString(position) +". Player is replaced by " + nick2 + " (bot)";
    	midlet.commandReceived(Constant.NETWORKCOMMON_NOTICESCREEN, message);
    }

    private String getPositionString(int position) {
    	switch (position) {
			case 0: return "East";
			case 1: return "South";
			case 2: return "West";
			case 3: return "North";
    	}
    	return "";
    }
    
    public void onOtherJoinInProgress(int position, int picture, String nick) {
    	//debug(5,"onOtherJoinInProgress()::position="+position+";picture="+picture+";nick="+nick);
    	for (int i=0;i<4;i++) {
    		if (playerInfo[i].position==position) {
    			playerInfo[i].picture=picture;
    			playerInfo[i].nick=nick;
    			break;
    		}
    	}
    	String message= nick + " joined " + getPositionString(position);
    	midlet.commandReceived(Constant.NETWORKCOMMON_NOTICESCREEN, message);
    }

    public void onJoinInProgress(int pos, TileCollection owncol, TileCollection ownvisibleTiles,
    		int pos1, int pictureNum1, int point1, String nick1,int numOfTile1, TileCollection visibleTiles1,
			int pos2, int pictureNum2, int point2,String nick2,int numOfTile2, TileCollection visibleTiles2,
			int pos3, int pictureNum3, int point3, String nick3,int numOfTile3, TileCollection visibleTiles3,
			TileCollection tilesOnTable) {
    	
    	//debug(100,"onJoinInProgress() start");
    	//debug(100,"Your Data: POS="+pos);
    	//debug(100,"OtherPlayer data..........");
    	//debug(100,"nick1="+nick1+" ;pictureNum1="+pictureNum1+" ;pos="+pos1 + " ;point="+point1);
    	//debug(100,"nick2="+nick2+" ;pictureNum2="+pictureNum2+" ;pos="+pos2 + " ;point="+point2);
    	//debug(100,"nick3="+nick3+" ;pictureNum3="+pictureNum3+" ;pos="+pos3+ " ;point="+point3);
    	
    	
    	player.wind=pos;
    	isStarted=true;
    	numberOfTile=0;
    	
    	t_printOwnTiles(5);

    	offScreenBuffer = Image.createImage(getWidth(), getHeight());
        osg = offScreenBuffer.getGraphics();
        drawBackgroundColor();
        inputMode=0;
        groupCount=0;
        if (player.visibleTiles.size()>0) {
        	Tile tmp=player.visibleTiles.getTileAt(player.visibleTiles.size()-1);
        	groupCount=tmp.group+1;
        }
        //debug(2,"calling onTileTaken:: numOfTile1="+numOfTile1);
        //debug(2,"calling onTileTaken:: numOfTile2="+numOfTile2);
        //debug(2,"calling onTileTaken:: numOfTile3="+numOfTile3);
    	onTileTaken(visibleTiles1,numOfTile1+1,pos1,false,null);
    	onTileTaken(visibleTiles2,numOfTile2+1,pos2,false,null);
    	onTileTaken(visibleTiles3,numOfTile3+1,pos3,false,null);

    	for (int i=1;i<4;i++) {
    		playerInfo[i]= new PlayerInfo() ;
    	}
    	playerInfo[1].position=pos1;
    	playerInfo[2].position=pos2;
    	playerInfo[3].position=pos3;
				   
    	playerInfo[1].picture=pictureNum1;
    	playerInfo[2].picture=pictureNum2;
    	playerInfo[3].picture=pictureNum3;
    	
    	playerInfo[1].nick=nick1;
    	playerInfo[2].nick=nick2;
    	playerInfo[3].nick=nick3;

    	playerInfo[0].score[0]=player.totalPoint;
    	playerInfo[1].score[0]=point1;
    	playerInfo[2].score[0]=point2;
    	playerInfo[3].score[0]=point3;
    	currentRound=1;
    	
    	//debug(2,"finished assigning other players");    	
    	
        drawArea8(1);
        drawWind();
        for (int i=0;i<tilesOnTable.size();i++) {
        	Tile tile=  tilesOnTable.getTileAt(i);
        	drawArea6(tile);
        }
    	drawWindInit();
    	screenRepaint();
        
    	startAutoTimer();
    	startWaiting();

    	lastInputTime=System.currentTimeMillis();
    	loadCompleted=true;//this variable is used by the keyPressed method & setting animation for initial loading
    	//debug(2,"Series60Client.onJoinInProgress() finished");
    }
    
    public void onTileDiscarded(Tile tile, int turn, int numTileOnDeck) {
    	//debug(2,"onTileDiscarded. Tile Discarded:::: " + tile.type + "_" + tile.value);
    	//if its our turn to discard the tile
    	
    	player.discardedtile= new Tile(tile.type,tile.value);
        //debug(5,"Player turn:" + turn + " discard " +player.discardedtile);
    	
    	if (player.getWind()!=turn) {
	        drawNumberofTile(numTileOnDeck);
        		
        	if (turn==(player.getWind()+1)%4) 
                osg.drawImage(tileSideRight, 
                        swidth-DeviceConstant.SIDETILE_SIZE,  ( (sheight) - 13*DeviceConstant.SIDETILE_SIZE)/2 -DeviceConstant.SIDETILE_SPACE , Graphics.LEFT| Graphics.TOP);
            else if (turn==(player.getWind()+2)%4)             
            	osg.drawImage(tileSideTop, 
                        swidth- 14*DeviceConstant.SIDETILE_SIZE - DeviceConstant.SIDETILE_SPACE  ,  0, Graphics.LEFT| Graphics.TOP);
            else if (turn==(player.getWind()+3)%4)             
                osg.drawImage(tileSideLeft, 
                        0,  DeviceConstant.TILE_LEFTPLAYER_POS_V+ DeviceConstant.SIDETILE_SIZE*12 + DeviceConstant.SIDETILE_SPACE , Graphics.LEFT | Graphics.TOP);
	        
        	screenRepaint();
            
            if (tileDiscardedPlayer!=null&&speedDebug<=4) {
            	//create a new player
            	if (tileDiscardedPlayer.getState()!=javax.microedition.media.Player.PREFETCHED) {
            		createTileDiscardedPlayer();
            	}

            	try {
                	Thread.sleep(200);
            	}
                catch (InterruptedException ex) {
                }
            		
                try {
                	tileDiscardedPlayer.start();
                }
                catch (MediaException e) {
                }
            	try {
                	Thread.sleep(100);
            	}
                catch (InterruptedException ex) {
                }

            }
            	
            if (turn==(player.getWind()+1)%4) 
                osg.drawImage(Common.extract(emptyBuffer2,0,0,DeviceConstant.SIDETILE_SIZE,DeviceConstant.SIDETILE_SIZE), 
                        swidth-DeviceConstant.SIDETILE_SIZE,  ( (sheight) - 13*DeviceConstant.SIDETILE_SIZE)/2 -DeviceConstant.SIDETILE_SPACE , Graphics.LEFT| Graphics.TOP);
            else if (turn==(player.getWind()+2)%4)             
            	osg.drawImage(Common.extract(emptyBuffer2,0,0,DeviceConstant.SIDETILE_SIZE,DeviceConstant.SIDETILE_SIZE), 
                        swidth- 14*DeviceConstant.SIDETILE_SIZE - DeviceConstant.SIDETILE_SPACE ,  0, Graphics.LEFT| Graphics.TOP);
            else if (turn==(player.getWind()+3)%4)             
            	osg.drawImage(Common.extract(emptyBuffer2,0,0,DeviceConstant.SIDETILE_SIZE,DeviceConstant.SIDETILE_SIZE), 
                    0,  DeviceConstant.TILE_LEFTPLAYER_POS_V+ DeviceConstant.SIDETILE_SIZE*12 + DeviceConstant.SIDETILE_SPACE  , Graphics.LEFT | Graphics.TOP);
	        drawArea6(tile);
	    	screenRepaint();

        	if (turn==(player.getWind()+1)%4) 
        		drawWaitArrow(2);
        	else if (turn==(player.getWind()+2)%4)             
        		drawWaitArrow(3);
            else if (turn==(player.getWind()+3)%4)             
        		drawWaitArrow(0);
        }
        //debug(2,"onTileDiscarded finished");
    }
    
    /* 1. show new tile (Region 1)
     * 2. activate user input (Show the arrow and enable it to move) 
     * 3. Wait for user input
     * turn is used for byenetwork playing
     * 
     */
    public void onTileReceived(Tile tile, int numTileOnDeck, int turn) {
    	if (turn!=player.getWind()) {
    		return;
    	}
    	//debug(2,"****onTileReceived()::: Client received::" + tile+ " ;NumofTile::" + numTileOnDeck);
    	this.numTileOnDeck=numTileOnDeck;
    	player.discardedtile=null;
    	t_printOwnTiles(3);  
        
        player.newtile=tile;
        pointer=0;
        prevpointer=player.concealedTiles.size();
        //debug(2,"onTileReceived::prevPointer="+prevpointer);
        drawArea1();
        
        drawNumberofTile(numTileOnDeck);
    	screenRepaint();
    	//debug(2,"onTileReceived()::: finish ");
    }

	//displaying player wind
    private void drawWindInit() {
    	String strWind=this.getPositionString(player.getWind()).toUpperCase();
    	screenRepaint();
    	discardedBuffer= Common.extract(offScreenBuffer,0,0,swidth,sheight); 
    	osg.setColor(0,0,0);
        osg.setFont(Font.getFont(
        	    Font.FACE_SYSTEM, Font.STYLE_BOLD,Font.SIZE_LARGE));        
    	osg.drawString( "Your Wind: " + strWind,DeviceConstant.WIND_STARTTXTPOS_H,DeviceConstant.WIND_STARTTXTPOS_V,Graphics.LEFT| Graphics.TOP);
    }
    
    public void onStart(TileCollection _col, TileCollection _visibleTiles) {
        //debug(2,"onStart()");
    	loadCompleted=false;
    	numberOfTile=0;

    	//this section is to prevent the multiple call on the nextGame() method in case of network playing
    	if (playMode!=0) {
        	try {
        		while(!nextGamePressed) {
        	    	loadCompleted=true;
	        		screen=1;
	        		Thread.sleep(500);
        		}
        		nextGamePressed=false;
        	}
        	catch (InterruptedException e) {
        	}
        	playAnimation();
        }
        else if (firstPlay==0) {
        	firstPlay++;
        	playAnimation();
        }
        
        startAnim=false;
    	offScreenBuffer = Image.createImage(getWidth(), getHeight());
        osg = offScreenBuffer.getGraphics();
        drawBackgroundColor();
        groupCount=0;
        
        int vsize=_visibleTiles.size();
        if (vsize>0) {
	        Tile tile=  _visibleTiles.getTileAt(vsize-1);
	        groupCount=tile.group+1;
        }
               
    	try {  
    		Thread.sleep(1000);
	        drawArea3(13,0);//numberofTile, position (0)= draw all the other tile
	        drawArea8(1);
	        drawWind();
	    	screenRepaint();
    	}
    	catch (InterruptedException ie) {
    	}

    	drawWindInit();
        inputMode=0;
    	
    	lastInputTime=System.currentTimeMillis();

    	screenRepaint();
    	loadCompleted=true;

    	startAutoTimer();
    	startWaiting();

    	//debug(10,"GameUI.onStart()::YOUR WIND="+player.getWind()+ "  ;PREVALING WIND="+engine.getCurrentWind());
    	//debug(2,"onStart() finished");
    }
    
    private void drawPointer() {
        int offset=DeviceConstant.POINTER_OFFSET;
        //debug(0,"drawPointer() start ***pointer=" + pointer + "  ;prevpointer=" + prevpointer);
        if (pointer==0)
            offset=0;
        if (arrow==null) {
    		System.gc();
    		preloadResources();
        }
        osg.drawImage(arrow,swidth-DeviceConstant.TILE_WIDTH
        		-offset-(pointer*DeviceConstant.TILE_WIDTH),sheight-DeviceConstant.TILE_HEIGHT-DeviceConstant.POINTER_HEIGHT, Graphics.LEFT| Graphics.TOP);

        if (prevpointer==0)
            offset=0;
        else
            offset=DeviceConstant.POINTER_OFFSET;
        
        osg.drawImage(emptyBuffer2, 
        		swidth-DeviceConstant.TILE_WIDTH-offset-(prevpointer*DeviceConstant.TILE_WIDTH),sheight -2*DeviceConstant.TILE_HEIGHT   , Graphics.LEFT| Graphics.TOP);
        osg.drawImage(tileTopEdge, 
        		swidth-DeviceConstant.TILE_WIDTH-offset-(prevpointer*DeviceConstant.TILE_WIDTH),sheight-DeviceConstant.TILE_HEIGHT -DeviceConstant.TILE_TOPEDGE_HEIGHT  , Graphics.LEFT| Graphics.TOP);
       
        prevpointer=pointer;
    	screenRepaint();
        //debug(2,"drawPointer() finish");
    }
    
    /*
     * area for new tile
     * 
     */
    private void drawArea1() {
        osg.drawImage(Image.createImage(drawTile(player.newtile)), 
               swidth-DeviceConstant.TILE_WIDTH,  sheight-DeviceConstant.TILE_HEIGHT, Graphics.LEFT| Graphics.TOP);
        osg.drawImage(tileTopEdge  , 
             swidth-DeviceConstant.TILE_WIDTH,  sheight-DeviceConstant.TILE_HEIGHT-DeviceConstant.TILE_TOPEDGE_HEIGHT, Graphics.LEFT| Graphics.TOP);
    }

    //helper method. called by drawSelectedTile
    private void drawInvertedTile(int i) {
		osg.drawImage(drawTile(player.concealedTiles.getTileAt(i)), 
          		(13-player.concealedTiles.size())*DeviceConstant.TILE_WIDTH + i*DeviceConstant.TILE_WIDTH, sheight-DeviceConstant.TILE_HEIGHT-DeviceConstant.TILE_TOPEDGE_HEIGHT, Graphics.LEFT| Graphics.TOP);                		            
        osg.drawImage(tileBottomEdge , 
                (13-player.concealedTiles.size())*DeviceConstant.TILE_WIDTH  + i*DeviceConstant.TILE_WIDTH  ,sheight-DeviceConstant.TILE_TOPEDGE_HEIGHT, Graphics.LEFT| Graphics.TOP);
    }
    
    //helper method. called by drawSelectedTile
    private void drawNormalTile(int i) {
		osg.drawImage(drawTile(player.concealedTiles.getTileAt(i)), 
          		(13-player.concealedTiles.size())*DeviceConstant.TILE_WIDTH  + i*DeviceConstant.TILE_WIDTH  ,  sheight-DeviceConstant.TILE_HEIGHT, Graphics.LEFT| Graphics.TOP);                
        osg.drawImage(tileTopEdge , 
                (13-player.concealedTiles.size())*DeviceConstant.TILE_WIDTH   + i*DeviceConstant.TILE_WIDTH   ,  sheight-DeviceConstant.TILE_HEIGHT-DeviceConstant.TILE_TOPEDGE_HEIGHT, Graphics.LEFT| Graphics.TOP);
    }
    
    /*
     * draw selected tile for kong/pong event
     */
    private void drawSelectedTile(int startpos, int endpos) throws IOException {
        //debug(2,"drawSelectedTile()");
    	drawVisibleTile();    		
        for(int i=0;i<=player.concealedTiles.size()-1;i++) {
        	if (i>=startpos && i<=endpos) {	            	
        		drawInvertedTile(i);
        	}
        	else {
        		drawNormalTile(i);
        	}
        }
    }

    //for drawing selected tile in visible area
    private void drawSelectedTile1b(int startpos, int endpos) {
        //debug(2,"drawSelectedTile1b()");
        //draw visible tiles
        int counter=1;
        int prevGroup=-1;
        Tile t=null;
        
        int j=0;//counter for drawing
        for(int i=0;i<player.visibleTiles.size();i++) {
	     	t=player.visibleTiles.getTileAt(i);
	     	//debug(2,"visibletiles size="+ player.visibleTiles.size());
	     	
	     	if (prevGroup!=t.group) {
	     		prevGroup=t.group;
	     		counter=1;
	     	}
	     	if (counter!=4) {
	        	if (i>=startpos && i<=endpos) {	            	
		     		osg.drawImage(smallEdge ,j*DeviceConstant.STILE_WIDTH, sheight-DeviceConstant.TILE_HEIGHT - DeviceConstant.TILE_SMALLEDGE_HEIGHT, Graphics.LEFT|Graphics.TOP);
		     		osg.drawImage(drawSmallTile(t, Tile.POSITION_BOTTOM),
			     			j*DeviceConstant.STILE_WIDTH,sheight-DeviceConstant.TILE_HEIGHT, Graphics.LEFT|Graphics.TOP);		        		
	        	}
	        	else {
	        		osg.drawImage(smallEdge ,j*DeviceConstant.STILE_WIDTH, sheight- DeviceConstant.TILE_SMALLEDGE_HEIGHT, Graphics.LEFT|Graphics.TOP);
		     		osg.drawImage(drawSmallTile(t, Tile.POSITION_BOTTOM),
		     			j*DeviceConstant.STILE_WIDTH,sheight-DeviceConstant.TILE_HEIGHT - DeviceConstant.TILE_SMALLEDGE_HEIGHT, Graphics.LEFT|Graphics.TOP);
	        	}
	     	}
	     	else { 
	     		osg.drawImage(drawBlock(Tile.POSITION_BOTTOM),
	         			(j-2)*DeviceConstant.STILE_WIDTH,sheight-DeviceConstant.TILE_HEIGHT, Graphics.LEFT|Graphics.TOP);
	     		j--;
	     	}
	     	counter++;
	     	j++;
	    }
        for(int i=0;i<=player.concealedTiles.size()-1;i++) {
        	drawNormalTile(i);
        }
    }

    /*
     * draw selected tile for chow event
     */
    private void drawSelectedTile2(int startpos, int endpos) {
        //debug(2,"drawSelectedTile2()");
    	
        for(int i=0;i<=player.concealedTiles.size()-1;i++) {
        	if (i==startpos || i==endpos) {	            	
        		drawInvertedTile(i);
        	}
        	else {
        		drawNormalTile(i);
        	}
        }
     }

    /*
     * area for drawing usertile
     * call: drawVisibleTile()
     * @param delay simulate delay
     */
    private void drawArea8(int delay) {
        //debug(2,"drawArea8()");
        player.concealedTiles=player.getTiles();

    	osg.drawImage(emptyBuffer3 , 0,  sheight -2*DeviceConstant.TILE_HEIGHT  , Graphics.LEFT| Graphics.TOP);
        drawVisibleTile();
        
        for(int i=0;i<=player.concealedTiles.size()-1;i++) {
        	osg.drawImage(drawTile(player.concealedTiles.getTileAt(i)), (13-player.concealedTiles.size())*DeviceConstant.TILE_WIDTH + i*DeviceConstant.TILE_WIDTH ,  
        			sheight-DeviceConstant.TILE_HEIGHT, Graphics.LEFT| Graphics.TOP);                
            
        	osg.drawImage(tileTopEdge, 
        			(13-player.concealedTiles.size())*DeviceConstant.TILE_WIDTH + i*DeviceConstant.TILE_WIDTH ,  sheight-DeviceConstant.TILE_HEIGHT-DeviceConstant.TILE_TOPEDGE_HEIGHT, Graphics.LEFT| Graphics.TOP);
        	if (delay==1)
        		simulateDelay();
    	}       
    }
    
    private void drawUnhighlight()  {
        //debug(2,"drawUnHighLight()");
        int leftOffset = (getWidth() - (11*DeviceConstant.TILE_WIDTH))/2;
        
        //draw tile on the table
        int xpos=(numberOfTile-1)%11;
        int ypos = (numberOfTile-1)/11;
      
     	osg.drawImage(discardedBuffer, leftOffset+ DeviceConstant.TILE_WIDTH* xpos,  DeviceConstant.TABLE_POSITION_V+ DeviceConstant.TILE_HEIGHT* ypos  , Graphics.LEFT | Graphics.TOP);
    }
    
    private void drawHighlight() {
        //debug(2,"drawHighLight()");
        int leftOffset = (swidth - (11*DeviceConstant.TILE_WIDTH))/2;
        
        //draw tile on the table
        int xpos=(numberOfTile-1)%11;
        int ypos = (numberOfTile-1)/11;
      
		discardedBuffer= Common.extract(offScreenBuffer, 
				leftOffset+ DeviceConstant.TILE_WIDTH* xpos,  DeviceConstant.TABLE_POSITION_V+ DeviceConstant.TILE_HEIGHT* ypos,   
				DeviceConstant.TILE_WIDTH, DeviceConstant.TILE_HEIGHT+ DeviceConstant.TILE_TOPEDGE_HEIGHT );
        
        osg.setColor(255,0,0);
        osg.drawRect(leftOffset+ DeviceConstant.TILE_WIDTH* xpos,  DeviceConstant.TABLE_POSITION_V+ DeviceConstant.TILE_HEIGHT* ypos,   
        		DeviceConstant.TILE_WIDTH-1,  DeviceConstant.TILE_HEIGHT+ DeviceConstant.TILE_TOPEDGE_HEIGHT -1);
    }

    /*
     * draw area of throw away tile
     * and increment the number of tile on the table
     */
    private void drawArea6(Tile newtile) {
        //debug(2,"drawArea6:::  "+ newtile.type+"_"+newtile.value);
        int leftOffset = (getWidth() - (11*DeviceConstant.TILE_WIDTH))/2;

        //draw tile on the table
        int xpos=(numberOfTile)%11;
        int ypos = (numberOfTile)/11;

     	osg.drawImage(drawTile(newtile), leftOffset+ DeviceConstant.TILE_WIDTH* xpos,  DeviceConstant.TABLE_POSITION_V+ DeviceConstant.TILE_HEIGHT* ypos  , Graphics.LEFT | Graphics.TOP);
     	osg.drawImage(tileBottomEdge, leftOffset+ DeviceConstant.TILE_WIDTH* xpos,  DeviceConstant.TABLE_POSITION_V+DeviceConstant.TILE_HEIGHT+DeviceConstant.TILE_HEIGHT* ypos, Graphics.LEFT | Graphics.TOP);
        numberOfTile++;
    }
    
    private void drawOtherVisibleTile(TileCollection tiles, int position) {
    	//debug(2,"drawOtherVisibleTile()");
    	int counter=1;
        int prevGroup=-1;
        Tile t=null;
        
        int j=0;//counter for drawing
        for(int i=0;i<tiles.size();i++) {
        	t=tiles.getTileAt(i);
        	if (prevGroup!=t.group) {
        		prevGroup=t.group;
        		counter=1;
        	}
        	if (counter!=4) {
        		if (position==1) {        		
	        		osg.drawImage(drawSmallTile(t, Tile.POSITION_RIGHT),
	        			swidth-DeviceConstant.TILE_HEIGHT, DeviceConstant.RIGHTTILE_POSITION_H- j*DeviceConstant.STILE_WIDTH, Graphics.LEFT|Graphics.TOP);
        		}
        		else if (position==2) {
	        		osg.drawImage(drawSmallTile(t, Tile.POSITION_UP),
	        				swidth-(j+1)*DeviceConstant.STILE_WIDTH, 0, Graphics.LEFT|Graphics.TOP);
        			
        		}
        		else if (position==3) {
	        		osg.drawImage(drawSmallTile(t, Tile.POSITION_LEFT),
		        			0, 0 + j*DeviceConstant.STILE_WIDTH, Graphics.LEFT|Graphics.TOP);
        		}
        	}
        	else { 
        		if (position==1) {
	        		osg.drawImage(drawBlock(Tile.POSITION_RIGHT),
	        				swidth-DeviceConstant.TILE_HEIGHT, DeviceConstant.RIGHTTILE_POSITION_H- (j-2)*DeviceConstant.STILE_WIDTH, Graphics.LEFT|Graphics.TOP);
        		}
        		else if (position==2) {
	        		osg.drawImage(drawBlock(Tile.POSITION_UP),
	        				swidth-(j+1-2)*DeviceConstant.STILE_WIDTH, 0, Graphics.LEFT|Graphics.TOP);
        		}
        		else if (position==3) {
	        		osg.drawImage(drawBlock(Tile.POSITION_LEFT),
	            			0, 0 + (j-2)*DeviceConstant.STILE_WIDTH, Graphics.LEFT|Graphics.TOP);
        		}
        		j--;
        	}
        	counter++;
        	j++;
        }
    }
    
    
    private void playAnimation() {
    	//debug(2,"playAnimation()");
//    	System.out.println("playAnimation");
    	try {
    		osg.drawImage(CanvasHelper.dealingImage,0, DeviceConstant.DEALINGTEXT_POSITION_H , Graphics.LEFT| Graphics.TOP);            	
			osg.drawImage(CanvasHelper.getAnimatedImage(0),(swidth-DeviceConstant.ANIM_WIDTH)/2,(sheight-DeviceConstant.ANIM_HEIGHT), Graphics.LEFT | Graphics.TOP);					
	    	screenRepaint();

    		if (initPlayer!=null) {
    			initPlayer.close();
    		}
	    	
    		InputStream is=getClass().getResourceAsStream("/res/shipai.amr");        	
    		initPlayer=Manager.createPlayer(is,"audio/amr");
	    	if (initPlayer!=null) {
		    	initPlayer.realize();
		    	
	    	
	        	initVolControl= (VolumeControl) initPlayer.getControl("VolumeControl");
//	        	System.out.println("initVolControl="+initVolControl);
	        	if (initVolControl!=null) {
//		    		System.out.println("PlayAnimation. initSound. realVolumeLevel="+realVolumeLevel);
	        		initVolControl.setLevel(realVolumeLevel);
//	        		initVolControl.setLevel(0);
//		    		System.out.println("PlayAnimation. initSound. realVolumeLevel="+realVolumeLevel);
	        	}
	    	}

	    	if (!startAnim) {
		    	startAnim=true;
		    	Thread t= new Thread( new AnimatedThread()) ;
		    	t.start();
	    	}
	    	
	    	while (startAnim) {
	    		Thread.sleep(500);
	    	} 
	    	
    		Thread.sleep(200);	    	
        	initPlayer.start();    	
    		System.out.println("playAnimation.init player start.:realVolumeLevel="+realVolumeLevel);

        	screenRepaint();
    	}
	  	catch (IOException ex) {    	  		
	  	}
        catch (MediaException e) {
        }
        catch (InterruptedException iex) {
        }

        catch (RuntimeException re) {
        }
    }
    
    
    /*
     * @param position 0=start
     */
    private void drawArea3(int numOfTile, int position) {
        //debug(2,"drawArea3() ::::" + " ;relative pos="+ position+ ";size="+ numOfTile );
    	if (position==3 || position==0) {        	
            //draw left tile
            for(int i=0;i<13-numOfTile;i++) {
                osg.drawImage(Common.extract(emptyBuffer2,0,0,DeviceConstant.SIDETILE_SIZE,DeviceConstant.SIDETILE_SIZE),
                        0,  DeviceConstant.TILE_LEFTPLAYER_POS_V+ DeviceConstant.SIDETILE_SIZE* i, Graphics.LEFT | Graphics.TOP);
                if (!loadCompleted)
                	simulateDelay();
            }
            for(int i=13-numOfTile;i<13;i++) {
                osg.drawImage(tileSideLeft, 
                        0,  DeviceConstant.TILE_LEFTPLAYER_POS_V+ DeviceConstant.SIDETILE_SIZE* i, Graphics.LEFT | Graphics.TOP);
                if (!loadCompleted)
                	simulateDelay();
            }
    	}

    	if (position==2 || position==0) {
        	//draw top tile
            for(int i=0;i<numOfTile;i++) {
                osg.drawImage(Image.createImage(tileSideTop), 
                        swidth- 14*DeviceConstant.SIDETILE_SIZE + i*DeviceConstant.SIDETILE_SIZE ,  0, Graphics.LEFT| Graphics.TOP);
                if (!loadCompleted)
                	simulateDelay();
            }            
            for(int i=numOfTile;i<13;i++) {
                osg.drawImage(Common.extract(emptyBuffer2,0,0,DeviceConstant.SIDETILE_SIZE,DeviceConstant.SIDETILE_SIZE), 
                		swidth- 14*DeviceConstant.SIDETILE_SIZE + i*DeviceConstant.SIDETILE_SIZE ,  0, Graphics.LEFT| Graphics.TOP);

                if (!loadCompleted)
                	simulateDelay();
            }
    	}

    	if (position==1 || position==0) {
        	//draw right tile
            for(int i=0;i<numOfTile;i++) {
                osg.drawImage(tileSideRight, 
                        swidth-DeviceConstant.SIDETILE_SIZE,  (getHeight()- 13*DeviceConstant.SIDETILE_SIZE)/2 + i*DeviceConstant.SIDETILE_SIZE, Graphics.LEFT| Graphics.TOP);
                if (position==0)
                	simulateDelay();
            }
            for(int i=numOfTile;i<13;i++) {
                osg.drawImage(Common.extract(emptyBuffer2,0,0,DeviceConstant.SIDETILE_SIZE,DeviceConstant.SIDETILE_SIZE), 
                        swidth-DeviceConstant.SIDETILE_SIZE,  (getHeight()- 13*DeviceConstant.SIDETILE_SIZE)/2 + i*DeviceConstant.SIDETILE_SIZE, Graphics.LEFT| Graphics.TOP);
                if (position==0)
                	simulateDelay();
            }
    	}            
        //debug(2,"drawArea3() end");
    }

    private void drawWaitArrow(int pos) {
    	if (playMode==0) 
    		return;

		osg.drawImage(waitArrowBuffer, 
                swidth-DeviceConstant.SIDETILE_SIZE-DeviceConstant.WAITARROW_SIZE,  (getHeight()- 13*DeviceConstant.SIDETILE_SIZE)/2 + 0*DeviceConstant.SIDETILE_SIZE, Graphics.LEFT| Graphics.TOP);
        osg.drawImage(waitArrowBuffer, 
                swidth- 14*DeviceConstant.SIDETILE_SIZE + 0*DeviceConstant.SIDETILE_SIZE ,  0+DeviceConstant.WAITARROW_SIZE, Graphics.LEFT| Graphics.TOP);
        osg.drawImage(waitArrowBuffer,  
                0+DeviceConstant.WAITARROW_SIZE,  DeviceConstant.TILE_LEFTPLAYER_POS_V+ DeviceConstant.SIDETILE_SIZE* 12, Graphics.LEFT | Graphics.TOP);

    	if (pos==1) {
    		try {    		
    			Thread.sleep(800);
    			osg.drawImage(getWaitingArrow(pos), 
    					swidth-DeviceConstant.SIDETILE_SIZE-DeviceConstant.WAITARROW_SIZE,  (getHeight()- 13*DeviceConstant.SIDETILE_SIZE)/2 + 0*DeviceConstant.SIDETILE_SIZE, Graphics.LEFT| Graphics.TOP);
    		}
    		catch (InterruptedException i) {
    		}
    	}
    	else if (pos==2) {
            osg.drawImage(getWaitingArrow(pos), 
                    swidth- 14*DeviceConstant.SIDETILE_SIZE + 0*DeviceConstant.SIDETILE_SIZE ,  0+DeviceConstant.WAITARROW_SIZE, Graphics.LEFT| Graphics.TOP);
    	}
    	else if (pos==3) {
            osg.drawImage(getWaitingArrow(pos),  
                    0+DeviceConstant.WAITARROW_SIZE,  DeviceConstant.TILE_LEFTPLAYER_POS_V+ DeviceConstant.SIDETILE_SIZE* 12, Graphics.LEFT | Graphics.TOP);
    	}
    	prevWaitArrowPos=pos;
    	screenRepaint();
    }
    
    
    private Image getWaitingArrow(int pos) {
    	String filename="";
    	if (pos==1) {
    		filename="arrowright.png";
    	}
    	else if (pos==2) {
    		filename="arrowup.png";
    	}
    	else if (pos==3) {
    		filename="arrowleft.png";    		
    	}
    	Image tmp=null;
    	try {
    		tmp=Image.createImage("/res/"+filename) ;        	
    	}
    	catch (IOException e){
    		throw new Error();
    	}
    	return tmp;
    }
    
    private Image getDirectionArrow(int player) {
    	String filename="";
    	if (player==0) {
    		filename="indi_arrow0.png";
    	}
    	else if (player==1) {
    		filename="indi_arrow1.png";
    	}
    	else if (player==2) {
    		filename="indi_arrow2.png";    		
    	}
    	else if (player==3) {
    		filename="indi_arrow3.png";    		    		
    	}
    	Image tmp=null;
    	try {
    		tmp=Image.createImage("/res/"+filename) ;        	
    	}
    	catch (IOException e){
    		throw new Error();
    	}
    	return tmp;
    }
    
    
    private Image drawTile(Tile t) {
    	int row=t.type;
    	int col=t.value-1;
    	
    	if (t.type==4) {
    		row=3;
    		col=col+4;
    	}
    	return Common.extract(mainTile, col*DeviceConstant.TILE_WIDTH,row*DeviceConstant.TILE_HEIGHT,DeviceConstant.TILE_WIDTH,DeviceConstant.TILE_HEIGHT);
    }
    
    private Image drawBlock(int position) {
    	Image image=null;
		image=Common.extract(mainTileSmall,7*DeviceConstant.STILE_WIDTH,3*DeviceConstant.TILE_HEIGHT,DeviceConstant.STILE_WIDTH,DeviceConstant.TILE_HEIGHT);
    	
    	Image tmpBuffer1 = Image.createImage(DeviceConstant.STILE_WIDTH, DeviceConstant.TILE_HEIGHT);
    	Image tmpBuffer2 = Image.createImage(DeviceConstant.TILE_HEIGHT, DeviceConstant.STILE_WIDTH );
        Graphics tmpGraphics1= tmpBuffer1.getGraphics();        
        Graphics tmpGraphics2= tmpBuffer2.getGraphics();        

    	if (position == Tile.POSITION_BOTTOM) {
    		tmpGraphics1.drawRegion(image,0,0,image.getWidth(),image.getHeight(), Sprite.TRANS_NONE,0,0,Graphics.TOP|Graphics.LEFT);
    		return tmpBuffer1;
    	}
    	else if (position == Tile.POSITION_UP) {
    		tmpGraphics1.drawRegion(image,0,0,image.getWidth(),image.getHeight(), Sprite.TRANS_ROT180,0,0,Graphics.TOP|Graphics.LEFT);
    		return tmpBuffer1;
    	}
       	else if (position == Tile.POSITION_LEFT) {
    		tmpGraphics2.drawRegion(image,0,0,image.getWidth(),image.getHeight(), Sprite.TRANS_ROT90,0,0,Graphics.TOP|Graphics.LEFT);
    		return tmpBuffer2;
       	}
       	else {
    		tmpGraphics2.drawRegion(image,0,0,image.getWidth(),image.getHeight(), Sprite.TRANS_ROT270,0,0,Graphics.TOP|Graphics.LEFT);
    		return tmpBuffer2;
       	}
    
    }

    /*
     * visible tile
     */
    private Image drawSmallTile(Tile t, int position) {
    	Image image=null;

    	int row=0;
    	int col=0;
    	
    	//new algoritm using rotation
    	Image tmpBuffer1 = Image.createImage(DeviceConstant.STILE_WIDTH, DeviceConstant.TILE_HEIGHT);
    	Image tmpBuffer2 = Image.createImage(DeviceConstant.TILE_HEIGHT, DeviceConstant.STILE_WIDTH );
        Graphics tmpGraphics1= tmpBuffer1.getGraphics();        
        Graphics tmpGraphics2= tmpBuffer2.getGraphics();        

    	row=t.type;
    	col=t.value-1;
    	if (t.type==4) {
    		row=3;
    		col=col+4;
    	}
    	image=Common.extract(mainTileSmall,col*DeviceConstant.STILE_WIDTH,row*DeviceConstant.TILE_HEIGHT,DeviceConstant.STILE_WIDTH,DeviceConstant.TILE_HEIGHT);	
  
    	if (position == Tile.POSITION_BOTTOM) {
    		tmpGraphics1.drawRegion(image,0,0,image.getWidth(),image.getHeight(), Sprite.TRANS_NONE,0,0,Graphics.TOP|Graphics.LEFT);
    		return tmpBuffer1;
    	}
    	else if (position == Tile.POSITION_UP) {
    		tmpGraphics1.drawRegion(image,0,0,image.getWidth(),image.getHeight(), Sprite.TRANS_ROT180,0,0,Graphics.TOP|Graphics.LEFT);
    		return tmpBuffer1;
    	}
       	else if (position == Tile.POSITION_LEFT) {
    		tmpGraphics2.drawRegion(image,0,0,image.getWidth(),image.getHeight(), Sprite.TRANS_ROT90,0,0,Graphics.TOP|Graphics.LEFT);
    		return tmpBuffer2;
       	}
       	else {
    		tmpGraphics2.drawRegion(image,0,0,image.getWidth(),image.getHeight(), Sprite.TRANS_ROT270,0,0,Graphics.TOP|Graphics.LEFT);
    		return tmpBuffer2;
       	}
    }
    
    private void drawVisibleTile() {
    	//debug(2,"drawVisibleTile()");
        int counter=1;
        int prevGroup=-1;
        Tile t=null;
        
        int j=0;//counter for drawing
        for(int i=0;i<player.visibleTiles.size();i++) {
       
        	t=player.visibleTiles.getTileAt(i);
        	if (prevGroup!=t.group) {
        		prevGroup=t.group;
        		counter=1;
        	}
        	
        	if (counter!=4) {
        		osg.drawImage(smallEdge, j*DeviceConstant.STILE_WIDTH,sheight-DeviceConstant.TILE_SMALLEDGE_HEIGHT , Graphics.LEFT|Graphics.TOP);
        		osg.drawImage(drawSmallTile(t, Tile.POSITION_BOTTOM),
        			j*DeviceConstant.STILE_WIDTH,sheight-DeviceConstant.TILE_HEIGHT-DeviceConstant.TILE_SMALLEDGE_HEIGHT , Graphics.LEFT|Graphics.TOP);
        	}
        	else { 
        		osg.drawImage(drawBlock(Tile.POSITION_BOTTOM),
            			(j-2)*DeviceConstant.STILE_WIDTH, sheight-DeviceConstant.TILE_HEIGHT, Graphics.LEFT|Graphics.TOP);
        		j--;
        	}
        	counter++;
        	j++;
        }
    }
    
    
    private void drawWind()  {
        //debug(2,"drawWind()");
        osg.drawImage(Common.extract(windImage, 
        		engine.getCurrentWind()*DeviceConstant.WIND_WIDTH,0,DeviceConstant.WIND_WIDTH,DeviceConstant.WIND_HEIGHT), 
        		0,  DeviceConstant.WIND_POSITION_H, Graphics.LEFT| Graphics.TOP);

        osg.drawImage(getDirectionArrow( (4- player.getWind() )%4  ), 
        		DeviceConstant.WIND_WIDTH+2,       
				DeviceConstant.WIND_POSITION_H - (DeviceConstant.INDO_IMGHEIGHT-DeviceConstant.WIND_HEIGHT)/2,
				Graphics.LEFT| Graphics.TOP);				
    }

    private void drawNumberofTile (int numTileOnDeck) {
        osg.drawImage(emptyBuffer2 , 
        		DeviceConstant.NUMOFTILE_POS_H  ,  DeviceConstant.NUMOFTILE_POS_V, Graphics.LEFT| Graphics.TOP);
        osg.drawImage(emptyBuffer2 , 
        		DeviceConstant.NUMOFTILE_POS_H +5,  DeviceConstant.NUMOFTILE_POS_V, Graphics.LEFT| Graphics.TOP);
        osg.drawImage(emptyBuffer2 , 
        		DeviceConstant.NUMOFTILE_POS_H  ,  DeviceConstant.NUMOFTILE_POS_V+5, Graphics.LEFT| Graphics.TOP);
        osg.drawImage(emptyBuffer2 , 
        		DeviceConstant.NUMOFTILE_POS_H +5,  DeviceConstant.NUMOFTILE_POS_V+5, Graphics.LEFT| Graphics.TOP);

        osg.setColor(0,0,0);
        osg.setFont(Font.getFont(
        	    Font.FACE_MONOSPACE, Font.STYLE_PLAIN ,Font.SIZE_SMALL));
        
        osg.drawString( numTileOnDeck +"",DeviceConstant.NUMOFTILE_POS_H ,DeviceConstant.NUMOFTILE_POS_V ,Graphics.LEFT| Graphics.TOP);
    }
    
    /*
     * NOTICE AREA
     * selectionState: 0=firstlevelselection ; 1=secondlevelselection
     * selection: no of selection, 0=first element; 4=cancelbutton
     * read globalvariable firstLevelAction
     * reset the currentActions vector();
     */
    private void drawArea7a(int selectionState, int selection, Vector actions) {
    	//debug(2,"drawArea7a()");
		
    	currentActions=new Vector();

    	//save the screen before drawing a new screen, we need to redraw the original screen later
    	if (!flagClearNotice){
			tempBuffer2= Common.extract(offScreenBuffer, 0,0,swidth,sheight);
			flagClearNotice=true;
		}
		
		//draw background dialog box
    	int leftpos=(swidth - dialog.getWidth())/2;
    	if (dialog==null) {
    		System.gc();
    		preloadResources();
    	}
    	osg.drawImage(dialog, leftpos, DeviceConstant.NOTICE_POSITION_V, Graphics.LEFT| Graphics.TOP);
    	
    	//drawing selection
    	int prevType=-1;
    	int leftnoticepos=leftpos+2;
    	int currentType=-1;
    	int drawCount=1;
    	//debug(0,"actions.size.numberof available action ="+actions.size());
    	if (selectionState==0) { //first level choice
    		for (int i=0;i<actions.size();i++) {
    			currentType=((SpecialAction)actions.elementAt(i)).type ;
    			
    			if (prevType!= currentType) {
    				prevType=currentType;
    				//draw a new image
    				if (drawCount==selection) {
            			osg.drawImage(getNoticeItemImage(currentType,1), leftnoticepos,  DeviceConstant.NOTICE_POSITION_V+2  , Graphics.LEFT| Graphics.TOP);        				
    				}
    				else {
            			osg.drawImage(getNoticeItemImage(currentType,0), leftnoticepos,  DeviceConstant.NOTICE_POSITION_V+2  , Graphics.LEFT| Graphics.TOP);        				        					
    				}
    				leftnoticepos=leftnoticepos+DeviceConstant.NOTICE_NOTICEITEM_W+1;
    				//debug(0,"currentActions.addElement at "+ i);
    				SpecialAction action=(SpecialAction)actions.elementAt(i);
    				currentActions.addElement( actions.elementAt(i));
        			//debug(0,"action properties:starttile=" +action.startTilePosition+";endtile="+action.endTilePosition+";type="+action.type+";isVisible="+action.isVisible);
    				drawCount++;	
    			}
    		}
    		if (selection==maxNumberofSelection) {
    			//debug(0,"**draw cancel1");
    			osg.drawImage(getNoticeItemImage(GameAction.TYPE_CANCEL,1), 
	                    leftpos+dialog.getWidth()-DeviceConstant.NOTICE_NOTICEITEM_W-2 , DeviceConstant.NOTICE_POSITION_V+2  , Graphics.LEFT| Graphics.TOP);
    		}
    		else {
    			//debug(0,"**draw cancel2");
    			osg.drawImage(getNoticeItemImage(GameAction.TYPE_CANCEL,0), 
    					leftpos+dialog.getWidth()-DeviceConstant.NOTICE_NOTICEITEM_W-2, DeviceConstant.NOTICE_POSITION_V+2  , Graphics.LEFT| Graphics.TOP);        			
    		}
        	maxNumberofSelection=drawCount;
    	}
    	else { //selectionstate=1
    		//debug(0,"**draw selection state1");

    		int firstLevelType=firstLevelAction.type;
			osg.drawImage(getNoticeItemImage(firstLevelType,0), 
                    leftnoticepos,  DeviceConstant.NOTICE_POSITION_V+2  , Graphics.LEFT| Graphics.TOP);        				
			leftnoticepos=leftnoticepos+DeviceConstant.NOTICE_NOTICEITEM_W+1;
    		
    		for (int i=0;i<actions.size();i++) {
    			currentType=((SpecialAction)actions.elementAt(i)).type ;
    			if (currentType==firstLevelType) {
    				//draw a new image
    				if (drawCount==1)
    					currentType=GameAction.TYPE_1;
    				else if (drawCount==2)
    					currentType=GameAction.TYPE_2;
    				else
    					currentType=GameAction.TYPE_3;
    				
    				if (drawCount==selection) {
            			osg.drawImage(getNoticeItemImage(currentType,1), leftnoticepos,  DeviceConstant.NOTICE_POSITION_V+2  , Graphics.LEFT| Graphics.TOP);        				
    				}
    				else {
            			osg.drawImage(getNoticeItemImage(currentType,0), leftnoticepos,  DeviceConstant.NOTICE_POSITION_V+2  , Graphics.LEFT| Graphics.TOP);        				        					
    				}
    				leftnoticepos=leftnoticepos+DeviceConstant.NOTICE_NOTICEITEM_W+1;
    				//debug(0,"currentActions.addElement");

    				currentActions.addElement(actions.elementAt(i));
    				SpecialAction action=(SpecialAction)actions.elementAt(i);

    				//debug(0,"actionproperties..starttile=" +action.startTilePosition+";endtile="+action.endTilePosition+";type="+action.type+";isVisible="+action.isVisible);
    				//debug(0,"drawArea7a():::Adding to currentActions. Size="+ currentActions.size() );
    				drawCount++;	
    			}
    		}//end for
    		if (selection==maxNumberofSelection) {
    			osg.drawImage(getNoticeItemImage(GameAction.TYPE_CANCEL,1), 
	                    leftpos+dialog.getWidth()-DeviceConstant.NOTICE_NOTICEITEM_W-2, DeviceConstant.NOTICE_POSITION_V+2  , Graphics.LEFT| Graphics.TOP);
    		}
    		else {
    			osg.drawImage(getNoticeItemImage(GameAction.TYPE_CANCEL,0), 
	                    leftpos+dialog.getWidth()-DeviceConstant.NOTICE_NOTICEITEM_W-2, DeviceConstant.NOTICE_POSITION_V+2  , Graphics.LEFT| Graphics.TOP);        			
    		}
        	maxNumberofSelection=drawCount;
    	}
        	
    	screenRepaint();
        //debug(2,"drawArea7a finished");
    }

    /* Params:
     * 1. type: chow, pong, kong, or secondlevel selection
     * 2. state: 0= not selected, 1=selected
	*/
    private Image getNoticeItemImage(int type, int state)  {
    	int row=0;
		if (type==GameAction.TYPE_CANCEL)
			row=0;
		else if (type==GameAction.TYPE_CHOW)
			row=1;
		else if (type==GameAction.TYPE_PONG)
			row=2;
		else if (type==GameAction.TYPE_KONG)
			row=3;
		else if (type==GameAction.TYPE_WIN || type==GameAction.TYPE_WIN_ONROBBING) 
			row=4;    			
		else if (type==GameAction.TYPE_1) 
			row=0;
		else if (type==GameAction.TYPE_2)
			row=1;
		else if (type==GameAction.TYPE_3)
			row=2;

    	if (type==GameAction.TYPE_CANCEL||type==GameAction.TYPE_CHOW||type==GameAction.TYPE_KONG||type==GameAction.TYPE_PONG||type==GameAction.TYPE_WIN||type==GameAction.TYPE_WIN_ONROBBING)
    		return Common.extract(firstLevelSymbol, state*DeviceConstant.NOTICE_NOTICEITEM_W,row*DeviceConstant.NOTICE_NOTICEITEM_H,DeviceConstant.NOTICE_NOTICEITEM_W,DeviceConstant.NOTICE_NOTICEITEM_H);    		
    	else
    		return Common.extract(secondLevelSymbol, state*DeviceConstant.NOTICE_NOTICEITEM_W,row*DeviceConstant.NOTICE_NOTICEITEM_H,DeviceConstant.NOTICE_NOTICEITEM_W,DeviceConstant.NOTICE_NOTICEITEM_H);
    }
    
    
    /*
     * to redraw the screen to the state before the notice
    */
    private void redraw() {
        //debug(2,"redraw()");
    	osg.drawImage(tempBuffer2,0,0,Graphics.LEFT|Graphics.TOP);
    	flagClearNotice=false;
    }
    
    
    /*
     * playerTurn = other player position relative to u
     */
    public void onTileTaken(TileCollection otherCol, int size, int playerTurn, boolean isDiscardedTile, Tile tempDiscard) {
    	//debug(2,"onTileTaken(), size="+size+";playTurn="+playerTurn+ " ;otherCol.size()="+otherCol.size()+ " ;ownwind="+player.getWind());
    	
    	player.discardedtile=tempDiscard;
    	if (isDiscardedTile) {
	    	numberOfTile--;
	    	drawEmptyTile();
    	}
    	screenRepaint();
	    	
    	if (playerTurn<player.getWind())
    		playerTurn=playerTurn+4;
    	
    	updateOtherTile(otherCol,size,playerTurn-player.getWind());

    	if (playerTurn==(player.getWind()+1)) 
    		drawWaitArrow(1);
    	else if (playerTurn==(player.getWind()+2))             
    		drawWaitArrow(2);
        else if (playerTurn==(player.getWind()+3))             
    		drawWaitArrow(3);

    	screenRepaint();

    	if (speedDebug<=4) {
	    	try {
	    		Thread.sleep(1000);
	    	}
	    	catch (InterruptedException ex) {
	    	}
    	}
    	screenRepaint();
    }
    
    
    private void updateOtherTile(TileCollection otherCol, int size, int playerTurn) {
    	drawArea3(size-1,playerTurn);
    	drawOtherVisibleTile(otherCol,playerTurn);
    }
	
	
    public void drawEmptyTile() {
        //debug(0,"drawEmptyTile");
        int leftOffset = (swidth - (11*DeviceConstant.TILE_WIDTH))/2;
        
        //draw tile on the table
        int xpos=(numberOfTile)%11;
        int ypos = (numberOfTile)/11;
      
     	osg.drawImage(emptyBuffer2, leftOffset+ DeviceConstant.TILE_WIDTH* xpos,  DeviceConstant.TABLE_POSITION_V+ DeviceConstant.TILE_HEIGHT* ypos  , Graphics.LEFT | Graphics.TOP);
     	osg.drawImage(emptyBuffer2, leftOffset+ DeviceConstant.TILE_WIDTH* xpos,  DeviceConstant.TABLE_POSITION_V+ DeviceConstant.TILE_HEIGHT* ypos+DeviceConstant.TILE_TOPEDGE_HEIGHT  , Graphics.LEFT | Graphics.TOP);
  
     	//if it is not the first row, redraw the edge
     	if (ypos>0) {
         	osg.drawImage(tileBottomEdge, leftOffset+ DeviceConstant.TILE_WIDTH* xpos,  DeviceConstant.TABLE_POSITION_V+ DeviceConstant.TILE_HEIGHT* ypos  , Graphics.LEFT | Graphics.TOP);
     	}
    }

    private void processInputNormal(int keyCode) {
    	//debug(0,"processInputNormal()");
        switch (keyCode) {    	        
	        case -3://case KEY_LEFT_ARROW:
	        	pointer=(pointer+1)% (player.concealedTiles.size()+1);
	            if (pointer==0 && player.newtile==null)
	            	pointer=1;
	            drawPointer();
	            break;
	        case -4://case KEY_RIGHT_ARROW:
	        	if (pointer==1 && player.newtile==null)
	                pointer+= player.concealedTiles.size();
	            else if (pointer==0)
	            	pointer+=player.concealedTiles.size()+1;
	            pointer--;
	            drawPointer();
	            break;	                
	        case -5://case KEY_SOFTKEY3:
	            inputMode=-1;
	            //discard the tile
	            //debug(0,"normalnode:center button pressed");
	            if (pointer==0) {
	            	Tile tile=new Tile(player.newtile.type, player.newtile.value);
	            	player.newtile=null;
	                drawArea8(0);
	                drawArea6(tile);
	                try {
	                    if (tileDiscardedPlayer!=null && this.speedDebug<=4) {
	                    	tileDiscardedPlayer.start();
	                    	if (playMode==0) {
		                    	Thread.sleep(150);
	                    	}
	                    }
	                }
	                catch (MediaException e){
	                }
	                catch (InterruptedException ie) {
	                }
	            	screenRepaint();
	            	engine.doDiscard(tile);
		            drawWaitArrow(1);
	            }
	            else {
	                Tile tile=player.getTiles().getTileAt(player.concealedTiles.size()-pointer);
	                //debug(0,"pointer !=0;;; receiving tile then remove");	                	
	                if(player.newtile!=null) {
                    	if (playMode==0) {
	                    	try {
	                    		Thread.sleep(350);	                    		
	                    	}
	                    	catch (InterruptedException ie) {
	                    	}
                    	}
		                player.receiveTile(player.newtile);
		                //debug(0,"newtile received");	                	
	                }
	                player.getTiles().removeTile(tile);
	                //debug(0,"tile removed");	                	
	                drawArea8(0);
	               
	                drawArea6(tile);
	                try {
	                    if (tileDiscardedPlayer!=null) {
	                    	tileDiscardedPlayer.start();
	                    }
	                }
	                catch (MediaException e){
	                }
	            	screenRepaint();
	                
	                player.newtile=null;	
	                engine.doDiscard(tile);
	                drawWaitArrow(1);
	            }
	            break;
	        default:
	            //debug(0,"nothing pressed");
        }//end case    	
    }
    
    /*
     * read the global variable selection level.
     */
    private void processInputChow(int keyCode) {
    	//debug(2,"processInputChow()");    		    	
    	//debug(0,"keypress.. currentSelection="+currentSelection + " ;maxselection="+maxNumberofSelection + " ;currentAction.size()="+ currentActions.size());    		
		
		switch (keyCode) {    
            case -3://case KEY_LEFT_ARROW:
                if (currentSelection>1)
                	currentSelection--;
                drawArea7a(selectionLevel,currentSelection, actions);

                if (selectionLevel==0) {
                	updateActionType(currentSelection-1);
                } 

                //for drawing selection effect
                if (selectionLevel==1) {
                	SpecialAction action=(SpecialAction) currentActions.elementAt(currentSelection-1);
                	drawSelectedTile2(action.startTilePosition,action.endTilePosition);
                }
                break;
            case -4://case KEY_RIGHT_ARROW:
                if (currentSelection<maxNumberofSelection)
                	currentSelection++;
                drawArea7a(selectionLevel,currentSelection, actions);

                if (selectionLevel==0) {
                	updateActionType(currentSelection-1);
                } 

                //for drawing selection effect
                if (selectionLevel==1 ) {
                	if (currentSelection<maxNumberofSelection) {
	                	SpecialAction action=(SpecialAction) currentActions.elementAt(currentSelection-1);
	                	drawSelectedTile2(action.startTilePosition,action.endTilePosition);
                	}
                	else {
                		drawArea8(0);
                		if (player.newtile!=null)
                			drawArea1();
                	}
                }
            	break;
                                
            case -5://case KEY_SOFTKEY3:
                //discard the tile
            	//debug(0,"inputmode5.center button pressed");
                redraw();
                //cancel button is not pressed
                if (currentSelection!=maxNumberofSelection) {
            		SpecialAction temp=(SpecialAction) currentActions.elementAt(currentSelection-1);
            		firstLevelAction=new SpecialAction(temp.type,temp.startTilePosition,temp.endTilePosition);

            		if (selectionLevel==0 && checkMultiple(firstLevelAction.type) ) {
            			//go to second level choice
                		selectionLevel=1;
                		//debug(0,"processInputChow()... firstlevelAction="+firstLevelAction.type);
                		drawArea7a(1,1, actions);
                		currentSelection=1;
	                	drawSelectedTile2(temp.startTilePosition,temp.endTilePosition);
                	}
                	else if (selectionLevel==1) {//second level selection
                		inputMode=-1;
                		player.newtile=null;
                		doChowHelper(currentSelection);
                        actions.removeElementAt(0);
                		engine.doSpecialResponse(GameAction.TYPE_CHOW,true, player.getWind());
                		onTurn();
                	}
                	else {//first level selection
                		inputMode=-1;
                		player.newtile=null;
                		doChowHelper(currentSelection);
                        actions.removeElementAt(0);
            			engine.doSpecialResponse(GameAction.TYPE_CHOW,true, player.getWind());
                		onTurn();
                	}
                }
                else {
	                inputMode=-1;
	                //debug(0,"You ignored the chow!!");
                	drawUnhighlight();
                    actions.removeElementAt(0);
                	engine.doSpecialResponse(GameAction.TYPE_CHOW,false,player.getWind());
                }
                	                
                break;
            default:
                //debug(2,"nothing pressed");
        }
    }
    
    
    /*
     * helper class for keyPressed() method
     * called when we respond for kong notice and its our turn
     */
    private void processInputKong(int keyCode) throws IOException {
    	//debug(2,"processInputKong().. currentSelection="+currentSelection + " ;maxselection="+maxNumberofSelection);    		

		Tile tempDiscard=null;
		
		switch (keyCode) {    
            case -3://case KEY_LEFT_ARROW:
                if (currentSelection>1)
                	currentSelection--;
                drawArea7a(selectionLevel,currentSelection, actions);

                if (selectionLevel==0) {
                	updateActionType(currentSelection-1);
                } 
                //for drawing selection effect
                if (selectionLevel==1) {
                	SpecialAction action=(SpecialAction) currentActions.elementAt(currentSelection-1);
                	if (!action.isVisible)
                		drawSelectedTile(action.startTilePosition,action.endTilePosition);
                	else
                		drawSelectedTile1b(action.startTilePosition,action.endTilePosition);
                }
                break;
            case -4://case KEY_RIGHT_ARROW:
                if (currentSelection<maxNumberofSelection)
                	currentSelection++;
                drawArea7a(selectionLevel,currentSelection, actions);

                if (selectionLevel==0) {
                	updateActionType(currentSelection-1);
                } 

                //for drawing selection effect
                if (selectionLevel==1 ) {
                	if (currentSelection<maxNumberofSelection) {
	                	SpecialAction action=(SpecialAction) currentActions.elementAt(currentSelection-1);
	                	if (!action.isVisible)
	                		drawSelectedTile(action.startTilePosition,action.endTilePosition);
	                	else
	                		drawSelectedTile1b(action.startTilePosition,action.endTilePosition);
                	}
                	else {
                		drawArea8(0);
                		if (player.newtile!=null)
                			drawArea1();
                	}
                }
            	break;
                
            case -5://case KEY_SOFTKEY3:
                //discard the tile
            	//debug(0,"inputmode3.center button pressed");
                redraw();
                
                //there may be multiple choice for kong possibilites
                //cancel button is not pressed
                if (currentSelection!=maxNumberofSelection) {

                	SpecialAction temp=(SpecialAction) currentActions.elementAt(currentSelection-1);
                	//debug(0,"tempproperties...starttile=" +temp.startTilePosition+";endtile="+temp.endTilePosition+";type="+temp.type+";isVisible="+temp.isVisible);
                	
                	//we need this as there may be multiple chow possiblities                	
                	if (selectionLevel==0 && maxNumberofSelection-1<actions.size()) {
                		//go to second level choice
                		selectionLevel=1;
                		firstLevelAction=new SpecialAction(temp.type,temp.startTilePosition,temp.endTilePosition);

                		drawArea7a(1,1, actions);
	                	if (!temp.isVisible)
	                		drawSelectedTile(temp.startTilePosition,temp.endTilePosition);
	                	else
	                		drawSelectedTile1b(temp.startTilePosition,temp.endTilePosition);
                	}
                	else if (selectionLevel==1) {
                		//do the kong
                		inputMode=-1;
                		//debug(0,"TEMP:::"+temp.isVisible);
	                	if (!temp.isVisible) {
	                		tempDiscard=doKongHelper(currentSelection);
	                	}
	                	else
	                		tempDiscard=doKongHelper1a(currentSelection,player.newtile);

                		engine.doConcealedKongResponse(true,tempDiscard);
                	}
                	
                	else {//only one selection left
                		inputMode=-1;
                		if (!temp.isVisible)
                			tempDiscard=doKongHelper(currentSelection);
                		else 
	                		tempDiscard=doKongHelper1a(currentSelection,player.newtile);                			
                		engine.doConcealedKongResponse(true,tempDiscard);
                	}
                }
                else {//cancel button is pressed
	                inputMode=1;
	                //debug(0,"You ignored the kong!!");

	                if (this.numTileOnDeck==0 || 
	                		(demoMode==Constant.TEST_CLEARHAND1 ||
	                				demoMode==Constant.TEST_CLEARHAND2 ||
									demoMode==Constant.TEST_CLEARHAND3 ||
									demoMode==Constant.TEST_CLEARHAND4  ) ){
	                
	                	engine.doDiscard(new Tile(1,1));//discard dummy tile.
	                }
	                else {
	                	drawPointer();
	                }
                }
                
                break;
            default:
            	//debug(0,"nothing pressed");
        }
    }
    
    private void processInputWin(int keyCode) {
    	//debug(2,"processInputWin()::" + "currentSelection="+currentSelection + " ;maxselection="+maxNumberofSelection + " ;player.discardedtile="+ player.discardedtile);    		    	

		switch (keyCode) {    
        	case -3: //case KEY_LEFT_ARROW:
	            if (currentSelection>1)
	            	currentSelection--;
	            drawArea7a(selectionLevel,currentSelection, actions);
            
	            if (selectionLevel==0) {
	            	updateActionType(currentSelection-1);
	            } 
	            break;
	        case -4://case KEY_RIGHT_ARROW:	
	            if (currentSelection<maxNumberofSelection)
	            	currentSelection++;
	            drawArea7a(selectionLevel,currentSelection, actions);
	
	            if (selectionLevel==0) {
	            	updateActionType(currentSelection-1);
	            } 
	            break;
	        case -5://case KEY_SOFTKEY3:
	        	redraw();
	            if (currentSelection!=maxNumberofSelection) {
	            	//debug(5,"You choose to win!!");
	    			inputMode=-1;
	            	doFinishHelper();
	            }
	        	else {
	                inputMode=1;
	        		if (ourTurn) {
	        			drawPointer();
	        		}
	        		else {
	        			//debug(5,"You ignored the win!!");
		            	
	        			////debug()
	        			if (!isRobbingKong)
		            		drawUnhighlight();
		
		                actions.removeElementAt(0);
		            	engine.doSpecialResponse(GameAction.TYPE_WIN,false,player.getWind());
	        		}
	            }
	            break;
		}//end switch    	
    }
    
    /*
     * helper class for keyPressed() method
     * called when we respond for kong/pong notice when its not our turn
     */
    private void processInputKong2(int keyCode) throws IOException{
    	//debug(2,"processInputKong2() " + "currentSelection="+currentSelection+ " ;maxselection="+maxNumberofSelection);    		    	

		switch (keyCode) {    
            case -3://case KEY_LEFT_ARROW:
                if (currentSelection>1)
                	currentSelection--;
                drawArea7a(selectionLevel,currentSelection, actions);

                if (selectionLevel==0) {
                	updateActionType(currentSelection-1);
                } 
                //for drawing selection effect
                if (selectionLevel==1) {
                	SpecialAction action=(SpecialAction) currentActions.elementAt(currentSelection-1);
                	
                	if (!action.isVisible)
                		drawSelectedTile(action.startTilePosition,action.endTilePosition);
                	else
                		drawSelectedTile1b(action.startTilePosition,action.endTilePosition);
                }
                break;
            case -4://case KEY_RIGHT_ARROW:
                if (currentSelection<maxNumberofSelection)
                	currentSelection++;
                drawArea7a(selectionLevel,currentSelection, actions);

                if (selectionLevel==0) {
                	updateActionType(currentSelection-1);
                } 

                //for drawing selection effect
                if (selectionLevel==1 ) {
                	if (currentSelection<maxNumberofSelection) {
	                	SpecialAction action=(SpecialAction) actions.elementAt(currentSelection-1);
	                	if (!action.isVisible)
	                		drawSelectedTile(action.startTilePosition,action.endTilePosition);
	                	else
	                		drawSelectedTile1b(action.startTilePosition,action.endTilePosition);
                	}
                	else {
                		drawArea8(0);
                		if (player.newtile!=null)
                			drawArea1();
                	}
                }
            	break;
            case -5://case KEY_SOFTKEY3:    
                //discard the tile
            	//debug(0,"inputmode4.center button pressed");
                redraw();
                
                //cancel button is not pressed
                if (currentSelection!=maxNumberofSelection) {
            		SpecialAction temp=(SpecialAction) currentActions.elementAt(currentSelection-1);
            		firstLevelAction=new SpecialAction(temp.type,temp.startTilePosition,temp.endTilePosition);

            		if (selectionLevel==0 && checkMultiple(firstLevelAction.type) ) {
            			//debug(0,"selectionlevel 0 and has lot of selection");
            			//go to second level choice
                		selectionLevel=1;
                		drawArea7a(1,1, actions);
	                	drawSelectedTile(temp.startTilePosition,temp.endTilePosition);
                	}
                	else if (selectionLevel==1) {//second level selection
                		//do the pong
                		inputMode=-1;
                		//debug(0,"selectionlevel 1");
                		player.newtile=null;
                		doKongHelper2(currentSelection);
                		//debug(0,"calling engine.doSpecialResponse. currentSelection="+currentSelection);
                    	SpecialAction action=(SpecialAction) actions.elementAt(currentSelection-1);

                		engine.doSpecialResponse(action.type,true, player.getWind());
                		onTurn();
                	}
                	
                	else {//first level selection
                		//debug(0,"selectionlevel 0");
                		inputMode=-1;
                		player.newtile=null;
                		doKongHelper2(currentSelection);
                		//debug(0,"calling engine.doSpecialResponse");
                		//22/10/2004
                		//use actions, not currentActions
                		//debug(0,"!!!!!!!!!!!!currentSelection="+currentSelection);
                    	SpecialAction action=(SpecialAction) actions.elementAt(currentSelection-1);

						engine.doSpecialResponse(action.type,true, player.getWind());
						if (action.type!=GameAction.TYPE_KONG ) {
							onTurn();
						}
                	}
                }
                else {
                	inputMode=-1;
                	//debug(0,"You ignored the kong!!");
                	drawUnhighlight();
                	drawWaitArrow(prevWaitArrowPos2);
                	engine.doSpecialResponse(GameAction.TYPE_KONG,false,player.getWind());
                }
                
                break;
            default:
            	//debug(0,"nothing pressed");
        }
    }

    
    public void onTurn() {
    	//debug(10,"onTurn()");
    	t_printOwnTiles(10);
    	startWaiting();
    	inputMode=1;
        drawPointer();
        //debug(2,"currentpointer="+pointer+ " ;prevpointer="+prevpointer);
    	lastInputTime=System.currentTimeMillis();
    }
    
    private void updateActionType(int selection) {
    	//debug(2,"updateActionType()::" +"player.discardedtile="+player.discardedtile);  
    	if (selection<actions.size()) {    	
    		SpecialAction action= (SpecialAction) actions.elementAt(selection);
    		//debug(0,"action.type="+action.type);
    		if (action.type==GameAction.TYPE_KONG||action.type==GameAction.TYPE_PONG) {
				if (player.discardedtile==null)
					inputMode=3;
				else
					inputMode=4;
				
				if (player.newtile!=null)
					inputMode=3;
				else
					inputMode=4;
			}
			else if (action.type==GameAction.TYPE_CHOW) 
				inputMode=5;
			else if (action.type==GameAction.TYPE_WIN)
				inputMode=6;
	    }
    	//debug(0,"**gameUI.updateActionType() finished.. discardedtile="+player.discardedtile);  
    }
    
    
    /*
     * checking vector of actions whether an actionType has more than one occurrence
     * 
     */
    private boolean checkMultiple(int actionType) {
    	int count=0;
    	for (int i=0;i<actions.size();i++) {
			if (actionType==((SpecialAction)actions.elementAt(i)).type ) {
				count++;
			}
			if (count==2)
				return true;
		}//end for
    	return false;
    }

    public int getPicture(int position) {
    	for (int i=0;i<4;i++) {
    		if (playerInfo[i].position==position) {
    			return playerInfo[i].picture;
    		}
    	}
    	return 0;
    }

    public String getNick(int position) {
    	for (int i=0;i<4;i++) {
    		if (playerInfo[i].position==position) {
    			return playerInfo[i].nick;
    		}
    	}
    	return "";
    }


    private void onClearHand2() {
    	//debug(2,"onClearHand2");
    	drawNumberofTile(0);
    	try {
    		Image clearHand=Image.createImage("/res/clear_hand.png");
        	osg.drawImage(clearHand, 
                    (swidth-clearHand.getWidth())/2  ,  (sheight-clearHand.getHeight())/2, Graphics.LEFT| Graphics.TOP);
    	}
    	catch (IOException ie) {
    	}
    	screenRepaint();
    	inputMode=8;//win input mode
    	lastInputTime=System.currentTimeMillis();
    	//debug(2,"onClearHand2 finished");
    }
    
    
    public void onClearHand() {
    	//debug(20,"onClearHand");
    	waitForEnter=false;
    	drawNumberofTile(0);
    	try {
    		Image clearHand=Image.createImage("/res/clear_hand.png");
        	osg.drawImage(clearHand, (swidth-clearHand.getWidth())/2  ,  (sheight-clearHand.getHeight())/2, Graphics.LEFT| Graphics.TOP);
    	}
    	catch (IOException ie) {
    	}
    	screenRepaint();
    	initNextRound();
    	
    	inputMode=8;//win input mode
    	screen=2;
    	nextGamePressed=false;
    	initNextRound();
    	startWaiting();
    	lastInputTime=System.currentTimeMillis();
    	//debug(2,"onClearHand finished");
    }

    public void onWin(int playerWind, TileCollection col1, TileCollection visibleTiles1, int double1, int point1,int total1,
    		TileCollection col2, TileCollection visibleTiles2, int double2, int point2,int total2,
			TileCollection col3, TileCollection visibleTiles3, int double3, int point3,int total3,
			TileCollection col4, TileCollection visibleTiles4, int double4, int point4,int total4) {

    	timerCounter=0;
    	
    	if (player.newtile!=null) {
    		try {
    			Thread.sleep(3000);
    		}
    		catch (InterruptedException e) {
    		}
    	}
    	
    	startAllHumanFlag=false;
    	//debug(20,"onWin::playerWind="+playerWind);
    	lastWinningPlayerWind=playerWind;
    	
    	//get the player with wind =east,south,west,north
    	for (int i=0;i<4;i++) {
        	if (playerInfo[i].position==0) {
        		playerInfo[i].score[currentRound]=total1;
        	}
        	if (playerInfo[i].position==1) {
        		playerInfo[i].score[currentRound]=total2;
        	}
        	if (playerInfo[i].position==2) {
        		playerInfo[i].score[currentRound]=total3;
        	}
        	if (playerInfo[i].position==3) {
        		playerInfo[i].score[currentRound]=total4;
        	}
    	}
    	String filename="";
    	
    	if (playerWind==player.getWind()) 
    		filename="win_bg.jpg";
    	else 
    		filename="lose_bg.jpg";

    	try {
    		if (playerWind<4) {
	        	osg.drawImage(Image.createImage("/res/"+ filename), 0,  0, Graphics.LEFT| Graphics.TOP);
	
	        	screenRepaint();
	        	Thread.sleep(3500);
	        	waitForEnter=false;
    		}
    		else if (waitForEnter){
    			startWaiting();
    			onClearHand2();
    		    this.playerWind=playerWind;
    			this.col1=col1;
    			this.visibleTiles1=visibleTiles1;
    			this.double1=double1;
    			this.point1=point1; 
    			this.total1=total1;
    		    this.col2=col2;
    			this.visibleTiles2=visibleTiles2; 
    			this.double2=double2;
    			this.point2=point2; 
    			this.total2=total2;
    			this.col3=col3; 
    			this.visibleTiles3=visibleTiles3;
    			this.double3=double3;
    			this.point3=point3;
    			this.total3=total3;
    			this.col4=col4;
    			this.visibleTiles4=visibleTiles4;
    			this.double4=double4;
    			this.point4=point4;
    			this.total4=total4;
    			return;
    		}

	    	osg.setColor(185,134,88);
	    	osg.fillRect(0,0,swidth,sheight);
	    	
	    	osg.drawImage(CanvasHelper.backgroundImage,0,0,Graphics.LEFT|Graphics.TOP);
	    	
	        osg.setFont(Font.getFont( Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN,Font.SIZE_SMALL));
	    	osg.setColor(255,255,255);
		    	
	    	drawFinishTile(col1,visibleTiles1,DeviceConstant.FINISHTILE_INITPOS_V);
	    	drawFace(getPicture(0),DeviceConstant.FINISHFACE_INITPOS_V);
	    	osg.drawString("E  D=" + (double1>0?"+":"")+double1 + "  P=" +  (point1>0?"+":"")+point1 + "  T="+ total1,DeviceConstant.FINISHFACE_INITPOS_H+2,DeviceConstant.FINISHTEXT_INITPOS_V,Graphics.LEFT| Graphics.TOP);
	    	screenRepaint();
	
	    	drawFinishTile(col2,visibleTiles2,DeviceConstant.FINISHTILE_INITPOS_V+DeviceConstant.FINISHTILE_SPACING_V);
	    	drawFace(getPicture(1),DeviceConstant.FINISHFACE_INITPOS_V+DeviceConstant.FINISHTILE_SPACING_V);
	    	osg.drawString("S  D="+ (double2>0?"+":"")+double2 + "  P=" +  (point2>0?"+":"")+point2 + "  T="+ total2,DeviceConstant.FINISHFACE_INITPOS_H+2,DeviceConstant.FINISHTEXT_INITPOS_V+DeviceConstant.FINISHTILE_SPACING_V,Graphics.LEFT| Graphics.TOP);
	    	screenRepaint();
	
	    	drawFinishTile(col3,visibleTiles3,DeviceConstant.FINISHTILE_INITPOS_V+DeviceConstant.FINISHTILE_SPACING_V*2);
	    	drawFace(getPicture(2),DeviceConstant.FINISHFACE_INITPOS_V+DeviceConstant.FINISHTILE_SPACING_V*2);
	    	osg.drawString("W  D="+ (double3>0?"+":"")+double3 + "  P=" +  (point3>0?"+":"")+point3 + "  T="+ total3,DeviceConstant.FINISHFACE_INITPOS_H+2,DeviceConstant.FINISHTEXT_INITPOS_V+DeviceConstant.FINISHTILE_SPACING_V*2,Graphics.LEFT| Graphics.TOP);
	    	screenRepaint();
	
	    	drawFinishTile(col4,visibleTiles4,DeviceConstant.FINISHTILE_INITPOS_V+DeviceConstant.FINISHTILE_SPACING_V*3);
	    	drawFace(getPicture(3),DeviceConstant.FINISHFACE_INITPOS_V+DeviceConstant.FINISHTILE_SPACING_V*3);
	    	osg.drawString("N  D="+ ( double4>0?"+":"")+double4 + "  P=" +  (point4>0?"+":"")+point4 + "  T="+ total4,DeviceConstant.FINISHFACE_INITPOS_H+2,DeviceConstant.FINISHTEXT_INITPOS_V+DeviceConstant.FINISHTILE_SPACING_V*3,Graphics.LEFT| Graphics.TOP);
	    	
	    	if (playerWind<4) {
	    		osg.drawImage(Image.createImage("/res/winhand.png"), 
	    				DeviceConstant.FINISH_WINDHAND_H,DeviceConstant.FINISHFACE_INITPOS_V+1 + playerWind*DeviceConstant.FINISHTILE_SPACING_V   , Graphics.LEFT| Graphics.TOP);
	    	}
	    	screenRepaint();
	
	    	if (speedDebug<6) {
	    		autoDebug=false;
	    	}
			this.col1=null;
			this.visibleTiles1=null;
		    this.col2=null;
			this.visibleTiles2=null; 
			this.col3=null; 
			this.visibleTiles3=null;
			this.col4=null;
			this.visibleTiles4=null;

	    	
	    	inputMode=8;//win input mode
	    	lastInputTime=System.currentTimeMillis();
    	
    	}
    	
        catch (InterruptedException e) {
            //debug(2,"thread is interrupted");
        }

    	catch (IOException ie) {
    	}
    	
    	screen=2;
    	nextGamePressed=false;
    	initNextRound();
    	startWaiting();
    	//debug(2,"onWin() finished");
    }

    private void drawFace(int pictureNum, int vpos) {
        osg.drawImage(CanvasHelper.getFace(pictureNum), DeviceConstant.FINISHFACE_INITPOS_H,vpos, Graphics.LEFT| Graphics.TOP);
    }
    
    private void drawFinishTile(TileCollection col, TileCollection vcol, int vpos) {
        //debug(2,"drawFinishTile()");

        int counter=1;
        int prevGroup=-1;
        Tile t=null;
        int j=0;//counter for drawing
        for(int i=0;i<vcol.size();i++) {
        	t=vcol.getTileAt(i);
        	if (prevGroup!=t.group) {
        		prevGroup=t.group;
        		counter=1;
        	}
        	
        	if (counter!=4) {
        		osg.drawImage(smallEdge, DeviceConstant.FINISHTILE_INITPOS_H+j*DeviceConstant.STILE_WIDTH,vpos+ DeviceConstant.TILE_HEIGHT-DeviceConstant.TILE_SMALLEDGE_HEIGHT, Graphics.LEFT|Graphics.TOP);
        		osg.drawImage(drawSmallTile(t, Tile.POSITION_BOTTOM), DeviceConstant.FINISHTILE_INITPOS_H+j*DeviceConstant.STILE_WIDTH,vpos-DeviceConstant.TILE_SMALLEDGE_HEIGHT, Graphics.LEFT|Graphics.TOP);
        	}
        	else { 
        		osg.drawImage(drawBlock(Tile.POSITION_BOTTOM), DeviceConstant.FINISHTILE_INITPOS_H+(j-2)*DeviceConstant.STILE_WIDTH,vpos, Graphics.LEFT|Graphics.TOP);
        		j--;
        	}
        	counter++;
        	j++;
        }
    	screenRepaint();
        
        for(int i=0;i<=col.size()-1;i++) {
            osg.drawImage(smallEdge, DeviceConstant.FINISHTILE_INITPOS_H+(15-col.size())*DeviceConstant.STILE_WIDTH + i*DeviceConstant.STILE_WIDTH ,  vpos+DeviceConstant.TILE_HEIGHT-DeviceConstant.TILE_SMALLEDGE_HEIGHT, Graphics.LEFT| Graphics.TOP);
            osg.drawImage(drawSmallTile(col.getTileAt(i), Tile.POSITION_BOTTOM  ), 
            		DeviceConstant.FINISHTILE_INITPOS_H+(15-col.size())*DeviceConstant.STILE_WIDTH + i*DeviceConstant.STILE_WIDTH ,  vpos-DeviceConstant.TILE_SMALLEDGE_HEIGHT, Graphics.LEFT| Graphics.TOP);                
    	}       
    	screenRepaint();
    }
    
    
    /*
     * read the global variable voice number
     */
    private void playSound(int p_pictureNum, int event) {
    	//debug(2,"playSound()");
    	if (speedDebug>4) {
    		return;
    	}
    	if (specialEventPlayer!=null) {
    		specialEventPlayer.close();
    		specialEventPlayer=null;
    	}
    	
    	String filename="";
    	//male voice
    	if (p_pictureNum>=5) {
    		if (event==GameAction.TYPE_CHOW) {
    			filename="/res/mchi.amr";
    		}
    		else if (event==GameAction.TYPE_KONG) {
    			filename="/res/mkon.amr";
    		}
    		else if (event==GameAction.TYPE_PONG) {
    			filename="/res/mpon.amr";
    		}    		
    	}
    	else {
    		if (event==GameAction.TYPE_CHOW) {
    			filename="/res/fchi.amr";
    		}
    		else if (event==GameAction.TYPE_KONG) {
    			filename="/res/fkon.amr";
    		}
    		else if (event==GameAction.TYPE_PONG) {
    			filename="/res/fpon.amr";
    		}
    	}
    	
    	try {
	    	specialEventStream=getClass().getResourceAsStream(filename);
	    	specialEventPlayer=Manager.createPlayer(specialEventStream,"audio/amr");
	    	specialEventPlayer.prefetch();
	    	
	    	if (specialEventPlayer!=null) {	    	
	        	specialEventVolControl= (VolumeControl) specialEventPlayer.getControl("VolumeControl");
	        	if (specialEventVolControl!=null) {
	        		specialEventVolControl.setLevel(realVolumeLevel);
	        	}
	    	}
	    	Thread.sleep(200);
	    	specialEventPlayer.start();
	    	Thread.sleep(600);

    	}
    	catch (MediaException me) {   		
        	//debug(2,"playsound mediaaexception");
    	}
    	catch (IOException io) {
    		//debug(2,"playsound ioex");
    	}
        catch (RuntimeException re) {
        	//debug(2,"playsound runtimeex");
        	
        }
        catch (InterruptedException re) {
        	
        }
    }
    
    private void initNextRound() {
    	//debug(2,"initNextRound()");
    	System.gc();
    	numberOfTile=0;
    	//changing player position:
    	//debug(10,"lastWinningPlayerWin="+lastWinningPlayerWind);
    	if (lastWinningPlayerWind!=0 && lastWinningPlayerWind!=-1) {
    		for (int i=0;i<4;i++) {
    			if (playerInfo[i].position==0) {
    				playerInfo[i].position=4;
    			}
    			playerInfo[i].position--;
			}    		
    	}
    	//nobody kong and nobody win
    	if (lastWinningPlayerWind==-1) {
    		if (currentRound!=0) {
    	    	for (int i=0;i<4;i++) {
    	        	playerInfo[i].score[currentRound]=playerInfo[i].score[currentRound-1];
    	    	}	        	
    		}
    		else {
    	    	for (int i=0;i<4;i++) {
    	        	playerInfo[i].score[currentRound]=2000;
    	    	}	        	
    		}
    	}

    	for(int i=0;i<4;i++) {
        	playerInfo[i].lastScore=playerInfo[i].score[currentRound];
    	}
    	
    	lastWinningPlayerWind=-1;
    	currentRound++;//for game info
    }
    
    
    private void redrawNewGame() {
    	//debug(2,"redrawNewGame()");
//    	System.out.println("redrawNewGame");
    	if (playMode==0) {
    		try {
        		if (initPlayer!=null) {
        			initPlayer.close();
        		}

    			InputStream is=getClass().getResourceAsStream("/res/shipai.amr");        	
        	  	initPlayer=Manager.createPlayer(is,"audio/amr");

    	    	if (initPlayer!=null) {
    	    		initPlayer.realize();
    	    	
    	        	VolumeControl volControl= (VolumeControl) initPlayer.getControl("VolumeControl");
//    	        	System.out.println("VolControl="+volControl);

    	        	if (volControl!=null) {
    	        		volControl.setLevel(realVolumeLevel);
//    	               	System.out.println("ReDrawNewGame::realVolumeLevel="+realVolumeLevel);    	
    	        	}
    	    	}
        		Thread.sleep(200);	    
//	        	System.out.println("RedrawNewGame. before play:realVolumeLevel="+realVolumeLevel);    	
            	initPlayer.start();    	
    		}
    		catch (IOException io) {}
    		catch (MediaException mo) {}
    		catch (InterruptedException ie) {}
    	}
    	
    	//for online mode... check if the onStarted is called or not
    	if (playMode==0) {
        	engine.nextGame();
    	}
    	else if (screen==2) {//for online mode
        	engine.nextGame();
    	}
    	if (!nextGamePressed) {
    		nextGamePressed=true;
    	}
    	
    	//debug(10,"try to call engine.nextgame()");
    }
    
    private void startGame() {
    	//debug(2,"startGame()");
    	osg.drawImage(discardedBuffer, 0,  0, Graphics.LEFT | Graphics.TOP);
    	screenRepaint();
    	engine.doConfirm();
    	//debug(2,"startGame() end");
    }
    
    private void simulateDelay() {
  	  	try {
  	    	screenRepaint();
  	  		Thread.sleep(10);
	  	}
	  	catch (InterruptedException ex) {    	  		
	  	}
    }


    /*
     * for used in network player, to send a quit message to server
     */	
    public void quitGame() {
    	engine.quitGame();
    	engine=null;
    }
    

    private void createTileDiscardedPlayer() {
    	if (tileDiscardedPlayer!=null) {
    		tileDiscardedPlayer.close();
    	}
    	if (specialEventPlayer!=null) {
    		specialEventPlayer.close();
    		specialEventPlayer=null;
    	}
    	
    	try {
	        tileDiscardedStream=getClass().getResourceAsStream("/res/pai.amr");   
    		tileDiscardedPlayer=Manager.createPlayer(tileDiscardedStream,"audio/amr");
    		tileDiscardedPlayer.prefetch();
	    	if (tileDiscardedPlayer!=null) {
	    		tileDiscardedVolControl= (VolumeControl) tileDiscardedPlayer.getControl("VolumeControl");
	        	
	    		if (tileDiscardedVolControl!=null)
	        		tileDiscardedVolControl.setLevel(realVolumeLevel);
	    	}
    	}
    	catch (MediaException e) {
    	}
    	catch (IOException e) {
    	}
    }
    
    public void onFinish() {
    	//debug(20,"onFinish()");
    	isCleanUp=true;
    	if (timer!=null) {
    		timer.cancel();
    		task.cancel();
    	}
    	midlet.commandReceived(Constant.COMMON_GAMEFINISH);
    }

    public void onFinish(String dateStr) {
    	//debug(20,"onFinish() network");
    	isCleanUp=true;
    	if (timer!=null) {
    		timer.cancel();
    		task.cancel();
    	}
    	midlet.commandReceived(Constant.COMMON_GAMEFINISH, dateStr);
    }

    public void drawBackgroundColor() {
    	//debug(20,"drawBackgroundColor()");
    	if (DeviceConstant.BG_COLOR==1) {
	    	osg.setColor(0,128,64);
	    	osg.fillRect(0,0,swidth,sheight);
	    	osg.setColor(0,0,0);
    	}
    	else {
	    	osg.setColor(86,55,44);
	    	osg.fillRect(0,0,swidth,sheight);
	    	osg.setColor(0,0,0);
    	}
		waitArrowBuffer=Common.extract(offScreenBuffer,swidth-DeviceConstant.SIDETILE_SIZE-DeviceConstant.WAITARROW_SIZE,(getHeight()- 13*DeviceConstant.SIDETILE_SIZE)/2 + 0*DeviceConstant.SIDETILE_SIZE,DeviceConstant.WAITARROW_SIZE,DeviceConstant.WAITARROW_SIZE);

    }
    
    public void reloadSetting() {
    	Setting.loadSetting();
    	pictureNum=Setting.picture;
    	playerInfo[0].picture=pictureNum;
    	
    	volumeLevel=Setting.sound;
    	
    	if (volumeLevel==0)
    		realVolumeLevel=0;
    	else if (volumeLevel==1)
    		realVolumeLevel=50;
    	else if (volumeLevel==2)
    		realVolumeLevel=80;
    	else if (volumeLevel==3)
    		realVolumeLevel=100;

		 
    	
    	try {
//    		if (initPlayer!=null) {
//    			initPlayer.close();
//    		}
//
//	    	InputStream is=getClass().getResourceAsStream("/res/shipai.amr");        	
//    	  	initPlayer=Manager.createPlayer(is,"audio/amr");
//	    	if (initPlayer!=null) {
//	    		initPlayer.realize();
//	        	VolumeControl volControl= (VolumeControl) initPlayer.getControl("VolumeControl");
//	        	if (volControl!=null) {
//	        		volControl.setLevel(realVolumeLevel);
//	               	System.out.println("ReloadSetting. realVolumeLevel="+realVolumeLevel);    	
//	        	}
//	    	}

    		createTileDiscardedPlayer();
	    	if (tileDiscardedPlayer!=null) {
	    		tileDiscardedVolControl= (VolumeControl) tileDiscardedPlayer.getControl("VolumeControl");
	        	
	    		if (tileDiscardedVolControl!=null)
	        		tileDiscardedVolControl.setLevel(realVolumeLevel);
	    	}
	    	
    	}
//    	catch (MediaException me){
//    	}
//    	catch (IOException ie){
//    	}
    	catch (RuntimeException re) {
        }

    }
        
    //release the memory here
    public void doCleanUp() {
    	isCleanUp=true;
    	if (timer!=null) {
    		timer.cancel();
    		task.cancel();
    		timer=null;
    		task=null;
    	}

    	if (tileDiscardedPlayer!=null) {
    		tileDiscardedPlayer.close();
    	}
    	if (initPlayer!=null) {
    		initPlayer.close();
    	}
    	if (specialEventPlayer!=null) {
    		specialEventPlayer.close();
    	}
    	
    	midlet=null;
    }
    
    public void onTableMessage(String message) {
    	//debug(5,"onTableMEssage");
    	midlet.commandReceived(Constant.NETWORKCOMMON_NOTICESCREEN, message);    	
    }

    class ForceActionTask extends TimerTask {
   	    //do the polling every second
   	    public void run() {
   	    	if (autoTimerEnabled && !isCleanUp) {
   	    		if (System.currentTimeMillis()-lastInputTime>500) {
   	    			timerCounter++;
   	    		}
   	    	}
   	    	
   	    	if (isCleanUp) {
   	    		return;
   	    	}
   	    	
   	    	if (timerCounter>Constant.N_USERWAITTIME) {
   	    		resetWaiting();	
   	    		keyPressed(-5);//-5=KEY_SOFTKEY3
   	    		keyPressed(-5);
   	    	}
   	    	//debuging
   	    	else if (autoDebug&&speedDebug>4) {
   	    		resetWaiting();	
   	    		keyPressed(-5);
   	    		keyPressed(-5);
   	    		keyPressed(-5);
   	    	}
   	    	
   	    }    
    }

    
    class AnimatedThread implements Runnable {
    	public void run() {
            
    		int animPicIndex=0;
            int timeCount=0;
    		int i=0;
            StringBuffer str= new StringBuffer();
    		str.append("Connecting  " );
            
            while (startAnim) {
    			try {
    				//debug(2,"try to draw animPIC...animPicIndex="+animPicIndex);
    				osg.drawImage(CanvasHelper.getAnimatedImage(animPicIndex),(swidth-DeviceConstant.ANIM_WIDTH)/2,(sheight-DeviceConstant.ANIM_HEIGHT), Graphics.LEFT | Graphics.TOP);					
    		    	repaint();
    		    	serviceRepaints();
    				Thread.sleep(500);
    				//timeCounter++;
    			}
    			catch (InterruptedException ie) {					
    			}
    			animPicIndex=(animPicIndex+1)%4;

    			if (timeCount==3) {
    				startAnim=false;
    			}
    			//debug(2,"timerCount.="+timeCount);
    			timeCount++;
    		}
    		//debug(2,"animation thread finished");
    	}//end run()
    }

    public void prepareBackground() {
    	if (!nextGamePressed) 
    		return;
    	
    	//debug(2,"prepareBackground()");
		osg.drawImage(CanvasHelper.dealingImage,0, DeviceConstant.DEALINGTEXT_POSITION_H , Graphics.LEFT| Graphics.TOP);            	
		osg.drawImage(CanvasHelper.getAnimatedImage(0),(swidth-DeviceConstant.ANIM_WIDTH)/2,(sheight-DeviceConstant.ANIM_HEIGHT), Graphics.LEFT | Graphics.TOP);					
    	screenRepaint();
    }

    //Accessed by inner class
    void debug(int level, String txt) {
    	if (level>=debugLevel) {
    		//System.out.println("GameUI."+txt);
    	}
    }    
    private void t_printOwnTiles(int x) {
    	//debug(x,"****SIZE: visibletiles=" + player.visibleTiles.size());
    	if (x>=debugLevel) {    	
	    	for(int i=0;i< player.visibleTiles.size();i++) {
	        	//System.out.print(  player.visibleTiles.getTileAt(i).type  +"_" +  player.visibleTiles.getTileAt(i).value + "_"+  player.visibleTiles.getTileAt(i).group+"_"+ player.visibleTiles.getTileAt(i).groupType+"  ");
	        }      	
	        //debug(x,"****SIZE: invisibletiles=" +  player.concealedTiles.size());
        	//System.out.println("");
	        for(int i=0;i< player.concealedTiles.size();i++) {
	        	//System.out.print("  ");
	        	//System.out.print(  player.concealedTiles.getTileAt(i).type  +"_" + player.concealedTiles.getTileAt(i).value );
	        }  
        	//System.out.println("****************");
    	}
    }

}


