
package ru.badver.jff.slotgame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.badver.jff.slotgame.util.Constants;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.useGL20 = true;
        config.fullscreen = false;
        config.title = Constants.TITLE;
        config.width = Constants.SCREEN_WIDTH;
        config.height = Constants.SCREEN_HEIGHT;
        config.resizable = true;

        new LwjglApplication(new SlotGame(), config);
    }
}
