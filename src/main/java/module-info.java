module particlefun {
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires slf4j.api;
    requires static lombok;

    // TODO: I do not really know what I am doing here. :-(
    opens net.speciesm.particlefun.gui to javafx.fxml;
    exports net.speciesm.particlefun to javafx.fxml, javafx.graphics;
    exports net.speciesm.particlefun.gui to javafx.fxml, javafx.graphics;
}