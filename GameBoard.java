
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameBoard extends JFrame implements MouseListener, MouseMotionListener, KeyListener, WindowListener {
	private static final long serialVersionUID = 1L;
	
	/*** Hardcoded Info ***/
	public static final String NAME = "RISK: The Game of World Domination";
	public static final String[] TERRITORY_NAMES = {"Alaska", "Alberta", "Central America", "Eastern United States", "Greenland", "Northwest Territory", "Ontario", "Quebec", "Western United States", "Argentina", "Brazil", "Peru", "Venezuela", "Great Britain", "Iceland", "Northern Europe", "Scandinavia", "Southern Europe", "Ukraine", "Western Europe", "Congo", "East Africa", "Egypt", "Madagascar", "North Africa", "South Africa", "Afghanistan", "China", "India", "Irkutsk", "Japan", "Kamchatka", "Middle East", "Mongolia", "Siam", "Siberia", "Ural", "Yakutsk", "Eastern Australia", "Indonesia", "New Guinea", "Western Australia"};
	public static final boolean[][] ADJACENCY = {};
	public static final double[] LOCATIONS = {};
	
	/*** Color Scheme ***/
	public static final Color MAIN = Color.BLACK;
	public static final Color FONT = Color.WHITE;
	public static final Color MOUSE = new Color(50, 50, 50);
	
	/*** Private Member Variables ***/
	//BoardState
	private BoardState state;
	//Image
	private int[] imgCorner;
	private JLabel imgPanel;
	private ImageIcon img;
	private int[] imgDim;
	//Controls
	private JPanel playerStats;
	private JPanel controls;
	
	/*** Constructor ***/
	public GameBoard() {
		
		//Set Up window
		super(NAME);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		setLayout(null);
		
		//Add controls
		playerStats = new JPanel(new BorderLayout(BoardState.MAX_PLAYER, 1));
		controls = new JPanel();
		this.add(playerStats);
		this.add(controls);
		playerStats.setBackground(Color.BLACK);
		controls.setBackground(Color.BLACK);
		
		//Set up image
		imgCorner = new int[2];
		imgDim = new int[2];
		imgPanel = new JLabel();
		img = new ImageIcon("Risk-Map.png");
		imgDim[1] = getHeight();
		imgDim[0] = imgDim[1] * img.getIconWidth() / img.getIconWidth();
		imgCorner[0] = (getWidth() - imgDim[0]) / 2;
		imgCorner[1] = 0;
		imgPanel.setBounds(imgCorner[0], imgCorner[1], imgCorner[0] + imgDim[0], imgCorner[1] + imgDim[1]);
		imgPanel.setIcon(img);
		add(imgPanel);
		
		//Listeners
		addMouseMotionListener(this);
		addMouseListener(this);
		addKeyListener(this);
		
	}
	
	/*** Draw ***/
	public void paint(Graphics g){
		super.paint(g);
		imgPanel.paint(g);
		controls.paint(g);
		playerStats.paint(g);
		//state.paint(g);
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
	public void mouseDragged(MouseEvent arg0) {}
	public void mouseMoved(MouseEvent arg0) {}
	public void mouseClicked(MouseEvent arg0) {}
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
