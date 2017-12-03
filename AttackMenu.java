
import javax.swing.*;

public class AttackMenu extends JFrame{
	private static final long serialVersionUID = 1L;
	
	/*** Territories ***/
	private Territory attacker;
	private Territory defender;
	
	/*** Components ***/
	
	public AttackMenu(Territory a, Territory d){
		//Territories
		attacker = a;
		defender = d;
	}

}
