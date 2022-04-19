package com.Vivek.TankGame;

import java.util.Vector;

/**
 * @author 楠秋
 * @version 1.0
 */
public class EnemyTank extends Tank implements Runnable  {
    boolean isLive=true;

        //因为是多线程，这里选择Vector
    Vector<Bullet> bullets=new Vector<>();

    public EnemyTank(int x, int y) {
        super(x, y);
    }





    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < 30; i++) {
                this.move(getDirect());

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        int randomMath =(int)(Math.random()*4);
            try {
                Thread.sleep((int)(Math.random()*100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.setDirect(randomMath);
            if (!this.isLive){
                break;
            }
        }

    }
}
