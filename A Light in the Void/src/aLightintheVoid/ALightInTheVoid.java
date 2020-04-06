package aLightintheVoid;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.InputStream;

import myClasses.MyImage;
import notMyClasses.InputHelper;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

public class ALightInTheVoid {
	
	protected Board gameboard;
	protected int screenWidth;
	protected int screenHeight;
	protected double width;
	protected double height;
	protected boolean gameover=false;
	protected MyImage[][] star = new MyImage[12][4];
	protected MyImage[][] asteroid = new MyImage[12][2];
	protected MyImage[] potentialMove = new MyImage[32];
	protected MyImage[] potentialAttack = new MyImage[32];
	protected MyImage[] arrow = new MyImage[3];
	protected MyImage smallShield;
	protected MyImage largeShield;
	protected MyImage[][] fighter = new MyImage[3][3];
	protected MyImage[][] cruiser = new MyImage[3][3];
	protected MyImage[][] fighterFire = new MyImage[3][7];
	protected MyImage[][] cruiserFire = new MyImage[3][4];
	
	protected TrueTypeFont prototype;
	protected TrueTypeFont smallPrototype;
	protected TrueTypeFont largePrototype;
	
	void start() {
		
		init();		
		
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {	
			
			if (!gameover) update();
			render();
			SoundStore.get().poll(0);
						
		}
		
		AL.destroy();
		Display.destroy();
		System.exit(0);
		
	}
	
	void update() {
		
		InputHelper.update();
		gameboard.updateBoard();
		
	}
	
	void render() {
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(256, 256, 256, 1);
		Color.white.bind();
		
		gameboard.drawBoard();
		
		if (gameboard.turn==0) prototype.drawString(600, 0, "Aliens' Turn", Color.green);
		else if (gameboard.turn==1) prototype.drawString(600, 0, "Mercenaries' Turn", Color.red);
		else prototype.drawString(600, 0, "Federation's Turn", Color.yellow);
		
		if (gameover) {
			if (gameboard.shipCount[0]>0) largePrototype.drawString(500, 300, "Aliens win!!", Color.green);
			else if (gameboard.shipCount[1]>0) largePrototype.drawString(500, 300, "Mercenaries win!!", Color.red);
			else largePrototype.drawString(500, 300, "Federation wins!!", Color.yellow);
		}
		
		Display.update();
		Display.sync(60);
		
	}
	
	protected void rebind() {
		Color.white.bind();
	}
	
	void init() {
		
		try {

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			screenWidth = (int)(1280/80);
			screenHeight = (int)(800/80);
			width = 1280;
			height = 800;
			
				Display.setDisplayMode(Display.getDesktopDisplayMode());
				Display.setFullscreen(true);
				Display.create();
			} catch (LWJGLException e) {
				e.printStackTrace();
				System.exit(0);
			}		
			
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glShadeModel(GL11.GL_SMOOTH);       
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_LIGHTING);                   
			 
			GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);               
			GL11.glClearDepth(1);                                      

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			 
			GL11.glViewport(0,0,(int) (width),(int) height);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			
			GL11.glMatrixMode(GL11.GL_PROJECTION);
			GL11.glLoadIdentity();
			GL11.glOrtho(0, (int) (width), (int) (height), 0, 1, -1);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			
			try {
				InputStream inputStream	= ResourceLoader.getResourceAsStream("res/Prototype.ttf");
		 
				Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
				awtFont = awtFont.deriveFont(24f);
				prototype = new TrueTypeFont(awtFont, false);
				awtFont = awtFont.deriveFont(12f);
				smallPrototype = new TrueTypeFont(awtFont, false);
				awtFont = awtFont.deriveFont(48f);
				largePrototype = new TrueTypeFont(awtFont, false);
		 
			} catch (Exception e) {
				e.printStackTrace();
			}	
			
			loadImages();
			
			Display.setTitle("A Light in the Void");
			
			gameboard=new Board(screenWidth, screenHeight, this);
			
			for (int i=0; i<5; i++) {
				gameboard.createAsteroid(i, 4);
				gameboard.createAsteroid(15-i,4);
			}
			
			for (int i=0; i<4; i++) {
				gameboard.createAsteroid(2+i,9-i);
				gameboard.createAsteroid(10+i, 6+i);
			}
			
			gameboard.createAsteroid(2,6);
			gameboard.createAsteroid(6,5);
			gameboard.createAsteroid(5,2);
			gameboard.createAsteroid(7,3);
			gameboard.createAsteroid(7,0);
			gameboard.createAsteroid(9,1);
			gameboard.createAsteroid(10,3);
			gameboard.createAsteroid(13,6);
			
			for (int i=0; i<4; i++) {
				gameboard.createCruiser(0, 2, i);
				gameboard.createCruiser(1, 6+i, 7);
				gameboard.createCruiser(2, 13, i);
				gameboard.createFighter(0, 1, i);
				gameboard.createFighter(1, 6+i, 8);
				gameboard.createFighter(2, 14, i);
			}
			
	}
	
	
	void loadImages() {
		
		for (int i=0; i<12; i++) {
			for (int z=0; z<4; z++) {			
				if (z<2) asteroid[i][z] = new MyImage("res/Backgrounds.png", i*40, z*40+160,40+i*40, 200+z*40, 0, 1);
				star[i][z] = new MyImage("res/Backgrounds.png", i*40, z*40,40+i*40, 40+z*40, 0, 1);
			}
		}
		
		for (int i=0; i<32; i++) {
			
			potentialMove[i]=new MyImage("res/Backgrounds2.png", 432-i, i, 512-i, 80+i, 0, 1);
			potentialAttack[i]=new MyImage("res/Backgrounds2.png", 321-i, i, 401-i, 80+i, 0, 1);	
			
		}
		
		for (int i=0; i<3; i++) {
			for (int z=0; z<3; z++) {
				
				if (z==2) {
					fighter[i][2] = new MyImage("res/GrayscaleShips.png", 0, i*80, 80, i*80+80, 0, .5f);
				} else {
					fighter[i][z] = new MyImage("res/Fighters.png", z*80, i*80, 80 + z*80, 80 + i*80, 0, .5f);
				}
				
			}
		}
		
		for (int i=0; i<3; i++) {
			for (int z=0; z<3; z++) {
				
				if (z==2) {
					cruiser[i][2] = new MyImage("res/GrayscaleShips.png", 0, i*80+240, 80, i*80+320, 0, 1);
				} else {
					cruiser[i][z] = new MyImage("res/Cruisers.png", z*80, i*80, 80 + z*80, 80 + i*80, 0, 1);
				}
				
			}
		}
		
		for (int i=0; i<7; i++) {
			if (i<3) {
				fighterFire[0][i] = new MyImage("res/Fighters.png", 165+i*30, 33, 190+i*30, 45, 0, .5f);
			} else {
				fighterFire[0][i] = new MyImage("res/Fighters.png", 171+(i-3)*18, 50, 183+(i-3)*18, 62, 0, .5f);
			}
		}
		
		for (int i=0; i<3; i++) {
			fighterFire[1][i] = new MyImage("res/Fighters.png", 167+23*i, 99, 182+23*i, 114, 0, .5f);
			fighterFire[2][i] = new MyImage("res/Fighters.png", 161+20*i, 176, 180+20*i, 195, 0, .5f);
			cruiserFire[1][i] = new MyImage("res/Fighters.png", 167+23*i, 99, 182+23*i, 114, 0, 1);
			cruiserFire[2][i] = new MyImage("res/Fighters.png", 161+20*i, 176, 180+20*i, 195, 0, 1);
		}
		
		for (int i=0; i<3; i++) {
			cruiserFire[0][i] = new MyImage("res/Cruisers.png", 166+17*i, 6, 178+i*17, 12, 0, 1);				
		}
			cruiserFire[0][3] = new MyImage("res/Cruisers.png", 166, 13, 247, 27, 0, 1);
		
		
		for (int i=0; i<3; i++) {
			arrow[i] = new MyImage("res/Backgrounds2.png", 80*i, 0, 80+80*i, 80, 0, 1);
		}
		
		smallShield = new MyImage("res/Backgrounds2.png", 0, 80, 80, 160, 0, 1);
		largeShield = new MyImage("res/Backgrounds2.png", 80, 80, 240, 16, 0, 1);
		
	}
	
	
	public static void main(String[] argv) {
		
		ALightInTheVoid game = new ALightInTheVoid();
		game.start();
		
	}
	
}