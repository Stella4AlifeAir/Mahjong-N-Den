package com.pheephoo.mjgame.engine;

import com.pheephoo.mjgame.d;
import com.pheephoo.mjgame.messaging.a;
import com.pheephoo.mjgame.messaging.b;

final class com/pheephoo/mjgame/engine/n extends Thread {
  private int b;
  
  final h a;
  
  public com/pheephoo/mjgame/engine/n(h paramh, int paramInt) {
    this.a = paramh;
    this.b = 0;
    this.b = paramInt;
  }
  
  public final synchronized void run() {
    if (this.b == 0)
      try {
        b.a(d.i, String.valueOf(d.j) + " " + h.b(this.a) + " " + h.c(this.a));
        return;
      } catch (a a) {
        h.a(this.a).a(d.as);
        return;
      } catch (SecurityException securityException) {
        h.a(this.a).c(d.c);
        return;
      }  
    if (this.b == 1)
      try {
        b.a(d.i, String.valueOf(d.k) + " " + h.b(this.a) + " " + h.c(this.a) + " " + this.a.j);
        return;
      } catch (a a) {
        h.a(this.a).a(d.ay);
        return;
      } catch (SecurityException securityException) {
        h.a(this.a).c(d.c);
        return;
      }  
    if (this.b == 2)
      try {
        b.a(d.i, String.valueOf(d.l) + " " + h.b(this.a) + " " + h.c(this.a) + " " + this.a.j);
        return;
      } catch (a a) {
        h.a(this.a).a(d.ay);
        return;
      } catch (SecurityException securityException) {
        h.a(this.a).c(d.c);
      }  
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\engine\n.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */