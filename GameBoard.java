
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class GameBoard extends JFrame implements MouseListener, MouseMotionListener, WindowListener, ActionListener {
	private static final long serialVersionUID = 1L;
	
	/*** Hardcoded Info ***/
	public static final String NAME = "RISK: The Game of World Domination";
	public static final String[] TERRITORY_NAMES = {"Alaska", "Alberta", "Central America", "Eastern United States", "Greenland", "Northwest Territory", "Ontario", "Quebec", "Western United States", "Argentina", "Brazil", "Peru", "Venezuela", "Great Britain", "Iceland", "Northern Europe", "Scandinavia", "Southern Europe", "Ukraine", "Western Europe", "Congo", "East Africa", "Egypt", "Madagascar", "North Africa", "South Africa", "Afghanistan", "China", "India", "Irkutsk", "Japan", "Kamchatka", "Middle East", "Mongolia", "Siam", "Siberia", "Ural", "Yakutsk", "Eastern Australia", "Indonesia", "New Guinea", "Western Australia"};
	public static final boolean[][] ADJACENCY = {};
	public static final double[][] LOCATIONS = {{0.072, 0.185}, {0.161, 0.2727}, {0.1834, 0.471}, {0.242, 0.389}, {0.3895, 0.1263}, {0.15596, 0.1948}, {0.226, 0.2834}, {0.307, 0.28}, {0.1675, 0.3636}, {0.245, 0.82}, {0.3, 0.68}, {0.245, 0.718}, {0.224, 0.583}, {0.425, 0.313}, {0.422, 0.2245}, {0.469, 0.337}, {0.504, 0.167}, {0.504, 0.405}, {0.567, 0.275}, {0.42, 0.438}, {0.493, 0.727}, {0.54, 0.66}, {0.5, 0.535}, {0.573, 0.845}, {0.425, 0.597}, {0.493, 0.86}, {0.644, 0.37}, {0.75, 0.446}, {0.67, 0.52}, {0.792, 0.287}, {0.88, 0.4}, {0.93, 0.193}, {0.574, 0.479}, {0.821, 0.36}, {0.764, 0.56}, {0.731, 0.233}, {0.663, 0.25}, {0.832, 0.191}, {0.876, 0.835}, {0.787, 0.685}, {0.892, 0.697}, {0.782, 0.831}};
	public static final double MAP_HEIGHT = 0.8;
	public static final double INFO_WIDTH = 0.6;
	public static final double PAD = 0.01;
	
	/*** Color Scheme ***/
	public static final Color MAIN = Color.BLACK;
	public static final Color FONT = Color.WHITE;
	public static final Color MOUSE = Color.DARK_GRAY;
	
	/*** Painting settings ***/
	public boolean showCount;
	public boolean showGraph;
	
	/*** Private Member Variables ***/
	//Image
	public int[] screenDim;
	private int[] imgCorner;
	private int[] imgDim;
	private Image img;
	//Controls
	private String[][] bottomButtonNames = {{"Hide Troop Count", "Show Troop Count"}, {"Show Adjacency Graph", "Hide Adjacency Graph"}, {"Button 3"}, {"End Phase"}, {"Show Cards", "Hide Cards"}, {"Button 6"}};
	private JPanel bottomControls;
	private JPanel sideControls;
	private JPanel playerStats;
	private JPanel[] holders;
	private JButton[] bottomButtons;
	private JTextArea infoDisplay;
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
		showCount = true;
		showGraph = false;
		BoardState.BOARD = this;
		BoardState.startGame();
		playerStats = new JPanel(new GridLayout(BoardState.MAX_PLAYER + 1, 1));
		for(Player p: BoardState.players){
			p.updateStatDisplay();
			playerStats.add(p.getStats());
		}
		
		//Add controls
		sideControls = new JPanel();
		bottomControls = new JPanel();
		holders = new JPanel[2];
		bottomButtons = new JButton[6];
		holders[0] = new JPanel(new GridLayout(bottomButtons.length / 2, 1));
		holders[1] = new JPanel(new GridLayout(bottomButtons.length / 2, 1));
		infoDisplay = new JTextArea();
		for(int i = 0; i < bottomButtons.length; i++){
			bottomButtons[i] = new myButton(bottomButtonNames[i], MAIN, MOUSE);
			bottomButtons[i].setForeground(FONT);
			bottomButtons[i].setBorder(BorderFactory.createEmptyBorder());
			bottomButtons[i].setFont(new Font("Consolas", Font.PLAIN, 3 * bottomButtons[i].getFont().getSize() / 2));
			holders[i / (bottomButtons.length / 2)].add(bottomButtons[i]);
		}
		infoDisplay.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		infoDisplay.setBackground(MAIN);
		infoDisplay.setForeground(FONT);
		infoDisplay.setMargin(new Insets(50, 50, 50, 50));
		infoDisplay.setFont(new Font("Consolas", Font.PLAIN, 2 * infoDisplay.getFont().getSize()));
		bottomControls.setLayout(null);
		bottomControls.add(holders[0]);
		bottomControls.add(holders[1]);
		bottomControls.add(infoDisplay);
		playerStats.setBackground(MAIN);
		sideControls.setBackground(Color.GREEN);
		bottomControls.setBackground(MAIN);
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
		setUndecorated(true);
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
		playerStats.setBounds((int)(screenDim[1] * PAD), (int)(screenDim[1] * PAD), (screenDim[0] - imgDim[0]) / 2 - (int)(screenDim[1] * PAD), screenDim[1] - getInsets().top - getInsets().bottom - 2 * (int)(screenDim[1] * PAD));
		sideControls.setBounds((screenDim[0] - imgDim[0]) / 2 + imgDim[0], 0, (screenDim[0] - imgDim[0]) / 2, screenDim[1] - getInsets().top - getInsets().bottom);
		bottomControls.setBounds((screenDim[0] - imgDim[0]) / 2, imgDim[1], imgDim[0], screenDim[1] - imgDim[1]);
		holders[0].setBounds(0, 0, (int)(bottomControls.getBounds().width * (1 - INFO_WIDTH) / 2), bottomControls.getBounds().height);
		holders[1].setBounds(bottomControls.getBounds().width - holders[0].getBounds().width, 0, holders[0].getBounds().width, bottomControls.getBounds().height);
		infoDisplay.setBounds(holders[0].getBounds().width, 0, (int)(bottomControls.getBounds().width * INFO_WIDTH), bottomControls.getBounds().height);
		infoDisplay.setFont(new Font(infoDisplay.getFont().getName(), infoDisplay.getFont().getStyle(), infoDisplay.getBounds().height / 10));
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
		super.paint(g);
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
		
		//Check players
		if(getMousePosition().getX() < imgCorner[0]){
			int i = (int)(getMousePosition().getY() / (screenDim[1] / (BoardState.MAX_PLAYER + 1)));
			if(i < BoardState.players.length)
				infoDisplay.setText(BoardState.players[i].fullStats());
			else
				infoDisplay.setText("");
		}
		else if(moused == null)
			infoDisplay.setText("");

		//Check territories
		Territory tMouse = null;
		for(Territory t: BoardState.territories)
			if(Math.sqrt(Math.pow(getMousePosition().getX() - t.getCLocX(), 2) + Math.pow(getMousePosition().getY() - t.getCLocY(), 2)) <= Territory.RAD())
				tMouse = t;
		if(tMouse != moused){
			if(moused != null)
				moused.setColor(Territory.BASE_COL);
			if(tMouse != null){
				tMouse.setColor(Territory.MOUSE_COL);
				infoDisplay.setText(tMouse.fullStats());
			}
			else
				infoDisplay.setText("");
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

	/*** Buttons ***/
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == bottomButtons[0]){
			showCount = !showCount;
			paintImage(getGraphics());
			BoardState.paint(getGraphics());
		}
		
	}
	
}
