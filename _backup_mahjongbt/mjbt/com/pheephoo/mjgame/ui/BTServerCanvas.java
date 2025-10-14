/*
 * 
 * Waiting screen for public network
 * 
 * @author Samuel
 * @version 1.00, 24/04/2005
 */

package com.pheephoo.mjgame.ui;

import com.pheephoo.mjgame.engine.*;
import com.pheephoo.mjgame.form.SelectRoomForm;
import com.pheephoo.mjgame.messaging.*;
import com.pheephoo.mjgame.network.Table;
import com.pheephoo.mjgame.*;

import javax.microedition.lcdui.*;
import java.io.IOException;
import java.util.*;

public class  BTServerCanvas extends Canvas {

	private int debugLevel=0;//5=show player move  7=show game summary only
	
	
	
	private int invitedTableNum=0;
	
    public EngineProxy engine;//this field is visible to MJGame midlet
    public String[] roomCategory;//used by SelectRoomForm
    //this field is visible to MJGame midlet
    public int screen=0; //0=Public Network list   1=Our own table 2= play started; 9= payment screen

    private MJGame midlet;    //reference to main midlet

    private Graphics osg;   //we draw using this offscreen graphics object
    private Image offScreenBuffer;
    private int swidth=0;
    private int sheight=0;
    
    private Image networkBg;
    private Image tableInfo;
    private Image tableStartedIcon;
    private Image waitingHostImage;
    private Image waitingNotHostImage;
    private Image newTableImage;
    
    private int currentRowSelection=0;//for drawing table and highlight the table
    private int currentRowScreen=0;//for drawing table and highlight the table
    private int numberOfRow=0;
    private int scrollPegOffset=0;//for drawing the scroll peg
    private Vector tables=new Vector();

    private boolean isRoomOwner=false;//to draw the appropriate button and background according to owner or not owner
    private int inputState=0;//06/01/2005.    0=disable, 1=enable
    private long lastInputTime=0;    //for escaping previous input

    
    public BTServerCanvas(MJGame m) {
		debug(0,"BTServerCanvas()");
    	this.setFullScreenMode(true);
    	swidth=getWidth();
    	sheight=getHeight();
    	midlet=m;
    	offScreenBuffer = Image.createImage(getWidth(), getHeight());
           
    	osg = offScreenBuffer.getGraphics();
        try {
        	networkBg=Image.createImage("/res/public_net.jpg");
        	tableInfo=Image.createImage("/res/table_info_txt.png");
        	waitingHostImage=Image.createImage("/res/waiting_host.png");
        	waitingNotHostImage=Image.createImage("/res/waiting_nothost.png");
        	newTableImage=Image.createImage("/res/new.png");
        }
        catch (IOException ie) {
        	throw new Error();
        }
        
//        drawBackground();
        onResponseCreateRoom(1); //1=table number
		debug(0,"BTServerCanvas() finish");
	}
	
    protected  void paint(Graphics graphics) {
    	graphics.drawImage(offScreenBuffer, 0, 0, Graphics.LEFT | Graphics.TOP);
    }

    public void onLogin(String motd){
    	//debug(0,"onLogin()");
    	drawBackground();    	
        midlet.commandReceived(Constant.PUBLIC_DISPLAY_MOTD,motd);
        repaint();
        serviceRepaints();
    }


    public  void onResponseCreateRoom(int tableNumber) {
    	isRoomOwner=true;
    	CanvasHelper.stopNetworkAnimation();
    	midlet.getDisplay().setCurrent(this);
    	//debug(0,"onResponseCreateRoom()::TableCreated.tablenum="+ tableNumber);
    	drawBackground();
        Table tmptable= new Table(Setting.minDouble,Setting.maxDouble,1,tableNumber);
        drawWaitingRoomFigure(tmptable);
		lastInputTime=System.currentTimeMillis();
		repaint();
		serviceRepaints();
    }

    //invoked by SelectRoomForm()
    public void onResponseRoomSelected(int index) {
    	//debug(0,"onResponseRoomSelected():: index="+index);
        midlet.lastForm=this;
        midlet.getDisplay().setCurrent(this);
        engine.getPublicRoom(index);
    }
    
    public void onResponseRoomAdd(Table table) {
    	//debug(0,"onResponseRoomAdd()");
    	int size=tables.size();
    	Table temp;
    	for (int i=0;i<size;i++) {
    		temp=(Table) tables.elementAt(i);
    		if (table.tableNumber>temp.tableNumber) {
    			tables.insertElementAt(table,i+1);
    			//debug(0,"table added");
    			break;
    		}
    	}    	
    	drawTable();
    }

    public void onResponseRoomUpdate(Table table) {
    	//debug(0,"onResponseRoomUpdate");
    	CanvasHelper.stopNetworkAnimation();
    	midlet.getDisplay().setCurrent(this);
    	int size=tables.size();
    	Table temp;
    	for (int i=0;i<size;i++) {
    		temp=(Table) tables.elementAt(i);
    		if (temp.tableNumber==table.tableNumber) {
    			temp.minDouble=table.minDouble;
    			temp.maxDouble=table.maxDouble;
    			temp.numOfPlayer=table.numOfPlayer;
    			temp.started=table.started;
    			//System.out.println("table updated");
    			break;
    		}
    	}
    	drawBackground();
    	if (this.screen==1) {
    		drawWaitingRoomFigure(table);
    	}
    	else {
    		drawTable();
    	}
    	repaint();
    	serviceRepaints();
    }
    
    public void onResponseRoomDelete(int tableNum) {
    	//debug(0,"onResponseRoomDelete");
    	
    	if (isRoomOwner) {
	    	CanvasHelper.stopNetworkAnimation();
	    	midlet.getDisplay().setCurrent(this);
    	}
    	
    	int size=tables.size();
    	Table table;
    	for (int i=0;i<size;i++) {
    		table=(Table) tables.elementAt(i);
    		if (table.tableNumber==tableNum) {
    			tables.removeElementAt(i);
    			//debug(0,"tableDeleted");
    			break;
    		}
    	}
    	drawTable();
    }
    

    public void onResponseStart() {
    	screen=2;
    	//debug(2,"onResponseStart()");
    	midlet.commandReceived(Constant.NETWORKCOMMON_STARTALLHUMAN);
    }


    

    
    
    private void rejoinNoHelper() {
    	//debug(2,"rejoinNoHelper");    	
    	//isWaitingForOurResponse=0;
    	
    	engine.getRoomCategoryHelper();

    	repaint();
    	serviceRepaints();    	
    }

    private void drawBackground() {
    	//debug(0,"drawBackground()");
    	osg.drawImage(CanvasHelper.backgroundImage,0,0,Graphics.LEFT|Graphics.TOP);
    	osg.drawImage(networkBg,0,0,Graphics.LEFT|Graphics.TOP);
    	osg.drawImage(tableInfo,0,networkBg.getHeight(),Graphics.LEFT|Graphics.TOP);
		if (isRoomOwner) {
			int hpos=(swidth-waitingHostImage.getWidth())/2;
			int vpos=networkBg.getHeight()+tableInfo.getHeight()+10;
			osg.drawImage(waitingHostImage,hpos,vpos,Graphics.LEFT|Graphics.TOP);
    		osg.drawImage(CanvasHelper.getButtonImage(2),0,sheight-DeviceConstant.BUTTON_HEIGHT,Graphics.LEFT|Graphics.TOP);
		}
		else {
			int hpos=(swidth-waitingHostImage.getWidth())/2;
			int vpos=networkBg.getHeight()+tableInfo.getHeight()+10;
			osg.drawImage(waitingNotHostImage,hpos,vpos,Graphics.LEFT|Graphics.TOP);
    		osg.drawImage(CanvasHelper.getButtonImage(3),0,sheight-DeviceConstant.BUTTON_HEIGHT,Graphics.LEFT|Graphics.TOP);
		}
    }
            
    private void drawWaitingRoomFigure(Table table) {
    	//debug(0,"drawWaitingRoomFigure");
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
    	//debug(0,"quitRoomHelper()");    	
    	screen=0;
    	currentRowSelection=0;
    	engine.quitGame();
    }
    
    private void joinRoomHelper(int invitedTableNum) {
    	screen=1;
    	isRoomOwner=false;

    	midlet.getDisplay().setCurrent(this);
    	
    	repaint();
    	serviceRepaints();
    	engine.joinPublicRoom(invitedTableNum,Setting.picture,Setting.nick);
    }

    
    
    private void joinRoomHelper() {
    	debug(0,"joinRoomHelper()");    	

    	Table tempTable = (Table) tables.elementAt(currentRowSelection);
    	
    	//if its a competition room, check if its alredy started or not
    	//user is not allowed to join an inprogress-game

    	
    	if (engine.isCompetitionRoom && tempTable.started==1) {
    		Alert alert= new Alert("","not allowed to join room",null, AlertType.INFO);
        	alert.setTimeout(Alert.FOREVER);
        	currentRowSelection=0;
        	midlet.getDisplay().setCurrent(alert,this);       	        	
        	debug(0,"join room helper...not allowed finished");
            this.screen=0;
    	}
    	else {
        	screen=1;
        	isRoomOwner=false;
        	drawBackground();
        	repaint();
        	serviceRepaints();

    		engine.joinPublicRoom(tempTable.tableNumber,Setting.picture,Setting.nick);
    	}
    }
    
    private void createRoomHelper() {
    	//debug(0,"createRoomHelper");
    	screen=1;
		isRoomOwner=true;    	
    	engine.createPublicRoom(Setting.minDouble,Setting.maxDouble,Setting.picture,Setting.nick);
    }
    
    private void forceStart() {
    	engine.forceStart();
    }

    private void drawTable() {
    	//debug(0,"drawTable()::currentRowSelection="+currentRowSelection + " ;currentRowScreen="+currentRowScreen);
        Vector p_tables=tables;
    	if (currentRowSelection==0) {
    		currentRowScreen=0;
    	}
		
    	if (screen!=0 || p_tables==null) {
    		////debug(0,"drawTable(). skip drawing");
    		return;
    	}
    	numberOfRow=p_tables.size();
    	
    	if (currentRowSelection>=numberOfRow || currentRowScreen>=numberOfRow) {
    		////debug(0,"***moving the pointer up!");
    		currentRowSelection=numberOfRow-1;
    		currentRowScreen=currentRowSelection;
    	}
    	
    	Table table;
        drawBackground();
    	int onlineStartPos=DeviceConstant.PUBLIC_TABLELIST_STARTPOS_V;
    	int maxRow=DeviceConstant.PUBLIC_TABLELIST_NUM_OF_ROW_PERSCREEN;
    	int scrollerHeight=CanvasHelper.scrollBar.getHeight();
    	if (numberOfRow>maxRow) {
    		//draw the scroll bar
    		osg.drawImage(CanvasHelper.scrollBar,swidth-CanvasHelper.scrollBar.getWidth(),sheight-DeviceConstant.BUTTON_HEIGHT-scrollerHeight,Graphics.LEFT|Graphics.TOP);
    		if (currentRowScreen==0 || currentRowScreen==maxRow-1) {
    			scrollPegOffset=( currentRowSelection*(scrollerHeight-CanvasHelper.scrollPeg.getHeight() )) /(numberOfRow-1);
    		}
    		osg.drawImage(CanvasHelper.scrollPeg,swidth-CanvasHelper.scrollBar.getWidth(),sheight-DeviceConstant.BUTTON_HEIGHT-scrollerHeight+scrollPegOffset,Graphics.LEFT|Graphics.TOP);
    	}
    	
    	////debug(0,"currentRowSelection="+currentRowSelection);
    	for (int i=0;i<numberOfRow;i++) {
    		//debug(0,"numberOfRow"+numberOfRow);
    		table = (Table) tables.elementAt(i);
    		int vpos=onlineStartPos+i*DeviceConstant.PUBLIC_TABLELIST_SPACING_V;
    		vpos=vpos-(currentRowSelection-currentRowScreen)*DeviceConstant.PUBLIC_TABLELIST_SPACING_V;
    		if (vpos<onlineStartPos || vpos>=onlineStartPos +DeviceConstant.PUBLIC_TABLELIST_SPACING_V*maxRow) {
    			continue;
    		}
    		//debug(0,"currentSelection="+currentRowSelection+"; vpos="+vpos+ ";i="+i);
    		
    		//draw highlight pointer
    		if (currentRowSelection==i) {
    			////debug(0,"vpos="+vpos);
    			osg.setColor(45,45,45);
    		   	int pointerWidth;
    			if (numberOfRow>maxRow) {
    				pointerWidth=DeviceConstant.PUBLIC_TABLELIST_POINTER_WIDTH;
    			}
    			else {
    				pointerWidth=swidth;
    			}
    			osg.fillRect(DeviceConstant.PUBLIC_TABLELIST_POINTER_HPOS,vpos,pointerWidth,DeviceConstant.PUBLIC_TABLELIST_POINTER_HEIGHT);
    			osg.setColor(0,0,0);
    			osg.drawRect(DeviceConstant.PUBLIC_TABLELIST_POINTER_HPOS,vpos,pointerWidth,DeviceConstant.PUBLIC_TABLELIST_POINTER_HEIGHT);
    		}
			if (table.started==1) {
				osg.drawImage(tableStartedIcon,DeviceConstant.PUBLICTABLE_ICONPOSH,vpos+DeviceConstant.PUBLICTABLE_ICONPOSV,Graphics.LEFT | Graphics.TOP) ;
    		}
    		osg.setColor(184,184,184);
    		    		
    		if (i==0) {
    			osg.drawImage(newTableImage,DeviceConstant.PUBLIC_TABLELIST_NEWHPOS,vpos,Graphics.LEFT | Graphics.TOP) ;    			    			    			
    		}
    		else {
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
    		}
    		
			if (table.minDouble<10) {
    			osg.drawImage(CanvasHelper.getNumberedImage(table.minDouble),DeviceConstant.PUBLICTABLE_POS2H,vpos,Graphics.LEFT | Graphics.TOP) ;    			    			    			    			
			}
			else if (table.minDouble<100){
				int firstDigit= table.minDouble/10;
				int secondDigit=table.minDouble%10;
    			osg.drawImage(CanvasHelper.getNumberedImage(firstDigit),DeviceConstant.PUBLICTABLE_POS2H-DeviceConstant.PUBLIC_TABLELIST_NUMOFFSET,vpos,Graphics.LEFT | Graphics.TOP) ;    			    			    			    			        			
    			osg.drawImage(CanvasHelper.getNumberedImage(secondDigit),DeviceConstant.PUBLICTABLE_POS2H,vpos,Graphics.LEFT | Graphics.TOP) ;
			}

			if (table.maxDouble<10) {
    			osg.drawImage(CanvasHelper.getNumberedImage(table.maxDouble),DeviceConstant.PUBLICTABLE_POS3H,vpos,Graphics.LEFT | Graphics.TOP) ;    			    			    			    			
			}
			else if (table.maxDouble<100){
				int firstDigit= table.maxDouble/10;
				int secondDigit=table.maxDouble%10;
    			osg.drawImage(CanvasHelper.getNumberedImage(firstDigit),DeviceConstant.PUBLICTABLE_POS3H-DeviceConstant.PUBLIC_TABLELIST_NUMOFFSET,vpos,Graphics.LEFT | Graphics.TOP) ;    			    			    			    			        			
    			osg.drawImage(CanvasHelper.getNumberedImage(secondDigit),DeviceConstant.PUBLICTABLE_POS3H,vpos,Graphics.LEFT | Graphics.TOP) ;    			    			    			    			        			
			}
    		osg.drawImage(CanvasHelper.getNumberedImage(table.numOfPlayer),DeviceConstant.PUBLICTABLE_POS4H,vpos,Graphics.LEFT | Graphics.TOP) ;
    	}

		repaint();
		serviceRepaints();
        //debug(0,"drawTable() finished");
    }

    protected  void keyPressed(int keyCode) {
    	numberOfRow=tables.size();
    	//debug(0,System.currentTimeMillis()+"::keyPressed()" + "keycode="+ keyCode+ " ;Screen="+screen + " ;inputState="+inputState);

		//escaping previous input if the class is still loading or multiple pressed
		if (System.currentTimeMillis()-200<lastInputTime || inputState==0) {
			return;
		}
		lastInputTime=System.currentTimeMillis();
		switch (keyCode) {    	        
        	case -6://case KEY_SOFTKEY1
            	if (screen==0) {
                	if (currentRowSelection==0) {
                		screen=1;
                		createRoomHelper();
                	} 
                	else {
                		joinRoomHelper();
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
        			midlet.commandReceived(Constant.PUBLIC_CPAUSE);            			
        		}
        		else if (screen==1) {
            		screen=0;
            		quitRoomHelper();
            		if (!isRoomOwner) {
            			drawTable();
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
    				drawTable();
            	}
    			break;
            case -2://case KEY_DOWN_ARROW:
            	if (screen==0) {
                	currentRowSelection++;
                	currentRowScreen++;
    				if (currentRowSelection==numberOfRow)
    					currentRowSelection=numberOfRow-1;
    				if (currentRowScreen==DeviceConstant.PUBLIC_TABLELIST_NUM_OF_ROW_PERSCREEN)
    					currentRowScreen=DeviceConstant.PUBLIC_TABLELIST_NUM_OF_ROW_PERSCREEN-1;
    				if (currentRowScreen>=numberOfRow) {
    					currentRowScreen=numberOfRow-1;
    				}
    				drawTable();
            	}
                break;
            case -3: //case KEY_LEFT_ARROW:
            	if (screen==0) {
                	currentRowSelection-=DeviceConstant.PUBLIC_TABLELIST_NUM_OF_ROW_PERSCREEN;
                	currentRowScreen=0;
    				if (currentRowSelection<0)
    					currentRowSelection=0;	    				
    				drawTable();
            	}
            	break;
            case -4: //case KEY_RIGHT_ARROW:
            	if (screen==0) {
                	currentRowSelection+=DeviceConstant.PUBLIC_TABLELIST_NUM_OF_ROW_PERSCREEN;
                	currentRowScreen=DeviceConstant.PUBLIC_TABLELIST_NUM_OF_ROW_PERSCREEN-1;
    				if (currentRowSelection>=numberOfRow)
    					currentRowSelection=numberOfRow-1;
    				if (currentRowScreen>=numberOfRow) {
    					currentRowScreen=numberOfRow-1;
    				}
    				drawTable();
            	}
                break;
            case -5:  //case KEY_SOFTKEY3:
            	if (screen==0) {
                	if (currentRowSelection==0) {
                		screen=1;
                		createRoomHelper();
                	}
                	else {
                		screen=1;
                		joinRoomHelper();
                	}                		
            	}
            	else if (screen==1) {
            		if (isRoomOwner) {
            			screen=2;
            			forceStart();
            		}
            		else {
                		screen=0;
                		quitRoomHelper();
            		}
            	}
            	break;
	        default:
        }  		
        repaint();
        serviceRepaints();
    }//end keyPressed()

    
    private void debug(int level, String txt) {
    	if (level>=debugLevel)
    		System.out.println("BTServerCanvas."+txt);
    }    

}
