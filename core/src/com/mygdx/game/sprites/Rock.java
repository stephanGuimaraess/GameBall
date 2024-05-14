package com.mygdx.game.sprites;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.BallMan;
import com.mygdx.game.screens.PlayScreen;



public class Rock extends Sprite {
    private World world;
    private Body b2body;

    private PlayScreen screen;

    private Texture texture;



    public Rock(PlayScreen playScreen){


        this.world = playScreen.getWorld();
        this.screen = playScreen;
        texture = new Texture("box/box.png");
        setBounds(getX(),getY(), (float) 32 / BallMan.PPM, (float) 32 / BallMan.PPM);
        defineRock();
    }

    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2 , b2body.getPosition().y - getWidth());
        setRegion(texture);

    }


    public void defineRock(){
        //cria o corpo
        BodyDef bDef = new BodyDef();
        //posição dele no mapa
        bDef.position.set((float) 94 / BallMan.PPM, (float) 64 / BallMan.PPM);
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

        fDef.filter.categoryBits = BallMan.ROCK_BIT;
        fDef.filter.maskBits = BallMan.GROUND_BIT | BallMan.BALLMAN_BIT | BallMan.FLOOR_BIT | BallMan.ENEMY_BIT | BallMan.ENEMY_HEAD_BIT | BallMan.OBJECT_BIT;

        fDef.shape = shape;
        //o corpo leva o formato para debug
        b2body.createFixture(fDef);


    }

    public void draw(Batch batch){
        super.draw(batch);
    }
}
