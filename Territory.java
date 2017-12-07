
import java.awt.*;
import java.util.*;

public class Territory {
	
	/*** General Information ***/
	public static final Color BASE_COL = GameBoard.MAIN;
	public static final Color MOUSE_COL = GameBoard.MOUSE;
	public static final Color CLICK_COL = Color.MAGENTA;
	public static final Color ATTACK_COL = Color.RED;
	public static final int BOUNDARY = 100;
	public static final double RELATIVE_RAD = 0.025;
	public static final double RELATIVE_FONT = 1.6;
	public static final int NUM_BIT = 32;
	public static int RAD(){return (int)(BoardState.BOARD.getImgDim()[1] * RELATIVE_RAD);}
	public static int FONT(){return (int)(RELATIVE_FONT * RAD());}
	
	/*** Private Member Variables ***/
	private Player occupation;
	private int continent;
	private boolean cLock;
	private double[] loc;
	private String name;
	private int troops;
	private Color col;
	private int indx;
	
	/*** Constructor ***/
	public Territory(int i){
		name = GameBoard.TERRITORY_NAMES[i];
		continent = GameBoard.CONTINENT[i];
		loc = GameBoard.LOCATIONS[i];
		occupation = null;
		col = BASE_COL;
		cLock = false;
		troops = 0;
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
	public int getIndx(){return indx;}
	public int getContinent(){return continent;}
	//Mutators
	public void setOccupation(Player p){occupation = p;}
	public void setLoc(double[] l){loc = l;}
	public void setName(String s){name = s;}
	public void setTroops(String n){name = n;}
	public void setColor(Color c){if(!cLock) col = c; paint(BoardState.BOARD.getGraphics());}
	public void lock(){cLock = true;}
	public void unlock(){cLock = false;}
	
	/*** Adjacency ***/
	public boolean isAdjacent(Territory t){	return (GameBoard.ADJACENCY[indx] & (long)1 << t.indx) != (long)0; }
	
	/*** Occupy territory with t troops of player p ***/
	public void occupy(Player p, int t){
		if(occupation != null){
			occupation.getOccupiedTerritories().remove(this);
			occupation.setTroops(occupation.getTroops() - troops);
			occupation.updateStatDisplay();
		}
		occupation = p;
		if(occupation != null){
			occupation.getOccupiedTerritories().add(this);
			occupation.setTroops(occupation.getTroops() + t);
			occupation.updateStatDisplay();
		}
		troops = t;
	}
	
	/*** Statistics Display ***/
	public String fullStats(){
		String stat = "";
		stat += name + "\n" + GameBoard.CONTINENT_NAME[continent] + "\n";
		stat += (occupation != null ? "occupied by " + occupation.getName() : "not occupied") + "\n";
		if(troops > 0)
			stat += troops + " occupying " + (troops == 1 ? "army" : "armies");
		return stat.toUpperCase();
	}
	
	/*** Paint ***/
	public int[] arr(double x, double y){int a[] = {(int)x, (int)y}; return a;}
	public void floodTerr(Graphics g){
		if(!BoardState.BOARD.flood)
			return;
		g.setColor(occupation == null ? Color.GRAY : occupation.getColor());
		int[] imgSize = {BoardState.BOARD.getImg().getWidth(), BoardState.BOARD.getImg().getHeight()}; 
		//System.out.println(imgSize[0] + " " + imgSize[1]);
		int[][] vis = new int[imgSize[0]][imgSize[1] / NUM_BIT + 1];
		LinkedList<int[]> q = new LinkedList<int[]>();
		for(double[] d: GameBoard.FILL_LOCS[indx])
			if(d.length > 0)
				q.addLast(arr(d[0] * imgSize[0], d[1] * imgSize[1]));
		while(!q.isEmpty()){
			int[] c = q.removeFirst(), nx;
			for(int i = -1; i <= 1; i++)
				for(int j = -1; j <= 1; j++){
					nx = arr(c[0] + i, c[1] + j);
					if(((vis[nx[0]][nx[1] / NUM_BIT] & 1 << nx[1] % NUM_BIT) == 0) && ((BoardState.BOARD.getImg().getRGB(nx[0], nx[1]) & 255) >= BOUNDARY || ((BoardState.BOARD.getImg().getRGB(nx[0], nx[1]) >> 8) & 255) >= BOUNDARY || ((BoardState.BOARD.getImg().getRGB(nx[0], nx[1]) >> 16) & 255) >= BOUNDARY)){
						q.addLast(nx);
						vis[nx[0]][nx[1] / NUM_BIT] = vis[nx[0]][nx[1] / NUM_BIT] | 1 << nx[1] % NUM_BIT;
						g.fillRect(nx[0] * BoardState.BOARD.getImgDim()[0] / imgSize[0] + BoardState.BOARD.getImgCorner()[0], nx[1] * BoardState.BOARD.getImgDim()[1] / imgSize[1] + BoardState.BOARD.getImgCorner()[1], 1, 1);
					}
				}
		}
	}
	public void paint(Graphics g){
		if(BoardState.BOARD.showCount){
			int[] l = {(int)(BoardState.BOARD.getImgCorner()[0] + loc[0] * BoardState.BOARD.getImgDim()[0] - RAD()), (int)(BoardState.BOARD.getImgCorner()[1] + loc[1] * BoardState.BOARD.getImgDim()[1] - RAD())};
			if(col != ATTACK_COL || (!BoardState.BOARD.flood && BoardState.phase == BoardState.ATTACK))
				g.setColor(col);
			g.fillOval(l[0], l[1], 2 * RAD(), 2 * RAD());
			if(occupation != null){
				g.setColor(!BoardState.BOARD.flood && col != ATTACK_COL ? occupation.getColor() : GameBoard.FONT);
				if(BoardState.BOARD.flood && BoardState.phase == BoardState.ATTACK && col == ATTACK_COL)
					g.setColor(ATTACK_COL);
				g.setFont(new Font("Consolas", Font.PLAIN, FONT()));
				g.drawString(Integer.toString(troops), l[0] + RAD() - g.getFontMetrics().stringWidth(Integer.toString(troops)) / 2, l[1] + RAD() + FONT() / 4);
			}
		}
	}

}
