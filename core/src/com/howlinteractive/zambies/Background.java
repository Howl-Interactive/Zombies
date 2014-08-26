package com.howlinteractive.zambies;

public class Background extends Physical {

	@Override
	Type getType() { return Type.NONE; }

    Background() {
    	super(Game.width / 2, Game.height, Game.width, Game.height, Game.backImg);
    }
    
    @Override
    void draw() {
		super.draw();
    	y -= Game.height;
    	super.draw();
    	y += Game.height;
    }
    
    @Override
    void update() {
    	if(y <= Game.height / 2) {
    		y += Game.height;
    	}
    	else if(y >= 3 * Game.height / 2) {
    		y -= Game.height;
    	}
    }
}