package com.howlinteractive.zambies;

import com.badlogic.gdx.graphics.Texture;

public abstract class Item extends Physical {

	@Override
	Physical.Type getType() { return Physical.Type.ITEM; }
	
	static int WIDTH, HEIGHT;
	
	public enum Type { BRAIIINS, ITEM1, ITEM2, GUN1, GUN2 }
	
	Item(int x, int y, Texture img) {
		super(x, y, WIDTH, HEIGHT, img);
	}
	
	Item(int x, int y) {
		super(x, y, WIDTH, HEIGHT, Game.itemImg);
	}
	
	static Item create(int x, int y, Type type) {
		switch(type) {
		case BRAIIINS:
			return new Braiiins(x, y);
		case ITEM1:
			return new Item1(x, y);
		case ITEM2:
			return new Item2(x, y);
		case GUN1:
			return new GunPickup(x, y, Gun.Type.GUN1);
		case GUN2:
			return new GunPickup(x, y, Gun.Type.GUN2);
		default:
			return null;
		}
	}
	
	static Item create(int x, int y, Type type, int value) {
		switch(type) {
		case BRAIIINS:
			return new Braiiins(x, y, value);
		default:
			return create(x, y, type);
		}
	}
	
	void pickup() {
		activate();
		isAlive = false;
	}
	
	abstract void activate();
	
	@Override
	void handleCollisions(int dir) {
		super.handleCollisions(dir);
		if(collisions.contains(Physical.Type.PLAYER)) {
			activate();
			isAlive = false;
		}
	}
	
	@SuppressWarnings("unused")
	private static class ItemTemplate extends Item {
		
		ItemTemplate(int x, int y) {
			super(x, y);
		}

		@Override
		void activate() {
			
		}
	}
	
	private static class Braiiins extends Item {
		
		int amount;

		Braiiins(int x, int y, int amount) {
			super(x, y);
			this.amount = amount;
		}
		
		Braiiins(int x, int y) {
			this(x, y, 1);
		}
		
		@Override
		void activate() {
			Game.level.p.braiiins += amount;
		}
	}
	
	private static class Item1 extends Item {
		
		Item1(int x, int y) {
			super(x, y);
		}

		@Override
		void activate() {
			
		}
	}
	
	private static class Item2 extends Item {
		
		Item2(int x, int y) {
			super(x, y);
		}

		@Override
		void activate() {
			
		}
	}
	
	private static class GunPickup extends Item {
		
		Gun.Type type;
		
		GunPickup(int x, int y, Gun.Type type) {
			super(x, y);
			this.type = type;
		}

		@Override
		void activate() {
			Game.level.p.gun = Gun.create(type);
		}
	}
}