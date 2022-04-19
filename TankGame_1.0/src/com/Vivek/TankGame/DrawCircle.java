package com.Vivek.TankGame;

import javax.swing.*;

/**
 * @author 楠秋
 * @version 1.0
 */
public class DrawCircle extends JFrame {


    public static void main(String[] args) {
        DrawCircle drawCircle = new DrawCircle();
        MyPanel.top=drawCircle.getInsetsTob();
        MyPanel.bottom=drawCircle.getInsetsBottom();
        MyPanel.left=drawCircle.getInsetsleft();
        MyPanel.right=drawCircle.getInsetsRight();

        Hero.top=drawCircle.getInsetsTob();
        Hero.bottom=drawCircle.getInsetsBottom();
        Hero.left=drawCircle.getInsetsleft();
        Hero.right=drawCircle.getInsetsRight();

        Bullet.top=drawCircle.getInsetsTob();
        Bullet.bottom=drawCircle.getInsetsBottom();
        Bullet.left=drawCircle.getInsetsleft();
        Bullet.right=drawCircle.getInsetsRight();

    }

    public DrawCircle()  {
        MyPanel mp ;      //给画板创建一个对象
        mp = new MyPanel();
        this.add(mp);
        new Thread(mp).start();
        this.addKeyListener(mp);
        this.setSize(1000, 800);
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setResizable(false);
        setLocationRelativeTo(getOwner());


    }
    public int getInsetsTob(){
        return insets().top;
    }
    public int getInsetsBottom(){
        return insets().bottom;
    }
    public int getInsetsleft(){
        return insets().left;
    }
    public int getInsetsRight(){
        return insets().right;
    }
}

