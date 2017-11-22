
import java.awt.*;
import javax.swing.*;

public class Territory {
	
	/*** General Information ***/
	public static final Color BASE_COL = GameBoard.MAIN;
	public static final Color MOUSE_COL = GameBoard.MOUSE;
	public static final Color CLICK_COL = Color.MAGENTA;
	public static final Color ATTACK_COL = Color.RED;
	public static final double RELATIVE_RAD = 0.025;
	public static final double RELATIVE_FONT = 1.6;
	//public static final int RAD = 20;
	//public static final int FONT_SIZE = 8 * RAD / 5;
	public static int RAD(){return (int)(BoardState.BOARD.getImgDim()[1] * RELATIVE_RAD);}
	public static int FONT(){return (int)(RELATIVE_FONT * RAD());}
	
	/*** Private Member Variables ***/
	private Player occupation;
	private boolean cLock;
	private double[] loc;
	private String name;
	private int troops;
	private Color col;
	
	/*** Constructor ***/
	public Territory(String nm, double lx, double ly){
		loc = new double[2];
		occupation = null;
		col = BASE_COL;
		cLock = false;
		loc[0] = lx;
		loc[1] = ly;
		troops = 0;
		name = nm;
	}
	
	/*** Accessors and Mutators ***/
	//Accessors
	public Player getOccupation(){return occupation;}
	public boolean locked(){return cLock;}
	public double[] getLoc(){return loc;}
	public int getCLocX(){return (int)(loc[0] * BoardState.BOARD.getImgDim()[0] + BoardState.BOARD.getImgCorner()[0]);}
	public int getCLocY(){return (int)(loc[1] * BoardState.BOARD.getImgDim()[1] + BoardState.BOARD.getImgCorner()[1]);}
	public String getName(){return name;}
	public int getTroops(){return troops;}
	public Color getColor(){return col;}
	//Mutators
	public void setOccupation(Player p){occupation = p;}
	public void setLoc(double[] l){loc = l;}
	public void setName(String s){name = s;}
	public void setTroops(String n){name = n;}
	public void setColor(Color c){if(!cLock) col = c;}
	public void lock(){cLock = true;}
	public void unlock(){cLock = false;}
	
	/*** Paint ***/
	public void paint(Graphics g){
		//JOptionPane.showMessageDialog(BoardState.BOARD, "Painting " + name);
		int[] l = {(int)(BoardState.BOARD.getImgCorner()[0] + loc[0] * BoardState.BOARD.getImgDim()[0] - RAD()), (int)(BoardState.BOARD.getImgCorner()[1] + loc[1] * BoardState.BOARD.getImgDim()[1] - RAD())};
		g.setColor(col);
		g.fillOval(l[0], l[1], 2 * RAD(), 2 * RAD());
		//g.setColor(occupation.getColor());
		g.setColor(Color.WHITE);
		g.setFont(new Font("Calibri", Font.PLAIN, FONT()));
		g.drawString(Integer.toString(troops), l[0] + RAD() / 2, l[1] + RAD() + FONT()/ 4);
	}

}
