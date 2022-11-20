package com.fx.javafxdemo;

import com.almasb.fxgl.core.View;
import com.fx.javafxdemo.graphics.Graphics;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.math.BigDecimal;
import java.util.Set;

public class HelloController {

    @FXML
    private Label fps;
    @FXML
    private Label score;

    @FXML
    private BorderPane index;

    @FXML
    private Pane game;

    /**
     * 覆盖 pane 生成游戏界面
     * @param event
     */
    @FXML
    void startGameClick(MouseEvent event) {
        //初始化 game 清除game中的所有
        Director instance = Director.getInstance();
        Director.running = true;
        instance.setIndex(index);
//         设置 game 撑开
        VBox.setVgrow(game, Priority.ALWAYS);
        //设置高度
        game.setPrefHeight(Director.HEIGHT);

        // 隐藏 首页
        index.setVisible(false);
//        index.setManaged(false);
//        index.setPrefHeight(0);
//        VBox.setVgrow(index, Priority.SOMETIMES);


        instance.initGame(game);


    }



}