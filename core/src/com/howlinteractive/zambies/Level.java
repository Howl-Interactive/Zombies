package com.howlinteractive.zambies;

import java.util.ArrayList;

import com.howlinteractive.zambies.Physical.Type;

public class Level {

	ArrayList<Physical> objs;
	
	Player p;
	
	final int DEFAULT_SCROLL = -2 * Game.aScale;
	boolean scrolling;
	float yScroll;

	Level() {
		objs = new ArrayList<Physical>();
		objs.add(new Background());
		objs.add(p = new Player(Game.width / 2, 100 * Game.aScale));
		scrolling = true;
		this.yScroll = DEFAULT_SCROLL;
	}
	
	void draw() {
		for(Physical obj : objs) {
			obj.draw();
		}
	}
	
	void update() {
		createObjects();
		for(Physical obj : objs) {
			obj.update();
		}
		for(int i = objs.size() - 1; i >= 0; i--) {
			if(p.damagedThisCycle) {
				p.invincibility = Player.INVINCIBILITY_TIME;
			}
			objs.get(i).damagedThisCycle = false;
			if(objs.get(i).health <= 0) {
				objs.get(i).isAlive = false;
			}
			if(!objs.get(i).isAlive) {
				if(objs.get(i) instanceof Player) {
					Game.gameOver();
				}
				objs.remove(i);
			}
		}
		scroll();
	}

	void addPanel(int offset) {
		
		int[][] section = LevelSection.panels[Game.rand.nextInt(LevelSection.panels.length)];
		for(int i = 0; i < section.length; i++) {
			if(section[i].length != 1) {
				if(section[0][0] == LevelSection.UNIT) {
					switch(section[i][0]) {
					case LevelSection.WALL:
						objs.add(new Wall(section[i][1] * Game.aScale, section[i][2] * Game.aScale + LevelSection.HEIGHT - offset, section[i][3] * Wall.WIDTH, section[i][4] * Wall.HEIGHT));
						break;
					case LevelSection.ZOMBIE1:
						objs.add(Zombie.create(section[i][1] * Game.aScale, section[i][2] * Game.aScale + LevelSection.HEIGHT - offset, Zombie.Type.ZOMBIE1));
						break;
					case LevelSection.ZOMBIE2:
						objs.add(Zombie.create(section[i][1] * Game.aScale, section[i][2] * Game.aScale + LevelSection.HEIGHT - offset, Zombie.Type.ZOMBIE2));
						break;
					case LevelSection.GUN2:
						objs.add(Item.create(section[i][1] * Game.aScale, section[i][2] * Game.aScale + LevelSection.HEIGHT - offset, Item.Type.GUN2));
						break;
					}
				}
				else if(section[0][0] == LevelSection.GRID) {
					for(int j = 0; j < section[i].length; j++) {
						switch(section[i][j]) {
						case LevelSection.WALL:
							objs.add(new Wall(j * LevelSection.TILE_SIZE, (section.length - 1 - i) * LevelSection.TILE_SIZE + LevelSection.HEIGHT - offset, Wall.WIDTH, Wall.HEIGHT));
							break;
						case LevelSection.ZOMBIE1:
							objs.add(Zombie.create(j * LevelSection.TILE_SIZE, (section.length - 1 - i) * LevelSection.TILE_SIZE + LevelSection.HEIGHT - offset, Zombie.Type.ZOMBIE1));
							break;
						case LevelSection.ZOMBIE2:
							objs.add(Zombie.create(j * LevelSection.TILE_SIZE, (section.length - 1 - i) * LevelSection.TILE_SIZE + LevelSection.HEIGHT - offset, Zombie.Type.ZOMBIE2));
							break;
						case LevelSection.GUN2:
							objs.add(Item.create(j * LevelSection.TILE_SIZE, (section.length - 1 - i) * LevelSection.TILE_SIZE + LevelSection.HEIGHT - offset, Item.Type.GUN2));
							break;
						}
					}
				}
			}
		}
		/*
		String[] section = LevelSection.sPanels[Game.rand.nextInt(LevelSection.sPanels.length)];
		int rows = Integer.parseInt(section[0]);
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < 8; col++) {
				switch(Integer.parseInt("" + section[1].charAt(row * rows + col))) {
				case LevelSection.WALL:
					objs.add(new Wall(col * LevelSection.TILE_SIZE,  (rows - 1 - row) * LevelSection.TILE_SIZE + LevelSection.HEIGHT - offset, Wall.WIDTH, Wall.HEIGHT));
					break;
				case LevelSection.ZOMBIE1:
					objs.add(Zombie.create(col * LevelSection.TILE_SIZE, (rows - 1 - row) * LevelSection.TILE_SIZE + LevelSection.HEIGHT - offset, Zombie.Type.ZOMBIE1));
					break;
				case LevelSection.ZOMBIE2:
					objs.add(Zombie.create(col * LevelSection.TILE_SIZE, (rows - 1 - row) * LevelSection.TILE_SIZE + LevelSection.HEIGHT - offset, Zombie.Type.ZOMBIE2));
					break;
				case LevelSection.GUN2:
					objs.add(Item.create(col * LevelSection.TILE_SIZE, (rows - 1 - row) * LevelSection.TILE_SIZE + LevelSection.HEIGHT - offset, Item.Type.GUN2));
					break;
				}
			}
		}*/
	}
	
	int scrollCounter;
	void createObjects() {
		if(Math.abs(scrollCounter) >= LevelSection.HEIGHT) {
			addPanel(Math.abs(scrollCounter) - LevelSection.HEIGHT);
			scrollCounter = 0;
		}
	}
	
	void scroll() {
		if(yScroll != 0) {
			p.scrollCheck();
			for(Physical obj : objs) {
				obj.y += yScroll;
			}
			scrollCounter += yScroll;
		}
	}
	
	void manageInput() {
		if(Game.leftButtonPressed()) {
			Physical clicked = null;
			for(Physical obj : objs) {
				if(obj.isClicked()) {
					clicked = obj;
				}
			}
			if(clicked != null && clicked.getType() == Type.ENEMY) {
				p.shoot(clicked);
			}
			else {
				p.xDest = Game.getXInput();
				p.yDest = Game.getYInput();
			}
		}
	}
}
