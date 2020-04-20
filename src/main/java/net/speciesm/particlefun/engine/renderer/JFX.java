package net.speciesm.particlefun.engine.renderer;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.speciesm.particlefun.model.GameStage;

/**
 * JFX
 * Implements a JavaFX-based renderer.
 *
 * @author Draget draget@speciesm.net
 */
@RequiredArgsConstructor
public class JFX implements Renderer {
    private static final long NSINSEC = 1000_000_000;
    private static final int WARN_FPS = 10;

    // update average FPS display each second
    private static final long AVG_FPS_TIMER_INTERVAL = 1 * NSINSEC;

    private int avgFPSFrameCounter;
    @Getter private float avgFPS;
    private long avgFPSLastTimerTick;

    private String infoText;

    @Setter @NonNull private GameStage gameStage;
    @Setter @NonNull private GraphicsContext graphicsContext;

    @Override
    public void renderFrame(final long curTimeNS) {
        drawBackground();
        drawInfoBackground();
        drawStageObjects();
        drawInfo();
        registerRenderedFrame(curTimeNS);
    }

    private void drawBackground() {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());
    }

    private void drawInfoBackground() {
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, 100, 50);
    }

    private void drawStageObjects() {
        gameStage.getGameObjects().forEach(g -> g.draw(this));
    }

    private void drawInfo() {
        if (avgFPS > WARN_FPS) {
        graphicsContext.setFill(Color.WHITE);
        } else {
            graphicsContext.setFill(Color.RED);
        }
        graphicsContext.fillText(infoText, 10, 20);
    }

    private void registerRenderedFrame(final long curTimeNS) {
        avgFPSFrameCounter++;
        long lastFPSUpdate = curTimeNS - avgFPSLastTimerTick;
        if (lastFPSUpdate > AVG_FPS_TIMER_INTERVAL) {
            avgFPS = avgFPSFrameCounter /  lastFPSUpdate * NSINSEC;
            avgFPSLastTimerTick = curTimeNS;
            infoText = String.format("%-9s %6.1f\n%-9s %6.1fK", "FPS:", avgFPS, "Particle:", gameStage.getParticleCount() / 1000d);
        }
    }

    @Override
    public void drawImage(Image image, int x, int y) {
        graphicsContext.drawImage(image, x, y);
    }

    @Override
    public void drawPixel(int x, int y, Color color) {
        graphicsContext.getPixelWriter().setColor(x, y, color);
    }
}
