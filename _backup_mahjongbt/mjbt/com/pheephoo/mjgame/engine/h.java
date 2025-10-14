package com.pheephoo.mjgame.engine;

import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.b;
import com.pheephoo.mjgame.d;
import com.pheephoo.mjgame.messaging.a;
import com.pheephoo.mjgame.messaging.b;
import com.pheephoo.mjgame.network.b;
import com.pheephoo.mjgame.network.c;
import com.pheephoo.mjgame.network.d;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.microedition.lcdui.Displayable;

public final class h implements s, Runnable {
  private boolean k = false;
  
  private boolean l = false;
  
  public boolean a = false;
  
  private boolean m = false;
  
  public d b;
  
  private MJGame n;
  
  private c o;
  
  private p p = new p();
  
  private com/pheephoo/mjgame/engine/g q;
  
  private com.pheephoo.mjgame.ui.h r;
  
  private com.pheephoo.mjgame.ui.g s;
  
  private int t = 0;
  
  l c = new l();
  
  boolean d = false;
  
  private Timer u = new Timer();
  
  private TimerTask v = new com/pheephoo/mjgame/engine/m(this);
  
  private Timer w = new Timer();
  
  private TimerTask x = new com/pheephoo/mjgame/engine/o(this);
  
  int e = 0;
  
  private long y = 0L;
  
  private String z = "";
  
  private boolean A = false;
  
  private boolean B = false;
  
  boolean f = false;
  
  private q C;
  
  public q[] g = new q[4];
  
  private int D;
  
  private int E = 0;
  
  private Vector F = new Vector();
  
  private i G = null;
  
  private int H = -1;
  
  private int I = 0;
  
  private int J;
  
  private int K;
  
  public String h;
  
  public String i;
  
  public String j;
  
  public h(d paramd, c paramc, int paramInt1, int paramInt2, MJGame paramMJGame) {
    this.o = paramc;
    this.b = paramd;
    this.J = paramInt1;
    this.n = paramMJGame;
    this.K = paramInt2;
    this.b.h = this;
    this.q = new com/pheephoo/mjgame/engine/g(this);
    this.q.start();
  }
  
  public final synchronized void run() {
    this.u.scheduleAtFixedRate(this.v, 0L, d.A);
    this.w.scheduleAtFixedRate(this.x, 0L, d.C);
    c();
  }
  
  private void s() {
    if (!this.l)
      this.y = System.currentTimeMillis(); 
  }
  
  final boolean a() {
    long l1;
    if (this.l) {
      l1 = 40000L;
    } else {
      l1 = 20000L;
    } 
    return (System.currentTimeMillis() - this.y > l1);
  }
  
  public final void b() {
    if (this.u != null)
      this.u.cancel(); 
    if (this.w != null)
      this.w.cancel(); 
    com.pheephoo.mjgame.ui.n.d();
    this.d = true;
    if (this.l) {
      this.n.a(d.ay);
      return;
    } 
    this.n.a(d.aR);
  }
  
  public final void c() {
    s();
    this.m = false;
    h();
    if (this.t == 1) {
      this.c.a = d.bC;
    } else {
      this.c.a = d.bt;
    } 
    this.c.b = new Object[] { new Integer(this.J), new Integer(d.a), new Integer(this.K) };
    t();
  }
  
  public final synchronized void a(Object[] paramArrayOfObject) {
    this.f = false;
    if (this.d)
      return; 
    s();
    int j;
    if ((j = ((Integer)paramArrayOfObject[0]).intValue()) == d.br)
      return; 
    int[] arrayOfInt = (int[])paramArrayOfObject[1];
    String[] arrayOfString = (String[])paramArrayOfObject[2];
    if (j == d.bp) {
      this.l = false;
      if (paramArrayOfObject != null) {
        this.z = arrayOfString[0];
        this.h = arrayOfString[1];
        if (this.t == 1) {
          k.e = this.h;
          k();
          return;
        } 
        j();
        return;
      } 
    } else if (j == d.bV) {
      this.J = arrayOfInt[0];
      this.K = arrayOfInt[1];
      com.pheephoo.mjgame.ui.n.d();
      if (this.t == 2) {
        this.r.b();
        return;
      } 
    } else if (j == d.bW) {
      this.l = false;
      if (this.t == 2) {
        this.r.d();
        return;
      } 
    } else if (j == d.bX) {
      this.h = arrayOfString[0];
      this.i = arrayOfString[1];
      if (this.t == 2) {
        this.r.c();
        return;
      } 
    } else if (j == d.bY) {
      if (this.t == 2) {
        this.r.e();
        return;
      } 
    } else {
      if (j == d.bB) {
        this.m = true;
        m(paramArrayOfObject);
        this.k = true;
        return;
      } 
      if (j == d.bz) {
        h(paramArrayOfObject);
        return;
      } 
      if (j == d.by) {
        com.pheephoo.mjgame.ui.n.d();
        Vector vector = l(paramArrayOfObject);
        this.n.a().setCurrent((Displayable)this.r);
        this.r.a(vector);
        return;
      } 
      if (j == d.bA) {
        k(paramArrayOfObject);
        return;
      } 
      if (j == d.bo) {
        if (this.t == 1) {
          this.s.a(arrayOfInt[0]);
          return;
        } 
        this.r.a(arrayOfInt[0]);
        return;
      } 
      if (j == d.bR) {
        this.o.b(arrayOfString[0]);
        return;
      } 
      if (j == d.bq) {
        this.A = true;
        this.B = false;
        b(paramArrayOfObject);
        return;
      } 
      if (j == d.bk) {
        this.o.a(arrayOfInt[0], 0, arrayOfInt[1], arrayOfString[0]);
        return;
      } 
      if (j == d.bl) {
        g(paramArrayOfObject);
        return;
      } 
      if (j == d.bm) {
        e(paramArrayOfObject);
        return;
      } 
      if (j == d.bn) {
        f(paramArrayOfObject);
        return;
      } 
      if (j == d.aZ) {
        this.G = new i(arrayOfInt[0], arrayOfInt[1]);
        this.H = arrayOfInt[3];
        this.I = arrayOfInt[2];
        if (this.H != this.C.b) {
          (this.g[g(this.H)]).h.e(this.G);
          this.G = null;
          return;
        } 
      } else {
        if (j == d.bb) {
          v();
          this.A = false;
          if (this.H == this.C.b) {
            if (this.G != null)
              this.o.a(this.G, this.I, this.H); 
            this.o.a();
          } else {
            (this.g[g(this.H)]).h.e(this.G);
          } 
          this.G = null;
          return;
        } 
        if (j == d.ba) {
          v();
          i i1 = new i(arrayOfInt[0], arrayOfInt[1]);
          int k;
          if ((k = arrayOfInt[2]) != this.C.b)
            (this.g[g(k)]).h.b(i1); 
          this.o.b(i1, arrayOfInt[2], arrayOfInt[3]);
          return;
        } 
        if (j == d.bg) {
          this.o.b();
          return;
        } 
        if (j == d.bf) {
          d(paramArrayOfObject);
          return;
        } 
        if (j == d.bh) {
          this.o.a(arrayOfString[0]);
          return;
        } 
        if (j == d.bM) {
          o(paramArrayOfObject);
          return;
        } 
        if (j == d.bN) {
          n(paramArrayOfObject);
          return;
        } 
        if (j == d.bO) {
          this.m = true;
          m(paramArrayOfObject);
          this.k = true;
          return;
        } 
        if (j == d.bK) {
          com.pheephoo.mjgame.ui.n.d();
          Vector vector = i(paramArrayOfObject);
          if (this.m) {
            this.n.a().setCurrent((Displayable)this.s);
            this.s.a(vector);
            return;
          } 
          this.s.a(vector);
          this.n.d = (Displayable)this.s;
          this.s.a(this.z, this.h);
          this.k = true;
          return;
        } 
        if (j == d.bL) {
          j(paramArrayOfObject);
          return;
        } 
        if (j == d.bP) {
          int k = arrayOfInt[0];
          int m = arrayOfInt[1];
          String str = arrayOfString[0];
          this.s.a(k, m, str);
          return;
        } 
        if (j == d.bc) {
          v();
          Vector vector = new Vector();
          for (byte b = 0; b < arrayOfInt.length; b += 4) {
            a a;
            if (arrayOfInt[b + 3] == 1) {
              a = new a(arrayOfInt[b], arrayOfInt[b + 1], arrayOfInt[b + 2], true);
            } else {
              a = new a(arrayOfInt[b], arrayOfInt[b + 1], arrayOfInt[b + 2], false);
            } 
            vector.addElement(a);
          } 
          if (this.G != null)
            this.o.a(this.G, this.I, this.C.b); 
          this.G = null;
          this.o.a(vector);
          return;
        } 
        if (j == d.bd) {
          v();
          Vector vector = new Vector();
          for (byte b = 0; b < arrayOfInt.length; b += 4) {
            a a;
            if (arrayOfInt[b + 3] == 1) {
              a = new a(arrayOfInt[b], arrayOfInt[b + 1], arrayOfInt[b + 2], true);
            } else {
              a = new a(arrayOfInt[b], arrayOfInt[b + 1], arrayOfInt[b + 2], false);
            } 
            vector.addElement(a);
          } 
          this.o.b(vector);
          return;
        } 
        if (j == d.be) {
          c(paramArrayOfObject);
          return;
        } 
        if (j == d.bT) {
          com.pheephoo.mjgame.ui.n.d();
          this.K = arrayOfInt[1];
          this.j = arrayOfString[0];
          if (this.t == 1) {
            this.s.c(arrayOfInt[0]);
            return;
          } 
          this.r.d(arrayOfInt[0]);
          return;
        } 
        if (j == d.bS) {
          com.pheephoo.mjgame.ui.n.d();
          this.n.a(d.az, arrayOfString[0]);
        } 
      } 
    } 
  }
  
  public final void a(i parami) {
    if (this.d)
      return; 
    h();
    this.c.a = d.aU;
    int j = b(parami);
    boolean bool = false;
    Object[] arrayOfObject;
    (arrayOfObject = new Object[3])[0] = new Integer(parami.g);
    arrayOfObject[1] = new Integer(parami.h);
    arrayOfObject[2] = new Integer(j);
    this.c.b = arrayOfObject;
    t();
  }
  
  public final void a(int paramInt1, boolean paramBoolean, int paramInt2) {
    int j;
    int[] arrayOfInt1 = new int[(j = this.C.i.e()) * 4];
    int k;
    int[] arrayOfInt2 = new int[(k = this.C.h.e()) * 2];
    byte b1 = 0;
    byte b2;
    for (b2 = 0; b2 < j; b2++) {
      i i1 = this.C.i.a(b2);
      arrayOfInt1[b1++] = i1.g;
      arrayOfInt1[b1++] = i1.h;
      arrayOfInt1[b1++] = i1.i;
      arrayOfInt1[b1++] = i1.j;
    } 
    b1 = 0;
    for (b2 = 0; b2 < k; b2++) {
      i i1 = this.C.h.a(b2);
      arrayOfInt2[b1++] = i1.g;
      arrayOfInt2[b1++] = i1.h;
    } 
    h();
    this.c.a = d.aV;
    b2 = 0;
    if (paramBoolean)
      b2 = 1; 
    this.c.b = new Object[] { new Integer(j * 4), arrayOfInt1, new Integer(this.C.h.e() * 2), arrayOfInt2, new Integer(paramInt1), new Integer(b2), new Integer(paramInt2) };
    t();
  }
  
  public final void a(boolean paramBoolean, i parami) {
    int j;
    int[] arrayOfInt1 = new int[(j = this.C.i.e()) * 4];
    int k;
    int[] arrayOfInt2 = new int[(k = this.C.h.e()) * 2];
    byte b1 = 0;
    byte b2;
    for (b2 = 0; b2 < j; b2++) {
      i i1 = this.C.i.a(b2);
      arrayOfInt1[b1++] = i1.g;
      arrayOfInt1[b1++] = i1.h;
      arrayOfInt1[b1++] = i1.i;
      arrayOfInt1[b1++] = i1.j;
    } 
    b1 = 0;
    for (b2 = 0; b2 < k; b2++) {
      i i1 = this.C.h.a(b2);
      arrayOfInt2[b1++] = i1.g;
      arrayOfInt2[b1++] = i1.h;
    } 
    h();
    b2 = 0;
    if (paramBoolean)
      b2 = 1; 
    this.c.a = d.aW;
    this.c.b = new Object[] { new Integer(j * 4), arrayOfInt1, new Integer(k * 2), arrayOfInt2, new Integer(b2), new Integer(parami.g), new Integer(parami.h) };
    t();
  }
  
  public final void a(int paramInt) {
    h();
    this.c.a = d.aX;
    this.c.b = new Object[] { new Integer(paramInt) };
    t();
  }
  
  private void b(Object[] paramArrayOfObject) {
    int[] arrayOfInt = (int[])paramArrayOfObject[1];
    q q1 = null;
    boolean bool1 = false;
    boolean bool2 = false;
    int j = arrayOfInt[1];
    this.D = arrayOfInt[2];
    this.C = new q(j);
    this.C.h = new e(e.c);
    this.C.h.a(arrayOfInt, 5, 31);
    this.C.i = new e(e.b);
    this.g[0] = this.C;
    (q1 = new q(arrayOfInt[31])).h = new e(e.c);
    q1.h.a(arrayOfInt, 32, 58);
    q1.i = new e(e.b);
    this.g[1] = q1;
    (q1 = new q(arrayOfInt[58])).h = new e(e.c);
    q1.h.a(arrayOfInt, 59, 85);
    q1.i = new e(e.b);
    this.g[2] = q1;
    (q1 = new q(arrayOfInt[85])).h = new e(e.c);
    q1.h.a(arrayOfInt, 86, 112);
    q1.i = new e(e.b);
    this.g[3] = q1;
    if (this.t == 1) {
      this.s.c();
    } else {
      this.r.a();
    } 
    this.o.a(0, j, 1, this, this.C, 0);
    this.o.a(this.C.h, this.C.i);
    s();
  }
  
  private void c(Object[] paramArrayOfObject) {
    int[] arrayOfInt = (int[])paramArrayOfObject[1];
    e e = new e(e.b);
    byte b1 = 1;
    int j = arrayOfInt[0];
    while (b1 < j) {
      i i3 = new i(arrayOfInt[b1], arrayOfInt[b1 + 1], arrayOfInt[b1 + 2], arrayOfInt[b1 + 3]);
      e.f(i3);
      b1 += 4;
    } 
    int k = arrayOfInt[b1];
    int m = arrayOfInt[++b1];
    int n = g(m);
    b1++;
    boolean bool = false;
    if (arrayOfInt[b1] == 1)
      bool = true; 
    b1++;
    i i1 = null;
    if (arrayOfInt[b1] != -1)
      i1 = new i(arrayOfInt[b1], arrayOfInt[b1 + 1]); 
    b1 += 2;
    int i2 = arrayOfInt[b1++];
    (this.g[n]).h = new e(14);
    for (byte b2 = 0; b2 < i2; b2++) {
      i1 = new i(arrayOfInt[b1], arrayOfInt[b1 + 1]);
      (this.g[n]).h.f(i1);
      b1 += 2;
    } 
    (this.g[n]).i = e;
    this.o.a(e, k, m, bool, i1);
  }
  
  private void d(Object[] paramArrayOfObject) {
    int[] arrayOfInt = (int[])paramArrayOfObject[1];
    byte b = 0;
    b++;
    int j = arrayOfInt[0];
    e e1 = new e(14);
    e e2 = new e(16);
    b++;
    int k = arrayOfInt[1];
    int m;
    for (m = 0; m < k; m++) {
      i i18 = new i(arrayOfInt[b], arrayOfInt[b + 1], arrayOfInt[b + 2], arrayOfInt[b + 3]);
      e1.f(i18);
      b += 4;
    } 
    m = arrayOfInt[b++];
    int n;
    for (n = 0; n < m; n++) {
      i i18 = new i(arrayOfInt[b], arrayOfInt[b + 1], arrayOfInt[b + 2], arrayOfInt[b + 3]);
      e2.f(i18);
      b += 4;
    } 
    n = arrayOfInt[b++];
    int i1 = arrayOfInt[b++];
    int i2 = arrayOfInt[b++];
    e e3 = new e(e.c);
    e e4 = new e(e.b);
    int i3 = arrayOfInt[b++];
    int i4;
    for (i4 = 0; i4 < i3; i4++) {
      i i18 = new i(arrayOfInt[b], arrayOfInt[b + 1], arrayOfInt[b + 2], arrayOfInt[b + 3]);
      e3.f(i18);
      b += 4;
    } 
    i4 = arrayOfInt[b++];
    int i5;
    for (i5 = 0; i5 < i4; i5++) {
      i i18 = new i(arrayOfInt[b], arrayOfInt[b + 1], arrayOfInt[b + 2], arrayOfInt[b + 3]);
      e4.f(i18);
      b += 4;
    } 
    i5 = arrayOfInt[b++];
    int i6 = arrayOfInt[b++];
    int i7 = arrayOfInt[b++];
    e e5 = new e(e.c);
    e e6 = new e(e.b);
    int i8 = arrayOfInt[b++];
    int i9;
    for (i9 = 0; i9 < i8; i9++) {
      i i18 = new i(arrayOfInt[b], arrayOfInt[b + 1], arrayOfInt[b + 2], arrayOfInt[b + 3]);
      e5.f(i18);
      b += 4;
    } 
    i9 = arrayOfInt[b++];
    int i10;
    for (i10 = 0; i10 < i9; i10++) {
      i i18 = new i(arrayOfInt[b], arrayOfInt[b + 1], arrayOfInt[b + 2], arrayOfInt[b + 3]);
      e6.f(i18);
      b += 4;
    } 
    i10 = arrayOfInt[b++];
    int i11 = arrayOfInt[b++];
    int i12 = arrayOfInt[b++];
    e e7 = new e(e.c);
    e e8 = new e(e.b);
    int i13 = arrayOfInt[b++];
    int i14;
    for (i14 = 0; i14 < i13; i14++) {
      i i18 = new i(arrayOfInt[b], arrayOfInt[b + 1], arrayOfInt[b + 2], arrayOfInt[b + 3]);
      e7.f(i18);
      b += 4;
    } 
    i14 = arrayOfInt[b++];
    int i15;
    for (i15 = 0; i15 < i14; i15++) {
      i i18 = new i(arrayOfInt[b], arrayOfInt[b + 1], arrayOfInt[b + 2], arrayOfInt[b + 3]);
      e8.f(i18);
      b += 4;
    } 
    i15 = arrayOfInt[b++];
    int i16 = arrayOfInt[b++];
    int i17 = arrayOfInt[b];
    this.o.a(j, e1, e2, n, i1, i2, e3, e4, i5, i6, i7, e5, e6, i10, i11, i12, e7, e8, i15, i16, i17);
  }
  
  private void e(Object[] paramArrayOfObject) {
    int[] arrayOfInt = (int[])paramArrayOfObject[1];
    String[] arrayOfString = (String[])paramArrayOfObject[2];
    this.o.a(arrayOfInt[0], arrayOfInt[1], arrayOfString[0]);
  }
  
  private void f(Object[] paramArrayOfObject) {
    int[] arrayOfInt = (int[])paramArrayOfObject[1];
    String[] arrayOfString = (String[])paramArrayOfObject[2];
    this.o.a(arrayOfInt[0], arrayOfInt[1], arrayOfString[0], arrayOfString[1]);
  }
  
  private void g(Object[] paramArrayOfObject) {
    int[] arrayOfInt = (int[])paramArrayOfObject[1];
    String[] arrayOfString = (String[])paramArrayOfObject[2];
    byte b1 = 0;
    String str1 = arrayOfString[0];
    String str2 = arrayOfString[1];
    String str3 = arrayOfString[2];
    b1++;
    this.D = arrayOfInt[0];
    b1++;
    b1++;
    e e = new e(e.d);
    b1++;
    int n = arrayOfInt[3];
    byte b2;
    for (b2 = 0; b2 < n; b2++) {
      int i2 = arrayOfInt[b1++];
      int i3 = arrayOfInt[b1++];
      i i1 = new i(i2, i3);
      e.f(i1);
    } 
    int j = arrayOfInt[b1++];
    int m = arrayOfInt[b1++];
    int k = arrayOfInt[b1++];
    this.C = new q(j);
    this.C.e = m;
    this.C.h = new e(e.c);
    this.C.i = new e(e.b);
    this.g[0] = this.C;
    for (b2 = 0; b2 < k; b2++) {
      int i2 = arrayOfInt[b1++];
      int i3 = arrayOfInt[b1++];
      i i1 = new i(i2, i3);
      this.C.h.f(i1);
    } 
    k = arrayOfInt[b1++];
    for (b2 = 0; b2 < k; b2++) {
      int i2 = arrayOfInt[b1++];
      int i3 = arrayOfInt[b1++];
      int i4 = arrayOfInt[b1++];
      int i5 = arrayOfInt[b1++];
      i i1 = new i(i2, i3, i4, i5);
      this.C.i.f(i1);
    } 
    for (b2 = 1; b2 < 4; b2++) {
      int i1 = arrayOfInt[b1++];
      j = arrayOfInt[b1++];
      m = arrayOfInt[b1++];
      k = arrayOfInt[b1++];
      q q1;
      (q1 = new q(j)).h = new e(e.c);
      q1.i = new e(e.b);
      q1.e = m;
      q1.f = i1;
      byte b;
      for (b = 0; b < k; b++) {
        int i3 = arrayOfInt[b1++];
        int i4 = arrayOfInt[b1++];
        i i2 = new i(i3, i4);
        q1.h.f(i2);
      } 
      k = arrayOfInt[b1++];
      for (b = 0; b < k; b++) {
        int i3 = arrayOfInt[b1++];
        int i4 = arrayOfInt[b1++];
        int i5 = arrayOfInt[b1++];
        int i6 = arrayOfInt[b1++];
        i i2 = new i(i3, i4, i5, i6);
        q1.i.f(i2);
      } 
      this.g[b2] = q1;
    } 
    this.o.a(0, this.C.b, 1, this, this.C, 1);
    if (this.t == 1) {
      this.s.c();
    } else {
      this.r.a();
    } 
    this.o.a((this.g[0]).b, (this.g[0]).h, (this.g[0]).i, (this.g[1]).b, (this.g[1]).f, (this.g[1]).e, str1, (this.g[1]).h.e(), (this.g[1]).i, (this.g[2]).b, (this.g[2]).f, (this.g[2]).e, str2, (this.g[2]).h.e(), (this.g[2]).i, (this.g[3]).b, (this.g[3]).f, (this.g[3]).e, str3, (this.g[3]).h.e(), (this.g[3]).i, e);
  }
  
  public final void d() {
    this.B = true;
  }
  
  public final int e() {
    return this.D;
  }
  
  public final void f() {
    this.B = false;
    if (this.A)
      return; 
    com.pheephoo.mjgame.ui.n.c();
    this.n.a().setCurrent((Displayable)com.pheephoo.mjgame.ui.n.a);
    h();
    this.c.a = d.aY;
    this.c.b = null;
    t();
  }
  
  public final void a(com.pheephoo.mjgame.ui.h paramh) {
    this.t = 2;
    this.r = paramh;
    this.r.a = this;
  }
  
  public final void a(com.pheephoo.mjgame.ui.g paramg) {
    this.t = 1;
    this.s = paramg;
    this.s.n = this;
  }
  
  public final void g() {
    if (this.d)
      return; 
    if (this.t == 2 && this.r.c != 2) {
      com.pheephoo.mjgame.ui.n.c();
      this.n.a().setCurrent((Displayable)com.pheephoo.mjgame.ui.n.a);
    } 
    h();
    this.f = false;
    this.c.a = d.bw;
    this.c.b = null;
    t();
  }
  
  public final void h() {
    while (this.f) {
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException interruptedException) {}
    } 
  }
  
  public final void i() {
    com.pheephoo.mjgame.ui.n.c();
    this.n.a().setCurrent((Displayable)com.pheephoo.mjgame.ui.n.a);
    this.c.a = d.bj;
    this.c.b = null;
    t();
  }
  
  public final void b(int paramInt) {
    h();
    com.pheephoo.mjgame.ui.n.c();
    this.n.a().setCurrent((Displayable)com.pheephoo.mjgame.ui.n.a);
    this.c.a = d.bu;
    this.c.b = new Object[] { new Integer(paramInt) };
    t();
  }
  
  public final void a(int paramInt1, int paramInt2, int paramInt3, String paramString) {
    com.pheephoo.mjgame.ui.n.c();
    this.n.a().setCurrent((Displayable)com.pheephoo.mjgame.ui.n.a);
    this.c.a = d.bv;
    this.c.b = new Object[] { new Integer(paramInt1), new Integer(paramInt2), new Integer(paramInt3), paramString };
    t();
  }
  
  public final void a(int paramInt1, int paramInt2, String paramString) {
    com.pheephoo.mjgame.ui.n.c();
    this.n.a().setCurrent((Displayable)com.pheephoo.mjgame.ui.n.a);
    this.c.a = d.bi;
    this.c.b = new Object[] { new Integer(paramInt1), new Integer(paramInt2), paramString };
    t();
  }
  
  public final void j() {
    this.c.a = d.bx;
    this.c.b = null;
    t();
  }
  
  private void h(Object[] paramArrayOfObject) {
    String[] arrayOfString = (String[])paramArrayOfObject[2];
    this.r.a(arrayOfString);
    this.r.a(this.z);
  }
  
  public final void c(int paramInt) {
    this.c.a = d.bG;
    this.c.b = new Object[] { new Integer(paramInt) };
    t();
  }
  
  public final void a(int paramInt1, int paramInt2, int paramInt3, Vector paramVector, String paramString) {
    com.pheephoo.mjgame.ui.n.c();
    this.n.a().setCurrent((Displayable)com.pheephoo.mjgame.ui.n.a);
    this.c.a = d.bD;
    int j = paramVector.size();
    int[] arrayOfInt = new int[3];
    for (byte b = 0; b < j; b++)
      arrayOfInt[b] = Integer.parseInt((String)paramVector.elementAt(b)); 
    this.c.b = new Object[] { new Integer(paramInt1), new Integer(paramInt2), new Integer(paramInt3), new Integer(arrayOfInt[0]), new Integer(arrayOfInt[1]), new Integer(arrayOfInt[2]), paramString };
    t();
  }
  
  private Vector i(Object[] paramArrayOfObject) {
    Vector vector = new Vector();
    int[] arrayOfInt = (int[])paramArrayOfObject[1];
    String[] arrayOfString = (String[])paramArrayOfObject[2];
    byte b1 = 0;
    byte b2 = 0;
    if (arrayOfInt != null)
      while (b2 < arrayOfString.length) {
        vector.addElement(new c(arrayOfInt[b1], arrayOfInt[b1 + 1], arrayOfString[b2]));
        b1 += 2;
        b2++;
      }  
    return vector;
  }
  
  private void j(Object[] paramArrayOfObject) {
    int[] arrayOfInt = (int[])paramArrayOfObject[1];
    this.s.b(arrayOfInt[0], arrayOfInt[1]);
  }
  
  private void k(Object[] paramArrayOfObject) {
    int[] arrayOfInt = (int[])paramArrayOfObject[1];
    if (this.t == 1) {
      this.s.a(new b(arrayOfInt[2], arrayOfInt[3], arrayOfInt[4], arrayOfInt[1], arrayOfInt[5]));
      return;
    } 
    if (arrayOfInt[0] == 1) {
      this.r.a(new b(arrayOfInt[2], arrayOfInt[3], arrayOfInt[4], arrayOfInt[1], arrayOfInt[5]));
      return;
    } 
    if (arrayOfInt[0] == 2) {
      this.r.b(new b(arrayOfInt[2], arrayOfInt[3], arrayOfInt[4], arrayOfInt[1], arrayOfInt[5]));
      return;
    } 
    this.r.c(arrayOfInt[1]);
  }
  
  private Vector l(Object[] paramArrayOfObject) {
    Vector vector = new Vector();
    int[] arrayOfInt;
    if ((arrayOfInt = (int[])paramArrayOfObject[1])[0] == 1) {
      this.a = true;
      System.out.println("isCompetition room");
    } 
    for (byte b = 1; b < arrayOfInt.length; b += 5)
      vector.addElement(new b(arrayOfInt[b + 1], arrayOfInt[b + 2], arrayOfInt[b + 3], arrayOfInt[b], arrayOfInt[b + 4])); 
    return vector;
  }
  
  private void m(Object[] paramArrayOfObject) {
    int[] arrayOfInt = (int[])paramArrayOfObject[1];
    String[] arrayOfString = (String[])paramArrayOfObject[2];
    if (this.t == 1) {
      if (arrayOfInt[0] == 30) {
        this.s.a(arrayOfInt[0], 0, "", "", arrayOfInt[1]);
      } else {
        this.s.a(arrayOfInt[0], arrayOfInt[1], arrayOfString[0], arrayOfString[1], 0);
      } 
      if (!this.k) {
        this.s.a(this.z, this.h);
        return;
      } 
      this.n.a().setCurrent(this.n.d);
      return;
    } 
    this.r.a(this.z);
    this.r.a(arrayOfInt[0], arrayOfInt[1]);
  }
  
  public final void d(int paramInt) {
    this.c.a = d.bH;
    this.c.b = new Object[] { new Integer(paramInt) };
    t();
  }
  
  public final void e(int paramInt) {
    this.c.a = d.bI;
    this.c.b = new Object[] { new Integer(paramInt) };
    t();
  }
  
  public final void a(String paramString1, String paramString2, String paramString3) {
    this.c.a = d.bE;
    if (paramString1.equals(""))
      paramString1 = "99999999"; 
    if (paramString2.equals(""))
      paramString2 = "99999999"; 
    if (paramString3.equals(""))
      paramString3 = "99999999"; 
    this.c.b = new Object[] { new String(paramString1), new String(paramString2), new String(paramString3) };
    t();
  }
  
  public final void a(int[] paramArrayOfint) {
    this.c.a = d.bF;
    Object[] arrayOfObject;
    (arrayOfObject = new Object[2 + paramArrayOfint.length])[0] = new Integer(paramArrayOfint.length);
    for (byte b = 0; b < paramArrayOfint.length; b++)
      arrayOfObject[b + 1] = new Integer(paramArrayOfint[b]); 
    this.c.b = arrayOfObject;
    t();
  }
  
  private void n(Object[] paramArrayOfObject) {
    int[] arrayOfInt = (int[])paramArrayOfObject[1];
    com.pheephoo.mjgame.ui.n.d();
    if (this.t == 1) {
      this.s.a(arrayOfInt[0], arrayOfInt[1]);
      return;
    } 
    this.r.a(arrayOfInt[0], arrayOfInt[1]);
  }
  
  private void o(Object[] paramArrayOfObject) {
    int[] arrayOfInt = (int[])paramArrayOfObject[1];
    if (this.t == 1)
      this.s.a(arrayOfInt[0], arrayOfInt[1], arrayOfInt[2], arrayOfInt[3]); 
  }
  
  public final void k() {
    h();
    this.c.a = d.bJ;
    this.c.b = null;
    t();
  }
  
  private void t() {
    this.q.a();
  }
  
  public final void a(String paramString) {
    this.c.a = d.bQ;
    this.c.b = new Object[] { new String(paramString) };
    t();
  }
  
  public final synchronized void l() {
    this.d = true;
    com.pheephoo.mjgame.ui.n.d();
    this.b.a();
    if (this.u != null) {
      this.u.cancel();
      this.v.cancel();
    } 
    if (this.w != null) {
      this.w.cancel();
      this.x.cancel();
    } 
    this.o = null;
    if (this.t == 1) {
      this.s.n = null;
      this.s = null;
      return;
    } 
    this.r.a = null;
    this.r = null;
  }
  
  public final void m() {
    b();
  }
  
  public final void b(String paramString) {
    this.c.a = d.bU;
    this.c.b = new Object[] { paramString, new String(b.a), new String(System.getProperty("microedition.platform")) };
    t();
  }
  
  public final int n() {
    return this.J;
  }
  
  public final int o() {
    return this.K;
  }
  
  private void u() {
    com.pheephoo.mjgame.ui.n.c();
    this.n.a().setCurrent((Displayable)com.pheephoo.mjgame.ui.n.a);
  }
  
  public final void p() {
    this.l = true;
    Thread thread;
    (thread = new Thread(new com/pheephoo/mjgame/engine/n(this, 2))).start();
    u();
  }
  
  public final void q() {
    this.l = true;
    Thread thread;
    (thread = new Thread(new com/pheephoo/mjgame/engine/n(this, 1))).start();
    u();
  }
  
  public final void r() {
    this.l = true;
    Thread thread;
    (thread = new Thread(new com/pheephoo/mjgame/engine/n(this, 0))).start();
    u();
  }
  
  private void v() {
    if (!this.B)
      while (!this.B) {
        try {
          Thread.sleep(500L);
        } catch (InterruptedException interruptedException) {}
      }  
  }
  
  public final void a(c paramc, int paramInt) {}
  
  public final void f(int paramInt) {}
  
  public final void a(int paramInt1, int paramInt2) {}
  
  private int g(int paramInt) {
    for (byte b = 0; b < 4; b++) {
      if ((this.g[b]).b == paramInt)
        return b; 
    } 
    return 0;
  }
  
  private int b(i parami) {
    this.F.removeAllElements();
    (this.g[0]).l.removeAllElements();
    (this.g[1]).l.removeAllElements();
    (this.g[2]).l.removeAllElements();
    (this.g[3]).l.removeAllElements();
    this.E = this.C.b;
    byte b;
    for (b = 1; b < 4; b++) {
      int j = g((this.E + b) % 4);
      int k;
      if ((k = c(j, this.F, parami)) >= 0)
        return 1; 
    } 
    for (b = 1; b < 4; b++) {
      int j = g((this.E + b) % 4);
      b(j, this.F, parami);
      if (this.F.size() > 0)
        return 1; 
    } 
    a(g((this.E + 1) % 4), this.F, parami);
    return (this.F.size() == 0) ? 0 : 1;
  }
  
  private void a(int paramInt, Vector paramVector, i parami) {
    if (parami.g > 2)
      return; 
    byte b1 = 0;
    byte b2 = -9;
    byte b3 = -9;
    byte b4 = -9;
    byte b5 = -9;
    boolean bool = false;
    e e;
    int j = (e = (this.g[paramInt]).h).e();
    for (byte b6 = 0; b6 < j; b6++) {
      i i1;
      if ((i1 = e.a(b6)).g == parami.g && parami.g != 3 && parami.g != 4) {
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
      (this.g[paramInt]).l.addElement(new a(r.c, b2, b3));
    } 
    if (b3 != -9 && b4 != -9) {
      bool = true;
      (this.g[paramInt]).l.addElement(new a(r.c, b3, b4));
    } 
    if (b4 != -9 && b5 != -9) {
      bool = true;
      (this.g[paramInt]).l.addElement(new a(r.c, b4, b5));
    } 
    if (bool)
      paramVector.addElement(new a(paramInt, r.c)); 
  }
  
  private void b(int paramInt, Vector paramVector, i parami) {
    byte b1 = 1;
    byte b2 = 0;
    byte b = -1;
    int j = (this.g[paramInt]).l.size();
    e e;
    int k = (e = (this.g[paramInt]).h).e();
    for (byte b3 = 0; b3 < k; b3++) {
      i i1;
      if ((i1 = e.a(b3)).g == parami.g && i1.h == parami.h) {
        b1++;
        if (b == -1)
          b = b2; 
        if (b1 == 3)
          (this.g[paramInt]).l.addElement(new a(r.a, b, b2)); 
        if (b1 == 4)
          (this.g[paramInt]).l.addElement(new a(r.b, b, b2)); 
      } else {
        b1 = 1;
        b = -1;
      } 
      b2++;
    } 
    if ((this.g[paramInt]).l.size() > j)
      paramVector.addElement(new a(paramInt, r.b)); 
  }
  
  private int c(int paramInt, Vector paramVector, i parami) {
    (this.g[paramInt]).l.removeAllElements();
    i i1 = new i(parami.g, parami.h);
    (this.g[paramInt]).h.e(i1);
    this.p.a((this.g[paramInt]).h, (this.g[paramInt]).i, this.D, (this.g[paramInt]).b);
    int j = this.p.a(false);
    (this.g[paramInt]).h.b(i1);
    return (j >= 0) ? 1 : 0;
  }
  
  static final MJGame a(h paramh) {
    return paramh.n;
  }
  
  static final int b(h paramh) {
    return paramh.J;
  }
  
  static final int c(h paramh) {
    return paramh.K;
  }
  
  private class com/pheephoo/mjgame/engine/g extends Thread {
    final h a;
    
    com/pheephoo/mjgame/engine/g(h this$0) {
      this.a = this$0;
    }
    
    public final synchronized void run() {
      while (!this.a.d) {
        try {
          wait();
          this.a.f = true;
          if (this.a.d)
            return; 
          this.a.e = 0;
          this.a.b.a(this.a.c.a, this.a.c.b);
        } catch (InterruptedException interruptedException) {
        
        } catch (IOException iOException) {
          this.a.b();
          iOException.printStackTrace();
        } catch (SecurityException securityException2) {
          SecurityException securityException1;
          (securityException1 = null).printStackTrace();
          h.a(this.a).c(d.d);
        } 
      } 
    }
    
    public final synchronized void a() {
      notify();
    }
  }
  
  class com/pheephoo/mjgame/engine/m extends TimerTask {
    final h a;
    
    com/pheephoo/mjgame/engine/m(h this$0) {
      this.a = this$0;
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
  
  class com/pheephoo/mjgame/engine/o extends TimerTask {
    final h a;
    
    com/pheephoo/mjgame/engine/o(h this$0) {
      this.a = this$0;
    }
    
    public final void run() {
      if (this.a.a())
        this.a.b(); 
    }
  }
  
  private class com/pheephoo/mjgame/engine/n extends Thread {
    private int b;
    
    final h a;
    
    public com/pheephoo/mjgame/engine/n(h this$0, int param1Int) {
      this.a = this$0;
      this.b = 0;
      this.b = param1Int;
    }
    
    public final synchronized void run() {
      if (this.b == 0)
        try {
          b.a(d.i, String.valueOf(d.j) + " " + h.b(this.a) + " " + h.c(this.a));
          return;
        } catch (a a) {
          h.a(this.a).a(d.as);
          return;
        } catch (SecurityException securityException) {
          h.a(this.a).c(d.c);
          return;
        }  
      if (this.b == 1)
        try {
          b.a(d.i, String.valueOf(d.k) + " " + h.b(this.a) + " " + h.c(this.a) + " " + this.a.j);
          return;
        } catch (a a) {
          h.a(this.a).a(d.ay);
          return;
        } catch (SecurityException securityException) {
          h.a(this.a).c(d.c);
          return;
        }  
      if (this.b == 2)
        try {
          b.a(d.i, String.valueOf(d.l) + " " + h.b(this.a) + " " + h.c(this.a) + " " + this.a.j);
          return;
        } catch (a a) {
          h.a(this.a).a(d.ay);
          return;
        } catch (SecurityException securityException) {
          h.a(this.a).c(d.c);
        }  
    }
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\engine\h.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */