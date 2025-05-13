package reversi.greversi;

import java.awt.Color;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	private int side;

	BoardPanel() {
		setBackground(Color.GREEN);
	}

	public void setSide(int side) {
		this.side = side;
	}
}
