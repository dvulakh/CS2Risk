
public class Card {
	
	/*** Card Types ***/
	public static int INFANTRY = 1;
	public static int CAVALRY = 2;
	public static int ARTILLERY = 3;
	public static int JOKER = 4;
	
	/*** Card info ***/
	private Territory terr;
	
	/*** Construct ***/
	public Card(Territory t){terr = t;}

}
