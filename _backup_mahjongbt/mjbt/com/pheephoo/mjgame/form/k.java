package com.pheephoo.mjgame.form;

import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.d;
import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class k extends Canvas implements Runnable {
  Image a;
  
  Image b;
  
  MJGame c;
  
  Graphics d;
  
  Image e;
  
  boolean f = false;
  
  protected final void paint(Graphics paramGraphics) {
    paramGraphics.drawImage(this.e, 0, 0, 20);
  }
  
  public k(MJGame paramMJGame) {
    System.out.println("FailureForm constructor");
    setFullScreenMode(true);
    this.c = paramMJGame;
    this.e = Image.createImage(getWidth(), getHeight());
    this.d = this.e.getGraphics();
    try {
      this.a = Image.createImage("/res/networkfail.jpg");
      this.d.drawImage(this.a, 0, 0, 20);
      if (this.c.c) {
        this.b = Image.createImage("/res/networkfail3.png");
      } else if (this.c.a == 0) {
        this.b = Image.createImage("/res/networkfail1.png");
      } else if (this.c.b) {
        this.c.a = 0;
        this.f = true;
        this.b = Image.createImage("/res/networkfail1.png");
      } else {
        this.b = Image.createImage("/res/networkfail2.png");
      } 
    } catch (IOException iOException) {
      throw new Error();
    } 
    this.d.drawImage(this.a, 0, 0, 20);
    this.d.drawImage(this.b, (getWidth() - this.b.getWidth()) / 2, 0, 20);
    repaint();
    serviceRepaints();
    Thread thread;
    (thread = new Thread(this)).start();
  }
  
  public final void run() {
    try {
      Thread.sleep(5500L);
      this.c.c = false;
      if (!this.f) {
        this.c.a(d.aQ);
        if (this.c.a == 2) {
          this.c.b();
          return;
        } 
      } 
    } catch (InterruptedException interruptedException) {}
  }
  
  protected final void keyPressed(int paramInt) {
    if (this.f) {
      this.c.b();
      this.c.a(d.aQ);
    } 
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\form\k.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */