package com.pheephoo.mjgame.form;

import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.b;
import com.pheephoo.mjgame.engine.k;
import com.pheephoo.mjgame.ui.n;
import com.pheephoo.utilx.a;
import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class d extends Canvas {
  private MJGame f;
  
  int a = 0;
  
  int b = 0;
  
  private Image g;
  
  private int h = 0;
  
  private int i = 2;
  
  private int j = 0;
  
  private int k = 0;
  
  private int l = 5;
  
  private int m = 0;
  
  private static int n = 0;
  
  private static int o = 1;
  
  private static int p = 2;
  
  private static int q = 3;
  
  Image c;
  
  Image[] d = new Image[11];
  
  Graphics e;
  
  public d(MJGame paramMJGame) {
    this.f = paramMJGame;
    setFullScreenMode(true);
    a();
  }
  
  private void a() {
    this.g = Image.createImage(getWidth(), getHeight());
    this.e = this.g.getGraphics();
    this.a = getWidth();
    this.b = getHeight();
    try {
      for (byte b = 0; b < 11; b++)
        this.d[b] = Image.createImage("/res/setting_" + b + ".png"); 
      this.c = Image.createImage("/res/setting.jpg");
    } catch (IOException iOException) {
    
    } finally {
      d();
    } 
  }
  
  private void b() {
    String str = "";
    if (this.i == n) {
      str = "/res/setting_soff.png";
    } else if (this.i == o) {
      str = "/res/setting_slow.png";
    } else if (this.i == p) {
      str = "/res/setting_smedium.png";
    } else if (this.i == q) {
      str = "/res/setting_shigh.png";
    } 
    try {
      this.e.drawImage(a.a(this.c, 0, 0, this.a, this.b), 0, 0, 20);
      System.out.println("imagesetting=" + this.j);
      if (this.h == 0) {
        this.e.drawImage(a.a(this.c, this.a, b.M, this.a, b.P), 0, b.M, 20);
        this.e.drawImage(n.b(this.j), b.O, b.N, 20);
        return;
      } 
      if (this.h == 1) {
        this.e.drawImage(a.a(this.c, this.a, b.Q, this.a, b.S), 0, b.Q, 20);
        this.e.drawImage(Image.createImage(str), b.T, b.R, 20);
        return;
      } 
      if (this.h == 2) {
        this.e.drawImage(a.a(this.c, this.a, b.U, this.a, b.X), 0, b.U, 20);
        this.e.drawImage(this.d[this.k], b.W, b.V, 20);
        return;
      } 
      if (this.h == 3) {
        this.e.drawImage(a.a(this.c, this.a, b.Y, this.a, b.aa), 0, b.Y, 20);
        this.e.drawImage(this.d[this.l], b.W, b.Z, 20);
        return;
      } 
    } catch (IOException iOException) {}
  }
  
  protected final void paint(Graphics paramGraphics) {
    b();
    paramGraphics.drawImage(this.g, 0, 0, 20);
  }
  
  protected final void keyPressed(int paramInt) {
    if (paramInt == -6) {
      this.f.a(com.pheephoo.mjgame.d.W);
      this.h = 0;
      c();
      return;
    } 
    if (paramInt == -7) {
      this.f.a(com.pheephoo.mjgame.d.V);
      this.h = 0;
      d();
      return;
    } 
    switch (getGameAction(paramInt)) {
      case 1:
        this.h--;
        if (this.h < 0)
          this.h = 0; 
        break;
      case 6:
        this.h++;
        if (this.h > 3)
          this.h = 3; 
        break;
      case 2:
        if (this.h == 1) {
          if (this.i != n)
            this.i--; 
          break;
        } 
        if (this.h == 0 && this.m == 0) {
          if (this.j != 0)
            this.j--; 
          break;
        } 
        if (this.h == 2 && this.m == 0) {
          if (this.k != 0)
            this.k--; 
          break;
        } 
        if (this.h == 3 && this.m == 0 && this.l > this.k)
          this.l--; 
        break;
      case 5:
        if (this.h == 1) {
          if (this.i != q)
            this.i++; 
          break;
        } 
        if (this.h == 0) {
          if (this.j < com.pheephoo.mjgame.d.t - 1 && this.m == 0)
            this.j++; 
          break;
        } 
        if (this.h == 2 && this.m == 0) {
          if (this.k < this.l)
            this.k++; 
          break;
        } 
        if (this.h == 3 && this.m == 0 && this.l != 10)
          this.l++; 
        break;
    } 
    repaint();
    serviceRepaints();
  }
  
  private boolean c() {
    k.a = this.i;
    k.b = this.j;
    k.c = this.k;
    k.d = this.l;
    return k.b();
  }
  
  private void d() {
    k.a();
    this.i = k.a;
    this.j = k.b;
    this.k = k.c;
    this.l = k.d;
  }
  
  public final void a(int paramInt) {
    this.m = paramInt;
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\form\d.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */