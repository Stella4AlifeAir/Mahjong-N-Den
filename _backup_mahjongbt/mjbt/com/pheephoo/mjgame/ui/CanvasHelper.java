/*
 * Created on 14/04/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pheephoo.mjgame.ui;

import java.io.IOException;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.*;


import com.pheephoo.mjgame.DeviceConstant;
import com.pheephoo.mjgame.*;
import com.pheephoo.utilx.Common;

/**
 * This temporary canvas is to prevent user to make any input that might cause multiple input
 */



public class CanvasHelper {
	
	public static WaitingCanvas canvas;
	
    public static Image animatedImage;
	public static Image dealingImage;
	public static Image backgroundImage;
	public static Image buttonImage;
	public static Image number0Image;
	public static Image number1Image;
	public static Image number2Image;
	public static Image number3Image;
	public static Image number4Image;
	public static Image number5Image;
	public static Image number6Image;
	public static Image number7Image;
	public static Image number8Image;
	public static Image number9Image;
	public static Image number10Image;
	public static Image scrollBar;
	public static Image scrollPeg;
    public static Image faces;

	
	
	MJGame midlet;

	static public void init() {
		CanvasHelper a=new CanvasHelper();
		
		try {
			animatedImage=Image.createImage("/res/animated.jpg") ;
			dealingImage=Image.createImage("/res/dealing.jpg") ;
			backgroundImage=Image.createImage("/res/new_bg.jpg") ;
			buttonImage=Image.createImage("/res/button.jpg");
			number0Image=Image.createImage("/res/n_0.png");
			number1Image=Image.createImage("/res/n_1.png");
			number2Image=Image.createImage("/res/n_2.png");
			number3Image=Image.createImage("/res/n_3.png");
			number4Image=Image.createImage("/res/n_4.png");
			number5Image=Image.createImage("/res/n_5.png");
			number6Image=Image.createImage("/res/n_6.png");
			number7Image=Image.createImage("/res/n_7.png");
			number8Image=Image.createImage("/res/n_8.png");
			number9Image=Image.createImage("/res/n_9.png");
			number10Image=Image.createImage("/res/n_10.png");
			scrollBar=Image.createImage("/res/scroll_bar.png");
			scrollPeg=Image.createImage("/res/scroll_peg.png");
			faces=Image.createImage("/res/faces.jpg");
		}
		catch (IOException e){
			throw new Error();
		}
		canvas=a.new WaitingCanvas();		

		
	}
	
	static public void drawOfflineWait() {
		canvas.drawOfflineWait();
	}
	
	static public void drawOnlineWait() {
		
	}
	
	static public void startNetworkAnimation() {
		canvas.startAnimation();
	}

	static public void stopNetworkAnimation() {
		canvas.stopAnimation();		
	}

    static public Image getAnimatedImage(int index) {
    	//System.out.println("CanvasHelper.getAnimatedImage");
    	return Common.extract(animatedImage, 0,index*DeviceConstant.ANIM_HEIGHT,DeviceConstant.ANIM_WIDTH,DeviceConstant.ANIM_HEIGHT);
    }
    
    static public Image getLastImage() {
    	return canvas.offScreenBuffer;
    }
    
    static public Image getFace(int index) {
    	return Common.extract(faces,index*DeviceConstant.SETTINGFACE_WIDTH,0,DeviceConstant.SETTINGFACE_WIDTH,faces.getHeight());
    }


    static public Image getButtonImage(int index) {
    	return Common.extract(buttonImage, 0,index*DeviceConstant.BUTTON_HEIGHT,buttonImage.getWidth(),DeviceConstant.BUTTON_HEIGHT);
    }

    static public Image getNumberedImage(int index) {
    	if (index==0) {
    		return number0Image;
    	}
    	else if (index==1) {
    		return number1Image;    		
    	}
    	else if (index==2) {
    		return number2Image;    		
    	}
    	else if (index==3) {
    		return number3Image;    		
    	}
    	else if (index==4) {
    		return number4Image;    		
    	}
    	else if (index==5) {
    		return number5Image;    		
    	}
    	else if (index==6) {
    		return number6Image;    		
    	}
    	else if (index==7) {
    		return number7Image;    		
    	}
    	else if (index==8) {
    		return number8Image;    		
    	}
    	else if (index==9) {
    		return number9Image;    		
    	}
    	else  {
    		return number10Image;    		
    	}
    }
	

 class WaitingCanvas extends Canvas implements Runnable { 

    Graphics osg;   //we draw using this offscreen graphics object
    Image offScreenBuffer;
	StringBuffer str= null;

    int swidth;
    int sheight;

    boolean animated=false;
    
    
    Thread thread;
    
    public WaitingCanvas() {
		setFullScreenMode(true);
    	offScreenBuffer = Image.createImage(getWidth(), getHeight());
    	swidth=getWidth();
    	sheight=getHeight();
    	osg = offScreenBuffer.getGraphics();    	
    	thread=new Thread (this);
    	thread.start();
    }
    
	
    protected void paint(Graphics graphics) {
    	graphics.drawImage(offScreenBuffer, 0, 0, Graphics.LEFT | Graphics.TOP);

    }
    
    
    public synchronized void run() {

		int i=0;

    	while (true) {
    		if (!animated) {
    			try {
    				//Sleep and release object lock
    				this.wait( );
    				str=new StringBuffer();
    				str.append("Connecting  " );
                	i=0;
    			} 
    			catch (InterruptedException e) {
    				e.printStackTrace( );
    				//Clear interrupt status
    			}
    		}
			str.append(". ");
            if (i==8) {
            	//System.out.println("reseting");
            	i=0;
            	str.delete(0,str.length());
            	str.append("Connecting  ");
            }		
            i++;
			//System.out.println(str.toString());
	        //osg.setColor(141,181,115);
			osg.setColor(86,55,44);
			osg.fillRect(0, DeviceConstant.PROGRESSBAR_POS_V,swidth,sheight);
            osg.setColor(255,255,255);
			osg.drawString(str.toString(),DeviceConstant.STATUSTEXT_POS_H,DeviceConstant.PROGRESSBAR_POS_V+4,Graphics.LEFT| Graphics.TOP);
			
			repaint();
	        serviceRepaints();
	        try {
	    	    Thread.sleep(1000);
	        }
	        catch (InterruptedException ie) {					
		    }
    	}
    	
    }
    public void drawOfflineWait() {
        osg.drawImage(dealingImage,0, DeviceConstant.DEALINGTEXT_POSITION_H , Graphics.LEFT| Graphics.TOP);            	
		osg.drawImage(getAnimatedImage(0),(swidth-DeviceConstant.ANIM_WIDTH)/2,(sheight-DeviceConstant.ANIM_HEIGHT), Graphics.LEFT | Graphics.TOP);					    	
    }

    
    
    
    
    
    public void drawScreen(int screenNum) {
    	if (screenNum==1) {
    		//offline mode
            osg.drawImage(dealingImage,0, DeviceConstant.DEALINGTEXT_POSITION_H , Graphics.LEFT| Graphics.TOP);            	
			osg.drawImage(getAnimatedImage(0),(swidth-DeviceConstant.ANIM_WIDTH)/2,(sheight-DeviceConstant.ANIM_HEIGHT), Graphics.LEFT | Graphics.TOP);					
			//System.out.println("finish drawing tempscren");
    	}
    	else if (screenNum==3) {
            osg.drawImage(backgroundImage,0, DeviceConstant.DEALINGTEXT_POSITION_H , Graphics.LEFT| Graphics.TOP);            	
            osg.drawImage(dealingImage,0, DeviceConstant.DEALINGTEXT_POSITION_H , Graphics.LEFT| Graphics.TOP);            	
			osg.setColor(86,55,44);
			osg.fillRect(0, DeviceConstant.PROGRESSBAR_POS_V,swidth,sheight);
            osg.setColor(255,255,255);
            osg.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD,Font.SIZE_SMALL));
			osg.drawString("Connecting .",DeviceConstant.STATUSTEXT_POS_H,DeviceConstant.PROGRESSBAR_POS_V+4,Graphics.LEFT| Graphics.TOP);
    	}
    	repaint();
    	serviceRepaints();
    }
    
    
    public synchronized void startAnimation() {
    	//System.out.println("WaitingCanvas.startAnimation()");

    	if (animated) {
    		return;
    	}
    	animated=true;
    	osg.setColor(86,55,44);
		osg.fillRect(0, DeviceConstant.PROGRESSBAR_POS_V,swidth,sheight);
        osg.setColor(255,255,255);
        osg.setFont(Font.getFont(Font.FACE_MONOSPACE, Font.STYLE_BOLD,Font.SIZE_SMALL));
   
        //System.out.println("trying to draw background img");
		osg.drawImage(backgroundImage,0,0,Graphics.LEFT| Graphics.TOP);
		osg.drawImage(dealingImage,0, DeviceConstant.DEALINGTEXT_POSITION_H , Graphics.LEFT| Graphics.TOP);            	
		repaint();
		serviceRepaints();
		this.notify();
    }
	
    public void stopAnimation() {
    	animated=false;
    }
 }

}
