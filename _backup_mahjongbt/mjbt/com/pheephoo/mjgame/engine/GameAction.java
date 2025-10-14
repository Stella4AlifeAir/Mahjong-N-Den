/*
 * Created on 2004/4/28
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
public class GameAction {
    public static int TYPE_PONG = 20;
    public static int TYPE_KONG = 21;
    public static int TYPE_CHOW = 22;
    public static int TYPE_WIN = 30;
    public static int TYPE_WIN_ONROBBING = 31;
    public static int TYPE_1 = 23;
    public static int TYPE_2 = 24;
    public static int TYPE_3 = 25;
    public static int TYPE_CANCEL=26;
    public static int POS_EAST=0;
    public static int POS_NORTH=1;
    public static int POS_WEST=2;
    public static int POS_SOUTH=3;    
    public static int START = 10;
    public static int QUIT = 11;
    public static int JOIN = 12;
    public static int GIVE = 13;
    
    public int type;
    public int player;
    public int data;
    public int reserved1;
}
