/*
 * Created on 20/10/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
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

/**
 * @author sam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Setting {
	public static int sound;
	public static int picture=5;
	public static int minDouble;
	public static int maxDouble=5;
	public static String nick="You";
	
	public static void loadSetting() {
        RecordStore store = null;
        try {
             store = RecordStore.openRecordStore("usersetting", true);

             //skip reading record store if this is a newly created recordstore
             if (store.getNumRecords()==0) {
             	return;
             }
             ByteArrayInputStream byteIn = new ByteArrayInputStream (store.getRecord(1));
             DataInputStream dataIn = new DataInputStream(byteIn);
             
             sound = dataIn.readInt();
             if (sound >3 ||sound<0)
             	sound=2;
             
             picture= dataIn.readInt();
             if (picture>9 || picture<0)
             	picture=0;
             
             minDouble=dataIn.readInt();
             if (minDouble>10 || minDouble<0)
             	minDouble=0;
             
             maxDouble=dataIn.readInt();
             if (maxDouble>20 || maxDouble<1)
             	maxDouble=5;
             
             dataIn.close();
             byteIn.close();
             store.closeRecordStore();
             store=null;
        }
        
        catch (IOException io) {
        }
        catch (RecordStoreException rse) {
        }
        
        finally {
            try {
                if (store != null)
                     store.closeRecordStore();
            }
            catch (RecordStoreNotOpenException e) {
            }
            catch (RecordStoreException e) {
            }
        }
     }

	public static boolean saveSetting(){
        RecordStore store = null;
        try {
             try {
                RecordStore.deleteRecordStore("usersetting");
             }
             catch (RecordStoreNotFoundException rse){
             }

             store = RecordStore.openRecordStore("usersetting", true);

             ByteArrayOutputStream byteOut= new ByteArrayOutputStream();
             DataOutputStream dataOut = new DataOutputStream(byteOut);
             dataOut.writeInt(sound);
             dataOut.writeInt(picture);
             dataOut.writeInt(minDouble);
             dataOut.writeInt(maxDouble);
             dataOut.flush();

             //delete the old record and add the new record
             byte[] recordOut = byteOut.toByteArray();

             try {             
                store.setRecord(1, recordOut, 0, recordOut.length);
             }
             catch (InvalidRecordIDException ir) {
                store.addRecord(recordOut, 0, recordOut.length);
             }
             dataOut.close();
             byteOut.close();

             return true;
        }
        catch (IOException io) {
            return false;
        }
        
        catch (RecordStoreException rse) {
            return false;
        }
        
        finally {
        	try {
        		if (store != null) store.closeRecordStore();
        	}
        	catch (RecordStoreNotOpenException e) {
        	}
        	catch (RecordStoreException e) {
        	}
        }
    }

}
