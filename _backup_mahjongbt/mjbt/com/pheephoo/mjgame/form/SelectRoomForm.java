/*
 * InviteForm.java
 *
 * Created on 2004�~4��21��, �U�� 2:50
 */

package com.pheephoo.mjgame.form;

import com.pheephoo.mjgame.*;
import com.pheephoo.mjgame.engine.*;
import com.pheephoo.mjgame.ui.PublicNetworkCanvas;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author  sam
 * @version
 */
public class SelectRoomForm extends Form implements CommandListener  {
    
    private static String TITLE = "Select Room";
    private MJGame midlet;
    private Command okCommand;
    
    
    public List list;

    private PublicNetworkCanvas canvas;
    public SelectRoomForm(MJGame m, PublicNetworkCanvas _canvas) {
        super(TITLE);
        midlet = m;
        canvas=_canvas;
        initializeForm();

    }
    
    public void initializeForm() {
        list= new List("Select Room ",List.EXCLUSIVE,canvas.roomCategory,null);
        okCommand = new Command("Ok", Command.OK,1);
        list.addCommand(okCommand);
        list.setCommandListener(this);
    }
    
    public void commandAction(javax.microedition.lcdui.Command command, javax.microedition.lcdui.Displayable displayable) {
    	if (command == okCommand) {
    		canvas.onResponseRoomSelected(list.getSelectedIndex());
        }
    }
}
