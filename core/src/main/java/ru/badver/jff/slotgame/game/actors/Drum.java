package ru.badver.jff.slotgame.game.actors;

import com.badlogic.gdx.scenes.scene2d.Group;
import ru.badver.jff.slotgame.util.Constants;
import ru.badver.jff.slotgame.util.GameState;
import ru.badver.jff.slotgame.util.States;

public class Drum extends Group {
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

        // TODO change states of reels instead of game

        if (GameState.instance.getState() == States.STOPPING) {

            GameState.instance.setState(States.DEFAULT);
        }
    }
}