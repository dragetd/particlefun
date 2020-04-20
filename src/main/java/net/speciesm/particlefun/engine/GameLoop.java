package net.speciesm.particlefun.engine;

import javafx.animation.AnimationTimer;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.speciesm.particlefun.engine.renderer.Renderer;
import net.speciesm.particlefun.model.GameStage;

/**
 * GameLoop
 *
 * @author Draget draget@speciesm.net
 */
@RequiredArgsConstructor
public class GameLoop {
    private static final long NSINSEC = 1000_000_000;

    private static final long MAXFPS = 70;
    private static final long MINFPS = 10;
    private static final long MINTICK = NSINSEC / MAXFPS;
    private static final long MAXTICK = NSINSEC / MINFPS;

    @Getter @Setter private boolean running;

    private double gameSpeed = 1;

    @Getter @NonNull private GameStage gameStage;
    @Setter @NonNull private Renderer renderer;

    private long lastTickNS;

    public void start() {
        if (!running) {
            lastTickNS = System.nanoTime();
            AnimationTimer animator = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    tick(now);
                }
            };
            animator.start();
            running = true;
        } else {
            //log.warn("Start GameLoop: GameLoop already running!");
        }
    }

    private void tick(long curTimeNS) {
        long tickDiff = curTimeNS - lastTickNS;
        lastTickNS = curTimeNS;

        // variable tick with limits
        if (tickDiff < MINTICK) return;
        if (tickDiff > MAXTICK) tickDiff = MAXTICK;
        double tickDelta = (((double) tickDiff) / NSINSEC) * gameSpeed;

        gameStage.tick(tickDelta);
        renderer.renderFrame(curTimeNS);
    }


}
