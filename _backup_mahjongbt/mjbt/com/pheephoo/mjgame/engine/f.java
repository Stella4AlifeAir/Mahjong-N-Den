package com.pheephoo.mjgame.engine;

import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.d;
import java.io.IOException;
import java.util.Vector;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DataElement;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connection;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import javax.microedition.lcdui.Canvas;

public final class f implements Runnable {
  private MJGame a;
  
  private int b = 0;
  
  private Thread c;
  
  private LocalDevice d;
  
  private StreamConnectionNotifier e;
  
  private static final UUID f = new UUID("F0E0D0C0B0A000908070605040302010", false);
  
  private ServiceRecord g;
  
  private com/pheephoo/mjgame/engine/j h;
  
  private boolean i;
  
  public f(MJGame paramMJGame) {
    a(2, " constructor()");
    this.a = paramMJGame;
    a();
  }
  
  private void a() {
    a(2, "init()");
    this.c = new Thread(this);
    this.c.start();
  }
  
  private void b() {
    a(2, "serviceRegistration() start");
    StringBuffer stringBuffer = new StringBuffer("btspp://localhost:" + f.toString() + ";name=Mahjong Server" + ";authorize=false");
    try {
      this.e = (StreamConnectionNotifier)Connector.open(stringBuffer.toString());
    } catch (IOException iOException2) {
      IOException iOException1;
      (iOException1 = null).printStackTrace();
      a(2, iOException1.getClass() + ":" + iOException1.getMessage());
    } 
    this.g = this.d.getRecord((Connection)this.e);
    a(2, "serviceRegistration() end");
  }
  
  public final synchronized void run() {
    a(0, "run()");
    boolean bool = false;
    try {
      this.d = LocalDevice.getLocalDevice();
      this.d.setDiscoverable(10390323);
      b();
      DataElement dataElement = new DataElement(48);
      this.g.setAttributeValue(17185, dataElement);
      bool = true;
    } catch (BluetoothStateException bluetoothStateException) {
      System.err.println("Can't initialize bluetooth: " + bluetoothStateException);
    } catch (SecurityException securityException) {
      System.err.println("SecurityException: " + securityException);
      this.a.c(d.e);
    } 
    if (!bool)
      return; 
    this.h = new com/pheephoo/mjgame/engine/j(this);
    while (!this.i) {
      StreamConnection streamConnection;
      try {
        streamConnection = this.e.acceptAndOpen();
      } catch (IOException iOException) {
        continue;
      } 
      this.h.a(streamConnection);
    } 
  }
  
  public final void a(Canvas paramCanvas) {
    a(2, "setGUI() start");
    a(2, "setGUI() finish");
  }
  
  final void a(int paramInt, String paramString) {
    if (paramInt >= this.b)
      System.out.println("BTServer." + paramString); 
  }
  
  static final boolean a(f paramf) {
    return paramf.i;
  }
  
  private class com/pheephoo/mjgame/engine/j implements Runnable {
    private Thread b;
    
    private Vector c;
    
    final f a;
    
    com/pheephoo/mjgame/engine/j(f this$0) {
      this.a = this$0;
      this.c = new Vector();
      this.b = new Thread(this);
      this.b.start();
    }
    
    public final void run() {
      while (!f.a(this.a)) {
        synchronized (this) {
          if (this.c.size() == 0)
            try {
              wait();
            } catch (InterruptedException interruptedException) {
              System.err.println("Unexpected exception: " + interruptedException);
              a(false);
              return;
            }  
        } 
        synchronized (this) {
          if (f.a(this.a))
            return; 
          this.c.firstElement();
          this.c.removeElementAt(0);
        } 
      } 
    }
    
    final void a(StreamConnection param1StreamConnection) {
      synchronized (this) {
        this.c.addElement(param1StreamConnection);
        notify();
        return;
      } 
    }
    
    final void a(boolean param1Boolean) {
      synchronized (this) {
        notify();
        while (this.c.size() != 0) {
          StreamConnection streamConnection = this.c.firstElement();
          this.c.removeElementAt(0);
          try {
            streamConnection.close();
          } catch (IOException iOException) {}
        } 
      } 
      try {
        this.b.join();
        return;
      } catch (InterruptedException interruptedException) {
        return;
      } 
    }
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\engine\f.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */