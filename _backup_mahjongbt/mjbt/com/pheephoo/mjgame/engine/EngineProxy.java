/*
 * 
 * Proxy for remote EngineFA
 * 
 * @author Samuel
 * @version 1.00, 24/04/2005
 */
package com.pheephoo.mjgame.engine;

import com.pheephoo.mjgame.*;
import com.pheephoo.mjgame.messaging.SMSEngine;
import com.pheephoo.mjgame.messaging.SMSSendException;
import com.pheephoo.mjgame.network.*;
import com.pheephoo.mjgame.Constant;
import com.pheephoo.mjgame.ui.*;
import java.util.*;
import java.io.IOException;

public class EngineProxy implements EngineIF ,Runnable {

	
	private boolean hasDisplayMOTD=false;
    private boolean transactionFlag=false;//useless??? for PrivateNetwork playing
    private int tempposition=0;    //useless??? temporary position storage as we need to handle purchase screen
	//a flag that stop the polling while waiting for user response
	public boolean isWaitingPrvResponse=false;//for private network only


	public boolean isCompetitionRoom=false;
	
	private boolean hasPendingEvent=false  ;//for private network use only
	
	private int debugLevel=0;

	public MobileSocket client;

    private MJGame midlet;
	private GameListenerIF gameUI;
    private WinEngine wineng=new WinEngine();//for precalculation of next step for the game
    private PacketSenderThread packetSenderThread;//thread for sending data to server (invoke the MobileSocket)
	private PublicNetworkCanvas networkCanvas;
	private PrivateNetworkCanvas networkCanvasPrv;
    private int playMode=0;// 1=privatenetwork; 2=publicnetwork
    NetworkCommand networkCommand=new NetworkCommand();//data structure to encapsulate an unit of command send to server

    
	//keep alive and cleanup task
    boolean isCleanUp=false;//use by inner class
	private Timer     timer = new Timer();
    private TimerTask task = new KeepAliveTask();
    private Timer     timer2 = new Timer();
    private TimerTask task2 = new TimeOutTask();//cleantask	
    int keepAliveCount=0;//use by inner class
    private long lastUpdateTime=0;
	private final int EXPIRE_TIME = 20;  //in second

	//temporary storage
    private String motd="";

    //various flag
    private boolean networkStarted=false;//to prevent processing the nextGame() request in case the game has started
    private boolean isConfirmed=false;//to pause the screen for a while
    
    /*
     * if we send the message to server, this flag is set to false. upon receiving message, this flag is set to true
     * this variable is used in synchHelper().. if (isWaiting), wait till we received message from server
     */
    boolean isWaiting=false;//use by innerclass
	

    //for precalculation of next step to reduce the load from server
    private Player player;
    public Player players[]= new Player[4];			//reference to generic player. contains attribute common to all kind of player
    private int currentWind;
    private int minDouble=0;//to be used to determine winning condition
    private int maxDouble=0;//to be used to determine winning condition    
    private boolean isRobbingKong=false;
    private int turn=0;//for use in doDiscardHelper(), to preprocess the nextcommand 
    private Vector eventQueue=new Vector();
    private Tile receivedTile=null;//global variable
    private int tempturn=-1;//global variable
    private int numTileOnDeck=0;//global variable
    
   
    //registration,transaction & verification  related
    private int cid;
    private int activationCode;
    public String nick;
    public String msisdn;
    public String xid;
    
    
    
    /*
     * engine proxy is initiated with the nick dummy
     */
    public EngineProxy(MobileSocket _client, GameListenerIF _gameUI, int _cid, int _code, MJGame _midlet) {
   	    gameUI=_gameUI;
   		client = _client;
   		cid=_cid;
   		midlet=_midlet;
   		activationCode=_code;
   		client.registerListener(this);
   		packetSenderThread= new PacketSenderThread();
        packetSenderThread.start();
    }
   
   
    public synchronized void run() {
	   	//debug(0,"EngineProxy thread is started");
        timer.scheduleAtFixedRate(task, 0, Constant.N_KEEPALIVETIMER);
        timer2.scheduleAtFixedRate(task2,0,Constant.N_TIMEOUTTIMER);
        login();
    }

    private class PacketSenderThread extends Thread {
    	public synchronized void run() {
        	while (!isCleanUp) {
            	try {
               		//debug(100,"Prepare to sleep..");
            		this.wait();
    		   		isWaiting=true;
            		//debug(100,"Wake up for sending message");
            		
            		if (isCleanUp) {
            			break;
            		}
    		   		keepAliveCount=0;
    	   			client.invokeServer(networkCommand.type,networkCommand.data); 
            	}
            	catch(InterruptedException e) {
            	}
       	   	    catch (IOException e) {
       	    		sendErrorMessage();
       	   	    	e.printStackTrace();
       	   	    	//debug(2,"error on join");
       	   	    }
       	   	    catch (SecurityException se) {
       	   	    	se.printStackTrace();
       	   	    	midlet.handleSecurityException(Constant.SECURITY_EXCEPTION_NETWORK);
       	   	    }
            }

    	}
    	public synchronized void service() {
    		this.notify();
    	}
    }
    
    //send keep alive packet
    class KeepAliveTask extends TimerTask {
   	    public void run() {
   	    	doKeepAlive();
   	    }    
    	public void doKeepAlive() {
			if (isWaiting) {
				return;
			}
			keepAliveCount++;
			if (keepAliveCount>=Constant.N_KEEPALIVE) {
				//debug(10,"send keep alive");
	   			keepAliveCount=0;
	   			try {
	   				client.invokeServer(Constant.KEEP_ALIVE,null); 
	   			}
	   	   	    catch (IOException e) {
	   	    		sendErrorMessage();
	   	   	    	e.printStackTrace();
	   	   	    	//debug(2,"error on join");
	   	   	    }
			}
    	}
    }//end keepAliveTask

    class TimeOutTask extends TimerTask {
	    public void run() {
	    	//debug(0,"cleantask");
	    	if (isExpired()) {
	    			sendErrorMessage();
	    	}
	    }    
    }

    private void updateTime() {
    	//debug(20,"Lastupdatetime="+lastUpdateTime);
    	if (!transactionFlag) {
    		lastUpdateTime = System.currentTimeMillis();
    	}
    }
    
    boolean isExpired() {
    	//debug(2,"CurrentMillis="+System.currentTimeMillis() + " ;Lastupdatetime="+lastUpdateTime);
    	long expiredTime;
    	
    	if (transactionFlag) {
    		expiredTime=(EXPIRE_TIME+20)*1000;
    	}
    	else {
    		expiredTime=EXPIRE_TIME*1000;    		
    	}
    	
    	if(System.currentTimeMillis() - lastUpdateTime > expiredTime)
            return true;
        else 
            return false;
    }

    
    public void sendErrorMessage() {
    	//debug(2,"sendErrorMessage.communication eror.displaying notice screen");
    	if (timer!=null) {
			timer.cancel();
		}
		if (timer2!=null) {
			timer2.cancel();
		}
		CanvasHelper.stopNetworkAnimation();		
		isCleanUp=true;
		
		if (transactionFlag) {
	    	midlet.commandReceived(Constant.TRANSACTION_ERR1);			
		}
		else {
	    	midlet.commandReceived(Constant.NETWORKCOMMON_COMMUNICATIONERROR);
		}
		
    }

    //send client id & nick to server when we login
    //called by publicnetworkcanvas if the registration is successful
    public void login() {
        //debug(2,"engineProxy.login()");
        updateTime();
        hasPendingEvent=false;
     	synchHelper();
     	if (playMode==1) {
     		networkCommand.type=Constant.PRV_DOLOGIN;    		
     	}
     	else {
     		networkCommand.type=Constant.PUB_DOLOGIN;    		
     	}
     	networkCommand.data=new Object[]{ new  Integer(cid), new Integer(Constant.VERSION), new Integer(activationCode)};
     	needSend();        
    }
    
    //invoked by MobileSocket. call back method
    public synchronized void processMessage(Object returnval[]) {
	    isWaiting=false;
   		if (isCleanUp) {
   			return;
   		}
   		updateTime();
   		int command=( (Integer) returnval[0]).intValue() ;
   	    //debug(2,"processMessage() ::Command="+command);
   	    if (command==Constant.RESPONSE_OK) {
   	    	//debug(2,"RESPONSE_OK");
   	    	return;
   	    }

   	    int dataInt[]=(int []) returnval[1];
   	    String dataString[]=(String[]) returnval[2];
   	    
   	    if (command==Constant.RESPONSE_LOGIN_OK) {
   	    	//debug(2,"RESPONSE_LOGIN_OK");
   	    	transactionFlag=false;
   	    	if (returnval!=null) {
        		motd=dataString[0];
        		nick=dataString[1];
            	if (playMode==1) {
            		//networkCanvasPrv.onLogin(dataString[0],dataString[1] );
            		Setting.nick=nick;
            		getPendingEvent();
            	}
            	else {
            		this.getRoomCategoryHelper();
            	}
	    	}
   	    }
   	    else if (command==Constant.NETWORKCOMMON_RES_REGISTRATION) {
   	    	//debug(2,"NETWORKCOMMON_RES_REGISTRATION");
   	    	cid= dataInt[0];
   	    	activationCode=dataInt[1];
   	    	//debug(2,"onRegistration():: cid="+cid+" ;activationCode="+activationCode);
   	    	CanvasHelper.stopNetworkAnimation();
   	    	if (playMode==2) {
   	    		networkCanvas.onRegistration();
   	    	}
   	    }
   	    else if (command==Constant.NETWORKCOMMON_RES_REGISTRATIONSTARTED) {
   	    	//debug(2,"NETWORKCOMMON_RES_REGISTRATIONSTARTED");
   	    	transactionFlag=false;
   	    	if (playMode==2) {
   	    		networkCanvas.onRegistrationStarted();
   	    	}
   	    }
   	    else if (command==Constant.NETWORKCOMMON_RES_REGISTRATIONSUCCESSFUL) {
   	    	//debug(2,"NETWORKCOMMON_RES_REGISTRATIONSUCCESSFUL");
   	    	nick= dataString[0];
   	    	msisdn=dataString[1];
   	    	if (playMode==2) {
   	    		networkCanvas.onRegistrationSuccess();
   	    	}
   	    }
   	    else if (command==Constant.NETWORKCOMMON_RES_DUPLICATENICK) {
   	    	//debug(2,"NETWORKCOMMON_RES_DUPLICATENICK");
   	    	if (playMode==2) {
   	    		networkCanvas.onRegistrationDuplicateNick();
   	    	}
   	    }
        else if(command==Constant.PUB_ONPENDINGEVENT) {
        	//debug(2,"PUB_ONPENDINGEVENT");        	
        	hasPendingEvent=true;
        	onPendingEventHelper(returnval);        
        	//debug(2,"PUB_ONPENDINGEVENT...finished");        	
        	hasDisplayMOTD=true;
        }
   	    
   	    else if (command==Constant.PUB_RESPONSE_ROOMCATEGORY) {
   	    	//debug(2,"PUB_RESPONSE_ROOMCATEGORY");
   	    	this.onResponseRoomCategory(returnval);
   	    }
   	    else if (command==Constant.PUB_RESPONSE_ROOMLIST) {
   	    	//initial room list from the server
   	    	//debug(2,"PUB_RESPONSE_ROOMLIST");
   	    	
   	    	CanvasHelper.stopNetworkAnimation();
   	    	Vector publicRooms=onRoomListHelper(returnval);
    		midlet.getDisplay().setCurrent(networkCanvas);
    		networkCanvas.onResponsePublicRoomList(publicRooms);
   	    }
   	    else if (command==Constant.PUB_RESPONSE_ROOMLISTUPDATE) {
   	    	//debug(40,"PUB_RESPONSE_ROOMLISTUPDATE");
   	    	onRoomListUpdateHelper(returnval);
   	    }
   	    else if (command==Constant.ON_ROOMCREATED) {
   	    	//debug(2,"ON_ROOMCREATED");
            //debug(2,"table number=" + dataInt[0]);
            if (playMode==1) {
                networkCanvasPrv.onResponseCreateRoom(dataInt[0]);            	
            }
            else {
                networkCanvas.onResponseCreateRoom(dataInt[0]);
            }
	    }
   	    else if (command==Constant.ON_MESSAGETABLE) {
   	    	//debug(2,"ON_MESSAGETABLE");
   			gameUI.onTableMessage(dataString[0]);
   	    }
   	    else if (command==Constant.RESPONSE_ONNETWORKSTART) {
   	    	
   	    	//debug(2,"RESPONSE_ONNETWORKSTART");
   	    	networkStarted=true;
   	    	isConfirmed=false;
   	    	onNetworkStartHelper(returnval);
   	    }
        else if ( command==Constant.ON_OTHERJOIN) {
   	    	//debug(2,"ON_OTHERJOIN");
            //debug(0,"data0="+dataInt[0]+" ;data1="+dataInt[1]+ " ;nick="+dataString[0]);
            gameUI.onOtherJoin(dataInt[0],0,dataInt[1],dataString[0]);//parameter 2 (number of player) is not used
        }
        else if(command==Constant.ON_JOININPROGRESS) {
            //debug(2,"ON_JOININPROGRESS"); 
            onJoinInProgressHelper(returnval);
        }   
        else if (command==Constant.ON_OTHERJOININPROGRESS) {
            //debug(2,"ON_OTHERJOININPROGRESS"); 
        	onOtherJoinInProgressHelper(returnval);
        }
        else if (command==Constant.ON_OTHERLEAVEINPROGRESS) {
            //debug(2,"ON_OTHERLEAVEINPROGRESS"); 
        	onOtherLeaveInProgressHelper(returnval);
        }
        //game playing message
        else if(command==Constant.ON_TILERECEIVED) {
            //debug(2,"ON_TILERECEIVED");
            receivedTile = new Tile(dataInt[0], dataInt[1]);//read from global variable
            tempturn=dataInt[3];
            numTileOnDeck = dataInt[2];//read from global variable
            //debug(0,"::numTileOnDeck="+numTileOnDeck+ " ;New Tile="+receivedTile+ " ;turn="+tempturn);
            
        	if (tempturn!=player.getWind()) {            
	    		players[getPlayNum(tempturn)].concealedTiles.addTile(receivedTile);
	    		receivedTile=null;
	    		//debug(2,"setting received tile to null");
        	}        
        }
        else if(command==Constant.ON_TURN) {
        	//debug(2,"ON_TURN");
        	waitConfirmHelper();
    		networkStarted=false;
        	
        	//debug(0,"tempturn="+tempturn+ " ;player.getWind()"+player.getWind());
        	if (tempturn==player.getWind()) {
	            if (receivedTile!=null)
	            	gameUI.onTileReceived(receivedTile,numTileOnDeck,tempturn);
	            gameUI.onTurn();
	            //this.t_printTile(20);
        	}
        	else {
        		players[getPlayNum(tempturn)].concealedTiles.addTile(receivedTile);
        	}
            receivedTile=null;
        }
        
        else if(command==Constant.ON_TILEDISCARDED) {
        	//debug(2,"ON_TILEDISCARDED");
        	waitConfirmHelper();
            Tile tile = new Tile(dataInt[0], dataInt[1]);
            
            int p_turn=dataInt[2];
            //debug(20,"onTilediscarded:: turn="+p_turn+ " ;player.getWind()="+player.getWind());
            if (p_turn!=player.getWind()) {            
            	players[getPlayNum(p_turn)].concealedTiles.removeTile(tile);
            }
        	gameUI.onTileDiscarded(tile,dataInt[2],dataInt[3]);
        }
        else if(command==Constant.ON_CLEARHAND) {
        	//debug(2,"ON_CLEARHAND");
        	gameUI.onClearHand();
        }
        else if(command==Constant.ON_WIN) {
        	//debug(2,"ON_WIN");
        	onWinHelper(returnval);
        }
        else if(command==Constant.ON_FINISH) {
        	//debug(2,"ON_FINISH");
        	gameUI.onFinish(dataString[0]);
        }
        else if(command==Constant.PRV_ONINVITE) {
        	//debug(2,"PRV_ONINVITE");        	
        	onInviteHelper(returnval);
        }
        else if(command==Constant.PRV_ONINVITEANSWERED) {//this thing is also used to send a notice to user if a table is closed
        	//debug(2,"PRV_ONINVITEANSWERED");        	
        	onInviteAnsweredHelper(returnval);        	
        }
        else if(command==Constant.PRV_ONPENDINGEVENT) {
        	//debug(2,"PRV_ONPENDINGEVENT");        	
        	hasPendingEvent=true;
        	onPendingEventHelper(returnval);        
        	hasDisplayMOTD=true;
        }
        else if (command==Constant.PRV_RESPONSE_CONTACTLIST) {
            //debug(2,"PRV_RESPONSE_CONTACTLIST"); 
   	    	CanvasHelper.stopNetworkAnimation();
   	    	Vector contacts=constructContactVector(returnval);

   	    	
   	    	if (hasPendingEvent) {
	   	    	midlet.getDisplay().setCurrent(networkCanvasPrv);
	        	networkCanvasPrv.onResponseContactList(contacts);
   	    	}
   	    	else {
	        	networkCanvasPrv.onResponseContactList(contacts);
   	    		midlet.lastForm=networkCanvasPrv;
   	    		networkCanvasPrv.onLogin(motd,nick);
   	    		hasDisplayMOTD=true;

   	    	}
        }
   	    else if (command==Constant.PRV_RESPONSE_CONTACTLISTUPDATE) {
   	    	//debug(40,"PRV_RESPONSE_CONTACTLISTUPDATE");
   	    	onContactListUpdateHelper(returnval);
   	    }
   	    else if (command==Constant.PRV_RESPONSE_CONTACTLISTADDDELETE) {
   	    	//debug(40,"PRV_RESPONSE_CONTACTLISTADDDELETE");
   	    	int mode=dataInt[0];
   	    	int cid=dataInt[1];
   	    	String nick=dataString[0];
   	    	networkCanvasPrv.onResponseContactListAddDelete(mode,cid,nick);
   	    }

        else if(command==Constant.ON_NOTICE) {
        	//debug(2,"ON_NOTICE");
        	waitConfirmHelper();

            Vector actions=new Vector();
        	for (int i=0;i<dataInt.length;) {
        		SpecialAction action;
				if (dataInt[i+3]==1)        		
					action= new SpecialAction(dataInt[i],dataInt[i+1],dataInt[i+2], true); 
				else
					action= new SpecialAction(dataInt[i],dataInt[i+1],dataInt[i+2], false); 					
				actions.addElement(action);
				i=i+4;            
        	}

        	//debug(2,"receivedTile="+receivedTile);
        	if (receivedTile!=null) {
            	//debug(2,"receivedTile not null");
            	gameUI.onTileReceived(receivedTile,numTileOnDeck,player.getWind());
        	}
            receivedTile=null;
            gameUI.onNotice(actions);
        }
        else if(command==Constant.ON_NOTICE2) {
        	//debug(2,"ON_NOTICE2");
        	waitConfirmHelper();

            Vector actions=new Vector();
        	for (int i=0;i<dataInt.length;) {
        		SpecialAction action;
				if (dataInt[i+3]==1)        		
					action= new SpecialAction(dataInt[i],dataInt[i+1],dataInt[i+2], true); 
				else
					action= new SpecialAction(dataInt[i],dataInt[i+1],dataInt[i+2], false); 					
				actions.addElement(action);
				i=i+4;            
        	}
            gameUI.onNotice2(actions);
        }
        else if(command==Constant.ON_TILETAKEN) {
        	//debug(2,"ON_TILETAKEN");
        	onTileTakenHelper(returnval);
        }//end else if

        else if (command==Constant.NETWORKCOMMON_TRANSPUB) {
        	//debug(2,"NETWORKCOMMON_TRANSPUB");
        	CanvasHelper.stopNetworkAnimation();
        	activationCode=dataInt[1];
        	xid=dataString[0];
        	
        	if (playMode==1) {
        		networkCanvasPrv.onTransaction(dataInt[0]);
        	}
        	else {
            	networkCanvas.onTransaction(dataInt[0]);
        	}
        }
        else if (command==Constant.NETWORKCOMMON_NEEDUPDATE) {
        	//debug(2,"NETWORKCOMMON_NEEDUPDATE");
        	CanvasHelper.stopNetworkAnimation();
        	midlet.commandReceived(Constant.MISC_NEEDUPDATE,dataString[0]);
        }
        
        
        //debug(2,"finished processmessage");
    }
   
    public void doDiscard(Tile tile) {
   		//debug(2,"doDiscard()");
   		if (isCleanUp) {
   			return;
   		}
		synchHelper();
    	networkCommand.type=Constant.DO_DISCARD;
    	//int 0=no special action; 1=has special action
    	int hasSpecialAction=doDiscardHelper(tile);//this flag is to speed up the process at EngineFA if there is no specialAction
    	Object data[];
    	int i=0;
		data= new Object[3];
    	data[i++]=new Integer (tile.type);
    	data[i++]=new Integer (tile.value);
    	data[i++]=new Integer (hasSpecialAction);
    	networkCommand.data=data;
        needSend();
    }
   
    public void doSpecialResponse(int type,boolean response, int wind){
   		//debug(2,"doSpecialResponse()");
    	//debug(0,"visibleTile size="+player.visibleTiles.size() + " ;concealedTile size="+player.concealedTiles.size());
    	int size = player.visibleTiles.size();
    	int tilesdata[]= new int[size*4];
    	int concealedSize=player.concealedTiles.size();
    	int tilesdataConcealed[]= new int[concealedSize*2];
    	int pos=0;
    	//debug(2,"pos length="+tilesdata.length);
    	
    	for (int i=0;i<size;i++) {
    		Tile tile=   player.visibleTiles.getTileAt(i);
    		tilesdata[pos++]= tile.type;
    		tilesdata[pos++]= tile.value;
    		tilesdata[pos++]= tile.group;
    		tilesdata[pos++]= tile.groupType;   	    			
    	}
    	pos=0;
    	for (int i=0;i<concealedSize;i++) {
    		Tile tile=   player.concealedTiles.getTileAt(i);
    		tilesdataConcealed[pos++]= tile.type;
    		tilesdataConcealed[pos++]= tile.value;
    	}
    	//debug(2,"finished writing out the tile");
		synchHelper();
		networkCommand.type=Constant.DO_SPECIALRESPONSE;
    	int responseInt=0;
    	if (response)
    		responseInt=1;
    	networkCommand.data=new Object[]{ 
    			 new Integer(size*4), tilesdata, new Integer(player.concealedTiles.size() *2), tilesdataConcealed ,    new Integer(type), new Integer(responseInt), new Integer (wind) };
        needSend();   	
    }

    public void doConcealedKongResponse(boolean response, Tile tempTile){
	    //debug(2,"doConcealedKongResponse");
    	//debug(0,"visibleTile size="+player.visibleTiles.size()+ " ;concealedTile size="+player.concealedTiles.size());
    	int size = player.visibleTiles.size();
    	int tilesdata[]= new int[size*4];
    	int concealedSize=player.concealedTiles.size();
    	int tilesdataConcealed[]= new int[concealedSize*2];
    	int pos=0;
    	for (int i=0;i<size;i++) {
    		Tile tile=   player.visibleTiles.getTileAt(i);
    		tilesdata[pos++]= tile.type;
    		tilesdata[pos++]= tile.value;
    		tilesdata[pos++]= tile.group;
    		tilesdata[pos++]= tile.groupType;   	    			
    	}
    	pos=0;
    	for (int i=0;i<concealedSize;i++) {
    		Tile tile=   player.concealedTiles.getTileAt(i);
    		tilesdataConcealed[pos++]= tile.type;
    		tilesdataConcealed[pos++]= tile.value;
    	}
		synchHelper();
    	int responseInt=0;
    	if (response)
    		responseInt=1;

		networkCommand.type=Constant.DO_CONCEALEDKONGRESPONSE;
    	networkCommand.data=new Object[]{ 
    			  new Integer(size*4), tilesdata, new Integer(concealedSize*2), tilesdataConcealed, new Integer(responseInt),
    			new Integer(tempTile.type), new Integer (tempTile.value) };
        needSend();
    }
   
    public void doWinResponse(int p_turn){
	    //debug(2,"doWinResponse");
		synchHelper();
    	networkCommand.type=Constant.DO_WINRESPONSE;
    	networkCommand.data=new Object[]{ new Integer(p_turn) };
        needSend();
    }

    private void onNetworkStartHelper(Object returnval[]) {
		//debug(2,"onNetworkStartHelper() start");
		int data[]= (int []) returnval[1];
		Player tmpPlayer=null;			

		int j=0;
		int transaction=data[j++];
		////debug(0,"transaction="+transaction);//reserved for transaction data
		int wind=data[j++];
		currentWind=data[j++];
		minDouble=data[j++];
		maxDouble=data[j++];
		player= new Player(wind);
		//debug(2,"onNetworkStartHelper()..... PlayerWind="+player.getWind());
		
		player.concealedTiles=new TileCollection(TileCollection.MAXCONCEALEDSIZE);		
		player.concealedTiles.batchLoad(data,j,j+26);//2=startpos
		player.visibleTiles=new TileCollection(TileCollection.MAXVISIBLESIZE);	
		players[0]=player;
		
		j=j+26;
		tmpPlayer= new Player(data[j++]);
		tmpPlayer.concealedTiles=new TileCollection(TileCollection.MAXCONCEALEDSIZE);
		tmpPlayer.concealedTiles.batchLoad(data,j,j+26);
		tmpPlayer.visibleTiles=new TileCollection(TileCollection.MAXVISIBLESIZE);	
		players[1]=tmpPlayer;
		////debug(10,"onNetworkStartHelper.players[1].wind="+players[1].getWind());
		
		j=j+26;
		tmpPlayer= new Player(data[j++]);
		tmpPlayer.concealedTiles=new TileCollection(TileCollection.MAXCONCEALEDSIZE);
		tmpPlayer.concealedTiles.batchLoad(data,j,j+26);
		tmpPlayer.visibleTiles=new TileCollection(TileCollection.MAXVISIBLESIZE);	
		players[2]=tmpPlayer;
		////debug(10,"onNetworkStartHelper.players[2].wind="+players[2].getWind());
		
		j=j+26;
		tmpPlayer= new Player(data[j++]);
		tmpPlayer.concealedTiles=new TileCollection(TileCollection.MAXCONCEALEDSIZE);
		tmpPlayer.concealedTiles.batchLoad(data,j,j+26);
		tmpPlayer.visibleTiles=new TileCollection(TileCollection.MAXVISIBLESIZE);	
		players[3]=tmpPlayer;
		////debug(10,"onNetworkStartHelper.players[3].wind="+players[3].getWind());
		
		if (playMode==1) {
			networkCanvasPrv.onResponseStart();
		}
		else {
			networkCanvas.onResponseStart();		
			//pending
			//networkCanvas.onResponseAskTransaction();
		}
		//move the following thing to another method
		//after a transaction is confirmed
		//pending
		gameUI.onJoin( 0, wind, 1,this,player,0 );
		gameUI.onStart(player.concealedTiles, player.visibleTiles );
		
		this.updateTime();
        //this.t_printTile(20);
    }//end on networkStart()

    
    
    private void onTileTakenHelper(Object returnval[]) {
   	    int dataInt[]=(int []) returnval[1];
    	TileCollection visibleTiles=new TileCollection(TileCollection.MAXVISIBLESIZE);
        int i=1;
        int visibleSize=dataInt[0];
        //debug(0,"visibleSize="+ visibleSize);
        while(i<visibleSize) {
        	Tile tile= new Tile(dataInt[i],dataInt[i+1],dataInt[i+2],dataInt[i+3]);
        	////debug(0,"Tile = "+ tile.type + "_" + tile.value);
        	visibleTiles.addTileNoSorted(tile);
        	i=i+4;
        }
        int otherSize=dataInt[i];
        i++;
        int wind=dataInt[i];
        //debug(2,"wind="+wind);
        int index=getPlayNum(wind);
        i++;
        boolean isDiscarded=false;
        if (dataInt[i]==1) {
        	isDiscarded=true;
        }
        i++;
        
        Tile tile=null;
        if (dataInt[i]!=-1) {
        	tile=new Tile(dataInt[i],dataInt[i+1]);
        }
        i=i+2;

        //updating other player concealed tile
        int colSize=dataInt[i++];
        players[index].concealedTiles=new TileCollection(14);
        for (int j=0;j<colSize;j++) {
        	////debug(2,"creating tile");
        	tile= new Tile(dataInt[i],dataInt[i+1]);
        	////debug(2,"Tile = "+ tile.type + "_" + tile.value);
        	players[index].concealedTiles.addTileNoSorted(tile);
        	i=i+2;
        }
        players[index].visibleTiles=visibleTiles;

        ////debug(20,"onTileTaken:::visibleSize="+ players[index].visibleTiles.size() +" concealedsize="+ players[index].concealedTiles.size());
        ////debug(2,"before calling gameUI.onTileTaken");
        gameUI.onTileTaken(visibleTiles,otherSize, wind,isDiscarded,tile);
        ////debug(2,"EngineProxy: finished on tile taken ");
    }

    private void onWinHelper(Object returnval[]) {
   		//debug(2,"onWinHelper()");
		int array1[]= (int []) returnval[1];
	    int j=0;
	    int playerwind=array1[j++];
	    ////debug(2,"playerwind="+ playerwind);
	    //player 1
	    TileCollection col1=new TileCollection(14);
	    TileCollection visibleTiles1=new TileCollection(16);            
	    int size1a=array1[j++];            
	    for (int i=0;i<size1a;i++) {
	    	Tile tile= new Tile(array1[j],array1[j+1],array1[j+2],array1[j+3]);
	    	////debug(2,"Tile = "+ tile.type + "_" + tile.value);
	    	col1.addTileNoSorted(tile);
	    	j=j+4;
	    }
	    int size1b=array1[j++];
	    for (int i=0;i<size1b;i++) {
	    	Tile tile= new Tile(array1[j],array1[j+1],array1[j+2],array1[j+3]);
	    	////debug(2,"Tile = "+ tile.type + "_" + tile.value);
	    	visibleTiles1.addTileNoSorted(tile);
	    	j=j+4;
	    }
	    int double1=array1[j++];
	    int point1=array1[j++];
	    int total1=array1[j++];
	    
	    //player 2
	    TileCollection col2=new TileCollection(TileCollection.MAXCONCEALEDSIZE);
	    TileCollection visibleTiles2=new TileCollection(TileCollection.MAXVISIBLESIZE);            
	    int size2a=array1[j++];            
	    for (int i=0;i<size2a;i++) {
	    	Tile tile= new Tile(array1[j],array1[j+1],array1[j+2],array1[j+3]);
	    	////debug(2,"Tile = "+ tile.type + "_" + tile.value);
	    	col2.addTileNoSorted(tile);
	    	j=j+4;
	    }
	    int size2b=array1[j++];
	    for (int i=0;i<size2b;i++) {
	    	Tile tile= new Tile(array1[j],array1[j+1],array1[j+2],array1[j+3]);
	    	////debug(2,"Tile = "+ tile.type + "_" + tile.value);
	    	visibleTiles2.addTileNoSorted(tile);
	    	j=j+4;
	    }
	    int double2=array1[j++];
	    int point2=array1[j++];
	    int total2=array1[j++];
	
	    //player 3
	    TileCollection col3=new TileCollection(TileCollection.MAXCONCEALEDSIZE);
	    TileCollection visibleTiles3=new TileCollection(TileCollection.MAXVISIBLESIZE);            
	    int size3a=array1[j++];            
	    for (int i=0;i<size3a;i++) {
	    	Tile tile= new Tile(array1[j],array1[j+1],array1[j+2],array1[j+3]);
	    	////debug(2,"Tile = "+ tile.type + "_" + tile.value);
	    	col3.addTileNoSorted(tile);
	    	j=j+4;
	    }
	    int size3b=array1[j++];
	    for (int i=0;i<size3b;i++) {
	    	Tile tile= new Tile(array1[j],array1[j+1],array1[j+2],array1[j+3]);
	    	////debug(2,"Tile = "+ tile.type + "_" + tile.value);
	    	visibleTiles3.addTileNoSorted(tile);
	    	j=j+4;
	    }
	    int double3=array1[j++];
	    int point3=array1[j++];
	    int total3=array1[j++];
	    
	    //player 4
	    TileCollection col4=new TileCollection(TileCollection.MAXCONCEALEDSIZE);
	    TileCollection visibleTiles4=new TileCollection(TileCollection.MAXVISIBLESIZE);            
	    int size4a=array1[j++];            
	    for (int i=0;i<size4a;i++) {
	    	Tile tile= new Tile(array1[j],array1[j+1],array1[j+2],array1[j+3]);
	    	////debug(2,"Tile = "+ tile.type + "_" + tile.value);
	    	col4.addTileNoSorted(tile);
	    	j=j+4;
	    }
	    int size4b=array1[j++];
	    for (int i=0;i<size4b;i++) {
	    	Tile tile= new Tile(array1[j],array1[j+1],array1[j+2],array1[j+3]);
	    	////debug(2,"Tile = "+ tile.type + "_" + tile.value);
	    	visibleTiles4.addTileNoSorted(tile);
	    	j=j+4;
	    }
	    int double4=array1[j++];
	    int point4=array1[j++];
	    int total4=array1[j++];
	    ////debug(2,"before calling gameUI.onWin");
	    gameUI.onWin(playerwind,col1,visibleTiles1,double1,point1,total1,
	    		col2,visibleTiles2,double2,point2,total2,
	    		col3,visibleTiles3,double3,point3,total3,
	    		col4,visibleTiles4,double4,point4,total4);
	    //debug(2,"onWinHelper() finished ");
    }// end onWinHelper
    
    private void onOtherJoinInProgressHelper(Object returnval[]) {
		//debug(2,"onOtherJoinInProgressHelper()");
   		int array1[]= (int []) returnval[1];
		String array2[] = (String[])returnval[2];
		gameUI.onOtherJoinInProgress(array1[0],array1[1],array2[0]);
    }
   
    private void onOtherLeaveInProgressHelper(Object returnval[]) {
   		//debug(2,"onOtherLeaveInProgressHelper()");
		int array1[]= (int []) returnval[1];
		String array2[] = (String[])returnval[2];
		gameUI.onOtherLeaveInProgress(array1[0],array1[1],array2[0],array2[1]);
   	}

    private void onJoinInProgressHelper(Object returnval[]) {
   		//debug(2,"onJoinInProgressHelper()");
   		int array1[]= (int []) returnval[1];
		String array2[] = (String[])returnval[2];
   		int j=0;
    	
		String nick1=array2[0];
		String nick2=array2[1];
		String nick3=array2[2];
		
		currentWind=array1[j++];
		minDouble=array1[j++];
		maxDouble=array1[j++];

		//reuseable variable
		int pictureNum;
		int wind;
		int size;
		int point;
		Tile tile;
		Player tmpPlayer;

		//debug(2,"onJoinInProgress::getting tile on table");
		TileCollection tilesOnTable=new TileCollection(TileCollection.MAXTILESONTABLE);
		//getting tile on the table
		int tilesOnTableSize=array1[j++];
		for(int i=0;i<tilesOnTableSize;i++) {
			int type=array1[j++];
			int value=array1[j++];
			tile= new Tile(type,value);
			//System.out.print("  " + tile );
			tilesOnTable.addTileNoSorted(tile);
		}
		//debug(2,"onJoinInProgress::getting own information");
		wind=array1[j++];
		point=array1[j++];
		size=array1[j++];
		//debug(2,"onJoinInProgress:: wind="+ wind+ " ;point="+point+ " ;size="+size);
		player= new Player(wind);
		player.totalPoint=point;
		player.concealedTiles=new TileCollection(TileCollection.MAXCONCEALEDSIZE);		
		player.visibleTiles=new TileCollection(TileCollection.MAXVISIBLESIZE);	
		players[0]=player;
		
		for(int i=0;i<size;i++) {
			int type=array1[j++];
			int value=array1[j++];
			tile= new Tile(type,value);
			//System.out.print("  " + tile );
			player.concealedTiles.addTileNoSorted(tile);
		}		
		//debug(2,"onJoinInProgress::finished getting own concealedtile");
		size=array1[j++];
		for(int i=0;i<size;i++) {
			int type=array1[j++];
			int value=array1[j++];
			int group=array1[j++];
			int groupType=array1[j++];			
			tile= new Tile(type,value,group,groupType);
			//System.out.print("  " + tile );
			player.visibleTiles.addTileNoSorted(tile);
		}		
		//debug(2,"onJoinInProgress::finished getting own visibletiles");
		
		for (int a=1;a<4;a++) {
			//debug(2,"onJoinInProgress::getting "+ a + " player information");
			pictureNum=array1[j++];
			wind=array1[j++];
			point=array1[j++];
			size=array1[j++];
			//debug(2,"onJoinInProgress:: pictureNum; wind="+ wind+ " ;point="+point+ " ;size="+size);			
			tmpPlayer= new Player(wind);
			tmpPlayer.concealedTiles=new TileCollection(TileCollection.MAXCONCEALEDSIZE);
			tmpPlayer.visibleTiles=new TileCollection(TileCollection.MAXVISIBLESIZE);	
			tmpPlayer.totalPoint=point;
			tmpPlayer.pictureNum=pictureNum;
			for(int i=0;i<size;i++) {
				int type=array1[j++];
				int value=array1[j++];
				tile= new Tile(type,value);
				//System.out.print("  " + tile );
				tmpPlayer.concealedTiles.addTileNoSorted(tile);
			}		
			//debug(2,"onJoinInProgress::finished getting own concealedtile");
			size=array1[j++];
			for(int i=0;i<size;i++) {
				int type=array1[j++];
				int value=array1[j++];
				int group=array1[j++];
				int groupType=array1[j++];			
				tile= new Tile(type,value,group,groupType);
				//System.out.print("  " + tile );
				tmpPlayer.visibleTiles.addTileNoSorted(tile);
			}		
			//debug(2,"onJoinInProgress::finished getting own visibletiles");
			players[a]=tmpPlayer;
		}
   		gameUI.onJoin( 0, player.getWind(), 1,this,player,1 );//to store own information, eg. nick, picNum	
		if (playMode==1) {
			networkCanvasPrv.onResponseStart();
		}
		else {
			networkCanvas.onResponseStart();			
		}		
		//to store other player information, eg. nick, picNum	
		gameUI.onJoinInProgress(players[0].getWind(),players[0].concealedTiles,players[0].visibleTiles,
				players[1].getWind(),players[1].pictureNum,players[1].totalPoint,nick1,players[1].concealedTiles.size(),players[1].visibleTiles,  
				players[2].getWind(),players[2].pictureNum,players[2].totalPoint,nick2,players[2].concealedTiles.size(),players[2].visibleTiles,  
				players[3].getWind(),players[3].pictureNum,players[3].totalPoint,nick3,players[3].concealedTiles.size(),players[3].visibleTiles, tilesOnTable);  
		//debug(2,"EngineProxy.onJoinInProgressHelper() end ");
    }
    
    
    /*
     * this method send nothing to server, juz reactive the waiting
     * called by Series60Client
     */
    public void doConfirm(){
		//debug(2,"doConfirm()");
   		isConfirmed=true;
    }

    public int getCurrentWind() {
   		return currentWind;
    }

    //the prevent sending the message twice... if we alredy receive the network start message, ignore the request
    public void nextGame(){
		//debug(20,"nextGame()");
	    isConfirmed=false;
	    if (networkStarted) {
	    	return;
	    }
		CanvasHelper.startNetworkAnimation();
		midlet.getDisplay().setCurrent(CanvasHelper.canvas);
		synchHelper();
		networkCommand.type=Constant.NEXTGAME;
		networkCommand.data=null;
	    needSend();
    }
   
    //called by MJGame
    public void setCanvas(PublicNetworkCanvas p_canvas) {
   		playMode=2;
   		networkCanvas=p_canvas;
   		networkCanvas.engine=this;
    }
   
    //called by MJGame
    public void setCanvas(PrivateNetworkCanvas p_canvas) {
		playMode=1;
   		networkCanvasPrv=p_canvas;
   		networkCanvasPrv.engine=this;
    }
   
    public void quitGame() {
   	    //debug(20,"quitGame()");
   		if (isCleanUp) {
   			return;
   		}
   	

   		if (playMode==2 ) {
   	    	if (networkCanvas.screen!=2) {
   	    	    CanvasHelper.startNetworkAnimation();
   	    	    midlet.getDisplay().setCurrent(CanvasHelper.canvas);   	    		
   	    	}
   	    }

   	    synchHelper();
    	isWaiting=false;//force to send this command
    	networkCommand.type=Constant.PUB_QUITROOM;
    	networkCommand.data=null;
    	needSend();
    }
   
    public void synchHelper() {
    	//if the previous response is not yet sent.... then wait
    	while (isWaiting ) {
    		try {
    			//debug(2,"synchHelper():::waiting for other command to finish");
    			Thread.sleep(1000);
    		}
    		catch (InterruptedException e){
    		}
    	}
    }
   
    //invoke by PublicNetworkCanvas
    public void forceStart() {
		//debug(2,"forceStart() start");
		CanvasHelper.startNetworkAnimation();
		midlet.getDisplay().setCurrent(CanvasHelper.canvas);
		networkCommand.type=Constant.FORCESTART;
		networkCommand.data=null;
		needSend();
		//debug(2,"EngineProxy.forceStart() finished");
    }
   
   	/*
   	 * invoked by PublicNetworkCanvas
   	 * Call the server. and construct a vector of table.
   	 * set the screen to Animated screen until room list is received from the server
   	 *
   	 * this method is only called once to get the initial list of rooms. 
   	 * subsequent room information is received from server.  
   	 *   
   	 * **/
    public void getPublicRoom(int index) {
   		//debug(2,"getPublicRoom()");
		synchHelper();
		CanvasHelper.startNetworkAnimation();
		midlet.getDisplay().setCurrent(CanvasHelper.canvas);
		networkCommand.type=Constant.PUB_GETROOM;
    	networkCommand.data=new Object[]{ new Integer(index) };
        needSend();
    	//debug(2,"EngineProxy:: after calling invokeServer(getpublicroom)");
    }

    //invoke by PublicNetworkCanvas
    public void createPublicRoom(int p_minDouble, int p_maxDouble, int pictureNum, String p_nick) {
    	
    	CanvasHelper.startNetworkAnimation();
   		midlet.getDisplay().setCurrent(CanvasHelper.canvas);
    	//debug(2,"createPublicRoom():nick="+p_nick);
    	networkCommand.type=Constant.PUB_CREATEROOM;
    	networkCommand.data=new Object[]{ 
				new Integer(p_minDouble), new Integer(p_maxDouble), new Integer(pictureNum), p_nick } ;
    	needSend();
    }

    //invoke by PublicNetworkCanvas
    public void joinPublicRoom(int tableNumber, int pictureNum, String p_nick) {
		//debug(2,"EngineProxy.joinPublicRoom()::: tableNumber="+ tableNumber+ " ;nick="+p_nick);
   		CanvasHelper.startNetworkAnimation();
   		midlet.getDisplay().setCurrent(CanvasHelper.canvas);
    	networkCommand.type=Constant.DO_JOINROOM;
    	networkCommand.data=new Object[]{new Integer(tableNumber), new Integer(pictureNum), p_nick};
        needSend();
    }

  	public void getRoomCategoryHelper() {
        //debug(2,"getRoomCategoryHelper()");
		networkCommand.type=Constant.PUB_GETROOMCATEGORY;
    	networkCommand.data=null;
        needSend();        
  	}

  	private void onResponseRoomCategory(Object[] returnval) {
        //debug(2,"onResponseRoomCategory()");
        String data[]= (String[]) returnval[2];		            	
        networkCanvas.onResponseRoomCategory(data);                
        networkCanvas.onLogin(motd);     
  	}
  	
    //this is used on privatenetwork
    public void rejectInvitation(int tableNumber) {
		//debug(2,"rejectInvitation()::: tableNumber="+ tableNumber);
    	networkCommand.type=Constant.PRV_REJECTJOINROOM;
    	networkCommand.data=new Object[]{ new Integer(tableNumber) };
        needSend();
    }
   
   	public void createPrivateRoom(int p_minDouble, int p_maxDouble, int pictureNum, Vector friends, String p_nick) {
   		//debug(2,"createPrivateRoom()");
   		CanvasHelper.startNetworkAnimation();
   		midlet.getDisplay().setCurrent(CanvasHelper.canvas);
		networkCommand.type=Constant.PRV_CREATEROOM;

		//always send 3 data for friends. use 0 for empty data
		int numOfFriendInvited=friends.size();
		int friendArray[]={0,0,0};
		for (int i=0;i<numOfFriendInvited;i++) {
			friendArray[i]= Integer.parseInt( (String) friends.elementAt(i) );
		}
		
		networkCommand.data=new Object[]{ 
				new Integer(p_minDouble), new Integer(p_maxDouble), new Integer(pictureNum),  
				new Integer(friendArray[0]), new Integer(friendArray[1]), new Integer(friendArray[2]), 
				p_nick } ;
   		needSend();
    }
   
    private Vector constructContactVector(Object[] returnval) {
   		//debug(2,"constructContactVector() start");
   		Vector contacts=new Vector();
   		//debug(2,"after creating contact vector");
   		   	int data[]= (int[]) returnval[1];
   		//debug(2,"assigning data");
	   	String p_nick[]= (String[]) returnval[2];
   		//debug(2,"assigning p_nick");
	   	int j=0;
	   	int i=0;
	   	if (data==null) {
	   		//debug(2,"data==null");
	   	}
	   	else {
	   		//debug(2,"data!=null");
		   	while (i<p_nick.length) {
				contacts.addElement(
						new Contact(data[j],data[j+1],p_nick[i]  ) );
				j=j+2;
				i++;
		   	}
	   	}
	   	//debug(2,"contactsize="+contacts.size());
   		//debug(2,"constructContactVector() finished");
	   	return contacts;
    }

    
    private void onContactListUpdateHelper(Object[] returnval) {
        //debug(2,"onContactListUpdateHelper");
   		int data[]=(int[]) returnval[1];

   		networkCanvasPrv.onResponseContactListUpdate(data[0],data[1]);
    }

    
    private void onRoomListUpdateHelper(Object[] returnval) {
        //debug(2,"onRoomListUpdateHelper");
   		int data[]=(int[]) returnval[1];
   		
   		if (playMode==1) {
   			networkCanvasPrv.onResponseRoomUpdate(new Table(data[2],data[3],data[4],data[1],data[5]) );
   		}
   		else {
   	   		if (data[0]==1) {
   	   			networkCanvas.onResponseRoomAdd(new Table(data[2],data[3],data[4],data[1],data[5]) );
   	   		}
   	   		else if (data[0]==2){
   	   			networkCanvas.onResponseRoomUpdate(new Table(data[2],data[3],data[4],data[1],data[5]) );
   	   		}
   	   		else {
   	   			networkCanvas.onResponseRoomDelete(data[1]);
   	   		}
   		}
    }
   
    /*
     * modified to accomodate competition room
     */
    private Vector onRoomListHelper(Object[] returnval) {
        //debug(2,"onRoomListHelper");
	   	Vector publicRooms=new Vector();
	   	int tableData[]= (int[]) returnval[1];
        //debug(2,"table data size="+ tableData.length);
	   	//add started data
	    if (tableData[0]==1) {
	    	isCompetitionRoom=true;
	    	System.out.println("isCompetition room");
	    }
	   	
		for (int i=1;i<tableData.length;i=i+5) {
			publicRooms.addElement(
					new Table(tableData[i+1],tableData[i+2],tableData[i+3],tableData[i], tableData[i+4]) );
		}
		return publicRooms;
    }
       
    //used in Private Network
    //used in PublicNetwork on the event when Host close the table
    private void onPendingEventHelper(Object returnval[]) {
    	//debug(2,"onPendingEventHelper()");
    	int array1[]= (int []) returnval[1];
    	String array2[]= (String []) returnval[2];
    	if (playMode==1) {
    		if (array1[0]==1 ) {
    	    	isWaitingPrvResponse=true;
    		}
    		if (array1[0]==30  ) {
        		networkCanvasPrv.onPendingEvent(array1[0],0,"","",array1[1]);   	    		    			    			
    		}
    		else {
        		networkCanvasPrv.onPendingEvent(array1[0],array1[1],array2[0],array2[1],0);   	    		    			
    		}
    		
        	if (!hasDisplayMOTD) {
        		//debug(2,"need to display login");
            	networkCanvasPrv.onLogin(motd,nick);
        	}
        	else {
        		//debug(2,"no need to display login");
        		midlet.getDisplay().setCurrent(midlet.lastForm);
        	}

    	}
    	
    	else {
            networkCanvas.onLogin(motd);     
    		networkCanvas.onInviteAnswered(array1[0],array1[1]);
    	}
    
    
	}

	public void acceptContact(int oriCid){
		//debug(2,"acceptContact()::: cid="+ oriCid);
		isWaitingPrvResponse=false;
		networkCommand.type=Constant.PRV_ACCEPTCONTACT;
    	networkCommand.data=new Object[]{new Integer(oriCid)  };
        needSend();
   		//debug(2,"EngineProxy.acceptContact() finished");		
	}

	public void rejectContact(int oriCid){
		//debug(2,"rejectContact()::: cid="+ cid);
		isWaitingPrvResponse=false;
		networkCommand.type=Constant.PRV_REJECTCONTACT;
    	networkCommand.data=new Object[]{ new Integer(oriCid)  };
        needSend();
   		//debug(2,"EngineProxy.rejectContact() finished");		
	}
	
	public void addContact(String destmsisdn1, String destmsisdn2, String destmsisdn3) {
		//debug(2,"addContact()::: destmsisdn1="+ destmsisdn1+ " ;destmsisdn2"+destmsisdn2+" ;destmisisdn3="+destmsisdn3);
		networkCommand.type=Constant.PRV_ADDCONTACT;

		//flag that the msisdn is empty
		if (destmsisdn1.equals(""))
			destmsisdn1="99999999";
		if (destmsisdn2.equals(""))
			destmsisdn2="99999999";
		if (destmsisdn3.equals(""))
			destmsisdn3="99999999";
		
		//debug(2,"destmsisdn1="+destmsisdn1+ " ;destmsisdn2="+destmsisdn2+" ;destmsisdn3="+destmsisdn3);
    	networkCommand.data=new Object[]{  new String(destmsisdn1),new String(destmsisdn2),new String(destmsisdn3)  };
        needSend();
		//debug(2,"EngineProxy.addContact() finished");
	}
	
	public void deleteContact(int destcid[]) {
		//debug(2,"deleteContact()::: destcid="+ destcid);
		networkCommand.type=Constant.PRV_DELETECONTACT;
    	Object tmp[]= new Object[2+ destcid.length];
    	tmp[0]=new Integer(destcid.length);//to let the server know number of data to read
    	for (int i=0;i<destcid.length;i++) {
    		tmp[i+1]=new Integer(destcid[i]);
    	}
    	networkCommand.data=tmp;
        needSend();    	
		//debug(2,"deleteContact() finished");		
	}
	   
    private void onInviteAnsweredHelper(Object returnval[]) {
    	//debug(2,"onInviteAnsweredHelper()");
    	int array1[]= (int []) returnval[1];
    	CanvasHelper.stopNetworkAnimation();
    	if (playMode==1) {
        	networkCanvasPrv.onInviteAnswered(array1[0],array1[1]);   	    		
    	}
    	else {
    		
    		networkCanvas.onInviteAnswered(array1[0],array1[1]);
    		
    	}
    }
   
    private void onInviteHelper(Object returnval[]) {
		//debug(2,"onInviteHelper()");
		int array1[]= (int []) returnval[1];
		if (playMode==1) {
			networkCanvasPrv.onInvite(array1[0],array1[1],array1[2],array1[3]);			
		}
    }
   
   
    //for private network
    public void getPendingEvent() {
   		//debug(2,"getPendingEvent() start");
		synchHelper();
		networkCommand.type=Constant.PRV_GETPENDINGEVENT;
    	networkCommand.data=null;
        needSend();
    }
   
   
  	public void doSpecialResponseN(TileCollection tiles, int concealedSize, int type, boolean response, int wind) {
    }
  	
  	private void needSend() {
  		//debug(2,"needSend()");
  		packetSenderThread.service();
  	}
  	
  	//invoked by MJTable
  	public void sendMessageTable(String message) {
        //debug(2,"sendMessageTable():: message="+message);
		networkCommand.type=Constant.SEND_MESSAGETABLE;
    	networkCommand.data=new Object[]{ new String(message) };
        needSend();        
  	}
  	  	
  	//invoked by MJTable
  	public synchronized void doCleanUp() {
  		//debug(2,"doCleanUp()");
  		isCleanUp=true;
  		CanvasHelper.stopNetworkAnimation();
  		client.doCleanUp();
  		if (timer!=null) {
  			timer.cancel();
  			task.cancel();
  		}
  		if (timer2!=null) {
  			timer2.cancel();
  			task2.cancel();
  		}
  		gameUI=null;
  		if (playMode==1) {
  			networkCanvasPrv.engine=null;
  			networkCanvasPrv=null;
  		}
  		else {
  	  		networkCanvas.engine=null;
  	  		networkCanvas=null;  			
  		}
  	}

  	
    public void communicationError() {
	    //debug(2,"communicationError");
		sendErrorMessage();
    }

  	//unused????
  	//called by DeleteForm
  	public void pausePoll() {
  		isWaitingPrvResponse=true;
  	}  	
  	public void resumePoll() {
  		isWaitingPrvResponse=false;
  	}
  	
    public void registerNick(String p_nick) {
   		//CanvasHelper.startNetworkAnimation();
   		//midlet.display.setCurrent(CanvasHelper.canvas);
    	//debug(10,"registerNick="+p_nick);
    	networkCommand.type=Constant.NETWORKCOMMON_REGISTERNICK;    		
    	networkCommand.data=new Object[]{ p_nick,
    			new String(DeviceConstant.CLIENT_CODE), new String(System.getProperty("microedition.platform" ))  };
    	needSend(); 
    }
    
    public int getCid() {
    	return cid;
    }
    public int getActivationCode() {
    	return activationCode;
    }
    
    private void onSMSSendAction() {
   		CanvasHelper.startNetworkAnimation();
   		midlet.getDisplay().setCurrent(CanvasHelper.canvas);
    }
    
    public void sendSMSPublic() {
		transactionFlag=true;
		Thread thread=new Thread(new SMSSenderThread(2));
		thread.start();
		onSMSSendAction();   

    }
    
    public void sendSMSPrivate() {
		transactionFlag=true;
		Thread thread=new Thread(new SMSSenderThread(1));
		thread.start();
		onSMSSendAction();   
    	
    }
    
    public void sendSMSRegistration() {
		transactionFlag=true;
		Thread thread=new Thread(new SMSSenderThread(0));
		thread.start();
		onSMSSendAction();   
    }

    
    
    //send the SMS in different thread
    private class SMSSenderThread extends Thread {
    	private int type=0;
    	
    	public SMSSenderThread(int _type) {
    		type=_type;
    	}
    	
    	public synchronized void run() {
    		if (type==0) {
    			try {
    				SMSEngine.sendMessage(Constant.IPX_SMSCENTER,Constant.IPX_HEADER_COM +" "+ cid+ " "+activationCode);
    			}
    			catch (SMSSendException e) {
    				midlet.commandReceived(Constant.REGISTRATION_ERR3);
    			}
    			catch (SecurityException e) {
    				midlet.handleSecurityException(Constant.SECURITY_EXCEPTION_SMS);
    			}
    		}
    		else if (type==1) {
    			try {
    				SMSEngine.sendMessage(Constant.IPX_SMSCENTER,Constant.IPX_HEADER_PRV + " "+ cid+ " "+activationCode + " " + xid);
    			}
    			catch (SMSSendException e) {
    				midlet.commandReceived(Constant.TRANSACTION_ERR1);
    			}
    			catch (SecurityException e) {
    				midlet.handleSecurityException(Constant.SECURITY_EXCEPTION_SMS);
    			}
    			
    		}
    		else if (type==2) {
    			try {
    				SMSEngine.sendMessage(Constant.IPX_SMSCENTER,Constant.IPX_HEADER_PUB + " "+ cid + " "+activationCode+ " " + xid);
    			}
    			catch (SMSSendException e) {
    				midlet.commandReceived(Constant.TRANSACTION_ERR1);
    			}
    			catch (SecurityException e) {
    				midlet.handleSecurityException(Constant.SECURITY_EXCEPTION_SMS);
    			}
    		}
    	}    	
    }
    
    
    
    
	//only executed once, when waiting the user to confirm
    private void waitConfirmHelper() {
		if (!isConfirmed) {
        	while (!isConfirmed) {
        		//debug(0,"EngineProxy.wait till confirmed");
            	try {
            		Thread.sleep(500);
            	}
            	catch (InterruptedException e) {}
            }
		}
    }

    public void join(GameListenerIF _gameUI, int _tableno) {}
    public void setDemoMode(int demoMode){}
    public void setGameRule(int minDouble, int MaxDouble){}
    //implementing the EngineIF, do nothing
    public Player[] getPlayers() {
    	return null;
    }

    /*
     * *************************************************************************
     * Helper method for //debugging
     * ***********************************************************************
     */
    void debug(int level, String txt) {
    	if (level>=debugLevel)
    		System.out.println("EngineProxy."+txt);
    }    

    private void t_printTileSize(int level) {
    	if (level>=debugLevel) {
        	for (int i=0;i<players.length;i++) {
        		//System.out.println("player " + players[i].getWind() + ":tilessize="+ players[i].concealedTiles.size() +" visible=" + players[i].visibleTiles.size());
        	}
    	}    	
    }

    private void t_printTile(int level) {
    	if (level>=debugLevel) {
    		//System.out.println("*********** PRINT TILE ***********");
    		for (int i=0;i<players.length;i++) {
        		//System.out.println("player " + players[i].getWind() + ":tilessize="+ players[i].concealedTiles.size() +" visible=" + players[i].visibleTiles.size());
        		//System.out.println("visible:  ");
    	    	for(int j=0;j< players[i].visibleTiles.size();j++) {
    	        	//System.out.print(  players[i].visibleTiles.getTileAt(j).type  +"_" +  players[i].visibleTiles.getTileAt(j).value + "_"+  players[i].visibleTiles.getTileAt(j).group+"_"+ players[i].visibleTiles.getTileAt(j).groupType+"  ");
    	        }      	
        		//System.out.println("concealed:  ");
    	        for(int j=0;j< players[i].concealedTiles.size();j++) {
    	        	//System.out.print("  ");
    	        	//System.out.print(  players[i].concealedTiles.getTileAt(j).type  +"_" + players[i].concealedTiles.getTileAt(j).value );
    	        }  
    	        //System.out.println("\n");
        	}
    	}    	
    }

    /*
     * *********************************************************************************
     * 
     * Part of EngineFA... to precalculate the next specialEvent
     * 
     * *****************************************************************************
     */
    private int getPlayNum(int wind) {
    	for(int i=0;i<4;i++) {
            if(players[i].getWind()==wind) {
                return i;
            }
    	}
    	return 0;
    }
    
    private int doDiscardHelper(Tile tile) {
    	eventQueue.removeAllElements();
        players[0].specialAction.removeAllElements();   
    	players[1].specialAction.removeAllElements();
    	players[2].specialAction.removeAllElements();
    	players[3].specialAction.removeAllElements();
    	
    	turn=player.getWind();
 
    	//debug(4,"player turn="+turn);

    	//check win
    	for (int i=1;i<4;i++) {
			int playerIndex=getPlayNum( (turn+i)%4);
			int doubleCount=checkWinHelper(playerIndex,eventQueue,tile);
			if (doubleCount>=0) {
				return 1;
			}
    	}
    	
    	//check all player that can pong/kong
    	//will update the eventQueue vector if there is a player that can pong/kong        
    	for (int i=1;i<4;i++) {
    		int playerIndex=getPlayNum( (turn+i)%4);
			checkPongHelper(playerIndex,eventQueue,tile);
			if (eventQueue.size()>0) 	// if there is one player that can pong/kong, exit the loop
				return 1;
    	}
		checkChowHelper( getPlayNum( (turn+1)%4  ),eventQueue,tile);
		if (eventQueue.size()==0) {
			return 0;
		}
		else {
			return 1;
		}
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

    	TileCollection col=players[index].concealedTiles;
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
			players[index].specialAction.addElement(
					new SpecialAction(GameAction.TYPE_CHOW,min2pos,min1pos) );
    	}
    	if (min1pos!=-9 && plus1pos!=-9) {
			canChow=true;
			//debug(1,"********this is CHOW-1!!!********* tile"+ min1pos + " tile " + plus1pos);
			//t_printOwnTiles(index);
			players[index].specialAction.addElement(
					new SpecialAction(GameAction.TYPE_CHOW,min1pos,plus1pos) );
    	}
    	if (plus1pos!=-9 && plus2pos!=-9) {
			canChow=true;
			//debug(1,"********this is CHOW+1!!!********* tile"+ plus1pos + " tile " + plus2pos);
			//t_printOwnTiles(index);
			players[index].specialAction.addElement(
					new SpecialAction(GameAction.TYPE_CHOW,plus1pos,plus2pos) );
    	}

        if (canChow) {
        	eventQueue.addElement(new SpecialAction(index,GameAction.TYPE_CHOW) );
        }
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
    	//debug(20,"EngineFA.checkPongHelper()");
    	//debug(1,"EngineFA.checkPongHelper()");
    	
    	int identCounter=1;//identical counter;
    	int tileposition=0;
    	int starttileposition=-1;
       	int previousSize=players[index].specialAction.size();
        
        TileCollection col=players[index].concealedTiles;
        int colsize=col.size();
        //while (enum.hasMoreElements()) {
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
        			players[index].specialAction.addElement(
        					new SpecialAction(GameAction.TYPE_PONG,starttileposition,tileposition) );
        		}
        		if (identCounter==4) {
        			//debug(1,"********this is KONG!!!*********player[" +index+"]" + currenttile.type+"_"+currenttile.value +"::::tile="+starttileposition+"-"+tileposition);
        			//t_printOwnTiles(index);
        			players[index].specialAction.addElement(
        					new SpecialAction(GameAction.TYPE_KONG,starttileposition,tileposition) );
        		}
        	}
        	else {
        		identCounter=1;
        		starttileposition=-1;
        	}            		
        	tileposition++;
        }//end while

        if (players[index].specialAction.size()>previousSize) {
        	//it doesn't matter if we use the gameaction TYPE_KONG or TYPE_PONG
        	eventQueue.addElement(new SpecialAction(index,GameAction.TYPE_KONG) );
        }
    }//end checkPongHelper()

    
    /*21/09/2004
     *  algorithm to check when a player win
     */
    private int checkWinHelper(int index, Vector eventQueue, Tile discardedTile) {
    	//boolean isWin=false;
    	int doubleCount;
    	//debug(3,"EngineProxy.checkWinHelper(): player="+index);

    	players[index].specialAction.removeAllElements();
    	Tile winningTile=new Tile(discardedTile.type,discardedTile.value);
    	players[index].concealedTiles.addTile(winningTile);
    	wineng.loadTiles(players[index].concealedTiles,players[index].visibleTiles, currentWind,players[index].getWind());

    	doubleCount=wineng.checkWin(false);
    	players[index].concealedTiles.removeTile(winningTile);
    	
    	if (doubleCount>=0) {
    		return 1;
    	}
    	return 0;
    }
}
