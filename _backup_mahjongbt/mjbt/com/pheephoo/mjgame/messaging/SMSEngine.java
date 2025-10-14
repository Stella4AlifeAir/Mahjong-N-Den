
package com.pheephoo.mjgame.messaging;

import javax.microedition.io.*;
import javax.wireless.messaging.*;
import java.io.*;

/**
 * @author sam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SMSEngine {

	public static void sendMessage(String destNumber, String message) throws SMSSendException, SecurityException{
		String address="";
		System.out.println("SMSEngine.sendMessage() start... destnumber="+destNumber+" ;message="+message);
		MessageConnection smsconn = null;

		//only for testing 
		address = "sms://" +destNumber; 
		
		/*
		if (destNumber.length()<=8) {
			address = "sms://" + DeviceConstant.LOCAl_COUNTRYCODE+destNumber; 
		}
		else {
			address = "sms://" + destNumber ;
		}
		*/
		try {
//			Open the message connection.
			smsconn = (MessageConnection)Connector.open(address);
			System.out.println("SMSEngine.sendMessage() connector opened");
			TextMessage txtMessage =(TextMessage)smsconn.newMessage(MessageConnection.TEXT_MESSAGE);
			System.out.println("SMSEngine.sendMessage() textmessage is created");
			txtMessage.setPayloadText(message);
			System.out.println("SMSEngine.sendMessage() trying to send");
			smsconn.send(txtMessage);
			System.out.println("SMSEngine.sendMessage() successfully send");
			smsconn.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new SMSSendException();
		}
		catch (SecurityException e) {
			e.printStackTrace();
			throw e;
		}
		System.out.println("SMSEngine.sendMessage() finished");

	}
	
}
