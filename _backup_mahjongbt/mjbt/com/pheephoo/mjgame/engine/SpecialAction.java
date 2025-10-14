/*
 * Created on 13/08/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pheephoo.mjgame.engine;

/**
 * @author sam
 *
 * Data structure to hold kong, pong, chow, pair data
 * 
 */
public class SpecialAction {
	public int player;//to determine this action belong to which player
	public int type;//pong, kong, or chow
	public int startTilePosition;
	public int endTilePosition;
	
	//08/10/2004
	public int group;  //for determining if notices can be displayed together as a group
	
	
	public boolean isVisible;
	
	public SpecialAction(int type, int startTilePosition, int endTilePosition) {
		this.type=type;
		this.startTilePosition=startTilePosition;
		this.endTilePosition=endTilePosition;
		this.isVisible=false;
	}
	
	public SpecialAction(int type, int startTilePosition, int endTilePosition, boolean isVisible) {
		this.type=type;
		this.startTilePosition=startTilePosition;
		this.endTilePosition=endTilePosition;
		this.isVisible=isVisible;
	}

	
	public SpecialAction(int player, int type) {
		this.player=player;
		this.type=type;
	}

}
