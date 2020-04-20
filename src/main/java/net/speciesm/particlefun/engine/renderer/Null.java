package net.speciesm.particlefun.engine.renderer;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Null
 * Ignores all render calls.
 *
 * @author Draget draget@speciesm.net
 */
public class Null implements Renderer {
    @Override
    public void renderFrame(long curTimeNS) {
        // Null
    }

    public float getAvgFPS() {
        return -1;
    }

    @Override
    public void drawImage(Image image, int x, int y) {
        // Null
    }

    @Override
    public void drawPixel(int x, int y, Color color) {
        // Null
    }
}
