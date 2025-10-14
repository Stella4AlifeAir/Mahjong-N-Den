/*
 * InviteForm.java
 *
 * Created on 2004�~4��21��, �U�� 2:50
 */

package com.pheephoo.mjgame.form;

import com.pheephoo.mjgame.*;
import com.pheephoo.mjgame.messaging.*;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
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
public class RegistrationForm extends Form implements CommandListener  {
    
    private MJGame midlet;

    private Command cancelCommand;
    private Command okCommand;
    
    private StringItem instructionSItem1;
    private StringItem instructionSItem2;
    private TextField nickField;

    
	boolean enabled=true;
    
    
    public RegistrationForm(MJGame m,String title, int formNum) {
        super(title);
        midlet = m;
        
        initializeForm1(formNum);
    }
    
    public void initializeForm1(int formNum) {
        if (formNum==1) {
        	instructionSItem1 = new StringItem (Constant.MSG_REGISTRATION2, "");        	
        }
        else {
        	instructionSItem1 = new StringItem (Constant.MSG_REGISTRATION4, "");        	        	
        }
        
        
        nickField = new TextField ("", "", 10, TextField.PLAIN);
        
        

        
        instructionSItem2 = new StringItem ("\nAllowed Chars(a-z,0-9,'.', '_', '-')", "");

        
        append(instructionSItem1);
        append(nickField);
        append(instructionSItem2);
        
        
        cancelCommand = new Command("Cancel", Command.CANCEL,1);            
        okCommand = new Command("Ok", Command.OK,1);
        addCommand(okCommand);
        addCommand(cancelCommand);
        setCommandListener(this);
    }

    
    public void commandAction(javax.microedition.lcdui.Command command, javax.microedition.lcdui.Displayable displayable) {
		if (!enabled) {
			return;
		}
		enabled=false;

		if (command == cancelCommand) {
            midlet.commandReceived(Constant.REGISTRATION_ERR1);   
        }
        else if (command == okCommand) {
            String nick=nickField.getString();        	
        	if (nick.length()==0 ) {
             	midlet.commandReceived(Constant.NICK_ERR1);           		
        	}
			else if (nick.indexOf(" ")!=-1) {
             	midlet.commandReceived(Constant.NICK_ERR2);           		
			}
			else {			
	        	boolean nickCorrect=true;
				for (int i=0;i<nick.length();i++) {
					int code=(int) nick.charAt(i);
					
					//correct
					if ( (code>=65 && code<=90) || (code>=97&&code<=122) 
							|| (code>=48&&code<=57) || code==95 || code==46  ) {					
					}
					else {
						nickCorrect=false;
						midlet.commandReceived(Constant.NICK_ERR2);
						break;
					}
				}
				if (nickCorrect)
					midlet.commandReceived(Constant.REGISTRATION_NICK_OK,nick); 
			}
        	enabled=true;
        	
        }
    }


}
