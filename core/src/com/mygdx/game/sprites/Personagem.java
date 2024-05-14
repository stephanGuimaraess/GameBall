package com.mygdx.game.sprites;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BallMan;
import com.mygdx.game.screens.PlayScreen;


public class Personagem extends Sprite {

    private enum State {RUNNING, JUMPING, FALLING, STANDING}

    ;
    public State currentState;
    public State previousState;
    private Animation<TextureRegion> ballManRunning;
    private Animation<TextureRegion> ballManJumping;
    private Animation<TextureRegion> ballManFalling;
    private Animation<TextureRegion> ballManStanding;
    private float stateTimer;
    private boolean runningRight;
    public World world;
    public Body b2body;
    private TextureRegion personagemStand;  //textura final
    private Array<TextureAtlas.AtlasRegion> regionsStand;
    private Array<TextureAtlas.AtlasRegion> regionsRunning;
    private Array<TextureAtlas.AtlasRegion> regionsJumping;
    private Array<TextureAtlas.AtlasRegion> regionsFalling;


    public Personagem(PlayScreen screen) {

        this.world = screen.getWorld();
        definePersonagem();
        regionsStand = new Array<TextureAtlas.AtlasRegion>();
        regionsStand.add(screen.getAtlasStand().findRegion("hit_0_resized"));
        regionsStand.add(screen.getAtlasStand().findRegion("hit_1_resized"));
        regionsStand.add(screen.getAtlasStand().findRegion("hit_2_resized"));
        regionsStand.add(screen.getAtlasStand().findRegion("hit_3_resized"));
        regionsStand.add(screen.getAtlasStand().findRegion("hit_4_resized"));
        regionsStand.add(screen.getAtlasStand().findRegion("hit_5_resized"));
        regionsStand.add(screen.getAtlasStand().findRegion("hit_6_resized"));
        regionsStand.add(screen.getAtlasStand().findRegion("hit_7_resized"));
        regionsStand.add(screen.getAtlasStand().findRegion("hit_8_resized"));

        regionsRunning = new Array<TextureAtlas.AtlasRegion>();
        regionsRunning.add(screen.getAtlasRunning().findRegion("roll_0_resized"));
        regionsRunning.add(screen.getAtlasRunning().findRegion("roll_1_resized"));
        regionsRunning.add(screen.getAtlasRunning().findRegion("roll_2_resized"));
        regionsRunning.add(screen.getAtlasRunning().findRegion("roll_3_resized"));
        regionsRunning.add(screen.getAtlasRunning().findRegion("roll_4_resized"));

        regionsJumping = new Array<TextureAtlas.AtlasRegion>();
        regionsJumping.add(screen.getAtlasJumping().findRegion("jumpStart_0_resized"));
        regionsJumping.add(screen.getAtlasJumping().findRegion("jumpStart_1_resized"));

        regionsFalling = new Array<TextureAtlas.AtlasRegion>();
        regionsFalling.add(screen.getAtlasFalling().findRegion("jumpEnd_0_resized"));
        regionsFalling.add(screen.getAtlasFalling().findRegion("jumpEnd_1_resized"));
        regionsFalling.add(screen.getAtlasFalling().findRegion("jumpEnd_2_resized"));

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        for (TextureAtlas.AtlasRegion region : regionsStand) {
            frames.add(new TextureRegion(region.getTexture(),1,1,512,512));

        }
        ballManStanding = new Animation<>(0.1f, frames);
        frames.clear();

        for (TextureAtlas.AtlasRegion region : regionsRunning) {
            frames.add(new TextureRegion(region.getTexture(),1,1,512,512));

        }
        ballManRunning = new Animation<>(0.1f, frames);
        frames.clear();


        for (TextureAtlas.AtlasRegion region : regionsJumping) {
            frames.add(new TextureRegion(region.getTexture(),1,1,512,512));
        }
        ballManJumping = new Animation<>(0.1f, frames);
        frames.clear();

        for (TextureAtlas.AtlasRegion region : regionsFalling) {
            frames.add(new TextureRegion(region.getTexture(),1,1,512,512));
        }
        ballManFalling = new Animation<>(0.1f, frames);
        frames.clear();


        //personagemStand = new TextureRegion(regionsStand.first().getTexture(), 1, 1, 512, 512);
        //setRegion(personagemStand);
        setBounds(1, 1, (float) 210 / BallMan.PPM, (float) 210 / BallMan.PPM);
    }

  public void update(float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 3.4f); // 3.4f
        setRegion(getFrame(dt));
    }

    public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;
        switch(currentState){
            case JUMPING:
                region = ballManJumping.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = ballManRunning.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
                region = ballManFalling.getKeyFrame(stateTimer);
                break;
            default:
                region = ballManStanding.getKeyFrame(stateTimer,true);
                break;
        }
        if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        }else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }
        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;


        return region;
    }

    public State getState() {
        if (b2body.getLinearVelocity().y > 0) {
            return State.JUMPING;
        }else if (b2body.getLinearVelocity().y < 0) {
            return State.FALLING;
        }else if (b2body.getLinearVelocity().x != 0){
            return State.RUNNING;
        }else{
            return State.STANDING;
        }
    }



    //criando personagem no mundo
    public void definePersonagem(){
        //cria o corpo
        BodyDef bDef = new BodyDef();
        //posição dele no mapa
        bDef.position.set((float) 64 / BallMan.PPM, (float) 64 / BallMan.PPM);
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

        fDef.filter.categoryBits = BallMan.BALLMAN_BIT;
        fDef.filter.maskBits = BallMan.GROUND_BIT | BallMan.ROCK_BIT | BallMan.FLOOR_BIT | BallMan.ENEMY_BIT | BallMan.ENEMY_HEAD_BIT | BallMan.OBJECT_BIT;

        fDef.shape = shape;
        //o corpo leva o formato para debug
        b2body.createFixture(fDef);

        //colisao do pé
       /* EdgeShape foot = new EdgeShape();
        foot.set(new Vector2((float) -15 / BallMan.PPM, (float) - 30 / BallMan.PPM),new Vector2((float) 15 / BallMan.PPM, (float)  - 30 / BallMan.PPM) );
        fDef.shape = foot;
        fDef.isSensor = true; */

        b2body.createFixture(fDef).setUserData("foot");

    }
    public void draw(Batch batch){
        super.draw(batch);
    }
}
