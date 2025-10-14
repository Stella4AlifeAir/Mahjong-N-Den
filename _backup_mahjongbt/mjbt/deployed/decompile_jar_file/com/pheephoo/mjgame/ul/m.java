package com.pheephoo.mjgame.ui;

import com.pheephoo.mjgame.MJGame;
import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class m extends Canvas implements Runnable {
  private Image b;
  
  private int c;
  
  public boolean a = false;
  
  protected final void paint(Graphics paramGraphics) {
    paramGraphics.drawImage(this.b, 0, 0, 20);
  }
  
  public m(MJGame paramMJGame, int paramInt) {
    setFullScreenMode(true);
    this.c = paramInt;
    try {
      this.b = Image.createImage("/res/mjlogo.jpg");
    } catch (IOException iOException) {
      throw new Error();
    } 
    Thread thread;
    (thread = new Thread(this)).start();
  }
  
  public final void run() {
    try {
      Thread.sleep(this.c);
      this.a = true;
      return;
    } catch (InterruptedException interruptedException) {
      return;
    } 
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgam\\ui\m.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */