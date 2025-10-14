/*
 * Created on 2004/4/29
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pheephoo.mjgame.engine;

import com.pheephoo.mjgame.ui.PublicNetworkCanvas;

//import com.pheephoo.mjgame.engine.
/**
 * @author sam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface EngineIF {
	public void doDiscard(Tile tile);

    public void join(GameListenerIF gameUI, int tableno);
    //public int getNumberOfPlayer();
    
    public int getCurrentWind();
 
    
    public void doConcealedKongResponse(boolean response, Tile tile);
    public void doSpecialResponse(int type,boolean response, int wind);

    //public void sendMessageTable(String message, int cid);
    
    public void doWinResponse(int turn);
   
    //public String doPing();
    
    public void nextGame();
    public void doConfirm();
    
    public void setDemoMode(int demoMode);
    public void setGameRule(int minDouble, int MaxDouble);
    
    //network related function
	//public void setCanvas(PublicNetworkCanvas canvas);
	public void quitGame();
	
    //public void doSpecialResponseN(TileCollection tiles, int type, boolean response, int wind); 
   	//public void doSpecialResponseN(TileCollection tiles, int concealedSize, int type, boolean response, int wind);

    public Player[] getPlayers();
    
	
}
