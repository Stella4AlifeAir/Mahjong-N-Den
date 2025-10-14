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


import java.util.*;

/*
 * RecordStore for storing user data, eg. cid, play history
 * History access method (read,write) is implemented as static method
 * 
 * RecordStore constant: userdata, historyonline, historyoffline
 * 
 * @author Samuel
 * @version 1.00, 24/04/2005
 */
public class UserData {
	public int cid;
	public int code;
	public int numOfPlay;
	public long firstPlayTime;
	public int clientType;
	
	public static Vector history;
	
	public UserData() {
		load();
	}

	public static Vector getHistory(int mode) {
		//System.out.println("getHistory,mode="+mode);
		RecordStore store = null;
        try {
        	history=new Vector();
        	
        	if (mode==0) {
             store = RecordStore.openRecordStore("historyoffline", true);
        	}
        	else if (mode==1) {
                store = RecordStore.openRecordStore("historyonline", true);
        	}
            if (store.getNumRecords()==0) {
            	//System.out.println("noRecord");
             	return history;
            }
            for (int i=0;i<store.getNumRecords();i++) {
             	ByteArrayInputStream byteIn = new ByteArrayInputStream (store.getRecord(i+1));
                DataInputStream dataIn = new DataInputStream(byteIn);
                PlayerInfo[] playerInfo=new PlayerInfo[4];
                for(int j=0;j<4;j++) {
                	playerInfo[j]=new PlayerInfo();
                	playerInfo[j].picture=dataIn.readInt();
                	playerInfo[j].nick=dataIn.readUTF();
                	playerInfo[j].lastScore=dataIn.readInt();
	                
                	if (mode==1) {
                		//System.out.println("getDate");
                		playerInfo[j].dateStr=dataIn.readUTF();
                	}
                	
                	dataIn.close();
	                byteIn.close();
                }              
                history.insertElementAt(playerInfo,0);
            }
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
		return history;
	}
	
	public static void addHistory(PlayerInfo[] playerInfo, int mode, String dataStr) {
        RecordStore store = null;
        //System.out.println("addHistory,mode="+mode);
        
        try {
        	if (mode==0) {
       			store = RecordStore.openRecordStore("historyoffline", true);
       		}
       		else if (mode==1) {
       			store = RecordStore.openRecordStore("historyonline", true);
       		}
            
            ByteArrayOutputStream byteOut= new ByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(byteOut);

            for (int i=0;i<4;i++) {
            	PlayerInfo info=playerInfo[i];
                dataOut.writeInt(info.picture);
                dataOut.writeUTF(info.nick);
                dataOut.writeInt(info.lastScore);
                if (mode==1) {
                	//System.out.println("dataStr="+dataStr);
                	dataOut.writeUTF(dataStr);
                }
            }
            
            dataOut.flush();
            //System.out.println("successfully written");
            
            //delete the old record and add the new record
            byte[] recordOut = byteOut.toByteArray();

            try {
                store.addRecord(recordOut, 0, recordOut.length);
            }
            catch (InvalidRecordIDException ir) {
            }
            dataOut.close();
            byteOut.close();
       }
       catch (IOException io) {
       }
       catch (RecordStoreException rse) {
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
	
	public void load() {
        RecordStore store = null;
        try {
             store = RecordStore.openRecordStore("userdata", true);
             //skip reading record store if this is a newly created recordstore
             ////System.out.println("number of records="+ store.getNumRecords());			 
             if (store.getNumRecords()==0) {
             	cid=0;
             	numOfPlay=0;
             	firstPlayTime=0;
             	clientType=-1;
             	return;
             }
             ByteArrayInputStream byteIn = new ByteArrayInputStream (store.getRecord(1));
             DataInputStream dataIn = new DataInputStream(byteIn);
             
             cid= dataIn.readInt();             
             numOfPlay= dataIn.readInt();
             firstPlayTime=dataIn.readLong();
             clientType=dataIn.readInt();
             code=dataIn.readInt();             
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
                if (store != null) {
                     store.closeRecordStore();
                }
            }
            catch (RecordStoreNotOpenException e) {
            }
            catch (RecordStoreException e) {
            }
        }
     }

	public boolean save(){
        RecordStore store = null;
         
        try {
             try {
                RecordStore.deleteRecordStore("userdata");
             }
             catch (RecordStoreNotFoundException rse){
             }

             store = RecordStore.openRecordStore("userdata", true);

             ByteArrayOutputStream byteOut= new ByteArrayOutputStream();
             DataOutputStream dataOut = new DataOutputStream(byteOut);

             dataOut.writeInt(cid);
             dataOut.writeInt(numOfPlay);
             dataOut.writeLong(firstPlayTime);
             dataOut.writeInt(clientType);
             dataOut.writeInt(code);
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
