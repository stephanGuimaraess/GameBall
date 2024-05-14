package com.mygdx.game.sprites;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.BallMan;
import com.mygdx.game.screens.PlayScreen;


public abstract class Enemy extends Sprite {

    protected World world;
    protected PlayScreen screen;
    public Body b2body;
    public Vector2 velocity;



    public Enemy(PlayScreen playScreen, float x  , float y){
        this.world = playScreen.getWorld();
        this.screen = playScreen;
        setPosition(x, y);
        defineEnemy();
        velocity = new Vector2(1,0);
        b2body.setActive(false);
    }

    protected abstract void defineEnemy();

    public abstract void hitOnhead();

    public abstract void update(float dt);

    public void velocityReverse(boolean x, boolean y){
        if(x){
            velocity.x = -velocity.x;
        }
        if(y){
            velocity.y = -velocity.y;
        }
    }
}
