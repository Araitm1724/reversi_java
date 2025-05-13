package reversi.greversi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	int screenWidth, screenHeight;
	Dimension screenSize;
	BoardPanel boardPanel;
	RightPanels gameSettingsPanel, playingPanel, outcomePanel;

	MainFrame(String title) {
		setTitle(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout());

		// ウィンドウサイズ設定
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) (screenSize.getWidth() * 0.9);
		screenHeight = (int) (screenSize.getHeight() * 0.9);
		setSize(screenWidth, screenHeight);

		boardPanel = new BoardPanel();
		boardPanel.setSide((int) (boardPanel.getSize().height * 0.8));
		add(boardPanel);

		gameSettingsPanel = new GameSettingsPanel("対局設定");
		add(gameSettingsPanel);

		boardPanel.setBackground(Color.BLACK);
		gameSettingsPanel.setBackground(Color.GRAY);
	}
}
