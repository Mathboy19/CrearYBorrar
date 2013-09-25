package crearyborrar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import java.util.Random;
import javax.swing.JOptionPane;

public class CrearYBorrarNormal extends JFrame implements Runnable {

    private Image dbImage;
    private Graphics dbg;
    int mx = 200;
    int my = 200;
    int x1 = 225;
    int y1 = 225;
    Enemy enemy1 = new Enemy(20, 20, 5);
    Enemy enemy2 = new Enemy(300, 40, 7);
    Enemy enemy3 = new Enemy(340, 300, 9);
    Enemy enemy4 = new Enemy(20, 330, 3);
    double start;
    double end;
    double quarter = 0.25;
    double speed = 1;
    double billion = 1000000000;
    boolean collision = false;
    boolean mousePressed = false;
    boolean mouseReleased = false;
    static boolean mouseDragged = false;

    @Override
    public void run() {
        try {
            while (mousePressed == false) {
                //System.out.println("MousePressed=False");
                Thread.sleep(20);
            }
            start = System.nanoTime();
            end = System.nanoTime();
            while (true) {
                while (collision == false) {
                    move();
                    end = System.nanoTime();
                    Thread.sleep(20);
                }
                while (collision == true) {
                    end = System.nanoTime();
                    break;
                }
                break;
            }
            //System.out.println("Game Over");
            //System.out.println("You got:" + "Something" + " seconds!");
            JOptionPane.showMessageDialog(this,
                    "Collision: You got " + ((end - start) / billion) + " seconds!");
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public CrearYBorrarNormal() {
        setSize(400, 400);
        setTitle("CrearYBorrarNormal");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addMouseMotionListener(new MouseMotionListener2());
        addMouseListener(new MouseListener2());
    }

    public void move() {
        if (mouseReleased == true) {
            collision = true;
            JOptionPane.showMessageDialog(null, 
                    "You Unclicked! You have to hold down the mouse to play!", 
                    "Mouse Error!", JOptionPane.ERROR_MESSAGE);
        }
        //mouse
        x1 = mx;
        y1 = my;
        //enemys
        enemy1.update();
        enemy2.update();
        enemy3.update();
        enemy4.update();
        speed = ((end - start) / billion) * quarter + 1;
        //System.out.println(((end - start)/billion));
        //System.out.println("end =" + end);
        //System.out.println("start = " + start);

    }

    @Override
    public void paint(Graphics g) {
        dbImage = createImage(getWidth(), getHeight());
        dbg = dbImage.getGraphics();
        paintComponent(dbg);
        g.drawImage(dbImage, 0, 0, this);
    }

    public void paintComponent(Graphics g) {
        Rectangle mouse = new Rectangle((x1 - 25), (y1 - 25), 50, 50);
        //Rectangle mouse = new Rectangle((200), (200), 50, 50);
        //enemy1
        Rectangle e1 = new Rectangle(enemy1.ex, enemy1.ey, 60, 60);
        //enemy2
        Rectangle e2 = new Rectangle(enemy2.ex, enemy2.ey, 100, 20);
        //enemy3
        Rectangle e3 = new Rectangle(enemy3.ex, enemy3.ey, 20, 100);
        //enemy4
        Rectangle e4 = new Rectangle(enemy4.ex, enemy4.ey, 60, 40);
        g.setColor(Color.RED);
        g.fillRect(mouse.x, mouse.y, mouse.width, mouse.height);
        //draw enemys
        g.setColor(Color.BLUE);
        g.fillRect(e1.x, e1.y, e1.width, e1.height);
        g.fillRect(e2.x, e2.y, e2.width, e2.height);
        g.fillRect(e3.x, e3.y, e3.width, e3.height);
        g.fillRect(e4.x, e4.y, e4.width, e4.height);

        //g.drawString("Collision = " + collision + " mouseDragged = " + 
        //mouseDragged + "!", 130, 60);
        if (mouse.intersects(e1) || mouse.intersects(e2)
                || mouse.intersects(e3) || mouse.intersects(e4)) {
            //g.drawString("Collision!!", 175, 75);
            collision = true;
        }

        repaint();
    }

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

        public void update() {
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
            setCords();
            ex = (int) Math.round(x);
            ey = (int) Math.round(y);
            //System.out.println("(" + ex + "," + ey + ")");

        }

        public void setCords() {
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

        public void main(String[] args) {
        }
    }

    public class MouseMotionListener2 implements MouseMotionListener {

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseDragged = false;
            e.consume();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mx = e.getX();
            my = e.getY();
            if (mx < 25) {
                mx = 25;
            }

            if (mx > 375) {
                mx = 375;
            }

            if (my < 25) {
                my = 25;
            }

            if (my > 375) {
                my = 375;
            }

            mouseDragged = true;
            e.consume();
        }
    }

    public class MouseListener2 implements MouseListener {
        @Override
        public void mousePressed(MouseEvent e) {
            if(e.getX() > 200 && e.getX() < 250 && e.getY() > 200 && 
            e.getY() < 250) {
                mousePressed = true;
            }
            e.consume();
            
            
        }
        @Override
        public void mouseReleased(MouseEvent e) {
            mouseReleased = true;
            e.consume();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            e.consume();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            e.consume();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            e.consume();
        }
    }

    public void main(String[] args) {
        //CrearYBorrar cyb = new CrearYBorrar();
        //Threads
        //Thread t1 = new Thread(this);
        //t1.start();
    }
}