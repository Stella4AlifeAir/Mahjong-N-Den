package com.pheephoo.mjgame.ui;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Item;
import javax.microedition.lcdui.StringItem;

public final class e extends Form implements CommandListener {
  boolean a = true;
  
  private g b;
  
  private h c;
  
  private Command d;
  
  private Command e;
  
  private int f = 0;
  
  private int g = 0;
  
  public e(h paramh, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2) {
    super(paramString1);
    this.c = paramh;
    append((Item)new StringItem(paramString2, ""));
    this.f = paramInt1;
    this.g = paramInt2;
    this.d = new Command(paramString3, 1, 1);
    addCommand(this.d);
    if (paramString4 != null) {
      this.e = new Command(paramString4, 1, 2);
      addCommand(this.e);
    } 
    setCommandListener(this);
  }
  
  public e(h paramh, String paramString1, String[] paramArrayOfString, String paramString2, String paramString3, int paramInt1, int paramInt2) {
    super(paramString1);
    this.c = paramh;
    for (byte b = 0; b < paramArrayOfString.length; b++)
      append((Item)new StringItem(paramArrayOfString[b], "")); 
    this.f = paramInt1;
    this.g = paramInt2;
    this.d = new Command(paramString2, 1, 1);
    addCommand(this.d);
    if (paramString3 != null) {
      this.e = new Command(paramString3, 1, 2);
      addCommand(this.e);
    } 
    setCommandListener(this);
  }
  
  public e(g paramg, String paramString1, String[] paramArrayOfString, String paramString2, String paramString3, int paramInt1, int paramInt2) {
    super(paramString1);
    this.b = paramg;
    for (byte b = 0; b < paramArrayOfString.length; b++)
      append((Item)new StringItem(paramArrayOfString[b], "")); 
    this.f = paramInt1;
    this.g = paramInt2;
    this.d = new Command(paramString2, 1, 1);
    addCommand(this.d);
    if (paramString3 != null) {
      this.e = new Command(paramString3, 1, 2);
      addCommand(this.e);
    } 
    setCommandListener(this);
  }
  
  public e(g paramg, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, int paramInt2) {
    super(paramString1);
    this.b = paramg;
    append((Item)new StringItem(paramString2, ""));
    this.f = paramInt1;
    this.g = paramInt2;
    this.d = new Command(paramString3, 1, 1);
    this.e = new Command(paramString4, 1, 2);
    addCommand(this.d);
    addCommand(this.e);
    setCommandListener(this);
  }
  
  public final void commandAction(Command paramCommand, Displayable paramDisplayable) {
    if (!this.a)
      return; 
    this.a = false;
    if (paramCommand == this.d) {
      if (this.b != null) {
        this.b.b(this.f);
        return;
      } 
      this.c.e(this.f);
      return;
    } 
    if (this.b != null) {
      this.b.b(this.g);
      return;
    } 
    this.c.e(this.g);
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgam\\ui\e.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */