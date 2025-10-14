/*
 */

package com.pheephoo.mjgame.form;

import java.io.IOException;

import com.pheephoo.mjgame.*;
import com.pheephoo.mjgame.engine.Setting;
import com.pheephoo.mjgame.ui.CanvasHelper;
import com.pheephoo.utilx.Common;


import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author  sam
 * @version
 */
public class SettingForm extends Canvas {
        
    private MJGame midlet;

	int swidth=0;
	int sheight=0;
    // display state
    private Image offScreenBuffer; 
    private int selection=0;
    private int soundsetting=2;
    private int imagesetting=0;
    private int minDouble=0;
    private int maxDouble=5;
    private int mode=0;
    
    private static int SOUND_OFF=0;
    private static int SOUND_LOW=1;
    private static int SOUND_MEDIUM=2;
    private static int SOUND_HIGH=3;
    
    Image background;	
    Image[] volSetting= new Image[11];
	    
    Graphics osg ;
	
    public SettingForm(MJGame m) {
        midlet = m;
    	this.setFullScreenMode(true);
        initResources(); //to initialize offscreen buffer
    }
    
    private void initResources() {
    	//System.out.println("SettingForm.initResources()");
	
        offScreenBuffer = Image.createImage(getWidth(), getHeight());        
        osg = offScreenBuffer.getGraphics();
    	swidth=getWidth();
    	sheight=getHeight();

        try {
        	for (int i=0;i<11;i++) {
        		volSetting[i]=Image.createImage("/res/setting_"+i+".png");
        	}
        	background=Image.createImage("/res/setting.jpg");
        	
        }
        catch (IOException io) {
        	System.out.println("no image");
        }
        finally {
            loadSetting(); //helper method to load usersetting from datastore
        }
    }
    
    /* 
     * Helper method called by paint
     * Draws the background graphics using rudimentary drawing
     * tools. Note that we draw to the offscreenBuffer graphics (osg) not the
     * screen. The offscreenBuffer is an image the size of the screen we render
     * to and then later "flip" (draw) onto the display in one go (see the paint
     * method).
     */
    private void renderScreen() {
        String settingfile="";
        if (soundsetting == SOUND_OFF)
        	settingfile="/res/setting_soff.png";
        else if (soundsetting == SOUND_LOW)
        	settingfile="/res/setting_slow.png";
        else if (soundsetting == SOUND_MEDIUM)
        	settingfile="/res/setting_smedium.png";
        else if (soundsetting == SOUND_HIGH)
        	settingfile="/res/setting_shigh.png";
        
        try {
        	osg.drawImage(Common.extract(background, 0,0,swidth,sheight), 0,  0, Graphics.LEFT| Graphics.TOP);

        	//String url="/res/pic" + imagesetting + ".png";
        	
        	System.out.println("imagesetting="+imagesetting);
        	if (selection==0) {
            	osg.drawImage(Common.extract(background,swidth,DeviceConstant.SETTING_PLAYER_VPOS,swidth,DeviceConstant.SETTING_PLAYER_HEIGHT), 
                		0,  DeviceConstant.SETTING_PLAYER_VPOS, Graphics.LEFT| Graphics.TOP);

            	osg.drawImage(CanvasHelper.getFace(imagesetting), DeviceConstant.SETTING_PLAYERPARAM_HPOS,DeviceConstant.SETTING_PLAYERPARAM_VPOS,
                        Graphics.LEFT | Graphics.TOP);
        	}
        	
        	else if (selection==1) {
            	osg.drawImage(Common.extract(background,swidth,DeviceConstant.SETTING_SOUND_VPOS,swidth,DeviceConstant.SETTING_SOUND_HEIGHT), 
                		0,  DeviceConstant.SETTING_SOUND_VPOS, Graphics.LEFT| Graphics.TOP);
                osg.drawImage(Image.createImage(settingfile),
            			DeviceConstant.SETTING_SOUNDPARAM_HPOS,DeviceConstant.SETTING_SOUNDPARAM_VPOS,Graphics.LEFT | Graphics.TOP);
        	}
        	else if (selection==2) {
            	osg.drawImage(Common.extract(background,swidth,DeviceConstant.SETTING_MIN_VPOS,swidth,DeviceConstant.SETTING_MIN_HEIGHT), 
                		0,  DeviceConstant.SETTING_MIN_VPOS, Graphics.LEFT| Graphics.TOP);

                osg.drawImage(volSetting[minDouble], DeviceConstant.SETTING_MINPARAM_HPOS,DeviceConstant.SETTING_MINPARAM_VPOS,
                		Graphics.LEFT | Graphics.TOP);
        	}
        	else if (selection==3) {
            	osg.drawImage(Common.extract(background,swidth,DeviceConstant.SETTING_MAX_VPOS,swidth,DeviceConstant.SETTING_MAX_HEIGHT), 
                		0,  DeviceConstant.SETTING_MAX_VPOS, Graphics.LEFT| Graphics.TOP);
                osg.drawImage(volSetting[maxDouble], DeviceConstant.SETTING_MINPARAM_HPOS,DeviceConstant.SETTING_MAXPARAM_VPOS,
                		Graphics.LEFT | Graphics.TOP);
        	}
        	
        }
        catch (IOException e) {
            //System.out.println("cant load file at ");
        }
          
    }
    
    
    protected void paint(javax.microedition.lcdui.Graphics graphics) {
        renderScreen();
        graphics.drawImage(offScreenBuffer,0,0, Graphics.LEFT | Graphics.TOP);
    }

    protected void keyPressed(int keyCode) {

    	if (keyCode==-6) {
            midlet.commandReceived(Constant.SETTING_OK);   
        	selection=0;
            saveSetting();
        	return;
    	}
    	else if (keyCode==-7) {
        	midlet.commandReceived(Constant.SETTING_CANCEL);   
        	selection=0;
        	loadSetting();
        	return;
    	}
        switch (getGameAction(keyCode)) {
            
            case UP:
            	selection=selection-1;
            	if (selection<0)
            		selection=0;
                break;
            case DOWN:
                selection = (selection + 1); 
				if (selection>3)
					selection=3;
                break;
            case LEFT:
                if (selection == 1) {
                    if (soundsetting != SOUND_OFF)   
                        soundsetting--;                    
                }
                //imagesetting
                else if (selection == 0 && mode==0){
                    if (imagesetting != 0)   
                        imagesetting--;
                }    
                //min double
                else if (selection == 2 && mode==0){
                    if (minDouble != 0)   
                        minDouble--;
                }
                //maxDouble
                else if (selection == 3 && mode==0){
                    if (maxDouble > minDouble)   
                        maxDouble--;
                }    
                break;
            case RIGHT:
                if (selection  == 1) {
                    if (soundsetting != SOUND_HIGH)
                        soundsetting++;
                }
                else if (selection == 0 ){
                    if (imagesetting < Constant.BOT_NUM-1 && mode==0)   
                        imagesetting++;
                }                
                //min double
                else if (selection == 2 && mode==0){
                    if (minDouble <maxDouble)   
                        minDouble++;
                }
                //maxDouble
                else if (selection == 3 && mode==0){
                    if (maxDouble != 10)   
                        maxDouble++;
                }    
                break;
            default:
        }
        // temporary
        // ask the system to repaint the whole screen
        // a better method will be implemented later
        repaint();  
        serviceRepaints();
    }
        
    /*  helper method for saving user setting into recordstore
     *  not the most efficient method.
     *  will refine this method later
     */
    private boolean saveSetting(){
    	Setting.sound=soundsetting;
    	Setting.picture=imagesetting;
    	Setting.minDouble=minDouble;
    	Setting.maxDouble=maxDouble;
    	return Setting.saveSetting();
    }
    
    /*  helper method for loading user setting from recordstore
     *
     */
    private void loadSetting() {
    	
    	Setting.loadSetting();
    	soundsetting=Setting.sound;
    	imagesetting=Setting.picture;
    	minDouble=Setting.minDouble;
    	maxDouble=Setting.maxDouble;
    }
 
     /*
     * @param mode 0 = game is not started
     * 			   1 = game is started
     */
    public void setMode(int p_mode) {
    	mode=p_mode;
    }
}
