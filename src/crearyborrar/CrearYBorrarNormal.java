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

public class CrearYBorrarNormal extends JFrame implements Runnable {
    //important graphics stuff
    private Image dbImage;
    private Graphics dbg;
    //variables
    int mx = 200; //mouse dragged positions
    int my = 200; 
    int x1 = 200; //positions of red rectangle
    int y1 = 200;
    int mmy = 0; //mouse moved positions
    int mmx = 0;
    //Set up enemy AI
    Enemy enemy1 = new Enemy(20, 20, 5);
    Enemy enemy2 = new Enemy(300, 40, 7);
    Enemy enemy3 = new Enemy(340, 300, 9);
    Enemy enemy4 = new Enemy(20, 330, 3);
    //setup highscore for saving highscore
    HighScoreList hsl = new HighScoreList();
    //stuff related to time tracking
    double start;
    double end;
    double quarter = 0.25;
    double speed = 1;
    double billion = 1000000000;
    //stuff related to conditions
    boolean collision = false;
    boolean mousePressed = false;
    boolean mouseReleased = false;
    static boolean mouseDragged = false;
    

    @Override
    public void run() { //main executable method
        try {
            while (mousePressed == false) {
                //System.out.println("MousePressed=False");
                Thread.sleep(20);
            }
            start = System.nanoTime();
            end = System.nanoTime();
            while (true) {
                while (collision == false) { //no collision = update
                    move();
                    end = System.nanoTime();
                    Thread.sleep(20);
                }
                while (collision == true) { //game over
                    end = System.nanoTime();
                    break;
                }
                break;
            }
            //[Console Mode \/]
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
            hsl.save("normal.txt");
            // and quit
            super.dispose();
        } catch (Exception e) { // if not able to execute, crash
            System.out.println("Error");
        }
    }

    public CrearYBorrarNormal() { //setups frame
        setSize(400, 400);
        setTitle("CrearYBorrarNormal");
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addMouseMotionListener(new MouseMotionListenerNormal());
        addMouseListener(new MouseListenerNormal());
        
    }

    public void move() { //update positions
        if (mouseReleased == true) { //make sure mouse is pressed
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
        //[\/Debug Code]
        //System.out.println(((end - start)/billion));
        //System.out.println("end =" + end);
        //System.out.println("start = " + start);

    }

    @Override
    public void paint(Graphics g) { //graphics
        dbImage = createImage(getWidth(), getHeight());
        dbg = dbImage.getGraphics();
        paintComponent(dbg);
        g.drawImage(dbImage, 0, 0, this);
    }

    public void paintComponent(Graphics g) {
        //setup mouse
        Rectangle mouse = new Rectangle((x1 - 25), (y1 - 25), 50, 50);
        //setup enemys
        Rectangle e1 = new Rectangle(enemy1.ex, enemy1.ey, 60, 60);
        Rectangle e2 = new Rectangle(enemy2.ex, enemy2.ey, 100, 20);
        Rectangle e3 = new Rectangle(enemy3.ex, enemy3.ey, 20, 100);
        Rectangle e4 = new Rectangle(enemy4.ex, enemy4.ey, 60, 40);
        //set color
        g.setColor(Color.RED);
        //draw mouse
        g.fillRect(mouse.x, mouse.y, mouse.width, mouse.height);
        //change color
        g.setColor(Color.BLUE);
        //draw enemys
        g.fillRect(e1.x, e1.y, e1.width, e1.height);
        g.fillRect(e2.x, e2.y, e2.width, e2.height);
        g.fillRect(e3.x, e3.y, e3.width, e3.height);
        g.fillRect(e4.x, e4.y, e4.width, e4.height);
        //draw time
        g.drawString("" + ((end - start) / billion), 300, 20);
        //[\/ Debug code]
        //g.drawString("(" + mmx + "," + mmy + ")", 20, 20); 
        //g.drawString("Collision = " + collision + " mouseDragged = " + 
        //mouseDragged + "!", 130, 60);
        if (mouse.intersects(e1) || mouse.intersects(e2) //check for collision
                || mouse.intersects(e3) || mouse.intersects(e4)) {
            //g.drawString("Collision!!", 175, 75);
            collision = true;
        }

        repaint(); //repeat!
    }
    



    public class MouseMotionListenerNormal implements MouseMotionListener {

        @Override
        public void mouseMoved(MouseEvent e) { //when mouse is moved
            mouseDragged = false;
            mmx = e.getX();
            mmy = e.getY();
            e.consume();
        }

        @Override
        public void mouseDragged(MouseEvent e) { //when mouse is dragged
            mx = e.getX();
            my = e.getY();
            //check borders
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

    public class MouseListenerNormal implements MouseListener {
        @Override
        public void mousePressed(MouseEvent e) { //starts game when red square is clicked
            if(e.getX() > 175 && e.getX() < 225 && e.getY() > 175 && 
            e.getY() < 225) {
                mousePressed = true;
            }
            e.consume();
            
            
        }
        @Override
        public void mouseReleased(MouseEvent e) { //
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
        //Uncomented, this code would allow you to run the class by itself.
        //CrearYBorrar cyb = new CrearYBorrar();
        //Threads
        //Thread t1 = new Thread(this);
        //t1.start();
    }
}