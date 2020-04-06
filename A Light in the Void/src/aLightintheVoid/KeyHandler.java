package aLightintheVoid;

import org.lwjgl.input.Keyboard;
import notMyClasses.InputHelper;

public class KeyHandler {

	protected Board board;
	protected int quickMoveTimer;
	protected int deltax;
	protected int deltay;
	protected boolean checkArrowKeys;
	protected boolean attemptSelection;
	protected boolean attemptDeselection;
	protected boolean isxHeldDown=true;
	
	public KeyHandler(Board board) {
		this.board=board;
	}

	public void handleKeys() {
		
		deltax=0;
		deltay=0;
		checkArrowKeys=false;
		attemptSelection=false;
		attemptDeselection=false;
		isxHeldDown=false;
		
		checkArrowKeys();
		
		if (InputHelper.isKeyPressed(Keyboard.KEY_Z)) attemptSelection=true;
		if (InputHelper.isKeyPressed(Keyboard.KEY_X)) {
			attemptDeselection=true;
			isxHeldDown=true;
		}
		if (InputHelper.isKeyDown(Keyboard.KEY_X)) isxHeldDown=true;
	}
	
	
	public void checkArrowKeys() {
		
			
			if (InputHelper.isKeyPressed(Keyboard.KEY_LEFT)) {
				deltax=-1;
				quickMoveTimer = board.frameCount;
				checkArrowKeys=true;
			} else if (InputHelper.isKeyDown(Keyboard.KEY_LEFT) && quickMoveTimer + 10 < board.frameCount) {
				if (board.frameCount%5 == 0) {
					deltax=-1;
					checkArrowKeys=true;
				}
			}		
			
			if (InputHelper.isKeyPressed(Keyboard.KEY_RIGHT)) {
				deltax=1;
				quickMoveTimer = board.frameCount;
				checkArrowKeys=true;
			} else if (InputHelper.isKeyDown(Keyboard.KEY_RIGHT) && quickMoveTimer + 10 < board.frameCount) {
				if (board.frameCount%5 == 0) {
					deltax=1;
					checkArrowKeys=true;
				}
			}
			
			if (InputHelper.isKeyPressed(Keyboard.KEY_UP)) {
				deltay=-1;
				quickMoveTimer = board.frameCount;
				checkArrowKeys=true;
			} else if (InputHelper.isKeyDown(Keyboard.KEY_UP) && quickMoveTimer + 10 < board.frameCount) {
				if (board.frameCount%5 == 0) {
					deltay=-1;
					checkArrowKeys=true;
				}
			}
			
			if (InputHelper.isKeyPressed(Keyboard.KEY_DOWN)) {
				deltay=1;
				quickMoveTimer = board.frameCount;
				checkArrowKeys=true;
			} else if (InputHelper.isKeyDown(Keyboard.KEY_DOWN) && quickMoveTimer + 10 < board.frameCount) {
				if (board.frameCount%5 == 0) {
					deltay=1;
					checkArrowKeys=true;
				}
			}
	
		
	}
	
}
