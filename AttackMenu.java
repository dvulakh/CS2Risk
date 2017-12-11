
import java.awt.*;
import javax.swing.*;

public class AttackMenu extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/*** AttackStats ***/
	public Territory attacker;
	public Territory defender;
	
	/*** Component proportions ***/
	public static final double MY_HEIGHT = 1.0 / 3;
	public static final double MY_WIDTH = 1.0 / 3;
	public static final double LABEL_HEIGHT = 0.25;
	public static final double BAR_HEIGHT = 0.1;
	public static final double DIE_HEIGHT = 0.5 / 3;
	public static final double TROOP_HEIGHT = 0.25;
	
	/*** Components ***/
	private String[][] buttonNames = {{"retreat"}, {"1 army"}, {"2 armies"}, {"3 armies"}, {"do or die"}};
	private JLabel[] dice;
	private JTextArea[] labels;
	private JLabel[] troopCount;
	private JPanel buttonHolder;
	public JButton[] buttons;
	
	/*** Constructor ***/
	public AttackMenu(Territory a, Territory d) {
		//Territories
		attacker = a;
		defender = d;
		attacker.getOccupation().battlesT++;
		defender.getOccupation().battlesT++;
		BoardState.dice = new int[5];
		//Initialize Components
		setLayout(null);
		setBackground(GameBoard.MAIN);
		setBorder(BorderFactory.createLineBorder(GameBoard.FONT));
		dice = new JLabel[5];
		labels = new JTextArea[2];
		troopCount = new JLabel[2];
		buttonHolder = new JPanel(new GridLayout(0, buttonNames.length));
		buttonHolder.setBorder(BorderFactory.createLineBorder(GameBoard.FONT));
		buttons = new JButton[buttonNames.length];
		for(int i = 0; i < 5; i++){
			dice[i] = new JLabel("-", SwingConstants.CENTER);
			dice[i].setBackground(GameBoard.MAIN);
			dice[i].setBorder(BorderFactory.createLineBorder(GameBoard.FONT));
			dice[i].setForeground(GameBoard.FONT);
			if(i < 3)
				dice[i].setForeground(Color.RED);
			add(dice[i]);
		}
		for(int i = 0; i < 2; i++){
			labels[i] = new JTextArea();
			labels[i].setOpaque(false);
			labels[i].setEditable(false);
			add(labels[i]);
		}
		labels[0].setForeground(attacker.getOccupation().getColor());
		labels[1].setForeground(defender.getOccupation().getColor());
		labels[1].setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		for(int i = 0; i < 2; i++){
			troopCount[i] = new JLabel("", SwingConstants.CENTER);
			troopCount[i].setBackground(GameBoard.MAIN);
			add(troopCount[i]);
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
	}
	
	public void reset(){
		//This
		setBounds((int)(BoardState.BOARD.getWidth() * (0.5 - MY_WIDTH / 2)), (int)(BoardState.BOARD.getHeight() * (0.5 - MY_HEIGHT / 2)), (int)(BoardState.BOARD.getWidth() * MY_WIDTH), (int)(BoardState.BOARD.getHeight() * MY_HEIGHT));
		//Labels
		labels[0].setForeground(attacker.getOccupation().getColor());
		labels[1].setForeground(defender.getOccupation().getColor());
		labels[0].setBounds(0, 0, getWidth() / 2, (int)(getHeight() * LABEL_HEIGHT));
		labels[1].setBounds(labels[0].getWidth(), 0, labels[0].getWidth(), labels[0].getHeight());
		labels[0].setText(attacker.fullStats());
		labels[1].setText(defender.fullStats());
		labels[0].setMargin(new Insets(5, 5, 5, 5));
		labels[1].setMargin(new Insets(5, 5, 5, 5));
		labels[0].setFont(new Font("Consolas", Font.PLAIN, labels[0].getHeight() / 5));
		labels[1].setFont(labels[0].getFont());
		//Buttons
		buttonHolder.setBounds(0, (int)(getHeight() * (1 - BAR_HEIGHT)), getWidth(), (int)(getHeight() * BAR_HEIGHT));
		//Dice
		int c = (labels[0].getHeight() + buttonHolder.getBounds().y) / 2;
		dice[0].setBounds(0, c + (int)(0.5 * getHeight() * DIE_HEIGHT), (int)(getHeight() * DIE_HEIGHT), (int)(getHeight() * DIE_HEIGHT));
		dice[1].setBounds(0, dice[0].getBounds().y - dice[0].getHeight(), dice[0].getWidth(), dice[0].getHeight());
		dice[2].setBounds(0, dice[1].getBounds().y - dice[1].getHeight(), dice[0].getWidth(), dice[0].getHeight());
		dice[3].setBounds(getWidth() - dice[0].getWidth(), c, dice[0].getWidth(), dice[0].getWidth());
		dice[4].setBounds(dice[3].getX(), c - dice[0].getWidth(), dice[0].getWidth(), dice[0].getWidth());
		for(int i = 0; i < dice.length; i++){
			dice[i].setFont(new Font("Consolas", Font.BOLD, (int)(0.8 * dice[0].getHeight())));
			dice[i].setText(BoardState.dice[i] != 0 ? Integer.toString(BoardState.dice[i]) : "-");
		}
		//Troop counts
		troopCount[0].setForeground(attacker.getOccupation().getColor());
		troopCount[1].setForeground(defender.getOccupation().getColor());
		troopCount[0].setBounds(getWidth() / 2 - (int)(1.5 * TROOP_HEIGHT * getHeight()), c - (int)(TROOP_HEIGHT * getHeight()) / 2, (int)(TROOP_HEIGHT * getHeight()), (int)(TROOP_HEIGHT * getHeight()));
		troopCount[1].setBounds(getWidth() / 2 + troopCount[0].getWidth() / 2, troopCount[0].getY(), troopCount[0].getWidth(), troopCount[0].getHeight());
		troopCount[0].setFont(new Font("Consolas", Font.BOLD, (int)(0.8 * troopCount[0].getHeight())));
		troopCount[1].setFont(new Font("Consolas", Font.BOLD, (int)(0.8 * troopCount[1].getHeight())));
		troopCount[0].setText(Integer.toString(attacker.getTroops()));
		troopCount[1].setText(Integer.toString(defender.getTroops()));
		//Repaint
		repaint();
		if(BoardState.BOARD.getMoveMenu() != null)
			BoardState.BOARD.getMoveMenu().reset();
	}

}
