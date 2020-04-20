package net.speciesm.particlefun.engine.renderer;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import net.speciesm.particlefun.model.GameStage;

import java.io.IOException;

public class PixelFlood implements Renderer {

    PixelFloodClient client = new PixelFloodClient("jroeger.de", 1234);
    private GameStage gameStage;

    public PixelFlood(GameStage gameStage) throws IOException {
        this.gameStage = gameStage;
    }

    @Override
    public void renderFrame(long curTimeNS) {
        gameStage.getGameObjects().forEach(g -> g.draw(this));
        client.flush();
    }

    @Override
    public float getAvgFPS() {
        return 0;
    }

    @Override
    public void drawImage(Image image, int x, int y) {
        //client.drawRect(x + 900, y + 500, (int) image.getWidth(), (int) image.getHeight(), Color.BLACK);

    }

    @Override
    public void drawPixel(int x, int y, Color color) {
        client.setPixel(x + 00, y + 00, color);
    }
}
