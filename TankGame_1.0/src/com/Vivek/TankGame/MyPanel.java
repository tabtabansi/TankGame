package com.Vivek.TankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * @author 楠秋
 * @version 1.0
 */
class MyPanel extends JPanel implements KeyListener, Runnable {
    static int top;
    static int bottom;
    static int left;
    static int right;

    //设置初始敌方坦克数量
    int enemyTanksSize = 5;

    private int skill = 0;

    public int enemyTankSum = 15;


    public void setSkill(int skill) {
        this.skill = skill;
    }

    Hero hero;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    Vector<Boom> booms = new Vector<>();
    static Vector<Tank> tanks = new Vector<>();

    Image image1;
    Image image2;
    Image image3;


    public MyPanel() {
        //创建主角坦克
        this.hero = new Hero(400, 800-top-bottom-90);
        //吧主角坦克加入到坦克集合，该集合包括敌我双方当前存活坦克数量
        tanks.add(hero);

        //遍历敌方坦克数量
        for (int i = 0; i < enemyTanksSize; i++) {
            //创建敌方坦克对象，并赋值x和y值。

           EnemyTank enemyTank=null;
            boolean isOver=true;
            for (int j = 0; j < tanks.size(); j++) {
                int randomX=(int)(Math.random()*700);
                int randomY=(int)(Math.random()*300);
                 enemyTank = new EnemyTank(randomX ,randomY);
                Tank tank=tanks.get(j);
                boolean loop=enemyTank.isTanksOverlap(tank);
                isOver=isOver&&loop;

                if (!isOver){
                    enemyTank=null;
                    i--;
                    break;
                }

            }


            //吧敌方坦克加入到坦克集合中
            if (isOver) {
                tanks.add(enemyTank);
                //吧敌方坦克加入到敌方坦克集合中
                enemyTanks.add(enemyTank);
                //启动线程
                new Thread(enemyTank).start();
                Bullet bullet = null;

                switch (enemyTank.getDirect()) {
                    case 0://up
                        bullet = new Bullet(enemyTank.getX() + 19, enemyTank.getY(), 0);
                        break;
                    case 1://down
                        bullet = new Bullet(enemyTank.getX() + 19, enemyTank.getY() + 40, 1);
                        break;
                    case 2://left
                        bullet = new Bullet(enemyTank.getX(), enemyTank.getY() + 19, 2);

                        break;
                    case 3://right
                        bullet = new Bullet(enemyTank.getX() + 40, enemyTank.getY() + 19, 3);
                        break;
                }


                enemyTank.bullets.add(bullet);
                new Thread(bullet).start();
            }
        }

        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));


    }


    @Override
    public void paint(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, 800 - right - left, 800 - bottom - top);
        g.setColor(Color.gray);
        g.fillRect(800-left-right, 0, 200, 800 - bottom - top);
        gameInfo(g);
        if (hero != null && !hero.isLive) {
            hero = null;
        } else if (hero != null)
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);


        for (int i = 0; i < booms.size(); i++) {
            Boom boom = booms.get(i);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (boom.life > 6) {
                g.drawImage(image1, boom.x, boom.y, 60, 60, this);
            } else if (boom.life > 3) {
                g.drawImage(image2, boom.x, boom.y, 60, 60, this);

            } else
                g.drawImage(image3, boom.x, boom.y, 60, 60, this);
            boom.lifeDown();
            if (boom.life == 0) {
                booms.remove(boom);
            }

        }


        if (hero != null) {
            for (int i = 0; i < hero.bullets.size(); i++) {
                Bullet bullet = hero.bullets.get(i);
                if (bullet != null && bullet.isLive) {
                    g.setColor(Color.red);
                    g.fill3DRect(bullet.getX(), bullet.getY(), 3, 3, false);
                } else
                    hero.bullets.remove(bullet);
            }
        }

        for (EnemyTank enemyTank : enemyTanks) {
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 2);
                for (int i = 0; i < enemyTank.bullets.size(); i++) {
                    Bullet bullet = enemyTank.bullets.get(i);
                    if (bullet.isLive) {
                        g.setColor(Color.yellow);
                        g.fill3DRect(bullet.getX(), bullet.getY(), 3, 3, false);
                    } else {
                        enemyTank.bullets.remove(bullet);
                        Bullet bullet1;
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 1) {
                            bullet1 = new Bullet(enemyTank.getX() + 19, enemyTank.getY() + 40, enemyTank.getDirect());
                        } else
                            bullet1 = new Bullet(enemyTank.getX() + 40, enemyTank.getY() + 19, enemyTank.getDirect());


                        enemyTank.bullets.add(bullet1);
                        new Thread(bullet1).start();
                    }
                }
            }
        }
    }


    /***
     *
     * @param x  坦克左上角的X坐标
     * @param y  坦克左上角的Y坐标
     * @param g  画笔
     * @param direction 坦克的方向
     * @param type  坦克类型
     */
    public void drawTank(int x, int y, Graphics g, int direction, int type) {

        switch (type) {
            case 0:
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.red);
                break;
            case 2:
                g.setColor(Color.yellow);
                break;
        }

        switch (direction) {
            case 0://up
                g.fill3DRect(x, y, 10, 40, false);
                g.fill3DRect(x + 10, y + 5, 20, 30, false);
                g.fill3DRect(x + 30, y, 10, 40, false);
                g.drawLine(x + 19, y + 5, x + 19, y);
                g.fillOval(x + 9, y + 10, 20, 20);
                break;


            case 1://down
                g.fill3DRect(x, y, 10, 40, false);
                g.fill3DRect(x + 10, y + 5, 20, 30, false);
                g.fill3DRect(x + 30, y, 10, 40, false);
                g.drawLine(x + 19, y + 35, x + 19, y + 40);
                g.fillOval(x + 9, y + 10, 20, 20);
                break;

            case 2://left
                g.fill3DRect(x, y, 40, 10, false);
                g.fill3DRect(x + 5, y + 10, 30, 20, false);
                g.fill3DRect(x, y + 30, 40, 10, false);
                g.drawLine(x + 5, y + 19, x, y + 19);
                g.fillOval(x + 10, y + 9, 20, 20);
                break;

            case 3://right
                g.fill3DRect(x, y, 40, 10, false);
                g.fill3DRect(x + 5, y + 10, 30, 20, false);
                g.fill3DRect(x, y + 30, 40, 10, false);
                g.drawLine(x + 35, y + 19, x + 40, y + 19);
                g.fillOval(x + 10, y + 9, 20, 20);

        }
    }

    public void hitEnemyTank(Vector<Bullet> bullets, EnemyTank enemyTank) {
        int enemyTankPoint = 0;

        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (bullet.getX() > enemyTank.getX() && bullet.getX() < enemyTank.getX() + 40 &&
                    bullet.getY() > enemyTank.getY() && bullet.getY() < enemyTank.getY() + 40) {
                bullet.isLive = false;
                enemyTank.isLive = false;
                enemyTanks.remove(enemyTank);
                tanks.remove(enemyTank);
                enemyTankSum--;
                Boom boom = new Boom(enemyTank.getX(), enemyTank.getY());
                booms.add(boom);


                //添加新的坦克
                if (enemyTankSum-enemyTanksSize>= 0) {
                    EnemyTank enemyTank1 = new EnemyTank(enemyTankPoint, 0);


                    tanks.add(enemyTank1);
                    while (true) {
                        boolean sumLoop = true;
                        for (int j = 0; j < MyPanel.tanks.size(); j++) {
                            boolean loop;

                            Tank tank = MyPanel.tanks.get(j);
                            if (enemyTank1.equals(tank)) {
                                continue;
                            }
                            loop = enemyTank1.isTanksOverlap(tank);
                            sumLoop = sumLoop && loop;
                            if (!sumLoop) {
                                break;
                            }
                        }
                        if (sumLoop) {
                            enemyTanks.add(enemyTank1);
                            new Thread(enemyTank1).start();
                            Bullet bullet1 = new Bullet(enemyTank1.getX() + 19, enemyTank1.getY() + 40, enemyTank1.getDirect());
                            enemyTank1.bullets.add(bullet1);
                            new Thread(bullet1).start();
                            break;
                        } else {
                            enemyTankPoint += 100;
                            enemyTank1.setX(enemyTankPoint);
                        }
                    }

                }
            }

        }

    }




    public void hitHeroTank(Bullet bullet, Hero hero) {

        if (hero != null && bullet.getX() > hero.getX() && bullet.getX() < hero.getX() + 40 &&
                bullet.getY() > hero.getY() && bullet.getY() < hero.getY() + 40) {
            bullet.isLive = false;
            hero.isLive = false;
            Boom boom = new Boom(hero.getX(), hero.getY());
            booms.add(boom);

        }
    }
    public void gameInfo(Graphics g){
        g.setColor(Color.cyan);
       int sum=0;
        for (int i = 0; i < enemyTankSum; i++) {
            for (int j = 0; j < 2; j++) {
                drawTank(850+j*60-right-left,40+i*60,g,0,2);
                sum++;
                if (sum==enemyTankSum){
                    System.out.println(enemyTankSum);
                    return;
                }
            }}


        }



    @Override
    public void keyTyped(KeyEvent e) {  //有字符输入，该方法被调用

    }

    @Override
    public void keyPressed(KeyEvent e) {//某个键位被按下，被触发
        if (hero == null) {
            return;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirect(0);
            hero.move(0);
            // hero.move(0);
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirect(1);
            // hero.move(1);
            hero.move(1);
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirect(2);
            hero.move(2);
            // hero.move(2);
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirect(3);
            hero.move(3);
            // hero.move(3);
        } else if (e.getKeyCode() == KeyEvent.VK_Q) {
            if (skill == 0)
                setSkill(1);
            else if (skill == 1)
                setSkill(0);
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {

            if (skill == 1) {
                hero.shotBullet(0);
            } else if (skill == 0) {
                hero.shotBullet(1);
            }
        }
        this.repaint();

    }


    @Override
    public void keyReleased(KeyEvent e) {  //某个键位被松开，被触发

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (hero != null && hero.bullet != null && hero.bullet.isLive) {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitEnemyTank(hero.bullets, enemyTank);

                }
            }
            if (hero != null) {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    for (int j = 0; j < enemyTank.bullets.size(); j++) {
                        Bullet bullet = enemyTank.bullets.get(j);
                        if (bullet != null && bullet.isLive) {
                          //  hitHeroTank(bullet, hero);
                        }

                    }
                }
            }
            this.repaint();
        }
    }
}
