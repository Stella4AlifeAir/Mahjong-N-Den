/*
 * Created on 2004/4/27
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.pheephoo.mjgame.engine;

import java.util.Vector;

/**
 * @author sam
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Player {
	
	int debugLevel=3;//only for debuggin

    
    //reference to table concealedTiles
    private MahjongDeck deckRef;

    public int wind;
    
    //for calculating point
    public  int payKongPoint;
    public int payPoint;
    public  int totalPoint=2000;
    
    public int pictureNum;
    public String nick;//05/10/2004
    
    
    //for network playing 04/11/2004
    public int cid;
    public int prevcid;//for private network playing 20/02/2005
    
    
    public TileCollection concealedTiles = new TileCollection(TileCollection.MAXCONCEALEDSIZE);
    public TileCollection visibleTiles = new TileCollection(TileCollection.MAXVISIBLESIZE);
    
    //for network playing 03/01/2005
    public Tile newtile; // temporary storage for a newtile draw from the deck
    public Tile discardedtile; //temporary storage for discarded tile

    //for use in network playing when a bot take over a human player
    //this var mark if a bot need to discard tile or respond to kong or pong.
    public int needAction;
    
    //for kong,pong,chow,pair notice
    public Vector specialAction=new Vector();

        
    /*single player*/
    public Player(MahjongDeck deck, int _wind) {
    	deckRef = deck;
        wind = _wind;
    }
    
    /* network player
     */
    public Player(int _wind) {
    	wind = _wind;
    }

    
    public void startNewGame() {
    	payKongPoint=0;
    	payPoint=0;
        concealedTiles.removeAll();
        visibleTiles.removeAll();
    }
    
    
    /*  
     *  load random tiles from the deck
     */
    public void loadInitialTile() {
    	for (int i=0;i<13;i++) {
            concealedTiles.addTile(deckRef.getRandomTile());
        }
    	
    }
    
    
    public void receiveTile(Tile tile) {
    	concealedTiles.addTile(tile);
    }
    
    public TileCollection getTiles() {
        return concealedTiles;
    }
    
    public int getWind() {
    	return wind;
    }
    
    public void incrementWind() {
    	if (wind==0)
    		wind=wind+4;
    	wind--;    	
    }
    

    /*
    public int getTilesOnDeck() {
    	return deckRef.numberOfTile;
    }
    */
    

    /*
     * temporary method for testing purpose
     */
    public void loadInitTileSet1() {
    	/*
    	for (int i=0;i<13;i++) {
            concealedTiles.addTile(deckRef.getRandomTile());
        }
        */
        concealedTiles.addTile( (new Tile(0,1)));
        concealedTiles.addTile( (new Tile(0,1)));
        concealedTiles.addTile( (new Tile(0,1)));
        concealedTiles.addTile( (new Tile(0,1)));

        concealedTiles.addTile( (new Tile(1,5)));
        concealedTiles.addTile( (new Tile(1,5)));
        concealedTiles.addTile( (new Tile(1,5)));
        concealedTiles.addTile( (new Tile(2,7)));

        concealedTiles.addTile( (new Tile(0,3)));
        concealedTiles.addTile( (new Tile(0,3)));
        concealedTiles.addTile( (new Tile(0,3)));
        concealedTiles.addTile( (new Tile(0,3)));

        concealedTiles.addTile( (new Tile(2,8)));
    }
    public void loadInitTileSet2() {
    	for (int i=0;i<13;i++) {
            concealedTiles.addTile(deckRef.getRandomTile());
        }
    }    
    public void loadInitTileSet3() {
    	for (int i=0;i<13;i++) {
            concealedTiles.addTile(deckRef.getRandomTile());
        }
    }    
    public void loadInitTileSet4() {
    	for (int i=0;i<13;i++) {
            concealedTiles.addTile(deckRef.getRandomTile());
        }
    }
    
    public void loadInitTileSet1b() {
        concealedTiles.addTile( (new Tile(0,1)));
        concealedTiles.addTile( (new Tile(0,1)));
        concealedTiles.addTile( (new Tile(0,1)));
        concealedTiles.addTile( (new Tile(0,1)));

        concealedTiles.addTile( (new Tile(2,1)));
        concealedTiles.addTile( (new Tile(2,1)));
        concealedTiles.addTile( (new Tile(2,1)));
        concealedTiles.addTile( (new Tile(2,7)));

        concealedTiles.addTile( (new Tile(0,3)));
        concealedTiles.addTile( (new Tile(0,3)));
        concealedTiles.addTile( (new Tile(0,3)));
        concealedTiles.addTile( (new Tile(0,3)));

        concealedTiles.addTile( (new Tile(2,8)));
    	
    }

    public void loadInitTileSet2b() {
        concealedTiles.addTile( (new Tile(2,1)));
        concealedTiles.addTile( (new Tile(2,6)));
        concealedTiles.addTile( (new Tile(2,6)));
        concealedTiles.addTile( (new Tile(2,6)));

        concealedTiles.addTile( (new Tile(2,2)));
        concealedTiles.addTile( (new Tile(2,2)));
        concealedTiles.addTile( (new Tile(2,2)));
        concealedTiles.addTile( (new Tile(2,3)));

        concealedTiles.addTile( (new Tile(2,4)));
        concealedTiles.addTile( (new Tile(2,4)));
        concealedTiles.addTile( (new Tile(2,4)));
        concealedTiles.addTile( (new Tile(2,5)));

        concealedTiles.addTile( (new Tile(2,5)));
    	
    }
    public void loadInitTileSet3b() {
        concealedTiles.addTile( (new Tile(1,0)));
        concealedTiles.addTile( (new Tile(1,1)));
        concealedTiles.addTile( (new Tile(1,2)));
        concealedTiles.addTile( (new Tile(1,2)));

        concealedTiles.addTile( (new Tile(1,3)));
        concealedTiles.addTile( (new Tile(1,3)));
        concealedTiles.addTile( (new Tile(1,3)));
        concealedTiles.addTile( (new Tile(1,3)));

        concealedTiles.addTile( (new Tile(1,4)));
        concealedTiles.addTile( (new Tile(1,4)));
        concealedTiles.addTile( (new Tile(1,4)));
        concealedTiles.addTile( (new Tile(1,5)));

        concealedTiles.addTile( (new Tile(1,5)));
    	
    }
    public void loadInitTileSet4b() {
        concealedTiles.addTile( (new Tile(0,2)));
        concealedTiles.addTile( (new Tile(0,2)));
        concealedTiles.addTile( (new Tile(0,2)));
        concealedTiles.addTile( (new Tile(0,2)));

        concealedTiles.addTile( (new Tile(0,4)));
        concealedTiles.addTile( (new Tile(0,4)));
        concealedTiles.addTile( (new Tile(0,4)));
        concealedTiles.addTile( (new Tile(0,4)));

        concealedTiles.addTile( (new Tile(0,5)));
        concealedTiles.addTile( (new Tile(0,5)));
        concealedTiles.addTile( (new Tile(0,5)));
        concealedTiles.addTile( (new Tile(0,5)));

        concealedTiles.addTile( (new Tile(0,6)));
    	
    }
    
    //test chow2
    public void loadInitTileSet1c() {
        concealedTiles.addTile( (new Tile(0,1)));
        concealedTiles.addTile( (new Tile(0,1)));
        concealedTiles.addTile( (new Tile(0,1)));
        concealedTiles.addTile( (new Tile(0,1)));

        concealedTiles.addTile( (new Tile(0,2)));
        concealedTiles.addTile( (new Tile(0,2)));
        concealedTiles.addTile( (new Tile(0,2)));
        concealedTiles.addTile( (new Tile(0,2)));

        concealedTiles.addTile( (new Tile(0,3)));
        concealedTiles.addTile( (new Tile(0,4)));
        concealedTiles.addTile( (new Tile(0,4)));
        concealedTiles.addTile( (new Tile(0,4)));

        concealedTiles.addTile( (new Tile(0,5)));
    	
    }
    //test chow3. simultaneous chow+kong+pong
    public void loadInitTileSet1c1() {
        concealedTiles.addTile( (new Tile(0,1)));
        concealedTiles.addTile( (new Tile(0,1)));
        concealedTiles.addTile( (new Tile(0,1)));
        concealedTiles.addTile( (new Tile(0,1)));

        concealedTiles.addTile( (new Tile(0,2)));
        concealedTiles.addTile( (new Tile(0,2)));
        concealedTiles.addTile( (new Tile(0,2)));
        concealedTiles.addTile( (new Tile(0,2)));

        concealedTiles.addTile( (new Tile(0,3)));
        concealedTiles.addTile( (new Tile(0,3)));
        concealedTiles.addTile( (new Tile(0,3)));
        concealedTiles.addTile( (new Tile(0,4)));

        concealedTiles.addTile( (new Tile(0,5)));
    	
    }
    
    
    public void loadInitTileSet2c() {
        concealedTiles.addTile( (new Tile(2,1)));
        concealedTiles.addTile( (new Tile(2,6)));
        concealedTiles.addTile( (new Tile(2,6)));
        concealedTiles.addTile( (new Tile(2,8)));

        concealedTiles.addTile( (new Tile(2,2)));
        concealedTiles.addTile( (new Tile(2,4)));
        concealedTiles.addTile( (new Tile(2,2)));
        concealedTiles.addTile( (new Tile(2,3)));

        concealedTiles.addTile( (new Tile(2,4)));
        concealedTiles.addTile( (new Tile(1,4)));
        concealedTiles.addTile( (new Tile(2,4)));
        concealedTiles.addTile( (new Tile(2,5)));

        concealedTiles.addTile( (new Tile(1,5)));
    	
    }
    public void loadInitTileSet3c() {
        concealedTiles.addTile( (new Tile(1,1)));
        concealedTiles.addTile( (new Tile(1,7)));
        concealedTiles.addTile( (new Tile(1,2)));
        concealedTiles.addTile( (new Tile(1,2)));

        concealedTiles.addTile( (new Tile(1,3)));
        concealedTiles.addTile( (new Tile(1,6)));
        concealedTiles.addTile( (new Tile(1,3)));
        concealedTiles.addTile( (new Tile(1,3)));

        concealedTiles.addTile( (new Tile(1,4)));
        concealedTiles.addTile( (new Tile(3,2)));
        concealedTiles.addTile( (new Tile(1,4)));
        concealedTiles.addTile( (new Tile(1,5)));

        concealedTiles.addTile( (new Tile(1,5)));
    	
    }
    public void loadInitTileSet4c() {
        concealedTiles.addTile( (new Tile(0,4)));
        concealedTiles.addTile( (new Tile(0,4)));
        concealedTiles.addTile( (new Tile(0,4)));
        concealedTiles.addTile( (new Tile(2,8)));

        concealedTiles.addTile( (new Tile(0,7)));
        concealedTiles.addTile( (new Tile(0,7)));
        concealedTiles.addTile( (new Tile(2,7)));
        concealedTiles.addTile( (new Tile(0,7)));

        concealedTiles.addTile( (new Tile(0,8)));
        concealedTiles.addTile( (new Tile(1,8)));
        concealedTiles.addTile( (new Tile(0,8)));
        concealedTiles.addTile( (new Tile(1,5)));

        concealedTiles.addTile( (new Tile(3,1)));
    	
    }


    /************************************************ SECOND ROUND TEST
    */
    
    //
    public void loadKong1Player1() {
        concealedTiles.addTile( (new Tile(0,1)));
        concealedTiles.addTile( (new Tile(0,1)));
        concealedTiles.addTile( (new Tile(0,1)));
        concealedTiles.addTile( (new Tile(0,1)));

        concealedTiles.addTile( (new Tile(0,2)));
        concealedTiles.addTile( (new Tile(0,2)));
        concealedTiles.addTile( (new Tile(0,2)));
        concealedTiles.addTile( (new Tile(0,2)));

        concealedTiles.addTile( (new Tile(0,3)));
        concealedTiles.addTile( (new Tile(0,3)));
        concealedTiles.addTile( (new Tile(0,4)));
        concealedTiles.addTile( (new Tile(0,4)));

        concealedTiles.addTile( (new Tile(0,5)));

    }
    
    public void loadKong1Player2() {
        concealedTiles.addTile( (new Tile(0,3)));
        concealedTiles.addTile( (new Tile(0,5)));
        concealedTiles.addTile( (new Tile(2,6)));
        concealedTiles.addTile( (new Tile(2,6)));

        concealedTiles.addTile( (new Tile(2,2)));
        concealedTiles.addTile( (new Tile(2,2)));
        concealedTiles.addTile( (new Tile(2,2)));
        concealedTiles.addTile( (new Tile(2,3)));

        concealedTiles.addTile( (new Tile(2,4)));
        concealedTiles.addTile( (new Tile(2,4)));
        concealedTiles.addTile( (new Tile(2,4)));
        concealedTiles.addTile( (new Tile(2,5)));

        concealedTiles.addTile( (new Tile(2,5)));
    }

    public void loadKong1Player3() {
        concealedTiles.addTile( (new Tile(1,1)));
        concealedTiles.addTile( (new Tile(1,1)));
        concealedTiles.addTile( (new Tile(1,2)));
        concealedTiles.addTile( (new Tile(1,2)));

        concealedTiles.addTile( (new Tile(1,3)));
        concealedTiles.addTile( (new Tile(1,3)));
        concealedTiles.addTile( (new Tile(1,3)));
        concealedTiles.addTile( (new Tile(1,3)));

        concealedTiles.addTile( (new Tile(1,4)));
        concealedTiles.addTile( (new Tile(1,4)));
        concealedTiles.addTile( (new Tile(1,4)));
        concealedTiles.addTile( (new Tile(1,5)));

        concealedTiles.addTile( (new Tile(1,5)));
    }
    
    public void loadKong1Player4() {
        concealedTiles.addTile( (new Tile(0,6)));
        concealedTiles.addTile( (new Tile(0,6)));
        concealedTiles.addTile( (new Tile(0,6)));
        concealedTiles.addTile( (new Tile(0,6)));

        concealedTiles.addTile( (new Tile(0,7)));
        concealedTiles.addTile( (new Tile(0,7)));
        concealedTiles.addTile( (new Tile(0,7)));
        concealedTiles.addTile( (new Tile(0,7)));

        concealedTiles.addTile( (new Tile(0,8)));
        concealedTiles.addTile( (new Tile(0,8)));
        concealedTiles.addTile( (new Tile(0,8)));
        concealedTiles.addTile( (new Tile(0,8)));

        concealedTiles.addTile( (new Tile(3,1)));
    }

    //*************************************** AI TEST
    public void loadAIKong1Player1() {
        concealedTiles.addTile( (new Tile(0,1)));
        concealedTiles.addTile( (new Tile(0,1)));
        concealedTiles.addTile( (new Tile(0,1)));
        concealedTiles.addTile( (new Tile(0,9)));

        concealedTiles.addTile( (new Tile(0,2)));
        concealedTiles.addTile( (new Tile(0,2)));
        concealedTiles.addTile( (new Tile(0,9)));
        concealedTiles.addTile( (new Tile(0,9)));

        concealedTiles.addTile( (new Tile(0,3)));
        concealedTiles.addTile( (new Tile(4,1)));
        concealedTiles.addTile( (new Tile(0,4)));
        concealedTiles.addTile( (new Tile(0,4)));

        concealedTiles.addTile( (new Tile(4,2)));

    }
    
    public void loadAIKong1Player2() {
        concealedTiles.addTile( (new Tile(0,1)));
        concealedTiles.addTile( (new Tile(0,2)));
        concealedTiles.addTile( (new Tile(0,5)));
        concealedTiles.addTile( (new Tile(2,6)));

        concealedTiles.addTile( (new Tile(0,6)));
        concealedTiles.addTile( (new Tile(0,6)));
        concealedTiles.addTile( (new Tile(2,6)));
        concealedTiles.addTile( (new Tile(2,3)));

        concealedTiles.addTile( (new Tile(2,4)));
        concealedTiles.addTile( (new Tile(2,4)));
        concealedTiles.addTile( (new Tile(2,4)));
        concealedTiles.addTile( (new Tile(2,5)));

        concealedTiles.addTile( (new Tile(2,5)));
    }

    public void loadAINotThrow1() {
    	String tilelist[]={
    			"0_1","0_1",   "0_3","0_3","0_3",
    			"0_4","0_4","0_4",  "1_7","1_8","2_4",
    			"4_1","4_1"};
    	loadBulkTiles(tilelist);
    	
    }
    
    public void loadAIKong1Player3() {
        concealedTiles.addTile( (new Tile(0,2)));
        concealedTiles.addTile( (new Tile(0,3)));
        concealedTiles.addTile( (new Tile(1,2)));
        concealedTiles.addTile( (new Tile(1,2)));
        concealedTiles.addTile( (new Tile(1,3)));
        concealedTiles.addTile( (new Tile(1,3)));
        concealedTiles.addTile( (new Tile(1,3)));
        concealedTiles.addTile( (new Tile(1,3)));
        concealedTiles.addTile( (new Tile(1,4)));
        concealedTiles.addTile( (new Tile(1,4)));
        concealedTiles.addTile( (new Tile(1,4)));
        concealedTiles.addTile( (new Tile(1,5)));
        concealedTiles.addTile( (new Tile(1,5)));
    }
    
    public void loadAIKong1Player4() {
        concealedTiles.addTile( (new Tile(0,6)));
        concealedTiles.addTile( (new Tile(1,6)));
        concealedTiles.addTile( (new Tile(1,6)));
        concealedTiles.addTile( (new Tile(1,6)));

        concealedTiles.addTile( (new Tile(0,7)));
        concealedTiles.addTile( (new Tile(0,7)));
        concealedTiles.addTile( (new Tile(0,7)));
        concealedTiles.addTile( (new Tile(0,7)));

        concealedTiles.addTile( (new Tile(0,8)));
        concealedTiles.addTile( (new Tile(0,8)));
        concealedTiles.addTile( (new Tile(0,8)));
        concealedTiles.addTile( (new Tile(0,8)));

        concealedTiles.addTile( (new Tile(3,1)));
    }

    
    
    public void loadFinish1Player2() {
        concealedTiles.addTile( (new Tile(0,4)));
        concealedTiles.addTile( (new Tile(0,6)));
        concealedTiles.addTile( (new Tile(0,5)));
        concealedTiles.addTile( (new Tile(2,6)));

        concealedTiles.addTile( (new Tile(0,6)));
        concealedTiles.addTile( (new Tile(0,6)));
        concealedTiles.addTile( (new Tile(2,6)));
        concealedTiles.addTile( (new Tile(2,3)));

        concealedTiles.addTile( (new Tile(2,4)));
        concealedTiles.addTile( (new Tile(2,4)));
        concealedTiles.addTile( (new Tile(2,4)));
        concealedTiles.addTile( (new Tile(2,5)));

        concealedTiles.addTile( (new Tile(2,5)));
    }

    public void loadFinish1Player3() {
        concealedTiles.addTile( (new Tile(1,2)));
        concealedTiles.addTile( (new Tile(0,3)));
        concealedTiles.addTile( (new Tile(1,2)));
        concealedTiles.addTile( (new Tile(1,2)));

        concealedTiles.addTile( (new Tile(1,3)));
        concealedTiles.addTile( (new Tile(1,3)));
        concealedTiles.addTile( (new Tile(1,3)));
        concealedTiles.addTile( (new Tile(1,3)));

        concealedTiles.addTile( (new Tile(1,4)));
        concealedTiles.addTile( (new Tile(1,4)));
        concealedTiles.addTile( (new Tile(1,4)));
        concealedTiles.addTile( (new Tile(1,5)));

        concealedTiles.addTile( (new Tile(1,5)));
    }
    
    public void loadFinish1Player4() {
        concealedTiles.addTile( (new Tile(0,6)));
        concealedTiles.addTile( (new Tile(1,6)));
        concealedTiles.addTile( (new Tile(1,6)));
        concealedTiles.addTile( (new Tile(1,6)));

        concealedTiles.addTile( (new Tile(0,7)));
        concealedTiles.addTile( (new Tile(0,7)));
        concealedTiles.addTile( (new Tile(0,7)));
        concealedTiles.addTile( (new Tile(0,7)));

        concealedTiles.addTile( (new Tile(0,8)));
        concealedTiles.addTile( (new Tile(0,8)));
        concealedTiles.addTile( (new Tile(0,8)));
        concealedTiles.addTile( (new Tile(0,8)));

        concealedTiles.addTile( (new Tile(3,1)));
    }

    
    
    
    
    
    public void loadBulkTiles(String tilelist[]) {
    	concealedTiles=new TileCollection(TileCollection.MAXCONCEALEDSIZE);
    	
    	for(int i=0;i<tilelist.length;i++) {
    		concealedTiles.addTile( (getTileFromString(tilelist[i]) ) );
    	}
    }
    
    public void loadBulkTiles(String tilelist[], String tileexp[]) {
    	concealedTiles=new TileCollection(TileCollection.MAXCONCEALEDSIZE);
    	
    	for(int i=0;i<tilelist.length;i++) {
    		concealedTiles.addTile( (getTileFromString(tilelist[i]) ) );
    	}
    	for(int i=0;i<tileexp.length;i++) {
    		Tile tile= (getTileFromString(tileexp[i]) ) ;
    		tile.group=getGroup(tileexp[i]);
    		//debug(0,"tile.group="+ tile.group);
    		tile.groupType=getGroupType(tileexp[i]);
    		visibleTiles.addTile(tile);  
    		//debug(0,"TILE!!");
    	}
    }

    

    //21/09/2004
    public void printVisible() {
    	//debug(3,"\nPlayer.printVisible()");
    	//debug(3,"\nConcealed : ");
        for(int i=0;i<=concealedTiles.size()-1;i++) {
        	debug2(3,  concealedTiles.getTileAt(i).type  +"_" + concealedTiles.getTileAt(i).value + "  " 
        			 );
        }  
        //debug(3,"\nVisible : ");
        for(int i=0;i<=visibleTiles.size()-1;i++) {
        	debug2(3,  visibleTiles.getTileAt(i).type  +"_" + visibleTiles.getTileAt(i).value+"_"+ visibleTiles.getTileAt(i).group  + "  ");
        }  

    }

    public void printEnd() {
    	//debug(3,"\nHand : ");
        for(int i=0;i<=concealedTiles.size()-1;i++) {
        	debug2(3, concealedTiles.getTileAt(i).type  +"_" + concealedTiles.getTileAt(i).value + "  ");
        }  
        //debug(3,"\nHand : ");
        for(int i=0;i<=concealedTiles.size()-1;i++) {
        	debug2(3, " " + concealedTiles.getTileAt(i).groupType + "   ");
        }  
    }
    
    private Tile getTileFromString(String strTile) {
    	//System.out.println("EngineFA().getTileFromString:::"+ strTile);
    	int type= Integer.parseInt( strTile.substring(0,1) );
    	int value = Integer.parseInt (strTile.substring(2,3) );
    
    	//strTile.substring(type,value);
    	return new Tile(type,value);
    }

    private int getGroup(String strTile) {
    	//strTile.substring(type,value);
    	return Integer.parseInt (strTile.substring(4,5) );
    }
    private int getGroupType(String strTile) {
    	//strTile.substring(type,value);
    	return Integer.parseInt (strTile.substring(6,7) );
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
    
    private void debug2(int level, String txt) {
    	
    	if (level>=debugLevel)
    		System.out.print(txt);
    }        

}
