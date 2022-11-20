package com.fx.javafxdemo.graphics;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *  a
 *  b
 * dc
 */
public class J extends AbsGraphics {

    @Override
    public void createGraphics() {
        a = new Rectangle(WIDTH / 2, 0, SIZE, SIZE);
        b = new Rectangle(WIDTH / 2, SIZE, SIZE, SIZE);
        c = new Rectangle(WIDTH / 2, SIZE * 2, SIZE, SIZE);
        d = new Rectangle(WIDTH / 2 - SIZE, SIZE * 2, SIZE, SIZE);
    }

    @Override
    protected double getAngDeg() {
        return 90;
    }

    @Override
    protected double getRY() {
        return b.getY();
    }

    @Override
    protected double getRX() {
        return b.getX();
    }


    @Override
    public Color setColor() {
        return Color.YELLOW;
    }
}
