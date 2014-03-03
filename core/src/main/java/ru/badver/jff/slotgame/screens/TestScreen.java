package ru.badver.jff.slotgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.badver.jff.slotgame.game.Assets;
import ru.badver.jff.slotgame.game.GameController;
import ru.badver.jff.slotgame.util.Constants;
import ru.badver.jff.slotgame.util.GameState;
import ru.badver.jff.slotgame.util.States;

/**
 * Created by user on 03.03.14.
 */
public class TestScreen extends AbstractGameScreen {
    private static final String TAG = "TEST SCREEN ";
    private final GameController inputProcessor;

    public TestScreen(DirectedGame game) {
        super(game);
        inputProcessor = new GameController();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return null;
    }

    @Override
    public void render(float deltaTime) {
        // clear screen
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(deltaTime);
        stage.draw();

    }

    @Override
    public void show() {
        Gdx.app.debug(TAG, "show");
        Gdx.input.setCatchBackKey(true);

        buildStage();

        GameState.instance.setState(States.GAME);
    }

    private void buildStage() {
        LoadBackground backgroundImage = new LoadBackground();
        GirlBlack girlBlack = new GirlBlack();
        girlBlack.setOrigin(girlBlack.getWidth() / 2, girlBlack.getHeight() / 2);
        girlBlack.setPosition(Constants.VIEWPORT_WIDTH/2,Constants.VIEWPORT_HEIGHT/2);

        stage.clear();

        // add layers
        stage.addActor(backgroundImage);
        stage.addActor(girlBlack);

    }

    @Override
    public void hide() {
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

    private class LoadBackground extends Actor {
        private TextureRegion loadBg;

        public LoadBackground() {
            super();
            loadBg = new TextureRegion(new TextureAtlas(Gdx.files.internal(Constants.LOADSCREEN)).findRegion(
                    "loadscreen"));
            setSize(loadBg.getRegionWidth(), loadBg.getRegionHeight());
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            super.draw(batch, parentAlpha);
            batch.draw(loadBg, 0, 0);
        }
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

            reg = Assets.instance.girlBlack.animGirlBlack.getKeyFrame(stateTime, true);

            batch.draw(reg, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
                    getScaleY(), getRotation());
        }

        @Override
        public void act(float delta) {
            super.act(delta);
            stateTime += delta;
            angle = MathUtils.clamp(++angle, 0f, 360f);
            if (angle==360f) {
                angle = 0;
            }
            setRotation(angle);

        }
    }
}
