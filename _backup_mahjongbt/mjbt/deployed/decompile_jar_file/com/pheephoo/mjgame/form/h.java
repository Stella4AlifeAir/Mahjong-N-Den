package com.pheephoo.mjgame.form;

import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.d;
import com.pheephoo.mjgame.network.c;
import com.pheephoo.mjgame.ui.g;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.List;

public final class h extends Form implements Runnable, CommandListener {
  private static String b = "Delete Friend";
  
  private MJGame c;
  
  private boolean d = true;
  
  private Command e;
  
  private Command f;
  
  public List a;
  
  private g g;
  
  public h(MJGame paramMJGame, g paramg) {
    super(b);
    this.c = paramMJGame;
    this.g = paramg;
    a();
  }
  
  public final void a() {
    System.out.println("DeleteForm.initializeForm()");
    int i;
    String[] arrayOfString = new String[i = this.g.x.size()];
    System.out.println("contact size=" + i);
    for (byte b = 0; b < i; b++) {
      c c = this.g.x.elementAt(b);
      arrayOfString[b] = String.valueOf(c.d) + " (" + c.c + ")";
    } 
    this.a = new List("Delete Contact", 2, arrayOfString, null);
    this.e = new Command("Cancel", 3, 1);
    this.f = new Command("Delete", 4, 1);
    this.a.addCommand(this.f);
    this.a.addCommand(this.e);
    this.a.setCommandListener(this);
  }
  
  public final void run() {}
  
  public final void commandAction(Command paramCommand, Displayable paramDisplayable) {
    if (paramCommand == this.e) {
      this.c.a(d.V);
      return;
    } 
    if (paramCommand == this.f) {
      boolean[] arrayOfBoolean = new boolean[this.a.size()];
      byte b1 = 0;
      for (byte b2 = 0; b2 < arrayOfBoolean.length; b2++) {
        arrayOfBoolean[b2] = this.a.isSelected(b2);
        if (arrayOfBoolean[b2])
          b1++; 
      } 
      this.g.a(arrayOfBoolean, b1);
      this.c.a(d.ap);
    } 
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\form\h.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */