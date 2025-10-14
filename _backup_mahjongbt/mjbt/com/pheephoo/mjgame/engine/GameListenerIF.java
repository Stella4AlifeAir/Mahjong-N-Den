/*
 * Created on 2004/4/28
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pheephoo.mjgame.engine;

import java.util.Vector;
/**
 * @author sam
 *
 * This interface is used by applications which need to receive high-level 
 * events from the GameEngine. 
 */
public interface GameListenerIF {
    /* area 1 = ur new tile
     * area 2 = ur exposed tile
     * area 3 = opponent1 exposed tile
     * area 4 = opponent2 exposed tile
     * area 5 = opponent3 exposed tile
     * area 6 = deck
     * area 7 = notification
     * Element 8 = ur tile
     * Element 9 = opponents concealed tile
     * 
     */
    
    
	public void onFinish();
	public void onFinish(String dateStr);
	
    /*
     * update element 1
     */
    //public void onTileReceived(Tile tile);
	public void onTileReceived(Tile tile, int numTileOnDeck, int turn);
    
    
    /*
     * update element 6
     */
    //public void onTileDiscarded(Tile tile, int turn);
	public void onTileDiscarded(Tile tile, int turn, int numTileOnDeck);
	
    //public void onTileReceivedN(Tile tile, int numOfTile);
    //public void onTileDiscardedN(Tile tile, int turn, int numOfTile);

    
    public void onTileTaken(TileCollection col, int numOfTile, int playerTurn, boolean isDiscardedTile, Tile tempDiscard);
    
    /*
     * update element 8
     */
    public void onJoin(int tableno, int position, int noOfPlayer, EngineIF _engine, Player _player, int started);//started= if join a game in progress or new game
    
    /*
     * update Element 9
     */
    public void onOtherJoin(int _pos, int _noOfPlayer, int pictureNum, String nick);

    /*
     * update element 7, 2,3,4,5
     *
     * noticetype: 2=kong
     * 
     * */
    public void onNotice(Vector actions);//when it is our turn
    public void onNotice2(Vector actions);//when it is not our turn
  
    public void onClearHand();
    
    public void onWin(int playerWind, TileCollection col1, TileCollection visibleTiles1, int double1, int point1,int total1,
    		TileCollection col2, TileCollection visibleTiles2, int double2, int point2,int total2,
			TileCollection col3, TileCollection visibleTiles3, int double3, int point3,int total3,
			TileCollection col4, TileCollection visibleTiles4, int double4, int point4,int total4);
    
    public void onStart(TileCollection col, TileCollection visibleTiles);
    
    public void onTurn();

    //14/10/2004
    public void setDemoMode(int demoMode);
   
    
    //for network playing
    public void onJoinInProgress(int pos, TileCollection owncol, TileCollection ownvisibleTiles,
    		int pos1, int pictureNum1, int point1, String nick1,int numOfTile1, TileCollection visibleTiles1,
			int pos2, int pictureNum2, int point2, String nick2,int numOfTile2, TileCollection visibleTiles2,
			int pos3, int pictureNum3, int point3, String nick3,int numOfTile3, TileCollection visibleTiles3,
			TileCollection tilesOnTable);
    
    //for network playing
    public void onOtherJoinInProgress(int pos, int picture, String nick);    
    public void onOtherLeaveInProgress(int pos, int picture, String nick1, String nick2);
    public void onTableMessage(String message);
    
    
    
    
    //used for AI only
    public void onJoinInProgressBot(int pos, TileCollection _owncol, TileCollection _ownvisibleTiles,
    		EngineIF _engine, Player _player,TileCollection visibleTiles1,TileCollection visibleTiles2,TileCollection visibleTiles3 );
    

    
    //public void onLogin(String motd);
    //for network purpose
    
    
    //for cleaning up
    public void doCleanUp();
    
}
