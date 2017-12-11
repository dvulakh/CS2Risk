
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;

public class Console extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/*** Private member variables ***/
	private static final String COMMANDS = "help: print command list\n[player]: print player stats\n[territory]: place army in territory\n[territory 1] [territory 2] [x]: attack/move from territory 1 to territory 2 with x armies\nend: end phase\nstall: forfeit turn\n";
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
		for(Territory t: BoardState.territories){
			boolean match = true;
			for(int i = 0; i < t.getName().split(" ").length; i++)
				if(i >= ss.length || !ss[i].equals(t.getName().split(" ")[i].toUpperCase()))
					match = false;
			if(!match) continue;
			if(BoardState.phase == BoardState.INITIAL_REINF){
				BoardState.BOARD.clicked = t;
				BoardState.pTurn().placeInitialReinforcements();
				display.append("reinforcements arrived\n".toUpperCase());
			}
			else if(BoardState.phase == BoardState.REINF){
				BoardState.BOARD.clicked = t;
				BoardState.pTurn().placeReinforcements();
				display.append("reinforcements arrived\n".toUpperCase());
			}
			else if(BoardState.phase == BoardState.ATTACK || BoardState.phase == BoardState.FORTIFY){
				for(Territory t2: BoardState.territories){
					match = true;
					for(int i = 0; i < t2.getName().split(" ").length; i++)
						if(t.getName().split(" ").length + i >= ss.length || !ss[t.getName().split(" ").length + i].equals(t2.getName().split(" ")[i].toUpperCase()))
							match = false;
					if(!match)
						continue;
					int tr;
					try{tr = Integer.parseInt(ss[t.getName().split(" ").length + t2.getName().split(" ").length]);}
					catch(Exception e){break;}
					if(BoardState.phase == BoardState.ATTACK && match && t.getOccupation() == BoardState.pTurn() && t.getOccupation() != t2.getOccupation() && t.isAdjacent(t2)){
						int init = t.getTroops();
						if(tr >= init)
							break;
						t.getOccupation().battlesT++;
						t2.getOccupation().battlesT++;
						while(init - t.getTroops() < tr && t.getTroops() > 1 && t.getOccupation() != t2.getOccupation()){
							BoardState.attack(t,  t2, Math.min(t.getTroops() - init + tr, 3));
						}
						if(t.getOccupation() == t2.getOccupation()){
							t.getOccupation().battlesW++;
							display.append("invasion successful\n".toUpperCase());
							int left = t.getTroops() - init + tr;
							t.occupy(t.getOccupation(), t.getTroops() - left);
							t2.occupy(t2.getOccupation(), t2.getTroops() + left);
							t.conquestPaint(BoardState.BOARD.getGraphics());
							t2.conquestPaint(BoardState.BOARD.getGraphics());
						}
						else{
							t2.getOccupation().battlesT++;
							display.append("invasion failed\n".toUpperCase());
						}
					}
					else if(BoardState.phase == BoardState.FORTIFY && t.getOccupation() == t2.getOccupation() && t.canReach(t2)){
						if(tr < t.getTroops()){
							t.occupy(t.getOccupation(), t.getTroops() - tr);
							t2.occupy(t2.getOccupation(), t2.getTroops() + tr);
							display.append("troops mobilized\n".toUpperCase());
							s = "END";
						}
					}
				}
						
			}
		}
		for(Player p: BoardState.players)
			if(s.equals(p.getName().toUpperCase()))
				display.append(p.fullStats().toUpperCase() + "\n");
		if(s.equals("STALL")){
			BoardState.nxtTurn();
			BoardState.BOARD.paintControls(BoardState.BOARD.getGraphics());
		}
		if(s.equals("END"))
			BoardState.BOARD.actionPerformed(new ActionEvent(BoardState.BOARD.getBottomButtons()[3], 0, ""));
	}

}
