
package crearyborrar;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import java.util.Random;
import javax.swing.JOptionPane;

public class CrearYBorrar extends JFrame implements Runnable {
    
    private Image dbImage;
    private Graphics dbg;
    int mx = 200;
    int my = 200;
    int x1 = 200;
    int y1 = 200;
    Enemy enemy1 = new Enemy(50, 50, 5);
    Enemy enemy2 = new Enemy(350, 50, 7);
    Enemy enemy3 = new Enemy(350, 350, 9);
    Enemy enemy4 = new Enemy(50, 350, 3);
    double start;
    double end;
    double quarter = 0.25;
    double speed = 1;
    double billion = 1000000000;
    boolean collision = false;
    static boolean mouseDragged = false;
    
    @Override
    public void run() {
        try {
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
            "Collision: You got " + ((end - start)/billion) + " seconds!" );
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
    
    public CrearYBorrar() {
        setSize(400,400);
        setTitle("CrearYBorrarClassic");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addMouseMotionListener(new MouseListenerClassic());
    }
    public void move() {
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
        Rectangle mouse = new Rectangle((x1 - 25), (y1 - 25), 50, 50);
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
        if(mouse.intersects(e1) || mouse.intersects(e2) || 
        mouse.intersects(e3) || mouse.intersects(e4)) {
            //g.drawString("Collision!!", 175, 75);
            collision = true;
        }
            
        repaint();
    }
        
    
    
    
    
    
    
    public class MouseListenerClassic implements MouseMotionListener {
        @Override
        public void mouseMoved(MouseEvent e) {
            mouseDragged = false;
            e.consume();
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            mx = e.getX();
            my = e.getY();
            if(mx < 25) {
                mx = 25;
            }
                
            if(mx > 374){
                mx = 374;
            }    
                
            if(my < 25) {
                my = 25;
            }
                
            if(my > 375) {
                my = 375;
            }
                
            mouseDragged = true;
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