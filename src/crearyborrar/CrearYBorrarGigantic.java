package crearyborrar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CrearYBorrarGigantic extends JFrame implements Runnable {

    private Image dbImage;
    private Graphics dbg;
    int mx = 400;
    int my = 400;
    int x1 = 400;
    int y1 = 400;
    EnemyGigantic enemy1 = new EnemyGigantic(20, 20, 5);
    EnemyGigantic enemy2 = new EnemyGigantic(700, 40, 7);
    EnemyGigantic enemy3 = new EnemyGigantic(740, 700, 9);
    EnemyGigantic enemy4 = new EnemyGigantic(20, 730, 3);
    EnemyGigantic enemy5 = new EnemyGigantic(400, 700, 6);
    EnemyGigantic enemy6 = new EnemyGigantic(700, 440, 4);
    EnemyGigantic enemy7 = new EnemyGigantic(20, 700, 3);
    EnemyGigantic enemy8 = new EnemyGigantic(440, 20, 8);
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

    public CrearYBorrarGigantic() {
        setSize(800, 800);
        setTitle("CrearYBorrarGigantic");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addMouseMotionListener(new MouseMotionListener2());
        addMouseListener(new MouseListener2());
    }

    public void move() {
        if (mouseReleased == true) {
            collision = true;
            JOptionPane.showMessageDialog(this, 
                    "You Unclicked! You have to hold down the mouse to play!", 
                    "Mouse Error!", JOptionPane.ERROR_MESSAGE);
        }
        //mouse
        x1 = mx;
        y1 = my;
        //enemys
        speed = ((end - start) / billion) * quarter + 1;
        enemy1.update(speed);
        enemy2.update(speed);
        enemy3.update(speed);
        enemy4.update(speed);
        enemy5.update(speed);
        enemy6.update(speed);
        enemy7.update(speed);
        enemy8.update(speed);
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
        //enemy5
        Rectangle e5 = new Rectangle(enemy5.ex, enemy5.ey, 60, 60);
        Rectangle e6 = new Rectangle(enemy6.ex, enemy6.ey, 60, 60);
        Rectangle e7 = new Rectangle(enemy7.ex, enemy7.ey, 60, 60);
        Rectangle e8 = new Rectangle(enemy8.ex, enemy8.ey, 60, 60);
        g.setColor(Color.RED);
        g.fillRect(mouse.x, mouse.y, mouse.width, mouse.height);
        //draw enemys
        g.setColor(Color.BLUE);
        g.fillRect(e1.x, e1.y, e1.width, e1.height);
        g.fillRect(e2.x, e2.y, e2.width, e2.height);
        g.fillRect(e3.x, e3.y, e3.width, e3.height);
        g.fillRect(e4.x, e4.y, e4.width, e4.height);
        g.fillRect(e5.x, e5.y, e5.width, e5.height);
        g.fillRect(e6.x, e6.y, e6.width, e6.height);
        g.fillRect(e7.x, e7.y, e7.width, e7.height);
        g.fillRect(e8.x, e8.y, e8.width, e8.height);
        g.drawString("" + ((end - start) / billion), 700, 20);

        //g.drawString("Collision = " + collision + " mouseDragged = " + 
        //mouseDragged + "!", 130, 60);
        if (mouse.intersects(e1) || mouse.intersects(e2)
                || mouse.intersects(e3) || mouse.intersects(e4) || 
                mouse.intersects(e5)) {
            //g.drawString("Collision!!", 175, 75);
            collision = true;
        }

        repaint();
    }
    
    private class EnemyGigantic extends Enemy {
        
        public EnemyGigantic(int startX, int startY, int startDirection) {
            super(startX, startY, startDirection);
            ex = startX;
            ey = startY;  
            x = startX;
            y = startY;
            direction = startDirection;
        }
        @Override
        public void update(double speed) {
            if (x < 0) {
                setDirection(4);
            }
            if (x > 765) {
                setDirection(2);
            }
            if (y < 25) {
                setDirection(1);
            }
            if (y > 765) {
                setDirection(3);
            }
            setCords(speed);
            ex = (int) Math.round(x);
            ey = (int) Math.round(y);
            //System.out.println("(" + ex + "," + ey + ")");
        
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

            if (mx > 775) {
                mx = 775;
            }

            if (my < 75) {
                my = 75;
            }

            if (my > 775) {
                my = 775;
            }

            mouseDragged = true;
            e.consume();
        }
    }

    public class MouseListener2 implements MouseListener {
        @Override
        public void mousePressed(MouseEvent e) {
            if(e.getX() > 400 && e.getX() < 450 && e.getY() > 400 && 
            e.getY() < 450) {
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