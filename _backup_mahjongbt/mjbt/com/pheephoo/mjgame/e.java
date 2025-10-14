package com.pheephoo.mjgame;

final class e extends Thread {
  final MJGame a;
  
  e(MJGame paramMJGame) {
    this.a = paramMJGame;
  }
  
  public final void run() {
    if (MJGame.c(this.a) == 1) {
      this.a.f();
      return;
    } 
    this.a.g();
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\e.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */