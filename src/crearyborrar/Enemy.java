package crearyborrar;

import java.util.Random;

public class Enemy {
        //the enemy class

        double x;
        double y;
        int ex;
        int ey;
        int direction;

        public Enemy(int startX, int startY, int startDirection) {
            ex = startX;
            ey = startY;  
            x = startX;
            y = startY;
            direction = startDirection;
        }

        public void update(double speed) {
            if (x < 0) {
                setDirection(4);
            }
            if (x > 365) {
                setDirection(2);
            }
            if (y < 25) {
                setDirection(1);
            }
            if (y > 365) {
                setDirection(3);
            }
            setCords(speed);
            ex = (int) Math.round(x);
            ey = (int) Math.round(y);
            //System.out.println("(" + ex + "," + ey + ")");

        }

        public void setCords(double speed) {
            if (direction == 2) {
                x = x + (0 * speed);
                y = y - (1 * speed);
            }
            if (direction == 3) {
                x = x + (1 * speed);
                y = y - (1 * speed);
            }
            if (direction == 4) {
                x = x + (1 * speed);
                y = y - (0 * speed);
            }
            if (direction == 5) {
                x = x + (1 * speed);
                y = y + (1 * speed);
            }
            if (direction == 6) {
                x = x + (0 * speed);
                y = y + (1 * speed);
            }
            if (direction == 7) {
                x = x - (1 * speed);
                y = y + (1 * speed);
            }
            if (direction == 8) {
                x = x - (1 * speed);
                y = y + (0 * speed);
            }
            if (direction == 9 || direction == 0) {
                x = x - (1 * speed);
                y = y - (1 * speed);
            }

        }

        public void setDirection(int side) {
            Random ran = new Random();
            if (side == 1) {
                direction = ran.nextInt(7 - 5 + 1) + 5;
            }
            if (side == 2) {
                direction = ran.nextInt(9 - 7 + 1) + 7;
            }
            if (side == 3) {
                direction = ran.nextInt(3 - 1 + 1) + 1;
            }
            if (side == 4) {
                direction = ran.nextInt(5 - 3 + 1) + 3;
            }
        }
    }
