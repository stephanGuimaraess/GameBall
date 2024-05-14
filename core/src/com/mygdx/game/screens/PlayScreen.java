package com.mygdx.game.screens;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.BallMan;
import com.mygdx.game.scenes.Hud;
import com.mygdx.game.sprites.Enemy;
import com.mygdx.game.sprites.Personagem;
import com.mygdx.game.sprites.Rock;
import com.mygdx.game.tools.B2WorldCreator;
import com.mygdx.game.tools.WorldContactListener;

public class PlayScreen implements Screen {

    private BallMan game;
    private OrthographicCamera gameCamera;
    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //box2d variaveis
    private World world;
    private Box2DDebugRenderer b2dr;
    private Personagem player;
    private TextureAtlas atlasStand;
    private TextureAtlas atlasRunning;
    private TextureAtlas atlasJumping;
    private TextureAtlas atlasFalling;
    private TextureAtlas enemyWalking;
    private B2WorldCreator creator;
    private Rock rock;




    public PlayScreen(BallMan game){

        atlasStand = new TextureAtlas("Stand/stand.pack");
        atlasRunning = new TextureAtlas("running/running.pack");
        atlasJumping = new TextureAtlas("jumping/jumping.pack");
        atlasFalling = new TextureAtlas("falling/falling.pack");
        enemyWalking = new TextureAtlas("enemywalking/enemywalking.pack");
        this.game = game;

        //cria camera usada para seguir personagem
        gameCamera = new OrthographicCamera();
        //cria o tamanho da tela
        gamePort = new FitViewport((float) BallMan.V_WIDTH / BallMan.PPM, (float) BallMan.V_HEIGHT / BallMan.PPM, gameCamera);
        //cria o game para score e vidas...
        hud = new Hud(game.batch);

        //pega o mapa do tiled
        mapLoader = new TmxMapLoader();
        //carrega o mapa
        map = mapLoader.load("teste.tmx");
        //renderiza o mapa
        renderer = new OrthogonalTiledMapRenderer(map, (float) 1 / BallMan.PPM);

        //ajusta a camera dentro do mapa
        gameCamera.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        //cria o mundo
        world = new World(new Vector2(0,-10), true);

        //cria a box de debug
        b2dr = new Box2DDebugRenderer();

        creator = new B2WorldCreator(this);

        //cria personagem no jogo
        player = new Personagem(this);

        rock = new Rock(this);



        world.setContactListener(new WorldContactListener());


    }



    public TextureAtlas getAtlasStand() {
        return atlasStand;
    }

    public TextureAtlas getAtlasRunning() {
        return atlasRunning;
    }

    public TextureAtlas getAtlasJumping() {
        return atlasJumping;
    }

    public TextureAtlas getAtlasFalling() {
        return atlasFalling;
    }

    public TextureAtlas getEnemyWalking() {
        return enemyWalking;
    }

    @Override
    public void show() {

    }

    public void handleInput(float dt){
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            player.b2body.applyLinearImpulse(new Vector2(0,4f), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2){
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= - 2){
            player.b2body.applyLinearImpulse(new Vector2(- 0.1f, 0), player.b2body.getWorldCenter(),true);
        }
    }

    public void update(float dt){
        handleInput(dt);
        world.step(1/60f, 6,2);

        player.update(dt);
        rock.update(dt);
        for(Enemy enemy : creator.getGoombas()){
            enemy.update(dt);
            if(enemy.getX() < player.getX() + (float) 748 / BallMan.PPM){
                enemy.b2body.setActive(true);
            }
        }

        // camera acompanhando player
        gameCamera.position.x = player.b2body.getPosition().x;


        gameCamera.update();
        renderer.setView(gameCamera);
    }

    @Override
    public void render(float delta) {
        update(delta);
        ScreenUtils.clear(0, 0, 0, 0);

        //renderiza o mapa do jogo
        renderer.render();

        // renderizar as boxes de debug do game
        b2dr.render(world,gameCamera.combined);


        //desenha o personagem
        game.batch.setProjectionMatrix(gameCamera.combined);
        game.batch.begin();
        player.draw(game.batch);
        rock.draw(game.batch);
        for(Enemy enemy : creator.getGoombas()){
            enemy.draw(game.batch);
        }
        game.batch.end();


        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {

        // atualiza a tela
        gamePort.update(width, height, true);

    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();


    }
    public Hud getHud(){ return hud; }
}
