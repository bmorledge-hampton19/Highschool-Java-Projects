package aLightintheVoid;

import java.util.ArrayList;

import myClasses.MyImage;

public class Cursor {

	protected int xpos;
	protected int ypos;
	protected int menuCount;
	protected String[] menuString = new String[5];
	protected int menuSelection;
	ArrayList<Integer> potentialAttacky = new ArrayList<Integer>();
	ArrayList<Integer> potentialAttackx = new ArrayList<Integer>();
	protected int attackTarget;
	static int[] directionConstantx = new int[]{1,0,-1,0};
	static int[] directionConstanty = new int[]{0,1,0,-1};
	protected MyImage[] cursorImage = new MyImage[] {new MyImage("res/Backgrounds.png", 400, 320, 480, 400, 0, 1),
												 new MyImage("res/Backgrounds.png", 400, 400, 480, 480, 0, 1)};
	protected MyImage menuSelector = new MyImage("res/Backgrounds2.png", 100, 472, 140, 512, 0, 1);
	protected MyImage menuBlock = new MyImage("res/Backgrounds2.png", 0, 472, 100, 512, 0, 1);
	protected Board board;
	
	public Cursor(int xpos, int ypos, Board board) {
		this.xpos=xpos;
		this.ypos=ypos;
		this.board=board;
	}
	
	public void draw() {
		
		if (board.selectPhase!=Board.MENUING){ 
		
			if (board.frameCount%60 < 30) {
				cursorImage[0].drawImage(xpos*80, ypos*80, 0);
			} else {
				cursorImage[1].drawImage(xpos*80, ypos*80, 0);
			}
		
		} else {
			
			for (int i=0; i<menuCount; i++) {
				menuBlock.drawImage(xpos*80+80, ypos*80+i*40, 0);
				
				board.game.prototype.drawString(xpos*80+90, ypos*80+i*40+5, menuString[i]);
				
			}
			
			menuSelector.drawImage(xpos*80+50, ypos*80+menuSelection*40, 0);
			
		}
			
	}
	
	public void boardMove(int dx, int dy) {
		
		if (xpos+dx!=-1 && xpos+dx!=board.width) xpos=xpos+dx;
		if (ypos+dy!=-1 && ypos+dy!=board.height) ypos=ypos+dy;
		
	}
	
	public void menuMove(int dy) {
		
		menuSelection=menuSelection+dy;
		
		if (menuSelection==-1) menuSelection=menuCount-1;
		if (menuSelection==menuCount) menuSelection=0;
		
	}
	
	public void initializeAttackCursor() {
		xpos=potentialAttackx.get(0);
		ypos=potentialAttacky.get(0);
		attackTarget=0;
	}
	
	public void attackMove(int dx, int dy) {
		
		int targetAdjustment;
		
		if (dx!=0) targetAdjustment=dx;
		else targetAdjustment=dy;

		attackTarget+=targetAdjustment;
		if (attackTarget==-1) attackTarget=potentialAttackx.size()-1;
		if (attackTarget==potentialAttackx.size()) attackTarget=0;
		
		xpos=potentialAttackx.get(attackTarget);
		ypos=potentialAttacky.get(attackTarget);
		
		
		
	}
	
	public void determineAttackTargets() {
		
		potentialAttackx.clear();
		potentialAttacky.clear();
		Ship attackingShip=((Ship)board.tile[board.arrow[0].xpos][board.arrow[0].ypos]);
		
		if (board.tile[xpos][ypos].distanceFromShip==0 || attackingShip.attackMove) {
			
			for (int i=-attackingShip.maxRange; i<attackingShip.maxRange+1; i++) { 
				for (int z=-attackingShip.maxRange+Math.abs(i); z<attackingShip.maxRange-Math.abs(i)+1; z++) {
				
					if (board.isValidTile(xpos+i, ypos+z)) {
						if (Math.abs(i)+Math.abs(z)>=attackingShip.minRange && 
						board.tile[xpos+i][ypos+z].isAttackPossible(attackingShip.faction)) {
							potentialAttackx.add(xpos+i);
							potentialAttacky.add(ypos+z);
						}
					}
						
				}
			}
			
		}
		
	}

	
	public void initializeMenu() {
		menuSelection=0;
		menuCount=0;
		
		if (!potentialAttackx.isEmpty()) {
			menuString[menuCount]="Attack";
			menuCount++;
		}
				
		menuString[menuCount]="Wait";
		menuCount++;
	}
	
	
}
