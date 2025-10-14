package com.pheephoo.mjgame.ui;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

final class com/pheephoo/mjgame/ui/b extends Canvas implements Runnable {
  Graphics a;
  
  Image b;
  
  StringBuffer c = null;
  
  int d;
  
  int e;
  
  boolean f = false;
  
  Thread g;
  
  public com/pheephoo/mjgame/ui/b(n paramn) {
    setFullScreenMode(true);
    this.b = Image.createImage(getWidth(), getHeight());
    this.d = getWidth();
    this.e = getHeight();
    this.a = this.b.getGraphics();
    this.g = new Thread(this);
    this.g.start();
  }
  
  protected final void paint(Graphics paramGraphics) {
    paramGraphics.drawImage(this.b, 0, 0, 20);
  }
  
  public final synchronized void run() {
    byte b = 0;
    while (true) {
      if (!this.f)
        try {
          wait();
          this.c = new StringBuffer();
          this.c.append("Connecting  ");
          b = 0;
        } catch (InterruptedException interruptedException2) {
          InterruptedException interruptedException1;
          (interruptedException1 = null).printStackTrace();
        }  
      this.c.append(". ");
      if (b == 8) {
        b = 0;
        this.c.delete(0, this.c.length());
        this.c.append("Connecting  ");
      } 
      b++;
      this.a.setColor(86, 55, 44);
      this.a.fillRect(0, com.pheephoo.mjgame.b.ab, this.d, this.e);
      this.a.setColor(255, 255, 255);
      this.a.drawString(this.c.toString(), com.pheephoo.mjgame.b.ac, com.pheephoo.mjgame.b.ab + 4, 20);
      repaint();
      serviceRepaints();
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException interruptedException) {}
    } 
  }
  
  public final void a() {
    this.a.drawImage(n.c, 0, com.pheephoo.mjgame.b.A, 20);
    this.a.drawImage(n.a(0), (this.d - com.pheephoo.mjgame.b.ad) / 2, this.e - com.pheephoo.mjgame.b.ae, 20);
  }
  
  public final synchronized void b() {
    if (this.f)
      return; 
    this.f = true;
    this.a.setColor(86, 55, 44);
    this.a.fillRect(0, com.pheephoo.mjgame.b.ab, this.d, this.e);
    this.a.setColor(255, 255, 255);
    this.a.setFont(Font.getFont(32, 1, 8));
    this.a.drawImage(n.d, 0, 0, 20);
    this.a.drawImage(n.c, 0, com.pheephoo.mjgame.b.A, 20);
    repaint();
    serviceRepaints();
    notify();
  }
  
  public final void c() {
    this.f = false;
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgam\\ui\b.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */