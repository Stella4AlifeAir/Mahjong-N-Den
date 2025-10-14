
package com.pheephoo.mjgame.network;

import java.io.*;


import javax.microedition.io.*;

import com.pheephoo.mjgame.*;
import com.pheephoo.mjgame.engine.EngineProxy;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This class provides J2ME client access to J2EE services. It is created
 * by a code-generator; you should note that any changes you make will
 * be lost if you re-generate the code.
 *
 * The methods in this class are not thread-safe.
 */

public class MobileSocket {

    int debugLevel=0;
	long byteSend=0;
	long byteReceived=0;

	private String serverURL;
    
    SocketConnection socket;
    DataInputStream input;
    DataOutputStream output;
    
    Timer     timer = new Timer();
    TimerTask task = new ListenTask();
        
    public EngineProxy listener;
    
    boolean isCleanUp=false;//read by innerclass

    int connectionCount=0;
    Object data[]=new Object [3];
    
    /**
     * Constructs a new EndToEndClient
     * and initializes the URL to the servlet gateway from the given value
     *
     * @param serverURL URL of the deployed servlet
     */
    public MobileSocket(String serverURL) {
        this.serverURL = serverURL;
        
        //debug(2,"Creating MobileSocket, serverURL = " + serverURL);
        //seq=1;
    }
    
    
    
        
    public void registerListener(EngineProxy _listener) {
    	listener=_listener;
    }
   
    
    private void openSocket() throws IOException, SecurityException {
    	
    	if (socket==null) {
    		System.out.println ("trying to open socket.....");
    	
        	socket= (SocketConnection) Connector.open( serverURL);
    		System.out.println ("invokeServer() socketconnected");
	        output = socket.openDataOutputStream();
	        input= socket.openDataInputStream();
	        
	        timer.schedule(task,400);
    	}
    	
    }


    
    /**
     * Called by EngineProxy.doPoll()
     * After sending the message to server, this method returns immediately
     */
    public void invokeServer(int requestID, Object[] parameters) throws IOException {
    	//debug(2,"invokeServer2() using object is called:: ");

    	try {
	    	openSocket();
	
	        
	    	synchronized (output) {
		    	//8 = datalength+requestID*4
		        int datalength= 8+countLength(parameters);
			        
			    //debug(2,"REQUEST_ID = " + requestID + " ;writing datalength="+ datalength);
		        output.writeInt(datalength);
		        output.writeInt(requestID);
			        
		        if (parameters!=null) {
		            //debug(2,"PARAMLENGTH = " + parameters.length);
		            //write the command & length
		            //write the paramater
		            for (int i = 0; i < parameters.length; i++ ) {
		                //debug(2,"Writing parameters: "  );
		                writeObject(output, parameters[i]);
		            }
		            output.flush();
		        }
		        byteSend+=datalength;
	    	}
    	}
    	catch (IOException e) {
    		throw e;
    	}
    	//debug(2,"invokeServer2() using object finished");
    }
    
    private int countLength(Object[] parameters) {
        int count=0;
    	if (parameters==null) {
    		return 0;
    	}
        
    	//debug(2,"parameterlen="+parameters.length);
        for (int i=0;i<parameters.length;i++) {
	    	Object o=parameters[i];	
	    	if (o instanceof String) {
	    		count=count+4;// int to mark the length of the string;
	    		count=count +  2+((String ) o).getBytes().length;
	        	//debug(2,"string="+((String ) o).getBytes().length);
	        }
	        else if (o instanceof Short ) {
	    		count=count +  2;
	    		//debug(2,"short=1");
	        }
	        else if (o instanceof Integer ) {
	    		count=count +  4;
	    		//debug(2,"integer=4");
	        }
	        else if (o instanceof Boolean) {
	    		count=count +  1;
	    		//debug(2,"integer=1");
	        }
	        else if (o instanceof int[]) {
	        	count=count + ((int[])o).length *4;
	    		//debug(2,"arrayofinteger="+((int[])o).length *2);
	        }
    	}    	
    	
    	return count;
    }
    
    private  void writeObject(DataOutputStream out, Object o) throws IOException {
        //debug(0,"writeObject() is called");
        if (o instanceof String) {
            ////debug(2, (String) o + " (String)");
        
        	int strlen=((String ) o).getBytes().length;
        	out.writeInt(strlen);
        	out.writeUTF(  (String) o);
        	//out.writeUTF((String) (String) "s");
        }
        else if (o instanceof Short ) {
            //debug(0, (Short) o + " (short)");
            out.writeShort( ((Short)o).shortValue() );
        }
        else if (o instanceof Integer ) {
            //debug(0, (Integer) o + " (int)");
            out.writeInt( ((Integer)o).intValue() );
        }
        else if (o instanceof Boolean) {
            //debug(0, (Boolean) o + " (boolean)");
            out.writeBoolean( ((Boolean)o).booleanValue() );
        }
        else if (o instanceof int[]) {
            //debug(0,"writing out array of integer");
            int arrayint[]= (int []) o;
            //debug(0,"array length="+arrayint.length);
            for (int i=0;i<arrayint.length;i++ ) {
            	out.writeInt(arrayint[i]);
            }
        }
		
    }
    
    //first object holding the type
    //second object hold array of integer
    //third object hold array of string
    private  synchronized Object[] readObject(DataInputStream in) throws IOException {
        //debug(2,"***********readObject() is called");
        int type = in.readInt();        
        
        byteReceived+=4;
        
        //debug(2,"Response type = " + type );
        if (type==Constant.RESPONSE_OK) {
            data[0]= new Integer (type);
            return data;
        }
        int lengthInt= in.readInt();
        int lengthUTF= in.readInt();
        byteReceived+=8;
        //debug(2,"type = " + type +" ;lengthInt="+lengthInt+ " ;lengthUTF=" + lengthUTF);
        data[0]= new Integer (type);
        data[1]=null;
        data[2]=null;
        
        if (lengthInt>0) {
        	int arrayInt[]= new int[lengthInt];
	        for (int i=0;i<lengthInt;i++) {
	        	arrayInt[i]=in.readInt();
	            byteReceived+=4;

	        	//debug(2,"reading data=" + arrayInt[i]);
	        }
	        data[1]=arrayInt;
        }
        if (lengthUTF>0) {
        	String arrayUTF[] = new String[lengthUTF];
                	
        	for (int i=0;i<lengthUTF;i++) {
            	int ch;
            	int charlen= in.readInt();
                byteReceived+=4;

            	StringBuffer b= new StringBuffer();	    
            	//debug(2,"charlen="+charlen);
            	for (int j=0;j<charlen;j++) {
            		ch = in.read();
                	b.append((char) ch);
                    byteReceived+=2;

            	}
        		arrayUTF[i]=new String(b);
	        	//debug(2,"reading data=" + arrayUTF[i]);
	        }
        	data[2]=arrayUTF;
        }
        
        //debug(2,"readObject() finished");

        //return data;
        return data;
    }
    
    
    public void doCleanUp() {
    	isCleanUp=true; 
    	closeConnection();
    	//debug(2,"Open connection="+connectionCount);
    }
    
    private void closeConnection() {
    	try {
        	//debug(2,"MobileSocket.cleanUp ...");
    		if (timer!=null) {
    			timer.cancel();
    			task.cancel();
            	//debug(2,"MobileSocket: all timer canceled..");    			
    		}
    		socket.close();
    		//debug(2,"socket is closed");
    		input.close();
    		//debug(2,"inputstream is closed");
    		output.close();
    		//debug(2,"outputstream is closed");
    	}
    	catch (IOException e) {
        	//debug(2,"error closing connection!!!!!...");
    	}
    	
    }
    

    
    /*
     * Helper method for debugging
     */
    private void debug(int level, String txt) {
    	
    	if (level>=debugLevel) {
    		System.out.println("EndClient."+txt);
    	}
    }    



    private class ListenTask extends TimerTask {
    	public void run() {
    		try {
        		listen();    			
    		}
    		catch (IOException ie){
    			listener.communicationError();
    		}
    	}
    	
        /*
         * 	blocking... wait for message from server
         */
        public  void listen() throws IOException {

        	//debug(10,"messageListener() started");

        	
        	
        	//if (!isCleanUp && !isWaiting) {
    	     while(!isCleanUp)   {
        		try {
    	        	//debug(3,"Waiting for message ....");        	
			        Object[] obj= readObject(input);
			        
			        if (isCleanUp) {
			        	break;
			        }
			        //debug(2,"messageListener() before calling listener.processMessage()");
			        listener.processMessage(obj);
			        //debug(2,"messageListener() after calling listener.processMessage()");
    		    	//isWaiting=false;
    	        } 
    	        catch (IOException e) {
    	        	e.printStackTrace();
    		        closeConnection();
    	        }
    	        //debug(1000,"BYTESEND="+byteSend+ " ;BYTERECEIVED="+byteReceived);
    	     }
    	        
    	        
        	//}
        	
        	
	    	//debug(2,"messageListener() finished");
        }
    }
    

}