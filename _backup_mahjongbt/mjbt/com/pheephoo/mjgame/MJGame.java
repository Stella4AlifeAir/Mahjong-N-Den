
package com.pheephoo.mjgame;

import com.pheephoo.mjgame.ui.*;
import com.pheephoo.mjgame.engine.*;
import com.pheephoo.mjgame.bt.*;
import com.pheephoo.mjgame.form.*;
import com.pheephoo.mjgame.network.*;
import com.pheephoo.utilx.Common;
import java.util.*;
import javax.microedition.lcdui.*;
import javax.microedition.io.*;
/** 
 * This is the Midlet of the application.
 * It functions as a central switch for different screen
 * It loads com.pheephoo.mjgame.ui.MainForm initially
 * and loads other screens according to the command it receives 
 * 
 * @author Samuel
 * @version 1.0.1 19/10/2004
 */
public class MJGame extends javax.microedition.midlet.MIDlet implements CommandListener, Runnable {

	public int numOfFail=0;//accessed by FailureForm
	public boolean needShowError=false;//accessed by FailureForm
	public boolean transactionError=false;//accessed by FailureForm
    public Displayable lastForm;//accessed by PublicNetworkCanvas & PrivateNetworkCanvas. pointer to last screen before the current screen, accessed by PublicNetworkCanvas
    public String dateStr="";//history date for private networ, acccessed by PlayerInfoCanvas
    
    
	private boolean isInterrupted=false;
	private boolean isCleanUp=false;
	private boolean isOnline=false;
    private boolean settingFormIsLoading=false;//for fast loading the setting form
	
	private Displayable interruptedForm=null;//the store the form that is active while there is external event such as phone call, sms
    private Displayable mainForm; 
    private InviteForm inviteForm;
    private Display display;
    
    private EngineIF engine;
    private EngineProxy engineProxy;
    private Series60Client  client1; //mahjong table UI
    private PublicNetworkCanvas publicNetworkCanvas;
    private PrivateNetworkCanvas privateNetworkCanvas;
    private SettingForm settingForm; //setting
    
    private int publicNetworkScreen=0;//need it for differentiating between the nick and message input  0=waiting screen.  1=playing screen
    
    private int networkSelect=0;// 1=privatenetwork; 2=publicnetwork
    private int playMode=0;  //0=offline  1=online
    
    private String motd;
    private String nick;
    private TextBox textBox;

    private UserData userdata;

    Command okCommand;
    int demoMode;

    private boolean checkExpired() {
		long curtime=System.currentTimeMillis();

		//set the expired date 365days from the first activation
		if (curtime>userdata.firstPlayTime+(long) (1000*60*60*24) * (long) 365) {
			return true;
		}

		return false;
	}
	
	/*
	 * main switch, implemented method of MidletIF to facilite communication between form and midlet
	 * load different form to screen depending on the command it received
	 * 
	 * @param command 	the constant representing a command
	 */
	public void commandReceived(int command, String message) {
		if (command==Constant.PUBLIC_DISPLAY_MOTD) {
			//System.out.println("public_display_motd");
			numOfFail=0;
			motd=message;
	    	textBox= new TextBox("Enter your nick",engineProxy.nick,15,TextField.ANY);
	    	okCommand= new Command("Ok", Command.OK,1);
	    	textBox.addCommand(okCommand);
	    	textBox.setCommandListener(this);
	    	CanvasHelper.stopNetworkAnimation();//the animation was started by startPublicTable()
	    	display.setCurrent(textBox);
		}
		else if (command==Constant.PRIVATE_DISPLAY_MOTD) {
			//System.out.println("private_display_motd:"+message);
			numOfFail=0;
			CanvasHelper.stopNetworkAnimation();
			displayMOTD(message);
		}
		else if (command==Constant.NETWORKCOMMON_NOTICESCREEN) {
			//System.out.println("display noticescreen");
			Alert alert= new Alert("",message,null, AlertType.INFO);
			alert.setTimeout(Alert.FOREVER);
			//currentForm=client1;
	    	lastForm=client1;
			display.setCurrent(alert,lastForm);      
		}
	    else if (command==Constant.REGISTRATION_NICK_OK) {//create user nick
			//System.out.println("registration nick ok");
			display.setCurrent(CanvasHelper.canvas); 
			engineProxy.registerNick(message);
		}
        else if (command==Constant.MISC_NEEDUPDATE) {
			networkCleanUp();
        	//System.out.println("message="+message);
			Alert alert= new Alert("","There is a new update available. You need to download the update to continue",
	    			null, AlertType.INFO);
			alert.setTimeout(Alert.FOREVER);
			display.setCurrent(alert,mainForm);      
			try {
				Thread.sleep(5000);
				this.platformRequest(message);
		        Thread.sleep(2000);
		        this.close();
			}
			catch (ConnectionNotFoundException e) {
				e.printStackTrace();
			}
			catch (InterruptedException e) {
			}
        }
        else if (command==Constant.COMMON_GAMEFINISH) {
        	//System.out.println("game finished");
        	dateStr=message;
        	networkCleanUp();        	
        	display.setCurrent(new PlayerInfoCanvas(this,client1,playMode,1));       	        	
        }
	}

    /*
     * load the MainForm as the first screen
     */
    public MJGame() {
	    mainForm=new MainForm(this).list;
    	userdata=new UserData();
    	
//for special purpose client and emulator
//    	userdata.cid=499;
//    	userdata.cid=-100;
    	display = Display.getDisplay(this);
    }
    
    public Display getDisplay() {
    	return display;
    }

    
    private void validityCheck() {
    	
    	//Constant.CLIENTTYPE=0 original client;  Constant.CLIENTTYPE=1 updated client);
    	//if user alredy have the original version, its userdata.clientType is already set to 1 on the first time the application run
    	if (Constant.CLIENTTYPE!=0 && userdata.clientType==-1) {
    		showAlertAndExit("Please download the client before applying the update",3000);
    	}
    	else {
    		userdata.clientType=1;
    	}

    	if (userdata.firstPlayTime<=0) {
        	userdata.firstPlayTime=System.currentTimeMillis();
        	userdata.save();
    	}
    	
    	if (checkExpired() ) {
    		showAlertAndExit("License expired. Please delete this client and download the latest client",5000);
    	}

    }
	
	/* executed when MIDlet is starting or resuming from a pause
     * set the currentForm as the current display screen
     * @throws MIDletStateChangeException
     */
    public void startApp() {
		
    	validityCheck();

    	if (isInterrupted) { //returning from incoming call, sms
    		if (needShowError) {//if playing online, incoming call will cause network disconnection
//    			//System.out.println("returning from incoming call. network");
        		display.setCurrent(new FailureForm(this));
    			needShowError=false;
        		numOfFail++;
    		}
    		else {
        		display.setCurrent(interruptedForm);    			
    		}
    		isInterrupted=false;
    	}
    	else { //normal start
//	    	//System.out.println("Midlet is started. Platform="+System.getProperty("microedition.platform" ));
			publicNetworkScreen=0;
	        lastForm=mainForm;       
	        SplashScreen splash=new SplashScreen(this,3500);
	        display.setCurrent(splash);
	        
	        //preload other resources while waiting for SplashScreen to expire
		    CanvasHelper.init();
	        settingFormIsLoading=true;
	        settingForm=new SettingForm(this); //settingForm initialization requires lot of processing
	        settingFormIsLoading=false;

	        while (!splash.finished) {  
	        	try {
	        		Thread.sleep(500);
	        	}
	        	catch(InterruptedException e) {
	        	}
	        }
        	display.setCurrent(mainForm);            
    	}
    }
    
    
    public void pauseApp() {
    	isInterrupted=true;
    	if (isOnline) {
    		needShowError=true;
    	}
    	interruptedForm=display.getCurrent();
    }
    
    public void destroyApp(boolean unconditional) {
    	notifyDestroyed();
    }
    
    /*
     * Utility method to shutdown the application
     */
    public void close() {
        destroyApp(true);
        notifyDestroyed();
    }
    
        
    /*
     * used for inputing nick name and message
     */
    public void commandAction(Command command, Displayable displayable) {
    	//do the checking for user input
    	nick=textBox.getString();
//		//System.out.println("command action, system public networkscreen="+publicNetworkScreen);
		if (publicNetworkScreen==0) {
			if (nick.length()==0) {
	        	Alert alert= new Alert("","Please input your nick",null, AlertType.ERROR);
	        	display.setCurrent(alert);       	        	
			}
			else if (nick.indexOf(" ")!=-1) {
	        	Alert alert= new Alert("","Please input your nick without space",null, AlertType.ERROR);
	        	display.setCurrent(alert);       	        	
			}
	    	else if (command==okCommand) {
//	    		//System.out.println("nick="+nick);
	    		Setting.nick=nick;//series60
	    		displayMOTD(motd);
	    	}
		}
		//network playing mode
		else {
			//the second argument is not used
			//((EngineProxy)networkCanvas.engine).doPing();
			if (networkSelect==1) {
				privateNetworkCanvas.engine.sendMessageTable(nick);
				display.setCurrent(lastForm);
			}
			else {
				publicNetworkCanvas.engine.sendMessageTable(nick);
				display.setCurrent(lastForm);
			}
		}
    }

    private void displayMOTD(String message) {
		//System.out.println("display motd");
		Alert alert= new Alert("",message, null, AlertType.INFO);
		alert.setTimeout(Alert.FOREVER);
    	display.setCurrent(alert,lastForm);      
    }

    
    private void networkCleanUp() {
    	System.out.println("*networkcommon_quitgame");
    	if (!isInterrupted) {
    		isOnline=false;   	
    	}
    	lastForm=mainForm;
    	display.setCurrent(lastForm);
    	isCleanUp=true;
    	Thread thread = new Thread() {
    		public void run() {
            	if (networkSelect==1) {
            		privateNetworkCleanUp();
            	}
            	else {
                	publicNetworkCleanUp();        		
            	}
    		}
    	};
    	
    	thread.start();
    }

    /*
	 * main switch, implemented method of MidletIF to facilite communication between form and midlet
	 * load different form to screen depending on the command it received
	 * 
	 * @param command 	the constant representing a command
	 */
    public void commandReceived(int command) {
    	//System.out.println("commandRECEIVED!="+command);
    	if (command == Constant.MAIN_PLAY) {
//    		userdata.cid=101;
    		
    		if (userdata.cid<=0) {
            	CanvasHelper.startNetworkAnimation();
            	display.setCurrent(CanvasHelper.canvas);
            	startPublicTable();
            }
            else {
        		CanvasHelper.drawOfflineWait();
        		display.setCurrent(CanvasHelper.canvas);
        		startSinglePlayer(0);            	
            }
    		
        }
    	else if (command == Constant.MAIN_TEST) {
        	display.setCurrent(new TestForm(this).list);    		
    	}
    	else if (command == Constant.MAIN_BTSERVER) {
    		System.out.println("BT DEMO");
    		//loading the BT Server

        	BTServerCanvas btServerCanvas= new BTServerCanvas (this);
        	display.setCurrent(btServerCanvas);
//        	engineProxy.setCanvas(publicNetworkCanvas);        	        	

        	BTServer btServer=new BTServer(this);
    		btServer.setGUI(btServerCanvas);
    	}
    	else if (command == Constant.MAIN_BTCLIENT) {
    		System.out.println("BT CLIENT");
    		//loading the BT Server

        	BTServerCanvas btServerCanvas= new BTServerCanvas (this);
        	display.setCurrent(btServerCanvas);
//        	engineProxy.setCanvas(publicNetworkCanvas);        	        	

        	BTServer btServer=new BTServer(this);
    		btServer.setGUI(btServerCanvas);
    	}

    	
    	else if (command == Constant.TEST_SPEEDOFFLINE1) {
    		startSinglePlayerDebug(0,5);
    	}
    	else if (command == Constant.TEST_SPEEDOFFLINE2) {
    		startSinglePlayerDebug(0,6);    		
    	}
    	else if (command == Constant.TEST_PONG1) {
    		startSinglePlayerDebug(Constant.TEST_PONG1,0);    		
    	}
    	else if (command == Constant.TEST_CONCEALKONG1) {
    		startSinglePlayerDebug(Constant.TEST_CONCEALKONG1,0);    		
    	}
    	else if (command == Constant.TEST_CONCEALKONG2) {
    		startSinglePlayerDebug(Constant.TEST_CONCEALKONG2,0);    		
    	}
    	else if (command == Constant.TEST_CONCEALKONG3) {
    		startSinglePlayerDebug(Constant.TEST_CONCEALKONG3,0);    		
    	}
    	else if (command == Constant.TEST_CONCEALKONG4) {
    		startSinglePlayerDebug(Constant.TEST_CONCEALKONG4,0);    		
    	}
    	else if (command == Constant.TEST_ROBKONG1) {
    		startSinglePlayerDebug(Constant.TEST_ROBKONG1,0);    		
    	}
    	else if (command == Constant.TEST_ROBKONG2) {
    		startSinglePlayerDebug(Constant.TEST_ROBKONG2,0);    		
    	}
    	
    	
    	else if (command == Constant.TEST_CLEARHAND1) {
    		startSinglePlayerDebug(Constant.TEST_CLEARHAND1,0);    		
    	}
    	else if (command == Constant.TEST_CLEARHAND2) {
    		startSinglePlayerDebug(Constant.TEST_CLEARHAND2,0);    		
    	}
    	else if (command == Constant.TEST_CLEARHAND3) {
    		startSinglePlayerDebug(Constant.TEST_CLEARHAND3,0);    		
    	}
    	
    	
    	else if (command==Constant.NETWORKCOMMON_SENDMESSAGE) {
        	textBox= new TextBox("Type your message","",50,TextField.ANY);
        	okCommand= new Command("Ok", Command.OK,1);
        	textBox.addCommand(okCommand);
        	textBox.setCommandListener(this);
        	display.setCurrent(textBox);
    	}
        else if (command == Constant.MAIN_SETTING) {
        	try {
	        	while (settingFormIsLoading) {
	        		Thread.sleep(500);
	        	}
        	}
        	catch (InterruptedException ie){        		
        	}
	        	
        	if (!settingFormIsLoading) {
        		settingFormIsLoading=true;
	        	if (settingForm==null) {
	        		settingForm=new SettingForm(this);
	        	}
	        	settingForm.setMode(0);//1=playing mode
	        	settingFormIsLoading=false;
	        	display.setCurrent(settingForm);
        	}
        }
        else if (command == Constant.MAIN_PUBLIC) {
        	CanvasHelper.startNetworkAnimation();
        	display.setCurrent(CanvasHelper.canvas);
        	startPublicTable();
        }
        
        else if (command == Constant.MAIN_PRIVATE) {
        	CanvasHelper.startNetworkAnimation();
        	display.setCurrent(CanvasHelper.canvas);
        	startPrivateTable();
        }
        else if (command == Constant.MAIN_HISTORY) {
        	display.setCurrent(new SelectHistoryForm(this).list);
        }
        else if (command == Constant.HISTORY_OFFLINE) {
        	//System.out.println("history offline");
        	display.setCurrent(new PlayerInfoCanvas(this,client1,playMode,2));       	        	
        }
        else if (command == Constant.HISTORY_ONLINE) {
        	//System.out.println("history online");
        	display.setCurrent(new PlayerInfoCanvas(this,client1,playMode,3));       	        	
        }
        
        else if (command == Constant.MAIN_NOTAVAILABLE) {
        	display.setCurrent(new HelpForm(this) );
        }
        else if (command==Constant.OFFLINE_PAUSE) {
        	lastForm=new PauseForm(this,Constant.OFFLINE_PAUSE).list;
        	display.setCurrent(lastForm);
        }        
        else if (command==Constant.NETWORKCOMMON_PAUSE) {
        	lastForm=new PauseForm(this,Constant.NETWORKCOMMON_PAUSE).list;
        	display.setCurrent(lastForm);
        }
        else if (command==Constant.PUBLIC_CPAUSE) {
        	lastForm=new PauseForm(this,Constant.PUBLIC_CPAUSE).list;
        	display.setCurrent(lastForm);
        }
        else if (command==Constant.PRIVATE_LPAUSE) {
        	lastForm=new PauseForm(this,Constant.PRIVATE_LPAUSE).list;
        	display.setCurrent(lastForm);
        }
        else if (command==Constant.PRIVATE_CPAUSE) {
        	lastForm=new PauseForm(this,Constant.PRIVATE_CPAUSE).list;
        	display.setCurrent(lastForm);
        }
        else if (command==Constant.PRIVATE_CONTINUE) {
        	//System.out.println("mjgame.private_continue");
        	display.setCurrent(privateNetworkCanvas);
        }
        else if (command==Constant.PRIVATE_ERRSTART1) {
        	Alert alert= new Alert("","Please invite at least one player to play with you. Press the center button to select player",null, AlertType.ERROR);
        	alert.setTimeout(Alert.FOREVER);
        	display.setCurrent(alert);       	        	
        }
        else if (command==Constant.PRIVATE_ERRSTART2) {
        	Alert alert= new Alert("","Max three players can be invited to play with you",null, AlertType.ERROR);
        	alert.setTimeout(Alert.FOREVER);
        	display.setCurrent(alert);       	        	
        }
        else if (command==Constant.PRIVATE_ERRINVITE1) {
        	Alert alert= new Alert("","Please invite at least one player to play with you",null, AlertType.ERROR);
        	display.setCurrent(alert);       	        	
        }
        else if (command==Constant.PRIVATE_ERRINVITE2) {
        	Alert alert= new Alert("","You have entered invalid an phone number",null, AlertType.ERROR);
        	display.setCurrent(alert);       	        	
        }
        else if (command==Constant.PRIVATE_ERRINVITE3) {
        	Alert alert= new Alert("","Unable to send SMS. Please try again",null, AlertType.ERROR);
        	display.setCurrent(alert);       	        	
        }
        else if (command==Constant.REGISTRATION_ERR1) {
        	Alert alert= new Alert("","Registration cancelled.",null, AlertType.ERROR);
        	display.setCurrent(alert);
        	networkCleanUp();
        }
        else if (command==Constant.REGISTRATION_ERR2) {
        	//System.out.println("registration time out");
        	Alert alert= new Alert("","Network timeout. Please try again.",null, AlertType.ERROR);
        	display.setCurrent(alert);
        	networkCleanUp();
        }
        else if (command==Constant.REGISTRATION_ERR3) {
        	Alert alert= new Alert("","Unable to send SMS. Please try again.",null, AlertType.ERROR);
        	display.setCurrent(alert);
        	networkCleanUp();
        }
        else if (command==Constant.NICK_ERR1) {
        	Alert alert= new Alert("","Please input your nick",null, AlertType.ERROR);
        	display.setCurrent(alert);       	        	
        }
        else if (command==Constant.NICK_ERR2) {
        	Alert alert= new Alert("","Please use valid characters",null, AlertType.ERROR);
        	display.setCurrent(alert);       	        	
        }
        else if (command == Constant.PRIVATE_INVITEFRIEND) {
            inviteForm=new InviteForm(this);
        	lastForm=new PauseForm(this,Constant.PRIVATE_LPAUSE).list;
            display.setCurrent(inviteForm);                    
        }
        else if (command == Constant.REGISTRATION_NICK) {
            RegistrationForm nickform=new RegistrationForm(this,DeviceConstant.MSG_REGISTRATION_TITLE2,1 );
        	display.setCurrent(nickform);                    
        }
        else if (command == Constant.REGISTRATION_NICK_DUPLICATE) {
            RegistrationForm nickform=new RegistrationForm(this,DeviceConstant.MSG_REGISTRATION_TITLE2,2 );
        	display.setCurrent(nickform);                    
        }
        else if (command == Constant.PRIVATE_DELETEFRIEND) {
            DeleteForm form=new DeleteForm(this,privateNetworkCanvas);
        	display.setCurrent(form.list);                    
        }
        else if (command==Constant.NETWORKCOMMON_CONTINUE) {
        	display.setCurrent(client1);
        }
        else if (command==Constant.PUBLIC_CONTINUE) {
        	display.setCurrent(publicNetworkCanvas);
        }
        else if (command == Constant.MAIN_ABOUT) {
        	display.setCurrent(new AboutForm(this));        
        }
        else if (command == Constant.MAIN_EXIT) {
        	this.close();
        }
        else if (command == Constant.ALL_SETTING) {
        	if (settingForm==null) {
        		settingForm=new SettingForm(this);
        	}
        	settingForm.setMode(1);//1=playing mode
        	display.setCurrent(settingForm);            
        }
        //command returned by PauseForm
        else if (command==Constant.OFFLINE_CONTINUE) {
        	client1.reloadSetting();
        	//System.out.println("setting to original form");
        	lastForm=client1;
        	display.setCurrent(client1);        	
        }
        else if (command == Constant.OFFLINE_QUITGAME) {
        	if (engine!=null ) {
        		((EngineFA)engine).doCleanUp();
        	}
        	engine=null;
        	if (client1!=null) {
	        	client1.doCleanUp();
	        	client1=null;
        	}
        	lastForm=null;
        	lastForm=mainForm;
        	display.setCurrent(mainForm);            
        }
        else if (command == Constant.MAIN_MAINMENU) {
        	//System.out.println("back_gamestart");        	
        	lastForm=mainForm;
        	display.setCurrent(lastForm);
        }
        else if (command == Constant.SETTING_CANCEL || command == Constant.SETTING_OK) {
        	//System.out.println("cancel_ok_setting");        	
            display.setCurrent(lastForm);
        }   		
        else if (command== Constant.NETWORKCOMMON_PAUSE) {
        	lastForm=new PauseForm(this, Constant.NETWORKCOMMON_PAUSE).list;
        	//System.out.println("Creating pauseform");
        	display.setCurrent(lastForm);
        }
        else if (command==Constant.PRIVATE_DELETEOK) {
        	//connect to server & add to pending database
        	Alert alert= new Alert("","The selected contacts have been deleted" , null, AlertType.INFO);
    		alert.setTimeout(Alert.FOREVER);
        	display.setCurrent(alert, lastForm);       	
        }        
        
        else if (command==Constant.PRIVATE_INVITEOK) {
        	//connect to server & add to pending database
        	privateNetworkCanvas.addContact(inviteForm.msisdn1,inviteForm.msisdn2,inviteForm.msisdn3);
        	Alert alert= new Alert("","Thank you! Your invitation will be sent via SMS to your friends. " 
        			 , null, AlertType.INFO);
    		alert.setTimeout(Alert.FOREVER);
        	display.setCurrent(alert, lastForm);         	
        }
        else if (command==Constant.TRANSACTION_ERR1) {
        	//System.out.println("TRANSACTION_ERR1");
        	transactionError=true;
        	display.setCurrent(new FailureForm(this));
        }

        else if (command==Constant.NETWORKCOMMON_COMMUNICATIONERROR) {
        	if (!isCleanUp) {
	        	try {
	        		display.setCurrent(new FailureForm(this));
	        		numOfFail++;
	        	}
	        	catch (IllegalStateException e){
	        	}
        	}
        }
        else if (command==Constant.NETWORKCOMMON_QUITGAME) {
        	networkCleanUp();        	
        }
        else if (command==Constant.COMMON_GAMEINFO) {
        	display.setCurrent(new GameInfoCanvas(this,client1,playMode));       	        	
        }
        else if (command==Constant.COMMON_GAMEFINISH) {
        	//System.out.println("game finished");
        	display.setCurrent(new PlayerInfoCanvas(this,client1,playMode,1));       	        	
        }
        else if (command==Constant.COMMON_PLAYERINFO) {
            display.setCurrent(new PlayerInfoCanvas(this,client1,playMode,0));                    
        }
        else if (command==Constant.NETWORKCOMMON_STARTALLHUMAN) {
        	publicNetworkScreen=1;
			//System.out.println("public_startallhuman");
			client1.prepareBackground();//draw a default background without animation
			client1.startAllHumanFlag=true;
			display.setCurrent(client1);
        	CanvasHelper.stopNetworkAnimation();
        }
    }
    
    /*
     * create PublicNetworkCanvas, and set the carrent display to PublicNetworkCanvas
     * create Series60Client
     * create EngineProxy, and start the EngineProxy thread
     * 
     */
    public void startPublicTable() {
    	networkSelect=2;
    	lastForm=publicNetworkCanvas;
    	Thread mainThread= new Thread(this);
    	mainThread.start();
    }
    
    public void startPrivateTable() {
    	networkSelect=1;
    	lastForm=privateNetworkCanvas;
    	Thread mainThread= new Thread(this);
    	mainThread.start();
    }
    
    
    public void run() {
    	isOnline=true;
      	playMode=1;
    	client1 = new Series60Client(this,1);
        
        //if the user has not been registered, point use publicNetwork
        if (userdata.cid<=0) {
        	networkSelect=2;
        }
        
        if (networkSelect==1) {
        	privateNetworkCanvas = new PrivateNetworkCanvas(this);
        	engineProxy= new EngineProxy( new MobileSocket( Constant.SERVER_URL+":"+ Constant.SERVER_PORT_PRV),client1,userdata.cid,userdata.code,this) ;
        	engineProxy.setCanvas(privateNetworkCanvas);        	
        }
        else if (networkSelect==2) {
        	publicNetworkCanvas = new PublicNetworkCanvas(this);
        	engineProxy= new EngineProxy( new MobileSocket( Constant.SERVER_URL+":"+ Constant.SERVER_PORT_PUB),client1,userdata.cid,userdata.code,this) ;
        	engineProxy.setCanvas(publicNetworkCanvas);        	        	
        }
    	isCleanUp=false;
    	Thread t = new Thread( engineProxy);
    	t.start();
    }

    public void updateCid(int cid,int activationCode) {
    	//System.out.println("save cid="+cid);
    	userdata= new UserData();
    	userdata.cid=cid;
    	userdata.code=activationCode;
    	userdata.save();
    }
    public void resetCid() {
		Alert alert= new Alert("","Resetting the client",null, AlertType.INFO);

		display.setCurrent(alert);
    	//System.out.println("RESET CID");
    	userdata= new UserData();
    	userdata.cid=-99;
    	userdata.save();
    }

    private class SinglePlayerThread implements Runnable {

    	MJGame midlet;
    	
    	SinglePlayerThread(MJGame p_midlet) {
    		midlet=p_midlet;
    	}

    	public void run() {
            client1 = new Series60Client (midlet,0); 
	        midlet.getDisplay().setCurrent(client1);
	    	engine = new EngineFA();
	    	
	    	Vector freeBot=new Vector();
	    	for (int i=0;i<Constant.BOT_NUM;i++) {
	    		freeBot.addElement(new Integer(i));
	    	}
    		int botPos=Common.getRandomInt(0,freeBot.size()-1);
	        int picNum=((Integer)freeBot.elementAt(botPos)).intValue();
    		freeBot.removeElementAt(botPos);
	        AIPlayer client2= new AIPlayer(picNum,Constant.BOT_NAME[picNum]);

	        botPos=Common.getRandomInt(0,freeBot.size()-1);
	        picNum=((Integer)freeBot.elementAt(botPos)).intValue();
	        freeBot.removeElementAt(botPos);
	        AIPlayer client3= new AIPlayer(picNum,Constant.BOT_NAME[picNum]);
	    	
	        botPos=Common.getRandomInt(0,freeBot.size()-1);
	        picNum=((Integer)freeBot.elementAt(botPos)).intValue();
	        freeBot.removeElementAt(botPos);
	        AIPlayer client4= new AIPlayer(picNum,Constant.BOT_NAME[picNum]);

	        engine.setDemoMode(demoMode);
	        engine.join(client1,1);        
	        engine.join(client2,1);
	        engine.join(client3,1);
	        engine.join(client4,1);
	        ((EngineFA) engine).startOffline();
    	}
    }
    
    
    public void startSinglePlayer(int p_demoMode) {
        demoMode=p_demoMode;
        playMode=0;
        Runtime.getRuntime().gc();
        Thread thread= new Thread(new SinglePlayerThread(this));
        thread.start();
    }

    public void startSinglePlayerDebug(int p_demoMode, int speedDebug) {
        demoMode=p_demoMode;
        playMode=0;
        //display.setCurrent(null,new Image())
        Runtime.getRuntime().gc();
        client1 = new Series60Client (this,0,speedDebug);
        client1.setDemoMode(demoMode);
    	display.setCurrent(client1 );
    	Thread thread= new Thread() {
    		public void run() {
    	    	engine = new EngineFA();
    	        AIPlayer client2= new AIPlayer(1,"Ivy");
    	        AIPlayer client3= new AIPlayer(2,"Susan");
    	        AIPlayer client4= new AIPlayer(3,"Anna");
    	        engine.setDemoMode(demoMode);
    	        engine.join(client1,1);        
    	        engine.join(client2,1);
    	        engine.join(client3,1);
    	        engine.join(client4,1);
    	        ((EngineFA) engine).startOffline();
    		}
    	};
    	thread.start();
    }

    void privateNetworkCleanUp() {
    	//System.out.println("mjgame.privatenetworkcleanup");
    	privateNetworkCanvas.engine.quitGame();
    	privateNetworkCanvas.doCleanUp();    	
    	if (client1!=null) {
        	client1.doCleanUp();
        	client1=null;
    	}
    }

    
    void publicNetworkCleanUp() {
    	System.out.println("mjgame.publicnetworkcleanup");
    	publicNetworkScreen=0;
    	
    	if (publicNetworkCanvas.screen==2) {
        	System.out.println("before calling engine.quitgame");
    		publicNetworkCanvas.engine.quitGame();
    	}

    	publicNetworkCanvas.engine.doCleanUp();    	

    	if (client1!=null) {
        	client1.doCleanUp();
        	client1=null;
    	}
    }
    
    public void handleSecurityException(int type) {
    	if (type==Constant.SECURITY_EXCEPTION_SMS) {
    		showAlertAndExit("Please set your mobile to allow SMS access for this application. Application will exit",4000);
    	}
    	else if (type==Constant.SECURITY_EXCEPTION_NETWORK) {
    		showAlertAndExit("Please set your mobile to allow network access for this application. Application will exit",4000);    	
    	}
    	else if (type==Constant.SECURITY_EXCEPTION_BT) {
    		showAlertAndExit("Please set your mobile to allow Bluetooth access for this application. Application will exit",4000);    	    	
    	}
    }
    
	private void showAlertAndExit(String message, int milis) {
		
		Alert alert= new Alert("",message,null, AlertType.INFO);
		alert.setTimeout(Alert.FOREVER);
        display.setCurrent(alert);
        System.out.println("showAlert");
        try {
	        Thread.sleep(milis);
	        this.close();
	        return;
        }
        catch (InterruptedException e) {
        }
	}

    
}
