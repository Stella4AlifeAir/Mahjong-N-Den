package com.pheephoo.mjgame.messaging;

import java.io.IOException;
import javax.microedition.io.Connector;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

public final class b {
  public static final void a(String paramString1, String paramString2) throws a, SecurityException {
    String str = null;
    System.out.println("SMSEngine.sendMessage() start... destnumber=" + paramString1 + " ;message=" + paramString2);
    MessageConnection messageConnection = null;
    str = "sms://" + paramString1;
    try {
      messageConnection = (MessageConnection)Connector.open(str);
      System.out.println("SMSEngine.sendMessage() connector opened");
      TextMessage textMessage = (TextMessage)messageConnection.newMessage("text");
      System.out.println("SMSEngine.sendMessage() textmessage is created");
      textMessage.setPayloadText(paramString2);
      System.out.println("SMSEngine.sendMessage() trying to send");
      messageConnection.send((Message)textMessage);
      System.out.println("SMSEngine.sendMessage() successfully send");
      messageConnection.close();
    } catch (IOException iOException2) {
      IOException iOException1;
      (iOException1 = null).printStackTrace();
      throw new a();
    } catch (SecurityException securityException2) {
      SecurityException securityException1;
      (securityException1 = null).printStackTrace();
      throw securityException1;
    } 
    System.out.println("SMSEngine.sendMessage() finished");
  }
}


/* Location:              D:\Project\Mahjong Den Development\Mahjong DEN\_sam_backup\2005.09.13_10.11am\_backup_mahjongbt\09.02.2005_11.33am\mjbt\deployed\mjbt.jar!\com\pheephoo\mjgame\messaging\b.class
 * Java compiler version: 1 (45.3)
 * JD-Core Version:       1.1.3
 */