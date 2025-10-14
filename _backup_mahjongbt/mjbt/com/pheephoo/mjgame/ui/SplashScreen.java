/*
 * Created on 19/01/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pheephoo.mjgame.ui;


import com.pheephoo.mjgame.*;
import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * @author sam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SplashScreen extends Canvas  implements Runnable {

    private Image bg;
    private MJGame midlet;

    private int milis;
    
    public boolean finished=false;
    
	protected  void  paint(Graphics graphics) {
    	graphics.drawImage(bg, 0, 0, Graphics.LEFT | Graphics.TOP);
    }
	
	public SplashScreen(MJGame m, int _milis) {
		this.setFullScreenMode(true);
		midlet=m;
		milis=_milis;
		try {
        	bg=Image.createImage("/res/mjlogo.jpg") ;
        }
    	catch (IOException ie) {
    		throw new Error();
    	}
    	Thread t= new Thread(this);
    	t.start();
	}
	public void run() {
		try {
			Thread.sleep(milis);
			finished=true;
			//midlet.commandReceived(Constant.OFFLINE_QUITGAME);
		}
		catch (InterruptedException e){
		}
	}

}
