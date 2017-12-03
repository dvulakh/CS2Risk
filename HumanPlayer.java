
import java.awt.*;

public class HumanPlayer extends Player {

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
	public void makeAttacks() {}
	public void maneuvers() {}
	public void turnInCards() {}

}
