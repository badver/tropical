package ru.badver.jff.slotgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
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

        // reels
        Reel reel_1 = new Reel(Constants.SYMBOL_COLUMN[0], 95, 1f);
        reel_1.setName("REEL 1");
        Reel reel_2 = new Reel(Constants.SYMBOL_COLUMN[1], 95, 2f);
        reel_2.setName("REEL 2");
        Reel reel_3 = new Reel(Constants.SYMBOL_COLUMN[2], 95, 3f);
        reel_3.setName("REEL 3");
        Reel reel_4 = new Reel(Constants.SYMBOL_COLUMN[3], 95, 4f);
        reel_4.setName("REEL 4");
        Reel reel_5 = new Reel(Constants.SYMBOL_COLUMN[4], 95, 5f);
        reel_5.setName("REEL 5");

        stage.clear();

        // add layers
        stage.addActor(backgroundImage); // add background

        // add reels:
        stage.addActor(reel_1);
        stage.addActor(reel_2);
        stage.addActor(reel_3);
        stage.addActor(reel_4);
        stage.addActor(reel_5);

        // add frame on top to cover all
        stage.addActor(gameFrame);
    }

    private class Reel extends Group {
        Rectangle scissors;
        Rectangle clipBounds;
        private float moveSpeed = 20;
        private float height = 588; // 588
        private float clipHeight = 500;

        public Reel(float posX, float posY) {
            this(posX, posY, 20f);
        }

        public Reel(float posX, float posY, float moveSpeed) {
            super();
            this.moveSpeed = moveSpeed;

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
                newActor.setName("ACTOR");
                this.addActor(newActor);
            }

            // set position and size of reel
            this.setPosition(posX, posY);
            this.setSize(Constants.SYMBOL_SIZE, height);

            //Create a scissor rectangle that covers my Actor.
            scissors = new Rectangle();
            clipBounds = new Rectangle(getX(), getY(), getWidth(), clipHeight);
        }

        @Override
        public void act(float delta) {
            super.act(delta);

            //            // do nothing if not rolling
            //            if (GameState.instance.getState() == States.GAME) {
            //                return;
            //            }

            // if rolling:
            // else set new positions
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

            // prepare stop
            if (GameState.instance.getState() == States.STOPPING) {
                // set name for last element
                actors[index].setName("LAST");
                // TODO !!!!!!!!!!
//                System.out.println(actors[index]+" name: "+actors[index].getName());
            }

            System.out.println("Reel: "+this.getName()+", Index: "+index+" Name: "+actors[index]);

            // add move to actors
            for (int i = 0, n = this.getChildren().size; i < n; i++) {
                // if out of reel box move actor to top
                if (actors[i].getTop() < 0) {

                    // if LAST stop animation
                    if (GameState.instance.getState() == States.STOPPING && actors[i].getName().equals("LAST")) {
                        System.out.println("LAST !!!!!");
                        // if last stop rolling
                        actors[i].setName("ACTOR");
                        GameState.instance.setState(States.GAME);
                    }
                    actors[i].setY(top);
                }

                if (GameState.instance.getState() == States.ROLLING) {
                    // set moving
                    actors[i].addAction(Actions.moveBy(0, -1 * moveSpeed, 1f));
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
}
