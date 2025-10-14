package com.pheephoo.mjgame.engine;

import java.util.Vector;

public final class q {
  int a = 3;
  
  private b m;
  
  public int b;
  
  public int c;
  
  public int d;
  
  public int e = 2000;
  
  public int f;
  
  public String g;
  
  public e h = new e(e.c);
  
  public e i = new e(e.b);
  
  public i j;
  
  public i k;
  
  public Vector l = new Vector();
  
  public q(b paramb, int paramInt) {
    this.m = paramb;
    this.b = paramInt;
  }
  
  public q(int paramInt) {
    this.b = paramInt;
  }
  
  public final void a() {
    this.c = 0;
    this.d = 0;
    this.h.f();
    this.i.f();
  }
  
  public final void b() {
    for (byte b1 = 0; b1 < 13; b1++)
      this.h.e(this.m.a()); 
  }
  
  public final void a(i parami) {
    this.h.e(parami);
  }
  
  public final void c() {
    if (this.b == 0)
      this.b += 4; 
    this.b--;
  }
  
  public final void d() {
    this.h.e(new i(1, 2));
    this.h.e(new i(0, 3));
    this.h.e(new i(1, 2));
    this.h.e(new i(1, 2));
    this.h.e(new i(1, 3));
    this.h.e(new i(1, 3));
    this.h.e(new i(1, 3));
    this.h.e(new i(1, 3));
    this.h.e(new i(1, 4));
    this.h.e(new i(1, 4));
    this.h.e(new i(1, 4));
    this.h.e(new i(1, 5));
    this.h.e(new i(1, 5));
  }
  
  public final void a(String[] paramArrayOfString) {
    this.h = new e(e.c);
    for (byte b1 = 0; b1 < paramArrayOfString.length; b1++)
      this.h.e(a(paramArrayOfString[b1])); 
  }
  
  public final void a(String[] paramArrayOfString1, String[] paramArrayOfString2) {
    this.h = new e(e.c);
    byte b1;
    for (b1 = 0; b1 < paramArrayOfString1.length; b1++)
      this.h.e(a(paramArrayOfString1[b1])); 
    for (b1 = 0; b1 < paramArrayOfString2.length; b1++) {
      i i1;
      (i1 = a(paramArrayOfString2[b1])).i = b(paramArrayOfString2[b1]);
      i1.j = c(paramArrayOfString2[b1]);
      this.i.e(i1);
    } 
  }
  
  public final void e() {
    byte b1;
    for (b1 = 0; b1 <= this.h.e() - 1; b1++)
      a(3, String.valueOf((this.h.a(b1)).g) + "_" + (this.h.a(b1)).h + "  "); 
    for (b1 = 0; b1 <= this.i.e() - 1; b1++)
      a(3, String.valueOf((this.i.a(b1)).g) + "_" + (this.i.a(b1)).h + "_" + (this.i.a(b1)).i + "  "); 
  }
  
  private i a(String paramString) {
    int j = Integer.parseInt(paramString.substring(0, 1));
    int k = Integer.parseInt(paramString.substring(2, 3));
    return new i(j, k);
  }
  
  private int b(String paramString) {
    return Integer.parseInt(paramString.substring(4, 5));
  }
  
  private int c(String paramString) {
    return Integer.parseInt(paramString.substring(6, 7));
  }
  
  private void a(int paramInt, String paramString) {
    if (paramInt >= this.a)
      System.out.print(paramString); 
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\engine\q.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */