package com.pheephoo.mjgame.engine;

import com.pheephoo.mjgame.d;
import java.io.IOException;

final class com/pheephoo/mjgame/engine/g extends Thread {
  final h a;
  
  com/pheephoo/mjgame/engine/g(h paramh) {
    this.a = paramh;
  }
  
  public final synchronized void run() {
    while (!this.a.d) {
      try {
        wait();
        this.a.f = true;
        if (this.a.d)
          return; 
        this.a.e = 0;
        this.a.b.a(this.a.c.a, this.a.c.b);
      } catch (InterruptedException interruptedException) {
      
      } catch (IOException iOException) {
        this.a.b();
        iOException.printStackTrace();
      } catch (SecurityException securityException2) {
        SecurityException securityException1;
        (securityException1 = null).printStackTrace();
        h.a(this.a).c(d.d);
      } 
    } 
  }
  
  public final synchronized void a() {
    notify();
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\engine\g.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */