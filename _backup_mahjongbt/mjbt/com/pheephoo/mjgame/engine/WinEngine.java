package com.pheephoo.mjgame.engine;

import com.pheephoo.mjgame.Constant;

/*
 * Algorithm to determine win, and calculating double
 * 
 * @author Samuel
 * @version 1.00, 24/04/2005
 */
public class WinEngine {
	
	private int debugLevel=1000;//only for debugging
	
	private TileCollection g_collection;
	private TileCollection g_visibleTiles;
	private int seatWind;
	private int prevailingWind;

	private boolean isSelfPick;
	
	
	private int countGroup;	//for marking the number of sets. increment by one if there is a new set
	public int countPair; 	//read by AIPlayer
	private int countHonourPong;
	private int countPong;
	private int countChow;
	private int countVisiblePong;
	private int countVisibleChow;
	public int numberOfDragon; 	//read by AIPlayer
	public int numberOfWind;	//read by AIPlayer
    private int numberOfTotalWind;

	private int pairPosFromPair[]=new int[6];
	private int pairPosFromPong[]=new int[6];
	private int totalPossiblePairFromPair;
	private int totalPossiblePairFromPong;
	public boolean isDragonPair;//read by AIPlayer, flag for determining 3 lil dragon or not
	public boolean isWindPair;//read by AIPlayer, flag for determining lil wind
	public boolean  notPingwuPair;//read by AIPlayer, flag for determining pingwu game
    
    boolean notThirteenWonder;
    

	public WinEngine() {
		//debug(2,"WinEngine() constructor");
	}
	
	private void reset() {
		countGroup=0;		
		countPair=0;
		countPong=0;
		countChow=0;
		countVisiblePong=0;
		countVisibleChow=0;
		countHonourPong=0;
		
		isDragonPair=false;
		isWindPair=false;

		
	    numberOfDragon=0;
	    numberOfWind=0;
	    numberOfTotalWind=0;
	    notPingwuPair=false;
	    
	    notThirteenWonder=false;
	    
		
	}
	
	public void loadTiles(TileCollection p_collection, TileCollection p_visibleTiles, int p_prevailingWind, int p_seatWind ) {
		g_collection=p_collection;
		g_collection.resetType();
		
		g_visibleTiles=p_visibleTiles;
		prevailingWind=p_prevailingWind;
		seatWind=p_seatWind;	

		reset();
		

	}
	
	/*
	 * cost: one loop
	 */
	private void checkPongConcealed() {
		//copy the global variable to local
		TileCollection collection=this.g_collection;
		countPong=0;
		int identCounter=1;//identical counter;
		int tileposition=1;
		int starttileposition=-1;
		
		Tile prevtile= collection.getTileAt(0);
		Tile temptile;
		
		int endPos=collection.getCommonTileEndPos();

		Tile currenttile=null;
		for (int i=1;i<endPos;i++) {
		
			currenttile = collection.getTileAt(i);

	    	if ( currenttile.type==prevtile.type && currenttile.value==prevtile.value && prevtile.groupType==0 && currenttile.groupType==0) {
				//debug(1,"similar tile");
				
	    		identCounter++;
	    		if (starttileposition==-1)
	    			starttileposition=tileposition-1; //becos we use a prev tile           		
	
	    		if (identCounter==3) {
	    			//debug(1,"********this is PONG!!!*********" + currenttile.type+"_"+currenttile.value +"::::tile="+starttileposition+"-"+tileposition);
	    			temptile=collection.getTileAt(starttileposition);
	    			temptile.groupType=2;
	    			temptile.group=countGroup;
	    			temptile=  collection.getTileAt(starttileposition+1);
	    			temptile.groupType=2;	    			
	    			temptile.group=countGroup;
	    			temptile=  collection.getTileAt(starttileposition+2);
	    			temptile.groupType=2;	    			
	    			temptile.group=countGroup;
	    			countPong++;
	    			countGroup++;

		    		//11/10/2004 counting dragon & wind	    
	    			updateHonourCount(temptile);
	    		}
	    	}
	    	else {
	    		identCounter=1;
	    		starttileposition=-1;
	    	}            		
	    	prevtile=  collection.getTileAt(i);;
	    	tileposition++;
	    }//end while
	    
	}
	
	
	/*
	 * Last Modified: 30/03/2005
	 * Checked
	 * 
	 * write count visible
	 * call: checkHonourTile() --> for scoring
	 */
	public void checkVisible() {
		//debug(2,"checkVisible()");
		TileCollection visibleTiles=this.g_visibleTiles;
		int currentgroup=-1;
		int colsize=visibleTiles.size();
		Tile currenttile=null;
		for (int i=0;i<colsize;i++) {
			currenttile=   visibleTiles.getTileAt(i);
	    	if ( currenttile.group!=currentgroup) {
	    		currentgroup=currenttile.group;
	    		if (currenttile.groupType==2)
	    			countVisiblePong++;
	    		else if (currenttile.groupType==3)
	    			countVisibleChow++;
    			updateHonourCount(currenttile);
	    	}
	    }
		//debug(4,"checkVisible()::countVisiblePong/Kong="+countVisiblePong);
		//debug(4,"checkVisible()::countVisibleChow="+countVisibleChow);
		//debug(2,"checkVisible() finished ******");
	}
	
	
	public void checkWin2() {
		//checkPair();
		checkPongConcealed();
		checkChowHelper(1);
	}
	
	
	private int calculateDouble() {
		int doublePoint=0;
		//check PINGWU or all pong game
		
		
		//activate this later
		
		if (g_visibleTiles.size()==0 && countChow==0 && isSelfPick) {
			//debug(10,"HIDDEN TREASURE (LIMIT");
			doublePoint=Constant.WIN_TABLELIMIT;
			return doublePoint;
			
		}
		
		
		if ( (countChow+countVisibleChow)==4 && !notPingwuPair) {
			doublePoint=1;
			//debug(10,"PING WU Game (1 dbl)");
		}
		else if (countPong+countVisiblePong+countHonourPong==4) {
			doublePoint=doublePoint+2;
			//debug(10,"All PONG Game (2 dbl)");
		}

		//check half color or full color or all honour
		int colorResult=checkColor();
		if (colorResult==1) {
			doublePoint=doublePoint+2;
			//debug(10,"HALF COLOR game (2 dbl)");
		}
		else if (colorResult==2) {
			doublePoint=doublePoint+4;
			//debug(10,"FULL COLOR game (4 dbl)");
		}
		else if (colorResult==3){
			//debug(10,"ALL HONOUR (limit)");
			doublePoint=Constant.WIN_TABLELIMIT;			
			return doublePoint;
		}
		
		//check all one and all nine
		
		int oneAndNineResult=checkOneAndNine();

		if (oneAndNineResult==1) {
			doublePoint=doublePoint+4;
			//debug(10,"HALF ONE AND NINE game (4 dbl)");
		}
		else if (oneAndNineResult==2) {
			doublePoint=Constant.WIN_TABLELIMIT;
			//debug(10,"FULL ONE AND NINE game (limit");
			return doublePoint;
		}
		
		//check little 3 dragon & big 3 dragon
		if (numberOfDragon==3) {
			//debug(10,"BIG THREE DRAGON (limit)");
			doublePoint=Constant.WIN_TABLELIMIT;			
			return doublePoint;
		}
		else if (numberOfDragon==2 && isDragonPair) {
			//debug(10,"LITTLE THREE DRAGON (3 dbl)");
			doublePoint=doublePoint+3;
		}
		else {
			doublePoint=doublePoint+numberOfDragon;
		}
		
		//check little 4 wind & big 4 wind
		if (numberOfTotalWind==4) {
			//debug(10,"BIG FOUR WIND (limit)");
			doublePoint=Constant.WIN_TABLELIMIT;						
			return doublePoint;
		}
		else if (numberOfTotalWind==3 && isWindPair) {
			//debug(10,"LITTLE FOUR WIND (4 dbl)");
			doublePoint=doublePoint+4;
		}
		
		
		if (doublePoint<Constant.WIN_TABLELIMIT) {
			//debug(10,"dragon="+numberOfDragon + "wind="+numberOfWind);
			doublePoint=doublePoint+numberOfWind;
		}
		//debug(10,"Number of Double=" + doublePoint);
		
		
		return doublePoint;
	}
	

	/*
	 * call resetCommonTile()
	 * cost: one loop + one loop (max)
	 * 
	 */	
	
	private void setPair(int startPos) {
		resetCommonTile();
		TileCollection collection=this.g_collection;
		Tile tile=   collection.getTileAt(startPos);
		tile.groupType=1;
		tile=   collection.getTileAt(startPos+1);
		tile.groupType=1;
	}
	
	/*
	 * cost: one loop
	 */	
	private void resetCommonTile() {
		TileCollection collection=this.g_collection;
		int endPos=collection.getCommonTileEndPos();

		Tile tile=null;
		for (int i=0;i<endPos;i++) {
			tile=   collection.getTileAt(i);
			tile.groupType=0;
		}
	}
	
	
	
	private boolean isWin() {
		//debug(2,"isWin()");
		checkPongConcealed();


		checkChowHelper(1);

		if (countPair==1 && (countPong + countHonourPong + countChow + countVisiblePong + countVisibleChow)==4) {
			//debug(10,"\nWIN!!!!!!"+ " countPong=" +countPong+" ;countHonourPong="+countHonourPong+ " ;countChow="+countChow+ " ;countVisiblePong="+countVisiblePong+ " ;countVisibleChow="+countVisibleChow);
			//printEnd(20);
			return true;
		}
		//debug(2,"isWin() finished");
		
		return false;
	}
	
	
	/*
	 * calculating how many chow, how many kong, how many pong, how many pair
	 */
	public int checkWin(boolean isSelfPick) {
	//triplet count
		
		this.isSelfPick=isSelfPick;//use to determine hiddentreasure or not
		//11/10/2004
		//resetting the doublePoint variable
		//doublePoint=0;
		//printEnd();

		boolean flagWin=false;
		
		checkVisible();
		
		//checkhonourTiles() update the countPair and countHonourPong var
		if (checkHonourTiles()==-1) {
			//debug(15,"***********************************quick shortcut for above reason");
			
			//still need to check for thirteenwonder
			checkAllPossiblePair();
			
			if (totalPossiblePairFromPair>1 ) {
				notThirteenWonder=true;
			}
			else if (totalPossiblePairFromPong>0) {
				notThirteenWonder=true;
			}
			else {
				countPair=totalPossiblePairFromPair;
				//debug(2,"countPair="+countPair);
			}
			
			
		}		
		else if (countPair==1) {
			//debug(15,"there is a honour pair.. skipping lengthly checking");
			if (isWin()) {
				notThirteenWonder=true;
				flagWin=true;
			}
			else if (countPong>0 || countChow>0) {
				notThirteenWonder=true;
			}
		}
		else if (countPair>1) {
			//debug(2,"***********************************quick shortcut. not win. more than one honour pairs.. return -1");
			return -1;
		}
		else {// no honour pair
			//get the total pair.
			checkAllPossiblePair();
			if (totalPossiblePairFromPair>1 || totalPossiblePairFromPair==0) {
				notThirteenWonder=true;
			}
			for (int i=0;i<totalPossiblePairFromPair;i++) {
				countPair=1;
				setPair(pairPosFromPair[i]);
				if (isWin()) {
					notThirteenWonder=true;
					flagWin=true;
					break;
				}
			}
			if (totalPossiblePairFromPong>0) {
				notThirteenWonder=true;
			}
			if (!flagWin) {
				for (int i=0;i<totalPossiblePairFromPong;i++) {
					countPair=1;
					setPair(pairPosFromPong[i]);
					if (isWin()) {
						flagWin=true;
						break;
					}
				}
			}
		}//end else
		
		//debug(500,"Pair="+countPair+";pong="+countPong+ " ;honourPong "+countHonourPong+";chow="+countChow+";visiblePong="+countVisiblePong+";visibleChow="+countVisibleChow);
		
		if (debugLevel<=5) {
			printEnd(5);
		}
		
		///finishing 
		
		

		//check thirteenwonder first 
		if (!notThirteenWonder) {
			if (checkThirteenWonder()) {
				//debug(500,"\nWIN!!!!!13 WONDER ");
				return Constant.WIN_TABLELIMIT;
			}
		}
		
		printEnd(20);
		
		if (flagWin) {
			//debug(10,"\nWIN!!!!!!");
			return calculateDouble();
		}
		return -1;

		
		//pair count. must have one pair only
	
		
	//check the chow first
	}

	
	//if mode=0 (Zero double game)
	//if mode=1 (all pong game)
	//if mode=2 (full color game)
	public int checkOwnTile(int mode) {
		int numOfStep=0;
		
		
		checkHonourTiles();//update countPair variable
		
		if (mode==0) {
			checkChowHelper(0);
		}
		checkAllPossiblePair();
		checkPongConcealed();
		//debug(100,"checkNumOfStep");
		printEnd(100);
		
		
		return numOfStep;
	}
	
	
	/*
	 * cost: 1 loop (max)
	 * mode: 1= fast. use shortcut to check if the chow seq can win
	 * 	     0= slow
	 */	
	private boolean checkChowHelper(int mode) {
    	countChow=0;
    	TileCollection collection=this.g_collection;
    	
    	//debug(4,"checkChowHelper()");
    	//debug(4,"Pattern before checking chow");
		printEnd(5);
		
		int endPos=collection.getCommonTileEndPos();		
		
		Tile currenttile=null;

		int chowTilePos[]= new int[14];
		
		//loop 4 times becoz everytime a chow seq is found, the inner loop break
		//and we start checking from the beginning
		for (int a=0;a<4;a++) {
			//find the first tile
			Tile prevtile=null;
			int lastpos=0;
			int numOfChowTile=0;
			int seqCount=0;
			for (int i=0;i<endPos;i++) {
				prevtile =   collection.getTileAt(i);
				if (prevtile.groupType==0) {
					//debug(2,"prevtile="+prevtile);
					chowTilePos[numOfChowTile++]=i;
					break;
				}
				lastpos++;
			}
	
			for (int i=lastpos+1;i<endPos;i++) {
				currenttile =   collection.getTileAt(i);
				//debug(2,"currenttile="+currenttile);
				if (currenttile.groupType==0) {
					if (currenttile.type==prevtile.type && currenttile.value-prevtile.value==1) {
						if (mode==0) {
							prevtile.groupType=9;
							currenttile.groupType=9;
						}
						seqCount++;
						chowTilePos[numOfChowTile++]=i;
						prevtile=   collection.getTileAt(i);
						if (seqCount==2) {//resetting the seq. need to find the first chow seq again
							countChow++;
							//debug(4,"THis is CHOW!");
							break;
						}
					}
					else if (currenttile.equals(prevtile)) {
						//do nothing
					}
					else {
						if (mode==1) {
					    	//debug(4,"************************* shortcut. quit chow. not a chow seq");							
							return false;
						}
						
						//else
						{
					    	//debug(4,"not equal to prev");							
							numOfChowTile=0;
							seqCount=0;
							int j=0;
							for (j=i;j<endPos;j++) {
								prevtile = collection.getTileAt(i);
								if (prevtile.groupType==0) {
									
									//debug(2,"prevtile="+prevtile);
									chowTilePos[numOfChowTile++]=i;
									break;
								}
							}
							i=j;
						}
					}
				}
				else {
					//to nothing
				}
			}
			//update the chow position;
			Tile temp=null;
			if (numOfChowTile>=3) {
				for (int i=0;i<numOfChowTile;i++) {
					temp=  collection.getTileAt(chowTilePos[i]);
					temp.groupType=3;
			    	//debug(4,"**************** updating chow seq.....");		
				}
			}
			
		}
    	//debug(4,"**************** checkChowHelper() finished");		
		return true;
	}
	
	
    /*
     * the logic is similar to checkPair
     * write: countPair, countHonourPong
     * cost: one loop
     */
    public int checkHonourTiles() {
    	//debug(2,"checkHonourPair()");
    	countPair=0;

    	TileCollection collection=this.g_collection;
    	int startPos=collection.getHonourTileStartPos();
    	
    	//there is honour pair
    	if (startPos!=-1) {
    	
	    	Tile prevTile=  collection.getTileAt(startPos);	    
			Tile currentTile=null;
			int colsize=collection.size();

			if ( (colsize-startPos)==1) {
    			//debug(2,"***********************************quick shortcut. not win. single honour tile. only one honour tile");
    			//debug(2,"checkHonourPair() finished *******");
				return -1;//not a winning configuration. shortcut				
			}
			
			//j increase if we find a similar tile
			for (int j=startPos+1;j<colsize;j++){
				currentTile =   collection.getTileAt(j);
				if (currentTile.equals(prevTile)) {//two similar tiles
					j++;
					//we check for the third tile
					if (j<colsize) {
						currentTile =   collection.getTileAt(j);					
					}
					if ( j==colsize || !currentTile.equals(prevTile)) {
		    			//debug(2,"********this is HONOUR PAIR!!!*********" );
						currentTile =   collection.getTileAt(j-1);
		    			currentTile.groupType=1;
						prevTile.groupType=1;					
						countPair++;
						
						//11/10/2004 checking the eye if its valid for ping wu
		    			if (currentTile.type==4) {
		    				isDragonPair=true;
		    				notPingwuPair=true;
		    			}
		    			else {
		    				isWindPair=true;
		    			}
		    					    			
		    			if (currentTile.type==3 && 
		    					(currentTile.value==(prevailingWind+1) || currentTile.value==(seatWind+1)) ) {
		    				notPingwuPair=true;
		    			}
					}
					else {//similar tile... this is probably PONG set
						//check for the forth tile
						j++;
						if (j<colsize) {
							currentTile =   collection.getTileAt(j);					
						}
						if ( j==colsize || !currentTile.equals(prevTile)) {
			    			//debug(2,"********this is HONOUR PONG !!!*********" );
							currentTile =   collection.getTileAt(j-1);
			    			currentTile.groupType=2;
							currentTile =   collection.getTileAt(j-2);
			    			currentTile.groupType=2;
							prevTile.groupType=2;					
							countHonourPong++;
							this.updateHonourCount(currentTile);
						}
						else {
							//4 tiles
			    			//debug(2,"***********************************quick shortcut. not win. four similar tiles");
			    			//debug(2,"checkHonourPair() finished *******");
							return -1;
						}
					}
				}
				else  {
	    			//debug(2,"***********************************quick shortcut. not win. single honour tile");
	    			//debug(2,"checkHonourPair() finished *******");
					return -1;//not a winning configuration. shortcut
				}
				
				if (j<colsize) {
					prevTile=  collection.getTileAt(j);

					//this is the lasttile and is singletile
					if (j==colsize-1) {
		    			//debug(2,"***********************************quick shortcut. not win. last tile is single");
		    			//debug(2,"checkHonourPair() finished *******");
						return -1;
					}
				}
			}	
			
			
    	}//end if
		//printEnd(2);
		//debug(2,"countPair="+countPair + " ;countHonourPONG="+countHonourPong);
		//debug(2,"checkHonourPair() finished *******");
    	return countPair;
    }
    
    
    /*
     *update pairPos[], totalPossiblePair;
     *cost: one loop
     */
    public void checkAllPossiblePair() {
    	//debug(2,"checkAllPossiblePair");    	
    	totalPossiblePairFromPair=0;
    	totalPossiblePairFromPong=0;

    	TileCollection collection=this.g_collection;
		Tile prevTile=  collection.getTileAt(0);	    
		Tile currentTile=null;
		
		int endPos=collection.getHonourTileStartPos();
		if (endPos==-1) {
			endPos=collection.size();
		}
		
		//j increase if we find a similar tile
		for (int j=1;j<endPos;j++){
			currentTile =   collection.getTileAt(j);
			if (currentTile.equals(prevTile)) {//two similar tiles
				j++;
				//we check for the third tile
			
				if (j<endPos) {
					currentTile =   collection.getTileAt(j);					
				}
				if ( j==endPos || !currentTile.equals(prevTile)) {
	    			//debug(1,"********this is PAIR!!!*********" );

	    			pairPosFromPair[totalPossiblePairFromPair++]=j-2;
	    			currentTile =   collection.getTileAt(j-1);
	    	
					currentTile.groupType=1;
					prevTile.groupType=1;					
					countPair++;
					
				}
				else {//similar tile... skip one tile
					//this is pong
	    			pairPosFromPong[totalPossiblePairFromPong++]=j-2;					
					j++;
				}
				
			}
			if (j<endPos) {
				prevTile=  collection.getTileAt(j);	    			
			}
		}			
    	
		//debug(2,"total possible pair from pair="+totalPossiblePairFromPair);
		for (int i=0;i<totalPossiblePairFromPair;i++) {
			//debug(2,"Pair  " + (i+1) +"=" + pairPosFromPair[i]);			
		}
		//debug(2,"total possible pair from pong="+totalPossiblePairFromPong);
		for (int i=0;i<totalPossiblePairFromPong;i++) {
			//debug(2,"Pair  " + (i+1) +"=" + pairPosFromPong[i]);			
		}
		
		//debug(2,"checkAllPossiblePair() finished *******");
    	
    }
    
    public void printResult() {
    	//debug(3,"**********RESULT*********");
    	//debug(3,"Number of Pair: " + countPair);
    	//debug(3,"Number of Pong: " + countPong);
    	//debug(3,"Number of Chow: " + countChow);
    }


    /*
     * Last Modified: 30/03/2005
     * Checked
     */
    private void updateHonourCount(Tile tile) {
		if (tile.type==4) {
			numberOfDragon++;
		}
		else if (tile.type==3){
			numberOfTotalWind++;
		}
		if (tile.type==3 && tile.value==(prevailingWind+1) ) {
			numberOfWind++;
		}
		if (tile.type==3 && tile.value==(seatWind+1)  ) {
			numberOfWind++;
		}
	
    }

    /* Return value
     * 0 = normal 
     * 1 = halfcolor
     * 2 = fullcolor
     **/
    private int checkColor() {
    	//debug(2,"checkColor()");
        int numOfColor=0;
        boolean hasHonour=false;
        Tile tile;
        int prevtileType=-1;
        TileCollection collection=g_collection;
        
        int collectionSize=collection.size();
        
        for(int i=0;i<collectionSize;i++) {
        	tile= collection.getTileAt(i);
        	if (tile.type==4 || tile.type==3)
        		hasHonour=true;
        	else if (prevtileType!=tile.type) {
        		numOfColor++;
        		prevtileType=tile.type;
        		if (numOfColor==2)
        			return 0;
        	}
        }  
        TileCollection visibleTiles=g_visibleTiles;
        collectionSize=visibleTiles.size();
        for(int i=0;i<collectionSize;i++) {
        	tile= visibleTiles.getTileAt(i);
        	if (tile.type==4 || tile.type==3)
        		hasHonour=true;
        	else if (prevtileType!=tile.type) {
        		numOfColor++;
        		prevtileType=tile.type;
        		if (numOfColor==2)
        			return 0;
        	}
        }
        //debug(10,"***************numOfColor="+numOfColor);
        if (numOfColor==0)
        	return 3;//only consist of honour tiles
        else if (hasHonour)
        	return 1;//half color
        else
        	return 2;//full color
        
    }
    
    /* Return value
     * 0 = normal 
     * 1 = half one and nine
     * 2 = full one and nine
     **/
    private int checkOneAndNine() {
        //debug(2,"checkOneAndNine");
    	boolean hasHonour=false;
        Tile tile;
        TileCollection collection=g_collection;
        int collectionSize=collection.size();
        for(int i=0;i<collectionSize;i++) {
        	tile= collection.getTileAt(i);
        	if (tile.type==4 || tile.type==3)
        		hasHonour=true;
        	else if (tile.value!=1 && tile.value!=9) {
        		return 0;
        	}
        }  
        TileCollection visibleTiles=g_visibleTiles;
        collectionSize=visibleTiles.size();
        for(int i=0;i<collectionSize;i++) {
        	tile= visibleTiles.getTileAt(i);
        	if (tile.type==4 || tile.type==3)
        		hasHonour=true;
        	else if (tile.value!=1 && tile.value!=9) {
        		return 0;
        	}
        }

        if (hasHonour) {
        	return 1;//half one and nine
        }
        //else 
        {
        	return 2;//full one and nine
        }
    }
    
    
    
    /*
     * cost: one loop (max)
     */
    public boolean checkThirteenWonder() {
    	//debug(2,"checkThirteenWonder");
        Tile tile;
    	TileCollection collection=this.g_collection;
    	TileCollection visibleTiles=this.g_visibleTiles;

        if  (countChow>0 || countPong>0 || visibleTiles.size()>0)
    		return false;
        
    	int colsize=collection.size();
        if (colsize!=14) {
        	return false;
        }

        //check the honourtile first;
    	int startPos=collection.getHonourTileStartPos();    	
    	//there is honour pair
    	Tile temptile;
    	//debug(2,"colsize-startpos="+ (colsize-startPos) );
    	if (startPos!=-1) {
    		if (colsize-startPos<7  || colsize-startPos>8 ) {
    			return false;
    		}
    		
    		//else 
    		{
    			int honourpaircount=0;
    			Tile prevtile=  collection.getTileAt(startPos);
    			//debug(1,"startpos="+startPos);
    			for (int i=startPos+1;i<colsize;i++){
    				temptile=   collection.getTileAt(i);
    				//debug(1,"check honour pair="+temptile);
    				if (temptile.equals(prevtile)) {
    					honourpaircount++;
    					if (honourpaircount+countPair>1) {
    						return false;
    					}
    				}
    				prevtile=  collection.getTileAt(i);
    			}
    		}
    	}	
		//check normal tile
    	for(int i=0;i<colsize;i++) {
        	tile=   collection.getTileAt(i);
        	if (tile.type==4 || tile.type==3) {
        		//skip
        	}
        	else if (tile.value!=1 && tile.value!=9) {
        		return false;
        	}
        }  
    	return true;
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
    

    private void printEnd(int level) {

    	TileCollection collection=g_collection;
    	TileCollection visibleTiles=g_visibleTiles;
    	
    	if (level>=debugLevel) {
			System.out.println("\nVisible : ");
		    for(int i=0;i<=visibleTiles.size()-1;i++) {
		    	System.out.print( visibleTiles.getTileAt(i).type +""  + visibleTiles.getTileAt(i).value + "_" + 
		    			visibleTiles.getTileAt(i).group + "_"+ visibleTiles.getTileAt(i).groupType + "  " );
		    }  
		    System.out.println("\nConcealed: ");
		    for(int i=0;i<=collection.size()-1;i++) {
		    	System.out.print( collection.getTileAt(i).type+""  + collection.getTileAt(i).value + "_" + 
		    			collection.getTileAt(i).groupType + "  ");
		    }  
		    System.out.println("");
    	}
    }
 
}
