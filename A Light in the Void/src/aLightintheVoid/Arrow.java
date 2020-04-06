package aLightintheVoid;

import myClasses.MyImage;

public class Arrow {

	protected Board board;
	protected int xpos;
	protected int ypos;
	protected int type;
	protected MyImage[] arrowImage = new MyImage[3];
	static int[] directionConstantx = new int[]{1,0,-1,0};
	static int[] directionConstanty = new int[]{0,1,0,-1};
	
	public Arrow(Board board) {
		
		this.board=board;
		
		for (int i=0; i<3; i++) {
			arrowImage[i]=board.game.arrow[i];
		}
		
	}

	public void draw() {
		
		if (!board.isAShipMoving) {
			if (type<2) {
				arrowImage[0].drawImage(xpos*80, ypos*80, type*90);
			} else if (type<6) {
				arrowImage[1].drawImage(xpos*80, ypos*80, (type-2)*90);
			} else {
				arrowImage[2].drawImage(xpos*80, ypos*80, (type-6)*90);
			}
		}
		
	}
	
	public void determineType(int previousx, int previousy, int nextx, int nexty) {
		
		int curveAlgorithm=(10*(previousx-xpos)+(previousy-ypos)+10*(nextx-xpos)+(nexty-ypos));
		
		if (previousy==nexty) type=0;
		else if (previousx==nextx) type=1;
		else if (curveAlgorithm==-9) type=2;
		else if (curveAlgorithm==-11) type=3;
		else if (curveAlgorithm==9) type=4;
		else if (curveAlgorithm==11) type=5;
		
	}
	
	public void determineType(int previousx, int previousy) {
		
		if (previousx-xpos==-1) type=6;
		if (previousy-ypos==-1) type=7;
		if (previousx-xpos==1) type=8;
		if (previousy-ypos==1) type=9;
		
	}
	
}
