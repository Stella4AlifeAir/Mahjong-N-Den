package com.pheephoo.mjgame.engine;

import com.pheephoo.utilx.Common;
import java.util.Vector;

/**
 * 
 * 
 * @author Samuel
 * @version 1.00, 24/04/2005
 */
public class Deck {
    int numberOfTile;
    
    int demoMode;//for debuggnin
    
    
    private Vector tiles = new Vector();
    private Tile[] tileSet= new Tile[136];//tile set is only created once
    
    public Deck() {
       createTileSet(); 
    }
    
    public void reset() {
    	
    }
    
    
    public Tile getRandomTile() {
    	
    	
        int randomNum= Common.getRandomInt(0,numberOfTile-1);
        numberOfTile--;
        
        if (demoMode==1)
        	randomNum=0;
        
        Tile tile = (Tile) tiles.elementAt(randomNum);
        tiles.removeElementAt(randomNum);
        
    	//System.out.println("tiles size in the deck=" + tiles.size());
        
        return tile;
    }
    
    /*
     * Temporary helper method for testing purpose
     */
    public Tile getTile(Tile t) {
    	Tile temp=null;
        numberOfTile--;
    	for (int i=0;i<tiles.size();i++) {
    		temp=(Tile) tiles.elementAt(i);
    		if (t.type==temp.type && t.value==temp.value) {
    			tiles.removeElementAt(i);
    			break;
    		}
    		else {
    			temp=null;
    		}
    	}
    	if (temp==null) {
    		temp = (Tile) tiles.elementAt(0);
            tiles.removeElementAt(0);    		
    	}
    		
		
    	return temp;
    }
    
    public void createTileSet() {
    	//System.out.println("Deck.createTileSet():: creating array of tile");
    	int numTileCreated=0;
    	for (int i=0;i<3;i++) {
            for (int j=1;j<10;j++) {
                for (int k=0;k<4;k++) {
                    tileSet[numTileCreated]= new Tile(i,j);
                    numTileCreated++;                         
                }
            }
        }               
        //create wind
        for (int i=1;i<5;i++) {
        	for (int j=0;j<4;j++) {
            	tileSet[numTileCreated]= new Tile(Tile.WIND, i );
                numTileCreated++;        		
        	}
        }
        //create dragon
        for (int i=1;i<4;i++) {
        	for (int j=0;j<4;j++) {
            	tileSet[numTileCreated]= new Tile(Tile.DRAGON, i );
                numTileCreated++;        		
        	}
        }    
        //System.out.println("numtilecreated="+numTileCreated);
        //System.out.println("arraysize="+ tileSet.length);
    }
    
    public void createTile() {
        //create character, stick, and ball
        //debugging
        
    	if (numberOfTile<136) {
        	//System.out.println("Deck... createTile()");
	    	tiles.removeAllElements();
	
	    	for (int i=0;i<136;i++) {
	            tiles.addElement(tileSet[i]);
	            //System.out.println("tile added to deck " + i);
	    	}
	    	numberOfTile=136;               
    	}
    }
    
    public int getNumberOfTile() {
    	return numberOfTile;
    	
    }
}
