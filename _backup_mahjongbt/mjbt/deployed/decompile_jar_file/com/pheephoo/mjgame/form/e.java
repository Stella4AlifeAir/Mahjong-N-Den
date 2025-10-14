package com.pheephoo.mjgame.form;

import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.ui.h;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;

public final class e extends Form implements CommandListener {
  private static String b = "Select Room";
  
  private Command c;
  
  public List a;
  
  private h d;
  
  public e(MJGame paramMJGame, h paramh) {
    super(b);
    this.d = paramh;
    a();
  }
  
  public final void a() {
    this.a = new List("Select Room ", 1, this.d.b, null);
    this.c = new Command("Ok", 4, 1);
    this.a.addCommand(this.c);
    this.a.setCommandListener(this);
  }
  
  public final void commandAction(Command paramCommand, Displayable paramDisplayable) {
    if (paramCommand == this.c)
      this.d.b(this.a.getSelectedIndex()); 
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\form\e.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */