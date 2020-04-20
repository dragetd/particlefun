package net.speciesm.particlefun;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import net.speciesm.particlefun.engine.GameLoop;
import net.speciesm.particlefun.engine.renderer.JFX;
import net.speciesm.particlefun.engine.renderer.PixelFlood;
import net.speciesm.particlefun.engine.renderer.Renderer;
import net.speciesm.particlefun.gui.MainWindowController;
import net.speciesm.particlefun.model.GameStage;
import net.speciesm.particlefun.model.particlesystem.Snow.SnowSystem;

import java.io.IOException;
import java.net.URL;

/**
 * Main - particlefun
 *
 * @author Draget draget@speciesm.net
 */
@Slf4j
public class Main extends Application {

    private static final String WND_NAME = "particlefun";
    private static final String WND_RESOURCEPATH = "/gui/Mainwindow.fxml";

    private static final int NUM_NEW_PARTICLES = 200;
    private static final int NUM_MAX_PARTICLES = 4000;

    private GameStage gameStage;
    private GameLoop mainLoop;
    private MainWindowController windowController;
    private Renderer renderer;

    private SnowSystem snow;

    public static void main(String[] args) {
        launch(args);
    }

    private MainWindowController initJFXController(Stage primaryStage) throws IOException {
        URL resource = getClass().getResource(WND_RESOURCEPATH);
        if (resource == null) {
            log.error("Failed to load window resource.");
            System.exit(1);
        }
        FXMLLoader loader = new FXMLLoader(resource);

        primaryStage.setTitle(WND_NAME);
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
        primaryStage.centerOnScreen();

        return ((MainWindowController) loader.getController());
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        windowController = initJFXController(primaryStage);
        windowController.setController(this);

        gameStage = new GameStage(500, 500);
        snow = new SnowSystem();
        gameStage.addParticleSystem(snow);
        //renderer = new JFX(gameStage, windowController.getGraphicsContext());
        renderer = new PixelFlood();
        addParticles(50, 50);
        mainLoop = new GameLoop(gameStage, renderer);
        mainLoop.start();
    }

    public void resizeGameStage(int newWidth, int newHeight) {
        gameStage.setWidth(newWidth);
        gameStage.setHeight(newHeight);
    }

    private static final double TAU = Math.PI * 2;

    public void addParticles(double x, double y) {
        if (gameStage.getParticleCount() > NUM_MAX_PARTICLES) return;

        for (int i = 0; i < NUM_NEW_PARTICLES; i++) {
            double ang = Math.random() * TAU;
            double rndVal = Math.random();
            double vel = Math.pow(rndVal, 0.8) * 100; // prettier distribution

            double life = 100;

            snow.addFlake(
                    x, y,
                    Math.cos(ang) * vel, Math.sin(ang) * vel,
                    (int) gameStage.getWidth(), (int) gameStage.getHeight() - 1,
                    life
            );
        }
    }

}
