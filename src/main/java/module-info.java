module com.fx.javafxdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    requires com.almasb.fxgl.all;

    opens com.fx.tetris to javafx.fxml;
    exports com.fx.tetris;
}