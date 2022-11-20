module com.fx.javafxdemo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens com.fx.javafxdemo to javafx.fxml;
    exports com.fx.javafxdemo;
}