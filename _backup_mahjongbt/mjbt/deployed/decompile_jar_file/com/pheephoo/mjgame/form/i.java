package com.pheephoo.mjgame.form;

import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.d;
import com.pheephoo.mjgame.messaging.a;
import com.pheephoo.mjgame.messaging.b;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;

public final class i extends Form implements CommandListener {
  private static String e = "Invite Friend";
  
  private MJGame f;
  
  private Command g;
  
  private Command h;
  
  private StringItem i;
  
  private TextField j;
  
  private TextField k;
  
  private TextField l;
  
  public String a = "";
  
  public String b = "";
  
  public String c = "";
  
  boolean d = true;
  
  public i(MJGame paramMJGame) {
    super(e);
    this.f = paramMJGame;
    a();
  }
  
  public final void a() {
    this.i = new StringItem("Register your Mahjong partners' mobile #", "");
    this.j = new TextField("1", "", 10, 2);
    this.k = new TextField("2", "", 10, 2);
    this.l = new TextField("3", "", 10, 2);
    append((Item)this.i);
    append((Item)this.j);
    append((Item)this.k);
    append((Item)this.l);
    this.g = new Command("Cancel", 3, 1);
    this.h = new Command("Send", 4, 1);
    addCommand(this.h);
    addCommand(this.g);
    setCommandListener(this);
  }
  
  public final void commandAction(Command paramCommand, Displayable paramDisplayable) {
    if (!this.d && paramCommand != this.g)
      return; 
    this.d = false;
    if (paramCommand == this.g) {
      this.f.a(d.V);
      this.d = true;
      return;
    } 
    if (paramCommand == this.h) {
      if (this.j.getString().length() == 0 && this.k.getString().length() == 0 && this.l.getString().length() == 0) {
        this.f.a(d.al);
        this.d = true;
        return;
      } 
      try {
        if (this.j.getString().length() != 0) {
          this.a = this.j.getString();
          b.a(this.j.getString(), d.m);
        } 
        if (this.k.getString().length() != 0) {
          this.b = this.k.getString();
          b.a(this.k.getString(), d.m);
        } 
        if (this.l.getString().length() != 0) {
          this.c = this.l.getString();
          b.a(this.l.getString(), d.m);
        } 
        this.f.a(d.ao);
        this.d = true;
        return;
      } catch (a a) {
        this.f.a(d.an);
        this.d = true;
      } 
    } 
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\form\i.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */