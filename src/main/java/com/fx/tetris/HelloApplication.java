package com.fx.tetris;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Director.getInstance().init(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}