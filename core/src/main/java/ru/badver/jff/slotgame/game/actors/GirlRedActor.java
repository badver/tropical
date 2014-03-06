package ru.badver.jff.slotgame.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.badver.jff.slotgame.game.assets.Assets;
import ru.badver.jff.slotgame.util.Constants;

/**
 * Created by user on 03.03.14.
 */
public class GirlRedActor extends Actor {
    private float stateTime;
    private TextureRegion reg;
    private boolean isAnimated = false;

    public GirlRedActor() {
        super();
        reg = Assets.instance.girlRed.girlRed;
        setSize(Constants.SYMBOL_WIDTH, Constants.SYMBOL_WIDTH);
        setOrigin(reg.getRegionWidth() / 2, reg.getRegionHeight() / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (!isAnimated) {
            reg = Assets.instance.girlRed.girlRed;
        } else {
            reg = Assets.instance.girlRed.animGirlRed.getKeyFrame(stateTime, true);
        }


        batch.draw(reg, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
                getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
    }
}
