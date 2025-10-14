/*
 * Created on 1/11/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pheephoo.mjgame.network;

/**
 * Data structure to represent a public mahjong table
 * @author Samuel
 * @version 1.00, 24/04/2005
 */
public class Table {
	public int minDouble;
	public int maxDouble;
	public int numOfPlayer;
	public int tableNumber;
	
	public int started=0;//0=not started; 1=started; 2=paused
	
	
	public Table(int p_minDouble, int p_maxDouble, int p_numOfPlayer, int p_tableNumber) {
		minDouble=p_minDouble;
		maxDouble=p_maxDouble;
		numOfPlayer=p_numOfPlayer;
		tableNumber=p_tableNumber;
	}

	public Table(int p_minDouble, int p_maxDouble, int p_numOfPlayer, int p_tableNumber, int p_started) {
		minDouble=p_minDouble;
		maxDouble=p_maxDouble;
		numOfPlayer=p_numOfPlayer;
		tableNumber=p_tableNumber;
		started=p_started;
	}

}
