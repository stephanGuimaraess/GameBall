package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BallMan;
import com.mygdx.game.screens.PlayScreen;
import sun.security.krb5.internal.crypto.Des;

public class Goomba extends Enemy{

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureAtlas.AtlasRegion> enemyWalkImages;

    private TextureRegion enemywalk;
    private boolean runningRight;

    private boolean setToDestroy;
    private boolean destroyed;

    public Goomba(PlayScreen playScreen, float x, float y) {
        super(playScreen, x, y);

        enemyWalkImages = new Array<TextureAtlas.AtlasRegion>();
        enemyWalkImages.add(playScreen.getEnemyWalking().findRegion("walk_0_resized"));
        enemyWalkImages.add(playScreen.getEnemyWalking().findRegion("walk_1_resized"));
        enemyWalkImages.add(playScreen.getEnemyWalking().findRegion("walk_2_resized"));
        enemyWalkImages.add(playScreen.getEnemyWalking().findRegion("walk_3_resized"));
        enemyWalkImages.add(playScreen.getEnemyWalking().findRegion("walk_4_resized"));
        enemyWalkImages.add(playScreen.getEnemyWalking().findRegion("walk_5_resized"));
        enemyWalkImages.add(playScreen.getEnemyWalking().findRegion("walk_6_resized"));
        enemyWalkImages.add(playScreen.getEnemyWalking().findRegion("walk_7_resized"));


        stateTime = 0;
        Array<TextureRegion> frames = new Array<TextureRegion>();

        for(TextureAtlas.AtlasRegion region : enemyWalkImages){
            frames.add(new TextureRegion(region.getTexture(),1,1,512,512));
        }
        walkAnimation = new Animation<>(0.1f, frames);
        frames.clear();

        //enemywalk = new TextureRegion(enemyWalkImages.first().getTexture(),1,1,512,512);
        //setRegion(enemywalk);
        setBounds(1,1 , (float) 210 / BallMan.PPM, (float) 210 / BallMan.PPM);

        setToDestroy =false;
        destroyed = false;
        runningRight = true;
    }

    public void update(float dt){
        stateTime += dt;

        if(setToDestroy && !destroyed){
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(screen.getAtlasRunning().findRegion("roll_0_resized"),1,1,512,512);
            stateTime =0;
        }else if (!destroyed){
            b2body.setLinearVelocity(velocity);
            setPosition(b2body.getPosition().x - getWidth() / 2 , b2body.getPosition().y - getWidth() / 3.4f); // / 3.4f
            setRegion(getFrameEnemy(dt));
        }

    }

    public TextureRegion getFrameEnemy(float dt) {
        TextureRegion region;
        region = walkAnimation.getKeyFrame(stateTime, true);
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        return region;
    }
    @Override
    protected void defineEnemy() {
        //cria o corpo
        BodyDef bDef = new BodyDef();
        //posição dele no mapa
        bDef.position.set(getX(), getY());

        //define se vai ser dinamico com os objetos
        bDef.type = BodyDef.BodyType.DynamicBody;
        //cria o corpo no mundo
        b2body = world.createBody(bDef);
        //define o formato do corpo
        FixtureDef fDef = new FixtureDef();
        //cria o formato para debug
        CircleShape shape = new CircleShape();
        //set tamanho do formato de debug
        shape.setRadius((float) 30 / BallMan.PPM);

        fDef.filter.categoryBits = BallMan.ENEMY_BIT;
        fDef.filter.maskBits = BallMan.GROUND_BIT | BallMan.ROCK_BIT | BallMan.FLOOR_BIT | BallMan.ENEMY_BIT | BallMan.OBJECT_BIT | BallMan.BALLMAN_BIT ;

        fDef.shape = shape;
        //o corpo leva o formato para debug
        b2body.createFixture(fDef).setUserData(this);

        PolygonShape headEnemy = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-20, 30).scl((float) 1 / BallMan.PPM);
        vertice[1] = new Vector2(20, 30).scl((float) 1 / BallMan.PPM);
        vertice[2] = new Vector2(-15, 20).scl((float) 1 / BallMan.PPM);
        vertice[3] = new Vector2(15, 20).scl((float) 1 / BallMan.PPM);

        headEnemy.set(vertice);

        fDef.shape =headEnemy;
        fDef.restitution = 0.5f;
        fDef.filter.categoryBits = BallMan.ENEMY_HEAD_BIT;

        b2body.createFixture(fDef).setUserData(this);
    }

    @Override
    public void hitOnhead() {
        setToDestroy = true;
    }

    public void draw(Batch batch){
        if(!destroyed || stateTime < 1){
            super.draw(batch);
        }

    }
}
