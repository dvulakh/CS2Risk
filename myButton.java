
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class myButton extends JButton implements MouseListener {
	private static final long serialVersionUID = 1L;
	
	/*** Added private member variables ***/
	private String[] txt;
	private Color col1;
	private Color col2;
	private int indx;
	
	/*** New Constructor ***/
	public myButton(String[] toggleText, Color c1, Color c2){
		addMouseListener(this);
		setText(toggleText[0].toUpperCase());
		setFocusPainted(false);
		setBackground(c1);
		txt = toggleText;
		col1 = c1;
		col2 = c2;
		indx = 0;
	}

	/*** Change colors when mouse enters and clicks ***/
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {setBackground(col2);}
	public void mouseExited(MouseEvent arg0) {setBackground(col1);}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {
		setBackground(col2);
		indx = (indx + 1) % txt.length;
		setText(txt[indx].toUpperCase());
		BoardState.BOARD.actionPerformed(new ActionEvent(this, 0, ""));
	}

}
