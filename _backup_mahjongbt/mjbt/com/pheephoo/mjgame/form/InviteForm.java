/*
 * InviteForm.java
 *
 * Created on 2004�~4��21��, �U�� 2:50
 */

package com.pheephoo.mjgame.form;

import com.pheephoo.mjgame.*;
import com.pheephoo.mjgame.messaging.*;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

/**
 *
 * @author  sam
 * @version
 */
public class InviteForm extends Form implements CommandListener  {
    
    private static String TITLE = "Invite Friend";

    private MJGame midlet;

    private Command cancelCommand;
    private Command okCommand;
    
    private StringItem instructionSItem;
    private TextField partner1TField, partner2TField, partner3TField;

    public String msisdn1="";
    public String msisdn2="";
    public String msisdn3="";
    
	boolean enabled=true;

    
    public InviteForm(MJGame m) {
        super(TITLE);
        midlet = m;
        initializeForm();
        

    }
    
    public void initializeForm() {
        instructionSItem = new StringItem ("Register your Mahjong partners' mobile #", "");
        partner1TField = new TextField ("1", "", 10, TextField.NUMERIC);
        partner2TField= new TextField ("2", "", 10, TextField.NUMERIC);
        partner3TField= new TextField ("3", "", 10, TextField.NUMERIC);

        append(instructionSItem);
        append(partner1TField);
        append(partner2TField);
        append(partner3TField);
        
        
        cancelCommand = new Command("Cancel", Command.CANCEL,1);            
        okCommand = new Command("Send", Command.OK,1);
        addCommand(okCommand);
        addCommand(cancelCommand);
        setCommandListener(this);
        

    }
    
    
    public void commandAction(javax.microedition.lcdui.Command command, javax.microedition.lcdui.Displayable displayable) {

    	if (!enabled && command !=cancelCommand) {
			return;
		}
		enabled=false;

		if (command == cancelCommand) {
            midlet.commandReceived(Constant.SETTING_CANCEL);   
            enabled=true;
		}
        else if (command == okCommand) {
                    	
        	if (partner1TField.getString().length()==0 &&
        			partner2TField.getString().length()==0 &&
					partner3TField.getString().length()==0 ) {
            	midlet.commandReceived(Constant.PRIVATE_ERRINVITE1);         
            	enabled=true;
        	}
        	else {
        		try {
	        		if (partner1TField.getString().length()!=0 ) {
	        			msisdn1=partner1TField.getString();
	        			SMSEngine.sendMessage(partner1TField.getString(),Constant.SMS_INVITE);
	        		}
	        		if (partner2TField.getString().length()!=0 ) {
	        			msisdn2=partner2TField.getString();
	        			SMSEngine.sendMessage(partner2TField.getString(),Constant.SMS_INVITE);
	        		}
	        		if (partner3TField.getString().length()!=0 ) {
	        			msisdn3=partner3TField.getString();
	        			SMSEngine.sendMessage(partner3TField.getString(),Constant.SMS_INVITE);
	        		}
	        		midlet.commandReceived(Constant.PRIVATE_INVITEOK); 
	            	enabled=true;

        		}
        		catch (SMSSendException e) {
        			midlet.commandReceived(Constant.PRIVATE_ERRINVITE3);
                	enabled=true;
        		}
        	}
        }
    }


}
