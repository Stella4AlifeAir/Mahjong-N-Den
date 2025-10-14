package com.pheephoo.mjgame;

import com.pheephoo.mjgame.engine.c;
import com.pheephoo.mjgame.engine.s;
import com.pheephoo.mjgame.engine.t;
import com.pheephoo.mjgame.ui.a;

final class a extends Thread {
  final MJGame a;
  
  a(MJGame paramMJGame) {
    this.a = paramMJGame;
  }
  
  public final void run() {
    MJGame.a(this.a, (s)new t());
    a a1 = new a(1, "Ivy");
    a a2 = new a(2, "Susan");
    a a3 = new a(3, "Anna");
    MJGame.b(this.a).f(this.a.g);
    MJGame.b(this.a).a((c)MJGame.a(this.a), 1);
    MJGame.b(this.a).a((c)a1, 1);
    MJGame.b(this.a).a((c)a2, 1);
    MJGame.b(this.a).a((c)a3, 1);
    ((t)MJGame.b(this.a)).b();
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\a.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */