package ru.badver.jff.slotgame.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import ru.badver.jff.slotgame.game.assets.Assets;
import ru.badver.jff.slotgame.util.Constants;

public class SeaDayActor extends Actor {
    private float         stateTime;
    private TextureRegion reg;

    public SeaDayActor() {
        super();
        reg = Assets.instance.sea_day.image;
        setSize(Constants.VIEWPORT_WIDTH, 100);
        setOrigin(reg.getRegionWidth() / 2, reg.getRegionHeight() / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        reg = Assets.instance.sea_day.animation.getKeyFrame(stateTime, true);

        batch.draw(reg, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
                getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
    }
}
