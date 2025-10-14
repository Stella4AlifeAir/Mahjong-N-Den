/*
 * InviteForm.java
 *
 * Created on 2004�~4��21��, �U�� 2:50
 */

package com.pheephoo.mjgame.form;

import com.pheephoo.mjgame.*;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;

/**
 *
 * @author  sam
 * @version
 */
public class SelectHistoryForm extends Form implements CommandListener  {
    
    private static String TITLE = "History Mode";

    private MJGame midlet;
    private Command okCommand;
    public List list;
    public SelectHistoryForm(MJGame m) {
        super(TITLE);
        midlet = m;
        initializeForm();
    }
    
    public void initializeForm() {

    	String selection[]=new String[] {"Offline","Online"};
        list= new List("Select History",List.EXCLUSIVE,selection,null);
        okCommand = new Command("Ok", Command.OK,1);
        list.addCommand(okCommand);
        list.setCommandListener(this);
    }
    
    public void commandAction(javax.microedition.lcdui.Command command, javax.microedition.lcdui.Displayable displayable) {
    	if (command == okCommand) {
    		if (list.getSelectedIndex()==0) {
    			midlet.commandReceived(Constant.HISTORY_OFFLINE);
    		}
    		else {
    			midlet.commandReceived(Constant.HISTORY_ONLINE);    			
    		}
        }
    }
}
