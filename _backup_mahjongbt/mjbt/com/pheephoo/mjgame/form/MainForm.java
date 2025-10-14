/*
 * MainForm.java
 */

package com.pheephoo.mjgame.form;

import com.pheephoo.mjgame.*;
import javax.microedition.lcdui.*;

/**
 * This is the first screen to be loaded by Midlet.
 * It displays a background image and list of command as the main entry to the application
 * 
 * @author Samuel
 * @version 1.0.1 19/10/2004
 */
public class MainForm implements CommandListener {
	boolean enabled=true;
	
	private MJGame midlet;
	
	private Command exitCommand;
	private Command okCommand;
	public List list;
	
	
	
	public MainForm(MJGame m) {
		
		list= new List("Mahjong DEN",List.IMPLICIT,
				new String[] {"New Game",
				"Settings","Private Network",
				"Public Network","History","Help","About","Exit","BTServer","BTClient"},null);
		
		midlet=m;
		okCommand=new Command("Select",Command.SCREEN,1);	     
		exitCommand=new Command("Exit",Command.EXIT,2);
		list.addCommand(okCommand);
		list.addCommand(exitCommand);
		list.setCommandListener(this);
	}
	
	public void commandAction(Command command, Displayable displayable) {
		int index=list.getSelectedIndex();
		if (!enabled) {
			return;
		}
		enabled=false;
		if (index== 0) {
			midlet.commandReceived(Constant.MAIN_PLAY);            
		}
		else if (index == 1) {   
			midlet.commandReceived(Constant.MAIN_SETTING);
		}
		else if (index == 2) {
			
			midlet.commandReceived(Constant.MAIN_PRIVATE);
		}
		else if (index == 3) {
			midlet.commandReceived(Constant.MAIN_PUBLIC);
		}
		else if (index == 4) {
			midlet.commandReceived(Constant.MAIN_HISTORY);
		}

		
		else if (index == 5) {
			midlet.commandReceived(Constant.MAIN_NOTAVAILABLE);        	
		}        
		else if (index == 6) {
			midlet.commandReceived(Constant.MAIN_ABOUT);
		}        
		else if (index == 7) {
			midlet.commandReceived(Constant.MAIN_EXIT);
		}
		//debugging code
//		else if (index == 8) {
//			midlet.commandReceived(Constant.MAIN_TEST);
//		}
		//debugging code
		else if (index == 8) {
			System.out.println("bt demo");
			midlet.commandReceived(Constant.MAIN_BTSERVER);
		}
		else if (index == 9) {
			System.out.println("bt demo");
			midlet.commandReceived(Constant.MAIN_BTCLIENT);
		}

		
		//end debugging code
		
		if (command == exitCommand) {
			midlet.commandReceived(Constant.MAIN_EXIT);
		}
		enabled=true;
//		System.out.println("MainForm finished");
	}
}
