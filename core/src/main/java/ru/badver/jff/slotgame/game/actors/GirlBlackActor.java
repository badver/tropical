package ru.badver.jff.slotgame.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.badver.jff.slotgame.game.assets.Assets;

/**
 * Created by user on 03.03.14.
 */
public class GirlBlackActor extends Actor {
    private float stateTime;
    private float angle;
    private TextureRegion reg;

    public GirlBlackActor() {
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
