/*
 * Created on 12/04/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pheephoo.mjgame.engine;

/**
 * @author sam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GameData {
	long playDate;
	String playDateStr;
	int ownScore;
	String[] otherNick= new String[3];
	int otherScore[] = new int[3];
	int playMode;//0=offline 1=private network 2=public network

	int numOfWind;
		
}
