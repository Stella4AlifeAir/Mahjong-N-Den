package com.pheephoo.mjgame.form;

import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.d;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

public final class b implements CommandListener {
  boolean a = true;
  
  private MJGame c;
  
  public List b;
  
  private Command d;
  
  private int e = 0;
  
  public b(MJGame paramMJGame, int paramInt) {
    this.c = paramMJGame;
    this.e = paramInt;
    if (this.e == d.T) {
      this.b = new List("Mahjong DEN (Option)", 3, new String[] { "Resume", "Player Info", "Game Info", "Setting", "Quit Game" }, null);
    } else if (this.e == d.aO) {
      this.b = new List("Network DEN (Option)", 3, new String[] { "Resume", "Player Info", "Game Info", "Send Message", "Setting", "Quit Game" }, null);
    } else if (this.e == d.ac) {
      this.b = new List("Network DEN (Option)", 3, new String[] { "Resume", "Quit Game" }, null);
    } else if (this.e == d.ae) {
      this.b = new List("Network (Option)", 3, new String[] { "Resume", "Invite Friend", "Delete Friend", "Quit Game" }, null);
    } else if (this.e == d.af) {
      this.b = new List("Network (Option)", 3, new String[] { "Resume", "Quit Game" }, null);
    } 
    this.d = new Command("Back", 2, 2);
    this.b.addCommand(this.d);
    this.b.setCommandListener(this);
  }
  
  public final void commandAction(Command paramCommand, Displayable paramDisplayable) {
    if (!this.a)
      return; 
    this.a = false;
    int i = this.b.getSelectedIndex();
    if (this.e == d.T) {
      if (paramCommand == this.d) {
        this.c.a(d.S);
        a();
      } else if (i == 0) {
        this.c.a(d.S);
        a();
      } else if (i == 1) {
        this.c.a(d.X);
        a();
      } else if (i == 2) {
        this.c.a(d.Y);
        a();
      } else if (i == 3) {
        this.c.a(d.U);
      } else if (i == 4) {
        this.c.a(d.R);
        a();
      } 
    } else if (this.e == d.aO) {
      if (paramCommand == this.d) {
        this.c.a(d.aP);
        a();
      } else if (i == 0) {
        this.c.a(d.aP);
        a();
      } else if (i == 1) {
        this.c.a(d.X);
        a();
      } else if (i == 2) {
        this.c.a(d.Y);
        a();
      } else if (i == 3) {
        this.c.a(d.aN);
      } else if (i == 4) {
        this.c.a(d.U);
      } else if (i == 5) {
        this.c.a(d.aQ);
        a();
      } 
    } else if (this.e == d.ac) {
      if (i == 0) {
        this.c.a(d.aa);
        a();
      } else if (i == 1) {
        this.c.a(d.aQ);
        a();
      } 
      if (paramCommand == this.d) {
        this.c.a(d.aa);
        a();
      } 
    } else if (this.e == d.ae) {
      if (paramCommand == this.d) {
        this.c.a(d.ad);
        a();
      } else if (i == 0) {
        System.out.println("privatecontinue received");
        this.c.a(d.ad);
        a();
      } else if (i == 1) {
        this.c.a(d.ag);
      } else if (i == 2) {
        this.c.a(d.ah);
      } else if (i == 3) {
        this.c.a(d.aQ);
        a();
      } 
    } else if (this.e == d.af) {
      if (paramCommand == this.d) {
        this.c.a(d.ad);
        a();
      } else if (i == 0) {
        this.c.a(d.ad);
        a();
      } else if (i == 1) {
        this.c.a(d.aQ);
        a();
      } 
    } 
    this.a = true;
  }
  
  private void a() {
    this.c = null;
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\form\b.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */