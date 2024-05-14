package com.mygdx.game.sprites.tileObjects;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.BallMan;
import com.mygdx.game.screens.PlayScreen;

public abstract class InteractTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle rect;
    protected Body body;

    protected Fixture fixture;
    protected BodyDef bDef;

    public InteractTileObject(PlayScreen playScreen, Rectangle rect){
        this.world = playScreen.getWorld();
        this.map =playScreen.getMap();
        this.rect = rect;

         bDef = new BodyDef();
        FixtureDef fDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bDef.type = BodyDef.BodyType.StaticBody;
        //define a posição dele pega x e largura e dividindo por dois e altura e y e dividindo por 2 e dividindo por pixel por metro(para cada metro no jogo são 100 pixels)
        bDef.position.set((rect.getX() + rect.getWidth() / 2) / BallMan.PPM, (rect.getY() + rect.getHeight() / 2) / BallMan.PPM);
        //alimenta o corpo com a criação dele
        body = world.createBody(bDef);
        //set o formato do corpo para colisao e dividindo por pixel por metro(para cada metro no jogo são 100 pixels)
        shape.setAsBox(rect.getWidth() / 2 / BallMan.PPM, rect.getHeight() / 2 / BallMan.PPM);
        fDef.shape = shape;
        //cria os dados para colisao
        fixture = body.createFixture(fDef);
    }
    public abstract void onFootHit();


    public void setCategoryFilter(short filterBit) {
    Filter filter = new Filter();
    filter.categoryBits = filterBit;
    fixture.setFilterData(filter);

    }

    public TiledMapTileLayer.Cell getCell(){
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get(3);
        return tileLayer.getCell((int) (body.getPosition().x * BallMan.PPM / 32),
                (int) (body.getPosition().y * BallMan.PPM / 32));
    }

}
