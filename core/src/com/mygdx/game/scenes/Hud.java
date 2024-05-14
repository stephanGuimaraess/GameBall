package com.mygdx.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.BallMan;


public class Hud  implements Disposable {
    public Stage stage;
    private Viewport viewport;
    private Integer life;


    public Hud(SpriteBatch sb) {
        life = 3;
        viewport = new FitViewport(BallMan.V_WIDTH, BallMan.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
