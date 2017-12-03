
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;

public class Console extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/*** Private member variables ***/
	private static final String COMMANDS = "help: print command list\n[player]: print player stats\n[territory]: place army in territory\n";
	private JScrollPane scroller;
	private JTextArea display;
	private JTextField cmd;
	private class quickListen extends KeyAdapter{
		public void keyPressed(KeyEvent k){
			if(k.getKeyCode() == KeyEvent.VK_ENTER)
				update();
		}
	}
	private class myScroll extends BasicScrollBarUI{
		protected void configureScrollBarColors(){
			thumbColor = GameBoard.FONT;
			thumbDarkShadowColor = GameBoard.FONT;
			thumbLightShadowColor = GameBoard.FONT;
			thumbHighlightColor = GameBoard.FONT;
			trackColor = GameBoard.MOUSE;
		}
	}
	
	/*** Constructor ***/
	public Console() {
		//Initialize
		super();
		setBackground(GameBoard.MAIN);
		setLayout(null);
		display = new JTextArea();
		scroller = new JScrollPane(display);
		scroller.getVerticalScrollBar().setUI(new myScroll());
		scroller.getHorizontalScrollBar().setUI(new myScroll());
		cmd = new JTextField();
		scroller.setBorder(BorderFactory.createLineBorder(GameBoard.FONT));
		cmd.setBorder(BorderFactory.createLineBorder(GameBoard.FONT));
		display.setFont(BoardState.BOARD.getInfoDisplay().getFont());
		cmd.setFont(BoardState.BOARD.getInfoDisplay().getFont());
		cmd.addKeyListener(new quickListen());
		display.setBackground(GameBoard.MAIN);
		display.setForeground(GameBoard.FONT);
		cmd.setBackground(GameBoard.MAIN);
		cmd.setForeground(GameBoard.FONT);
		cmd.setCaretColor(GameBoard.FONT);
		display.setEditable(false);
		//Add
		add(scroller);
		add(cmd);
	}
	
	/*** Update Display and Execute Commands ***/
	public void update(){
		//Resize
		display.setFont(BoardState.BOARD.getInfoDisplay().getFont());
		cmd.setFont(BoardState.BOARD.getInfoDisplay().getFont());
		cmd.setBounds(0, getHeight() - 3 * cmd.getFont().getSize() / 2, getWidth(), 3 * cmd.getFont().getSize() / 2);
		scroller.setBounds(0, 0, getWidth(), getHeight() - cmd.getHeight());
		//Get command
		String s = cmd.getText().toUpperCase();
		if(s.equals(""))
			return;
		cmd.setText("");
		display.append(">>> " + s + "\n");
		repaint();
		//Execute
		if(s.equals("HELP"))
			display.append(COMMANDS.toUpperCase());
		//Split
		String[] ss = s.split(" ");
		if(ss.length < 1)
			return;
		for(Territory t: BoardState.territories)
			if(s.equals(t.getName().toUpperCase())){
				BoardState.BOARD.clicked = t;
				if(BoardState.phase == BoardState.INITIAL_REINF){
					BoardState.pTurn().placeInitialReinforcements();
					display.append("reinforcements arrived\n".toUpperCase());
				}
				if(BoardState.phase == BoardState.REINF){
					BoardState.pTurn().placeReinforcements();
					display.append("reinforcements arrived\n".toUpperCase());
				}
			}
		for(Player p: BoardState.players)
			if(s.equals(p.getName().toUpperCase()))
				display.append(p.fullStats().toUpperCase() + "\n");
	}

}
