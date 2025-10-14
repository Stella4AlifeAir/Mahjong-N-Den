package com.pheephoo.mjgame;

import com.pheephoo.mjgame.engine.c;
import com.pheephoo.mjgame.engine.f;
import com.pheephoo.mjgame.engine.h;
import com.pheephoo.mjgame.engine.k;
import com.pheephoo.mjgame.engine.s;
import com.pheephoo.mjgame.engine.t;
import com.pheephoo.mjgame.engine.u;
import com.pheephoo.mjgame.form.a;
import com.pheephoo.mjgame.form.b;
import com.pheephoo.mjgame.form.c;
import com.pheephoo.mjgame.form.d;
import com.pheephoo.mjgame.form.f;
import com.pheephoo.mjgame.form.g;
import com.pheephoo.mjgame.form.h;
import com.pheephoo.mjgame.form.i;
import com.pheephoo.mjgame.form.j;
import com.pheephoo.mjgame.form.k;
import com.pheephoo.mjgame.network.d;
import com.pheephoo.mjgame.ui.a;
import com.pheephoo.mjgame.ui.c;
import com.pheephoo.mjgame.ui.d;
import com.pheephoo.mjgame.ui.g;
import com.pheephoo.mjgame.ui.h;
import com.pheephoo.mjgame.ui.j;
import com.pheephoo.mjgame.ui.k;
import com.pheephoo.mjgame.ui.l;
import com.pheephoo.mjgame.ui.m;
import com.pheephoo.mjgame.ui.n;
import com.pheephoo.utilx.a;
import java.util.Vector;
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.TextBox;
import javax.microedition.midlet.MIDlet;

public class MJGame extends MIDlet implements CommandListener, Runnable {
  public int a = 0;
  
  public boolean b = false;
  
  public boolean c = false;
  
  public Displayable d;
  
  public String e = "";
  
  private boolean h = false;
  
  private boolean i = false;
  
  private boolean j = false;
  
  private boolean k = false;
  
  private Displayable l = null;
  
  private Displayable m = (Displayable)(new a(this)).b;
  
  private i n;
  
  private Display o = Display.getDisplay(this);
  
  private s p;
  
  private h q;
  
  private l r;
  
  private h s;
  
  private g t;
  
  private d u;
  
  private int v = 0;
  
  private int w = 0;
  
  private int x = 0;
  
  private String y;
  
  private String z;
  
  private TextBox A;
  
  private u B = new u();
  
  Command f;
  
  int g;
  
  private boolean h() {
    long l1;
    return ((l1 = System.currentTimeMillis()) > this.B.d + 31536000000L);
  }
  
  public final void a(int paramInt, String paramString) {
    if (paramInt == d.ab) {
      this.a = 0;
      this.y = paramString;
      this.A = new TextBox("Enter your nick", this.q.h, 15, 0);
      this.f = new Command("Ok", 4, 1);
      this.A.addCommand(this.f);
      this.A.setCommandListener(this);
      n.d();
      this.o.setCurrent((Displayable)this.A);
      return;
    } 
    if (paramInt == d.ai) {
      this.a = 0;
      n.d();
      a(paramString);
      return;
    } 
    if (paramInt == d.aS) {
      Alert alert;
      (alert = new Alert("", paramString, null, AlertType.INFO)).setTimeout(-2);
      this.d = (Displayable)this.r;
      this.o.setCurrent(alert, this.d);
      return;
    } 
    if (paramInt == d.au) {
      this.o.setCurrent((Displayable)n.a);
      this.q.b(paramString);
      return;
    } 
    if (paramInt == d.az) {
      j();
      Alert alert;
      (alert = new Alert("", "There is a new update available. You need to download the update to continue", null, AlertType.INFO)).setTimeout(-2);
      this.o.setCurrent(alert, this.m);
      try {
        Thread.sleep(5000L);
        platformRequest(paramString);
        Thread.sleep(2000L);
        b();
        return;
      } catch (ConnectionNotFoundException connectionNotFoundException2) {
        ConnectionNotFoundException connectionNotFoundException1;
        (connectionNotFoundException1 = null).printStackTrace();
        return;
      } catch (InterruptedException interruptedException) {
        return;
      } 
    } 
    if (paramInt == d.Z) {
      this.e = paramString;
      j();
      this.o.setCurrent((Displayable)new c(this, this.r, this.x, 1));
    } 
  }
  
  public final Display a() {
    return this.o;
  }
  
  private void i() {
    if (d.b != 0 && this.B.e == -1) {
      a("Please download the client before applying the update", 3000);
    } else {
      this.B.e = 1;
    } 
    if (this.B.d <= 0L) {
      this.B.d = System.currentTimeMillis();
      this.B.b();
    } 
    if (h())
      a("License expired. Please delete this client and download the latest client", 5000); 
  }
  
  public final void startApp() {
    i();
    if (this.h) {
      if (this.b) {
        this.o.setCurrent((Displayable)new k(this));
        this.b = false;
        this.a++;
      } else {
        this.o.setCurrent(this.l);
      } 
      this.h = false;
      return;
    } 
    this.v = 0;
    this.d = this.m;
    m m = new m(this, 3500);
    this.o.setCurrent((Displayable)m);
    n.a();
    this.k = true;
    this.u = new d(this);
    this.k = false;
    while (!m.a) {
      try {
        Thread.sleep(500L);
      } catch (InterruptedException interruptedException) {}
    } 
    this.o.setCurrent(this.m);
  }
  
  public final void pauseApp() {
    this.h = true;
    if (this.j)
      this.b = true; 
    this.l = this.o.getCurrent();
  }
  
  public final void destroyApp(boolean paramBoolean) {
    notifyDestroyed();
  }
  
  public final void b() {
    destroyApp(true);
    notifyDestroyed();
  }
  
  public final void commandAction(Command paramCommand, Displayable paramDisplayable) {
    this.z = this.A.getString();
    if (this.v == 0) {
      if (this.z.length() == 0) {
        Alert alert = new Alert("", "Please input your nick", null, AlertType.ERROR);
        this.o.setCurrent((Displayable)alert);
        return;
      } 
      if (this.z.indexOf(" ") != -1) {
        Alert alert = new Alert("", "Please input your nick without space", null, AlertType.ERROR);
        this.o.setCurrent((Displayable)alert);
        return;
      } 
      if (paramCommand == this.f) {
        k.e = this.z;
        a(this.y);
        return;
      } 
    } else {
      if (this.w == 1) {
        this.t.n.a(this.z);
        this.o.setCurrent(this.d);
        return;
      } 
      this.s.a.a(this.z);
      this.o.setCurrent(this.d);
    } 
  }
  
  private void a(String paramString) {
    Alert alert;
    (alert = new Alert("", paramString, null, AlertType.INFO)).setTimeout(-2);
    this.o.setCurrent(alert, this.d);
  }
  
  private void j() {
    System.out.println("*networkcommon_quitgame");
    if (!this.h)
      this.j = false; 
    this.d = this.m;
    this.o.setCurrent(this.d);
    this.i = true;
    e e;
    (e = new e(this)).start();
  }
  
  public final void a(int paramInt) {
    if (paramInt == d.E) {
      if (this.B.a <= 0) {
        n.c();
        this.o.setCurrent((Displayable)n.a);
        c();
        return;
      } 
      n.b();
      this.o.setCurrent((Displayable)n.a);
      b(0);
      return;
    } 
    if (paramInt == d.P) {
      this.o.setCurrent((Displayable)(new j(this)).a);
      return;
    } 
    if (paramInt == d.Q) {
      System.out.println("BT DEMO");
      d d1 = new d(this);
      this.o.setCurrent((Displayable)d1);
      f f;
      (f = new f(this)).a((Canvas)d1);
      return;
    } 
    if (paramInt == d.aA) {
      b(0, 5);
      return;
    } 
    if (paramInt == d.aB) {
      b(0, 6);
      return;
    } 
    if (paramInt == d.aC) {
      b(d.aC, 0);
      return;
    } 
    if (paramInt == d.aD) {
      b(d.aD, 0);
      return;
    } 
    if (paramInt == d.aE) {
      b(d.aE, 0);
      return;
    } 
    if (paramInt == d.aF) {
      b(d.aF, 0);
      return;
    } 
    if (paramInt == d.aG) {
      b(d.aG, 0);
      return;
    } 
    if (paramInt == d.aL) {
      b(d.aL, 0);
      return;
    } 
    if (paramInt == d.aM) {
      b(d.aM, 0);
      return;
    } 
    if (paramInt == d.aH) {
      b(d.aH, 0);
      return;
    } 
    if (paramInt == d.aI) {
      b(d.aI, 0);
      return;
    } 
    if (paramInt == d.aJ) {
      b(d.aJ, 0);
      return;
    } 
    if (paramInt == d.aN) {
      this.A = new TextBox("Type your message", "", 50, 0);
      this.f = new Command("Ok", 4, 1);
      this.A.addCommand(this.f);
      this.A.setCommandListener(this);
      this.o.setCurrent((Displayable)this.A);
      return;
    } 
    if (paramInt == d.F) {
      try {
        while (this.k)
          Thread.sleep(500L); 
      } catch (InterruptedException interruptedException) {}
      if (!this.k) {
        this.k = true;
        if (this.u == null)
          this.u = new d(this); 
        this.u.a(0);
        this.k = false;
        this.o.setCurrent((Displayable)this.u);
        return;
      } 
    } else {
      if (paramInt == d.H) {
        n.c();
        this.o.setCurrent((Displayable)n.a);
        c();
        return;
      } 
      if (paramInt == d.G) {
        n.c();
        this.o.setCurrent((Displayable)n.a);
        d();
        return;
      } 
      if (paramInt == d.K) {
        this.o.setCurrent((Displayable)(new c(this)).a);
        return;
      } 
      if (paramInt == d.L) {
        this.o.setCurrent((Displayable)new c(this, this.r, this.x, 2));
        return;
      } 
      if (paramInt == d.M) {
        this.o.setCurrent((Displayable)new c(this, this.r, this.x, 3));
        return;
      } 
      if (paramInt == d.O) {
        this.o.setCurrent((Displayable)new g(this));
        return;
      } 
      if (paramInt == d.T) {
        this.d = (Displayable)(new b(this, d.T)).b;
        this.o.setCurrent(this.d);
        return;
      } 
      if (paramInt == d.aO) {
        this.d = (Displayable)(new b(this, d.aO)).b;
        this.o.setCurrent(this.d);
        return;
      } 
      if (paramInt == d.ac) {
        this.d = (Displayable)(new b(this, d.ac)).b;
        this.o.setCurrent(this.d);
        return;
      } 
      if (paramInt == d.ae) {
        this.d = (Displayable)(new b(this, d.ae)).b;
        this.o.setCurrent(this.d);
        return;
      } 
      if (paramInt == d.af) {
        this.d = (Displayable)(new b(this, d.af)).b;
        this.o.setCurrent(this.d);
        return;
      } 
      if (paramInt == d.ad) {
        this.o.setCurrent((Displayable)this.t);
        return;
      } 
      if (paramInt == d.aj) {
        Alert alert;
        (alert = new Alert("", "Please invite at least one player to play with you. Press the center button to select player", null, AlertType.ERROR)).setTimeout(-2);
        this.o.setCurrent((Displayable)alert);
        return;
      } 
      if (paramInt == d.ak) {
        Alert alert;
        (alert = new Alert("", "Max three players can be invited to play with you", null, AlertType.ERROR)).setTimeout(-2);
        this.o.setCurrent((Displayable)alert);
        return;
      } 
      if (paramInt == d.al) {
        Alert alert = new Alert("", "Please invite at least one player to play with you", null, AlertType.ERROR);
        this.o.setCurrent((Displayable)alert);
        return;
      } 
      if (paramInt == d.am) {
        Alert alert = new Alert("", "You have entered invalid an phone number", null, AlertType.ERROR);
        this.o.setCurrent((Displayable)alert);
        return;
      } 
      if (paramInt == d.an) {
        Alert alert = new Alert("", "Unable to send SMS. Please try again", null, AlertType.ERROR);
        this.o.setCurrent((Displayable)alert);
        return;
      } 
      if (paramInt == d.aq) {
        Alert alert = new Alert("", "Registration cancelled.", null, AlertType.ERROR);
        this.o.setCurrent((Displayable)alert);
        j();
        return;
      } 
      if (paramInt == d.ar) {
        Alert alert = new Alert("", "Network timeout. Please try again.", null, AlertType.ERROR);
        this.o.setCurrent((Displayable)alert);
        j();
        return;
      } 
      if (paramInt == d.as) {
        Alert alert = new Alert("", "Unable to send SMS. Please try again.", null, AlertType.ERROR);
        this.o.setCurrent((Displayable)alert);
        j();
        return;
      } 
      if (paramInt == d.aw) {
        Alert alert = new Alert("", "Please input your nick", null, AlertType.ERROR);
        this.o.setCurrent((Displayable)alert);
        return;
      } 
      if (paramInt == d.ax) {
        Alert alert = new Alert("", "Please use valid characters", null, AlertType.ERROR);
        this.o.setCurrent((Displayable)alert);
        return;
      } 
      if (paramInt == d.ag) {
        this.n = new i(this);
        this.d = (Displayable)(new b(this, d.ae)).b;
        this.o.setCurrent((Displayable)this.n);
        return;
      } 
      if (paramInt == d.at) {
        j j = new j(this, b.c, 1);
        this.o.setCurrent((Displayable)j);
        return;
      } 
      if (paramInt == d.av) {
        j j = new j(this, b.c, 2);
        this.o.setCurrent((Displayable)j);
        return;
      } 
      if (paramInt == d.ah) {
        h h1 = new h(this, this.t);
        this.o.setCurrent((Displayable)h1.a);
        return;
      } 
      if (paramInt == d.aP) {
        this.o.setCurrent((Displayable)this.r);
        return;
      } 
      if (paramInt == d.aa) {
        this.o.setCurrent((Displayable)this.s);
        return;
      } 
      if (paramInt == d.I) {
        this.o.setCurrent((Displayable)new f(this));
        return;
      } 
      if (paramInt == d.N) {
        b();
        return;
      } 
      if (paramInt == d.U) {
        if (this.u == null)
          this.u = new d(this); 
        this.u.a(1);
        this.o.setCurrent((Displayable)this.u);
        return;
      } 
      if (paramInt == d.S) {
        this.r.h();
        this.d = (Displayable)this.r;
        this.o.setCurrent((Displayable)this.r);
        return;
      } 
      if (paramInt == d.R) {
        if (this.p != null)
          ((t)this.p).g(); 
        this.p = null;
        if (this.r != null) {
          this.r.e();
          this.r = null;
        } 
        this.d = null;
        this.d = this.m;
        this.o.setCurrent(this.m);
        return;
      } 
      if (paramInt == d.J) {
        this.d = this.m;
        this.o.setCurrent(this.d);
        return;
      } 
      if (paramInt == d.V || paramInt == d.W) {
        this.o.setCurrent(this.d);
        return;
      } 
      if (paramInt == d.aO) {
        this.d = (Displayable)(new b(this, d.aO)).b;
        this.o.setCurrent(this.d);
        return;
      } 
      if (paramInt == d.ap) {
        Alert alert;
        (alert = new Alert("", "The selected contacts have been deleted", null, AlertType.INFO)).setTimeout(-2);
        this.o.setCurrent(alert, this.d);
        return;
      } 
      if (paramInt == d.ao) {
        this.t.a(this.n.a, this.n.b, this.n.c);
        Alert alert;
        (alert = new Alert("", "Thank you! Your invitation will be sent via SMS to your friends. ", null, AlertType.INFO)).setTimeout(-2);
        this.o.setCurrent(alert, this.d);
        return;
      } 
      if (paramInt == d.ay) {
        this.c = true;
        this.o.setCurrent((Displayable)new k(this));
        return;
      } 
      if (paramInt == d.aR) {
        if (!this.i)
          try {
            this.o.setCurrent((Displayable)new k(this));
            this.a++;
            return;
          } catch (IllegalStateException illegalStateException) {
            return;
          }  
      } else {
        if (paramInt == d.aQ) {
          j();
          return;
        } 
        if (paramInt == d.Y) {
          this.o.setCurrent((Displayable)new k(this, this.r, this.x));
          return;
        } 
        if (paramInt == d.Z) {
          this.o.setCurrent((Displayable)new c(this, this.r, this.x, 1));
          return;
        } 
        if (paramInt == d.X) {
          this.o.setCurrent((Displayable)new c(this, this.r, this.x, 0));
          return;
        } 
        if (paramInt == d.aT) {
          this.v = 1;
          this.r.i();
          this.o.setCurrent((Displayable)this.r);
          n.d();
        } 
      } 
    } 
  }
  
  public final void c() {
    this.w = 2;
    this.d = (Displayable)this.s;
    Thread thread;
    (thread = new Thread(this)).start();
  }
  
  public final void d() {
    this.w = 1;
    this.d = (Displayable)this.t;
    Thread thread;
    (thread = new Thread(this)).start();
  }
  
  public final void run() {
    this.j = true;
    this.x = 1;
    this.r = new l(this, 1);
    if (this.B.a <= 0)
      this.w = 2; 
    if (this.w == 1) {
      this.t = new g(this);
      this.q = new h(new d(String.valueOf(d.f) + ":" + d.g), (c)this.r, this.B.a, this.B.b, this);
      this.q.a(this.t);
    } else if (this.w == 2) {
      this.s = new h(this);
      this.q = new h(new d(String.valueOf(d.f) + ":" + d.h), (c)this.r, this.B.a, this.B.b, this);
      this.q.a(this.s);
    } 
    this.i = false;
    Thread thread;
    (thread = new Thread((Runnable)this.q)).start();
  }
  
  public final void a(int paramInt1, int paramInt2) {
    this.B = new u();
    this.B.a = paramInt1;
    this.B.b = paramInt2;
    this.B.b();
  }
  
  public final void e() {
    Alert alert = new Alert("", "Resetting the client", null, AlertType.INFO);
    this.o.setCurrent((Displayable)alert);
    this.B = new u();
    this.B.a = -99;
    this.B.b();
  }
  
  public final void b(int paramInt) {
    this.g = paramInt;
    this.x = 0;
    Runtime.getRuntime().gc();
    Thread thread;
    (thread = new Thread(new com/pheephoo/mjgame/c(this, this))).start();
  }
  
  public final void b(int paramInt1, int paramInt2) {
    this.g = paramInt1;
    this.x = 0;
    Runtime.getRuntime().gc();
    this.r = new l(this, 0, paramInt2);
    this.r.a(this.g);
    this.o.setCurrent((Displayable)this.r);
    a a;
    (a = new a(this)).start();
  }
  
  final void f() {
    this.t.n.g();
    this.t.d();
    if (this.r != null) {
      this.r.e();
      this.r = null;
    } 
  }
  
  final void g() {
    System.out.println("mjgame.publicnetworkcleanup");
    this.v = 0;
    if (this.s.c == 2) {
      System.out.println("before calling engine.quitgame");
      this.s.a.g();
    } 
    this.s.a.l();
    if (this.r != null) {
      this.r.e();
      this.r = null;
    } 
  }
  
  public final void c(int paramInt) {
    if (paramInt == d.c) {
      a("Please set your mobile to allow SMS access for this application. Application will exit", 4000);
      return;
    } 
    if (paramInt == d.d) {
      a("Please set your mobile to allow network access for this application. Application will exit", 4000);
      return;
    } 
    if (paramInt == d.e)
      a("Please set your mobile to allow Bluetooth access for this application. Application will exit", 4000); 
  }
  
  private void a(String paramString, int paramInt) {
    Alert alert;
    (alert = new Alert("", paramString, null, AlertType.INFO)).setTimeout(-2);
    this.o.setCurrent((Displayable)alert);
    System.out.println("showAlert");
    try {
      Thread.sleep(paramInt);
      b();
      return;
    } catch (InterruptedException interruptedException) {
      return;
    } 
  }
  
  static final void a(MJGame paramMJGame, l paraml) {
    paramMJGame.r = paraml;
  }
  
  static final l a(MJGame paramMJGame) {
    return paramMJGame.r;
  }
  
  static final void a(MJGame paramMJGame, s params) {
    paramMJGame.p = params;
  }
  
  static final s b(MJGame paramMJGame) {
    return paramMJGame.p;
  }
  
  static final int c(MJGame paramMJGame) {
    return paramMJGame.w;
  }
  
  private class com/pheephoo/mjgame/c implements Runnable {
    MJGame a;
    
    final MJGame b;
    
    com/pheephoo/mjgame/c(MJGame this$0, MJGame param1MJGame1) {
      this.b = this$0;
      this.a = param1MJGame1;
    }
    
    public final void run() {
      MJGame.a(this.b, new l(this.a, 0));
      this.a.a().setCurrent((Displayable)MJGame.a(this.b));
      MJGame.a(this.b, (s)new t());
      Vector vector = new Vector();
      int i;
      for (i = 0; i < d.t; i++)
        vector.addElement(new Integer(i)); 
      i = a.a(0, vector.size() - 1);
      int j = ((Integer)vector.elementAt(i)).intValue();
      vector.removeElementAt(i);
      a a1 = new a(j, d.u[j]);
      i = a.a(0, vector.size() - 1);
      j = ((Integer)vector.elementAt(i)).intValue();
      vector.removeElementAt(i);
      a a2 = new a(j, d.u[j]);
      i = a.a(0, vector.size() - 1);
      j = ((Integer)vector.elementAt(i)).intValue();
      vector.removeElementAt(i);
      a a3 = new a(j, d.u[j]);
      MJGame.b(this.b).f(this.b.g);
      MJGame.b(this.b).a((c)MJGame.a(this.b), 1);
      MJGame.b(this.b).a((c)a1, 1);
      MJGame.b(this.b).a((c)a2, 1);
      MJGame.b(this.b).a((c)a3, 1);
      ((t)MJGame.b(this.b)).b();
    }
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\MJGame.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */