package com.pheephoo.mjgame.engine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;
import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotFoundException;
import javax.microedition.rms.RecordStoreNotOpenException;

public final class u {
  public int a;
  
  public int b;
  
  public int c;
  
  public long d;
  
  public int e;
  
  public static Vector f;
  
  public u() {
    a();
  }
  
  public static final Vector a(int paramInt) {
    RecordStore recordStore = null;
    try {
      f = new Vector();
      if (paramInt == 0) {
        recordStore = RecordStore.openRecordStore("historyoffline", true);
      } else if (paramInt == 1) {
        recordStore = RecordStore.openRecordStore("historyonline", true);
      } 
      if (recordStore.getNumRecords() == 0)
        return f; 
      for (byte b = 0; b < recordStore.getNumRecords(); b++) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(recordStore.getRecord(b + 1));
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        d[] arrayOfD = new d[4];
        for (byte b1 = 0; b1 < 4; b1++) {
          arrayOfD[b1] = new d();
          (arrayOfD[b1]).b = dataInputStream.readInt();
          (arrayOfD[b1]).c = dataInputStream.readUTF();
          (arrayOfD[b1]).e = dataInputStream.readInt();
          if (paramInt == 1)
            (arrayOfD[b1]).d = dataInputStream.readUTF(); 
          dataInputStream.close();
          byteArrayInputStream.close();
        } 
        f.insertElementAt(arrayOfD, 0);
      } 
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
      if (recordStore != null)
        recordStore.closeRecordStore(); 
    } catch (RecordStoreNotOpenException recordStoreNotOpenException) {
    
    } catch (RecordStoreException recordStoreException) {}
    return f;
  }
  
  public static final void a(d[] paramArrayOfd, int paramInt, String paramString) {
    RecordStore recordStore = null;
    try {
      if (paramInt == 0) {
        recordStore = RecordStore.openRecordStore("historyoffline", true);
      } else if (paramInt == 1) {
        recordStore = RecordStore.openRecordStore("historyonline", true);
      } 
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
      for (byte b = 0; b < 4; b++) {
        d d1 = paramArrayOfd[b];
        dataOutputStream.writeInt(d1.b);
        dataOutputStream.writeUTF(d1.c);
        dataOutputStream.writeInt(d1.e);
        if (paramInt == 1)
          dataOutputStream.writeUTF(paramString); 
      } 
      dataOutputStream.flush();
      byte[] arrayOfByte = byteArrayOutputStream.toByteArray();
      try {
        recordStore.addRecord(arrayOfByte, 0, arrayOfByte.length);
      } catch (InvalidRecordIDException invalidRecordIDException) {}
      dataOutputStream.close();
      byteArrayOutputStream.close();
    } catch (IOException iOException) {
    
    } catch (RecordStoreException recordStoreException) {
    
    } finally {
      try {
        if (recordStore != null)
          recordStore.closeRecordStore(); 
      } catch (RecordStoreNotOpenException recordStoreNotOpenException) {
      
      } catch (RecordStoreException recordStoreException) {}
    } 
  }
  
  public final void a() {
    RecordStore recordStore = null;
    try {
      if ((recordStore = RecordStore.openRecordStore("userdata", true)).getNumRecords() == 0) {
        this.a = 0;
        this.c = 0;
        this.d = 0L;
        this.e = -1;
        return;
      } 
      ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(recordStore.getRecord(1));
      DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
      this.a = dataInputStream.readInt();
      this.c = dataInputStream.readInt();
      this.d = dataInputStream.readLong();
      this.e = dataInputStream.readInt();
      this.b = dataInputStream.readInt();
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
  
  public final boolean b() {
    RecordStore recordStore = null;
    try {
      try {
        RecordStore.deleteRecordStore("userdata");
      } catch (RecordStoreNotFoundException recordStoreNotFoundException) {}
      recordStore = RecordStore.openRecordStore("userdata", true);
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      DataOutputStream dataOutputStream;
      (dataOutputStream = new DataOutputStream(byteArrayOutputStream)).writeInt(this.a);
      dataOutputStream.writeInt(this.c);
      dataOutputStream.writeLong(this.d);
      dataOutputStream.writeInt(this.e);
      dataOutputStream.writeInt(this.b);
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


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\engin\\u.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */