/*
 * Created on 2004/4/28
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pheephoo.utilx;

import java.util.Random;
import java.lang.Math;
import javax.microedition.lcdui.*;

/**
 * @author sam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Common {

    private static Random random= new Random();
    //  Will return a number between teh startint and the maxint
    public static int getRandomInt(int low, int high) {
        return low + (Math.abs( random.nextInt()>>>1) % ((high-low) + 1)); 
    }
    
    
    public static Image extract(Image source, int x, int y, int width, int height) {
    	Image result = Image.createImage(width, height);
    	
        if(x+width > source.getWidth() || y+ height > source.getHeight() ) {
            System.out.println("error");
        }
        //draw the image
        result.getGraphics().drawImage(source, -x, -y, Graphics.TOP|Graphics.LEFT);
        return result;
    }
    
}
