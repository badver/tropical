package ru.badver.jff.slotgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import ru.badver.jff.slotgame.game.GameController;

public class GameScreen extends AbstractGameScreen {

    private static final String TAG = "GAME SCREEN ";
    private InputProcessor inputProcessor;

    private boolean paused;

    public GameScreen(DirectedGame game) {
        super(game);
        inputProcessor = new GameController();
    }

    @Override
    public InputProcessor getInputProcessor() {
        return inputProcessor;
    }

    @Override
    public void render(float deltaTime) {

        // Do not update game world when paused.
        if (!paused) {
            // Update game world by the time that has passed
            // since last rendered frame.
//            worldController.update(deltaTime);
        }

        // Sets the clear screen color
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render game world to screen
//        worldRenderer.render();
    }

    @Override
    public void resize(int width, int height) {
//        worldRenderer.resize(width, height);
    }

    @Override
    public void show() {
        Gdx.app.debug(TAG, "show");
//        worldController = new WorldController(game);
//        worldRenderer = new WorldRenderer(worldController);
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void hide() {
//        worldController.dispose();
//        worldRenderer.dispose();
        Gdx.input.setCatchBackKey(false);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        super.resume();
        Gdx.app.debug(TAG, "resume");
        // Only called on Android!
        paused = false;
    }
}
