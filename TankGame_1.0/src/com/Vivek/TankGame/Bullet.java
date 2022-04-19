package com.Vivek.TankGame;

/**
 * @author 楠秋
 * @version 1.0
 */
public class Bullet implements Runnable {
    static int top;
    static int bottom;
    static int left;
    static int right;

    private int x;
    private int y;


    private final int direct;    //方向


    boolean isLive = true;//判断子弹是否存活。

    public Bullet(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void run() {
        while (x < 800 -left-right && x > 0 && y < 800 - bottom - top && y > 0) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.move(direct);

        }
        isLive = false;
    }

    public void move(int direct) {
        int speed = 30;
        switch (direct) {
            case 0:
                y -= speed;
                break;
            case 1:
                y += speed;
                break;
            case 2:
                x -= speed;
                break;
            case 3:
                x += speed;
                break;
        }
    }


}
