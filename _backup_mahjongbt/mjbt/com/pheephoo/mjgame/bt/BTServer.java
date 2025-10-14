/*
 * Created on 22/07/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pheephoo.mjgame.bt;

import java.io.IOException;

import javax.bluetooth.*;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import javax.microedition.lcdui.Canvas;
import java.util.*;

import com.pheephoo.mjgame.Constant;
import com.pheephoo.mjgame.MJGame;
import com.pheephoo.mjgame.ui.*;

/**
 * @author sam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BTServer implements Runnable {

    private MJGame midlet;
    
    private BTServerCanvas canvas;
    
    private int debugLevel=0;
    
    /** Creates notifier and accepts clients to be processed. */
    private Thread accepterThread;

    /** Keeps the local device reference. */
    private LocalDevice localDevice;

    /** Accepts new connections. */
    private StreamConnectionNotifier notifier;

    /** Describes this server */
    private static final UUID PICTURES_SERVER_UUID = 
        new UUID("F0E0D0C0B0A000908070605040302010", false);

    /** The attribute id of the record item with images names. */
    private static final int IMAGES_NAMES_ATTRIBUTE_ID = 0x4321;

    /** Keeps the information about this server. */
    private ServiceRecord record;

    /** Process the particular client from queue. */
    private ClientProcessor processor;

    
    /** Becomes 'true' when this component is finilized. */
    private boolean isClosed;

    public BTServer(MJGame _midlet) {
    	debug(2," constructor()");
    	midlet=_midlet;
    	init();
    }
    
    private void init() {
    	debug(2,"init()");
    	
        // we have to initialize a system in different thread...
        accepterThread = new Thread(this);
        accepterThread.start();
    	
    }
    
    
    private void serviceRegistration() {
    	debug(2,"serviceRegistration() start");
        // prepare a URL to create a notifier
        String url = "btspp://localhost:"+
        		PICTURES_SERVER_UUID.toString()+
				";name=MahjongServer"+
				";authorize=false";        // request all of the client not to be authorized some devices fail on authorize=true

        // create notifier now
        try {
            notifier = (StreamConnectionNotifier) Connector.open(url);        	
        }
        catch (IOException e) {
        	e.printStackTrace();
        	debug(2,e.getClass() + ":"+e.getMessage());
        }
        // and remember the service record for the later updates
        record = localDevice.getRecord(notifier);
    	debug(2,"serviceRegistration() end");

    }
    
    public synchronized void run() {
	   	debug(0,"run()");
        boolean isBTReady = false;
        try {

            localDevice = LocalDevice.getLocalDevice();
            if (!localDevice.setDiscoverable(DiscoveryAgent.GIAC)) {
                // Some implementations always return false, even if 
                // setDiscoverable successful
                // throw new IOException("Can't set discoverable mode...");
            }

            serviceRegistration();

            // create a special attribute with images names
            DataElement base = new DataElement(DataElement.DATSEQ);
            record.setAttributeValue(IMAGES_NAMES_ATTRIBUTE_ID, base);

            // remember we've reached this point.
            isBTReady = true;
        } 
        catch (BluetoothStateException e) {
            System.err.println("Can't initialize bluetooth: " + e);
        }
	    catch (SecurityException e) {
	        System.err.println("SecurityException: " + e);
	        midlet.handleSecurityException(Constant.SECURITY_EXCEPTION_BT);
	    }
        
        
        //GUIRelated thing
//        parent.completeInitialization(isBTReady);

        // nothing to do if no bluetooth available
        if (!isBTReady) {
            return;
        }

        // ok, start processor now
        processor = new ClientProcessor();

        // ok, start accepting connections then
        while (!isClosed) {
        	debug(2,"waiting for connection");
            StreamConnection conn = null;

            try {
                conn = notifier.acceptAndOpen();
            } catch (IOException e) {

                // wrong client or interrupted - continue anyway
                continue;
            }
            processor.addConnection(conn);
        	debug(2,"connected...");

        }

       
    }

    
    
    /**
     * Organizes the queue of clients to be processed,
     * processes the clients one by one until destroyed.
     */
    private class ClientProcessor implements Runnable {
        private Thread processorThread;
        private Vector queue = new Vector();
        private boolean isOk = true;

        ClientProcessor() {
            processorThread = new Thread(this);
            processorThread.start();
        }

        public void run() {
            while (!isClosed) {

                // wait for new task to be processed
                synchronized (this) {
                    if (queue.size() == 0) {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                            System.err.println("Unexpected exception: " + e);
                            destroy(false);
                            return;
                        }
                    }
                }

                // send the image to specified connection
                StreamConnection conn;

                synchronized (this) {

                    // may be awaked by "destroy" method.
                    if (isClosed) {
                        return;
                    }
                    conn = (StreamConnection) queue.firstElement();
                    queue.removeElementAt(0);
                    
                    
//                    processConnecti on(conn);
                }
            }
        }

        /** Adds the connection to queue and notifys the thread. */
        void addConnection(StreamConnection conn) {
            synchronized (this) {
                queue.addElement(conn);
                notify();
            }
        }

        /** Closes the connections and . */
        void destroy(boolean needJoin) {
            StreamConnection conn;

            synchronized (this) {
                notify();

                while (queue.size() != 0) {
                    conn = (StreamConnection) queue.firstElement();
                    queue.removeElementAt(0);

                    try {
                        conn.close();
                    } catch (IOException e) {} // ignore
                }
            }

            // wait until dispatching thread is done
            try {
                processorThread.join();
            } catch (InterruptedException e) {} // ignore
        }
    }

    public void setGUI(Canvas _canvas) {
    	debug(2,"setGUI() start");
    	canvas= (BTServerCanvas) _canvas;
    	debug(2,"setGUI() finish");
    }
    
    
    
    /*
     * *************************************************************************
     * Helper method for //debugging
     * ***********************************************************************
     */
    void debug(int level, String txt) {
    	if (level>=debugLevel)
    		System.out.println("BTServer."+txt);
    }    

}
