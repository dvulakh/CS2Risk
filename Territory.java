
import java.awt.*;

public class Territory {
	
	/*** General Information ***/
	public static final Color BASE_COL = GameBoard.MAIN;
	public static final Color MOUSE_COL = GameBoard.MOUSE;
	public static final Color CLICK_COL = Color.MAGENTA;
	public static final Color ATTACK_COL = Color.RED;
	public static final int RAD = 20;
	public static GameBoard BOARD;
	
	/*** Private Member Variables ***/
	private Player occupation;
	private double[] loc;
	private String name;
	private int troops;
	private Color col;
	
	/*** Constructor ***/
	public Territory(String nm, double lx, double ly){
		loc = new double[2];
		occupation = null;
		col = BASE_COL;
		loc[0] = lx;
		loc[1] = ly;
		troops = 0;
		name = nm;
	}
	
	/*** Paint ***/
	public void paint(Graphics g){
		int[] l = {(int)(BOARD.getImgCorner()[0] + loc[0] * BOARD.getImgDim()[0] - RAD), (int)(BOARD.getImgCorner()[1] + loc[1] * BOARD.getImgDim()[1] - RAD)};
		g.setColor(col);
		g.fillOval(l[0], l[1], RAD, RAD);
		//g.setColor(occupation.getColor());
		g.setColor(Color.WHITE);
		g.drawString(Integer.toString(troops), l[0], l[1]);
	}

}
