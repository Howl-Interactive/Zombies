package com.howlinteractive.zambies;

import com.badlogic.gdx.graphics.Texture;

public class Player extends Physical {
	
	@Override
	Type getType() { return Type.PLAYER; }

	static int WIDTH, HEIGHT;

	static final int HEALTH = 100;
	static final int INVINCIBILITY_TIME = 30;
	int invincibility;

	final static int MOVE_PRECISION = 5 * Game.aScale;
	final static float SPEED = 5 * Game.aScale;
	int xDest = -1, yDest = -1;
	
	Gun gun = Gun.create(Gun.Type.GUN1);
	
	int braiiins = 0;
	
	Player(int x, int y, Texture img) {
		super(x, y, WIDTH, HEIGHT, img, HEALTH, 0, SPEED);
	}
	
	Player(int x, int y) {
		this(x, y, Game.playerImg);
	}
	
	@Override
	void update() {
		gun.update();
		if(invincibility > 0) {
			invincibility--;
		}
		double length = distanceLeft();
		if(length > MOVE_PRECISION) {
			xVel = (int)(speed * (xDest - x) / length);
			yVel = (int)(speed * (yDest - y) / length);
		}
		else {
			xVel = 0;
			yVel = 0;
			xDest = -1;
			yDest = -1;
		}
		if(isColliding()) {
			handleCollisions(-1);
		}
		for(int i = 0; i < Math.abs(Math.round(xVel)); i++) {
			x += Math.signum(xVel);
			if(isColliding()) {
				handleCollisions(0);
			}
			if(distanceLeft() <= MOVE_PRECISION) {
				break;
			}
		}
		for(int i = 0; i < Math.abs(Math.round(yVel)); i++) {
			y += Math.signum(yVel);
			if(isColliding()) {
				handleCollisions(1);
			}
			if(distanceLeft() <= MOVE_PRECISION) {
				break;
			}
		}
	}
	
	@Override
	void handleCollisions(int dir) {
		super.handleCollisions(dir);
		if(collidingWith(Type.ENEMY)) {
			takeDamage(collision(Type.ENEMY).damage);
		}
		if(collidingWith(Type.ITEM)) {
			((Item)collision(Type.ITEM)).pickup();
		}
	}
	
	@Override
	void takeDamage(int damage) {
		if(invincibility == 0) {
			super.takeDamage(damage);
			invincibility = INVINCIBILITY_TIME;
		}
	}
	
	void shoot(Physical target) {
		gun.shoot(this, target);
	}
	
	double distanceLeft() {
		return xDest == -1 ? -1 : Math.hypot(xDest - x, yDest - y);
	}
	
	void scrollCheck() {
		float temp = yVel;
		yVel = -Game.level.yScroll;
		for(int i = 0; i < Math.abs(yVel); i++) {
			y += Math.signum(yVel);
			if(isColliding()) {
				handleCollisions(1);
			}
		}
		yVel = temp;
	}
}