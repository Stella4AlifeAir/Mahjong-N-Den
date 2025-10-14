/*
 * Created on 24/04/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pheephoo.mjgame.engine;

import com.pheephoo.utilx.Common;

/**
 * A specialize version of TileCollection.
 * The concealedTiles is filled with 136 mahjong tiles, and provide random access method
 * 
 * @author Samuel
 * @version 1.00, 24/04/2005
 */
public class MahjongDeck extends TileCollection  {

    int demoMode=0;//debugging code

	public MahjongDeck() {
		super(136);
		createTileSet();
	}
	
	private void createTileSet() {
    	//System.out.println("Deck.createTileSet():: creating array of tile");
    	for (int i=0;i<3;i++) {
            for (int j=1;j<10;j++) {
                for (int k=0;k<4;k++) {
                	addTileNoSorted(new Tile(i,j));
                }
            }
        }               
        //create wind
        for (int i=1;i<5;i++) {
        	for (int j=0;j<4;j++) {
        		addTileNoSorted(new Tile(Tile.WIND, i ));
        	}
        }
        //create dragon
        for (int i=1;i<4;i++) {
        	for (int j=0;j<4;j++) {
        		addTileNoSorted(new Tile(Tile.DRAGON, i ));
        	}
        }    
	}
	
    public Tile getRandomTile() {
        int randomNum= Common.getRandomInt(0,size()-1);
        
        if (demoMode==1)
        	randomNum=0;
        
        Tile tile= getTileAt(randomNum);
        removeTileAt(randomNum);
        return tile;
    }

    public void reset() {
    	removeAll();
		createTileSet();
    }
	
}
