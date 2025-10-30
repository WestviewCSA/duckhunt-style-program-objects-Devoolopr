import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Image;
import java.awt.Cursor;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {

    private int screenWidth = 900, screenHeight = 900;
    private String title = "Duck Hunt";

    Music mouseClickSound = new Music("avi_pew.wav", false);
    Music bgMusic = new Music("bg_music.wav", true);
    Music waveSound = new Music("sfx_wpn_laser9.wav", false);

    private ghost ghostObject = new ghost();
    private ghost2 ghost2Object = new ghost2();
    private Background myBackground = new Background();
    private theTrack myTrack = new theTrack();
    private pacman myPacman = new pacman();
    private fruits myFruit = new fruits();
    private fruits2 myFruit2 = new fruits2();
    private strawberry myStrawberry = new strawberry();
    private MyCursor cursor = new MyCursor();
    private score scorecount = new score();

    private int totalScore = 0;
    private int currentWave = 0;

    public void paint(Graphics pen) {
        super.paintComponent(pen);

        myBackground.paint(pen);
        myTrack.paint(pen);

        Font f = new Font("Segoe UI", Font.PLAIN, 45);
        pen.setFont(f);
        pen.setColor(Color.white);
        pen.drawString(" " + totalScore, 805, 130);

        int newWave = 0;
        if (totalScore < 5) newWave = 0;
        else if (totalScore < 10) newWave = 1;
        else if (totalScore < 15) newWave = 2;
        else if (totalScore < 20) newWave = 3;
        else newWave = 4;

        if (newWave != currentWave) {
            currentWave = newWave;
            waveSound.play();
            double speedMultiplier = 1 + newWave * 0.25;
            int newVX1 = (int)(ghostObject.getVXBase() * speedMultiplier);
            int newVY1 = (int)(ghostObject.getVYBase() * speedMultiplier);
            int newVX2 = (int)(ghost2Object.getVXBase() * speedMultiplier);
            int newVY2 = (int)(ghost2Object.getVYBase() * speedMultiplier);
            ghostObject.setVelocityVariables(newVX1, newVY1);
            ghost2Object.setVelocityVariables(newVX2, newVY2);
        }

        if (newWave < 4) {
            Font a = new Font("Segoe UI", Font.PLAIN, 60);
            pen.drawString("WAVE " + newWave, 50, 800 - (newWave * 50));
        } else {
            pen.setColor(Color.green);
            Font a = new Font("Segoe UI", Font.PLAIN, 70);
            pen.setFont(a);
            pen.drawString("CONGRATS!!!", 300, 350);
        }

        myFruit.paint(pen);
        myStrawberry.paint(pen);
        myPacman.paint(pen);
        ghost2Object.paint(pen);
        ghostObject.paint(pen);
        scorecount.paint(pen);
        cursor.paint(pen);
    }

    @Override
    public void mouseClicked(MouseEvent mouse) {}

    @Override
    public void mouseEntered(MouseEvent mouse) {}

    @Override
    public void mouseExited(MouseEvent mouse) {}

    @Override
    public void mousePressed(MouseEvent mouse) {
        System.out.println(mouse.getX()+":" + mouse.getY());
        this.mouseClickSound.play();

        if(ghostObject.checkCollision(mouse.getX(), mouse.getY())) {
            totalScore++;
            myPacman.spin();
        }

        if(ghost2Object.checkCollision(mouse.getX(), mouse.getY())) {
            totalScore++;
            myPacman.spin();
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouse) {}

    public void keyPressed(KeyEvent key) {
        System.out.println("from keyPressed method:"+key.getKeyCode());
    }

    public void keyReleased(KeyEvent key) {}

    public void keyTyped(KeyEvent key) {}

    @Override
    public void actionPerformed(ActionEvent arg0) {
        repaint();
    }

    public static void main(String[] arg) {
        new Frame();
    }

    public Frame() {
        JFrame f = new JFrame(title);
        f.setSize(new Dimension(screenWidth, screenHeight));
        f.setBackground(Color.blue);
        f.add(this);
        f.setResizable(false);
        f.setLayout(new GridLayout(1,2));
        f.addMouseListener(this);
        f.addKeyListener(this);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("/imgs/Crosshair.gif");
        Cursor a = toolkit.createCustomCursor(image, new Point(this.getX(), this.getY()), "");
        this.setCursor (a);

        Timer t = new Timer(16, this);
        t.start();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        this.bgMusic.play();
    }
}
