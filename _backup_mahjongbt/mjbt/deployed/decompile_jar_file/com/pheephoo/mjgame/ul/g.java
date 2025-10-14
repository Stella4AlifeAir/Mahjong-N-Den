package com.pheephoo.mjgame.ui;

import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.b;
import com.pheephoo.mjgame.d;
import com.pheephoo.mjgame.engine.h;
import com.pheephoo.mjgame.engine.k;
import com.pheephoo.mjgame.network.b;
import com.pheephoo.mjgame.network.c;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class g extends Canvas {
  private int C = 1000;
  
  public static int a = 100;
  
  public static int b = 101;
  
  public static int c = 200;
  
  public static int d = 201;
  
  public static int e = 300;
  
  public static int f = 301;
  
  public static int g = 302;
  
  public static int h = 303;
  
  private String D = "";
  
  boolean i = false;
  
  int j = 0;
  
  int k = 0;
  
  int l = 0;
  
  MJGame m;
  
  public h n;
  
  Vector o = new Vector();
  
  Graphics p;
  
  Image q;
  
  Image r;
  
  Image s;
  
  Image t;
  
  Image u;
  
  private Image E;
  
  private Image F;
  
  private Image G;
  
  int v = 0;
  
  int w = 0;
  
  public Vector x = new Vector();
  
  long y = 0L;
  
  private int H = 0;
  
  private int I = 0;
  
  private int J = 0;
  
  private int K = 0;
  
  public int z = 0;
  
  boolean A = false;
  
  int B = 0;
  
  public g(MJGame paramMJGame) {
    a(2, "constructor");
    setFullScreenMode(true);
    this.v = getWidth();
    this.w = getHeight();
    this.m = paramMJGame;
    this.q = Image.createImage(getWidth(), getHeight());
    this.p = this.q.getGraphics();
    try {
      this.t = Image.createImage("/res/private_net1.jpg");
      this.u = Image.createImage("/res/private_net2.jpg");
      this.r = Image.createImage("/res/onlineico.png");
      this.s = Image.createImage("/res/busyico.png");
      this.E = Image.createImage("/res/table_info_txt.png");
      this.F = Image.createImage("/res/waiting_host.png");
      this.G = Image.createImage("/res/waiting_nothost.png");
    } catch (IOException iOException) {}
    a();
  }
  
  protected final void paint(Graphics paramGraphics) {
    paramGraphics.drawImage(this.q, 0, 0, 20);
  }
  
  public final void a(String paramString1, String paramString2) {
    a(0, "onLogin()");
    this.D = paramString2;
    this.m.a(d.ai, "Hello " + paramString2 + "\n" + paramString1);
  }
  
  protected final void keyPressed(int paramInt) {
    a(0, String.valueOf(System.currentTimeMillis()) + "::keyPressed():: keyCode=" + paramInt);
    if (System.currentTimeMillis() - 200L < this.y || this.B == 0)
      return; 
    switch (paramInt) {
      case -6:
        if (this.z == 0) {
          if (this.o.size() == 0) {
            this.m.a(d.aj);
            break;
          } 
          if (this.o.size() > 3) {
            this.m.a(d.ak);
            break;
          } 
          l();
          break;
        } 
        if (this.z == 1 && this.A) {
          this.z = 2;
          m();
        } 
        break;
      case -7:
        if (this.z == 0) {
          this.m.a(d.ae);
          break;
        } 
        if (this.z == 1) {
          this.z = 0;
          g();
          if (!this.A)
            e(); 
          break;
        } 
        if (this.z == 2)
          this.m.a(d.aO); 
        break;
      case -1:
        if (this.z == 0) {
          this.H--;
          this.I--;
          if (this.H < 0)
            this.H = 0; 
          if (this.I < 0)
            this.I = 0; 
          e();
        } 
        break;
      case -2:
        if (this.z == 0) {
          this.H++;
          this.I++;
          if (this.H == this.J)
            this.H = this.J - 1; 
          if (this.I == b.aA)
            this.I = b.aA - 1; 
          if (this.I >= this.J)
            this.I = this.J - 1; 
          e();
        } 
        break;
      case -3:
        if (this.z == 0) {
          this.H -= b.aA;
          this.I = 0;
          if (this.H < 0)
            this.H = 0; 
          e();
        } 
        break;
      case -4:
        if (this.z == 0) {
          this.H += b.aA;
          this.I = b.aA - 1;
          if (this.H >= this.J)
            this.H = this.J - 1; 
          if (this.I >= this.J)
            this.I = this.J - 1; 
          e();
        } 
        break;
      case -5:
        if (this.z == 0) {
          b();
          e();
          break;
        } 
        if (this.z == 1) {
          if (this.A) {
            this.z = 2;
            m();
            break;
          } 
          this.z = 0;
          g();
        } 
        break;
    } 
    repaint();
    serviceRepaints();
  }
  
  private void e() {
    int m;
    a(0, "drawContact() ::currentRowSelection=" + this.H + " ;currentRowScreen=" + this.I);
    Vector vector = this.x;
    if (this.H == 0)
      this.I = 0; 
    if (this.z != 0 || vector == null) {
      a(0, "drawContact(). skip drawing");
      return;
    } 
    this.J = vector.size();
    a(0, "drawContact()::numberOfRow=" + this.J);
    if (this.H >= this.J || this.I >= this.J) {
      a(0, "***moving the pointer up!");
      if (this.J != 0) {
        this.H = this.J - 1;
        this.I = this.H;
      } 
    } 
    f();
    int i = b.az;
    int j = b.aA;
    int k = n.q.getHeight();
    if (this.J > j) {
      this.p.drawImage(n.q, this.v - n.q.getWidth(), this.w - b.aG - k, 20);
      if (this.I == 0 || this.I == j - 1)
        this.K = this.H * (k - n.r.getHeight()) / (this.J - 1); 
      this.p.drawImage(n.r, this.v - n.q.getWidth(), this.w - b.aG - k + this.K, 20);
    } 
    if (this.J > j) {
      m = b.aC;
    } else {
      m = this.v;
    } 
    a(0, "currentRowSelection=" + this.H);
    this.p.setFont(Font.getFont(32, 0, 8));
    for (byte b = 0; b < this.J; b++) {
      a(0, "numberOfRow" + this.J);
      c c = vector.elementAt(b);
      int n;
      if ((n = (n = i + b * b.aB) - (this.H - this.I) * b.aB) >= i && n < i + b.aB * j) {
        a(0, "currentSelection=" + this.H + "; vpos=" + n + ";i=" + b);
        if (this.H == b) {
          a(0, "vpos=" + n);
          this.p.setColor(45, 45, 45);
          this.p.fillRect(b.aD, n, m, b.aE);
          this.p.setColor(0, 0, 0);
          this.p.drawRect(b.aD, n, m, b.aE);
        } 
        if (c.b == 1) {
          if (c.e) {
            this.p.setColor(167, 142, 120);
            this.p.fillRect(b.aD, n, m, b.aE);
          } 
          this.p.drawImage(this.r, b.aT, n + b.aF, 20);
          this.p.setColor(255, 255, 255);
        } else if (c.b == 2) {
          this.o.removeElement((new StringBuffer(String.valueOf(c.a))).toString());
          this.p.drawImage(this.s, b.aT, n + b.aF, 20);
          this.p.setColor(255, 255, 255);
        } else {
          this.p.setColor(150, 150, 150);
        } 
        this.p.drawString(c.d, b.aP, n, 20);
      } 
    } 
    repaint();
    serviceRepaints();
    a(0, "drawTable() finished");
  }
  
  private void f() {
    a(0, "drawBackground()");
    this.p.drawImage(n.d, 0, 0, 20);
    if (this.z == 0) {
      this.p.drawImage(this.t, 0, 0, 20);
      this.p.drawImage(n.c(1), 0, this.w - b.aG, 20);
      return;
    } 
    if (this.z == 1) {
      this.p.drawImage(this.u, 0, 0, 20);
      this.p.drawImage(this.E, 0, this.u.getHeight(), 20);
      if (this.A) {
        int k = (this.v - this.F.getWidth()) / 2;
        int m = this.u.getHeight() + this.E.getHeight() + 10;
        this.p.drawImage(this.F, k, m, 20);
        this.p.drawImage(n.c(2), 0, this.w - b.aG, 20);
        return;
      } 
      int i = (this.v - this.F.getWidth()) / 2;
      int j = this.u.getHeight() + this.E.getHeight() + 10;
      this.p.drawImage(this.G, i, j, 20);
      this.p.drawImage(n.c(3), 0, this.w - b.aG, 20);
    } 
  }
  
  public final void a() {
    k.a();
  }
  
  private void b(b paramb) {
    a(0, "drawWaitingRoomFigure");
    int i = b.aH;
    if (paramb.d < 10) {
      this.p.drawImage(n.d(paramb.d), b.aP, i, 20);
    } else if (paramb.d < 100) {
      int j = paramb.d / 10;
      int k = paramb.d % 10;
      this.p.drawImage(n.d(j), b.aP - b.aI, i, 20);
      this.p.drawImage(n.d(k), b.aP, i, 20);
    } else {
      int j = paramb.d / 100;
      int k;
      int m = (k = paramb.d / 10) % 10;
      int n = paramb.d % 10;
      this.p.drawImage(n.d(j), b.aP - b.aI * 2, i, 20);
      this.p.drawImage(n.d(m), b.aP - b.aI, i, 20);
      this.p.drawImage(n.d(n), b.aP, i, 20);
    } 
    this.p.drawImage(n.d(paramb.a), b.aQ, i, 20);
    this.p.drawImage(n.d(paramb.b), b.aR, i, 20);
    this.p.drawImage(n.d(paramb.c), b.aS, i, 20);
  }
  
  private void g() {
    this.z = 0;
    this.j = 0;
    e();
    this.n.g();
  }
  
  private void h() {
    this.m.a().setCurrent((Displayable)this);
    this.j = 0;
    repaint();
    serviceRepaints();
  }
  
  private void i() {
    a(2, "acceptContactHelper()");
    this.n.d(this.l);
  }
  
  private void j() {
    this.m.a().setCurrent((Displayable)this);
    this.j = 0;
    repaint();
    serviceRepaints();
    this.n.e(this.l);
  }
  
  private void k() {
    this.m.a().setCurrent((Displayable)this);
    this.j = 0;
    repaint();
    serviceRepaints();
    this.n.c(this.k);
  }
  
  private void d(int paramInt) {
    this.z = 1;
    this.A = false;
    this.m.a().setCurrent((Displayable)this);
    repaint();
    serviceRepaints();
    this.n.a(paramInt, k.b, this.D);
  }
  
  public final void a(int paramInt1, int paramInt2, String paramString) {
    if (paramInt1 == 0) {
      for (byte b = 0; b < this.x.size(); b++) {
        c c;
        if ((c = this.x.elementAt(b)).a == paramInt2)
          this.x.removeElementAt(b); 
      } 
    } else {
      char c = paramString.charAt(0);
      int i = this.x.size();
      for (byte b = 0; b < i; b++) {
        c c1;
        if ((c1 = this.x.elementAt(b)).d.charAt(0) > c) {
          this.x.addElement(new c(paramInt2, 1, paramString));
          break;
        } 
        if (b == i - 1) {
          this.x.addElement(new c(paramInt2, 1, paramString));
          break;
        } 
      } 
      if (i == 0)
        this.x.addElement(new c(paramInt2, 1, paramString)); 
    } 
    e();
  }
  
  public final void a(Vector paramVector) {
    a(2, "onResponseContactList()");
    this.x = paramVector;
    c c = new c(0, 1, "bot");
    this.x.insertElementAt(c, 0);
    b(paramVector);
    e();
    this.B = 1;
    this.y = System.currentTimeMillis();
  }
  
  private void b(Vector paramVector) {
    this.x = paramVector;
    int i = this.x.size();
    boolean bool = false;
    for (byte b = 0; b < this.o.size(); b++) {
      bool = false;
      for (byte b1 = 0; b1 < i; b1++) {
        c c = this.x.elementAt(b1);
        if (this.o.elementAt(b).equals((new StringBuffer(String.valueOf(c.a))).toString())) {
          c.e = true;
          bool = true;
          break;
        } 
      } 
      if (!bool) {
        this.o.removeElement(this.o.elementAt(b));
        b--;
      } 
    } 
    if (this.x.size() == 0 && !this.i) {
      Alert alert = null;
      alert = new Alert("", "You don't have any mahjong buddies. Please invite your friends", null, AlertType.INFO);
      this.m.a().setCurrent((Displayable)alert);
      this.i = true;
    } 
  }
  
  public final void a(int paramInt) {
    a(0, "onResponseCreateRoom()::TableCreated.tablenum=" + paramInt);
    n.d();
    this.m.a().setCurrent((Displayable)this);
    f();
    b b = new b(k.c, k.d, 1, paramInt);
    b(b);
    this.y = System.currentTimeMillis();
    repaint();
    serviceRepaints();
  }
  
  public final void a(b paramb) {
    a(0, "onResponseRoomUpdate()");
    if (this.z == 0)
      return; 
    if (!this.A) {
      n.d();
      this.m.a().setCurrent((Displayable)this);
    } 
    f();
    b(paramb);
    this.y = System.currentTimeMillis();
    repaint();
    serviceRepaints();
  }
  
  private void l() {
    a(0, "createRoomHelper()");
    this.z = 1;
    this.A = true;
    this.n.a(k.c, k.d, k.b, this.o, k.e);
  }
  
  public final void b(int paramInt) {
    a(2, "onDialogResponse");
    if (paramInt == a) {
      a(2, "onDialogResponse:: choice prv_acceptinvitation");
      d(this.k);
      return;
    } 
    if (paramInt == b) {
      a(2, "onDialogResponse:: choice prv_rejectinvitation");
      k();
      return;
    } 
    if (paramInt == c) {
      a(2, "onDialogResponse:: choice prv_acceptcontact");
      i();
      return;
    } 
    if (paramInt == d) {
      a(2, "onDialogResponse:: choice prv_rejectcontact");
      j();
      return;
    } 
    if (paramInt == e) {
      a(2, "onDialogResponse:: choice prv_rejoinyes");
      d(this.k);
      return;
    } 
    if (paramInt == f) {
      a(2, "onDialogResponse:: choice prv_rejoinno");
      h();
      return;
    } 
    if (paramInt == g) {
      this.n.q();
      a(2, "transactionOK");
      return;
    } 
    if (paramInt == h) {
      a(2, "transactionCancel");
      this.m.a(d.aQ);
    } 
  }
  
  public final void a(int paramInt1, int paramInt2, String paramString1, String paramString2, int paramInt3) {
    a(2, "onPendingEvent() ::eventType=" + paramInt1 + " ;msisdn=" + paramString1 + " ;nick=" + paramString2);
    this.l = paramInt2;
    if (paramInt1 == 1) {
      e e = new e(this, "contact", String.valueOf(paramString2) + " (" + paramString1 + ") has requested you to be his mahjong buddy =)", "Accept", "Reject", c, d);
      this.m.d = (Displayable)e;
      return;
    } 
    if (paramInt1 == 2) {
      Alert alert;
      (alert = new Alert("", "Your friend (" + paramString1 + " ) has rejected to be your mahjong buddy ", null, AlertType.INFO)).setTimeout(-2);
      this.m.d = (Displayable)alert;
      return;
    } 
    if (paramInt1 == 20) {
      Alert alert;
      (alert = new Alert("", "Your friend " + paramString2 + " (" + paramString1 + " ) has deleted you from his contact list", null, AlertType.INFO)).setTimeout(-2);
      this.m.d = (Displayable)alert;
      return;
    } 
    if (paramInt1 == 30) {
      this.k = paramInt3;
      e e = new e(this, "rejoin", "Would you like to rejoin your last game?", "Yes", "No", e, f);
      this.m.d = (Displayable)e;
    } 
  }
  
  public final void a(boolean[] paramArrayOfboolean, int paramInt) {
    int[] arrayOfInt = new int[paramInt];
    byte b1 = 0;
    byte b2;
    for (b2 = 0; b2 < this.x.size(); b2++) {
      if (paramArrayOfboolean[b2]) {
        c c = this.x.elementAt(b2);
        arrayOfInt[b1++] = c.a;
      } 
    } 
    this.n.a(arrayOfInt);
    for (b2 = 0; b2 < this.x.size(); b2++) {
      c c = this.x.elementAt(b2);
      for (byte b = 0; b < arrayOfInt.length; b++) {
        if (c.a == arrayOfInt[b]) {
          this.x.removeElementAt(b2);
          b2--;
          break;
        } 
      } 
    } 
    e();
  }
  
  public final void a(int paramInt1, int paramInt2) {
    a(2, "onInviteAnswered:: choice=" + paramInt1 + " ;contactId=" + paramInt2);
    Alert alert = null;
    String str = e(paramInt2);
    if (paramInt1 == 0) {
      alert = new Alert("", String.valueOf(str) + " has rejected your invitation", null, AlertType.INFO);
    } else if (paramInt1 == 1) {
      alert = new Alert("", String.valueOf(str) + " has joined your table", null, AlertType.INFO);
    } else if (paramInt1 == 2) {
      alert = new Alert("", String.valueOf(str) + " has left your table", null, AlertType.INFO);
    } else if (paramInt1 == 3) {
      this.z = 0;
      this.j = 0;
      e();
      repaint();
      serviceRepaints();
      alert = new Alert("", "host has closed the table", null, AlertType.ERROR);
    } 
    alert.setTimeout(-2);
    this.m.a().setCurrent(alert, (Displayable)this);
    if (this.x.size() == 0)
      this.n.k(); 
  }
  
  private String e(int paramInt) {
    String str = "";
    for (byte b = 0; b < this.x.size(); b++) {
      c c;
      if ((c = this.x.elementAt(b)).a == paramInt) {
        str = c.d;
        break;
      } 
    } 
    return str;
  }
  
  public final void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    while (this.j == 1) {
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException interruptedException) {}
    } 
    this.j = 1;
    this.k = paramInt2;
    String str = e(paramInt1);
    e e = new e(this, "invite", String.valueOf(str) + " has invited you to play mahjong\nMin Double= " + paramInt3 + " \nMax Double=" + paramInt4, "Accept", "Reject", a, b);
    this.m.a().setCurrent((Displayable)e);
  }
  
  public final void a(String paramString1, String paramString2, String paramString3) {
    this.n.a(paramString1, paramString2, paramString3);
  }
  
  public final void b() {
    c c;
    if (this.H < this.x.size() && (c = this.x.elementAt(this.H)).b == 1) {
      if (this.o.contains((new StringBuffer(String.valueOf(c.a))).toString())) {
        this.o.removeElement((new StringBuffer(String.valueOf(c.a))).toString());
        c.e = false;
        return;
      } 
      this.o.addElement((new StringBuffer(String.valueOf(c.a))).toString());
      c.e = true;
    } 
  }
  
  public final void b(int paramInt1, int paramInt2) {
    a(0, "onResponseContactListUpdate()");
    int i = this.x.size();
    for (byte b = 0; b < i; b++) {
      c c;
      if ((c = this.x.elementAt(b)).a == paramInt1) {
        c.b = paramInt2;
        break;
      } 
    } 
    if (this.z == 0) {
      e();
      repaint();
      serviceRepaints();
    } 
  }
  
  public final void c(int paramInt) {
    a(2, "onTransaction");
    int i = paramInt / 100;
    int j = paramInt % 100;
    String[] arrayOfString = { d.r, String.valueOf(d.o) + i + "." + j + " " + d.p, d.s };
    e e = new e(this, d.n, arrayOfString, "Ok", "Cancel", g, h);
    this.m.a().setCurrent((Displayable)e);
    this.m.d = (Displayable)this;
  }
  
  public final void c() {
    this.z = 2;
    repaint();
    serviceRepaints();
    this.m.a(d.aT);
  }
  
  private void m() {
    this.n.i();
  }
  
  public final void d() {
    this.n.l();
  }
  
  private void a(int paramInt, String paramString) {
    if (paramInt >= this.C)
      System.out.println("PrivateNetworkCanvas." + paramString); 
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgam\\ui\g.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */