
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;


public class StartMenu extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	/*** Start game ***/
	public StartMenu() throws IOException{
		
		super(GameBoard.NAME);
		new GameBoard();
		dispose();
		
	}
	
	/*** Button Response ***/
	public void actionPerformed(ActionEvent arg0) {}
	
	/*** Main ***/
	public static void main(String[] args) throws IOException{new StartMenu();}

}
