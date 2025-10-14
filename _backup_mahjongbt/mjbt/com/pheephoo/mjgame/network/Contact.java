/*
 * Created on 1/02/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pheephoo.mjgame.network;

/**
 * @author sam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Contact {
	public int user;
	public int status;
	public String msisdn;
	public String nick;

	public boolean selected;
	public Contact(int _user,int _status, String _msisdn, String _nick) {
		user=_user;
		msisdn=_msisdn;
		status=_status;
		nick=_nick;
		
	}
	public Contact(int _user,int _status, String _nick) {
		user=_user;
		msisdn="";
		status=_status;
		nick=_nick;
		
	}

}
