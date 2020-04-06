package myclasses;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class Image {
	
	public Texture stuff;
	public double xco1;
	public double yco1;
	public double xco2;
	public double yco2;
	public double xco3;
	public double yco3;
	public double xco4;
	public double yco4;
	//public String filename;
	//public String filetype;
	public int RectNumber;
	public int[][] RectX;
	public int[][] RectY;
	
	public Image(String filetype, String filename, double x1, double y1, double x3, double y3, int ColNumber) {
		
	}
	
	
	public void loadimage(String filetype, String filename, double x1, double y1, double x3, double y3, int ColNumber) {
			
			xco1 = x1;
			yco1 = y1;
			xco2 = x3;
			yco2 = y1;
			xco3 = x3;
			yco3 = y3;
			xco4 = x1;
			yco4 = y3;
			RectNumber = 0;
			
			if (ColNumber != 0) { 
			RectX = new int[ColNumber][4];
			RectY = new int[ColNumber][4];
			}
			
			try {
				stuff = TextureLoader.getTexture(filetype, ResourceLoader.getResourceAsStream(filename));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
	
	
	public void createnewrect(int x1, int y1, int x3, int y3){
			
			RectX[RectNumber][0] = x1;
			RectX[RectNumber][1] = x3;
			RectX[RectNumber][2] = x3;
			RectX[RectNumber][3] = x1;
			
			RectY[RectNumber][0] = y1;
			RectY[RectNumber][1] = y1;
			RectY[RectNumber][2] = y3;
			RectY[RectNumber][3] = y3;
			
			RectNumber = RectNumber + 1;
			
		}

	public void drawimage(double x, double y) {
		stuff.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2d(xco1/stuff.getImageWidth(),yco1/stuff.getImageHeight());
			GL11.glVertex2d(x,y);
			GL11.glTexCoord2d(xco2/stuff.getImageWidth(),yco2/stuff.getImageHeight());
			GL11.glVertex2d(x+(xco2-xco1),y);
			GL11.glTexCoord2d(xco3/stuff.getImageWidth(),yco3/stuff.getImageHeight());
			GL11.glVertex2d(x+(xco2-xco1),y+(yco4-yco1));
			GL11.glTexCoord2d(xco4/stuff.getImageWidth(),yco4/stuff.getImageHeight());
			GL11.glVertex2d(x,y+(yco4-yco1));
		GL11.glEnd();
	}
	
	public static boolean colcheck(Image Image1, double x1, double y1, Image Image2, double x2, double y2) {
		for(int i=0; i<Image1.RectNumber; i++){
			for (int z=0; z<Image2.RectNumber; z++){
				for (int q=0; q<4; q++){
					
					if ( (Image1.RectX[i][q] + x1) < (Image2.RectX[z][1] + x2) ) {
						if ( (Image1.RectX[i][q] + x1) > (Image2.RectX[z][0] + x2) ) {
							if ( (Image1.RectY[i][q] + y1) < (Image2.RectY[z][2] + y2) ) {
								if ( (Image1.RectY[i][q] + y1) > (Image2.RectY[z][1] + y2) ) {	
										return true;
								}
							}
						}
					}			
					
				}
			}
		}
		return false;
	}
	
}