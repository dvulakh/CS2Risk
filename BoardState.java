
import java.awt.*;

public class BoardState {
	
	/*** Game Information ***/
	public static final int MAX_PLAYER = 6;
	public static Territory[] territories;
	public static Player[] players;
	public static GameBoard BOARD;
	
	/*** Set-Up ***/
	public static void startGame(){
		territories = new Territory[GameBoard.LOCATIONS.length];
		for(int i = 0; i < territories.length; i++)
			territories[i] = new Territory(GameBoard.TERRITORY_NAMES[i], GameBoard.LOCATIONS[i][0], GameBoard.LOCATIONS[i][1]);
	}
	
	/*** Draw Board ***/
	public static void paint(Graphics g){
		for(Territory t: territories)
			t.paint(g);
	}

}
