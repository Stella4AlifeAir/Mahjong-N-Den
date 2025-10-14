package com.pheephoo.mjgame.ui;

import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.d;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

public final class j implements CommandListener {
  private MJGame b;
  
  private Command c;
  
  private Command d;
  
  public List a = new List("Mahjong", 3, new String[] { 
        "SPEED1", "SPEED2", "PONG1", "CONCEALKONG1", "CONCEALKONG2", "CONCEALKONG3", "CONCEALKONG4", "ROBKONG1", "ROBKONG2", "ClearHand1", 
        "ClearHand2", "ClearHand3", "RESET", "Exit" }, null);
  
  public j(MJGame paramMJGame) {
    this.b = paramMJGame;
    this.d = new Command("Select", 1, 1);
    this.c = new Command("Exit", 7, 2);
    this.a.addCommand(this.d);
    this.a.addCommand(this.c);
    this.a.setCommandListener(this);
  }
  
  public final void commandAction(Command paramCommand, Displayable paramDisplayable) {
    int i;
    if ((i = this.a.getSelectedIndex()) == 0) {
      this.b.a(d.aA);
    } else if (i == 1) {
      this.b.a(d.aB);
    } else if (i == 2) {
      this.b.a(d.aC);
    } else if (i == 3) {
      this.b.a(d.aD);
    } else if (i == 4) {
      this.b.a(d.aE);
    } else if (i == 5) {
      this.b.a(d.aF);
    } else if (i == 6) {
      this.b.a(d.aG);
    } else if (i == 7) {
      this.b.a(d.aL);
    } else if (i == 8) {
      this.b.a(d.aM);
    } else if (i == 9) {
      this.b.a(d.aH);
    } else if (i == 10) {
      this.b.a(d.aI);
    } else if (i == 11) {
      this.b.a(d.aJ);
    } else if (i == 12) {
      this.b.e();
    } else if (i == 13) {
      this.b.a(d.J);
    } 
    if (paramCommand == this.c)
      this.b.a(d.J); 
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgam\\ui\j.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */