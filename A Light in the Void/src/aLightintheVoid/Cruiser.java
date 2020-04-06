package aLightintheVoid;

import org.newdawn.slick.Color;

import myClasses.MyImage;

public class Cruiser extends Ship {

	static MyImage[][] cruiserImages = new MyImage[3][3];
	static MyImage shield;
	
	public Cruiser(int faction, int xpos, int ypos, Board board) {
		super(faction, cruiserImages, xpos, ypos, 25, 1, .75,
				.5, 1, 1.5, 50, 50, 60, 10,
				6, 1, 1, true, true, board);
		
		for (int i=0; i<3; i++) {
			for (int z=0; z<3; z++) {
				
				cruiserImages[i][z]=board.game.cruiser[i][z];
				
			}
		}
		
		shield = board.game.smallShield;
		
		if (faction==0) {			
			fireImage = new MyImage[4];
			fire = new ShipProjectile[4];
			for (int i=0; i<4; i++) {
				fireImage[i]=board.game.cruiserFire[faction][i];
			}
			firexAdjust= new int[]{36,63,33,11};
			fireyAdjust= new int[]{11,36,63,33};
		} else {
			fireImage = new MyImage[3];
			for (int i=0; i<3; i++) {
				fireImage[i]=board.game.cruiserFire[faction][i];
			}
			if (faction==1) {
				fire = new ShipProjectile[8];
				firexAdjust= new int[]{47,58,18,7,54,48,11,17};
				fireyAdjust= new int[]{7,47,58,18,17,54,48,11};
				firexSpeed= new int[]{4,0,-4,0};
				fireySpeed= new int[]{0,4,0,-4};
			} else {
				fire = new ShipProjectile[4];
				firexAdjust= new int[]{53,59,8,2};
				fireyAdjust= new int[]{2,53,59,8};		
				firexSpeed= new int[]{2,0,-2,0};
				fireySpeed= new int[]{0,2,0,-2};
			}
		}
		
	}

	
	public void draw() {
		
		super.draw();
			
		if (moving) move();
		if (attacking) {
			if (faction==0) updateAlienAttack();
			else if (faction==1) updateMercenaryAttack();
			else updateFederationAttack();
		}

		image[faction][imageState].drawImage(((double)(xpos+tempMovementX))*80, 
		((double)(ypos+tempMovementY))*80, shipFacing*90);
		
		String healthText = Integer.toString((int)(100*(double)health/(double)maxHealth));
		board.game.smallPrototype.drawString((float)(xpos+tempMovementX)*80+50, (float)(ypos+tempMovementY)*80+68, healthText+"%", Color.white);
	
		if (shields>0) {
			shield.drawImage(((double)(xpos+tempMovementX))*80, ((double)(ypos+tempMovementY))*80, shipFacing*90);
			String shieldText = Integer.toString((int)(100*(double)shields/(double)maxShields));
			board.game.smallPrototype.drawString((float)(xpos+tempMovementX)*80, (float)(ypos+tempMovementY)*80+68, shieldText+"%", Color.cyan);
			board.game.rebind();
		}
	}
	
	public void updateAlienAttack() {

		if (fireNumber==0) {			
			for (int i=0; i<2; i++) {
				
				fire[fireNumber]=new ShipProjectile(xpos*80+firexAdjust[shipFacing]+-(directionConstanty[shipFacing])*51*i, 
													ypos*80+fireyAdjust[shipFacing]+(directionConstantx[shipFacing])*51*i, 
													0, 0, fireImage, shipFacing*90);
				fireNumber++;
				
			}
		}
		
		for (int i=0; i<fireNumber; i++) {
			
			if (fire[i].frame<40 && fire[i].frame%5==0) fire[i].imageState=rand.nextInt(3);
			else if (fire[i].frame>40) fire[i].imageState=3;
			
			if (fire[i].frame==40)  {
				if (shipFacing==0) {
				fire[i].ypos-=4;
				} else if (shipFacing==1) {
				fire[i].xpos-=4;	
				} else if (shipFacing==2) {
					fire[i].xpos-=69;
					fire[i].ypos-=4;
				} else {
					fire[i].xpos-=4;
					fire[i].ypos-=69;
				}
			}
			
			fire[i].update(false, false);
			
		}
		
		if (fire[0].frame==80) {
			finishAttack();
		}
		
	}

	public void updateMercenaryAttack() {
		
		boolean create=false;
		
		if (fireNumber==0) create=true;
		else if (fire[0].frame==15 && fire[fireNumber]==null) create=true;
		
		if (create) {
			for (int i=0; i<4; i++) {
				
				if (i<2) fire[fireNumber]=new ShipProjectile(xpos*80+firexAdjust[shipFacing]+-(directionConstanty[shipFacing])*57*i, 
															 ypos*80+fireyAdjust[shipFacing]+(directionConstantx[shipFacing])*57*i, 
															 firexSpeed[shipFacing], fireySpeed[shipFacing], fireImage, shipFacing*90);
				else fire[fireNumber]=new ShipProjectile(xpos*80+firexAdjust[shipFacing+4]+-(directionConstanty[shipFacing])*37*(i-2), 
														 ypos*80+fireyAdjust[shipFacing+4]+(directionConstantx[shipFacing])*37*(i-2), 
														 firexSpeed[shipFacing], fireySpeed[shipFacing], fireImage, shipFacing*90);
				fireNumber++;
				
			}
		}
		
		for (int i=fireNumber-1; i>-1; i--) {
			
			boolean move = false;
			boolean advanceFrame = false;
			
			if (fire[i].frame==5) advanceFrame=true;
			else if (fire[4]==null || (i<4 && fire[4].frame<5)){
				if (fire[i].frame==18) advanceFrame=true;
			} else {
				if (fire[i].frame==11) advanceFrame=true;
			}
			
			if (fire[4]==null || (i<4 && fire[4].frame<5)) {
				if (fire[i].frame<18) move=true;
			} else {
				if (fire[i].frame<11) move=true;
			}
			
			fire[i].update(move, advanceFrame);
			
			if (fire[i].frame==30 && fireNumber>i+4) {
				fire[i]=fire[i+4];
				fireNumber--;
			}
			
		}
		
		if (fire[0].frame==26 && !(fire[4]==null || fire[4].frame<15)) {
			finishAttack();
			for (int i=0; i<8; i++){
				fire[i]=null;
			}
		}
		
	}
	
	
	public void updateFederationAttack() {
		
		boolean create=false;
		
		if (fireNumber==0) create=true;
		else if (fire[0].frame==25 && fire[fireNumber]==null) create=true;
		
		if (create) {			
			
			for (int i=0; i<2; i++) {
				
				fire[fireNumber]=new ShipProjectile(xpos*80+firexAdjust[shipFacing]+-(directionConstanty[shipFacing])*57*i, 
						 ypos*80+fireyAdjust[shipFacing]+(directionConstantx[shipFacing])*57*i, 
						 firexSpeed[shipFacing], fireySpeed[shipFacing], fireImage, shipFacing*90);
				fireNumber++;
				
			}	
			
		}
		
		for (int i=fireNumber-1; i>-1; i--) {
			
			boolean move = false;
			boolean advanceFrame = false;
			
			if (fire[i].frame==5) advanceFrame=true;
			else if (fire[2]==null || (i<2 && fire[2].frame<12)){
				if (fire[i].frame==34) advanceFrame=true;
			} else {
				if (fire[i].frame==17) advanceFrame=true;
			}
			
			if (fire[2]==null || (i<2 && fire[2].frame<12)) {
				if (fire[i].frame<34) move=true;
			} else {
				if (fire[i].frame<17) move=true;
			}
			
			fire[i].update(move, advanceFrame);
			
			if (fire[i].frame==48 && fireNumber>i+2) {
				fire[i]=fire[i+2];
				fireNumber--;
			}
			
		}
		
		if (fire[0].frame==34 && !(fire[2]==null || fire[2].frame<18)) {
			finishAttack();
			for (int i=0; i<4; i++){
				fire[i]=null;
			}
		}
	
	}
	
	
}

