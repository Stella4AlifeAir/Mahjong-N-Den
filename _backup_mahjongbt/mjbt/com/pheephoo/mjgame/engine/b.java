package com.pheephoo.mjgame.engine;

import com.pheephoo.utilx.a;

public final class b extends e {
  int a = 0;
  
  public b() {
    super(136);
    i();
  }
  
  private void i() {
    byte b1;
    for (b1 = 0; b1 < 3; b1++) {
      for (byte b2 = 1; b2 < 10; b2++) {
        for (byte b3 = 0; b3 < 4; b3++)
          f(new i(b1, b2)); 
      } 
    } 
    for (b1 = 1; b1 < 5; b1++) {
      for (byte b2 = 0; b2 < 4; b2++)
        f(new i(i.a, b1)); 
    } 
    for (b1 = 1; b1 < 4; b1++) {
      for (byte b2 = 0; b2 < 4; b2++)
        f(new i(i.b, b1)); 
    } 
  }
  
  public final i a() {
    int i = a.a(0, e() - 1);
    if (this.a == 1)
      i = 0; 
    i i1 = a(i);
    b(i);
    return i1;
  }
  
  public final void b() {
    f();
    i();
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\engine\b.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */