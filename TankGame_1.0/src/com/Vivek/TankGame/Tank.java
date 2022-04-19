package com.Vivek.TankGame;


/**
 * @author 楠秋
 * @version 1.0
 */
class Tank {
    static int top;
    static int bottom;
    static int left;
    static int right;

    private int x;
    private int y;
    private int direct =(int) (Math.random()*3);


    public Tank(int x, int y) {


        this.x = x;
        this.y = y;
    }


    public void move(int direct) {
        boolean sumLoop=true;
        for (int i = 0; i < MyPanel.tanks.size();i++ ) {
            boolean loop;

            Tank tank=MyPanel.tanks.get(i);
            if (this.equals(tank)) {
                continue;
            }
            loop=isTanksOverlap(tank);
            sumLoop=sumLoop&&loop;
        }

        if (sumLoop) {
            switch (direct) {
                case 0:
                    if (y > 0)
                        y -= 5;
                    break;
                case 1:
                    if (y + 40 + 5 <= (800 - top - bottom))

                        y += 5;
                    break;
                case 2:
                    if (x > 0)
                        x -= 5;
                    break;
                case 3:
                    if (x + 40 + 5 <= (800 - left - right))
                        x += 5;
                    break;
            }

        }
    }

    public boolean isTanksOverlap(Tank tank) {


            if (this.direct == 0) {
                if ((this.x<tank.x ||this.x>tank.x+40)&&(this.x+40<tank.x||this.x+40>tank.x+40)){
                    return true;
                }else
                    return this.y - 5 < tank.y || this.y - 5 > tank.y + 40;
            }

            else if (this.direct == 1) {

                if ((this.x<tank.x ||this.x>tank.x+40)&&(this.x+40<tank.x||this.x+40>tank.x+40)){
                    return true;
                }else
                    return this.y + 40+5 < tank.y || this.y + 5 > tank.y + 40;
            }
            else if (this.direct == 2) {
                if ((this.y<tank.y||this.y>tank.y+40)&&(this.y+40<tank.y||this.y+40>tank.y+40)){
                    return true;
                }else
                    return this.x-5>tank.x+40 ||this.x+40-5<tank.x;
            }
            else {
                if ((this.y<tank.y||this.y>tank.y+40)&&(this.y+40<tank.y||this.y+40>tank.y+40)){
                    return true;
                } else
                    return this.x+40+5<tank.x || this.x+5 >tank.x+40;


            }
        }





    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getX() {
        return this.x;
    }
    public void setX(int x) {
        this.x = x;
    }


    public int getY() {
        return this.y;
    }


}
