package aLightintheVoid;

import myClasses.MyImage;

public class ShipProjectile {

	protected int xpos;
	protected int ypos;
	protected int xSpeed;
	protected int ySpeed;
	protected MyImage[] image;
	protected int imageState;
	protected int rotation;
	protected int frame;
	
	public ShipProjectile(int xpos, int ypos, int xSpeed, int ySpeed, MyImage[] image, int rotation) {
		this.xpos=xpos;
		this.ypos=ypos;
		this.xSpeed=xSpeed;
		this.ySpeed=ySpeed;
		this.image=image;
		this.rotation=rotation;
		frame=0;
	}
	
	public void update(boolean moveProjectile, boolean advanceImageState) {
		if (moveProjectile) {
			xpos+=xSpeed;
			ypos+=ySpeed;
		}
		if (advanceImageState) imageState++;
		frame++;
	}
	
	public void draw() {
		image[imageState].drawImage(xpos, ypos, rotation);
	}

}
