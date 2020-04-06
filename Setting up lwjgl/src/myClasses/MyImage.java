package myClasses;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class MyImage {
	
	private Image image;
	private double xco1;
	private double yco1;
	private double xco3;
	private double yco3;
	private int rectNumber;
	private int[][] rectx;
	private int[][] recty;
	private double resize;
	
	public MyImage(String fileName, double xco1, double yco1, double xco3, double yco3, int ColNumber, double resize) {
		
		this.xco1=xco1;
		this.yco1=yco1;
		this.xco3=xco3;
		this.yco3=yco3;
		this.resize=resize;
		rectNumber=0;
		
		if (ColNumber != 0) { 
			rectx = new int[ColNumber][4];
			recty = new int[ColNumber][4];
		}
		
		try {
			image = new Image(fileName);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		image.draw();
		
	}	
	
	public void createNewRect(int x1, int y1, int x3, int y3){
			
			rectx[rectNumber][0] = x1;
			rectx[rectNumber][1] = x3;
			rectx[rectNumber][2] = x3;
			rectx[rectNumber][3] = x1;
			
			recty[rectNumber][0] = y1;
			recty[rectNumber][1] = y1;
			recty[rectNumber][2] = y3;
			recty[rectNumber][3] = y3;
			
			rectNumber = rectNumber + 1;
			
	}

	public void drawImage(double x, double y, float rotation) {
		
		image.setCenterOfRotation((float) ((xco3-xco1)/2*resize), (float) ((yco3-yco1)/2*resize));
		image.setRotation(rotation);
		
		image.draw((float)x,(float)y,(float)(x+(xco3-xco1)*resize),(float)(y+(yco3-yco1)*resize),(float)xco1,(float)yco1,(float)xco3,(float)yco3);
		
	}
	
	public void drawImageCentered(double x, double y, float rotation) {
		
		image.setCenterOfRotation((float) ((xco3-xco1)/2*resize), (float) ((yco3-yco1)/2*resize));
		image.setRotation(rotation);
		
		image.draw((float)(x-(xco3-xco1)/2*resize),(float)(y-(yco3-yco1)/2*resize),(float)(x+(xco3-xco1)/2*resize),
				   (float)(y+(yco3-yco1)/2*resize),(float)xco1,(float)yco1,(float)xco3,(float)yco3);
		
	}
	
	
	public static boolean colCheck(MyImage Image1, double x1, double y1, MyImage Image2, double x2, double y2) {
		for(int i=0; i<Image1.rectNumber; i++){
			for (int z=0; z<Image2.rectNumber; z++){
				for (int q=0; q<4; q++){					
					if ( (Image1.rectx[i][q]*Image1.resize + x1) < (Image2.rectx[z][1]*Image2.resize + x2) ) {
						if ( (Image1.rectx[i][q]*Image1.resize + x1) > (Image2.rectx[z][0]*Image2.resize + x2) ) {
							if ( (Image1.recty[i][q]*Image1.resize + y1) < (Image2.recty[z][2]*Image2.resize + y2) ) {
								if ( (Image1.recty[i][q]*Image1.resize + y1) > (Image2.recty[z][1]*Image2.resize + y2) ) {	
										return true;
								}
							}
						}
					}			
					
				}
			}
		}
		
		
		for(int i=0; i<Image2.rectNumber; i++){
			for (int z=0; z<Image1.rectNumber; z++){
				for (int q=0; q<4; q++){					
					if ( (Image2.rectx[i][q]*Image2.resize + x2) < (Image1.rectx[z][1]*Image1.resize + x1) ) {
						if ( (Image2.rectx[i][q]*Image2.resize + x2) > (Image1.rectx[z][0]*Image1.resize + x1) ) {
							if ( (Image2.recty[i][q]*Image2.resize + y2) < (Image1.recty[z][2]*Image1.resize + y1) ) {
								if ( (Image2.recty[i][q]*Image2.resize + y2) > (Image1.recty[z][1]*Image1.resize + y1) ) {	
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