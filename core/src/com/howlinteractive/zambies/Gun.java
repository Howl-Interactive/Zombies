package com.howlinteractive.zambies;

public abstract class Gun {

	enum Type { GUN1, GUN2 }
	Type type;
	int damage;
	int speed;
	int fireRate;
	int cooldown;
	int timer;
	int timeLimit;
	
	Gun(Type type, int timeLimit) {
		this.type = type;
		this.timeLimit = timeLimit;
		timer = timeLimit;
		damage = 34;
		speed = 10;
	}
		
	static Gun create(Type type) {
		switch(type) {
		case GUN1:
			return new Gun1(type);
		case GUN2:
			return new Gun2(type);
		default:
			return null;
		}
	}

	int getXVel(Physical origin, Physical target) {
		return (int)(speed * (target.x - origin.x) / Physical.distance(origin, target));
	}
	
	int getYVel(Physical origin, Physical target) {
		return (int)(speed * (target.y - origin.y) / Physical.distance(origin, target));
	}
	
	void shoot(Physical origin, Physical target) {
		if(cooldown <= 0) {
			cooldown = fireRate;
			fire(origin, target);
		}
	}

	abstract void fire(Physical origin, Physical target);
	
	void update() {
		if(cooldown != 0) { cooldown--; }
		if(timer > 0) { timer--; }
		else if(timer == 0) { Game.level.p.gun = Gun.create(Gun.Type.GUN1); }
	}
	
	private static class Gun1 extends Gun {

		static final int TIME_LIMIT = -1;
		static final int SPEED = 10;
		static final int FIRE_RATE = 50;

		Gun1(Type type) {
			super(type, TIME_LIMIT);
			speed = SPEED * Game.aScale;
			fireRate = FIRE_RATE;
		}

		@Override
		void fire(Physical origin, Physical target) {
			Game.level.objs.add(new Bullet(origin.x, origin.y, getXVel(origin, target), getYVel(origin, target), damage));
		}
		
		private class Bullet extends com.howlinteractive.zambies.Bullet {

			Bullet(int x, int y, int xVel, int yVel, int damage) {
				super(x, y, xVel, yVel, damage);
			}			
		}
	}
	
	private static class Gun2 extends Gun {

		static final int TIME_LIMIT = 500;
		final int SPEED = 20;
		final int FIRE_RATE = 10;

		Gun2(Type type) {
			super(type, TIME_LIMIT);
			speed = SPEED * Game.aScale;
			fireRate = FIRE_RATE;
		}

		@Override
		void fire(Physical origin, Physical target) {
			Game.level.objs.add(new Bullet(origin.x, origin.y, getXVel(origin, target), getYVel(origin, target), damage));
		}
		
		private class Bullet extends com.howlinteractive.zambies.Bullet {

			Bullet(int x, int y, int xVel, int yVel, int damage) {
				super(x, y, xVel, yVel, damage);
			}
			
		}
	}
}