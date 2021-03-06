package crearyborrar;


import highscore.HighScoreList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class CrearYBorrarLarge extends JFrame implements Runnable {

    private Image dbImage;
    private Graphics dbg;
    int mx = 300;
    int my = 300;
    int mmx;
    int mmy;
    int x1 = 300;
    int y1 = 300;
    EnemyLarge enemy1 = new EnemyLarge(20, 20, 5);
    EnemyLarge enemy2 = new EnemyLarge(500, 40, 7);
    EnemyLarge enemy3 = new EnemyLarge(540, 500, 9);
    EnemyLarge enemy4 = new EnemyLarge(20, 530, 3);
    HighScoreList hsl = new HighScoreList();
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
                    "Game Over: You got " + ((end - start) / billion) + 
                    " seconds!");
            //save highscore
            String name = (String)JOptionPane.showInputDialog(this, 
                    "What is your name?", "Name?", 
                    JOptionPane.QUESTION_MESSAGE);
            if("".equals(name) || name == null) {
                name = "Anon";
            }
            hsl.add(name, ((end - start) / billion));
            hsl.save("large.txt");
            super.dispose();
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public CrearYBorrarLarge() {
        setSize(600, 600);
        setTitle("CrearYBorrarLarge");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addMouseMotionListener(new CrearYBorrarLarge.MouseMotionListenerLardge());
        addMouseListener(new CrearYBorrarLarge.MouseListenerLarge());
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
        //time
        speed = ((end - start)/billion) * quarter + 1;
        //enemys
        enemy1.update(speed);
        enemy2.update(speed);
        enemy3.update(speed);
        enemy4.update(speed);
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
        Rectangle mouse = new Rectangle((x1 - 30), (y1 - 30), 75, 75);
        //Rectangle mouse = new Rectangle((200), (200), 50, 50);
        //enemy1
        Rectangle e1 = new Rectangle(enemy1.ex, enemy1.ey, 90, 90);
        //enemy2
        Rectangle e2 = new Rectangle(enemy2.ex, enemy2.ey, 150, 40);
        //enemy3
        Rectangle e3 = new Rectangle(enemy3.ex, enemy3.ey, 40, 150);
        //enemy4
        Rectangle e4 = new Rectangle(enemy4.ex, enemy4.ey, 90, 60);
        g.setColor(Color.RED);
        g.fillRect(mouse.x, mouse.y, mouse.width, mouse.height);
        //draw enemys
        g.setColor(Color.BLUE);
        g.fillRect(e1.x, e1.y, e1.width, e1.height);
        g.fillRect(e2.x, e2.y, e2.width, e2.height);
        g.fillRect(e3.x, e3.y, e3.width, e3.height);
        g.fillRect(e4.x, e4.y, e4.width, e4.height);
        g.drawString("" + ((end - start) / billion), 500, 60);
        //g.drawString("(" + mmx + "," + mmy + ")", 20, 20); 

        //g.drawString("Collision = " + collision + " mouseDragged = " + 
        //mouseDragged + "!", 130, 60);
        if (mouse.intersects(e1) || mouse.intersects(e2)
                || mouse.intersects(e3) || mouse.intersects(e4)) {
            //g.drawString("Collision!!", 175, 75);
            collision = true;
        }

        repaint();
    }
    
    private class EnemyLarge extends Enemy {
        
        public EnemyLarge(int startX, int startY, int startDirection) {
            super(startX, startY, startDirection);
            ex = startX;
            ey = startY;  
            x = startX;
            y = startY;
            direction = startDirection;
        }
        @Override
        public void update(double speed) {
            if (x < 25) {
                setDirection(4);
            }
            if (x > 575) {
                setDirection(2);
            }
            if (y < 25) {
                setDirection(1);
            }
            if (y > 575) {
                setDirection(3);
            }
            setCords(speed);
            ex = (int) Math.round(x);
            ey = (int) Math.round(y);
            //System.out.println("(" + ex + "," + ey + ")");
        
        }
    }
    
    public class MouseMotionListenerLardge implements MouseMotionListener {

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseDragged = false;
            mmx = e.getX();
            mmy = e.getY();
            e.consume();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mx = e.getX();
            my = e.getY();
            if (mx < 25) {
                mx = 25;
            }

            if (mx > 575) {
                mx = 575;
            }

            if (my < 25) {
                my = 25;
            }

            if (my > 575) {
                my = 575;
            }

            mouseDragged = true;
            e.consume();
        }
    }

    public class MouseListenerLarge implements MouseListener {
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getX() > 275 && e.getX() < 350 && e.getY() > 275 && 
            e.getY() < 350) {
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