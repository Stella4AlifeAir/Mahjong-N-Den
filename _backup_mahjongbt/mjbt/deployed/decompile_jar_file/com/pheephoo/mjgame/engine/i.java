package com.pheephoo.mjgame.engine;

public final class i {
  public static int a = 3;
  
  public static int b = 4;
  
  public static int c = 1;
  
  public static int d = 2;
  
  public static int e = 3;
  
  public static int f = 4;
  
  public int g;
  
  public int h;
  
  public int i;
  
  public int j;
  
  public i(int paramInt1, int paramInt2) {
    this.g = paramInt1;
    this.h = paramInt2;
  }
  
  public i(int paramInt1, int paramInt2, int paramInt3) {
    this.g = paramInt2;
    this.h = paramInt3;
    this.i = paramInt1;
  }
  
  public i(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    this.g = paramInt1;
    this.h = paramInt2;
    this.i = paramInt3;
    this.j = paramInt4;
  }
  
  public final String toString() {
    return (this.j == 0) ? (String.valueOf(this.g) + this.h) : (String.valueOf(this.g) + this.h + "(" + this.j + ")");
  }
  
  public final boolean a(i parami) {
    return (this.g == parami.g && this.h == parami.h);
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\engine\i.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */