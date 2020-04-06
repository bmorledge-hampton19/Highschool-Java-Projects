package aLightintheVoid;

import org.newdawn.slick.Color;
import myClasses.MyImage;

public class Fighter extends Ship {

	static MyImage[][] fighterImages = new MyImage[3][3];	
	
	public Fighter(int faction, int xpos, int ypos, Board board) {

		super(faction, fighterImages, xpos, ypos, 40, 1, 
				1, 1, 1, .25, 0, 50,
				25, 4, 8, 1, 1, true, true, board);
	
		for (int i=0; i<3; i++) {
			for (int z=0; z<3; z++) {
				
				fighterImages[i][z]=board.game.fighter[i][z];
				
			}
		}
		
		if (faction==0) {			
			fireImage = new MyImage[7];
			fire = new ShipProjectile[4];
			for (int i=0; i<7; i++) {
				fireImage[i]=board.game.fighterFire[faction][i];
			}
			firexAdjust= new int[]{26,18,2,16};
			fireyAdjust= new int[]{16,26,18,2};
			firexSpeed= new int[]{2,0,-2,0};
			fireySpeed= new int[]{0,2,0,-2};
		} else {
			fireImage = new MyImage[3];
			for (int i=0; i<3; i++) {
				fireImage[i]=board.game.fighterFire[faction][i];
			}
			if (faction==1) {
				fire = new ShipProjectile[16];
				firexAdjust= new int[]{25,10,7,10};
				fireyAdjust= new int[]{10,25,10,7};
				firexSpeed= new int[]{4,0,-4,0};
				fireySpeed= new int[]{0,4,0,-4};
			} else {
				fire = new ShipProjectile[8];
				firexAdjust= new int[]{22,6,10,8};
				fireyAdjust= new int[]{8,22,6,10};		
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
			
			for (int i=0; i<(4*((double)health/(double)maxHealth)); i++) {
				
				if (i<2) {
					image[faction][imageState].drawImage(((double)(xpos+tempMovementX))*80+i*40, 
					((double)(ypos+tempMovementY))*80, shipFacing*90);
				} else {
					image[faction][imageState].drawImage(((double)(xpos+tempMovementX))*80+(i-2)*40, 
					((double)(ypos+tempMovementY))*80+40, shipFacing*90);
				}
				
			}
			
			String healthText = Integer.toString((int)(100*(double)health/(double)maxHealth));
			board.game.smallPrototype.drawString((float)(xpos+tempMovementX)*80+50, (float)(ypos+tempMovementY)*80+68, healthText+"%", Color.white);
		
	}
	
	public void updateAlienAttack() {

		if (fireNumber==0) {			
			for (int i=0; i<(4*((double)health/(double)maxHealth)); i++) {
				
				if (i<2) fire[fireNumber]=new ShipProjectile(xpos*80+i*40+firexAdjust[shipFacing], ypos*80+fireyAdjust[shipFacing], 
													firexSpeed[shipFacing], fireySpeed[shipFacing], fireImage, shipFacing*90);
				else fire[fireNumber]=new ShipProjectile(xpos*80+(i-2)*40+firexAdjust[shipFacing], ypos*80+40+fireyAdjust[shipFacing], 
												firexSpeed[shipFacing], fireySpeed[shipFacing], fireImage, shipFacing*90);
				fireNumber++;
				
			}
		}
		
		for (int i=0; i<fireNumber; i++) {
			
			boolean move=false;
			
			if (fire[i].frame<40) fire[i].imageState=rand.nextInt(3);
			else if (fire[i].frame<76) {
				fire[i].imageState=rand.nextInt(3)+3;
				move=true;
			} else fire[i].imageState=6;
			
			fire[i].update(move, false);
			
		}
		
		if (fire[0].frame==100) {
			finishAttack();
		}
		
	}
	
	public void updateMercenaryAttack() {
		
		boolean create=false;
		
		if (fireNumber==0) create=true;
		else if (fire[0].frame==15 && fire[fireNumber]==null) create=true;
		
		if (create) {
			for (int z=0; z<2; z++) {
				for (int i=0; i<(4*((double)health/(double)maxHealth)); i++) {
					
					if (i<2) fire[fireNumber]=new ShipProjectile(xpos*80+i*40+firexAdjust[shipFacing]+Math.abs(fireySpeed[shipFacing])*13/4*z, 
														ypos*80+fireyAdjust[shipFacing]+Math.abs(firexSpeed[shipFacing])*(13/4)*z, 
														firexSpeed[shipFacing], fireySpeed[shipFacing], fireImage, shipFacing*90);
					else fire[fireNumber]=new ShipProjectile(xpos*80+(i-2)*40+firexAdjust[shipFacing]+Math.abs(fireySpeed[shipFacing])*13/4*z, 
													ypos*80+40+fireyAdjust[shipFacing]+Math.abs(firexSpeed[shipFacing])*(13/4)*z, 
													firexSpeed[shipFacing], fireySpeed[shipFacing], fireImage, shipFacing*90);
					fireNumber++;
					
				}
			}	
		}
		
		for (int i=fireNumber-1; i>-1; i--) {
			
			boolean move = false;
			boolean advanceFrame = false;
			
			if (fire[i].frame==5) advanceFrame=true;
			else if (fire[i].frame==20) advanceFrame=true;
			
			if (fire[i].frame<18) move=true;
			
			fire[i].update(move, advanceFrame);
			
			if (fire[i].frame==30 && fireNumber>i+2*(int)(4*((double)health/(double)maxHealth))) {
				fire[i]=fire[i+2*(int)((4*((double)health/(double)maxHealth)))];
				fireNumber--;
			}
			
		}
		
		if (fire[0].frame==31) {
			finishAttack();
			for (int i=0; i<16; i++){
				fire[i]=null;
			}
		}
		
	}

	public void updateFederationAttack() {
	
		if (fireNumber==0) {			
			for (int z=0; z<2; z++) {
				for (int i=0; i<(4*((double)health/(double)maxHealth)); i++) {
					
					if (i<2) fire[fireNumber]=new ShipProjectile(xpos*80+i*40+firexAdjust[shipFacing]+Math.abs(fireySpeed[shipFacing])*7*z, 
														ypos*80+fireyAdjust[shipFacing]+Math.abs(firexSpeed[shipFacing])*7*z, 
														firexSpeed[shipFacing], fireySpeed[shipFacing], fireImage, shipFacing*90);
					else fire[fireNumber]=new ShipProjectile(xpos*80+(i-2)*40+firexAdjust[shipFacing]+Math.abs(fireySpeed[shipFacing])*7*z, 
													ypos*80+40+fireyAdjust[shipFacing]+Math.abs(firexSpeed[shipFacing])*7*z, 
													firexSpeed[shipFacing], fireySpeed[shipFacing], fireImage, shipFacing*90);
					fireNumber++;
					
				}
			}	
		}
		
		for (int i=fireNumber-1; i>-1; i--) {
			
			boolean move = false;
			boolean advanceFrame = false;
			
			if (fire[i].frame==5) advanceFrame=true;
			else if (fire[i].frame==36) advanceFrame=true;
			
			if (fire[i].frame<36) move=true;
			
			fire[i].update(move, advanceFrame);
			
		}
		
		if (fire[0].frame==48) {
			finishAttack();
		}
	
	}


}
