package reversi.greversi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	int screenWidth, screenHeight;
	Dimension screenSize;
	GamePanel gamePanel;
	RightPanels gameSettingsPanel, playingPanel, outcomePanel;

	MainFrame(String title) {
		setTitle(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// ウィンドウサイズ設定
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) (screenSize.getWidth() * 0.9);
		screenHeight = (int) (screenSize.getHeight() * 0.9);
		setSize(screenWidth, screenHeight);

		gamePanel = new GamePanel(screenWidth, screenHeight);
		add(gamePanel, BorderLayout.WEST);

		gameSettingsPanel = new GameSettingsPanel("対局設定", screenWidth, gamePanel.getWidth(), screenHeight);
		add(gameSettingsPanel, BorderLayout.EAST);

		//		gamePanel.setBackground(Color.BLACK);
		gameSettingsPanel.setBackground(Color.GRAY);
	}
}
