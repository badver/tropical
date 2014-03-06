package ru.badver.jff.slotgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import ru.badver.jff.slotgame.game.GameController;
import ru.badver.jff.slotgame.game.actors.GameBackgroundActor;
import ru.badver.jff.slotgame.game.actors.GameFrameActor;
import ru.badver.jff.slotgame.game.actors.GirlBlackActor;
import ru.badver.jff.slotgame.game.actors.GirlRedActor;
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
        inputProcessor = new GameController(game);
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

    private void buildStage() {
        GameBackgroundActor backgroundImage = new GameBackgroundActor();
        backgroundImage.setSize(stage.getWidth(), stage.getHeight());

        GameFrameActor gameFrame = new GameFrameActor();
        gameFrame.setSize(stage.getWidth(), stage.getHeight());

        Drum drum = new Drum();

        stage.clear();

        // add background
        stage.addActor(backgroundImage);

        // add drum (contains reel)
        stage.addActor(drum);

        // add frame on top to cover all
        stage.addActor(gameFrame);
    }

    private class Reel extends Group {
        private final float duration;
        private final float timeOffset;
        private final float height = 588; // 588
        private final float clipHeight = 500;
        private Rectangle scissors;
        private Rectangle clipBounds;
        private float time;

        public Reel(float posX, float posY) {
            this(posX, posY, 0.1f, 0);
        }

        public Reel(float posX, float posY, float duration) {
            this(posX, posY, duration, 0);
        }

        public Reel(float posX, float posY, float duration, float timeOffset) {
            super();
            this.duration = duration;
            this.timeOffset = timeOffset;

            // fill with symbols
            for (int i = 0; i < 5; i++) {
                Actor newActor;
                if (i % 2 == 0) {
                    newActor = new GirlRedActor();
                } else {
                    newActor = new GirlBlackActor();
                }
                newActor.setOrigin(newActor.getWidth() / 2, newActor.getHeight() / 2);
                newActor.setPosition(0, Constants.SYMBOL_LINE[i]);
                this.addActor(newActor);
            }

            // set position and size of reel
            this.setPosition(posX, posY);
            this.setSize(Constants.SYMBOL_WIDTH, height);

            //Create a scissor rectangle that covers my Actor.
            scissors = new Rectangle();
            clipBounds = new Rectangle(getX(), getY(), getWidth(), clipHeight);
        }

        @Override
        public void act(float delta) {
            super.act(delta);

            // do nothing if not rolling
//            if (GameState.instance.getState() == States.GAME) {
//                return;
//            }

//            if (GameState.instance.getState() != States.GAME) {
                time -= delta;
//                if (time > 0) {
//                    GameState.instance.setState(States.STOPPING);
//                }
//            }

            // if rolling:
            // set new positions
            Actor[] actors = this.getChildren().begin();

            // current highest Y
            float top = 0;
            int index = 0;

            // find max Y coord of actors (top of reel's actors)
            for (int i = 0, n = this.getChildren().size; i < n; i++) {
                float actTop = actors[i].getTop();
                if (actTop > top) {
                    top = actTop;
                    index = i;
                }
            }

            top += 9; // it corrects offset, it's a magic :)

            // add move to actors
            for (int i = 0, n = this.getChildren().size; i < n; i++) {

                // if out of reel box move actor to top
                if (actors[i].getTop() < -Constants.SYMBOL_SHIFT) {
                    actors[i].setY(top);
                }

                if (GameState.instance.getState() == States.ROLLING) {
                    // set moving
                    if (actors[i].getActions().size < 1) {
                        actors[i].addAction(Actions.moveBy(0, -1 * Constants.SYMBOL_SHIFT, duration, Interpolation.linear));
                    }
                    time = timeOffset;
                }

                if (time > 0) {
                    if (actors[i].getActions().size < 1) {
                        actors[i].addAction(Actions.moveBy(0, -1 * Constants.SYMBOL_SHIFT, duration, Interpolation.linear));
                    }
                }

                if (GameState.instance.getState() == States.STOPPING) {

                    if (time > 0) {
                        if (actors[i].getActions().size < 1) {
                            actors[i].addAction(Actions.moveBy(0, -1 * Constants.SYMBOL_SHIFT, duration, Interpolation.linear));
                        }
                    } else {
                        // set stopping
                        actors[i].addAction(Actions.moveBy(0, -1 * Constants.SYMBOL_SHIFT, 1, Interpolation.bounceOut));
//                        GameState.instance.setState(States.GAME);
                    }
                }
            }

            this.getChildren().end();
        }

        @Override
        public void draw(Batch batch, float parentAlpha) {
            // calculate scissors
            ScissorStack.calculateScissors(camera, batch.getTransformMatrix(), clipBounds, scissors);

            //Make sure nothing is clipped before we want it to.
            batch.flush();

            // push clipping
            ScissorStack.pushScissors(scissors);

            // draw as usual
            super.draw(batch, parentAlpha);

            //Perform the actual clipping
            ScissorStack.popScissors();
        }
    }

    private class Drum extends Group {
        private final float duration = 0.08f; // time to move 1 symbol

        public Drum() {
            this(0.5f, 1f, 1.5f, 2f, 2.5f);
        }

        public Drum(float reel1, float reel2, float reel3, float reel4, float reel5) {
            super();

            // reels
            Reel reel_1 = new Reel(Constants.SYMBOL_COLUMN[0], 97, duration, reel1);
            reel_1.setName("REEL 1");
            Reel reel_2 = new Reel(Constants.SYMBOL_COLUMN[1], 97, duration, reel2);
            reel_2.setName("REEL 2");
            Reel reel_3 = new Reel(Constants.SYMBOL_COLUMN[2], 97, duration, reel3);
            reel_3.setName("REEL 3");
            Reel reel_4 = new Reel(Constants.SYMBOL_COLUMN[3], 97, duration, reel4);
            reel_4.setName("REEL 4");
            Reel reel_5 = new Reel(Constants.SYMBOL_COLUMN[4], 97, duration, reel5);
            reel_5.setName("REEL 5");

            this.addActor(reel_1);
            this.addActor(reel_2);
            this.addActor(reel_3);
            this.addActor(reel_4);
            this.addActor(reel_5);
        }

        @Override
        public void act(float delta) {
            super.act(delta);

            if (GameState.instance.getState() == States.STOPPING) {

                GameState.instance.setState(States.GAME);
            }
        }
    }
}
