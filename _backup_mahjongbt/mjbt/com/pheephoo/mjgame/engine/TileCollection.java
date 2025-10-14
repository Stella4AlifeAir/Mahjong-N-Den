package com.pheephoo.mjgame.engine;

/*
 * Collection of tile object. Implemented using array
 * 
 * @author Samuel
 * @version 1.00, 24/04/2005
 */
public class TileCollection {

	public static int MAXVISIBLESIZE=16;
	public static int MAXCONCEALEDSIZE=14;
	public static int MAXTILESONTABLE=100;

	
	private int debugLevel=200;//debugging code
	
    private Tile[] arrayTiles;
    private int size;//size of tile concealedTiles

    /*
     * @param maxSize	maximum size allocated for the concealedTiles
     */
	public TileCollection(int maxSize) {
		arrayTiles= new Tile[maxSize];
	}
	

	/*
	 * @return the last position of common tile (exclusive)
	 */
	public int getCommonTileEndPos() {
		int endPos=getHonourTileStartPos();
		if (endPos==-1) {
			endPos=size;
		}
		return endPos;		
	}
	
	/*
	 * @return the startposition of honour tile (inclusive)
	 */
    public int getHonourTileStartPos() {
    	int lastindex=size-1;
    	Tile temp;
    	for (int i=lastindex;i>=0;i--) {
    		temp= arrayTiles[i];
    		if (temp.type<3) {//not a honour tile
    			if (i==lastindex) {
    				return -1;//there is no honour tile
    			}
    			return i+1;//honour tile found
    		}
    	}
    	return 0;//no normal tile
    }
    

    /*
     * @return the size of this concealedTiles.
     */
    public int size() {
    	return size;
    }

    public Tile getTileAt(int pos) {
    	return arrayTiles[pos];
    }


    /*
     * Insert a tile in the concealedTiles. Insertion position is before the tile in the same group
     * Used in the case of inserting a tile to a PONG set
     */
    public void insertTile(Tile tile) {
    	Tile temp=null;
    	for (int i=0;i<size;i++) {
    		temp=arrayTiles[i];
    		if (tile.group==temp.group) {
    			//move the rest of the tile one element above
    			for (int j=size;j>i+1;j--) {
    				arrayTiles[j]=arrayTiles[j-1];
    			}
    			arrayTiles[i+1]=tile;
    			size++;
    			break;
    		}
    	}
    }

    /*
     * Clear the array
     */
    public void removeAll() {
    	size=0;
    }
   

    
    
    public void removeLastTile() {
    	size--;
    }
    
    public void removeTileAt(int pos) {
		for (int j=pos;j<size-1;j++) {
			arrayTiles[j]=arrayTiles[j+1];
		}
		size--;
    }
    
    public void removeTile(Tile tile) {
    	//debug(0,"****TileCollection.removeTile():: beforeremove=" + size);
        for(int i=0;i<size;i++) {
    		if(tile.equals(arrayTiles[i]) ) {
               //move the element after the element down
    			for (int j=i;j<size-1;j++) {
    				arrayTiles[j]=arrayTiles[j+1];
    			}
    			size--;
    			break;
            }
        }
        //debug(0,"****TileCollection.removeTile():: afterremove=" + size);
    }
    
    public void insertTileAt(Tile tile, int pos) {
    	//debug(0,"****TileCollection.insertTileAt():: beforeinsert=" + size);
		for (int j=size;j>pos;j--) {
			arrayTiles[j]=arrayTiles[j-1];
		}
		arrayTiles[pos]=tile;
		size++;
    	//debug(0,"****TileCollection.insertTileAt():: afterinsert=" + size);
    }

    /*
     * Method for quick loading a tile in the tile concealedTiles
     *  @param data array of tile information, eg. 4 1 2 7 = Tile(4,1) , Tile(2,7)
     */
    public void batchLoad(int[] data,int startpos, int endpos) {
        for(int i=startpos;i<endpos;) {
        	//System.out.println("tile="+data[i]+"_"+ data[i+1]);
            addTile(new Tile(data[i],data[i+1]));
            i=i+2;    
        }
    }
    
    /*
     * Used by AIPlayer class
     * @return Number of occurence of a tile in the concealedTiles. zero is not exist
     */
    public int getNumOfTile(Tile tile) {
    	int count=0;
    	for (int i=0;i<size;i++){
    		if (tile.equals(arrayTiles[i])) {
    			count++;
    		}
    	}
    	return count;
    }
    
    /*
     * Used by AIPlayer class
     * @return Number of occurence of a tile in the concealedTiles. zero is not exist
     * @param tile
     * @param groupType
     */    
    public int getNumOfTile(Tile tile, int groupType) {
    	int count=0;
    	for (int i=0;i<size;i++){
    		if (tile.equals(arrayTiles[i])) {
    			if(arrayTiles[i].groupType==groupType) {
    				return 1;
    			}
    		}
    	}
    	return count;
    }


    /*
     * Used by AIPlayer class
     * Add tile sorted by Tile.groupType
     */
    public void addTileByGroup(Tile tile) {
    	//debug(100,"****TileCollection.addTileByGroup():: beforeadd=" + size);
        int pos = 0;
        if (size==0) {
        	arrayTiles[size++]=tile;
            return;
        }
        //insert the tile into correct place
        while (pos  <= size-1) {
            if (tile.groupType< arrayTiles[pos].groupType ) {
                insertTileAt(tile,pos);
                return;
            }
            pos++;
        }
        arrayTiles[size++]=tile;
    }
    
    
    /* 
     * Main method for adding tile
     * Maintain a sorted concealedTiles of tile
     */
    public void addTile(Tile tile) {
    	//debug(0,"****TileCollection.addTile():: beforeadd=" + size);
        int pos = 0;
        if (size==0) {
        	arrayTiles[size++]=tile;
            return;
        }
        
        //insert the tile into correct place
        while (pos  <= size-1) {
            if (tile.type< arrayTiles[pos].type ) {
                insertTileAt(tile,pos);
                return;
            }
            else if (tile.type== arrayTiles[pos].type) {
    	        //compare the value
                if (tile.value<= arrayTiles[pos].value) {
                	insertTileAt(tile,pos);
                    return;
                }
                pos++;
            }
            else {
                pos++;
            }
        }
        arrayTiles[size++]=tile;
    }
    
    /*
     * add tile to the end of the concealedTiles. ignore sorting
     */
    public void addTileNoSorted(Tile tile) {
    	//debug(2,"addTileNoSorted");
    	arrayTiles[size++]=tile;
    }

    

    /*
     * Called by EngineFA and EngineProxy
     * Reset the group and groupTile of all tile in the concealedTiles
     */    
    public void resetType() {
    	for (int i=0;i<size;i++) {
    		arrayTiles[i].group=0;
    		arrayTiles[i].groupType=0;
    	}    	
    }
    
    /*
     * Helper method for debugging
     * @author sam
     *
     */
    private void debug(int level, String txt) {
    	
    	if (level>=debugLevel)
    		System.out.println(txt);
    }        

    
}

