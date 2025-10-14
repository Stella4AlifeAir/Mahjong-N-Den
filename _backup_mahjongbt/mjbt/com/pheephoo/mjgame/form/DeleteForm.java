/*
 * InviteForm.java
 *
 * Created on 2004�~4��21��, �U�� 2:50
 */

package com.pheephoo.mjgame.form;

import com.pheephoo.mjgame.*;
import com.pheephoo.mjgame.engine.*;
import com.pheephoo.mjgame.network.Contact;
import com.pheephoo.mjgame.ui.PrivateNetworkCanvas;

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
public class DeleteForm extends Form implements Runnable, CommandListener  {
    
    private static String TITLE = "Delete Friend";

    private MJGame midlet;
    private boolean running=true;    

    private Command cancelCommand;
    private Command okCommand;
    
    private StringItem instructionSItem;
    private TextField partner1TField, partner2TField, partner3TField;

    public String msisdn1="";
    public String msisdn2="";
    public String msisdn3="";
    
    public List list;

    private PrivateNetworkCanvas canvas;
    
    public DeleteForm(MJGame m, PrivateNetworkCanvas _canvas) {
        super(TITLE);
        midlet = m;
        canvas=_canvas;
        initializeForm();

    }
    
    public void initializeForm() {

    	canvas.engine.pausePoll();
    	System.out.println("DeleteForm.initializeForm()");
    	int size= canvas.contacts.size();
    	String contacts[]= new String[size];
    	System.out.println("contact size="+size);
    	
    	for (int i=0;i<size;i++) {
    		Contact contact=(Contact) canvas.contacts.elementAt(i);
    		contacts[i]=contact.nick+ " (" + contact.msisdn + ")";
    	}
    	
        list= new List("Delete Contact",List.MULTIPLE,contacts,null);

        cancelCommand = new Command("Cancel", Command.CANCEL,1);            
        okCommand = new Command("Delete", Command.OK,1);
        list.addCommand(okCommand);
        list.addCommand(cancelCommand);
        list.setCommandListener(this);

    }
    
    /*Called when thread is started. Controls the main loop
    */
    public void run() {
        while(running) {
            
        }
    }
    
    public void commandAction(javax.microedition.lcdui.Command command, javax.microedition.lcdui.Displayable displayable) {
        if (command == cancelCommand) {
            midlet.commandReceived(Constant.SETTING_CANCEL);   
        }
        else if (command == okCommand) {
        	boolean contactindex[] = new boolean[list.size()];
        	int numOfSelection=0;
        	for(int i=0;i<contactindex.length;i++) {
        		contactindex[i]=list.isSelected(i);
        		if (contactindex[i]) {
        			numOfSelection++;
        		}
        	}        	
        	canvas.deleteContact(contactindex,numOfSelection);
        	midlet.commandReceived(Constant.PRIVATE_DELETEOK);
        	
        	//call the canvas to delete

        }
        canvas.engine.resumePoll();

    }

}
