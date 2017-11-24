
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
			territories[i] = new Territory(GameBoard.TERRITORY_NAMES[i], GameBoard.LOCATIONS[i][0], GameBoard.LOCATIONS[i][1], i);
		//Player test
		players = new Player[6];
		players[0] = new HumanPlayer(1, "Alice", Color.RED);
		players[1] = new HumanPlayer(2, "Bob", Color.GREEN);
		players[2] = new HumanPlayer(3, "Cate", Color.MAGENTA);
		players[3] = new HumanPlayer(4, "Daniel", Color.CYAN);
		players[4] = new HumanPlayer(5, "Eli", Color.BLUE);
		players[5] = new HumanPlayer(6, "Falstaff", Color.ORANGE);
	}
	
	/*** Draw Board ***/
	public static void paint(Graphics g){
		try{
			for(Territory t: territories)
				t.paint(g);
		}catch(Exception e){}
	}

}
