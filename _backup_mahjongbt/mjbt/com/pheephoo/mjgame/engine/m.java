package com.pheephoo.mjgame.engine;

import com.pheephoo.mjgame.d;
import java.io.IOException;
import java.util.TimerTask;

final class com/pheephoo/mjgame/engine/m extends TimerTask {
  final h a;
  
  com/pheephoo/mjgame/engine/m(h paramh) {
    this.a = paramh;
  }
  
  public final void run() {
    a();
  }
  
  public final void a() {
    if (this.a.f)
      return; 
    this.a.e++;
    if (this.a.e >= d.B) {
      this.a.e = 0;
      try {
        this.a.b.a(d.bs, null);
        return;
      } catch (IOException iOException) {
        this.a.b();
        iOException.printStackTrace();
      } 
    } 
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\engine\m.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */