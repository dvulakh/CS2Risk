
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
	public static int RAD(){return (int)(BoardState.BOARD.getImgDim()[1] * RELATIVE_RAD);}
	public static int FONT(){return (int)(RELATIVE_FONT * RAD());}
	
	/*** Private Member Variables ***/
	private Player occupation;
	private boolean cLock;
	private double[] loc;
	private String name;
	private int troops;
	private Color col;
	private int indx;
	
	/*** Constructor ***/
	public Territory(String nm, double lx, double ly, int i){
		loc = new double[2];
		occupation = null;
		col = BASE_COL;
		cLock = false;
		loc[0] = lx;
		loc[1] = ly;
		troops = 0;
		name = nm;
		indx = i;
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
	public void setColor(Color c){if(!cLock) col = c; paint(BoardState.BOARD.getGraphics());}
	public void lock(){cLock = true;}
	public void unlock(){cLock = false;}
	
	/*** Statistics Display ***/
	public String fullStats(){
		String stat = "";
		stat += name + "\n";
		stat += (occupation != null ? "occupied by " + occupation.getName() : "not occupied") + "\n";
		if(troops > 0)
			stat += troops + " occupying troops";
		return stat.toUpperCase();
	}
	
	/*** Paint ***/
	public void paint(Graphics g){
		if(BoardState.BOARD.showCount){
			int[] l = {(int)(BoardState.BOARD.getImgCorner()[0] + loc[0] * BoardState.BOARD.getImgDim()[0] - RAD()), (int)(BoardState.BOARD.getImgCorner()[1] + loc[1] * BoardState.BOARD.getImgDim()[1] - RAD())};
			g.setColor(col);
			g.fillOval(l[0], l[1], 2 * RAD(), 2 * RAD());
			//g.setColor(occupation.getColor());
			g.setColor(Color.WHITE);
			g.setFont(new Font("Consolas", Font.PLAIN, FONT()));
			g.drawString(Integer.toString(troops), l[0] + RAD() / 2, l[1] + RAD() + FONT()/ 4);
		}
	}

}
