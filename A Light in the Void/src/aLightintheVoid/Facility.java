package aLightintheVoid;

import myClasses.MyImage;

public class Facility extends Tile {

	protected MyImage image[] = new MyImage[8];
	protected int xpos;
	protected int ypos;
	protected int faction;
	
	public Facility(int xpos, int ypos, int faction, Board board) {
		
		super (xpos, ypos, board);
		
		this.xpos = xpos;
		this.ypos = ypos;
		this.faction = faction;
		
	}
	
	public boolean isAFacility() {
		return true;
	}
	
	public boolean isMovePossible(int faction) {
		if (this.faction==3 || this.faction==faction) {
			return true;
		} else {
			return false;
		}
	}

}
