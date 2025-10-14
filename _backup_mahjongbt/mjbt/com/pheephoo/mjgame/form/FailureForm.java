/*
 * Created on 26/04/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pheephoo.mjgame.form;

import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import com.pheephoo.mjgame.*;

/**
 * @author sam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class FailureForm extends Canvas  implements Runnable {

    Image bg;
    Image bgtext;
    MJGame midlet;
    Graphics osg;   //we draw using this offscreen graphics object
    Image offScreenBuffer;

    boolean inputEnabled=false;
    
	protected  void  paint(Graphics graphics) {
    	graphics.drawImage(offScreenBuffer, 0, 0, Graphics.LEFT | Graphics.TOP);

    }
	
	
	public FailureForm(MJGame m) {
		System.out.println("FailureForm constructor");
		this.setFullScreenMode(true);
		midlet=m;
    	offScreenBuffer = Image.createImage(getWidth(), getHeight());
    	osg = offScreenBuffer.getGraphics();

    	int textpos;
    	try {
        	bg=Image.createImage("/res/networkfail.jpg") ;
        	osg.drawImage(bg, 0, 0, Graphics.LEFT | Graphics.TOP);

        	if (midlet.transactionError) {
            	bgtext=Image.createImage("/res/networkfail3.png") ;    			
    		}
        	else if (midlet.numOfFail==0) {
            	bgtext=Image.createImage("/res/networkfail1.png") ;
        	}
        	else if (midlet.needShowError){
        		midlet.numOfFail=0;
        		inputEnabled=true;
            	bgtext=Image.createImage("/res/networkfail1.png") ;
        	}
        	else {
            	bgtext=Image.createImage("/res/networkfail2.png") ;
        	}
        }
    	catch (IOException ie) {
    		throw new Error();
    	}
    	osg.drawImage(bg, 0, 0, Graphics.LEFT | Graphics.TOP);
    	osg.drawImage(bgtext, (getWidth()-bgtext.getWidth())/2, 0, Graphics.LEFT | Graphics.TOP);

    	repaint();
    	serviceRepaints();
    	
    	Thread t= new Thread(this);
    	t.start();
	}
	public void run() {
		try {
			//Thread.sleep(4000);
			Thread.sleep(5500);
			midlet.transactionError=false;
			
			if (!inputEnabled) {
				midlet.commandReceived(Constant.NETWORKCOMMON_QUITGAME);
				if(midlet.numOfFail==2) {
					midlet.close();
				}
			}
		}
		catch (InterruptedException e){
		}
	}

    protected void keyPressed(int keyCode) {
    	if (inputEnabled) {
    		midlet.close();
			midlet.commandReceived(Constant.NETWORKCOMMON_QUITGAME);
    	}
    }

}