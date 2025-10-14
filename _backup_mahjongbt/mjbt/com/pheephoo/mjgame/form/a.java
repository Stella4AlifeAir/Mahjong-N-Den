package com.pheephoo.mjgame.form;

import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.d;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

public final class a implements CommandListener {
  boolean a = true;
  
  private MJGame c;
  
  private Command d;
  
  private Command e;
  
  public List b = new List("Mahjong DEN", 3, new String[] { "New Game", "Settings", "Private Network", "Public Network", "History", "Help", "About", "Exit", "BTDemo" }, null);
  
  public a(MJGame paramMJGame) {
    this.c = paramMJGame;
    this.e = new Command("Select", 1, 1);
    this.d = new Command("Exit", 7, 2);
    this.b.addCommand(this.e);
    this.b.addCommand(this.d);
    this.b.setCommandListener(this);
  }
  
  public final void commandAction(Command paramCommand, Displayable paramDisplayable) {
    int i = this.b.getSelectedIndex();
    if (!this.a)
      return; 
    this.a = false;
    if (i == 0) {
      this.c.a(d.E);
    } else if (i == 1) {
      this.c.a(d.F);
    } else if (i == 2) {
      this.c.a(d.G);
    } else if (i == 3) {
      this.c.a(d.H);
    } else if (i == 4) {
      this.c.a(d.K);
    } else if (i == 5) {
      this.c.a(d.O);
    } else if (i == 6) {
      this.c.a(d.I);
    } else if (i == 7) {
      this.c.a(d.N);
    } else if (i == 8) {
      System.out.println("bt demo");
      this.c.a(d.Q);
    } 
    if (paramCommand == this.d)
      this.c.a(d.N); 
    this.a = true;
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\form\a.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */