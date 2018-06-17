package net.speciesm.snowsnowsnow;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.speciesm.snowsnowsnow.engine.GameLoop;
import net.speciesm.snowsnowsnow.engine.JFXRenderer;
import net.speciesm.snowsnowsnow.engine.Renderer;
import net.speciesm.snowsnowsnow.gui.MainWindowController;
import net.speciesm.snowsnowsnow.model.GameStage;
import net.speciesm.snowsnowsnow.model.particlesystem.Snow.SnowSystem;

import java.io.IOException;

/**
 * Main - snowsnowsnow
 *
 * @author Draget draget@speciesm.net
 */
public class Main extends Application {

    public static final String WND_NAME = "snowsnowsnow";
    public static final String WND_RESOURCEPATH = "/gui/Mainwindow.fxml";

    private GameStage gameStage;
    private GameLoop mainLoop;
    private MainWindowController windowController;
    private Renderer renderer;

    private SnowSystem snow;

    public static void main(String[] args) {
        launch(args);
    }

    private MainWindowController initJFXController(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(WND_RESOURCEPATH));

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
        renderer = new JFXRenderer(gameStage, windowController.getGraphicsContext());
        mainLoop = new GameLoop(gameStage, renderer);
        mainLoop.start();
    }

    public void resizeGameStage(int newWidth, int newHeight) {
        gameStage.setWidth(newWidth);
        gameStage.setHeight(newHeight);
    }

    private static final double TAU = Math.PI * 2;

    public void addParticles(double x, double y) {
        for (int i = 0; i < 1000; i++) {
            double ang = Math.random() * TAU;
            double rng = Math.random();
            double vel = (((rng * rng) + rng) / 2) * 20 + 100; // more pretty distribution
            rng = Math.random();
            //double life = (((rng * rng) + rng) / 2) * 20 + 1; // more pretty distribution
            double life = 10;

            snow.addFlake(
                    x, y,
                    Math.cos(ang) * vel, Math.sin(ang) * vel,
                    (int) gameStage.getWidth(), (int) gameStage.getHeight() - 1,
                    life
            );
        }
    }

}
