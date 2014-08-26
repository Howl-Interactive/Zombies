package com.howlinteractive.zambies;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class Physical {

	enum Type { NONE, PLAYER, WALL, ENEMY, ITEM, FRIENDLY }
	abstract Type getType();
	ArrayList<Physical> collisions;
	boolean collidingWith(Type type) {
		for(Physical obj : collisions) {
			if(obj.getType() == type) {
				return true;
			}
		}
		return false;
	}
	int indexOfCollision(Type type) {
		for(int i = 0; i < collisions.size(); i++) {
			if(collisions.get(i).getType() == type) {
				return i;
			}
		}
		return -1;
	}
	Physical collision(Type type) {
		return collisions.get(indexOfCollision(type));
	}
	
	int x;
	int y;
	int w;
	int h;
	
	float xVel;
	float yVel;
	float speed = 1;
	
	Texture img;
	TextureRegion region;

	float rotation = 0;
	
	int health = 100;
	int damage = 0;
	boolean isAlive = true;
	boolean damagedThisCycle = false;

	Physical(int x, int y, int w, int h, Texture img) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.img = img;
		region = new TextureRegion(img);
	}
	
	Physical(int x, int y, int w, int h, Texture img, int health) {
		this(x, y, w, h, img);
		this.health = health;
	}
	
	Physical(int x, int y, int w, int h, Texture img, int health, int damage, float speed) {
		this(x, y, w, h, img, health);
		this.damage = damage;
		this.speed = speed;
	}
	
	void takeDamage(int damage) {
		if(!damagedThisCycle) {
			health -= damage;
			damagedThisCycle = true;
		}
	}
	
	static double distance(Physical from, Physical to) {
		return Math.hypot(to.x - from.x, to.y - from.y);
	}
	
	boolean isClicked() {
		return Game.getXInput() > x - w / 2 && Game.getXInput() < x - w / 2 + w && Game.getYInput() > y - h / 2 && Game.getYInput() < y - h / 2 + h;
	}
	
	boolean isCollidingWith(Physical obj) {
		return x - w / 2 + w > obj.x - obj.w / 2 && x - w / 2 < obj.x - obj.w / 2 + obj.w && y - h / 2 + h > obj.y - obj.h / 2 && y - h / 2 < obj.y - obj.h / 2 + obj.h;
	}

	boolean isColliding() {
		collisions = new ArrayList<Physical>();
		for(int i = 0; i < Game.level.objs.size(); i++) {
			if(this != Game.level.objs.get(i) && isCollidingWith(Game.level.objs.get(i))) {
				collisions.add(Game.level.objs.get(i));
			}
		}
		return collisions.size() != 0;
	}
	
	void handleCollisions(int dir) {
		if(collidingWith(Type.WALL)) {
			if(dir == 0) {
				x -= Math.signum(xVel);
				xVel = 0;
			}
			else if(dir == 1) {
				y -= Math.signum(yVel);
				yVel = 0;
			}
		}
	}
	
	void update() {
		if(isColliding()) {
			handleCollisions(-1);
		}
		for(int i = 0; i < Math.abs(Math.round(xVel)); i++) {
			x += Math.signum(xVel);
			if(isColliding()) {
				handleCollisions(0);
			}
		}
		for(int i = 0; i < Math.abs(Math.round(yVel)); i++) {
			y += Math.signum(yVel);
			if(isColliding()) {
				handleCollisions(1);
			}
		}
	}
	
	void onDeath() { }
	
	void draw() {
		Game.sB.draw(region, x - w / 2, y - h / 2, w / 2, h / 2, w, h, 1, 1, rotation);
	}
}