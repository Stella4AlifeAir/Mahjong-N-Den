package com.pheephoo.mjgame.network;

import com.pheephoo.mjgame.engine.h;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

public final class d {
  long a = 0L;
  
  long b = 0L;
  
  private String k;
  
  SocketConnection c;
  
  DataInputStream d;
  
  DataOutputStream e;
  
  Timer f = new Timer();
  
  TimerTask g = new com/pheephoo/mjgame/network/a(this);
  
  public h h;
  
  boolean i = false;
  
  Object[] j = new Object[3];
  
  public d(String paramString) {
    this.k = paramString;
  }
  
  private void b() throws IOException, SecurityException {
    if (this.c == null) {
      System.out.println("trying to open socket.....");
      this.c = (SocketConnection)Connector.open(this.k);
      System.out.println("invokeServer() socketconnected");
      this.e = this.c.openDataOutputStream();
      this.d = this.c.openDataInputStream();
      this.f.schedule(this.g, 400L);
    } 
  }
  
  public final void a(int paramInt, Object[] paramArrayOfObject) throws IOException {
    try {
      b();
      synchronized (this.e) {
        int i = 8 + a(paramArrayOfObject);
        this.e.writeInt(i);
        this.e.writeInt(paramInt);
        if (paramArrayOfObject != null) {
          for (byte b = 0; b < paramArrayOfObject.length; b++)
            a(this.e, paramArrayOfObject[b]); 
          this.e.flush();
        } 
        this.a += i;
        return;
      } 
    } catch (IOException iOException2) {
      IOException iOException1;
      throw iOException1 = null;
    } 
  }
  
  private int a(Object[] paramArrayOfObject) {
    int i = 0;
    if (paramArrayOfObject == null)
      return 0; 
    for (byte b = 0; b < paramArrayOfObject.length; b++) {
      Object object;
      if (object = paramArrayOfObject[b] instanceof String) {
        i += true;
        i = i + 2 + (((String)object).getBytes()).length;
      } else if (object instanceof Short) {
        i += 2;
      } else if (object instanceof Integer) {
        i += 4;
      } else if (object instanceof Boolean) {
        i++;
      } else if (object instanceof int[]) {
        i += ((int[])object).length * 4;
      } 
    } 
    return i;
  }
  
  private void a(DataOutputStream paramDataOutputStream, Object paramObject) throws IOException {
    if (paramObject instanceof String) {
      int i = (((String)paramObject).getBytes()).length;
      paramDataOutputStream.writeInt(i);
      paramDataOutputStream.writeUTF((String)paramObject);
      return;
    } 
    if (paramObject instanceof Short) {
      paramDataOutputStream.writeShort(((Short)paramObject).shortValue());
      return;
    } 
    if (paramObject instanceof Integer) {
      paramDataOutputStream.writeInt(((Integer)paramObject).intValue());
      return;
    } 
    if (paramObject instanceof Boolean) {
      paramDataOutputStream.writeBoolean(((Boolean)paramObject).booleanValue());
      return;
    } 
    if (paramObject instanceof int[]) {
      int[] arrayOfInt = (int[])paramObject;
      for (byte b = 0; b < arrayOfInt.length; b++)
        paramDataOutputStream.writeInt(arrayOfInt[b]); 
    } 
  }
  
  private synchronized Object[] a(DataInputStream paramDataInputStream) throws IOException {
    int i = paramDataInputStream.readInt();
    this.b += 4L;
    if (i == com.pheephoo.mjgame.d.br) {
      this.j[0] = new Integer(i);
      return this.j;
    } 
    int j = paramDataInputStream.readInt();
    int k = paramDataInputStream.readInt();
    this.b += 8L;
    this.j[0] = new Integer(i);
    this.j[1] = null;
    this.j[2] = null;
    if (j > 0) {
      int[] arrayOfInt = new int[j];
      for (byte b = 0; b < j; b++) {
        arrayOfInt[b] = paramDataInputStream.readInt();
        this.b += 4L;
      } 
      this.j[1] = arrayOfInt;
    } 
    if (k > 0) {
      String[] arrayOfString = new String[k];
      for (byte b = 0; b < k; b++) {
        int m = paramDataInputStream.readInt();
        this.b += 4L;
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b1 = 0; b1 < m; b1++) {
          int n = paramDataInputStream.read();
          stringBuffer.append((char)n);
          this.b += 2L;
        } 
        arrayOfString[b] = new String(stringBuffer);
      } 
      this.j[2] = arrayOfString;
    } 
    return this.j;
  }
  
  public final void a() {
    this.i = true;
    c();
  }
  
  private void c() {
    try {
      if (this.f != null) {
        this.f.cancel();
        this.g.cancel();
      } 
      this.c.close();
      this.d.close();
      this.e.close();
      return;
    } catch (IOException iOException) {
      return;
    } 
  }
  
  static final Object[] a(d paramd, DataInputStream paramDataInputStream) {
    return paramd.a(paramDataInputStream);
  }
  
  static final void a(d paramd) {
    paramd.c();
  }
  
  private class com/pheephoo/mjgame/network/a extends TimerTask {
    final d a;
    
    com/pheephoo/mjgame/network/a(d this$0) {
      this.a = this$0;
    }
    
    public final void run() {
      try {
        a();
        return;
      } catch (IOException iOException) {
        this.a.h.m();
        return;
      } 
    }
    
    public final void a() throws IOException {
      while (!this.a.i) {
        try {
          Object[] arrayOfObject = d.a(this.a, this.a.d);
          if (this.a.i)
            return; 
          this.a.h.a(arrayOfObject);
        } catch (IOException iOException2) {
          IOException iOException1;
          (iOException1 = null).printStackTrace();
          d.a(this.a);
        } 
      } 
    }
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\network\d.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */