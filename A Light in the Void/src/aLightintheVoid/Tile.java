package aLightintheVoid;

import myClasses.MyImage;

public class Tile {

	protected MyImage[] potentialMove = new MyImage[32];
	protected MyImage[] potentialAttack = new MyImage[32];
	protected int xpos;
	protected int ypos;
	protected boolean isPotentialMove;
	protected boolean isPotentialAttack;
	protected int distanceFromShip;
	protected Board board;
	static int[] directionConstantx = new int[]{1,0,-1,0};
	static int[] directionConstanty = new int[]{0,1,0,-1};
	
	public Tile(int xpos, int ypos, Board board) {
		
		this.board=board;
		
		this.xpos=xpos;
		this.ypos=ypos;		
		
		for (int i=0; i<32; i++) {
			
			potentialMove[i]=board.game.potentialMove[i];
			potentialAttack[i]=board.game.potentialAttack[i];
			
		}
		
	}
	
	public void draw(){
		
		if (board.selectPhase==Board.MOVING) {
			if (isPotentialMove) potentialMove[(board.frameCount%64)/2].drawImage(xpos*80, ypos*80, 0);
			else if (isPotentialAttack && ((Ship)board.tile[board.arrow[0].xpos][board.arrow[0].ypos]).attackMove) 
				potentialAttack[(board.frameCount%64)/2].drawImage(xpos*80, ypos*80, 0);			
		} else if (board.showAttackRange) {
			if (isPotentialAttack) potentialAttack[(board.frameCount%64)/2].drawImage(xpos*80, ypos*80, 0);
		}
		
		
	}
	
	public boolean isAShip() {
		
		return false;
		
	}
	
	public boolean isAFacility() {
		
		return false;
		
	}
	
	public boolean isMovePossible(int faction) {
		return true;
	}
	
	public boolean isAttackPossible(int faction) {
		return false;
	}
	
	public boolean isCounterAttackPossible(int attackerx, int attackery) {
		return false;
	}
	
}
