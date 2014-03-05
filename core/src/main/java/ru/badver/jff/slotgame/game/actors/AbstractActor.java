package ru.badver.jff.slotgame.game.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by user on 05.03.14.
 */
public class AbstractActor extends Actor {
    private TextureRegion image;
    private float stateTime;

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(image, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
                getScaleY(), getRotation());
    }
}
