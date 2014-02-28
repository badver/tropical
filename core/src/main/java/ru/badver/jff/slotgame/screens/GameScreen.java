package ru.badver.jff.slotgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.badver.jff.slotgame.game.Assets;
import ru.badver.jff.slotgame.game.GameController;
import ru.badver.jff.slotgame.util.Constants;
import ru.badver.jff.slotgame.util.GameState;
import ru.badver.jff.slotgame.util.States;

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
        Gdx.gl.glClearColor(0.9f, 0.9f, 0.9f, 1f);
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

        // background music
        //        Music loadMusic = Assets.instance.music.song01;
        //        AudioManager.instance.play(loadMusic);

        GameState.instance.setState(States.GAME);
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
        GirlBlack girlBlack = new GirlBlack();
        //        girlBlack.setPosition(10, 10);
        //        girlBlack.setVisible(true);

        // clear stage
        stage.clear();

        // add layers
        stage.addActor(girlBlack);
    }

    private class GirlBlack extends Actor {
        private float stateTime;
        private float angle;
        private TextureRegion reg;

        public GirlBlack() {
            super();
            reg = Assets.instance.girlBlack.girlBlack;
            setSize(reg.getRegionWidth(), reg.getRegionHeight());
            setOrigin(reg.getRegionWidth() / 2, reg.getRegionHeight() / 2);
            angle=0;
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);

            // reg = Assets.instance.girlBlack.animGirlBlack.getKeyFrame(stateTime, true);

            batch.draw(reg, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
                    getScaleY(), getRotation());
        }

        @Override
        public void act(float delta) {
            super.act(delta);
            stateTime += delta;
            angle = MathUtils.clamp(++angle, 0f, 360f);
            setRotation(angle);

        }
    }
}
