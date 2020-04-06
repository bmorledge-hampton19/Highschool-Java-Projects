package myClasses;

import java.awt.Font;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;
import myClasses.MyImage;

public class Experimentingwithlwjgl {
	
	TrueTypeFont font;

	
	float x;
	float y;
	float rotation;
	
	MyImage FE1;
	MyImage FE2;
	MyImage FE3;
	MyImage FE4;
	MyImage penguin;
	MyImage ship;
	MyImage battleShip;
	MyImage battleShipTurret;
	
	
	
	
	
	public void start() {
		
		
		initializestuff();
		
		
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {	
		
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
				x = x-2;
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
				x = x+2;
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				y = y+2;
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
				y = y-2;
			}
				
			render();		
			
			
		}
		
		
		Display.destroy();
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void render() {
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(256, 256, 256, 1);;
		Color.white.bind();
//		FE1.drawImage(x, y, 0);
		rotation+=1;
		if (rotation==360) rotation=0;
//		ship.drawImage(x, y, rotation);
//		ship.drawImage(x+100, y, rotation+90);
//		ship.drawImage(x, y+100, rotation+180);
//		ship.drawImage(x+100, y+100, rotation+270);
		
		battleShip.drawImage(x, y, 0);
		battleShipTurret.drawImage(x+50, y+12, rotation);
		battleShipTurret.drawImage(x+92, y+12, rotation);
		battleShipTurret.drawImage(x+50, y+57, rotation);
		battleShipTurret.drawImage(x+92, y+57, rotation);
		
		penguin.drawImage(300, 300, 0);
		if (MyImage.colCheck(penguin, 300, 300, FE1, x, y)) {
			font.drawString(300, 300, "Colliding!", Color.cyan);
		}
		Display.update();
		Display.sync(100);
		
	}
	
	
	
	
	private void initializestuff() {
		
		try {
			Display.setDisplayMode(new DisplayMode(800,600));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		x = 300;
		y = 300;
		
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glShadeModel(GL11.GL_SMOOTH);       
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_LIGHTING);                   
		 
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);               
		GL11.glClearDepth(1);                                      

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		 
		GL11.glViewport(0,0,800,600);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 600, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		Font awtFont = new Font("Times New Roman", Font.PLAIN, 24);
		font = new TrueTypeFont(awtFont, true);
		
		FE1 = new MyImage("res/Fire Emblem.jpg", 0, 0, 256, 256, 1, .5);
		FE1.createNewRect(0,0,256,256);
		penguin = new MyImage("res/penguin.png", 0, 0, 256, 256, 3, 1);
		penguin.createNewRect(7,105,250,158);
		penguin.createNewRect(53,2,206,103);
		penguin.createNewRect(37,159,220,237);
			
		ship = new MyImage("res/fighters.png", 80, 0, 160, 80, 0, 1);
		battleShip = new MyImage("res/battleships.png",0,0,160,80,0,1);
		battleShipTurret = new MyImage("res/BattleShips.png",174,21,191,35,0,1);
				
		Display.setTitle("Poopity poopy poop.");
		
	}
	
	public static void main(String[] argv) {
		Experimentingwithlwjgl poop = new Experimentingwithlwjgl();
		poop.start();
		
	}
	
}
