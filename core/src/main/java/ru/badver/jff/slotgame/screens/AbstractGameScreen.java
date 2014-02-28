package ru.badver.jff.slotgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import ru.badver.jff.slotgame.game.Assets;
import ru.badver.jff.slotgame.util.GameState;
import ru.badver.jff.slotgame.util.States;

public abstract class AbstractGameScreen implements Screen {
    protected DirectedGame game;

    public AbstractGameScreen(DirectedGame game) {
        this.game = game;
    }

    public abstract InputProcessor getInputProcessor();

    public abstract void render(float deltaTime);

    public abstract void resize(int width, int height);

    public abstract void show();

    public abstract void hide();

    public abstract void pause();

    public void resume() {
        Gdx.app.debug("AbstractGS ", "resume");
        States state = GameState.instance.getState();
        if (state != States.START && state != States.LOADING) {
//            Assets.instance.init(new AssetManager());
        }
    }

    public void dispose() {
//        Assets.instance.dispose();
    }
}
