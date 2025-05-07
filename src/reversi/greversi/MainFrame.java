package reversi.greversi;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import reversi.RightPanels;

public class MainFrame extends JFrame {
	MainFrame() {
		// スクリーンサイズを取得
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int windowStartX = (int) (screenSize.getWidth() * 0.05);
		int windowStartY = (int) (screenSize.getHeight() * 0.05);
		int windowWidth = (int) (screenSize.getWidth() * 0.8);
		int windowHeight = (int) (screenSize.getHeight() * 0.8);

		// フォントサイズを設定
		int fontSize = 16;

		// メインフレームの設定
		setTitle("Reversi");
		setBounds(windowStartX, windowStartY, windowWidth, windowHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		// メインパネル
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout());

		// 盤パネル
		JPanel boardPanel = new JPanel();
		mainPanel.add(boardPanel);
		boardPanel.setBackground(Color.GRAY);

		RightPanels[] rightPanels = {
				new GameSettingsPanel("対局設定"),
				new SituationPanel("戦局"),
				new OutcomePanel("結果")
		};

		getContentPane().add(mainPanel, BorderLayout.CENTER);
	}
}
