package com.pheephoo.mjgame.engine;

import java.io.IOException;
import java.util.Vector;
import javax.microedition.io.StreamConnection;

final class com/pheephoo/mjgame/engine/j implements Runnable {
  private Thread b;
  
  private Vector c;
  
  final f a;
  
  com/pheephoo/mjgame/engine/j(f paramf) {
    this.a = paramf;
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
  
  final void a(StreamConnection paramStreamConnection) {
    synchronized (this) {
      this.c.addElement(paramStreamConnection);
      notify();
      return;
    } 
  }
  
  final void a(boolean paramBoolean) {
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


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\engine\j.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */