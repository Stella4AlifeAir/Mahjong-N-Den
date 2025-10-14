package com.pheephoo.mjgame.engine;

public class e {
  public static int b = 16;
  
  public static int c = 14;
  
  public static int d = 100;
  
  private i[] e;
  
  private int f;
  
  public e(int paramInt) {
    this.e = new i[paramInt];
  }
  
  public final int c() {
    int j;
    if ((j = d()) == -1)
      j = this.f; 
    return j;
  }
  
  public final int d() {
    for (int j = this.f - 1, k = j; k >= 0; k--) {
      i i1;
      if ((i1 = this.e[k]).g < 3)
        return (k == j) ? -1 : (k + 1); 
    } 
    return 0;
  }
  
  public final int e() {
    return this.f;
  }
  
  public final i a(int paramInt) {
    return this.e[paramInt];
  }
  
  public final void a(i parami) {
    i i1 = null;
    for (byte b = 0; b < this.f; b++) {
      i1 = this.e[b];
      if (parami.i == i1.i) {
        for (int j = this.f; j > b + 1; j--)
          this.e[j] = this.e[j - 1]; 
        this.e[b + 1] = parami;
        this.f++;
        return;
      } 
    } 
  }
  
  public final void f() {
    this.f = 0;
  }
  
  public final void g() {
    this.f--;
  }
  
  public final void b(int paramInt) {
    for (int j = paramInt; j < this.f - 1; j++)
      this.e[j] = this.e[j + 1]; 
    this.f--;
  }
  
  public final void b(i parami) {
    for (byte b = 0; b < this.f; b++) {
      if (parami.a(this.e[b])) {
        for (byte b1 = b; b1 < this.f - 1; b1++)
          this.e[b1] = this.e[b1 + 1]; 
        this.f--;
        return;
      } 
    } 
  }
  
  public final void a(i parami, int paramInt) {
    for (int j = this.f; j > paramInt; j--)
      this.e[j] = this.e[j - 1]; 
    this.e[paramInt] = parami;
    this.f++;
  }
  
  public final void a(int[] paramArrayOfint, int paramInt1, int paramInt2) {
    for (int j = paramInt1; j < paramInt2; j += 2)
      e(new i(paramArrayOfint[j], paramArrayOfint[j + 1])); 
  }
  
  public final int c(i parami) {
    byte b1 = 0;
    for (byte b2 = 0; b2 < this.f; b2++) {
      if (parami.a(this.e[b2]))
        b1++; 
    } 
    return b1;
  }
  
  public final int b(i parami, int paramInt) {
    boolean bool = false;
    for (byte b = 0; b < this.f; b++) {
      if (parami.a(this.e[b]) && (this.e[b]).j == paramInt)
        return 1; 
    } 
    return 0;
  }
  
  public final void d(i parami) {
    byte b = 0;
    if (this.f == 0) {
      this.e[this.f++] = parami;
      return;
    } 
    while (b <= this.f - 1) {
      if (parami.j < (this.e[b]).j) {
        a(parami, b);
        return;
      } 
      b++;
    } 
    this.e[this.f++] = parami;
  }
  
  public final void e(i parami) {
    byte b = 0;
    if (this.f == 0) {
      this.e[this.f++] = parami;
      return;
    } 
    while (b <= this.f - 1) {
      if (parami.g < (this.e[b]).g) {
        a(parami, b);
        return;
      } 
      if (parami.g == (this.e[b]).g) {
        if (parami.h <= (this.e[b]).h) {
          a(parami, b);
          return;
        } 
        b++;
        continue;
      } 
      b++;
    } 
    this.e[this.f++] = parami;
  }
  
  public final void f(i parami) {
    this.e[this.f++] = parami;
  }
  
  public final void h() {
    for (byte b = 0; b < this.f; b++) {
      (this.e[b]).i = 0;
      (this.e[b]).j = 0;
    } 
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\engine\e.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */