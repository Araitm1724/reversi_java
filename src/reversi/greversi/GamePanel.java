package reversi.greversi;

import java.awt.Dimension;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	private int panelWidth, panelHeight, startX, startY, side;

	GamePanel(int screenWidth, int screenHeight) {
		panelWidth = screenWidth / 2;
		panelHeight = screenHeight;

		setPreferredSize(
				new Dimension(panelWidth, panelHeight));

		setLayout(null);
		BoardPanel boardPanel = new BoardPanel(panelWidth);

		side = boardPanel.getSide();
		startX = (panelWidth - side) / 2;
		startY = (panelHeight - side) / 2;
		boardPanel.setBounds(startX, startY, side, side);
		add(boardPanel);
	}

	public int getPanelWidth() {
		return panelWidth;
	}

}
