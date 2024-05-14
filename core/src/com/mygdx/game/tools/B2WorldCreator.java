package com.mygdx.game.tools;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.BallMan;
import com.mygdx.game.screens.PlayScreen;
import com.mygdx.game.sprites.Goomba;
import com.mygdx.game.sprites.tileObjects.Floor;
import com.mygdx.game.sprites.Rock;
import com.mygdx.game.sprites.tileObjects.Trees;

public class B2WorldCreator {

    private Array<Goomba> goombas;



    public B2WorldCreator(PlayScreen playScreen){
        World world = playScreen.getWorld();
        TiledMap map = playScreen.getMap();
        //cria o corpo
        BodyDef bDef = new BodyDef();
        //cria formato do corpo
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;


        //busca o objeto no tiled pela posição
        for(RectangleMapObject mapObject : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            //cria o objeto interno
            Rectangle rect = mapObject.getRectangle();
           new Floor(playScreen,rect);
        }

        /* for(RectangleMapObject mapObject : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            //cria o objeto interno
            Rectangle rect = mapObject.getRectangle();

            new Rock(playScreen,rect);
        } */

        for(RectangleMapObject mapObject : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)){
            //cria o objeto interno
            Rectangle rect = mapObject.getRectangle();
            fDef.filter.categoryBits = BallMan.OBJECT_BIT;

            new Trees(playScreen,rect);
        }
        //create enemies

        goombas = new Array<>();
        for(RectangleMapObject mapObject : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = mapObject.getRectangle();
            goombas.add( new Goomba(playScreen,rect.getX() / BallMan.PPM,rect.getY() / BallMan.PPM));

        }

    }

    public Array<Goomba> getGoombas() {
        return goombas;
    }
}
