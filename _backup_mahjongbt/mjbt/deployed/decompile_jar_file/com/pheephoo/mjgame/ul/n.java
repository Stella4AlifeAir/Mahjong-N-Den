package com.pheephoo.mjgame.ui;

import com.pheephoo.utilx.a;
import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class n {
  public static com/pheephoo/mjgame/ui/b a;
  
  public static Image b;
  
  public static Image c;
  
  public static Image d;
  
  public static Image e;
  
  public static Image f;
  
  public static Image g;
  
  public static Image h;
  
  public static Image i;
  
  public static Image j;
  
  public static Image k;
  
  public static Image l;
  
  public static Image m;
  
  public static Image n;
  
  public static Image o;
  
  public static Image p;
  
  public static Image q;
  
  public static Image r;
  
  public static Image s;
  
  public static final void a() {
    n n1 = new n();
    try {
      b = Image.createImage("/res/animated.jpg");
      c = Image.createImage("/res/dealing.jpg");
      d = Image.createImage("/res/new_bg.jpg");
      e = Image.createImage("/res/button.jpg");
      f = Image.createImage("/res/n_0.png");
      g = Image.createImage("/res/n_1.png");
      h = Image.createImage("/res/n_2.png");
      i = Image.createImage("/res/n_3.png");
      j = Image.createImage("/res/n_4.png");
      k = Image.createImage("/res/n_5.png");
      l = Image.createImage("/res/n_6.png");
      m = Image.createImage("/res/n_7.png");
      n = Image.createImage("/res/n_8.png");
      o = Image.createImage("/res/n_9.png");
      p = Image.createImage("/res/n_10.png");
      q = Image.createImage("/res/scroll_bar.png");
      r = Image.createImage("/res/scroll_peg.png");
      s = Image.createImage("/res/faces.jpg");
    } catch (IOException iOException) {
      throw new Error();
    } 
    a = new com/pheephoo/mjgame/ui/b(n1);
  }
  
  public static final void b() {
    a.a();
  }
  
  public static final void c() {
    a.b();
  }
  
  public static final void d() {
    a.c();
  }
  
  public static final Image a(int paramInt) {
    return a.a(b, 0, paramInt * com.pheephoo.mjgame.b.ae, com.pheephoo.mjgame.b.ad, com.pheephoo.mjgame.b.ae);
  }
  
  public static final Image b(int paramInt) {
    return a.a(s, paramInt * com.pheephoo.mjgame.b.L, 0, com.pheephoo.mjgame.b.L, s.getHeight());
  }
  
  public static final Image c(int paramInt) {
    return a.a(e, 0, paramInt * com.pheephoo.mjgame.b.aG, e.getWidth(), com.pheephoo.mjgame.b.aG);
  }
  
  public static final Image d(int paramInt) {
    return (paramInt == 0) ? f : ((paramInt == 1) ? g : ((paramInt == 2) ? h : ((paramInt == 3) ? i : ((paramInt == 4) ? j : ((paramInt == 5) ? k : ((paramInt == 6) ? l : ((paramInt == 7) ? m : ((paramInt == 8) ? n : ((paramInt == 9) ? o : p)))))))));
  }
  
  class com/pheephoo/mjgame/ui/b extends Canvas implements Runnable {
    Graphics a;
    
    Image b;
    
    StringBuffer c = null;
    
    int d;
    
    int e;
    
    boolean f = false;
    
    Thread g;
    
    public com/pheephoo/mjgame/ui/b(n this$0) {
      setFullScreenMode(true);
      this.b = Image.createImage(getWidth(), getHeight());
      this.d = getWidth();
      this.e = getHeight();
      this.a = this.b.getGraphics();
      this.g = new Thread(this);
      this.g.start();
    }
    
    protected final void paint(Graphics param1Graphics) {
      param1Graphics.drawImage(this.b, 0, 0, 20);
    }
    
    public final synchronized void run() {
      byte b = 0;
      while (true) {
        if (!this.f)
          try {
            wait();
            this.c = new StringBuffer();
            this.c.append("Connecting  ");
            b = 0;
          } catch (InterruptedException interruptedException2) {
            InterruptedException interruptedException1;
            (interruptedException1 = null).printStackTrace();
          }  
        this.c.append(". ");
        if (b == 8) {
          b = 0;
          this.c.delete(0, this.c.length());
          this.c.append("Connecting  ");
        } 
        b++;
        this.a.setColor(86, 55, 44);
        this.a.fillRect(0, com.pheephoo.mjgame.b.ab, this.d, this.e);
        this.a.setColor(255, 255, 255);
        this.a.drawString(this.c.toString(), com.pheephoo.mjgame.b.ac, com.pheephoo.mjgame.b.ab + 4, 20);
        repaint();
        serviceRepaints();
        try {
          Thread.sleep(1000L);
        } catch (InterruptedException interruptedException) {}
      } 
    }
    
    public final void a() {
      this.a.drawImage(n.c, 0, com.pheephoo.mjgame.b.A, 20);
      this.a.drawImage(n.a(0), (this.d - com.pheephoo.mjgame.b.ad) / 2, this.e - com.pheephoo.mjgame.b.ae, 20);
    }
    
    public final synchronized void b() {
      if (this.f)
        return; 
      this.f = true;
      this.a.setColor(86, 55, 44);
      this.a.fillRect(0, com.pheephoo.mjgame.b.ab, this.d, this.e);
      this.a.setColor(255, 255, 255);
      this.a.setFont(Font.getFont(32, 1, 8));
      this.a.drawImage(n.d, 0, 0, 20);
      this.a.drawImage(n.c, 0, com.pheephoo.mjgame.b.A, 20);
      repaint();
      serviceRepaints();
      notify();
    }
    
    public final void c() {
      this.f = false;
    }
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgam\\ui\n.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */