package HungryHungryJamison;

	import myClasses.MyImage;
	


public class Food {

	public MyImage image;
	public int x;
	public int y;
	public int xmove;
	public int ymove;
	public int score;
	public boolean delete;
	
	public Food(int startx, int starty, int startxmove, int startymove, int startscore, MyImage startimage){
		x = startx;
		y = starty;
		xmove = startxmove;
		ymove = startymove;
		score = startscore;
		image = startimage;
		
		
	}
	
	public Food food(int startx, int starty, int startxmove, int startymove, int startscore, boolean startgood, MyImage startimage){
		return new Food(startx, starty, startxmove, startymove, startscore, startimage);
	}
	
	public void update(double xmax, double ymax){
		
		delete = false;
		
		x = x + xmove;
		y = y + ymove;
		
		if (x > xmax) {
			delete = true;
		} else if (x < -100) {
			delete = true;
		} else if (y > ymax) {
			delete = true;
		} else if (y < -100) {
			delete = true;
		}
		
	}

	
}
