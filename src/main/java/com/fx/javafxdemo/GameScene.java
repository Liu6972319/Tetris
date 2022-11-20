package com.fx.javafxdemo;

import com.fx.javafxdemo.graphics.AbsGraphics;
import com.fx.javafxdemo.graphics.Graphics;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GameScene {
    Refish refish = new Refish();
    KeyProcess keyProcess = new KeyProcess();
    public Graphics graphics = new Graphics();

    public void init(Stage stage) {
        // 生成线
        drawGrid();

        //生成图像
        graphics.init();

        // 添加键盘监听
        stage.getScene().setOnKeyPressed(keyProcess);
        stage.show();

        refish.start();
    }

    void drawGrid(){
        Pane game = Director.getInstance().getGame();

        for (int i = 1; i < (Director.MESH_HEIGHT); i++) {
            Line line = new Line();
            line.setStartX(0);
            line.setStartY(i * Director.SIZE);
            line.setEndX(Director.WIDTH);
            line.setEndY(i * Director.SIZE);
            line.setStroke(Paint.valueOf("#aba"));
            game.getChildren().add(line);
        }
        for (int i = 1; i < (Director.MESH_WIDTH); i++) {
            Line line = new Line();
            line.setStartX(i*Director.SIZE);
            line.setStartY(0);
            line.setEndX(i*Director.SIZE);
            line.setEndY(Director.HEIGHT);
            line.setStroke(Paint.valueOf("#aba"));
            game.getChildren().add(line);
        }
    }

    public void clear() {
        Director.getInstance().getStage().getScene().removeEventHandler(KeyEvent.KEY_RELEASED, keyProcess);
        refish.stop();
    }

    long dropTime = 0;

    private class Refish extends AnimationTimer {
        //用于计算fps
        private long lastUpdate = 0;
        //用于定时记录
        private long secondTime = 0;


        @Override
        public void handle(long now) {

            if (lastUpdate <= 0) {
                lastUpdate = now;
                dropTime = now;
            } else {
                // 计算fps
                calculateFPS(now);
                if (Director.running) {
                    // 定时下落
                    timedDrop(now);
                }

                // 上次刷新的时间
                lastUpdate = now;
            }
        }
        private void calculateFPS (Long now){
            // 纳秒 / 秒 = 1000000000
            if (now - secondTime > 1000000000) {
                secondTime = now;
                Node node = Director.getInstance().getStage().getScene().lookup("#fps");
                if (node instanceof Label) {
                    // fps = 1000000000 / 间隔时间
                    double fps = BigDecimal.valueOf(1000000000).divide(new BigDecimal(now - lastUpdate), 1, RoundingMode.HALF_UP).doubleValue();
                    ((Label) node).setText(String.valueOf(fps));
                }
            }
        }
        private void timedDrop(long now){
            int score = 0;
            Node node = Director.getInstance().getStage().getScene().lookup("#score");
            if (node instanceof Label){
                String text = ((Label) node).getText();
                score = Integer.parseInt(text);
            }

            if (now - dropTime > Director.SPEED  - (long) score * Director.LEVE) {
                dropTime = now;
                graphics.moveDown();
            }
        }
    }


    private class KeyProcess implements EventHandler<KeyEvent> {

        @Override
        public void handle(KeyEvent keyEvent) {
            KeyCode code = keyEvent.getCode();
            if (code == KeyCode.SPACE){
                // TODO： 此处要添加涂层 提示暂停
                Director.running = !Director.running;
            }

            if (Director.running){
                //TODO: 设置
                switch (code) {
                    case UP -> graphics.moveUP();
                    case DOWN -> graphics.moveDown();
                    case LEFT -> graphics.moveLeft();
                    case RIGHT -> graphics.moveRight();
                }
            }
        }
    }
}
