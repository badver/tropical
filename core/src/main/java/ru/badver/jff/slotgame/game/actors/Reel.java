package ru.badver.jff.slotgame.game.actors;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import ru.badver.jff.slotgame.util.Constants;
import ru.badver.jff.slotgame.util.GameState;
import ru.badver.jff.slotgame.util.ReelState;
import ru.badver.jff.slotgame.util.States;

public class Reel extends Group {
    private final float duration;
    private final float timeOffset;
    private final float height     = 588; // 588
    private final float clipHeight = 500;
    private final Rectangle scissors;
    private final Rectangle clipBounds;
    private       Camera    camera;
    private       ReelState state;
    private       float     time;

    public Reel(float posX, float posY) {
        this(posX, posY, 0.1f, 0);
    }

    public Reel(float posX, float posY, float duration, float timeOffset) {
        super();
        this.duration = duration;
        this.timeOffset = timeOffset;
        this.state = ReelState.STOP;

        // set position and size of reel
        this.setPosition(posX, posY);
        this.setSize(Constants.SYMBOL_WIDTH, height);

        //Create a scissor rectangle that covers the Reel
        scissors = new Rectangle();
        clipBounds = new Rectangle(getX(), getY(), getWidth(), clipHeight);
        //        clipBounds = new Rectangle(0, 0, getWidth(), clipHeight);

        addSymbols();
    }

    private void addSymbols() {
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
    }

    public Reel(float posX, float posY, float duration) {
        this(posX, posY, duration, 0);
    }

    public ReelState getState() {
        return state;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // decrease time delay
        time -= delta;

        // check Actors (move out of reel)
        Actor[] actors = this.getChildren().begin();

        // current highest Y
        float top = 0;

        // find max Top coordinate of actors (top of reel)
        for (int i = 0, n = this.getChildren().size; i < n; i++) {
            float actTop = actors[i].getTop();
            if (actTop > top) {
                top = actTop;
            }
        }

        // it corrects offset, it's a magic :) // TODO why 9 ?
        top += 9;

        // add move to actors
        for (int i = 0, n = this.getChildren().size; i < n; i++) {

            // if out of reel box move actor to top
            if (actors[i].getTop() < -Constants.SYMBOL_SHIFT) {
                actors[i].setY(top);
            }

            if (GameState.instance.getState() == States.ROLLING) {
                // set moving
                if (actors[i].getActions().size < 1) {
                    actors[i].addAction(
                            Actions.moveBy(0, -1 * Constants.SYMBOL_SHIFT, duration, Interpolation.linear));
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
                        actors[i].addAction(
                                Actions.moveBy(0, -1 * Constants.SYMBOL_SHIFT, duration, Interpolation.linear));
                    }
                } else {
                    // set stopping
                    actors[i].addAction(Actions.moveBy(0, -1 * Constants.SYMBOL_SHIFT, 1, Interpolation.bounceOut));
                    //                        GameState.instance.setState(States.DEFAULT);
                }
            }
        }
        this.getChildren().end();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // TODO how to use clipBegin/clipEnd to clip instead of ScissorStack ???

        // calculate scissors
        ScissorStack.calculateScissors(camera, batch.getTransformMatrix(), clipBounds, scissors);

        //Make sure nothing is clipped before we want it to.
        batch.flush();

        // push clipping
        ScissorStack.pushScissors(scissors);
        //        clipBegin(getX(), getY(), getWidth(), getHeight());

        // draw as usual
        super.draw(batch, parentAlpha);

        //        clipEnd();

        //Perform the actual clipping
        ScissorStack.popScissors();
    }

    @Override
    protected void setStage(Stage stage) {
        super.setStage(stage);
        Stage st = getStage();
        if (st != null) {
            camera = st.getCamera();
        }
    }
}
