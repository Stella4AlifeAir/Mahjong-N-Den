/*
 * MainForm.java
 */

package com.pheephoo.mjgame.ui;

import com.pheephoo.mjgame.*;

import javax.microedition.lcdui.*;

/**
 * This is the first screen to be loaded by Midlet.
 * It displays a background image and list of command as the main entry to the application
 * 
 * @author Samuel
 * @version 1.0.1 19/10/2004
 */
public class TestForm implements CommandListener {
	
	private MJGame midlet;
	
	private Command exitCommand;
	private Command okCommand;
	public List list;
	
	public TestForm(MJGame m) {
		
		list= new List("Mahjong",List.IMPLICIT,
				new String[] {"SPEED1","SPEED2","PONG1","CONCEALKONG1","CONCEALKONG2","CONCEALKONG3",
				"CONCEALKONG4","ROBKONG1","ROBKONG2",   "ClearHand1","ClearHand2","ClearHand3","RESET","Exit"},null);
		
		
		midlet=m;
		okCommand=new Command("Select",Command.SCREEN,1);	     
		exitCommand=new Command("Exit",Command.EXIT,2);
		list.addCommand(okCommand);
		list.addCommand(exitCommand);
		list.setCommandListener(this);
		
		
	}
	
	public void commandAction(Command command, Displayable displayable) {
		int index=list.getSelectedIndex();
		
		if (index== 0) {
			midlet.commandReceived(Constant.TEST_SPEEDOFFLINE1);
		}
		else if (index == 1) {   
			midlet.commandReceived(Constant.TEST_SPEEDOFFLINE2);
		}
		else if (index == 2) {
			
			midlet.commandReceived(Constant.TEST_PONG1);
		}
		else if (index == 3) {
			midlet.commandReceived(Constant.TEST_CONCEALKONG1);
		}
		else if (index == 4) {
			midlet.commandReceived(Constant.TEST_CONCEALKONG2);
		}
		else if (index == 5) {
			midlet.commandReceived(Constant.TEST_CONCEALKONG3);
		}
		else if (index == 6) {
			midlet.commandReceived(Constant.TEST_CONCEALKONG4);
		}
		else if (index == 7) {
			midlet.commandReceived(Constant.TEST_ROBKONG1);
		}
		else if (index == 8) {
			midlet.commandReceived(Constant.TEST_ROBKONG2);
		}

		
		else if (index == 9) {
			midlet.commandReceived(Constant.TEST_CLEARHAND1);
		}
		else if (index == 10) {
			midlet.commandReceived(Constant.TEST_CLEARHAND2);
		}
		else if (index == 11) {
			midlet.commandReceived(Constant.TEST_CLEARHAND3);
		}

		else if (index == 12) {
			midlet.resetCid();
		}        
		else if (index == 13) {
			midlet.commandReceived(Constant.MAIN_MAINMENU);
		}
		//end debugging code
	
		if (command == exitCommand) {
			midlet.commandReceived(Constant.MAIN_MAINMENU);
		}
		
	}
}
