
import java.awt.*;
import java.util.*;

public abstract class BoardState {
	
	/*** Game Information ***/
	public static final String[] PHASE_NAMES = {"Select a Territory", "Reinforce", "Reinforce", "Attack", "Fortify"};
	public static final int CHOOSE_TERR = 0;
	public static final int INITIAL_REINF = 1;
	public static final int REINF = 2;
	public static final int ATTACK = 3;
	public static final int FORTIFY = 4;
	public static final int MAX_PLAYER = 6;
	public static Territory[] territories;
	public static Player[] players;
	public static GameBoard BOARD;
	public static int startTroops;
	public static int reinf;
	public static int turn;
	public static int phase;
	public static Player pTurn(){return players[turn % players.length];}
	
	/*** Set-Up ***/
	public static void startGame(){
		//Turn
		turn = 0;
		//Territories
		territories = new Territory[GameBoard.LOCATIONS.length];
		for(int i = 0; i < territories.length; i++)
			territories[i] = new Territory(i);
		//Player test
		players = new Player[6];
		startTroops = 8;
		players[0] = new HumanPlayer(1, "Alice", Color.RED);
		players[1] = new HumanPlayer(2, "Bob", Color.GREEN);
		players[2] = new HumanPlayer(3, "Cate", Color.MAGENTA);
		players[3] = new HumanPlayer(4, "Daniel", Color.CYAN);
		players[4] = new HumanPlayer(5, "Eli", Color.BLUE);
		players[5] = new HumanPlayer(6, "Falstaff", Color.ORANGE);
		//Distribute territories
		randTerr();
		phase = 1;
	}
	public static void randTerr(){
		ArrayList<Territory> ter = new ArrayList<Territory>();
		for(Territory t: territories)
			ter.add(t);
		while(ter.size() > 0){
			for(Player p: players){
				int i = (int)(Math.random() * ter.size());
				ter.get(i).occupy(p, 1);
				ter.remove(i);
				if(ter.size() <= 0)
					return;
			}
		}
	}
	
	/*** Process Turns ***/
	public static void nxtTurn(){
		turn++;
		if(phase == INITIAL_REINF){
			int i = 0;
			while(pTurn().getTroops() >= startTroops && i <= players.length){
				turn++;
				i++;
			}
			if(pTurn().getTroops() >= startTroops){
				turn = 0;
				phase = REINF;
			}
		}
		if(phase == REINF){
			reinf = Math.max(pTurn().getOccupiedTerritories().size() / 3, 3) + pTurn().continentBonus();
			pTurn().placeReinforcements();
		}
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
