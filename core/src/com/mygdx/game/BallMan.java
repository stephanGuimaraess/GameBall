package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.PlayScreen;


public class BallMan extends Game {

	//largura game
	public static final int V_WIDTH = 1280;
	//altura game
	public static final int V_HEIGHT = 720;
	//pixel por metro
	public static final int PPM = 300;

	public static final short GROUND_BIT = 1;
	public static final short BALLMAN_BIT = 2;
	public static final short ROCK_BIT = 4;
	public static final short DESTROYED_BIT = 8;

	public static final short FLOOR_BIT = 16;
	public static final short ENEMY_BIT = 32;
	public static final short OBJECT_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;

	public static final float VALORFINALY = 0.2705f;

	public SpriteBatch batch;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

}
