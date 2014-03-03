package ru.badver.jff.slotgame.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.badver.jff.slotgame.game.assets.Assets;
import ru.badver.jff.slotgame.util.Constants;

/**
 * Created by user on 03.03.14.
 */
public class GirlBlackActor extends Actor {
    private float stateTime;
    private TextureRegion reg;

    public GirlBlackActor() {
        super();
        reg = Assets.instance.girlBlack.girlBlack;
        setSize(Constants.SYMBOL_SIZE,Constants.SYMBOL_SIZE);
        setOrigin(reg.getRegionWidth() / 2, reg.getRegionHeight() / 2);
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
    }
}
