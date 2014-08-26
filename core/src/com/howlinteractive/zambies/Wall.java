package com.howlinteractive.zambies;

import com.badlogic.gdx.graphics.Texture;

public class Wall extends Physical {

	@Override
	Type getType() { return Type.WALL; }
	
	static int WIDTH, HEIGHT;

	Wall(int x, int y, Texture img) {
		super(x, y, WIDTH, HEIGHT, img);
	}

	Wall(int x, int y) {
		super(x, y, WIDTH, HEIGHT, Game.wallImg);
	}
	
	Wall(int x, int y, int w, int h) {
		super(x + w / 2, y + h / 2, w, h, Game.wallImg);
	}
	
	@Override
	void draw() {
		for(int i = 0; i < Math.ceil(w / (float)Game.wallImg.getWidth()); i++) {
			for(int j = 0; j < Math.ceil(h / (float)Game.wallImg.getHeight()); j++) {
				Game.sB.draw(img, x - w / 2 + i * Game.wallImg.getWidth(), y - h / 2 + j * Game.wallImg.getHeight(), Game.wallImg.getWidth(), Game.wallImg.getHeight());
			}
		}
	}
}