package HungryHungryJamison;

import myClasses.MyImage;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

import java.util.Random;

import HungryHungryJamison.Food;

public class MainGame {
	
	TrueTypeFont font;
	TrueTypeFont smallfont;
	
	MyImage Jamison;
	MyImage JamisonChewing;
	MyImage JamisonMad;
	public MyImage spider;
	public MyImage math;
	public MyImage broccoli;
	public MyImage beet;
	public MyImage pizza;
	public MyImage ramen;
	public MyImage burger;
	public MyImage icecream;
	Random rand = new Random();
	int x,y,rng,foodx,foody,foodxmove,foodymove,foodnumber,foodscore,score,wins;
	boolean goon,saved;
	double width, height;
	String scoretext,wintext;
	MyImage foodimage;
	Food[] food;
	int JamisonState,StateFrameCount,mode,i;
	Audio Trouble;
	Audio Enchanted;
	Audio December;
		
	
	public void Start(){
		
		init();
		
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {	
			
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			x = x-4;
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			x = x+4;
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			y = y+4;
			}
			
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			y = y-4;
			}
			
			update();
			SoundStore.get().poll(0);
			render();		
			
			
		}
		
		AL.destroy();
		Display.destroy();
		System.exit(0);
	}
	
	
	
	public void render(){
		
		if (score < 0) {
			
			Trouble.playAsMusic(1, 1, true);
			
			while (goon == false && !Display.isCloseRequested()) {
			
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				GL11.glClearColor(256, 256, 256, 1);
				Color.white.bind();
			
				font.drawString((float) (width/2 - 200), (float) (height/2 - 20), "You Lose... Jamison is hungry and unhappy.", Color.black);
				font.drawString((float) (width/2 - 200), (float) (height/2), "Press Escape to quit.", Color.black);
				if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
					goon = true;
				}
				Display.update();
				Display.sync(60);
			}
				AL.destroy();
				Display.destroy();
				System.exit(0);	
			
		} else if (score > 725) {
			
			Enchanted.playAsMusic(1, 1, true);
			
			while (goon == false && !Display.isCloseRequested()) {
				
				if (saved==false) {
					wins++;
					save();
					saved = true;
					wintext=Integer.toString(wins);
				}
				
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				GL11.glClearColor(256, 256, 256, 1);;
				Color.white.bind();
			
				font.drawString((float) (width/2 - 200), (float) (height/2 - 20), "You Win! Jamison is finally full and happy!", Color.black);
				font.drawString((float) (width/2 - 200), (float) (height/2), "You have won " + wintext + " times!", Color.black);
				font.drawString((float) (width/2 - 200), (float) (height/2 + 20), "Press Escape to quit.", Color.black);
				if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
					goon = true;
				}
				Display.update();
				Display.sync(60);
			}
			AL.destroy();
			Display.destroy();
			System.exit(0);	
			
		} else {	
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(256, 256, 256, 1);;
		Color.white.bind();
		if (JamisonState == 0) {
			Jamison.drawImage(x, y, 0);
		} else if (JamisonState == 1) {
			StateFrameCount++;
			if (StateFrameCount < 30 || StateFrameCount > 60) {
				JamisonChewing.drawImage(x, y, 0);
			} else {
				Jamison.drawImage(x, y, 0);
			}
			if (StateFrameCount == 90) {
				JamisonState = 0;
			}
		} else {
			StateFrameCount++;
			JamisonMad.drawImage(x, y, 0);
			if (StateFrameCount ==90) {
				JamisonState = 0;
			}
		}
		for (int i=0; i<foodnumber; i++) {
			food[i].image.drawImage(food[i].x, food[i].y, 0);
		}
		scoretext=Integer.toString(score);
		font.drawString((float) (width/2 + 100), 10, "Score: " + scoretext, Color.black);
		font.drawString(0,0,x + "," + y, Color.black);
		Display.update();
		Display.sync(60);
		
		}
		
	}
	
	public void update(){ 
		
		rng = rand.nextInt(60) + 1;
		
		if (rng == 60) {
		if (foodnumber < 11) {
			
			foodx = 0;
			foody = 0;
			foodxmove = 0;
			foodymove = 0;
			
			switch(rand.nextInt(4)) {
				case(0):	
					foody = -75;
					foodx = rand.nextInt((int) (width) - 50)+1;
					foodymove = rand.nextInt(4)+1;
					break;
				case(1):				
					foodx = -75;
					foody = rand.nextInt((int) (height) - 50)+1;
					foodxmove = rand.nextInt(4)+1;
					break;
				case(2):
					foodx = rand.nextInt((int) (width) - 50)+1;
					foody = (int) (height) + 25;
					foodymove = (rand.nextInt(4)+1)*-1;
					break;
				case(3):
					foody = rand.nextInt((int) (height) - 50)+1;
					foodx = (int) (width) + 25;
					foodxmove = (rand.nextInt(4)+1)*-1;
					break;
			}
			
			switch(rand.nextInt(8)) {
				case(0):
					foodimage = spider;
					foodscore = -200;
					break;
				case(1):
					foodimage = math;
					foodscore = -150;
					break;
				case(2):
					foodimage = broccoli;
					foodscore = -50;
					break;
				case(3):
					foodimage = beet;
					foodscore = -100;
					break;
				case(4):
					foodimage = pizza;
					foodscore = 20;
					break;
				case(5):
					foodimage = ramen;
					foodscore = 10;
					break;
				case(6):
					foodimage = burger;
					foodscore = 5;
					break;
				case(7):
					foodimage = icecream;
					foodscore = 15;
					break;
			}
			food[foodnumber] = new Food(foodx, foody, foodxmove, foodymove, foodscore, foodimage);
			foodnumber=foodnumber+1;
		}		
		}
		for (i=0; i<foodnumber; i++) {
				food[i].update((width + 50), (height + 50));
				if (MyImage.colCheck(Jamison, x, y, food[i].image, food[i].x, food[i].y)) {
					food[i].delete = true;
					StateFrameCount = 0;				
					if (food[i].score > 0) {
						JamisonState = 1;
					} else {
						JamisonState = 2;
					}	
					score = score + food[i].score;
				}
		}
		for (i=0; i<foodnumber; i++) {
			if (food[i].delete) {			
				food[i] = null;
				foodnumber--;					
				for (int q=i; q<foodnumber; q++) {
					food[q] = food[q+1];
				}
			}
		}
	}
			
	
	public void init(){
		
		try {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth();
		height = screenSize.getHeight();
		
			Display.setDisplayMode(Display.getDesktopDisplayMode());
			Display.setFullscreen(true);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}		
		
		score = 200;
		
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
		
		Font awtFont = new Font("Times New Roman", Font.PLAIN, 24);
		Font smalltext = new Font("Times New Roman", Font.PLAIN, 5);
		font = new TrueTypeFont(awtFont, true);
		smallfont = new TrueTypeFont(smalltext, true);
		
		Jamison = new MyImage("png", "res/Jamisonandfood.png", 0, 0, 155, 270, 5, 1);
		Jamison.createNewRect(56,58,100,94);
		Jamison.createNewRect(44,98,112,209);
		Jamison.createNewRect(13,134,150,141);
		Jamison.createNewRect(55,210,62,254);
		Jamison.createNewRect(90,210,98,254);
		JamisonChewing = new MyImage("png", "res/Jamisonandfood.png", 0, 0, 155, 270, 0, 1);
		JamisonMad = new MyImage("png", "res/Jamisonandfood.png", 0, 0, 155, 270, 0, 1);
		
		spider = new MyImage("png", "res/Jamisonandfood.png", 0, 272, 76, 336, 1, 1);
		spider.createNewRect(5,288-272,69,327-272);
		
		math = new MyImage("png", "res/Jamisonandfood.png", 0, 338, 76, 397, 1, 1);
		math.createNewRect(20,344-338,55,388-338);
		
		broccoli = new MyImage("png", "res/Jamisonandfood.png", 0, 399, 76, 457, 2, 1);
		broccoli.createNewRect(23,404-399,70,435-399);
		broccoli.createNewRect(16,436-399,44,453-399);
		
		beet = new MyImage("png", "res/Jamisonandfood.png", 0, 459, 76, 511, 3, 1);
		beet.createNewRect(24,460-459,62,490-459);
		beet.createNewRect(19,489-459,23,494-459);
		beet.createNewRect(7,495-459,18,504-459);
		
		pizza = new MyImage("png", "res/Jamisonandfood.png", 78, 272, 155, 336, 2, 1);
		pizza.createNewRect(82-78,307-272,151-78,320-272);
		pizza.createNewRect(101-78,287-272,146-78,306-272);
		
		ramen = new MyImage("png", "res/Jamisonandfood.png", 78, 338, 155, 397, 1, 1);
		ramen.createNewRect(91-78,341-338,141-78,391-338);
		
		burger = new MyImage("png", "res/Jamisonandfood.png", 78, 399, 155, 457, 1, 1);
		burger.createNewRect(90-78,405-399,146-78,442-399);
		
		icecream = new MyImage("png", "res/Jamisonandfood.png", 78, 459, 155, 511, 1, 1);
		icecream.createNewRect(87-78,464-459,148-78,510-459);
		
		food=new Food[11];
		
		try {
			
			Trouble = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/Trouble.ogg"));
			Enchanted = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/Enchanted.ogg"));
			December = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("res/December.ogg"));			
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		December.playAsMusic(1, 1, true);
		
		Display.setTitle("Hungry Hungry Jamison!");
		
		load();
	}
	
	public void save() {
		
		try {
		
			FileOutputStream HHJ = new FileOutputStream("res/HHJ.sav");
			ObjectOutputStream save = new ObjectOutputStream(HHJ);
			save.writeObject(wins);
			save.close();
			
		}
	
		catch(Exception exc){
			
			exc.printStackTrace();
			
		}
	}
	
	public void load(){
		
		try{

			FileInputStream HHJ = new FileInputStream("res/HHJ.sav");
			ObjectInputStream save = new ObjectInputStream(HHJ);
			wins = (Integer) save.readObject();
			save.close();
		
		}
		
		catch(Exception exc){
			
			exc.printStackTrace();
		
		}
		
	}
	
	public static void main(String[] argv) {
		MainGame idontknow = new MainGame();
		idontknow.Start();
	}
	
}
