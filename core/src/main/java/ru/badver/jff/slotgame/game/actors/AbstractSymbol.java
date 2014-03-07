package ru.badver.jff.slotgame.game.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

// TODO finish base Actor Class
public abstract class AbstractSymbol extends Actor {
    private final TextureRegion image;
    private final Animation     animation;
    private final int           width;
    private final int           height;
    private       float         stateTime;
    private boolean isLast     = false;
    private boolean isAnimated = false;
    private boolean isLooping  = true;

    protected AbstractSymbol(TextureRegion image, Animation animation, int width, int height) {
        this.image = image;
        this.animation = animation;
        this.width = width;
        this.height = height;
    }

    public boolean isAnimated() {
        return isAnimated;
    }

    public void setAnimated(boolean isAnimated) {
        if (animation != null) {
            this.isAnimated = isAnimated;
        } else {
            isAnimated = false;
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        TextureRegion reg;
        if (isAnimated) {
            reg = animation.getKeyFrame(stateTime, isLooping);
        } else {
            reg = image;
        }

        batch.draw(reg, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
                getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (isAnimated) { stateTime += delta; } else { stateTime = 0; }
    }
}
