
import java.awt.*;
import javax.swing.*;

public class Player {
	
	/*** Player Information ***/
	private String name;
	private Color col;
	private Integer indx;
	private Integer ter;
	private Integer troop;
	private Integer team;
	
	/*** Stat display ***/
	private Integer[] stats = {ter, troop, team};
	private String[] labels = {"Territories", "Troops", "Team"};
	
	/*** Constructor ***/
	public Player(int i, String s, Color c){
		name = s;
		col = c;
		indx = i;
		ter = 0;
		troop = 0;
		team = -1;
	}

}
