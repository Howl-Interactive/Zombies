package com.howlinteractive.zambies;

import java.util.Random;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Game extends ApplicationAdapter {
	
	enum State { MENU, PLAYING, PAUSED, GAME_OVER }
	static State state;
	
	static Random rand = new Random();
	
	static SpriteBatch sB;
	
	static int width;
	static int height;
	
	static Level level;

	static Texture rect;
	static Texture playerImg;
	static Texture enemyImg;
	static Texture wallImg;
	static Texture itemImg;
	static Texture bulletImg;
	static Texture backImg;
	
	static int aScale;
	
	@Override
	public void create () {
		sB = new SpriteBatch();
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		aScale = (Gdx.app.getType() == ApplicationType.Android ? 2 : 1);
		loadTextures();
		setSizes();
		level = new Level();
		state = State.PLAYING;
	}
	
	void reset() {
		level = new Level();
	}
	
	void loadTextures() {
		playerImg = new Texture("prect.png");
		enemyImg = new Texture("zambie.png");
		wallImg = new Texture("brick.png");
		itemImg = new Texture("yrects.png");
		bulletImg = new Texture("brectxs.png");
		backImg = new Texture("back.png");
		rect = new Texture("rects.png");
	}
	
	void setSizes() {
		Player.WIDTH = playerImg.getWidth() * aScale;
		Player.HEIGHT = playerImg.getHeight() * aScale;
		Wall.WIDTH = wallImg.getWidth() * aScale;
		Wall.HEIGHT = wallImg.getHeight() * aScale;
		Zombie.WIDTH = enemyImg.getWidth() * aScale;
		Zombie.HEIGHT = enemyImg.getHeight() * aScale;
		Bullet.WIDTH = bulletImg.getWidth() * aScale;
		Bullet.HEIGHT = bulletImg.getHeight() * aScale;
		Item.WIDTH = itemImg.getWidth() * aScale;
		Item.HEIGHT = itemImg.getHeight() * aScale;
	}
	
	void setAndroidSizes() {
		setSizes();
	}

	void update() {
		switch(state) {
		case PLAYING:
			level.update();
			break;
		case GAME_OVER:
			state = State.PLAYING;
			reset();
			break;
		case MENU:
			break;
		case PAUSED:
			break;
		default:
			break;
		}
	}
	
	void draw() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sB.begin();
		level.draw();
		sB.end();
	}
	
	void manageInput() {
		level.manageInput();
	}
	
	@Override
	public void render () {
		manageInput();
		update();
		draw();
	}

	static int getXInput() {
		return Gdx.input.getX();
	}
	
	static int getYInput() {
		return Game.height - Gdx.input.getY();
	}
	
	static boolean leftButtonPressed() {
		return Gdx.input.isButtonPressed(Buttons.LEFT);
	}
	
	static void gameOver() {
		state = State.GAME_OVER;
	}
}