
import java.awt.*;
import java.util.*;
import javax.swing.*;

public abstract class Player {
	
	/*** Player Information ***/
	private LinkedList<Territory> ter;
	private String name;
	private Color col;
	private int indx;
	private int troop;
	private int team;
	
	/*** Stat display ***/
	private JPanel statPane;
	private JPanel[] bottomTwo;
	private JLabel[][] statDis;
	
	/*** Constructor ***/
	public Player(int i, String s, Color c){
		//Panels
		statPane = new JPanel(new GridLayout(3, 1));
		bottomTwo = new JPanel[2];
		bottomTwo[0] = new JPanel(new GridLayout(1, 3));
		bottomTwo[1] = new JPanel(new GridLayout(1, 3));
		statDis = new JLabel[3][3];
		statDis[0][0] = new JLabel();
		statPane.add(statDis[0][0]);
		for(int j = 1; j < 3; j++)
			for(int k = 0; k < statDis[j].length; k++){
				statDis[j][k] = new JLabel();
				bottomTwo[j - 1].add(statDis[j][k]);
			}
		statPane.add(bottomTwo[0]);
		statPane.add(bottomTwo[1]);
		//Player info
		ter = new LinkedList<Territory>();
		name = s;
		col = c;
		indx = i;
		troop = 0;
		team = -1;
	}
	
	/*** Accessors and Mutators ***/
	//Accessors
	public LinkedList<Territory> getOccupiedTerritories(){return ter;}
	public String getName(){return name;}
	public Color getColor(){return col;}
	public int getIndx(){return indx;}
	public int getTroops(){return troop;}
	public int getTeam(){return team;}
	public JPanel getStats(){return statPane;}
	//Mutators
	public void setOccupiedTerritories(LinkedList<Territory> l){ter = l;}
	public void setName(String s){name = s;}
	public void setColor(Color c){col = c;}
	public void setIndx(int i){indx = i;}
	public void setTroops(int t){troop = t;}
	public void setTeam(int t){team = t;}
	
	/*** Update stats ***/
	public void updateStatDisplay(){
		//Name
		statDis[0][0].setBackground(GameBoard.MAIN);
		statDis[0][0].setForeground(col);
		statDis[0][0].setText(indx + " " + name);
		statDis[0][0].setFont(new Font("Consolas", Font.BOLD, BoardState.BOARD.screenDim[1] / BoardState.MAX_PLAYER / 4));
		//Numbers
		statDis[1][0].setText(Integer.toString(ter.size()));
		statDis[1][1].setText(Integer.toString(troop));
		statDis[1][2].setText(team > 0 ? Integer.toString(team) : "---");
		//Labels
		statDis[2][0].setText("Occupied");
		statDis[2][1].setText("Troops");
		statDis[2][2].setText("Team");
		//Color
		bottomTwo[0].setBackground(GameBoard.MAIN);
		bottomTwo[1].setBackground(GameBoard.MAIN);
		statPane.setBackground(GameBoard.MAIN);
		for(int i = 1; i < statDis.length; i++)
			for(int j = 0; j < statDis[i].length; j++){
				if(i == statDis.length - 1)
					statDis[i][j].setVerticalAlignment(SwingConstants.TOP);
				statDis[i][j].setFont(new Font("Consolas", statDis[i][j].getFont().getStyle(), 4 * statDis[i][j].getFont().getSize() / 3));
				statDis[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				statDis[i][j].setBackground(GameBoard.MAIN);
				statDis[i][j].setForeground(GameBoard.FONT);
			}
	}
	
	/*** Abstract methods ***/
	public abstract void placeReinforcements();
	public abstract void makeAttacks();
	public abstract void maneuvers();

}
