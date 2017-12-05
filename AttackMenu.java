
import java.awt.event.*;
import java.awt.*;

import javax.swing.*;
import javax.swing.text.StyleConstants;

public class AttackMenu extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	/*** AttackStats ***/
	private Territory attacker;
	private Territory defender;
	private int[] losses;
	private int[] dieVals;
	
	/*** Component proportions ***/
	public static final double MY_HEIGHT = 1.0 / 3;
	public static final double MY_WIDTH = 1.0 / 3;
	public static final double LABEL_HEIGHT = 1.0 / 3;
	public static final double BAR_HEIGHT = 0.1;
	public static final double DIE_HEIGHT = 0.5 / 3;
	public static final double LOSS_HEIGHT = 0.05;
	public static final double TROOP_HEIGHT = 0.25;
	
	/*** Components ***/
	private String[][] buttonNames = {{"retreat"}, {"1 army"}, {"2 armies"}, {"3 armies"}, {"do or die"}};
	private JLabel[] dice;
	private JTextArea[] labels;
	private JLabel[] troopCount;
	private JLabel[] lossDis;
	private JPanel buttonHolder;
	private JButton[] buttons;
	
	/*** Constructor ***/
	public AttackMenu(Territory a, Territory d) {
		//Territories
		attacker = a;
		defender = d;
		losses = new int[2];
		dieVals = new int[5];
		//Initilaize Components
		setLayout(null);
		setBackground(GameBoard.MAIN);
		setBorder(BorderFactory.createLineBorder(GameBoard.FONT));
		dice = new JLabel[5];
		labels = new JTextArea[2];
		troopCount = new JLabel[2];
		lossDis = new JLabel[4];
		buttonHolder = new JPanel(new GridLayout(0, buttonNames.length));
		buttonHolder.setBorder(BorderFactory.createLineBorder(GameBoard.FONT));
		buttons = new JButton[buttonNames.length];
		for(int i = 0; i < 5; i++){
			dice[i] = new JLabel();
			dice[i].setBackground(GameBoard.MAIN);
			dice[i].setBorder(BorderFactory.createLineBorder(GameBoard.FONT));
			dice[i].setForeground(GameBoard.FONT);
			if(i < 3)
				dice[i].setForeground(Color.RED);
			add(dice[i]);
		}
		for(int i = 0; i < 2; i++){
			labels[i] = new JTextArea();
			labels[i].setBackground(GameBoard.MAIN);
			add(labels[i]);
		}
		labels[0].setForeground(attacker.getColor());
		labels[1].setForeground(defender.getColor());
		labels[1].setAlignmentX(StyleConstants.ALIGN_RIGHT);
		for(int i = 0; i < 2; i++){
			troopCount[i] = new JLabel();
			troopCount[i].setBackground(GameBoard.MAIN);
			add(troopCount[i]);
		}
		troopCount[0].setForeground(attacker.getOccupation().getColor());
		troopCount[1].setForeground(defender.getOccupation().getColor());
		for(int i = 0; i < 4; i++){
			lossDis[i] = new JLabel();
			lossDis[i].setBackground(GameBoard.MAIN);
			lossDis[i].setForeground(GameBoard.FONT);
			add(lossDis[i]);
		}
		for(int i = 0; i < buttons.length; i++){
			buttons[i] = new myButton(buttonNames[i], GameBoard.MAIN, GameBoard.MOUSE);
			buttons[i].setForeground(GameBoard.FONT);
			buttons[i].setFocusPainted(false);
			buttons[i].setBorder(BorderFactory.createEmptyBorder());
			buttons[i].setFont(new Font("Consolas", Font.PLAIN, buttons[i].getFont().getSize()));
			buttonHolder.add(buttons[i]);
		}
		add(buttonHolder);
		setVisible(true);
		//reset();
	}
	
	public void reset(){
		//This
		setBounds((int)(BoardState.BOARD.getWidth() * (0.5 - MY_WIDTH / 2)), (int)(BoardState.BOARD.getHeight() * (0.5 - MY_HEIGHT / 2)), (int)(BoardState.BOARD.getWidth() * MY_WIDTH), (int)(BoardState.BOARD.getHeight() * MY_HEIGHT));
		//Labels
		labels[0].setBounds(0, 0, getWidth() / 2, (int)(getHeight() * LABEL_HEIGHT));
		labels[1].setBounds(0, labels[0].getWidth(), labels[0].getWidth(), labels[0].getHeight());
		labels[0].setFont(new Font("Consolas", Font.BOLD, labels[0].getHeight() / 7));
		labels[1].setFont(labels[0].getFont());
		//Buttons
		buttonHolder.setBounds(0, (int)(getHeight() * (1 - BAR_HEIGHT)), getWidth(), (int)(getHeight() * BAR_HEIGHT));
		//Dice
		int c = (labels[0].getHeight() + buttonHolder.getBounds().y) / 2;
		dice[0].setBounds(0, c - (int)(1.5 * getHeight() * DIE_HEIGHT), (int)(getHeight() * DIE_HEIGHT), (int)(getHeight() * DIE_HEIGHT));
		dice[1].setBounds(0, dice[0].getBounds().y + dice[0].getHeight(), dice[0].getWidth(), dice[0].getHeight());
		dice[2].setBounds(0, dice[1].getBounds().y + dice[1].getHeight(), dice[0].getWidth(), dice[0].getHeight());
	}

	public void actionPerformed(ActionEvent arg0) {}

}
