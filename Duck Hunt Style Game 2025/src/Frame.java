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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Cursor;
import java.awt.Point;
public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	//frame size
	private int screenWidth = 900, screenHeight = 900;
	private String title = "Duck Hunt";
	
	
	//Music
	Music mouseClickSound = new Music("avi_pew.wav", false);
	Music bgMusic = new Music("bg_music.wav", true);
	
	/**
	 * Declare and instantiate (create) your objects here
	 */
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
	
	private int totalScore = 0; //needs to increment when collision happens
	private int time = 30;      //not used right now

	public void paint(Graphics pen) {
		
		//this line of code is to force redraw the entire frame
		super.paintComponent(pen);
		
		//call paint for the object
		//for objects, you call methods on them using the dot operator
		//methods use always involve parenthesis
		myBackground.paint(pen);
		myTrack.paint(pen);
		
		// drawing text on screen
		//draw numbers on screen
		Font f = new Font("Segoe UI", Font.PLAIN, 45);
		pen.setFont(f);
		pen.setColor(Color.white);
		pen.drawString(" " + totalScore, 805, 130);
		//pen.drawString("" + time, 310, 510);
		
		
		
		
		if(totalScore < 5) {
			Font a = new Font("Segoe UI", Font.PLAIN, 60);
			pen.drawString("WAVE 0" + "", 50, 800);
		}
		if(totalScore > 5) {
			Font a = new Font("Segoe UI", Font.PLAIN, 60);
			pen.drawString("WAVE 1" + "", 50, 750);
		}
		if(totalScore > 10) {
			Font a = new Font("Segoe UI", Font.PLAIN, 60);
			pen.drawString("WAVE 2" + "", 50, 700);
		}
		if(totalScore > 15) {
			Font a = new Font("Segoe UI", Font.PLAIN, 60);
			pen.drawString("WAVE 3" + "", 50, 650);
		}
		if(totalScore > 15) {
			Font a = new Font("Segoe UI", Font.PLAIN, 60);
			pen.drawString("WAVE 4" + "", 50, 600);
		}
		if(totalScore > 20) {
			pen.setColor(Color.green);
			Font a = new Font("Segoe UI", Font.PLAIN, 150);
			pen.drawString("CONGRATS!!!" + "", 300, 350);
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
	public void mouseClicked(MouseEvent mouse) {
	    // Runs when the mouse is clicked (pressed and released quickly).
	    // Example: You could use this to open a menu or select an object.
	}

	@Override
	public void mouseEntered(MouseEvent mouse) {
	    // Runs when the mouse enters the area of a component (like a button).
	    // Example: You could highlight the button when the mouse hovers over it.
	}

	@Override
	public void mouseExited(MouseEvent mouse) {
	    // Runs when the mouse leaves the area of a component.
	    // Example: You could remove the highlight when the mouse moves away.
	}

	@Override
	public void mousePressed(MouseEvent mouse) {
	    // Runs when a mouse button is pressed down.
	    // Example: You could start dragging an object here.
		System.out.println(mouse.getX()+":" + mouse.getY());
		
		
		//make the music file play each click
		this.mouseClickSound.play();
		
		
		if(ghostObject.checkCollision(mouse.getX(), mouse.getY())) {
			totalScore++;
		}
			
		if(ghost2Object.checkCollision(mouse.getX(), mouse.getY())) {
			totalScore++;
			
			
			
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent mouse) {
	    // Runs when a mouse button is released.
	    // Example: You could stop dragging the object or drop it in place.
	}



	/*
	 * This method runs automatically when a key is pressed down
	 */
	public void keyPressed(KeyEvent key) {
		
		System.out.println("from keyPressed method:"+key.getKeyCode());
		
	}

	/*
	 * This method runs when a keyboard key is released from a pressed state
	 * aka when you stopped pressing it
	 */
	public void keyReleased(KeyEvent key) {
		
	}

	/**
	 * Runs when a keyboard key is pressed then released
	 */
	public void keyTyped(KeyEvent key) {
		
		
	}
	
	
	/**
	 * The Timer animation calls this method below which calls for a repaint of the JFrame.
	 * Allows for our animation since any changes to states/variables will be reflected
	 * on the screen if those variables are being used for any drawing on the screen.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}
	
	/*
	 * Main method to create a Frame (the GUI that you see)
	 */
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


