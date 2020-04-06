package HungryHungryJamison;

import myclasses.Image;

import java.awt.Font;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;	
import java.util.Random;
import HungryHungryJamison.Food;
import java.util.List;
import java.util.ArrayList;

public class MainGame {
	
	TrueTypeFont font;
	
	Image Jamison = new Image();
	Image spider = new Image();
	//Image[] spiders = {new Image(), new Image(), new Image(), new Image() };
	List<Image> spiders = new ArrayList<Image>();
	Image math = new Image();
	Image broccoli = new Image();
	Image beet = new Image();
	Image pizza = new Image();
	Image ramen = new Image();
	Image burger = new Image();
	Image icecream = new Image();
	Random rand = new Random();
	int rng;
	int x;
	int y;
	int foodx;
	int foody;
	int foodxmove;
	int foodymove;
	boolean foodgood;
	Image foodimage = new Image();
	List<Image> allImages = new ArrayList<Image>();
	
	Food food = new Food(foodx, foody, foodxmove, foodymove, foodgood, foodimage);
	
	public MainGame() {
		spiders.add(new Image())
		
	}
	
	public void crap() {
		// add stuff
		allImages.add(spider);
		allImages.add(math);
		allImages.add(broccoli);
		allImages.add(spider);
		allImages.add(spider);
		allImages.add(spider);
		
		for (int i=0; i<allImages.size(); i++) {
			Image image = allImages.get(i);
			image.doStuff();
		}
		
		for (Image image: allImages) {
			image.doStuff();
		}
		
	}
	
	
	public void spawnSpider() {
		Image s = new Image();
		spiders.add(s);
		allImages.add(s);
	}
	
	public void Start(){
		
		init();
		
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
			
			update();
			
			render();		
			
			
		}
		
		
		Display.destroy();
		
	}
	
	public void update(){
		
		rng = rand.nextInt(60) + 1;
		
		if (rng == 60) {
			
			foodx = 0;
			foody = 0;
			foodxmove = 0;
			foodymove = 0;
			
			if (rand.nextInt(1) == 1) {
				
				foodx = rand.nextInt(750)+1;
				foodymove = rand.nextInt(2)+1;
				
			} else {
				
				foody = rand.nextInt(550)+1;
				foodxmove = rand.nextInt(2)+1;
				
			}
			
			Food item = null;
			
			switch(rand.nextInt(8)){
				case(0):
					//foodimage = spider;
					//foodgood = false;
					item = new Spider();
					break;
				case(1):
//					foodimage = math;
//					foodgood = false;
					item = new Math();
					break;
				case(2):
					foodimage = broccoli;
					foodgood = false;
					break;
				case(3):
					foodimage = beet;
					foodgood = false;
					break;
				case(4):
					foodimage = pizza;
					foodgood = true;
					break;
				case(5):
					foodimage = ramen;
					foodgood = true;
					break;
				case(6):
					foodimage = burger;
					foodgood = true;
					break;
				case(7):
					foodimage = icecream;
					foodgood = true;
					break;
					
			}
			
			//Food food = new Food(foodx, foody, foodxmove, foodymove, foodgood, foodimage);
			allItems.add(item);
		}
		
		food.update();
		
		
	}
	
	public void render(){
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(256, 256, 256, 1);;
		Color.white.bind();
		Jamison.drawimage(x, y);
		
		for (Food food: allFood) {
			food.drawImage();
		}
		Display.update();
		Display.sync(100);
		
	}
	
	public void init(){
		
		try {
			Display.setDisplayMode(new DisplayMode(800,600));
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
		 
		GL11.glViewport(0,0,800,600);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 600, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		
		Font awtFont = new Font("Times New Roman", Font.PLAIN, 24);
		font = new TrueTypeFont(awtFont, true);
		
		Jamison.loadimage("png", "Jamisonandfood.png", 0, 0, 155, 270, 5);
		Jamison.createnewrect(56,58,100,94);
		Jamison.createnewrect(44,98,112,209);
		Jamison.createnewrect(13,134,150,141);
		Jamison.createnewrect(55,210,62,254);
		Jamison.createnewrect(90,210,98,254);
		
		spider.loadimage("png", "Jamisonandfood.png", 0, 272, 76, 336, 1);
		spider.createnewrect(5,288,69,327);
		
		math.loadimage("png", "Jamisonandfood.png", 0, 338, 76, 397, 1);
		math.createnewrect(20,344,55,388);
		
		broccoli.loadimage("png", "Jamisonandfood.png", 0, 399, 76, 457, 2);
		broccoli.createnewrect(23,404,70,435);
		broccoli.createnewrect(16,436,44,453);
		
		beet.loadimage("png", "Jamisonandfood.png", 0, 459, 76, 511, 3);
		beet.createnewrect(24,460,62,490);
		beet.createnewrect(19,489,23,494);
		beet.createnewrect(7,495,18,504);
		
		pizza.loadimage("png", "Jamisonandfood.png", 78, 272, 155, 336, 2);
		pizza.createnewrect(82,307,151,320);
		pizza.createnewrect(101,287,146,306);
		
		ramen.loadimage("png", "Jamisonandfood.png", 78, 338, 155, 397, 1);
		ramen.createnewrect(91,341,141,391);
		
		burger.loadimage("png", "Jamisonandfood.png", 78, 399, 155, 457, 1);
		burger.createnewrect(90,405,146,442);
		
		icecream.loadimage("png", "Jamisonandfood.png", 78, 459, 155, 511, 1);
		icecream.createnewrect(87,464,148,510);
		
		Display.setTitle("Hungry Hungry Jamison!");
	}
	
	
	
	public static void main(String[] argv) {
		MainGame idontknow = new MainGame();
		idontknow.Start();
	}
	
}
