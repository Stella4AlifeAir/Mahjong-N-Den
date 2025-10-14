package com.pheephoo.mjgame.form;

import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.d;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;

public final class g extends Form implements CommandListener {
  private MJGame a;
  
  private Command b;
  
  public g(MJGame paramMJGame) {
    super("Help");
    this.a = paramMJGame;
    Font font1 = Font.getFont(64, 1, 0);
    Font font2 = Font.getFont(64, 0, 8);
    StringItem stringItem1;
    (stringItem1 = new StringItem("New Game\n", null)).setFont(font1);
    append((Item)stringItem1);
    StringItem stringItem2;
    (stringItem2 = new StringItem(null, "Play an offline version of the game on a default challenge mode.\n ")).setFont(font2);
    append((Item)stringItem2);
    (stringItem1 = new StringItem("Setting\n", null)).setFont(font1);
    append((Item)stringItem1);
    (stringItem2 = new StringItem(null, "Game Setting: Change user image, sound effect\n ")).setFont(font2);
    append((Item)stringItem2);
    (stringItem2 = new StringItem(null, "Game Rules: Min Double  & Max Double. For network playing, this setting will apply only when user is the host.\n")).setFont(font2);
    append((Item)stringItem2);
    (stringItem1 = new StringItem("Private Network\n", null)).setFont(font1);
    append((Item)stringItem1);
    (stringItem2 = new StringItem(null, "User will be charge one dollar per complete round of four winds mahjong game. In private network user are only allowed to play with their selected friend in their own network friend list. If there is none they can invite friend automatically via the Mahjong Den server exchange. Normal SMS charges will apply. Open chatting is available via GPRS Network. User will enjoy a secluded game with their friends.\n")).setFont(font2);
    append((Item)stringItem2);
    (stringItem1 = new StringItem("Public Network\n", null)).setFont(font1);
    append((Item)stringItem1);
    (stringItem2 = new StringItem(null, "User will be charge one dollar per day of entry into the public network they can login any time within the same day to play a game with anyone online. Open chatting is available via GPRS Network. Normal SMS charges will apply.\n")).setFont(font2);
    append((Item)stringItem2);
    (stringItem1 = new StringItem("History\n", null)).setFont(font1);
    append((Item)stringItem1);
    (stringItem2 = new StringItem(null, "All game score of all completed round for both offline and online are kept here. Online score will be save with date of each recorded complete game of four wind mahjong.\n")).setFont(font2);
    append((Item)stringItem2);
    (stringItem1 = new StringItem("About\n", null)).setFont(font1);
    append((Item)stringItem1);
    (stringItem2 = new StringItem(null, "Version of the game and the developer.\n")).setFont(font2);
    append((Item)stringItem2);
    (stringItem1 = new StringItem("Exit\n", null)).setFont(font1);
    append((Item)stringItem1);
    (stringItem2 = new StringItem(null, "To quit the game\n")).setFont(font2);
    append((Item)stringItem2);
    this.b = new Command("Back", 7, 1);
    addCommand(this.b);
    setCommandListener(this);
  }
  
  public final void commandAction(Command paramCommand, Displayable paramDisplayable) {
    this.a.a(d.J);
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\form\g.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */