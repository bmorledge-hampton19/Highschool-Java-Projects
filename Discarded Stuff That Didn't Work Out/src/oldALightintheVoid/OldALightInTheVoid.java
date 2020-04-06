package oldALightintheVoid;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.InputStream;
import java.util.Random;

import notMyClasses.InputHelper;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.openal.AL;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

import myClasses.Image;

public class OldALightInTheVoid {
	
	Random rand = new Random();		
	
	TrueTypeFont font;
	TrueTypeFont prototype;
	
	private double width, height;
	public int[] backgroundrandx;
	public int[] backgroundrandy;
	public int cursorposx;
	public int cursorposy;
	public int cursorshipID = -1;
	public int cursorfacilityID = -1;
	public int selectionstage=0;
	public int framecount;
	public int framenumber = 1;
	public int z;
	public int[] event = new int[10];
	public int shipbounce;
	public int shipcount;
	public int facilitycount;
	private int[][] occupied;
	public boolean[][] movechecked;
	public boolean[][] movechecking;
	public boolean[][] movecheckingnext;
	public boolean shipmoving;
	public boolean firing;
	public int firetimer;
	public int fireframe;
	public int firexpos;
	public int firexoffset;
	public int fireypos;
	public int fireyoffset;
	public int firerepeat;
	public int doubleshotx;
	public int doubleshoty;
	public int[] shipmovex = new int[80];
	public int[] shipmovey = new int[80];
	public int shipxchange;
	public int shipychange;
	public int shipmoveframe;
	public int shipfacing=1;
	public int[][]movenumber;
	public int[] arrowxpos = new int[9];
	public int[] arrowypos = new int[9];
	public int arrownumber;
	public int[] arrowtype = new int[9];
	public int pointer;
	public boolean arrowredraw;
	public int tempmove;
	public int tempint;
	public boolean[] adjacentattack = new boolean[4];
	public int shipaim;
	public boolean checked;
	public boolean cursormove;
	public boolean showmenu;
	public int menucount;
	public String[] menutext = new String[5];
	public int menuselection;
	
	
	public Image[][] background = new Image[12][4];
	public Image[] asteroid = new Image[24];
	public Image[] cursor = new Image[2];	
	public Image[] shipmove = new Image[32];
	public Image[] shipattack = new Image[32];
	public Image[] arrow = new Image[10];
	public Image menu = new Image();
	public Image menuselector= new Image();
	public Image[] spacedock = new Image[4];
	public Image[][] research = new Image[4][2];
	public Image[] turret = new Image[4];
	public Image[][] turretfire = new Image[3][4];
	
	public Image[][] fighter = new Image[3][6];
	public Image[][][] fighterfire = new Image[3][7][4];
	public Image[][] cruiser = new Image[3][6];	
	public Image miniships[][] = new Image[3][15];
	
	public Ship[] ship = new Ship[110];
	public Facility[] facility = new Facility[20];
	
	
	
	public void start() {
		
		init();		
		
		while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {	
			

			
			update();
			render();
			SoundStore.get().poll(0);
			
			
		}
		
		AL.destroy();
		Display.destroy();
		System.exit(0);
	}
		
	
	
	public void update() {
		
		InputHelper.update();
		
		if (selectionstage<2) {
		
			if (InputHelper.isKeyPressed(Keyboard.KEY_LEFT) && cursorposx > 0) {
				cursorposx = cursorposx - 1;
				cursormove = true;
				event[0] = framecount;
			}
			
			if (InputHelper.isKeyDown(Keyboard.KEY_LEFT) && (event[0] != 1) && event[0] + 30 < framecount && cursorposx > 0) {
				if (framecount%5 == 0) {
					cursorposx = cursorposx -1;
					cursormove = true;
				}
			}
			
			if (InputHelper.isKeyReleased(Keyboard.KEY_LEFT)) {
				event[0] = 1;
			}
			
			
			
			
			if (InputHelper.isKeyPressed(Keyboard.KEY_RIGHT) && cursorposx < (width/80-1)) {
				cursorposx = cursorposx + 1;
				cursormove = true;
				event[1] = framecount;
			}
			
			if (InputHelper.isKeyDown(Keyboard.KEY_RIGHT) && (event[1] != 1) && event[1] + 30 < framecount && cursorposx < (width/80-1)) {
				if (framecount%5 == 0) {
					cursorposx = cursorposx +1;
					cursormove = true;
				}
			}
			
			if (InputHelper.isKeyReleased(Keyboard.KEY_RIGHT)) {
				event[1] = 1;
			}
			
			
			
			
			if (InputHelper.isKeyPressed(Keyboard.KEY_DOWN) && cursorposy < (height/80-1)) {
				cursorposy = cursorposy + 1;
				cursormove = true;
				event[2] = framecount;
			}
			
			if (InputHelper.isKeyDown(Keyboard.KEY_DOWN) && (event[2] != 1) && event[2] + 30 < framecount && cursorposy < (height/80-1)) {
				if (framecount%5 == 0) {
					cursorposy = cursorposy +1;
					cursormove = true;
				}
			}
			
			if (InputHelper.isKeyReleased(Keyboard.KEY_DOWN)) {
				event[2] = 1;
			}
			
			
			
			
			if (InputHelper.isKeyPressed(Keyboard.KEY_UP) && cursorposy > 0) {
				cursorposy = cursorposy - 1;
				cursormove = true;
				event[3] = framecount;
			}
			
			if (InputHelper.isKeyDown(Keyboard.KEY_UP) && (event[3] != 1) && event[3] + 30 < framecount && cursorposy > 0) {
				if (framecount%5 == 0) {
					cursorposy = cursorposy -1;
					cursormove = true;
				}
			}
			
			if (InputHelper.isKeyReleased(Keyboard.KEY_UP)) {
				event[3] = 1;
			}
			
		}	else if(selectionstage==2) {
			
			if (InputHelper.isKeyPressed(Keyboard.KEY_DOWN)){
				menuselection++;		
			}
			
			if (InputHelper.isKeyPressed(Keyboard.KEY_UP)){
				menuselection--;		
			}
			
			if (menuselection<0) {
				menuselection=menucount-1;
			} else if (menuselection==menucount) {
				menuselection=0;
			}
			
		} else if (!firing) {
			
			if (InputHelper.isKeyPressed(Keyboard.KEY_RIGHT)){
				
				if (adjacentattack[0] && !(shipaim==0)) {
					shipaim=0;		
				} else {
					
					tempint=0;
					
					while (tempint==0) {
						
						shipaim++;
						if (shipaim==4) shipaim=0;						
						if (adjacentattack[shipaim]) tempint=1;
						
					}
					
				}
				
			}
			
			if (InputHelper.isKeyPressed(Keyboard.KEY_DOWN)){
				
				if (adjacentattack[1] && !(shipaim==1)) {
					shipaim=1;		
				} else {
					
					tempint=0;
					
					while (tempint==0) {
						
						shipaim++;
						if (shipaim==4) shipaim=0;						
						if (adjacentattack[shipaim]) tempint=1;
						
					}
					
				}
				
			}
			
			if (InputHelper.isKeyPressed(Keyboard.KEY_LEFT)){
				
				if (adjacentattack[2] && !(shipaim==2)) {
					shipaim=2;	
				} else {
					
					tempint=0;
					
					while (tempint==0) {
						
						shipaim--;
						if (shipaim==-1) shipaim=3;						
						if (adjacentattack[shipaim]) tempint=1;
						
					}
					
				}
				
			}
			
			if (InputHelper.isKeyPressed(Keyboard.KEY_UP)){
				
				if (adjacentattack[3] && !(shipaim==3)) {
					shipaim=3;		
				} else {
					
					tempint=0;
					
					while (tempint==0) {
						
						shipaim--;
						if (shipaim==-1) shipaim=3;						
						if (adjacentattack[shipaim]) tempint=1;
						
					}
					
				}
				
			}
			
			switch (shipaim) {
			
			case(0):cursorposx=ship[cursorshipID].xpos+1;
					cursorposy=ship[cursorshipID].ypos;
					break;			
			case(1):cursorposx=ship[cursorshipID].xpos;
					cursorposy=ship[cursorshipID].ypos+1;
					break;
			case(2):cursorposx=ship[cursorshipID].xpos-1;
					cursorposy=ship[cursorshipID].ypos;
					break;
			case(3):cursorposx=ship[cursorshipID].xpos;
					cursorposy=ship[cursorshipID].ypos-1;
					break;
			}
				
			
		}
		
		
			
			if (InputHelper.isKeyPressed(Keyboard.KEY_Z)){
					
				if (selectionstage==0) {
					for (int i=0; i<shipcount; i++) {
						
						if (ship[i].xpos == cursorposx && ship[i].ypos == cursorposy && !ship[i].acted) {
							
							cursorshipID = ship[i].ID;
							ship[cursorshipID].selected = true;
							selectionstage=1;
							
						}
						
						if (facility[i].xpos == cursorposx && facility[i].ypos == cursorposy && cursorfacilityID==-1) {
							
							cursorfacilityID = facility[i].ID;
							
						}
						
						
					}
					
					if (cursorshipID==-1 && cursorfacilityID==-1) {
						
						selectionstage=2;
						menucount=1;
						menutext[0]="End Turn";
						
					}

				} else if (selectionstage==1 && cursorposx==arrowxpos[arrownumber] && cursorposy==arrowypos[arrownumber]) {
					
					for (int i=0; i<4; i++) {
						adjacentattack[i]=false;
					}
					menucount=0;
					
					for (int i=0; i<shipcount; i++) {
						
						if (!(ship[i].faction==ship[cursorshipID].faction)) {
							
							tempint=adjacent(cursorposx, cursorposy, ship[i].xpos, ship[i].ypos);
							
							if (!(tempint==0)) {
								
								menucount=1;
								menutext[0]="Attack";
								adjacentattack[tempint-1]=true;
								
							}
							
						}						
						
					}
					
					menutext[menucount]="Wait";
					menucount++;
					selectionstage=2;
					shipmoving=true;
					if (arrownumber==0) {
						shipmoving=false;
					}
					shipmoveframe=0;
					
					for (int i=0; i<arrownumber*10; i++) {
						
						if (i%10==0) {
							tempint=adjacent(arrowxpos[i/10],arrowypos[i/10],arrowxpos[i/10+1],arrowypos[i/10+1]);
						}
						
						switch (tempint) {
						
						case 1: shipmovex[i]=8;
								shipmovey[i]=0;
								break;
						case 2: shipmovex[i]=0;
								shipmovey[i]=8;
								break;		
						case 3: shipmovex[i]=-8;
								shipmovey[i]=0;
								break;	
						case 4: shipmovex[i]=0;
								shipmovey[i]=-8;
								break;
						
						}
						
					}
					
				} else if ((selectionstage==2)&&!(shipmoving)) {
					
					if (menutext[menuselection]=="Wait") {
						
						ship[cursorshipID].acted=true;
						reset();
						
					} else if (menutext[menuselection]=="Attack") {
						selectionstage=3;
						if (adjacentattack[0]) {
							shipaim=0;
							cursorposx=ship[cursorshipID].xpos+1;
							cursorposy=ship[cursorshipID].ypos;
						} else if (adjacentattack[1]) {
							shipaim=1;
							cursorposx=ship[cursorshipID].xpos;
							cursorposy=ship[cursorshipID].ypos+1;
						} else if (adjacentattack[2]) {
							shipaim=2;
							cursorposx=ship[cursorshipID].xpos-1;
							cursorposy=ship[cursorshipID].ypos;
						} else {
							shipaim=3;
							cursorposx=ship[cursorshipID].xpos;
							cursorposy=ship[cursorshipID].ypos-1;
						}
					} else if (menutext[menuselection]=="End Turn") {
						for (int i=0; i<shipcount; i++) {
							ship[i].acted=false;
						}
						selectionstage=0;
					}
					
				} else if ((selectionstage==3) && !(firing)) {
										
					prepareshipfire();
					
				}
				
			}
			
			if (InputHelper.isKeyPressed(Keyboard.KEY_X)){
				if (selectionstage==1) {
						reset();
				} else if (selectionstage==2 && cursorshipID>-1) {
					selectionstage=1;
					shipfacing=1;
					shipmoving=false;
					shipxchange=0;
					shipychange=0;
					ship[cursorshipID].xpos=arrowxpos[0];
					ship[cursorshipID].ypos=arrowypos[0];
					cursorposx=arrowxpos[arrownumber];
					cursorposy=arrowypos[arrownumber];
				} else if (selectionstage==2 && cursorshipID==-1) {
					selectionstage=0;
				} else if (selectionstage==3) {
					selectionstage--;
				}
			}
			
		
	}
	
	
	
	public void render() {
		
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(256, 256, 256, 1);
		Color.white.bind();
		
		int y = 0;
		for (int i=0; i<((int) (width/40)); i++) {
			for (int z=0; z<((int) (height/40)); z++) {
				
				background[backgroundrandx[y]][backgroundrandy[y]].drawimage(i*40, z*40);
				
				y++;
				
			}
		}
		
		
		
		
		for (int i=0; i<24; i++) {
			asteroid[i].drawimage(i*40, 480);
			asteroid[(23-i)].drawimage(i*40, 520);			
		}
		
		
		
		if (selectionstage==1) {
		
			for (int x=0; x<width/80; x++) {
				
				for (int j=0; j<height/80; j++) {
					
					if (movechecked[x][j]) {
						shipmove[framecount%64/2].drawimage(x*80, j*80);
					} else if (movechecking[x][j]) {
						shipattack[framecount%64/2].drawimage(x*80, j*80);
					}
					
				}
					
			} 
			
		}
		
			
		for (int x=0; x<facilitycount; x++) {
		
			if (facility[x].type==0) {
			
				if (facility[x].event < framecount-60) {
					facility[x].event = framecount;
					z=0;
				} else if (facility[x].event<framecount-45) {
					z=1;
				} else {
					z=0;
				}
				
				
				if (z==0) {
					spacedock[facility[x].faction].drawimage(80*facility[x].xpos+3, 80*facility[x].ypos);
				} else {
					spacedock[0].drawimage(80*facility[x].xpos+3, 80*facility[x].ypos);
				}
				
			
			} else if (facility[x].type==1) {
				
				
				if (facility[x].event < framecount-30) {
					facility[x].event = framecount;
					z=0;
				} else if (facility[x].event<framecount-15) {
					z=1;
				} else {
					z=0;
				}
				

				research[facility[x].faction][z].drawimage(80*facility[x].xpos+3, 80*facility[x].ypos+3);			
			
				
			} else if (facility[x].type==2) {
				
				
				
				turret[facility[x].faction].drawimage(80*facility[x].xpos+2, 80*facility[x].ypos);
				
				
					if (facility[x].event<framecount-12) {
						z=3;
					} else if (facility[x].event<framecount-8) {
						z=2;
					} else if (facility[x].event<framecount-4) {
						z=1;
					} else {
						z=0;
					}
				
				
				if (z!=3) {
					turretfire[z][0].drawimage(facility[x].xpos*80+5+2, facility[x].ypos*80+5);
					turretfire[z][1].drawimage(facility[x].xpos*80+51+2, facility[x].ypos*80+5);
					turretfire[z][3].drawimage(facility[x].xpos*80+5+2, facility[x].ypos*80+53);
					turretfire[z][2].drawimage(facility[x].xpos*80+51+2, facility[x].ypos*80+53);
				}
		
				
			}	
				
		}	
		
		
		//if (framecount%59==0) {
			//if (shipbounce==-5) {
				//shipbounce=5;				
			//} else {
				//shipbounce=-5;
			//}
		//}
		
		
		
		
		for (int i=0; i<shipcount; i++) {
			if (ship[i].selected) {
				
				if (shipmoving) {
					
					shipxchange=shipxchange+shipmovex[shipmoveframe];
					shipychange=shipychange+shipmovey[shipmoveframe];
					
					if (shipmovex[shipmoveframe]==8) {
						shipfacing=1;
					} else if (shipmovex[shipmoveframe]==-8) {
						shipfacing=3;
					} else if (shipmovey[shipmoveframe]==8) {
						shipfacing=2;
					} else {
						shipfacing=4;
					}
					
					shipdraw(ship[i].xpos*80+shipxchange, ship[i].ypos*80+shipychange, ship[i].faction, shipfacing, ship[i].image, ship[i].Class, ship[i].maxHealth, ship[i].health);
					
					shipmoveframe++;					
					
					if (shipmoveframe==(arrownumber*10-1)||arrownumber==0) {
						
						shipmoving=false;
						shipxchange=0;
						shipychange=0;
						ship[i].xpos=arrowxpos[arrownumber];
						ship[i].ypos=arrowypos[arrownumber];
						
					}
					
				} else if (selectionstage>2) {
					shipdraw(ship[i].xpos*80, ship[i].ypos*80, ship[i].faction, shipaim+1, ship[i].image, ship[i].Class, ship[i].maxHealth, ship[i].health);		
				} else {
					shipdraw(ship[i].xpos*80, ship[i].ypos*80, ship[i].faction, shipfacing, ship[i].image, ship[i].Class, ship[i].maxHealth, ship[i].health);
				}
				
				if (checked == false) {
					moveinit();
				}							
				
			} else {
				
				if (ship[i].acted) {
					shipdraw(ship[i].xpos*80, ship[i].ypos*80, ship[i].faction, 5, ship[i].image, ship[i].Class, ship[i].maxHealth, ship[i].health);
				} else {
					shipdraw(ship[i].xpos*80, ship[i].ypos*80, ship[i].faction, 0, ship[i].image, ship[i].Class, ship[i].maxHealth, ship[i].health);
				}
				
			}
		}		
		
		
		
		if (cursormove) {
			arrowupdate();	
		}
		
		
		if (selectionstage==1){
			for (int x=1; x<arrownumber+1; x++) {
				if (x==arrownumber) {
					
					tempint=adjacent(arrowxpos[x],arrowypos[x],arrowxpos[x-1],arrowypos[x-1]);
					
					if (tempint==4) {
						pointer=7;
						arrowtype[x]=1;
					} else if (tempint==2) {
						pointer=9;
						arrowtype[x]=1;
					} else if (tempint==3) {
						pointer=6;
						arrowtype[x]=0;
					} else {
						pointer=8;
						arrowtype[x]=0;
					}
					
					
					arrow[pointer].drawimage(arrowxpos[x]*80, arrowypos[x]*80);
					
					
				} else {
					
				arrow[arrowtype[x]].drawimage(arrowxpos[x]*80, arrowypos[x]*80);
				
				}
			}
		}	
		
		if (selectionstage==2 && !(shipmoving)) {
			
			for (int i=0; i<menucount; i++) {
				menu.drawimage(cursorposx*80+80, cursorposy*80+i*40);
				prototype.drawString(cursorposx*80+90, cursorposy*80+i*40+5, menutext[i]);
			}
				
		}	
		
		if (!(selectionstage==2) && !(firing)){
			cursor[framenumber/30].drawimage(cursorposx*80, cursorposy*80);	
		} else if (!shipmoving && !firing) {
			menuselector.drawimage(cursorposx*80+50, cursorposy*80+menuselection*40);
		} else if (firing){
			drawshipfire();
		}
			
		
		if (framenumber < 59) {
			framenumber = framenumber+1;
		} else {
			framenumber = 0;
		}
		

		
		framecount++;
		
		String texttest=Integer.toString(selectionstage);
		prototype.drawString(800, 0, texttest);
		prototype.drawString(800, 24, "Is this working?");
		
		Display.update();
		Display.sync(60);
		
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
		
		backgroundrandx = new int[((int) (width/40) * (int) (height/40))];
		backgroundrandy = new int[((int) (width/40) * (int) (height/40))];
		occupied = new int[(int) (width/80)][(int) (height/80)];
		movechecked = new boolean[(int) (width/80)][(int) (height/80)];
		movechecking = new boolean[(int) (width/80)][(int) (height/80)];
		movecheckingnext = new boolean[(int) (width/80)][(int) (height/80)];
		movenumber = new int[(int) (width/80)][(int) (height/80)];
		
		for (int i=0; i<width/80; i++) {
			for (int z=0; z<height/80; z++) {
			
				occupied[i][z] = 3;
				
			}			
		}
		
		for (int i=0; i<12; i++) {
			
				occupied[i][6]=4;
			
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
		
		Font awtFont = new Font("Times New Roman", Font.PLAIN, 24);
		font = new TrueTypeFont(awtFont, true);
		
		try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream("res/Prototype.ttf");
	 
			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(24f); // set font size
			prototype = new TrueTypeFont(awtFont2, false);
	 
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		
		
		Display.setTitle("A Light in the Void");
	
		
		
		for (int i=0; i<(((int) (width/40)) * ((int) (height/40))); i++) {
				
			backgroundrandx[i] = rand.nextInt(12);
			backgroundrandy[i] = rand.nextInt(4);
								
		}	
		
		for (int i=0; i<12; i++) {
			for (int z=0; z<4; z++) {
				
				background[i][z] = new Image();
				background[i][z].loadimage("png", "res/Backgrounds.png", i*40, z*40, i*40 + 40, z*40 + 40, 0);
				
			}
		}
		
		for (int i=0; i<32; i++) {
				
					shipmove[i] = new Image();
					shipmove[i].loadimage("png", "res/Backgrounds2.png", 432-i, i, 512-i, 80+i, 0);
					
					shipattack[i]=new Image();
					shipattack[i].loadimage("png", "res/Backgrounds2.png", 321-i, i, 401-i, 80+i, 0);				

		}
		
		for (int i=0; i<10; i++) {
			
			if (i < 2) {
				arrow[i]=new Image();
				arrow[i].loadimage("png", "res/Backgrounds2.png", 0, 0, 80, 80, 0);
				arrow[i].rotateimage(i*90);
			} else if (i < 6) {
				arrow[i]=new Image();
				arrow[i].loadimage("png", "res/Backgrounds2.png", 80, 0, 160, 80, 0);
				arrow[i].rotateimage((i-2)*90);
			} else {
				arrow[i]=new Image();
				arrow[i].loadimage("png", "res/Backgrounds2.png", 160, 0, 240, 80, 0);
				arrow[i].rotateimage((i-6)*90);
			}
				
		}
		
		
		menu.loadimage("png", "res/Backgrounds2.png", 0, 472, 100, 512, 0);
		menuselector.loadimage("png", "res/Backgrounds2.png", 100, 472, 140, 512, 0, 1);
		
		
		for (int i=0; i<24; i++) {
			
			asteroid[i] = new Image();
			
			if (i > 11) {
				asteroid[i].loadimage("png", "res/Backgrounds.png", (i-12)*40, 200, 40+(i-12)*40, 240, 0);
			} else {
				asteroid[i].loadimage("png", "res/Backgrounds.png", i*40, 160, 40+i*40, 200, 0);
			}
		}
		
		
		
		
		for (int i=0; i<4; i++) {
			
			spacedock[i] = new Image();	
			
			spacedock[i].loadimage("png", "res/Backgrounds.png", i*80, 240, 80+i*80, 320, 0);
			
		}
		
		
		
		
		for (int i=0; i<4; i++) {
			for (int z=0; z<2; z++) {
				
				research[i][z] = new Image();
				
				research[i][z].loadimage("png", "res/Backgrounds.png", i*80, 320+z*80, i*80+80, 400+z*80, 0);
				
			}
		}
		
		
		
		
		for (int i=0; i<4; i++) {
			
			turret[i] = new Image();
			
			if (i<3) {
				turret[i].loadimage("png", "res/Backgrounds.png", 320, 240+i*80, 400, 320+i*80, 0);
			} else {
				turret[i].loadimage("png", "res/Backgrounds.png", 400, 240, 480, 320, 0);
			}
			
		}
		
		
		
		for (int i=0; i<3; i++) {
			for (int z=0; z<4; z++) {
				
				turretfire[i][z] = new Image();
			
				turretfire[i][z].loadimage("png", "res/Backgrounds.png", i*80+z*20, 480, 20+i*80+z*20, 500, 0);
				
			}			
		}
		
		
		
		
		for (int i=0; i<3; i++) {
			for (int z=0; z<6; z++) {
			
				fighter[i][z]=new Image();
				
				if (z==0) {
					fighter[i][0].loadimage("png", "res/Fighters.png", 0, i*80, 80, 80 + i*80, 0);
					miniships[i][0] = new Image();
					miniships[i][0].loadimage("png", "res/Fighters.png", 0, i*80, 80, 80 + i*80, 0);
					miniships[i][0].resizeimage(.5);
				} else if (z==5) {
					fighter[i][5].loadimage("png", "res/GrayscaleShips.png", 0, i*80, 80, i*80+80, 0);
				} else {
					fighter[i][z].loadimage("png", "res/Fighters.png", 80, i*80, 160, 80 + i*80, 0);
					fighter[i][z].rotateimage(z*90 - 90);
				}
				
				fighter[i][z].resizeimage(.5);
			
			}
		}
		
		for (int i=0; i<3; i++) {
		for (int q=0; q<4; q++) {
			if (i==0) {
				for (int z=0; z<7; z++) {
					fighterfire[i][z][q]=new Image();
					
					if (z<3) {
						fighterfire[i][z][q].loadimage("png", "res/Fighters.png", 165+z*30, 33, 190+z*30, 45, 0);
						fighterfire[i][z][q].rotateimage(90*q);
					} else {
						fighterfire[i][z][q].loadimage("png", "res/Fighters.png", 171+(z-3)*18, 50, 183+(z-3)*18, 62, 0);
					}
					
					fighterfire[i][z][q].resizeimage(.5);
					
				}
			} else {
				for (int z=0; z<3; z++) {
					fighterfire[i][z][q]=new Image();
				
					if (i==1) {
						fighterfire[i][z][q].loadimage("png", "res/Fighters.png", 167+23*z, 99, 182+23*z, 114, 0);
					} else {
						fighterfire[i][z][q].loadimage("png", "res/Fighters.png", 161+20*z, 176, 180+20*z, 195, 0);
					}
					
					fighterfire[i][z][q].resizeimage(.5);
					fighterfire[i][z][q].rotateimage(90*q);
				}
			}

		}	
		}
		
		for (int i=0; i<2; i++) {
			cursor[i] = new Image();
			cursor[i].loadimage("png", "res/Backgrounds.png", 400, 320+i*80, 480, 400+i*80, 0);
		}
		
		
		for (int z=0; z<3; z++) {
			for (int i=0; i<4; i++) {
					
				facility[i+z*4] = new Facility(i+1, z+2, i, facilitycount, z);
				facilitycount++;
					if (i==0) {
					occupied[i+1][z+2] = 3;	
					} else {
					occupied[i+1][z+2] = i-1;
					}
				
			}
		}
			
		
		createfighter(0, 0, 0, shipcount);
		ship[shipcount-1].health=ship[shipcount-1].maxHealth/2;
		createfighter(1, 1, 0, shipcount);
		createfighter(1, 3, 2, shipcount);
		createfighter(2, 2, 0, shipcount);
		createfighter(2, 3, 1, shipcount);
		createfighter(1, 10, 3, shipcount);
		createfighter(1, 1, 2, shipcount);
		
	}
	
	
	
	public void createfighter(int faction, int xpos, int ypos, int ID) {
		
		ship[ID] = new Ship(fighter);
		ship[ID].createfighter(faction, xpos, ypos, ID);
		shipcount++;
		occupied[xpos][ypos]=faction;
		
	}
	
	
	public int adjacent(int x1, int y1, int x2, int y2) {
		
		if (y1==y2) {
			
			if (x2==x1+1) {
				return 1;
			} else if (x2==x1-1) {
				return 3;
			} else {
				return 0;
			}
			
		} else if (x1==x2) {
			
			if (y2==y1+1) {
				return 2;
			} else if (y2==y1-1) {
				return 4;
			} else {
				return 0;
			}
			
		} else {
			return 0;
		}	
		
	}

	public void moveinit() {
		movechecking[ship[cursorshipID].xpos][ship[cursorshipID].ypos]=true;
		arrowxpos[0]=ship[cursorshipID].xpos;
		arrowypos[0]=ship[cursorshipID].ypos;
		arrownumber=0;
		for (int p=0; p<ship[cursorshipID].movement+1; p++) {
			
			for (int x=0; x<width/80; x++) {
				
				for (int j=0; j<height/80; j++) {
					
					if ((movechecking[x][j] && ((occupied[x][j]==3) || (occupied[x][j]==ship[cursorshipID].faction)))) {
						movechecking[x][j]=false;
						movechecked[x][j]=true;
						movenumber[x][j]=p;
						
						if (!(x==(width/80-1))) {
							if (!movechecked[x+1][j]) {
									movecheckingnext[x+1][j]=true;
							}
						}
						
						if (!(j==(height/80-1))) {
							if (!movechecked[x][j+1]) {		
									movecheckingnext[x][j+1]=true;										
							}
						}
						
						if (!(x==0)) {
							if (!movechecked[x-1][j]) {
									movecheckingnext[x-1][j]=true;
							}
						}
						
						if (!(j==0)) {
							if (!movechecked[x][j-1]) {
									movecheckingnext[x][j-1]=true;
							}
						}
						
					
					}
				
				}
				
			}
			
			for (int x=0; x<width/80; x++) {
					
				for (int j=0; j<height/80; j++) {
					
					if (movecheckingnext[x][j]) {
						movecheckingnext[x][j]=false;
						movechecking[x][j]=true;
					}
					
				}
					
			}
			
		}				
		checked=true;
	}
	
	public void arrowupdate() {
		
		if ((cursorshipID > -1) && (movechecked[cursorposx][cursorposy])) {
			
			
			if (arrownumber<ship[cursorshipID].movement) {
				
				if ( (cursorposx==arrowxpos[arrownumber]) && ((cursorposy==(arrowypos[arrownumber]+1)) || (cursorposy==(arrowypos[arrownumber]-1) ))) {
					
					
					arrownumber++;
					arrowxpos[arrownumber]=cursorposx;
					arrowypos[arrownumber]=cursorposy;
					
					arrowtype[arrownumber]=1;
					
					if (!(arrownumber==1)) {
						if (arrowtype[arrownumber-1]==0) {
							
							if (arrowypos[arrownumber]-1==arrowypos[arrownumber-1]) {
								
								if (arrowxpos[arrownumber-1]-1==arrowxpos[arrownumber-2]) {
									arrowtype[arrownumber-1]=2;
								} else {
									arrowtype[arrownumber-1]=5;
								}
									
								
							} else {
								
								if (arrowxpos[arrownumber-1]-1==arrowxpos[arrownumber-2]) {
									arrowtype[arrownumber-1]=3;
								} else {
									arrowtype[arrownumber-1]=4;
								}
								
							}
								
							
						}
					} 
					
				
				} else if ((cursorposy==arrowypos[arrownumber]) && 
				((cursorposx==(arrowxpos[arrownumber]+1)) || (cursorposx==(arrowxpos[arrownumber]-1) )))  {
				
					
					
					arrownumber++;
					arrowxpos[arrownumber]=cursorposx;
					arrowypos[arrownumber]=cursorposy;
					
					arrowtype[arrownumber]=0;
					
					if (!(arrownumber==1)) {
						if (arrowtype[arrownumber-1]==1) {
							
							if (arrowxpos[arrownumber]-1==arrowxpos[arrownumber-1]) {
								
								if (arrowypos[arrownumber-1]-1==arrowypos[arrownumber-2]) {
									arrowtype[arrownumber-1]=4;
								} else {
									arrowtype[arrownumber-1]=5;
								}
									
								
							} else {
								
								if (arrowypos[arrownumber-1]-1==arrowypos[arrownumber-2]) {
									arrowtype[arrownumber-1]=3;
								} else {
									arrowtype[arrownumber-1]=2;
								}
								
							}
								
							
						}
					} 
					
				
					
				} else {
					
				arrowredraw=true;
					
				}
				
				
			} else {
				
				arrowredraw=true;
				
			}
			
			if (arrowredraw==true) {
				
				arrownumber=movenumber[cursorposx][cursorposy];
				
				arrowxpos[arrownumber]=cursorposx;
				arrowypos[arrownumber]=cursorposy;
				
				
					for (int j=arrownumber; j>1; j=j-1) {
						
						if (arrowypos[j]>0) {
							if (movenumber[arrowxpos[j]][arrowypos[j]-1]==j-1) {
								arrowxpos[j-1]=arrowxpos[j];
								arrowypos[j-1]=arrowypos[j]-1;
							} 
						}
						if (arrowypos[j]<(height/80-1)) {
							if (movenumber[arrowxpos[j]][arrowypos[j]+1]==j-1) {
								arrowxpos[j-1]=arrowxpos[j];
								arrowypos[j-1]=arrowypos[j]+1;
							} 
						}
						if (arrowxpos[j]>0) {
							if (movenumber[arrowxpos[j]-1][arrowypos[j]]==j-1) {
								arrowxpos[j-1]=arrowxpos[j]-1;
								arrowypos[j-1]=arrowypos[j];
							} 
						}
						if (arrowxpos[j]<(width/80-1)) {
							if (movenumber[arrowxpos[j]+1][arrowypos[j]]==j-1) {
								arrowxpos[j-1]=arrowxpos[j]+1;
								arrowypos[j-1]=arrowypos[j];
							}
						}	
						
						arrowtype[j-1]=0;
						
					}
					
					
					for (int j=1; j<arrownumber+1; j++) {
						
						if (arrowxpos[j]==arrowxpos[j-1]) {
							
							arrowtype[j]=1;
							
							if (j>1) {
								if (arrowtype[j-1]==0) {
									
									if (arrowypos[j]-1==arrowypos[j-1]) {
										
										if (arrowxpos[j-1]-1==arrowxpos[j-2]) {
											arrowtype[j-1]=2;
										} else {
											arrowtype[j-1]=5;
										}
											
										
									} else {
										
										if (arrowxpos[j-1]-1==arrowxpos[j-2]) {
											arrowtype[j-1]=3;
										} else {
											arrowtype[j-1]=4;
										}
										
									}
										
									
								}
							} 
							
						} else {
							
							if (j>1) {
								if (arrowtype[j-1]==1) {
									
									if (arrowxpos[j]-1==arrowxpos[j-1]) {
										
										if (arrowypos[j-1]-1==arrowypos[j-2]) {
											arrowtype[j-1]=4;
										} else {
											arrowtype[j-1]=5;
										}
											
										
									} else {
										
										if (arrowypos[j-1]-1==arrowypos[j-2]) {
											arrowtype[j-1]=3;
										} else {
											arrowtype[j-1]=2;
										}
										
									}
										
									
								}
							} 
							
						}
						
					}
					
				
				arrowredraw=false;
			}
			
			
			for (int i=0; i<(arrownumber+1); i++) {
				
				if ((cursorposx==arrowxpos[i])&&(cursorposy==arrowypos[i])){
						
					for (int j=i+1; j<(arrownumber+1); j++) {
						arrowxpos[j]=-1;
						arrowypos[j]=-1;
						arrowtype[j]=-1;
					}
					
					arrownumber=i;
				
				}
				
			}
			
	
		}	
	
	cursormove=false;
		
	}
	
	public void shipdraw(int x, int y, int faction, int shipfacing, Image[][] image, String Class, int maxhealth, int health) {
		
			if (Class=="Fighter") {
				
				for (int i=0; i<(4*((double)health/(double)maxhealth)); i++) {
					if (i<2) {
						image[faction][shipfacing].drawimage(x+i*40, y);
					} else {
						image[faction][shipfacing].drawimage(x+(i-2)*40, y+40);
					}
					
				}
				
				
				
			} else {
				
				image[faction][shipfacing].drawimage(x*80, y*80);
				
			}
			
			
		}
	
	public void prepareshipfire(){
		
		firing = true;
		firetimer=framecount;
		firerepeat=0;
		fireframe=0;
		firexpos=0;
		fireypos=0;
		doubleshotx=0;
		doubleshoty=0;
		
		if (ship[cursorshipID].Class=="Fighter") {
			
			if (ship[cursorshipID].faction==0) {
				
				switch (shipaim) {
				
				case 0:
					firexoffset=26;
					fireyoffset=16;
					break;
				
				case 1:
					firexoffset=18;
					fireyoffset=26;
					break;
					
				case 2:
					firexoffset=2;
					fireyoffset=18;
					break;
					
				case 3:
					firexoffset=16;
					fireyoffset=2;
					break;
					
					
				}
				
			} else if (ship[cursorshipID].faction==1) {
				
				switch (shipaim) {
				
				case 0:
					firexoffset=25;
					fireyoffset=10;
					doubleshoty=1;
					break;
				
				case 1:
					firexoffset=10;
					fireyoffset=25;
					doubleshotx=1;
					break;
					
				case 2:
					firexoffset=7;
					fireyoffset=10;
					doubleshoty=1;
					break;
					
				case 3:
					firexoffset=10;
					fireyoffset=7;
					doubleshotx=1;
					break;
					
					
				}
				
			} else {
				
				switch (shipaim) {
				
				case 0:
					firexoffset=22;
					fireyoffset=8;
					doubleshoty=1;
					break;
				
				case 1:
					firexoffset=6;
					fireyoffset=22;
					doubleshotx=1;
					break;
					
				case 2:
					firexoffset=10;
					fireyoffset=6;
					doubleshoty=1;
					break;
					
				case 3:
					firexoffset=8;
					fireyoffset=10;
					doubleshotx=1;
					break;
					
				}
			}
			
		}
		
	}
	
	public void drawshipfire() {
		
		if (ship[cursorshipID].Class=="Fighter") {
			
			if (ship[cursorshipID].faction==0) {
				
				if (firetimer > framecount-40) {
					fireframe=rand.nextInt(3);
				} else if (firetimer > framecount-75) {
					fireframe=rand.nextInt(3)+3;
				} else {
					fireframe=6;
				}
				
				if (fireframe>2 && fireframe<6) {
					switch (shipaim) {
					
					case 0: firexpos=firexpos+2;
					break;
					
					case 1: fireypos=fireypos+2;
					break;
					
					case 2: firexpos=firexpos-2;
					break;
					
					case 3: fireypos=fireypos-2;
					break;
					
					}
				}
				
				
				
				for (int z=0; z<2; z++) {
					for (int i=0; i<4*((double)ship[cursorshipID].health/(double)ship[cursorshipID].maxHealth); i++) {
						if (i<2) {
							fighterfire[0][fireframe][shipaim].drawimage(ship[cursorshipID].xpos*80+i*40+firexoffset+firexpos+16*z*doubleshotx, 
							ship[cursorshipID].ypos*80+fireyoffset+fireypos+16*z*doubleshoty);
						} else {
							fighterfire[0][fireframe][shipaim].drawimage(ship[cursorshipID].xpos*80+(i-2)*40+firexoffset+firexpos+16*z*doubleshotx, 
							ship[cursorshipID].ypos*80+40+fireyoffset+fireypos+16*z*doubleshoty);
						}
						
					}
				}
				
				if (firetimer < framecount-100) {		
					ship[cursorshipID].acted=true;
					reset();
				}
				
			} else if (ship[cursorshipID].faction==1) {
				
				if (firetimer > framecount-5) {
					fireframe=0;
				} else if (firetimer > framecount-18) {
					fireframe=1;
				} else {
					fireframe=2;
				}
				
				if (fireframe<2) {
					switch (shipaim) {
					
					case 0: firexpos=firexpos+4;
					break;
					
					case 1: fireypos=fireypos+4;
					break;
					
					case 2: firexpos=firexpos-4;
					break;
					
					case 3: fireypos=fireypos-4;
					break;
					
					}
				}
				
				
				
				for (int z=0; z<2; z++) {
					for (int i=0; i<4*((double)ship[cursorshipID].health/(double)ship[cursorshipID].maxHealth); i++) {
						if (i<2) {
							fighterfire[1][fireframe][shipaim].drawimage(ship[cursorshipID].xpos*80+i*40+firexoffset+firexpos+13*z*doubleshotx, 
							ship[cursorshipID].ypos*80+fireyoffset+fireypos+13*z*doubleshoty);
						} else {
							fighterfire[1][fireframe][shipaim].drawimage(ship[cursorshipID].xpos*80+(i-2)*40+firexoffset+firexpos+13*z*doubleshotx, 
							ship[cursorshipID].ypos*80+40+fireyoffset+fireypos+13*z*doubleshoty);
						}
						
					}
				}
				
				if (firetimer < framecount-25) {
					
					firetimer=framecount;
					firerepeat++;		
					firexpos=0;
					fireypos=0;
					if (firerepeat==2) {
						ship[cursorshipID].acted=true;
						reset();
					}	
				}
				
			} else {
				
				if (firetimer > framecount-10) {
					fireframe=0;
				} else if (firetimer > framecount-34) {
					fireframe=1;
				} else {
					fireframe=2;
				}
				
				if (fireframe<2) {
					switch (shipaim) {
					
					case 0: firexpos=firexpos+2;
					break;
					
					case 1: fireypos=fireypos+2;
					break;
					
					case 2: firexpos=firexpos-2;
					break;
					
					case 3: fireypos=fireypos-2;
					break;
					
					}
				}
				
				
				
				for (int z=0; z<2; z++) {
					for (int i=0; i<4*((double)ship[cursorshipID].health/(double)ship[cursorshipID].maxHealth); i++) {
						if (i<2) {
							fighterfire[2][fireframe][shipaim].drawimage(ship[cursorshipID].xpos*80+i*40+firexoffset+firexpos+16*z*doubleshotx, 
							ship[cursorshipID].ypos*80+fireyoffset+fireypos+16*z*doubleshoty);
						} else {
							fighterfire[2][fireframe][shipaim].drawimage(ship[cursorshipID].xpos*80+(i-2)*40+firexoffset+firexpos+16*z*doubleshotx, 
							ship[cursorshipID].ypos*80+40+fireyoffset+fireypos+16*z*doubleshoty);
						}
						
					}
				}
				
				if (firetimer < framecount-40) {					
					ship[cursorshipID].acted=true;
					reset();
				}
				
			}
			
		}
		
	}
	
	public void reset() {
		
		selectionstage=0;
		firing=false;
		ship[cursorshipID].selected=false;
		occupied[arrowxpos[0]][arrowypos[0]]=3;
		if (!(cursorfacilityID==-1)) {
			
			if (facility[cursorfacilityID].faction==0){
				occupied[facility[cursorfacilityID].xpos][facility[cursorfacilityID].ypos]=3;
			} else {
				occupied[facility[cursorfacilityID].xpos][facility[cursorfacilityID].ypos]=facility[cursorfacilityID].faction-1;
			}
		}
		occupied[ship[cursorshipID].xpos][ship[cursorshipID].ypos]=ship[cursorshipID].faction;
		
		cursorposx=ship[cursorshipID].xpos;
		cursorposy=ship[cursorshipID].ypos;
		
		cursorshipID=-1;
		cursorfacilityID=-1;
		checked=false;
		
		for (int x=0; x<width/80; x++) {
			
			for (int j=0; j<height/80; j++) {
			
				movechecked[x][j]=false;
				movechecking[x][j]=false;
				movecheckingnext[x][j]=false;
				movenumber[x][j]=-1;
				
			}
				
		}
		
		for (int i=0; i<8; i++) {
			arrownumber=0;
			arrowtype[i]=-1;
			arrowxpos[i]=-1;
			arrowypos[i]=-1;
		}
		
		
	}
	
	
	
}