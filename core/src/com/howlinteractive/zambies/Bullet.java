package com.howlinteractive.zambies;

import com.badlogic.gdx.graphics.Texture;

public abstract class Bullet extends Physical {

	@Override
	Type getType() { return Type.FRIENDLY; }
	
	static int WIDTH, HEIGHT;
	
	Bullet(int x, int y, int xVel, int yVel, Texture img, int damage) {
		super(x, y, WIDTH, HEIGHT, img, 100, damage, 1);
		this.xVel = xVel;
		this.yVel = yVel;
	}
	
	Bullet(int x, int y, int xVel, int yVel, int damage) {
		super(x, y, WIDTH, HEIGHT, Game.bulletImg, 100, damage, 1);
		this.xVel = xVel;
		this.yVel = yVel;
	}
	
	@Override
	void handleCollisions(int dir) {
		super.handleCollisions(dir);
		if(collidingWith(Type.ENEMY)) {
			if(!collision(Type.ENEMY).damagedThisCycle) {
				collision(Type.ENEMY).health -= damage;
				collision(Type.ENEMY).damagedThisCycle = true;
			}
			isAlive = false;
		}
		if(collidingWith(Type.WALL)) {
			isAlive = false;
		}
	}
}