package com.fx.javafxdemo;

import com.fx.javafxdemo.utils.FileUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Director {
    public static int LEVE = 1;
    public static final int LEVE_STEP = 10;
    public static final long SPEED = 1000000000L;
    public static final double SIZE=25;
    public static final double MOVE=25;
    public static int MESH_WIDTH = 12;
    public static int MESH_HEIGHT = 20;
    public static final double WIDTH = MESH_WIDTH*SIZE;
    public static final double HEIGHT = MESH_HEIGHT*SIZE;
    public static int[][] MESH = new int[MESH_WIDTH][MESH_HEIGHT];

    static Director director = new Director();
    private Director() {}
    public static Director getInstance(){
        return director;
    }
    private Stage stage;

    public BorderPane getIndex() {
        return index;
    }

    public void setIndex(BorderPane index) {
        this.index = index;
    }

    private BorderPane index;
    /**
     * 游戏界面布局
     */
    private Pane game;


    public GameScene gameScene = new GameScene();

    public static boolean running;

    /**
     * 初始化首页
     * @param stage
     * @throws IOException
     */
    public void init(Stage stage) throws IOException {
        setStage(stage);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("俄罗斯方块");
        stage.getIcons().add(new Image(FileUtils.getImage("/image/2022-11-15_21-45.png")));
        Node index = scene.lookup("#index");
        if (index instanceof BorderPane){
            ((BorderPane) index).setPrefHeight(Director.HEIGHT);
            ((BorderPane) index).setPrefWidth(Director.WIDTH);
        }
        stage.setScene(scene);
        //此处不能设置 宽高，否则内部容器不能撑开
//        stage.setWidth(WIDTH);
//        stage.setHeight(HEIGHT);
        //是否置顶
//        stage.setAlwaysOnTop(true);
        //设置窗口样式
        stage.initStyle(StageStyle.UNIFIED);
        // 禁止改变窗口大小
        stage.setResizable(false);
        //是否全屏
//        stage.setFullScreen(true);
        //设置全屏提示语
//        stage.setFullScreenExitHint("");
        // 禁止esc退出全屏
//        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

        stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setGame(Pane game) {
        this.game = game;
    }
    public Pane getGame() {
        return game;
    }



    public void initGame(Pane game) {
        setGame(game);
        gameScene.init(stage);
    }
}
