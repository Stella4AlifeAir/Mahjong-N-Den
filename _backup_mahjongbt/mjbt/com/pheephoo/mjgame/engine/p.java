package com.pheephoo.mjgame.engine;

import com.pheephoo.mjgame.d;

public final class p {
  private int h = 1000;
  
  private e i;
  
  private e j;
  
  private int k;
  
  private int l;
  
  private boolean m;
  
  private int n;
  
  public int a;
  
  private int o;
  
  private int p;
  
  private int q;
  
  private int r;
  
  private int s;
  
  public int b;
  
  public int c;
  
  private int t;
  
  private int[] u = new int[6];
  
  private int[] v = new int[6];
  
  private int w;
  
  private int x;
  
  public boolean d;
  
  public boolean e;
  
  public boolean f;
  
  boolean g;
  
  private void e() {
    this.n = 0;
    this.a = 0;
    this.p = 0;
    this.q = 0;
    this.r = 0;
    this.s = 0;
    this.o = 0;
    this.d = false;
    this.e = false;
    this.b = 0;
    this.c = 0;
    this.t = 0;
    this.f = false;
    this.g = false;
  }
  
  public final void a(e parame1, e parame2, int paramInt1, int paramInt2) {
    this.i = parame1;
    this.i.h();
    this.j = parame2;
    this.l = paramInt1;
    this.k = paramInt2;
    e();
  }
  
  private void f() {
    e e1 = this.i;
    this.p = 0;
    byte b1 = 1;
    byte b2 = 1;
    int i = -1;
    i i1 = e1.a(0);
    int j = e1.c();
    i i2 = null;
    for (byte b3 = 1; b3 < j; b3++) {
      if ((i2 = e1.a(b3)).g == i1.g && i2.h == i1.h && i1.j == 0 && i2.j == 0) {
        b1++;
        if (i == -1)
          i = b2 - 1; 
        if (b1 == 3) {
          i i3;
          (i3 = e1.a(i)).j = 2;
          i3.i = this.n;
          (i3 = e1.a(i + 1)).j = 2;
          i3.i = this.n;
          (i3 = e1.a(i + 2)).j = 2;
          i3.i = this.n;
          this.p++;
          this.n++;
          a(i3);
        } 
      } else {
        b1 = 1;
        i = -1;
      } 
      i1 = e1.a(b3);
      b2++;
    } 
  }
  
  public final void a() {
    e e1 = this.j;
    int i = -1;
    int j = e1.e();
    i i1 = null;
    for (byte b = 0; b < j; b++) {
      if ((i1 = e1.a(b)).i != i) {
        i = i1.i;
        if (i1.j == 2) {
          this.r++;
        } else if (i1.j == 3) {
          this.s++;
        } 
        a(i1);
      } 
    } 
  }
  
  private int g() {
    int i = 0;
    if (this.j.e() == 0 && this.q == 0 && this.m)
      return i = d.z; 
    if (this.q + this.s == 4 && !this.f) {
      i = 1;
    } else if (this.p + this.r + this.o == 4) {
      i += 2;
    } 
    int j;
    if ((j = j()) == 1) {
      i += 2;
    } else if (j == 2) {
      i += 4;
    } else if (j == 3) {
      return i = d.z;
    } 
    int k;
    if ((k = k()) == 1) {
      i += 4;
    } else if (k == 2) {
      return i = d.z;
    } 
    if (this.b == 3)
      return i = d.z; 
    if (this.b == 2 && this.d) {
      i += 3;
    } else {
      i += this.b;
    } 
    if (this.t == 4)
      return i = d.z; 
    if (this.t == 3 && this.e)
      i += 4; 
    if (i < d.z)
      i += this.c; 
    return i;
  }
  
  private void b(int paramInt) {
    h();
    e e1;
    i i;
    (i = (e1 = this.i).a(paramInt)).j = 1;
    (i = e1.a(paramInt + 1)).j = 1;
  }
  
  private void h() {
    e e1;
    int i = (e1 = this.i).c();
    i i1 = null;
    for (byte b = 0; b < i; b++)
      (i1 = e1.a(b)).j = 0; 
  }
  
  private boolean i() {
    f();
    c(1);
    return (this.a == 1 && this.p + this.o + this.q + this.r + this.s == 4);
  }
  
  public final int a(boolean paramBoolean) {
    this.m = paramBoolean;
    boolean bool = false;
    a();
    if (b() == -1) {
      c();
      if (this.w > 1) {
        this.g = true;
      } else if (this.x > 0) {
        this.g = true;
      } else {
        this.a = this.w;
      } 
    } else if (this.a == 1) {
      if (i()) {
        this.g = true;
        bool = true;
      } else if (this.p > 0 || this.q > 0) {
        this.g = true;
      } 
    } else {
      if (this.a > 1)
        return -1; 
      c();
      if (this.w > 1 || this.w == 0)
        this.g = true; 
      byte b;
      for (b = 0; b < this.w; b++) {
        this.a = 1;
        b(this.u[b]);
        if (i()) {
          this.g = true;
          bool = true;
          break;
        } 
      } 
      if (this.x > 0)
        this.g = true; 
      if (!bool)
        for (b = 0; b < this.x; b++) {
          this.a = 1;
          b(this.v[b]);
          if (i()) {
            bool = true;
            break;
          } 
        }  
    } 
    if (this.h <= 5)
      d(5); 
    if (!this.g && d())
      return d.z; 
    d(20);
    return bool ? g() : -1;
  }
  
  public final int a(int paramInt) {
    boolean bool = false;
    b();
    if (paramInt == 0)
      c(0); 
    c();
    f();
    d(100);
    return 0;
  }
  
  private boolean c(int paramInt) {
    this.q = 0;
    e e1 = this.i;
    d(5);
    int i = e1.c();
    i i1 = null;
    int[] arrayOfInt = new int[14];
    for (byte b = 0; b < 4; b++) {
      i i2 = null;
      byte b1 = 0;
      byte b2 = 0;
      byte b3 = 0;
      int j;
      for (j = 0; j < i; j++) {
        if ((i2 = e1.a(j)).j == 0) {
          b2++;
          arrayOfInt[0] = j;
          break;
        } 
        b1++;
      } 
      for (j = b1 + 1; j < i; j++) {
        if ((i1 = e1.a(j)).j == 0)
          if (i1.g == i2.g && i1.h - i2.h == 1) {
            if (paramInt == 0) {
              i2.j = 9;
              i1.j = 9;
            } 
            b3++;
            arrayOfInt[b2++] = j;
            i2 = e1.a(j);
            if (b3 == 2) {
              this.q++;
              break;
            } 
          } else if (!i1.a(i2)) {
            if (paramInt == 1)
              return false; 
            b2 = 0;
            b3 = 0;
            int k;
            for (k = j; k < i; k++) {
              if ((i2 = e1.a(j)).j == 0) {
                b2++;
                arrayOfInt[0] = j;
                break;
              } 
            } 
            j = k;
          }  
      } 
      i i3 = null;
      if (b2 >= 3)
        for (byte b4 = 0; b4 < b2; b4++)
          (i3 = e1.a(arrayOfInt[b4])).j = 3;  
    } 
    return true;
  }
  
  public final int b() {
    this.a = 0;
    e e1;
    int i;
    if ((i = (e1 = this.i).d()) != -1) {
      i i1 = e1.a(i);
      i i2 = null;
      int j;
      if ((j = e1.e()) - i == 1)
        return -1; 
      for (int k = i + 1; k < j; k++) {
        if ((i2 = e1.a(k)).a(i1)) {
          if (++k < j)
            i2 = e1.a(k); 
          if (k == j || !i2.a(i1)) {
            (i2 = e1.a(k - 1)).j = 1;
            i1.j = 1;
            this.a++;
            if (i2.g == 4) {
              this.d = true;
              this.f = true;
            } else {
              this.e = true;
            } 
            if (i2.g == 3 && (i2.h == this.l + 1 || i2.h == this.k + 1))
              this.f = true; 
          } else {
            if (++k < j)
              i2 = e1.a(k); 
            if (k == j || !i2.a(i1)) {
              (i2 = e1.a(k - 1)).j = 2;
              (i2 = e1.a(k - 2)).j = 2;
              i1.j = 2;
              this.o++;
              a(i2);
            } else {
              return -1;
            } 
          } 
        } else {
          return -1;
        } 
        if (k < j) {
          i1 = e1.a(k);
          if (k == j - 1)
            return -1; 
        } 
      } 
    } 
    return this.a;
  }
  
  public final void c() {
    this.w = 0;
    this.x = 0;
    e e1;
    i i1 = (e1 = this.i).a(0);
    i i2 = null;
    int i;
    if ((i = e1.d()) == -1)
      i = e1.e(); 
    for (byte b = 1; b < i; b++) {
      if ((i2 = e1.a(b)).a(i1)) {
        if (++b < i)
          i2 = e1.a(b); 
        if (b == i || !i2.a(i1)) {
          this.u[this.w++] = b - 2;
          (i2 = e1.a(b - 1)).j = 1;
          i1.j = 1;
          this.a++;
        } else {
          this.v[this.x++] = b - 2;
          b++;
        } 
      } 
      if (b < i)
        i1 = e1.a(b); 
    } 
  }
  
  private void a(i parami) {
    if (parami.g == 4) {
      this.b++;
    } else if (parami.g == 3) {
      this.t++;
    } 
    if (parami.g == 3 && parami.h == this.l + 1)
      this.c++; 
    if (parami.g == 3 && parami.h == this.k + 1)
      this.c++; 
  }
  
  private int j() {
    byte b1 = 0;
    boolean bool = false;
    int i = -1;
    e e1;
    int j = (e1 = this.i).e();
    for (byte b2 = 0; b2 < j; b2++) {
      i i1;
      if ((i1 = e1.a(b2)).g == 4 || i1.g == 3) {
        bool = true;
      } else if (i != i1.g) {
        b1++;
        i = i1.g;
        if (b1 == 2)
          return 0; 
      } 
    } 
    e e2;
    j = (e2 = this.j).e();
    for (byte b3 = 0; b3 < j; b3++) {
      i i1;
      if ((i1 = e2.a(b3)).g == 4 || i1.g == 3) {
        bool = true;
      } else if (i != i1.g) {
        b1++;
        i = i1.g;
        if (b1 == 2)
          return 0; 
      } 
    } 
    return (b1 == 0) ? 3 : (bool ? 1 : 2);
  }
  
  private int k() {
    boolean bool = false;
    e e1;
    int i = (e1 = this.i).e();
    for (byte b1 = 0; b1 < i; b1++) {
      i i1;
      if ((i1 = e1.a(b1)).g == 4 || i1.g == 3) {
        bool = true;
      } else if (i1.h != 1 && i1.h != 9) {
        return 0;
      } 
    } 
    e e2;
    i = (e2 = this.j).e();
    for (byte b2 = 0; b2 < i; b2++) {
      i i1;
      if ((i1 = e2.a(b2)).g == 4 || i1.g == 3) {
        bool = true;
      } else if (i1.h != 1 && i1.h != 9) {
        return 0;
      } 
    } 
    return bool ? 1 : 2;
  }
  
  public final boolean d() {
    e e1 = this.i;
    e e2 = this.j;
    if (this.q > 0 || this.p > 0 || e2.e() > 0)
      return false; 
    int i;
    if ((i = e1.e()) != 14)
      return false; 
    int j;
    if ((j = e1.d()) != -1) {
      if (i - j < 7 || i - j > 8)
        return false; 
      byte b1 = 0;
      i i1 = e1.a(j);
      for (int k = j + 1; k < i; k++) {
        i i2;
        if ((i2 = e1.a(k)).a(i1) && ++b1 + this.a > 1)
          return false; 
        i1 = e1.a(k);
      } 
    } 
    for (byte b = 0; b < i; b++) {
      i i1;
      if ((i1 = e1.a(b)).g != 4 && i1.g != 3 && i1.h != 1 && i1.h != 9)
        return false; 
    } 
    return true;
  }
  
  private void d(int paramInt) {
    e e1 = this.i;
    e e2 = this.j;
    if (paramInt >= this.h) {
      System.out.println("\nVisible : ");
      byte b;
      for (b = 0; b <= e2.e() - 1; b++)
        System.out.print(String.valueOf((e2.a(b)).g) + (e2.a(b)).h + "_" + (e2.a(b)).i + "_" + (e2.a(b)).j + "  "); 
      System.out.println("\nConcealed: ");
      for (b = 0; b <= e1.e() - 1; b++)
        System.out.print(String.valueOf((e1.a(b)).g) + (e1.a(b)).h + "_" + (e1.a(b)).j + "  "); 
      System.out.println("");
    } 
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\engine\p.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */