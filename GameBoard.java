
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class GameBoard extends JFrame implements MouseListener, MouseMotionListener, WindowListener {
	private static final long serialVersionUID = 1L;
	
	/*** Hardcoded Info ***/
	public static final String NAME = "RISK: The Game of World Domination";
	public static final String[] TERRITORY_NAMES = {"Alaska", "Alberta", "Central America", "Eastern United States", "Greenland", "Northwest Territory", "Ontario", "Quebec", "Western United States", "Argentina", "Brazil", "Peru", "Venezuela", "Great Britain", "Iceland", "Northern Europe", "Scandinavia", "Southern Europe", "Ukraine", "Western Europe", "Congo", "East Africa", "Egypt", "Madagascar", "North Africa", "South Africa", "Afghanistan", "China", "India", "Irkutsk", "Japan", "Kamchatka", "Middle East", "Mongolia", "Siam", "Siberia", "Ural", "Yakutsk", "Eastern Australia", "Indonesia", "New Guinea", "Western Australia"};
	public static final boolean[][] ADJACENCY = {};
	public static final double[][] LOCATIONS = {{0.072, 0.185}, {0.161, 0.2727}, {0.1834, 0.471}, {0.242, 0.389}, {0.3895, 0.1263}, {0.15596, 0.1948}, {0.226, 0.2834}, {0.307, 0.28}, {0.1675, 0.3636}};
	public static final double MAP_HEIGHT = 0.82;
	public static final double PAD = 0.01;
	
	/*** Color Scheme ***/
	public static final Color MAIN = Color.BLACK;
	public static final Color FONT = Color.WHITE;
	public static final Color MOUSE = new Color(75, 75, 75);
	
	/*** Private Member Variables ***/
	//Image
	public int[] screenDim;
	private int[] imgCorner;
	private int[] imgDim;
	private Image img;
	//Controls
	private JPanel bottomControls;
	private JPanel sideControls;
	private JPanel playerStats;
	//Mouse manipulation
	private Territory moused;
	
	/*** Constructor ***/
	public GameBoard() throws IOException {
		
		//Create window
		super(NAME);
		setLayout(null);
		
		//Set up image
		screenDim = new int[2];
		imgCorner = new int[2];
		imgDim = new int[2];
		screenDim[0] = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		screenDim[1] = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		loadImage();
		
		//Set up game
		BoardState.BOARD = this;
		BoardState.startGame();
		playerStats = new JPanel(new GridLayout(BoardState.MAX_PLAYER, 1));
		for(Player p: BoardState.players){
			p.updateStatDisplay();
			playerStats.add(p.getStats());
		}
		
		//Add controls
		sideControls = new JPanel();
		bottomControls = new JPanel();
		playerStats.setBackground(MAIN);
		sideControls.setBackground(Color.GREEN);
		bottomControls.setBackground(Color.BLUE);
		add(playerStats);
		add(sideControls);
		add(bottomControls);
		
		//Listeners
		addMouseMotionListener(this);
		addWindowListener(this);
		addMouseListener(this);
		
		//Window settings
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		getContentPane().setBackground(MAIN);
		resetImage();
		resetControls();
		setVisible(true);
		setState(Frame.ICONIFIED);
		setState(Frame.NORMAL);
		repaint();
		
	}
	
	/*** Resize Panels ***/
	private void resetControls(){
		setLayout(null);
		playerStats.setBounds(0, 0, (screenDim[0] - imgDim[0]) / 2, screenDim[1] - getInsets().top - getInsets().bottom);
		sideControls.setBounds((screenDim[0] - imgDim[0]) / 2 + imgDim[0], 0, (screenDim[0] - imgDim[0]) / 2, screenDim[1] - getInsets().top - getInsets().bottom);
		bottomControls.setBounds((screenDim[0] - imgDim[0]) / 2, imgDim[1], imgDim[0], screenDim[1] - imgDim[1]);
		//playerStats.setBounds(getInsets().left, getInsets().top, (screenDim[0] - imgDim[0] + getInsets().left + getInsets().right) / 2, screenDim[1] - getInsets().top);
		//sideControls.setBounds((screenDim[0] - imgDim[0] - getInsets().left - getInsets().right) / 2 + imgDim[0], getInsets().top, (screenDim[0] - imgDim[0]  + getInsets().left + getInsets().right) / 2, screenDim[1] - getInsets().top);
		//bottomControls.setBounds((screenDim[0] - imgDim[0] + getInsets().left + getInsets().right) / 2, imgDim[1] + getInsets().top, imgDim[0], screenDim[1] - imgDim[1] - getInsets().top);
	}
	
	/*** Load and Resize Image ***/
	private void loadImage() throws IOException{
		try{img = ImageIO.read(new File("src/Risk-Map.png"));}
		catch(IIOException e){JOptionPane.showMessageDialog(this, "Error reading map");}
	}
	private void resetImage(){
		if(img == null){
			JOptionPane.showMessageDialog(this, "Error: Image not loaded");
			return;
		}
		imgDim[1] = (int)(MAP_HEIGHT * (screenDim[1] - getInsets().top));
		imgDim[0] = imgDim[1] * img.getWidth(null) / img.getHeight(null);
		imgCorner[0] = (screenDim[0] + getInsets().left + getInsets().right - imgDim[0]) / 2;
		imgCorner[1] = getInsets().top;
	}
	
	/*** Draw ***/
	public void paintImage(Graphics g){
		resetImage();
		try{
			g.drawImage(img, imgCorner[0], imgCorner[1], imgDim[0], imgDim[1], MAIN, null);
		}catch(Exception e){JOptionPane.showMessageDialog(this, "Map Drawing Error: " + e);}
	}
	public void paintComponent(Graphics g){repaint();}
	public void repaint(){drawGame(getGraphics());}
	public void paint(Graphics g){}
	public void drawGame(Graphics g){
		resetImage();
		resetControls();
		//g.fillRect(bottomControls.getBounds().x, bottomControls.getBounds().y, bottomControls.getBounds().width, bottomControls.getBounds().height);
		super.paint(g);
		//bottomControls.paint(getGraphics());
		//sideControls.paint(getGraphics());
		//playerStats.paint(getGraphics());
		paintImage(g);
		BoardState.paint(g);
	}
	
	/*** Accessors & Mutators ***/
	//Accessors
	public int[] getImgCorner(){return imgCorner;}
	public int[] getImgDim(){return imgDim;}
	//Mutators
	public void setImgCorner(int[] l){imgCorner = l;}
	public void setImgDim(int[] d){imgDim = d;}
	
	/*** Save Game ***/
	public boolean save() {
		return false;
	}

	/*** Mouse Response ***/
	public void printCoords(){
		double[] locs = {0, 0};
		locs[0] = (double)(getMousePosition().getX() - imgCorner[0]) / imgDim[0];
		locs[1] = (double)(getMousePosition().getY() - imgCorner[1]) / imgDim[1];
		JOptionPane.showMessageDialog(this, locs[0] + "     " + locs[1]);
	}
	public void mouseDragged(MouseEvent arg0) {}
	public void mouseMoved(MouseEvent arg0) {

		//Check territories
		if(BoardState.territories == null)
			return;
		Territory tMouse = null;
		for(Territory t: BoardState.territories)
			if(Math.sqrt(Math.pow(getMousePosition().getX() - t.getCLocX(), 2) + Math.pow(getMousePosition().getY() - t.getCLocY(), 2)) <= Territory.RAD())
				tMouse = t;
		if(tMouse != moused){
			if(moused != null)
				moused.setColor(Territory.BASE_COL);
			if(tMouse != null)
				tMouse.setColor(Territory.MOUSE_COL);
		}
		moused = tMouse;		
		
	}
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
	public void windowDeiconified(WindowEvent arg0) {repaint();}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
	
}
