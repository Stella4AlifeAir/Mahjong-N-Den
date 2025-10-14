package com.pheephoo.mjgame.engine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

public final class k {
  public static int a;
  
  public static int b = 5;
  
  public static int c;
  
  public static int d = 5;
  
  public static String e = "You";
  
  public static final void a() {
    RecordStore recordStore = null;
    try {
      if ((recordStore = RecordStore.openRecordStore("usersetting", true)).getNumRecords() == 0)
        return; 
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(recordStore.getRecord(1));
      DataInputStream dataInputStream;
      a = (dataInputStream = new DataInputStream(byteArrayInputStream)).readInt();
      if (a > 3 || a < 0)
        a = 2; 
      b = dataInputStream.readInt();
      if (b > 9 || b < 0)
        b = 0; 
      c = dataInputStream.readInt();
      if (c > 10 || c < 0)
        c = 0; 
      d = dataInputStream.readInt();
      if (d > 20 || d < 1)
        d = 5; 
      dataInputStream.close();
      byteArrayInputStream.close();
      recordStore.closeRecordStore();
    } catch (IOException iOException) {
    
    } catch (RecordStoreException recordStoreException) {
    
    } finally {
      try {
        if (recordStore != null)
          recordStore.closeRecordStore(); 
      } catch (RecordStoreNotOpenException recordStoreNotOpenException) {
      
      } catch (RecordStoreException recordStoreException) {}
    } 
    try {
      if (recordStore != null) {
        recordStore.closeRecordStore();
        return;
      } 
    } catch (RecordStoreNotOpenException recordStoreNotOpenException) {
      return;
    } catch (RecordStoreException recordStoreException) {}
  }
  
  public static final boolean b() {
    RecordStore recordStore = null;
    try {
      try {
        RecordStore.deleteRecordStore("usersetting");
      } catch (RecordStoreNotFoundException recordStoreNotFoundException) {}
      recordStore = RecordStore.openRecordStore("usersetting", true);
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      DataOutputStream dataOutputStream;
      (dataOutputStream = new DataOutputStream(byteArrayOutputStream)).writeInt(a);
      dataOutputStream.writeInt(b);
      dataOutputStream.writeInt(c);
      dataOutputStream.writeInt(d);
      dataOutputStream.flush();
      byte[] arrayOfByte = byteArrayOutputStream.toByteArray();
      try {
        recordStore.setRecord(1, arrayOfByte, 0, arrayOfByte.length);
      } catch (InvalidRecordIDException invalidRecordIDException) {
        recordStore.addRecord(arrayOfByte, 0, arrayOfByte.length);
      } 
      dataOutputStream.close();
      byteArrayOutputStream.close();
      return true;
    } catch (IOException iOException) {
      return false;
    } catch (RecordStoreException recordStoreException) {
      return false;
    } finally {
      try {
        if (recordStore != null)
          recordStore.closeRecordStore(); 
      } catch (RecordStoreNotOpenException recordStoreNotOpenException) {
      
      } catch (RecordStoreException recordStoreException) {}
    } 
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\engine\k.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */