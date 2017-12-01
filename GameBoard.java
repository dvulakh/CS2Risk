
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class GameBoard extends JFrame implements MouseListener, MouseMotionListener, WindowListener, ActionListener {
	private static final long serialVersionUID = 1L;
	
	/*** Hardcoded Info ***/
	public static final long L = 1; 
	public static final String NAME = "RISK: The Game of World Domination";
	public static final String[] TERRITORY_NAMES = {"Alaska", "Alberta", "Central America", "Eastern United States", "Greenland", "Northwest Territory", "Ontario", "Quebec", "Western United States", "Argentina", "Brazil", "Peru", "Venezuela", "Great Britain", "Iceland", "Northern Europe", "Scandinavia", "Southern Europe", "Ukraine", "Western Europe", "Congo", "East Africa", "Egypt", "Madagascar", "North Africa", "South Africa", "Afghanistan", "China", "India", "Irkutsk", "Japan", "Kamchatka", "Middle East", "Mongolia", "Siam", "Siberia", "Ural", "Yakutsk", "Eastern Australia", "Indonesia", "New Guinea", "Western Australia"};
	public static final int[] CONTINENT = {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5};
	public static final int[] CONTINENT_COUNT = {7, 4, 9, 6, 12, 4};
	public static final int[] CONTINENT_BONUS = {5, 2, 5, 3, 7, 2};
	public static final String[] CONTINENT_NAME = {"North America", "South America", "Europe", "Africa", "Asia", "Australia"};
	public static final double[][] LOCATIONS = {
			{0.072, 0.185},
			{0.161, 0.2727},
			{0.1834, 0.471},
			{0.242, 0.389},
			{0.3895, 0.1263},
			{0.15596, 0.1948},
			{0.226, 0.2834},
			{0.307, 0.28},
			{0.1675, 0.3636},
			{0.245, 0.82},
			{0.3, 0.68},
			{0.245, 0.718},
			{0.224, 0.583},
			{0.425, 0.313},
			{0.422, 0.2245},
			{0.469, 0.337},
			{0.504, 0.167},
			{0.504, 0.405},
			{0.567, 0.275},
			{0.42, 0.438},
			{0.493, 0.727},
			{0.54, 0.66},
			{0.5, 0.535},
			{0.573, 0.845},
			{0.425, 0.597},
			{0.493, 0.86},
			{0.644, 0.37},
			{0.75, 0.446},
			{0.67, 0.52},
			{0.792, 0.287},
			{0.88, 0.4},
			{0.93, 0.193},
			{0.574, 0.479},
			{0.821, 0.36},
			{0.764, 0.56},
			{0.731, 0.26},
			{0.663, 0.25},
			{0.832, 0.191},
			{0.876, 0.835},
			{0.787, 0.685},
			{0.892, 0.697},
			{0.782, 0.831}};
	public static final double[][][] FILL_LOCS = {
			{{0.072, 0.185}, {0.049193, 0.25116}, {0.102998, 0.25116}, {0.10684, 0.2581}, {0.1091, 0.2685}},
			{{0.161, 0.2727}, {0.1299, 0.3125}},
			{{0.1834, 0.471}},
			{{0.242, 0.389}, {0.277, 0.483}, {0.265, 0.4896}},
			{{0.3895, 0.1263}, {0.312, 0.045}, {0.2698, 0.1053}, {0.3075, 0.1644}, {0.3397, 0.1828}, {0.2913, 0.1817}, {0.241, 0.185}, {0.2667, 0.2060}, {0.2644, 0.22685}, {0.27133, 0.2269}, {0.2475, 0.1169}, {0.2329, 0.10764}, {0.2352, 0.1342}, {0.2014, 0.1447}, {0.2329, 0.07639}, {0.22214, 0.08323}, {0.2121, 0.1053}, {0.1652, 0.1238}, {0.1868, 0.09954}},
			{{0.15596, 0.1948}},
			{{0.226, 0.2834}},
			{{0.307, 0.28}, {0.349, 0.3125}, {0.328, 0.31597}},
			{{0.1675, 0.3636}, {0.058, 0.449}},
			{{0.245, 0.82}, {0.289, 0.939}, {0.3028, 0.931}},
			{{0.3, 0.68}, {0.3075, 0.6146}},
			{{0.245, 0.718}},
			{{0.224, 0.583}},
			{{0.4135, 0.316}, {0.432, 0.324}},
			{{0.422, 0.2245}},
			{{0.469, 0.337}},
			{{0.504, 0.167}, {0.534, 0.08}, {0.545, 0.066}, {0.5465, 0.0868}, {0.4896, 0.2905}, {0.4835, 0.2951}},
			{{0.504, 0.405}, {0.4789, 0.4549}, {0.4612, 0.4294}, {0.508, 0.471}},
			{{0.567, 0.275}, {0.601, 0.1}, {0.595, 0.119}, {0.619, 0.146}},
			{{0.42, 0.438}, {0.46195, 0.4144}},
			{{0.493, 0.727}},
			{{0.54, 0.66}, {0.6057, 0.6424}},
			{{0.5, 0.535}},
			{{0.573, 0.845}},
			{{0.425, 0.597}},
			{{0.493, 0.86}},
			{{0.644, 0.37}},
			{{0.75, 0.446}, {0.8217, 0.5093}, {0.7879, 0.5382}},
			{{0.67, 0.52}, {0.6949, 0.669}},
			{{0.792, 0.287}},
			{{0.88, 0.4}, {0.8862, 0.3542}, {0.8779, 0.3611}, {0.8693, 0.4271}, {0.8601, 0.4375}},
			{{0.93, 0.193}, {0.92, 0.1539}, {0.937, 0.145}, {0.875, 0.3137}},
			{{0.574, 0.479}, {0.5365, 0.478}},
			{{0.821, 0.36}},
			{{0.764, 0.56}},
			{{0.731, 0.26}, {0.7025, 0.0845}, {0.7164, 0.0868}, {0.7325, 0.0938}, {0.7694, 0.1296}, {0.707, 0.0718}},
			{{0.663, 0.25}},
			{{0.832, 0.191}, {0.8255, 0.1181}, {0.8355, 0.1146}, {0.8432, 0.1169}, {0.8401, 0.1273}},
			{{0.876, 0.835}, {0.935, 0.833}, {0.952, 0.815}},
			{{0.787, 0.685}, {0.8493, 0.559}, {0.85857, 0.5926}, {0.8524, 0.5903}, {0.8324, 0.5926}, {0.8286, 0.662}, {0.8078, 0.6771}, {0.7594, 0.6898}, {0.7663, 0.6748}, {0.7717, 0.6875}, {0.8317, 0.6863}, {0.8055, 0.7118}, {0.837, 0.7025}, {0.8355, 0.7037}, {0.8224, 0.7106}},
			{{0.892, 0.697}, {0.917, 0.698}, {0.93, 0.685}},
			{{0.782, 0.831}}};
	public static final long[] ADJACENCY = {
			(long)0 | L << 1 | L << 5 | L << 31,
			(long)0 | L | L << 5 | L << 6 | L << 8,
			(long)0 | L << 3 | L << 8 | L << 12,
			(long)0 | L << 2 | L << 6 | L << 7 | L << 8,
			(long)0 | L << 5 | L << 6 | L << 7 | L << 14,
			(long)0 | L | L << 1 | L << 4 | L << 6,
			(long)0 | L << 1 | L << 3 | L << 4 | L << 5 | L << 7 | L << 8,
			(long)0 | L << 3 | L << 4 | L << 6, 
			(long)0 | L << 1 | L << 2 | L << 3 | L << 6,
			(long)0 | L << 10 | L << 11,
			(long)0 | L << 9 | L << 11 | L << 12 | L << 24,
			(long)0 | L << 9 | L << 10 | L << 12,
			(long)0 | L << 2 | L << 10 | L << 11,
			(long)0 | L << 14 | L << 15 | L << 16 | L << 19,
			(long)0 | L << 4 | L << 13 | L << 16,
			(long)0 | L << 13 | L << 16 | L << 17 | L << 18 | L << 19,
			(long)0 | L << 13 | L << 14 | L << 15 | L << 18,
			(long)0 | L << 15 | L << 18 | L << 19 | L << 22 | L << 24 | L << 32,
			(long)0 | L << 15 | L << 16 | L << 17 | L << 26 | L << 32 | L << 36,
			(long)0 | L << 13 | L << 15 | L << 17 | L << 24,
			(long)0 | L << 21 | L << 24 | L << 25,
			(long)0 | L << 20 | L << 22 | L << 23 | L << 24 | L << 25 | L << 32,
			(long)0 | L << 17 | L << 21 | L << 24 | L << 32,
			(long)0 | L << 21 | L << 25,
			(long)0 | L << 10 | L << 17 | L << 19 | L << 20 | L << 21 | L << 22,
			(long)0 | L << 20 | L << 21 | L << 23,
			(long)0 | L << 18 | L << 27 | L << 28 | L << 32 | L << 36,
			(long)0 | L << 26 | L << 28 | L << 33 | L << 34 | L << 35 | L << 36,
			(long)0 | L << 26 | L << 27 | L << 32 | L << 34,
			(long)0 | L << 31 | L << 33 | L << 35 | L << 37,
			(long)0 | L << 31 | L << 33,
			(long)0 | L | L << 30 | L << 33 | L << 29 | L << 37,
			(long)0 | L << 17 | L << 18 | L << 21 | L << 22 | L << 26 | L << 28,
			(long)0 | L << 27 | L << 29 | L << 30 | L << 35 | L << 31,
			(long)0 | L << 27 | L << 28 | L << 39,
			(long)0 | L << 27 | L << 29 | L << 33 | L << 36 | L << 37,
			(long)0 | L << 18 | L << 26 | L << 27 | L << 35,
			(long)0 | L << 29 | L << 31 | L << 35,
			(long)0 | L << 40 | L << 41,
			(long)0 | L << 34 | L << 40 | L << 41,
			(long)0 | L << 39 | L << 38, 
			(long)0 | L << 38 | L << 39};
	public static final double MAP_HEIGHT = 0.8;
	public static final double INFO_WIDTH = 0.6;
	public static final double INFO_HEIGHT = 0.8;
	public static final double CONSOLE_HEIGHT = 0.8 - 0.2 / 3;
	public static final double PAD = 0.01;
	
	/*** Color Scheme ***/
	public static final Color MAIN = Color.BLACK;
	public static final Color LINE = Color.BLACK;
	public static final Color FONT = Color.WHITE;
	public static final Color MOUSE = Color.DARK_GRAY;
	
	/*** Painting settings ***/
	public boolean showCount;
	public boolean showGraph;
	public boolean flood;
	
	/*** Private Member Variables ***/
	//Image
	public int[] screenDim;
	private int[] imgCorner;
	private int[] imgDim;
	private BufferedImage img;
	//Controls
	private String[][] bottomButtonNames = {{"Hide Troop Count", "Show Troop Count"}, {"Show Adjacency Graph", "Hide Adjacency Graph"}, {"Fill by Continent", "Fill by Occupant"}, {"End Phase"}, {"Show Cards", "Hide Cards"}, {"Button 6"}};
	private String[][] rightButtonNames = {{"Pause", "Unpause"}, {"Load"}, {"Save"}, {"Exit"}};
	private JPanel bottomControls;
	private JPanel sideControls;
	private JPanel playerStats;
	private JPanel[] holders;
	private JButton[] bottomButtons;
	private JPanel rightHolder;
	private JButton[] rightButtons;
	private JTextArea infoDisplay;
	private JLabel turnDisplay;
	private Console sideConsole;
	//Mouse manipulation
	private Territory moused;
	private Territory clicked;
	
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
		flood = true;
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
		infoDisplay.setEditable(false);
		sideConsole = new Console();
		turnDisplay = new JLabel();
		for(int i = 0; i < bottomButtons.length; i++){
			bottomButtons[i] = new myButton(bottomButtonNames[i], MAIN, MOUSE);
			bottomButtons[i].setForeground(FONT);
			bottomButtons[i].setBorder(BorderFactory.createEmptyBorder());
			bottomButtons[i].setFont(new Font("Consolas", Font.PLAIN, 3 * bottomButtons[i].getFont().getSize() / 2));
			holders[i / (bottomButtons.length / 2)].add(bottomButtons[i]);
		}
		turnDisplay.setHorizontalAlignment(SwingConstants.CENTER);
		turnDisplay.setForeground(FONT);
		turnDisplay.setBorder(BorderFactory.createLineBorder(FONT));
		infoDisplay.setBorder(BorderFactory.createLineBorder(FONT));
		infoDisplay.setBackground(MAIN);
		infoDisplay.setForeground(FONT);
		infoDisplay.setMargin(new Insets(50, 50, 50, 50));
		infoDisplay.setFont(new Font("Consolas", Font.PLAIN, 2 * infoDisplay.getFont().getSize()));
		rightHolder = new JPanel(new GridLayout(rightButtonNames.length, 1));
		rightButtons = new JButton[rightButtonNames.length];
		for(int i = 0; i < rightButtonNames.length; i++){
			rightButtons[i] = new myButton(rightButtonNames[i], MAIN, MOUSE);
			rightButtons[i].setForeground(FONT);
			rightButtons[i].setBorder(BorderFactory.createEmptyBorder());
			rightButtons[i].setFont(new Font("Consolas", Font.PLAIN, 3 * rightButtons[i].getFont().getSize() / 2));
			rightHolder.add(rightButtons[i]);
		}
		bottomControls.setLayout(null);
		bottomControls.add(turnDisplay);
		bottomControls.add(holders[0]);
		bottomControls.add(holders[1]);
		bottomControls.add(infoDisplay);
		sideControls.setLayout(null);
		sideControls.add(sideConsole);
		sideControls.add(rightHolder);
		playerStats.setBackground(MAIN);
		sideControls.setBackground(MAIN);
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
		infoDisplay.setBounds(holders[0].getBounds().width, (int)((1 - INFO_HEIGHT) * bottomControls.getHeight()), (int)(bottomControls.getBounds().width * INFO_WIDTH), (int)(INFO_HEIGHT * bottomControls.getBounds().height));
		infoDisplay.setFont(new Font(infoDisplay.getFont().getName(), infoDisplay.getFont().getStyle(), infoDisplay.getBounds().height / 10));
		sideConsole.setBounds(0, 0, sideControls.getWidth(), (int)(CONSOLE_HEIGHT * sideControls.getHeight()));
		rightHolder.setBounds(0, sideConsole.getHeight(), sideControls.getWidth(), sideControls.getHeight() - sideConsole.getHeight());
		turnDisplay.setBounds(infoDisplay.getBounds().x, 0, infoDisplay.getBounds().width, infoDisplay.getBounds().y);
		turnDisplay.setForeground(BoardState.pTurn().getColor());
		turnDisplay.setFont(new Font("Consolas", Font.BOLD, infoDisplay.getFont().getSize()));
		turnDisplay.setText((BoardState.pTurn().getName() + " - " + BoardState.PHASE_NAMES[BoardState.phase]).toUpperCase());
		sideConsole.update();
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
		for(Player p: BoardState.players)
			p.updateStatDisplay();
		super.paint(g);
		paintImage(g);
		BoardState.paint(g);
	}
	
	/*** Accessors & Mutators ***/
	//Accessors
	public int[] getImgCorner(){return imgCorner;}
	public int[] getImgDim(){return imgDim;}
	public BufferedImage getImg(){return img;}
	public JTextArea getInfoDisplay(){return infoDisplay;}
	
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
	public void mouseClicked(MouseEvent arg0) {
		if(BoardState.phase == BoardState.INITIAL_REINF){
			if(moused != null && moused.getOccupation() == BoardState.pTurn()){
				moused.occupy(moused.getOccupation(), moused.getTroops() + 1);
				moused.paint(getGraphics());
				BoardState.nxtTurn();
				resetControls();
				playerStats.repaint();
			}
			return;
		}
		if(clicked != null){
			clicked.unlock();
			clicked.setColor(clicked == moused ? Territory.MOUSE_COL : Territory.BASE_COL);
			clicked = null;
			return;
		}
		clicked = moused;
		if(clicked != null){
			clicked.setColor(Territory.ATTACK_COL);
			clicked.lock();
		}
	}
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
		
		//Hide troop count
		if(e.getSource() == bottomButtons[0]){
			showCount = !showCount;
			paintImage(getGraphics());
			BoardState.paint(getGraphics());
		}
		
		//Hide adjacency
		if(e.getSource() == bottomButtons[1]){
			showGraph = !showGraph;
			paintImage(getGraphics());
			BoardState.paint(getGraphics());
		}
		
		//Flood
		if(e.getSource() == bottomButtons[2]){
			flood = !flood;
			paintImage(getGraphics());
			BoardState.paint(getGraphics());
		}
		
		//Exit
		if(e.getSource() == rightButtons[3])
			dispose();
		
	}
	
}
