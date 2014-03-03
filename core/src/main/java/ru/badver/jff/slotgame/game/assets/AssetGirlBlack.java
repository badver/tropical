package ru.badver.jff.slotgame.game.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * animated black hair girl
 */
public class AssetGirlBlack {
    public final TextureRegion girlBlack;
    public final Animation animGirlBlack;

    public AssetGirlBlack(AssetManager am) {
        // picture
        TextureAtlas textureAtlas = am.get("images/girl_black_anim_0.txt", TextureAtlas.class);
        girlBlack = textureAtlas.findRegion("000");

        // Animation:
        Array<TextureAtlas.AtlasRegion> regions = textureAtlas.findRegions("000");
        TextureAtlas.AtlasRegion region = regions.first();
        for (int i = 0; i < 10; i++)
            regions.insert(0, region);

        animGirlBlack = new Animation(1.0f / 20.0f, regions,
                Animation.LOOP);
    }
}