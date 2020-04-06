package aLightintheVoid;

import java.util.Random;
import myClasses.MyImage;

public class Asteroid extends Tile {

	protected MyImage[] asteroid = new MyImage[4];
	
	public Asteroid(int xpos, int ypos, Board board) {
		
		super (xpos, ypos, board); 
		
		Random rand = new Random();
		
		for (int i=0; i<4; i++) {
			int randIntx = rand.nextInt(12);
			int randInty = rand.nextInt(2);		
			
			asteroid[i] = board.game.asteroid[randIntx][randInty];
			
		}
		
	}

	
	public void draw() {
		
		super.draw();
		
		asteroid[0].drawImage(xpos*80, ypos*80, 0);
		asteroid[1].drawImage(xpos*80+40, ypos*80, 0);
		asteroid[2].drawImage(xpos*80, ypos*80+40, 0);
		asteroid[3].drawImage(xpos*80+40, ypos*80+40, 0);

	}
	
	public boolean isMovePossible(int faction) {
		return false;
	}
	
}
