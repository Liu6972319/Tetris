package com.fx.tetris.graphics;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * ab
 * cd
 */
public class O extends AbsGraphics {

    @Override
    public void createGraphics() {
        a = new Rectangle(WIDTH / 2 - SIZE, 0, SIZE, SIZE);
        b = new Rectangle(WIDTH / 2, 0, SIZE, SIZE);
        c = new Rectangle(WIDTH / 2 - SIZE, SIZE, SIZE, SIZE);
        d = new Rectangle(WIDTH / 2, SIZE, SIZE, SIZE);
    }

    @Override
    public void rotate() {

    }

    @Override
    protected double getAngDeg() {
        return 0;
    }

    @Override
    protected double getRY() {
        return 0;
    }

    @Override
    protected double getRX() {
        return 0;
    }

    @Override
    public Color setColor() {
        return Color.ORANGE;

    }
}
