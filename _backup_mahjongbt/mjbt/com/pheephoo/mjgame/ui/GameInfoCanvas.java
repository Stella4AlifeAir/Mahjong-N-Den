/*
 * Created on 1/11/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pheephoo.mjgame.ui;

import com.pheephoo.mjgame.*;
import java.io.IOException;
import javax.microedition.lcdui.*;


/**
 * @author sam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class  GameInfoCanvas extends Canvas {

    //reference to main midlet
    MJGame midlet;
    Series60Client client;
    Graphics osg;   //we draw using this offscreen graphics object
    Image offScreenBuffer;
    
    long lastInputTime=0;    
    public int screen=0; //0=Public Network list   1=Our own table 2= play started
    int playMode=0; //0=offline   1=private  2= public

    int numOfPage=1;
    int currentPage=1;

	String nick[]=new String[4];

	
	Image background;
	
	public GameInfoCanvas(MJGame m, Series60Client _client, int _playMode) {
		//System.out.println("**************** GameInfoCanvas constructor");
    	this.setFullScreenMode(true);
    	midlet=m;
		client=_client;
		playMode=_playMode;
    	offScreenBuffer = Image.createImage(getWidth(), getHeight());
    	osg = offScreenBuffer.getGraphics();
		try {
			background=Image.createImage("/res/game_info.jpg");
		}
		catch (IOException e) {
		}
    	drawInfo();

	}
	
	private void drawInfo() {
		osg.drawImage(CanvasHelper.backgroundImage,0,0,Graphics.LEFT|Graphics.TOP);
		osg.drawImage(background,0,0,Graphics.LEFT|Graphics.TOP);
        osg.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD,Font.SIZE_MEDIUM));
    	osg.setColor(225,225,255);


    	//get the first 3 chars of the nick
    	String tempnick="";
    	for (int i=0;i<4;i++) {
    		tempnick=client.playerInfo[i].nick;
    		if (tempnick.length()<3) {
    			nick[i]=tempnick;
    		}
    		else {
    			nick[i]=tempnick.substring(0,3);
    		}
    	}
    	//System.out.println("nick1="+nick[0]+ " ;nick2="+nick[1]+ " ;nick3="+nick[2]+" ;nick4="+nick[3]);
    	
    	osg.drawString(nick[0],DeviceConstant.GAMEINFO_NICKPOS_H,DeviceConstant.GAMEINFO_NICKPOS_V,Graphics.LEFT| Graphics.TOP);
        osg.drawString(nick[1],DeviceConstant.GAMEINFO_NICKPOS_H+DeviceConstant.GAMEINFO_SPACING_H,DeviceConstant.GAMEINFO_NICKPOS_V,Graphics.LEFT| Graphics.TOP);
        osg.drawString(nick[2],DeviceConstant.GAMEINFO_NICKPOS_H+DeviceConstant.GAMEINFO_SPACING_H*2,DeviceConstant.GAMEINFO_NICKPOS_V,Graphics.LEFT| Graphics.TOP);
        osg.drawString(nick[3],DeviceConstant.GAMEINFO_NICKPOS_H+DeviceConstant.GAMEINFO_SPACING_H*3,DeviceConstant.GAMEINFO_NICKPOS_V,Graphics.LEFT| Graphics.TOP);

        
        //debugging code..to check if more than 1 page
//        client.playerInfo[0].score[0]=2001;
//        client.playerInfo[1].score[0]=2004;
//        client.playerInfo[2].score[0]=1999;
//        client.playerInfo[3].score[0]=2001;
//        client.playerInfo[0].score[10]=1951;
//        client.playerInfo[1].score[10]=2005;
//        client.playerInfo[2].score[10]=1999;
//        client.playerInfo[3].score[10]=2100;
//        client.playerInfo[0].score[20]=2199;
//        client.playerInfo[1].score[20]=2000;
//        client.playerInfo[2].score[20]=2000;
//        client.playerInfo[3].score[20]=2001;
//        client.currentRound=22;
        

        //draw score
        osg.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_PLAIN,Font.SIZE_MEDIUM));
        numOfPage=(  (client.currentRound) /DeviceConstant.GAMEINFO_NUM_OF_ROW)+1;
        
        if (numOfPage==1 || currentPage==1) {
        	//System.out.println("drawing firstpage");
            osg.drawString("2000",DeviceConstant.GAMEINFO_SCOREPOS_H,DeviceConstant.GAMEINFO_SCOREPOS_V,Graphics.LEFT| Graphics.TOP);
            osg.drawString("2000",DeviceConstant.GAMEINFO_SCOREPOS_H+DeviceConstant.GAMEINFO_SPACING_H,DeviceConstant.GAMEINFO_SCOREPOS_V,Graphics.LEFT| Graphics.TOP);
            osg.drawString("2000",DeviceConstant.GAMEINFO_SCOREPOS_H+DeviceConstant.GAMEINFO_SPACING_H*2,DeviceConstant.GAMEINFO_SCOREPOS_V,Graphics.LEFT| Graphics.TOP);
            osg.drawString("2000",DeviceConstant.GAMEINFO_SCOREPOS_H+DeviceConstant.GAMEINFO_SPACING_H*3,DeviceConstant.GAMEINFO_SCOREPOS_V,Graphics.LEFT| Graphics.TOP);
            
            for (int i=0;i<DeviceConstant.GAMEINFO_NUM_OF_ROW-1  && i<client.currentRound;i++) {
                osg.drawString(client.playerInfo[0].score[i]+"",DeviceConstant.GAMEINFO_SCOREPOS_H,DeviceConstant.GAMEINFO_SCOREPOS_V+DeviceConstant.GAMEINFO_SPACING_V*(i+1),Graphics.LEFT| Graphics.TOP);
                osg.drawString(client.playerInfo[1].score[i]+"",DeviceConstant.GAMEINFO_SCOREPOS_H+DeviceConstant.GAMEINFO_SPACING_H,DeviceConstant.GAMEINFO_SCOREPOS_V+DeviceConstant.GAMEINFO_SPACING_V*(i+1),Graphics.LEFT| Graphics.TOP);
                osg.drawString(client.playerInfo[2].score[i]+"",DeviceConstant.GAMEINFO_SCOREPOS_H+DeviceConstant.GAMEINFO_SPACING_H*2,DeviceConstant.GAMEINFO_SCOREPOS_V+DeviceConstant.GAMEINFO_SPACING_V*(i+1),Graphics.LEFT| Graphics.TOP);
                osg.drawString(client.playerInfo[3].score[i]+"",DeviceConstant.GAMEINFO_SCOREPOS_H+DeviceConstant.GAMEINFO_SPACING_H*3,DeviceConstant.GAMEINFO_SCOREPOS_V+DeviceConstant.GAMEINFO_SPACING_V*(i+1),Graphics.LEFT| Graphics.TOP);
            }
        }
        else {
        	//System.out.println("drawing overflowpage");
            for (int i=(currentPage-1)*DeviceConstant.GAMEINFO_NUM_OF_ROW -1;i< (currentPage)*DeviceConstant.GAMEINFO_NUM_OF_ROW -1  && i<client.currentRound ;i++) {
                osg.drawString(client.playerInfo[0].score[i]+"",DeviceConstant.GAMEINFO_SCOREPOS_H,DeviceConstant.GAMEINFO_SCOREPOS_V+DeviceConstant.GAMEINFO_SPACING_V*(i- DeviceConstant.GAMEINFO_NUM_OF_ROW*(currentPage-1)       +1),Graphics.LEFT| Graphics.TOP);
                osg.drawString(client.playerInfo[1].score[i]+"",DeviceConstant.GAMEINFO_SCOREPOS_H+DeviceConstant.GAMEINFO_SPACING_H,DeviceConstant.GAMEINFO_SCOREPOS_V+DeviceConstant.GAMEINFO_SPACING_V*(i-DeviceConstant.GAMEINFO_NUM_OF_ROW*(currentPage-1) +1),Graphics.LEFT| Graphics.TOP);
                osg.drawString(client.playerInfo[2].score[i]+"",DeviceConstant.GAMEINFO_SCOREPOS_H+DeviceConstant.GAMEINFO_SPACING_H*2,DeviceConstant.GAMEINFO_SCOREPOS_V+DeviceConstant.GAMEINFO_SPACING_V*(i-DeviceConstant.GAMEINFO_NUM_OF_ROW*(currentPage-1) +1),Graphics.LEFT| Graphics.TOP);
                osg.drawString(client.playerInfo[3].score[i]+"",DeviceConstant.GAMEINFO_SCOREPOS_H+DeviceConstant.GAMEINFO_SPACING_H*3,DeviceConstant.GAMEINFO_SCOREPOS_V+DeviceConstant.GAMEINFO_SPACING_V*(i-DeviceConstant.GAMEINFO_NUM_OF_ROW*(currentPage-1) +1),Graphics.LEFT| Graphics.TOP);
            }

        }
        osg.drawString(currentPage+ "/"+ numOfPage,DeviceConstant.GAMEINFO_PAGENUMPOS_H,DeviceConstant.GAMEINFO_PAGENUMPOS_V,Graphics.LEFT| Graphics.TOP);
                
        repaint();
        serviceRepaints();
        
	}

	
    protected  void paint(Graphics graphics) {
    	graphics.drawImage(offScreenBuffer, 0, 0, Graphics.LEFT | Graphics.TOP);

    }

    protected void keyPressed(int keyCode) {
    	//System.out.println(System.currentTimeMillis()+"::keyPressed()");
		if (System.currentTimeMillis()-350<lastInputTime) {
			return;
		}
		else {
			lastInputTime=System.currentTimeMillis();
		}
		
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
			drawInfo();
		}
		else {
			if (playMode==0) {
				midlet.commandReceived(Constant.OFFLINE_PAUSE);				
			}
			else {
				midlet.commandReceived(Constant.NETWORKCOMMON_PAUSE);
			}
		}
    }

}
