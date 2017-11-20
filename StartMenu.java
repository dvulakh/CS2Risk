
import java.awt.event.*;
import javax.swing.*;


public class StartMenu extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	/*** Start game ***/
	public StartMenu(){
		
		super(GameBoard.NAME);
		new GameBoard();
		dispose();
		
	}
	
	/*** Button Response ***/
	public void actionPerformed(ActionEvent arg0) {}
	
	/*** Main ***/
	public static void main(String[] args){new StartMenu();}

}
