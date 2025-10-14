/*
 * Created on 1/11/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pheephoo.mjgame.network;


import com.pheephoo.mjgame.ui.PublicNetworkCanvas;
import java.util.Vector;
/**
 * @author sam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface NetworkFunctionIF {

	public void setCanvas(PublicNetworkCanvas canvas);
	
	//public Vector getPublicRoom();
	
	public void getPublicRoom(int index);
	
	public void getRoomCategory();
	
	
	public int getCid();
	public int getActivationCode();
	
	public void onSMSSendAction(int action);
	
	
	
	//public int createPublicRoom(int minDouble, int maxDouble);
	public void createPublicRoom(int minDouble, int maxDouble, int pictureNum, String nick);
	public void createPrivateRoom(int minDouble, int maxDouble, int pictureNum, Vector friends, String nick);
	public void forceStart();
	public void joinPublicRoom(int tableNumber, int pictureNum, String nick);
	
	public void rejectInvitation(int tableNumber);
	public void acceptContact(int cid);
	public void rejectContact(int cid);
	
	public void pausePoll();
	public void resumePoll();
	
	public void addContact(String msisdn1,String msisdn2,String msisdn3);
	public void deleteContact(int[] cid);
	
	public void quitPublicRoom();

	//for private network utility
	public void getContact();
	
	public void setPause(boolean pause);
	
	//public void stopThread();
	public void quitGame();
	
	public void doTransaction(int status);
	
	public void doCleanUp();
	
	//15/03/2005 hack.. for sending network problem to UI layer
	public void sendErrorMessage();
	
	
}
