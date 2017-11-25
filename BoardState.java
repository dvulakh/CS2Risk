
import java.awt.*;

public class BoardState {
	
	/*** Game Information ***/
	public static final int MAX_PLAYER = 6;
	public static Territory[] territories;
	public static Player[] players;
	public static GameBoard BOARD;
	
	/*** Set-Up ***/
	public static void startGame(){
		//Territories
		territories = new Territory[GameBoard.LOCATIONS.length];
		for(int i = 0; i < territories.length; i++)
			territories[i] = new Territory(i);
		//Player test
		players = new Player[6];
		players[0] = new HumanPlayer(1, "Alice", Color.RED);
		players[1] = new HumanPlayer(2, "Bob", Color.GREEN);
		players[2] = new HumanPlayer(3, "Cate", Color.MAGENTA);
		players[3] = new HumanPlayer(4, "Daniel", Color.CYAN);
		players[4] = new HumanPlayer(5, "Eli", Color.BLUE);
		players[5] = new HumanPlayer(6, "Falstaff", Color.ORANGE);
		//Territory test - not evenly distributed
		for(Territory t: territories)
			t.occupy(players[(int)(Math.random() * players.length)], (int)(Math.random() * 20) + 1);
	}
	
	/*** Draw Board ***/
	public static void paintAdjacent(Graphics g, Territory t){
		if(!BOARD.showGraph)
			return;
		for(int i = 0; i < territories.length; i++)
			if(t.isAdjacent(territories[i])){
				if(t.getOccupation() == null)
					g.setColor(GameBoard.LINE);
				else if(t.getOccupation() == territories[i].getOccupation() && !BOARD.flood)
					g.setColor(t.getOccupation().getColor());
				else
					g.setColor(GameBoard.LINE);
				((Graphics2D)g).setStroke(new BasicStroke(Territory.RAD() / 5));
				if(t.getName().equals("Alaska") && territories[i].getName().equals("Kamchatka")){
					g.drawLine(t.getCLocX(), t.getCLocY(), BOARD.getImgCorner()[0] + Territory.RAD() / 5, t.getCLocY());
					g.drawLine(territories[i].getCLocX(), territories[i].getCLocY(), BOARD.getImgCorner()[0] + BOARD.getImgDim()[0] - Territory.RAD() / 5, territories[i].getCLocY());
				}
				else if(t.getName().equals("Kamchatka") && territories[i].getName().equals("Alaska")){
					g.drawLine(territories[i].getCLocX(), territories[i].getCLocY(), BOARD.getImgCorner()[0] + Territory.RAD() / 5, territories[i].getCLocY());
					g.drawLine(t.getCLocX(), t.getCLocY(), BOARD.getImgCorner()[0] + BOARD.getImgDim()[0] - Territory.RAD() / 5, t.getCLocY());
				}
				else
					g.drawLine(t.getCLocX(), t.getCLocY(), territories[i].getCLocX(), territories[i].getCLocY());
			}
	}
	public static void paint(Graphics g){
		
		//Flood
		for(Territory t: territories)
			t.floodTerr(g);
		
		//Adjacency graph
		for(Territory t: territories)
			paintAdjacent(g, t);
		
		//Territories
		for(Territory t: territories)
			t.paint(g);
		
	}

}
