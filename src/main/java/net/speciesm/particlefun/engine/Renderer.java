package net.speciesm.particlefun.engine;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Renderer
 *
 * @author Draget draget@speciesm.net
 */
public interface Renderer {
    void renderFrame(final long curTimeNS);

    float getAvgFPS();

    void drawImage(Image image, int x, int y);

    void drawPixel(int x, int y, Color color);
}
