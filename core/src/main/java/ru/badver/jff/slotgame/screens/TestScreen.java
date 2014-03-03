package ru.badver.jff.slotgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.badver.jff.slotgame.game.GameController;
import ru.badver.jff.slotgame.game.actors.*;
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
        return inputProcessor;
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
        GameBackgroundActor backgroundImage = new GameBackgroundActor();
        backgroundImage.setSize(stage.getWidth(), stage.getHeight());
        GameFrameActor gameFrame = new GameFrameActor();
        gameFrame.setSize(stage.getWidth(), stage.getHeight());
        SeaDayActor seaDayActor = new SeaDayActor();

        Actor[][] actors=new Actor[5][3];

//        GirlBlackActor girlBlack = new GirlBlackActor();
//        girlBlack.setOrigin(girlBlack.getWidth() / 2, girlBlack.getHeight() / 2);
//        girlBlack.setPosition(Constants.SYMBOL_COLUMN[1], Constants.SYMBOL_LINE[1]);

//        GirlRedActor girlRed = new GirlRedActor();
//        girlRed.setOrigin(girlRed.getWidth() / 2, girlRed.getHeight() / 2);
//        girlRed.setPosition(Constants.SYMBOL_COLUMN[0], Constants.SYMBOL_LINE[1]);

        for (int i=0;i<5;i++) {
            for (int j=0;j<3;j++) {
                actors[i][j] = new GirlRedActor();
                actors[i][j].setOrigin(actors[i][j].getWidth() / 2, actors[i][j].getHeight() / 2);
                actors[i][j].setPosition(Constants.SYMBOL_COLUMN[i], Constants.SYMBOL_LINE[j]);
            }
        }



        stage.clear();

        // add layers
        stage.addActor(backgroundImage);

        stage.addActor(seaDayActor);


        for (Actor[] column : actors) {
            for (Actor line : column) {
                stage.addActor(line);
            }
        }
//        stage.addActor(girlBlack);
//        stage.addActor(girlRed);

        stage.addActor(gameFrame);
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
}
