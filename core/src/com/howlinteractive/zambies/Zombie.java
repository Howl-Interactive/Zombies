package com.howlinteractive.zambies;

import com.badlogic.gdx.graphics.Texture;

public abstract class Zombie extends Physical {
	
	@Override
	Physical.Type getType() { return Physical.Type.ENEMY; }
	
	static int WIDTH, HEIGHT;
	
	enum Type { ZOMBIE1, ZOMBIE2 }
	
	int visionRange;
	
	enum STAT { NONE, HEALTH, SPEED }
	STAT stat;
	
	Zombie(int x, int y, Texture img, int health, int damage, float speed, int visionRange) {
		super(x, y, WIDTH, HEIGHT, img, health, damage, speed);
		this.visionRange = visionRange;
		rotation = -90;
		stat = STAT.NONE;
	}
	
	Zombie(int x, int y, int health, int damage, float speed, int visionRange) {
		this(x, y, Game.enemyImg, health, damage, speed, visionRange);
	}
	
	static Zombie create(int x, int y, Type type) {
		switch(type) {
		case ZOMBIE1:
			return new Zombie1(x, y);
		case ZOMBIE2:
			return new Zombie2(x, y);
		default:
			return null;
		}
	}
	
	@Override
	void update() {
		if(Math.abs(Math.hypot(Game.level.p.x - x, Game.level.p.y - y)) < visionRange) {
			act();
		}
		super.update();
	}
	
	abstract void act();
		
	@Override
	void handleCollisions(int dir) {
		super.handleCollisions(dir);
		if(collidingWith(Physical.Type.PLAYER)) {
			collision(Physical.Type.PLAYER).takeDamage(damage);
		}
	}
	
	private static class Zombie1 extends Zombie {
		
		static final int HEALTH = 100;
		static final int DAMAGE = 50;
		static final float SPEED = 2 * Game.aScale;
		static final int VISION_RANGE = 300 * Game.aScale;
		
		Zombie1(int x, int y) {
			super(x, y, HEALTH, DAMAGE, SPEED, VISION_RANGE);
		}
		
		@Override
		void act() {
			rotation = (float)Math.toDegrees(Math.atan2(Game.level.p.y - y, Game.level.p.x - x));
			xVel = (int)(speed * (Game.level.p.x - x) / Physical.distance(this, Game.level.p));
			yVel = (int)(speed * (Game.level.p.y - y) / Physical.distance(this, Game.level.p));
		}
	}
	
	private static class Zombie2 extends Zombie {
		
		static final int HEALTH = 100;
		static final int DAMAGE = 50;
		static final int SPEED = 4 * Game.aScale;
		static final int VISION_RANGE = 400 * Game.aScale;
		
		Zombie2(int x, int y) {
			super(x, y, HEALTH, DAMAGE, SPEED, VISION_RANGE);
		}
		
		@Override
		void act() {
			rotation = (float)Math.toDegrees(Math.atan2(Game.level.p.y - y, Game.level.p.x - x));
			xVel = (int)(speed * (Game.level.p.x - x) / Physical.distance(this, Game.level.p));
			yVel = (int)(speed * (Game.level.p.y - y) / Physical.distance(this, Game.level.p));
		}
	}
}