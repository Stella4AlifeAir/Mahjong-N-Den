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

public final class f extends Form implements CommandListener {
  private static String a = "Mahjong DEN";
  
  private MJGame b;
  
  private Command c;
  
  public f(MJGame paramMJGame) {
    super(a);
    this.b = paramMJGame;
    Font font = Font.getFont(64, 0, 8);
    StringItem stringItem1;
    (stringItem1 = new StringItem("Mahjong DEN\n", null)).setFont(font);
    append((Item)stringItem1);
    StringItem stringItem2;
    (stringItem2 = new StringItem(null, "Version 1.28\n")).setFont(font);
    append((Item)stringItem2);
    StringItem stringItem3;
    (stringItem3 = new StringItem(null, "Build 1.28." + d.a + "DEMO" + "\n")).setFont(font);
    append((Item)stringItem3);
    StringItem stringItem4;
    (stringItem4 = new StringItem(null, "Copyright (C) 2005\nPHEE! PHOO! Pte Ltd\n")).setFont(font);
    append((Item)stringItem4);
    StringItem stringItem5;
    (stringItem5 = new StringItem(null, "All rights reserved\n")).setFont(font);
    append((Item)stringItem5);
    this.c = new Command("Back", 7, 1);
    addCommand(this.c);
    setCommandListener(this);
  }
  
  public final void commandAction(Command paramCommand, Displayable paramDisplayable) {
    this.b.a(d.J);
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\form\f.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */