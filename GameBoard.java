
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class GameBoard extends JFrame implements MouseListener, MouseMotionListener, KeyListener, WindowListener {
	private static final long serialVersionUID = 1L;
	
	/*** Hardcoded Info ***/
	public static final String NAME = "RISK: The Game of World Domination";
	public static final String[] TERRITORY_NAMES = {"Alaska", "Alberta", "Central America", "Eastern United States", "Greenland", "Northwest Territory", "Ontario", "Quebec", "Western United States", "Argentina", "Brazil", "Peru", "Venezuela", "Great Britain", "Iceland", "Northern Europe", "Scandinavia", "Southern Europe", "Ukraine", "Western Europe", "Congo", "East Africa", "Egypt", "Madagascar", "North Africa", "South Africa", "Afghanistan", "China", "India", "Irkutsk", "Japan", "Kamchatka", "Middle East", "Mongolia", "Siam", "Siberia", "Ural", "Yakutsk", "Eastern Australia", "Indonesia", "New Guinea", "Western Australia"};
	public static final boolean[][] ADJACENCY = {};
	public static final double[] LOCATIONS = {};
	public static final double MAP_HEIGHT = 0.85;
	
	/*** Color Scheme ***/
	public static final Color MAIN = Color.BLACK;
	public static final Color FONT = Color.WHITE;
	public static final Color MOUSE = new Color(50, 50, 50);
	
	/*** Private Member Variables ***/
	//BoardState
	private BoardState state;
	//Image
	private int[] imgCorner;
	private int[] imgDim;
	private Image img;
	//Controls
	private JPanel bottomControls;
	private JPanel sideControls;
	private JPanel playerStats;
	
	/*** Constructor ***/
	public GameBoard() throws IOException {
		
		//Set Up window
		super(NAME);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().setBackground(MAIN);
		setLayout(new FlowLayout());
		setVisible(true);
		
		//Set up image
		try{Thread.sleep(100);}catch(Exception e){}
		imgCorner = new int[2];
		imgDim = new int[2];
		try{
			img = ImageIO.read(new File("src/Risk-Map.png"));
			imgDim[1] = (int)(MAP_HEIGHT * (getHeight() - getInsets().top));
			imgDim[0] = imgDim[1] * img.getWidth(null) / img.getHeight(null);
			imgCorner[0] = (getWidth() + getInsets().left + getInsets().right - imgDim[0]) / 2;
			imgCorner[1] = getInsets().top;
		}catch(IIOException e){
			JOptionPane.showMessageDialog(this, "Error reading map");
			imgDim[1] = getHeight();
			imgDim[0] = 3 * getWidth() / 5;
			imgCorner[0] = (getWidth() - imgDim[0]) / 2;
			imgCorner[1] = 0;
		}
		
		//Add controls
		playerStats = new JPanel();
		sideControls = new JPanel();
		bottomControls = new JPanel();
		this.add(playerStats);
		this.add(sideControls);
		this.add(bottomControls);
		playerStats.setBackground(Color.RED);
		sideControls.setBackground(Color.GREEN);
		bottomControls.setBackground(Color.BLUE);
		playerStats.setBounds(0, 0, (getWidth() - imgDim[0]) / 2, getHeight());
		sideControls.setBounds((getWidth() - imgDim[0]) / 2 + imgDim[0], 0, (getWidth() - imgDim[0]) / 2, getHeight());
		bottomControls.setBounds((getWidth() - imgDim[0]) / 2, imgDim[1], imgDim[0], getHeight() - imgDim[1]);
		
		//Listeners
		addMouseMotionListener(this);
		addMouseListener(this);
		addKeyListener(this);
		paint(getGraphics());
		
	}
	
	/*** Draw ***/
	public void paintComponent(Graphics g){paint(g);}
	public void repaint(){paint(getGraphics());}
	public void paint(Graphics g){
		try{
			super.paint(g);
			g.drawImage(img, imgCorner[0], imgCorner[1], imgDim[0], imgDim[1], MAIN, null);
			sideControls.paint(g);
			playerStats.paint(g);
			//state.paint(g);
		}catch(Exception e){}
	}
	
	/*** Accessors & Mutators ***/
	//Accessors
	public BoardState getBoardState(){return state;}
	public int[] getImgCorner(){return imgCorner;}
	public int[] getImgDim(){return imgDim;}
	//Mutators
	public void setBoardState(BoardState bs){state = bs;}
	public void setImgCorner(int[] l){imgCorner = l;}
	public void setImgDim(int[] d){imgDim = d;}
	
	/*** Save Game ***/
	public boolean save() {
		return false;
	}

	/*** Key Response ***/
	public void keyPressed(KeyEvent arg0) {}
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0) {}

	/*** Mouse Response ***/
	public void printCoords(){
		double[] locs = {0, 0};
		locs[0] = (double)(getMousePosition().getX() - imgCorner[0]) / imgDim[0];
		locs[1] = (double)(getMousePosition().getX() - imgCorner[1]) / imgDim[1];
		JOptionPane.showMessageDialog(this, locs[0] + "     " + locs[1]);
	}
	public void mouseDragged(MouseEvent arg0) {}
	public void mouseMoved(MouseEvent arg0) {}
	public void mouseClicked(MouseEvent arg0) {printCoords();}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

	/*** Respond to window manipulation ***/
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowClosing(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
	
}
