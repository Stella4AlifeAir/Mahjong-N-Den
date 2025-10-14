/*
 * MainForm.java
 *
 * Created on 2004��4��19��, ����10:34
 */

package com.pheephoo.mjgame.form;

import com.pheephoo.mjgame.*;
import java.io.IOException;
import javax.microedition.lcdui.*;

/**
 *
 * @author  programmer2
 * @version
 */
public class AboutForm extends Form implements CommandListener {
    private static String TITLE = "Mahjong DEN";
    
    private MJGame midlet;
    private Command exitCommand;

    /*  Build the MainForm
     *
     */
    public AboutForm(MJGame m) {
        super(TITLE);
        midlet=m;
        Font normalFont=Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN,Font.SIZE_SMALL);
        StringItem title=new StringItem("Mahjong DEN\n",null);
        title.setFont(normalFont);
        append(title);

        StringItem version=new StringItem(null,"Version 1.28\n");
        version.setFont(normalFont);
        append(version);
        StringItem desc=new StringItem(null,"Build 1.28."+ Constant.VERSION+ "DEMO"+"\n");  
        desc.setFont(normalFont);
        append(desc);
        StringItem copyright=new StringItem(null,"Copyright (C) 2005\nPHEE! PHOO! Pte Ltd\n");  
        copyright.setFont(normalFont);
        append(copyright);
        StringItem right=new StringItem(null,"All rights reserved"+ "\n");  
        right.setFont(normalFont);
        append(right);

        exitCommand = new Command("Back", Command.EXIT,1);            
        addCommand(exitCommand);
        setCommandListener(this);

    }
    
    public void commandAction(Command command, Displayable displayable) {
            midlet.commandReceived(Constant.MAIN_MAINMENU);
    }
}
