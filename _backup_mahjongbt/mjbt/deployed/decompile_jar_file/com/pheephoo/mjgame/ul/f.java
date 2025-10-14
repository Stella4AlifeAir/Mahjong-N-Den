package com.pheephoo.mjgame.ui;

import com.pheephoo.mjgame.b;

final class com/pheephoo/mjgame/ui/f implements Runnable {
  final l a;
  
  com/pheephoo/mjgame/ui/f(l paraml) {
    this.a = paraml;
  }
  
  public final void run() {
    int i = 0;
    byte b = 0;
    boolean bool = false;
    StringBuffer stringBuffer;
    (stringBuffer = new StringBuffer()).append("Connecting  ");
    while (this.a.d) {
      try {
        this.a.E.drawImage(n.a(i), (l.a(this.a) - b.ad) / 2, l.b(this.a) - b.ae, 20);
        this.a.repaint();
        this.a.serviceRepaints();
        Thread.sleep(500L);
      } catch (InterruptedException interruptedException) {}
      i = (i + 1) % 4;
      if (b == 3)
        this.a.d = false; 
      b++;
    } 
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgam\\ui\f.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */