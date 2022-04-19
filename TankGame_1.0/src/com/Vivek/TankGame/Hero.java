package com.Vivek.TankGame;

import java.util.Vector;

/**
 * @author 楠秋
 * @version 1.0
 */
class Hero extends Tank {
    Bullet bullet=null;
    boolean isLive=true;
    Vector<Bullet> bullets=new Vector<>();
    public Hero(int x, int y) {
        super(x, y);
    }

    public void shotBullet(int skill ) {
        switch (getDirect()) {
            case 0://up
                bullet = new Bullet(getX() + 19, getY() , 0);
                break;
            case 1://down
                bullet = new Bullet(getX() + 19, getY() + 40, 1);
                break;
            case 2://left
                bullet = new Bullet(getX() , getY() + 19, 2);

                break;
            case 3://right
                bullet = new Bullet(getX() + 40, getY() + 19, 3);
                break;
        }
        if ( skill==1 &&bullets.size()<=3){
        bullets.add(bullet);
        new Thread(bullet).start();
    }else if (bullets.size()==0&&skill==0){
            bullets.add(bullet);
            new Thread(bullet).start();
        }
    }
}
