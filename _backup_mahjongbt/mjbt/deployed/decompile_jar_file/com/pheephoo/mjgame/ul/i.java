package com.pheephoo.mjgame.ui;

import com.pheephoo.mjgame.d;
import java.util.TimerTask;

final class com/pheephoo/mjgame/ui/i extends TimerTask {
  final l a;
  
  com/pheephoo/mjgame/ui/i(l paraml) {
    this.a = paraml;
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


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgam\\ui\i.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */