/*
 * InviteForm.java
 *
 * Created on 2004¦~4¤ë21¤é, ¤U¤È 2:50
 */

package com.pheephoo.mjgame.ui;


import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;
import com.pheephoo.mjgame.*;
//import com.pheephoo.mjgame.engine.MJTable;

/**
 *
 * @author  sam
 * @version
 */
public class Dialog extends Form implements CommandListener  {
    
	boolean enabled=true;

    private MJGame midlet;
    
    private PrivateNetworkCanvas canvas;
    private PublicNetworkCanvas publiccanvas;
    private boolean running=true;    

    private Command okCommand;
    private Command cancelCommand;
    
    
    private StringItem message;
    private int action1=0;
    private int action2=0;

    
    

    public Dialog(PublicNetworkCanvas _canvas, String title, String message, 
    		String buttonLabel1, String buttonLabel2,
			int _action1, int _action2) {
        super(title);
        //System.out.println("Dialog constructor");
        publiccanvas=_canvas;
    	append(new StringItem(message,""));  
    
    	action1=_action1;
    	action2=_action2;
    	
        okCommand = new Command(buttonLabel1, Command.SCREEN,1);            
        addCommand(okCommand);

        if (buttonLabel2!=null) {
            cancelCommand = new Command(buttonLabel2, Command.SCREEN,2);            
            addCommand(cancelCommand);
        }
        setCommandListener(this);
    }

    public Dialog(PublicNetworkCanvas _canvas, String title, String[] message, 
    		String buttonLabel1, String buttonLabel2,
			int _action1, int _action2) {
        super(title);
        //System.out.println("Dialog constructor");
        publiccanvas=_canvas;
        
        for (int i=0;i<message.length;i++) {
        	append(new StringItem(message[i],""));          	
        }
    	action1=_action1;
    	action2=_action2;
        okCommand = new Command(buttonLabel1, Command.SCREEN,1);            
        addCommand(okCommand);

        if (buttonLabel2!=null) {
            cancelCommand = new Command(buttonLabel2, Command.SCREEN,2);            
            addCommand(cancelCommand);
        }
        setCommandListener(this);
    }

    public Dialog(PrivateNetworkCanvas _canvas, String title, String[] message, 
    		String buttonLabel1, String buttonLabel2,
			int _action1, int _action2) {
        super(title);
        //System.out.println("Dialog constructor");
        canvas=_canvas;
        
        for (int i=0;i<message.length;i++) {
        	append(new StringItem(message[i],""));          	
        }
    	action1=_action1;
    	action2=_action2;
        okCommand = new Command(buttonLabel1, Command.SCREEN,1);            
        addCommand(okCommand);

        if (buttonLabel2!=null) {
            cancelCommand = new Command(buttonLabel2, Command.SCREEN,2);            
            addCommand(cancelCommand);
        }
        setCommandListener(this);
    }
    
    public Dialog(PrivateNetworkCanvas _canvas, String title, String message, 
    		String buttonLabel1, String buttonLabel2,
			int _action1, int _action2) {
        super(title);
        canvas=_canvas;
        
    	append(new StringItem(message,""));  
    
    	action1=_action1;
    	action2=_action2;
    	
        okCommand = new Command(buttonLabel1, Command.SCREEN,1);            
        cancelCommand = new Command(buttonLabel2, Command.SCREEN,2);            

        addCommand(okCommand);
        addCommand(cancelCommand);
        setCommandListener(this);
    }
    
    public void commandAction(Command command, Displayable displayable) {
		if (!enabled) {
			return;
		}
		enabled=false;

    	if (command==okCommand) {
    		if (canvas!=null) {
    			canvas.onDialogResponse(action1);
    		}
    		else {
    			publiccanvas.onDialogResponse(action1);
    		}
    	}
    	else {
    		if (canvas!=null) {
    			canvas.onDialogResponse(action2);
    		}
    		else {
    			publiccanvas.onDialogResponse(action2);
    		}
    	}
    	
    }
}
