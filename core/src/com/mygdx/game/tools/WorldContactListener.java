package com.mygdx.game.tools;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.BallMan;
import com.mygdx.game.sprites.Enemy;
import com.mygdx.game.sprites.tileObjects.InteractTileObject;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        int cdef = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;

        if(fixtureA.getUserData() == "foot" || fixtureB.getUserData() == "foot"){
            Fixture head = fixtureA.getUserData() == "foot" ? fixtureA : fixtureB;
            Fixture object = head == fixtureA ? fixtureB : fixtureA;
            if(object.getUserData() != null && InteractTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractTileObject) object.getUserData()).onFootHit();
            }
        }

        switch(cdef) {
            case BallMan.ENEMY_HEAD_BIT | BallMan.BALLMAN_BIT:
                if (fixtureA.getFilterData().categoryBits == BallMan.ENEMY_HEAD_BIT){
                    ((Enemy)fixtureA.getUserData()).hitOnhead();
                }else if (fixtureB.getFilterData().categoryBits == BallMan.ENEMY_HEAD_BIT){
                ((Enemy)fixtureB.getUserData()).hitOnhead();
            }
                break;
            case BallMan.ENEMY_BIT | BallMan.OBJECT_BIT:
                if (fixtureA.getFilterData().categoryBits == BallMan.ENEMY_BIT){
                    ((Enemy)fixtureA.getUserData()).velocityReverse(true, false);
                }else{
                    ((Enemy)fixtureB.getUserData()).velocityReverse(true, false);
                }
                break;
            case BallMan.ENEMY_BIT:
                ((Enemy)fixtureA.getUserData()).velocityReverse(true, false);
                ((Enemy)fixtureB.getUserData()).velocityReverse(true, false);
                break;
        }
    }


    @Override
    public void endContact(Contact contact) {

    }
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
