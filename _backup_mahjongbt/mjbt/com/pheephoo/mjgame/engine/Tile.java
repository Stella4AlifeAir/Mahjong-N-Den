package com.pheephoo.mjgame.engine;

/*
 * 
 * data structure
 * A Tile is represented mainly as type and value
 * eg. (1,4) a four stick tile; (4,0) a red dragon
 * additional properties:
 * group:  if a group tiles form a set. it is assigned a group number, start from zero
 * groupType: 
 * 			- main function: each group of tile is assigned a group type, eg. KONG, CHOW, PAIR
 * 			- other function: (Used in AIPlayer) groupType is used to represent the importance of a tile in choosing which tile to throw
 * 
 * @author Samuel
 * @version 1.00, 24/04/2005
 */
public class Tile {
    public static int CHARACTER=0;
    public static int STICK=1;
    public static int BALL=2;
    public static int WIND=3;
    public static int DRAGON=4;
    public static int WIND_EAST=30;
    public static int WIND_SOUTH=31;
    public static int WIND_WEST=32;
    public static int WIND_NORTH=33;
    public static int DRAGON_RED=40;
    public static int DRAGON_GREEN=41;
    public static int DRAGON_WHITE=42;

    
    
    public static int POSITION_BOTTOM=1;//position of the tile on a Mahjong table
    public static int POSITION_RIGHT=2;
    public static int POSITION_UP=3;
    public static int POSITION_LEFT=4;
    
    
	public int type;//eg. stick, ball, characters
    public int value;
    public int group;// for each checking, the group is marked non-zero
    public int groupType; //type of group.. eg. 2=pair 1=pong 3=chow and various number assigned for priority of tile 
    
    public Tile(int _type, int _value) {
    	type = _type;
    	value = _value;
    }
    public Tile(int _group, int _type, int _value) {
    	type = _type;
    	value = _value;
    	group = _group;
    }
    public Tile(int _type, int _value, int _group, int _groupType) {
    	type = _type;
    	value = _value;
    	group = _group;
    	groupType=_groupType;
    }
    
    
    public String toString() {
    	if (groupType==0) {
    		return type + "" + value;
    	}
    	return type + "" + value+ "("+groupType+")";
    }
    
    public boolean equals(Tile tile) {
    	if (this.type==tile.type && this.value==tile.value)
    		return true;
    	return false;
    }
}
