package com.Vivek.TankGame;

/**
 * @author 楠秋
 * @version 1.0
 */
public class Boom {
    int x;
    int y;
    int life=9;
    boolean isLive=true;

    public Boom(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void lifeDown(){
        if (life>0){
            life--;
        }
        else {
            isLive=false;
        }
    }
}
