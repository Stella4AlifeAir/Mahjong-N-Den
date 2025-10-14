/*
 * Created on 1/11/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pheephoo.mjgame.ui;


import com.pheephoo.mjgame.*;
import com.pheephoo.mjgame.engine.PlayerInfo;
import com.pheephoo.mjgame.engine.UserData;

import java.io.IOException;
import javax.microedition.lcdui.*;

import java.util.*;

/**
 * @author sam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class  PlayerInfoCanvas extends Canvas {

    //reference to main midlet
    MJGame midlet;
    Series60Client client;
    
    Graphics osg;   //we draw using this offscreen graphics object
    Image offScreenBuffer;    
    long lastInputTime=0;    
    
    //is need for loading the correct pause screen
    int playMode=0; //0=offline   1=private  2= public
    int screenType=0;
    
    int numOfPage=1;
    int currentPage=1;
    
    int historyMode=0;

    
	public PlayerInfoCanvas(MJGame m, Series60Client _client, int _playMode, int screenType) {
    	this.setFullScreenMode(true);
		midlet=m;
		client=_client;
		playMode=_playMode;
    	offScreenBuffer = Image.createImage(getWidth(), getHeight());
    	osg = offScreenBuffer.getGraphics();
    	this.screenType=screenType;
    	if (screenType==0) {//player info
        	drawInfo();  		
    	}
    	else if (screenType==1) {//end score
    		drawEnd();
    	}
    	else if (screenType==2) {//history
    		historyMode=0;
    		drawHistory(0);
    	}
    	else if (screenType==3) {//history
    		historyMode=1;
    		drawHistory(1);
    	}
	}
	
	//0=offline 1=online
	private void drawHistory(int mode) {
		//System.out.println("drawEnd, mode="+mode);

		try {
			osg.drawImage(CanvasHelper.backgroundImage,0,0,Graphics.LEFT|Graphics.TOP);
			osg.drawImage(Image.createImage("/res/history.jpg"),0,0,Graphics.LEFT|Graphics.TOP);
		}
		catch (IOException e) {
		}
		
		//debug add history
		/*
		PlayerInfo[] playerInfo= new PlayerInfo[4];
		playerInfo[0]=new PlayerInfo();
		playerInfo[1]=new PlayerInfo();
		playerInfo[2]=new PlayerInfo();
		playerInfo[3]=new PlayerInfo();
		playerInfo[0].picture=1;
		playerInfo[0].nick="sam";
		playerInfo[0].lastScore=2001;
		playerInfo[1].picture=2;
		playerInfo[1].nick="bot1";
		playerInfo[1].lastScore=2002;
		playerInfo[2].picture=3;
		playerInfo[2].nick="bot2";
		playerInfo[2].lastScore=1991;
		playerInfo[3].picture=2;
		playerInfo[3].nick="bot3";
		playerInfo[3].lastScore=2001;
		
		UserData.addHistory(playerInfo,mode);

		playerInfo[0]=new PlayerInfo();
		playerInfo[1]=new PlayerInfo();
		playerInfo[2]=new PlayerInfo();
		playerInfo[3]=new PlayerInfo();
		playerInfo[0].picture=1;
		playerInfo[0].nick="samzzz";
		playerInfo[0].lastScore=2021;
		playerInfo[1].picture=2;
		playerInfo[1].nick="box1";
		playerInfo[1].lastScore=2002;
		playerInfo[2].picture=3;
		playerInfo[2].nick="bs2";
		playerInfo[2].lastScore=1991;
		playerInfo[3].picture=2;
		playerInfo[3].nick="bossst3";
		playerInfo[3].lastScore=2001;
		UserData.addHistory(playerInfo,mode);
		*/
		
		Vector history=UserData.getHistory(mode);
		numOfPage=history.size();
		//System.out.println("numOFPage="+numOfPage);
		//System.out.println("currentPAge"+currentPage);

        osg.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN,Font.SIZE_MEDIUM));
    	osg.setColor(225,225,255);

    	
		if (numOfPage>0) {
			drawScore( (PlayerInfo[]) history.elementAt(currentPage-1) );

			
			if (mode==1) {
				PlayerInfo info[]= (PlayerInfo[]) history.elementAt(currentPage-1);
	    		osg.drawString( info[0].dateStr, 5 ,DeviceConstant.GAMEINFO_PAGENUMPOS_V,Graphics.LEFT| Graphics.TOP);
	    	}

			osg.drawString(currentPage+ "/"+ numOfPage,DeviceConstant.GAMEINFO_PAGENUMPOS_H,DeviceConstant.GAMEINFO_PAGENUMPOS_V,Graphics.LEFT| Graphics.TOP);
		}
		else {
	        osg.drawString("Empty",DeviceConstant.GAMEINFO_PAGENUMPOS_H,DeviceConstant.GAMEINFO_PAGENUMPOS_V,Graphics.LEFT| Graphics.TOP);			
		}
        repaint();
        serviceRepaints();
		
	}
	
	
	private void drawScore(PlayerInfo[] playerInfo) {
        osg.setFont(Font.getFont(
        	    Font.FACE_MONOSPACE, Font.STYLE_BOLD,Font.SIZE_MEDIUM));
    	osg.setColor(225,225,255);    	
    	
    	int[] checkedScore=new int[4];
    	checkedScore[0]=9;
    	checkedScore[1]=9;
    	checkedScore[2]=9;
    	checkedScore[3]=9;
    	
    	int higherscore=0;
    	int temp=0;
    	
    	//System.out.println("playerInfo size="+playerInfo.length);
    	boolean nextFlag=false;
    	for (int a=0;a<4;a++) {
    		higherscore=0;
        	for (int i=0;i<4;i++) {
        		nextFlag=false;
        		for (int j=0;j<4;j++) {
        			if (i==checkedScore[j]) {
        				nextFlag=true;
        				break;
        			}
        		}
        		if (!nextFlag) {
	        		temp=playerInfo[i].lastScore;
        			if (higherscore<temp ) {
	                	checkedScore[a]=i;
        				higherscore=temp;
        			}
        		}
        	}
    	}
    	
    	//System.out.println("finsihed sorting");
    	//System.out.println("printing checked score: "+checkedScore[0] +" "+ checkedScore[1]+ " " +checkedScore[2]+ " " +checkedScore[3]);
    	int offset1;
    	int offset2;
    	if (screenType==0) {
    		offset1=DeviceConstant.PLAYERINFO_FACEPOS2_V;
    		offset2=DeviceConstant.PLAYERINFO_TEXTPOS2_V;
    	}
    	else {
    		offset1=DeviceConstant.PLAYERINFO_FACEPOS3_V;
    		offset2=DeviceConstant.PLAYERINFO_TEXTPOS3_V;
    		
    	}

    	for (int i=0;i<4;i++) {
    		osg.drawString(playerInfo[checkedScore[i]].nick  ,DeviceConstant.PLAYERINFO_TEXTPOS1_H,offset2+DeviceConstant.PLAYERINFO_SPACING_V*i,Graphics.LEFT| Graphics.TOP);
	        drawFace(playerInfo[checkedScore[i]].picture,DeviceConstant.PLAYERINFO_FACEPOS_H,offset1+DeviceConstant.PLAYERINFO_SPACING_V*i);
	        osg.drawString(playerInfo[checkedScore[i]].lastScore+"",DeviceConstant.PLAYERINFO_TEXTPOS3_H,offset2+DeviceConstant.PLAYERINFO_SPACING_V*i,Graphics.LEFT| Graphics.TOP);
    	}
	}
	
	
	
	private void drawEnd() {
		//System.out.println("drawEnd,playmode="+playMode);
		try {
			osg.drawImage(CanvasHelper.backgroundImage,0,0,Graphics.LEFT|Graphics.TOP);
			osg.drawImage(Image.createImage("/res/end_round.jpg"),0,0,Graphics.LEFT|Graphics.TOP);
		}
		catch (IOException e) {
		}
		
		drawScore(client.playerInfo);
		if (playMode!=2) {
			UserData.addHistory(client.playerInfo,playMode,midlet.dateStr);
		}
	}

	
	private void drawInfo() {
		try {
			osg.drawImage(CanvasHelper.backgroundImage,0,0,Graphics.LEFT|Graphics.TOP);
			osg.drawImage(Image.createImage("/res/player_info.jpg"),0,0,Graphics.LEFT|Graphics.TOP);
		}
		catch (IOException e) {
		}
        osg.setFont(Font.getFont( Font.FACE_MONOSPACE, Font.STYLE_BOLD,Font.SIZE_MEDIUM));
    	osg.setColor(225,225,255);

        //east
        osg.drawString("E ",DeviceConstant.PLAYERINFO_TEXTPOS2_H,DeviceConstant.PLAYERINFO_TEXTPOS_V,Graphics.LEFT| Graphics.TOP);
        osg.drawString("S ",DeviceConstant.PLAYERINFO_TEXTPOS2_H,DeviceConstant.PLAYERINFO_TEXTPOS_V+DeviceConstant.PLAYERINFO_SPACING_V,Graphics.LEFT| Graphics.TOP);
        osg.drawString("W ",DeviceConstant.PLAYERINFO_TEXTPOS2_H,DeviceConstant.PLAYERINFO_TEXTPOS_V+DeviceConstant.PLAYERINFO_SPACING_V*2,Graphics.LEFT| Graphics.TOP);
        osg.drawString("N ",DeviceConstant.PLAYERINFO_TEXTPOS2_H,DeviceConstant.PLAYERINFO_TEXTPOS_V+DeviceConstant.PLAYERINFO_SPACING_V*3,Graphics.LEFT| Graphics.TOP);
        osg.drawString(client.getNick(0)  ,DeviceConstant.PLAYERINFO_TEXTPOS1_H,DeviceConstant.PLAYERINFO_TEXTPOS_V+DeviceConstant.PLAYERINFO_SPACING_V*0,Graphics.LEFT| Graphics.TOP);
        osg.drawString(client.getNick(1)  ,DeviceConstant.PLAYERINFO_TEXTPOS1_H,DeviceConstant.PLAYERINFO_TEXTPOS_V+DeviceConstant.PLAYERINFO_SPACING_V,Graphics.LEFT| Graphics.TOP);
        osg.drawString(client.getNick(2)  ,DeviceConstant.PLAYERINFO_TEXTPOS1_H,DeviceConstant.PLAYERINFO_TEXTPOS_V+DeviceConstant.PLAYERINFO_SPACING_V*2,Graphics.LEFT| Graphics.TOP);
        osg.drawString(client.getNick(3)  ,DeviceConstant.PLAYERINFO_TEXTPOS1_H,DeviceConstant.PLAYERINFO_TEXTPOS_V+DeviceConstant.PLAYERINFO_SPACING_V*3,Graphics.LEFT| Graphics.TOP);
                
        drawFace(client.getPicture(0),DeviceConstant.PLAYERINFO_FACEPOS_H,DeviceConstant.PLAYERINFO_FACEPOS_V);
        drawFace(client.getPicture(1),DeviceConstant.PLAYERINFO_FACEPOS_H,DeviceConstant.PLAYERINFO_FACEPOS_V+DeviceConstant.PLAYERINFO_SPACING_V);
        drawFace(client.getPicture(2),DeviceConstant.PLAYERINFO_FACEPOS_H,DeviceConstant.PLAYERINFO_FACEPOS_V+DeviceConstant.PLAYERINFO_SPACING_V*2);
        drawFace(client.getPicture(3),DeviceConstant.PLAYERINFO_FACEPOS_H,DeviceConstant.PLAYERINFO_FACEPOS_V+DeviceConstant.PLAYERINFO_SPACING_V*3);

        

	}
	

	private void drawFace(int pictureNum, int hpos, int vpos) {
        osg.drawImage(CanvasHelper.getFace(pictureNum) , hpos,vpos, Graphics.LEFT| Graphics.TOP);
    }
	
    protected  void paint(Graphics graphics) {
    	graphics.drawImage(offScreenBuffer, 0, 0, Graphics.LEFT | Graphics.TOP);
    }


    protected void keyPressed(int keyCode) {
		if (System.currentTimeMillis()-350<lastInputTime) {
			return;
		}
		else {
			lastInputTime=System.currentTimeMillis();
		}

		if (screenType==0) {
			if (playMode==0) {
				midlet.commandReceived(Constant.OFFLINE_PAUSE);				
			}
			else {
				midlet.commandReceived(Constant.NETWORKCOMMON_PAUSE);
			}
		}
		else if (screenType==1) {
			if (keyCode==-5) {
				midlet.commandReceived(Constant.OFFLINE_QUITGAME);
			}
		}
		else {
			if (numOfPage>1 && (keyCode==-1 || keyCode==-2 || keyCode==-3 || keyCode==-4)) {
				//up
				if (keyCode==-1 || keyCode==-3) {
					if (currentPage>1) 
						currentPage--;
				}
				//down arrow
				else if (keyCode==-2 || keyCode==-4) {
					if (currentPage<numOfPage)
						currentPage++;
				}
				//System.out.println("numOfPAge="+numOfPage + " ;currentPage="+currentPage);
				drawHistory(historyMode);
			}
			else {
				midlet.commandReceived(Constant.MAIN_MAINMENU);
			}

			
			
			
		
		}
    }

}
