package com.pheephoo.mjgame.ui;

import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.b;
import com.pheephoo.mjgame.d;
import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class k extends Canvas {
  MJGame a;
  
  l b;
  
  Graphics c;
  
  Image d;
  
  long e = 0L;
  
  int f = 0;
  
  int g = 1;
  
  int h = 1;
  
  String[] i = new String[4];
  
  Image j;
  
  public k(MJGame paramMJGame, l paraml, int paramInt) {
    setFullScreenMode(true);
    this.a = paramMJGame;
    this.b = paraml;
    this.f = paramInt;
    this.d = Image.createImage(getWidth(), getHeight());
    this.c = this.d.getGraphics();
    try {
      this.j = Image.createImage("/res/game_info.jpg");
    } catch (IOException iOException) {}
    a();
  }
  
  private void a() {
    this.c.drawImage(n.d, 0, 0, 20);
    this.c.drawImage(this.j, 0, 0, 20);
    this.c.setFont(Font.getFont(32, 1, 0));
    this.c.setColor(225, 225, 255);
    String str = null;
    int i;
    for (i = 0; i < 4; i++) {
      if ((str = (this.b.G[i]).c).length() < 3) {
        this.i[i] = str;
      } else {
        this.i[i] = str.substring(0, 3);
      } 
    } 
    this.c.drawString(this.i[0], b.ar, b.aq, 20);
    this.c.drawString(this.i[1], b.ar + b.at, b.aq, 20);
    this.c.drawString(this.i[2], b.ar + b.at * 2, b.aq, 20);
    this.c.drawString(this.i[3], b.ar + b.at * 3, b.aq, 20);
    this.c.setFont(Font.getFont(32, 0, 0));
    this.g = this.b.i / b.aw + 1;
    if (this.g == 1 || this.h == 1) {
      this.c.drawString("2000", b.av, b.au, 20);
      this.c.drawString("2000", b.av + b.at, b.au, 20);
      this.c.drawString("2000", b.av + b.at * 2, b.au, 20);
      this.c.drawString("2000", b.av + b.at * 3, b.au, 20);
      for (i = 0; i < b.aw - 1 && i < this.b.i; i++) {
        this.c.drawString((new StringBuffer(String.valueOf((this.b.G[0]).f[i]))).toString(), b.av, b.au + b.as * (i + 1), 20);
        this.c.drawString((new StringBuffer(String.valueOf((this.b.G[1]).f[i]))).toString(), b.av + b.at, b.au + b.as * (i + 1), 20);
        this.c.drawString((new StringBuffer(String.valueOf((this.b.G[2]).f[i]))).toString(), b.av + b.at * 2, b.au + b.as * (i + 1), 20);
        this.c.drawString((new StringBuffer(String.valueOf((this.b.G[3]).f[i]))).toString(), b.av + b.at * 3, b.au + b.as * (i + 1), 20);
      } 
    } else {
      for (i = (this.h - 1) * b.aw - 1; i < this.h * b.aw - 1 && i < this.b.i; i++) {
        this.c.drawString((new StringBuffer(String.valueOf((this.b.G[0]).f[i]))).toString(), b.av, b.au + b.as * (i - b.aw * (this.h - 1) + 1), 20);
        this.c.drawString((new StringBuffer(String.valueOf((this.b.G[1]).f[i]))).toString(), b.av + b.at, b.au + b.as * (i - b.aw * (this.h - 1) + 1), 20);
        this.c.drawString((new StringBuffer(String.valueOf((this.b.G[2]).f[i]))).toString(), b.av + b.at * 2, b.au + b.as * (i - b.aw * (this.h - 1) + 1), 20);
        this.c.drawString((new StringBuffer(String.valueOf((this.b.G[3]).f[i]))).toString(), b.av + b.at * 3, b.au + b.as * (i - b.aw * (this.h - 1) + 1), 20);
      } 
    } 
    this.c.drawString(String.valueOf(this.h) + "/" + this.g, b.ay, b.ax, 20);
    repaint();
    serviceRepaints();
  }
  
  protected final void paint(Graphics paramGraphics) {
    paramGraphics.drawImage(this.d, 0, 0, 20);
  }
  
  protected final void keyPressed(int paramInt) {
    if (System.currentTimeMillis() - 350L < this.e)
      return; 
    this.e = System.currentTimeMillis();
    if (this.g > 1 && (paramInt == -1 || paramInt == -2 || paramInt == -3 || paramInt == -4)) {
      if (paramInt == -1 || paramInt == -3) {
        if (this.h > 1)
          this.h--; 
      } else if ((paramInt == -2 || paramInt == -4) && this.h < this.g) {
        this.h++;
      } 
      a();
      return;
    } 
    if (this.f == 0) {
      this.a.a(d.T);
      return;
    } 
    this.a.a(d.aO);
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgam\\ui\k.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */