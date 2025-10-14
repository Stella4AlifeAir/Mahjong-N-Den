package com.pheephoo.mjgame.ui;

import com.pheephoo.mjgame.engine.c;
import com.pheephoo.mjgame.engine.e;
import com.pheephoo.mjgame.engine.i;
import com.pheephoo.mjgame.engine.p;
import com.pheephoo.mjgame.engine.q;
import com.pheephoo.mjgame.engine.r;
import com.pheephoo.mjgame.engine.s;
import com.pheephoo.mjgame.engine.t;
import java.util.Vector;

public final class a implements c {
  int a = 10000;
  
  s b;
  
  q c;
  
  p d = new p();
  
  e e = new e(e.d);
  
  e f = new e(e.b);
  
  e g = new e(e.b);
  
  e h = new e(e.b);
  
  e i = new e(30);
  
  Vector j;
  
  int k;
  
  int l;
  
  int m;
  
  String n;
  
  boolean o;
  
  int p;
  
  public a(int paramInt, String paramString) {
    this.d = new p();
    this.m = paramInt;
    this.n = paramString;
  }
  
  public final void a(i parami, int paramInt1, int paramInt2) {
    if (paramInt2 != this.c.b)
      return; 
    e(100);
    this.c.j = parami;
    this.c.k = null;
  }
  
  public final void a() {
    if (this.c.j != null) {
      this.c.a(this.c.j);
      this.c.j = null;
    } 
    i i = h();
    this.c.h.b(i);
    this.b.a(i);
  }
  
  private void f() {
    for (byte b = 0; b < this.c.h.e(); b++) {
      i i;
      if ((i = this.c.h.a(b)).j == 0)
        if (i.g == 3 && i.h != this.b.e() + 1 && i.h != this.c.b + 1) {
          i.j = 11;
          this.i.d(i);
        } else if (i.h == 9 && i.j != 9) {
          i.j = 21;
          this.i.d(i);
        } else if (i.h == 1 && i.g != 3 && i.g != 4 && i.j != 9) {
          i.j = 22;
          this.i.d(i);
        } else if (i.g == 4) {
          i.j = 31;
          this.i.d(i);
        } else if (i.g == 3 && i.h == this.b.e()) {
          i.j = 41;
          this.i.d(i);
        } else if (i.g == 3) {
          i.j = 42;
          this.i.d(i);
        }  
    } 
  }
  
  private void g() {
    e e1;
    int i = (e1 = this.c.h).e();
    for (byte b = 0; b < i; b++) {
      i i1;
      if ((i1 = e1.a(b)).j == 0 || i1.j == 9)
        if (i1.g == 3 && i1.h != this.b.e() + 1 && i1.h != this.c.b + 1) {
          i1.j = 11;
          this.i.d(i1);
        } else if (i1.h == 9 && i1.j != 9) {
          i1.j = 21;
          this.i.d(i1);
        } else if (i1.h == 1 && i1.g != 3 && i1.g != 4 && i1.j != 9) {
          i1.j = 22;
          this.i.d(i1);
        } else if (i1.g == 4) {
          i1.j = 31;
          this.i.d(i1);
        } else if (i1.g == 3 && i1.h == this.b.e()) {
          i1.j = 41;
          this.i.d(i1);
        } else if (i1.g == 3) {
          i1.j = 42;
          this.i.d(i1);
        } else if (i1.j == 9 && i1.h == 1) {
          i i2 = new i(i1.g, i1.h + 2);
          int j;
          if ((j = this.e.c(i2) + this.f.c(i2) + this.g.c(i2) + this.h.c(i2) + this.c.i.c(i2) + e1.c(i2)) == 4) {
            i1.j = 51;
            this.i.d(i1);
          } else {
            i1.j = 61;
            this.i.d(i1);
          } 
          b++;
        } else if (i1.j == 9 && i1.h == 8) {
          i i2 = new i(i1.g, i1.h - 1);
          int j;
          if ((j = this.e.c(i2) + this.f.c(i2) + this.g.c(i2) + this.h.c(i2) + this.c.i.c(i2) + e1.c(i2)) == 4) {
            i1.j = 52;
            this.i.d(i1);
            b++;
          } else {
            if (++b >= i)
              return; 
            (i1 = this.c.h.a(b)).j = 62;
            this.i.d(i1);
          } 
        } else if (i1.j == 9) {
          byte b1 = 2;
          i i2 = new i(i1.g, i1.h - 1);
          int j;
          if ((j = this.e.c(i2) + this.f.c(i2) + this.g.c(i2) + this.h.c(i2) + this.c.i.c(i2) + this.c.h.c(i2)) == 4)
            b1--; 
          i2 = new i(i1.g, i1.h + 2);
          if ((j = this.e.c(i2) + this.f.c(i2) + this.g.c(i2) + this.h.c(i2) + this.c.i.c(i2) + this.c.h.c(i2)) == 4)
            b1--; 
          if (b1 == 0) {
            i1.j = 53;
            this.i.d(i1);
          } else {
            i1.j = 63;
            this.i.d(i1);
          } 
          b++;
        } else if (i1.j == 0 && i1.g != 3 && i1.g != 4) {
          i i2 = new i(i1.g, i1.h + 2);
          i i3 = null;
          if (e1.b(i2, 0) == 0) {
            i1.j = 61;
            this.i.d(i1);
          } else {
            i i4 = new i(i1.g, i1.h + 1);
            int j;
            if ((j = this.e.c(i4) + this.f.c(i4) + this.g.c(i4) + this.h.c(i4) + this.c.i.c(i4) + this.c.h.c(i4)) == 4) {
              i1.j = 54;
              this.i.d(i1);
            } else {
              i1.j = 71;
              this.i.d(i1);
              for (int k = b + 1; k < i; k++) {
                i3 = this.c.h.a(k);
                if (i2.a(i3) && i3.j == 0)
                  i3.j = 8; 
              } 
            } 
          } 
        }  
    } 
  }
  
  private i h() {
    i i = null;
    this.i.f();
    this.d.a(this.c.h, this.c.i, this.b.e(), this.c.b);
    this.d.a(0);
    int j;
    if ((j = this.d.b + this.d.c) == 0 && (this.d.d || (this.d.e && this.d.f)))
      j = 1; 
    int k = ((t)this.b).w - j;
    if (this.d.a >= 3) {
      f();
    } else if (k <= 0) {
      this.o = false;
      g();
    } else if (k <= 1) {
      this.d.a(this.c.h, this.c.i, this.b.e(), this.c.b);
      this.d.a(1);
      if (this.d.a >= 3) {
        this.o = true;
        f();
      } else {
        f();
      } 
    } 
    e(100);
    f(100);
    if (this.i.e() > 0)
      return this.i.a(0); 
    int m = com.pheephoo.utilx.a.a(0, this.c.h.e() - 1);
    i = this.c.h.a(m);
    for (byte b = 0; i.j != 0 && b < 20; b++) {
      m = com.pheephoo.utilx.a.a(0, this.c.h.e() - 1);
      i = this.c.h.a(m);
    } 
    return i;
  }
  
  public final void a(int paramInt) {}
  
  public final void b(i parami, int paramInt1, int paramInt2) {
    this.c.k = parami;
    this.e.e(parami);
  }
  
  public final void a(int paramInt1, int paramInt2, int paramInt3, s params, q paramq, int paramInt4) {
    this.b = params;
    this.c = paramq;
    this.c.g = this.n;
    this.c.f = this.m;
  }
  
  public final void a(int paramInt1, int paramInt2, int paramInt3, String paramString) {}
  
  public final void a(Vector paramVector) {
    this.j = paramVector;
    com.pheephoo.mjgame.engine.a a1;
    if ((a1 = this.j.firstElement()).b == r.b || a1.b == r.a) {
      i();
      return;
    } 
    if (a1.b == r.d)
      l(); 
  }
  
  public final void b(Vector paramVector) {
    this.j = paramVector;
    com.pheephoo.mjgame.engine.a a1;
    if ((a1 = this.j.firstElement()).b == r.b || a1.b == r.a) {
      j();
      return;
    } 
    if (a1.b == r.c) {
      k();
      return;
    } 
    if (a1.b == r.d || a1.b == r.e)
      l(); 
  }
  
  public final void a(e parame, int paramInt1, int paramInt2, boolean paramBoolean, i parami) {
    this.e.b(parami);
    if (paramInt2 < this.c.b)
      paramInt2 += 4; 
    if (paramInt2 == this.c.b + 1) {
      this.f.f();
      for (byte b = 0; b < parame.e(); b++) {
        i i1 = parame.a(b);
        this.f.e(i1);
      } 
      return;
    } 
    if (paramInt2 == this.c.b + 2) {
      this.g.f();
      for (byte b = 0; b < parame.e(); b++) {
        i i1 = parame.a(b);
        this.g.e(i1);
      } 
      return;
    } 
    if (paramInt2 == this.c.b + 3) {
      this.h.f();
      for (byte b = 0; b < parame.e(); b++) {
        i i1 = parame.a(b);
        this.h.e(i1);
      } 
    } 
  }
  
  public final void a(e parame1, e parame2) {
    this.e = new e(e.d);
    this.l = 0;
    int i = parame2.e();
    int j = -1;
    for (byte b = 0; b < i; b++) {
      i i1 = this.c.i.a(b);
      if (j != i1.i) {
        this.p++;
        j = i1.i;
      } 
    } 
    this.l = this.p;
    this.b.d();
  }
  
  private void i() {
    com.pheephoo.mjgame.engine.a a1 = this.j.elementAt(0);
    i i = null;
    if (!a1.e) {
      i = d(this.k);
    } else {
      i = a(this.k, this.c.j);
    } 
    this.p++;
    this.b.a(true, i);
    if (this.a >= 2)
      c(); 
  }
  
  private void j() {
    this.d.a(this.c.h, this.c.i, this.b.e(), this.c.b);
    this.d.a(1);
    if (this.e.e() < 20) {
      if (this.d.a > 1) {
        if (this.c.k.g == 4 || (this.c.k.g == 3 && this.c.k.h == this.c.b) || (this.c.k.g == 3 && this.c.k.h == this.b.e())) {
          this.c.j = null;
          b(this.k);
          return;
        } 
        this.c.j = null;
        b(this.k);
        return;
      } 
      this.b.a(r.a, false, this.c.b);
      return;
    } 
    if (this.d.a > 1) {
      this.c.j = null;
      b(this.k);
      return;
    } 
    this.b.a(r.a, false, this.c.b);
  }
  
  private void k() {
    this.d.a(this.c.h, this.c.i, this.b.e(), this.c.b);
    this.d.a(0);
    if (this.o) {
      this.b.a(r.a, false, this.c.b);
      return;
    } 
    if (this.e.e() < 20) {
      this.b.a(r.a, false, this.c.b);
      return;
    } 
    com.pheephoo.mjgame.engine.a a1 = this.j.elementAt(0);
    i i1 = this.c.h.a(a1.c);
    i i2 = this.c.h.a(a1.d);
    if (i1.j == 3 || i2.j == 3) {
      this.b.a(r.a, false, this.c.b);
      return;
    } 
    if (i1.j == 1 && this.d.a > 1) {
      this.c.j = null;
      c(this.k);
      return;
    } 
    if (i1.j == 0 && i2.j == 0) {
      this.c.j = null;
      c(this.k);
      return;
    } 
    this.b.a(r.a, false, this.c.b);
  }
  
  private void b(int paramInt) {
    boolean bool = false;
    com.pheephoo.mjgame.engine.a a1;
    int i;
    for (i = (a1 = this.j.elementAt(0)).c; i <= a1.d; i++) {
      i i1;
      (i1 = this.c.h.a(i)).i = this.l;
      i1.j = 2;
      this.c.i.f(i1);
    } 
    for (i = a1.c; i <= a1.d; i++)
      this.c.h.b(this.c.h.a(a1.c)); 
    this.c.k.i = this.l;
    this.c.k.j = 2;
    this.c.i.f(this.c.k);
    this.l++;
    this.p++;
    this.b.a(a1.b, true, this.c.b);
    a1.getClass();
    if (this.a >= 2)
      c(); 
    a();
  }
  
  private void c(int paramInt) {
    com.pheephoo.mjgame.engine.a a1 = this.j.elementAt(0);
    i i1 = this.c.h.a(a1.c);
    i i2 = this.c.h.a(a1.d);
    i1.i = this.l;
    this.c.k.i = this.l;
    this.c.k.j = 3;
    if (this.c.k.h < i1.h) {
      this.c.i.f(this.c.k);
      this.c.i.f(new i(i1.g, i1.h, i1.i, 3));
      i2.i = this.l;
      this.c.i.f(new i(i2.g, i2.h, i2.i, 3));
    } else {
      this.c.i.f(new i(i1.g, i1.h, i1.i, 3));
      i2.i = this.l;
      if (this.c.k.h < i2.h) {
        this.c.i.f(this.c.k);
        this.c.i.f(new i(i2.g, i2.h, i2.i, 3));
      } else {
        this.c.i.f(new i(i2.g, i2.h, i2.i, 3));
        this.c.i.f(this.c.k);
      } 
    } 
    this.c.h.b(i1);
    this.c.h.b(i2);
    this.l++;
    this.p++;
    this.b.a(r.a, true, this.c.b);
    a();
  }
  
  private i d(int paramInt) {
    com.pheephoo.mjgame.engine.a a1 = this.j.elementAt(0);
    i i = null;
    byte b = 0;
    int j;
    for (j = a1.c; j <= a1.d; j++) {
      (i = this.c.h.a(j)).i = this.l;
      i.j = 2;
      this.c.i.f(i);
      b++;
    } 
    for (j = a1.c; j <= a1.d; j++)
      this.c.h.b(this.c.h.a(a1.c)); 
    if (b != 4) {
      this.c.j.i = this.l;
      this.c.j.j = 2;
      this.c.i.f(this.c.j);
    } else {
      this.c.j.i = this.l;
      this.c.j.j = 2;
      this.c.h.e(this.c.j);
    } 
    this.c.j = null;
    this.l++;
    return i;
  }
  
  private i a(int paramInt, i parami) {
    com.pheephoo.mjgame.engine.a a1 = this.j.elementAt(0);
    i i1;
    if ((i1 = this.c.i.a(a1.c)).a(parami)) {
      parami.i = i1.i;
      parami.j = i1.j;
      this.c.i.a(parami);
    } else {
      for (byte b = 0; b < this.c.h.e(); b++) {
        i i2 = this.c.h.a(b);
        if (i1.a(i2)) {
          i2.i = i1.i;
          i2.j = i1.j;
          this.c.i.a(i2);
          this.c.h.b(i2);
          this.c.h.e(parami);
          break;
        } 
      } 
    } 
    return i1;
  }
  
  public final void a(int paramInt1, e parame1, e parame2, int paramInt2, int paramInt3, int paramInt4, e parame3, e parame4, int paramInt5, int paramInt6, int paramInt7, e parame5, e parame6, int paramInt8, int paramInt9, int paramInt10, e parame7, e parame8, int paramInt11, int paramInt12, int paramInt13) {}
  
  public final void b() {}
  
  private void l() {
    if (this.c.k != null) {
      this.c.h.e(this.c.k);
    } else if (this.c.j != null) {
      this.c.h.e(this.c.j);
    } 
    this.b.a(this.c.b);
  }
  
  public final void c() {
    int i = this.c.h.e();
    byte b;
    for (b = 0; b < i; b++)
      a(2, String.valueOf((this.c.h.a(b)).g) + "_" + (this.c.h.a(b)).h + "  "); 
    for (b = 0; b <= this.c.i.e() - 1; b++)
      a(2, String.valueOf((this.c.i.a(b)).g) + "_" + (this.c.i.a(b)).h + "_" + (this.c.i.a(b)).i + "  "); 
  }
  
  private void a(int paramInt, String paramString) {
    if (paramInt >= this.a)
      System.out.print(paramString); 
  }
  
  public final void a(int paramInt1, e parame1, e parame2, int paramInt2, int paramInt3, int paramInt4, String paramString1, int paramInt5, e parame3, int paramInt6, int paramInt7, int paramInt8, String paramString2, int paramInt9, e parame4, int paramInt10, int paramInt11, int paramInt12, String paramString3, int paramInt13, e parame5, e parame6) {}
  
  private void e(int paramInt) {
    if (paramInt >= this.a) {
      System.out.println("\nVisible : ");
      byte b;
      for (b = 0; b <= this.c.i.e() - 1; b++)
        System.out.print(String.valueOf((this.c.i.a(b)).g) + (this.c.i.a(b)).h + "_" + (this.c.i.a(b)).i + "_" + (this.c.i.a(b)).j + "  "); 
      System.out.println("\nConcealed: ");
      for (b = 0; b <= this.c.h.e() - 1; b++)
        System.out.print(String.valueOf((this.c.h.a(b)).g) + (this.c.h.a(b)).h + "_" + (this.c.h.a(b)).j + "  "); 
      System.out.println("");
    } 
  }
  
  private void f(int paramInt) {
    if (paramInt >= this.a) {
      System.out.println("\nDiscard Candidates: ");
      for (byte b = 0; b <= this.i.e() - 1; b++)
        System.out.print(String.valueOf((this.i.a(b)).g) + (this.i.a(b)).h + "_" + (this.i.a(b)).i + "_" + (this.i.a(b)).j + "  "); 
      System.out.println("");
    } 
  }
  
  public final void d() {}
  
  public final void a(String paramString) {}
  
  public final void a(int paramInt1, int paramInt2, String paramString) {}
  
  public final void a(int paramInt1, int paramInt2, String paramString1, String paramString2) {}
  
  public final void b(String paramString) {}
  
  public final void e() {
    this.b = null;
    this.c = null;
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgam\\ui\a.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */