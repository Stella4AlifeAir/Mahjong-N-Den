package com.pheephoo.mjgame.ui;

import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.b;
import com.pheephoo.mjgame.d;
import com.pheephoo.mjgame.engine.d;
import com.pheephoo.mjgame.engine.u;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class c extends Canvas {
  MJGame a;
  
  l b;
  
  Graphics c;
  
  Image d;
  
  long e = 0L;
  
  int f = 0;
  
  int g = 0;
  
  int h = 1;
  
  int i = 1;
  
  int j = 0;
  
  public c(MJGame paramMJGame, l paraml, int paramInt1, int paramInt2) {
    setFullScreenMode(true);
    this.a = paramMJGame;
    this.b = paraml;
    this.f = paramInt1;
    this.d = Image.createImage(getWidth(), getHeight());
    this.c = this.d.getGraphics();
    this.g = paramInt2;
    if (paramInt2 == 0) {
      b();
      return;
    } 
    if (paramInt2 == 1) {
      a();
      return;
    } 
    if (paramInt2 == 2) {
      this.j = 0;
      a(0);
      return;
    } 
    if (paramInt2 == 3) {
      this.j = 1;
      a(1);
    } 
  }
  
  private void a(int paramInt) {
    try {
      this.c.drawImage(n.d, 0, 0, 20);
      this.c.drawImage(Image.createImage("/res/history.jpg"), 0, 0, 20);
    } catch (IOException iOException) {}
    Vector vector = u.a(paramInt);
    this.h = vector.size();
    this.c.setFont(Font.getFont(32, 0, 0));
    this.c.setColor(225, 225, 255);
    if (this.h > 0) {
      a(vector.elementAt(this.i - 1));
      if (paramInt == 1) {
        d[] arrayOfD = vector.elementAt(this.i - 1);
        this.c.drawString((arrayOfD[0]).d, 5, b.ax, 20);
      } 
      this.c.drawString(String.valueOf(this.i) + "/" + this.h, b.ay, b.ax, 20);
    } else {
      this.c.drawString("Empty", b.ay, b.ax, 20);
    } 
    repaint();
    serviceRepaints();
  }
  
  private void a(d[] paramArrayOfd) {
    int m;
    this.c.setFont(Font.getFont(32, 1, 0));
    this.c.setColor(225, 225, 255);
    int[] arrayOfInt;
    (arrayOfInt = new int[4])[0] = 9;
    arrayOfInt[1] = 9;
    arrayOfInt[2] = 9;
    arrayOfInt[3] = 9;
    int i = 0;
    int j = 0;
    boolean bool = false;
    int k;
    for (k = 0; k < 4; k++) {
      i = 0;
      for (m = 0; m < 4; m++) {
        bool = false;
        for (byte b1 = 0; b1 < 4; b1++) {
          if (m == arrayOfInt[b1]) {
            bool = true;
            break;
          } 
        } 
        if (!bool) {
          j = (paramArrayOfd[m]).e;
          if (i < j) {
            arrayOfInt[k] = m;
            i = j;
          } 
        } 
      } 
    } 
    if (this.g == 0) {
      k = b.ag;
      m = b.an;
    } else {
      k = b.ah;
      m = b.ao;
    } 
    for (byte b = 0; b < 4; b++) {
      this.c.drawString((paramArrayOfd[arrayOfInt[b]]).c, b.aj, m + b.ap * b, 20);
      a((paramArrayOfd[arrayOfInt[b]]).b, b.ai, k + b.ap * b);
      this.c.drawString((new StringBuffer(String.valueOf((paramArrayOfd[arrayOfInt[b]]).e))).toString(), b.al, m + b.ap * b, 20);
    } 
  }
  
  private void a() {
    try {
      this.c.drawImage(n.d, 0, 0, 20);
      this.c.drawImage(Image.createImage("/res/end_round.jpg"), 0, 0, 20);
    } catch (IOException iOException) {}
    a(this.b.G);
    if (this.f != 2)
      u.a(this.b.G, this.f, this.a.e); 
  }
  
  private void b() {
    try {
      this.c.drawImage(n.d, 0, 0, 20);
      this.c.drawImage(Image.createImage("/res/player_info.jpg"), 0, 0, 20);
    } catch (IOException iOException) {}
    this.c.setFont(Font.getFont(32, 1, 0));
    this.c.setColor(225, 225, 255);
    this.c.drawString("E ", b.ak, b.am, 20);
    this.c.drawString("S ", b.ak, b.am + b.ap, 20);
    this.c.drawString("W ", b.ak, b.am + b.ap * 2, 20);
    this.c.drawString("N ", b.ak, b.am + b.ap * 3, 20);
    this.c.drawString(this.b.c(0), b.aj, b.am + b.ap * 0, 20);
    this.c.drawString(this.b.c(1), b.aj, b.am + b.ap, 20);
    this.c.drawString(this.b.c(2), b.aj, b.am + b.ap * 2, 20);
    this.c.drawString(this.b.c(3), b.aj, b.am + b.ap * 3, 20);
    a(this.b.b(0), b.ai, b.af);
    a(this.b.b(1), b.ai, b.af + b.ap);
    a(this.b.b(2), b.ai, b.af + b.ap * 2);
    a(this.b.b(3), b.ai, b.af + b.ap * 3);
  }
  
  private void a(int paramInt1, int paramInt2, int paramInt3) {
    this.c.drawImage(n.b(paramInt1), paramInt2, paramInt3, 20);
  }
  
  protected final void paint(Graphics paramGraphics) {
    paramGraphics.drawImage(this.d, 0, 0, 20);
  }
  
  protected final void keyPressed(int paramInt) {
    if (System.currentTimeMillis() - 350L < this.e)
      return; 
    this.e = System.currentTimeMillis();
    if (this.g == 0) {
      if (this.f == 0) {
        this.a.a(d.T);
        return;
      } 
      this.a.a(d.aO);
      return;
    } 
    if (this.g == 1) {
      if (paramInt == -5) {
        this.a.a(d.R);
        return;
      } 
    } else {
      if (this.h > 1 && (paramInt == -1 || paramInt == -2 || paramInt == -3 || paramInt == -4)) {
        if (paramInt == -1 || paramInt == -3) {
          if (this.i > 1)
            this.i--; 
        } else if ((paramInt == -2 || paramInt == -4) && this.i < this.h) {
          this.i++;
        } 
        a(this.j);
        return;
      } 
      this.a.a(d.J);
    } 
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgam\\ui\c.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */