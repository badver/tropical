package ru.badver.jff.slotgame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = "ASSETS ";
    public static final Assets instance = new Assets();
    public AssetBunny bunny;
    public AssetGoldCoin goldCoin;
    public AssetFeather feather;
    public AssetLevelDecoration levelDecoration;
    public AssetFonts fonts;
    public AssetSounds sounds;
    public AssetMusic music;
    public AssetGirlBlack girlBlack;
    private AssetManager assetManager;

    // singleton: prevent instantiation from other classes
    private Assets() {
    }

    /**
     * update load progress and return progress from  0.0f to 1.0f ( 0% - 100% )
     *
     * @return
     */
    public float getProgress() {
        assetManager.update();
        return assetManager.getProgress();
    }

    /**
     * Call this method when all data loaded, this finishes creating assets
     */
    public void finishInit() {
        if (assetManager.getProgress() >= 1) {
            music = new AssetMusic(assetManager);
            girlBlack = new AssetGirlBlack(assetManager);

            //        TextureAtlas atlas = assetManager.get(Constants.GAME_ATLAS);

            //        create game resource objects
            //        fonts = new AssetFonts();
            //        bunny = new AssetBunny(atlas);
            //        rock = new AssetRock(atlas);
            //        goldCoin = new AssetGoldCoin(atlas);
            //        feather = new AssetFeather(atlas);
            //        levelDecoration = new AssetLevelDecoration(atlas);
            //        sounds = new AssetSounds(assetManager);
            //        music = new AssetMusic(assetManager);
        }
    }

    /**
     * Prepare loading assets.
     * to continue load, call getProgress() until it'll returns 1.0f (it means 100% load)
     *
     * @param assetManager
     */
    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;

        // set asset manager error handler
        assetManager.setErrorListener(this);
        Gdx.app.debug(TAG, "Start loading");

        // load music
        assetManager.load("music/music.ogg", Music.class);

        //        // load sounds
        //        for (int i = 1; i <= 5; i++) {
        //            assetManager.load("sounds/bar/bar_start_" + i + ".ogg", Sound.class);
        //            assetManager.load("sounds/bar/bar_stop_" + i + ".ogg", Sound.class);
        //        }

        // load texture atlases
        assetManager.load("images/girl_black_anim_0.txt", TextureAtlas.class);
        //        assetManager.load("images/girl_black_anim_1.txt", TextureAtlas.class);
        //        assetManager.load("images/girl_black_anim_2.txt", TextureAtlas.class);
        //
        //        assetManager.load("images/girl_orange_anim_0.atlas", TextureAtlas.class);
        //        assetManager.load("images/girl_orange_anim_1.atlas", TextureAtlas.class);
        //        assetManager.load("images/girl_orange_anim_2.atlas", TextureAtlas.class);
        //
        //        assetManager.load("images/girl_red_anim_0.atlas", TextureAtlas.class);
        //        assetManager.load("images/girl_red_anim_1.atlas", TextureAtlas.class);
        //        assetManager.load("images/girl_red_anim_2.atlas", TextureAtlas.class);
        //
        //        assetManager.load("images/mainfon_0.atlas", TextureAtlas.class);
        //        assetManager.load("images/mainfon_1.atlas", TextureAtlas.class);
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'",
                (Exception) throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        fonts.defaultSmall.dispose();
        fonts.defaultNormal.dispose();
        fonts.defaultBig.dispose();
    }

    /**
     * contains game sounds
     */
    public class AssetSounds {
        public final Sound jump;
        public final Sound jumpWithFeather;
        public final Sound pickupCoin;
        public final Sound pickupFeather;
        public final Sound liveLost;

        public AssetSounds(AssetManager am) {
            jump = am.get("sounds/jump.wav", Sound.class);
            jumpWithFeather = am.get("sounds/jump_with_feather.wav",
                    Sound.class);
            pickupCoin = am.get("sounds/pickup_coin.wav", Sound.class);
            pickupFeather = am.get("sounds/pickup_feather.wav", Sound.class);
            liveLost = am.get("sounds/live_lost.wav", Sound.class);
        }
    }

    /**
     * contains game music
     */
    public class AssetMusic {
        public final Music song01;

        public AssetMusic(AssetManager am) {
            song01 = am.get("music/music.ogg", Music.class);
        }
    }

    /**
     * contains game fonts
     */
    public class AssetFonts {
        public final BitmapFont defaultSmall;
        public final BitmapFont defaultNormal;
        public final BitmapFont defaultBig;

        public AssetFonts() {
            // create three fonts using Libgdx's 15px bitmap font
            defaultSmall = new BitmapFont(false);
            defaultNormal = new BitmapFont(false);
            defaultBig = new BitmapFont(false);

            // set font sizes
            defaultSmall.setScale(0.75f);
            defaultNormal.setScale(1.0f);
            defaultBig.setScale(2.0f);

            // enable linear texture filtering for smooth fonts
            defaultSmall.getRegion().getTexture()
                    .setFilter(TextureFilter.Linear, TextureFilter.Linear);
            defaultNormal.getRegion().getTexture()
                    .setFilter(TextureFilter.Linear, TextureFilter.Linear);
            defaultBig.getRegion().getTexture()
                    .setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }
    }

    // just for example
    public class AssetBunny {
        public final AtlasRegion head;
        public final Animation animNormal;
        public final Animation animCopterTransform;
        public final Animation animCopterTransformBack;
        public final Animation animCopterRotate;

        public AssetBunny(TextureAtlas atlas) {
            head = atlas.findRegion("bunny_head");

            Array<AtlasRegion> regions = null;
            //AtlasRegion region = null;

            // Animation: Bunny Normal
            regions = atlas.findRegions("anim_bunny_normal");
            animNormal = new Animation(1.0f / 10.0f, regions,
                    Animation.LOOP_PINGPONG);

            // Animation: Bunny Copter - knot ears
            regions = atlas.findRegions("anim_bunny_copter");
            animCopterTransform = new Animation(1.0f / 10.0f, regions);

            // Animation: Bunny Copter - unknot ears
            regions = atlas.findRegions("anim_bunny_copter");
            animCopterTransformBack = new Animation(1.0f / 10.0f, regions,
                    Animation.REVERSED);

            // Animation: Bunny Copter - rotate ears
            regions = new Array<AtlasRegion>();
            regions.add(atlas.findRegion("anim_bunny_copter", 4));
            regions.add(atlas.findRegion("anim_bunny_copter", 5));
            animCopterRotate = new Animation(1.0f / 15.0f, regions);
        }
    }

    // just for example
    public class AssetLevelDecoration {
        public final AtlasRegion cloud01;
        public final AtlasRegion cloud02;
        public final AtlasRegion cloud03;
        public final AtlasRegion mountainLeft;
        public final AtlasRegion mountainRight;
        public final AtlasRegion waterOverlay;
        public final AtlasRegion carrot;
        public final AtlasRegion goal;

        public AssetLevelDecoration(TextureAtlas atlas) {
            cloud01 = atlas.findRegion("cloud01");
            cloud02 = atlas.findRegion("cloud02");
            cloud03 = atlas.findRegion("cloud03");
            mountainLeft = atlas.findRegion("mountain_left");
            mountainRight = atlas.findRegion("mountain_right");
            waterOverlay = atlas.findRegion("water_overlay");
            carrot = atlas.findRegion("carrot");
            goal = atlas.findRegion("goal");
        }
    }

    // example of single texture
    public class AssetFeather {
        public final AtlasRegion feather;

        public AssetFeather(TextureAtlas atlas) {
            feather = atlas.findRegion("item_feather");
        }
    }

    // example texture and animation
    public class AssetGoldCoin {
        public final AtlasRegion goldCoin;
        public final Animation animGoldCoin;

        public AssetGoldCoin(TextureAtlas atlas) {
            goldCoin = atlas.findRegion("item_gold_coin");

            // Animation: Gold Coin
            Array<AtlasRegion> regions = atlas.findRegions("anim_gold_coin");
            AtlasRegion region = regions.first();
            for (int i = 0; i < 10; i++)
                regions.insert(0, region);
            animGoldCoin = new Animation(1.0f / 20.0f, regions,
                    Animation.LOOP_PINGPONG);
        }
    }

    /**
     * animated black hair girl
     */
    public class AssetGirlBlack {
        public final TextureRegion girlBlack;
        public final Animation animGirlBlack;

        public AssetGirlBlack(AssetManager am) {
            TextureAtlas textureAtlas = am.get("images/girl_black_anim_0.txt", TextureAtlas.class);
            girlBlack = textureAtlas.findRegion("000");

            // Animation:
            Array<AtlasRegion> regions = textureAtlas.findRegions("000");
            AtlasRegion region = regions.first();
            for (int i = 0; i < 10; i++)
                regions.insert(0, region);

            animGirlBlack = new Animation(1.0f / 20.0f, regions,
                    Animation.LOOP);
        }
    }
}
