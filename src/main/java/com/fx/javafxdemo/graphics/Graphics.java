package com.fx.javafxdemo.graphics;

import com.fx.javafxdemo.Director;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Graphics {

    AbsGraphics graphics;

    public void init() {
        Pane game = Director.getInstance().getGame();
        game.getChildren().addAll(getGraphics());
    }
    /**
     * 获取图形
     * @return
     */
    public List<Rectangle> getGraphics() {
        Random random = new Random();
        switch (random.nextInt(7)) {
            case 1 -> graphics = new O();
            case 0 -> graphics = new T();
            case 2 -> graphics = new J();
            case 5 -> graphics = new Z();
            case 4 -> graphics = new S();
            case 3 -> graphics = new L();
            case 6 -> graphics = new I();
        }

        return Arrays.asList(graphics.a, graphics.b, graphics.c, graphics.d);
    }

    /**
     * 向下移动
     * TODO： 判断网格
     */
    public void moveDown() {
        graphics.moveDown();
    }

    /**
     * 左移动
     */
    public void moveLeft() {
        graphics.moveLeft();
    }

    /**
     * 右移动
     */
    public void moveRight() {
        graphics.moveRight();
    }

    /**
     * TODO:变形
     */
    public void moveUP() {
        graphics.rotate();
    }

}
