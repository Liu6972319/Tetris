package com.fx.javafxdemo.graphics;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *  a
 * bcd
 */
public class T extends AbsGraphics {

    @Override
    public void createGraphics() {
        a = new Rectangle(WIDTH / 2 - SIZE, 0, SIZE, SIZE);
        b = new Rectangle(WIDTH / 2 - SIZE * 2, SIZE, SIZE, SIZE);
        c = new Rectangle(WIDTH / 2 - SIZE, SIZE, SIZE, SIZE);
        d = new Rectangle(WIDTH / 2, SIZE, SIZE, SIZE);
    }

    @Override
    protected double getAngDeg() {
        return 90;
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
        return Color.RED;

    }
}
