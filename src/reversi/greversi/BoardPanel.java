package reversi.greversi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	private int side, cellSize, cellNumber = 8, lineNumber = cellNumber + 1;

	BoardPanel(int panelWidth) {
		cellSize = (int) (panelWidth * 0.9 / 8);
		side = cellSize * cellNumber;
		setPreferredSize(new Dimension(side, side));
	}

	/**
	 *盤の描画
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GREEN); // 盤の色
		g.fillRect(0, 0, side, side); // 正方形の盤を描画

		g.setColor(Color.BLACK); // 枠線の色

		for (int i = 0; i < lineNumber; i++) { // 枠線を引く
			g.drawLine(0, i * cellSize, side, i * cellSize);
			g.drawLine(i * cellSize, 0, i * cellSize, side);
		}
	}

	public int getSide() {
		return side;
	}
}
