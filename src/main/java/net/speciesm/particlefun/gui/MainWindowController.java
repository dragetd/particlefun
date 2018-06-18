package net.speciesm.particlefun.gui;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lombok.Setter;
import net.speciesm.particlefun.Main;

/**
 * MainWindowController
 *
 * @author Draget draget@speciesm.net
 */
//@Log4j2(topic = "MainWindow")
public class MainWindowController {
    @FXML private Pane pnlRoot;
    @FXML private Canvas pnlCanvas;

    @Setter private Main controller;

    private PauseTransition resizeUpdateDelay;

    public MainWindowController() {
        // run resizeUpdates in JFX thread
        resizeUpdateDelay = new PauseTransition(Duration.millis(10));
        resizeUpdateDelay.setOnFinished(e -> {
            pnlCanvas.setWidth(pnlRoot.getWidth());
            pnlCanvas.setHeight(pnlRoot.getHeight());
            controller.resizeGameStage((int) pnlCanvas.getWidth(), (int) pnlCanvas.getHeight());
        });
    }

    public GraphicsContext getGraphicsContext() {
        registerResizeListener();
        return pnlCanvas.getGraphicsContext2D();
    }

    private void registerResizeListener() {
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> resizeUpdateDelay.playFromStart();
        pnlRoot.widthProperty().addListener(stageSizeListener);
        pnlRoot.heightProperty().addListener(stageSizeListener);
    }

    @FXML
    private void onClick(javafx.scene.input.MouseEvent m) {
        controller.addParticles(m.getX(), m.getY());
    }
}
