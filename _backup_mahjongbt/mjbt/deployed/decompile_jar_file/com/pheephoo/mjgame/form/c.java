package com.pheephoo.mjgame.form;

import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.d;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;

public final class c extends Form implements CommandListener {
  private static String b = "History Mode";
  
  private MJGame c;
  
  private Command d;
  
  public List a;
  
  public c(MJGame paramMJGame) {
    super(b);
    this.c = paramMJGame;
    a();
  }
  
  public final void a() {
    String[] arrayOfString = { "Offline", "Online" };
    this.a = new List("Select History", 1, arrayOfString, null);
    this.d = new Command("Ok", 4, 1);
    this.a.addCommand(this.d);
    this.a.setCommandListener(this);
  }
  
  public final void commandAction(Command paramCommand, Displayable paramDisplayable) {
    if (paramCommand == this.d) {
      if (this.a.getSelectedIndex() == 0) {
        this.c.a(d.L);
        return;
      } 
      this.c.a(d.M);
    } 
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\form\c.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */