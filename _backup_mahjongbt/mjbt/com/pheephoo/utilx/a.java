package com.pheephoo.utilx;

import java.util.Random;
import javax.microedition.lcdui.Image;

public final class a {
  private static Random a = new Random();
  
  public static final int a(int paramInt1, int paramInt2) {
    return paramInt1 + Math.abs(a.nextInt() >>> 1) % (paramInt2 - paramInt1 + 1);
  }
  
  public static final Image a(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
    Image image = Image.createImage(paramInt3, paramInt4);
    if (paramInt1 + paramInt3 > paramImage.getWidth() || paramInt2 + paramInt4 > paramImage.getHeight())
      System.out.println("error"); 
    image.getGraphics().drawImage(paramImage, -paramInt1, -paramInt2, 20);
    return image;
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheepho\\utilx\a.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */