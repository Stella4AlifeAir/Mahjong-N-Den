package com.pheephoo.mjgame.ui;

import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.b;
import com.pheephoo.mjgame.d;
import com.pheephoo.mjgame.engine.a;
import com.pheephoo.mjgame.engine.c;
import com.pheephoo.mjgame.engine.d;
import com.pheephoo.mjgame.engine.e;
import com.pheephoo.mjgame.engine.k;
import com.pheephoo.mjgame.engine.q;
import com.pheephoo.mjgame.engine.r;
import com.pheephoo.mjgame.engine.s;
import com.pheephoo.utilx.a;
import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;

public final class l extends Canvas implements c {
  int a = 0;
  
  private int I = 1;
  
  private boolean J = true;
  
  int b = 0;
  
  boolean c = true;
  
  private int K = 0;
  
  private int L = 0;
  
  private MJGame M;
  
  private s N;
  
  private q O;
  
  boolean d = false;
  
  private int P = 0;
  
  private int Q = -1;
  
  private boolean R;
  
  private int S = 0;
  
  private int T = 0;
  
  private int U = 0;
  
  private int V = 0;
  
  private int W = 0;
  
  private a X;
  
  private Vector Y;
  
  private Vector Z;
  
  private int aa = 0;
  
  private int ab = 0;
  
  private int ac = 0;
  
  private int ad = 0;
  
  private boolean ae = false;
  
  boolean e = false;
  
  boolean f = false;
  
  private Timer af = null;
  
  private TimerTask ag = null;
  
  int g = 0;
  
  private boolean ah = false;
  
  private boolean ai = false;
  
  long h = 0L;
  
  private boolean aj = true;
  
  public int i = 0;
  
  int j;
  
  e k;
  
  e l;
  
  int m;
  
  int n;
  
  int o;
  
  e p;
  
  e q;
  
  int r;
  
  int s;
  
  int t;
  
  e u;
  
  e v;
  
  int w;
  
  int x;
  
  int y;
  
  e z;
  
  e A;
  
  int B;
  
  int C;
  
  int D;
  
  private int ak = 0;
  
  private int al = 0;
  
  Graphics E;
  
  private Image am;
  
  private Image an;
  
  private Image ao;
  
  private Image ap;
  
  private Image aq;
  
  private Image ar;
  
  private Image as;
  
  private Image at;
  
  private Image au;
  
  private Image av;
  
  private Image aw;
  
  private Image ax;
  
  private Image ay;
  
  private Image az;
  
  private Image aA;
  
  private Image aB;
  
  private Image aC;
  
  private Image aD;
  
  private Image aE;
  
  private int aF = -1;
  
  private int aG = -1;
  
  private Player aH;
  
  private Player aI;
  
  private Player aJ;
  
  private VolumeControl aK;
  
  private VolumeControl aL;
  
  private VolumeControl aM;
  
  private InputStream aN;
  
  private InputStream aO;
  
  private int aP = 0;
  
  private int aQ;
  
  public int F;
  
  public d[] G = new d[4];
  
  int H = 0;
  
  public l(MJGame paramMJGame, int paramInt1, int paramInt2) {
    this.b = paramInt2;
    setFullScreenMode(true);
    this.P = paramInt1;
    this.M = paramMJGame;
    j();
    k();
    this.d = true;
    this.E.setColor(0, 0, 0);
    this.E.fillRect(0, 0, this.ak, this.al);
  }
  
  public final void a(int paramInt) {
    this.L = paramInt;
  }
  
  public l(MJGame paramMJGame, int paramInt) {
    setFullScreenMode(true);
    this.P = paramInt;
    this.M = paramMJGame;
    j();
    k();
    this.d = true;
    i();
  }
  
  private void j() {
    try {
      this.ar = Image.createImage("/res/top_edge.png");
      this.as = Image.createImage("/res/tile_edge.png");
      this.at = Image.createImage("/res/side_l.png");
      this.au = Image.createImage("/res/side_r.png");
      this.av = Image.createImage("/res/side_t.png");
      this.aw = Image.createImage("/res/maintile.png");
      this.ax = Image.createImage("/res/maintile_s.png");
      this.ay = Image.createImage("/res/arrow.png");
      this.az = Image.createImage("/res/small_edge.png");
      this.aA = Image.createImage("/res/dialog.png");
      this.aB = Image.createImage("/res/firstlevel.png");
      this.aC = Image.createImage("/res/secondlevel.png");
      this.aD = Image.createImage("/res/wind.png");
      this.am = Image.createImage(getWidth(), getHeight());
      this.E = this.am.getGraphics();
      return;
    } catch (IOException iOException) {
      throw new Error();
    } 
  }
  
  private void k() {
    this.G[0] = new d();
    this.ak = getWidth();
    this.al = getHeight();
    this.am = Image.createImage(getWidth(), getHeight());
    this.E = this.am.getGraphics();
    h();
    g();
    this.ao = a.a(this.am, 0, 0, b.f, b.g);
    this.ap = a.a(this.am, 0, 0, this.ak, 2 * b.g);
  }
  
  protected final void paint(Graphics paramGraphics) {
    paramGraphics.drawImage(this.am, 0, 0, 20);
  }
  
  protected final void keyPressed(int paramInt) {
    try {
      if (!this.ah || System.currentTimeMillis() - 350L < this.h)
        return; 
      switch (paramInt) {
        case -7:
        case -6:
          if (this.ah) {
            if (this.P == 0) {
              this.M.a(d.T);
              break;
            } 
            this.M.a(d.aO);
          } 
          break;
      } 
      if (this.ab == 0) {
        this.ab = -1;
        B();
      } else if (this.ab == 1) {
        p(paramInt);
      } else if (this.ab == 3) {
        r(paramInt);
      } else if (this.ab == 4) {
        t(paramInt);
      } else if (this.ab == 5) {
        q(paramInt);
      } else if (this.ab == 6) {
        s(paramInt);
      } else if (this.ab == 8) {
        if (this.aj) {
          this.aj = false;
          a(this.j, this.k, this.l, this.m, this.n, this.o, this.p, this.q, this.r, this.s, this.t, this.u, this.v, this.w, this.x, this.y, this.z, this.A, this.B, this.C, this.D);
          return;
        } 
        this.c = true;
        if (paramInt == -5) {
          this.ab = -1;
          this.aj = true;
          A();
        } 
      } else {
        return;
      } 
    } catch (IOException iOException) {
      throw new Error();
    } 
    l();
  }
  
  private void d(int paramInt) {
    boolean bool = false;
    a a1;
    int i;
    for (i = (a1 = this.Z.elementAt(paramInt - 1)).c; i <= a1.d; i++) {
      com.pheephoo.mjgame.engine.i i1;
      (i1 = this.O.h.a(i)).i = this.S;
      i1.j = 2;
      this.O.i.f(i1);
    } 
    for (i = a1.c; i <= a1.d; i++)
      this.O.h.b(this.O.h.a(a1.c)); 
    this.O.k.i = this.S;
    this.O.k.j = 2;
    this.O.i.f(this.O.k);
    this.ac = 1;
    this.ad = this.O.h.e();
    g(this.F, a1.b);
    this.S++;
    j(0);
    this.T--;
    f();
    l();
  }
  
  private void l() {
    repaint();
    serviceRepaints();
  }
  
  private void m() {
    if (this.O.k != null) {
      this.O.h.e(this.O.k);
    } else {
      this.O.h.e(this.O.j);
    } 
    this.N.a(this.O.b);
  }
  
  private void e(int paramInt) {
    a a1 = this.Z.elementAt(paramInt - 1);
    com.pheephoo.mjgame.engine.i i1 = this.O.h.a(a1.c);
    com.pheephoo.mjgame.engine.i i2 = this.O.h.a(a1.d);
    i1.i = this.S;
    i1.j = 3;
    i2.i = this.S;
    i2.j = 3;
    this.O.k.i = this.S;
    this.O.k.j = 3;
    String str = "";
    if (this.O.k.h < i1.h) {
      this.O.i.f(this.O.k);
      com.pheephoo.mjgame.engine.i i;
      (i = new com.pheephoo.mjgame.engine.i(i1.i, i1.g, i1.h)).j = 3;
      this.O.i.f(i);
      str = String.valueOf(str) + " ;" + i;
      (i = new com.pheephoo.mjgame.engine.i(i2.i, i2.g, i2.h)).j = 3;
      this.O.i.f(i);
      String.valueOf(str) + " ;" + i;
    } else {
      com.pheephoo.mjgame.engine.i i;
      (i = new com.pheephoo.mjgame.engine.i(i1.i, i1.g, i1.h)).j = 3;
      this.O.i.f(i);
      str = String.valueOf(str) + " ;" + i;
      if (this.O.k.h < i2.h) {
        this.O.i.f(this.O.k);
        str = String.valueOf(str) + " ;" + i;
        (i = new com.pheephoo.mjgame.engine.i(i2.i, i2.g, i2.h)).j = 3;
        this.O.i.f(i);
        String.valueOf(str) + " ;" + i;
      } else {
        (i = new com.pheephoo.mjgame.engine.i(i2.i, i2.g, i2.h)).j = 3;
        this.O.i.f(i);
        str = String.valueOf(str) + " ;" + i;
        this.O.i.f(this.O.k);
        String.valueOf(str) + " ;" + i;
      } 
    } 
    this.O.h.b(i1);
    this.O.h.b(i2);
    this.ac = 1;
    this.ad = this.O.h.e();
    g(this.F, r.c);
    this.S++;
    j(0);
    this.T--;
    f();
    l();
  }
  
  private com.pheephoo.mjgame.engine.i a(int paramInt, com.pheephoo.mjgame.engine.i parami) {
    a a1 = this.Z.elementAt(paramInt - 1);
    com.pheephoo.mjgame.engine.i i1;
    if ((i1 = this.O.i.a(a1.c)).a(parami)) {
      parami.i = i1.i;
      parami.j = i1.j;
      this.O.i.a(parami);
    } else {
      for (byte b = 0; b < this.O.h.e(); b++) {
        com.pheephoo.mjgame.engine.i i2 = this.O.h.a(b);
        if (i1.a(i2)) {
          i2.i = i1.i;
          i2.j = i1.j;
          this.O.i.a(i2);
          this.O.h.b(i2);
          this.O.h.e(parami);
          break;
        } 
      } 
    } 
    this.ac = 1;
    g(this.F, r.b);
    j(0);
    l();
    return i1;
  }
  
  private com.pheephoo.mjgame.engine.i f(int paramInt) {
    a a1 = this.Z.elementAt(paramInt - 1);
    String str = "";
    byte b = 0;
    com.pheephoo.mjgame.engine.i i = null;
    int j;
    for (j = a1.c; j <= a1.d; j++) {
      (i = this.O.h.a(j)).i = this.S;
      i.j = 2;
      this.O.i.f(i);
      str = String.valueOf(str) + " ;" + i;
      b++;
    } 
    for (j = a1.c; j <= a1.d; j++)
      this.O.h.b(this.O.h.a(a1.c)); 
    if (b != 4) {
      this.O.j.i = this.S;
      this.O.j.j = 2;
      this.O.i.f(this.O.j);
      String.valueOf(str) + " ;" + this.O.j;
    } else {
      this.O.j.i = this.S;
      this.O.j.j = 2;
      this.O.h.e(this.O.j);
      String.valueOf(str) + " ;" + this.O.j;
    } 
    this.O.j = null;
    this.ac = 1;
    g(this.F, r.b);
    this.S++;
    j(0);
    l();
    return i;
  }
  
  private void n() {
    this.g = 0;
    this.f = true;
  }
  
  final void c() {
    this.f = false;
    this.g = 0;
  }
  
  private void o() {
    if (this.af != null) {
      this.af.cancel();
      this.ag.cancel();
      this.af = null;
    } 
    if (this.P != 0) {
      if (this.af == null) {
        this.f = false;
        this.ag = new com/pheephoo/mjgame/ui/i(this);
        this.af = new Timer();
        this.af.scheduleAtFixedRate(this.ag, 1L, 1000L);
        return;
      } 
    } else if (this.b > 4 && this.af == null) {
      this.f = false;
      this.ag = new com/pheephoo/mjgame/ui/i(this);
      this.af = new Timer();
      this.af.scheduleAtFixedRate(this.ag, 50L, 50L);
    } 
  }
  
  public final void a(Vector paramVector) {
    this.ae = true;
    this.Y = paramVector;
    this.aa = 0;
    this.W = 1;
    a a1;
    if ((a1 = paramVector.firstElement()).b == r.b || a1.b == r.a) {
      this.ab = 3;
    } else if (a1.b == r.d) {
      this.ab = 6;
    } 
    a(0, 1, paramVector);
    n();
    this.h = System.currentTimeMillis();
    l();
  }
  
  public final void b(Vector paramVector) {
    this.aG = this.aF;
    k(0);
    n();
    this.R = false;
    this.ae = false;
    this.Y = paramVector;
    this.aa = 0;
    this.W = 1;
    a a1;
    if ((a1 = paramVector.firstElement()).b == r.b || a1.b == r.a) {
      this.ab = 4;
      t();
    } else if (a1.b == r.c) {
      this.ab = 5;
      t();
    } else if (a1.b == r.d) {
      this.ab = 6;
      t();
    } else if (a1.b == r.e) {
      this.R = true;
      this.ab = 6;
    } 
    a(0, 1, paramVector);
    this.h = System.currentTimeMillis();
    l();
  }
  
  public final void a(int paramInt1, int paramInt2, int paramInt3, s params, q paramq, int paramInt4) {
    this.d = false;
    this.N = params;
    this.O = paramq;
    this.O.g = k.e;
    (this.G[0]).c = k.e;
    (this.G[0]).a = paramInt2;
    (this.G[0]).b = this.F;
    this.O.f = this.F;
    this.N.a(k.c, k.d);
  }
  
  public final void a(int paramInt1, int paramInt2, int paramInt3, String paramString) {
    this.H++;
    if (this.H < this.G.length) {
      this.G[this.H] = new d();
      (this.G[this.H]).a = paramInt1;
      (this.G[this.H]).c = paramString;
      (this.G[this.H]).b = paramInt3;
    } 
  }
  
  public final void a(int paramInt1, int paramInt2, String paramString1, String paramString2) {
    for (byte b = 0; b < 4; b++) {
      if ((this.G[b]).a == paramInt1) {
        (this.G[b]).b = paramInt2;
        (this.G[b]).c = paramString2;
        break;
      } 
    } 
    String str = String.valueOf(paramString1) + " left " + g(paramInt1) + ". Player is replaced by " + paramString2 + " (bot)";
    this.M.a(d.aS, str);
  }
  
  private String g(int paramInt) {
    switch (paramInt) {
      case 0:
        return "East";
      case 1:
        return "South";
      case 2:
        return "West";
      case 3:
        return "North";
    } 
    return "";
  }
  
  public final void a(int paramInt1, int paramInt2, String paramString) {
    for (byte b = 0; b < 4; b++) {
      if ((this.G[b]).a == paramInt1) {
        (this.G[b]).b = paramInt2;
        (this.G[b]).c = paramString;
        break;
      } 
    } 
    String str = String.valueOf(paramString) + " joined " + g(paramInt1);
    this.M.a(d.aS, str);
  }
  
  public final void a(int paramInt1, e parame1, e parame2, int paramInt2, int paramInt3, int paramInt4, String paramString1, int paramInt5, e parame3, int paramInt6, int paramInt7, int paramInt8, String paramString2, int paramInt9, e parame4, int paramInt10, int paramInt11, int paramInt12, String paramString3, int paramInt13, e parame5, e parame6) {
    this.O.b = paramInt1;
    this.T = 0;
    this.am = Image.createImage(getWidth(), getHeight());
    this.E = this.am.getGraphics();
    g();
    this.ab = 0;
    this.S = 0;
    if (this.O.i.e() > 0) {
      com.pheephoo.mjgame.engine.i i = this.O.i.a(this.O.i.e() - 1);
      this.S = i.i + 1;
    } 
    a(parame3, paramInt5 + 1, paramInt2, false, null);
    a(parame4, paramInt9 + 1, paramInt6, false, null);
    a(parame5, paramInt13 + 1, paramInt10, false, null);
    byte b;
    for (b = 1; b < 4; b++)
      this.G[b] = new d(); 
    (this.G[1]).a = paramInt2;
    (this.G[2]).a = paramInt6;
    (this.G[3]).a = paramInt10;
    (this.G[1]).b = paramInt3;
    (this.G[2]).b = paramInt7;
    (this.G[3]).b = paramInt11;
    (this.G[1]).c = paramString1;
    (this.G[2]).c = paramString2;
    (this.G[3]).c = paramString3;
    (this.G[0]).f[0] = this.O.e;
    (this.G[1]).f[0] = paramInt4;
    (this.G[2]).f[0] = paramInt8;
    (this.G[3]).f[0] = paramInt12;
    this.i = 1;
    j(1);
    w();
    for (b = 0; b < parame6.e(); b++) {
      com.pheephoo.mjgame.engine.i i = parame6.a(b);
      a(i);
    } 
    p();
    l();
    o();
    n();
    this.h = System.currentTimeMillis();
    this.ah = true;
  }
  
  public final void b(com.pheephoo.mjgame.engine.i parami, int paramInt1, int paramInt2) {
    this.O.k = new com.pheephoo.mjgame.engine.i(parami.g, parami.h);
    if (this.O.b != paramInt1) {
      o(paramInt2);
      if (paramInt1 == (this.O.b + 1) % 4) {
        this.E.drawImage(this.au, this.ak - b.k, (this.al - 13 * b.k) / 2 - b.l, 20);
      } else if (paramInt1 == (this.O.b + 2) % 4) {
        this.E.drawImage(this.av, this.ak - 14 * b.k - b.l, 0, 20);
      } else if (paramInt1 == (this.O.b + 3) % 4) {
        this.E.drawImage(this.at, 0, b.m + b.k * 12 + b.l, 20);
      } 
      l();
      if (this.aH != null && this.b <= 4) {
        if (this.aH.getState() != 300)
          D(); 
        try {
          Thread.sleep(200L);
        } catch (InterruptedException interruptedException) {}
        try {
          this.aH.start();
        } catch (MediaException mediaException) {}
        try {
          Thread.sleep(100L);
        } catch (InterruptedException interruptedException) {}
      } 
      if (paramInt1 == (this.O.b + 1) % 4) {
        this.E.drawImage(a.a(this.ao, 0, 0, b.k, b.k), this.ak - b.k, (this.al - 13 * b.k) / 2 - b.l, 20);
      } else if (paramInt1 == (this.O.b + 2) % 4) {
        this.E.drawImage(a.a(this.ao, 0, 0, b.k, b.k), this.ak - 14 * b.k - b.l, 0, 20);
      } else if (paramInt1 == (this.O.b + 3) % 4) {
        this.E.drawImage(a.a(this.ao, 0, 0, b.k, b.k), 0, b.m + b.k * 12 + b.l, 20);
      } 
      a(parami);
      l();
      if (paramInt1 == (this.O.b + 1) % 4) {
        k(2);
        return;
      } 
      if (paramInt1 == (this.O.b + 2) % 4) {
        k(3);
        return;
      } 
      if (paramInt1 == (this.O.b + 3) % 4)
        k(0); 
    } 
  }
  
  public final void a(com.pheephoo.mjgame.engine.i parami, int paramInt1, int paramInt2) {
    if (paramInt2 != this.O.b)
      return; 
    this.U = paramInt1;
    this.O.k = null;
    this.O.j = parami;
    this.ac = 0;
    this.ad = this.O.h.e();
    r();
    o(paramInt1);
    l();
  }
  
  private void p() {
    String str = g(this.O.b).toUpperCase();
    l();
    this.aq = a.a(this.am, 0, 0, this.ak, this.al);
    this.E.setColor(0, 0, 0);
    this.E.setFont(Font.getFont(0, 1, 16));
    this.E.drawString("Your Wind: " + str, b.q, b.p, 20);
  }
  
  public final void a(e parame1, e parame2) {
    this.ah = false;
    this.T = 0;
    if (this.P != 0) {
      try {
        while (!this.J) {
          this.ah = true;
          this.I = 1;
          Thread.sleep(500L);
        } 
        this.J = false;
      } catch (InterruptedException interruptedException) {}
      u();
    } else if (this.a == 0) {
      this.a++;
      u();
    } 
    this.d = false;
    this.am = Image.createImage(getWidth(), getHeight());
    this.E = this.am.getGraphics();
    g();
    this.S = 0;
    int i;
    if ((i = parame2.e()) > 0) {
      com.pheephoo.mjgame.engine.i i1 = parame2.a(i - 1);
      this.S = i1.i + 1;
    } 
    try {
      Thread.sleep(1000L);
      d(13, 0);
      j(1);
      w();
      l();
    } catch (InterruptedException interruptedException) {}
    p();
    this.ab = 0;
    this.h = System.currentTimeMillis();
    l();
    this.ah = true;
    o();
    n();
  }
  
  private void q() {
    int i = b.n;
    if (this.ac == 0)
      i = 0; 
    if (this.ay == null) {
      System.gc();
      j();
    } 
    this.E.drawImage(this.ay, this.ak - b.f - i - this.ac * b.f, this.al - b.g - b.o, 20);
    if (this.ad == 0) {
      i = 0;
    } else {
      i = b.n;
    } 
    this.E.drawImage(this.ao, this.ak - b.f - i - this.ad * b.f, this.al - 2 * b.g, 20);
    this.E.drawImage(this.ar, this.ak - b.f - i - this.ad * b.f, this.al - b.g - b.i, 20);
    this.ad = this.ac;
    l();
  }
  
  private void r() {
    this.E.drawImage(Image.createImage(b(this.O.j)), this.ak - b.f, this.al - b.g, 20);
    this.E.drawImage(this.ar, this.ak - b.f, this.al - b.g - b.i, 20);
  }
  
  private void h(int paramInt) {
    this.E.drawImage(b(this.O.h.a(paramInt)), (13 - this.O.h.e()) * b.f + paramInt * b.f, this.al - b.g - b.i, 20);
    this.E.drawImage(this.as, (13 - this.O.h.e()) * b.f + paramInt * b.f, this.al - b.i, 20);
  }
  
  private void i(int paramInt) {
    this.E.drawImage(b(this.O.h.a(paramInt)), (13 - this.O.h.e()) * b.f + paramInt * b.f, this.al - b.g, 20);
    this.E.drawImage(this.ar, (13 - this.O.h.e()) * b.f + paramInt * b.f, this.al - b.g - b.i, 20);
  }
  
  private void a(int paramInt1, int paramInt2) throws IOException {
    v();
    for (byte b = 0; b <= this.O.h.e() - 1; b++) {
      if (b >= paramInt1 && b <= paramInt2) {
        h(b);
      } else {
        i(b);
      } 
    } 
  }
  
  private void b(int paramInt1, int paramInt2) {
    byte b1 = 1;
    int i = -1;
    com.pheephoo.mjgame.engine.i i1 = null;
    byte b2 = 0;
    byte b3;
    for (b3 = 0; b3 < this.O.i.e(); b3++) {
      i1 = this.O.i.a(b3);
      if (i != i1.i) {
        i = i1.i;
        b1 = 1;
      } 
      if (b1 != 4) {
        if (b3 >= paramInt1 && b3 <= paramInt2) {
          this.E.drawImage(this.az, b2 * b.h, this.al - b.g - b.j, 20);
          this.E.drawImage(a(i1, com.pheephoo.mjgame.engine.i.c), b2 * b.h, this.al - b.g, 20);
        } else {
          this.E.drawImage(this.az, b2 * b.h, this.al - b.j, 20);
          this.E.drawImage(a(i1, com.pheephoo.mjgame.engine.i.c), b2 * b.h, this.al - b.g - b.j, 20);
        } 
      } else {
        this.E.drawImage(n(com.pheephoo.mjgame.engine.i.c), (b2 - 2) * b.h, this.al - b.g, 20);
        b2--;
      } 
      b1++;
      b2++;
    } 
    for (b3 = 0; b3 <= this.O.h.e() - 1; b3++)
      i(b3); 
  }
  
  private void c(int paramInt1, int paramInt2) {
    for (byte b = 0; b <= this.O.h.e() - 1; b++) {
      if (b == paramInt1 || b == paramInt2) {
        h(b);
      } else {
        i(b);
      } 
    } 
  }
  
  private void j(int paramInt) {
    this.O.h = this.O.h;
    this.E.drawImage(this.ap, 0, this.al - 2 * b.g, 20);
    v();
    for (byte b = 0; b <= this.O.h.e() - 1; b++) {
      this.E.drawImage(b(this.O.h.a(b)), (13 - this.O.h.e()) * b.f + b * b.f, this.al - b.g, 20);
      this.E.drawImage(this.ar, (13 - this.O.h.e()) * b.f + b * b.f, this.al - b.g - b.i, 20);
      if (paramInt == 1)
        C(); 
    } 
  }
  
  private void s() {
    int i = (getWidth() - 11 * b.f) / 2;
    int j = (this.T - 1) % 11;
    int k = (this.T - 1) / 11;
    this.E.drawImage(this.aq, i + b.f * j, b.y + b.g * k, 20);
  }
  
  private void t() {
    int i = (this.ak - 11 * b.f) / 2;
    int j = (this.T - 1) % 11;
    int k = (this.T - 1) / 11;
    this.aq = a.a(this.am, i + b.f * j, b.y + b.g * k, b.f, b.g + b.i);
    this.E.setColor(255, 0, 0);
    this.E.drawRect(i + b.f * j, b.y + b.g * k, b.f - 1, b.g + b.i - 1);
  }
  
  private void a(com.pheephoo.mjgame.engine.i parami) {
    int j = (getWidth() - 11 * b.f) / 2;
    int k = this.T % 11;
    int m = this.T / 11;
    this.E.drawImage(b(parami), j + b.f * k, b.y + b.g * m, 20);
    this.E.drawImage(this.as, j + b.f * k, b.y + b.g + b.g * m, 20);
    this.T++;
  }
  
  private void a(e parame, int paramInt) {
    byte b1 = 1;
    int i = -1;
    com.pheephoo.mjgame.engine.i i1 = null;
    byte b2 = 0;
    for (byte b3 = 0; b3 < parame.e(); b3++) {
      i1 = parame.a(b3);
      if (i != i1.i) {
        i = i1.i;
        b1 = 1;
      } 
      if (b1 != 4) {
        if (paramInt == 1) {
          this.E.drawImage(a(i1, com.pheephoo.mjgame.engine.i.d), this.ak - b.g, b.z - b2 * b.h, 20);
        } else if (paramInt == 2) {
          this.E.drawImage(a(i1, com.pheephoo.mjgame.engine.i.e), this.ak - (b2 + 1) * b.h, 0, 20);
        } else if (paramInt == 3) {
          this.E.drawImage(a(i1, com.pheephoo.mjgame.engine.i.f), 0, 0 + b2 * b.h, 20);
        } 
      } else {
        if (paramInt == 1) {
          this.E.drawImage(n(com.pheephoo.mjgame.engine.i.d), this.ak - b.g, b.z - (b2 - 2) * b.h, 20);
        } else if (paramInt == 2) {
          this.E.drawImage(n(com.pheephoo.mjgame.engine.i.e), this.ak - (b2 + 1 - 2) * b.h, 0, 20);
        } else if (paramInt == 3) {
          this.E.drawImage(n(com.pheephoo.mjgame.engine.i.f), 0, 0 + (b2 - 2) * b.h, 20);
        } 
        b2--;
      } 
      b1++;
      b2++;
    } 
  }
  
  private void u() {
    try {
      this.E.drawImage(n.c, 0, b.A, 20);
      this.E.drawImage(n.a(0), (this.ak - b.ad) / 2, this.al - b.ae, 20);
      l();
      if (this.aJ != null)
        this.aJ.close(); 
      InputStream inputStream = getClass().getResourceAsStream("/res/shipai.amr");
      this.aJ = Manager.createPlayer(inputStream, "audio/amr");
      if (this.aJ != null) {
        this.aJ.realize();
        this.aM = (VolumeControl)this.aJ.getControl("VolumeControl");
        if (this.aM != null)
          this.aM.setLevel(this.aP); 
      } 
      if (!this.d) {
        this.d = true;
        Thread thread;
        (thread = new Thread(new com/pheephoo/mjgame/ui/f(this))).start();
      } 
      while (this.d)
        Thread.sleep(500L); 
      Thread.sleep(200L);
      this.aJ.start();
      System.out.println("playAnimation.init player start.:realVolumeLevel=" + this.aP);
      l();
      return;
    } catch (IOException iOException) {
      return;
    } catch (MediaException mediaException) {
      return;
    } catch (InterruptedException interruptedException) {
      return;
    } catch (RuntimeException runtimeException) {
      return;
    } 
  }
  
  private void d(int paramInt1, int paramInt2) {
    if (paramInt2 == 3 || paramInt2 == 0) {
      int i;
      for (i = 0; i < 13 - paramInt1; i++) {
        this.E.drawImage(a.a(this.ao, 0, 0, b.k, b.k), 0, b.m + b.k * i, 20);
        if (!this.ah)
          C(); 
      } 
      for (i = 13 - paramInt1; i < 13; i++) {
        this.E.drawImage(this.at, 0, b.m + b.k * i, 20);
        if (!this.ah)
          C(); 
      } 
    } 
    if (paramInt2 == 2 || paramInt2 == 0) {
      int i;
      for (i = 0; i < paramInt1; i++) {
        this.E.drawImage(Image.createImage(this.av), this.ak - 14 * b.k + i * b.k, 0, 20);
        if (!this.ah)
          C(); 
      } 
      for (i = paramInt1; i < 13; i++) {
        this.E.drawImage(a.a(this.ao, 0, 0, b.k, b.k), this.ak - 14 * b.k + i * b.k, 0, 20);
        if (!this.ah)
          C(); 
      } 
    } 
    if (paramInt2 == 1 || paramInt2 == 0) {
      int i;
      for (i = 0; i < paramInt1; i++) {
        this.E.drawImage(this.au, this.ak - b.k, (getHeight() - 13 * b.k) / 2 + i * b.k, 20);
        if (paramInt2 == 0)
          C(); 
      } 
      for (i = paramInt1; i < 13; i++) {
        this.E.drawImage(a.a(this.ao, 0, 0, b.k, b.k), this.ak - b.k, (getHeight() - 13 * b.k) / 2 + i * b.k, 20);
        if (paramInt2 == 0)
          C(); 
      } 
    } 
  }
  
  private void k(int paramInt) {
    if (this.P == 0)
      return; 
    this.E.drawImage(this.aE, this.ak - b.k - b.v, (getHeight() - 13 * b.k) / 2 + 0 * b.k, 20);
    this.E.drawImage(this.aE, this.ak - 14 * b.k + 0 * b.k, 0 + b.v, 20);
    this.E.drawImage(this.aE, 0 + b.v, b.m + b.k * 12, 20);
    if (paramInt == 1) {
      try {
        Thread.sleep(800L);
        this.E.drawImage(l(paramInt), this.ak - b.k - b.v, (getHeight() - 13 * b.k) / 2 + 0 * b.k, 20);
      } catch (InterruptedException interruptedException) {}
    } else if (paramInt == 2) {
      this.E.drawImage(l(paramInt), this.ak - 14 * b.k + 0 * b.k, 0 + b.v, 20);
    } else if (paramInt == 3) {
      this.E.drawImage(l(paramInt), 0 + b.v, b.m + b.k * 12, 20);
    } 
    this.aF = paramInt;
    l();
  }
  
  private Image l(int paramInt) {
    String str = "";
    if (paramInt == 1) {
      str = "arrowright.png";
    } else if (paramInt == 2) {
      str = "arrowup.png";
    } else if (paramInt == 3) {
      str = "arrowleft.png";
    } 
    Image image = null;
    try {
      image = Image.createImage("/res/" + str);
    } catch (IOException iOException) {
      throw new Error();
    } 
    return image;
  }
  
  private Image m(int paramInt) {
    String str = "";
    if (paramInt == 0) {
      str = "indi_arrow0.png";
    } else if (paramInt == 1) {
      str = "indi_arrow1.png";
    } else if (paramInt == 2) {
      str = "indi_arrow2.png";
    } else if (paramInt == 3) {
      str = "indi_arrow3.png";
    } 
    Image image = null;
    try {
      image = Image.createImage("/res/" + str);
    } catch (IOException iOException) {
      throw new Error();
    } 
    return image;
  }
  
  private Image b(com.pheephoo.mjgame.engine.i parami) {
    int j = parami.g;
    int k = parami.h - 1;
    if (parami.g == 4) {
      j = 3;
      k += 4;
    } 
    return a.a(this.aw, k * b.f, j * b.g, b.f, b.g);
  }
  
  private Image n(int paramInt) {
    Image image1 = null;
    image1 = a.a(this.ax, 7 * b.h, 3 * b.g, b.h, b.g);
    Image image2 = Image.createImage(b.h, b.g);
    Image image3 = Image.createImage(b.g, b.h);
    Graphics graphics1 = image2.getGraphics();
    Graphics graphics2 = image3.getGraphics();
    if (paramInt == com.pheephoo.mjgame.engine.i.c) {
      graphics1.drawRegion(image1, 0, 0, image1.getWidth(), image1.getHeight(), 0, 0, 0, 20);
      return image2;
    } 
    if (paramInt == com.pheephoo.mjgame.engine.i.e) {
      graphics1.drawRegion(image1, 0, 0, image1.getWidth(), image1.getHeight(), 3, 0, 0, 20);
      return image2;
    } 
    if (paramInt == com.pheephoo.mjgame.engine.i.f) {
      graphics2.drawRegion(image1, 0, 0, image1.getWidth(), image1.getHeight(), 5, 0, 0, 20);
      return image3;
    } 
    graphics2.drawRegion(image1, 0, 0, image1.getWidth(), image1.getHeight(), 6, 0, 0, 20);
    return image3;
  }
  
  private Image a(com.pheephoo.mjgame.engine.i parami, int paramInt) {
    Image image1 = null;
    int j = 0;
    int k = 0;
    Image image2 = Image.createImage(b.h, b.g);
    Image image3 = Image.createImage(b.g, b.h);
    Graphics graphics1 = image2.getGraphics();
    Graphics graphics2 = image3.getGraphics();
    j = parami.g;
    k = parami.h - 1;
    if (parami.g == 4) {
      j = 3;
      k += 4;
    } 
    image1 = a.a(this.ax, k * b.h, j * b.g, b.h, b.g);
    if (paramInt == com.pheephoo.mjgame.engine.i.c) {
      graphics1.drawRegion(image1, 0, 0, image1.getWidth(), image1.getHeight(), 0, 0, 0, 20);
      return image2;
    } 
    if (paramInt == com.pheephoo.mjgame.engine.i.e) {
      graphics1.drawRegion(image1, 0, 0, image1.getWidth(), image1.getHeight(), 3, 0, 0, 20);
      return image2;
    } 
    if (paramInt == com.pheephoo.mjgame.engine.i.f) {
      graphics2.drawRegion(image1, 0, 0, image1.getWidth(), image1.getHeight(), 5, 0, 0, 20);
      return image3;
    } 
    graphics2.drawRegion(image1, 0, 0, image1.getWidth(), image1.getHeight(), 6, 0, 0, 20);
    return image3;
  }
  
  private void v() {
    byte b1 = 1;
    int i = -1;
    com.pheephoo.mjgame.engine.i i1 = null;
    byte b2 = 0;
    for (byte b3 = 0; b3 < this.O.i.e(); b3++) {
      i1 = this.O.i.a(b3);
      if (i != i1.i) {
        i = i1.i;
        b1 = 1;
      } 
      if (b1 != 4) {
        this.E.drawImage(this.az, b2 * b.h, this.al - b.j, 20);
        this.E.drawImage(a(i1, com.pheephoo.mjgame.engine.i.c), b2 * b.h, this.al - b.g - b.j, 20);
      } else {
        this.E.drawImage(n(com.pheephoo.mjgame.engine.i.c), (b2 - 2) * b.h, this.al - b.g, 20);
        b2--;
      } 
      b1++;
      b2++;
    } 
  }
  
  private void w() {
    this.E.drawImage(a.a(this.aD, this.N.e() * b.t, 0, b.t, b.s), 0, b.r, 20);
    this.E.drawImage(m((4 - this.O.b) % 4), b.t + 2, b.r - (b.u - b.s) / 2, 20);
  }
  
  private void o(int paramInt) {
    this.E.drawImage(this.ao, b.w, b.x, 20);
    this.E.drawImage(this.ao, b.w + 5, b.x, 20);
    this.E.drawImage(this.ao, b.w, b.x + 5, 20);
    this.E.drawImage(this.ao, b.w + 5, b.x + 5, 20);
    this.E.setColor(0, 0, 0);
    this.E.setFont(Font.getFont(32, 0, 8));
    this.E.drawString((new StringBuffer(String.valueOf(paramInt))).toString(), b.w, b.x, 20);
  }
  
  private void a(int paramInt1, int paramInt2, Vector paramVector) {
    this.Z = new Vector();
    if (!this.ai) {
      this.an = a.a(this.am, 0, 0, this.ak, this.al);
      this.ai = true;
    } 
    int i = (this.ak - this.aA.getWidth()) / 2;
    if (this.aA == null) {
      System.gc();
      j();
    } 
    this.E.drawImage(this.aA, i, b.B, 20);
    int j = -1;
    int k = i + 2;
    int m = 0;
    int n = 1;
    if (paramInt1 == 0) {
      for (byte b = 0; b < paramVector.size(); b++) {
        m = ((a)paramVector.elementAt(b)).b;
        if (j != m) {
          j = m;
          if (n == paramInt2) {
            this.E.drawImage(e(m, 1), k, b.B + 2, 20);
          } else {
            this.E.drawImage(e(m, 0), k, b.B + 2, 20);
          } 
          k = k + b.C + 1;
          paramVector.elementAt(b);
          Object object = null;
          this.Z.addElement(paramVector.elementAt(b));
          n++;
        } 
      } 
      if (paramInt2 == this.V) {
        this.E.drawImage(e(r.i, 1), i + this.aA.getWidth() - b.C - 2, b.B + 2, 20);
      } else {
        this.E.drawImage(e(r.i, 0), i + this.aA.getWidth() - b.C - 2, b.B + 2, 20);
      } 
      this.V = n;
    } else {
      int i1 = this.X.b;
      this.E.drawImage(e(i1, 0), k, b.B + 2, 20);
      k = k + b.C + 1;
      for (byte b = 0; b < paramVector.size(); b++) {
        if ((m = ((a)paramVector.elementAt(b)).b) == i1) {
          if (n == 1) {
            m = r.f;
          } else if (n == 2) {
            m = r.g;
          } else {
            m = r.h;
          } 
          if (n == paramInt2) {
            this.E.drawImage(e(m, 1), k, b.B + 2, 20);
          } else {
            this.E.drawImage(e(m, 0), k, b.B + 2, 20);
          } 
          k = k + b.C + 1;
          this.Z.addElement(paramVector.elementAt(b));
          paramVector.elementAt(b);
          Object object = null;
          n++;
        } 
      } 
      if (paramInt2 == this.V) {
        this.E.drawImage(e(r.i, 1), i + this.aA.getWidth() - b.C - 2, b.B + 2, 20);
      } else {
        this.E.drawImage(e(r.i, 0), i + this.aA.getWidth() - b.C - 2, b.B + 2, 20);
      } 
      this.V = n;
    } 
    l();
  }
  
  private Image e(int paramInt1, int paramInt2) {
    byte b = 0;
    if (paramInt1 == r.i) {
      b = 0;
    } else if (paramInt1 == r.c) {
      b = 1;
    } else if (paramInt1 == r.a) {
      b = 2;
    } else if (paramInt1 == r.b) {
      b = 3;
    } else if (paramInt1 == r.d || paramInt1 == r.e) {
      b = 4;
    } else if (paramInt1 == r.f) {
      b = 0;
    } else if (paramInt1 == r.g) {
      b = 1;
    } else if (paramInt1 == r.h) {
      b = 2;
    } 
    return (paramInt1 == r.i || paramInt1 == r.c || paramInt1 == r.b || paramInt1 == r.a || paramInt1 == r.d || paramInt1 == r.e) ? a.a(this.aB, paramInt2 * b.C, b * b.D, b.C, b.D) : a.a(this.aC, paramInt2 * b.C, b * b.D, b.C, b.D);
  }
  
  private void x() {
    this.E.drawImage(this.an, 0, 0, 20);
    this.ai = false;
  }
  
  public final void a(e parame, int paramInt1, int paramInt2, boolean paramBoolean, com.pheephoo.mjgame.engine.i parami) {
    this.O.k = parami;
    if (paramBoolean) {
      this.T--;
      f();
    } 
    l();
    if (paramInt2 < this.O.b)
      paramInt2 += 4; 
    a(parame, paramInt1, paramInt2 - this.O.b);
    if (paramInt2 == this.O.b + 1) {
      k(1);
    } else if (paramInt2 == this.O.b + 2) {
      k(2);
    } else if (paramInt2 == this.O.b + 3) {
      k(3);
    } 
    l();
    if (this.b <= 4)
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException interruptedException) {} 
    l();
  }
  
  private void a(e parame, int paramInt1, int paramInt2) {
    d(paramInt1 - 1, paramInt2);
    a(parame, paramInt2);
  }
  
  public final void f() {
    int i = (this.ak - 11 * b.f) / 2;
    int j = this.T % 11;
    int k = this.T / 11;
    this.E.drawImage(this.ao, i + b.f * j, b.y + b.g * k, 20);
    this.E.drawImage(this.ao, i + b.f * j, b.y + b.g * k + b.i, 20);
    if (k > 0)
      this.E.drawImage(this.as, i + b.f * j, b.y + b.g * k, 20); 
  }
  
  private void p(int paramInt) {
    com.pheephoo.mjgame.engine.i i;
    switch (paramInt) {
      case -3:
        this.ac = (this.ac + 1) % (this.O.h.e() + 1);
        if (this.ac == 0 && this.O.j == null)
          this.ac = 1; 
        q();
        return;
      case -4:
        if (this.ac == 1 && this.O.j == null) {
          this.ac += this.O.h.e();
        } else if (this.ac == 0) {
          this.ac += this.O.h.e() + 1;
        } 
        this.ac--;
        q();
        return;
      case -5:
        this.ab = -1;
        if (this.ac == 0) {
          com.pheephoo.mjgame.engine.i i1 = new com.pheephoo.mjgame.engine.i(this.O.j.g, this.O.j.h);
          this.O.j = null;
          j(0);
          a(i1);
          try {
            if (this.aH != null && this.b <= 4) {
              this.aH.start();
              if (this.P == 0)
                Thread.sleep(150L); 
            } 
          } catch (MediaException mediaException) {
          
          } catch (InterruptedException interruptedException) {}
          l();
          this.N.a(i1);
          k(1);
          return;
        } 
        i = this.O.h.a(this.O.h.e() - this.ac);
        if (this.O.j != null) {
          if (this.P == 0)
            try {
              Thread.sleep(350L);
            } catch (InterruptedException interruptedException) {} 
          this.O.a(this.O.j);
        } 
        this.O.h.b(i);
        j(0);
        a(i);
        try {
          if (this.aH != null)
            this.aH.start(); 
        } catch (MediaException mediaException) {}
        l();
        this.O.j = null;
        this.N.a(i);
        k(1);
        break;
    } 
  }
  
  private void q(int paramInt) {
    switch (paramInt) {
      case -3:
        if (this.W > 1)
          this.W--; 
        a(this.aa, this.W, this.Y);
        if (this.aa == 0)
          u(this.W - 1); 
        if (this.aa == 1) {
          a a1 = this.Z.elementAt(this.W - 1);
          c(a1.c, a1.d);
          return;
        } 
        break;
      case -4:
        if (this.W < this.V)
          this.W++; 
        a(this.aa, this.W, this.Y);
        if (this.aa == 0)
          u(this.W - 1); 
        if (this.aa == 1) {
          if (this.W < this.V) {
            a a1 = this.Z.elementAt(this.W - 1);
            c(a1.c, a1.d);
            return;
          } 
          j(0);
          if (this.O.j != null) {
            r();
            return;
          } 
        } 
        break;
      case -5:
        x();
        if (this.W != this.V) {
          a a1 = this.Z.elementAt(this.W - 1);
          this.X = new a(a1.b, a1.c, a1.d);
          if (this.aa == 0 && v(this.X.b)) {
            this.aa = 1;
            a(1, 1, this.Y);
            this.W = 1;
            c(a1.c, a1.d);
            return;
          } 
          if (this.aa == 1) {
            this.ab = -1;
            this.O.j = null;
            e(this.W);
            this.Y.removeElementAt(0);
            this.N.a(r.c, true, this.O.b);
            a();
            return;
          } 
          this.ab = -1;
          this.O.j = null;
          e(this.W);
          this.Y.removeElementAt(0);
          this.N.a(r.c, true, this.O.b);
          a();
          return;
        } 
        this.ab = -1;
        s();
        this.Y.removeElementAt(0);
        this.N.a(r.c, false, this.O.b);
        break;
    } 
  }
  
  private void r(int paramInt) throws IOException {
    com.pheephoo.mjgame.engine.i i = null;
    switch (paramInt) {
      case -3:
        if (this.W > 1)
          this.W--; 
        a(this.aa, this.W, this.Y);
        if (this.aa == 0)
          u(this.W - 1); 
        if (this.aa == 1) {
          a a1;
          if (!(a1 = this.Z.elementAt(this.W - 1)).e) {
            a(a1.c, a1.d);
            return;
          } 
          b(a1.c, a1.d);
          return;
        } 
        break;
      case -4:
        if (this.W < this.V)
          this.W++; 
        a(this.aa, this.W, this.Y);
        if (this.aa == 0)
          u(this.W - 1); 
        if (this.aa == 1) {
          if (this.W < this.V) {
            a a1;
            if (!(a1 = this.Z.elementAt(this.W - 1)).e) {
              a(a1.c, a1.d);
              return;
            } 
            b(a1.c, a1.d);
            return;
          } 
          j(0);
          if (this.O.j != null) {
            r();
            return;
          } 
        } 
        break;
      case -5:
        x();
        if (this.W != this.V) {
          a a1 = this.Z.elementAt(this.W - 1);
          if (this.aa == 0 && this.V - 1 < this.Y.size()) {
            this.aa = 1;
            this.X = new a(a1.b, a1.c, a1.d);
            a(1, 1, this.Y);
            if (!a1.e) {
              a(a1.c, a1.d);
              return;
            } 
            b(a1.c, a1.d);
            return;
          } 
          if (this.aa == 1) {
            this.ab = -1;
            if (!a1.e) {
              i = f(this.W);
            } else {
              i = a(this.W, this.O.j);
            } 
            this.N.a(true, i);
            return;
          } 
          this.ab = -1;
          if (!a1.e) {
            i = f(this.W);
          } else {
            i = a(this.W, this.O.j);
          } 
          this.N.a(true, i);
          return;
        } 
        this.ab = 1;
        if (this.U == 0 || this.L == d.aH || this.L == d.aI || this.L == d.aJ || this.L == d.aK) {
          this.N.a(new com.pheephoo.mjgame.engine.i(1, 1));
          return;
        } 
        q();
        break;
    } 
  }
  
  private void s(int paramInt) {
    switch (paramInt) {
      case -3:
        if (this.W > 1)
          this.W--; 
        a(this.aa, this.W, this.Y);
        if (this.aa == 0) {
          u(this.W - 1);
          return;
        } 
        break;
      case -4:
        if (this.W < this.V)
          this.W++; 
        a(this.aa, this.W, this.Y);
        if (this.aa == 0) {
          u(this.W - 1);
          return;
        } 
        break;
      case -5:
        x();
        if (this.W != this.V) {
          this.ab = -1;
          m();
          return;
        } 
        this.ab = 1;
        if (this.ae) {
          q();
          return;
        } 
        if (!this.R)
          s(); 
        this.Y.removeElementAt(0);
        this.N.a(r.d, false, this.O.b);
        break;
    } 
  }
  
  private void t(int paramInt) throws IOException {
    switch (paramInt) {
      case -3:
        if (this.W > 1)
          this.W--; 
        a(this.aa, this.W, this.Y);
        if (this.aa == 0)
          u(this.W - 1); 
        if (this.aa == 1) {
          a a1;
          if (!(a1 = this.Z.elementAt(this.W - 1)).e) {
            a(a1.c, a1.d);
            return;
          } 
          b(a1.c, a1.d);
          return;
        } 
        break;
      case -4:
        if (this.W < this.V)
          this.W++; 
        a(this.aa, this.W, this.Y);
        if (this.aa == 0)
          u(this.W - 1); 
        if (this.aa == 1) {
          if (this.W < this.V) {
            a a1;
            if (!(a1 = this.Y.elementAt(this.W - 1)).e) {
              a(a1.c, a1.d);
              return;
            } 
            b(a1.c, a1.d);
            return;
          } 
          j(0);
          if (this.O.j != null) {
            r();
            return;
          } 
        } 
        break;
      case -5:
        x();
        if (this.W != this.V) {
          a a1 = this.Z.elementAt(this.W - 1);
          this.X = new a(a1.b, a1.c, a1.d);
          if (this.aa == 0 && v(this.X.b)) {
            this.aa = 1;
            a(1, 1, this.Y);
            a(a1.c, a1.d);
            return;
          } 
          if (this.aa == 1) {
            this.ab = -1;
            this.O.j = null;
            d(this.W);
            a a3 = this.Y.elementAt(this.W - 1);
            this.N.a(a3.b, true, this.O.b);
            a();
            return;
          } 
          this.ab = -1;
          this.O.j = null;
          d(this.W);
          a a2 = this.Y.elementAt(this.W - 1);
          this.N.a(a2.b, true, this.O.b);
          if (a2.b != r.b) {
            a();
            return;
          } 
          break;
        } 
        this.ab = -1;
        s();
        k(this.aG);
        this.N.a(r.b, false, this.O.b);
        break;
    } 
  }
  
  public final void a() {
    n();
    this.ab = 1;
    q();
    this.h = System.currentTimeMillis();
  }
  
  private void u(int paramInt) {
    if (paramInt < this.Y.size()) {
      a a1;
      if ((a1 = this.Y.elementAt(paramInt)).b == r.b || a1.b == r.a) {
        if (this.O.k == null) {
          this.ab = 3;
        } else {
          this.ab = 4;
        } 
        if (this.O.j != null) {
          this.ab = 3;
          return;
        } 
        this.ab = 4;
        return;
      } 
      if (a1.b == r.c) {
        this.ab = 5;
        return;
      } 
      if (a1.b == r.d)
        this.ab = 6; 
    } 
  }
  
  private boolean v(int paramInt) {
    byte b1 = 0;
    for (byte b2 = 0; b2 < this.Y.size(); b2++) {
      if (paramInt == ((a)this.Y.elementAt(b2)).b)
        b1++; 
      if (b1 == 2)
        return true; 
    } 
    return false;
  }
  
  public final int b(int paramInt) {
    for (byte b = 0; b < 4; b++) {
      if ((this.G[b]).a == paramInt)
        return (this.G[b]).b; 
    } 
    return 0;
  }
  
  public final String c(int paramInt) {
    for (byte b = 0; b < 4; b++) {
      if ((this.G[b]).a == paramInt)
        return (this.G[b]).c; 
    } 
    return "";
  }
  
  private void y() {
    o(0);
    try {
      Image image = Image.createImage("/res/clear_hand.png");
      this.E.drawImage(image, (this.ak - image.getWidth()) / 2, (this.al - image.getHeight()) / 2, 20);
    } catch (IOException iOException) {}
    l();
    this.ab = 8;
    this.h = System.currentTimeMillis();
  }
  
  public final void b() {
    this.aj = false;
    o(0);
    try {
      Image image = Image.createImage("/res/clear_hand.png");
      this.E.drawImage(image, (this.ak - image.getWidth()) / 2, (this.al - image.getHeight()) / 2, 20);
    } catch (IOException iOException) {}
    l();
    z();
    this.ab = 8;
    this.I = 2;
    this.J = false;
    z();
    n();
    this.h = System.currentTimeMillis();
  }
  
  public final void a(int paramInt1, e parame1, e parame2, int paramInt2, int paramInt3, int paramInt4, e parame3, e parame4, int paramInt5, int paramInt6, int paramInt7, e parame5, e parame6, int paramInt8, int paramInt9, int paramInt10, e parame7, e parame8, int paramInt11, int paramInt12, int paramInt13) {
    this.g = 0;
    if (this.O.j != null)
      try {
        Thread.sleep(3000L);
      } catch (InterruptedException interruptedException) {} 
    this.Q = paramInt1;
    for (byte b = 0; b < 4; b++) {
      if ((this.G[b]).a == 0)
        (this.G[b]).f[this.i] = paramInt4; 
      if ((this.G[b]).a == 1)
        (this.G[b]).f[this.i] = paramInt7; 
      if ((this.G[b]).a == 2)
        (this.G[b]).f[this.i] = paramInt10; 
      if ((this.G[b]).a == 3)
        (this.G[b]).f[this.i] = paramInt13; 
    } 
    String str = null;
    if (paramInt1 == this.O.b) {
      str = "win_bg.jpg";
    } else {
      str = "lose_bg.jpg";
    } 
    try {
      if (paramInt1 < 4) {
        this.E.drawImage(Image.createImage("/res/" + str), 0, 0, 20);
        l();
        Thread.sleep(3500L);
        this.aj = false;
      } else if (this.aj) {
        n();
        y();
        this.j = paramInt1;
        this.k = parame1;
        this.l = parame2;
        this.m = paramInt2;
        this.n = paramInt3;
        this.o = paramInt4;
        this.p = parame3;
        this.q = parame4;
        this.r = paramInt5;
        this.s = paramInt6;
        this.t = paramInt7;
        this.u = parame5;
        this.v = parame6;
        this.w = paramInt8;
        this.x = paramInt9;
        this.y = paramInt10;
        this.z = parame7;
        this.A = parame8;
        this.B = paramInt11;
        this.C = paramInt12;
        this.D = paramInt13;
        return;
      } 
      this.E.setColor(185, 134, 88);
      this.E.fillRect(0, 0, this.ak, this.al);
      this.E.drawImage(n.d, 0, 0, 20);
      this.E.setFont(Font.getFont(64, 0, 8));
      this.E.setColor(255, 255, 255);
      a(parame1, parame2, b.E);
      f(b(0), b.H);
      this.E.drawString("E  D=" + ((paramInt2 > 0) ? "+" : "") + paramInt2 + "  P=" + ((paramInt3 > 0) ? "+" : "") + paramInt3 + "  T=" + paramInt4, b.I + 2, b.J, 20);
      l();
      a(parame3, parame4, b.E + b.G);
      f(b(1), b.H + b.G);
      this.E.drawString("S  D=" + ((paramInt5 > 0) ? "+" : "") + paramInt5 + "  P=" + ((paramInt6 > 0) ? "+" : "") + paramInt6 + "  T=" + paramInt7, b.I + 2, b.J + b.G, 20);
      l();
      a(parame5, parame6, b.E + b.G * 2);
      f(b(2), b.H + b.G * 2);
      this.E.drawString("W  D=" + ((paramInt8 > 0) ? "+" : "") + paramInt8 + "  P=" + ((paramInt9 > 0) ? "+" : "") + paramInt9 + "  T=" + paramInt10, b.I + 2, b.J + b.G * 2, 20);
      l();
      a(parame7, parame8, b.E + b.G * 3);
      f(b(3), b.H + b.G * 3);
      this.E.drawString("N  D=" + ((paramInt11 > 0) ? "+" : "") + paramInt11 + "  P=" + ((paramInt12 > 0) ? "+" : "") + paramInt12 + "  T=" + paramInt13, b.I + 2, b.J + b.G * 3, 20);
      if (paramInt1 < 4)
        this.E.drawImage(Image.createImage("/res/winhand.png"), b.K, b.H + 1 + paramInt1 * b.G, 20); 
      l();
      if (this.b < 6)
        this.c = false; 
      this.k = null;
      this.l = null;
      this.p = null;
      this.q = null;
      this.u = null;
      this.v = null;
      this.z = null;
      this.A = null;
      this.ab = 8;
      this.h = System.currentTimeMillis();
    } catch (InterruptedException interruptedException) {
    
    } catch (IOException iOException) {}
    this.I = 2;
    this.J = false;
    z();
    n();
  }
  
  private void f(int paramInt1, int paramInt2) {
    this.E.drawImage(n.b(paramInt1), b.I, paramInt2, 20);
  }
  
  private void a(e parame1, e parame2, int paramInt) {
    byte b1 = 1;
    int i = -1;
    com.pheephoo.mjgame.engine.i i1 = null;
    byte b2 = 0;
    byte b3;
    for (b3 = 0; b3 < parame2.e(); b3++) {
      i1 = parame2.a(b3);
      if (i != i1.i) {
        i = i1.i;
        b1 = 1;
      } 
      if (b1 != 4) {
        this.E.drawImage(this.az, b.F + b2 * b.h, paramInt + b.g - b.j, 20);
        this.E.drawImage(a(i1, com.pheephoo.mjgame.engine.i.c), b.F + b2 * b.h, paramInt - b.j, 20);
      } else {
        this.E.drawImage(n(com.pheephoo.mjgame.engine.i.c), b.F + (b2 - 2) * b.h, paramInt, 20);
        b2--;
      } 
      b1++;
      b2++;
    } 
    l();
    for (b3 = 0; b3 <= parame1.e() - 1; b3++) {
      this.E.drawImage(this.az, b.F + (15 - parame1.e()) * b.h + b3 * b.h, paramInt + b.g - b.j, 20);
      this.E.drawImage(a(parame1.a(b3), com.pheephoo.mjgame.engine.i.c), b.F + (15 - parame1.e()) * b.h + b3 * b.h, paramInt - b.j, 20);
    } 
    l();
  }
  
  private void g(int paramInt1, int paramInt2) {
    if (this.b > 4)
      return; 
    if (this.aI != null) {
      this.aI.close();
      this.aI = null;
    } 
    String str = "";
    if (paramInt1 >= 5) {
      if (paramInt2 == r.c) {
        str = "/res/mchi.amr";
      } else if (paramInt2 == r.b) {
        str = "/res/mkon.amr";
      } else if (paramInt2 == r.a) {
        str = "/res/mpon.amr";
      } 
    } else if (paramInt2 == r.c) {
      str = "/res/fchi.amr";
    } else if (paramInt2 == r.b) {
      str = "/res/fkon.amr";
    } else if (paramInt2 == r.a) {
      str = "/res/fpon.amr";
    } 
    try {
      this.aN = getClass().getResourceAsStream(str);
      this.aI = Manager.createPlayer(this.aN, "audio/amr");
      this.aI.prefetch();
      if (this.aI != null) {
        this.aL = (VolumeControl)this.aI.getControl("VolumeControl");
        if (this.aL != null)
          this.aL.setLevel(this.aP); 
      } 
      Thread.sleep(200L);
      this.aI.start();
      Thread.sleep(600L);
      return;
    } catch (MediaException mediaException) {
      return;
    } catch (IOException iOException) {
      return;
    } catch (RuntimeException runtimeException) {
      return;
    } catch (InterruptedException interruptedException) {
      return;
    } 
  }
  
  private void z() {
    System.gc();
    this.T = 0;
    if (this.Q != 0 && this.Q != -1)
      for (byte b1 = 0; b1 < 4; b1++) {
        if ((this.G[b1]).a == 0)
          (this.G[b1]).a = 4; 
        (this.G[b1]).a--;
      }  
    if (this.Q == -1)
      if (this.i != 0) {
        for (byte b1 = 0; b1 < 4; b1++)
          (this.G[b1]).f[this.i] = (this.G[b1]).f[this.i - 1]; 
      } else {
        for (byte b1 = 0; b1 < 4; b1++)
          (this.G[b1]).f[this.i] = 2000; 
      }  
    for (byte b = 0; b < 4; b++)
      (this.G[b]).e = (this.G[b]).f[this.i]; 
    this.Q = -1;
    this.i++;
  }
  
  private void A() {
    if (this.P == 0)
      try {
        if (this.aJ != null)
          this.aJ.close(); 
        InputStream inputStream = getClass().getResourceAsStream("/res/shipai.amr");
        this.aJ = Manager.createPlayer(inputStream, "audio/amr");
        this.aJ.realize();
        VolumeControl volumeControl;
        if (this.aJ != null && (volumeControl = (VolumeControl)this.aJ.getControl("VolumeControl")) != null)
          volumeControl.setLevel(this.aP); 
        Thread.sleep(200L);
        this.aJ.start();
      } catch (IOException iOException) {
      
      } catch (MediaException mediaException) {
      
      } catch (InterruptedException interruptedException) {} 
    if (this.P == 0) {
      this.N.f();
    } else if (this.I == 2) {
      this.N.f();
    } 
    if (!this.J)
      this.J = true; 
  }
  
  private void B() {
    this.E.drawImage(this.aq, 0, 0, 20);
    l();
    this.N.d();
  }
  
  private void C() {
    try {
      l();
      Thread.sleep(10L);
      return;
    } catch (InterruptedException interruptedException) {
      return;
    } 
  }
  
  private void D() {
    if (this.aH != null)
      this.aH.close(); 
    if (this.aI != null) {
      this.aI.close();
      this.aI = null;
    } 
    try {
      this.aO = getClass().getResourceAsStream("/res/pai.amr");
      this.aH = Manager.createPlayer(this.aO, "audio/amr");
      this.aH.prefetch();
      if (this.aH != null) {
        this.aK = (VolumeControl)this.aH.getControl("VolumeControl");
        if (this.aK != null) {
          this.aK.setLevel(this.aP);
          return;
        } 
      } 
    } catch (MediaException mediaException) {
      return;
    } catch (IOException iOException) {}
  }
  
  public final void d() {
    this.e = true;
    if (this.af != null) {
      this.af.cancel();
      this.ag.cancel();
    } 
    this.M.a(d.Z);
  }
  
  public final void a(String paramString) {
    this.e = true;
    if (this.af != null) {
      this.af.cancel();
      this.ag.cancel();
    } 
    this.M.a(d.Z, paramString);
  }
  
  public final void g() {
    if (b.e == 1) {
      this.E.setColor(0, 128, 64);
      this.E.fillRect(0, 0, this.ak, this.al);
      this.E.setColor(0, 0, 0);
    } else {
      this.E.setColor(86, 55, 44);
      this.E.fillRect(0, 0, this.ak, this.al);
      this.E.setColor(0, 0, 0);
    } 
    this.aE = a.a(this.am, this.ak - b.k - b.v, (getHeight() - 13 * b.k) / 2 + 0 * b.k, b.v, b.v);
  }
  
  public final void h() {
    k.a();
    this.F = k.b;
    (this.G[0]).b = this.F;
    this.aQ = k.a;
    if (this.aQ == 0) {
      this.aP = 0;
    } else if (this.aQ == 1) {
      this.aP = 50;
    } else if (this.aQ == 2) {
      this.aP = 80;
    } else if (this.aQ == 3) {
      this.aP = 100;
    } 
    try {
      D();
      if (this.aH != null) {
        this.aK = (VolumeControl)this.aH.getControl("VolumeControl");
        if (this.aK != null) {
          this.aK.setLevel(this.aP);
          return;
        } 
      } 
    } catch (RuntimeException runtimeException) {}
  }
  
  public final void e() {
    this.e = true;
    if (this.af != null) {
      this.af.cancel();
      this.ag.cancel();
      this.af = null;
      this.ag = null;
    } 
    if (this.aH != null)
      this.aH.close(); 
    if (this.aJ != null)
      this.aJ.close(); 
    if (this.aI != null)
      this.aI.close(); 
    this.M = null;
  }
  
  public final void b(String paramString) {
    this.M.a(d.aS, paramString);
  }
  
  public final void i() {
    if (!this.J)
      return; 
    this.E.drawImage(n.c, 0, b.A, 20);
    this.E.drawImage(n.a(0), (this.ak - b.ad) / 2, this.al - b.ae, 20);
    l();
  }
  
  static final int a(l paraml) {
    return paraml.ak;
  }
  
  static final int b(l paraml) {
    return paraml.al;
  }
  
  class com/pheephoo/mjgame/ui/i extends TimerTask {
    final l a;
    
    com/pheephoo/mjgame/ui/i(l this$0) {
      this.a = this$0;
    }
    
    public final void run() {
      if (this.a.f && !this.a.e && System.currentTimeMillis() - this.a.h > 500L)
        this.a.g++; 
      if (this.a.e)
        return; 
      if (this.a.g > d.D) {
        this.a.c();
        this.a.keyPressed(-5);
        this.a.keyPressed(-5);
        return;
      } 
      if (this.a.c && this.a.b > 4) {
        this.a.c();
        this.a.keyPressed(-5);
        this.a.keyPressed(-5);
        this.a.keyPressed(-5);
      } 
    }
  }
  
  class com/pheephoo/mjgame/ui/f implements Runnable {
    final l a;
    
    com/pheephoo/mjgame/ui/f(l this$0) {
      this.a = this$0;
    }
    
    public final void run() {
      int i = 0;
      byte b = 0;
      boolean bool = false;
      StringBuffer stringBuffer;
      (stringBuffer = new StringBuffer()).append("Connecting  ");
      while (this.a.d) {
        try {
          this.a.E.drawImage(n.a(i), (l.a(this.a) - b.ad) / 2, l.b(this.a) - b.ae, 20);
          this.a.repaint();
          this.a.serviceRepaints();
          Thread.sleep(500L);
        } catch (InterruptedException interruptedException) {}
        i = (i + 1) % 4;
        if (b == 3)
          this.a.d = false; 
        b++;
      } 
    }
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgam\\ui\l.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */