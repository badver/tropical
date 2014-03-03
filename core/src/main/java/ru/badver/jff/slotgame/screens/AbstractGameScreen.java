package ru.badver.jff.slotgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import ru.badver.jff.slotgame.util.Constants;
import ru.badver.jff.slotgame.util.GameState;
import ru.badver.jff.slotgame.util.States;

public abstract class AbstractGameScreen implements Screen {
    protected DirectedGame game;
    protected OrthographicCamera camera;
    protected Stage stage;
    protected boolean paused;

    public AbstractGameScreen(DirectedGame game) {
        this.game = game;
        stage = new Stage(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT); // set virtual space
        camera = new OrthographicCamera(); // set camera
        camera.position.set(stage.getWidth() / 2, stage.getHeight() / 2, 0); // center camera
        stage.setCamera(camera); // assign camera to stage
    }

    public abstract InputProcessor getInputProcessor();

    public abstract void render(float deltaTime);

    public void resize(int width, int height) {
        camera.viewportHeight = height; //set the viewport
        camera.viewportWidth = width;

        if (Constants.VIEWPORT_WIDTH / camera.viewportWidth < Constants.VIEWPORT_HEIGHT / camera.viewportHeight) {
            //set the right zoom direct
            camera.zoom = Constants.VIEWPORT_HEIGHT / camera.viewportHeight;
        } else {
            //set the right zoom direct
            camera.zoom = Constants.VIEWPORT_WIDTH / camera.viewportWidth;
        }
        camera.update();
    }

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
