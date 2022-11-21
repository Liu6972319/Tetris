package com.fx.tetris.graphics;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * ab
 *  cd
 */
public class Z extends AbsGraphics {

    @Override
    public void createGraphics() {
        a = new Rectangle(WIDTH / 2 - 2 * SIZE, 0, SIZE, SIZE);
        b = new Rectangle(WIDTH / 2 - SIZE, 0, SIZE, SIZE);
        c = new Rectangle(WIDTH / 2 - SIZE, SIZE, SIZE, SIZE);
        d = new Rectangle(WIDTH / 2, SIZE, SIZE, SIZE);
    }
    private int i;
    @Override
    protected double getAngDeg() {
        i++;
        if (i%2 == 0){
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
        return Color.BLUE;
    }
}
