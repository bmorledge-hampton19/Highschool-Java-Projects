package aLightintheVoid;

import java.util.Random;

import myClasses.MyImage;

public class Ship extends Tile {
	
	protected double tempMovementX;
	protected double tempMovementY;
	public int changeMovement;
	protected MyImage image[][];
	protected MyImage attackAnimation[];
	protected int imageState;
	protected int shipFacing; 
	protected int attack;
	protected double lightHullMod;
	protected double medHullMod;
	protected double heavyHullMod;
	protected int hullType;
	protected double shieldMod;
	protected int maxShields;
	protected int shields;
	protected int maxHealth;
	protected int health;
	protected int maxFuel;
	protected int fuel;
	protected int maxAmmo;
	protected int ammo;
	protected int faction;
	protected int movement;
	protected int maxRange;
	protected int minRange;
	protected boolean moving;
	protected int movementDistanceTraveled;
	protected boolean proceedToAttack;
	protected boolean attacking;
	protected MyImage[] fireImage;
	protected ShipProjectile[] fire;
	protected int[] firexAdjust;
	protected int[] fireyAdjust;
	protected int[] firexSpeed;
	protected int[] fireySpeed;
	protected int targetxpos;
	protected int targetypos;
	protected int fireNumber;
	protected boolean counterattack;
	protected boolean attackMove;
	protected boolean acted;
	static final int FIGHTER=0;
	static final int CRUISER=1;
	static final int SEEKER=2;
	static final int BATTLESHIP=3;
	Random rand = new Random();
	

	public Ship(int faction, MyImage[][] image, int xpos, int ypos, int attack, double lightHullMod, 
			double medHullMod, double heavyHullMod, int hullType, double shieldMod, int shields, int health,
			int fuel, int ammo, int movement, int maxRange, int minRange, boolean counterattack, boolean attackMove, Board board) {
		
		super (xpos, ypos, board);
		
		this.image = image;
		
		this.faction = faction;
		this.xpos = xpos;
		this.ypos = ypos;
		this.attack = attack;
		this.lightHullMod = lightHullMod;
		this.medHullMod = medHullMod;
		this.heavyHullMod = heavyHullMod;
		this.hullType = hullType;
		this.shieldMod = shieldMod;
		maxShields = shields;
		this.shields = shields;
		maxHealth = health;
		this.health = health;
		maxFuel = fuel;
		this.fuel = fuel;
		maxAmmo = ammo;
		this.ammo = ammo;
		this.movement = movement;
		this.maxRange=maxRange;
		this.minRange=minRange;
		this.counterattack=counterattack;
		this.attackMove=attackMove;
		
	}

	public boolean isAShip(){
		return true;
	}
	
	public boolean isMovePossible(int faction){
		if (this.faction==faction) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isAttackPossible(int faction){
		if (this.faction==faction) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isCounterAttackPossible(int attackerx, int attackery) {
		int attackDistance = Math.abs(attackerx-xpos)+Math.abs(attackery-ypos);
		if (counterattack && maxRange>=attackDistance && minRange<=attackDistance) return true; 
		else return false;
	}
	
	public void resetFromAttack() {
		acted=true;
		imageState=2;
		shipFacing=0;
		board.cursor.xpos=xpos;
		board.cursor.ypos=ypos;
		board.isAShipAttacking=false;
	}
	
	public void finishAttack() {
	
		if (!applyDamage()) checkForCounterattack();
		else resetFromAttack();
		fireNumber=0;
		attacking=false;
		
	}
	
	public boolean applyDamage() {
		int damage;
		if (((Ship)board.tile[targetxpos][targetypos]).shields<1) {
			if (((Ship)board.tile[targetxpos][targetypos]).hullType==0) {
				damage=(int)((double)attack*lightHullMod*((double)health/(double)maxHealth));
			} else if (((Ship)board.tile[targetxpos][targetypos]).hullType==1) {
				damage=(int)((double)attack*medHullMod*((double)health/(double)maxHealth));
			} else {
				damage=(int)((double)attack*heavyHullMod*((double)health/(double)maxHealth));
			}
			((Ship)board.tile[targetxpos][targetypos]).health-=damage;
			if (((Ship)board.tile[targetxpos][targetypos]).health<0) {
				board.shipCount[((Ship)board.tile[targetxpos][targetypos]).faction]--;
				board.tile[targetxpos][targetypos]=null;
				board.tile[targetxpos][targetypos] = new Tile(targetxpos, targetypos, board);
				return true;
			}
			return false;
		} else {
			damage=(int)((double)attack*shieldMod*((double)health/(double)maxHealth));
			((Ship)board.tile[targetxpos][targetypos]).shields-=damage;
			return false;
		}
	}
	
	private void checkForCounterattack() {
		if (board.isAShipCounterattacking) {
			board.isAShipCounterattacking=false;
			((Ship)board.tile[targetxpos][targetypos]).resetFromAttack();
			imageState=0;
			shipFacing=0;
		} else if (board.tile[targetxpos][targetypos].isCounterAttackPossible(xpos, ypos)) {
			((Ship)board.tile[targetxpos][targetypos]).initializeAttack(xpos, ypos);
			board.isAShipCounterattacking=true;
		} else {
			resetFromAttack();
		}
	}
	
	public void move() {
		
		tempMovementX=tempMovementX+(board.arrow[movementDistanceTraveled+1].xpos-board.arrow[movementDistanceTraveled].xpos)*.1;
		tempMovementY=tempMovementY+(board.arrow[movementDistanceTraveled+1].ypos-board.arrow[movementDistanceTraveled].ypos)*.1;
		changeMovement++;
		
		if (board.arrow[movementDistanceTraveled+1].xpos-board.arrow[movementDistanceTraveled].xpos==1) shipFacing=0;
		if (board.arrow[movementDistanceTraveled+1].xpos-board.arrow[movementDistanceTraveled].xpos==-1) shipFacing=2;
		if (board.arrow[movementDistanceTraveled+1].ypos-board.arrow[movementDistanceTraveled].ypos==1) shipFacing=1;
		if (board.arrow[movementDistanceTraveled+1].ypos-board.arrow[movementDistanceTraveled].ypos==-1) shipFacing=3;
		
		if (changeMovement==10) {
			movementDistanceTraveled++;
			changeMovement=0;
			if (movementDistanceTraveled==board.arrowNumber) {
				moving=false;
				board.isAShipMoving=false;
				tempMovementX=0;
				tempMovementY=0;
				movementDistanceTraveled=0;
				board.moveTile(board.arrow[0].xpos, board.arrow[0].ypos, 
							   board.arrow[board.arrowNumber].xpos, board.arrow[board.arrowNumber].ypos);
				board.arrowNumber=0;
				if (proceedToAttack) initializeAttack(board.cursor.potentialAttackx.get(board.cursor.attackTarget),
													  board.cursor.potentialAttacky.get(board.cursor.attackTarget));
				else {
					shipFacing=0;
					acted=true;
					imageState=2;
				}
				
			}
		}
		
	}
	
	protected void initializeAttack(int targetxpos, int targetypos) {
		
		this.targetxpos=targetxpos;
		this.targetypos=targetypos;
		int xdifference=targetxpos-xpos;
		int ydifference=targetypos-ypos;
		
		if (Math.abs(xdifference)-Math.abs(ydifference)>0) {
			if (xdifference>0) shipFacing=0;
			else shipFacing=2;
		} else if (Math.abs(ydifference)-Math.abs(xdifference)>0) {
			if (ydifference>0) shipFacing=1;
			else shipFacing=3;
		} else if (Math.abs(xdifference)-Math.abs(ydifference)==0) {
			if (xdifference>0 && ydifference>0) shipFacing=0;
			else if (xdifference<0 && ydifference>0) shipFacing=1;
			else if (xdifference<0 && ydifference<0) shipFacing=2;
			else if (xdifference>0 && ydifference<0) shipFacing=3;
		}
				
		attacking=true;
		board.isAShipAttacking=true;
		imageState=1;
		
	}
	
	public void createbattleship(int faction, int  xpos, int ypos) {
		
		this.faction = faction;
		this.xpos = xpos;
		this.ypos = ypos;
		attack = 50;
		lightHullMod = 1;
		medHullMod = 1;
		heavyHullMod = 1;
		shieldMod = 1;
		shields = 100;
		health = 100;
		fuel = 50;
		ammo = 8;
		maxShields = shields;
		maxHealth = health;
		maxFuel = fuel;
		maxAmmo = ammo;
		movement = 5;
		
	}
	
	public void createstealth(int faction, int  xpos, int ypos) {
		
		this.faction = faction;
		this.xpos = xpos;
		this.ypos = ypos;
		attack = 40;
		lightHullMod = 1;
		medHullMod = 1.5;
		heavyHullMod = 2;
		shieldMod = 0;
		shields = 20;
		health = 50;
		fuel = 60;
		ammo = 6;
		maxShields = shields;
		maxHealth = health;
		maxFuel = fuel;
		maxAmmo = ammo;
		movement = 8;
		
	}
	
	public void createseekers(int faction, int  xpos, int ypos) {
		
		this.faction = faction;
		this.xpos = xpos;
		this.ypos = ypos;
		attack = 40;
		lightHullMod = 2;
		medHullMod = 1.5;
		heavyHullMod = 1.5;
		shieldMod = 1;
		shields = 0;
		health = 50;
		fuel = 60;
		ammo = 6;
		maxShields = shields;
		maxHealth = health;
		maxFuel = fuel;
		maxAmmo = ammo;
		movement = 4;
				
	}
	
	public void createpulser(int faction, int  xpos, int ypos) {
		
		this.faction = faction;
		this.xpos = xpos;
		this.ypos = ypos;
		attack = 25;
		lightHullMod = 0;
		medHullMod = 0;
		heavyHullMod = 0;
		shieldMod = 1;
		shields = 0;
		health = 150;
		fuel = 60;
		ammo = 5;
		maxShields = shields;
		maxHealth = health;
		maxFuel = fuel;
		maxAmmo = ammo;
		movement = 5;
		
	}
	
	public void createvanguard(int faction, int  xpos, int ypos) {
		
		this.faction = faction;
		this.xpos = xpos;
		this.ypos = ypos;
		attack = 40;
		lightHullMod = 1;
		medHullMod = 1;
		heavyHullMod = .5;
		shieldMod = .5;
		shields = 150;
		health = 50;
		fuel = 60;
		ammo = 10;
		maxShields = shields;
		maxHealth = health;
		maxFuel = fuel;
		maxAmmo = ammo;
		movement = 5;
		
	}
	
	
}
