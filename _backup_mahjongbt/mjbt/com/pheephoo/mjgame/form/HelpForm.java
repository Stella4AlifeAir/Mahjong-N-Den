/*
 * InviteForm.java
 *
 * Created on 2004¦~4¤ë21¤é, ¤U¤È 2:50
 */

package com.pheephoo.mjgame.form;

import com.pheephoo.mjgame.Constant;
import com.pheephoo.mjgame.*;
import javax.microedition.lcdui.*;

//import com.pheephoo.mjgame.engine.MJTable;

/**
 *
 * @author  sam
 * @version
 */
public class HelpForm extends Form implements CommandListener  {
    
    private MJGame midlet;
    private Command exitCommand;
    
    public HelpForm(MJGame m) {
        super("Help");
        midlet=m;
        Font boldFont=Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_BOLD,Font.SIZE_MEDIUM);
        Font normalFont=Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN,Font.SIZE_SMALL);

        StringItem title=new StringItem("New Game\n",null);
        title.setFont(boldFont);
        append(title);
        StringItem desc=new StringItem(null,"Play an offline version of the game on a default challenge mode.\n ");  
        desc.setFont(normalFont);
        append(desc);
        
    	title=new StringItem("Setting\n",null);
        title.setFont(boldFont);
        append(title);
        desc=new StringItem(null,"Game Setting: Change user image, sound effect\n ");
        desc.setFont(normalFont);
        append(desc);
        desc=new StringItem(null,"Game Rules: Min Double  & Max Double. For network playing, this setting will apply only when user is the host.\n");
        desc.setFont(normalFont);
        append(desc);

        
    	title=new StringItem("Private Network\n",null);
        title.setFont(boldFont);
        append(title);
        desc=new StringItem(null,"User will be charge one dollar per complete round of four winds mahjong game. In private network user are only allowed to play with their selected friend in their own network friend list. If there is none they can invite friend automatically via the Mahjong Den server exchange. Normal SMS charges will apply. Open chatting is available via GPRS Network. User will enjoy a secluded game with their friends.\n");
        desc.setFont(normalFont);
        append(desc);

    	title=new StringItem("Public Network\n",null);
        title.setFont(boldFont);
        append(title);
        desc=new StringItem(null,"User will be charge one dollar per day of entry into the public network they can login any time within the same day to play a game with anyone online. Open chatting is available via GPRS Network. Normal SMS charges will apply.\n");
        desc.setFont(normalFont);
        append(desc);

    	title=new StringItem("History\n",null);
        title.setFont(boldFont);
        append(title);
        
        desc=new StringItem(null,"All game score of all completed round for both offline and online are kept here. Online score will be save with date of each recorded complete game of four wind mahjong.\n");
        desc.setFont(normalFont);
        append(desc);
        
    	title=new StringItem("About\n",null);
        title.setFont(boldFont);
        append(title);
        desc=new StringItem(null,"Version of the game and the developer.\n");
        desc.setFont(normalFont);
        append(desc);

    	title=new StringItem("Exit\n",null);
        title.setFont(boldFont);
        append(title);
        desc=new StringItem(null,"To quit the game\n");
        desc.setFont(normalFont);
        append(desc);

        
        exitCommand = new Command("Back", Command.EXIT,1);            
        addCommand(exitCommand);
        setCommandListener(this);
    }
    
    public void commandAction(Command command, Displayable displayable) {
            midlet.commandReceived(Constant.MAIN_MAINMENU);
    }
}
