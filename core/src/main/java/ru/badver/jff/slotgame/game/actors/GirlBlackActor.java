package ru.badver.jff.slotgame.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.badver.jff.slotgame.game.assets.Assets;
import ru.badver.jff.slotgame.util.Constants;

public class GirlBlackActor extends Actor {
    private float         stateTime;
    private TextureRegion reg;
    private boolean isAnimated = false;

    public GirlBlackActor() {
        super();
        reg = Assets.instance.girlBlack.girlBlack;
        setSize(Constants.SYMBOL_WIDTH, Constants.SYMBOL_WIDTH);
        setOrigin(reg.getRegionWidth() / 2, reg.getRegionHeight() / 2);
    }

    public boolean isAnimated() {
        return isAnimated;
    }

    public void setAnimated(boolean isAnimated) {
        this.isAnimated = isAnimated;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (!isAnimated) {
            reg = Assets.instance.girlBlack.girlBlack;
        } else {
            reg = Assets.instance.girlBlack.animGirlBlack.getKeyFrame(stateTime, true);
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
