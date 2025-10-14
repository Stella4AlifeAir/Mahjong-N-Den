package com.pheephoo.mjgame.form;

import com.pheephoo.mjgame.*;
import javax.microedition.lcdui.*;

/*
 * this form serve for various pause form. It loads command base on the parameter passed to the constructor
 * @author Samuel
 * @version 1.0.1 19/10/2004
 */
public class PauseForm implements CommandListener {
    
	boolean enabled=true;

    private MJGame midlet;
    public List list;

    private Command backCommand;
        
    private int screen=0;  //0=pause while game is playing, 1=pause in the public network mode, 2=
    
    public PauseForm(MJGame m, int _screen) {
        midlet=m;
        screen=_screen;

        if (screen==Constant.OFFLINE_PAUSE) {
            list= new List("Mahjong DEN (Option)",List.IMPLICIT,
        			new String[] {"Resume","Player Info","Game Info","Setting", "Quit Game"},null);
        }
        else if (screen==Constant.NETWORKCOMMON_PAUSE) {
            list= new List("Network DEN (Option)",List.IMPLICIT,
        			new String[] {"Resume","Player Info", "Game Info","Send Message","Setting","Quit Game"},null);
        }        
        else if (screen==Constant.PUBLIC_CPAUSE) {
            list= new List("Network DEN (Option)",List.IMPLICIT,
        			new String[] {"Resume","Quit Game"},null);        
        }        	
        else if (screen==Constant.PRIVATE_LPAUSE) {
            list= new List("Network (Option)",List.IMPLICIT,
        			new String[] {"Resume","Invite Friend","Delete Friend","Quit Game"},null);        
        }        	
        else if (screen==Constant.PRIVATE_CPAUSE) {
            list= new List("Network (Option)",List.IMPLICIT,
        			new String[] {"Resume","Quit Game"},null);        
        }        	
        

    	backCommand=new Command("Back",Command.BACK,2);
    	list.addCommand(backCommand);
    	list.setCommandListener(this);
        
    }
    
    public void commandAction(Command command, Displayable displayable) {
//        System.out.println("PauseForm.commandAction()");

		if (!enabled) {
			return;
		}
		enabled=false;

        int index=list.getSelectedIndex();

        if (screen==Constant.OFFLINE_PAUSE) {
            if (command==backCommand) {
            	midlet.commandReceived(Constant.OFFLINE_CONTINUE);            	
                doCleanUp();
            }
            else if (index== 0) {
            	midlet.commandReceived(Constant.OFFLINE_CONTINUE);
                doCleanUp();
        	}
        	else if (index==1) {
            	midlet.commandReceived(Constant.COMMON_PLAYERINFO);
                doCleanUp();
        	}
        	
        	else if (index==2) {
            	midlet.commandReceived(Constant.COMMON_GAMEINFO);
                doCleanUp();
        	}
        	else if (index==3) {
                midlet.commandReceived(Constant.ALL_SETTING);
        	}
        	else if (index==4) {
        		
            	midlet.commandReceived(Constant.OFFLINE_QUITGAME);
                doCleanUp();
        	}

        }
        else if (screen==Constant.NETWORKCOMMON_PAUSE) {
            if (command==backCommand) {
            	midlet.commandReceived(Constant.NETWORKCOMMON_CONTINUE);
                doCleanUp();
            }
            else if (index== 0) {
            	midlet.commandReceived(Constant.NETWORKCOMMON_CONTINUE);
                doCleanUp();
        	}
        	else if (index==1) {
            	midlet.commandReceived(Constant.COMMON_PLAYERINFO);
                doCleanUp();
        	}
        	else if (index==2) {
            	midlet.commandReceived(Constant.COMMON_GAMEINFO);
                doCleanUp();
        	}
        	else if (index==3) {
            	midlet.commandReceived(Constant.NETWORKCOMMON_SENDMESSAGE);
        	}
        	else if (index==4) {
                midlet.commandReceived(Constant.ALL_SETTING);
            }
        	else if (index==5) {
            	midlet.commandReceived(Constant.NETWORKCOMMON_QUITGAME);
                doCleanUp();
        	}
        }        
        else if (screen==Constant.PUBLIC_CPAUSE) {
        	if (index== 0) {
            	midlet.commandReceived(Constant.PUBLIC_CONTINUE);
                doCleanUp();
        	}
        	else if (index==1) {
            	midlet.commandReceived(Constant.NETWORKCOMMON_QUITGAME);
                doCleanUp();
        	}
            if (command==backCommand) {
            	midlet.commandReceived(Constant.PUBLIC_CONTINUE);
                doCleanUp();
            }
        } 
        else if (screen==Constant.PRIVATE_LPAUSE) {
            if (command==backCommand) {
            	midlet.commandReceived(Constant.PRIVATE_CONTINUE);
                doCleanUp();
            }
        	else if (index== 0) {
        		System.out.println("privatecontinue received");
            	midlet.commandReceived(Constant.PRIVATE_CONTINUE);
                doCleanUp();
        	}
        	else if (index==1) {
                midlet.commandReceived(Constant.PRIVATE_INVITEFRIEND);
            }
        	else if (index==2) {
                midlet.commandReceived(Constant.PRIVATE_DELETEFRIEND);
            }
        	else if (index==3) {
            	midlet.commandReceived(Constant.NETWORKCOMMON_QUITGAME);
                doCleanUp();
        	}
        } 
   
        else if (screen==Constant.PRIVATE_CPAUSE) {
            if (command==backCommand) {
            	midlet.commandReceived(Constant.PRIVATE_CONTINUE);
                doCleanUp();
            }
            else if (index== 0) {
            	midlet.commandReceived(Constant.PRIVATE_CONTINUE);
                doCleanUp();
        	}
        	else if (index==1) {
            	midlet.commandReceived(Constant.NETWORKCOMMON_QUITGAME);
                doCleanUp();
        	}
        } 
		enabled=true;
    }
    
    private void doCleanUp() {
    	midlet=null;
    }

}
