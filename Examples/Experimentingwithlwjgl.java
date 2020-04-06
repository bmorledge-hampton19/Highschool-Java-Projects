package myclasses;

import java.awt.Font;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;
import myclasses.Image;

public class Experimentingwithlwjgl {
	
	TrueTypeFont font;

	
	double x;
	double y;
	
	Image FE1 = new Image();
	Image FE2 = new Image();
	Image FE3 = new Image();
	Image FE4 = new Image();
	Image Penguin = new Image();
	
	
	
	
	public void start() {
		
		
		initializestuff();
		
		
		while (!Display.isCloseRequested()) {	
		
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
		Color.white.bind();
		FE1.drawimage(x, y);
		Penguin.drawimage(300, 300);
		if (Image.colcheck(Penguin, 300, 300, FE1, x, y)) {
			font.drawString(300, 300, "Colliding!", Color.cyan);
		}
		Display.update();
		Display.sync(100);
		
	}
	
	
	
	
	public Experimentingwithlwjgl() {
		
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
		
		FE1.loadimage("jpg", "Fire Emblem.jpg", 0, 0, 256, 256, 1);
		FE1.createnewrect(0,0,256,256);
		Penguin.loadimage("png", "penguin.png", 0, 0, 256, 256, 3);
		Penguin.createnewrect(7,105,250,158);
		Penguin.createnewrect(53,2,206,103);
		Penguin.createnewrect(37,159,220,237);
		
		
		
		Display.setTitle("Poopity poopy poop.");
		
	}
	
	public static void main(String[] argv) {
		Experimentingwithlwjgl poop = new Experimentingwithlwjgl();
		poop.start();
		
	}
	
}
