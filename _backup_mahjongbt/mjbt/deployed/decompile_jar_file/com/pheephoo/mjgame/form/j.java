package com.pheephoo.mjgame.form;

import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.d;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

public final class j extends Form implements CommandListener {
  private MJGame b;
  
  private Command c;
  
  private Command d;
  
  private StringItem e;
  
  private StringItem f;
  
  private TextField g;
  
  boolean a = true;
  
  public j(MJGame paramMJGame, String paramString, int paramInt) {
    super(paramString);
    this.b = paramMJGame;
    a(paramInt);
  }
  
  public final void a(int paramInt) {
    if (paramInt == 1) {
      this.e = new StringItem(d.w, "");
    } else {
      this.e = new StringItem(d.y, "");
    } 
    this.g = new TextField("", "", 10, 0);
    this.f = new StringItem("\nAllowed Chars(a-z,0-9,'.', '_', '-')", "");
    append((Item)this.e);
    append((Item)this.g);
    append((Item)this.f);
    this.c = new Command("Cancel", 3, 1);
    this.d = new Command("Ok", 4, 1);
    addCommand(this.d);
    addCommand(this.c);
    setCommandListener(this);
  }
  
  public final void commandAction(Command paramCommand, Displayable paramDisplayable) {
    if (!this.a)
      return; 
    this.a = false;
    if (paramCommand == this.c) {
      this.b.a(d.aq);
      return;
    } 
    if (paramCommand == this.d) {
      String str;
      if ((str = this.g.getString()).length() == 0) {
        this.b.a(d.aw);
      } else if (str.indexOf(" ") != -1) {
        this.b.a(d.ax);
      } else {
        boolean bool = true;
        for (byte b = 0; b < str.length(); b++) {
          char c;
          if (((c = str.charAt(b)) < 'A' || c > 'Z') && (c < 'a' || c > 'z') && (c < '0' || c > '9') && c != '_' && c != '.') {
            bool = false;
            this.b.a(d.ax);
            break;
          } 
        } 
        if (bool)
          this.b.a(d.au, str); 
      } 
      this.a = true;
    } 
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\form\j.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */