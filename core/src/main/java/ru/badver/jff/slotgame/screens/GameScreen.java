package ru.badver.jff.slotgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.badver.jff.slotgame.game.Assets;
import ru.badver.jff.slotgame.game.GameController;
import ru.badver.jff.slotgame.util.AudioManager;
import ru.badver.jff.slotgame.util.Constants;

public class GameScreen extends AbstractGameScreen {

    private static final String TAG = "GAME SCREEN ";
    private InputProcessor inputProcessor;

    private boolean paused;
    private Stage stage;
    private OrthographicCamera camera;

    public GameScreen(DirectedGame game) {
        super(game);
        inputProcessor = new GameController();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return inputProcessor;
    }

    @Override
    public void render(float deltaTime) {

        // Do not update game world when paused.
        if (!paused) {
            // Update game world by the time that has passed
            // since last rendered frame.
            //            worldController.update(deltaTime);
        }

        // clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(deltaTime);
        stage.draw();

        // Render game world to screen
        //        worldRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
        //        camera.viewportHeight = height; //set the viewport
        camera.viewportWidth = width;

        if (Constants.VIEWPORT_WIDTH / camera.viewportWidth < Constants.VIEWPORT_HEIGHT / camera.viewportHeight) {
            //set the right zoom direct
            camera.zoom = Constants.VIEWPORT_HEIGHT / camera.viewportHeight;
        } else {
            //set the right zoom direct
            camera.zoom = Constants.VIEWPORT_WIDTH / camera.viewportWidth;
        }
        camera.update();
    }

    @Override
    public void show() {
        Gdx.app.debug(TAG, "show");
        //        worldController = new WorldController(game);
        //        worldRenderer = new WorldRenderer(worldController);
        Gdx.input.setCatchBackKey(true);

        stage = new Stage(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT); // set virtual space
        camera = new OrthographicCamera(); // set camera
        camera.position.set(stage.getWidth() / 2, stage.getHeight() / 2, 0); // center camera
        stage.setCamera(camera); // assign camera to stage

        buildStage();

        // background music on Load Screen
        Music loadMusic = Assets.instance.music.song01;
        AudioManager.instance.play(loadMusic);
    }

    @Override
    public void hide() {
        //        worldController.dispose();
        //        worldRenderer.dispose();
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        super.resume();
        Gdx.app.debug(TAG, "resume");
        // Only called on Android!
        paused = false;
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private void buildStage() {
        // Layers
        //        LoadBarFill loadBarFill = new LoadBarFill(loadBarImage.getWidth() - 30);
        //        loadBarFill.setPosition(loadBarImage.getX() + 15, loadBarImage.getY() + 10);
        GirlBlack girlBlack = new GirlBlack();

        stage.clear();

        // add layers
        stage.addActor(girlBlack);
    }

    private class GirlBlack extends Actor {
        private float stateTime;

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);

            TextureRegion reg = null;

            reg = Assets.instance.girlBlack.animGirlBlack.getKeyFrame(stateTime, true);

            batch.draw(reg, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
                    getScaleY(), getRotation(), true);
        }

        @Override
        public void act(float delta) {
            super.act(delta);
            stateTime += delta;
        }
    }
}
