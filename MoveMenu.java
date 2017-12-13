
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class MoveMenu extends JPanel {
	private static final long serialVersionUID = 1L;

	/*** Movement Stats ***/
	public Territory from;
	public Territory to;
	private int finit;
	private int tinit;
	private class myListener implements ChangeListener{
		public void stateChanged(ChangeEvent e) {
			int m = slider.getValue();
			from.occupy(from.getOccupation(), finit - m);
			to.occupy(to.getOccupation(), tinit + m);
			reset();
		}
	}
	
	/*** Component proportions ***/
	public static final double MY_HEIGHT = 0.2;
	public static final double MY_WIDTH = 0.25;
	public static final double LABEL_HEIGHT = 0.10;
	public static final double BAR_HEIGHT = 0.15;
	public static final double TROOP_HEIGHT = 0.25;
	public static final double SLIDER_WIDTH = 0.8;
	public static final double SLIDER_HEIGHT = 0.2;
	
	/*** Components ***/
	private String[][] buttonNames = {{"mobilize"}};
	private JTextArea[] labels;
	private JLabel[] troopCount;
	private JPanel buttonHolder;
	public JButton[] buttons;
	public JSlider slider;
	
	/*** Constructor ***/
	public MoveMenu(Territory f, Territory t, int minMove) {
		//Territories
		from = f;
		to = t;
		finit = from.getTroops() + minMove;
		tinit = to.getTroops() - minMove;
		//Initialize Components
		setLayout(null);
		setBackground(GameBoard.MAIN);
		setBorder(BorderFactory.createLineBorder(GameBoard.FONT));
		labels = new JTextArea[2];
		troopCount = new JLabel[2];
		buttonHolder = new JPanel(new GridLayout(0, buttonNames.length));
		buttonHolder.setBorder(BorderFactory.createLineBorder(GameBoard.FONT));
		buttons = new JButton[buttonNames.length];
		for(int i = 0; i < 2; i++){
			labels[i] = new JTextArea();
			labels[i].setOpaque(false);
			labels[i].setEditable(false);
			add(labels[i]);
		}
		labels[0].setForeground(from.getOccupation().getColor());
		labels[1].setForeground(to.getOccupation().getColor());
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
		slider = new JSlider(JSlider.HORIZONTAL, minMove, from.getTroops() + minMove - 1, minMove);
		slider.addChangeListener(new myListener());
		slider.setMajorTickSpacing((slider.getMaximum() - slider.getMinimum()) >= 10 ? (slider.getMaximum() - slider.getMinimum()) / 10 : 1);
		slider.setMinorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setBackground(GameBoard.MAIN);
		slider.setForeground(GameBoard.FONT);
		add(slider);
	}
	
	public void reset(){
		//This
		setBounds((int)(BoardState.BOARD.getWidth() * (0.5 - MY_WIDTH / 2)), (int)(BoardState.BOARD.getHeight() * (0.5 - MY_HEIGHT / 2)), (int)(BoardState.BOARD.getWidth() * MY_WIDTH), (int)(BoardState.BOARD.getHeight() * MY_HEIGHT));
		//Labels
		labels[0].setForeground(from.getOccupation().getColor());
		labels[1].setForeground(to.getOccupation().getColor());
		labels[0].setBounds(0, 0, getWidth() / 2, (int)(getHeight() * LABEL_HEIGHT));
		labels[1].setBounds(labels[0].getWidth(), 0, labels[0].getWidth(), labels[0].getHeight());
		labels[0].setText(from.getName().toUpperCase());
		labels[1].setText(to.getName().toUpperCase());
		labels[0].setMargin(new Insets(5, 5, 5, 5));
		labels[1].setMargin(new Insets(5, 5, 5, 5));
		labels[0].setFont(new Font("Consolas", Font.PLAIN, (int)(0.8 * labels[0].getHeight())));
		labels[1].setFont(labels[0].getFont());
		//Buttons
		buttonHolder.setBounds(0, (int)(getHeight() * (1 - BAR_HEIGHT)), getWidth(), (int)(getHeight() * BAR_HEIGHT));
		for(JButton b: buttons)
			b.setFont(new Font(b.getFont().getFontName(), b.getFont().getStyle(), (int)(0.8 * buttonHolder.getHeight())));
		//Troop counts
		int c = (labels[0].getHeight() + buttonHolder.getBounds().y) / 3;
		troopCount[0].setForeground(from.getOccupation().getColor());
		troopCount[1].setForeground(to.getOccupation().getColor());
		troopCount[0].setBounds(getWidth() / 2 - (int)(1.5 * TROOP_HEIGHT * getHeight()), c - (int)(TROOP_HEIGHT * getHeight()) / 2, (int)(TROOP_HEIGHT * getHeight()), (int)(TROOP_HEIGHT * getHeight()));
		troopCount[1].setBounds(getWidth() / 2 + troopCount[0].getWidth() / 2, troopCount[0].getY(), troopCount[0].getWidth(), troopCount[0].getHeight());
		troopCount[0].setFont(new Font("Consolas", Font.BOLD, (int)(0.8 * troopCount[0].getHeight())));
		troopCount[1].setFont(new Font("Consolas", Font.BOLD, (int)(0.8 * troopCount[1].getHeight())));
		troopCount[0].setText(Integer.toString(from.getTroops()));
		troopCount[1].setText(Integer.toString(to.getTroops()));
		//Slider
		c = 3 * c / 2;
		slider.setBounds((int)((1 - SLIDER_WIDTH) / 2 * getWidth()), c, (int)(SLIDER_WIDTH * getWidth()), (int)(SLIDER_HEIGHT * getHeight()));
		//Repaint
		repaint();
	}
	
}
