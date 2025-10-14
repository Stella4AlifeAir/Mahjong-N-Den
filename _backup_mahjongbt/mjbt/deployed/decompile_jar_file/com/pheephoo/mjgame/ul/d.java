package com.pheephoo.mjgame.ui;

import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.b;
import com.pheephoo.mjgame.engine.h;
import com.pheephoo.mjgame.engine.k;
import com.pheephoo.mjgame.network.b;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class d extends Canvas {
  private int c = 0;
  
  public h a;
  
  public int b = 0;
  
  private MJGame d;
  
  private Graphics e;
  
  private Image f;
  
  private int g = 0;
  
  private int h = 0;
  
  private Image i;
  
  private Image j;
  
  private Image k;
  
  private Image l;
  
  private Image m;
  
  private Image n;
  
  private int o = 0;
  
  private int p = 0;
  
  private int q = 0;
  
  private int r = 0;
  
  private Vector s = new Vector();
  
  private boolean t = false;
  
  private int u = 0;
  
  private long v = 0L;
  
  public d(MJGame paramMJGame) {
    a(0, "BTServerCanvas()");
    setFullScreenMode(true);
    this.g = getWidth();
    this.h = getHeight();
    this.d = paramMJGame;
    this.f = Image.createImage(getWidth(), getHeight());
    this.e = this.f.getGraphics();
    try {
      this.i = Image.createImage("/res/public_net.jpg");
      this.j = Image.createImage("/res/table_info_txt.png");
      this.l = Image.createImage("/res/waiting_host.png");
      this.m = Image.createImage("/res/waiting_nothost.png");
      this.n = Image.createImage("/res/new.png");
    } catch (IOException iOException) {
      throw new Error();
    } 
    a(1);
    a(0, "BTServerCanvas() finish");
  }
  
  protected final void paint(Graphics paramGraphics) {
    paramGraphics.drawImage(this.f, 0, 0, 20);
  }
  
  public final void a(int paramInt) {
    this.t = true;
    n.d();
    this.d.a().setCurrent((Displayable)this);
    a();
    b b = new b(k.c, k.d, 1, paramInt);
    a(b);
    this.v = System.currentTimeMillis();
    repaint();
    serviceRepaints();
  }
  
  private void a() {
    this.e.drawImage(n.d, 0, 0, 20);
    this.e.drawImage(this.i, 0, 0, 20);
    this.e.drawImage(this.j, 0, this.i.getHeight(), 20);
    if (this.t) {
      int k = (this.g - this.l.getWidth()) / 2;
      int m = this.i.getHeight() + this.j.getHeight() + 10;
      this.e.drawImage(this.l, k, m, 20);
      this.e.drawImage(n.c(2), 0, this.h - b.aG, 20);
      return;
    } 
    int i = (this.g - this.l.getWidth()) / 2;
    int j = this.i.getHeight() + this.j.getHeight() + 10;
    this.e.drawImage(this.m, i, j, 20);
    this.e.drawImage(n.c(3), 0, this.h - b.aG, 20);
  }
  
  private void a(b paramb) {
    int i = b.aH;
    if (paramb.d < 10) {
      this.e.drawImage(n.d(paramb.d), b.aP, i, 20);
    } else if (paramb.d < 100) {
      int j = paramb.d / 10;
      int k = paramb.d % 10;
      this.e.drawImage(n.d(j), b.aP - b.aI, i, 20);
      this.e.drawImage(n.d(k), b.aP, i, 20);
    } else {
      int j = paramb.d / 100;
      int k;
      int m = (k = paramb.d / 10) % 10;
      int n = paramb.d % 10;
      this.e.drawImage(n.d(j), b.aP - b.aI * 2, i, 20);
      this.e.drawImage(n.d(m), b.aP - b.aI, i, 20);
      this.e.drawImage(n.d(n), b.aP, i, 20);
    } 
    this.e.drawImage(n.d(paramb.a), b.aQ, i, 20);
    this.e.drawImage(n.d(paramb.b), b.aR, i, 20);
    this.e.drawImage(n.d(paramb.c), b.aS, i, 20);
  }
  
  private void b() {
    this.b = 0;
    this.o = 0;
    this.a.g();
  }
  
  private void c() {
    a(0, "joinRoomHelper()");
    b b = this.s.elementAt(this.o);
    if (this.a.a && b.e == 1) {
      Alert alert;
      (alert = new Alert("", "not allowed to join room", null, AlertType.INFO)).setTimeout(-2);
      this.o = 0;
      this.d.a().setCurrent(alert, (Displayable)this);
      a(0, "join room helper...not allowed finished");
      this.b = 0;
      return;
    } 
    this.b = 1;
    this.t = false;
    a();
    repaint();
    serviceRepaints();
    this.a.a(b.d, k.b, k.e);
  }
  
  private void d() {
    this.b = 1;
    this.t = true;
    this.a.a(k.c, k.d, k.b, k.e);
  }
  
  private void e() {
    this.a.i();
  }
  
  private void f() {
    Vector vector = this.s;
    if (this.o == 0)
      this.p = 0; 
    if (this.b != 0 || vector == null)
      return; 
    this.q = vector.size();
    if (this.o >= this.q || this.p >= this.q) {
      this.o = this.q - 1;
      this.p = this.o;
    } 
    a();
    int i = b.aH;
    int j = b.aO;
    int k = n.q.getHeight();
    if (this.q > j) {
      this.e.drawImage(n.q, this.g - n.q.getWidth(), this.h - b.aG - k, 20);
      if (this.p == 0 || this.p == j - 1)
        this.r = this.o * (k - n.r.getHeight()) / (this.q - 1); 
      this.e.drawImage(n.r, this.g - n.q.getWidth(), this.h - b.aG - k + this.r, 20);
    } 
    for (byte b = 0; b < this.q; b++) {
      b b1 = this.s.elementAt(b);
      int m;
      if ((m = (m = i + b * b.aK) - (this.o - this.p) * b.aK) >= i && m < i + b.aK * j) {
        if (this.o == b) {
          int n;
          this.e.setColor(45, 45, 45);
          if (this.q > j) {
            n = b.aL;
          } else {
            n = this.g;
          } 
          this.e.fillRect(b.aM, m, n, b.aN);
          this.e.setColor(0, 0, 0);
          this.e.drawRect(b.aM, m, n, b.aN);
        } 
        if (b1.e == 1)
          this.e.drawImage(this.k, b.aT, m + b.aU, 20); 
        this.e.setColor(184, 184, 184);
        if (b == 0) {
          this.e.drawImage(this.n, b.aJ, m, 20);
        } else if (b1.d < 10) {
          this.e.drawImage(n.d(b1.d), b.aP, m, 20);
        } else if (b1.d < 100) {
          int n = b1.d / 10;
          int i1 = b1.d % 10;
          this.e.drawImage(n.d(n), b.aP - b.aI, m, 20);
          this.e.drawImage(n.d(i1), b.aP, m, 20);
        } else {
          int n = b1.d / 100;
          int i1;
          int i2 = (i1 = b1.d / 10) % 10;
          int i3 = b1.d % 10;
          this.e.drawImage(n.d(n), b.aP - b.aI * 2, m, 20);
          this.e.drawImage(n.d(i2), b.aP - b.aI, m, 20);
          this.e.drawImage(n.d(i3), b.aP, m, 20);
        } 
        if (b1.a < 10) {
          this.e.drawImage(n.d(b1.a), b.aQ, m, 20);
        } else if (b1.a < 100) {
          int n = b1.a / 10;
          int i1 = b1.a % 10;
          this.e.drawImage(n.d(n), b.aQ - b.aI, m, 20);
          this.e.drawImage(n.d(i1), b.aQ, m, 20);
        } 
        if (b1.b < 10) {
          this.e.drawImage(n.d(b1.b), b.aR, m, 20);
        } else if (b1.b < 100) {
          int n = b1.b / 10;
          int i1 = b1.b % 10;
          this.e.drawImage(n.d(n), b.aR - b.aI, m, 20);
          this.e.drawImage(n.d(i1), b.aR, m, 20);
        } 
        this.e.drawImage(n.d(b1.c), b.aS, m, 20);
      } 
    } 
    repaint();
    serviceRepaints();
  }
  
  protected final void keyPressed(int paramInt) {
    this.q = this.s.size();
    if (System.currentTimeMillis() - 200L < this.v || this.u == 0)
      return; 
    this.v = System.currentTimeMillis();
    switch (paramInt) {
      case -6:
        if (this.b == 0) {
          if (this.o == 0) {
            this.b = 1;
            d();
            break;
          } 
          c();
          break;
        } 
        if (this.b == 1 && this.t) {
          this.b = 2;
          e();
        } 
        break;
      case -7:
        if (this.b == 0) {
          this.d.a(com.pheephoo.mjgame.d.ac);
          break;
        } 
        if (this.b == 1) {
          this.b = 0;
          b();
          if (!this.t)
            f(); 
          break;
        } 
        if (this.b == 2)
          this.d.a(com.pheephoo.mjgame.d.aO); 
        break;
      case -1:
        if (this.b == 0) {
          this.o--;
          this.p--;
          if (this.o < 0)
            this.o = 0; 
          if (this.p < 0)
            this.p = 0; 
          f();
        } 
        break;
      case -2:
        if (this.b == 0) {
          this.o++;
          this.p++;
          if (this.o == this.q)
            this.o = this.q - 1; 
          if (this.p == b.aO)
            this.p = b.aO - 1; 
          if (this.p >= this.q)
            this.p = this.q - 1; 
          f();
        } 
        break;
      case -3:
        if (this.b == 0) {
          this.o -= b.aO;
          this.p = 0;
          if (this.o < 0)
            this.o = 0; 
          f();
        } 
        break;
      case -4:
        if (this.b == 0) {
          this.o += b.aO;
          this.p = b.aO - 1;
          if (this.o >= this.q)
            this.o = this.q - 1; 
          if (this.p >= this.q)
            this.p = this.q - 1; 
          f();
        } 
        break;
      case -5:
        if (this.b == 0) {
          if (this.o == 0) {
            this.b = 1;
            d();
            break;
          } 
          this.b = 1;
          c();
          break;
        } 
        if (this.b == 1) {
          if (this.t) {
            this.b = 2;
            e();
            break;
          } 
          this.b = 0;
          b();
        } 
        break;
    } 
    repaint();
    serviceRepaints();
  }
  
  private void a(int paramInt, String paramString) {
    if (paramInt >= this.c)
      System.out.println("BTServerCanvas." + paramString); 
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgam\\ui\d.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */