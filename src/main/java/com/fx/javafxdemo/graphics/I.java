package com.fx.javafxdemo.graphics;

import com.fx.javafxdemo.Director;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * a
 * b
 * c
 * d
 */
public class I extends AbsGraphics {
    /**
     * 计数 转动次数
     */
    private int i = -1;

    @Override
    public void createGraphics() {
        a = new Rectangle(WIDTH / 2 - SIZE, 0, SIZE, SIZE);
        b = new Rectangle(WIDTH / 2 - SIZE, SIZE, SIZE, SIZE);
        c = new Rectangle(WIDTH / 2 - SIZE, SIZE * 2, SIZE, SIZE);
        d = new Rectangle(WIDTH / 2 - SIZE, SIZE * 3, SIZE, SIZE);
    }

    @Override
    protected double getAngDeg() {
        i ++ ;
        if (i % 2 == 0){
            return 90;
        }else{
            return -90;
        }
    }

    @Override
    protected double getRY() {
        return c.getY();
    }

    @Override
    protected double getRX() {
        return c.getX();
    }

    @Override
    public Color setColor() {
        return Color.PURPLE;
    }
}
