package aLightintheVoid;

import java.util.Random;

import myClasses.MyImage;

public class Board {

	protected int width;
	protected int height;
	protected Tile[][] tile;
	protected Cursor cursor = new Cursor(0, 0, this);
	protected KeyHandler keyHandler = new KeyHandler(this);
	protected Arrow[] arrow = new Arrow[9];
	ALightInTheVoid game;
	protected MyImage[][] star;
	protected int frameCount;
	protected int[] shipCount = new int[3];
	protected int turn=0;
	static final int SELECTING = 0;
	static final int MOVING = 1;
	static final int MENUING = 2;
	static final int ATTACKING = 3;
	protected int selectPhase;
	protected int arrowNumber;
	protected boolean isAShipMoving;
	protected boolean isAShipAttacking;
	protected boolean isAShipCounterattacking;
	protected boolean showAttackRange;
	static int[] directionConstantx = new int[]{1,0,-1,0};
	static int[] directionConstanty = new int[]{0,1,0,-1};
			
	Random rand = new Random();
	
	public Board(int width, int height, ALightInTheVoid game) {
		
		this.width = width;
		this.height = height;
		this.game = game;
		
		tile = new Tile[width][height];
		
		for (int i=0; i<width; i++) {
			for (int z=0; z<height; z++) {
				
				tile[i][z] = new Tile(i,z,this);
				
			}
		}
		
		star = new MyImage[width*2][height*2];
		
		for (int i=0; i<width*2; i++) {
			for (int z=0; z<height*2; z++) {
				int randintx = rand.nextInt(12);
				int randinty = rand.nextInt(4);		
				
				star[i][z] = game.star[randintx][randinty];
			}
		}
		
		for (int i=0; i<9; i++) {
			arrow[i] = new Arrow(this);		
		}
		
	}
	
	public void createFighter(int faction, int xpos, int ypos) {		
		tile[xpos][ypos] = new Fighter(faction, xpos, ypos, this);		
		shipCount[faction]++;
	}
	
	public void createCruiser(int faction, int xpos, int ypos) {
		tile[xpos][ypos] = new Cruiser(faction, xpos, ypos, this);
		shipCount[faction]++;
	}
	
	public void createAsteroid(int xpos, int ypos) {		
		tile[xpos][ypos] = new Asteroid(xpos, ypos, this);		
	}
	
	public void moveTile(int originx, int originy, int destinationx, int destinationy) {
		Tile temp = tile[originx][originy];		
		tile[originx][originy]=tile[destinationx][destinationy];
		tile[originx][originy].xpos=originx;
		tile[originx][originy].ypos=originy;
		tile[destinationx][destinationy]=temp;
		tile[destinationx][destinationy].xpos=destinationx;
		tile[destinationx][destinationy].ypos=destinationy;
	}
	
	public boolean isValidTile(int x, int y) {
		if (x<0 || x>=width) return false;
		if (y<0 || y>=height) return false;
		return true;
	}

	public void drawBoard() {
		
		for (int i=0; i<width*2; i++) {
			for (int z=0; z<height*2; z++) {
				star[i][z].drawImage(i*40, z*40, 0);
			}
		}
		
		for (int i=0; i<width; i++) {
			for (int z=0; z<height; z++) {
				tile[i][z].draw();
			}
		}
		
		for (int i=0; i<width; i++) {
			for (int z=0; z<height; z++) {
				if (tile[i][z].isAShip()) {
					for (int q=0; q<((Ship)tile[i][z]).fireNumber; q++) {
						((Ship)tile[i][z]).fire[q].draw();
					}
				}
			}
		}
		
		for (int i=1; i<arrowNumber+1; i++) {
			arrow[i].draw();
		}
		
		if (!isAShipMoving && !isAShipAttacking && !isAShipCounterattacking) cursor.draw();
		
		
	}
	
	public void updateBoard() {
		
		int[] gameoverCheck = new int[]{0,0,0};
		
		keyHandler.handleKeys();
		if (keyHandler.checkArrowKeys && !showAttackRange) updatexyPositions();
		if (keyHandler.attemptSelection && !showAttackRange) attemptSelection();
		if (keyHandler.attemptDeselection) attemptDeselection();
		if (showAttackRange && !keyHandler.isxHeldDown) {
			showAttackRange=false;
			((Ship)tile[cursor.xpos][cursor.ypos]).imageState=0;
		}
		
		for (int i=0; i<3; i++) {
			if (shipCount[i]==0) gameoverCheck[i]=1;
		}
		
		if (gameoverCheck[0]+gameoverCheck[1]+gameoverCheck[2]==2) game.gameover=true;
		
		frameCount++;
	}	
	
	public void updatexyPositions() {
		
		int dx = keyHandler.deltax;
		int dy = keyHandler.deltay;
		
		if (selectPhase<MENUING) {
			cursor.boardMove(dx,dy);
		} else if (selectPhase==MENUING && !isAShipMoving) {
			cursor.menuMove(dy);
		} else if (selectPhase==ATTACKING && !isAShipAttacking) {
			cursor.attackMove(dx,dy);
		}
			
		if (selectPhase==MOVING) {
			arrowUpdate();
		}
		
	}
	
	
	
	public void attemptSelection() {
		
		if (selectPhase==SELECTING && !isAShipMoving && !isAShipAttacking) {
			if (tile[cursor.xpos][cursor.ypos].isAShip() && !((Ship)tile[cursor.xpos][cursor.ypos]).acted) { 
				for (int i=0; i<width; i++) {
					for (int z=0; z<height; z++) {
						tile[i][z].isPotentialAttack=false;
						tile[i][z].isPotentialMove=false;
					}
				}
				selectPhase=MOVING;
				initializeMovement((Ship)tile[cursor.xpos][cursor.ypos]);	
				initializeAttackRange((Ship)tile[cursor.xpos][cursor.ypos]);
			} else {
				selectPhase=MENUING;
				cursor.menuCount=1;
				cursor.menuSelection=0;
				cursor.menuString[0]="End Turn";
			}
	
		} else if (selectPhase==MOVING && ((Ship)tile[arrow[0].xpos][arrow[0].ypos]).faction==turn) {
			if ((arrow[0].xpos==cursor.xpos && arrow[0].ypos==cursor.ypos) || 
				(tile[cursor.xpos][cursor.ypos].isPotentialMove && !tile[cursor.xpos][cursor.ypos].isAShip())) {
				selectPhase=MENUING;
				cursor.determineAttackTargets();
				cursor.initializeMenu();
			}
		} else if (selectPhase==MENUING) {
			
			if (cursor.menuString[cursor.menuSelection]=="Wait") {
				selectPhase=SELECTING;
				if (arrowNumber==0) {
					((Ship)tile[arrow[0].xpos][arrow[0].ypos]).acted=true;
					((Ship)tile[arrow[0].xpos][arrow[0].ypos]).imageState=2;
				} else {
					((Ship)tile[arrow[0].xpos][arrow[0].ypos]).moving=true;
					isAShipMoving=true;
					((Ship)tile[arrow[0].xpos][arrow[0].ypos]).proceedToAttack=false;
				}
			}
			
			else if (cursor.menuString[cursor.menuSelection]=="Attack") {
				selectPhase=ATTACKING;
				cursor.initializeAttackCursor();
			}
			
			if (cursor.menuString[cursor.menuSelection]=="End Turn") {
				selectPhase=SELECTING;
				turn++;
				if (turn==3) turn=0;
				if (shipCount[turn]==0) turn++;
				if (turn==3) turn=0;
				for (int i=0; i<width; i++) {
					for (int z=0; z<height; z++) {
						if (tile[i][z].isAShip()) {
						((Ship)tile[i][z]).imageState=0;	
						((Ship)tile[i][z]).acted=false;
						if (((Ship)tile[i][z]).shields>0 && turn==((Ship)tile[i][z]).faction) {							
							((Ship)tile[i][z]).shields+=((Ship)tile[i][z]).maxShields*.15;	
							if (((Ship)tile[i][z]).shields>((Ship)tile[i][z]).maxShields) 
								((Ship)tile[i][z]).shields=((Ship)tile[i][z]).maxShields;
						}
						}
					}
				}
			}
			
		} else if (selectPhase==ATTACKING) {
			selectPhase=SELECTING;	
			if (arrowNumber==0) ((Ship)tile[arrow[0].xpos][arrow[0].ypos]).initializeAttack(cursor.potentialAttackx.get(cursor.attackTarget),
																							cursor.potentialAttacky.get(cursor.attackTarget));
			else {
				((Ship)tile[arrow[0].xpos][arrow[0].ypos]).moving=true;
				isAShipMoving=true;
				((Ship)tile[arrow[0].xpos][arrow[0].ypos]).proceedToAttack=true;
			}
			
		}
		
	}
	
	public void attemptDeselection() {
		if (selectPhase==MOVING) {
			selectPhase=SELECTING;
			arrowNumber=0;
			((Ship)tile[arrow[0].xpos][arrow[0].ypos]).imageState=0;
			cursor.xpos=arrow[0].xpos;
			cursor.ypos=arrow[0].ypos;
		} else if (selectPhase==MENUING) {
			if (cursor.menuString[cursor.menuCount-1]=="End Turn"){
				selectPhase=SELECTING;
			} else {
				selectPhase=MOVING;
			}
		} else if (selectPhase==ATTACKING) {
			selectPhase=MENUING;
			cursor.xpos=arrow[arrowNumber].xpos;
			cursor.ypos=arrow[arrowNumber].ypos;
		} else if (selectPhase==SELECTING && tile[cursor.xpos][cursor.ypos].isAShip()) {
			for (int i=0; i<width; i++) {
				for (int z=0; z<height; z++) {
					tile[i][z].isPotentialAttack=false;
					tile[i][z].isPotentialMove=false;
				}
			}
			initializeMovement((Ship)tile[cursor.xpos][cursor.ypos]);	
			initializeAttackRange((Ship)tile[cursor.xpos][cursor.ypos]);
			showAttackRange=true;
		}
	}
	
	
	public void initializeMovement(Ship ship) {
		
		((Ship)tile[ship.xpos][ship.ypos]).imageState=1;
		((Ship)tile[ship.xpos][ship.ypos]).shipFacing=0;
		tile[ship.xpos][ship.ypos].isPotentialMove=true;
		tile[ship.xpos][ship.ypos].distanceFromShip=0;
		arrow[0].xpos=tile[ship.xpos][ship.ypos].xpos;
		arrow[0].ypos=tile[ship.xpos][ship.ypos].ypos;
		arrowNumber=0;
		
		for (int p=0; p<ship.movement+1; p++) {			
			for (int x=0; x<width; x++) {				
				for (int y=0; y<height; y++) {		
					
					if (tile[x][y].isPotentialMove && tile[x][y].distanceFromShip==p) {
						
						for (int i=0; i<4; i++) {
							
							if (isValidTile(x+directionConstantx[i], y+directionConstanty[i])) {
								if (!tile[x+directionConstantx[i]][y+directionConstanty[i]].isPotentialMove) {
									if (tile[x+directionConstantx[i]][y+directionConstanty[i]].isMovePossible(ship.faction) && 
										tile[x][y].distanceFromShip<ship.movement) {
										tile[x+directionConstantx[i]][y+directionConstanty[i]].isPotentialMove=true;
										tile[x+directionConstantx[i]][y+directionConstanty[i]].distanceFromShip=tile[x][y].distanceFromShip+1;
									}
								}
							}
							
						}
						
					}
					
				}	
			}	
		}	
		
	}

	public void initializeAttackRange(Ship ship) {
		
		if (!ship.attackMove) {
			
			for (int i=-ship.maxRange; i<ship.maxRange+1; i++) { 
				for (int z=-ship.maxRange+Math.abs(i); z<ship.maxRange-Math.abs(i)+1; z++) {
				
					if (isValidTile(ship.xpos+i, ship.ypos+z)) {
						if (Math.abs(i)+Math.abs(z)>=ship.minRange) {
							tile[ship.xpos+i][ship.ypos+z].isPotentialAttack=true;
						}
					}
						
				}
			}
			
		} else {
			
			for (int x=0; x<width; x++) {				
				for (int y=0; y<height; y++) {		
					
					if (tile[x][y].isPotentialMove&&(!tile[x][y].isAShip()||(x==ship.xpos&&y==ship.ypos))) {
						
						for (int i=-ship.maxRange; i<ship.maxRange+1; i++) { 
							for (int z=-ship.maxRange+Math.abs(i); z<ship.maxRange-Math.abs(i)+1; z++) {
							
								if (isValidTile(x+i, y+z)) {
									if (Math.abs(i)+Math.abs(z)>=ship.minRange) {
										
											tile[x+i][y+z].isPotentialAttack=true;
											
									}
								}
									
							}
						}
						
					}
					
				}
			}
			
		}
		
	}
	
	public void arrowUpdate() {
		boolean newArrowPlaced=false;		
		int movement=((Ship)tile[arrow[0].xpos][arrow[0].ypos]).movement;
		
		if (tile[cursor.xpos][cursor.ypos].isPotentialMove) {
			
			newArrowPlaced=checkArrowOverlap();
			
			if (!newArrowPlaced && arrowNumber+1>movement) {
				repositionArrows();
				newArrowPlaced=true;
			}
			
			int adjacentCheck=Math.abs((100*(cursor.xpos-arrow[arrowNumber].xpos)+(cursor.ypos-arrow[arrowNumber].ypos)));
			if (!newArrowPlaced && !(adjacentCheck==1 || adjacentCheck==100)) {
				repositionArrows();
			    newArrowPlaced=true;
			}
			
			if (!newArrowPlaced) {
				arrowNumber++;
				arrow[arrowNumber].xpos=cursor.xpos;
				arrow[arrowNumber].ypos=cursor.ypos;
				newArrowPlaced=true;
			}
			
		}
		
		
		if (newArrowPlaced) {
			for (int i=1; i<arrowNumber+1; i++) {
				if (i==arrowNumber) {
					arrow[i].determineType(arrow[i-1].xpos, arrow[i-1].ypos);
				} else {
					arrow[i].determineType(arrow[i-1].xpos, arrow[i-1].ypos, arrow[i+1].xpos, arrow[i+1].ypos);
				}
			}
		}
		
	}
	
	public boolean checkArrowOverlap() {
		for (int i=0; i<(arrowNumber+1); i++) {
			
			if ((cursor.xpos==arrow[i].xpos)&&(cursor.ypos==arrow[i].ypos)){
				
				for (int j=i+1; j<(arrowNumber+1); j++) {
					arrow[j].xpos=-1;
					arrow[j].ypos=-1;
					arrow[j].type=-1;
				}
				
				arrowNumber=i;
				return true;
				
			}
			
		}
		return false;
	}
	
	public void repositionArrows() {
		
		arrowNumber=tile[cursor.xpos][cursor.ypos].distanceFromShip;
		arrow[arrowNumber].xpos=cursor.xpos;
		arrow[arrowNumber].ypos=cursor.ypos;
		
		for (int i=arrowNumber-1; i>0; i=i-1) {
			
			if (arrow[i+1].xpos<width-1) {
				if (tile[arrow[i+1].xpos+1][arrow[i+1].ypos].distanceFromShip==i) {
					arrow[i].xpos=arrow[i+1].xpos+1;
					arrow[i].ypos=arrow[i+1].ypos;
				}
			}
			if (arrow[i+1].ypos<height-1) {
				if (tile[arrow[i+1].xpos][arrow[i+1].ypos+1].distanceFromShip==i) {
					arrow[i].xpos=arrow[i+1].xpos;
					arrow[i].ypos=arrow[i+1].ypos+1;
				}
			}
			if (arrow[i+1].xpos>0) {
				if (tile[arrow[i+1].xpos-1][arrow[i+1].ypos].distanceFromShip==i) {
					arrow[i].xpos=arrow[i+1].xpos-1;
					arrow[i].ypos=arrow[i+1].ypos;
				} 
			}
			if (arrow[i+1].ypos>0) {
				if(tile[arrow[i+1].xpos][arrow[i+1].ypos-1].distanceFromShip==i) {
					arrow[i].xpos=arrow[i+1].xpos;
					arrow[i].ypos=arrow[i+1].ypos-1;
				}
			}
		}
		
	}
	
	
}