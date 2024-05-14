package com.mygdx.game.sprites.tileObjects;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.BallMan;
import com.mygdx.game.screens.PlayScreen;


public class Floor extends InteractTileObject {
    public Floor(PlayScreen playScreen, Rectangle rect) {
        super(playScreen, rect);

        fixture.setUserData(this);


    }

    @Override
    public void onFootHit() {

    }


}
