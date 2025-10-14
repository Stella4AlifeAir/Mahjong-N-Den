/*
 * Created on 1/11/2004
 * Last modified 1/31/2005
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pheephoo.mjgame.ui;

import com.pheephoo.mjgame.engine.*;
import com.pheephoo.mjgame.messaging.SMSEngine;
import com.pheephoo.mjgame.messaging.SMSSendException;
import com.pheephoo.mjgame.network.*;
import com.pheephoo.mjgame.*;

import javax.microedition.lcdui.*;
import java.io.IOException;
import java.util.*;


public class  PrivateNetworkCanvas extends Canvas {

	private int debugLevel=1000;//5=show player move  7=show game summary only

	
    //constant used in PrivateNetworkCanvas...
    //put here to save space
    public static int PRV_ACCEPTINVITATION=100;
    public static int PRV_REJECTINVITATION=101;
    public static int PRV_ACCEPTCONTACT=200;
    public static int PRV_REJECTCONTACT=201;
    public static int PRV_REJOINYES=300;
    public static int PRV_REJOINNO=301;
    public static int PRV_TRANSACTION_OK=302;
    public static int PRV_TRANSACTION_CANCEL=303;
	
    private String nick="";
    boolean flagNoticeNoContact=false;
    
    int isWaitingForOurResponse=0;//if the value is 1, do not redraw the screen
    
	//14/02/2005
    int invitedTableNum=0;
    int oriCid=0;//originating user that send request to be a buddy
    
    //reference to main midlet
    MJGame midlet;
    
    public EngineProxy engine;

	
	//2/2/2005
	//priv network
	Vector invitedPlayer=new Vector();
	
	
	
    Graphics osg;   //we draw using this offscreen graphics object
    Graphics osg2;
    Graphics osg3;
    Image offScreenBuffer;
    Image onlineIcon;
    Image busyIcon;
    Image networkBg;
    Image networkBg2;
    Image networkButton;

    private Image tableInfo;
    private Image tableStartedIcon;
    private Image waitingHostImage;
    private Image waitingNotHostImage;

    
    boolean animated=true;

    int swidth=0;
    int sheight=0;
    
    boolean startProgressBar=false;//06/01/2005
    
    public Vector contacts= new Vector();
    

    
    //for escaping previous input
    long lastInputTime=0;

    Setting setting=new Setting();
    int minDouble;
    int maxDouble;

    boolean isAnimThreadFinished=false;
    

    private int currentRowSelection=0;//for drawing table and highlight the table
    private int currentRowScreen=0;//for drawing table and highlight the table
    private int numberOfRow=0;
    private int scrollPegOffset=0;//for drawing the scroll peg

	int currentPage=0;

    public int screen=0; //0=Contact list   1=Our own table 2= play started
    
    boolean isRoomOwner=false;
    
    boolean isDrawingPublic=false;//for drawing synchronization
    
    int inputState=0;//06/01/2005.    0=disable, 1=enable
    
	public PrivateNetworkCanvas(MJGame m) {
		debug(2,"constructor");
    	this.setFullScreenMode(true);
    	swidth=getWidth();
    	sheight=getHeight();
    	midlet=m;

    	offScreenBuffer = Image.createImage(getWidth(), getHeight());
    	osg = offScreenBuffer.getGraphics();
        try {
        	networkBg=Image.createImage("/res/private_net1.jpg");
        	networkBg2=Image.createImage("/res/private_net2.jpg");
        	onlineIcon=Image.createImage("/res/onlineico.png");
        	busyIcon=Image.createImage("/res/busyico.png");
        	tableInfo=Image.createImage("/res/table_info_txt.png");
        	waitingHostImage=Image.createImage("/res/waiting_host.png");
        	waitingNotHostImage=Image.createImage("/res/waiting_nothost.png");
        }
        catch (IOException ie) {
        	//System.out.println("file not found");
        }
        reloadSetting();
	}
	
    protected  void paint(Graphics graphics) {
    	graphics.drawImage(offScreenBuffer, 0, 0, Graphics.LEFT | Graphics.TOP);
    }

    //network related
    //onLogin... stop the main animation
    //get the nick from database
    public void onLogin(String motd,String _nick){
    	debug(0,"onLogin()");
    	nick=_nick;
        midlet.commandReceived(Constant.PRIVATE_DISPLAY_MOTD, "Hello " + _nick+ "\n"+motd);
    }


	
	
    protected  void keyPressed(int keyCode) {
    	debug(0,System.currentTimeMillis()+"::keyPressed():: keyCode="+keyCode);
    	
		//escaping previous input if the class is still loading or multiple pressed
		if (System.currentTimeMillis()-200<lastInputTime || inputState==0) {
			return;
		}

        switch (keyCode) {    	        
        	case -6://case KEY_SOFTKEY1:
        		if (screen==0) {
                	if (invitedPlayer.size()==0) {
                        midlet.commandReceived(Constant.PRIVATE_ERRSTART1);           		
                	}
                	else if (invitedPlayer.size()>3) {
                        midlet.commandReceived(Constant.PRIVATE_ERRSTART2);           		
                	}
                	else {
        	        	createRoomHelper();
                	}
        		}
            	else if (screen==1) {
            		if (isRoomOwner ) {
            			screen=2;
            			forceStart();
            		}
            	}
        		break;
        	case -7://case KEY_SOFTKEY2:
        		if (screen==0) {
                    midlet.commandReceived(Constant.PRIVATE_LPAUSE);   
        		}
        		else if (screen==1) {
            		screen=0;
            		quitRoomHelper();
            		if (!isRoomOwner) {
            			drawContact();
            		}
        		}
        		else if (screen==2) {
        			midlet.commandReceived(Constant.NETWORKCOMMON_PAUSE);
        		}
        		break;
            case -1: //case KEY_UP_ARROW:
            	if (screen==0) {
                	currentRowSelection--;
                	currentRowScreen--;
    				if (currentRowSelection<0)
    					currentRowSelection=0;
    				if (currentRowScreen<0)
    					currentRowScreen=0;
    				drawContact();
            	}
    			break;
            case -2://case KEY_DOWN_ARROW:
            	if (screen==0) {
                	currentRowSelection++;
                	currentRowScreen++;
    				if (currentRowSelection==numberOfRow)
    					currentRowSelection=numberOfRow-1;
    				if (currentRowScreen==DeviceConstant.PRIVATE_CONTACTLIST_NUM_OF_ROW_PERSCREEN)
    					currentRowScreen=DeviceConstant.PRIVATE_CONTACTLIST_NUM_OF_ROW_PERSCREEN-1;
    				if (currentRowScreen>=numberOfRow) {
    					currentRowScreen=numberOfRow-1;
    				}
    				drawContact();
            	}
                break;
            case -3: //case KEY_LEFT_ARROW:
            	if (screen==0) {
                	currentRowSelection-=DeviceConstant.PRIVATE_CONTACTLIST_NUM_OF_ROW_PERSCREEN;
                	currentRowScreen=0;
    				if (currentRowSelection<0)
    					currentRowSelection=0;	    				
    				drawContact();
            	}
            	break;
            case -4: //case KEY_RIGHT_ARROW:
            	if (screen==0) {
                	currentRowSelection+=DeviceConstant.PRIVATE_CONTACTLIST_NUM_OF_ROW_PERSCREEN;
                	currentRowScreen=DeviceConstant.PRIVATE_CONTACTLIST_NUM_OF_ROW_PERSCREEN-1;
    				if (currentRowSelection>=numberOfRow)
    					currentRowSelection=numberOfRow-1;
    				if (currentRowScreen>=numberOfRow) {
    					currentRowScreen=numberOfRow-1;
    				}
    				drawContact();
            	}
                break;
            case -5://case KEY_SOFTKEY3:
            	if (screen==0) {
            		//currentRowSelection
            		inviteUser();
            		drawContact();                	
            	}
            	else if (screen==1) {
            		if (isRoomOwner ) {
            			screen=2;
            			forceStart();
            		}
            		else {
                		////System.out.println("keypressed.quitroomhelper");                			
                		screen=0;
                		quitRoomHelper();
                		////System.out.println("finished keypressed.quitroomhelper");
            		}
            	}
            	break;
	        default:
	            ////System.out.println("nothing pressed");

        }  		
    		
    	////System.out.println("DEBUG:keyPressed():input mode= "+ inputMode);
    	////System.out.println("finishing keyPressed");
        repaint();
        serviceRepaints();

    }

    public void doContinue() {
    	
    }
    
    
    public void onCommunicationError() {
    	////System.out.println("PrivateNetworkCanvas.onCommunicationError()");
    	animated=false;
    	midlet.commandReceived(Constant.NETWORKCOMMON_COMMUNICATIONERROR);
    }
    
    private void drawContact() {
    	debug(0,"drawContact() ::currentRowSelection="+currentRowSelection + " ;currentRowScreen="+currentRowScreen);
        Vector p_contacts=contacts;
    	if (currentRowSelection==0) {
    		currentRowScreen=0;
    	}
		
    	if (screen!=0 || p_contacts==null) {
    		debug(0,"drawContact(). skip drawing");
    		return;
    	}
    	numberOfRow=p_contacts.size();
    	debug(0,"drawContact()::numberOfRow="+numberOfRow);
    	if (currentRowSelection>=numberOfRow || currentRowScreen>=numberOfRow) {
    		debug(0,"***moving the pointer up!");
    		if (numberOfRow!=0) {
        		currentRowSelection=numberOfRow-1;
        		currentRowScreen=currentRowSelection;
    		}
    	}
    	
    	Contact contact;
        drawBackground();
    	int onlineStartPos=DeviceConstant.PRIVATE_CONTACTLIST_STARTPOS_V;
    	int maxRow=DeviceConstant.PRIVATE_CONTACTLIST_NUM_OF_ROW_PERSCREEN;
    	int scrollerHeight=CanvasHelper.scrollBar.getHeight();
    	if (numberOfRow>maxRow) {
    		//draw the scroll bar
    		osg.drawImage(CanvasHelper.scrollBar,swidth-CanvasHelper.scrollBar.getWidth(),sheight-DeviceConstant.BUTTON_HEIGHT-scrollerHeight,Graphics.LEFT|Graphics.TOP);
    		if (currentRowScreen==0 || currentRowScreen==maxRow-1) {
    			scrollPegOffset=( currentRowSelection*(scrollerHeight-CanvasHelper.scrollPeg.getHeight() )) /(numberOfRow-1);
    		}
    		osg.drawImage(CanvasHelper.scrollPeg,swidth-CanvasHelper.scrollBar.getWidth(),sheight-DeviceConstant.BUTTON_HEIGHT-scrollerHeight+scrollPegOffset,Graphics.LEFT|Graphics.TOP);
    	}

	   	int pointerWidth;
		if (numberOfRow>maxRow) {
			pointerWidth=DeviceConstant.PRIVATE_CONTACTLIST_POINTER_WIDTH;
		}
		else {
			pointerWidth=swidth;
		}

		debug(0,"currentRowSelection="+currentRowSelection);
    	osg.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN,Font.SIZE_SMALL));
    	for (int i=0;i<numberOfRow;i++) {
    		debug(0,"numberOfRow"+numberOfRow);
    		contact = (Contact) p_contacts.elementAt(i);
    		int vpos=onlineStartPos+i*DeviceConstant.PRIVATE_CONTACTLIST_SPACING_V;
    		vpos=vpos-(currentRowSelection-currentRowScreen)*DeviceConstant.PRIVATE_CONTACTLIST_SPACING_V;
    		if (vpos<onlineStartPos || vpos>=onlineStartPos +DeviceConstant.PRIVATE_CONTACTLIST_SPACING_V*maxRow) {
    			continue;
    		}
    		debug(0,"currentSelection="+currentRowSelection+"; vpos="+vpos+ ";i="+i);
    		
    		//draw highlight pointer
    		if (currentRowSelection==i) {
    			debug(0,"vpos="+vpos);
    			osg.setColor(45,45,45);
    			osg.fillRect(DeviceConstant.PRIVATE_CONTACTLIST_POINTER_HPOS,vpos,pointerWidth,DeviceConstant.PRIVATE_CONTACTLIST_POINTER_HEIGHT);
    			osg.setColor(0,0,0);
    			osg.drawRect(DeviceConstant.PRIVATE_CONTACTLIST_POINTER_HPOS,vpos,pointerWidth,DeviceConstant.PRIVATE_CONTACTLIST_POINTER_HEIGHT);
    		}
    		if (contact.status==1) {
    			if (contact.selected) {
        			////System.out.println("draw selected color");
    				osg.setColor(167,142,120);
        			osg.fillRect(DeviceConstant.PRIVATE_CONTACTLIST_POINTER_HPOS,vpos,pointerWidth,DeviceConstant.PRIVATE_CONTACTLIST_POINTER_HEIGHT);
    			}
				osg.drawImage(onlineIcon,DeviceConstant.PUBLICTABLE_ICONPOSH,vpos+DeviceConstant.PRIVATE_CONTACTLIST_ICONPOSV,Graphics.LEFT | Graphics.TOP) ;
	    		osg.setColor(255,255,255);
    		}
			else if (contact.status==2) {
				invitedPlayer.removeElement(contact.user+"");
				osg.drawImage(busyIcon,DeviceConstant.PUBLICTABLE_ICONPOSH,vpos+DeviceConstant.PRIVATE_CONTACTLIST_ICONPOSV,Graphics.LEFT | Graphics.TOP) ;
	    		osg.setColor(255,255,255);
			}
			else {
    			osg.setColor(150,150,150);
			}
    		osg.drawString(contact.nick ,DeviceConstant.PUBLICTABLE_POS1H,vpos,Graphics.LEFT | Graphics.TOP) ;    		

    	}

		repaint();
		serviceRepaints();
        debug(0,"drawTable() finished");
    	
    	
    }
    
    private void drawBackground() {
    	debug(0,"drawBackground()");
    	osg.drawImage(CanvasHelper.backgroundImage,0,0,Graphics.LEFT|Graphics.TOP);
    	
    	if (screen==0) {
        	osg.drawImage(networkBg,0,0,Graphics.LEFT|Graphics.TOP);
    		osg.drawImage(CanvasHelper.getButtonImage(1),0,sheight-DeviceConstant.BUTTON_HEIGHT,Graphics.LEFT|Graphics.TOP);
	
    	}
    	else if (screen==1){
        	osg.drawImage(networkBg2,0,0,Graphics.LEFT|Graphics.TOP);
    		osg.drawImage(tableInfo,0,networkBg2.getHeight(),Graphics.LEFT|Graphics.TOP);
    		if (isRoomOwner) {
    			int hpos=(swidth-waitingHostImage.getWidth())/2;
    			int vpos=networkBg2.getHeight()+tableInfo.getHeight()+10;
    			osg.drawImage(waitingHostImage,hpos,vpos,Graphics.LEFT|Graphics.TOP);
        		osg.drawImage(CanvasHelper.getButtonImage(2),0,sheight-DeviceConstant.BUTTON_HEIGHT,Graphics.LEFT|Graphics.TOP);
    		}
    		else {
    			int hpos=(swidth-waitingHostImage.getWidth())/2;
    			int vpos=networkBg2.getHeight()+tableInfo.getHeight()+10;
    			osg.drawImage(waitingNotHostImage,hpos,vpos,Graphics.LEFT|Graphics.TOP);
        		osg.drawImage(CanvasHelper.getButtonImage(3),0,sheight-DeviceConstant.BUTTON_HEIGHT,Graphics.LEFT|Graphics.TOP);
    		}
    	}
    }
    
    public void reloadSetting() {
    	////System.out.println("PrivateNetwork.reloadSetting()");
    	Setting.loadSetting();
    	minDouble=Setting.minDouble;
    	maxDouble=Setting.maxDouble;
    }
    

    private void drawWaitingRoomFigure(Table table) {
    	debug(0,"drawWaitingRoomFigure");
        int vpos=DeviceConstant.PUBLIC_TABLELIST_STARTPOS_V;
		if (table.tableNumber<10) {
			osg.drawImage(CanvasHelper.getNumberedImage(table.tableNumber),DeviceConstant.PUBLICTABLE_POS1H,vpos,Graphics.LEFT | Graphics.TOP) ;    			    			    			    			
		}
		else if (table.tableNumber<100){
			int firstDigit= table.tableNumber/10;
			int secondDigit=table.tableNumber%10;
			osg.drawImage(CanvasHelper.getNumberedImage(firstDigit),DeviceConstant.PUBLICTABLE_POS1H-DeviceConstant.PUBLIC_TABLELIST_NUMOFFSET,vpos,Graphics.LEFT | Graphics.TOP) ;    			    			    			    			        			
			osg.drawImage(CanvasHelper.getNumberedImage(secondDigit),DeviceConstant.PUBLICTABLE_POS1H,vpos,Graphics.LEFT | Graphics.TOP) ;    			    			    			    			        			
		}
		else {
			int firstDigit= table.tableNumber/100;
			int temp=table.tableNumber/10;
			int secondDigit=temp%10;
			int thirdDigit=table.tableNumber%10;
			
			osg.drawImage(CanvasHelper.getNumberedImage(firstDigit),DeviceConstant.PUBLICTABLE_POS1H-DeviceConstant.PUBLIC_TABLELIST_NUMOFFSET*2,vpos,Graphics.LEFT | Graphics.TOP) ;    			    			    			    			        			
			osg.drawImage(CanvasHelper.getNumberedImage(secondDigit),DeviceConstant.PUBLICTABLE_POS1H-DeviceConstant.PUBLIC_TABLELIST_NUMOFFSET,vpos,Graphics.LEFT | Graphics.TOP) ;    			    			    			    			        			
			osg.drawImage(CanvasHelper.getNumberedImage(thirdDigit),DeviceConstant.PUBLICTABLE_POS1H,vpos,Graphics.LEFT | Graphics.TOP) ;    			    			    			    			        			
		}
		osg.drawImage(CanvasHelper.getNumberedImage(table.minDouble),DeviceConstant.PUBLICTABLE_POS2H,vpos,Graphics.LEFT | Graphics.TOP) ;
		osg.drawImage(CanvasHelper.getNumberedImage(table.maxDouble),DeviceConstant.PUBLICTABLE_POS3H,vpos,Graphics.LEFT | Graphics.TOP) ;
		osg.drawImage(CanvasHelper.getNumberedImage(table.numOfPlayer),DeviceConstant.PUBLICTABLE_POS4H,vpos,Graphics.LEFT | Graphics.TOP) ;
    }
    
    
    private void quitRoomHelper() {
    	////System.out.println("PrivateNetworkCanvas.quitRoomHelper() start");    	
    	screen=0;
    	isWaitingForOurResponse=0;
    	drawContact();
    	engine.quitGame();
    	////System.out.println("PrivateNetworkCanvas.quitRoomHelper() finish");    	

    }


    private void rejoinNoHelper() {
    	////System.out.println("PrivateNetworkCanvas.rejoinNoHelper");    	
    	midlet.getDisplay().setCurrent(this);
    	isWaitingForOurResponse=0;
    	repaint();
    	serviceRepaints();    	
    }
    
    private void acceptContactHelper() {
    	debug(2,"acceptContactHelper()");    	
    	engine.acceptContact(oriCid);
    }
    private void rejectContactHelper() {
    	////System.out.println("PrivateNetworkCanvas.rejectContactHelper");    	
    	midlet.getDisplay().setCurrent(this);
    	isWaitingForOurResponse=0;
    	repaint();
    	serviceRepaints();
    	engine.rejectContact(oriCid);
    }

    
    
    private void rejectInvitationHelper() {
    	////System.out.println("PrivateNetworkCanvas.rejectInvitationHelper");    	
    	midlet.getDisplay().setCurrent(this);
    	isWaitingForOurResponse=0;
    	repaint();
    	serviceRepaints();
    	engine.rejectInvitation(invitedTableNum);
    }
    
    
    
    private void joinRoomHelper(int invitedTableNum) {
    	screen=1;
    	isRoomOwner=false;

    	//this.setFullScreenMode(true);
    	////System.out.println("PrivateNetworkCanvas.joinRoomHelper");    	
    	////System.out.println("invitedtablenumber="+invitedTableNum);    	

    	midlet.getDisplay().setCurrent(this);
    	
    	repaint();
    	serviceRepaints();
    	engine.joinPublicRoom(invitedTableNum,Setting.picture,nick);
    }
    
    public void  onResponseOtherJoin(Table table) {

    	isDrawingPublic=true;
    	//System.out.println("PrivateNetworkCanvas.onResponseOtherJoin() start");
    	//System.out.println("*****screen="+screen);
    	
    	drawWaitingRoomFigure(table);
    	
    	//System.out.println("try repaint");

    	repaint();
    	serviceRepaints();
    	//System.out.println("PrivateNetworkCanvas.onResponseOtherJoin() finished");
    	isDrawingPublic=false;
		lastInputTime=System.currentTimeMillis();
		
    }
    
    public void onResponseContactListAddDelete(int mode, int cid, String nick) {

    	//delete mode...temporarily not used
    	if (mode==0) {
        	for (int i=0;i<contacts.size();i++) {
        		Contact contact=(Contact) contacts.elementAt(i);
        		if (contact.user==cid) {
        			contacts.removeElementAt(i);
        		}
        	}    		
    	}
    	else {
    		char firstLetter=nick.charAt(0);
    		int size=contacts.size();
        	
    		for (int i=0;i<size;i++) {
        		Contact contact=(Contact) contacts.elementAt(i);
        		if (contact.nick.charAt(0)>firstLetter) {
        			contacts.addElement(new Contact(cid,1,nick));
        			break;
        		}
        		else if (i==size-1) {
        			contacts.addElement(new Contact(cid,1,nick));
        			break;
        		}
        	}    		
    		if (size==0) {
    			contacts.addElement(new Contact(cid,1,nick));
    		}
        	
    	}
    	drawContact();
    }
    
    public void onResponseContactList(Vector p_contact) {
        debug(2,"onResponseContactList()");
        
    	contacts=p_contact;
    		
//    	//debug... set some player on

    	Contact temp=new Contact(0,1,"bot");
    	contacts.insertElementAt(temp,0);

    	/*
    	for (int i=0;i<contacts.size();i++) {
    		Contact contact=(Contact) contacts.elementAt(i);
    		if (i==0 ) {
    			contact.status=1;
    		}
    	}*/
    	
        animated=false;
        updateContact(p_contact);
        drawContact();
    	this.inputState=1;
		lastInputTime=System.currentTimeMillis();
		 
    }
    
    private void updateContact(Vector p_contact) {
    	contacts=p_contact;

    	//System.out.println("updateContact start");
    	int size=contacts.size();
    	boolean isFound=false;
    	for (int i=0;i<invitedPlayer.size();i++) {
        	isFound=false;
    		for (int j=0;j<size;j++) {
        		Contact contact = (Contact) contacts.elementAt(j);
        		if (invitedPlayer.elementAt(i).equals(contact.user+"")) {
        			contact.selected=true;
        			isFound=true;
        			break;
        		}
        	}
    		if (!isFound) {
    			invitedPlayer.removeElement(invitedPlayer.elementAt(i));
    			i--;
    		}
    	}
    	//System.out.println("updateContact finish");

    	
    	if (contacts.size()==0 && !flagNoticeNoContact) {
        	Alert alert=null;
            alert= new Alert("","You don't have any mahjong buddies. Please invite your friends",null, AlertType.INFO);
        	midlet.getDisplay().setCurrent(alert);       	        	
        	//System.out.println("PrivateNetworkCanvas.onInviteAnswered() finished");
        	flagNoticeNoContact=true;
    	}
    	
    }
    
    public void onResponseJoinPublicRoom(Table tempTable) {
    	if (tempTable.numOfPlayer==4) {
    		return;
    	}
    	drawBackground();
    
    	drawWaitingRoomFigure(tempTable);

    	repaint();
		serviceRepaints();
		lastInputTime=System.currentTimeMillis();
    }
    
    public  void onResponseCreateRoom(int tableNumber) {
    	debug(0,"onResponseCreateRoom()::TableCreated.tablenum="+ tableNumber);
    	CanvasHelper.stopNetworkAnimation();
    	midlet.getDisplay().setCurrent(this);
    	drawBackground();
        Table tmptable= new Table(Setting.minDouble,Setting.maxDouble,1,tableNumber);
        drawWaitingRoomFigure(tmptable);
		lastInputTime=System.currentTimeMillis();
		repaint();
		serviceRepaints();
    }
    
    public void onResponseRoomUpdate(Table table) {
    	debug(0,"onResponseRoomUpdate()");
    	
    	if (screen==0) {
    		return;
    	}
    	
    	if (!this.isRoomOwner) {
        	CanvasHelper.stopNetworkAnimation();
        	midlet.getDisplay().setCurrent(this);
    	}

    	drawBackground();
        drawWaitingRoomFigure(table);
		lastInputTime=System.currentTimeMillis();
    	repaint();
    	serviceRepaints();
    }

    
    private void createRoomHelper() {
    	debug(0,"createRoomHelper()");
    	screen=1;
		isRoomOwner=true;    	
    	engine.createPrivateRoom(Setting.minDouble,Setting.maxDouble,Setting.picture,invitedPlayer,Setting.nick);    	
    }
    
    public void onAcceptInvitation() {
    	
    }
    
    public void onDialogResponse(int choice) {
    	debug(2,"onDialogResponse");
    	if (choice==PrivateNetworkCanvas.PRV_ACCEPTINVITATION) {
    		debug(2,"onDialogResponse:: choice prv_acceptinvitation");
    		joinRoomHelper(invitedTableNum);
    		
    	}
    	else if (choice==PrivateNetworkCanvas.PRV_REJECTINVITATION)  {
    		debug(2,"onDialogResponse:: choice prv_rejectinvitation");    		
    		rejectInvitationHelper();
    		
    	}
    	else if (choice==PrivateNetworkCanvas.PRV_ACCEPTCONTACT)  {
    		debug(2,"onDialogResponse:: choice prv_acceptcontact");    		
    		acceptContactHelper();    		
    	}
    	else if (choice==PrivateNetworkCanvas.PRV_REJECTCONTACT)  {
    		debug(2,"onDialogResponse:: choice prv_rejectcontact");    		
    		rejectContactHelper();    		
    	}
    	else if (choice==PrivateNetworkCanvas.PRV_REJOINYES)  {
    		debug(2,"onDialogResponse:: choice prv_rejoinyes");    		
    		joinRoomHelper(invitedTableNum);
    	}
    	else if (choice==PrivateNetworkCanvas.PRV_REJOINNO)  {
    		debug(2,"onDialogResponse:: choice prv_rejoinno");    		
    		rejoinNoHelper();    		
    	}
    	else if (choice==PrivateNetworkCanvas.PRV_TRANSACTION_OK) {
    		engine.sendSMSPrivate();
    		debug(2,"transactionOK");
    	}
    	else if (choice==PrivateNetworkCanvas.PRV_TRANSACTION_CANCEL) {
    		debug(2,"transactionCancel");
    		midlet.commandReceived(Constant.NETWORKCOMMON_QUITGAME);
    	}

    	
    }
    
    public void onPendingEvent(int eventType, int p_oriCid,String msisdn, String p_nick, int tableNum) {
    	debug(2,"onPendingEvent() ::eventType="+eventType+" ;msisdn="+msisdn+" ;nick="+p_nick);    	
    	oriCid=p_oriCid;//need to store this variable into global variable to be read by other method later

    	if (eventType==1) {
    	  	Dialog dialog = new Dialog(this,"contact",p_nick + " (" +msisdn + ") has requested you to be his mahjong buddy =)","Accept","Reject",PrivateNetworkCanvas.PRV_ACCEPTCONTACT,PrivateNetworkCanvas.PRV_REJECTCONTACT);
        	midlet.lastForm=dialog;
    	}
    	else if (eventType==2) {
        	Alert alert= new Alert("","Your friend ("+ msisdn+  " ) has rejected to be your mahjong buddy ",null, AlertType.INFO);
        	alert.setTimeout(Alert.FOREVER);
        	midlet.lastForm=alert;
    	}
    	else if (eventType==20) {
        	Alert alert= new Alert("","Your friend "+ p_nick+ " ("+ msisdn+  " ) has deleted you from his contact list",null, AlertType.INFO);
        	alert.setTimeout(Alert.FOREVER);
        	midlet.lastForm=alert;
    	}
    	else if (eventType==30) {
    	  	invitedTableNum=tableNum;
    	  	Dialog dialog = new Dialog(this,"rejoin",  "Would you like to rejoin your last game?","Yes","No",PrivateNetworkCanvas.PRV_REJOINYES,PrivateNetworkCanvas.PRV_REJOINNO);
        	midlet.lastForm=dialog;
    	}
    }
    
    /*
     * to be called from DeleteForm
     */
    public void deleteContact(boolean[] contactstatus, int numOfSelection) {
    	//System.out.println("PrivateNetworkCanvas.deleteContact:: contactsize="+contacts.size());
    
    	int cidlist[]=new int[numOfSelection];
    	int j=0;
    	for (int i=0;i<contacts.size();i++) {
    		if (contactstatus[i]) {
    			Contact contact= (Contact) contacts.elementAt(i);
    			cidlist[j++]=contact.user;
    		}
    	}
    	engine.deleteContact(cidlist);

    	for (int i=0;i<contacts.size();i++) {
    		Contact contact= (Contact) contacts.elementAt(i);   
    		for (int k=0;k<cidlist.length;k++) {
    			if (contact.user==cidlist[k]) {
    				contacts.removeElementAt(i);
    				i--;
    				break;
    			}
    		}
    	}

    	drawContact();
    	
    }
    
    
    public void onInviteAnswered(int choice, int contactId) {
    	debug(2,"onInviteAnswered:: choice="+choice+" ;contactId="+contactId);
    	Alert alert=null;
    	String name=getUserName(contactId);

    	if (choice==0) {
        	alert= new Alert("",name + " has rejected your invitation",null, AlertType.INFO);
    	}
    	else if (choice==1){
        	alert= new Alert("",name + " has joined your table",null, AlertType.INFO);    		
    	}
    	else if (choice==2) {
        	alert= new Alert("",name + " has left your table",null, AlertType.INFO);    		    		
    	}
    	//host close the table
    	else if (choice==3) {
        	screen=0;
        	isWaitingForOurResponse=0;
        	drawContact();
        	repaint();
        	serviceRepaints();
        	
        	alert= new Alert("","host has closed the table",null, AlertType.ERROR);
        	
    	}    	
    	alert.setTimeout(Alert.FOREVER);
    	midlet.getDisplay().setCurrent(alert,this);       	        	
    	//System.out.println("PrivateNetworkCanvas.onInviteAnswered() finished");
    	
    	if (contacts.size()==0) {
    		engine.getPendingEvent();
    	}
    }
    
    private String getUserName(int id) {
    	String username="";
    	
    	for (int i=0;i<contacts.size();i++) {
    		Contact contact=(Contact) contacts.elementAt(i);
    		if (contact.user==id) {
    			username=contact.nick;
    			break;
    		}
    	}

    	return username;
    }
    
    public void onInvite(int originate, int tableNum,int minDbl, int maxDbl) {
    	//System.out.println("originate="+originate+" ;tableNum="+tableNum+" ;minDbl="+minDbl+" ;maxDbl="+maxDbl);
    	
    	while (isWaitingForOurResponse==1) {
    		try {
    			Thread.sleep(1000);
    		}
    		catch (InterruptedException e) {
    		}
    	}
    	
    	isWaitingForOurResponse=1;
    	invitedTableNum=tableNum;
    	
    	//System.out.println("PVT.onInvite():::originate="+originate+" ;minDbl="+minDbl+" ;maxDbl="+maxDbl);
    	
    	//get username according to originate number
    	String username=getUserName(originate);
    	Dialog dialog = new Dialog(this,"invite",username+ " has invited you to play mahjong\nMin Double= "+minDbl+" \nMax Double="+maxDbl   ,"Accept","Reject",PrivateNetworkCanvas.PRV_ACCEPTINVITATION,PrivateNetworkCanvas.PRV_REJECTINVITATION);
    	midlet.getDisplay().setCurrent(dialog);

    }
    
    public void addContact(String msisdn1, String msisdn2, String msisdn3) {
    	//System.out.println("PrivateNetworkCanvas.addContact start");
    	
    	//send addcontact message to engine
    	engine.addContact(msisdn1,msisdn2,msisdn3);
    	//System.out.println("PrivateNetworkCanvas.addContact finished");
    }
    
    
    public void inviteUser() {
    	//check if user if is online
    	//System.out.println("PrivateNetworkCanvas.inviteUser()");
    	if (currentRowSelection<contacts.size()) {
    		Contact contact = (Contact) contacts.elementAt(currentRowSelection);
    		if (contact.status==1) {
    			if (invitedPlayer.contains(contact.user+"")) {
    				invitedPlayer.removeElement(contact.user+"");
    				contact.selected=false;
    			}
    			else {
    		    	//System.out.println("PVT.inviteUser() set status selected");
    				invitedPlayer.addElement(contact.user+"");    				
    				contact.selected=true;
    			}
    		}
    	}
    }

    public void onResponseContactListUpdate(int cid, int status) {
    	debug(0,"onResponseContactListUpdate()");
    	int size=contacts.size();
    	Contact temp;
    	for (int i=0;i<size;i++) {
    		temp=(Contact) contacts.elementAt(i);
    		if (temp.user==cid) {
    			temp.status=status;
    			break;
    		}
    	}
    	if (this.screen==0) {
    		drawContact();
        	repaint();
        	serviceRepaints();
    	}
    }

    
    public void onTransaction(int price) {
    	debug(2,"onTransaction");
    	int dollar=price/100;
    	int cent=price%100;
    	
    	String message[]={Constant.MSG_TRANSACTIONPRV1,
    			Constant.MSG_TRANSACTION_PRICE1+dollar + "." + cent+ " " +Constant.MSG_TRANSACTION_PRICE2,
				Constant.MSG_TRANSACTION};
    	Dialog dialog = new Dialog(this,Constant.MSG_TRANSACTION_TITLE1,message,"Ok","Cancel",PrivateNetworkCanvas.PRV_TRANSACTION_OK,PrivateNetworkCanvas.PRV_TRANSACTION_CANCEL);
    	midlet.getDisplay().setCurrent(dialog);    	
    	midlet.lastForm=this;
    	
    }

    
    
    public void onResponseStart() {
    	screen=2;
    	//startProgress();
    	//System.out.println("PrivateNetworkCanvas.onResponseStart()");
    	//System.out.println("cid="+ ((EngineProxy)engine).getCid());
    	animated=false;
    	repaint();
    	serviceRepaints();
    	midlet.commandReceived(Constant.NETWORKCOMMON_STARTALLHUMAN);
    }
        
    
    private void forceStart() {
    	engine.forceStart();
    }
    
    
    public void quitGame() {

    }
    
    public void doCleanUp() {
    	animated=false;
    	startProgressBar=false;
    	engine.doCleanUp();
    }
    
    
    private void debug(int level, String txt) {
    	if (level>=debugLevel)
    		System.out.println("PrivateNetworkCanvas."+txt);
    }    

}
