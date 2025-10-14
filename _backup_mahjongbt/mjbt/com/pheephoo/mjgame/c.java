package com.pheephoo.mjgame;

import com.pheephoo.mjgame.engine.c;
import com.pheephoo.mjgame.engine.s;
import com.pheephoo.mjgame.engine.t;
import com.pheephoo.mjgame.ui.a;
import com.pheephoo.mjgame.ui.l;
import com.pheephoo.utilx.a;
import java.util.Vector;
import javax.microedition.lcdui.Displayable;

final class com/pheephoo/mjgame/c implements Runnable {
  MJGame a;
  
  final MJGame b;
  
  com/pheephoo/mjgame/c(MJGame paramMJGame1, MJGame paramMJGame2) {
    this.b = paramMJGame1;
    this.a = paramMJGame2;
  }
  
  public final void run() {
    MJGame.a(this.b, new l(this.a, 0));
    this.a.a().setCurrent((Displayable)MJGame.a(this.b));
    MJGame.a(this.b, (s)new t());
    Vector vector = new Vector();
    int i;
    for (i = 0; i < d.t; i++)
      vector.addElement(new Integer(i)); 
    i = a.a(0, vector.size() - 1);
    int j = ((Integer)vector.elementAt(i)).intValue();
    vector.removeElementAt(i);
    a a1 = new a(j, d.u[j]);
    i = a.a(0, vector.size() - 1);
    j = ((Integer)vector.elementAt(i)).intValue();
    vector.removeElementAt(i);
    a a2 = new a(j, d.u[j]);
    i = a.a(0, vector.size() - 1);
    j = ((Integer)vector.elementAt(i)).intValue();
    vector.removeElementAt(i);
    a a3 = new a(j, d.u[j]);
    MJGame.b(this.b).f(this.b.g);
    MJGame.b(this.b).a((c)MJGame.a(this.b), 1);
    MJGame.b(this.b).a((c)a1, 1);
    MJGame.b(this.b).a((c)a2, 1);
    MJGame.b(this.b).a((c)a3, 1);
    ((t)MJGame.b(this.b)).b();
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\c.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */