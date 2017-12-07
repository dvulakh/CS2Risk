
import java.awt.*;

public class HumanPlayer extends Player {
	
	private Territory attacker;
	
	/*** Constructor ***/
	public HumanPlayer(int i, String s, Color c) {super(i, s, c);}

	/*** Making moves ***/
	public void selectTerritory() {}
	public void placeInitialReinforcements(){
		if(BoardState.BOARD.clicked != null && BoardState.BOARD.clicked.getOccupation() == this){
			BoardState.BOARD.clicked.occupy(BoardState.BOARD.clicked.getOccupation(), BoardState.BOARD.clicked.getTroops() + 1);
			BoardState.BOARD.clicked.paint(BoardState.BOARD.getGraphics());
			BoardState.nxtTurn();
			BoardState.BOARD.paintControls(BoardState.BOARD.getGraphics());
			BoardState.BOARD.clicked = null;
			if(BoardState.phase == BoardState.INITIAL_REINF)
				BoardState.pTurn().placeInitialReinforcements();
			else
				BoardState.pTurn().placeReinforcements();
		}
	}
	public void placeReinforcements() {
		if(BoardState.BOARD.clicked != null && BoardState.BOARD.clicked.getOccupation() == this){
			BoardState.BOARD.clicked.occupy(BoardState.BOARD.clicked.getOccupation(), BoardState.BOARD.clicked.getTroops() + 1);
			BoardState.BOARD.clicked.paint(BoardState.BOARD.getGraphics());
			BoardState.reinf--;
			if(BoardState.reinf == 0)
				BoardState.phase = BoardState.ATTACK;
			BoardState.BOARD.paintControls(BoardState.BOARD.getGraphics());
			BoardState.BOARD.clicked = null;
		}
	}
	public void makeAttacks() {
		if(attacker == null){
			if(BoardState.BOARD.clicked != null && BoardState.BOARD.clicked.getOccupation() == this && BoardState.BOARD.clicked.getTroops() > 1){
				attacker = BoardState.BOARD.clicked;
				attacker.setColor(Territory.ATTACK_COL);
				attacker.lock();
			}
		}
		else{
			if(BoardState.BOARD.clicked != null && BoardState.BOARD.clicked.getOccupation() != this && BoardState.BOARD.clicked.isAdjacent(attacker)){
				attacker.unlock();
				attacker.setColor(Territory.BASE_COL);
				BoardState.BOARD.getInfoDisplay().setText("");
				BoardState.BOARD.clicked.setColor(Territory.BASE_COL);
				BoardState.BOARD.setAttackMenu(new AttackMenu(attacker, BoardState.BOARD.clicked));
				attacker = null;
			}
		}
	}
	public void maneuvers() {}
	public void turnInCards() {}

}
