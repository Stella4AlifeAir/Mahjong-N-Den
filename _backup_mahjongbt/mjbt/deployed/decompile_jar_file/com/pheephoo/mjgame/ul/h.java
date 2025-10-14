package com.pheephoo.mjgame.ui;

import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.b;
import com.pheephoo.mjgame.d;
import com.pheephoo.mjgame.engine.k;
import com.pheephoo.mjgame.form.e;
import com.pheephoo.mjgame.network.b;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class h extends Canvas {
  private int d = 0;
  
  private int e = 0;
  
  public com.pheephoo.mjgame.engine.h a;
  
  public String[] b;
  
  public int c = 0;
  
  private MJGame f;
  
  private static int g = 0;
  
  private static int h = 1;
  
  private static int i = 2;
  
  private static int j = 10;
  
  private static int k = 11;
  
  private static int l = 12;
  
  private static int m = 13;
  
  private Graphics n;
  
  private Image o;
  
  private int p = 0;
  
  private int q = 0;
  
  private Image r;
  
  private Image s;
  
  private Image t;
  
  private Image u;
  
  private Image v;
  
  private Image w;
  
  private int x = 0;
  
  private int y = 0;
  
  private int z = 0;
  
  private int A = 0;
  
  private Vector B = new Vector();
  
  private boolean C = false;
  
  private int D = 0;
  
  private long E = 0L;
  
  public h(MJGame paramMJGame) {
    setFullScreenMode(true);
    this.p = getWidth();
    this.q = getHeight();
    this.f = paramMJGame;
    this.o = Image.createImage(getWidth(), getHeight());
    this.n = this.o.getGraphics();
    try {
      this.r = Image.createImage("/res/public_net.jpg");
      this.s = Image.createImage("/res/table_info_txt.png");
      this.u = Image.createImage("/res/waiting_host.png");
      this.v = Image.createImage("/res/waiting_nothost.png");
      this.w = Image.createImage("/res/new.png");
      this.t = Image.createImage("/res/onlineico.png");
      return;
    } catch (IOException iOException) {
      throw new Error();
    } 
  }
  
  protected final void paint(Graphics paramGraphics) {
    paramGraphics.drawImage(this.o, 0, 0, 20);
  }
  
  public final void a(String paramString) {
    g();
    this.f.a(d.ab, paramString);
    repaint();
    serviceRepaints();
  }
  
  public final void a(String[] paramArrayOfString) {
    this.b = paramArrayOfString;
    e e = new e(this.f, this);
    this.f.d = (Displayable)e.a;
  }
  
  public final void a(Vector paramVector) {
    this.B = paramVector;
    if (this.B.size() == 1 && ((b)this.B.elementAt(0)).d == 0)
      this.B.removeElementAt(0); 
    this.B.insertElementAt(new b(k.c, k.d, 0, 0), 0);
    l();
    this.E = System.currentTimeMillis();
    this.D = 1;
  }
  
  public final void a(int paramInt) {
    n.d();
    this.f.a().setCurrent((Displayable)this);
    g();
    b b = new b(k.c, k.d, 1, paramInt);
    c(b);
    this.E = System.currentTimeMillis();
    repaint();
    serviceRepaints();
  }
  
  public final void b(int paramInt) {
    this.f.d = (Displayable)this;
    this.f.a().setCurrent((Displayable)this);
    this.a.b(paramInt);
  }
  
  public final void a(b paramb) {
    int i = this.B.size();
    for (byte b1 = 0; b1 < i; b1++) {
      b b2 = this.B.elementAt(b1);
      if (paramb.d > b2.d) {
        this.B.insertElementAt(paramb, b1 + 1);
        break;
      } 
    } 
    l();
  }
  
  public final void b(b paramb) {
    n.d();
    this.f.a().setCurrent((Displayable)this);
    int i = this.B.size();
    for (byte b1 = 0; b1 < i; b1++) {
      b b2;
      if ((b2 = this.B.elementAt(b1)).d == paramb.d) {
        b2.a = paramb.a;
        b2.b = paramb.b;
        b2.c = paramb.c;
        b2.e = paramb.e;
        break;
      } 
    } 
    g();
    if (this.c == 1) {
      c(paramb);
    } else {
      l();
    } 
    repaint();
    serviceRepaints();
  }
  
  public final void c(int paramInt) {
    if (this.C) {
      n.d();
      this.f.a().setCurrent((Displayable)this);
    } 
    int i = this.B.size();
    for (byte b = 0; b < i; b++) {
      b b1;
      if ((b1 = this.B.elementAt(b)).d == paramInt) {
        this.B.removeElementAt(b);
        break;
      } 
    } 
    l();
  }
  
  public final void a(int paramInt1, int paramInt2) {
    Alert alert = null;
    if (paramInt1 != 0 && paramInt1 != 1 && paramInt1 != 2) {
      if (paramInt1 == 3) {
        this.c = 0;
        g();
        l();
        if (this.B.size() == 0)
          this.a.j(); 
        (alert = new Alert("", "host has closed the table", null, AlertType.INFO)).setTimeout(-2);
        this.x = 0;
        this.f.a().setCurrent(alert, (Displayable)this);
        this.c = 0;
        return;
      } 
      if (paramInt1 == 4) {
        g();
        l();
        (alert = new Alert("", "Table is full. Please join another table", null, AlertType.INFO)).setTimeout(-2);
        this.c = 0;
        this.x = 0;
        this.f.a().setCurrent(alert, (Displayable)this);
        this.c = 0;
        return;
      } 
      if (paramInt1 == 10) {
        this.e = paramInt2;
        e e = new e(this, "rejoin", "Would you like to rejoin your last game?", "Yes", "No", l, m);
        this.f.d = (Displayable)e;
      } 
    } 
  }
  
  public final void a() {
    this.c = 2;
    this.f.a(d.aT);
  }
  
  public final void d(int paramInt) {
    int i = paramInt / 100;
    int j = paramInt % 100;
    String[] arrayOfString = { d.q, String.valueOf(d.o) + i + "." + j + " " + d.p, d.s };
    e e = new e(this, d.n, arrayOfString, "Ok", "Cancel", j, k);
    this.f.a().setCurrent((Displayable)e);
    this.f.d = (Displayable)this;
  }
  
  public final void b() {
    e e = new e(this, b.b, d.v, "Ok", "Cancel", g, h);
    this.f.a().setCurrent((Displayable)e);
    this.f.d = (Displayable)this;
  }
  
  public final void c() {
    this.f.a(this.a.n(), this.a.o());
    e e = new e(this, b.d, String.valueOf(d.x) + "Nick=" + this.a.h + "\nPhoneNum=" + this.a.i, "Ok", null, i, 0);
    this.f.a().setCurrent((Displayable)e);
    this.f.d = (Displayable)this;
  }
  
  public final void d() {
    this.f.d = (Displayable)this;
    this.f.a(d.at);
  }
  
  public final void e() {
    this.f.d = (Displayable)this;
    this.f.a(d.av);
  }
  
  public final void e(int paramInt) {
    if (paramInt == g) {
      this.a.r();
      return;
    } 
    if (paramInt == h) {
      this.f.a(d.aq);
      return;
    } 
    if (paramInt == i) {
      this.f.a(d.aQ);
      return;
    } 
    if (paramInt == j) {
      this.a.p();
      return;
    } 
    if (paramInt == k) {
      this.f.a(d.aQ);
      return;
    } 
    if (paramInt == l) {
      f(this.e);
      return;
    } 
    if (paramInt == m)
      f(); 
  }
  
  private void f() {
    this.a.j();
    repaint();
    serviceRepaints();
  }
  
  private void g() {
    this.n.drawImage(n.d, 0, 0, 20);
    this.n.drawImage(this.r, 0, 0, 20);
    if (this.c == 0) {
      this.n.drawImage(this.s, 0, this.r.getHeight(), 20);
      this.n.drawImage(n.c(0), 0, this.q - b.aG, 20);
      return;
    } 
    if (this.c == 1) {
      this.n.drawImage(this.s, 0, this.r.getHeight(), 20);
      if (this.C) {
        int k = (this.p - this.u.getWidth()) / 2;
        int m = this.r.getHeight() + this.s.getHeight() + 10;
        this.n.drawImage(this.u, k, m, 20);
        this.n.drawImage(n.c(2), 0, this.q - b.aG, 20);
        return;
      } 
      int i = (this.p - this.u.getWidth()) / 2;
      int j = this.r.getHeight() + this.s.getHeight() + 10;
      this.n.drawImage(this.v, i, j, 20);
      this.n.drawImage(n.c(3), 0, this.q - b.aG, 20);
    } 
  }
  
  private void c(b paramb) {
    int i = b.aH;
    if (paramb.d < 10) {
      this.n.drawImage(n.d(paramb.d), b.aP, i, 20);
    } else if (paramb.d < 100) {
      int j = paramb.d / 10;
      int k = paramb.d % 10;
      this.n.drawImage(n.d(j), b.aP - b.aI, i, 20);
      this.n.drawImage(n.d(k), b.aP, i, 20);
    } else {
      int j = paramb.d / 100;
      int k;
      int m = (k = paramb.d / 10) % 10;
      int n = paramb.d % 10;
      this.n.drawImage(n.d(j), b.aP - b.aI * 2, i, 20);
      this.n.drawImage(n.d(m), b.aP - b.aI, i, 20);
      this.n.drawImage(n.d(n), b.aP, i, 20);
    } 
    this.n.drawImage(n.d(paramb.a), b.aQ, i, 20);
    this.n.drawImage(n.d(paramb.b), b.aR, i, 20);
    this.n.drawImage(n.d(paramb.c), b.aS, i, 20);
  }
  
  private void h() {
    this.c = 0;
    this.x = 0;
    this.a.g();
  }
  
  private void f(int paramInt) {
    this.c = 1;
    this.C = false;
    this.f.a().setCurrent((Displayable)this);
    repaint();
    serviceRepaints();
    this.a.a(paramInt, k.b, k.e);
  }
  
  private void i() {
    a(0, "joinRoomHelper()");
    b b = this.B.elementAt(this.x);
    if (this.a.a && b.e == 1) {
      Alert alert;
      (alert = new Alert("", "not allowed to join room", null, AlertType.INFO)).setTimeout(-2);
      this.x = 0;
      this.f.a().setCurrent(alert, (Displayable)this);
      a(0, "join room helper...not allowed finished");
      this.c = 0;
      return;
    } 
    this.c = 1;
    this.C = false;
    g();
    repaint();
    serviceRepaints();
    this.a.a(b.d, k.b, k.e);
  }
  
  private void j() {
    this.c = 1;
    this.C = true;
    this.a.a(k.c, k.d, k.b, k.e);
  }
  
  private void k() {
    this.a.i();
  }
  
  private void l() {
    Vector vector = this.B;
    if (this.x == 0)
      this.y = 0; 
    if (this.c != 0 || vector == null)
      return; 
    this.z = vector.size();
    if (this.x >= this.z || this.y >= this.z) {
      this.x = this.z - 1;
      this.y = this.x;
    } 
    g();
    int i = b.aH;
    int j = b.aO;
    int k = n.q.getHeight();
    if (this.z > j) {
      this.n.drawImage(n.q, this.p - n.q.getWidth(), this.q - b.aG - k, 20);
      if (this.y == 0 || this.y == j - 1)
        this.A = this.x * (k - n.r.getHeight()) / (this.z - 1); 
      this.n.drawImage(n.r, this.p - n.q.getWidth(), this.q - b.aG - k + this.A, 20);
    } 
    for (byte b = 0; b < this.z; b++) {
      b b1 = this.B.elementAt(b);
      int m;
      if ((m = (m = i + b * b.aK) - (this.x - this.y) * b.aK) >= i && m < i + b.aK * j) {
        if (this.x == b) {
          int n;
          this.n.setColor(45, 45, 45);
          if (this.z > j) {
            n = b.aL;
          } else {
            n = this.p;
          } 
          this.n.fillRect(b.aM, m, n, b.aN);
          this.n.setColor(0, 0, 0);
          this.n.drawRect(b.aM, m, n, b.aN);
        } 
        if (b1.e == 1)
          this.n.drawImage(this.t, b.aT, m + b.aU, 20); 
        this.n.setColor(184, 184, 184);
        if (b == 0) {
          this.n.drawImage(this.w, b.aJ, m, 20);
        } else if (b1.d < 10) {
          this.n.drawImage(n.d(b1.d), b.aP, m, 20);
        } else if (b1.d < 100) {
          int n = b1.d / 10;
          int i1 = b1.d % 10;
          this.n.drawImage(n.d(n), b.aP - b.aI, m, 20);
          this.n.drawImage(n.d(i1), b.aP, m, 20);
        } else {
          int n = b1.d / 100;
          int i1;
          int i2 = (i1 = b1.d / 10) % 10;
          int i3 = b1.d % 10;
          this.n.drawImage(n.d(n), b.aP - b.aI * 2, m, 20);
          this.n.drawImage(n.d(i2), b.aP - b.aI, m, 20);
          this.n.drawImage(n.d(i3), b.aP, m, 20);
        } 
        if (b1.a < 10) {
          this.n.drawImage(n.d(b1.a), b.aQ, m, 20);
        } else if (b1.a < 100) {
          int n = b1.a / 10;
          int i1 = b1.a % 10;
          this.n.drawImage(n.d(n), b.aQ - b.aI, m, 20);
          this.n.drawImage(n.d(i1), b.aQ, m, 20);
        } 
        if (b1.b < 10) {
          this.n.drawImage(n.d(b1.b), b.aR, m, 20);
        } else if (b1.b < 100) {
          int n = b1.b / 10;
          int i1 = b1.b % 10;
          this.n.drawImage(n.d(n), b.aR - b.aI, m, 20);
          this.n.drawImage(n.d(i1), b.aR, m, 20);
        } 
        this.n.drawImage(n.d(b1.c), b.aS, m, 20);
      } 
    } 
    repaint();
    serviceRepaints();
  }
  
  protected final void keyPressed(int paramInt) {
    this.z = this.B.size();
    if (System.currentTimeMillis() - 200L < this.E || this.D == 0)
      return; 
    this.E = System.currentTimeMillis();
    switch (paramInt) {
      case -6:
        if (this.c == 0) {
          if (this.x == 0) {
            this.c = 1;
            j();
            break;
          } 
          i();
          break;
        } 
        if (this.c == 1 && this.C) {
          this.c = 2;
          k();
        } 
        break;
      case -7:
        if (this.c == 0) {
          this.f.a(d.ac);
          break;
        } 
        if (this.c == 1) {
          this.c = 0;
          h();
          if (!this.C)
            l(); 
          break;
        } 
        if (this.c == 2)
          this.f.a(d.aO); 
        break;
      case -1:
        if (this.c == 0) {
          this.x--;
          this.y--;
          if (this.x < 0)
            this.x = 0; 
          if (this.y < 0)
            this.y = 0; 
          l();
        } 
        break;
      case -2:
        if (this.c == 0) {
          this.x++;
          this.y++;
          if (this.x == this.z)
            this.x = this.z - 1; 
          if (this.y == b.aO)
            this.y = b.aO - 1; 
          if (this.y >= this.z)
            this.y = this.z - 1; 
          l();
        } 
        break;
      case -3:
        if (this.c == 0) {
          this.x -= b.aO;
          this.y = 0;
          if (this.x < 0)
            this.x = 0; 
          l();
        } 
        break;
      case -4:
        if (this.c == 0) {
          this.x += b.aO;
          this.y = b.aO - 1;
          if (this.x >= this.z)
            this.x = this.z - 1; 
          if (this.y >= this.z)
            this.y = this.z - 1; 
          l();
        } 
        break;
      case -5:
        if (this.c == 0) {
          if (this.x == 0) {
            this.c = 1;
            j();
            break;
          } 
          this.c = 1;
          i();
          break;
        } 
        if (this.c == 1) {
          if (this.C) {
            this.c = 2;
            k();
            break;
          } 
          this.c = 0;
          h();
        } 
        break;
    } 
    repaint();
    serviceRepaints();
  }
  
  private void a(int paramInt, String paramString) {
    if (paramInt >= this.d)
      System.out.println("PublicNetworkCanvas." + paramString); 
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgam\\ui\h.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */