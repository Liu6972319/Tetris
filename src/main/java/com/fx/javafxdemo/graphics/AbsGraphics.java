package com.fx.javafxdemo.graphics;

import com.fx.javafxdemo.Director;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.fx.javafxdemo.Director.MOVE;
import static com.fx.javafxdemo.Director.HEIGHT;
import static com.fx.javafxdemo.Director.MESH;

public abstract class AbsGraphics {
    public double WIDTH = Director.WIDTH;
    public double SIZE = Director.SIZE;
    Rectangle a;
    Rectangle b;
    Rectangle c;
    Rectangle d;

    AbsGraphics() {
        createGraphics();
        setFillColor();
        setStyle();
    }

    private void setStyle() {
        List<Rectangle> rectangles = Arrays.asList(a, b, c, d);
        rectangles.forEach(item -> {
            item.setStroke(Paint.valueOf("#aba"));
            item.setArcWidth(10);
            item.setArcHeight(10);
        });
    }

    /**
     * 创建图形
     */
    public abstract void createGraphics();

    /**
     * 旋转
     */
    public void rotate() {
        double RX = getRX();
        double RY = getRY();
        double angDeg = getAngDeg();

        // 计算 XY 坐标
        double[] ac = calculateCoordinates(a.getX(), a.getY(), RX, RY, angDeg);
        double[] bc = calculateCoordinates(b.getX(), b.getY(), RX, RY, angDeg);
        double[] cc = calculateCoordinates(c.getX(), c.getY(), RX, RY, angDeg);
        double[] dc = calculateCoordinates(d.getX(), d.getY(), RX, RY, angDeg);

        boolean proceed = findOutOfBounds(ac, bc, cc, dc);

        if (proceed) {
            a.setX(ac[0]);
            a.setY(ac[1]);
            b.setX(bc[0]);
            b.setY(bc[1]);
            c.setX(cc[0]);
            c.setY(cc[1]);
            d.setX(dc[0]);
            d.setY(dc[1]);
        }
    }

    protected abstract double getRX();

    protected abstract double getRY();

    protected abstract double getAngDeg();

    private boolean findOutOfBounds(double[]... array) {
        double left = 0;
        double right = 0;
        for (double[] doubles : array) {
            double x = doubles[0];
            if (x < 0) {
                left = Math.max(left, -x);
            }
            if (x > Director.WIDTH - Director.SIZE) {
                right = Math.max(right, x - (Director.WIDTH - Director.SIZE));
            }
            double y = doubles[1];
            if (y < 0) {
                return false;
            }
            if (y > Director.HEIGHT - Director.SIZE) {
                return false;
            }

        }
        // 处理越界
        if (left > 0) {
            for (double[] doubles : array) {
                doubles[0] = doubles[0] + left;
            }
        }
        if (right > 0) {
            for (double[] doubles : array) {
                doubles[0] = doubles[0] - right;
            }
        }
        for (double[] doubles : array) {
            // 判断是否存在在 MESH 中
            if (Director.MESH[(int) (doubles[0] / SIZE)][(int) (doubles[1] / SIZE)] == 1) {
                return false;
            }
        }
        return true;
    }


    /**
     * 设置颜色
     */
    public void setFillColor() {
        Color color = setColor();
        a.setFill(color);
        b.setFill(color);
        c.setFill(color);
        d.setFill(color);
    }

    public abstract Color setColor();

    public void moveDown() {
        if (MESH[(int) (a.getX() / SIZE)][(int) (a.getY() / SIZE)] == 1
                || MESH[(int) (b.getX() / SIZE)][(int) (b.getY() / SIZE)] == 1
                || MESH[(int) (c.getX() / SIZE)][(int) (c.getY() / SIZE)] == 1
                || MESH[(int) (d.getX() / SIZE)][(int) (d.getY() / SIZE)] == 1
        ) {
            //game over
            System.out.println("game over");
            Director.running = false;
            Text game_over = new Text("GAME OVER");
            game_over.setWrappingWidth(Director.WIDTH);
            game_over.setTextAlignment(TextAlignment.CENTER);
            game_over.setY(HEIGHT / 2);
            game_over.setFont(Font.font(50));
            game_over.setStroke(Paint.valueOf("#ff0000"));
            // 点击 gameOver 清除 game 中所有控件
            game_over.setOnMouseClicked(mouseEvent -> {
                Pane game = Director.getInstance().getGame();
                ObservableList<Node> children = game.getChildren();
                if (children.size() > 0) {
                    children.subList(0, children.size()).clear();
                }
                // 清空 MESH
                MESH = new int[(int) (WIDTH / SIZE)][(int) (HEIGHT / SIZE)];

                VBox.setVgrow(game, Priority.NEVER);
//                //设置高度
                game.setPrefHeight(0);
                Director.getInstance().getIndex().setVisible(true);

                //降分清零
                Node score = Director.getInstance().getStage().getScene().lookup("#score");
                if (score instanceof Label) {
                    ((Label) score).setText("0");
                }
                Director.getInstance().gameScene.clear();
            });

            Director.getInstance().getGame().getChildren().add(game_over);
        }


        AtomicBoolean isMove = new AtomicBoolean(true);
        // 检查是否可移动
        List<Rectangle> rectangles = Arrays.asList(a, b, c, d);

        rectangles.forEach(item -> {
            double y = item.getY();
            if (HEIGHT - SIZE - y <= 0 || existMesh("DOWN")) {
                isMove.set(false);
            }
        });
        // 移动
        if (isMove.get()) {
            rectangles.forEach(item -> {
                item.setY(item.getY() + MOVE);
            });
        } else {
            //当不能下落的时候记录到MESH中
            putMesh();

            //判断是否填满
            isRemove();

            Node node = Director.getInstance().getStage().getScene().lookup("#score");
            // 判断分数  小人跳舞
            if (node instanceof Label) {
                String text = ((Label) node).getText();
                int i = Integer.parseInt(text);
                if (Director.LEVE * Director.LEVE_STEP <= i) {
                    Director.LEVE = Director.LEVE + 1;
                    try {
                        Director.running = false;
                        System.out.println("小人跳舞");
                        System.out.println("播放音乐");
                        Thread.sleep(3000);
                        System.out.println("小人退场");
                        System.out.println("音乐停止");
                        Director.running = true;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            //添加图形
            Pane game = Director.getInstance().getGame();
            game.getChildren().addAll(Director.getInstance().gameScene.graphics.getGraphics());

        }

    }

    private void isRemove() {
        List<Rectangle> rectangles = Arrays.asList(a, b, c, d);
        Set<Double> line = new HashSet<>();
        rectangles.forEach(item -> {
            double y = item.getY();
            boolean isRemove = true;
            for (int i = 0; i < WIDTH / SIZE; i++) {
                if (MESH[i][(int) (y / SIZE)] != 1) {
                    isRemove = false;
                }
            }
            if (isRemove) {
                line.add(y);
            }
        });

        if (line.size() > 0) {
            // 加分
            Node label = Director.getInstance().getStage().getScene().lookup("#score");
            if (label instanceof Label) {
                String s = new BigDecimal(((Label) label).getText()).add(new BigDecimal(line.size())).toString();
                ((Label) label).setText(s);
            }
            ObservableList<Node> children = Director.getInstance().getGame().getChildren();

            for (int i = children.size() - 1; i >= 0; i--) {
                Node item = children.get(i);
                if (item instanceof Rectangle) {
                    double y = ((Rectangle) item).getY();
                    if (line.contains(y)) {
                        // 从 MESH 中删除
                        children.remove(item);
                    }
                }
            }

            Double minY = line.stream().min(Double::compare).get();
            // 删除后需要将上层所有格子向下移动
            children.forEach(item -> {
                if (item instanceof Rectangle) {
                    if (((Rectangle) item).getY() < minY) {
                        // 下移操作
                        ((Rectangle) item).setY(((Rectangle) item).getY() + line.size() * SIZE);
                    }
                }
            });

            // 重新填充数据
            MESH = new int[(int) (WIDTH / SIZE)][(int) (HEIGHT / SIZE)];
            children.forEach(item -> {
                if (item instanceof Rectangle) {
                    MESH[(int) (((Rectangle) item).getX() / SIZE)][(int) (((Rectangle) item).getY() / SIZE)] = 1;
                }
            });
        }


    }

    /**
     * 是否存在在MESH中
     *
     * @param status
     * @return
     */
    public boolean existMesh(String status) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        List<Rectangle> rectangles = Arrays.asList(a, b, c, d);
        rectangles.forEach(item -> {
            switch (status) {
                case "DOWN":
                    try {
                        if (MESH[(int) (item.getX() / SIZE)][(int) (item.getY() / SIZE) + 1] == 1) {
                            atomicBoolean.set(true);
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                    break;
                case "RIGHT":
                    try {
                        if (MESH[(int) (item.getX() / SIZE) + 1][(int) (item.getY() / SIZE)] == 1) {
                            atomicBoolean.set(true);
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                    break;
                case "LEFT":
                    try {
                        if (MESH[(int) (item.getX() / SIZE) - 1][(int) (item.getY() / SIZE)] == 1) {
                            atomicBoolean.set(true);
                        }
                    } catch (ArrayIndexOutOfBoundsException ignored) {
                    }
                    break;
            }
        });
        return atomicBoolean.get();
    }

    /**
     * 向MESH中添加
     */
    public void putMesh() {
        MESH[(int) (a.getX() / SIZE)][(int) (a.getY() / SIZE)] = 1;
        MESH[(int) (b.getX() / SIZE)][(int) (b.getY() / SIZE)] = 1;
        MESH[(int) (c.getX() / SIZE)][(int) (c.getY() / SIZE)] = 1;
        MESH[(int) (d.getX() / SIZE)][(int) (d.getY() / SIZE)] = 1;
    }

    public void moveLeft() {
        AtomicBoolean isMove = new AtomicBoolean(true);
        // 检查是否可移动
        List<Rectangle> rectangles = Arrays.asList(a, b, c, d);
        rectangles.forEach(item -> {
            double x = item.getX();
            if (x <= 0 || existMesh("LEFT")) {
                isMove.set(false);
            }
        });
        if (isMove.get()) {
            rectangles.forEach(item -> {
                item.setX(item.getX() - SIZE);
            });
        }
    }

    public void moveRight() {
        AtomicBoolean isMove = new AtomicBoolean(true);
        // 检查是否可移动
        List<Rectangle> rectangles = Arrays.asList(a, b, c, d);
        rectangles.forEach(item -> {
            double x = item.getX();
            if (x >= WIDTH - SIZE || existMesh("RIGHT")) {
                isMove.set(false);
            }
        });
        if (isMove.get()) {
            rectangles.forEach(item -> {
                item.setX(item.getX() + SIZE);
            });
        }
    }

    public double[] calculateCoordinates(double ax, double ay, double ox, double oy) {
        return calculateCoordinates(ax, ay, ox, oy, 90);
    }

    /**
     * 旋转45度计算新坐标
     *
     * @param ax
     * @param ay
     * @param ox
     * @param oy
     * @return
     */
    public double[] calculateCoordinates(double ax, double ay, double ox, double oy, double angDeg) {
        double k = (float) Math.toRadians(angDeg);
        double x = (ax - ox) * Math.cos(k) + (ay - oy) * Math.sin(k) + ox;
        double y = -(ax - ox) * Math.sin(k) + (ay - oy) * Math.cos(k) + oy;
        // 因旋转造成方块与预定格子位置偏移，故提供校验
        BigDecimal bx = new BigDecimal(x).setScale(2, RoundingMode.HALF_UP);
        BigDecimal by = new BigDecimal(y).setScale(2, RoundingMode.HALF_UP);


        return new double[]{
                bx.doubleValue(),
                by.doubleValue()};
    }


}
