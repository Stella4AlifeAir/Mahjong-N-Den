package com.pheephoo.mjgame.network;

import java.io.IOException;
import java.util.TimerTask;

final class com/pheephoo/mjgame/network/a extends TimerTask {
  final d a;
  
  com/pheephoo/mjgame/network/a(d paramd) {
    this.a = paramd;
  }
  
  public final void run() {
    try {
      a();
      return;
    } catch (IOException iOException) {
      this.a.h.m();
      return;
    } 
  }
  
  public final void a() throws IOException {
    while (!this.a.i) {
      try {
        Object[] arrayOfObject = d.a(this.a, this.a.d);
        if (this.a.i)
          return; 
        this.a.h.a(arrayOfObject);
      } catch (IOException iOException2) {
        IOException iOException1;
        (iOException1 = null).printStackTrace();
        d.a(this.a);
      } 
    } 
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\network\a.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */