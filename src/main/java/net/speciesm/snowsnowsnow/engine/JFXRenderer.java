package net.speciesm.snowsnowsnow.engine;

import com.sun.javafx.perf.PerformanceTracker;
import com.sun.javafx.tk.Toolkit;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.speciesm.snowsnowsnow.model.GameStage;

/**
 * JFXRenderer
 * Implements a JavaFX-based renderer.
 *
 * @author Draget draget@speciesm.net
 */
@RequiredArgsConstructor
public class JFXRenderer implements Renderer {
    private static final long NS_IN_SEC = 1000_000_000;

    // update FPS each second
    private static final long TIMER_AVGFPS_INTERVAL = 1 * NS_IN_SEC;

    private int frameCounter;
    private PerformanceTracker perfTracker = Toolkit.getToolkit().getPerformanceTracker();
    @Getter private float avgFPS;
    private long timerLastAvgFPSTick;

    private String infoText = "";

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
        gameStage.draw(this);
    }

    private void drawInfo() {
        //if (avgFPS > MINFPS) {
        graphicsContext.setFill(Color.WHITE);
        //} else {
        //    graphicsContext.setFill(Color.RED);
        //}
        graphicsContext.fillText(infoText, 10, 20);
    }

    private void registerRenderedFrame(final long curTimeNS) {
        frameCounter++;
        perfTracker.frameRendered();
        if (curTimeNS - timerLastAvgFPSTick > TIMER_AVGFPS_INTERVAL) {
            timerLastAvgFPSTick = curTimeNS;
            avgFPS = perfTracker.getAverageFPS();
            infoText = String.format("%-9s %6.1f\n%-9s %6.1fK", "FPS:", avgFPS, "Particle:", gameStage.getParticleCount() / 1000d);

            perfTracker.resetAverageFPS();
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
