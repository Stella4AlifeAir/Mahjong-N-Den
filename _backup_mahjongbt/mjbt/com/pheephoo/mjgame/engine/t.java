package com.pheephoo.mjgame.engine;

import com.pheephoo.mjgame.d;
import com.pheephoo.utilx.a;
import java.util.Enumeration;
import java.util.Vector;

public final class t implements s {
  c[] a;
  
  b b;
  
  public q[] c;
  
  boolean d;
  
  public int e;
  
  int f;
  
  int g;
  
  boolean h = false;
  
  p i;
  
  public int j;
  
  boolean k;
  
  private int B;
  
  boolean l;
  
  boolean m;
  
  int n;
  
  int o;
  
  int p;
  
  int q;
  
  int r = -1;
  
  int s = this.o;
  
  int[] t = new int[] { 9, 9, 9, 9 };
  
  public Vector u = new Vector();
  
  public e v = new e(e.d);
  
  public int w;
  
  public int x = 5;
  
  private int C = 1;
  
  private int D = 2;
  
  private int E = -1;
  
  private int F;
  
  private int[] G = new int[4];
  
  private i H;
  
  private i I;
  
  Vector y;
  
  public int z;
  
  public boolean A;
  
  private String[] J = new String[] { "1_2" };
  
  private String[] K;
  
  public t() {
    (new String[2])[0] = "0_8";
    (new String[2])[1] = "1_2";
    this.K = new String[] { "0_3" };
    a();
  }
  
  public final void a() {
    System.currentTimeMillis();
    this.y = new Vector();
    for (byte b1 = 0; b1 < d.t; b1++)
      this.y.addElement(new Integer(b1)); 
    this.t[0] = a.a(0, 3);
    this.i = new p();
    if (this.e == 1) {
      this.t[0] = 0;
    } else if (this.e == 11) {
      this.t[0] = 2;
    } else if (this.e == 41) {
      this.t[0] = 0;
    } else if (this.e == 20) {
      this.t[0] = 0;
    } 
    this.t[0] = 0;
    this.t[1] = (this.t[0] + 1) % 4;
    this.t[2] = (this.t[0] + 2) % 4;
    this.t[3] = (this.t[0] + 3) % 4;
    this.b = new b();
    this.a = new c[4];
    this.c = new q[4];
  }
  
  public final void b() {
    this.a[0].a(0, this.t[0], 1, this, this.c[0], 0);
    this.a[1].a(0, this.t[1], 2, this, this.c[1], 0);
    this.a[2].a(0, this.t[2], 3, this, this.c[2], 0);
    this.a[3].a(0, this.t[3], 4, this, this.c[3], 0);
    for (byte b1 = 0; b1 < 4; b1++) {
      for (byte b2 = 0; b2 < 4; b2++) {
        if (b1 != b2)
          this.a[b1].a((this.c[b2]).b, 4, (this.c[b2]).f, (this.c[b2]).g); 
      } 
    } 
    h();
  }
  
  public final void a(c paramc, int paramInt) {
    this.c[this.n] = new q(this.b, this.t[this.n]);
    this.a[this.n] = paramc;
    this.n++;
  }
  
  private void h() {
    this.A = true;
    byte b1 = 0;
    this.b.b();
    if (this.e == 0) {
      for (b1 = 0; b1 < 4; b1++)
        this.c[b1].b(); 
      for (b1 = 0; b1 < 4; b1++)
        this.a[b1].a((this.c[b1]).h, (this.c[b1]).i); 
    } else if (this.e == 1) {
      this.b.a = 1;
      this.a[0].a(1);
      this.a[1].a(1);
      this.a[2].a(1);
      this.a[3].a(1);
      B();
    } else if (this.e == 10) {
      q();
    } else if (this.e == 11) {
      r();
    } else if (this.e == 12) {
      s();
    } else if (this.e == 13) {
      A();
    } else if (this.e == d.aD) {
      t();
    } else if (this.e == d.aE) {
      u();
    } else if (this.e == d.aF) {
      w();
    } else if (this.e == d.aG) {
      x();
    } else if (this.e == d.aL) {
      y();
    } else if (this.e == d.aM) {
      z();
    } else if (this.e == d.aF) {
      A();
    } else if (this.e == d.aH) {
      for (b1 = 0; b1 < 4; b1++)
        this.c[b1].b(); 
      for (b1 = 0; b1 < 4; b1++)
        this.a[b1].a((this.c[b1]).h, (this.c[b1]).i); 
    } else if (this.e == d.aI) {
      w();
    } else if (this.e == d.aJ) {
      v();
    } else if (this.e == d.aC) {
      this.b.a = d.aC;
      n();
    } else if (this.e == 22) {
      this.b.a = 22;
      o();
    } else if (this.e == 110) {
      this.b.a = 110;
      p();
    } 
    if (this.j != 0)
      j(); 
  }
  
  public final void d() {
    this.q++;
    if (this.j == 0 && this.q == 4)
      j(); 
  }
  
  public final void f() {
    if (this.A)
      return; 
    this.b.b();
    this.s = 0;
    this.g = 0;
    this.v.f();
    if (this.r != 0)
      if (this.r == -1) {
        if (this.l) {
          this.p++;
          this.c[0].c();
          this.c[1].c();
          this.c[2].c();
          this.c[3].c();
        } 
      } else {
        this.p++;
        this.c[0].c();
        this.c[1].c();
        this.c[2].c();
        this.c[3].c();
      }  
    this.f = 0;
    this.q = 0;
    this.k = false;
    this.l = false;
    this.r = -1;
    if (this.e == d.aD) {
      this.p = 4;
      this.o = 3;
    } 
    if (this.p == 4) {
      this.p = 0;
      if (this.o == 3 || this.j == 3) {
        this.a[0].d();
        this.a[1].d();
        this.a[2].d();
        this.a[3].d();
        return;
      } 
      this.o++;
    } 
    this.c[0].a();
    this.c[1].a();
    this.c[2].a();
    this.c[3].a();
    h();
  }
  
  private void i() {
    if (this.e == d.aF) {
      this.H = c(d.aF);
    } else if (this.e == d.aJ) {
      this.H = c(d.aJ);
    } else {
      this.H = this.b.a();
    } 
    (this.c[0]).l.removeAllElements();
    (this.c[1]).l.removeAllElements();
    (this.c[2]).l.removeAllElements();
    (this.c[3]).l.removeAllElements();
    for (byte b1 = 0; b1 < 4; b1++)
      this.a[b1].a(this.H, this.b.e(), this.s); 
    if (a(this.s, this.H) != 0) {
      this.a[m()].a((this.c[m()]).l);
      return;
    } 
    if (this.l) {
      this.A = false;
      c();
      return;
    } 
    this.A = false;
    this.a[0].b();
    this.a[1].b();
    this.a[2].b();
    this.a[3].b();
  }
  
  private void j() {
    this.f++;
    this.k = false;
    if ((this.e == d.aH || this.e == d.aI || this.e == d.aJ) && this.f == 1) {
      i();
      return;
    } 
    if ((this.e == d.aH || this.e == d.aI || this.e == d.aJ) && this.f == 2) {
      if (this.l) {
        this.A = false;
        c();
        return;
      } 
      this.A = false;
      this.a[0].b();
      this.a[1].b();
      this.a[2].b();
      this.a[3].b();
      return;
    } 
    if (this.b.e() == 0) {
      if (this.l) {
        this.A = false;
        c();
        return;
      } 
      this.A = false;
      this.a[0].b();
      this.a[1].b();
      this.a[2].b();
      this.a[3].b();
      return;
    } 
    if (this.e == d.aF) {
      this.H = c(d.aF);
    } else if (this.e == d.aE) {
      this.H = new i(0, 3);
    } else {
      this.H = this.b.a();
    } 
    (this.c[0]).l.removeAllElements();
    (this.c[1]).l.removeAllElements();
    (this.c[2]).l.removeAllElements();
    (this.c[3]).l.removeAllElements();
    for (byte b1 = 0; b1 < 4; b1++)
      this.a[b1].a(this.H, this.b.e(), this.s); 
    if (a(this.s, this.H) != 0) {
      this.a[m()].a((this.c[m()]).l);
      return;
    } 
    if (this.b.e() == 0) {
      if (this.l) {
        this.A = false;
        c();
        return;
      } 
      this.A = false;
      this.a[0].b();
      this.a[1].b();
      this.a[2].b();
      this.a[3].b();
      return;
    } 
    this.a[m()].a();
  }
  
  private boolean b(i parami) {
    if (!this.d) {
      this.k = false;
      return false;
    } 
    i i1 = new i(parami.g, parami.h);
    k();
    for (byte b1 = 0; b1 < 4; b1++) {
      if (b1 != b(this.s)) {
        this.B = this.s;
        this.k = true;
        this.G[b1] = a(b1, this.u, i1);
        if (this.u.size() != 0) {
          l();
          return true;
        } 
        this.k = false;
      } 
    } 
    this.k = false;
    return false;
  }
  
  private void k() {
    this.u.removeAllElements();
    (this.c[0]).l.removeAllElements();
    (this.c[1]).l.removeAllElements();
    (this.c[2]).l.removeAllElements();
    (this.c[3]).l.removeAllElements();
  }
  
  public final void a(i parami) {
    if (this.j == 3 && this.z == 0)
      return; 
    this.h = false;
    this.g++;
    if (this.b.e() == 0 || this.e == d.aH || this.e == d.aI || this.e == d.aJ || this.e == d.aK) {
      j();
      return;
    } 
    this.E = -1;
    this.v.f(parami);
    int j;
    for (j = 0; j < 4; j++) {
      if (j != b(this.s))
        this.a[j].b(parami, this.s, this.b.e()); 
    } 
    k();
    for (j = 1; j < 4; j++) {
      int k = b((this.s + j) % 4);
      this.G[k] = a(k, this.u, parami);
    } 
    j = this.u.size();
    for (byte b1 = 1; b1 < 4; b1++) {
      int k = b((this.s + b1) % 4);
      b(k, this.u, parami);
      if (this.u.size() > j)
        break; 
    } 
    c(b((this.s + 1) % 4), this.u, parami);
    if (this.u.size() == 0) {
      this.s = (this.s + 1) % 4;
      j();
      return;
    } 
    l();
  }
  
  private int a(int paramInt, Vector paramVector, i parami) {
    (this.c[paramInt]).l.removeAllElements();
    this.I = new i(parami.g, parami.h);
    (this.c[paramInt]).h.e(this.I);
    this.i.a((this.c[paramInt]).h, (this.c[paramInt]).i, this.o, (this.c[paramInt]).b);
    boolean bool = false;
    if (this.s == (this.c[paramInt]).b)
      bool = true; 
    int j;
    if ((j = this.i.a(bool)) >= 0 && this.g < 2)
      j = d.z; 
    (this.c[paramInt]).h.b(this.I);
    if (j >= 0 && this.k)
      j++; 
    if (j >= this.w)
      if (this.k) {
        (this.c[paramInt]).l.addElement(new a(r.e, 0, 0));
        paramVector.addElement(new a(paramInt, r.e));
      } else {
        (this.c[paramInt]).l.addElement(new a(r.d, 0, 0));
        byte b1 = 0;
        if (paramVector.size() != 0) {
          a a;
          for (b1 = 0; b1 < paramVector.size() && (a = paramVector.elementAt(b1)).b == r.d; b1++);
        } 
        paramVector.insertElementAt(new a(paramInt, r.d), b1);
        if (b1 != paramVector.size() && b1 != 0)
          this.h = true; 
      }  
    (this.c[paramInt]).h.h();
    return j;
  }
  
  private void b(int paramInt, Vector paramVector, i parami) {
    byte b1 = 1;
    byte b2 = 0;
    byte b3 = -1;
    int j = (this.c[paramInt]).l.size();
    e e1;
    int k = (e1 = (this.c[paramInt]).h).e();
    for (byte b4 = 0; b4 < k; b4++) {
      i i1;
      if ((i1 = e1.a(b4)).g == parami.g && i1.h == parami.h) {
        b1++;
        if (b3 == -1)
          b3 = b2; 
        if (b1 == 3)
          (this.c[paramInt]).l.addElement(new a(r.a, b3, b2)); 
        if (b1 == 4) {
          this.m = false;
          (this.c[paramInt]).l.addElement(new a(r.b, b3, b2));
        } 
      } else {
        b1 = 1;
        b3 = -1;
      } 
      b2++;
    } 
    if ((this.c[paramInt]).l.size() > j)
      paramVector.addElement(new a(paramInt, r.b)); 
  }
  
  public final int a(int paramInt, i parami) {
    this.u.removeAllElements();
    int j = m();
    (this.c[j]).l.removeAllElements();
    this.G[j] = a(j, this.u, parami);
    if (this.b.e() != 0)
      b(j, parami); 
    c(j, parami);
    return (this.c[j]).l.isEmpty() ? 0 : 1;
  }
  
  private void b(int paramInt, i parami) {
    this.d = false;
    boolean bool1 = false;
    boolean bool2 = false;
    i i1 = null;
    i i2 = null;
    e e1 = (this.c[paramInt]).h;
    e e2 = (this.c[paramInt]).i;
    int j = e1.e();
    int k = e2.e();
    for (byte b1 = 0; b1 < k; b1++) {
      if ((i1 = e2.a(b1)).j == 2) {
        if (i1.a(parami)) {
          (this.c[paramInt]).l.addElement(new a(r.b, b1, b1 + 2, true));
          b1 += 2;
          this.m = false;
          this.d = true;
        } else {
          for (byte b2 = 0; b2 < j; b2++) {
            i2 = e1.a(b2);
            if (i1.a(i2)) {
              (this.c[paramInt]).l.addElement(new a(r.b, b1, b1 + 2, true));
              this.d = true;
              this.m = false;
              b1 += 2;
              break;
            } 
          } 
        } 
      } else if (i1.j == 3) {
        b1 += 2;
      } 
    } 
  }
  
  private void c(int paramInt, i parami) {
    byte b1 = 1;
    byte b2 = 0;
    int j = -1;
    byte b3 = -1;
    i i1 = new i(9, 9);
    e e1;
    int k = (e1 = (this.c[paramInt]).h).e();
    for (byte b4 = 0; b4 < k; b4++) {
      i i2;
      if ((i2 = e1.a(b4)).g == i1.g && i2.h == i1.h) {
        b1++;
        if (j == -1)
          j = b2 - 1; 
        if (i2.g == parami.g && i2.h == parami.h && b3 == -1) {
          b1++;
          b3 = 1;
        } 
        if (b1 == 4) {
          this.m = true;
          (this.c[paramInt]).l.addElement(new a(r.b, j, b2));
        } 
      } else {
        b1 = 1;
        j = -1;
        b3 = -1;
      } 
      i1 = i2;
      b2++;
    } 
  }
  
  private void c(int paramInt, Vector paramVector, i parami) {
    if (parami.g > 2)
      return; 
    byte b1 = 0;
    byte b2 = -9;
    byte b3 = -9;
    byte b4 = -9;
    byte b5 = -9;
    boolean bool = false;
    e e1;
    int j = (e1 = (this.c[paramInt]).h).e();
    for (byte b6 = 0; b6 < j; b6++) {
      i i1;
      if ((i1 = e1.a(b6)).g == parami.g && parami.g != 3 && parami.g != 4) {
        if (i1.h > parami.h + 2)
          break; 
        if (i1.h == parami.h - 2) {
          b2 = b1;
        } else if (i1.h == parami.h - 1) {
          b3 = b1;
        } else if (i1.h == parami.h + 1) {
          b4 = b1;
        } else if (i1.h == parami.h + 2) {
          b5 = b1;
        } 
      } 
      b1++;
    } 
    if (b2 != -9 && b3 != -9) {
      bool = true;
      (this.c[paramInt]).l.addElement(new a(r.c, b2, b3));
    } 
    if (b3 != -9 && b4 != -9) {
      bool = true;
      (this.c[paramInt]).l.addElement(new a(r.c, b3, b4));
    } 
    if (b4 != -9 && b5 != -9) {
      bool = true;
      (this.c[paramInt]).l.addElement(new a(r.c, b4, b5));
    } 
    if (bool)
      paramVector.addElement(new a(paramInt, r.c)); 
  }
  
  public final int e() {
    return this.o;
  }
  
  public final void a(boolean paramBoolean, i parami) {
    (this.c[0]).l.removeAllElements();
    (this.c[1]).l.removeAllElements();
    (this.c[2]).l.removeAllElements();
    (this.c[3]).l.removeAllElements();
    if (paramBoolean) {
      for (byte b1 = 0; b1 < 4; b1++) {
        if (b1 != m())
          this.a[b1].a((this.c[m()]).i, (this.c[m()]).h.e() + 1, this.s, false, parami); 
      } 
      if (b(parami))
        return; 
      a(m(), this.m);
      j();
    } 
  }
  
  public final void a(int paramInt1, boolean paramBoolean, int paramInt2) {
    if (paramBoolean) {
      i i1 = this.v.a(this.v.e() - 1);
      this.v.g();
      int j;
      for (j = 0; j < 4; j++) {
        if (j != b(paramInt2))
          this.a[j].a((this.c[b(paramInt2)]).i, (this.c[b(paramInt2)]).h.e(), paramInt2, true, i1); 
      } 
      this.s = paramInt2;
      j = m();
      if (paramInt1 == r.b) {
        j();
        a(j, false);
        return;
      } 
    } else {
      l();
    } 
  }
  
  public final void c() {
    int[] arrayOfInt = new int[4];
    byte b1;
    for (b1 = 0; b1 < 4; b1++) {
      (this.c[b(b1)]).d = (this.c[b(b1)]).c;
      (this.c[b(b1)]).e -= (this.c[b(b1)]).d;
    } 
    for (b1 = 0; b1 < 4; b1++)
      this.a[b1].a(9, (this.c[b(0)]).h, (this.c[b(0)]).i, arrayOfInt[b(0)], -(this.c[b(0)]).d, (this.c[b(0)]).e, (this.c[b(1)]).h, (this.c[b(1)]).i, arrayOfInt[b(1)], -(this.c[b(1)]).d, (this.c[b(1)]).e, (this.c[b(2)]).h, (this.c[b(2)]).i, arrayOfInt[b(2)], -(this.c[b(2)]).d, (this.c[b(2)]).e, (this.c[b(3)]).h, (this.c[b(3)]).i, arrayOfInt[b(3)], -(this.c[b(3)]).d, (this.c[b(3)]).e); 
  }
  
  public final void a(int paramInt) {
    this.A = false;
    this.r = paramInt;
    int j = 0;
    int[] arrayOfInt = new int[4];
    int k = 0;
    this.F = this.G[b(paramInt)];
    if (this.F >= d.z)
      this.F = this.x; 
    if (this.F != 0) {
      k = 1;
      for (byte b2 = 0; b2 < this.F && b2 < d.z; b2++)
        k *= 2; 
    } else {
      k = 1;
    } 
    byte b1;
    for (b1 = 0; b1 < 4; b1++) {
      if (b1 == paramInt) {
        arrayOfInt[b(b1)] = this.F;
      } else {
        arrayOfInt[b(b1)] = -this.F;
        if (b1 == this.s || paramInt == this.s) {
          (this.c[b(b1)]).d = this.C * k * 2 + (this.c[b(b1)]).c;
        } else {
          (this.c[b(b1)]).d = (this.c[b(b1)]).c;
        } 
        (this.c[b(b1)]).e -= (this.c[b(b1)]).d;
        j += (this.c[b(b1)]).d;
      } 
    } 
    (this.c[b(paramInt)]).e += j;
    (this.c[b(paramInt)]).d = -j;
    if (this.j != 0 && !(this.a[b(paramInt)] instanceof com.pheephoo.mjgame.ui.a)) {
      System.out.println("playmode=" + this.j);
      (this.c[b(paramInt)]).h.e(this.I);
    } 
    if (this.k) {
      (this.c[b(this.B)]).i.b(this.I);
      if (this.a[b(paramInt)] instanceof com.pheephoo.mjgame.ui.a)
        (this.c[b(paramInt)]).h.e(this.I); 
    } 
    for (b1 = 0; b1 < 4; b1++)
      this.a[b1].a(paramInt, (this.c[b(0)]).h, (this.c[b(0)]).i, arrayOfInt[b(0)], -(this.c[b(0)]).d, (this.c[b(0)]).e, (this.c[b(1)]).h, (this.c[b(1)]).i, arrayOfInt[b(1)], -(this.c[b(1)]).d, (this.c[b(1)]).e, (this.c[b(2)]).h, (this.c[b(2)]).i, arrayOfInt[b(2)], -(this.c[b(2)]).d, (this.c[b(2)]).e, (this.c[b(3)]).h, (this.c[b(3)]).i, arrayOfInt[b(3)], -(this.c[b(3)]).d, (this.c[b(3)]).e); 
    this.s = paramInt;
  }
  
  private void a(int paramInt, boolean paramBoolean) {
    this.l = true;
    byte b1 = 1;
    if (paramBoolean)
      b1 = 2; 
    for (byte b2 = 0; b2 < 4; b2++) {
      if (b2 != paramInt)
        (this.c[b2]).c += this.D * b1; 
    } 
    (this.c[paramInt]).c -= 3 * this.D * b1;
  }
  
  private void l() {
    Enumeration enumeration;
    if ((enumeration = this.u.elements()).hasMoreElements()) {
      a a = (a)enumeration.nextElement();
      this.u.removeElementAt(0);
      while (a.a == this.E && !this.u.isEmpty()) {
        a = (a)(enumeration = this.u.elements()).nextElement();
        this.u.removeElementAt(0);
      } 
      if (this.E != a.a) {
        this.E = a.a;
        if (!this.h) {
          this.a[a.a].b((this.c[a.a]).l);
          return;
        } 
        Vector vector = new Vector();
        a a1 = (this.c[a.a]).l.firstElement();
        (this.c[a.a]).l.removeElementAt(0);
        vector.addElement(a1);
        this.a[a.a].b(vector);
        return;
      } 
      if (this.k) {
        a(m(), true);
        j();
        return;
      } 
      this.s = (this.s + 1) % 4;
      j();
      return;
    } 
    if (this.k) {
      j();
      return;
    } 
    this.s = (this.s + 1) % 4;
    j();
  }
  
  public final void a(int paramInt1, int paramInt2) {
    this.w = paramInt1;
    this.x = paramInt2;
  }
  
  private int m() {
    for (byte b1 = 0; b1 < 4; b1++) {
      if ((this.c[b1]).b == this.s)
        return b1; 
    } 
    return 0;
  }
  
  private int b(int paramInt) {
    for (byte b1 = 0; b1 < 4; b1++) {
      if ((this.c[b1]).b == paramInt)
        return b1; 
    } 
    return 0;
  }
  
  private i a(String paramString) {
    int j = Integer.parseInt(paramString.substring(0, 1));
    int k = Integer.parseInt(paramString.substring(2, 3));
    return new i(j, k);
  }
  
  private i c(int paramInt) {
    i i1 = null;
    if (paramInt == d.aF) {
      if (this.f - 1 < this.J.length) {
        i1 = a(this.J[this.f - 1]);
      } else {
        i1 = this.b.a();
      } 
    } else if (paramInt == d.aJ) {
      if (this.f - 1 < this.K.length) {
        i1 = a(this.K[this.f - 1]);
      } else {
        i1 = this.b.a();
      } 
    } 
    return i1;
  }
  
  public final void f(int paramInt) {
    this.e = paramInt;
  }
  
  private void n() {
    String[] arrayOfString1 = { 
        "0_1", "0_1", "3_2", "3_2", "0_3", "0_3", "0_4", "0_4", "1_5", "1_5", 
        "0_6", "0_6", "0_7" };
    this.c[0].a(arrayOfString1);
    this.a[0].a((this.c[0]).h, (this.c[0]).i);
    this.c[0].e();
    String[] arrayOfString2 = { 
        "0_1", "0_1", "3_2", "2_2", "0_3", "0_3", "0_4", "0_4", "1_5", "1_5", 
        "0_6", "0_6", "0_5" };
    String[] arrayOfString3 = { 
        "0_4", "0_4", "1_5", "1_5", "0_6", "0_6", "2_1", "2_1", "2_1", "2_3", 
        "2_3", "2_4", "2_5" };
    this.c[1].a(arrayOfString2);
    this.a[1].a((this.c[1]).h, (this.c[1]).i);
    this.c[2].a(arrayOfString3);
    this.a[2].a((this.c[2]).h, (this.c[2]).i);
    this.c[3].b();
    this.a[3].a((this.c[3]).h, (this.c[3]).i);
    for (byte b1 = 0; b1 < 1; b1++) {
      this.a[b1].a((this.c[(b1 + 1) % 4]).i, (this.c[(b1 + 1) % 4]).h.e() + 1, (this.c[(b1 + 1) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 2) % 4]).i, (this.c[(b1 + 2) % 4]).h.e() + 1, (this.c[(b1 + 2) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 3) % 4]).i, (this.c[(b1 + 3) % 4]).h.e() + 1, (this.c[(b1 + 3) % 4]).b, false, null);
    } 
  }
  
  private void o() {
    String[] arrayOfString = { 
        "0_1", "0_1", "0_1", "3_2", "3_2", "0_3", "0_3", "0_4", "0_4", "1_5", 
        "1_5", "0_6", "0_6" };
    this.c[0].a(arrayOfString);
    this.a[0].a((this.c[0]).h, (this.c[0]).i);
    this.c[0].e();
    this.c[1].b();
    this.a[1].a((this.c[1]).h, (this.c[1]).i);
    this.c[2].b();
    this.a[2].a((this.c[2]).h, (this.c[2]).i);
    this.c[3].b();
    this.a[3].a((this.c[3]).h, (this.c[3]).i);
    for (byte b1 = 0; b1 < 1; b1++) {
      this.a[b1].a((this.c[(b1 + 1) % 4]).i, (this.c[(b1 + 1) % 4]).h.e() + 1, (this.c[(b1 + 1) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 2) % 4]).i, (this.c[(b1 + 2) % 4]).h.e() + 1, (this.c[(b1 + 2) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 3) % 4]).i, (this.c[(b1 + 3) % 4]).h.e() + 1, (this.c[(b1 + 3) % 4]).b, false, null);
    } 
  }
  
  private void p() {
    String[] arrayOfString1 = { 
        "0_1", "0_1", "0_1", "0_1", "1_5", "1_5", "1_5", "2_7", "0_3", "0_3", 
        "4_2", "4_2", "2_7" };
    this.c[0].a(arrayOfString1);
    this.c[0].e();
    String[] arrayOfString2 = { 
        "3_1", "3_1", "0_2", "0_4", "2_3", "2_3", "2_3", "2_5", "2_6", "2_7", 
        "4_1", "4_1", "4_1" };
    this.c[1].b();
    this.c[2].a(arrayOfString2);
    this.c[3].b();
    this.a[0].a((this.c[0]).h, (this.c[0]).i);
    this.a[1].a((this.c[1]).h, (this.c[1]).i);
    this.a[2].a((this.c[2]).h, (this.c[2]).i);
    this.a[3].a((this.c[3]).h, (this.c[3]).i);
  }
  
  private void q() {
    String[] arrayOfString1 = { 
        "0_1", "0_1", "0_1", "0_1", "1_5", "1_5", "1_5", "2_7", "0_3", "0_3", 
        "0_3", "0_3", "2_8" };
    this.c[0].a(arrayOfString1);
    this.a[0].a((this.c[0]).h, (this.c[0]).i);
    this.c[0].e();
    String[] arrayOfString2 = { "3_1", "3_1", "0_2", "0_4", "2_3", "2_3", "2_3" };
    String[] arrayOfString3 = { "2_5_0_3", "2_6_0_3", "2_7_0_3", "4_1_1_2", "4_1_1_2", "4_1_1_2" };
    this.c[2].a(arrayOfString2, arrayOfString3);
    this.a[2].a((this.c[2]).h, (this.c[2]).i);
    this.c[1].b();
    this.a[1].a((this.c[1]).h, (this.c[1]).i);
    this.c[3].b();
    this.a[3].a((this.c[3]).h, (this.c[3]).i);
    for (byte b1 = 0; b1 < 1; b1++) {
      this.a[b1].a((this.c[(b1 + 1) % 4]).i, (this.c[(b1 + 1) % 4]).h.e() + 1, (this.c[(b1 + 1) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 2) % 4]).i, (this.c[(b1 + 2) % 4]).h.e() + 1, (this.c[(b1 + 2) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 3) % 4]).i, (this.c[(b1 + 3) % 4]).h.e() + 1, (this.c[(b1 + 3) % 4]).b, false, null);
    } 
  }
  
  private void r() {
    String[] arrayOfString1 = { 
        "0_1", "0_1", "0_1", "0_1", "1_5", "1_5", "1_5", "2_7", "0_3", "0_3", 
        "0,3", "0_3", "2_8" };
    String[] arrayOfString2 = { "3_1", "3_1", "0_2", "0_4", "2_3", "2_3", "2,3" };
    String[] arrayOfString3 = { "2_5_0_3", "2_6_0_3", "2_7_0_3", "4_1_1_2", "4_1_1_2", "4_1_1_2" };
    this.c[2].a(arrayOfString1);
    this.a[2].a((this.c[2]).h, (this.c[2]).i);
    this.c[0].a(arrayOfString2, arrayOfString3);
    this.a[0].a((this.c[0]).h, (this.c[0]).i);
    this.c[0].e();
    this.c[1].b();
    this.a[1].a((this.c[1]).h, (this.c[1]).i);
    this.c[3].b();
    this.a[3].a((this.c[3]).h, (this.c[3]).i);
    for (byte b1 = 0; b1 < 1; b1++) {
      this.a[b1].a((this.c[(b1 + 1) % 4]).i, (this.c[(b1 + 1) % 4]).h.e() + 1, (this.c[(b1 + 1) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 2) % 4]).i, (this.c[(b1 + 2) % 4]).h.e() + 1, (this.c[(b1 + 2) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 3) % 4]).i, (this.c[(b1 + 3) % 4]).h.e() + 1, (this.c[(b1 + 3) % 4]).b, false, null);
    } 
  }
  
  private void s() {
    String[] arrayOfString1 = { 
        "0_1", "0_1", "0_1", "1_5", "1_5", "1_5", "0_3", "0_3", "0,3", "3_1", 
        "3_1", "3_1", "3_2" };
    String[] arrayOfString2 = { 
        "0_1", "1_5", "0_3", "3_1", "2_1", "2_2", "2_3", "2_4", "2_5", "2_6", 
        "2_7", "2_8", "2_9" };
    this.c[0].a(arrayOfString1);
    this.c[0].e();
    this.c[1].a(arrayOfString2);
    this.c[2].b();
    this.c[3].b();
    this.a[0].a((this.c[0]).h, (this.c[0]).i);
    this.a[1].a((this.c[1]).h, (this.c[1]).i);
    this.a[2].a((this.c[2]).h, (this.c[2]).i);
    this.a[3].a((this.c[3]).h, (this.c[3]).i);
  }
  
  private void t() {
    String[] arrayOfString1 = { "1_1", "0_1", "0_1", "1_5", "1_5", "1_5", "1_5", "0_3", "0_3", "0_3" };
    String[] arrayOfString2 = { "1_2_0_2", "1_2_0_2", "1_2_0_2" };
    this.c[0].a(arrayOfString1, arrayOfString2);
    this.a[0].a((this.c[0]).h, (this.c[0]).i);
    this.c[0].e();
    this.c[1].b();
    this.a[1].a((this.c[1]).h, (this.c[1]).i);
    this.c[2].b();
    this.a[2].a((this.c[2]).h, (this.c[2]).i);
    this.c[3].b();
    this.a[3].a((this.c[3]).h, (this.c[3]).i);
    for (byte b1 = 0; b1 < 1; b1++) {
      this.a[b1].a((this.c[(b1 + 1) % 4]).i, (this.c[(b1 + 1) % 4]).h.e() + 1, (this.c[(b1 + 1) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 2) % 4]).i, (this.c[(b1 + 2) % 4]).h.e() + 1, (this.c[(b1 + 2) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 3) % 4]).i, (this.c[(b1 + 3) % 4]).h.e() + 1, (this.c[(b1 + 3) % 4]).b, false, null);
    } 
  }
  
  private void u() {
    String[] arrayOfString = { 
        "1_1", "0_1", "0_1", "1_5", "1_5", "1_5", "1_2", "0_3", "0_3", "0_3", 
        "1_2", "1,2", "1,2" };
    (new String[3])[0] = "1_2_0_2";
    (new String[3])[1] = "1_2_0_2";
    (new String[3])[2] = "1_2_0_2";
    Object object = null;
    this.c[0].a(arrayOfString);
    this.c[0].e();
    this.c[1].b();
    this.c[2].b();
    this.c[3].b();
    this.a[0].a((this.c[0]).h, (this.c[0]).i);
    this.a[1].a((this.c[1]).h, (this.c[1]).i);
    this.a[2].a((this.c[2]).h, (this.c[2]).i);
    this.a[3].a((this.c[3]).h, (this.c[3]).i);
  }
  
  private void v() {
    String[] arrayOfString1 = { "0_1", "0_1", "2_5", "2_5", "2_5", "2_5", "2_6", "2_7", "0_3", "0_3" };
    String[] arrayOfString2 = { "1_2_0_2", "1_2_0_2", "1_2_0_2" };
    this.c[0].a(arrayOfString1, arrayOfString2);
    this.a[0].a((this.c[0]).h, (this.c[0]).i);
    this.c[0].e();
    this.c[1].b();
    this.a[1].a((this.c[1]).h, (this.c[1]).i);
    this.c[2].b();
    this.a[2].a((this.c[2]).h, (this.c[2]).i);
    this.c[3].b();
    this.a[3].a((this.c[3]).h, (this.c[3]).i);
    for (byte b1 = 0; b1 < 1; b1++) {
      this.a[b1].a((this.c[(b1 + 1) % 4]).i, (this.c[(b1 + 1) % 4]).h.e() + 1, (this.c[(b1 + 1) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 2) % 4]).i, (this.c[(b1 + 2) % 4]).h.e() + 1, (this.c[(b1 + 2) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 3) % 4]).i, (this.c[(b1 + 3) % 4]).h.e() + 1, (this.c[(b1 + 3) % 4]).b, false, null);
    } 
  }
  
  private void w() {
    String[] arrayOfString1 = { "1_1", "0_1", "0_1", "1_5", "1_5", "1_5", "1_5", "0_3", "0_3", "0_3" };
    String[] arrayOfString2 = { "1_2_0_2", "1_2_0_2", "1_2_0_2" };
    this.c[0].a(arrayOfString1, arrayOfString2);
    this.a[0].a((this.c[0]).h, (this.c[0]).i);
    this.c[0].e();
    this.c[1].b();
    this.a[1].a((this.c[1]).h, (this.c[1]).i);
    this.c[2].b();
    this.a[2].a((this.c[2]).h, (this.c[2]).i);
    this.c[3].b();
    this.a[3].a((this.c[3]).h, (this.c[3]).i);
    for (byte b1 = 0; b1 < 1; b1++) {
      this.a[b1].a((this.c[(b1 + 1) % 4]).i, (this.c[(b1 + 1) % 4]).h.e() + 1, (this.c[(b1 + 1) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 2) % 4]).i, (this.c[(b1 + 2) % 4]).h.e() + 1, (this.c[(b1 + 2) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 3) % 4]).i, (this.c[(b1 + 3) % 4]).h.e() + 1, (this.c[(b1 + 3) % 4]).b, false, null);
    } 
  }
  
  private void x() {
    String[] arrayOfString1 = { "1_1", "0_1", "0_1", "1_5", "1_5", "1_5", "1_5", "0_3", "0_3", "0_3" };
    String[] arrayOfString2 = { "1_2_0_2", "1_2_0_2", "1_2_0_2" };
    this.c[1].a(arrayOfString1, arrayOfString2);
    this.a[1].a((this.c[1]).h, (this.c[1]).i);
    this.a[0].a((this.c[0]).h, (this.c[0]).i);
    this.c[0].b();
    this.c[2].b();
    this.a[2].a((this.c[2]).h, (this.c[2]).i);
    this.c[3].b();
    this.a[3].a((this.c[3]).h, (this.c[3]).i);
    for (byte b1 = 0; b1 < 1; b1++) {
      this.a[b1].a((this.c[(b1 + 1) % 4]).i, (this.c[(b1 + 1) % 4]).h.e() + 1, (this.c[(b1 + 1) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 2) % 4]).i, (this.c[(b1 + 2) % 4]).h.e() + 1, (this.c[(b1 + 2) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 3) % 4]).i, (this.c[(b1 + 3) % 4]).h.e() + 1, (this.c[(b1 + 3) % 4]).b, false, null);
    } 
  }
  
  private void y() {
    String[] arrayOfString1 = { "1_1", "1_2", "0_1", "0_1", "1_5", "1_5", "1_5", "0_3", "0_3", "0_3" };
    String[] arrayOfString2 = { "1_2_0_2", "1_2_0_2", "1_2_0_2" };
    String[] arrayOfString3 = { "1_3", "1_1", "2_2", "2_3", "2_4", "2_5", "2_5", "2_7", "2_8", "2_9" };
    String[] arrayOfString4 = { "1_3_0_2", "1_3_0_2", "1_3_0_2" };
    this.c[0].a(arrayOfString1, arrayOfString2);
    this.a[0].a((this.c[0]).h, (this.c[0]).i);
    this.c[1].a(arrayOfString3, arrayOfString4);
    this.a[1].a((this.c[1]).h, (this.c[1]).i);
    this.c[2].b();
    this.a[2].a((this.c[2]).h, (this.c[2]).i);
    this.c[3].b();
    this.a[3].a((this.c[3]).h, (this.c[3]).i);
    for (byte b1 = 0; b1 < 1; b1++) {
      this.a[b1].a((this.c[(b1 + 1) % 4]).i, (this.c[(b1 + 1) % 4]).h.e() + 1, (this.c[(b1 + 1) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 2) % 4]).i, (this.c[(b1 + 2) % 4]).h.e() + 1, (this.c[(b1 + 2) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 3) % 4]).i, (this.c[(b1 + 3) % 4]).h.e() + 1, (this.c[(b1 + 3) % 4]).b, false, null);
    } 
  }
  
  private void z() {
    String[] arrayOfString1 = { "1_1", "1_2", "0_1", "0_1", "1_5", "1_5", "1_5", "0_3", "0_3", "0_3" };
    String[] arrayOfString2 = { "1_2_0_2", "1_2_0_2", "1_2_0_2" };
    String[] arrayOfString3 = { "1_3", "1_1", "2_2", "2_3", "2_4", "2_5", "2_5", "2_7", "2_8", "2_9" };
    String[] arrayOfString4 = { "1_3_0_2", "1_3_0_2", "1_3_0_2" };
    this.c[0].a(arrayOfString3, arrayOfString4);
    this.a[0].a((this.c[0]).h, (this.c[0]).i);
    this.c[1].a(arrayOfString1, arrayOfString2);
    this.a[1].a((this.c[1]).h, (this.c[1]).i);
    this.c[2].b();
    this.a[2].a((this.c[2]).h, (this.c[2]).i);
    this.c[3].b();
    this.a[3].a((this.c[3]).h, (this.c[3]).i);
    for (byte b1 = 0; b1 < 1; b1++) {
      this.a[b1].a((this.c[(b1 + 1) % 4]).i, (this.c[(b1 + 1) % 4]).h.e() + 1, (this.c[(b1 + 1) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 2) % 4]).i, (this.c[(b1 + 2) % 4]).h.e() + 1, (this.c[(b1 + 2) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 3) % 4]).i, (this.c[(b1 + 3) % 4]).h.e() + 1, (this.c[(b1 + 3) % 4]).b, false, null);
    } 
  }
  
  private void A() {
    String[] arrayOfString1 = { "1_1", "0_1", "0_1", "1_5", "1_5", "1_5", "1_5", "0_3", "0_3", "0,3" };
    String[] arrayOfString2 = { "1_2_0_2", "1_2_0_2", "1_2_0_2" };
    this.c[0].a(arrayOfString1, arrayOfString2);
    this.a[0].a((this.c[0]).h, (this.c[0]).i);
    this.c[0].e();
    this.c[1].b();
    this.a[1].a((this.c[1]).h, (this.c[1]).i);
    this.c[2].b();
    this.a[2].a((this.c[2]).h, (this.c[2]).i);
    this.c[3].b();
    this.a[3].a((this.c[3]).h, (this.c[3]).i);
    for (byte b1 = 0; b1 < 1; b1++) {
      this.a[b1].a((this.c[(b1 + 1) % 4]).i, (this.c[(b1 + 1) % 4]).h.e() + 1, (this.c[(b1 + 1) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 2) % 4]).i, (this.c[(b1 + 2) % 4]).h.e() + 1, (this.c[(b1 + 2) % 4]).b, false, null);
      this.a[b1].a((this.c[(b1 + 3) % 4]).i, (this.c[(b1 + 3) % 4]).h.e() + 1, (this.c[(b1 + 3) % 4]).b, false, null);
    } 
  }
  
  private void B() {
    String[] arrayOfString1 = { "2_3", "2_3", "4_2", "4_2" };
    String[] arrayOfString2 = { 
        "1_9_0_2", "1_9_0_2", "1_9_0_2", "0_4_1_2", "0_4_1_2", "0_4_1_2", "0_4_1_2", "1_5_2_2", "1_5_2_2", "1_5_2_2", 
        "1_5_2_2" };
    String[] arrayOfString3 = { "2_4", "2_5", "1_3", "1_7", "3_1", "3_1", "3_1", "2_8", "2_8", "2_8" };
    String[] arrayOfString4 = { "0_3_0_2", "0_3_0_2", "0_3_0_2" };
    this.c[0].a(arrayOfString1, arrayOfString2);
    this.a[0].a((this.c[0]).h, (this.c[0]).i);
    this.c[3].a(arrayOfString3, arrayOfString4);
    this.a[3].a((this.c[3]).h, (this.c[3]).i);
    this.c[2].d();
    this.a[2].a((this.c[2]).h, (this.c[2]).i);
    this.c[1].a(arrayOfString3, arrayOfString4);
    this.a[1].a((this.c[1]).h, (this.c[1]).i);
  }
  
  public final void g() {
    this.c[0] = null;
    this.c[1] = null;
    this.c[2] = null;
    this.c[3] = null;
    this.a[0].e();
    this.a[0] = null;
    this.a[1].e();
    this.a[1] = null;
    this.a[2].e();
    this.a[2] = null;
    this.a[3].e();
    this.a[3] = null;
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\engine\t.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */